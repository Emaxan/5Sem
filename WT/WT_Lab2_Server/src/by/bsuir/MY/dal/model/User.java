////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2017.                                                         /
// Developed by Maksim Ermoshyn.                                               /
////////////////////////////////////////////////////////////////////////////////

package by.bsuir.MY.dal.model;

import by.bsuir.MY.dal.model.interf.Entity;

import java.util.HashMap;

/**
 * User class. Contain user data and credentials.
 */
public class User
        extends BaseEntity
        implements Entity {
    /**
     * Unique identificator of the user.
     */
    private Integer id;
    /**
     * Name of the user.
     */
    private String name;
    /**
     * User email.
     */
    private String email;
    /**
     * User password.
     */
    private String password;
    /**
     * User role.
     */
    private Role role;

    /**
     * Get id of this user.
     *
     * @return Id of this user.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Set id of this user.
     *
     * @param i Id of this user.
     */
    public void setId(final Integer i) {
        this.id = i;
    }

    /**
     * Get name of this user.
     *
     * @return Name of this user.
     */
    public String getName() {
        return name;
    }

    /**
     * Set name of this user.
     *
     * @param n Name of this user.
     */
    public void setName(final String n) {
        this.name = n;
    }

    /**
     * Get email of this user.
     *
     * @return Email of this user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set email of this user.
     *
     * @param em Email of this user
     */
    public void setEmail(final String em) {
        this.email = em;
    }

    /**
     * Get hash of the password of this user.
     *
     * @return Hash of the password of this user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set hash of the password of this user.
     *
     * @param passwd Hash of the password of this user.
     */
    public void setPassword(final String passwd) {
        this.password = passwd;
    }

    /**
     * Get role of this user.
     *
     * @return Role of this user.
     */
    public Role getRole() {
        return role;
    }

    /**
     * Set role of this user.
     *
     * @param r Role of this user.
     */
    public void setRole(final Role r) {
        this.role = r;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * <p>
     * The {@code equals} method implements an equivalence relation
     * on non-null object references:
     * <ul>
     * <li>It is <i>reflexive</i>: for any non-null reference value
     * {@code x}, {@code x.equals(x)} should return
     * {@code true}.
     * <li>It is <i>symmetric</i>: for any non-null reference values
     * {@code x} and {@code y}, {@code x.equals(y)}
     * should return {@code true} if and only if
     * {@code y.equals(x)} returns {@code true}.
     * <li>It is <i>transitive</i>: for any non-null reference values
     * {@code x}, {@code y}, and {@code z}, if
     * {@code x.equals(y)} returns {@code true} and
     * {@code y.equals(z)} returns {@code true}, then
     * {@code x.equals(z)} should return {@code true}.
     * <li>It is <i>consistent</i>: for any non-null reference values
     * {@code x} and {@code y}, multiple invocations of
     * {@code x.equals(y)} consistently return {@code true}
     * or consistently return {@code false}, provided no
     * information used in {@code equals} comparisons on the
     * objects is modified.
     * <li>For any non-null reference value {@code x},
     * {@code x.equals(null)} should return {@code false}.
     * </ul>
     * <p>
     * The {@code equals} method for class {@code Object} implements
     * the most discriminating possible equivalence relation on objects;
     * that is, for any non-null reference values {@code x} and
     * {@code y}, this method returns {@code true} if and only
     * if {@code x} and {@code y} refer to the same object
     * ({@code x == y} has the value {@code true}).
     * <p>
     * Note that it is generally necessary to override the {@code hashCode}
     * method whenever this method is overridden, so as to maintain the
     * general contract for the {@code hashCode} method, which states
     * that equal objects must have equal hash codes.
     *
     * @param o the reference object with which to compare.
     * @return {@code true} if this object is the same as the obj
     * argument; {@code false} otherwise.
     * @see #hashCode()
     * @see HashMap
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;

        return id.equals(user.id)
                && name.equals(user.name)
                && email.equals(user.email)
                && password.equals(user.password)
                && role == user.role;
    }

    /**
     * Returns a hash code value for the object. This method is
     * supported for the benefit of hash tables such as those provided by
     * {@link HashMap}.
     * <p>
     * The general contract of {@code hashCode} is:
     * <ul>
     * <li>Whenever it is invoked on the same object more than once during
     * an execution of a Java application, the {@code hashCode} method
     * must consistently return the same integer, provided no information
     * used in {@code equals} comparisons on the object is modified.
     * This integer need not remain consistent from one execution of an
     * application to another execution of the same application.
     * <li>If two objects are equal according to the {@code equals(Object)}
     * method, then calling the {@code hashCode} method on each of
     * the two objects must produce the same integer result.
     * <li>It is <em>not</em> required that if two objects are unequal
     * according to the {@link Object#equals(Object)}
     * method, then calling the {@code hashCode} method on each of the
     * two objects must produce distinct integer results.  However, the
     * programmer should be aware that producing distinct integer results
     * for unequal objects may improve the performance of hash tables.
     * </ul>
     * <p>
     * As much as is reasonably practical, the hashCode method defined
     * by class {@code Object} does return distinct integers for
     * distinct objects. (The hashCode may or may not be implemented
     * as some function of an object's memory address at some point
     * in time.)
     *
     * @return a hash code value for this object.
     * @see Object#equals(Object)
     * @see System#identityHashCode
     */
    @Override
    public int hashCode() {
        return id;
    }

    /**
     * Initialize user from string representation.
     *
     * @param record String to parse.
     */
    @Override
    public void fromString(final String record) {
        String[] fields = record.split(getSpecialSymbol());

        this.setId(Integer.parseInt(translateSpecialSymbols(fields[0])));
        this.setRole(Role.valueOf(translateSpecialSymbols(fields[1])));
        this.setName(translateSpecialSymbols(fields[2]));
        this.setEmail(translateSpecialSymbols(fields[3]));
        this.setPassword(translateSpecialSymbols(fields[4]));
    }

    /**
     * Returns a string representation of the object. In general, the
     * {@code toString} method returns a string that
     * "textually represents" this object. The result should
     * be a concise but informative representation that is easy for a
     * person to read.
     * It is recommended that all subclasses override this method.
     * <p>
     * The {@code toString} method for class {@code Object}
     * returns a string consisting of the name of the class of which the
     * object is an instance, the at-sign character `{@code @}', and
     * the unsigned hexadecimal representation of the hash code of the
     * object. In other words, this method returns a string equal to the
     * value of:
     * <blockquote>
     * <pre>
     * getClass().getName() + '@' + Integer.toHexString(hashCode())
     * </pre></blockquote>
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return translateSpecialSymbols(String.valueOf(this.getId()))
                + getSpecialSymbol() + translateSpecialSymbols(
                this.getRole().toString()
        )
                + getSpecialSymbol() + translateSpecialSymbols(this.getName())
                + getSpecialSymbol() + translateSpecialSymbols(this.getEmail())
                + getSpecialSymbol()
                + translateSpecialSymbols(this.getPassword());
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
