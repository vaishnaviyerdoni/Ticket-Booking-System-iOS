
package com.sunbeam.tikito.serviceimpl;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sunbeam.tikito.dto.ImageUploadResponse;
import com.sunbeam.tikito.services.UploadService;
import com.sunbeam.tikito.utils.CloudinaryUtil;

@Service
public class UploadServiceImpl implements UploadService {

    private final CloudinaryUtil cloudinaryUtil;

    public UploadServiceImpl(CloudinaryUtil cloudinaryUtil) {
        this.cloudinaryUtil = cloudinaryUtil;
    }

    @Override
    public ImageUploadResponse uploadPoster(MultipartFile file)
            throws IOException {

        return cloudinaryUtil.upload(file, "tikito/posters");
    }

    @Override
    public ImageUploadResponse uploadProfile(MultipartFile file)
            throws IOException {

        return cloudinaryUtil.upload(file, "tikito/profiles");
    }
}