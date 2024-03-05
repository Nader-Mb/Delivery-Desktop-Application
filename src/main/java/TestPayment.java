import services.KonnectPaymentService;

public class TestPayment {
    public static void main(String[] args) {
        KonnectPaymentService konnectPaymentService = new KonnectPaymentService();
        float amount = 200.0f;
        konnectPaymentService.initPayment(amount);
    }
}
