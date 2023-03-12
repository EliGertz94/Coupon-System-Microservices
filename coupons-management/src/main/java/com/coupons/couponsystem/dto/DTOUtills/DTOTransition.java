package com.coupons.couponsystem.dto.DTOUtills;

import com.coupons.couponsystem.dto.request.CustomerDTO;
import com.coupons.couponsystem.dto.response.CompanyDTOResponse;
import com.coupons.couponsystem.dto.response.CustomerDTOResponse;
import com.coupons.couponsystem.dto.response.PurchaseDTO;
import com.coupons.couponsystem.dto.response.PurchasesListDTO;
import com.coupons.couponsystem.exception.CouponSystemException;
import com.coupons.couponsystem.model.Company;
import com.coupons.couponsystem.model.Customer;
import com.coupons.couponsystem.model.Purchase;
import com.coupons.couponsystem.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DTOTransition {

    public CompanyDTOResponse EntityToDTO (Company company,String message){
        User user = company.getUser();
        return new CompanyDTOResponse(user,company,message);
    }


    public CustomerDTOResponse EntityToDTO (Customer customer, String message) {

            User user = customer.getUser();
            return new CustomerDTOResponse(user.getId(), customer.getId(),
                    customer.getFirstName(), customer.getLastName(), user.getUsername()
                    , customer.getPurchases(), message);

    }

    //PurchaseDTO
    public PurchaseDTO EntityToDTO(Purchase purchase, String message) {

            return new PurchaseDTO( purchase.getId(), purchase.getPurchaseDate(),
           purchase.getTotalPrice(), purchase.getCustomer(), purchase.isPaymentApproval() ,purchase.getCoupons());

    }

    public PurchasesListDTO EntityToDTO(List<Purchase> purchases, String message) {

        List<PurchaseDTO> purchasesDTO =  new ArrayList<>();
            for (Purchase purchase:
            purchases) {
                purchasesDTO.add(new PurchaseDTO( purchase.getId(), purchase.getPurchaseDate(),
                        purchase.getTotalPrice(), purchase.getCustomer(), purchase.isPaymentApproval() ,purchase.getCoupons()));
            }

            return new PurchasesListDTO(message,purchasesDTO);


    }
}
