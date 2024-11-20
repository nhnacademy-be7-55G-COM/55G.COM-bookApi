package shop.s5g.bookApi.service;

import shop.s5g.bookApi.dto.ImageResponseDto;

public interface ImageService {
    ImageResponseDto uploadImage(String path, byte[] image);
}
