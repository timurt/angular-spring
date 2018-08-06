package kz.iamtim.arm.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * Represents super class for all domain classes.
 *
 * @author Timur Tibeyev.
 */
@MappedSuperclass
public abstract class PersistenceObject {

    /** Entity id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Flag, if entity is deleted. */
    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    /** Created date. */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    /** Updated date. */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    /** Deleted date. */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    /**
     * Getter method.
     *
     * @return boolean value
     */
    public boolean isDeleted() {
        return isDeleted;
    }

    /**
     * Setter method.
     *
     * @param value boolean value
     */
    public void setDeleted(boolean value) {
        isDeleted = value;
    }

    /**
     * Getter method.
     *
     * @return updated date
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Setter method.
     *
     * @param value updated date
     */
    public void setUpdatedAt(LocalDateTime value) {
        this.updatedAt = value;
    }

    /**
     * Getter method.
     *
     * @return deleted date
     */
    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    /**
     * Setter method.
     *
     * @param value deleted date
     */
    public void setDeletedAt(LocalDateTime value) {
        this.deletedAt = value;
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
}
