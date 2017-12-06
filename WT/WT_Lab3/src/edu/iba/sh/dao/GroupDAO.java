package edu.iba.sh.dao;

import java.util.List;

import edu.iba.sh.bean.Group;
import edu.iba.sh.dao.DAOException;

public interface GroupDAO {

	List<Group> getAllGroups() throws DAOException;

	Group getGroupById(String id) throws DAOException;

	void updateGroup(String id, Group group) throws DAOException;

	void deleteGroupById(String id) throws DAOException;

	void addGroup(Group group) throws DAOException;


}
