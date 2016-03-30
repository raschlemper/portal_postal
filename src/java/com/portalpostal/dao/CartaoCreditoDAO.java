package com.portalpostal.dao;

import com.portalpostal.dao.handler.CartaoCreditoHandler;
import com.portalpostal.model.CartaoCredito;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartaoCreditoDAO extends GenericDAO { 
    
    private CartaoCreditoHandler cartaoCreditoHandler;

    public CartaoCreditoDAO(String nameDB) { 
        super(nameDB, CartaoCreditoDAO.class);
        cartaoCreditoHandler = new CartaoCreditoHandler();
    } 

    public List<CartaoCredito> findAll() throws Exception {
        String sql = "SELECT * FROM cartao_credito "
                   + "LEFT OUTER JOIN conta_corrente ON(conta_corrente.idContaCorrente = cartao_credito.idContaCorrente) "
                   + "LEFT OUTER JOIN banco ON(banco.idBanco = conta_corrente.idBanco) "
                   + "ORDER BY cartao_credito.idCartaoCredito";        
        return findAll(sql, null, cartaoCreditoHandler);
    }

    public CartaoCredito find(Integer idCartaoCredito) throws Exception {
        String sql = "SELECT * FROM cartao_credito "
                   + "LEFT OUTER JOIN conta_corrente ON(conta_corrente.idContaCorrente = cartao_credito.idContaCorrente) "
                   + "LEFT OUTER JOIN banco ON(banco.idBanco = conta_corrente.idBanco) "
                   + "WHERE cartao_credito.idCartaoCredito = :idCartaoCredito";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idCartaoCredito", idCartaoCredito);
        return (CartaoCredito) find(sql, params, cartaoCreditoHandler);
    }

    public CartaoCredito save(CartaoCredito cartaoCredito) throws Exception {  
        String sql = "INSERT INTO cartao_credito (idContaCorrente, nome, bandeira, diaFechamento, diaVencimento, valorLimiteCredito) "
                   + "VALUES(:idContaCorrente, :nome, :bandeira, :diaFechamento, :diaVencimento, :valorLimiteCredito)";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idContaCorrente", (cartaoCredito.getContaCorrente() == null ? null : cartaoCredito.getContaCorrente().getIdContaCorrente()));
        params.put("nome", cartaoCredito.getNome());
        params.put("bandeira", cartaoCredito.getBandeira());
        params.put("diaFechamento", cartaoCredito.getDiaFechamento());      
        params.put("diaVencimento", cartaoCredito.getDiaVencimento());
        params.put("valorLimiteCredito", cartaoCredito.getValorLimiteCredito());
        Integer idContaCorrente = save(sql, params, cartaoCreditoHandler);
        return find(idContaCorrente);
    }

    public CartaoCredito update(CartaoCredito cartaoCredito) throws Exception {
        String sql = "UPDATE cartao_credito "
                   + "SET idContaCorrente = :idContaCorrente, nome = :nome, bandeira = :bandeira, diaFechamento = :diaFechamento, "
                   + "diaVencimento = :diaVencimento, valorLimiteCredito = :valorLimiteCredito "
                   + "WHERE idCartaoCredito = :idCartaoCredito ";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idCartaoCredito", cartaoCredito.getIdCartaoCredito());
        params.put("idContaCorrente", (cartaoCredito.getContaCorrente() == null ? null : cartaoCredito.getContaCorrente().getIdContaCorrente()));
        params.put("nome", cartaoCredito.getNome());
        params.put("bandeira", cartaoCredito.getBandeira());
        params.put("diaFechamento", cartaoCredito.getDiaFechamento());      
        params.put("diaVencimento", cartaoCredito.getDiaVencimento());
        params.put("valorLimiteCredito", cartaoCredito.getValorLimiteCredito());
        update(sql, params, cartaoCreditoHandler);
        return cartaoCredito;  
    }

    public CartaoCredito remove(Integer idCartaoCredito) throws Exception { 
        String sql = "DELETE FROM cartao_credito WHERE idCartaoCredito = :idCartaoCredito ";
        CartaoCredito cartaoCredito = find(idCartaoCredito);
        Map<String, Object> params = new HashMap<String, Object>();        
        params.put("idCartaoCredito", idCartaoCredito);
        remove(sql, params, cartaoCreditoHandler);
        return cartaoCredito;
    }
    
}
