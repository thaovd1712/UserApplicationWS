package com.thao.wsapplication.ui.controller;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.thao.wsapplication.exception.UserServiceException;
import com.thao.wsapplication.service.AddressService;
import com.thao.wsapplication.service.UserService;
import com.thao.wsapplication.shared.dto.AddressDto;
import com.thao.wsapplication.shared.dto.UserDto;
import com.thao.wsapplication.ui.model.request.UserDetailsRequestModel;
import com.thao.wsapplication.ui.model.response.AddressResponseModel;
import com.thao.wsapplication.ui.model.response.ErrorMessages;
import com.thao.wsapplication.ui.model.response.OperationStatusModel;
import com.thao.wsapplication.ui.model.response.RequestOperationStatus;
import com.thao.wsapplication.ui.model.response.UserDetailsResponseModel;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
	AddressService addressService;

	@GetMapping
	public List<UserDetailsResponseModel> getUser(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "25") int limit) {
		List<UserDetailsResponseModel> returnValue = new ArrayList<>();

		List<UserDto> usersDto = userService.getUsers(page, limit);

//		for (UserDto userDto : usersDto) {
//			UserDetailsResponseModel userModel = new UserDetailsResponseModel();
//			BeanUtils.copyProperties(userDto, userModel);
//			returnValue.add(userModel);
//		}
		if (usersDto != null && !usersDto.isEmpty()) {
			Type listType = new TypeToken<List<UserDetailsResponseModel>>() {
			}.getType();
			
			returnValue = new ModelMapper().map(usersDto, listType);
		}
		
		return returnValue;
	}

	@GetMapping("/{userId}")
	public UserDetailsResponseModel getUser(@PathVariable String userId) {
		ModelMapper modelMapper = new ModelMapper();
		UserDto userDto = userService.getUserByUserId(userId);
		UserDetailsResponseModel returnValue = modelMapper.map(userDto, UserDetailsResponseModel.class);
//		BeanUtils.copyProperties(userDto, returnValue);
		return returnValue;
	}

	@PostMapping
	public UserDetailsResponseModel createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception {
		ModelMapper modelMapper = new ModelMapper();

		if (userDetails.getFirstName().isEmpty())
			throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
//		UserDto userDto = new UserDto();
//		BeanUtils.copyProperties(userDetails, userDto);
		UserDto userDto = modelMapper.map(userDetails, UserDto.class);

		UserDto createdUser = userService.createUser(userDto);

		UserDetailsResponseModel returnValue = modelMapper.map(createdUser, UserDetailsResponseModel.class);

//		BeanUtils.copyProperties(createdUser, returnValue);

		return returnValue;
	}

	@DeleteMapping("/{userId}")
	public OperationStatusModel deleteUser(@PathVariable String userId) {
		OperationStatusModel returnValue = new OperationStatusModel();
		returnValue.setOperationName(RequestOperationName.DELETE.name());

		userService.deleteUser(userId);

		returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
		return returnValue;
	}

	@PutMapping("/{userId}")
	public UserDetailsResponseModel updateUser(@PathVariable String userId,
			@RequestBody UserDetailsRequestModel userDetails) {
		ModelMapper modelMapper = new ModelMapper();

//		BeanUtils.copyProperties(userDetails, userDto);
		UserDto userDto = modelMapper.map(userDetails, UserDto.class);

		UserDto createdUser = userService.updateUser(userId, userDto);

		UserDetailsResponseModel returnValue = modelMapper.map(createdUser, UserDetailsResponseModel.class);
//		BeanUtils.copyProperties(createdUser, returnValue);

		return returnValue;
	}

	@GetMapping("/{userId}/addresses")
	public List<AddressResponseModel> getUserAddresses(@PathVariable String userId) {
//		ModelMapper modelMapper = new ModelMapper();
		List<AddressResponseModel> returnValue = new ArrayList<>();

		List<AddressDto> addressesDto = addressService.getAddresses(userId);

		if (addressesDto != null && !addressesDto.isEmpty()) {
			Type listType = new TypeToken<List<AddressResponseModel>>() {
			}.getType();
			
			returnValue = new ModelMapper().map(addressesDto, listType);
		}
		return returnValue;
	}
	
	@GetMapping("/{userId}/addresses/{addressId}")
	public AddressResponseModel getUserAddress(@PathVariable String addressId, @PathVariable String userId) {
		ModelMapper modelMapper = new ModelMapper();
		AddressDto addressDto = addressService.getAddress(addressId);
		
		Link addressLink = Link.of("https://myhost/people/42" + userId + "/addresses" + addressId);
		AddressResponseModel returnValue = modelMapper.map(addressDto, AddressResponseModel.class);
		returnValue.add(addressLink);
//		BeanUtils.copyProperties(userDto, returnValue);
		return returnValue;
	}
	
}