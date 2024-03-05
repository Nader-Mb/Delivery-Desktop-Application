package services;


import javafx.scene.control.Alert;

import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import static javax.mail.Transport.send;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

    public class SendMail {
        public static void showAlert(Alert.AlertType alertType, String title, String content) {
            Alert alert = new Alert(alertType);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(content);
            alert.showAndWait();
        }
        static Session sesh;
        static Properties prop = new Properties();

        public static void Mail(String UN, String PW,String to, String sub, String cont) /*throws IOException*/ {

            try {
                prop.put("mail.smtp.auth", "true");

                prop.put("mail.smtp.starttls.enable", "true"); //fot TLS
                //prop.put("mail.smtp.ssl.enable", "true"); //forSSL

                prop.put("mail.smtp.host", "smtp.gmail.com");
                //prop.put("mail.smtp.host", "smtp.mail.yahoo.com");

                prop.put("mail.smtp.port", "587"); //fot TLS
                //prop.put("mail.smtp.port", "465");  //for SSL
                sesh = Session.getInstance(prop,
                        new javax.mail.Authenticator() {
                            @Override
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(UN, PW);
                            }
                        });

                Message m = new MimeMessage(sesh);
                m.setFrom(new InternetAddress(UN));
                m.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
                m.setSubject(sub);
                m.setSentDate(new Date());
                m.setContent(cont, "text/plain");
                m.setHeader("EMAIL HEAD", "Recipe");
                System.out.println("\n \n \n \t >> ??????? " + m.getContentType());
                System.out.println("\n \n \n \t >> ??????? " + m.getDataHandler());
                System.out.println("\n \n \n \t >> ??????? " + m.getSubject());

                Transport t;

                t = sesh.getTransport("smtp");

                System.out.println(">> ? smtp(s) ---> ## " + t.getURLName() + " \n>> ?");

                Transport.send(m);  //for TLS

                //for SSL
                /*
                t.connect("smtp.mail.yahoo.com", UN, PW);
                t.sendMessage(m, m.getAllRecipients());
                t.close();*/

                System.out.println("Message sent successfully");
                showAlert(Alert.AlertType.INFORMATION, "Email Sent", "Email has been sent successfully.");
            } catch (MessagingException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while sending the email.");
            }
            }
            }


