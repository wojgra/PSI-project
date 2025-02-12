package com.example.scheduleservice.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScreeningDTO {
    private Long id;
    private MovieDTO movie;
    private Integer cinemaHallId;
    private LocalDateTime dateOfBeginning;
}

