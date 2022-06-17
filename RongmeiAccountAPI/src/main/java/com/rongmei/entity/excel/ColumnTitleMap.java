package com.rongmei.entity.excel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ColumnTitleMap {
    private Map<String, String> columnTitleMap = new HashMap<String, String>();
    private ArrayList<String> titleKeyList = new ArrayList<String> ();

    public ColumnTitleMap(String datatype) {
        switch (datatype) {
            case "excel":
                initUserInfoColu();
                initUserInfoTitleKeyList();
                break;
            default:
                break;
        }

    }
    /**
     * mysql用户表需要导出字段--显示名称对应集合
     */
    private void initUserInfoColu() {
        columnTitleMap.put("id","ID");
        columnTitleMap.put("title","标头");
        columnTitleMap.put("largePrice","起拍价格");
        columnTitleMap.put("coverUrl","略缩图");
        columnTitleMap.put("tags", "标签");
        columnTitleMap.put("contentUrl","内容链接");
        columnTitleMap.put("description","描述");
        columnTitleMap.put("signingInfo","签约信息");
        columnTitleMap.put("extra","额外");
        //columnTitleMap.put("creatorUserGroupId",userGroupItems.get(0).getId()+"");
        columnTitleMap.put("downloadUrl","下载路径");
//        columnTitleMap.put("createTime","创建时间");
//        columnTitleMap.put("updateTime","更新时间");
        columnTitleMap.put("isExclusive","是否签约");
        columnTitleMap.put("author","作者");
        //columnTitleMap.put("draftStatus","");


    }

    private   String splist(String url){
        int i = url.lastIndexOf("/");
        String substring = url.substring(i+1);
        System.out.println(substring);
        return substring;
    }

    /**
     * mysql用户表需要导出字段集
     */
    private void initUserInfoTitleKeyList() {
        titleKeyList.add("id");
        titleKeyList.add("title");
        titleKeyList.add("largePrice");
        titleKeyList.add("coverUrl");
        titleKeyList.add("tags");
        titleKeyList.add("contentUrl");
        titleKeyList.add("description");
        titleKeyList.add("signingInfo");
        titleKeyList.add("extra");
        //titleKeyList.add("creatorUserGroupId");
        titleKeyList.add("downloadUrl");
        //titleKeyList.add("createTime");
        //titleKeyList.add("updateTime");
        titleKeyList.add("isExclusive");
        titleKeyList.add("author");
        //titleKeyList.add("draftStatus");

    }

    public Map<String, String> getColumnTitleMap() {
        return columnTitleMap;
    }

    public ArrayList<String> getTitleKeyList() {
        return titleKeyList;
    }
}

