package kz.iamtim.arm.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Documentation for {@code Apartment}.
 *
 * @author Timur Tibeyev.
 */
@Entity
@Table(name = "apartments")
public class Apartment extends PersistenceObject {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "floor_area_size")
    private Double floorAreaSize;

    @Column(name = "price_per_month")
    private Double pricePerMonth;

    @Column(name = "number_of_rooms")
    private Integer numberOfRooms;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @ManyToOne
    @JoinColumn(name = "realtor_id", referencedColumnName = "id")
    private User realtor;

    @Column(name = "is_rented")
    private boolean isRented = false;

    public boolean isRented() {
        return isRented;
    }

    public void setRented(boolean rented) {
        isRented = rented;
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

    public User getRealtor() {
        return realtor;
    }

    public void setRealtor(User realtor) {
        this.realtor = realtor;
    }


}
