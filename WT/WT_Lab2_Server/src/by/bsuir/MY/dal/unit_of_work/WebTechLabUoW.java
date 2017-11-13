////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2017.                                                         /
// Developed by Maksim Ermoshyn.                                               /
////////////////////////////////////////////////////////////////////////////////

package by.bsuir.MY.dal.unit_of_work;

import by.bsuir.MY.dal.repository.interf.UsrRepository;

/**
 * Unit of Work with repositories with special functionality.
 */
public interface WebTechLabUoW extends UoW {
    /**
     * Return {@link UsrRepository}.
     *
     * @return Return {@link UsrRepository}.
     */
    UsrRepository getUserRepository();

    /**
     * Save changes to the database.
     *
     * @return {@code True} on success, {@code False} otherwise.
     */
    boolean saveChanges();
}
