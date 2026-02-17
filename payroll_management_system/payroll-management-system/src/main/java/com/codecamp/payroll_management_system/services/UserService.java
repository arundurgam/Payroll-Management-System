package com.codecamp.payroll_management_system.services;

import com.codecamp.payroll_management_system.model.User;
import java.util.List;

public interface UserService {

	User register(User user);

	List<User> registerBulk(List<User> users);

	User findByUsername(String username);

}
