package com.almaviva.libreria_common.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GenericService<DTO, ID>
{
    Page<DTO> getAllPaged(Pageable pageable);

    DTO getById(ID id);

    DTO create(DTO dto);

    DTO update(ID id, DTO dto);

    void delete(ID id);
}