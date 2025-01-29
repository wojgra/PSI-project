package com.stripe.sample;

import static spark.Spark.before;
import static spark.Spark.port;

import com.google.gson.Gson;
import com.stripe.Stripe;
import com.stripe.sample.controllers.*;
import io.github.cdimascio.dotenv.Dotenv;

public class Server {

    private static Gson gson = new Gson();

    public static void main(String[] args) {
        port(4242);
        Dotenv dotenv = Dotenv.load();
    
        Stripe.apiKey = dotenv.get("STRIPE_SECRET_KEY");
        
        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE");
            response.header("Access-Control-Allow-Headers", "Content-Type");
        });

        ProductController.setupRoutes();
        PriceController.setupRoutes();
        CustomerController.setupRoutes();
        CouponController.setupRoutes();
    }
}

