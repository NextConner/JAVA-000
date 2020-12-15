package com.sharding.demo.dao;

import com.sharding.demo.entity.OrderDetail;
import org.apache.shardingsphere.transaction.annotation.ShardingTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.apache.shardingsphere.transaction.core.TransactionTypeHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/12/13 22:49
 */
@Repository
public class OrderDetailXaDao {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(rollbackFor = Exception.class)
    @ShardingTransactionType(TransactionType.XA)
    public void insert(OrderDetail orderDetail){
        String sql = " INSERT INTO t_order_detail (id,order_id,comdi_id,comdi_name,store_name,sku_info,status,user_id,sell_price,num,discount,shipping,total_price,create_time,update_time,price)" +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.execute(sql,(PreparedStatementCallback<TransactionType>) preparedStatement -> {
            doInsert(orderDetail,preparedStatement);
            return TransactionTypeHolder.get();
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @ShardingTransactionType(TransactionType.XA)
    public void update(OrderDetail orderDetail){
        String SQL = "UPDATE t_order_detail SET update_time = now() WHERE user_id = ? AND order_id = ?";
        jdbcTemplate.execute(SQL,(PreparedStatementCallback<Object>) preparedStatement ->{
            preparedStatement.setObject(1,orderDetail.getUserId());
            preparedStatement.setObject(2,orderDetail.getOrderId());
            return preparedStatement.executeUpdate();
        });
    }

    public int clear(){
        return jdbcTemplate.update(" DELETE FROM t_order_detail");
    }

    public int selectAll() {
        return jdbcTemplate.queryForObject("SELECT COUNT(1) AS count FROM t_order_detail", Integer.class);
    }

    public String selectAll(Long userId, Long orderId) {
        List<Map<String,Object>> result = jdbcTemplate.queryForList("SELECT user_id,order_id from t_order_detail where user_id = "+userId+ " and order_id = "+orderId + "");
        return null == result ? "null" : result.toString();
    }

    @Transactional(rollbackFor = Exception.class)
    @ShardingTransactionType(TransactionType.XA)
    public TransactionType insertWithException(OrderDetail orderDetail) {

        String sql = " INSERT INTO t_order_detail (id,order_id,comdi_id,comdi_name,store_name,sku_info,status,user_id,sell_price,num,discount,shipping,total_price,create_time,update_time,price)" +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        return jdbcTemplate.execute("",(PreparedStatementCallback<TransactionType>) preparedStatement -> {
            doInsert(orderDetail,preparedStatement);
            throw new SQLException("插入异常回滚！");
        });
    }

    private void doInsert(final OrderDetail orderDetail, final PreparedStatement preparedStatement) throws SQLException {
        String sql = " INSERT INTO t_order_detail (id,order_id,comdi_id,comdi_name,store_name,sku_info,status,user_id,sell_price,num,discount,shipping,total_price,create_time,update_time,price)" +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        preparedStatement.setObject(1,orderDetail.getId());
        preparedStatement.setObject(2,orderDetail.getOrderId());
        preparedStatement.setObject(3,orderDetail.getComdId());
        preparedStatement.setObject(4,orderDetail.getCondName());
        preparedStatement.setObject(5,orderDetail.getStoreName());
        preparedStatement.setObject(6,orderDetail.getSkuInfo());
        preparedStatement.setObject(7,orderDetail.getStatus());
        preparedStatement.setObject(8,orderDetail.getUserId());
        preparedStatement.setObject(9,orderDetail.getSellPrice());
        preparedStatement.setObject(10,orderDetail.getNum());
        preparedStatement.setObject(11,orderDetail.getDiscount());
        preparedStatement.setObject(12,orderDetail.getShipping());
        preparedStatement.setObject(13,orderDetail.getTotalPrice());
        preparedStatement.setObject(14,orderDetail.getCreateTime());
        preparedStatement.setObject(15,orderDetail.getUpdateTime());
        preparedStatement.setObject(16,orderDetail.getPrice());
        preparedStatement.executeUpdate();
    }

}
