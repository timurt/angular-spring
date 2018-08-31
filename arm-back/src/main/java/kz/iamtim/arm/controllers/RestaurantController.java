package kz.iamtim.arm.controllers;

import kz.iamtim.arm.models.Restaurant;
import kz.iamtim.arm.payload.RestaurantDto;
import kz.iamtim.arm.models.User;
import kz.iamtim.arm.payload.ErrorResponse;
import kz.iamtim.arm.security.MyUserPrincipal;
import kz.iamtim.arm.service.RestaurantService;
import kz.iamtim.arm.service.UserService;
import kz.iamtim.arm.specs.RestaurantSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

/**
 * Controller class for {@code Restaurant} class CRUD operations.
 *
 * @author Timur Tibeyev.
 */

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    /** Service layer for db operations over users. */
    @Autowired
    RestaurantService restaurantService;

    /** Service layer for db operations over users. */
    @Autowired
    UserService userService;

    /** Logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(RestaurantController.class);

    /**
     * Returns filtered list of the restaurants.
     *
     * @param averageCheckFrom lower bound for {@code Restaurant.averageCheckFrom}
     * @param averageCheckTo   upper bound for {@code Restaurant.averageCheckTo}
     * @param numberOfSeatsFrom lower bound for {@code Restaurant.numberOfSeatsFrom}
     * @param numberOfSeatsTo   upper bound for {@code Restaurant.numberOfSeatsTo}
     * @param hasWifi boolean property {@code Restaurant.hasWifi}
     * @param pageable {@code Pageable} contains page number and page size fields
     *
     * @return list of restaurants
     */
    @GetMapping
    public Page<RestaurantDto> list(
            @PathParam("averageCheckFrom") final Double averageCheckFrom,
            @PathParam("averageCheckTo") final Double averageCheckTo,
            @PathParam("numberOfSeatsFrom") final Integer numberOfSeatsFrom,
            @PathParam("numberOfSeatsTo") final Integer numberOfSeatsTo,
            @PathParam("hasWifi") final Boolean hasWifi,
            @PathParam("sortBy") final String sortBy,
            @PageableDefault final Pageable pageable) {

        LOGGER.debug("Get restaurants endpoint called");


        final Page<Restaurant> restaurants = restaurantService.getByPage(
                RestaurantSpecification.filtered(averageCheckFrom,
                        averageCheckTo,
                        numberOfSeatsFrom,
                        numberOfSeatsTo,
                        getCurrentUserPrincipal().getRole(),
                        getCurrentUserPrincipal().getId(),
                        hasWifi),
                pageable,
                sortBy
        );

        Page<RestaurantDto> dtoPage = restaurants.map(restaurant -> new RestaurantDto(restaurant));
        return dtoPage;
    }

    /**
     * Deletes corresponding restaurant.
     *
     * @param restaurantId id of the restaurant
     * @return http response
     */
    @DeleteMapping("/{restaurantId}")
    ResponseEntity delete(@PathVariable final Long restaurantId) {
        LOGGER.debug("Delete restaurant with id {}", restaurantId);

        if (restaurantService.restaurantExists(restaurantId)) {
            restaurantService.delete(restaurantId);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Returns corresponding restaurant.
     *
     * @param restaurantId id of the restaurant
     * @return http response
     */
    @GetMapping("/{restaurantId}")
    ResponseEntity<RestaurantDto> get(@PathVariable final Long restaurantId) {
        LOGGER.debug("Get restaurant with id {}", restaurantId);

        if (restaurantService.restaurantExists(restaurantId)) {
            final Restaurant res = restaurantService.findById(restaurantId);

            return new ResponseEntity(new RestaurantDto(res), HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Saves new/edited restaurant.
     *
     * @param input {@code RestaurantDto} object
     * @return http response
     */
    @PostMapping
    ResponseEntity<RestaurantDto> save(@Valid @RequestBody final RestaurantDto input) {
        LOGGER.debug("Save restaurant");

        if (getOwner(input) == null) {
            return new ResponseEntity(new ErrorResponse("Specified owner not found"),
                    HttpStatus.BAD_REQUEST);
        }

        Restaurant restaurant = null;
        if (input.getId() != null) {
            restaurant = restaurantService.findById(input.getId());
        }

        if (restaurant == null) {
            restaurant = new Restaurant();
        }

        restaurant.setName(input.getName());
        restaurant.setDescription(input.getDescription());
        restaurant.setAverageCheck(input.getAverageCheck());
        restaurant.setNumberOfSeats(input.getNumberOfSeats());
        restaurant.setLongitude(input.getLongitude());
        restaurant.setLatitude(input.getLatitude());

        restaurant.setOwner(getOwner(input));

        return new ResponseEntity(new RestaurantDto(restaurantService.save(restaurant)),
                HttpStatus.OK);
    }

    /**
     * Returns authenticated user.
     *
     * @return authenticated user
     */
    private MyUserPrincipal getCurrentUserPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (MyUserPrincipal) authentication.getPrincipal();
    }

    /**
     * Returns owner.
     *
     * @param input restaurant dto
     * @return owner
     */
    private User getOwner(final RestaurantDto input) {
        final MyUserPrincipal principal = getCurrentUserPrincipal();

        User author = null;
        if (principal.getRole().equals("ADMIN")) {
            if (input.getOwnerId() != null) {
                author = userService.findById(input.getOwnerId());
            }
        } else {
            author = principal.getUser();
        }

        return author;
    }
}
