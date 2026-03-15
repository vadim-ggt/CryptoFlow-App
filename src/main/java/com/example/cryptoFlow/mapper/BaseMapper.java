package com.example.cryptoFlow.mapper;

import org.mapstruct.*;

import java.util.List;

@MapperConfig(
        componentModel = "spring",
        builder = @Builder(disableBuilder = true),
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT
)
public interface BaseMapper<E,D> {

    D toDto(E e);

    E toEntity(D dto);

    List<D> toDtoList(Iterable<E> list);

    List<E> toEntityList(Iterable<D> list);

    E merge(@MappingTarget E entity, D dto);

}
