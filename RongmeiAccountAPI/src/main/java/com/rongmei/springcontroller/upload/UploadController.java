package com.rongmei.springcontroller.upload;

import com.rongmei.blservice.upload.ImageUploadBlService;
import com.rongmei.exception.SystemException;
import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.upload.UploadImageResponse;
import io.swagger.annotations.*;
import com.rongmei.response.Response;
import com.rongmei.response.WrongResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

@RestController
public class UploadController {
    private final ImageUploadBlService imageUploadBlService;

    @Autowired
    public UploadController(ImageUploadBlService imageUploadBlService) {
        this.imageUploadBlService = imageUploadBlService;
    }

    @ApiOperation(value = "上传文件", notes = "上传文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "multipartFile", value = "文件", required = true, dataType = "MultipartFile"),
            @ApiImplicitParam(name = "thingId", value = "物品项ID", required = true, dataType = "int", paramType = "path")
    })
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Image uploaded", response = UploadImageResponse.class),
            @ApiResponse(code = 403, message = "Upload session timed out", response = WrongResponse.class),
            @ApiResponse(code = 404, message = "Upload session id not exist", response = WrongResponse.class),
            @ApiResponse(code = 503, message = "Failure", response = WrongResponse.class)
    })
    public ResponseEntity<Response> uploadFiles(@RequestParam("file") MultipartFile multipartFile,Integer type,HttpServletResponse response) {
        try {
            return new ResponseEntity<>(imageUploadBlService.uploadFiles(multipartFile,type,response), HttpStatus.CREATED);
        } catch (SystemException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getResponse(), HttpStatus.SERVICE_UNAVAILABLE);
        } catch (ThingIdDoesNotExistException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
        }
    }


    @RequestMapping(value = "/uploadZip", method = RequestMethod.POST)
    public Object uploadZipFiles(@RequestParam("zipFile") MultipartFile multipartFile,@RequestParam("type") int type,HttpServletResponse response) {
        try {
            return new ResponseEntity<>(imageUploadBlService.uploadZipFileByCasting(multipartFile,type,response), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new SuccessResponse("ok"), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @RequestMapping(value = "/down/{key}", method = RequestMethod.GET)
    public void uploadZipFiles(@PathVariable("key") String key,
                               HttpServletResponse response) {
        imageUploadBlService.down(key,response);
    }

}
