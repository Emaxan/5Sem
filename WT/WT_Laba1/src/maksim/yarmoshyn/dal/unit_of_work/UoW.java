////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2017.                                                         /
// Developed by Maksim Ermoshyn.                                               /
////////////////////////////////////////////////////////////////////////////////

package maksim.yarmoshyn.dal.unit_of_work;

import maksim.yarmoshyn.dal.model.interf.Entity;
import maksim.yarmoshyn.dal.repository.interf.Repository;

/**
 * Base Unit of Work to get repositories, than don't need special functionality.
 */
public interface UoW {
    /**
     * Return repository with basic functionality for TEntity.
     *
     * @param <TEntity>   Class of repository.
     * @param objectClass Class of repository.
     * @return Repository for TEntity.
     */
    <TEntity extends Entity>
    Repository<TEntity> getRepository(Class<TEntity> objectClass);
}
