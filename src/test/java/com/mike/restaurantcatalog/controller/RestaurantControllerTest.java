package com.mike.restaurantcatalog.controller;

import com.mike.restaurantcatalog.dto.RestaurantDTO;
import com.mike.restaurantcatalog.service.RestaurantService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantControllerTest {

    @InjectMocks
    private RestaurantController restaurantController;

    @Mock
    private RestaurantService restaurantService;

    @Test
    void fetchAllRestaurants_shouldReturnListAndStatusOk() {
        // given
        List<RestaurantDTO> restaurants = List.of(
                new RestaurantDTO(1, "Restaurant 1", "Address 1", "city 1", "Desc 1"),
                new RestaurantDTO(2, "Restaurant 2", "Address 2", "city 2", "Desc 2")
        );
        when(restaurantService.findAllRestaurants()).thenReturn(restaurants);

        // when
        ResponseEntity<List<RestaurantDTO>> response = restaurantController.fetchAllRestaurants();

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(restaurants, response.getBody());
        verify(restaurantService, times(1)).findAllRestaurants();
    }

    @Test
    void addRestaurant_shouldReturnCreatedRestaurantAndStatusCreated() {
        // given
        RestaurantDTO restaurant = new RestaurantDTO(1, "Restaurant 1", "Address 1", "city 1", "Desc 1");
        when(restaurantService.addRestaurantInDB(restaurant)).thenReturn(restaurant);

        // when
        ResponseEntity<RestaurantDTO> response = restaurantController.addRestaurant(restaurant);

        // then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(restaurant, response.getBody());
        verify(restaurantService, times(1)). addRestaurantInDB(restaurant);
    }

    @Test
    void findRestaurantById_shouldReturnRestaurantAndStatusOk() {
        // given
        Integer restaurantId = 1;
        RestaurantDTO restaurant = new RestaurantDTO(1, "Restaurant 1", "Address 1", "city 1", "Desc 1");
        when(restaurantService.fetchRestaurantById(restaurantId)).thenReturn(restaurant);

        // when
        ResponseEntity<RestaurantDTO> response = restaurantController.findRestaurantById(restaurantId);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(restaurant, response.getBody());
        verify(restaurantService, times(1)).fetchRestaurantById(restaurantId);
    }
}
