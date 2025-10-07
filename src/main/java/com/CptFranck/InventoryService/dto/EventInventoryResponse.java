package com.CptFranck.InventoryService.dto;

import com.CptFranck.InventoryService.entity.VenueEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventInventoryResponse {

    private Long eventId;

    private String event;

    private Long capacity;

    private VenueEntity venue;

    private BigDecimal ticketPrice;
}
