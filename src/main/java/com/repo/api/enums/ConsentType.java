package com.repo.api.enums;

public enum ConsentType {

    MARKETING_PROMOTIONS,
    ACCOUNT_ACTIVITIES;

    public String getDescription(){
        switch (this){
            case MARKETING_PROMOTIONS -> {
                return "Marketing and Promotions";
            }
            case ACCOUNT_ACTIVITIES -> {
                return "Account activities";
            }default -> {
                return null;
            }
        }
    }
}
