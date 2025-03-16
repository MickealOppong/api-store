package com.repo.api.enums;

public enum InterestType {
    COMPOUND_INTEREST,
    SIMPLE_INTEREST;
    public String getDescription(){
        switch (this){
            case SIMPLE_INTEREST -> {
                return "Simple interest";
            }
            case COMPOUND_INTEREST -> {
                return "Compound interest";
            }
            default -> {
                return null;
            }
        }
    }
}
