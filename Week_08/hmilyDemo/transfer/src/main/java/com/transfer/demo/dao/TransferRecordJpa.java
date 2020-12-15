package com.transfer.demo.dao;


import com.transfer.demo.entity.TransferRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/12/12 14:34
 */
@Repository
public interface TransferRecordJpa extends JpaRepository<TransferRecord,Long> {
}
