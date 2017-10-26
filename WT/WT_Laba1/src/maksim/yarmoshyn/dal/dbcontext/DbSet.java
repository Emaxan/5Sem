package maksim.yarmoshyn.dal.dbcontext;

import maksim.yarmoshyn.dal.exception.EntityAlreadyExistException;
import maksim.yarmoshyn.dal.exception.EntityNotFoundException;
import maksim.yarmoshyn.dal.model.interf.Entity;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Database set of the entities.
 *
 * @param <TEntity> Class of the entity.
 */
public class DbSet<TEntity extends Entity> {

    /**
     * List of entities.
     */
    private List<TEntity> list = new ArrayList<>();

    /**
     * Get all records from database.
     *
     * @return List of entities.
     */
    public List<TEntity> getAll() {
        return list;
    }

    /**
     * Add entity to database.
     *
     * @param entity Entity to add.
     * @throws EntityAlreadyExistException when the entity
     *                                     you want to add is exist in database.
     */
    public void add(final TEntity entity) throws EntityAlreadyExistException {
        for (TEntity e : list) {
            if (e.checkPrimaryKey(entity.getPrimaryKey())) {
                throw new EntityAlreadyExistException(
                        "Entity with that primary key already exist."
                );
            }
        }
        list.add(entity);
    }

    /**
     * Delete entity from database.
     *
     * @param entity Entity to delete.
     */
    public void remove(final TEntity entity) {
        list.remove(entity);
    }

    /**
     * Update entity in database.
     *
     * @param entity Entity to update.
     * @throws EntityNotFoundException when Entity doesn't exist.
     */
    public void update(final TEntity entity) throws EntityNotFoundException {
        TEntity e = find(entity.getPrimaryKey());

        if (e == null) {
            throw new EntityNotFoundException(
                    "There are no entities with that primary key in database."
            );
        }
        e.fromString(entity.toString());
    }

    /**
     * Find entity with primary key.
     *
     * @param key Primary key to find entity.
     * @return Entity from database with primary key equal with params,
     * or {@code Null} if there is no such entities.
     */
    @Nullable
    public TEntity find(final Object... key) {
        TEntity entity = null;
        for (TEntity e : list) {
            if (e.checkPrimaryKey(key)) {
                entity = e;
                break;
            }
        }

        return entity;
    }

    /**
     * Filter for DbSet.
     *
     * @param predicate Condition to filter.
     * @return Filtered DbSet.
     */
    public DbSet<TEntity> where(final Predicate<TEntity> predicate) {
        DbSet<TEntity> set = new DbSet<>();
        for (TEntity entity : list) {
            if (predicate.test(entity)) {
                try {
                    set.add(entity);
                } catch (EntityAlreadyExistException e) {
                    // ignore
                }
            }
        }

        return set;
    }

    /**
     * Take sublist of DbSet.
     *
     * @param from  First index to return.
     * @param count Return count.
     * @return List of entities.
     */
    public List<TEntity> take(final int from, final int count) {
        if (from + count > list.size()) {
            return list.subList(from, list.size());
        }

        return list.subList(from, from + count);
    }
}
