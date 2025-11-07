package com.mike.restaurantcatalog.service;

import com.mike.restaurantcatalog.dto.RestaurantDTO;
import com.mike.restaurantcatalog.entity.RestaurantEntity;
import com.mike.restaurantcatalog.exception.RestaurantNotFoundException;
import com.mike.restaurantcatalog.mapper.RestaurantMapper;
import com.mike.restaurantcatalog.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantMapper mapper;

    public List<RestaurantDTO> findAllRestaurants() {
        List<RestaurantEntity> allRestaurantEntities = restaurantRepository.findAll();
        return this.mapper.toDto(allRestaurantEntities);
    }

    public RestaurantDTO addRestaurantInDB(RestaurantDTO restaurantDTO) {
        RestaurantEntity toSave = this.mapper.toEntity(restaurantDTO);
        RestaurantEntity saved = restaurantRepository.save(toSave);
        return this.mapper.toDto(saved);
    }

    public RestaurantDTO fetchRestaurantById(Integer id) {
        return restaurantRepository.findById(id).map(mapper::toDto).orElseThrow(() -> new RestaurantNotFoundException(id));
    }

}
