package io.kimmking.rpcfx.conts;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/12/18 14:55
 */
public enum ServiceTagWeight {

    WEIGHTEST(-2),WEIGHTER(-1),NORMAL(0),LIGHTER(1),LIGHTEST(2);

    private int value;


    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    ServiceTagWeight(int value){
        this.value=value;
    }

}
