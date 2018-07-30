package kz.iamtim.arm.controllers;

import kz.iamtim.arm.dto.ApartmentDto;
import kz.iamtim.arm.models.Apartment;
import kz.iamtim.arm.service.ApartmentService;
import kz.iamtim.arm.specs.ApartmentSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

/**
 * Documentation for {@code ApartmentController}.
 *
 * @author Timur Tibeyev.
 */

@RestController
@RequestMapping("/apartments")
public class ApartmentController {

    @Autowired
    ApartmentService apartmentService;

    @GetMapping
    public Page<ApartmentDto> list(
            @PathParam("floorAreaSizeFrom") Double floorAreaSizeFrom,
            @PathParam("floorAreaSizeTo") Double floorAreaSizeTo,
            @PathParam("pricePerMonthFrom") Double pricePerMonthFrom,
            @PathParam("pricePerMonthTo") Double pricePerMonthTo,
            @PathParam("numberOfRoomsFrom") Integer numberOfRoomsFrom,
            @PathParam("numberOfRoomsTo") Integer numberOfRoomsTo,
            @PageableDefault(size = 10) Pageable pageable) {

        final Page<Apartment> apartments = apartmentService.getByPage(
                ApartmentSpecification.filtered(floorAreaSizeFrom,
                        floorAreaSizeTo,
                        pricePerMonthFrom,
                        pricePerMonthTo,
                        numberOfRoomsFrom,
                        numberOfRoomsTo),
                pageable
        );

        Page<ApartmentDto> dtoPage = apartments.map(apartment -> new ApartmentDto(apartment));
        return dtoPage;
    }

    @DeleteMapping("/{apartmentId}")
    public ResponseEntity delete(@PathVariable Long apartmentId) {
        System.out.println("Deleted Apartment " + apartmentId);
        if (apartmentService.apartmentExists(apartmentId)) {
            apartmentService.delete(apartmentId);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{apartmentId}")
    ResponseEntity<ApartmentDto> get(@PathVariable Long apartmentId) {
        if (apartmentService.apartmentExists(apartmentId)) {
            final Apartment res = apartmentService.findById(apartmentId);
            return new ResponseEntity(new ApartmentDto(res), HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    ApartmentDto save(@RequestBody ApartmentDto input) {
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

        return new ApartmentDto(apartmentService.save(apartment));
    }
}
