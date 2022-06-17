package com.rongmei.bl.image;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.region.Region;
import com.rongmei.blservice.image.ImageUploadBlService;
import com.rongmei.exception.SystemException;
import com.rongmei.response.image.ImageResponse;
import com.rongmei.util.FormatDateTime;
import com.rongmei.util.ImageWaterMarkUtil;
import com.rongmei.util.ImgWatermarkUtil;
import com.rongmei.util.PathUtil;
import com.rongmei.util.RandomUtil;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class ImageUploadBlServiceImpl implements ImageUploadBlService {

  @Value("${file.url}")
  private String fileUrl;
  private static final String secretId = "AKIDSkwD1ZGnW1GK8lj1OAh42nl7SYu1arXr";
  private static final String secretKey = "sYM7pRtBSa2iovaCfDYq2iWcltwnT0OX";
  private static final String bucketName = "rongmei-1255617399";
  private COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
  // 2 设置 bucket 的区域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
// clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
  private Region region = new Region("ap-nanjing");
  private ClientConfig clientConfig = new ClientConfig(region);
  // 3 生成 cos 客户端。
  private COSClient cosClient = new COSClient(cred, clientConfig);

  @Override
  public ImageResponse encodeImageWatermark(MultipartFile multipartFile, String token)
      throws SystemException {
    try {
//      ImageCreateEntity imageCreateEntity = new ImageCreateEntity();
//      imageCreateEntity.setHeight(64);
//      imageCreateEntity.setWidth(64);
//      imageCreateEntity.setImgHeight(64);
//      imageCreateEntity.setImgWidth(64);
//      imageCreateEntity.setFontSize(12f);
//      imageCreateEntity.setLinePadding(0);
//      imageCreateEntity.setCenterLine(true);
//      imageCreateEntity.setTextContent(token);
//      imageCreateEntity.setTransparentBackground(true);
//      imageCreateEntity.setFontName("Serif");
//      imageCreateEntity
//          .setImgContent(ImageIO.read(new File(PathUtil.getResourcePath() + "white.jpg")));
//      BufferedImage bufferedImage = ImageCreateUtil.createImg(imageCreateEntity);

      BufferedImage srcBufferedImage = ImageIO.read(multipartFile.getInputStream());
      Mat src = ImageWaterMarkUtil.convertMat(srcBufferedImage);
      Mat target = ImgWatermarkUtil.addImageWatermarkWithText(src, token);
      String url = uploadFiles(target, token);
      return new ImageResponse(url);
    } catch (IOException e) {
      e.printStackTrace();
      throw new SystemException();
    }
  }

  @Override
  public ImageResponse decodeImageWatermark(MultipartFile multipartFile) throws SystemException {
    try {
      //读取图片水印
      BufferedImage srcBufferedImage = ImageIO.read(multipartFile.getInputStream());
      Mat src = ImageWaterMarkUtil.convertMat(srcBufferedImage);
      Mat target = ImgWatermarkUtil.getImageWatermarkWithText(src);
      String url = uploadFiles(target, RandomUtil.generateCode(6));
      return new ImageResponse(url);
    } catch (IOException e) {
      e.printStackTrace();
      throw new SystemException();
    }
  }

  private String uploadFiles(Mat mat, String token) throws IOException {
    String key = generateImageKey(token);
    File localFile = new File(PathUtil.getTmpPath() + "/" + key);
//    Highgui.imwrite(localFile.getAbsolutePath(), mat);
    BufferedImage bufferedImage = ImageWaterMarkUtil.mat2BufImg(mat, ".png");
    ImageIO.write(bufferedImage, "png", localFile);
// 指定要上传到 COS 上对象键
    PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
    cosClient.putObject(putObjectRequest);
    return "https://" + bucketName + ".cos.ap-nanjing.myqcloud.com/" + key;
  }

  private String uploadFiles(BufferedImage bufferedImage, String token) throws IOException {
    String key = generateImageKey(token);
    File localFile = new File(PathUtil.getTmpPath() + "/" + key);
    ImageIO.write(bufferedImage, "png", localFile);
// 指定要上传到 COS 上对象键
    PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
    cosClient.putObject(putObjectRequest);
    return "https://" + bucketName + ".cos.ap-nanjing.myqcloud.com/" + key;
  }

  private String generateImageKey(String name) {
    return FormatDateTime.currentTimeString() + "_" + name;
  }
}
