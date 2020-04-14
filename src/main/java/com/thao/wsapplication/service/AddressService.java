package com.thao.wsapplication.service;

import java.util.List;

import com.thao.wsapplication.shared.dto.AddressDto;

public interface AddressService {
	List<AddressDto> getAddresses(String userId);
	AddressDto getAddress(String addressId);
}
