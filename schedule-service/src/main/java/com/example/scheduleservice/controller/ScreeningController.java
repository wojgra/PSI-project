package com.example.scheduleservice.controller;

import com.example.scheduleservice.dto.request.ScreeningCreateDTO;
import com.example.scheduleservice.dto.response.ScreeningDTO;
import com.example.scheduleservice.service.ScreeningService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedule-service/screenings")
@RequiredArgsConstructor
public class ScreeningController {
    private final ScreeningService screeningService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ScreeningDTO> createScreening(@RequestBody ScreeningCreateDTO screeningCreateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(screeningService.createScreening(screeningCreateDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ScreeningDTO> updateScreening(@PathVariable Long id, @RequestBody ScreeningCreateDTO screeningCreateDTO) {
        return ResponseEntity.ok(screeningService.updateScreening(id, screeningCreateDTO));
    }

    @GetMapping
    public ResponseEntity<List<ScreeningDTO>> getAllScreenings() {
        return ResponseEntity.ok(screeningService.getAllScreenings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScreeningDTO> getScreeningById(@PathVariable Long id) {
        return ResponseEntity.ok(screeningService.getScreeningById(id));
    }
}
