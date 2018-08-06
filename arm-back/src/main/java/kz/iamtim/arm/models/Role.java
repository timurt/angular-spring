package kz.iamtim.arm.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Role model.
 *
 * @author Timur Tibeyev.
 */
@Entity
@Table(name = "roles")
public class Role extends PersistenceObject {

    /** Role name. */
    @Column(name = "name")
    private String name;

    /** Role key. */
    @Column(name = "key", unique = true)
    private String key;

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
     * @return key
     */
    public String getKey() {
        return key;
    }

    /**
     * Setter method.
     *
     * @param value name
     */
    public void setKey(String value) {
        this.key = value;
    }
}
