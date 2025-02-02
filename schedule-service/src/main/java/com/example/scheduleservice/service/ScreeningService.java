package com.example.scheduleservice.service;

import com.example.scheduleservice.dto.request.ScreeningCreateDTO;
import com.example.scheduleservice.dto.response.ScreeningDTO;
import com.example.scheduleservice.mapper.ScreeningMapper;
import com.example.scheduleservice.model.Movie;
import com.example.scheduleservice.model.Screening;
import com.example.scheduleservice.repository.MovieRepository;
import com.example.scheduleservice.repository.ScreeningRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScreeningService {
    private final ScreeningRepository screeningRepository;
    private final MovieRepository movieRepository;
    private final ScreeningMapper screeningMapper;

    public ScreeningDTO createScreening(ScreeningCreateDTO dto) {
        Movie movie = movieRepository.findById(dto.getMovieId())
                .orElseThrow(() -> new EntityNotFoundException("Movie not found"));

        Screening screening = screeningMapper.toScreening(dto, movie);
        Screening savedScreening = screeningRepository.save(screening);

        return screeningMapper.toScreeningDTO(savedScreening);
    }

    public ScreeningDTO updateScreening(Long id, ScreeningCreateDTO dto) {
        Screening screening = screeningRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Screening not found"));

        if (dto.getMovieId() != null) {
            Movie movie = movieRepository.findById(dto.getMovieId())
                    .orElseThrow(() -> new EntityNotFoundException("Movie not found"));
            screening.setMovie(movie);
        }

        if (dto.getCinemaHallId() != null) {
            screening.setCinemaHallId(dto.getCinemaHallId());
        }

        if (dto.getDateOfBeginning() != null) {
            screening.setDateOfBeginning(dto.getDateOfBeginning());
        }

        Screening updatedScreening = screeningRepository.save(screening);
        return screeningMapper.toScreeningDTO(updatedScreening);
    }

    public List<ScreeningDTO> getAllScreenings() {
        return screeningRepository.findAll()
                .stream()
                .map(screeningMapper::toScreeningDTO)
                .collect(Collectors.toList());
    }

    public ScreeningDTO getScreeningById(Long id) {
        Screening screening = screeningRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Screening not found"));

        return screeningMapper.toScreeningDTO(screening);
    }
}
