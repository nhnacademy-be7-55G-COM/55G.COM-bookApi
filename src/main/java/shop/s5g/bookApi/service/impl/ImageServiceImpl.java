package shop.s5g.bookApi.service.impl;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import shop.s5g.bookApi.adapter.ImageProviderAdapter;
import shop.s5g.bookApi.dto.ImageResponseDto;
import shop.s5g.bookApi.service.ImageService;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageProviderAdapter imageProviderAdapter;

    @Override
    public ImageResponseDto uploadImage(String path, byte[] image) {
        log.debug("Uploading image: path={}", path);
        return mapResponseToDto(imageProviderAdapter.uploadImage(path, image));
    }

    private ImageResponseDto mapResponseToDto(Map<String, Object> response) {
        @SuppressWarnings("unchecked")
        Map<String, Object> file = (Map<String, Object>) response.get("file");

        String name = (String) file.get("name");
        return new ImageResponseDto(name);
    }
}