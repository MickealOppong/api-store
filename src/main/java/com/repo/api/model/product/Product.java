package com.repo.api.model.product;

import com.repo.api.util.LogEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product extends LogEntity {

    @Id @GeneratedValue
    private Long recId;

    private String productName;
    private String searchName;
    @Column(length =2000)
    private String productDescription;

    @Column(length =2000)
    private String generalInfo1;
    @Column(length =2000)
    private String generalInfo2;
    @Column(length= 2000)
    private String generalInfo3;
    @Column(length =2000)
    private String generalInfo4;

    private double price;
    private double reducedPrice;

    private boolean isFreeShipping;
    private boolean isNewArrival;
    private boolean isOnSale;


     @ManyToMany
    private List<Category> categoryList = new ArrayList<>();

    @ManyToMany
    private List<Parameter> parameterList= new ArrayList<>();


}
