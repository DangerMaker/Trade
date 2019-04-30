package com.ez08.trade.net;

public class KeyExchangeRequest extends YCRequest {
    public KeyExchangeRequest(int pid) {
        super(pid);
    }

    @Override
    public String parse(byte[] buffer) {
        return NativeTools.parseTradePacketKeyExchangeFromJNI(buffer);
    }
}
