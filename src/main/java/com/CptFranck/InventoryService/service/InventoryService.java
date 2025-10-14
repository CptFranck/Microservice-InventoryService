package com.CptFranck.InventoryService.service;

import com.CptFranck.InventoryService.dto.*;
import com.CptFranck.InventoryService.entity.EventEntity;
import com.CptFranck.InventoryService.entity.VenueEntity;
import com.CptFranck.InventoryService.repository.EventRepository;
import com.CptFranck.InventoryService.repository.VenueRepository;
import com.CptFranck.dto.BookingConfirmed;
import com.CptFranck.dto.BookingEvent;
import com.CptFranck.dto.BookingRejected;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InventoryService {

    private final EventRepository eventRepository;

    private final VenueRepository venueRepository;

    private final KafkaTemplate<String, BookingRejected> rejectedKafkaTemplate;

    private final KafkaTemplate<String, BookingConfirmed> confirmedKafkaTemplate;

    public InventoryService(EventRepository eventRepository, VenueRepository venueRepository, KafkaTemplate<String, BookingConfirmed> confirmedKafkaTemplate, KafkaTemplate<String, BookingRejected> rejectedKafkaTemplate) {
        this.eventRepository = eventRepository;
        this.venueRepository = venueRepository;
        this.rejectedKafkaTemplate = rejectedKafkaTemplate;
        this.confirmedKafkaTemplate = confirmedKafkaTemplate;
    }

    public List<EventInventoryResponse> getAllEvents() {
        final List<EventEntity> events = eventRepository.findAll();

        return events.stream().map(event -> EventInventoryResponse.builder()
                .eventId(event.getId())
                .event(event.getName())
                .capacity(event.getLeftCapacity())
                .venue(event.getVenue())
                .build())
                .collect(Collectors.toList());
    }

    public VenueInventoryResponse getVenueInformation(Long venueId) {
        final VenueEntity venue = venueRepository.findById(venueId).orElse(null);

        assert venue != null;
        return VenueInventoryResponse.builder()
                        .venueId(venue.getId())
                        .venueName(venue.getName())
                        .totalCapacity(venue.getTotalCapacity())
                        .build();
    }

    public EventInventoryResponse getEventInventory(final Long eventId) {
        final EventEntity event = eventRepository.findById(eventId).orElse(null);

        assert event != null;
        return EventInventoryResponse.builder()
                .eventId(event.getId())
                .event(event.getName())
                .capacity(event.getLeftCapacity())
                .venue(event.getVenue())
                .ticketPrice(event.getTicketPrice())
                .build();
    }

    @KafkaListener(topics = "booking-event", groupId = "inventory-service")
    public void handleBookingRequested(BookingEvent event) {
        log.info("Received booking event: {}", event);

        boolean reserved = tryReserveTickets(event.getEventId(), event.getTicketCount());
        if (reserved) {
            BookingConfirmed confirmed = new BookingConfirmed();
            confirmed.setUserId(event.getUserId());
            confirmed.setEventId(event.getEventId());
            confirmed.setTicketCount(event.getTicketCount());
            confirmed.setTotalPrice(calculateTotal(event.getEventId(), Math.toIntExact(event.getTicketCount())));
            log.info("Emit booking confirmed: {}", event);

            confirmedKafkaTemplate.send("booking-confirmed", confirmed);
        } else {
            BookingRejected rejected = new BookingRejected();
            rejected.setUserId(event.getUserId());
            rejected.setEventId(event.getEventId());
            rejected.setTicketCount(event.getTicketCount());
            rejected.setReason("Not enough tickets");
            log.info("Emit booking rejected: {}", event);

            rejectedKafkaTemplate.send("booking-rejected", rejected);
        }
    }

    private boolean tryReserveTickets(Long eventId, Long ticketsBooked) {
        final EventEntity event = eventRepository.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found"));

        if (10 < ticketsBooked) {
            log.info("Reservation failed: can't booked more than 10 tickets");
            return false;
        }

        if (event.getLeftCapacity() < ticketsBooked) {
            log.info("Reservation failed: No enough tickets for event: {}", eventId);
            return false;
        }

        event.setLeftCapacity(event.getLeftCapacity() - ticketsBooked);
        eventRepository.save(event);

        log.info("Reservation succeed :Updated event capacity for event id: {} with tickets booked: {}", eventId, ticketsBooked);
        return true;
    }

    private BigDecimal calculateTotal(Long eventId, int count) {
        return eventRepository.findById(eventId).orElseThrow().getTicketPrice().multiply(BigDecimal.valueOf(count));
    }
}
