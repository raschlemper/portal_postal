package com.portalpostal.dao.handler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

class GenericHandler {
    
    protected String table;
    
    public GenericHandler(String table) {
        this.table = table;
    }
    
    protected boolean existColumn(ResultSet result, String column) {
        try {
            Integer resultFind = result.findColumn(column);
            if(resultFind != 0) return true;
        } catch (SQLException ex) {
            return false;
        }
        return false;
    }
    
    protected Integer getInt(ResultSet result, String columnName) throws SQLException {
        return (Integer) result.getObject(table + "." + columnName);
    }
    
    protected String getString(ResultSet result, String columnName) throws SQLException {
        return result.getString(table + "." + columnName);
    }
    
    protected Date getDate(ResultSet result, String columnName) throws SQLException {
        return result.getDate(table + "." + columnName);
    }
    
    protected Double getDouble(ResultSet result, String columnName) throws SQLException {
        Double valor = result.getDouble(table  + "." + columnName);
        if (result.wasNull()) { valor = null; }
        return valor;
    }
    
    protected Boolean getBoolean(ResultSet result, String columnName) throws SQLException {
        return result.getBoolean(table  + "." + columnName);
    }
}
