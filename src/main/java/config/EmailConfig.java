package config;

public class EmailConfig {
    private static final String USERNAME = "nader123mb456@gmail.com";
    private static final String PASSWORD = "pfbc reya owzr webu";
    private static final String HOST = "smtp.gmail.com";
    private static final int PORT = 587;

    public String getUsername() {
        return USERNAME;
    }

    public String getPassword() {
        return PASSWORD;
    }

    public String getHost() {
        return HOST;
    }

    public int getPort() {
        return PORT;
    }
}
