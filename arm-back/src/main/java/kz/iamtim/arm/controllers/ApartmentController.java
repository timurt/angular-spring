package kz.iamtim.arm.controllers;

import kz.iamtim.arm.payload.ApartmentDto;
import kz.iamtim.arm.models.Apartment;
import kz.iamtim.arm.models.User;
import kz.iamtim.arm.payload.ErrorResponse;
import kz.iamtim.arm.security.MyUserPrincipal;
import kz.iamtim.arm.service.ApartmentService;
import kz.iamtim.arm.service.UserService;
import kz.iamtim.arm.specs.ApartmentSpecification;
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
 * Controller class for {@code Apartment} class CRUD operations.
 *
 * @author Timur Tibeyev.
 */

@RestController
@RequestMapping("/apartments")
public class ApartmentController {

    /** Service layer for db operations over users. */
    @Autowired
    ApartmentService apartmentService;

    /** Service layer for db operations over users. */
    @Autowired
    UserService userService;

    /** Logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ApartmentController.class);

    /**
     * Returns filtered list of the apartments.
     *
     * @param floorAreaSizeFrom lower bound for {@code Apartment.floorAreaSize}
     * @param floorAreaSizeTo   upper bound for {@code Apartment.floorAreaSize}
     * @param pricePerMonthFrom lower bound for {@code Apartment.pricePerMonth}
     * @param pricePerMonthTo   upper bound for {@code Apartment.pricePerMonth}
     * @param numberOfRoomsFrom lower bound for {@code Apartment.numberOfRooms}
     * @param numberOfRoomsTo   upper bound for {@code Apartment.numberOfRooms}
     * @param isRented boolean property {@code Apartment.isRented}
     * @param pageable {@code Pageable} contains page number and page size fields
     *
     * @return list of apartments
     */
    @GetMapping
    public Page<ApartmentDto> list(
            @PathParam("floorAreaSizeFrom") final Double floorAreaSizeFrom,
            @PathParam("floorAreaSizeTo") final Double floorAreaSizeTo,
            @PathParam("pricePerMonthFrom") final Double pricePerMonthFrom,
            @PathParam("pricePerMonthTo") final Double pricePerMonthTo,
            @PathParam("numberOfRoomsFrom") final Integer numberOfRoomsFrom,
            @PathParam("numberOfRoomsTo") final Integer numberOfRoomsTo,
            @PathParam("rented") final Boolean isRented,
            @PageableDefault final Pageable pageable) {

        LOGGER.debug("Get apartments endpoint called");

        final Page<Apartment> apartments = apartmentService.getByPage(
                ApartmentSpecification.filtered(floorAreaSizeFrom,
                        floorAreaSizeTo,
                        pricePerMonthFrom,
                        pricePerMonthTo,
                        numberOfRoomsFrom,
                        numberOfRoomsTo,
                        getCurrentUserPrincipal().getRole(),
                        isRented),
                pageable
        );

        Page<ApartmentDto> dtoPage = apartments.map(apartment -> new ApartmentDto(apartment));
        return dtoPage;
    }

    /**
     * Deletes corresponding apartment.
     *
     * @param apartmentId id of the apartment
     * @return http response
     */
    @DeleteMapping("/{apartmentId}")
    ResponseEntity delete(@PathVariable final Long apartmentId) {
        LOGGER.debug("Delete apartment with id {}", apartmentId);

        if (apartmentService.apartmentExists(apartmentId)) {
            apartmentService.delete(apartmentId);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Returns corresponding apartment.
     *
     * @param apartmentId id of the apartment
     * @return http response
     */
    @GetMapping("/{apartmentId}")
    ResponseEntity<ApartmentDto> get(@PathVariable final Long apartmentId) {
        LOGGER.debug("Get apartment with id {}", apartmentId);

        if (apartmentService.apartmentExists(apartmentId)) {
            final Apartment res = apartmentService.findById(apartmentId);
            if (res.isRented()) {
                if ("CLIENT".equals(getCurrentUserPrincipal().getRole())) {
                    return new ResponseEntity(HttpStatus.NOT_FOUND);
                }
            }
            return new ResponseEntity(new ApartmentDto(res), HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Saves new/edited apartment.
     *
     * @param input {@code ApartmentDto} object
     * @return http response
     */
    @PostMapping
    ResponseEntity<ApartmentDto> save(@Valid @RequestBody final ApartmentDto input) {
        LOGGER.debug("Save apartment");

        if (getRealtor(input) == null) {
            return new ResponseEntity(new ErrorResponse("Specified realtor not found"),
                    HttpStatus.BAD_REQUEST);
        }

        Apartment apartment = null;
        if (input.getId() != null) {
            apartment = apartmentService.findById(input.getId());
        }

        if (apartment == null) {
            apartment = new Apartment();
        }

        apartment.setName(input.getName());
        apartment.setDescription(input.getDescription());
        apartment.setFloorAreaSize(input.getFloorAreaSize());
        apartment.setPricePerMonth(input.getPricePerMonth());
        apartment.setNumberOfRooms(input.getNumberOfRooms());
        apartment.setLongitude(input.getLongitude());
        apartment.setLatitude(input.getLatitude());

        apartment.setRealtor(getRealtor(input));

        return new ResponseEntity(new ApartmentDto(apartmentService.save(apartment)),
                HttpStatus.OK);
    }

    /**
     * Makes specified apartment not available for renting.
     *
     * @param apartmentId id of the apartment
     * @return http response
     */
    @PostMapping("/rent/{apartmentId}")
    public ResponseEntity rent(@PathVariable final Long apartmentId) {
        LOGGER.debug("Make apartment with {} rented", apartmentId);

        if (apartmentService.apartmentExists(apartmentId)) {
            apartmentService.setRented(apartmentId, true);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Makes specified apartment available for renting.
     *
     * @param apartmentId id of the apartment
     * @return http response
     */
    @PostMapping("/free/{apartmentId}")
    public ResponseEntity free(@PathVariable final Long apartmentId) {
        LOGGER.debug("Make apartment with {} available", apartmentId);

        if (apartmentService.apartmentExists(apartmentId)) {
            apartmentService.setRented(apartmentId, false);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
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
     * Returns responsible realtor.
     *
     * @param input apartment dto
     * @return responsible realtor
     */
    private User getRealtor(final ApartmentDto input) {
        final MyUserPrincipal principal = getCurrentUserPrincipal();

        User author = null;
        if (principal.getRole().equals("ADMIN")) {
            if (input.getRealtorId() != null) {
                author = userService.findById(input.getRealtorId());
            }
        } else {
            author = principal.getUser();
        }

        return author;
    }
}
