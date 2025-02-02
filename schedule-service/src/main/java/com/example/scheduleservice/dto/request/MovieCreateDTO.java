package com.example.scheduleservice.dto.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieCreateDTO {
    private Integer lengthInMins;
    private String director;
    private String title;
    private String description;
    private String photoUrl;
}
