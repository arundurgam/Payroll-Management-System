package com.codecamp.payroll_management_system.servicesImpl;

import com.codecamp.payroll_management_system.model.User;
import com.codecamp.payroll_management_system.repository.UserRepository;
import com.codecamp.payroll_management_system.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository repository;

	public UserServiceImpl(UserRepository repository) {
		this.repository = repository;
	}

	@Override
	public User register(User user) {
		return repository.save(user);
	}

	@Override
	public List<User> registerBulk(List<User> users) {
		return repository.saveAll(users);
	}
	@Override
	public User findByUsername(String username) {
	    return repository.findByUsername(username).orElse(null);
	}

}
