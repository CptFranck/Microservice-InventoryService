package com.CptFranck.InventoryService.controller;

import com.CptFranck.InventoryService.response.EventInventoryResponse;
import com.CptFranck.InventoryService.service.inventoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class InventoryController {

    private final com.CptFranck.InventoryService.service.inventoryService inventoryService;

    public InventoryController(inventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/inventory/events")
    public @ResponseBody List<EventInventoryResponse> inventoryGetAllResponses(){
        return inventoryService.getAllEvents();
    }
}
