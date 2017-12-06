package edu.iba.sh.dao;

import java.util.List;

import edu.iba.sh.bean.User;


public interface UserDAO {

	List<User> getAllUsers() throws DAOException;
	User getUserId(String id) throws DAOException;
	void updateUser(String id,User user)throws DAOException;
	void deleteUserById(String id)throws DAOException;
	void addUser(User user)throws DAOException;
	User getUserByIdAndPassword(String id, String password)throws DAOException;

}
