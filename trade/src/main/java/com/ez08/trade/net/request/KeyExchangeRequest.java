package com.ez08.trade.net.request;

import com.ez08.trade.net.NativeTools;
import com.ez08.trade.net.SnFactory;
import com.ez08.trade.net.YCRequest;

public class KeyExchangeRequest extends YCRequest {

    public KeyExchangeRequest() {
        super(SnFactory.getSnClient());
        this.mData = NativeTools.genTradePacketKeyExchangePackFromJNI(sn);
    }

    @Override
    public String parse(byte[] head, byte[] body) {
        return NativeTools.parseTradePacketKeyExchangeFromJNI(body);
    }

}
