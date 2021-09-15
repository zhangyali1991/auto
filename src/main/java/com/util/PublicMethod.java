package com.util;


import java.util.List;

public class PublicMethod {
    public static String getResult(List list, String message){

        Object[] arr=list.toArray();
        for (int i=0;i<arr.length;i++){
            if(!arr[i].equals(message)){
              return "失败，联系管理员";
            }

        }
        return message;
    }
}
