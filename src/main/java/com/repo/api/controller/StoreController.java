package com.repo.api.controller;

import com.repo.api.dto.ResponseDto;
import com.repo.api.dto.request.CartRequest;
import com.repo.api.service.CartService;
import com.repo.api.service.CategoryService;
import com.repo.api.service.ProductService;
import com.repo.api.service.SliderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/store")
public class StoreController {

    private final ProductService productService;
    private final CategoryService  categoryService;
    private final SliderService sliderService;
    private final CartService cartService;

    public StoreController(ProductService productService,CategoryService categoryService,
                           SliderService sliderService,CartService cartService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.sliderService = sliderService;
        this.cartService = cartService;
    }


    @GetMapping("/products")
    public ResponseDto<Object> allProducts(){
        return productService.getAllProducts();
    }
    @GetMapping("/category")
    public ResponseDto<Object> productByCategory(String category) {
        return productService.getProductsByCategory(category);
    }

    @GetMapping("/categories")
    public ResponseDto<Object> productParentCategories() {
        return categoryService.getProductParentCategories();
    }

    @GetMapping("/reduced-prices")
    public ResponseDto<Object> reducedPriceProducts() {
        return productService.getReducedPriceProducts();
    }
    @GetMapping("/free-shipping")
    public ResponseDto<Object> freeShippingProducts() {
        return productService.getFreeShippingProducts();
    }
    @GetMapping("/product")
    public ResponseDto<Object> product(Long productId) {
        return productService.getProductById(productId);
    }
    @GetMapping("/last-watched")
    public ResponseDto<Object> lastWatched(String sessionId,Long customerId) {
        return productService.getLastWatchedProducts(sessionId,customerId);
    }
    @GetMapping("/sliders")
    public ResponseDto<Object> getSliderList() {
        return sliderService.getSliderData();
    }


    @GetMapping("/cart")
    public ResponseDto<Object> getCart(Long customerId,String sessionId){
        return cartService.userCartItems(customerId,sessionId);
    }

    @GetMapping("/cart-quantity")
    public Long getCartQuantity(String sessionId,Long customerId){
        return cartService.totalItemsInCart(sessionId,customerId);
    }

    @GetMapping("/product-variations")
    public ResponseDto<Object> attributeList(String productName){
        return productService.getAttributesByProductNameLike(productName);
    }
    @PostMapping("/slider")
    public ResponseDto<Object> addSliderData(String message,String category, MultipartFile[] images){
        return sliderService.saveSliderData(message,category,images);
    }

    @PostMapping("/last-watched")
    public void addToLastWatched(Long productId,String sessionId,Long customerId){
         productService.addToLastWatched(productId,customerId,sessionId);
    }

    @PostMapping("/cart")
    public ResponseDto<Object> addToCart(CartRequest cartRequest){
        return cartService.addToCart(cartRequest);
    }
}
