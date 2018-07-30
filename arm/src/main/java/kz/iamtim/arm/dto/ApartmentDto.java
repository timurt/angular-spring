package kz.iamtim.arm.dto;

import kz.iamtim.arm.models.Apartment;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Documentation for {@code ApartmentDto}.
 *
 * @author Timur Tibeyev.
 */
public class ApartmentDto {
    private Long id;

    @NotNull
    @Size(min = 1, max = 20)
    private String name;

    @NotNull
    @Size(min = 1)
    private String description;

    @NotNull
    private Double floorAreaSize;

    @NotNull
    private Double pricePerMonth;

    @NotNull
    private Integer numberOfRooms;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    private LocalDateTime createdAt;

    private String realtorName;

    public ApartmentDto(Apartment apartment) {
        this.id = apartment.getId();
        this.name = apartment.getName();
        this.description = apartment.getDescription();
        this.floorAreaSize = apartment.getFloorAreaSize();
        this.pricePerMonth = apartment.getPricePerMonth();
        this.numberOfRooms = apartment.getNumberOfRooms();
        this.latitude = apartment.getLatitude();
        this.longitude = apartment.getLongitude();
        this.createdAt = apartment.getCreatedAt();
        //this.realtorName = apartment.getRealtor().getName();
    }

    public ApartmentDto() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getFloorAreaSize() {
        return floorAreaSize;
    }

    public void setFloorAreaSize(Double floorAreaSize) {
        this.floorAreaSize = floorAreaSize;
    }

    public Double getPricePerMonth() {
        return pricePerMonth;
    }

    public void setPricePerMonth(Double pricePerMonth) {
        this.pricePerMonth = pricePerMonth;
    }

    public Integer getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(Integer numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getRealtorName() {
        return realtorName;
    }

    public void setRealtorName(String realtorName) {
        this.realtorName = realtorName;
    }
}
