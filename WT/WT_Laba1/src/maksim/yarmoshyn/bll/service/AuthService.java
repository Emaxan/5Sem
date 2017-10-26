////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2017.                                                         /
// Developed by Maksim Ermoshyn.                                               /
////////////////////////////////////////////////////////////////////////////////

package maksim.yarmoshyn.bll.service;

import maksim.yarmoshyn.bll.dto.UserDto;

/**
 * Service for authenticate and authorize users.
 */
public interface AuthService {

    /**
     * Authenticate and authorize user.
     *
     * @param user User's credentials.
     * @return User if login and password is correct, null otherwise.
     */
    UserDto signIn(UserDto user);

    /**
     * Register user.
     *
     * @param user User's data.
     * @return True, if successfully registered, false otherwise.
     */
    boolean signUp(UserDto user);

}
