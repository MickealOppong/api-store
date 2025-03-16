package com.repo.api.impl;

import com.repo.api.exceptions.PhotoStorageException;
import com.repo.api.interfaces.PhotoUtil;
import com.repo.api.util.PhotoStorageLocation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Slf4j
@Component

public class PhotoUtilImpl implements PhotoUtil {


    private final Path root;

    public PhotoUtilImpl(PhotoStorageLocation photoStorageLocation){

        if(photoStorageLocation.getLocation().trim().length()==0){
            throw new PhotoStorageException("Could not initialize empty folder");
        }
        root = Paths.get(photoStorageLocation.getLocation());

    }

    @Override
    public void init(){
        try{
            if (!Files.exists(root)){
                Files.createDirectories(root);
            }
        }catch (IOException e){
           log.info(e.getMessage());
        }
    }

    @Override
    public void store(MultipartFile file, String name) {
        try{
            if(file.isEmpty()){
                throw new PhotoStorageException("Failed to store empty file");
            }

            Path destination = root.resolve(Paths.get(name+"-"+file.getOriginalFilename())).normalize().toAbsolutePath();
            if(!destination.getParent().equals(this.root.toAbsolutePath())){
                throw new PhotoStorageException("File cannot be stored outside the current directory");
            }
            try(InputStream inputStream = file.getInputStream()){
                Files.copy(inputStream,destination, StandardCopyOption.REPLACE_EXISTING);
            }
        }catch (IOException e){
            throw new PhotoStorageException("Failed to store file");
        }
    }

    @Override
    public Stream<Path> loadAll(String filename){
        try(var children = Files.walk(root,1)) {
            return children.filter(file->!file.equals(root)).map(
                    this.root::relativize);
        } catch (IOException e) {
            throw new PhotoStorageException("Failed to read files",e);
        }

    }


    @Override
    public Path toPath(String filename) {
        return Path.of(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = toPath(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()){
                return resource;
            }else{
                throw new PhotoStorageException("Count not read file "+filename);
            }
        } catch (MalformedURLException e) {
            throw new PhotoStorageException("Count not read file"+filename,e);
        }
    }

    @Override
    public void delete(String file) {
        Path path = toPath(file);
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
