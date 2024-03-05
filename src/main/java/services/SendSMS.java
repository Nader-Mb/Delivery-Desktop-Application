package services;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SendSMS {

    // Method to send an SMS message
    public void send_SMS(String recipientNumber) {
        // Twilio credentials
        String ACCOUNT_SID = "AC94cd8c2410409dd2d20ddd449a4c82a0"; // Your Twilio Account SID
        String AUTH_TOKEN = "c9d39ede64fad01e9614947e954aa521"; // Your Twilio Auth Token

        // Initialize Twilio with your credentials
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        // Compose the message to be sent
        String message = "Menu updated";

        // Send the message using Twilio API
        // Create an SMS message specifying sender's and recipient's phone numbers, along with the message content
        Message twilioMessage = Message.creator(
                // Recipient's phone number (should be in E.164 format)
                new PhoneNumber(recipientNumber),
                // Sender's phone number (Twilio phone number)
                new PhoneNumber("+19782917692"),
                // Message content
                message
        ).create();

        // Print the SID (Service Identifier) of the sent message to the console
        System.out.println("SMS envoyé : " + twilioMessage.getSid());
    }

}