////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2017.                                                         /
// Developed by Maksim Ermoshyn.                                               /
////////////////////////////////////////////////////////////////////////////////

package by.bsuir.MY.dal.model.interf;

import by.bsuir.MY.domain.exception.WrongDataException;

/**
 * General interface of entities of application.
 */
public interface Entity {

    /**
     * Get id of this entity.
     *
     * @return Id of this entity.
     */
    Integer getId();

    /**
     * Set id of this entity.
     *
     * @param i Id of this entity.
     */
    void setId(Integer i);

    /**
     * Return string representation for this object.
     *
     * @return String representation for this object.
     */
    @Override
    String toString();

    /**
     * Initialize entity from string representation.
     *
     * @param record String to parse.
     */
    void fromString(String record) throws WrongDataException;

    /**
     * Check primary key.
     *
     * @param key Primary key.
     * @return {@code True} on success, {@code False} otherwise.
     */
    boolean checkPrimaryKey(Object... key);

    /**
     * Get primary key of this entity.
     *
     * @return Primary key.
     */
    Object[] getPrimaryKey();
}
