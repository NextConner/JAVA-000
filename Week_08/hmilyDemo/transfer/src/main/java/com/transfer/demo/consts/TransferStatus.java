package com.transfer.demo.consts;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/12/12 15:26
 */
public enum TransferStatus {

    TRANSFER_ING(0),TRANSFER_SUC(1),TRANSFER_FAIL(2);

    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    TransferStatus(int code){
        this.code = code;
    }

}
