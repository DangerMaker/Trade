package com.ez08.trade.net;

public interface ConnectListener {

    public void connectSuccess(Client client);

    public void connectFail(Client client);

    public void connectLost(Client client);
}
