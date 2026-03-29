package com.almaviva.libreria_common.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GenericService<DTO, ID>
{
    Page<DTO> getAllPaged(Pageable pageable);

    Page<DTO> getAll(int page, int size, String orderBy, String direction);

    DTO getById(ID id);

    DTO create(DTO dto);

    DTO update(ID id, DTO dto);

    void delete(ID id);
}