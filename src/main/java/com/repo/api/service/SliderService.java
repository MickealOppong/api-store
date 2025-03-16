package com.repo.api.service;

import com.repo.api.dto.ResponseDto;
import com.repo.api.dto.response.SliderDto;
import com.repo.api.model.utl.Slider;
import com.repo.api.repository.SliderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class SliderService {



    private final SliderRepository sliderRepository;
    private final PhotoService photoService;

    public SliderService(SliderRepository sliderRepository, PhotoService photoService) {
        this.sliderRepository = sliderRepository;
        this.photoService = photoService;
    }

    public ResponseDto<Object> saveSliderData(String message,String category,MultipartFile[] images){
      try{
          Slider retreivedSlider= sliderRepository.findByMessage(message).orElse(null);
          if(retreivedSlider==null) {
              Slider sliderData = Slider.builder()
                      .message(message)
                      .category(category)
                      .build();
             Slider savedSlider= sliderRepository.save(sliderData);
              photoService.saveSliderImages(images,savedSlider);
          }else{
              retreivedSlider.setMessage(message);
              sliderRepository.save(retreivedSlider);
              photoService.saveSliderImages(images,retreivedSlider);
          }
          return ResponseDto.builder()
                  .data(null)
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


    public ResponseDto<Object> getSliderData(){
        try {
            List<SliderDto> sliderDtoList = new ArrayList<>();
            for(Slider slider :sliderRepository.findAll()){
                SliderDto sliderDto = SliderDto.builder()
                        .id(slider.getRecId())
                        .message(slider.getMessage())
                        .category(slider.getCategory())
                        .image(photoService.getSliderImages(slider.getRecId()).orElse(null))
                        .build();
                sliderDtoList.add(sliderDto);
            }
            return ResponseDto.builder()
                    .data(sliderDtoList)
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
}
