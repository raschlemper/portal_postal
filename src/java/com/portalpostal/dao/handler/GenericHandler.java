package com.portalpostal.dao.handler;

import java.sql.ResultSet;
import java.sql.SQLException;

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
}
