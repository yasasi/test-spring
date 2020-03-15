package com.n26.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
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
public class StatisticsResponse {
    @JsonFormat(shape= JsonFormat.Shape.STRING)
    private BigDecimal sum = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);

    @JsonFormat (shape= JsonFormat.Shape.STRING)
    private BigDecimal avg = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);;

    @JsonFormat (shape= JsonFormat.Shape.STRING)
    private BigDecimal max = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);

    @JsonFormat (shape= JsonFormat.Shape.STRING)
    private BigDecimal min = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);

    private Long count = 0L;
}
