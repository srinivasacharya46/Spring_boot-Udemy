package com.appsdeveloperblog.app.ws.service;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.appsdeveloperblog.app.ws.shared.dto.AddressDto;

@Service
public interface AddressService {
	List<AddressDto> findAllByUserDetails(String userId);

	AddressDto findAddressByAddressId(@Param("id") String id);
}
