package com.coupons.couponsystem.controller;

import com.coupons.couponsystem.clientLogIn.ClientType;
import com.coupons.couponsystem.dto.CompanyDTO;
import com.coupons.couponsystem.dto.CustomerDTO;
import com.coupons.couponsystem.exception.CouponSystemException;
import com.coupons.couponsystem.model.Company;
import com.coupons.couponsystem.model.Customer;
import com.coupons.couponsystem.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("api/admin/")

public class AdminController extends ClientController {



    @PostMapping("add-company")
        public ResponseEntity<?> addCompany(@RequestBody CompanyDTO companyDTO){
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
                    .isActive(true)
                    .build();


            return new ResponseEntity<>(adminService.addCompany(user, company), HttpStatus.OK);
        } catch (CouponSystemException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }
    }

    @PutMapping("update-company")
    public ResponseEntity<?> updateCompany(@RequestBody CompanyDTO company){
//        System.out.println("company.getEmail() "+company.getEmail());
        try {
            return new ResponseEntity<>(adminService.updateCompany(
                    new User(company.getId(),company.getUsername(),company.getPassword(),ClientType.Company),
                    new Company(company.getCompanyId(),company.getName(),company.getCoupons(),company.isActive())),HttpStatus.OK);
        } catch (CouponSystemException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());

        }
    }

    @DeleteMapping("delete-company/{companyId}")
    public ResponseEntity<Company> deleteCompany(@PathVariable Long companyId){
        try {
            System.out.println(companyId);
            return new ResponseEntity<>(adminService.deleteCompany(companyId),HttpStatus.OK);

        } catch (CouponSystemException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }




    @GetMapping("/all-companies/")
    public ResponseEntity<List<Company>> getAllCompanies(){
        return  new ResponseEntity<>(adminService.getAllCompanies(),HttpStatus.OK);
    }


    @GetMapping("/all-companies/{companyId}")
    public ResponseEntity<?> getOneCompany(@PathVariable Long companyId){
        try {
            return  new ResponseEntity<>(adminService.getOneCompany(companyId),HttpStatus.OK);
        } catch (CouponSystemException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());

        }
    }

    @PostMapping("/add-customer/")
    public ResponseEntity<?> addCustomer(@RequestBody CustomerDTO customerDTO){
        try {


            User user =  new User(customerDTO.getId(), customerDTO.getUsername(), customerDTO.getPassword(),ClientType.Customer);
            Customer customer = new Customer(customerDTO.getCustomerId(),customerDTO.getFirstName(), customerDTO.getLastName(), customerDTO.getPurchases());

            return  new ResponseEntity<>(adminService.addCustomer(user,customer),HttpStatus.OK);

        } catch (CouponSystemException e) {
            return new ResponseEntity<>(e.getMessage(),e.getHttpStatus());

        }
    }

    @PutMapping("/update-customer/")
    public ResponseEntity<?> updateCustomer(@RequestBody CustomerDTO customerDTO){
        try {
            return new ResponseEntity<>(adminService.updateCustomer(
             new User(customerDTO.getId(), customerDTO.getUsername(), customerDTO.getPassword(),ClientType.Customer)
            ,new Customer(customerDTO.getCustomerId(),customerDTO.getFirstName(), customerDTO.getLastName(), customerDTO.getPurchases())),HttpStatus.OK);
        } catch (CouponSystemException e) {
            return new ResponseEntity<>(e.getMessage(),e.getHttpStatus());
        }
    }
    @DeleteMapping("/delete-customer/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long customerId){
        try {
            adminService.deleteCustomer(customerId);
        } catch (CouponSystemException e) {
            return new ResponseEntity<>(e.getMessage(),e.getHttpStatus());

        }
        return new ResponseEntity<>("customer "+customerId+" was deleted",HttpStatus.OK);
    }

    @GetMapping("/all-customers/")
    public ResponseEntity<List<Customer>> getAllCustomers(){
        return  new ResponseEntity<>(adminService.getAllCustomers(),HttpStatus.OK);
    }

    @GetMapping("/all-customers/{customerId}")
    public ResponseEntity<?> getOneCustomer(@PathVariable Long customerId){
        try {
            return  new ResponseEntity<>(adminService.getOneCustomer(customerId),HttpStatus.OK);
        } catch (CouponSystemException e) {
            return new ResponseEntity<>(e.getMessage(),e.getHttpStatus());
        }
    }

}
