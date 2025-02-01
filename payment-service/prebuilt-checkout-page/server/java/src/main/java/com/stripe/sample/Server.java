package com.stripe.sample;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.port;
import static spark.Spark.staticFiles;

import com.google.gson.Gson;

import com.stripe.Stripe;
import com.stripe.model.Event;
import com.stripe.model.Product;
import com.stripe.model.ProductCollection;
import com.stripe.model.checkout.Session;
import com.stripe.exception.*;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;

import io.github.cdimascio.dotenv.Dotenv;

import java.util.List;
import java.util.stream.Collectors;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import org.json.JSONArray;
import org.json.JSONObject;

public class Server {
    private static Gson gson = new Gson();

    public static void main(String[] args) {
        port(4242);

        Dotenv dotenv = Dotenv.load();

        checkEnv();

        Stripe.apiKey = dotenv.get("STRIPE_SECRET_KEY");

        // For sample support and debugging, not required for production:
        Stripe.setAppInfo(
            "stripe-samples/accept-a-payment/prebuilt-checkout-page",
            "0.0.1",
            "https://github.com/stripe-samples"
        );

        staticFiles.externalLocation(
            Paths.get(Paths.get("").toAbsolutePath().toString(), dotenv.get("STATIC_DIR")).normalize().toString()
        );

        // New endpoint: Retrieve product list from Stripe with images
        get("/products", (request, response) -> {
            response.type("application/json");
            Map<String, Object> params = new HashMap<>();
            ProductCollection productCollection = Product.list(params);
            
            // Map each product to a simple JSON object with images parsed correctly.
            List<Map<String, Object>> products = productCollection.getData().stream().map(product -> {
                Map<String, Object> p = new HashMap<>();
                p.put("id", product.getId());
                p.put("name", product.getName());
                p.put("description", product.getDescription());
                p.put("images", product.getImages()); // This is a List<String> of image URLs.
                return p;
            }).collect(Collectors.toList());
            
            System.out.println("Returned JSON: " + gson.toJson(products));
            return gson.toJson(products);
        });

        get("/favicon.ico", (request, response) -> {
            response.status(204); // No Content
            return "";
        });

        // Fetch the Checkout Session to display the JSON result on the success page
        get("/checkout-session", (request, response) -> {
            response.type("application/json");

            String sessionId = request.queryParams("sessionId");
            Session session = Session.retrieve(sessionId);

            return gson.toJson(session);
        });

        post("/create-checkout-session", (request, response) -> {
            response.type("application/json");

            String domainUrl = dotenv.get("DOMAIN");
            String price = dotenv.get("PRICE");

            // Create new Checkout Session for the payment
            SessionCreateParams.Builder builder = new SessionCreateParams.Builder()
                .setSuccessUrl(domainUrl + "/success.html?session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl(domainUrl + "/canceled.html")
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPrice(price)
                        .build());

            SessionCreateParams createParams = builder.build();
            Session session = Session.create(createParams);

            response.redirect(session.getUrl(), 303);
            return "";
        });

        post("/webhook", (request, response) -> {
            String payload = request.body();
            String sigHeader = request.headers("Stripe-Signature");
            String endpointSecret = dotenv.get("STRIPE_WEBHOOK_SECRET");

            Event event = null;

            try {
                event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
            } catch (SignatureVerificationException e) {
                // Invalid signature
                response.status(400);
                return "";
            }

            switch (event.getType()) {
                case "checkout.session.completed":
                    System.out.println("Payment succeeded!");
                    response.status(200);
                    return "";
                default:
                    response.status(200);
                    return "";
            }
        });
    }

    public static void checkEnv() {
        Dotenv dotenv = Dotenv.load();
        String price = dotenv.get("PRICE");
        if(price == "price_12345" || price == "" || price == null) {
            System.out.println("You must set a Price ID in the .env file. Please see the README.");
            System.exit(0);
        }
    }
}