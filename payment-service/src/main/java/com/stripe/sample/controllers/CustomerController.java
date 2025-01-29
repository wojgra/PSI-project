package com.stripe.sample.controllers;

import com.google.gson.Gson;
import com.stripe.model.Customer;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.CustomerListParams;
import static spark.Spark.*;
import static com.stripe.sample.errorhandlers.StripeExceptions.*;
import java.util.Map;

public class CustomerController {
    private static Gson gson = new Gson();

    public static void setupRoutes() {
        get("/api/customers", (req, res) -> {
            res.type("application/json");
            try {
                CustomerListParams params = CustomerListParams.builder().build();
                return gson.toJson(Customer.list(params).getData());
            } catch (Exception e) {
                throw new BadRequestException("Failed to list customers: " + e.getMessage());
            }
        });

        post("/api/customers", (req, res) -> {
            try {
                String requestBody = req.body();
                if (requestBody == null || requestBody.isEmpty()) {
                    throw new BadRequestException("Request body is required");
                }

                Customer requestCustomer = gson.fromJson(requestBody, Customer.class);
                if (requestCustomer.getEmail() == null) {
                    throw new InvalidInputException("Customer email is required");
                }

                CustomerCreateParams params = CustomerCreateParams.builder()
                    .setEmail(requestCustomer.getEmail())
                    .setName(requestCustomer.getName())
                    .build();
                
                return gson.toJson(Customer.create(params));
            } catch (ApiException e) {
                throw e;
            } catch (Exception e) {
                throw new BadRequestException("Invalid customer parameters: " + e.getMessage());
            }
        });

        get("/api/customers/:id", (req, res) -> {
            try {
                String customerId = req.params(":id");
                if (customerId == null || customerId.isEmpty()) {
                    throw new BadRequestException("Customer ID is required");
                }

                Customer customer = Customer.retrieve(customerId);
                if (customer == null) {
                    throw new ResourceNotFoundException("Customer", customerId);
                }

                return gson.toJson(customer);
            } catch (ApiException e) {
                throw e;
            } catch (Exception e) {
                throw new ResourceNotFoundException("Customer", req.params(":id"));
            }
        });
    }
}