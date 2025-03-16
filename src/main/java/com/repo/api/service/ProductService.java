package com.repo.api.service;

import com.repo.api.dto.ResponseDto;
import com.repo.api.dto.response.*;
import com.repo.api.model.product.Category;
import com.repo.api.model.product.Parameter;
import com.repo.api.model.product.Product;
import com.repo.api.model.product.ProductAttribute;
import com.repo.api.model.user.Customer;
import com.repo.api.model.user.LastWatched;
import com.repo.api.repository.LastWatchedRepository;
import com.repo.api.repository.ParameterRepository;
import com.repo.api.repository.ProductAttributeRepository;
import com.repo.api.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Slf4j
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final PhotoService photoService;
    private final ProductAttributeRepository attributeRepository;
    private final ParameterRepository parameterRepository;
    private final CustomerDetailsService customerDetailsService;
    private final LastWatchedRepository lastWatchedRepository;

    public ProductService(ProductRepository productRepository, PhotoService photoService, ProductAttributeRepository attributeRepository,
                          ParameterRepository parameterRepository,
                          CustomerDetailsService customerDetailsService,LastWatchedRepository lastWatchedRepository) {
        this.productRepository = productRepository;
        this.photoService = photoService;
        this.attributeRepository = attributeRepository;
        this.parameterRepository = parameterRepository;
        this.customerDetailsService = customerDetailsService;
        this.lastWatchedRepository = lastWatchedRepository;
    }

    public ResponseDto<Object> saveWithImage(Product product, MultipartFile[] images){
        Product retreivedProduct= productRepository.findByProductName(product.getProductName())
                .orElse(null);
        if(retreivedProduct==null){
            productRepository.save(product);
            photoService.saveImagesByProduct(images,product);
            return ResponseDto.builder()
                    .message("Product saved")
                    .httpStatus(HttpStatus.OK)
                    .build();
        }
        return ResponseDto.builder()
                .message("Product already exist")
                .httpStatus(HttpStatus.BAD_REQUEST)
                .build();
    }
    public ResponseDto<Object> save(Product product){
        Product retreivedProduct= productRepository.findByProductName(product.getProductName())
                .orElse(null);
        if(retreivedProduct==null){
            productRepository.save(product);
            return ResponseDto.builder()
                    .message("Product saved")
                    .httpStatus(HttpStatus.OK)
                    .build();
        }
        return ResponseDto.builder()
                .message("Product already exist")
                .httpStatus(HttpStatus.BAD_REQUEST)
                .build();
    }

    public void addToLastWatched(Long productId,Long customerId,String sessionId){
        Customer customer = customerDetailsService.getCustomerById(customerId);
        LastWatched retrievedItem = lastWatchedRepository.findByProductId(productId).orElse(null);

               if(retrievedItem!=null && !retrievedItem.getSessionId().equals(sessionId)) {
                   LastWatched lastWatched = LastWatched.builder()
                           .productId(productId)
                           .sessionId(sessionId)
                           .customer(customer)
                           .build();
                   lastWatchedRepository.save(lastWatched);
               }else{
                   LastWatched lastWatched = LastWatched.builder()
                           .productId(productId)
                           .sessionId(sessionId)
                           .customer(customer)
                           .build();
                   lastWatchedRepository.save(lastWatched);
               }

    }


    public ResponseDto<Object> getProductById(Long id){
        Product product = productRepository.findById(id).orElse(null);
        if(product!=null){

            List<CategoryDto> categoryList = new ArrayList<>();
            Set<AttributeDto> attributeList = new HashSet<>();
            List<ParameterDto> parameterList = new ArrayList<>();

            //product category dto
            for(Category category :product.getCategoryList()){
                CategoryDto categoryDto = CategoryDto.builder()
                        .category(category.getCategory())
                        .parent(category.getParent())
                        .id(category.getRecId())
                        .build();
                categoryList.add(categoryDto);
            }

            //product attribute dto
            for(ProductAttribute attribute:attributeRepository.findByProductRecId(product.getRecId())){
                AttributeDto productAttribute= AttributeDto.builder()
                        .id(attribute.getAttributeId())
                        .attribute(attribute.getAttribute())
                        .value(attribute.getValue())
                        .build();
                attributeList.add(productAttribute);
            }
            //product attribute dto
            for(ProductAttribute attribute :attributeRepository.findByProductRecId(product.getRecId())){
                AttributeDto attributeDto = AttributeDto.builder()
                        .attribute(attribute.getAttribute())
                        .value(attribute.getValue())
                        .id(attribute.getAttributeId())
                        .build();
                attributeList.add(attributeDto);
            }


            //product parameter dto
            for(Parameter parameter :product.getParameterList()){
                ParameterDto parameterDto = ParameterDto.builder()
                        .id(parameter.getId())
                        .parameter(parameter.getParameter())
                        .value(parameter.getValue())
                        .build();
                parameterList.add(parameterDto);
            }

            //product images
            List<String> images =photoService.getProductImages(product.getRecId());

            //product dto
            ProductDto productDto = ProductDto.builder()
                    .productId(product.getRecId())
                    .productName(product.getProductName())
                    .productImages(images)
                    .productDescription(product.getProductDescription())
                    .price(product.getPrice())
                    .generalInfo1(product.getGeneralInfo1())
                    .generalInfo2(product.getGeneralInfo2())
                    .generalInfo3(product.getGeneralInfo3())
                    .generalInfo4(product.getGeneralInfo4())
                    .isFreeShipping(product.isFreeShipping())
                    .reducedPrice(product.getReducedPrice())
                    .categoryList(categoryList)
                    .parameterList(parameterList)
                    .attributeList(attributeList)
                    .productImages( photoService.getProductImages(product.getRecId()))
                    .build();
            return ResponseDto.builder()
                    .data(productDto)
                    .httpStatus(HttpStatus.OK)
                    .message("Success")
                    .build();
        }

        return ResponseDto.builder()
                .data(null)
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message("Record does not exist")
                .build();
    }

    public Product getProduct(Long id){
        return productRepository.findById(id).orElse(null);
    }




    public ResponseDto<Object> getProductByName(String productName){
        Product product = productRepository.findByProductName(productName).orElse(null);
        if(product!=null){
            List<String> images =photoService.getProductImages(product.getRecId());
            ProductDto productDto = ProductDto.builder()
                    .productId(product.getRecId())
                    .productName(product.getProductName())
                    .productImages(images)
                    .productDescription(product.getProductDescription())
                    .price(product.getPrice())
                    .isFreeShipping(product.isFreeShipping())
                    .reducedPrice(product.getReducedPrice())
                    .build();
            return ResponseDto.builder()
                    .data(productDto)
                    .httpStatus(HttpStatus.OK)
                    .message("Success")
                    .build();
        }

        return ResponseDto.builder()
                .data(null)
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message("Record does not exist")
                .build();
    }

    public ResponseDto<Object> getReducedPriceProducts(){
    try {
        List<ProductDto> productList = new ArrayList<>();
        for(Product  product:productRepository.findAll().stream().filter(product->product.getPrice()>product.getReducedPrice()).limit(20).toList()){
            List<String> images =photoService.getProductImages(product.getRecId());
            ProductDto productDto = ProductDto.builder()
                    .productId(product.getRecId())
                    .productName(product.getProductName())
                    .productImages(images)
                    .productDescription(product.getProductDescription())
                    .price(product.getPrice())
                    .isFreeShipping(product.isFreeShipping())
                    .reducedPrice(product.getReducedPrice())
                    .build();
           productList.add(productDto);
        }
        return ResponseDto.builder()
                .data(productList)
                .httpStatus(HttpStatus.OK)
                .message("Success")
                .build();
    }catch (Exception e){
        return ResponseDto.builder()
                .data(null)
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message(e.getMessage())
                .build();
    }


    }




    public ResponseDto<Object> getNewArrivals(){
        Random random = new Random();
         try{
             return ResponseDto.builder()
                .data(  productRepository.findAll().stream().filter(Product::isNewArrival).limit(random.nextInt()* 20L).toList())
                .message("Success")
                .httpStatus(HttpStatus.OK)
                .build();
         }catch (Exception e){
             return ResponseDto.builder()
                     .data(null)
                     .message(e.getMessage())
                     .httpStatus(HttpStatus.OK)
                     .build();
         }
    }

    public ResponseDto<Object> getLastWatchedProducts(String sessionId,Long customerId){

        Set<ProductDto> lastWatchedList = new HashSet<>();
        try{

            for(LastWatched lastWatched:lastWatchedRepository.findAllBySessionId(sessionId)){
                Product product=  productRepository.findById(lastWatched.getProductId()).orElse(null);
                if(product!=null){
                    List<String> images =photoService.getProductImages(product.getRecId());
                    ProductDto productDto = ProductDto.builder()
                            .productId(product.getRecId())
                            .productName(product.getProductName())
                            .productImages(images)
                            .productDescription(product.getProductDescription())
                            .price(product.getPrice())
                            .isFreeShipping(product.isFreeShipping())
                            .reducedPrice(product.getReducedPrice())
                            .build();
                    lastWatchedList.add(productDto);
                }

            }
            for(LastWatched lastWatched:lastWatchedRepository.findAllByCustomerId(customerId)){
                Product product=  productRepository.findById(lastWatched.getProductId()).orElse(null);
                if(product!=null){
                    List<String> images =photoService.getProductImages(product.getRecId());
                    ProductDto productDto = ProductDto.builder()
                            .productId(product.getRecId())
                            .productName(product.getProductName())
                            .productImages(images)
                            .productDescription(product.getProductDescription())
                            .price(product.getPrice())
                            .isFreeShipping(product.isFreeShipping())
                            .reducedPrice(product.getReducedPrice())
                            .build();
                    lastWatchedList.add(productDto);
                }

            }
            return ResponseDto.builder()
                    .data(lastWatchedList)
                    .message("Success")
                    .httpStatus(HttpStatus.OK)
                    .build();

        }catch (Exception e){
            return ResponseDto.builder()
                    .data(null)
                    .message(e.getMessage())
                    .httpStatus(HttpStatus.OK)
                    .build();
        }
    }
    public ResponseDto<Object> getOnSaleProducts(){
        Random random = new Random();
        try{
            return ResponseDto.builder()
                    .data(  productRepository.findAll().stream().filter(Product::isOnSale).limit(random.nextInt()* 20L).toList())
                    .message("Success")
                    .httpStatus(HttpStatus.OK)
                    .build();
        }catch (Exception e){
            return ResponseDto.builder()
                    .data(null)
                    .message(e.getMessage())
                    .httpStatus(HttpStatus.OK)
                    .build();
        }
    }

    public ResponseDto<Object> getLowPriceProducts(){
        Random random = new Random();
        try{
            return ResponseDto.builder()
                    .data(  productRepository.findAll().stream().filter(product ->product.getPrice()<150).limit(random.nextInt()* 20L).toList())
                    .message("Success")
                    .httpStatus(HttpStatus.OK)
                    .build();
        }catch (Exception e){
            return ResponseDto.builder()
                    .data(null)
                    .message(e.getMessage())
                    .httpStatus(HttpStatus.OK)
                    .build();
        }
    }
    public ResponseDto<Object> getFreeShippingProducts(){
        Random random = new Random();
        try{
            return ResponseDto.builder()
                    .data(  productRepository.findAll().stream().filter(Product::isFreeShipping).limit(random.nextInt()* 20L).toList())
                    .message("Success")
                    .httpStatus(HttpStatus.OK)
                    .build();
        }catch (Exception e){
            return ResponseDto.builder()
                    .data(null)
                    .message(e.getMessage())
                    .httpStatus(HttpStatus.OK)
                    .build();
        }
    }
    public ResponseDto<Object> getProductsBySearchName(String searchName){
        Random random = new Random();
        try{
            return ResponseDto.builder()
                    .data(  productRepository.findAll().stream().filter(product -> product.getProductName().contains(searchName)).limit(random.nextInt()* 20L).toList())
                    .message("Success")
                    .httpStatus(HttpStatus.OK)
                    .build();
        }catch (Exception e){
            return ResponseDto.builder()
                    .data(null)
                    .message(e.getMessage())
                    .httpStatus(HttpStatus.OK)
                    .build();
        }
    }
    public ResponseDto<Object> getAllProducts(){
        try{
            List<ProductDto> productDtoList = new ArrayList<>();

            for(Product product:productRepository.findAll()){

               ProductDto productDto = ProductDto.builder()
                       .productImages( photoService.getProductImages(product.getRecId()))
                       .productId(product.getRecId())
                       .productName(product.getProductName())
                       .price(product.getPrice())
                       .productDescription(product.getProductDescription())
                       .searchName(product.getSearchName())
                       .build();
               productDtoList.add(productDto);
            }
            return ResponseDto.builder()
                    .data( productDtoList)
                    .message("Success")
                    .httpStatus(HttpStatus.OK)
                    .build();
        }catch (Exception e){
            return ResponseDto.builder()
                    .data(null)
                    .message(e.getMessage())
                    .httpStatus(HttpStatus.OK)
                    .build();
        }
    }

    public ResponseDto<Object> getProductsByCategory(String category){
        try{
            List<ProductDto> productDtoList = new ArrayList<>();

            for(Product product:productRepository.findByCategory(category)){

                ProductDto productDto = ProductDto.builder()
                        .productImages( photoService.getProductImages(product.getRecId()))
                        .productId(product.getRecId())
                        .productName(product.getProductName())
                        .price(product.getPrice())
                        .productDescription(product.getProductDescription())
                        .searchName(product.getSearchName())
                        .build();
                productDtoList.add(productDto);
            }
            return ResponseDto.builder()
                    .data( productDtoList)
                    .message("Success")
                    .httpStatus(HttpStatus.OK)
                    .build();
        }catch (Exception e){
            return ResponseDto.builder()
                    .data(null)
                    .message(e.getMessage())
                    .httpStatus(HttpStatus.OK)
                    .build();
        }
    }

    public   ResponseDto<Object> getAttributesByProductNameLike(String name){
        try {
            Set<AttributeListDto> attributeList =new HashSet<>();

            for(ProductAttribute productAttribute :attributeRepository.findByProductNameLike(name)){
                AttributeListDto attributeDTO = AttributeListDto.builder()
                        .name(productAttribute.getAttribute())
                        .attributes(attributeRepository.findByProductNameLike(name)
                                .stream().filter(f->f.getAttribute().equals(productAttribute.getAttribute()))
                                .map(AttributeDto::new).toList())
                        .build();
                attributeList.add(attributeDTO);
            }
            return ResponseDto.builder()
                    .data(attributeList)
                    .message("Success")
                    .httpStatus(HttpStatus.OK)
                    .build();
        }catch (Exception e){
            return ResponseDto.builder()
                    .data(null)
                    .message(e.getMessage())
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }

}
