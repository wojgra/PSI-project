package com.example.scheduleservice.service;

import com.example.scheduleservice.dto.request.MovieCreateDTO;
import com.example.scheduleservice.dto.response.MovieDTO;
import com.example.scheduleservice.mapper.MovieMapper;
import com.example.scheduleservice.model.Movie;
import com.example.scheduleservice.repository.MovieRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    public MovieDTO createMovie(MovieCreateDTO movieCreateDTO) {
        Movie movie = movieMapper.toMovie(movieCreateDTO);
        Movie savedMovie = movieRepository.save(movie);
        return movieMapper.toMovieDTO(savedMovie);
    }


    public MovieDTO updateMovie(Long id, MovieCreateDTO movieCreateDTO) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Movie not found"));

        if (movieCreateDTO.getLengthInMins() != null) {
            movie.setLengthInMins(movieCreateDTO.getLengthInMins());
        }
        if (StringUtils.hasText(movieCreateDTO.getDirector())) {
            movie.setDirector(movieCreateDTO.getDirector());
        }
        if (StringUtils.hasText(movieCreateDTO.getTitle())) {
            movie.setTitle(movieCreateDTO.getTitle());
        }
        if (StringUtils.hasText(movieCreateDTO.getDescription())) {
            movie.setDescription(movieCreateDTO.getDescription());
        }
        if (StringUtils.hasText(movieCreateDTO.getPhotoUrl())) {
            movie.setPhotoUrl(movieCreateDTO.getPhotoUrl());
        }

        Movie updatedMovie = movieRepository.save(movie);
        return movieMapper.toMovieDTO(updatedMovie);
    }

    public List<MovieDTO> getAllMovies() {
        return movieRepository.findAll()
                .stream()
                .map(movieMapper::toMovieDTO)
                .collect(Collectors.toList());
    }

    public MovieDTO getMovieById(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Movie not found"));
        return movieMapper.toMovieDTO(movie);
    }
}
