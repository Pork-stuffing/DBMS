package com.sxt.utils;

import org.apache.commons.dbutils.ResultSetHandler;

import entity.HistoryDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HistoryDBHandler implements ResultSetHandler<List<HistoryDB>> {

    @Override
    public List<HistoryDB> handle(ResultSet rs) throws SQLException {
        List<HistoryDB> result = new ArrayList<>();
        while (rs.next()) {
            HistoryDB historyDB = new HistoryDB();
            historyDB.setUid(rs.getInt("uid"));
            historyDB.setName(rs.getString("name"));
            historyDB.setAccount(rs.getString("account"));
            historyDB.setBid(rs.getInt("bid"));
            historyDB.setBookName(rs.getString("book_name"));
            historyDB.setBeginTime(rs.getTimestamp("begin_time")); // 使用 getTimestamp
            historyDB.setEndTime(rs.getTimestamp("end_time")); // 使用 getTimestamp
            historyDB.setStatus(rs.getInt("status"));
            historyDB.setHid(rs.getInt("hid"));
            result.add(historyDB);
        }
        return result;
    }
}
