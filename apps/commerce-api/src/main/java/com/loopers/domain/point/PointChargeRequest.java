package com.loopers.domain.point;

import jakarta.validation.constraints.NotNull;

public record PointChargeRequest(
        @NotNull
        int amount
) {
}
