package com.repo.api.service;

import com.repo.api.dto.ResponseDto;
import com.repo.api.dto.response.CategoryDto;
import com.repo.api.model.product.Category;
import com.repo.api.repository.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final PhotoService photoService;

    public CategoryService(CategoryRepository categoryRepository, PhotoService photoService) {
        this.categoryRepository = categoryRepository;
        this.photoService = photoService;
    }


    public ResponseDto<Object> getProductParentCategories(){
        try{
            List<CategoryDto> categoryList = new ArrayList<>();
            for(Category category :categoryRepository.findAll()){
                CategoryDto categoryDto =  CategoryDto.builder()
                        .id(category.getRecId())
                        .parent(category.getParent())
                        .image(photoService.getCategoryImages(category.getRecId()).orElse(null))
                        .build();
                categoryList.add(categoryDto);
            }
            return ResponseDto.builder()
                    .data(categoryList)
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
    public ResponseDto<Object> getProductCategories(){
        try{
            List<CategoryDto> categoryList = new ArrayList<>();
            for(Category category :categoryRepository.findAll()){
                CategoryDto categoryDto =  CategoryDto.builder()
                        .id(category.getRecId())
                        .category(category.getCategory())
                        .image(photoService.getCategoryImages(category.getRecId()).orElse(null))
                        .build();
                categoryList.add(categoryDto);
            }
            return ResponseDto.builder()
                    .data(categoryList)
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
}
