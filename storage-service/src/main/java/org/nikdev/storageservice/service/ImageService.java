package org.nikdev.storageservice.service;

import org.nikdev.storageservice.dto.ImageOutDto;
import org.nikdev.storageservice.dto.ImageRequestDto;

public interface ImageService {

    ImageOutDto saveImage(ImageRequestDto imageRequestDto) throws Exception;
}
