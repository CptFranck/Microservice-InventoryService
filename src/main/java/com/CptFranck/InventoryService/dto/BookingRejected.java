package com.CptFranck.InventoryService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingRejected {
    private Long userId;

    private Long eventId;

    private Long ticketCount;

    private String reason;
}
