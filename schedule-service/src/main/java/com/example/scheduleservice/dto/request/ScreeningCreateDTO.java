package com.example.scheduleservice.dto.request;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScreeningCreateDTO {
    private Long movieId;
    private Integer cinemaHallId;
    private LocalDateTime dateOfBeginning;
}
