package com.trisys.Pos.System.modal;

import com.trisys.Pos.System.domain.PaymentType;
import lombok.Data;

@Data
public class PaymentSummary {

    private PaymentType paymentType;

    private Double totalAmount;

    private int transactionCount;

    private double percentage;
}