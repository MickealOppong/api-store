package com.repo.api.service;

import com.repo.api.dto.CheckoutDto;
import com.repo.api.dto.CheckoutResponse;
import com.repo.api.dto.CheckoutStatusResponse;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CheckoutService {

    private String URL = "http://localhost:5173";

    @Value("${STRIPE_SECRET_KEY}")
    private String secretKey;

    @PostConstruct
    public void init(){
        Stripe.apiKey =secretKey;
    }


    public CheckoutResponse charge(CheckoutDto checkout) throws StripeException {

        // Create a PaymentIntent with the order amount and currency
        SessionCreateParams.LineItem.PriceData.ProductData productData =
                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                        .setName(checkout.getStore())
                        .build();

        // Create new line item with the above product data and associated price
        SessionCreateParams.LineItem.PriceData priceData =
                SessionCreateParams.LineItem.PriceData.builder()
                        .setCurrency(checkout.getCurrency())
                        .setUnitAmount((long) (checkout.getAmount()*100))
                        .setProductData(productData)
                        .build();

        // Create new line item with the above product data and associated price
        SessionCreateParams.LineItem lineItem =
                SessionCreateParams.LineItem.builder()
                        .setPriceData(priceData)
                        .setQuantity(checkout.getQuantity())
                        .build();


        SessionCreateParams params = SessionCreateParams.builder()
                .addLineItem(lineItem)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setReturnUrl(URL+"/return?session_id={CHECKOUT_SESSION_ID}")
                .setUiMode(SessionCreateParams.UiMode.EMBEDDED)
                .build();

        Session session =null;
        try{
            session= Session.create(params);

            return CheckoutResponse.builder()
                    .clientSecret(session.getClientSecret())
                    .build();
        }catch (StripeException e){
            return CheckoutResponse.builder()
                    .clientSecret(null)
                    .build();
        }
    }

    public CheckoutStatusResponse checkoutStatus(String sessionId,Long orderId) throws StripeException {
        Session session = Session.retrieve(sessionId);
        return CheckoutStatusResponse.builder()
                .status(session.getStatus())
                .username(session.getCustomerDetails().getEmail())
                .build();
    }

}
