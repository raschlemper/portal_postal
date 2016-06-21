package com.portalpostal.dao.handler;

import com.portalpostal.model.Colaborador;
import com.portalpostal.model.Endereco;
import com.portalpostal.model.InformacaoBancaria;
import com.portalpostal.model.InformacaoProfissional;
import com.portalpostal.model.dd.TipoEstadoCivil;
import com.portalpostal.model.dd.TipoSexo;
import com.portalpostal.model.dd.TipoStatusColaborador;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class ColaboradorHandler extends GenericHandler implements ResultSetHandler<Colaborador> {
        
    public ColaboradorHandler() {
        super("colaborador");
    }
    
    public ColaboradorHandler(String table) {
        super(table);
    }

    public Colaborador handle(ResultSet result) throws SQLException {
        Colaborador colaborador = new Colaborador();
        colaborador.setIdColaborador(getInt(result, "idColaborador"));
        colaborador.setNome(getString(result, "nome"));
        colaborador.setStatus(TipoStatusColaborador.values()[getInt(result, "status")]);
        colaborador.setCpf(getString(result, "cpf"));
        colaborador.setRg(getString(result, "rg"));    
        colaborador.setSexo(TipoSexo.values()[getInt(result, "sexo")]); 
        colaborador.setDataNascimento(getDate(result, "dataNascimento"));   
        colaborador.setDddTelefone(getInt(result, "dddTelefone"));   
        colaborador.setTelefone(getInt(result, "telefone")); 
        colaborador.setDddCelular(getInt(result, "dddCelular"));    
        colaborador.setCelular(getInt(result, "celular"));
        colaborador.setEmail(getString(result, "email"));     
        colaborador.setConjuge(getString(result, "conjuge"));       
        colaborador.setEstadoCivil(TipoEstadoCivil.values()[getInt(result, "estadoCivil")]);
        colaborador.setNaturalidade(getString(result, "naturalidade"));
        colaborador.setNacionalidade(getString(result, "nacionalidade"));
        colaborador.setQuantidadeDependente(getInt(result, "quantidadeDependente"));
        colaborador.setNomePai(getString(result, "nomePai"));
        colaborador.setNomeMae(getString(result, "nomeMae"));
        colaborador.setObservacao(getString(result, "observacao"));
        colaborador.setEndereco(getEndereco(result));
        colaborador.setInformacaoProfissional(getInformacaoProfissional(result));
        colaborador.setInformacaoBancaria(getInformacaoBancaria(result));
        return colaborador;
    }
    
    private Endereco getEndereco(ResultSet result) throws SQLException {
        if(!existColumn(result, "endereco.idEndereco")) return null;
        return new EnderecoHandler().handle(result);         
    }
    
    private InformacaoProfissional getInformacaoProfissional(ResultSet result) throws SQLException {
        if(!existColumn(result, "informacao_profissional.idInformacaoProfissional")) return null;
        return new InformacaoProfissionalHandler().handle(result);         
    }
    
    private InformacaoBancaria getInformacaoBancaria(ResultSet result) throws SQLException {
        if(!existColumn(result, "informacao_bancaria.idInformacaoBancaria")) return null;
        return new InformacaoBancariaHandler().handle(result);         
    }
    
}
