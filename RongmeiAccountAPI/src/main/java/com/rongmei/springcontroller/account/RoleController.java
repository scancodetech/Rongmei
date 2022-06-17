package com.rongmei.springcontroller.account;

import com.rongmei.blservice.account.UserBlService;
import com.rongmei.exception.AddFailedException;
import com.rongmei.exception.RoleNameDoesNotFoundException;
import com.rongmei.parameters.user.AddEmployeesParameters;
import com.rongmei.parameters.user.AddRoleParameters;
import com.rongmei.response.Response;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.WrongResponse;
import com.rongmei.response.employees.EmployeesListResponse;
import com.rongmei.response.item.ItemListResponse;
import com.rongmei.response.role.RoleListResponse;
import com.rongmei.response.user.UserLoginResponse;
import com.rongmei.security.jwt.JwtEmployees;
import com.rongmei.security.jwt.JwtUser;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "role")
public class RoleController {
    private final UserBlService userBlService;

    @Autowired
    public RoleController(UserBlService userBlService) {
        this.userBlService = userBlService;
    }
    @PreAuthorize("hasAnyAuthority('ROLE','EMPLOYEES')")
    @ApiOperation(value = "查询所有的角色", notes = "查询显示所有的角色")
    @RequestMapping(value = "getAllRole", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = UserLoginResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> getAllRole() {
        RoleListResponse roleListResponse= userBlService.getAllRole();
        return new ResponseEntity<>(roleListResponse, HttpStatus.OK);

    }

    @PreAuthorize("hasAuthority('ROLE')")
    @ApiOperation(value = "查询所有的权限菜单", notes = "查询显示所有的权限菜单")
    @RequestMapping(value = "getAllItem", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = UserLoginResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> getAllItem() {
        ItemListResponse itemListResponse = userBlService.getAllItems();
        return new ResponseEntity<>(itemListResponse, HttpStatus.OK);

    }

    @PreAuthorize("hasAuthority('ROLE')")
    @ApiOperation(value = "添加角色", notes = "添加角色返回是否添加成功")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleName", value = "角色名称", required = true, dataType = "String"),
            @ApiImplicitParam(name = "roleStr", value = "角色描述", required = true, dataType = "String")
    })
    @PostMapping(value = "add",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = UserLoginResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> addRole(
            @RequestBody AddRoleParameters roleParameters
    )
    {
        try {
            SuccessResponse successResponse = userBlService.addRole(roleParameters.getRoleName(),
                    roleParameters.getRoleStr(),roleParameters.getItemNames()
                    );
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        }
        catch (AddFailedException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getResponse(),HttpStatus.UNAUTHORIZED);
        }
    }

    @PreAuthorize("hasAuthority('EMPLOYEES')")
    @ApiOperation(value = "通过角色名称查询相关员工", notes = "通过角色名称查询显示所有相关员工")
    @RequestMapping(value = "findEByRoleName", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = UserLoginResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> findEByRoleName(@RequestParam(value = "roleName") String roleName) {
        EmployeesListResponse employeesListResponse= null;
        try {
            employeesListResponse = userBlService.findEByRoleName(roleName);
            return new ResponseEntity<>(employeesListResponse, HttpStatus.OK);
        } catch (RoleNameDoesNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getResponse(),HttpStatus.NOT_FOUND);
        }

    }

    @PreAuthorize("hasAuthority('EMPLOYEES')")
    @ApiOperation(value = "通过角色名称查询角色具有的相关权限", notes = "通过角色名称查询角色具有的相关权限")
    @RequestMapping(value = "findItemsByRoleName", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = UserLoginResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> findItemByRoleName(@RequestParam(value = "roleName") String roleName) {
        ItemListResponse itemListResponse= null;
        try {
            itemListResponse = userBlService.findItemByRoleName(roleName);
            return new ResponseEntity<>(itemListResponse, HttpStatus.OK);
        } catch (RoleNameDoesNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getResponse(),HttpStatus.NOT_FOUND);
        }

    }

    @PreAuthorize("hasAuthority('EMPLOYEES')")
    @ApiOperation(value = "新增员工账号", notes = "创建一个新的员工")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "员工账号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "roleId", value = "角色id", required = true, dataType = "int")
    })
    @PostMapping(value = "AddEmployees",produces = MediaType.APPLICATION_JSON_VALUE)

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = UserLoginResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> AddEmployees(
            @RequestBody AddEmployeesParameters employeesParameters
    ) {
        try {

            SuccessResponse successResponse = userBlService.AddEmployees(employeesParameters.getUsername(), employeesParameters.getRoleName());
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        }
        catch (AddFailedException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getResponse(), HttpStatus.UNAUTHORIZED);
        }
    }

    @ApiOperation(value = "通过token来判断员工的权限", notes = "通过token来判断员工的权限")
    @RequestMapping(value = "checkToken", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = UserLoginResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<JwtEmployees> checkToken(@RequestParam(value = "token") String token) {
        UserDetails userDetails=userBlService.checkToken(token);
        JwtEmployees jwtEmployees=null;
        try {
            jwtEmployees =(JwtEmployees)userDetails;
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(jwtEmployees, HttpStatus.OK);
        }
        return new ResponseEntity<>(jwtEmployees, HttpStatus.OK);

    }
    @ApiOperation(value = "通过token来获取用户的权限", notes = "通过token来获取用户的权限")
    @RequestMapping(value = "checkUserToken", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = UserLoginResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<JwtUser> checkUserToken(@RequestParam(value = "token") String token) {
        UserDetails userDetails=userBlService.checkToken(token);
        return new ResponseEntity<>((JwtUser)userDetails, HttpStatus.OK);
    }

}
