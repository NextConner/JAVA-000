package io.kimmking.rpcfx.exception;

import java.util.Date;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/12/18 11:36
 */

public class RpcfxException extends Exception{

    private String method;
    private String strParams;
    private String requestService;
    private Exception exception;
    private String exceptionMsg;
    private Date date;


    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getRequestService() {
        return requestService;
    }

    public void setRequestService(String requestService) {
        this.requestService = requestService;
    }

    public String getStrParams() {
        return strParams;
    }

    public void setStrParams(String strParams) {
        this.strParams = strParams;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public String getExceptionMsg() {
        return exceptionMsg;
    }

    public void setExceptionMsg(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}
