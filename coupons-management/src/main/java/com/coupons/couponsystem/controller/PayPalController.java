package com.coupons.couponsystem.controller;

import com.coupons.couponsystem.dto.request.Order;
import com.coupons.couponsystem.dto.request.RequestPurchaseStats;
import com.coupons.couponsystem.exception.CouponSystemException;
import com.coupons.couponsystem.exception.customeResponse.CustomResponse;
import com.coupons.couponsystem.model.Coupon;
import com.coupons.couponsystem.model.Customer;
import com.coupons.couponsystem.model.Purchase;
import com.coupons.couponsystem.repositoty.CouponRepository;
import com.coupons.couponsystem.repositoty.CustomerRepository;
import com.coupons.couponsystem.service.impl.PaypalService;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("api/paypal/")
public class PayPalController extends ClientController {

    @Autowired
    PaypalService paypalService;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CouponRepository couponRepository;

    public static final String SUCCESS_URL="pay/success/";
    public static final String CANCEL_URL="pay/cancel";
    public static final String DOMAIN="http://localhost:8081/api/paypal/";


    @PostMapping
    public String payment(@RequestBody Order order){
        try {
            Payment payment = paypalService.createPayment(
                    order.getPrice(),
                    order.getCurrency(),
                    order.getMethod(),
                    order.getIntent(),
                    order.getDescription(),
                    DOMAIN + CANCEL_URL,
                    DOMAIN + SUCCESS_URL,
                    order.getItems());
        

            for(Links link:payment.getLinks()) {
                if(link.getRel().equals("approval_url")) {
                    System.out.println("the links " + link.getHref());
                    return link.getHref();
                }
            }

        } catch (PayPalRESTException e) {
            throw new RuntimeException(e);
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
        return "redirect:/";
    }

    @GetMapping("pay/cancel")
    public String cancelPay() {
        return "cancel";
    }

    // successful payment action endpoint
    @GetMapping("pay/success/")
    public Boolean successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
//            payment.getTransactions()
            if (payment.getState().equals("approved")) {

                //customer id
                long customerId = Long.parseLong(payment.getTransactions().get(0).getDescription());

                //PayPal item List
                List<Item> items = payment.getTransactions().get(0).getItemList().getItems();

                double totalPrice = Double.parseDouble(payment.getTransactions().get(0).getAmount().getTotal());

                System.out.println(customerId +" is about to get saved");


                List<Coupon> coupons = new LinkedList<>();


                for (Item item:items) {
                    Coupon coupon = couponRepository.findById(Long.parseLong(item.getSku())).orElseThrow(()
                            -> new CouponSystemException( " no customer with such id was found", HttpStatus.NOT_FOUND));

                    coupon.setAmount(coupon.getAmount() - 1);


                    coupons.add(coupon);

                    System.out.println(coupon.getCompany().getId() + "coupon company id ");
                    int itemQuantity =Integer.parseInt(item.getQuantity());
                    for(int i=0 ;i< itemQuantity;i++){
                        RequestPurchaseStats requestPurchaseStats = RequestPurchaseStats.builder()
                                .company_id(coupon.getCompany().getId())
                                .customer_id(customerId)
                                .product_id(coupon.getId())
                                .price(coupon.getPrice())
                                .build();

                        //sending info to stats database
                        ResponseEntity<String> response = webClient.post()
                                .uri("http://localhost:8082/stats/")
                                .bodyValue(requestPurchaseStats)
                                .retrieve()
                                .toEntity(String.class).block();
                    }
                }

                Customer customer =  customerRepository.findById(customerId).orElseThrow(()
                        -> new CouponSystemException( " no customer with such id was found", HttpStatus.NOT_FOUND));

                //add purchase

                Purchase purchase = Purchase.builder()
                        .purchaseDate(LocalDateTime.now())
                        .id(0)
                        .customer(customer)
                        .totalPrice(totalPrice)
                        .coupons(coupons)
                        .build();

//                Purchase savedPurchase = purchaseRepository.save(purchase);

               purchase.setPaymentApproval(true);
                purchaseRepository.save(purchase);


                return true;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
//        } catch (CouponSystemException e) {
//            throw new RuntimeException(e);
        }
        return null;
    }



}
