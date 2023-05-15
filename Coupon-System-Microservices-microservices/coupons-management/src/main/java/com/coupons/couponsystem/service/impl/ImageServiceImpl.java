package com.coupons.couponsystem.service.impl;

import com.coupons.couponsystem.exception.CouponSystemException;
import com.coupons.couponsystem.model.ImageData;
import com.coupons.couponsystem.repositoty.ImageRepository;
import com.coupons.couponsystem.utilities.imageUtils.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;


@Service
public class ImageServiceImpl {

    @Autowired
    private ImageRepository imageRepository;

    public ImageData uploadImage(MultipartFile file) throws CouponSystemException {
        System.out.println(file.getOriginalFilename());
        try{
          return  imageRepository.save(ImageData.builder()
                    .name(file.getOriginalFilename())
                    .type(file.getContentType())
                    .imageData(ImageUtils.compressImage(file.getBytes())).build());
//            if(imageData==null)
//            {
//                return null;
//            }

        }catch (IOException e)
        {
            throw new CouponSystemException("something wrong with the file provided for image", HttpStatus.BAD_REQUEST);

        }

    }

    public byte[] downloadImage(String fileName){
        Optional<ImageData> dbImageData = imageRepository.findByName(fileName);
        byte[] images= ImageUtils.decompressImage(dbImageData.get().getImageData());
        return images;
    }
}