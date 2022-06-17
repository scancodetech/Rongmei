package com.rongmei.bl.boxegg;

import com.google.gson.GsonBuilder;
import com.rongmei.blservice.boxegg.BoxEggBlService;
import com.rongmei.dao.blindboxnft.BlindBoxNFTDao;
import com.rongmei.dao.boxegg.AddBoxEggDao;
import com.rongmei.dao.boxegg.BoxEggDao;
import com.rongmei.entity.blindboxnft.BlindBoxNFT;
import com.rongmei.entity.boxegg.AddBoxEgg;
import com.rongmei.entity.boxegg.BoxEgg;
import com.rongmei.exception.AddFailedException;
import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.exception.ThingNameDoesNotExistException;
import com.rongmei.parameters.auction.TokenUpdateParamaters;
import com.rongmei.parameters.boxegg.*;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.WrongResponse;
import com.rongmei.response.auction.TokenResponse;
import com.rongmei.response.boxegg.*;
import com.rongmei.util.EnvironmentUtil;
import com.rongmei.util.HttpUtil;
import com.rongmei.util.UserInfoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class BoxEggBlServiceImpl implements BoxEggBlService {

  private final BoxEggDao boxEggDao;
  private final BlindBoxNFTDao blindBoxNFTDao;
  private final AddBoxEggDao addBoxEggDao;

  @Autowired
  public BoxEggBlServiceImpl(BoxEggDao boxEggDao,BlindBoxNFTDao blindBoxNFTDao,
                             AddBoxEggDao addBoxEggDao) {

    this.boxEggDao = boxEggDao;
    this.blindBoxNFTDao = blindBoxNFTDao;
    this.addBoxEggDao = addBoxEggDao;
  }

  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AddFailedException.class)
  @Override
  public SuccessResponse castBoxEgg(CastBoxEggParameters parameters) throws AddFailedException {
    AddBoxEgg bySeriesName = addBoxEggDao.findBySeriesName(parameters.getSeriesName());
    if (bySeriesName!=null){
      throw new AddFailedException("系列名称已经存在",new WrongResponse(10013,"系列名称已经存在"));
    }
    try {
      CastBoxEggItem hideBoxEgg = parameters.getHideBoxEgg();
      BoxEgg boxEgg=new BoxEgg(parameters.getSpecification(),hideBoxEgg.getCoverUrl(),hideBoxEgg.getBoxEggName(),
              hideBoxEgg.getBoxEggIntroduction(),hideBoxEgg.getLimitNumber(),parameters.getWalletAddress(),
              hideBoxEgg.getResourceInfoURL(),System.currentTimeMillis(),System.currentTimeMillis(),0
              );
      //隐藏款
      BoxEgg saveHideModule = boxEggDao.save(boxEgg);
      AddBoxEgg addBoxEgg=new AddBoxEgg();
      //铸造
      long tokenId = casting(hideBoxEgg);
      //根据限量数存多少份
      for (int i = 0; i < hideBoxEgg.getLimitNumber(); i++) {
        BlindBoxNFT blindBoxNF=new BlindBoxNFT();
        blindBoxNF.setAuthor(UserInfoUtil.getUsername());
        blindBoxNF.setOwner(UserInfoUtil.getUsername());
        blindBoxNF.setTokenId(tokenId+"");
        //可能还有其他的属性需要添加，弄清楚再加
        blindBoxNF.setActive(true);
        blindBoxNF.setName(boxEgg.getBoxEggName());
        blindBoxNF.setLimitNumber(hideBoxEgg.getLimitNumber());
        blindBoxNF.setResourceInfoURL(hideBoxEgg.getResourceInfoURL());
        blindBoxNF.setWalletAddress(parameters.getWalletAddress());
        blindBoxNF.setDescription(boxEgg.getBoxEggIntroduction());
        blindBoxNF.setCreateTime(System.currentTimeMillis());
        blindBoxNF.setUpdateTime(System.currentTimeMillis());
        blindBoxNF.setBoxEggId(saveHideModule.getId());
        BlindBoxNFT blindBoxNFT = blindBoxNFTDao.save(blindBoxNF);
      }
      addBoxEgg.setHideModelId(saveHideModule.getId()+"");//隐藏款的id 字符串
      //普通款

      List<CastBoxEggItem> commonBoxEggList = parameters.getCommonBoxEggList();
      StringBuilder comBoxIdStr=new StringBuilder();
      for (CastBoxEggItem castBoxEggItem : commonBoxEggList) {
        long comTokenId = casting(castBoxEggItem);
            BoxEgg comBoxEgg=new BoxEgg(
                    parameters.getSpecification(),castBoxEggItem.getCoverUrl(),castBoxEggItem.getBoxEggName(),
                    castBoxEggItem.getBoxEggIntroduction(),castBoxEggItem.getLimitNumber(),parameters.getWalletAddress(),
                    castBoxEggItem.getResourceInfoURL(),System.currentTimeMillis(),System.currentTimeMillis(),0
            );
        BoxEgg save = boxEggDao.save(comBoxEgg);
        //铸造完之后将信息存入blind_box_nft 中 然后再获取id。
        for (int i = 0; i < castBoxEggItem.getLimitNumber(); i++) {
          BlindBoxNFT blindBoxNF=new BlindBoxNFT();
          blindBoxNF.setAuthor(UserInfoUtil.getUsername());
          blindBoxNF.setOwner(UserInfoUtil.getUsername());
          blindBoxNF.setTokenId(comTokenId+"");
          //可能还有其他的属性需要添加，弄清楚再加
          blindBoxNF.setActive(true);
          blindBoxNF.setDescription(castBoxEggItem.getBoxEggIntroduction());
          blindBoxNF.setCreateTime(System.currentTimeMillis());
          blindBoxNF.setUpdateTime(System.currentTimeMillis());
          blindBoxNF.setBoxEggId(save.getId());
          BlindBoxNFT blindBoxNFT = blindBoxNFTDao.save(blindBoxNF);
        }
        comBoxIdStr.append(save.getId()+",");
      }
      addBoxEgg.setUsername(UserInfoUtil.getUsername());
      addBoxEgg.setSpecification(parameters.getSpecification());
      addBoxEgg.setBoxNumber(parameters.getBoxNumber());
      addBoxEgg.setCommonModelId(comBoxIdStr+"");//普通款的id字符串
      addBoxEgg.setSeriesName(parameters.getSeriesName());
      addBoxEgg.setSeriesIntroduction(parameters.getSeriesIntroduction());
      addBoxEggDao.save(addBoxEgg);
    } catch (Exception e) {
      e.printStackTrace();
      throw new AddFailedException();
    }
    return new SuccessResponse("add success");
  }
  @Override
  public AddBoxEggNameGetResponse findAllAddBoxEggName() {
    List<AddBoxEgg> all = addBoxEggDao.findAll();
    List<String> addBoxEggNameList=new ArrayList<>();
    for (AddBoxEgg addBoxEgg : all) {
      addBoxEggNameList.add(addBoxEgg.getSeriesName());
    }
    return new AddBoxEggNameGetResponse(addBoxEggNameList);
  }

  @Override
  public AddBoxEggGetResponse findAddBoxEggByName(String seriesName) throws ThingIdDoesNotExistException, ThingNameDoesNotExistException {
    AddBoxEgg addBoxEgg = addBoxEggDao.findBySeriesName(seriesName);
    if (addBoxEgg==null){
      throw new ThingNameDoesNotExistException();
    }
    String hideModelId = addBoxEgg.getHideModelId();
    String[] strings = hideModelId.split(",");
    Integer integer = Integer.valueOf(strings[0]);
    Optional<BoxEgg> byId = boxEggDao.findById(integer);
    if (!byId.isPresent()){
      throw new ThingIdDoesNotExistException();
    }
    BoxEgg hideBoxEgg = byId.get();
    BoxEggItem hideBoxEggItem = packOne(hideBoxEgg);
    List<BoxEgg> boxEggList=new ArrayList<>();
    String commonModelId = addBoxEgg.getCommonModelId();
    String[] split = commonModelId.split(",");
    for (String s : split) {
      Optional<BoxEgg> byId1 = boxEggDao.findById(Integer.valueOf(s));
      if (!byId1.isPresent()){
        throw new ThingIdDoesNotExistException();
      }
      BoxEgg comBoxEgg = byId1.get();
      boxEggList.add(comBoxEgg);
    }
    List<BoxEggItem> comBoxItemList = pack(boxEggList);
    return new AddBoxEggGetResponse(hideBoxEggItem,comBoxItemList);
  }

  @Override
  public SuccessResponse randomPackageAddBoxEgg(AddBoxEggParameters parameters) throws AddFailedException, ThingIdDoesNotExistException {
    //将信息先存入addBoxEgg中
    AddBoxEgg bySeriesName = addBoxEggDao.findBySeriesName(parameters.getSeriesName());
    if (bySeriesName==null){
      throw new AddFailedException("系列名称不存在",new WrongResponse(10013,"系列名称不存在"));
    }
    bySeriesName.setSeriesIntroduction(parameters.getSeriesIntroduction());
    bySeriesName.setCoverUrl(parameters.getCoverUrl());
    bySeriesName.setStartTime(parameters.getStartTime());
    bySeriesName.setEndTime(parameters.getEndTime());
    bySeriesName.setCreateTime(System.currentTimeMillis());
    bySeriesName.setUpdateTime(System.currentTimeMillis());
    addBoxEggDao.save(bySeriesName);
    //将价格加入到每个盒蛋里面
    addPrice(bySeriesName,parameters);
    return  new SuccessResponse("add success");

  }

  private void addPrice(AddBoxEgg bySeriesName,AddBoxEggParameters parameters) throws ThingIdDoesNotExistException {
    //将价格加入到每个盒蛋里面
    String hideModelId = bySeriesName.getHideModelId();
    String[] split = hideModelId.split(",");
    for (String s : split) {
      Optional<BoxEgg> byId = boxEggDao.findById(Integer.valueOf(s));
      if (!byId.isPresent()){
        throw new ThingIdDoesNotExistException();
      }
      BoxEgg boxEgg = byId.get();
      boxEgg.setPrice(parameters.getPrice());
      List<BlindBoxNFT> blindBoxNFTList=blindBoxNFTDao.findAllByBoxEggId(boxEgg.getId());
      for (BlindBoxNFT blindBoxNFT : blindBoxNFTList) {
        blindBoxNFT.setPrice(parameters.getPrice());
        blindBoxNFTDao.save(blindBoxNFT);
      }
      boxEggDao.save(boxEgg);
    }
    String commonModelId = bySeriesName.getCommonModelId();
    String[] split1 = commonModelId.split(",");
    for (String s : split1) {
      Optional<BoxEgg> byId = boxEggDao.findById(Integer.valueOf(s));
      if (!byId.isPresent()){
        throw new ThingIdDoesNotExistException();
      }
      BoxEgg boxEgg = byId.get();
      boxEgg.setPrice(parameters.getPrice());
      boxEggDao.save(boxEgg);
      List<BlindBoxNFT> allByBoxEggId = blindBoxNFTDao.findAllByBoxEggId(boxEgg.getId());
      for (BlindBoxNFT blindBoxNFT : allByBoxEggId) {
        blindBoxNFT.setPrice(parameters.getPrice());
        blindBoxNFTDao.save(blindBoxNFT);
      }
    }
  }
  @Transactional(propagation= Propagation.REQUIRED,rollbackFor= ThingIdDoesNotExistException.class)
  @Override
  public SuccessResponse publishBoxEgg(PublishBoxEggParameters parameters) throws ThingIdDoesNotExistException, AddFailedException {
    // 发布盒蛋
    Optional<BoxEgg> byId = boxEggDao.findById(parameters.getBoxEggId());
    if (!byId.isPresent()){
      throw new ThingIdDoesNotExistException();
    }
    BoxEgg boxEgg = byId.get();
    String username = UserInfoUtil.getUsername();
    List<BlindBoxNFT> blindBoxNFTList = blindBoxNFTDao.findAllByAuthorAndOwnerAndBoxEggId(username,username,parameters.getBoxEggId());
    List<String> usernameList = parameters.getUsernameList();
    if (blindBoxNFTList.size()<usernameList.size()){
      throw new AddFailedException("盒蛋数目不够,用户数量太多了",new WrongResponse(10011,"盒蛋数目不够,用户数量太多了"));
    }
    for (int i = 0; i < usernameList.size(); i++) {
      //这里应该要判断用户名是否存在，如果有不存在的用户名就回滚 这个暂时不怎么好实现，下周来看看。
      BlindBoxNFT blindBoxNFT1 = blindBoxNFTList.get(i);
      BlindBoxNFT blindBoxNFT = blindBoxNFTDao.findFirstByTokenId(blindBoxNFT1.getTokenId());
      if (blindBoxNFT == null) {
        throw new ThingIdDoesNotExistException();
      }
      blindBoxNFT.setOwner(usernameList.get(i));
      blindBoxNFTDao.save(blindBoxNFT);
    }
    return new SuccessResponse();
  }

  @Override
  public RandomBoxEggListResponse randomPackageAddBoxEgg2(int addBoxEggId) throws ThingIdDoesNotExistException {
    Optional<AddBoxEgg> byId = addBoxEggDao.findById(addBoxEggId);
    if (!byId.isPresent()){
      throw new ThingIdDoesNotExistException();
    }
    AddBoxEgg addBoxEgg = byId.get();
    List<BoxList> boxLists=new ArrayList<>();
    String specification = addBoxEgg.getSpecification();
    if (specification.equals("1")){
      //表示的是3*3为一套
      HashMap<Integer, BlindBoxNFT> packaging = packaging(addBoxEgg);
     boxLists = randomPackaging(packaging, 9);

    }
    if (specification.equals("2")) {
      //表示的是3*4为一套
      HashMap<Integer, BlindBoxNFT> packaging = packaging(addBoxEgg);
      boxLists = randomPackaging(packaging, 12);
    }
    return new RandomBoxEggListResponse(boxLists);
  }

  @Override
  public SuccessResponse recordLineInformation(int addBoxEggId) {
    return null;
  }

  @Override
  public Object getAllBoxSeriesList() {
    return null;
  }

  private List<BoxList> randomPackaging(HashMap<Integer,BlindBoxNFT> hashMap,int num) throws ThingIdDoesNotExistException {
    List<BoxList> boxLists=new ArrayList<>();
    ArrayList<Integer> keyList=new ArrayList<>();
    Set<Integer> integers = hashMap.keySet();
    for (Integer integer : integers) {
      keyList.add(integer.intValue());
    }
    for (int i = 0; i < num; i++) {
      //每一套封装
      BoxList boxList=new BoxList();
      List<BlindBoxNFT> blindBoxNFTS=new ArrayList<>();
      for (int j = 0; j < num; j++) {
        //每一个盒蛋封装
        Random random=new Random();
        int randomNumber=random.nextInt(keyList.size());
        Integer randomId = keyList.get(randomNumber);
        BlindBoxNFT boxNFT = hashMap.get(randomId);
        Integer remove1 = keyList.remove(randomNumber);
        blindBoxNFTS.add(boxNFT);
      }
      boxList.setBoxEggItemList(blindBoxNFTS);
      boxLists.add(boxList);
    }
    return boxLists;
  }

  private HashMap<Integer, BlindBoxNFT> packaging(AddBoxEgg bySeriesName) throws ThingIdDoesNotExistException {
    Integer  number=1;
    HashMap<Integer, BlindBoxNFT> hashMap=new HashMap<>();
    String hideModelId = bySeriesName.getHideModelId();
    String[] hideModelIds = hideModelId.split(",");
    List<BlindBoxNFT> allByBoxEggId = blindBoxNFTDao.findAllByBoxEggId(Integer.parseInt(hideModelIds[0]));
    //将 盒蛋信息（id和数量）放入map集合里。 如何随机封装
    for (BlindBoxNFT blindBoxNFT : allByBoxEggId) {
      hashMap.put(number,blindBoxNFT);
      number++;
    }
    String commonModelId = bySeriesName.getCommonModelId();
    String[] commonModelIds = commonModelId.split(",");
    for (String modelId : commonModelIds) {
      List<BlindBoxNFT> blindBoxNFTS = blindBoxNFTDao.findAllByBoxEggId(Integer.parseInt(modelId));
      for (BlindBoxNFT blindBoxNFT : blindBoxNFTS) {
        hashMap.put(number,blindBoxNFT);
        number++;
      }
    }
    return hashMap;
  }


  private long  casting(CastBoxEggItem hideBoxEgg) {
    //上传图片
//    String url="http://127.0.0.1:8888"+"/auction/token";
    String url="https://api.dimension.pub/rongmei_mall_api_dev"+"/auction/token";
    TokenUpdateParamaters param=new TokenUpdateParamaters();
    param.setTokenId(0);
    param.setValue(hideBoxEgg.getCoverUrl());
    String json = getJson(param);
    //获取tokenId
    TokenResponse tokenResponse = HttpUtil.getTokenId(url, json);
    long tokenId = tokenResponse.getTokenId();
    //铸造
//    String  castUrl="https://127.0.0.1"+"rongmei.account.payment"+"balance/mint";
    String  castUrl="https://api.dimension.pub"+EnvironmentUtil.packEnvironment("rongmei.account.payment")+"balance/mint";
    HashMap<String,Object> map=new HashMap<String,Object>();
    String username = UserInfoUtil.getUsername();
    map.put("username",username);
    map.put("tokenId",tokenId);
    map.put("host","api.dimension.pub");
    HttpUtil.postCasting(castUrl,getJson(map));
    return tokenId;
  }

  private String getJson(Object value){
    GsonBuilder gsonBuilder = new GsonBuilder();
    return gsonBuilder.create().toJson(value);
  }
  @Override
  public BoxEggGetResponse findCastBoxEggByStatus(int status) throws ThingIdDoesNotExistException {
    List<BlindBoxNFT> blindBoxNFTList = blindBoxNFTDao.findAllByOwner(UserInfoUtil.getUsername());
    List<BoxEggItem> boxEggItemList=packNFT(blindBoxNFTList);
    return new BoxEggGetResponse(boxEggItemList);
  }

  private List<BoxEggItem> packNFT(List<BlindBoxNFT> blindBoxNFTList) throws ThingIdDoesNotExistException {
    HashMap<Integer,BoxEgg>  map=new HashMap<>();

    List<BoxEggItem> boxEggItemList=new ArrayList<>();
    for (BlindBoxNFT blindBoxNFT : blindBoxNFTList) {
      Optional<BoxEgg> byId = boxEggDao.findById(blindBoxNFT.getBoxEggId());
      if (!byId.isPresent()){
        throw new ThingIdDoesNotExistException();
      }
      BoxEgg boxEgg = byId.get();
      map.put(blindBoxNFT.getBoxEggId(),boxEgg);
    }
    for (Integer integer : map.keySet()) {
      BoxEgg boxEgg = map.get(integer);
      BoxEggItem boxEggItem = new BoxEggItem();
      boxEggItem.setId(boxEgg.getId());
      //设置的是Nft的id,先都设置为BoxEgg的id吧，暂时的信息在boxEgg里面都可以获取。
      boxEggItem.setBoxEggName(boxEgg.getBoxEggName());
      boxEggItem.setLimitNumber(boxEgg.getLimitNumber());
      String username = UserInfoUtil.getUsername();
      List<BlindBoxNFT> list = blindBoxNFTDao.findAllByAuthorAndOwnerAndBoxEggId(username, username, boxEgg.getId());
      int num=boxEgg.getLimitNumber()-list.size();
      boxEggItem.setPublishNumber(num);//设置已经分发的盒蛋数量
      boxEggItem.setCreateTime(boxEgg.getCreateTime());
      boxEggItem.setUpdateTime(boxEgg.getUpdateTime());
      boxEggItem.setBoxEggIntroduction(boxEgg.getBoxEggIntroduction());
      boxEggItemList.add(boxEggItem);
    }
    return boxEggItemList;
  }

  @Override
  public AddBoxEggListResponse findAddBoxEggByStatus(int status) throws ThingIdDoesNotExistException {
    List<AddBoxEgg> allByStatus = addBoxEggDao.findAll();
    List<BoxEggItem> boxEggItemList=packAddBox(allByStatus);
    return new AddBoxEggListResponse(boxEggItemList);
  }



  private List<BoxEggItem> packAddBox(List<AddBoxEgg> allByStatus) throws ThingIdDoesNotExistException {
    List<BoxEggItem> boxEggItemList=new ArrayList<>();
    for (AddBoxEgg byStatus : allByStatus) {
      //每一个系列都是有很多的普通款和一个隐藏款。
      //添加隐藏款的显示对象
      String hideModelId = byStatus.getHideModelId();
      String[] split1 = hideModelId.split(",");
      Optional<BoxEgg> byId = boxEggDao.findById(Integer.valueOf(split1[0]));
      if (!byId.isPresent()){
        throw new ThingIdDoesNotExistException();
      }
      BoxEgg hideBoxEgg = byId.get();
      List<BlindBoxNFT> blindBoxNFTList = blindBoxNFTDao.findAllByAuthorAndOwnerAndBoxEggId(UserInfoUtil.getUsername(),
              UserInfoUtil.getUsername(),hideBoxEgg.getId());
      int num=hideBoxEgg.getLimitNumber()-blindBoxNFTList.size();
      BoxEggItem boxItem=new BoxEggItem(hideBoxEgg.getId(),byStatus.getSeriesName(),hideBoxEgg.getSpecification()
      ,byStatus.getCoverUrl(),hideBoxEgg.getBoxEggName(),hideBoxEgg.getBoxEggIntroduction(),hideBoxEgg.getLimitNumber(),
              num,byStatus.getPrice(),hideBoxEgg.getWalletAddress(),hideBoxEgg.getResourceInfo(),
              hideBoxEgg.getCreateTime(),hideBoxEgg.getUpdateTime()
      );
      boxEggItemList.add(boxItem);//添加隐藏款的显示对象
      //添加普通款的显示对象
      String commonModelId = byStatus.getCommonModelId();
      String[] split = commonModelId.split(",");
      for (String s : split) {
        Optional<BoxEgg> byId1 = boxEggDao.findById(Integer.valueOf(s));
        if (!byId1.isPresent()){
          throw new ThingIdDoesNotExistException();
        }
        BoxEgg boxEgg = byId1.get();
        int num1=hideBoxEgg.getLimitNumber()-blindBoxNFTList.size();
        List<BlindBoxNFT> blindBoxNFTList1 = blindBoxNFTDao.findAllByAuthorAndOwnerAndBoxEggId(UserInfoUtil.getUsername(),UserInfoUtil.getUsername(),hideBoxEgg.getId());
        BoxEggItem boxItem1=new BoxEggItem(boxEgg.getId(),byStatus.getSeriesName(),boxEgg.getSpecification()
                ,byStatus.getCoverUrl(),boxEgg.getBoxEggName(),boxEgg.getBoxEggIntroduction(),boxEgg.getLimitNumber(),
               num1,byStatus.getPrice(),boxEgg.getWalletAddress(),boxEgg.getResourceInfo(),boxEgg.getCreateTime(),
                boxEgg.getUpdateTime()
        );
        boxEggItemList.add(boxItem1);
      }
    }
    return  boxEggItemList;
  }

  private BoxEggItem packOne(BoxEgg boxEgg) {
    BoxEggItem boxItem=new BoxEggItem();
    boxItem.setId(boxEgg.getId());
    boxItem.setSpecification(boxEgg.getSpecification());
    boxItem.setCoverUrl(boxEgg.getCoverUrl());
    boxItem.setBoxEggIntroduction(boxEgg.getBoxEggIntroduction());
    boxItem.setLimitNumber(boxEgg.getLimitNumber());
    boxItem.setWalletAddress(boxEgg.getWalletAddress());
    boxItem.setResourceInfo(boxEgg.getResourceInfo());
    boxItem.setCreateTime(boxEgg.getCreateTime());
    boxItem.setUpdateTime(boxEgg.getUpdateTime());
    return  boxItem;
  }

  private List<BoxEggItem> pack(List<BoxEgg> allByStatus) {

    List<BoxEggItem> boxEggItemList=new ArrayList<>();
    for (BoxEgg byStatus : allByStatus) {
      BoxEggItem boxItem=new BoxEggItem();
      boxItem.setId(byStatus.getId());
      boxItem.setSpecification(byStatus.getSpecification());
      boxItem.setCoverUrl(byStatus.getCoverUrl());
      boxItem.setBoxEggIntroduction(byStatus.getBoxEggIntroduction());
      boxItem.setLimitNumber(byStatus.getLimitNumber());
      boxItem.setWalletAddress(byStatus.getWalletAddress());
      boxItem.setResourceInfo(byStatus.getResourceInfo());
      boxItem.setCreateTime(byStatus.getCreateTime());
      boxItem.setUpdateTime(byStatus.getUpdateTime());
      boxEggItemList.add(boxItem);
    }
    return boxEggItemList;
  }

}
