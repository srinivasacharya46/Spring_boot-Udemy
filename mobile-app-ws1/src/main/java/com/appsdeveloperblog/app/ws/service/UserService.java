package com.appsdeveloperblog.app.ws.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.appsdeveloperblog.app.ws.shared.dto.UserDto;

public interface UserService extends UserDetailsService {
	UserDto createUser(UserDto user);

	UserDto findByUserId(String id);

	UserDto getUser(String email);

	UserDto updateUser(String id, UserDto user);

	void deleteUserById(String id);
}
