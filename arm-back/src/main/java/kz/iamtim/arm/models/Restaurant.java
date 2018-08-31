package kz.iamtim.arm.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Restaurant model.
 *
 * @author Timur Tibeyev.
 */
@Entity
@Table(name = "restaurants")
public class Restaurant extends PersistenceObject {

    /** User name. */
    @Column(name = "name")
    private String name;

    /** User description. */
    @Column(name = "description")
    private String description;

    /** Average check. */
    @Column(name = "average_check")
    private Double averageCheck;

    /** Number of seats. */
    @Column(name = "number_of_seats")
    private Integer numberOfSeats;

    /** Latitude. */
    @Column(name = "latitude")
    private Double latitude;

    /** Longitude. */
    @Column(name = "longitude")
    private Double longitude;

    /** Owner. */
    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;

    /** Flag, if restaurant has wifi. */
    @Column(name = "has_wifi")
    private boolean hasWifi = false;

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
     * @return owner
     */
    public User getOwner() {
        return owner;
    }

    /**
     * Setter method.
     *
     * @param value owner
     */
    public void setOwner(User value) {
        this.owner = value;
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

}
