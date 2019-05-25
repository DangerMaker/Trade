package com.ez08.trade.net;

public interface ConnectListener {

    void connectSuccess(Client client);

    void connectFail(Client client);

    void connectLost(Client client);
}
