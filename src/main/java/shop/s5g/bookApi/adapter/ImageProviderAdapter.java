package shop.s5g.bookApi.adapter;

import feign.Headers;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import shop.s5g.bookApi.config.FeignImageProviderAuthorizationConfig;

@FeignClient(
    name = "imageProvider",
    url = "${image.url}/${image.appkey}",
    configuration = FeignImageProviderAuthorizationConfig.class
)
public interface ImageProviderAdapter {
    @PutMapping(
        value = "/images?overwrite=true",
        consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    @Headers({"Content-Type: application/octet-stream"})
    Map<String, Object> uploadImage(@RequestParam("path") String path, @RequestBody byte[] image);
}
