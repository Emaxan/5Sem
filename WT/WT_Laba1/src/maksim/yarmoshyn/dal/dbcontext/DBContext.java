////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2017.                                                         /
// Developed by Maksim Ermoshyn.                                               /
////////////////////////////////////////////////////////////////////////////////

package maksim.yarmoshyn.dal.dbcontext;

import maksim.yarmoshyn.dal.model.interf.Entity;

/**
 * Database context.
 */
public interface DBContext {

    /**
     * Return set of the entities.
     *
     * @param <TEntity>   Class to extract.
     * @param entityClass Class .
     * @return {@link DbSet} of the objects.
     */
    <TEntity extends Entity> DbSet<TEntity> set(Class<TEntity> entityClass);

    /**
     * Save changes.
     *
     * @param <TEntity>   Class of entities.
     * @param entityClass Class of entities.
     * @param set         Set of entities.
     * @return {@code True} on success, {@code False} otherwise.
     */
    <TEntity extends Entity> boolean saveChanges(
            DbSet<TEntity> set,
            Class<TEntity> entityClass
    );
}
