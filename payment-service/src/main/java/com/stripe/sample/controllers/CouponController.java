package com.stripe.sample.controllers;

import com.google.gson.Gson;
import com.stripe.model.Coupon;
import com.stripe.param.CouponCreateParams;
import com.stripe.param.CouponListParams;
import static spark.Spark.*;
import static com.stripe.sample.errorhandlers.StripeExceptions.*;
import java.math.BigDecimal;
import java.util.Map;

public class CouponController {
    private static Gson gson = new Gson();

    public static void setupRoutes() {
        get("/api/coupons", (req, res) -> {
            res.type("application/json");
            try {
                CouponListParams params = CouponListParams.builder().build();
                return gson.toJson(Coupon.list(params).getData());
            } catch (Exception e) {
                throw new BadRequestException("Failed to list coupons: " + e.getMessage());
            }
        });

        post("/api/coupons", (req, res) -> {
            try {
                String requestBody = req.body();
                if (requestBody == null || requestBody.isEmpty()) {
                    throw new BadRequestException("Request body is required");
                }

                Coupon requestCoupon = gson.fromJson(requestBody, Coupon.class);
                if (requestCoupon.getPercentOff() == null) {
                    throw new InvalidInputException("Percent off is required");
                }

                CouponCreateParams params = CouponCreateParams.builder()
                    .setPercentOff(requestCoupon.getPercentOff())
                    .setDuration(CouponCreateParams.Duration.ONCE)
                    .build();
                
                return gson.toJson(Coupon.create(params));
            } catch (ApiException e) {
                throw e;
            } catch (Exception e) {
                throw new BadRequestException("Invalid coupon parameters: " + e.getMessage());
            }
        });

        get("/api/coupons/:id", (req, res) -> {
            try {
                String couponId = req.params(":id");
                if (couponId == null || couponId.isEmpty()) {
                    throw new BadRequestException("Coupon ID is required");
                }

                Coupon coupon = Coupon.retrieve(couponId);
                if (coupon == null) {
                    throw new ResourceNotFoundException("Coupon", couponId);
                }

                return gson.toJson(coupon);
            } catch (ApiException e) {
                throw e;
            } catch (Exception e) {
                throw new ResourceNotFoundException("Coupon", req.params(":id"));
            }
        });

        exception(ApiException.class, (e, req, res) -> {
            res.status(e.getStatusCode());
            res.type("application/json");
            res.body(gson.toJson(Map.of(
                "error", e.getErrorCode(),
                "message", e.getMessage(),
                "status", e.getStatusCode()
            )));
        });
    }
}