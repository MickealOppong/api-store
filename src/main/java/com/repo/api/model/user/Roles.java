package com.repo.api.model.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Roles {

    @Id @GeneratedValue
    private Long id;
    private String role;

    public Roles(String role){
        this.role = role;
    }


}
