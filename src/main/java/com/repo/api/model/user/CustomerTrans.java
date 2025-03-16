package com.repo.api.model.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerTrans {


    @Id
    @GeneratedValue
    private Long id;


    @ManyToOne
    @JoinColumn(name = "fk_id",referencedColumnName = "id")
    private Customer customer;
}
