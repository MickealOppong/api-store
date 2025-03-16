package com.repo.api.enums;

public enum PrivacyType {

    TARGETED_MARKETING,
   FUNCTIONAL_COOKIES,
    STRICTLY_NECESSARY,
    ANALYTICAL,
    TARGETING_ADVERTISING;

    public String getDescription(){
        switch (this){
            case TARGETED_MARKETING -> {
                return "Targeted Advertisement";
            }
            case FUNCTIONAL_COOKIES -> {
                return "Functional Cookies";
            }
            case STRICTLY_NECESSARY -> {
                return "Strictly necessary cookies";
            }
            case ANALYTICAL -> {
                return "Analytical or Performance Cookies";
            }
            case TARGETING_ADVERTISING -> {
                return "Targeting / advertising";
            }

            default -> {
                return null;
            }
        }
    }

}
