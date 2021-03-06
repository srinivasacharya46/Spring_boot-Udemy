package com.appsdeveloperblog.app.ws.ui.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.appsdeveloperblog.app.ws.exception.UserServiceException;
import com.appsdeveloperblog.app.ws.service.AddressService;
import com.appsdeveloperblog.app.ws.service.UserService;
import com.appsdeveloperblog.app.ws.shared.dto.AddressDto;
import com.appsdeveloperblog.app.ws.shared.dto.UserDto;
import com.appsdeveloperblog.app.ws.ui.model.requestUserDetailsRequestModel.UserDetailsRequestModel;
import com.appsdeveloperblog.app.ws.ui.model.response.AddressesRest;
import com.appsdeveloperblog.app.ws.ui.model.response.ErrorMessages;
import com.appsdeveloperblog.app.ws.ui.model.response.UserRest;

@RestController // enables the class to recieve http requests
@RequestMapping("users") // http://localhost:8080/users/

public class UserController {
	@Autowired
	private UserService userService;

	@Autowired
	AddressService as;

	@GetMapping(path = "/{id}") // binding this method to recieve get method
	public UserRest getUser(@PathVariable String id) {
		UserRest returnValue = new UserRest();
		UserDto userDto = userService.findByUserId(id);
		BeanUtils.copyProperties(userDto, returnValue);

		return returnValue;
	}

	@PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception {
		if (userDetails.getFirstName().isEmpty())
			throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
		UserRest returnValue = new UserRest();
		// UserDto userDto = new UserDto();
		// BeanUtils.copyProperties(userDetails, userDto);

		ModelMapper modelMapper = new ModelMapper();
		UserDto userDto = modelMapper.map(userDetails, UserDto.class);

		UserDto createdUser = userService.createUser(userDto);
		// BeanUtils.copyProperties(createdUser, returnValue);
		returnValue = modelMapper.map(createdUser, UserRest.class);
		return returnValue;
	}

	@PutMapping(path = "/{id}") // binding this method to recieve post method
	public UserRest updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails) {
		UserRest returnValue = new UserRest();

		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userDetails, userDto);

		UserDto updatedUser = userService.updateUser(id, userDto);
		BeanUtils.copyProperties(updatedUser, returnValue);
		return returnValue;
	}

	@DeleteMapping(path = "/{id}") // binding this method to delete post method
	public String deleteUser(@PathVariable String id) {
		userService.deleteUserById(id);
		return "deleted user with userId:  " + id;
	}

	@GetMapping
	public List<UserRest> getAllUsers(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "25") int limit) {
		List<UserRest> returnValue = new ArrayList<>();
		List<UserDto> users = userService.getAllUsers(page, limit);

		for (UserDto userDto : users) {
			UserRest userModel = new UserRest();
			BeanUtils.copyProperties(userDto, userModel);
			returnValue.add(userModel);
		}

		return returnValue;
	}

	@GetMapping(path = "/{id}/address") // binding this method to recieve get method
	public List<AddressesRest> getAddress(@PathVariable String userId) {
		List<AddressesRest> returnValue = new ArrayList<>();
		// AddressDto addressDto = as.findAllByUserDetails(userId);
		return returnValue;
	}

	@GetMapping(path = "/{id}/getBy")
	public UserRest getUserDetailsByFirstName(@PathVariable String id) {
		UserRest returnValue = new UserRest();
		UserDto userDto = userService.findAllUsersByFirstName(id);

		BeanUtils.copyProperties(userDto, returnValue);

		return returnValue;
	}

	@GetMapping(path = "/{first_name}/{last_name}")
	public UserRest getUserDetailsByFirstNameAndLastName(@PathVariable String first_name,
			@PathVariable String last_name) {
		UserRest returnValue = new UserRest();
		UserDto userDto = userService.findUserDetailsByFirstAndLastName(first_name, last_name);
		BeanUtils.copyProperties(userDto, returnValue);
		return returnValue;
	}

	@GetMapping(path = "/{id}/getAddress")
	public AddressesRest findAddressByAddressId(@PathVariable String id) {
		AddressesRest a = new AddressesRest();
		AddressDto addressDto = as.findAddressByAddressId(id);
		BeanUtils.copyProperties(addressDto, a);
		// adding link to response
		Link userLink = WebMvcLinkBuilder.linkTo(UserController.class).slash(id).withRel("User");
		a.add(userLink);
		// or make use of EnitityModel----no need to extend representationModel
		// class(returning)
		// EntityModel.of(returnValue,Array.asList(links));
		return a;

	}

}
