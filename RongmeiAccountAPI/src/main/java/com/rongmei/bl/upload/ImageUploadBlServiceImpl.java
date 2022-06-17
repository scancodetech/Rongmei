package com.rongmei.bl.upload;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.$Gson$Preconditions;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import com.rongmei.blservice.account.UserGroupBlService;
import com.rongmei.blservice.upload.ImageUploadBlService;
import com.rongmei.entity.excel.ColumnTitleMap;
import com.rongmei.exception.FileSuffixWrongException;
import com.rongmei.exception.SystemException;
import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.exception.UsernameDoesNotFoundException;
import com.rongmei.response.Response;
import com.rongmei.response.upload.UploadImageResponse;
import com.rongmei.response.upload.UploadZipFileResponse;
import com.rongmei.response.user.UserGroupGetResponse;
import com.rongmei.response.user.UserGroupItem;
import com.rongmei.util.*;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.*;


@Service
public class ImageUploadBlServiceImpl implements ImageUploadBlService {

  @Value("${file.url}")
  private String fileUrl;

  @Value("${unzipPath}")
  private String unzipPath;


  private final UserGroupBlService userGroupBlService;



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

  @Autowired
  public ImageUploadBlServiceImpl(UserGroupBlService userGroupBlService) {
    this.userGroupBlService = userGroupBlService;
  }




  /**
   * down file
   * @param  key
   */

  @Override
  public void down(String key, HttpServletResponse response)  {
    System.out.println(key);
    try {

    // Bucket的命名格式为 BucketName-APPID ，此处填写的存储桶名称必须为此格式
    // 指定文件在 COS 上的路径，即对象键。例如对象键为folder/picture.jpg，则表示下载的文件 picture.jpg 在 folder 路径下
    // 方法1 获取下载输入流
    GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
    COSObject cosObject = cosClient.getObject(getObjectRequest);
    COSObjectInputStream cosObjectInput = cosObject.getObjectContent();
    // 关闭输入流
    if("jpg,jepg,gif,png".contains(key)){//图片类型
      response.setContentType("image/"+key);
    }else if("pdf".contains(key)){//pdf类型
      response.setContentType("application/pdf");
    }else{//自动判断下载文件类型
      response.setContentType("multipart/form-data");
    }
    //设置文件头：最后一个参数是设置下载文件名
    response.setHeader("Content-Disposition", "attachment;fileName="+key);
    ServletOutputStream outputStream = response.getOutputStream();// 读取文件流
    int len = 0;
    byte[] buffer = new byte[1024 * 10];
    while ((len = cosObjectInput.read(buffer)) != -1) {
      outputStream.write(buffer, 0, len);
    }
      outputStream.flush();

      cosObjectInput.close();
      outputStream.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }



  /**
   * Upload the image of the mission
   *
   * @param multipartFile
   * @return the url of the image
   */
  @Override
  public UploadImageResponse uploadFiles(MultipartFile multipartFile,Integer type,HttpServletResponse response) throws SystemException {
    try {

      String key = generateImageKey(multipartFile.getOriginalFilename());
      if(key.endsWith("xlsx") || key.endsWith("xls")){
        return readExcel(multipartFile);
      }
      if(key.endsWith("zip")){
        try {
          if(type == null ){
            type = 0;
          }
          return uploadZipFile(multipartFile,type,response);
        } catch (Exception e) {
          e.printStackTrace();
          throw new SystemException();
        }
      }
      String fileUrl = getCosUrl(key);
      BufferedImage bufferedImage = ImageIO.read(multipartFile.getInputStream());
      uploadFiles(bufferedImage, key);
      return new UploadImageResponse(fileUrl);
    } catch (IOException e) {
      e.printStackTrace();
      throw new SystemException();
    }
  }

  /**
   * 读取excel 内容 发送请求
   * @param multipartFile
   * @return
   */
  private UploadImageResponse readExcel(MultipartFile multipartFile)  {
    String path = "";
    String key = "";
    try {
       key = generateImageKey(multipartFile.getOriginalFilename());
       path = uploadXlsx(key, multipartFile);
      //读取第一个sheet
      System.out.println(path);
      List<Object> objects = PoiUtil.readExcel(path, true, 0);
      List<Map<String, Object>> hashMaps = constructionParametersByExcel(objects);
      sendUrl(hashMaps);
    } catch (SystemException e) {
      e.printStackTrace();
    }finally {
      FileSystemUtils.deleteRecursively(new File(path));
    }
    return new UploadImageResponse("success");
  }

  /**
   * 根据excel内容 构造请求内容
   * @param list
   * @return
   */
  private List<Map<String ,Object>> constructionParametersByExcel(List<Object> list){
    ArrayList<Map<String ,Object>> mapArrayList = Lists.newArrayList();
    if (list != null) {
      for (int i = 0; i < list.size(); i++) {
        // 获取工作表
        List<Object> sheetList = (List<Object>) list.get(i);
        for (int j = 1; j < sheetList.size(); j++) {
          // 获取工作表的每一行
          List<Object> rowList = (List<Object>) sheetList.get(j);
          //不获取表头
          for (int k = 1; k < rowList.size(); k++) {
            // 获取工作表的每一列
            List<Object> cellList = (List<Object>) rowList.get(k);
            HashMap<String, Object> hashMap = constructionParameters(cellList);
            mapArrayList.add(hashMap);
          }
        }
      }
    }
    return mapArrayList;
  }

  public String uploadFiles(File file)  {
    try {
      String key = generateImageKey(file.getName());
      String fileUrl = getCosUrl(key);
      BufferedImage bufferedImage = ImageIO.read(new FileInputStream(file));
      uploadFiles(bufferedImage, key);
      return fileUrl;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }



  private String generateImageKey(String name) {
    return FormatDateTime.currentTimeString() + "_" + name;
  }


  /**
   * upload the image to the oos cloud
   *
   * @param key           the id of the image
   * @param multipartFile the image content
   * @return the url of the uploaded image
   */
  @Deprecated
  private String uploadImage(String key, MultipartFile multipartFile) throws SystemException {
    try {
      //保存到临时文件
      String filePath = PathUtil.getStaticPath();
      System.out.println(filePath);
      filePath = filePath + "/" + key;
      File file = new File(filePath);
      multipartFile.transferTo(file);
      return fileUrl + key;
    } catch (Exception e) {
      e.printStackTrace();
      throw new SystemException();
    }
  }

  /**
   * 保存临时文件
   * @param key
   * @param multipartFile
   * @return
   * @throws SystemException
   */
  private String uploadXlsx(String key, MultipartFile multipartFile) throws SystemException {
    try {
      //保存到临时文件
      System.out.println(fileUrl);
      fileUrl = fileUrl + key;
      File file = new File(fileUrl);
      multipartFile.transferTo(file);
      return fileUrl;
    } catch (Exception e) {
      e.printStackTrace();
      throw new SystemException();
    }
  }

  private void uploadFiles(BufferedImage bufferedImage, String key) throws IOException {
    File localFile = new File(PathUtil.getTmpPath() + "/" + key);
    ImageIO.write(bufferedImage, "png", localFile);
    // 指定要上传到 COS 上对象键
    PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
    cosClient.putObject(putObjectRequest);
  }

  private String getCosUrl(String key) {
    return "https://" + bucketName + ".cos.ap-nanjing.myqcloud.com/" + key;
  }



  private UploadImageResponse uploadZipFile(MultipartFile multipartFile,int type,HttpServletResponse response) throws Exception {
    String username = UserInfoUtil.getUsername();
    if(null == username || "".equals(username)){
      throw  new UsernameDoesNotFoundException();
    }
    //String username = "shixianglong";
    String unzipPath = Unzip(multipartFile, username);
    List<Map<String, Object>> list = readFile(unzipPath, username);
    if(type == 0){
     returnExcel(list,response);
     return null;
    }else if(type == 1) {
      //发送请求
      sendUrl(list);
      return new UploadImageResponse(" success");
    }
      throw new SystemException();
  }

  @Override
  public UploadImageResponse uploadZipFileByCasting(MultipartFile multipartFile,int type,HttpServletResponse response) throws Exception {
    String username = UserInfoUtil.getUsername();
    if(null == username || "".equals(username)){
      throw  new UsernameDoesNotFoundException();
    }
    //String username = "shixianglong";
    String unzipPath = Unzip(multipartFile, username);
    List<Map<String, Object>> list = readFile(unzipPath, username);
    if(type == 0){
      returnExcel(list,response);
      return null;
    }else if(type == 1) {
      //发送请求
      sendUrl(list);
      return new UploadImageResponse(" success");
    }
    throw new SystemException();
  }


  private void sendUrl(List<Map<String,Object>> list) {

    String baseServiceUrl =
            "http://localhost:8888";
            //"https://api.dimension.pub/" + EnvironmentUtil.packEnvironment("rongmei.mall.api");



    String URL = baseServiceUrl+"/commodity/group";



      String json = getJson(list);
      HttpUtil.put(URL, json);
  }

  private String getJson(Object value){
    GsonBuilder gsonBuilder = new GsonBuilder();
    return gsonBuilder.create().toJson(value);
  }


  /**
   * 解压 zip
   * @param multipartFile
   * @param username
   * @return
   * @throws Exception
   */
  private String Unzip(MultipartFile multipartFile,String username) throws Exception {
    String originalFilename = multipartFile.getOriginalFilename();
    if(!originalFilename.endsWith("zip")){
      throw new FileSuffixWrongException("file suffix is not zip");
    }
    String path = unzipPath + "\\" + username;
    ReadZipUtil.ZipUncompress(multipartFile.getInputStream(),path);
    //文件路径 供二次读取
    return path;
  }

  /**
   * 读取解呀后得zp 按照 GetFolderFileUtil.getDirFileNames来读
   * @param path
   * @param username
   * @return
   * @throws FileSuffixWrongException
   */
  private List<Map<String,Object>> readFile(String path, String username) throws FileSuffixWrongException {
    HashMap<String, List<File>> dirFileNames = GetFolderFileUtil.getDirFileNames(new File(path));
    ArrayList<Map<String,Object>> maps = Lists.newArrayList();

    //上传素材
    Set<String> keySet = dirFileNames.keySet();
    for (String key : keySet) {
      List<File> files = dirFileNames.get(key);

      if(files.size() != 2){
        FileSystemUtils.deleteRecursively(new File(path));
        throw new FileSuffixWrongException("file size wrong");
      }
      if(GetFolderFileUtil.isImageByList(files)) {
        //比较大小 默认小的是封面 大的是资源文件
        List<File> filesAfter = GetFolderFileUtil.comparisonFileSize(files);
        String coverUrl = uploadFiles(filesAfter.get(0));
        String contentUrl = uploadFiles(filesAfter.get(1));
        HashMap<String, Object> hashMap = ConstructionParameters(coverUrl, contentUrl, username,filesAfter.get(1).getName());
        maps.add(hashMap);
      }else {
        List<File> files1 = GetFolderFileUtil.reSort(files);
        String coverUrl = uploadFiles(files1.get(0));
        String contentUrl = uploadFiles(files1.get(1));
        HashMap<String, Object> hashMap = ConstructionParameters(coverUrl, contentUrl, username,files1.get(1).getName());
        maps.add(hashMap);
      }
    }

    return maps;

  }





  private HashMap<String ,Object> ConstructionParameters(String coverUrl,String contentUrl,String username,String title){
    UserGroupGetResponse userGroupByUsername = userGroupBlService.getUserGroupByUsername(username);
    List<UserGroupItem> userGroupItems = userGroupByUsername.getUserGroupItems();
    HashMap<String, Object> commodityUpdateParameters = Maps.newHashMap();
    commodityUpdateParameters.put("id","0");
    commodityUpdateParameters.put("title",title);
    commodityUpdateParameters.put("largePrice",9.9*100);
    commodityUpdateParameters.put("coverUrl",coverUrl);
    commodityUpdateParameters.put("tags",Lists.newArrayList("设卡模板"));
    commodityUpdateParameters.put("contentUrl",contentUrl);
    commodityUpdateParameters.put("description","一键上传");
    commodityUpdateParameters.put("signingInfo","");
    commodityUpdateParameters.put("extra","");
    if(userGroupItems.size() <= 0){
      commodityUpdateParameters.put("creatorUserGroupId","1");
    }else {
      commodityUpdateParameters.put("creatorUserGroupId", +userGroupItems.get(0).getId()+"");
    }
      String baseServiceUrl =
            "https://api.dimension.pub/" + EnvironmentUtil.packEnvironment("rongmei.mall.api");
    String downUrl = baseServiceUrl + "/down";
    commodityUpdateParameters.put("downloadUrl",downUrl + splist(contentUrl));
    commodityUpdateParameters.put("createTime","");
    commodityUpdateParameters.put("updateTime","");
    commodityUpdateParameters.put("isExclusive","true");
    commodityUpdateParameters.put("author",username);
    commodityUpdateParameters.put("draftStatus","2");
    return commodityUpdateParameters;
  }

  private HashMap<String ,Object> constructionParameters(List<Object> list){
    UserGroupGetResponse userGroupByUsername = userGroupBlService.getUserGroupByUsername(String.valueOf(list.get(list.size()-1)));
    List<UserGroupItem> userGroupItems = userGroupByUsername.getUserGroupItems();
    HashMap<String, Object> commodityUpdateParameters = Maps.newHashMap();
    commodityUpdateParameters.put("id",String.valueOf(list.get(0)));
    commodityUpdateParameters.put("title",String.valueOf(list.get(1)));
    commodityUpdateParameters.put("largePrice",getLong(list.get(2)));
    commodityUpdateParameters.put("coverUrl",String.valueOf(list.get(3)));
    commodityUpdateParameters.put("tags",getList(list.get(4)));
    commodityUpdateParameters.put("contentUrl",String.valueOf(list.get(5)));
    commodityUpdateParameters.put("description",String.valueOf(list.get(6)));
    commodityUpdateParameters.put("signingInfo",String.valueOf(list.get(7)));
    commodityUpdateParameters.put("extra",String.valueOf(list.get(8)));
    commodityUpdateParameters.put("creatorUserGroupId",userGroupItems.get(0).getId()+"");
    commodityUpdateParameters.put("downloadUrl",String.valueOf(list.get(9)));
    commodityUpdateParameters.put("createTime","");
    commodityUpdateParameters.put("updateTime","");
    commodityUpdateParameters.put("isExclusive",Boolean.valueOf(String.valueOf(list.get(2))));
    commodityUpdateParameters.put("author",String.valueOf(list.get(list.size()-1)));
    commodityUpdateParameters.put("draftStatus","2");
    return commodityUpdateParameters;
  }

  private long getLong(Object o){

    NumberFormat numberFormat = NumberFormat.getInstance();
    // 不显示千位分割符，否则显示结果会变成类似1,234,567,890
    numberFormat.setGroupingUsed(false);
    String s = String.valueOf(o);
    String content = numberFormat.format(BigDecimal.valueOf(Double.valueOf(s)));
    return Long.valueOf(content);

  }
  private List<String> getList(Object o){

    GsonBuilder gsonBuilder = new GsonBuilder();
    List <String>list = (List<String>)gsonBuilder.create().fromJson(String.valueOf(o), List.class);
    return list;

  }

  private   String splist(String url){
    int i = url.lastIndexOf("/");
    String substring = url.substring(i+1);
    return substring;
  }

  private void returnExcel(List<Map<String, Object>> date,HttpServletResponse response){
    ColumnTitleMap excel = new ColumnTitleMap("excel");
    try {
      PoiUtil.expoerDataExcel(response, excel.getTitleKeyList(), excel.getColumnTitleMap(), date);
    } catch (IOException e) {
      e.printStackTrace();
    }

  }




}
