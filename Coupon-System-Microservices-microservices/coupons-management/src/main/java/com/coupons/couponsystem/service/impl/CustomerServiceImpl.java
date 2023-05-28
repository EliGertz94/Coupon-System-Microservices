package com.coupons.couponsystem.service.impl;

import com.coupons.couponsystem.CouponSystemApplication;
import com.coupons.couponsystem.clientLogIn.ClientType;
import com.coupons.couponsystem.dto.Order;
import com.coupons.couponsystem.exception.CouponSystemException;
import com.coupons.couponsystem.model.Coupon;
import com.coupons.couponsystem.model.Customer;
import com.coupons.couponsystem.model.Purchase;
import com.coupons.couponsystem.model.User;
import com.coupons.couponsystem.service.CustomerService;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@Transactional
@Scope("prototype")
public class CustomerServiceImpl extends ClientFacade  implements CustomerService {

    private long customerId;

    public static Logger logger = LoggerFactory.getLogger(CouponSystemApplication.class);


    /**
     * @param email
     * @param password
     * @return
     * @throws CouponSystemException - customer not found
     */
    public boolean logIn(String email, String password, ClientType clientRole) throws CouponSystemException {

        User customerUser = userRepository.findByUsername(email)
                .orElseThrow(() -> new CouponSystemException("customer not found logIn customerService ", HttpStatus.NOT_FOUND));

        if (customerUser.getPassword().equals(password)
                && customerUser.getClientRole().equals(clientRole)) {
            Customer customer = customerRepository.findByUserId(customerUser.getId())
                    .orElseThrow(() -> new CouponSystemException("customer not found logIn customerService ", HttpStatus.NOT_FOUND));

            customerId = customer.getId();

            return true;
        }
        return false;
    }

    /**
     * @param
     * @throws CouponSystemException - if purchaseCoupon purchase already exist -  customer or coupon not founds by id
     */
    @Override
    public String makePurchase(List<Long> couponIdList,long customerId) throws CouponSystemException {


        Map<Long, Integer> couponPurchases = new HashMap<>();

        //filtering the coupon id's to a map with value being the amount of each
        // to make less requests
        for (Long id : couponIdList) {
            if (!couponPurchases.containsKey(id)) {
                couponPurchases.put(id, 1);
            } else {
                couponPurchases.put(id, couponPurchases.get(id) + 1);
            }
        }

        //validating coupons
        // filtering all coupons to PayPal item and to list

        double totalPrice = 0;

        List<Item> items = new ArrayList<>();

        for (Long id : couponPurchases.keySet()) {

            Coupon coupon = couponRepository.findById(id).orElseThrow(()
                    -> new CouponSystemException(" coupon in coupon list was not found ", HttpStatus.NOT_FOUND));
            if (!checkedCoupon(coupon)) {
                throw new CouponSystemException("coupon # " + coupon.getId() + " can't be bought ", HttpStatus.NOT_FOUND);
            }
            for(int i=0; i<couponPurchases.get(id);i++){




                System.out.println(coupon.getTitle());
                Item item = new Item();
                item.setQuantity("1");
                item.setSku(id + "");
                item.setName(coupon.getTitle());
                item.setPrice(coupon.getPrice() + "");
                item.setCurrency("USD");
                items.add(item);


                totalPrice += coupon.getPrice() ;


            }
        }



        System.out.println(totalPrice+" total price");
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8081/api/paypal/";

        ItemList itemList = new ItemList();
        itemList.setItems(items);

        Order order = Order.builder()
                .price(totalPrice)
                .currency("USD")
                .description(customerId +"")
                .intent("sale")
                .method("paypal")
                .items(itemList.getItems())
                .build();
        String result=  restTemplate.postForEntity(url,order,String.class).getBody();

        return result;

    }



    /**
     *checking if coupon is purchasable and has amount bigger than 0 also checking if the company is currently active
     * @return boolean
     */

    private boolean checkedCoupon(Coupon coupon){


        if(coupon.getAmount()<= 0|| !coupon.isBuyable()|| !coupon.getCompany().isActive())
        {
            return false;
        }
        return true;
    }

    /**
     *
     * @return List<Coupon>
     */
    @Override
    public List<Purchase> getCustomerPurchases(long customerId) throws CouponSystemException {

        List<Purchase> purchases= purchaseRepository.findByCustomer_idOrderByIdDesc(customerId);

        System.out.println(purchases);
        return purchases;
    }

    /**
     *
     * @param maxPrice
     * @return
     */
    @Override
    public List<Purchase> getCustomerPurchases(double maxPrice,long customerId) throws CouponSystemException {

        List<Purchase> purchases= purchaseRepository.findAllByCustomer_idAndTotalPriceLessThanEqual(customerId,maxPrice);
        return purchases;
    }


    /**
     *
     * @return Customer
     * @throws CouponSystemException -  customer not found
     */
    @Override
    public Customer getCustomerDetails(long customerId) throws CouponSystemException {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(()-> new CouponSystemException(
                        "customer not found by id - getCustomerDetails",HttpStatus.NOT_FOUND));

        return customer;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }
}
