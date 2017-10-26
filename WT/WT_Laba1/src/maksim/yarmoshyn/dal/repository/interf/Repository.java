////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2017.                                                         /
// Developed by Maksim Ermoshyn.                                               /
////////////////////////////////////////////////////////////////////////////////

package maksim.yarmoshyn.dal.repository.interf;

import maksim.yarmoshyn.dal.exception.EntityNotFoundException;
import maksim.yarmoshyn.dal.model.interf.Entity;

import java.util.List;

/**
 * Interface for repositories.
 *
 * @param <TEntity> Class of the object.
 */
public interface Repository<TEntity extends Entity> {

    /**
     * Create new record in database.
     *
     * @param o new object.
     */
    void create(TEntity o);

    /**
     * Get instance by Id.
     *
     * @param id Id of the {@link TEntity}.
     * @return {@link TEntity} or null, if there are no record with this key.
     */
    TEntity getById(int id);

    /**
     * Get all records.
     *
     * @return List of {@link TEntity} from database.
     */
    List<TEntity> getAll();

    /**
     * Update record with correspond primary key.
     *
     * @param o {@link TEntity} to update.
     * @throws EntityNotFoundException when Entity doesn't exist
     */
    void update(TEntity o) throws EntityNotFoundException;

    /**
     * Delete record from database.
     *
     * @param entity {@link TEntity} to delete.
     */
    void delete(TEntity entity);

    /**
     * Save changes to the database.
     *
     * @return {@code True} on success, {@code False} otherwise.
     */
    boolean saveChanges();

    /**
     * Set free id to the entity.
     *
     * @param entity Entity to set Id.
     */
    void setId(TEntity entity);
}
