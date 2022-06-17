package com.rongmei.bl.select;

import com.google.common.collect.Lists;
import com.rongmei.blservice.select.SelectBlService;
import com.rongmei.dao.finance.FinanceDao;
import com.rongmei.dao.select.SelectDao;
import com.rongmei.entity.finance.Finance;
import com.rongmei.entity.select.Select;
import com.rongmei.response.Response;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.WrongResponse;
import com.rongmei.response.select.SelectItem;
import com.rongmei.response.select.SelectResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class SelectImpl implements SelectBlService {


    private final SelectDao selectDao;
    private final FinanceDao financeDao;


    @Autowired
    public SelectImpl (SelectDao selectDao,FinanceDao financeDao){
        this.selectDao = selectDao;
        this.financeDao = financeDao;
    }


    @Override
    public Response getSelect() {
        List<Select> all = selectDao.findAllByOrderBySortDesc();
        LinkedList<SelectItem> objects = Lists.newLinkedList();
        for (Select select : all) {
            SelectItem selectItem = new SelectItem();
            selectItem.setTitle(select.getTitle());
            selectItem.setValue(select.getValue());
            objects.add(selectItem);
        }
        return new SelectResponse(objects);
    }

    @Override
    public Response submitSelect(Select select) {
        if(select != null){
            selectDao.save(select);
            return new  SuccessResponse("add success");
        }
        return new WrongResponse(10004,"select id is not exits");
    }

    @Override
    public Response putSelect(Select select) {
        if(select != null){
            Optional<Select> byId = selectDao.findById(select.getId());
            if(!byId.isPresent()){
                return new WrongResponse(10004,"select id is not exits");
            }
            String value = byId.get().getValue();
        Finance firstByType = financeDao.findFirstByType(value);
        if(firstByType != null){
            return new WrongResponse(10004,"请先清除关联数据");
        }else {
                selectDao.save(select);
            return new  SuccessResponse("add success");

            }
        }
        return new WrongResponse(10004,"select id is not exits");
    }

    @Override
    public Response deleteSelect(Integer id) {
        Optional<Select> byId = selectDao.findById(id);
        if(byId.isPresent()){
            String value = byId.get().getValue();
            Finance firstByType = financeDao.findFirstByType(value);
            if(firstByType != null){
                return new WrongResponse(10004,"请先清除关联数据");
            }else{
                selectDao.deleteById(id);
                return new  SuccessResponse("add success");
            }

        }
        return new WrongResponse(10004,"select id is not exits");
    }

    @Override
    public Response getById(Integer id) {
        if(id != null){
            Optional<Select> byId = selectDao.findById(id);
            if(byId.isPresent()){
                return  new GetSelectResponse(byId.get(),"get success");
            }
        }
        return new WrongResponse(10004,"select id is not exits");
    }
}
