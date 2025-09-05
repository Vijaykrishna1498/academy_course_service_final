package com.courseacademy.courseacademyservice.controller;


import com.courseacademy.courseacademyservice.dto.ModuleDTO;
import com.courseacademy.courseacademyservice.service.ModuleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/module-service/v1/modules")
public class ModuleController {

    private final ModuleService service;

    public ModuleController(ModuleService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ModuleDTO> create(@Valid @RequestBody ModuleDTO dto) {
        ModuleDTO saved = service.create(dto);
        return ResponseEntity
                .created(URI.create("/api/module-service/v1/modules/" + saved.getModuleId()))
                .body(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModuleDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<ModuleDTO>> list() {
        return ResponseEntity.ok(service.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModuleDTO> update(@PathVariable Long id, @Valid @RequestBody ModuleDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}