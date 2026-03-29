package com.almaviva.libreria_common.service;

import com.almaviva.libreria_common.entity.EntityBase;
import com.almaviva.libreria_common.mapper.GenericMapper;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;


/**
 * Base service for CRUD operations.
 *
 * @param <T>   the entity type
 * @param <DTO> the request DTO type
 * @param <ID>  the entity identifier type
 */
public abstract class AbstractGenericServiceImpl<T extends EntityBase, DTO, ID>
        implements GenericService<DTO, ID> {

    private static final String NOT_FOUND_MSG = "error.resource.notfound";

    protected final JpaRepository<T, ID> repository;
    protected final GenericMapper<T, DTO> mapper;

    protected AbstractGenericServiceImpl(JpaRepository<T, ID> repository,
                                         GenericMapper<T, DTO> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DTO> getAllPaged(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DTO> getAll(int page, int size, String orderBy, String direction) {
        Sort sort = "desc".equalsIgnoreCase(direction)
                ? Sort.by(orderBy).descending()
                : Sort.by(orderBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return repository.findAll(pageable).map(mapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public DTO getById(ID id) {
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MSG));
    }

    @Override
    @Transactional
    public DTO create(DTO dto) {
        T entity = mapper.toEntity(dto);
        T saved = repository.save(entity);
        return mapper.toDTO(saved);
    }

    @Override
    @Transactional
    public DTO update(ID id, DTO dto) {
        T existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MSG));
        mapper.updateEntityFromDTO(dto, existing);
        T saved = repository.save(existing);
        return mapper.toDTO(saved);
    }

    @Override
    @Transactional
    public void delete(ID id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(NOT_FOUND_MSG);
        }
        repository.deleteById(id);
    }
}