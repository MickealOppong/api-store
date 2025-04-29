package com.repo.api.config;

import com.repo.api.impl.AuditAwareImpl;
import com.repo.api.model.product.*;
import com.repo.api.model.user.Roles;
import com.repo.api.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditAware")
public class JpaConfig {


    private final RolesRepository rolesRepository;
    private final CategoryRepository categoryRepository;
    private final AttributeRepository attributeRepository;
    private final ProductRepository productRepository;
    private final ParameterRepository parameterRepository;
    private final ProductAttributeRepository productAttributeRepository;

    public JpaConfig(RolesRepository rolesRepository, CategoryRepository categoryRepository,
                     AttributeRepository attributeRepository, ProductRepository productRepository,
                     ParameterRepository parameterRepository,
                     ProductAttributeRepository productAttributeRepository) {
        this.rolesRepository = rolesRepository;
        this.categoryRepository = categoryRepository;
        this.attributeRepository = attributeRepository;
        this.productRepository = productRepository;
        this.parameterRepository = parameterRepository;
        this.productAttributeRepository = productAttributeRepository;
    }

    @Bean
    public AuditorAware<String> auditAware(){
        return  new AuditAwareImpl();
    }

    @Bean
    public CommandLineRunner load(){
        return args -> {
            Roles role = new Roles("USER");
            rolesRepository.save(role);

            Category category1 = Category.builder()
                    .category("Clothing")
                    .parent("Clothing")
                    .build();
            Category category2 = Category.builder()
                    .category("Children")
                    .parent("Children")
                    .build();
            Category category3 = Category.builder()
                    .category("Furniture")
                    .parent("Furniture")
                    .build();
            categoryRepository.saveAll(List.of(category1,category2,category3));

            Attribute attr1= Attribute.builder()
                    .attribute("Color")
                    .value("#EEE0CB")
                    .build();
            Attribute attr2= Attribute.builder()
                    .attribute("Color")
                    .value("#C2847A")
                    .build();

            Attribute attr3= Attribute.builder()
                    .attribute("Size")
                    .value("S")
                    .build();
            Attribute attr4= Attribute.builder()
                    .attribute("Size")
                    .value("L")
                    .build();
            attributeRepository.saveAll(List.of(attr1,attr2,attr3,attr4));

            Parameter parameter1 = Parameter.builder()
                    .parameter("Weight")
                    .value("200 kg")
                    .build();

            Parameter parameter2 = Parameter.builder()
                    .parameter("Height")
                    .value("200 cm")
                    .build();


            Parameter parameter3 = Parameter.builder()
                    .parameter("Color")
                    .value("Light red")
                    .build();
            Parameter parameter4 = Parameter.builder()
                    .parameter("Product code")
                    .value("3049049849002")
                    .build();
            Parameter parameter5 = Parameter.builder()
                    .parameter("State")
                    .value("New")
                    .build();

            parameterRepository.saveAll(List.of(parameter1,parameter2,parameter3,parameter4,parameter5));



            Product product1 = Product.builder()
                    .price(102.99)
                    .productDescription("Shoe small")
                    .productName("Za Shoe")
                    .reducedPrice(90)
                    .shippingCost(9.99)
                    .generalInfo1("Lorem ipsum dolor, sit amet consectetur adipisicing elit. Aliquid saepe neque doloremque pariatur eum excepturi, culpa velit totam maxime dicta delectus explicabo quisquam" +
                            " ratione dolorum aperiam corporis nam praesentium consequatur quidem distinctio modi vero dolor beatae quaerat. Quis, ipsum vitae.")
                    .generalInfo2("Lorem ipsum dolor, sit amet consectetur adipisicing elit. Aliquid saepe neque doloremque pariatur eum excepturi, culpa velit totam maxime dicta delectus explicabo quisquam" +
                            " ratione dolorum aperiam corporis nam praesentium consequatur quidem distinctio modi vero dolor beatae quaerat. Quis, ipsum vitae.")
                    .generalInfo3("Lorem ipsum dolor, sit amet consectetur adipisicing elit. Aliquid saepe neque doloremque pariatur eum excepturi, culpa velit totam maxime dicta delectus explicabo quisquam" +
                            " ratione dolorum aperiam corporis nam praesentium consequatur quidem distinctio modi vero dolor beatae quaerat. Quis, ipsum vitae.")
                    .searchName("za")
                    .categoryList(List.of(category1))
                    .parameterList(List.of(parameter1,parameter2,parameter3,parameter4,parameter5))
                    .build();
            Product product2 = Product.builder()
                    .price(999)
                    .reducedPrice(99)
                    .shippingCost(9.99)
                    .productDescription("Travel charger")
                    .productName("Charger")
                    .generalInfo1("Lorem ipsum dolor, sit amet consectetur adipisicing elit. Aliquid saepe neque doloremque pariatur eum excepturi, culpa velit totam maxime dicta delectus explicabo quisquam" +
                            " ratione dolorum aperiam corporis nam praesentium consequatur quidem distinctio modi vero dolor beatae quaerat. Quis, ipsum vitae.")
                    .generalInfo2("Lorem ipsum dolor, sit amet consectetur adipisicing elit. Aliquid saepe neque doloremque pariatur eum excepturi, culpa velit totam maxime dicta delectus explicabo quisquam" +
                            " ratione dolorum aperiam corporis nam praesentium consequatur quidem distinctio modi vero dolor beatae quaerat. Quis, ipsum vitae.")
                    .generalInfo3("Lorem ipsum dolor, sit amet consectetur adipisicing elit. Aliquid saepe neque doloremque pariatur eum excepturi, culpa velit totam maxime dicta delectus explicabo quisquam" +
                            " ratione dolorum aperiam corporis nam praesentium consequatur quidem distinctio modi vero dolor beatae quaerat. Quis, ipsum vitae.")
                    .searchName("charger")
                    .categoryList(List.of(category2,category1))
                    .parameterList(List.of(parameter1,parameter2,parameter3,parameter4,parameter5))
                    .build();
            Product product7 = Product.builder()
                    .price(99)
                    .reducedPrice(190)
                    .shippingCost(9.99)
                    .productDescription("Pen")
                    .productName("Bic pen")
                    .generalInfo1("Lorem ipsum dolor, sit amet consectetur adipisicing elit. Aliquid saepe neque doloremque pariatur eum excepturi, culpa velit totam maxime dicta delectus explicabo quisquam" +
                            " ratione dolorum aperiam corporis nam praesentium consequatur quidem distinctio modi vero dolor beatae quaerat. Quis, ipsum vitae.")
                    .generalInfo2("Lorem ipsum dolor, sit amet consectetur adipisicing elit. Aliquid saepe neque doloremque pariatur eum excepturi, culpa velit totam maxime dicta delectus explicabo quisquam" +
                            " ratione dolorum aperiam corporis nam praesentium consequatur quidem distinctio modi vero dolor beatae quaerat. Quis, ipsum vitae.")
                    .generalInfo3("Lorem ipsum dolor, sit amet consectetur adipisicing elit. Aliquid saepe neque doloremque pariatur eum excepturi, culpa velit totam maxime dicta delectus explicabo quisquam" +
                            " ratione dolorum aperiam corporis nam praesentium consequatur quidem distinctio modi vero dolor beatae quaerat. Quis, ipsum vitae.")
                    .searchName("charger")
                    .categoryList(List.of(category2,category1))
                    .parameterList(List.of(parameter1,parameter2,parameter3,parameter4,parameter5))
                    .build();
            Product product3 = Product.builder()
                    .price(300)
                    .reducedPrice(349)
                    .shippingCost(9.99)
                    .productDescription("Travel charger")
                    .productName("Charger")
                    .generalInfo1("Lorem ipsum dolor, sit amet consectetur adipisicing elit. Aliquid saepe neque doloremque pariatur eum excepturi, culpa velit totam maxime dicta delectus explicabo quisquam" +
                            " ratione dolorum aperiam corporis nam praesentium consequatur quidem distinctio modi vero dolor beatae quaerat. Quis, ipsum vitae.")
                    .generalInfo2("Lorem ipsum dolor, sit amet consectetur adipisicing elit. Aliquid saepe neque doloremque pariatur eum excepturi, culpa velit totam maxime dicta delectus explicabo quisquam" +
                            " ratione dolorum aperiam corporis nam praesentium consequatur quidem distinctio modi vero dolor beatae quaerat. Quis, ipsum vitae.")
                    .generalInfo3("Lorem ipsum dolor, sit amet consectetur adipisicing elit. Aliquid saepe neque doloremque pariatur eum excepturi, culpa velit totam maxime dicta delectus explicabo quisquam" +
                            " ratione dolorum aperiam corporis nam praesentium consequatur quidem distinctio modi vero dolor beatae quaerat. Quis, ipsum vitae.")
                    .searchName("charger")
                    .categoryList(List.of(category2,category1))
                    .parameterList(List.of(parameter1,parameter2,parameter3,parameter4,parameter5))
                    .build();
            Product product4 = Product.builder()
                    .price(340)
                    .reducedPrice(230)
                    .shippingCost(0.00)
                    .productDescription("Iphone xs max")
                    .productName("Iphone")
                    .generalInfo1("Lorem ipsum dolor, sit amet consectetur adipisicing elit. Aliquid saepe neque doloremque pariatur eum excepturi, culpa velit totam maxime dicta delectus explicabo quisquam" +
                            " ratione dolorum aperiam corporis nam praesentium consequatur quidem distinctio modi vero dolor beatae quaerat. Quis, ipsum vitae.")
                    .generalInfo2("Lorem ipsum dolor, sit amet consectetur adipisicing elit. Aliquid saepe neque doloremque pariatur eum excepturi, culpa velit totam maxime dicta delectus explicabo quisquam" +
                            " ratione dolorum aperiam corporis nam praesentium consequatur quidem distinctio modi vero dolor beatae quaerat. Quis, ipsum vitae.")
                    .generalInfo3("Lorem ipsum dolor, sit amet consectetur adipisicing elit. Aliquid saepe neque doloremque pariatur eum excepturi, culpa velit totam maxime dicta delectus explicabo quisquam" +
                            " ratione dolorum aperiam corporis nam praesentium consequatur quidem distinctio modi vero dolor beatae quaerat. Quis, ipsum vitae.")
                    .searchName("charger")
                    .categoryList(List.of(category2,category1))
                    .parameterList(List.of(parameter1,parameter2,parameter3,parameter4,parameter5))
                    .build();
            Product product5 = Product.builder()
                    .price(89)
                    .reducedPrice(29)
                    .shippingCost(0.00)
                    .productDescription("Smart tv")
                    .productName("Smart tv")
                    .generalInfo1("Lorem ipsum dolor, sit amet consectetur adipisicing elit. Aliquid saepe neque doloremque pariatur eum excepturi, culpa velit totam maxime dicta delectus explicabo quisquam" +
                            " ratione dolorum aperiam corporis nam praesentium consequatur quidem distinctio modi vero dolor beatae quaerat. Quis, ipsum vitae.")
                    .generalInfo2("Lorem ipsum dolor, sit amet consectetur adipisicing elit. Aliquid saepe neque doloremque pariatur eum excepturi, culpa velit totam maxime dicta delectus explicabo quisquam" +
                            " ratione dolorum aperiam corporis nam praesentium consequatur quidem distinctio modi vero dolor beatae quaerat. Quis, ipsum vitae.")
                    .generalInfo3("Lorem ipsum dolor, sit amet consectetur adipisicing elit. Aliquid saepe neque doloremque pariatur eum excepturi, culpa velit totam maxime dicta delectus explicabo quisquam" +
                            " ratione dolorum aperiam corporis nam praesentium consequatur quidem distinctio modi vero dolor beatae quaerat. Quis, ipsum vitae.")
                    .searchName("tv")
                    .categoryList(List.of(category2,category1))
                    .parameterList(List.of(parameter1,parameter2,parameter3,parameter4,parameter5))
                    .build();
            Product product6 = Product.builder()
                    .price(30)
                    .reducedPrice(99)
                    .shippingCost(0.00)
                    .productDescription("Chair")
                    .productName("Chair")
                    .generalInfo1("Lorem ipsum dolor, sit amet consectetur adipisicing elit. Aliquid saepe neque doloremque pariatur eum excepturi, culpa velit totam maxime dicta delectus explicabo quisquam" +
                            " ratione dolorum aperiam corporis nam praesentium consequatur quidem distinctio modi vero dolor beatae quaerat. Quis, ipsum vitae.")
                    .generalInfo2("Lorem ipsum dolor, sit amet consectetur adipisicing elit. Aliquid saepe neque doloremque pariatur eum excepturi, culpa velit totam maxime dicta delectus explicabo quisquam" +
                            " ratione dolorum aperiam corporis nam praesentium consequatur quidem distinctio modi vero dolor beatae quaerat. Quis, ipsum vitae.")
                    .generalInfo3("Lorem ipsum dolor, sit amet consectetur adipisicing elit. Aliquid saepe neque doloremque pariatur eum excepturi, culpa velit totam maxime dicta delectus explicabo quisquam" +
                            " ratione dolorum aperiam corporis nam praesentium consequatur quidem distinctio modi vero dolor beatae quaerat. Quis, ipsum vitae.")
                    .searchName("chair")
                    .categoryList(List.of(category2,category1))
                    .parameterList(List.of(parameter1,parameter2,parameter3,parameter4,parameter5))
                    .build();
            productRepository.saveAll(List.of(product1,product2,product3,product4,product5,product6,product7));

            ProductAttribute pa1 = ProductAttribute.builder()
                    .attribute(attr1.getAttribute())
                    .value(attr1.getValue())
                    .product(product3)
                    .build();
            ProductAttribute pa2 = ProductAttribute.builder()
                    .attribute(attr3.getAttribute())
                    .value(attr3.getValue())
                    .product(product3)
                    .build();
            ProductAttribute pa3 = ProductAttribute.builder()
                    .attribute(attr2.getAttribute())
                    .value(attr2.getValue())
                    .product(product2)
                    .build();

            ProductAttribute pa4 = ProductAttribute.builder()
                    .attribute(attr4.getAttribute())
                    .value(attr4.getValue())
                    .product(product2)
                    .build();
            productAttributeRepository.saveAll(List.of(pa1,pa2,pa3));
        };
    }
}