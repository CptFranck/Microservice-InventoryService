package com.CptFranck.InventoryService.controller;

import com.CptFranck.InventoryService.dto.EventInventoryResponse;
import com.CptFranck.InventoryService.dto.VenueInventoryResponse;
import com.CptFranck.InventoryService.service.InventoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/inventory/events")
    public @ResponseBody List<EventInventoryResponse> inventoryGetAllEvents(){
        return inventoryService.getAllEvents();
    }

    @GetMapping("/inventory/venue/{venueId}")
    public @ResponseBody VenueInventoryResponse inventoryByVenueId(@PathVariable("venueId") Long venueId){
        return inventoryService.getVenueInformation(venueId);
    }

    @GetMapping("/inventory/event/{eventId}")
    public @ResponseBody EventInventoryResponse inventoryForEvent(@PathVariable("eventId") Long eventId) {
        return inventoryService.getEventInventory(eventId);
    }
}
