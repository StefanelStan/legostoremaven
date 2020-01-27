package rc.legostoremaven.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class PaymentOptions {
    @Id
    private String id;
    private PaymentType paymentType;
    private int fee;

    public PaymentOptions(PaymentType paymentType, int fee) {
        this.paymentType = paymentType;
        this.fee = fee;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public int getFee() {
        return fee;
    }
}
