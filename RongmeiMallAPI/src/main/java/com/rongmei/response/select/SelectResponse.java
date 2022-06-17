package com.rongmei.response.select;

import com.rongmei.entity.select.Select;
import com.rongmei.response.Response;

import java.util.List;

public class SelectResponse extends Response {

   private List<SelectItem> list ;

   public SelectResponse(List<SelectItem> list) {
      this.list = list;
   }
}
