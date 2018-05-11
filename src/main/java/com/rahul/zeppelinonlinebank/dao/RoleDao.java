package com.rahul.zeppelinonlinebank.dao;

import com.rahul.zeppelinonlinebank.pojo.Role;

public interface RoleDao {
	
	public Role findRoleByName(String theRoleName);

}
