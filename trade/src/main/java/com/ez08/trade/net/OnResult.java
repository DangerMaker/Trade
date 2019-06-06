package com.ez08.trade.net;

public interface OnResult<T> {
    void onSucceed(T response);
    void onFailure(Error error);
}
