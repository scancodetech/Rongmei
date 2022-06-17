package com.rongmei.blservice.upload;

import com.rongmei.exception.FileSuffixWrongException;
import com.rongmei.exception.UsernameDoesNotFoundException;
import com.rongmei.response.upload.UploadImageResponse;
import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.exception.SystemException;
import com.rongmei.response.upload.UploadZipFileResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public interface ImageUploadBlService {
    /**
     * Upload the image of the thing
     *
     * @param multipartFile
     * @return the url of the image
     */
    UploadImageResponse uploadFiles(MultipartFile multipartFile,Integer type,HttpServletResponse response) throws SystemException, ThingIdDoesNotExistException;


    /**
     * upload material files to casting
     *
     * @param multipartFile
     *
     * @return ?
     * @throws SystemException
     * @throws ThingIdDoesNotExistException
     */
    UploadImageResponse uploadZipFileByCasting(MultipartFile multipartFile,int type,HttpServletResponse response) throws Exception;


    void down(String key, HttpServletResponse response);


}
