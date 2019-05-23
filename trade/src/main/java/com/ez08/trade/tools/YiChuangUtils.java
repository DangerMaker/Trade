package com.ez08.trade.tools;

import android.text.TextUtils;

public class YiChuangUtils {

//    NSString *const UNSZQuoteTypes = @"对方最优价格,本方最优价格,即时成交剩余撤销,五档即成剩撤,全额成交或撤销";
//    NSString *const UNSHQuoteTypes = @"五档即成剩撤,五档即成转限价";
//    NSString *const UNConversionTypes = @"可转债转股,债券回售";

    public static final String[] szQuoteType = new String[]{"对方最优价格","本方最优价格","即时成交剩余撤销","五档即成剩撤","全额成交或撤销"};
    public static final String[] shQuoteType = new String[]{"五档即成剩撤","五档即成转限价"};
    public static final String[] conversionType = new String[]{"可转债转股","债券回售"};

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


//    + (NSString *)characterToBsflag:(NSString *)characters tradetype:(NSString *__nullable)tradetype {
//        NSString *result = nil;
//        if (!tradetype) {
//            if ([characters isEqualToString:@"可转债转股"]) {
//                result = @"0G";
//            } else if ([characters isEqualToString:@"债券回售"]) {
//                result = @"0H";
//            }
//        } else if ([tradetype isEqualToString:@"B"] || [tradetype isEqualToString:@"S"]) {
//            BOOL type = [tradetype isEqualToString:@"B"];
//            if ([characters isEqualToString:@"限价委托"]) {
//                result = type ? @"0B" : @"0S";
//            } else if ([characters isEqualToString:@"对方最优价格"]) {
//                result = type ? @"0a" : @"0f";
//            } else if ([characters isEqualToString:@"本方最优价格"]) {
//                result = type ? @"0b" : @"0g";
//            } else if ([characters isEqualToString:@"即时成交剩余撤销"]) {
//                result = type ? @"0c" : @"0h";
//            } else if ([characters isEqualToString:@"五档即成剩撤"]) {
//                result = type ? @"0d" : @"0i";
//            } else if ([characters isEqualToString:@"全额成交或撤销"]) {
//                result = type ? @"0e" : @"0j";
//            } else if ([characters isEqualToString:@"五档即成转限价"]) {
//                result = type ? @"0q" : @"0r";
//            }
//        }
//        return result;
//    }

    public static String getTagByQuoteName(String bsFlag,String quoteType){
        if(TextUtils.isEmpty(quoteType)){
            return "";
        }
        String result ="";
        if(bsFlag.equals("B") || bsFlag.equals("S")){
            boolean type = bsFlag.equals("B");
            if(quoteType.equals("限价委托")){
                result = type ? "0B" : "0S";
            }else if(quoteType.equals("对方最优价格")){
                result = type ? "0a" : "0f";
            }else if(quoteType.equals("本方最优价格")){
                result = type ? "0b" : "0g";
            }else if(quoteType.equals("即时成交剩余撤销")){
                result = type ? "0c" : "0h";
            }else if(quoteType.equals("五档即成剩撤")){
                result = type ? "0d" : "0i";
            }else if(quoteType.equals("全额成交或撤销")){
                result = type ? "0e" : "0j";
            }else if(quoteType.equals("五档即成转限价")){
                result = type ? "0q" : "0r";
            }
        }
        return result;
    }

}
