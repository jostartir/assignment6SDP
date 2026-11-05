import java.math.BigDecimal;

public class PaymentService {
    public boolean isValidPayment(BigDecimal amount) {
        return amount != null && amount.compareTo(BigDecimal.ZERO) > 0;
    }

    public BigDecimal calculateFee(BigDecimal amount) {
        if (!isValidPayment(amount)) {
            throw new IllegalArgumentException("Payment amount must be positive");
        }

        return amount.multiply(new BigDecimal("0.02"));
    }
}