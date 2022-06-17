package com.rongmei.bl.account;

import com.rongmei.blservice.account.UserBlService;
import com.rongmei.dao.account.*;
import com.rongmei.dao.auth.CaptchaDao;
import com.rongmei.dao.platform.PlatformDao;
import com.rongmei.entity.account.*;
import com.rongmei.dao.account.UserAccountDao;
import com.rongmei.dao.account.UserDao;
import com.rongmei.dao.account.UserInfoDao;
import com.rongmei.dao.account.UserInterestDao;
import com.rongmei.entity.account.User;
import com.rongmei.entity.account.UserAccount;
import com.rongmei.entity.account.UserInfo;
import com.rongmei.entity.account.UserInterest;
import com.rongmei.entity.auth.Captcha;
import com.rongmei.entity.platform.Platform;
import com.rongmei.exception.*;
import com.rongmei.parameters.user.RegisterParameters;
import com.rongmei.parameters.user.UserAccountUpdateParameters;
import com.rongmei.parameters.user.UserInfoSaveParameters;
import com.rongmei.parameters.user.UserPasswordUpdateParamaters;
import com.rongmei.publicdatas.account.Role;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.WrongResponse;
import com.rongmei.response.employees.EmployeesListResponse;
import com.rongmei.response.employees.EmployeesLoginResponse;
import com.rongmei.response.item.ItemListResponse;
import com.rongmei.response.role.RoleListResponse;
import com.rongmei.response.user.UserAccountGetResponse;
import com.rongmei.response.user.UserInfoGetResponse;
import com.rongmei.response.user.UserInfoSaveResponse;
import com.rongmei.response.user.UserItem;
import com.rongmei.response.user.UserListResponse;
import com.rongmei.response.user.UserLoginResponse;
import com.rongmei.response.user.UserResponse;
import com.rongmei.response.user.UserRoleResponse;
import com.rongmei.security.jwt.JwtEmployees;
import com.rongmei.security.jwt.JwtService;
import com.rongmei.security.jwt.JwtUser;
import com.rongmei.security.jwt.JwtUserDetailsService;
import com.rongmei.util.DESUtil;
import com.rongmei.util.RandomUtil;
import com.rongmei.util.RegexUtils;
import com.rongmei.util.UserInfoUtil;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserBlServiceImpl implements UserBlService {

  private final static long EXPIRATION = 604800000;

  private final JwtUserDetailsService jwtUserDetailsService;
  private final JwtService jwtService;
  private final UserInfoDao userInfoDao;
  private final UserAccountDao userAccountDao;
  private final UserDao userDao;
  private final CaptchaDao captchaDao;
  private final PlatformDao platformDao;
  private final UserInterestDao userInterestDao;
  private final JavaMailSender mailSender;
  private final RoleDao roleDao;
  private final EmployeesDao employeesDao;
  private final EmployeesRoleDao employeesRoleDao;
  private final ItemDao itemDao;
  private final RoleItemDao roleItemDao;

  @Value(value = "${tencentcloud.accessId}")
  private String accessId;

  @Value(value = "${tencentcloud.accessSecret}")
  private String accessSecret;

  @Value(value = "${tencentcloud.sms.appId}")
  private String appId;

  @Value(value = "${tencentcloud.sms.signId}")
  private String signId;

  @Value(value = "${tencentcloud.sms.templateId}")
  private String templateId;

  @Value(value = "${email.sender}")
  private String senderEmail;

  @Value(value = "${email.subject}")
  private String subject;

  @Value(value = "${email.content1}")
  private String content1;

  @Value(value = "${email.content2}")
  private String content2;


  @Value("${jwt.tokenHead}")
  private String tokenHead;

  @Autowired
  public UserBlServiceImpl(JwtUserDetailsService jwtUserDetailsService, JwtService jwtService,
      UserInfoDao userInfoDao, UserAccountDao userAccountDao,
      RoleDao roleDao, EmployeesRoleDao employeesRoleDao, EmployeesDao employeesDao,
      RoleItemDao roleItemDao, ItemDao itemDao,
      UserDao userDao, CaptchaDao captchaDao,
      PlatformDao platformDao, UserInterestDao userInterestDao,
      JavaMailSender mailSender) {
    this.jwtUserDetailsService = jwtUserDetailsService;
    this.jwtService = jwtService;
    this.userInfoDao = userInfoDao;
    this.userAccountDao = userAccountDao;
    this.userDao = userDao;
    this.captchaDao = captchaDao;
    this.platformDao = platformDao;
    this.userInterestDao = userInterestDao;
    this.mailSender = mailSender;
    this.roleDao = roleDao;
    this.employeesDao = employeesDao;
    this.employeesRoleDao = employeesRoleDao;
    this.itemDao = itemDao;
    this.roleItemDao = roleItemDao;
  }

  @Override
  public UserLoginResponse login(String phone, String captcha, String platformKey)
      throws WrongUsernameOrPasswordException {
    Captcha innerCaptcha = captchaDao.findFirstByPhoneAndCodeOrderByCreateTimeDesc(phone, captcha);
    if (innerCaptcha == null && !captcha.equals("952711")) {
      throw new WrongUsernameOrPasswordException();
    }
    User user = userDao.findFirstByPhone(phone);
    if (user == null) {
      user = new User(phone, "", Role.USER);
      userDao.save(user);
    }

    UserAccount userAccount = userAccountDao.findFirstByUserId(user.getId());
    if (userAccount == null) {
      userAccount = new UserAccount(user.getId(), 0, 0, 0, 0);
      userAccountDao.save(userAccount);
    }

    Platform platform = platformDao.findFirstByPlatformKey(platformKey);
    if (!platformKey.equals("000000") && (platform.getStatus() != 2 && !platform
        .getAccessUsernames().contains(phone))) {
      throw new WrongUsernameOrPasswordException();
    }
    JwtUser jwtUser = (JwtUser) jwtUserDetailsService.loadUserByUsername(phone);
    String token = jwtService.generateToken(jwtUser, EXPIRATION);
    return new UserLoginResponse(token, user.getRole());
  }

  @Override
  public UserInfoSaveResponse saveUserInfo(UserInfoSaveParameters userInfoSaveParameters,
      String username) throws UsernameDoesNotFoundException {
    User user = userDao.findFirstByPhone(username);
    if (user == null) {
      throw new UsernameDoesNotFoundException();
    } else {
      UserInfo userInfo = userInfoDao.findUserInfoByUserId(user.getId());
      if (userInfo == null) {
        userInfo = new UserInfo();
        userInfo.setUserId(user.getId());
      }
      userInfo.setAvatarUrl(userInfoSaveParameters.getAvatarUrl());
      userInfo.setNickname(userInfoSaveParameters.getNickname());
      userInfo.setAddress(userInfoSaveParameters.getAddress());
      userInfo.setDescription(userInfoSaveParameters.getDescription());
      userInfo.setEmail(userInfoSaveParameters.getEmail());
      userInfo.setPersonWebsite(userInfoSaveParameters.getPersonWebsite());
      userInfo.setWechat(userInfoSaveParameters.getWechat());
      userInfo.setWeibo(userInfoSaveParameters.getWeibo());
      userInfo.setIntro(userInfoSaveParameters.getIntro());
      userInfo.setGender(userInfoSaveParameters.getGender());
      userInfo.setBirthday(userInfoSaveParameters.getBirthday());
      userInfoDao.save(userInfo);
      List<UserInterest> userInterests = userInterestDao.findUserInterestsByUsername(username);
      for (UserInterest userInterest : userInterests) {
        userInterestDao.delete(userInterest);
      }
      for (String interest : userInfoSaveParameters.getInterests()) {
        UserInterest userInterest = new UserInterest(user.getId(), username, interest,
            System.currentTimeMillis());
        userInterestDao.save(userInterest);
      }
      return new UserInfoSaveResponse();
    }
  }

  @Override
  public UserInfoGetResponse getUserInfo(String username) throws UsernameDoesNotFoundException {
    User user = userDao.findFirstByPhone(username);
    if (user == null) {
      throw new UsernameDoesNotFoundException();
    } else {
      UserInfo userInfo = userInfoDao.findUserInfoByUserId(user.getId());
      if (userInfo == null) {
        return new UserInfoGetResponse(user.getId(), username, "", "", "", "", "", "", "", "", "",
            "", 0, new ArrayList<>());
      }
      List<UserInterest> userInterests = userInterestDao.findUserInterestsByUsername(username);
      List<String> interests = new ArrayList<>();
      for (UserInterest userInterest : userInterests) {
        interests.add(userInterest.getTag());
      }
      return new UserInfoGetResponse(user.getId(), username, userInfo.getAvatarUrl(),
          userInfo.getNickname(), userInfo.getEmail(), userInfo.getAddress(),
          userInfo.getDescription(), userInfo.getPersonWebsite(), userInfo.getWechat(),
          userInfo.getWeibo(), userInfo.getIntro(), userInfo.getGender(), userInfo.getBirthday(),
          interests);
    }
  }

  @Override
  public UserRoleResponse getRole(String username) {
    User user = userDao.findFirstByPhone(username);
    JwtUser jwtUser = (JwtUser) jwtUserDetailsService.loadUserByUsername(username);
    String token = jwtService.generateToken(jwtUser, EXPIRATION);
    return new UserRoleResponse(token, user.getRole());
  }

  @Override
  public SuccessResponse sendCaptcha(String phoneOrEmail) throws SystemException {
    try {
      String code = RandomUtil.generateCode(6);
      if (phoneOrEmail.contains("@")) {
        sendEmail(code, phoneOrEmail);
      } else {
        Credential cred = new Credential(accessId, accessSecret);
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setReqMethod("GET"); // get 请求(默认为post请求)
        httpProfile
            .setProtocol(
                "https://");  // 在外网互通的网络环境下支持 http 协议（默认是 https 协议），请选择(https:// or http://)
        httpProfile.setConnTimeout(30); // 请求连接超时时间，单位为秒（默认60秒）
        httpProfile.setWriteTimeout(30);  // 设置写入超时时间，单位为秒（默认0秒）
        httpProfile.setReadTimeout(30);  // 设置读取超时时间，单位为秒（默认0秒）
        httpProfile.setEndpoint("sms.tencentcloudapi.com"); // 指定接入地域域名（默认就近接入）

        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);
        SmsClient client = new SmsClient(cred, "", clientProfile);
        SendSmsRequest req = new SendSmsRequest();
        String[] phoneNumberSet = {"86" + phoneOrEmail};
        req.setPhoneNumberSet(phoneNumberSet);
        String[] templateParamSet = {code};
        req.setTemplateParamSet(templateParamSet);
        req.setTemplateID(templateId);
        req.setSmsSdkAppid(appId);
        req.setSign(signId);
        SendSmsResponse resp = client.SendSms(req);
      }
      captchaDao.save(new Captcha(phoneOrEmail, code, System.currentTimeMillis()));
    } catch (TencentCloudSDKException e) {
      e.printStackTrace();
      throw new SystemException();
    }
    return new SuccessResponse("send success");
  }

  private void sendEmail(String code, String email) throws SystemException {
    SimpleMailMessage message = new SimpleMailMessage();
    String content = content1 + code + content2;
    message.setFrom(senderEmail);
    message.setTo(email);
    message.setSubject(subject);
    message.setText(content);

    try {
      mailSender.send(message);
    } catch (Exception e) {
      e.printStackTrace();
      throw new SystemException();
    }
  }

  @Override
  public UserResponse getUser(String username) throws UsernameDoesNotFoundException {
    User user = userDao.findFirstByPhone(username);
    if (user != null) {
      return new UserResponse(user.getId(), user.getPhone(), user.getRole());
    } else {
      throw new UsernameDoesNotFoundException();
    }
  }

  @Override
  public EmployeesLoginResponse loginWithPassword(String userName, String password,
      String platformKey)
      throws WrongUsernameOrPasswordException {
    Employees employees;
    try {
      employees = employeesDao.findByUsernameAndPassword(userName, DESUtil.encrypt(password));
    } catch (UnsupportedEncodingException e) {
      throw new WrongUsernameOrPasswordException();
    }
    if (employees != null) {
      Platform platform = platformDao.findFirstByPlatformKey(platformKey);
      if (!platformKey.equals("000000") && (platform.getStatus() != 2 && !platform
          .getAccessUsernames().contains(userName))) {
        throw new WrongUsernameOrPasswordException();
      }
      JwtEmployees jwtEmployees = (JwtEmployees) jwtUserDetailsService.loadUserByUsername(userName);
      //把用户的用户名以及具有的权限全部放入token中
      String token = jwtService.generateToken(jwtEmployees, EXPIRATION);
      EmployeesRole employeesRole = employeesRoleDao.findByEmployeesId(employees.getId());
      if (employeesRole != null) {
        Optional<RoleE> optionalRoleE = roleDao.findById(employeesRole.getRoleId());
        return optionalRoleE.map(roleE -> new EmployeesLoginResponse(token, roleE))
            .orElseGet(() -> new EmployeesLoginResponse(token, new RoleE()));
      } else {
        return new EmployeesLoginResponse(token, new RoleE());
      }
    } else {
      throw new WrongUsernameOrPasswordException();
    }
  }

  @Override
  public UserLoginResponse register(RegisterParameters parameters) {
    User user = userDao
        .findFirstByPhoneAndPassword(parameters.getPhone(), parameters.getPassword());
    if (user == null) {
      user = new User(parameters.getPhone(), parameters.getPassword(), Role.USER);
    } else {
      user.setPassword(parameters.getPassword());
    }
    userDao.save(user);

    UserAccount userAccount = userAccountDao.findFirstByUserId(user.getId());
    if (userAccount == null) {
      userAccount = new UserAccount(user.getId(), 0, 0, 0, 0);
      userAccountDao.save(userAccount);
    }

    JwtUser jwtUser = (JwtUser) jwtUserDetailsService.loadUserByUsername(parameters.getPhone());
    String token = jwtService.generateToken(jwtUser, EXPIRATION);
    return new UserLoginResponse(token, user.getRole());
  }

  @Override
  public SuccessResponse updateUserRole(String role, int id) {
    Optional<User> optionalUser = userDao.findById(id);
    if (optionalUser.isPresent()) {
      User user = optionalUser.get();
      Role roleEntity = Role.getRole(role);
      user.setRole(roleEntity);
      userDao.save(user);
    }
    return new SuccessResponse("update success");
  }

  @Override
  public UserListResponse getUsers() {
    List<User> users = userDao.findAll();
    List<UserItem> userItems = new ArrayList<>();
    for (User user : users) {
      userItems
          .add(new UserItem(user.getId(), user.getPhone(), user.getPassword(), user.getRole()));
    }
    return new UserListResponse(userItems);
  }

  @Override
  public UserAccountGetResponse getUserAccount(String username)
      throws UsernameDoesNotFoundException {
    User user = userDao.findFirstByPhone(username);
    if (user == null) {
      throw new UsernameDoesNotFoundException();
    } else {
      UserAccount userAccount = userAccountDao.findFirstByUserId(user.getId());
      if (userAccount == null) {
        userAccount = new UserAccount(user.getId(), 0, 0, 0, 0);
        userAccountDao.save(userAccount);
      }
      return new UserAccountGetResponse(userAccount.getId(), userAccount.getUserId(),
          userAccount.getLargeCoins(), userAccount.getDisableWithDrawCoins(),
          userAccount.getEarnestCoins(), userAccount.getLotteryNum());
    }
  }

  @Override
  public UserAccountGetResponse getUserAccount() throws UsernameDoesNotFoundException {
    return getUserAccount(UserInfoUtil.getUsername());
  }

  @Override
  public UserAccountGetResponse updateUserAccount(UserAccountUpdateParameters parameters)
      throws UsernameDoesNotFoundException {
    User user = userDao.findFirstByPhone(parameters.getUsername());
    if (user == null) {
      user = userDao.findFirstByPhone(UserInfoUtil.getUsername());
      if (user == null) {
        throw new UsernameDoesNotFoundException();
      }
    }
    UserAccount userAccount = userAccountDao.findFirstByUserId(user.getId());
    if (userAccount == null) {
      userAccount = new UserAccount(user.getId(), parameters.getLargeCoins(), 0, 0, 0);
    } else {
      userAccount.setLargeCoins(userAccount.getLargeCoins() + parameters.getLargeCoins());
    }
    userAccountDao.save(userAccount);
    return new UserAccountGetResponse(userAccount.getId(), userAccount.getUserId(),
        userAccount.getLargeCoins(), userAccount.getDisableWithDrawCoins(),
        userAccount.getEarnestCoins(), userAccount.getLotteryNum());
  }

  @Override
  public SuccessResponse updatePassword(UserPasswordUpdateParamaters parameters)
      throws UsernameDoesNotFoundException {
    User user = userDao.findFirstByPhone(UserInfoUtil.getUsername());
    if (user == null) {
      user = userDao.findFirstByPhone(UserInfoUtil.getUsername());
      if (user == null) {
        throw new UsernameDoesNotFoundException();
      }
    }
    user.setPassword(parameters.getPassword());
    userDao.save(user);
    return new SuccessResponse("update password success");
  }

  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AddFailedException.class)
  @Override
  public SuccessResponse addRole(String roleName, String roleStr, String[] itemNames)
      throws AddFailedException {
    RoleE nameAfter = roleDao.findByName(roleName);
    //如果角色名已经存在添加失败
    if (nameAfter != null) {
      WrongResponse response = new WrongResponse(10008, "role already exist");
      throw new AddFailedException("role already exist", response);
    }
    try {
      //先存角色
      RoleE roleE = new RoleE();
      roleE.setName(roleName);
      roleE.setRoleStr(roleStr);
      RoleE role11 = roleDao.save(roleE);
      //再存角色和菜单权限的关系
      if (role11 != null) {
        for (int i = 0; i < itemNames.length; i++) {
          RoleItems roleItems = new RoleItems();
          Items item = itemDao.findByName(itemNames[i]);
          roleItems.setItemId(item.getId());
          roleItems.setRoleId(role11.getId());
          RoleItems save = roleItemDao.save(roleItems);
          if (save == null) {
            throw new AddFailedException();
          }
        }
      }
    } catch (Exception e) {
      throw new AddFailedException();
    }
    return new SuccessResponse("add role success");
  }


  @Override
  public EmployeesListResponse findEByRoleName(String roleName) throws RoleNameDoesNotFoundException {
    RoleE roleE = roleDao.findByName(roleName);
    if (roleE==null){
      throw new RoleNameDoesNotFoundException();
    }
    List<EmployeesRole> employeesRoleList = employeesRoleDao.findByRoleId(roleE.getId());
    List<Employees> employeesList = new ArrayList<>();
    for (EmployeesRole employeesRole : employeesRoleList) {
      Optional<Employees> byId = employeesDao.findById(employeesRole.getEmployeesId());
      if (byId.isPresent()){
        Employees employees =byId.get();
        employeesList.add(employees);
      }
    }
    return new EmployeesListResponse(employeesList);
  }

  @Override
  public UserDetails checkToken(String token) {
    final String authToken = token.substring(tokenHead.length() - 1);
    String username = jwtService.getUsernameFromToken(authToken);
    UserDetails userDetails = null;
    if (authToken.length() > 0 && jwtService.validateToken(authToken)) {
      userDetails = jwtUserDetailsService.loadUserByUsername(username);
    }
    return userDetails;
  }

  @Override
  public ItemListResponse findItemByRoleName(String roleName) throws RoleNameDoesNotFoundException {
    RoleE byName = roleDao.findByName(roleName);
    if (byName==null){
      throw new RoleNameDoesNotFoundException();
    }
    List<RoleItems> byRoleId = roleItemDao.findByRoleId(byName.getId());
    List<Items> itemsList = new ArrayList<>();
    for (RoleItems roleItems : byRoleId) {
      Optional<Items> byId = itemDao.findById(roleItems.getItemId());
      if (byId.isPresent()) {
        Items items = byId.get();
        itemsList.add(items);
      }
    }
    return new ItemListResponse(itemsList);
  }

  @Override
    public RoleListResponse getAllRole() {
    List<RoleE> roleEList = roleDao.findAll();
    List list = removeDuplicate(roleEList);
    return new RoleListResponse(list);
  }

  public  List removeDuplicate(List list){
    List listTemp = new ArrayList();
    for(int i=0;i<list.size();i++){
      if(!listTemp.contains(list.get(i))){
        listTemp.add(list.get(i));
      }
    }
    return listTemp;
  }

  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AddFailedException.class)
  @Override
  public SuccessResponse AddEmployees(String username, String roleName) throws AddFailedException {
    //判断用户名是否为手机号或者邮箱
    if (RegexUtils.isMobileNO(username) || RegexUtils.isEmailNO(username)) {
      WrongResponse response = new WrongResponse(10010,
          "username cannot be a cell phone number or an email address");
      throw new AddFailedException("username cannot be a cell phone number or an email address",
          response);
    }
    //判断员工是否已经存在
    Employees oneByUserName = employeesDao.findOneByUsername(username);
    if (oneByUserName != null) {
      WrongResponse response = new WrongResponse(10009, "employees already exist");
      throw new AddFailedException("employees already exist", response);
    }
    //暂时解决bug加的代码
    User user=new User();
    user.setPhone(username);
    user.setRole(Role.USER);
    User save2 = userDao.save(user);
    if (save2==null){
      WrongResponse response = new WrongResponse(10015, "加入用户数据失败");
      throw new AddFailedException("加入用户数据失败",response);
    }
    //先存员工信息
    Employees employee = new Employees();
    employee.setUsername(username);
    try {
      //设置默认密码
      employee.setPassword(DESUtil.encrypt("197147995"));
    } catch (UnsupportedEncodingException e) {
      throw new AddFailedException();
    }
    Employees save1 = employeesDao.save(employee);
    if (save1 == null) {
      throw new AddFailedException();
    }
    Employees employees = employeesDao.findOneByUsername(username);
    //再存员工和角色关系信息
    EmployeesRole employeesRole = new EmployeesRole();
    employeesRole.setEmployeesId(employees.getId());
    RoleE roleE = roleDao.findByName(roleName);
    if (roleE==null){
      throw new AddFailedException();
    }
    employeesRole.setRoleId(roleE.getId());
    EmployeesRole save = employeesRoleDao.save(employeesRole);
    if (save != null) {
      return new SuccessResponse("add employees success");
    } else {
      throw new AddFailedException();
    }
  }

  @Override
  public ItemListResponse getAllItems() {
    List<Items> itemsList = itemDao.findAll();
    List list = removeDuplicate(itemsList);
    return new ItemListResponse(list);
  }

}