package com.n26.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

/**
 * <p>
 * </p>
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Statistics {
    private LocalDateTime timestamp;
    private BigDecimal sum = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
    private BigDecimal avg = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
    private BigDecimal max = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
    private BigDecimal min = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
    private Long count = 0L;
}
