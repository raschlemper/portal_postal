package com.portalpostal.dao.handler;

import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

class GenericHandler {
    
    protected String table;
    
    public GenericHandler(String table) {
        this.table = table;
    }
    
    protected boolean existFKValue(ResultSet result, String column) {
        try {
            Integer value = (Integer) result.getObject(column);
            if (value == null) return false;
        } catch (SQLException ex) {
            return true;
        }
        return true;
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
    
    private String getColumn(String columnName, String table) {
        if(table == null) return columnName;
        return table + "." + columnName;
    }
    
    protected Integer getInt(ResultSet result, String columnName) throws SQLException {
        return getInt(result, columnName, table);
    }
    
    protected Integer getInt(ResultSet result, String columnName, String table) throws SQLException {
        if(!existColumn(result, columnName)) return null;
        return (Integer) result.getObject(getColumn(columnName, table));
    }
    
    protected Long getLong(ResultSet result, String columnName) throws SQLException {
        return getLong(result, columnName, table);
    }
    
    protected Long getLong(ResultSet result, String columnName, String table) throws SQLException {
        if(!existColumn(result, columnName)) return null;
        return (Long) result.getObject(getColumn(columnName, table));
    }
    
    protected String getString(ResultSet result, String columnName) throws SQLException {
        return getString(result, columnName, table);
    }
    
    protected String getString(ResultSet result, String columnName, String table) throws SQLException {
        if(!existColumn(result, columnName)) return null;
        return result.getString(getColumn(columnName, table));        
    }
    
    protected Date getDate(ResultSet result, String columnName) throws SQLException {
        return getDate(result, columnName, table);
    }
    
    protected Date getDate(ResultSet result, String columnName, String table) throws SQLException {
        if(!existColumn(result, columnName)) return null;
        return result.getTimestamp(getColumn(columnName, table));
    }
    
    protected Double getDouble(ResultSet result, String columnName) throws SQLException {
        return getDouble(result, columnName, table);
    }
    
    protected Double getDouble(ResultSet result, String columnName, String table) throws SQLException {
        if(!existColumn(result, columnName)) return null;
        Double valor = result.getDouble(getColumn(columnName, table));
        if (result.wasNull()) { valor = null; }
        return valor;
    }
    
    protected Boolean getBoolean(ResultSet result, String columnName) throws SQLException {
        return getBoolean(result, columnName, table);
    }
    
    protected Boolean getBoolean(ResultSet result, String columnName, String table) throws SQLException {
        if(!existColumn(result, columnName)) return null;
        return result.getBoolean(getColumn(columnName, table));
    }
    
    protected InputStream getBinaryStream(ResultSet result, String columnName) throws SQLException {
        if(!existColumn(result, columnName)) return null;
        return result.getBinaryStream(getColumn(columnName, table));
    }
}
