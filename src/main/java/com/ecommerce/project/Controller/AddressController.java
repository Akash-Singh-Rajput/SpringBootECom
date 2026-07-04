package com.ecommerce.project.Controller;

import com.ecommerce.project.Model.User;
import com.ecommerce.project.Util.AuthUtil;
import com.ecommerce.project.payload.AddressDTO;
import com.ecommerce.project.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private AuthUtil authUtil;

    @PostMapping("/addresses")
    public ResponseEntity<AddressDTO> createAddress(@RequestBody AddressDTO addressDTO){
        AddressDTO addressCreated = addressService.createNewAddress(addressDTO);

        return new ResponseEntity<>(addressCreated, HttpStatus.CREATED);
    }

    @GetMapping("/user/addresses")
    public ResponseEntity<List<AddressDTO>> getUserAddress(){
        User user = authUtil.getLoggedInUser();

        List<AddressDTO> list = addressService.findByUserId(user);

        return new ResponseEntity<>(list , HttpStatus.CREATED);
    }

    @GetMapping("/addresses")
    public ResponseEntity<List<AddressDTO>> getAllAddress(){

        List<AddressDTO> list = addressService.findAllAddress();

        return new ResponseEntity<>(list , HttpStatus.OK);
    }

    @GetMapping("/address/{addressId}")
    public ResponseEntity<AddressDTO> getAddressById(@PathVariable Long addressId){
        AddressDTO addressDTO = addressService.getAddressById(addressId);
        return new ResponseEntity<>(addressDTO , HttpStatus.OK);
    }

    @PutMapping("/address/{addressId}")
    public ResponseEntity<AddressDTO> updateAddressById(@PathVariable Long addressId, @RequestBody AddressDTO addressDTO){
        AddressDTO savedAddress = addressService.updateAddressById(addressId, addressDTO);
        return new ResponseEntity<>(savedAddress , HttpStatus.OK);
    }

    @DeleteMapping("/address/{addressId}")
    public ResponseEntity<AddressDTO> deleteAddressById(@PathVariable Long addressId){
        AddressDTO addressDTO = addressService.deletedAddressById(addressId);

        return new ResponseEntity<>(addressDTO , HttpStatus.OK);
    }
}
