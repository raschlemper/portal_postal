package com.portalpostal.dao;

import Controle.ContrErroLog;
import Util.Sql2oConexao;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.ResultSetHandler;

public class GenericDAO {
    
    private final String nameDB;
    private final Class<?> clazz;

    protected GenericDAO(String nameDB, Class<?> clazz) {
        this.nameDB = nameDB;
        this.clazz = clazz;
    }    
    
    private void addParameter(Query query, Map<String, Object> params) {
        for (Map.Entry<String, Object> entrySet : params.entrySet()) {
            query.addParameter(entrySet.getKey(), entrySet.getValue());                   
        }
    }

    public List findAll(String sql, Map<String, Object> params, ResultSetHandler handler) throws Exception {
        Connection connection = Sql2oConexao.getConnection(nameDB);
        List list = new ArrayList();
        try {  
            Query query = connection.createQuery(sql);
            if(params != null) { addParameter(query, params); }
            list = query.executeAndFetch(handler);
            query.close();
        } catch (Exception e) {
            ContrErroLog.inserir("HOITO - " + clazz.getSimpleName(), "SQLException", sql, e.toString());
        } finally {
            connection.close();   
        }
        return list;
    }  

    public List findAll(String sql, Map<String, Object> params, Class<?> classe) throws Exception {
        Connection connection = Sql2oConexao.getConnection(nameDB);
        List list = new ArrayList();
        try {  
            Query query = connection.createQuery(sql);
            if(params != null) { addParameter(query, params); }
            list = query.executeAndFetch(classe);
            query.close();
        } catch (Exception e) {
            ContrErroLog.inserir("HOITO - " + clazz.getSimpleName(), "SQLException", sql, e.toString());
        } finally {
            connection.close();   
        }
        return list;
    }  

    public Object find(String sql, Map<String, Object> params, ResultSetHandler handler) throws Exception {
        Connection connection = Sql2oConexao.getConnection(nameDB);
        Object object = null;
        try {      
            Query query = connection.createQuery(sql);
            if(params != null) { addParameter(query, params); }
            object = query.executeAndFetchFirst(handler);
            query.close();
        } catch (Exception e) {
            ContrErroLog.inserir("HOITO - " + clazz.getSimpleName(), "SQLException", sql, e.toString());
        } finally {
            connection.close();   
        }
        return object;
    } 

    public Object find(String sql, Map<String, Object> params, Class<?> classe) throws Exception {
        Connection connection = Sql2oConexao.getConnection(nameDB);
        Object object = null;
        try {      
            Query query = connection.createQuery(sql);
            if(params != null) { addParameter(query, params); }
            object = query.executeAndFetchFirst(classe);
            query.close();
        } catch (Exception e) {
            ContrErroLog.inserir("HOITO - " + clazz.getSimpleName(), "SQLException", sql, e.toString());
        } finally {
            connection.close();   
        }
        return object;
    }

    public Integer save(String sql, Map<String, Object> params, ResultSetHandler handler) throws Exception { 
        Connection connection = Sql2oConexao.getConnection(nameDB);
        Integer id = null;
        try {              
            Query query = connection.createQuery(sql, true);
            if(params != null) { addParameter(query, params); }
            id = query.executeUpdate().getKey(Integer.class); 
            query.close();
        } catch (Exception e) {
            ContrErroLog.inserir("HOITO - " + clazz.getSimpleName(), "SQLException", sql, e.toString());
        } finally {
            connection.close();
        }
        return id;
    }
    
    public void update(String sql, Map<String, Object> params, ResultSetHandler handler) throws Exception { 
        Connection connection = Sql2oConexao.getConnection(nameDB);
        try {              
            Query query = connection.createQuery(sql, true);
            if(params != null) { addParameter(query, params); }
            query.executeUpdate(); 
            query.close();
        } catch (Exception e) {
            ContrErroLog.inserir("HOITO - " + clazz.getSimpleName(), "SQLException", sql, e.toString());
        } finally {
            connection.close();
        }
    }

    public void remove(String sql, Map<String, Object> params, ResultSetHandler handler) throws Exception { 
        Connection connection = Sql2oConexao.getConnection(nameDB);
        try {              
            Query query = connection.createQuery(sql, true);
            if(params != null) { addParameter(query, params); }
            query.executeUpdate().getKey(Integer.class); 
            query.close();
        } catch (Exception e) {
            ContrErroLog.inserir("HOITO - " + clazz.getSimpleName(), "SQLException", sql, e.toString());
        } finally {
            connection.close();
        }
    }

    public void execute(String sql, Map<String, Object> params) throws Exception { 
        Connection connection = Sql2oConexao.getConnection(nameDB);
        try {              
            Query query = connection.createQuery(sql, true);
            if(params != null) { addParameter(query, params); }
            query.executeUpdate(); 
            query.close();
        } catch (Exception e) {
            ContrErroLog.inserir("HOITO - " + clazz.getSimpleName(), "SQLException", sql, e.toString());
        } finally {
            connection.close();
        }
    }
    
    public String addList(List<Integer> itens) {
        StringBuilder sql = new StringBuilder();
        for (int i = 0; i < itens.size(); i++) {
            Integer item = itens.get(i);
            if(i > 0) { sql.append(", "); }
            sql.append(item);
        }
        return sql.toString();
    }
    
}
