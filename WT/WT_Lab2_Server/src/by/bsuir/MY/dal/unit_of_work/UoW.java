////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2017.                                                         /
// Developed by Maksim Ermoshyn.                                               /
////////////////////////////////////////////////////////////////////////////////

package by.bsuir.MY.dal.unit_of_work;

import by.bsuir.MY.dal.model.interf.Entity;
import by.bsuir.MY.dal.repository.interf.Repository;

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
