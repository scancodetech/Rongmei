package com.rongmei.blservice.account;

import com.rongmei.entity.account.Items;
import com.rongmei.exception.*;
import com.rongmei.parameters.user.RegisterParameters;
import com.rongmei.parameters.user.UserAccountUpdateParameters;
import com.rongmei.parameters.user.UserInfoSaveParameters;
import com.rongmei.parameters.user.UserPasswordUpdateParamaters;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.employees.EmployeesListResponse;
import com.rongmei.response.employees.EmployeesLoginResponse;
import com.rongmei.response.item.ItemListResponse;
import com.rongmei.response.role.RoleListResponse;
import com.rongmei.response.user.UserAccountGetResponse;
import com.rongmei.response.user.UserInfoGetResponse;
import com.rongmei.response.user.UserInfoSaveResponse;
import com.rongmei.response.user.UserListResponse;
import com.rongmei.response.user.UserLoginResponse;
import com.rongmei.response.user.UserResponse;
import com.rongmei.response.user.UserRoleResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserBlService {

  UserLoginResponse login(String phone, String captcha, String platformKey)
      throws WrongUsernameOrPasswordException;

  UserInfoSaveResponse saveUserInfo(UserInfoSaveParameters userInfoSaveParameters, String username)
      throws UsernameDoesNotFoundException;

  UserInfoGetResponse getUserInfo(String username) throws UsernameDoesNotFoundException;

  UserRoleResponse getRole(String username);

  SuccessResponse sendCaptcha(String phone) throws SystemException;

  UserResponse getUser(String username) throws UsernameDoesNotFoundException;

  EmployeesLoginResponse loginWithPassword(String phone, String password, String platformKey)
          throws WrongUsernameOrPasswordException;

  UserLoginResponse register(RegisterParameters parameters) throws UsernameDoesNotFoundException;

  SuccessResponse updateUserRole(String role, int id);

  UserListResponse getUsers();

  UserAccountGetResponse getUserAccount(String username) throws UsernameDoesNotFoundException;

  UserAccountGetResponse getUserAccount() throws UsernameDoesNotFoundException;

  UserAccountGetResponse updateUserAccount(UserAccountUpdateParameters parameters)
          throws UsernameDoesNotFoundException;

  SuccessResponse updatePassword(UserPasswordUpdateParamaters parameters)
          throws UsernameDoesNotFoundException;

  RoleListResponse getAllRole();

  ItemListResponse getAllItems();

  SuccessResponse AddEmployees(String username, String roleId) throws AddFailedException;

  SuccessResponse addRole(String roleName, String roleStr, String[] itemNames) throws AddFailedException;

  EmployeesListResponse findEByRoleName(String roleName) throws RoleNameDoesNotFoundException;

  UserDetails checkToken(String token);

  ItemListResponse findItemByRoleName(String roleName) throws RoleNameDoesNotFoundException;
}
