package com.ecommerce.project.service;

import com.ecommerce.project.Exception.APIException;
import com.ecommerce.project.Exception.ResourceNotFoundException;
import com.ecommerce.project.Model.Address;
import com.ecommerce.project.Model.User;
import com.ecommerce.project.Util.AuthUtil;
import com.ecommerce.project.payload.AddressDTO;
import com.ecommerce.project.repository.AddressRepository;
import com.ecommerce.project.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService{

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthUtil authUtil;

    @Autowired
    ModelMapper modelMapper;


    @Override
    public AddressDTO createNewAddress(AddressDTO addressDTO) {
        User user = authUtil.getLoggedInUser();

        Address address = modelMapper.map(addressDTO, Address.class);
        address.setUser(user);

        Address savedAddress = addressRepository.save(address);

        return modelMapper.map(savedAddress, AddressDTO.class);

    }

    @Override
    public List<AddressDTO> findByUserId(User user) {
        List<Address> addressList = user.getAddresses();
        if(addressList == null){
            throw new APIException("User does not have any listed address yet!!");
        }

        return addressList.stream().map(address -> modelMapper.map(address , AddressDTO.class)).toList();
    }

    @Override
    public List<AddressDTO> findAllAddress() {
        List<Address> list = addressRepository.findAll();

        return list.stream().map(address ->
                modelMapper.map(address, AddressDTO.class)).toList();
    }

    @Override
    public AddressDTO getAddressById(Long addressId) {
        Address address = addressRepository.findById(addressId).
                orElseThrow(() -> new ResourceNotFoundException("Address", "addressId: ", addressId));

        return modelMapper.map(address , AddressDTO.class);
    }

    @Override
    public AddressDTO updateAddressById(Long addressId, AddressDTO addressDTO) {

        Address addressFromDatabase = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address" , "addressId" , addressId));

        addressFromDatabase.setCity(addressDTO.getCity());
        addressFromDatabase.setPincode(addressDTO.getPincode());
        addressFromDatabase.setState(addressDTO.getState());
        addressFromDatabase.setCountry(addressDTO.getCountry());
        addressFromDatabase.setStreet(addressDTO.getStreet());
        addressFromDatabase.setBuildingName(addressDTO.getBuildingName());

        Address savedAddress = addressRepository.save(addressFromDatabase);

        return modelMapper.map(savedAddress, AddressDTO.class);

    }

    @Override
    public AddressDTO deletedAddressById(Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address" , "addressId" , addressId));

        addressRepository.delete(address);
        return modelMapper.map(address, AddressDTO.class);
    }
}
