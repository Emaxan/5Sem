////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2017.                                                         /
// Developed by Maksim Ermoshyn.                                               /
////////////////////////////////////////////////////////////////////////////////

package maksim.yarmoshyn.bll.dto;

import maksim.yarmoshyn.dal.model.Role;

/**
 * User data transfer object.
 */
public class UserDto {
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
}
