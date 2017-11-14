package by.bsuir.MY.domain;

import by.bsuir.MY.dal.model.interf.Entity;
import by.bsuir.MY.domain.exception.WrongDataException;

/**
 * TODO.
 */
public class File implements Entity {
    /**
     * TODO.
     */
    private Integer id;

    /**
     * TODO.
     *
     * @return TODO.
     */
    public Integer getId() {
        return id;
    }

    /**
     * TODO.
     *
     * @param id TODO.
     */
    public void setId(final Integer id) {
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
     * @return TODO.
     */
    public String getPhrase() {
        return phrase;
    }

    /**
     * TODO.
     *
     * @param phrase TODO.
     */
    public void setPhrase(final String phrase) {
        this.phrase = phrase;
    }

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

    /**
     * TODO.
     *
     * @return TODO.
     */
    @Override
    public String toString() {
        return "<File><Id>"
                + getId().toString()
                + "</Id><FirstName>"
                + getFirstName()
                + "</FirstName><LastName>"
                + getLastName()
                + "</LastName><SurnameName>"
                + getSurnameName()
                + "</SurnameName><Phrase>"
                + getPhrase()
                + "</Phrase></File>";
    }

    /**
     * Initialize entity from string representation.
     *
     * @param record String to parse.
     */
    @Override
    public void fromString(final String record) throws WrongDataException {
        if (!record.startsWith("<File>")) {
            throw new WrongDataException("Wrong xml data.");
        }
        String str = record.substring("<File>".length(), record.length() - "<File>".length() - "</File>".length());

        if (!str.startsWith("<Id>")) {
            throw new WrongDataException("Wrong xml data.");
        }
        str = str.substring("<Id>".length());
        int length = str.indexOf("</Id>");
        int ids;
        try{
            ids = Integer.parseInt(str.substring(0, length));
        } catch (NumberFormatException e) {
            throw new WrongDataException("Wrong xml data.");
        }
        str = str.substring(length).substring("</Id>".length());

        if (!str.startsWith("<FirstName>")) {
            throw new WrongDataException("Wrong xml data.");
        }
        str = str.substring("<FirstName>".length());
        length = str.indexOf("</FirstName>");
        String firstNames;
        firstNames = str.substring(0, length);
        str = str.substring(length).substring("</FirstName>".length());

        if (!str.startsWith("<LastName>")) {
            throw new WrongDataException("Wrong xml data.");
        }
        str = str.substring("<LastName>".length());
        length = str.indexOf("</LastName>");
        String lastNames;
        lastNames = str.substring(0, length);
        str = str.substring(length).substring("</LastName>".length());

        if (!str.startsWith("<SurnameName>")) {
            throw new WrongDataException("Wrong xml data.");
        }
        str = str.substring("<SurnameName>".length());
        length = str.indexOf("</SurnameName>");
        String surnameNames;
        surnameNames = str.substring(0, length);
        str = str.substring(length).substring("</SurnameName>".length());

        if (!str.startsWith("<Phrase>")) {
            throw new WrongDataException("Wrong xml data.");
        }
        str = str.substring("<Phrase>".length());
        length = str.indexOf("</Phrase>");
        String phrases;
        phrases = str.substring(0, length);
        str = str.substring(length).substring("</Phrase>".length());

        setId(ids);
        setFirstName(firstNames);
        setLastName(lastNames);
        setSurnameName(surnameNames);
        setPhrase(phrases);

    }

    /**
     * Check primary key.
     *
     * @param key Primary key.
     * @return {@code True} on success, {@code False} otherwise.
     */
    @Override
    public boolean checkPrimaryKey(final Object... key) {
        return key.length == 1
                && key[0] instanceof Integer
                && key[0] == id;
    }

    /**
     * Get primary key of this entity.
     *
     * @return Primary key.
     */
    @Override
    public Object[] getPrimaryKey() {
        return new Object[]{id};
    }
}
