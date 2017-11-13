package by.bsuir.MY.domain;

/**
 * TODO.
 */
public class File {
    /**
     * TODO.
     */
    private int id;

    /**
     * TODO.
     *
     * @return TODO.
     */
    public int getId() {
        return id;
    }

    /**
     * TODO.
     *
     * @param id TODO.
     */
    public void setId(final int id) {
        this.id = id;
    }

    /**
     * TODO.
     */
    private String firstName;

    /**
     * TODO.
     *
     * @return TODO.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * TODO.
     *
     * @param firstName TODO.
     */
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    /**
     * TODO.
     */
    private String lastName;

    /**
     * TODO.
     *
     * @return TODO.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * TODO.
     *
     * @param lastName TODO.
     */
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    /**
     * TODO.
     */
    private String surnameName;

    /**
     * TODO.
     *
     * @return TODO.
     */
    public String getSurnameName() {
        return surnameName;
    }

    /**
     * TODO.
     *
     * @param surnameName TODO.
     */
    public void setSurnameName(final String surnameName) {
        this.surnameName = surnameName;
    }

    /**
     * TODO.
     */
    private String phrase;

    /**
     * TODO.
     *
     * @param o TODO.
     * @return TODO.
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        File file = (File) o;

        if (getId() != file.getId()) return false;
        if (getFirstName() != null ? !getFirstName().equals(file.getFirstName()) : file.getFirstName() != null)
            return false;
        if (getLastName() != null ? !getLastName().equals(file.getLastName()) : file.getLastName() != null)
            return false;
        if (getSurnameName() != null ? !getSurnameName().equals(file.getSurnameName()) : file.getSurnameName() != null)
            return false;
        return phrase != null ? phrase.equals(file.phrase) : file.phrase == null;
    }

    /**
     * TODO.
     *
     * @return TODO.
     */
    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getFirstName() != null ? getFirstName().hashCode() : 0);
        result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
        result = 31 * result + (getSurnameName() != null ? getSurnameName().hashCode() : 0);
        result = 31 * result + (phrase != null ? phrase.hashCode() : 0);
        return result;
    }
}
