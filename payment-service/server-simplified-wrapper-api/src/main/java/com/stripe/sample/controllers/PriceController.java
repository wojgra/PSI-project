package com.stripe.sample.controllers;

import com.google.gson.Gson;
import com.stripe.model.Price;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.PriceListParams;
import static spark.Spark.*;
import static com.stripe.sample.errorHandlers.StripeExceptions.*;
public class PriceController {
    private static Gson gson = new Gson();

    public static void setupRoutes() {
        get("/api/prices", (req, res) -> {
            res.type("application/json");
            try {
                PriceListParams params = PriceListParams.builder().build();
                return gson.toJson(Price.list(params).getData());
            } catch (Exception e) {
                throw new BadRequestException("Failed to list prices: " + e.getMessage());
            }
        });

        post("/api/prices", (req, res) -> {
            try {
                String requestBody = req.body();
                if (requestBody == null || requestBody.isEmpty()) {
                    throw new BadRequestException("Request body is required");
                }

                Price requestPrice = gson.fromJson(requestBody, Price.class);
                if (requestPrice.getUnitAmount() == null) {
                    throw new InvalidInputException("Unit amount is required");
                }

                PriceCreateParams params = PriceCreateParams.builder()
                    .setUnitAmount(requestPrice.getUnitAmount())
                    .setCurrency("usd")
                    .setProduct(requestPrice.getProduct())
                    .build();
                
                return gson.toJson(Price.create(params));
            } catch (ApiException e) {
                throw e;
            } catch (Exception e) {
                throw new BadRequestException("Invalid price parameters: " + e.getMessage());
            }
        });

        get("/api/prices/:id", (req, res) -> {
            try {
                String priceId = req.params(":id");
                if (priceId == null || priceId.isEmpty()) {
                    throw new BadRequestException("Price ID is required");
                }

                Price price = Price.retrieve(priceId);
                if (price == null) {
                    throw new ResourceNotFoundException("Price", priceId);
                }

                return gson.toJson(price);
            } catch (ApiException e) {
                throw e;
            } catch (Exception e) {
                throw new ResourceNotFoundException("Price", req.params(":id"));
            }
        });
    }
}