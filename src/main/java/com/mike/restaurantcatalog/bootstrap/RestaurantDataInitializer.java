package com.mike.restaurantcatalog.bootstrap;

import com.mike.restaurantcatalog.entity.RestaurantEntity;
import com.mike.restaurantcatalog.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RestaurantDataInitializer implements CommandLineRunner {

    private final RestaurantRepository restaurantRepository;

    @Override
    public void run(String... args) {
        // only insert if there is no data
        if (restaurantRepository.count() > 0) {
            return;
        }

        List<RestaurantEntity> restaurants = List.of(
                new RestaurantEntity(0, "Prenzlauer Hof", "Kollwitzstraße 27", "Berlin",
                        "Neighborhood restaurant in Prenzlauer Berg offering seasonal German dishes and regional wines."),
                new RestaurantEntity(0, "Mitte Tapas Bar", "Torstraße 81", "Berlin",
                        "Lively tapas bar in Mitte serving Spanish small plates and sangria in a modern setting."),
                new RestaurantEntity(0, "Tempelhofer Grill", "Manfred-von-Richthofen-Straße 5", "Berlin",
                        "Casual grill near Tempelhofer Feld specializing in steaks, burgers, and hearty sides."),
                new RestaurantEntity(0, "Vegan Vibes Neukölln", "Weserstraße 134", "Berlin",
                        "Trendy vegan restaurant in Neukölln with creative plant-based dishes and craft cocktails."),
                new RestaurantEntity(0, "Charlottenburg Brasserie", "Kantstraße 102", "Berlin",
                        "Classic brasserie near Kurfürstendamm serving French-German fusion cuisine and fine desserts."),
                new RestaurantEntity(0, "Friedrichshain Noodle House", "Simon-Dach-Straße 22", "Berlin",
                        "Asian noodle restaurant with ramen, stir-fry, and dumplings in a cozy Friedrichshain location."),
                new RestaurantEntity(0, "Wedding Curry Corner", "Müllerstraße 190", "Berlin",
                        "Colorful Indian restaurant in Wedding offering curries, tandoori dishes, and lunch specials."),
                new RestaurantEntity(0, "Spandau Seaside Café", "Am Havelufer 3", "Berlin",
                        "Relaxed café-restaurant by the Havel serving breakfast, cakes, and light Mediterranean meals."),
                new RestaurantEntity(0, "Lichtenberg Bistro & Bar", "Frankfurter Allee 231", "Berlin",
                        "Casual bistro in Lichtenberg with burgers, salads, and a wide selection of local beers."),
                new RestaurantEntity(0, "Alexanderplatz Sky Diner", "Alexanderstraße 5", "Berlin",
                        "Modern diner near Alexanderplatz offering international dishes with city views from the upper floor.")
        );

        restaurantRepository.saveAll(restaurants);
    }
}
