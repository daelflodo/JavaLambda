package com.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * P = Monto del préstamo
 * i = Tasa de interés mensual
 * n = Plazo del crédito en meses
 * <p>
 * Cuota mensual = (P * i) / (1 - (1 + i) ^ (-n))
 */
public class LambdaBank implements RequestHandler<BankRequest, BankResponse> {
    @Override
    public BankResponse handleRequest(BankRequest bankRequest, Context context) {

        MathContext mathContext = MathContext.DECIMAL128;

        BigDecimal amount = bankRequest.getAmount()
                .setScale(2, RoundingMode.HALF_UP);//Metodo del redondeo del banquero a dos decimales
        BigDecimal monthlyRate = bankRequest.getRate()
                .setScale(2, RoundingMode.HALF_UP)
                .divide(BigDecimal.valueOf(100), mathContext);
        BigDecimal monthlyRateWithAccount = bankRequest.getRate()
                .subtract(BigDecimal.valueOf(0.2), mathContext)
                .setScale(2, RoundingMode.HALF_UP)
                .divide(BigDecimal.valueOf(100), mathContext);
        Integer term = bankRequest.getTerm();

        BigDecimal monthlyPayment = this.calculateQuota(amount, monthlyRate, term, mathContext);
        BigDecimal monthlyPaymentWithAccount = this.calculateQuota(amount, monthlyRateWithAccount, term, mathContext);

        BankResponse bankResponse = new BankResponse();
        //Cuando no tiene cuota;
        bankResponse.setQuota(monthlyPayment);
        bankResponse.setRate(monthlyRate);
        bankResponse.setTerm(term);
        //cuando tiene cuota;
        bankResponse.setQuotaWithAccount(monthlyPaymentWithAccount);
        bankResponse.setRateWithAccount(monthlyRateWithAccount);
        bankResponse.setTermWithAccount(term);
        return bankResponse;
    }

    public BigDecimal calculateQuota(BigDecimal amount, BigDecimal rate, Integer term, MathContext mathContext) {

        //calcular 1 + i
        BigDecimal onePlusRate = rate.add(BigDecimal.ONE, mathContext);
        //calcular  (1 + i) ^ (n)
        BigDecimal onePlusRateToN = onePlusRate.pow(term, mathContext);
        //Calculamos el reciproco de (1 + i) ^ (n) para obtener  (1 + i) ^ (-n)
        BigDecimal onePlusRateToNegativeN = BigDecimal.ONE.divide(onePlusRateToN, mathContext);
        //Calcular cuota mensual
        BigDecimal numerator = amount.multiply(rate, mathContext);
        BigDecimal denominator = BigDecimal.ONE.subtract(onePlusRateToNegativeN, mathContext);

        BigDecimal monthlyPayment = (numerator.divide(denominator, mathContext)).setScale(2, RoundingMode.HALF_UP);
//        monthlyPayment = monthlyPayment.setScale(2, RoundingMode.HALF_UP);
        return monthlyPayment;
    }
}