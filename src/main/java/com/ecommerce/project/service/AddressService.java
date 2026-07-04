package com.ecommerce.project.service;

import com.ecommerce.project.Model.Address;
import com.ecommerce.project.Model.User;
import com.ecommerce.project.payload.AddressDTO;

import java.util.List;

public interface AddressService {
    AddressDTO createNewAddress(AddressDTO addressDTO);

    List<AddressDTO> findByUserId(User user);

    List<AddressDTO> findAllAddress();

    AddressDTO getAddressById(Long addressId);

    AddressDTO updateAddressById(Long addressId, AddressDTO addressDTO);

    AddressDTO deletedAddressById(Long addressId);
}
