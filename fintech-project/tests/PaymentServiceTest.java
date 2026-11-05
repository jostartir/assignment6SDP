import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PaymentServiceTest {
    private final PaymentService paymentService = new PaymentService();

    @Test
    void acceptsPositivePayment() {
        assertTrue(paymentService.isValidPayment(new BigDecimal("25.00")));
    }

    @Test
    void rejectsZeroPayment() {
        assertFalse(paymentService.isValidPayment(BigDecimal.ZERO));
    }

    @Test
    void calculatesTwoPercentFee() {
        BigDecimal fee = paymentService.calculateFee(new BigDecimal("100.00"));

        assertEquals(new BigDecimal("2.0000"), fee);
    }

    @Test
    void rejectsFeeCalculationForInvalidPayment() {
        assertThrows(
                IllegalArgumentException.class,
                () -> paymentService.calculateFee(BigDecimal.ZERO)
        );
    }
}