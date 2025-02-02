package com.example.scheduleservice.mapper;

import com.example.scheduleservice.dto.request.ScreeningCreateDTO;
import com.example.scheduleservice.dto.response.ScreeningDTO;
import com.example.scheduleservice.model.Movie;
import com.example.scheduleservice.model.Screening;
import org.springframework.stereotype.Component;

@Component
public class ScreeningMapper {
    public Screening toScreening(ScreeningCreateDTO dto, Movie movie) {
        return new Screening(
                null,
                movie,
                dto.getCinemaHallId(),
                dto.getDateOfBeginning()
        );
    }

    public ScreeningDTO toScreeningDTO(Screening screening) {
        return new ScreeningDTO(
                screening.getId(),
                screening.getMovie().getId(),
                screening.getCinemaHallId(),
                screening.getDateOfBeginning()
        );
    }
}
