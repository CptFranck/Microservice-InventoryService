package com.CptFranck.InventoryService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingConfirmed {

    private Long userId;

    private Long eventId;

    private Long ticketCount;

    private BigDecimal totalPrice;
}
