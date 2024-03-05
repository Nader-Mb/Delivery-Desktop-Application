package config;

public class KonnectConfig {

    // this is works: 65e324850ed588b9933885f0:e3q1ibb3b4meQAZXwHSPRdn
    private final String API_KEY = "65e35bca0ed588b99338f467:qoGhyM7htBzdFjpTvzrxLIqYXb";

    private final String API_URL = "https://api.preprod.konnect.network/api/v2/";

    private final String PORTFOLIO_ID = "65e35bca0ed588b99338f46b"; // receiver wallet id


    public String getAPI_KEY() {
        return API_KEY;
    }

    public String getAPI_URL() {
        return API_URL;
    }

    public String getPORTFOLIO_ID() {
        return PORTFOLIO_ID;
    }
}