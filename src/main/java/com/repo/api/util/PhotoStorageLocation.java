package com.repo.api.util;

import org.springframework.stereotype.Component;

@Component
public class PhotoStorageLocation {

    public String getLocation(){
        String location = "App-photos";
        return location;
    }
}
