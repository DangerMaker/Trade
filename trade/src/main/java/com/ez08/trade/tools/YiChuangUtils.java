package com.ez08.trade.tools;

public class YiChuangUtils {

    public static String getMoneyType(String type) {
        if (type.equals("0")) {
            return "人民币";
        } else if (type.equals("1")) {
            return "港元";
        } else if (type.equals("2")) {
            return "美元";
        } else {
            return "未知币种";
        }
    }

    public static String getTime(String time) {
        if (time.length() == 6 || time.length() == 8) {
            String hour = time.substring(0, 2);
            String min = time.substring(2, 4);
            String sec = time.substring(4, 6);
            return hour + ":" + min + ":" + sec;
        }
        return "";
    }

    public static String getTransferStatus(String type){
        if (type.equals("0")) {
            return "未报";
        } else if (type.equals("1")) {
            return "已报";
        } else if (type.equals("2")) {
            return "成功";
        } else if (type.equals("3")) {
            return "失败";
        }else if (type.equals("4")) {
            return "超时";
        }else if (type.equals("5")) {
            return "待冲正";
        }else if (type.equals("6")) {
            return "已冲正";
        }else if (type.equals("7")) {
            return "调整为成功";
        }else if (type.equals("8")) {
            return "调整为失败";
        }else {
            return "未知";
        }
    }

    public static String getZhongqianStatus(String type) {
        if (type.equals("0")) {
            return "新股中签";
        } else if (type.equals("1")) {
            return "中签缴款";
        } else if (type.equals("2")) {
            return "中签确认";
        } else {
            return "未知状态";
        }
    }

    public static String getMarketByTag(String tag){
        if(tag.equals("0") || tag.equals("2") || tag.equals("")){
            return "SZHQ";
        }else{
            return "SHHQ";
        }
    }

    public static String getBSStringByTag(String tag){
        if(tag.equals("B")){
            return "买入";
        }else{
            return "卖出";
        }

    }

}
