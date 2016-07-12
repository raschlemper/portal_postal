package com.portalpostal.dao;

import com.portalpostal.dao.handler.FavorecidoHandler;
import com.portalpostal.model.Favorecido;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FavorecidoDAO extends GenericDAO { 
    
    private final FavorecidoHandler favorecidoHandler;

    public FavorecidoDAO(String nameDB) { 
        super(nameDB, FavorecidoDAO.class);
        favorecidoHandler = new FavorecidoHandler();
    } 

    public List<Favorecido> findAll() throws Exception {
        String sql = "SELECT * FROM favorecido "
                   + "LEFT OUTER JOIN colaborador ON(favorecido.idColaborador = colaborador.idColaborador) "
                   + "LEFT OUTER JOIN cliente ON(favorecido.idCliente = cliente.codigo) "
                   + "ORDER BY favorecido.idFavorecido";        
        return findAll(sql, null, favorecidoHandler);
    }

    public Favorecido find(Integer idFavorecido) throws Exception {
        String sql = "SELECT * FROM favorecido "
                   + "LEFT OUTER JOIN colaborador ON(favorecido.idColaborador = colaborador.idColaborador) "
                   + "LEFT OUTER JOIN cliente ON(favorecido.idCliente = cliente.codigo) "
                   + "WHERE favorecido.idFavorecido = :idFavorecido";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idFavorecido", idFavorecido);
        return (Favorecido) find(sql, params, favorecidoHandler);
    }

    public Favorecido findByColaborador(Integer idColaborador) throws Exception {
        String sql = "SELECT * FROM favorecido "
                   + "WHERE favorecido.idColaborador = :idColaborador";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idColaborador", idColaborador);
        return (Favorecido) find(sql, params, favorecidoHandler);
    }

    public Favorecido findByFornecedor(Integer idFornecedor) throws Exception {
        String sql = "SELECT * FROM favorecido "
                   + "WHERE favorecido.idFornecedor = :idFornecedor";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idFornecedor", idFornecedor);
        return (Favorecido) find(sql, params, favorecidoHandler);
    }

    public Favorecido findByCliente(Integer idCliente) throws Exception {
        String sql = "SELECT * FROM favorecido "
                   + "WHERE favorecido.idCliente = :idCliente";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idCliente", idCliente);
        return (Favorecido) find(sql, params, favorecidoHandler);
    }

    public Favorecido save(Favorecido favorecido) throws Exception {  
        String sql = "INSERT INTO favorecido (tipo, idColaborador, idFornecedor, idCliente, nome) "
                   + "VALUES(:tipo, :idColaborador, :idFornecedor, :idCliente, :nome)";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("tipo", favorecido.getTipo().ordinal());
        params.put("idColaborador", (favorecido.getColaborador() == null ? null : favorecido.getColaborador().getIdColaborador()));
        params.put("idFornecedor", null);//(favorecido.getFornecedor() == null ? null : favorecido.getFornecedor().getIdFornecedor()));      
        params.put("idCliente", (favorecido.getCliente() == null ? null : favorecido.getCliente().getCodigo()));      
        params.put("nome", favorecido.getNome()); 
        Integer idFavorecido = save(sql, params, favorecidoHandler);
        return find(idFavorecido);
    }

    public Favorecido update(Favorecido favorecido) throws Exception {
        String sql = "UPDATE favorecido "
                   + "SET tipo = :tipo, idColaborador = :idColaborador, idFornecedor = :idFornecedor, idCliente = :idCliente, nome = :nome "
                   + "WHERE idFavorecido = :idFavorecido ";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idFavorecido", favorecido.getIdFavorecido());
        params.put("tipo", favorecido.getTipo().ordinal());
        params.put("idColaborador", (favorecido.getColaborador() == null ? null : favorecido.getColaborador().getIdColaborador()));
        params.put("idFornecedor", null);//(favorecido.getFornecedor() == null ? null : favorecido.getFornecedor().getIdFornecedor()));      
        params.put("idCliente", (favorecido.getCliente() == null ? null : favorecido.getCliente().getCodigo()));      
        params.put("nome", favorecido.getNome());   
        update(sql, params, favorecidoHandler);
        return favorecido;  
    }

    public Favorecido remove(Integer idFavorecido) throws Exception { 
        String sql = "DELETE FROM favorecido WHERE idFavorecido = :idFavorecido ";
        Favorecido favorecido = find(idFavorecido);
        Map<String, Object> params = new HashMap<String, Object>();        
        params.put("idFavorecido", idFavorecido);
        remove(sql, params, favorecidoHandler);
        return favorecido;
    }

    public void removeByColaborador(Integer idColaborador) throws Exception { 
        String sql = "DELETE FROM favorecido WHERE idColaborador = :idColaborador ";
        Map<String, Object> params = new HashMap<String, Object>();        
        params.put("idColaborador", idColaborador);
        remove(sql, params, favorecidoHandler);
    }

    public void removeByFornecedor(Integer idFornecedor) throws Exception { 
        String sql = "DELETE FROM favorecido WHERE idFornecedor = :idFornecedor ";
        Map<String, Object> params = new HashMap<String, Object>();        
        params.put("idFornecedor", idFornecedor);
        remove(sql, params, favorecidoHandler);
    }

    public void removeByCliente(Integer idCliente) throws Exception { 
        String sql = "DELETE FROM favorecido WHERE idCliente = :idCliente ";
        Map<String, Object> params = new HashMap<String, Object>();        
        params.put("idCliente", idCliente);
        remove(sql, params, favorecidoHandler);
    }
    
}
