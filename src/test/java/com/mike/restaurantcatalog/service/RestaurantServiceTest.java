package com.mike.restaurantcatalog.service;

import com.mike.restaurantcatalog.dto.RestaurantDTO;
import com.mike.restaurantcatalog.entity.RestaurantEntity;
import com.mike.restaurantcatalog.exception.RestaurantNotFoundException;
import com.mike.restaurantcatalog.mapper.RestaurantMapper;
import com.mike.restaurantcatalog.repository.RestaurantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private RestaurantMapper mapper;

    @InjectMocks
    private RestaurantService restaurantService;

    @Test
    void findAllRestaurants_shouldReturnMappedDtos() {
        // given
        List<RestaurantEntity> entities = List.of(
                new RestaurantEntity(1, "Restaurant 1", "Address 1", "city 1", "Desc 1"),
                new RestaurantEntity(2, "Restaurant 2", "Address 2", "city 2", "Desc 2")
        );
        List<RestaurantDTO> dtos = List.of(
                new RestaurantDTO(1, "Restaurant 1", "Address 1", "city 1", "Desc 1"),
                new RestaurantDTO(2, "Restaurant 2", "Address 2", "city 2", "Desc 2")
        );

        when(restaurantRepository.findAll()).thenReturn(entities);
        when(mapper.toDto(entities)).thenReturn(dtos);

        // when
        List<RestaurantDTO> result = restaurantService.findAllRestaurants();

        // then
        assertEquals(dtos, result);
        verify(restaurantRepository, times(1)).findAll();
        verify(mapper, times(1)).toDto(entities);
    }

    @Test
    void addRestaurantInDB_shouldSaveEntityAndReturnMappedDto() {
        // given
        RestaurantDTO inputDto = new RestaurantDTO(0, "New Restaurant", "Address", "city", "Desc");
        RestaurantEntity toSave = new RestaurantEntity(0, "New Restaurant", "Address", "city", "Desc");
        RestaurantEntity saved = new RestaurantEntity(1, "New Restaurant", "Address", "city", "Desc");
        RestaurantDTO outputDto = new RestaurantDTO(1, "New Restaurant", "Address", "city", "Desc");

        when(mapper.toEntity(inputDto)).thenReturn(toSave);
        when(restaurantRepository.save(toSave)).thenReturn(saved);
        when(mapper.toDto(saved)).thenReturn(outputDto);

        // when
        RestaurantDTO result = restaurantService.addRestaurantInDB(inputDto);

        // then
        assertEquals(outputDto, result);
        verify(mapper, times(1)).toEntity(inputDto);
        verify(restaurantRepository, times(1)).save(toSave);
        verify(mapper, times(1)).toDto(saved);
    }

    @Test
    void fetchRestaurantById_whenIdExists_shouldReturnMappedDto() {
        // given
        Integer id = 1;
        RestaurantEntity entity = new RestaurantEntity(1, "Restaurant 1", "Address 1", "city 1", "Desc 1");
        RestaurantDTO dto = new RestaurantDTO(1, "Restaurant 1", "Address 1", "city 1", "Desc 1");

        when(restaurantRepository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);

        // when
        RestaurantDTO result = restaurantService.fetchRestaurantById(id);

        // then
        assertEquals(dto, result);
        verify(restaurantRepository, times(1)).findById(id);
        verify(mapper, times(1)).toDto(entity);
    }

    @Test
    void fetchRestaurantById_whenIdDoesNotExist_shouldThrowException() {
        // given
        Integer id = 999;

        when(restaurantRepository.findById(id)).thenReturn(Optional.empty());

        // when + then
        RestaurantNotFoundException ex = assertThrows(
                RestaurantNotFoundException.class,
                () -> restaurantService.fetchRestaurantById(id)
        );

        assertTrue(ex.getMessage().contains(String.valueOf(id)));
        verify(restaurantRepository, times(1)).findById(id);
        verify(mapper, never()).toDto(any(RestaurantEntity.class));
    }
}
