package com.repo.api.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SimpleInterest {

    private static final  long DURATION = 6;
    private static final BigDecimal FACTOR = new BigDecimal(1);
    private static final BigDecimal NUMBER_OF_MONTHS = new BigDecimal(12);
    private static final BigDecimal MONTHLY_FACTOR = FACTOR.divide(NUMBER_OF_MONTHS,4, RoundingMode.FLOOR);


    public static long getDuration(){
        return DURATION;
    }

    public static BigDecimal computeMonthlyInterest(Double principal, Double rate) {
        BigDecimal monthlyInterestRate = BigDecimal.valueOf(rate).divide(BigDecimal.valueOf(12),4,RoundingMode.CEILING);
        BigDecimal loanAmount = new BigDecimal(principal);
        return loanAmount.multiply(monthlyInterestRate);
    }

    public static BigDecimal computeInterest(Double principal, Double rate,long duration) {
        BigDecimal interestRate = new BigDecimal(rate);
        BigDecimal loanAmount = new BigDecimal(principal);
        return loanAmount.multiply(interestRate).multiply(BigDecimal.valueOf(getDuration()).divide(BigDecimal.valueOf(12),4,RoundingMode.CEILING));
    }

    public static BigDecimal computeMonthlyRepayment(Double principal,double rate) {
        BigDecimal loanAmount = new BigDecimal(principal);
        return loanAmount.divide(BigDecimal.valueOf(getDuration()),4,RoundingMode.CEILING).add(computeMonthlyInterest(principal,rate));
    }

    public static BigDecimal computeMonthlyAccumulatedValue(double principal, double rate,long duration) {
        BigDecimal interestRate = new BigDecimal(rate);
        BigDecimal loanAmount = new BigDecimal(principal);
        return loanAmount.add(computeInterest(principal,rate,duration));
    }

    public static BigDecimal computeAccumulatedValue(double principal, double rate,long duration) {
        BigDecimal interestRate = new BigDecimal(rate);
        BigDecimal loanAmount = new BigDecimal(principal);
        return loanAmount.add(computeInterest(principal,rate,duration));
    }



    public static void main(String[] args) {
        System.out.println(computeMonthlyInterest(200.00,0.125));
        System.out.println(computeAccumulatedValue(200.00,0.125,6));
        System.out.println("Int "+computeInterest(200.00,0.125,6));
    }
}
