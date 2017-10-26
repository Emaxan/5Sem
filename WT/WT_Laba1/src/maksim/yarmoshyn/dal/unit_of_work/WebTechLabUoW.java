////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2017.                                                         /
// Developed by Maksim Ermoshyn.                                               /
////////////////////////////////////////////////////////////////////////////////

package maksim.yarmoshyn.dal.unit_of_work;

import maksim.yarmoshyn.dal.repository.interf.BkRepository;
import maksim.yarmoshyn.dal.repository.interf.UsrRepository;

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
     * Return {@link BkRepository}.
     *
     * @return Return {@link BkRepository}.
     */
    BkRepository getBookRepository();

    /**
     * Save changes to the database.
     *
     * @return {@code True} on success, {@code False} otherwise.
     */
    boolean saveChanges();
}
