package com.almaviva.libreria_common.controller;

import com.almaviva.libreria_common.service.GenericService;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Base controller for CRUD operations.
 *
 * @param <DTO> the request/response DTO type
 * @param <ID>  the entity identifier type
 */
public abstract class GenericController<DTO, ID> {

    protected final GenericService<DTO, ID> service;

    protected GenericController(GenericService<DTO, ID> service) {
        this.service = service;
    }

    @GetMapping("/getAll/paged")
    public ResponseEntity<Page<DTO>> getAllPaged(Pageable pageable) {
        return ResponseEntity.ok(service.getAllPaged(pageable));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<DTO> getById(@PathVariable ID id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<DTO> create(@RequestBody @Valid DTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<DTO> update(@PathVariable ID id, @RequestBody @Valid DTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable ID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}