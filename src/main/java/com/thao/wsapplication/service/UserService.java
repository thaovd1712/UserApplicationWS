package com.thao.wsapplication.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.thao.wsapplication.shared.dto.UserDto;

public interface UserService extends UserDetailsService {
	UserDto createUser(UserDto user);

	UserDto getUser(String email);

	UserDto getUserByUserId(String userId);

	UserDto updateUser(String userId, UserDto user);

	List<UserDto> getUsers(int page, int limit);

	void deleteUser(String userId);
}
