package models;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class SMSSender {


    public void  send_SMS ( String recipientNumber,String username,String lastName) {


        String ACCOUNT_SID = "";
        String AUTH_TOKEN = "";

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        String message = "Bonjour Mr " +username +lastName+"your successfully signed to our app green menu";


        Message twilioMessage = Message.creator(
                new com.twilio.type.PhoneNumber("noumrou l chtabaathlou"),
                new com.twilio.type.PhoneNumber("noumrou chtekhdhou mel twilio"),message).create();

        System.out.println("SMS envoyé : " + twilioMessage.getSid());
    }
}
