package response;

public record PayementResponse(
  String payUrl,
  String paymentRef
) {
}
