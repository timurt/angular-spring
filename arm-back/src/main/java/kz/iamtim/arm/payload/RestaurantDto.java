package kz.iamtim.arm.payload;

import kz.iamtim.arm.models.Restaurant;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Dto class for representing {@code Restaurant} class.
 *
 * @author Timur Tibeyev.
 */
public class RestaurantDto {
    /** Id. */
    private Long id;

    /** Name. */
    @NotBlank
    @Size(min = 1, max = 255)
    private String name;

    /** Description. */
    @NotBlank
    private String description;

    /** Average check. */
    @NotNull
    private Double averageCheck;

    /** Number of seats. */
    @NotNull
    private Integer numberOfSeats;

    /** Latitude. */
    @NotNull
    private Double latitude;

    /** Longitude. */
    @NotNull
    private Double longitude;

    /** Flag, if restaurant has wifi. */
    private boolean hasWifi;

    /** Created date. */
    private LocalDateTime createdAt;

    /** Owner name. */
    private String ownerName;

    /** Owner id. */
    private Long ownerId;



    /**
     * Creates new instance of {@code Restaurant} class.
     *
     * @param restaurant restaurant
     */
    public RestaurantDto(Restaurant restaurant) {
        this.id = restaurant.getId();
        this.name = restaurant.getName();
        this.description = restaurant.getDescription();
        this.averageCheck = restaurant.getAverageCheck();
        this.hasWifi = restaurant.isHasWifi();
        this.numberOfSeats = restaurant.getNumberOfSeats();
        this.latitude = restaurant.getLatitude();
        this.longitude = restaurant.getLongitude();
        this.createdAt = restaurant.getCreatedAt();

        if (restaurant.getOwner() != null) {
            this.ownerName = restaurant.getOwner().getName();
            this.ownerId = restaurant.getOwner().getId();
        }
    }

    /**
     * Creates new instance of {@code Restaurant} class.
     */
    public RestaurantDto() {

    }

    /**
     * Getter method.
     *
     * @return entity id
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter method.
     *
     * @param value entity id
     */
    public void setId(Long value) {
        this.id = value;
    }

    /**
     * Getter method.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method.
     *
     * @param value name
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Getter method.
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter method.
     *
     * @param value description
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Getter method.
     *
     * @return latitude
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * Setter method.
     *
     * @param value latitude
     */
    public void setLatitude(Double value) {
        this.latitude = value;
    }

    /**
     * Getter method.
     *
     * @return longitude
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * Setter method.
     *
     * @param value longitude
     */
    public void setLongitude(Double value) {
        this.longitude = value;
    }

    /**
     * Getter method.
     *
     * @return created date
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Setter method.
     *
     * @param value created date
     */
    public void setCreatedAt(LocalDateTime value) {
        this.createdAt = value;
    }

    /**
     * Getter method.
     *
     * @return average check
     */
    public Double getAverageCheck() {
        return averageCheck;
    }

    /**
     * Setter method.
     *
     * @param value average check
     */
    public void setAverageCheck(Double value) {
        this.averageCheck = value;
    }

    /**
     * Getter method.
     *
     * @return number of the seats
     */
    public Integer getNumberOfSeats() {
        return numberOfSeats;
    }

    /**
     * Setter method.
     *
     * @param value number of the seats
     */
    public void setNumberOfSeats(Integer value) {
        this.numberOfSeats = value;
    }

    /**
     * Getter method.
     *
     * @return owner name
     */
    public String getOwnerName() {
        return ownerName;
    }

    /**
     * Setter method.
     *
     * @param ownerName owner name
     */
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    /**
     * Getter method.
     *
     * @return owner id
     */
    public Long getOwnerId() {
        return ownerId;
    }

    /**
     * Setter method.
     *
     * @param ownerId owner id
     */
    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    /**
     * Getter method.
     *
     * @return flag if has wifi
     */
    public boolean isHasWifi() {
        return hasWifi;
    }

    /**
     * Setter method.
     *
     * @param value hasWifi
     */
    public void setHasWifi(boolean value) {
        this.hasWifi = value;
    }
}
