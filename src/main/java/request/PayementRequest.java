package request;

public record PayementRequest(
  String receiverWalletId,
  float amount
) {
}
