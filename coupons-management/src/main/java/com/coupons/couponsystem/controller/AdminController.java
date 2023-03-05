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
        public ResponseEntity<Company> addCompany(@RequestBody CompanyDTO companyDTO){
        try {

//            for (Coupon coupon:
//                    companyDTO.getCoupons()) {
//                System.out.println(coupon);
//            }
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


            return new ResponseEntity<>(adminService.addCompany(user, company), HttpStatus.OK);
        } catch (CouponSystemException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }

    @PutMapping("update-company")
    public ResponseEntity<Company> updateCompany(@RequestBody CompanyDTO company){
//        System.out.println("company.getEmail() "+company.getEmail());
        try {
            return new ResponseEntity<>(adminService.updateCompany(
                    new User(company.getId(),company.getUsername(),company.getPassword(),ClientType.Company),
                    new Company(company.getCompanyId(),company.getName(),company.getCoupons())),HttpStatus.OK);
        } catch (CouponSystemException e) {
            throw new ResponseStatusException(e.getHttpStatus(),e.getMessage());

        }
    }

    @DeleteMapping("delete-company/{companyId}")
    public ResponseEntity<String> deleteCompany(@PathVariable Long companyId){
        try {
            adminService.deleteCompany(companyId);
        } catch (CouponSystemException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
        return new ResponseEntity<>("company was deleted",HttpStatus.OK);
    }

    @GetMapping("/company/get-all/")
    public ResponseEntity<List<Company>> getAllCompanies(){
        return  new ResponseEntity<>(adminService.getAllCompanies(),HttpStatus.OK);
    }
    @GetMapping("/company/{companyId}")
    public ResponseEntity<Company> getOneCompany(@PathVariable Long companyId){
        try {
            return  new ResponseEntity<>(adminService.getOneCompany(companyId),HttpStatus.OK);
        } catch (CouponSystemException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());

        }
    }

    @PostMapping("/customer/")
    public ResponseEntity<Customer> addCustomer(@RequestBody CustomerDTO customerDTO){
        try {
            return  new ResponseEntity<>(adminService.addCustomer(
                    new User(customerDTO.getId(), customerDTO.getUsername(), customerDTO.getPassword(),ClientType.Customer)
                    ,new Customer(customerDTO.getCustomerId(),customerDTO.getFirstName(), customerDTO.getLastName(), customerDTO.getPurchases())),HttpStatus.OK);
        } catch (CouponSystemException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());

        }
    }

    @PutMapping("/customer/update")
    public ResponseEntity<Customer> updateCustomer(@RequestBody CustomerDTO customerDTO){
        try {
            return new ResponseEntity<>(adminService.updateCustomer(
             new User(customerDTO.getId(), customerDTO.getUsername(), customerDTO.getPassword(),ClientType.Customer)
            ,new Customer(customerDTO.getCustomerId(),customerDTO.getFirstName(), customerDTO.getLastName(), customerDTO.getPurchases())),HttpStatus.OK);
        } catch (CouponSystemException e) {
            throw new ResponseStatusException(e.getHttpStatus(),e.getMessage());

        }
    }
    @DeleteMapping("/customer/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long customerId){
        try {
            adminService.deleteCustomer(customerId);
        } catch (CouponSystemException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());

        }
        return new ResponseEntity<>("customer"+customerId+" was deleted",HttpStatus.OK);
    }

    @GetMapping("/customer/get-all")
    public ResponseEntity<List<Customer>> getAllCustomers(){
        return  new ResponseEntity<>(adminService.getAllCustomers(),HttpStatus.OK);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<Customer> getOneCustomer(@PathVariable Long customerId){
        try {
            return  new ResponseEntity<>(adminService.getOneCustomer(customerId),HttpStatus.OK);
        } catch (CouponSystemException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }

}
