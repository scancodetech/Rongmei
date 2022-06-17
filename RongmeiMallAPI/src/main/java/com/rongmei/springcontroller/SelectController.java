package com.rongmei.springcontroller;

import com.rongmei.blservice.select.SelectBlService;
import com.rongmei.entity.select.Select;
import com.rongmei.response.Response;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.WrongResponse;
import com.rongmei.response.select.SelectResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "select")
public class SelectController {

    private final SelectBlService selectBlService;

    @Autowired
    public SelectController(SelectBlService selectBlService) {
        this.selectBlService = selectBlService;
    }

    @ApiOperation(value = "获取下拉框", notes = "获取下拉框")
    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SelectResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> getSelect() {
        return new ResponseEntity<>(
                selectBlService.getSelect(), HttpStatus.OK);
    }
    @PostMapping
    @ApiOperation(value = "新增下拉框", notes = "新增下拉框")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> submitSelect(
            @RequestBody Select select) {
        return new ResponseEntity<>(
                selectBlService.submitSelect(select), HttpStatus.OK);
    }
    @PutMapping
    @ApiOperation(value = "修改下拉框", notes = "修改下拉框")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> putSelect(
            @RequestBody Select select) {
        return new ResponseEntity<>(
                selectBlService.putSelect(select), HttpStatus.OK);
    }
    @DeleteMapping
    @ApiOperation(value = "删除下拉框", notes = "删除下拉框")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> deleteSelect(
            @RequestParam("id") Integer id) {
        return new ResponseEntity<>(
                selectBlService.deleteSelect(id), HttpStatus.OK);
    }


    @GetMapping("/getById")
    @ApiOperation(value = "根据ID获取", notes = "根据ID获取")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Select.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> getById(
            @RequestParam("id") Integer id) {
        return new ResponseEntity<>(
                selectBlService.getById(id), HttpStatus.OK);
    }

}
