package com.example.scheduleservice.dto.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {
    private Long id;
    private Integer lengthInMins;
    private String director;
    private String title;
    private String description;
    private String photoUrl;
}

