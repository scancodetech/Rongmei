package com.rongmei.blservice.image;

import com.rongmei.exception.SystemException;
import com.rongmei.response.image.ImageResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ImageUploadBlService {

  ImageResponse encodeImageWatermark(MultipartFile multipartFile, String token)
      throws SystemException;

  ImageResponse decodeImageWatermark(MultipartFile multipartFile) throws SystemException;
}
