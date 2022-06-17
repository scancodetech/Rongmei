package com.rongmei.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.rongmei.exception.FileSuffixWrongException;

import java.io.File;
import java.util.*;

public class GetFolderFileUtil {


    private static List<String> deleteFailList = new ArrayList<>();


    public static List<String> getDeleteFailList(){
        return deleteFailList;
    }

    /**
     *
     * 获取目录下以及子目录的所有文件名
     */
    public  List<String> getFileNames(String path) {
        ArrayList<String> fileNames = new ArrayList<>();
        File file = new File(path);
        getDirFileNames1(fileNames, file);
        return fileNames;
    }

    /**
     *
     * 获取某个目录下所有直接下级文件
     */
    public  List<String> getDirFileNames1(ArrayList<String> fileNames, File file) {
        File[] tempList = file.listFiles();

        for (int i = 0; i < tempList.length; i++) {
            // 判断是文件还是目录，是文件的话
            if (tempList[i].isFile()) {
                //文件名，不包含路径
                String fileName = tempList[i].getName();
                fileNames.add(fileName);
            }
            // 是目录的话，继续获取该目录下的文件
            if (tempList[i].isDirectory()) {
                //递归查询目录下文件
                getDirFileNames1(fileNames, tempList[i]);
            }
        }
        return fileNames;
    }


    /**
     *
     * 获取某个目录下所有直接下级文件及其目录
     */
    public static  HashMap<String,List<File>> getDirFileNames(File file) throws FileSuffixWrongException {
        File[] tempList = file.listFiles();
        HashMap<String, List<File>> fileHashMap = Maps.newHashMap();
        for (int i = 0; i < tempList.length; i++) {
            // 判断是文件还是目录，是文件的话
            if (tempList[i].isFile()) {
                throw new FileSuffixWrongException("file internal format error");
            }
            // 是目录的话，继续获取该目录下的文件
            if (tempList[i].isDirectory()) {
                //认为已经到了 取数据的文件夹
                //递归查询目录下文件
                System.out.println(tempList[i].getPath());
                File file1 = tempList[i];
                File[] files1 = file1.listFiles();
               /* if(files1.length != 2){
                    throw new FileSuffixWrongException("file internal format error");
                }*/
                if(files1.length == 2 && files1[0].isFile() && files1[1].isFile()){
                    fileHashMap.put(tempList[i].getName(),Arrays.asList(files1));
                }else {
                    HashMap<String, List<File>> dirFileNames = getDirFileNames(tempList[i]);
                    if(dirFileNames.size() != 0){
                        Set<String> keySet = dirFileNames.keySet();
                        for (String key : keySet) {
                            fileHashMap.put(key,dirFileNames.get(key));
                        }
                    }
                }
            }
        }
        return fileHashMap;
    }



    /**
     * 文件是否是图片
     */
    public static Boolean isImage(String fileName ) {
        ArrayList<String> strings = Lists.newArrayList("jpg", "bmp", "gif", "png", "wbmp", "jpeg");
        String aCase = fileName.toLowerCase();
        for (String string : strings) {
            if (aCase.endsWith(string)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 文件是否是图片
     */
    public static Boolean isImageByList(List<File> files) {
        ArrayList<String> strings = Lists.newArrayList( "jpg", "bmp",  "gif",  "png",  "wbmp", "jpeg");
        boolean flag = false;
        for (File file : files) {
            String aCase = file.getName().toLowerCase();
            for (String string : strings) {
                if(aCase.endsWith(string)){
                    flag = true;
                    break;
                }else {
                    flag = false;
                    return flag;
                }
            }
        }
        return flag;
    }

    public static List<File>  comparisonFileSize(List<File> files) {
        Collections.sort(files,(var1 ,var2) -> var1.length() > var2.length() ? 0 : -1);
        return files;
    }

    public static List<File>  reSort(List<File> files) {
        HashMap<Integer, File> integerFileHashMap = new HashMap<>(16);
        ArrayList<File> files1 = Lists.newArrayList();
        for (File file : files) {
            if(isImage(file.getName())){
                integerFileHashMap.put(0,file);
            }else {
                integerFileHashMap.put(1,file);
            }
        }
        files1.add(integerFileHashMap.get(0));
        files1.add(integerFileHashMap.get(1));
        return files1;
    }



    private static int isImageInt(String fileName){

        ArrayList<String> strings = Lists.newArrayList("JPG", "jpg", "bmp", "BMP", "gif", "GIF", "WBMP", "png", "PNG", "wbmp", "jpeg", "JPEG");
        for (String string : strings) {
            if(!fileName.endsWith(string)){
                return -1;
            }
        }
        return 0;
    }



    public static void main(String[] args) throws FileSuffixWrongException {


    }
}
