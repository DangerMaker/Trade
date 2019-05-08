package com.ez08.trade.net;

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
