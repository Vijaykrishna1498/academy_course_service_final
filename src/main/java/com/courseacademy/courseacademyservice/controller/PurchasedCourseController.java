package com.courseacademy.courseacademyservice.controller;

import com.courseacademy.courseacademyservice.dto.PurchasedCourseDTO;
import com.courseacademy.courseacademyservice.service.PurchasedCourseService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/purchase-service/v1/purchases")
public class PurchasedCourseController {

    private final PurchasedCourseService service;

    public PurchasedCourseController(PurchasedCourseService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PurchasedCourseDTO> create(@Valid @RequestBody PurchasedCourseDTO dto) {
        PurchasedCourseDTO saved = service.create(dto);
        return ResponseEntity
                .created(URI.create("/api/purchase-service/v1/purchases/" + saved.getPurchaseId()))
                .body(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchasedCourseDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<PurchasedCourseDTO>> list() {
        return ResponseEntity.ok(service.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PurchasedCourseDTO> update(@PathVariable Long id, @Valid @RequestBody PurchasedCourseDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}