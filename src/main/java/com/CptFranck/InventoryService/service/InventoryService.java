package com.CptFranck.InventoryService.service;

import com.CptFranck.InventoryService.entity.EventEntity;
import com.CptFranck.InventoryService.entity.VenueEntity;
import com.CptFranck.InventoryService.repository.EventRepository;
import com.CptFranck.InventoryService.repository.VenueRepository;
import com.CptFranck.InventoryService.dto.EventInventoryResponse;
import com.CptFranck.InventoryService.dto.VenueInventoryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InventoryService {

    private final EventRepository eventRepository;
    private final VenueRepository venueRepository;

    public InventoryService(EventRepository eventRepository, VenueRepository venueRepository) {
        this.eventRepository = eventRepository;
        this.venueRepository = venueRepository;
    }

    public List<EventInventoryResponse> getAllEvents() {
        final List<EventEntity> events = eventRepository.findAll();

        return events.stream().map(event -> EventInventoryResponse.builder()
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

    public void updateEventCapacity(final Long eventId, final Long ticketsBooked) {
        final EventEntity event = eventRepository.findById(eventId).orElse(null);
        assert event != null;
        event.setLeftCapacity(event.getLeftCapacity() - ticketsBooked);
        eventRepository.saveAndFlush(event);
        log.info("Updated event capacity for event id: {} with tickets booked: {}", eventId, ticketsBooked);
    }

    public EventInventoryResponse getEventInventory(final Long eventId) {
        final EventEntity event = eventRepository.findById(eventId).orElse(null);

        assert event != null;
        return EventInventoryResponse.builder()
                .event(event.getName())
                .capacity(event.getLeftCapacity())
                .venue(event.getVenue())
                .ticketPrice(event.getTicketPrice())
                .eventId(event.getId())
                .build();
    }
}
