package com.rongmei.parameters.finance;

import com.rongmei.util.ann.Query;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
@Getter
@Setter
public class FinanceParameters4MyAccount {

    //@NotBlank(message = "用户名不能为空")
    @Query(type = Query.Type.EQUAL)
    private String username;
    @Query(type = Query.Type.INNER_LIKE)
    private String transactionType;
    @Query(type = Query.Type.SINCE)
    private Long createTime;

}
