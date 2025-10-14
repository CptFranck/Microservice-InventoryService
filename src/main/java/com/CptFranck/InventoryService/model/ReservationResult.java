package com.CptFranck.InventoryService.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResult {

    private boolean success;

    private String reason;

    public static ReservationResult success() {
        return new ReservationResult(true, null);
    }

    public static ReservationResult failure(String reason) {
        return new ReservationResult(false, reason);
    }
}
