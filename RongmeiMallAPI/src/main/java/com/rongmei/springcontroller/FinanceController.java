package com.rongmei.springcontroller;

import com.rongmei.blservice.finance.FinanceService;
import com.rongmei.dao.auction.AuctionDao;
import com.rongmei.dao.auction.SaleDao;
import com.rongmei.dao.auction.ThingDao;
import com.rongmei.dao.finance.FinanceDao;
import com.rongmei.dao.pcuser.PcUserDao;
import com.rongmei.entity.finance.Finance;
import com.rongmei.parameters.finance.FinanceParameters4MyAccount;
import com.rongmei.response.Response;
import com.rongmei.response.WrongResponse;
import com.rongmei.response.auction.AuctionHistoryGetResponse;
import com.rongmei.response.finance.FinanceResponse;
import com.rongmei.threads.AuctionThread;
import com.rongmei.util.MoneyUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping(value = "finance")
public class FinanceController {
    private final FinanceService financeService;
    private final SaleDao saleDao;
    private final AuctionDao auctionDao;
    private final ThingDao thingDao;
    private final FinanceDao financeDao;
    private final PcUserDao pcUserDao;




    @Autowired
    public FinanceController(FinanceService financeService,SaleDao saleDao, AuctionDao auctionDao,
                              ThingDao thingDao, FinanceDao financeDao,PcUserDao pcUserDao){
        this.financeService = financeService;
        this.saleDao = saleDao;
        this.auctionDao = auctionDao;
        this.thingDao = thingDao;
        this.financeDao = financeDao;
        this.pcUserDao = pcUserDao;
    }



    @PreAuthorize("hasAuthority('FINANCIALDETAILS')")
    @ApiOperation(value = "获取财务流水", notes = "获取财务流水")
    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = FinanceResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> getAuctionHistory(
            String type,
            String transactionType,
            Long startTime,
            Long endTime,
            Integer time,
            Integer page,
            Integer size
    ) {
        if(StringUtils.isEmpty(type)){
            type = "竞品";
        }

        if(StringUtils.isEmpty(transactionType)){
            transactionType = "收入";
        }

        if(StringUtils.isEmpty(page)){
            page = 1;
        }

        if(StringUtils.isEmpty(size)){
            size = 10;
        }
        return new ResponseEntity<>(financeService.findBySome(type,transactionType,startTime,endTime,time,page,size), HttpStatus.OK);
    }



    @PreAuthorize("hasAuthority('FINANCIALDETAILS')")
    @GetMapping("/save")
    public Response aaaa(
            @RequestParam("startPrice") Double startPrice ,
            @RequestParam("username") String username,
            Double margin
            ) {
        return financeService.saveByserciceCash(startPrice,username,margin);
    }

    @PreAuthorize("hasAuthority('FINANCIALDETAILS')")
    @PostMapping
    public Response create(@RequestBody Finance finance) {
        return financeService.save(finance);
    }

    @PreAuthorize("hasAuthority('FINANCIALDETAILS')")
    @GetMapping("/margin")
    public Response save(
            @RequestParam("margin") Double margin ,
            @RequestParam("username") String username
    ) {
        return financeService.saveByserciceCash1(margin,username);
    }

    @PreAuthorize("hasAuthority('FINANCIALDETAILS')")
    @GetMapping("/findByUserAccount")
    public Response findByUserAccount(@ModelAttribute(binding = false) FinanceParameters4MyAccount financeParameters4MyAccount,
                                      @PageableDefault(sort = "createTime",direction = Sort.Direction.DESC,page = 1)Pageable pageable) {
        return financeService.findByUserAccount(financeParameters4MyAccount,pageable);
    }

    @PreAuthorize("hasAuthority('FINANCIALDETAILS')")
    @GetMapping("/find/{id}")
    public Response findByUserAccount(@PathVariable("id") long id) {
        return financeService.findById(id);
    }

}
