package kz.iamtim.arm.payload;

import kz.iamtim.arm.models.Apartment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Dto class for representing {@code Apartment} class.
 *
 * @author Timur Tibeyev.
 */
public class ApartmentDto {
    /** Id. */
    private Long id;

    /** Name. */
    @NotBlank
    @Size(min = 1, max = 20)
    private String name;

    /** description. */
    @NotBlank
    private String description;

    /** Floor area size. */
    @NotNull
    private Double floorAreaSize;

    /** Price per month. */
    @NotNull
    private Double pricePerMonth;

    /** Number of rooms. */
    @NotNull
    private Integer numberOfRooms;

    /** Latitude. */
    @NotNull
    private Double latitude;

    /** Longitude. */
    @NotNull
    private Double longitude;

    /** Flag, if apartment is rented. */
    private boolean isRented;

    /** Created date. */
    private LocalDateTime createdAt;

    /** Realtor name. */
    private String realtorName;

    /** Realtor id. */
    private Long realtorId;

    /**
     * Getter method.
     *
     * @return realtor id
     */
    public Long getRealtorId() {
        return realtorId;
    }

    /**
     * Setter method.
     *
     * @param value realtor id
     */
    public void setRealtorId(Long value) {
        this.realtorId = value;
    }

    /**
     * Getter method.
     *
     * @return is rented
     */
    public boolean isRented() {
        return isRented;
    }

    /**
     * Setter method.
     *
     * @param value is rented
     */
    public void setRented(boolean value) {
        isRented = value;
    }

    /**
     * Creates new instance of {@code Apartment} class.
     *
     * @param apartment apartment
     */
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
        this.isRented = apartment.isRented();

        if (apartment.getRealtor() != null) {
            this.realtorName = apartment.getRealtor().getName();
            this.realtorId = apartment.getRealtor().getId();
        }
    }

    /**
     * Creates new instance of {@code Apartment} class.
     */
    public ApartmentDto() {

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
     * @return floor area size
     */
    public Double getFloorAreaSize() {
        return floorAreaSize;
    }

    /**
     * Setter method.
     *
     * @param value floor area size
     */
    public void setFloorAreaSize(Double value) {
        this.floorAreaSize = value;
    }

    /**
     * Getter method.
     *
     * @return price per month
     */
    public Double getPricePerMonth() {
        return pricePerMonth;
    }

    /**
     * Setter method.
     *
     * @param value price per month
     */
    public void setPricePerMonth(Double value) {
        this.pricePerMonth = value;
    }

    /**
     * Getter method.
     *
     * @return number of rooms
     */
    public Integer getNumberOfRooms() {
        return numberOfRooms;
    }

    /**
     * Setter method.
     *
     * @param value number of rooms
     */
    public void setNumberOfRooms(Integer value) {
        this.numberOfRooms = value;
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
     * @return realtor name
     */
    public String getRealtorName() {
        return realtorName;
    }

    /**
     * Setter method.
     *
     * @param value realtor name
     */
    public void setRealtorName(String value) {
        this.realtorName = value;
    }
}
