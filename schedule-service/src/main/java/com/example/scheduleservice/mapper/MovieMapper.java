package com.example.scheduleservice.mapper;

import com.example.scheduleservice.dto.request.MovieCreateDTO;
import com.example.scheduleservice.dto.response.MovieDTO;
import com.example.scheduleservice.model.Movie;
import org.springframework.stereotype.Service;

@Service
public class MovieMapper {
    public Movie toMovie(MovieCreateDTO dto) {
        return new Movie(
                null,
                null,
                dto.getLengthInMins(),
                dto.getDirector(),
                dto.getTitle(),
                dto.getDescription(),
                dto.getPhotoUrl()
        );
    }

    public MovieDTO toMovieDTO(Movie movie) {
        return new MovieDTO(
                movie.getId(),
                movie.getLengthInMins(),
                movie.getDirector(),
                movie.getTitle(),
                movie.getDescription(),
                movie.getPhotoUrl()
        );
    }
}
