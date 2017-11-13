////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2017.                                                         /
// Developed by Maksim Ermoshyn.                                               /
////////////////////////////////////////////////////////////////////////////////

package by.bsuir.MY.dal.dbcontext;

import by.bsuir.MY.dal.exception.EntityAlreadyExistException;
import by.bsuir.MY.dal.model.interf.Entity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * DBContext with file source.
 */
public final class FilesDBContext implements DBContext {

    /**
     * Path to the directory where placed database.
     */
    private String dirPath;

    /**
     * Create new DBContext.
     *
     * @param path Path to the directory with database.
     * @throws IOException if can't create directory.
     */
    public FilesDBContext(final String path) throws IOException {
        File dir = new File(path);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                throw new IOException("Can't create directory: " + path);
            }
        }
        this.dirPath = path;
    }

    /**
     * Return set of the entities.
     *
     * @param <TEntity> Class to extract.
     * @return {@link DbSet} of the objects.
     */
    @Override
    public <TEntity extends Entity> DbSet<TEntity> set(
            final Class<TEntity> entityClass
    ) {
        DbSet<TEntity> set = new DbSet<>();
        try {
            File db = new File(dirPath);
            String path = db.getPath()
                    .concat("\\" + entityClass.getName() + ".db");
            if (!new File(path).exists()) {
                if (!db.createNewFile()) {
                    return set;
                }
            }

            BufferedReader rd = new BufferedReader(new FileReader(path));
            String line;
            while ((line = rd.readLine()) != null) {
                TEntity entity = entityClass.getConstructor().newInstance();
                entity.fromString(line);
                try {
                    set.add(entity);
                } catch (EntityAlreadyExistException e) {
                    //TODO.
                }
            }
        } catch (IllegalAccessException
                | InstantiationException
                | IOException
                | NoSuchMethodException
                | InvocationTargetException e
                ) {
            System.out.println(e.getMessage());
        }
        return set;
    }

    /**
     * Save changes.
     *
     * @param entityClass Class of entities.
     * @param set         Set of entities.
     */
    @Override
    public <TEntity extends Entity> boolean saveChanges(
            final DbSet<TEntity> set,
            final Class<TEntity> entityClass
    ) {
        File db = new File(dirPath);
        String path = db.getPath().concat("\\" + entityClass.getName() + ".db");
        try {
            if (!new File(path).exists()) {
                if (!db.createNewFile()) {
                    return false;
                }
            }
            BufferedWriter wr = new BufferedWriter(new FileWriter(path));
            for (TEntity e : set.getAll()) {
                wr.write(e.toString());
                wr.newLine();
            }
            wr.close();
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
