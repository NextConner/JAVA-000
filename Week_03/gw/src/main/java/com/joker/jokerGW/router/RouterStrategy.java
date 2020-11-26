package com.joker.jokergw.router;

public enum RouterStrategy {

    /**
     *  路由策略
     */
    Random("RandomRouter","随机路由",0),RoundRobin("RoundRobinRouter","轮询",1),Weight("WeightRouter","权重",2);

    private String strategyKey;
    //策略名称
    private String strategyName;
    //策略优先级
    private int order;


    /**
     *  根据优先级获取路由策略
     * @param order
     * @return
     */
    public RouterStrategy getByOrder(int order){

        RouterStrategy result  = null;
        for(RouterStrategy strategy : RouterStrategy.values()){
            if(strategy.order == order){
                result =  strategy;
            }
        }
        return result;
    }

    RouterStrategy(String strategyKey,String strategyName,int order){
        this.strategyKey= strategyKey;
        this.strategyName = strategyName;
        this.order=order;
    }

    public String getStrategyKey() {
        return strategyKey;
    }

    public void setStrategyKey(String strategyKey) {
        this.strategyKey = strategyKey;
    }

    public String getStrategyName() {
        return strategyName;
    }

    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
