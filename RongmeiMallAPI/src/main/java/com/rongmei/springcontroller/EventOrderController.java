package com.rongmei.springcontroller;

import com.rongmei.blservice.event.EventBlService;
import com.rongmei.blservice.event.EventOrderService;
import com.rongmei.blservice.finance.FinanceService;
import com.rongmei.entity.eventorder.EventOrder;
import com.rongmei.entity.finance.Finance;
import com.rongmei.exception.SystemException;
import com.rongmei.response.Response;
import com.rongmei.response.WrongResponse;
import com.rongmei.response.auction.AuctionHistoryGetResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "enentOrder")
public class EventOrderController {

    private final EventOrderService eventOrderService;

    @Autowired
    public EventOrderController(EventOrderService eventOrderService) {
        this.eventOrderService = eventOrderService;
    }




    @ApiOperation(value = "活动财务记录", notes = "活动记录")
    @RequestMapping(method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = AuctionHistoryGetResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> getAuctionHistory(@RequestBody EventOrder eventOrder)  {

        try {
            return new ResponseEntity<>(eventOrderService.save(eventOrder), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new WrongResponse(10002,"支付错误"), HttpStatus.OK);
        }

    }

}
