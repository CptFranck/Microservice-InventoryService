package com.CptFranck.InventoryService.response;

import com.CptFranck.InventoryService.entity.VenueEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventInventoryResponse {
    private String event;
    private Long capacity;
    private VenueEntity venue;
}
