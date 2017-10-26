////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2017.                                                         /
// Developed by Maksim Ermoshyn.                                               /
////////////////////////////////////////////////////////////////////////////////

package maksim.yarmoshyn.dal.repository;

import maksim.yarmoshyn.dal.dbcontext.DBContext;
import maksim.yarmoshyn.dal.dbcontext.DbSet;
import maksim.yarmoshyn.dal.exception.EntityAlreadyExistException;
import maksim.yarmoshyn.dal.exception.EntityNotFoundException;
import maksim.yarmoshyn.dal.model.interf.Entity;
import maksim.yarmoshyn.dal.repository.interf.Repository;
import org.jetbrains.annotations.Contract;

import java.util.List;

/**
 * Base functionality for repositories.
 *
 * @param <TEntity> Class of the objects.
 */
public class BaseRepository<TEntity extends Entity>
        implements Repository<TEntity> {

    /**
     * Set of the entities.
     */
    private final DbSet<TEntity> dbSet;
    /**
     * Path to database file.
     */
    private final DBContext ctx;
    /**
     * Class of the entity.
     */
    private Class<TEntity> entityClass;

    /**
     * Create new instance of {@link BaseRepository}.
     *
     * @param context      Database context.
     * @param tEntityClass Class of the entity.
     */
    public BaseRepository(
            final DBContext context,
            final Class<TEntity> tEntityClass
    ) {
        this.ctx = context;
        this.entityClass = tEntityClass;
        this.dbSet = context.set(tEntityClass);
    }

    /**
     * Get set of entities from database.
     *
     * @return Set of entities from database.
     */
    @Contract(pure = true)
    final DbSet<TEntity> getDbSet() {
        return dbSet;
    }

    /**
     * Get database context.
     *
     * @return Database context.
     */
    public DBContext getCtx() {
        return ctx;
    }

    /**
     * Create new record in database.
     *
     * @param entity new object.
     */
    @Override
    public void create(final TEntity entity) {
        try {
            dbSet.add(entity);
        } catch (EntityAlreadyExistException e) {
            //TODO.
        }
    }

    /**
     * Get instance by Id.
     *
     * @param id Id of the {@link TEntity}.
     * @return {@link TEntity} or null, if there are no record with this key.
     */
    @Override
    public TEntity getById(final int id) {
        return dbSet.find(id);
    }

    /**
     * Get all records.
     *
     * @return List of {@link TEntity} from database.
     */
    @Override
    public List<TEntity> getAll() {
        return dbSet.getAll();
    }

    /**
     * Update record with correspond primary key.
     *
     * @param entity {@link TEntity} to update.
     */
    @Override
    public void update(final TEntity entity) throws EntityNotFoundException {
        dbSet.update(entity);
    }

    /**
     * Delete record from database.
     *
     * @param entity {@link TEntity} to delete.
     */
    @Override
    public void delete(final TEntity entity) {
        dbSet.remove(entity);
    }

    /**
     * Save changes to the database.
     *
     * @return {@code True} on success, {@code False} otherwise.
     */
    @Override
    public boolean saveChanges() {
        return getCtx().saveChanges(getDbSet(), entityClass);
    }

    /**
     * Set free id to the entity.
     *
     * @param entity Entity to set Id.
     */
    @Override
    public void setId(final TEntity entity) {
        List<TEntity> list = getDbSet().getAll();
        entity.setId(list.get(list.size() - 1).getId() + 1);
    }
}
