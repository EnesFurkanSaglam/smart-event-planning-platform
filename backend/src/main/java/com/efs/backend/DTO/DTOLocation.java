package com.efs.backend.DTO;

import java.math.BigDecimal;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DTOLocation {

    private Long locationId;

    private String address;

    private BigDecimal latitude;

    private BigDecimal longitude;

}