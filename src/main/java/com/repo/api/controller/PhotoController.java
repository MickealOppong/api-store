package com.repo.api.controller;

import com.repo.api.dto.ResponseDto;
import com.repo.api.service.PhotoService;
import com.repo.api.util.PhotoStorageLocation;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.zip.DataFormatException;

@RestController
@RequestMapping("/photos")
public class PhotoController {


    private final PhotoService photoService;
    private final PhotoStorageLocation photoStorageLocation;

    public PhotoController(PhotoService photoService,PhotoStorageLocation photoStorageLocation) {
        this.photoService = photoService;
        this.photoStorageLocation = photoStorageLocation;
    }

    @GetMapping("/{filename:..+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) throws DataFormatException {
        Resource image = photoService.getResource(photoStorageLocation.getLocation()+"/"+filename);
        if(image == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment;filename=\""+image.getFilename()+"\"").body(image);
    }

    @PostMapping("/product")
    public ResponseDto<Object> addProductPhoto(MultipartFile[] images,Long productId){
       return photoService.saveProductImagesById(images,productId);
    }
    @PostMapping("/category")
    public ResponseDto<Object> addCategoryPhoto(MultipartFile image,Long categoryId){
        return photoService.saveCategoryImage(image,categoryId);
    }
}
