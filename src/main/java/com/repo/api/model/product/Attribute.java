package com.repo.api.model.product;

import com.repo.api.util.LogEntity;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Attribute extends LogEntity {

   @Id
   @GeneratedValue
   private Long id;
   private String attribute;
   private String value;


}
