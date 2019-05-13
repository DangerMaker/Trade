package com.ez08.trade.user;

public class UserHelper {

    public static TradeUser user;

    public static TradeUser init(String name,String market,String fundsid,String custcert) {
        user = new TradeUser();
        user.name = name;
        user.market = market;
        user.fundid = fundsid;
        user.custcert = custcert;
        return user;
    }

    public static TradeUser getUser(){
        return user;
    }

}
