package com.almaviva.libreria_common.mapper;

import com.almaviva.libreria_common.entity.EntityBase;
import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

/**
 * @param <DTO> the request DTO type
 * @param <T>   the entity type
 */
public interface GenericMapper<T extends EntityBase, DTO>
{
    DTO toDTO(T entity);

    T toEntity(DTO dto);

    /**
     * @MappingTarget — dice a MapStruct che quell'oggetto è il bersaglio da modificare in-place,
     * non un valore di ritorno.*/
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(DTO dto, @MappingTarget T entity);

    List<DTO> toDtoList(List<T> list);
}