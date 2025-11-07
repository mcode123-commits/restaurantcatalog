package com.mike.restaurantcatalog.mapper;

import com.mike.restaurantcatalog.dto.RestaurantDTO;
import com.mike.restaurantcatalog.entity.RestaurantEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    RestaurantEntity toEntity(RestaurantDTO restaurantDTO);

    List<RestaurantEntity> toEntity(List<RestaurantDTO> restaurantDTOList);

    RestaurantDTO toDto(RestaurantEntity restaurantEntity);

    List<RestaurantDTO> toDto(List<RestaurantEntity> restaurantEntityList);
}
