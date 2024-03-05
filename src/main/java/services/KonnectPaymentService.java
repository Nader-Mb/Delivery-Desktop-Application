package services;

import com.fasterxml.jackson.databind.ObjectMapper;
import config.KonnectConfig;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import request.PayementRequest;
import response.PayementResponse;

import java.io.IOException;
import java.util.logging.Logger;


public class KonnectPaymentService {

    private static final Logger logger = Logger.getLogger(KonnectPaymentService.class.getName());

    private final String apiKey;
    private final String baseUrl;
    private final String receiverWalletId; // receiver wallet id

    private final MailSenderService mailSenderService = new MailSenderService();


    public KonnectPaymentService() {
        KonnectConfig konnectConfig = new KonnectConfig();
        this.apiKey = konnectConfig.getAPI_KEY();
        this.baseUrl = konnectConfig.getAPI_URL();
        this.receiverWalletId = konnectConfig.getPORTFOLIO_ID();
    }

    public PayementResponse initPayment(float amount) {
        // Create HttpClient instance
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            // https://api.preprod.konnect.network/api/v2/payments/init-payment
            String url = baseUrl + "payments/init-payment";

            logger.info("url: " + url);
            logger.info("Initiating payment to: " + receiverWalletId + " amount: " + amount);

            // Create a POST request
            HttpPost httpPost = new HttpPost(url);

            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setHeader("x-api-key", apiKey);

            PayementRequest paymentRequest = new PayementRequest(receiverWalletId, amount);

            // Serialize PaymentRequest object to JSON
            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody = objectMapper.writeValueAsString(paymentRequest);

            logger.info("Request Body: " + requestBody);

            // Set the request body
            StringEntity entity = new StringEntity(requestBody, "UTF-8");
            entity.setContentType("application/json");
            httpPost.setEntity(entity);

            // Execute the POST request
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                // Get the response entity
                HttpEntity responseEntity = response.getEntity();

                // Print the response status code
                logger.info("Response Status: " + response.getStatusLine());

                // Check if response status is 200
                if (response.getStatusLine().getStatusCode() == 200) {
                    // Print the response body
                    if (responseEntity != null) {
                        String responseString = EntityUtils.toString(responseEntity);
                        logger.info("Response Body: " + responseString);
                        // Parse response to get payUrl and paymentRef
                        PayementResponse paymentResponse = objectMapper.readValue(responseString, PayementResponse.class);
                        mailSenderService.sendEmail("Congratulations", "flousek waslet sa7pi");
                        return paymentResponse;
                    }
                } else {
                    // If response status is not 200, just log the response status
                    logger.warning("Non-200 response received: " + response.getStatusLine());
                    return null; // or return a default value as needed
                }

                // Ensure the response entity is fully consumed
                EntityUtils.consume(responseEntity);
            }
        } catch (IOException e) {
            logger.severe("Error occurred while initiating payment: " + e.getMessage());
        }
        return null;
    }
}
