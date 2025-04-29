package com.repo.api.util;

import com.repo.api.model.product.Category;
import com.repo.api.model.product.Product;
import com.repo.api.model.util.Slider;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Photo {

    @Id @GeneratedValue
    private Long id;
    private String fileName;
    private String contentType;
    private String path;

    @ManyToOne
    @JoinColumn(name = "productId",referencedColumnName = "recId")
    private Product product;

    @OneToOne
    @JoinColumn(name ="categoryId",referencedColumnName ="recId")
    private Category category;


    @OneToOne
    @JoinColumn(name ="sliderId",referencedColumnName ="recId")
    private Slider slider;

}
