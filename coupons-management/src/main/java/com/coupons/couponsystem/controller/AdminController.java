package com.coupons.couponsystem.controller;

import com.coupons.couponsystem.clientLogIn.ClientType;
import com.coupons.couponsystem.dto.DTOUtills.DTOTransition;
import com.coupons.couponsystem.dto.request.CompanyDTO;
import com.coupons.couponsystem.dto.request.CustomerDTO;
import com.coupons.couponsystem.dto.response.CompanyDTOResponse;
import com.coupons.couponsystem.dto.response.CustomerDTOResponse;
import com.coupons.couponsystem.dto.response.ResponseMessage;
import com.coupons.couponsystem.exception.CouponSystemException;
import com.coupons.couponsystem.exception.customeResponse.CustomResponse;
import com.coupons.couponsystem.model.Company;
import com.coupons.couponsystem.model.Customer;
import com.coupons.couponsystem.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static com.coupons.couponsystem.exception.customeResponse.CustomResponse.response;

@CrossOrigin
@RestController
@RequestMapping("api/admin/")

public class AdminController extends ClientController {



    @PostMapping("add-company")
        public ResponseEntity<ResponseMessage> addCompany(@RequestBody CompanyDTO companyDTO){
        try {

            User user = User.builder()
                    .id(0)
                    .password(companyDTO.getPassword())
                    .username(companyDTO.getUsername())
                    .clientRole(ClientType.Company)
                    .build();

            Company company = Company.builder()
                    .id(0L)
                    .name(companyDTO.getName())
                    .coupons(companyDTO.getCoupons())
                    .build();

            return new ResponseEntity<>(dtoTransition.EntityToDTO(adminService.addCompany(user, company)
                    ,"Company was added successfully"), HttpStatus.OK);
        } catch (CouponSystemException e) {
            return CustomResponse.response(e.getHttpStatus(),e.getMessage());
        }
    }

    @PutMapping("update-company")
    public ResponseEntity<ResponseMessage> updateCompany(@RequestBody CompanyDTO companyDTO){
//        System.out.println("company.getEmail() "+company.getEmail());
        try {
            User user =new User(companyDTO.getId(),companyDTO.getUsername(),companyDTO.getPassword(),ClientType.Company);
           Company company= new Company(companyDTO.getCompanyId(),companyDTO.getName(),companyDTO.getCoupons());
            return new ResponseEntity<>(
                    dtoTransition.EntityToDTO(adminService.updateCompany(user,company),"Company was Updates")
                    ,HttpStatus.OK);
        } catch (CouponSystemException e) {

            return response(e.getHttpStatus(),e.getMessage());
        }
    }

    @DeleteMapping("delete-company/{companyId}")
    public ResponseEntity<ResponseMessage> deleteCompany(@PathVariable Long companyId){
        try {
            adminService.deleteCompany(companyId);
        } catch (CouponSystemException e) {
            return CustomResponse.response(e.getHttpStatus(),e.getMessage());
        }
        return CustomResponse.response(HttpStatus.OK,"company was deleted");


    }

    @GetMapping("/company/get-all/")
    public ResponseEntity<List<Company>> getAllCompanies(){
        return  new ResponseEntity<>(adminService.getAllCompanies(),HttpStatus.OK);
    }
    @GetMapping("/company/{companyId}")
    public ResponseEntity<ResponseMessage> getOneCompany(@PathVariable Long companyId){
        try {
            return  new ResponseEntity<>(dtoTransition.EntityToDTO(adminService.getOneCompany(companyId),"success"),HttpStatus.OK);
        } catch (CouponSystemException e) {
            return CustomResponse.response(e.getHttpStatus(),e.getMessage());

        }
    }

    @PostMapping("/add-customer/")
    public ResponseEntity<ResponseMessage> addCustomer(@RequestBody CustomerDTO customerDTO){
        try {
            User user =  new User(customerDTO.getId(), customerDTO.getUsername(), customerDTO.getPassword(),ClientType.Customer);
            Customer customer =new Customer(customerDTO.getCustomerId(),customerDTO.getFirstName(), customerDTO.getLastName(), customerDTO.getPurchases()) ;
            return  new ResponseEntity<>(dtoTransition.EntityToDTO(adminService.addCustomer(
                   user,customer),"customer was added successfully"),HttpStatus.OK);
        } catch (CouponSystemException e) {
            System.out.println(e.getMessage() + "  was the message");
            return CustomResponse.response(e.getHttpStatus(),e.getMessage());

        }
    }

    @PutMapping("/customer/update")
    public ResponseEntity<ResponseMessage> updateCustomer(@RequestBody CustomerDTO customerDTO){
        try {
            User user=    new User(customerDTO.getId(), customerDTO.getUsername(), customerDTO.getPassword(),ClientType.Customer);
           Customer customer =  new Customer(customerDTO.getCustomerId(),customerDTO.getFirstName(), customerDTO.getLastName(), customerDTO.getPurchases());

           return new ResponseEntity<>(dtoTransition.EntityToDTO(adminService.updateCustomer(user,customer),"success")
                    ,HttpStatus.OK);
        } catch (CouponSystemException e) {
            return CustomResponse.response(e.getHttpStatus(),e.getMessage());

        }
    }
    @DeleteMapping("/customer/{customerId}")
    public ResponseEntity<ResponseMessage> deleteCustomer(@PathVariable Long customerId){
        try {
            adminService.deleteCustomer(customerId);
        } catch (CouponSystemException e) {
            return CustomResponse.response(e.getHttpStatus(),e.getMessage());

        }
        return CustomResponse.response(HttpStatus.OK,"customer"+customerId+" was deleted");


    }

    @GetMapping("/customer/get-all")
    public ResponseEntity<List<Customer>> getAllCustomers(){
        return  new ResponseEntity<>(adminService.getAllCustomers(),HttpStatus.OK);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<ResponseMessage> getOneCustomer(@PathVariable Long customerId){
        try {
            return  new ResponseEntity<>(dtoTransition.EntityToDTO(adminService.getOneCustomer(customerId),"success"),HttpStatus.OK);
        } catch (CouponSystemException e) {

            return CustomResponse.response(e.getHttpStatus(),e.getMessage());
        }
    }

}
