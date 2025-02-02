package com.example.scheduleservice.mapper;

import com.example.scheduleservice.dto.request.ScreeningCreateDTO;
import com.example.scheduleservice.dto.response.MovieDTO;
import com.example.scheduleservice.dto.response.ScreeningDTO;
import com.example.scheduleservice.model.Movie;
import com.example.scheduleservice.model.Screening;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScreeningMapper {

    private final MovieMapper movieMapper;

    public Screening toScreening(ScreeningCreateDTO dto, Movie movie) {
        return new Screening(
                null,
                movie,
                dto.getCinemaHallId(),
                dto.getDateOfBeginning()
        );
    }

    public ScreeningDTO toScreeningDTO(Screening screening) {
        MovieDTO movieDTO = movieMapper.toMovieDTO(screening.getMovie());

        return new ScreeningDTO(
                screening.getId(),
                movieDTO,
                screening.getCinemaHallId(),
                screening.getDateOfBeginning()
        );
    }
}
