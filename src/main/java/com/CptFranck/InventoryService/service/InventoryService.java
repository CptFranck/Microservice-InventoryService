package com.CptFranck.InventoryService.service;

import com.CptFranck.InventoryService.entity.EventEntity;
import com.CptFranck.InventoryService.repository.EventRepository;
import com.CptFranck.InventoryService.response.EventInventoryResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryService {


    private final EventRepository eventRepository;

    public InventoryService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
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
}
