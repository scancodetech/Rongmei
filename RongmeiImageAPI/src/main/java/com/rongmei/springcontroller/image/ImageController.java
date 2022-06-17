package com.rongmei.springcontroller.image;

import com.rongmei.blservice.image.ImageUploadBlService;
import com.rongmei.exception.SystemException;
import com.rongmei.response.image.ImageResponse;
import io.swagger.annotations.*;
import com.rongmei.response.Response;
import com.rongmei.response.WrongResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "image")
public class ImageController {

  private final ImageUploadBlService imageUploadBlService;

  @Autowired
  public ImageController(ImageUploadBlService imageUploadBlService) {
    this.imageUploadBlService = imageUploadBlService;
  }

  @ApiOperation(value = "给图片加隐藏水印", notes = "给图片加隐藏水印")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "multipartFile", value = "图片", required = true, dataType = "MultipartFile")
  })
  @RequestMapping(value = "/watermark/encode", method = RequestMethod.POST)
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = "Image uploaded", response = ImageResponse.class),
      @ApiResponse(code = 403, message = "Upload session timed out", response = WrongResponse.class),
      @ApiResponse(code = 404, message = "Upload session id not exist", response = WrongResponse.class),
      @ApiResponse(code = 503, message = "Failure", response = WrongResponse.class)
  })
  public ResponseEntity<Response> encodeImageWatermark(
      @RequestParam("file") MultipartFile multipartFile, @RequestParam("token") String token) {
    try {
      return new ResponseEntity<>(imageUploadBlService.encodeImageWatermark(multipartFile, token),
          HttpStatus.CREATED);
    } catch (SystemException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @ApiOperation(value = "给图片解隐藏水印", notes = "给图片解隐藏水印")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "multipartFile", value = "图片", required = true, dataType = "MultipartFile")
  })
  @RequestMapping(value = "/watermark/decode", method = RequestMethod.POST)
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = "Image uploaded", response = ImageResponse.class),
      @ApiResponse(code = 403, message = "Upload session timed out", response = WrongResponse.class),
      @ApiResponse(code = 404, message = "Upload session id not exist", response = WrongResponse.class),
      @ApiResponse(code = 503, message = "Failure", response = WrongResponse.class)
  })
  public ResponseEntity<Response> decodeImageWatermark(
      @RequestParam("file") MultipartFile multipartFile) {
    try {
      return new ResponseEntity<>(imageUploadBlService.decodeImageWatermark(multipartFile),
          HttpStatus.CREATED);
    } catch (SystemException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
