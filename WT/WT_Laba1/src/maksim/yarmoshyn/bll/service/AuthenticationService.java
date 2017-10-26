////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2017.                                                         /
// Developed by Maksim Ermoshyn.                                               /
////////////////////////////////////////////////////////////////////////////////

package maksim.yarmoshyn.bll.service;

import maksim.yarmoshyn.bll.dto.UserDto;
import maksim.yarmoshyn.dal.dbcontext.DBContext;
import maksim.yarmoshyn.dal.model.User;

import maksim.yarmoshyn.dal.unit_of_work.WebTechLabUnitOfWork;
import maksim.yarmoshyn.dal.unit_of_work.WebTechLabUoW;
import org.jetbrains.annotations.Nullable;
import org.modelmapper.ModelMapper;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Service for authenticate and authorize users.
 */
public class AuthenticationService implements AuthService {

    /**
     * Unit of work object.
     */
    private WebTechLabUoW uow;
    /**
     * Mapper to map models.
     */
    private ModelMapper mapper;

    /**
     * Create new instance of {@link AuthService}.
     *
     * @param context Database context.
     */
    public AuthenticationService(final DBContext context) {
        this.uow = new WebTechLabUnitOfWork(context);
        this.mapper = new ModelMapper();
    }

    /**
     * Authenticate and authorize user.
     *
     * @param user User's credentials.
     * @return User if login and password is correct, null otherwise.
     */
    public UserDto signIn(final UserDto user) {
        User u = uow.getUserRepository()
                .getByEmail(user.getEmail().toLowerCase());
        if (u == null) {
            return null;
        }

        String password = md5(user.getPassword());
        if (password != null && password.equals(u.getPassword())) {
            return mapper.map(u, UserDto.class);
        }

        return null;
    }

    /**
     * Register user.
     *
     * @param user User's data.
     * @return True, if successfully registered, false otherwise.
     */
    public boolean signUp(final UserDto user) {
        User us = mapper.map(user, User.class);
        us.setEmail(us.getEmail().toLowerCase());
        String passwd = md5(us.getPassword());
        if (passwd == null) {
            return false;
        }

        us.setPassword(passwd);
        uow.getUserRepository().setId(us);
        uow.getUserRepository().create(us);
        uow.saveChanges();

        return true;
    }

    /**
     * Get MD5 hash from string.
     *
     * @param str String to get hash.
     * @return MD5 hash.
     */
    @Nullable
    private String md5(final String str) {
        MessageDigest messageDigest;
        byte[] digest;

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            System.err.println(e.getMessage());

            return null;
        }

        BigInteger bigInt = new BigInteger(1, digest);
        StringBuilder md5Hex = new StringBuilder(bigInt.toString(16));

        while (md5Hex.length() < 32) {
            md5Hex.insert(0, "0");
        }

        return md5Hex.toString();
    }
}
