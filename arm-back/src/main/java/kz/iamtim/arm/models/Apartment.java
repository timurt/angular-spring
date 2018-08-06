package kz.iamtim.arm.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Apartment model.
 *
 * @author Timur Tibeyev.
 */
@Entity
@Table(name = "apartments")
public class Apartment extends PersistenceObject {

    /** User name. */
    @Column(name = "name")
    private String name;

    /** User description. */
    @Column(name = "description")
    private String description;

    /** Floor area size. */
    @Column(name = "floor_area_size")
    private Double floorAreaSize;

    /** Price per month. */
    @Column(name = "price_per_month")
    private Double pricePerMonth;

    /** Number of rooms. */
    @Column(name = "number_of_rooms")
    private Integer numberOfRooms;

    /** Latitude. */
    @Column(name = "latitude")
    private Double latitude;

    /** Longitude. */
    @Column(name = "longitude")
    private Double longitude;

    /** Realtor. */
    @ManyToOne
    @JoinColumn(name = "realtor_id", referencedColumnName = "id")
    private User realtor;

    /** Flag, is apartment rented. */
    @Column(name = "is_rented")
    private boolean isRented = false;

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
     * @return realtor
     */
    public User getRealtor() {
        return realtor;
    }

    /**
     * Setter method.
     *
     * @param value realtor
     */
    public void setRealtor(User value) {
        this.realtor = value;
    }


}
