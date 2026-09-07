package com.sunbeam.tikito.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.sunbeam.tikito.dto.ImageUploadResponse;
import com.sunbeam.tikito.services.UploadService;
import com.sunbeam.tikito.utils.Resp;

@RestController
@RequestMapping("/tikito/upload")
public class UploadController {

    private final UploadService uploadService;

    public UploadController(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    @PostMapping(
            value = "/poster",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public Resp<?> uploadPoster(
            @RequestParam("file") MultipartFile file)
            throws IOException {

        ImageUploadResponse image =
                uploadService.uploadPoster(file);

        Map<String, String> response = new HashMap<>();

        response.put("posterUrl", image.getUrl());
        response.put("posterPublicId", image.getPublicId());

        return Resp.success(response);
    }

    @PostMapping(
            value = "/profile",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public Resp<?> uploadProfile(
            @RequestParam("file") MultipartFile file)
            throws IOException {

        ImageUploadResponse image =
                uploadService.uploadProfile(file);

        Map<String, String> response = new HashMap<>();

        response.put("profileUrl", image.getUrl());
        response.put("profilePublicId", image.getPublicId());

        return Resp.success(response);
    }
}