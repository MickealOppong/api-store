package com.repo.api.service;

import com.repo.api.controller.PhotoController;
import com.repo.api.dto.ResponseDto;
import com.repo.api.impl.PhotoUtilImpl;
import com.repo.api.model.product.Category;
import com.repo.api.model.product.Product;
import com.repo.api.model.utl.Slider;
import com.repo.api.repository.CategoryRepository;
import com.repo.api.repository.PhotoRepository;
import com.repo.api.repository.ProductRepository;
import com.repo.api.util.Photo;
import com.repo.api.util.PhotoStorageLocation;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class PhotoService {

    private final PhotoRepository photoRepository;
    private final PhotoUtilImpl photoUtil;
    private final PhotoStorageLocation photoStorageLocation;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public PhotoService(PhotoRepository photoRepository, PhotoUtilImpl photoUtil,
                        PhotoStorageLocation photoStorageLocation,ProductRepository productRepository,CategoryRepository categoryRepository) {
        this.photoStorageLocation = photoStorageLocation;
        this.photoRepository = photoRepository;
        this.photoUtil = photoUtil;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    /*
     this method saves image metadata to the database and persist the actual image to the local drive folder
     App-photos
     it returns message and status whether successful or not
     */
    public ResponseDto<Object> saveImagesByProduct(MultipartFile[] files, Product product){
        try{
             Arrays.stream(files).forEach(file -> {
                //check the repository whether same record exist
               Photo retrivedPhoto= photoRepository.findByFileName(file.getName()).orElse(null);

               //if record not found in repository, then we proceed to save it or return record already exist
                  if(retrivedPhoto==null) {
                Photo photo = Photo.builder()
                        .contentType(file.getContentType())
                        .fileName(file.getOriginalFilename())
                        .product(product)
                        .path(photoStorageLocation.getLocation() + "/"+product .getRecId()+"-"+ file.getOriginalFilename())
                        .build();
                photoRepository.save(photo);
                photoUtil.store(file, String.valueOf(product.getRecId()));
                 }
            });
            return ResponseDto.builder()
                    .httpStatus(HttpStatus.OK)
                    .message("Image(s) saved successfully")
                    .build();
        }catch (Exception e){
            return ResponseDto.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message(e.getMessage())
                    .build();
        }
    }
    public ResponseDto<Object> saveSliderImages(MultipartFile[] files, Slider slider){
        try{
            Arrays.stream(files).forEach(file -> {
                //check the repository whether same record exist
                Photo retrivedPhoto= photoRepository.findByFileName(file.getName()).orElse(null);

                //if record not found in repository, then we proceed to save it or return record already exist
                if(retrivedPhoto==null) {
                    Photo photo = Photo.builder()
                            .contentType(file.getContentType())
                            .fileName(file.getOriginalFilename())
                            .slider(slider)
                            .path(photoStorageLocation.getLocation() + "/"+slider.getRecId()+"-" + file.getOriginalFilename())
                            .build();
                    photoRepository.save(photo);
                    photoUtil.store(file, String.valueOf(slider.getRecId()));
                }
            });
            return ResponseDto.builder()
                    .httpStatus(HttpStatus.OK)
                    .message("Image(s) saved successfully")
                    .build();
        }catch (Exception e){
            return ResponseDto.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message(e.getMessage())
                    .build();
        }
    }

    public ResponseDto<Object> saveProductImagesById(MultipartFile[] files, Long productId){
        try{
            Product retreivedProduct = productRepository.findById(productId).orElse(null);
            if(retreivedProduct!=null){
                Arrays.stream(files).forEach(file -> {
                    //check the repository whether same record exist
                    Photo retrivedPhoto= photoRepository.findByFileName(file.getName()).orElse(null);

                    //if record not found in repository, then we proceed to save it or return record already exist
                    if(retrivedPhoto==null) {
                        Photo photo = Photo.builder()
                                .contentType(file.getContentType())
                                .fileName(file.getOriginalFilename())
                                .product(retreivedProduct)
                                .path(photoStorageLocation.getLocation() +"/" +productId+"-"+ file.getOriginalFilename())
                                .build();
                        photoRepository.save(photo);
                        photoUtil.store(file, String.valueOf(retreivedProduct.getRecId()));
                    }
                });
                return ResponseDto.builder()
                        .httpStatus(HttpStatus.OK)
                        .message("Image(s) saved successfully")
                        .build();
            }
            return ResponseDto.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message("Product doest not exist")
                    .build();
        }catch (Exception e){
            return ResponseDto.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message(e.getMessage())
                    .build();
        }
    }


    public Resource getResource(String fileName){
        return photoUtil.loadAsResource(fileName);
    }

    public ResponseDto<Object> saveCategoryImage(MultipartFile image, Long categoryId) {
        try{
            Photo retrivedPhoto= photoRepository.findByFileName(image.getName()).orElse(null);
            Category category = categoryRepository.findById(categoryId).orElse(null);
            //if record not found in repository, then we proceed to save it or return record already exist
            if(retrivedPhoto==null & category!=null) {
                Photo photo = Photo.builder()
                        .contentType(image.getContentType())
                        .fileName(image.getOriginalFilename())
                        .category(category)
                        .path(photoStorageLocation.getLocation() + "/" +categoryId+"-"+ image.getOriginalFilename())
                        .build();
                photoRepository.save(photo);
                photoUtil.store(image, String.valueOf(category.getRecId()));
            }
            return ResponseDto.builder()
                    .httpStatus(HttpStatus.OK)
                    .message("Image(s) saved successfully")
                    .build();
        }catch (Exception e){
            return ResponseDto.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message(e.getMessage())
                    .build();
        }
    }

    /*
     Retrieves images metadata using product id from repository and actual image from local directory
     */
    public List<String> getProductImages(Long productId){
        List<String> urls = new ArrayList<>();
        for(Photo image: photoRepository.findByProductRecId(productId)){
            Resource uri = photoUtil.loadAsResource(image.getPath());
            urls.add(MvcUriComponentsBuilder.fromMethodName(PhotoController.class, "serveFile",
                    uri.getFilename()).build().toUri().toString());
        }
        return urls;
    }


    /*
    Retrieves images metadata using product id from repository and actual image from local directory
    */
    public Optional<String> getCategoryImages(Long categoryId){

        Photo photo = photoRepository.findByCategoryRecId(categoryId).orElse(null);
        if(photo != null ){
            Resource uri = photoUtil.loadAsResource(photo.getPath());
           return  Optional.of(MvcUriComponentsBuilder.fromMethodName(PhotoController.class, "serveFile",
                   uri.getFilename()).build().toUri().toString());
        }
        return Optional.empty();
    }

    /*
   Retrieves images metadata using product id from repository and actual image from local directory
   */
    public Optional<String> getSliderImages(Long sliderId){

        Photo photo = photoRepository.findBySliderRecId(sliderId).orElse(null);
        if(photo != null ){
            Resource uri = photoUtil.loadAsResource(photo.getPath());
            return  Optional.of(MvcUriComponentsBuilder.fromMethodName(PhotoController.class, "serveFile",
                    uri.getFilename()).build().toUri().toString());
        }
        return Optional.empty();
    }

}
