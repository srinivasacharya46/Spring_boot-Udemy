package com.appsdeveloperblog.app.ws.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.appsdeveloperblog.app.ws.shared.dto.UserDto;

public interface UserService extends UserDetailsService {
	UserDto createUser(UserDto user);

	UserDto findByUserId(String id);

	UserDto getUser(String email);

	UserDto updateUser(String id, UserDto user);

	void deleteUserById(String id);

	List<UserDto> getAllUsers(int page, int limit);
}
