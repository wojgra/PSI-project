package com.stripe.sample.controllers;

import com.google.gson.Gson;
import com.stripe.model.Product;
import com.stripe.param.ProductCreateParams;
import com.stripe.param.ProductListParams;
import static spark.Spark.*;
import static com.stripe.sample.errorhandlers.StripeExceptions.*;
import java.util.Map;

public class ProductController {
    private static Gson gson = new Gson();

    public static void setupRoutes() {
        get("/api/products", (req, res) -> {
            res.type("application/json");
            try {
                ProductListParams params = ProductListParams.builder().build();
                return gson.toJson(Product.list(params).getData());
            } catch (Exception e) {
                throw new BadRequestException("Failed to list products: " + e.getMessage());
            }
        });

        post("/api/products", (req, res) -> {
            try {
                String requestBody = req.body();
                if (requestBody == null || requestBody.isEmpty()) {
                    throw new BadRequestException("Request body is required");
                }

                Product requestProduct = gson.fromJson(requestBody, Product.class);
                if (requestProduct.getName() == null) {
                    throw new InvalidInputException("Product name is required");
                }

                ProductCreateParams params = ProductCreateParams.builder()
                    .setName(requestProduct.getName())
                    .build();
                
                return gson.toJson(Product.create(params));
            } catch (ApiException e) {
                throw e;
            } catch (Exception e) {
                throw new BadRequestException("Invalid product parameters: " + e.getMessage());
            }
        });

        get("/api/products/:id", (req, res) -> {
            try {
                String productId = req.params(":id");
                if (productId == null || productId.isEmpty()) {
                    throw new BadRequestException("Product ID is required");
                }

                Product product = Product.retrieve(productId);
                if (product == null) {
                    throw new ResourceNotFoundException("Product", productId);
                }

                return gson.toJson(product);
            } catch (ApiException e) {
                throw e;
            } catch (Exception e) {
                throw new ResourceNotFoundException("Product", req.params(":id"));
            }
        });
    }
}