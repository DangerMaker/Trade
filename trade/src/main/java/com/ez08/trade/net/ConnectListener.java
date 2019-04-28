package com.ez08.trade.net;

public interface ConnectListener {

    public void connectSuccess(YCSocketClient client);

    public void connectFail(YCSocketClient client);

    public void connectLost(YCSocketClient client);
}
