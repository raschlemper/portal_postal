package com.portalpostal.dao.handler;

import java.io.InputStream;
import java.sql.Blob;
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
    
    private String getColumn(String columnName) {
        if(table == null) return columnName;
        return table + "." + columnName;
    }
    
    protected Integer getInt(ResultSet result, String columnName) throws SQLException {
        if(!existColumn(result, columnName)) return null;
        return (Integer) result.getObject(getColumn(columnName));
    }
    
    protected String getString(ResultSet result, String columnName) throws SQLException {
        if(!existColumn(result, columnName)) return null;
        return result.getString(getColumn(columnName));
    }
    
    protected Date getDate(ResultSet result, String columnName) throws SQLException {
        if(!existColumn(result, columnName)) return null;
        return result.getTimestamp(getColumn(columnName));
    }
    
    protected Double getDouble(ResultSet result, String columnName) throws SQLException {
        if(!existColumn(result, columnName)) return null;
        Double valor = result.getDouble(getColumn(columnName));
        if (result.wasNull()) { valor = null; }
        return valor;
    }
    
    protected Boolean getBoolean(ResultSet result, String columnName) throws SQLException {
        if(!existColumn(result, columnName)) return null;
        return result.getBoolean(getColumn(columnName));
    }
    
    protected InputStream getBinaryStream(ResultSet result, String columnName) throws SQLException {
        if(!existColumn(result, columnName)) return null;
        return result.getBinaryStream(getColumn(columnName));
    }
}
