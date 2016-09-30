package caixapostal.dao;


import caixapostal.componentes.Conexao;
import caixapostal.componentes.SituacaoObjetoInterno;
import caixapostal.entity.DadosAdicionais;
import caixapostal.entity.ObjetoInterno;
import caixapostal.filter.FilterObjetos;
import com.google.gson.Gson;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CaixaPostalDao implements AutoCloseable {

    private Connection conn;

    public CaixaPostalDao(String nomeDB) {
        conn = Conexao.getConnection(nomeDB);
    }

    public List<ObjetoInterno> procuraObjetos(FilterObjetos filter) {
        List<ObjetoInterno> objetosInternos = new ArrayList<ObjetoInterno>();
       
        try {
            PreparedStatement prepare = conn.prepareStatement(montaSqlFilter(filter));
            prepare.setDate(1, new java.sql.Date(toDate(filter.getDataIni())));
            prepare.setDate(2, new java.sql.Date(toDate(filter.getDataFim())));
            ResultSet result = prepare.executeQuery();

            while (result.next()) {
                ObjetoInterno objetoInterno = criaObjetoInterno(result);
                objetosInternos.add(objetoInterno);
            }

            return objetosInternos;

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return new ArrayList<ObjetoInterno>();

    }

    private ObjetoInterno criaObjetoInterno(ResultSet result) throws SQLException {
        ObjetoInterno objetoInterno = new ObjetoInterno();
        objetoInterno.setId(result.getInt("id"));
        objetoInterno.setNumeroObjeto(result.getString("numeroObjeto"));
        objetoInterno.setNumeroLote(result.getInt("numeroLote"));
        objetoInterno.setDataLancamento(result.getTimestamp("dataLancamento"));
        objetoInterno.setIdPrePostagem(result.getInt("idPrePostagem"));
        objetoInterno.setSituacao(getSituacao(result));
        objetoInterno.setRemetente(criaRemetente(result));
        objetoInterno.setDestinatario(criaDestinatario(result));
        return objetoInterno;
    }

    public void Reenviar(FilterObjetos filter) throws SQLException {
        ObjetoInterno objetoInterno = findById(filter);
        if(isStatusPendente(objetoInterno)){
            String sql = "UPDATE caixa_postal_objetos set situacao = ?,sincronizado=? WHERE id = ? ";
            PreparedStatement prepare = conn.prepareStatement(sql);
            prepare.setString(1, SituacaoObjetoInterno.REENVIAR.toString());
            prepare.setBoolean(2, false);
            prepare.setInt(3, filter.getIdObjeto());
            prepare.executeUpdate();
            registraLogAlteracao(findById(filter));
        }
    }

    public void Devolver(FilterObjetos filter) throws SQLException {
        ObjetoInterno objetoInterno = findById(filter);
        if(isStatusPendente(objetoInterno)){
            String sql = "UPDATE caixa_postal_objetos set situacao = ?,sincronizado=? WHERE id = ? ";
            PreparedStatement prepare = conn.prepareStatement(sql);
            prepare.setString(1, SituacaoObjetoInterno.DEVOLVER.toString());
            prepare.setBoolean(2, false);
            prepare.setInt(3, filter.getIdObjeto());
            prepare.executeUpdate();
            registraLogAlteracao(findById(filter));

        }

    }

    public void alterarEnderecoDeEntrega(FilterObjetos filter,DadosAdicionais novoEndereco) throws SQLException{
        ObjetoInterno objetoInterno = findById(filter);
        if(objetoInterno!=null){
            registraLogAlteracao(objetoInterno);
            objetoInterno.setDestinatario(novoEndereco);
            atualizaEndereco(objetoInterno);
        }
    
    }

    private void registraLogAlteracao(ObjetoInterno objetoInterno) throws SQLException {
        Gson gson = new Gson();
        String objetoJson = gson.toJson(objetoInterno);
        String sql = "INSERT INTO log_caixa_postal_objetos(idObjeto,objeto,json) VALUES(?,?,?)";
        PreparedStatement prepare = conn.prepareStatement(sql);
        prepare.setInt(1, objetoInterno.getId());
        prepare.setString(2, ObjetoInterno.class.getSimpleName());
        prepare.setString(3, objetoJson);
        prepare.execute();
    }

    private static String getSituacao(ResultSet result) throws SQLException {
        return SituacaoObjetoInterno.valueOf(result.getString("situacao")).getValue();
    }

    private String montaSqlFilter(FilterObjetos filter) {
        String sql = " SELECT * FROM caixa_postal_objetos WHERE CAST(dataLancamento AS DATE) BETWEEN ? AND ? ";

        if (!filter.getSituacao().isEmpty() && !filter.getSituacao().equals("TODOS")) {
            sql += " AND situacao = '" + SituacaoObjetoInterno.valueOf(filter.getSituacao()).toString() + "' ";
        }

        if (filter.getDestinatario() != null && !filter.getDestinatario().trim().isEmpty()) {
            sql += " AND destinatarioNome LIKE '%" + filter.getDestinatario() + "%'";
        }
        if (filter.getNumeroObjeto() != null && !filter.getNumeroObjeto().trim().isEmpty()) {
            sql += " AND numeroObjeto = '" + filter.getNumeroObjeto() + "'";
        }

        return sql;

    }

    private boolean isStatusPendente(ObjetoInterno objetoInterno){
        return objetoInterno!=null && objetoInterno.getSituacao().equals(SituacaoObjetoInterno.PENDENTE.toString());
    }

    
    public ObjetoInterno findById(FilterObjetos filter) throws SQLException{
        String sql = "SELECT * FROM caixa_postal_objetos where id=?";
        PreparedStatement prepare = conn.prepareStatement(sql);
        prepare.setInt(1,filter.getIdObjeto());
        ResultSet resultSet = prepare.executeQuery();
        if(resultSet.next()){
            return criaObjetoInterno(resultSet);
        }
        return null;
    }


    private long toDate(String data) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date objetcData = dateFormat.parse(data);
        return objetcData.getTime();

    }

    private DadosAdicionais criaRemetente(ResultSet result) throws SQLException {
        DadosAdicionais remetente = new DadosAdicionais();
        remetente.setCep(result.getString("remetenteCep"));
        remetente.setBairro(result.getString("remetenteBairro"));
        remetente.setNumero(result.getString("remetenteNumero"));
        remetente.setLogradouro(result.getString("remetenteLogradouro"));
        remetente.setNome(result.getString("remetenteNome"));
        remetente.setUf(result.getString("remetenteUf"));
        remetente.setCidade(result.getString("remetenteCidade"));
        remetente.setComplemento(result.getString("remetenteComplemento"));
        return remetente;
    }

    private DadosAdicionais criaDestinatario(ResultSet result) throws SQLException {
        DadosAdicionais destinatario = new DadosAdicionais();
        destinatario.setCep(result.getString("destinatarioCep"));
        destinatario.setBairro(result.getString("destinatarioBairro"));
        destinatario.setNumero(result.getString("destinatarioNumero"));
        destinatario.setLogradouro(result.getString("destinatarioLogradouro"));
        destinatario.setNome(result.getString("destinatarioNome"));
        destinatario.setUf(result.getString("destinatarioUf"));
        destinatario.setCidade(result.getString("destinatarioCidade"));
        destinatario.setComplemento(result.getString("destinatarioComplemento"));
        return destinatario;
    }

    @Override
    public void close() throws Exception {
        if (conn != null) {
            conn.close();
        }
    }

    private void atualizaEndereco(ObjetoInterno objetoInterno) throws SQLException {
        PreparedStatement prepare = conn.prepareStatement(sqlAtualizaObjetoInterno());
        DadosAdicionais novoEndereco = objetoInterno.getDestinatario();
        prepare.setString(1,novoEndereco.getCep());
        prepare.setString(2,novoEndereco.getLogradouro());
        prepare.setString(3,novoEndereco.getNumero());
        prepare.setString(4,novoEndereco.getComplemento());
        prepare.setString(5,novoEndereco.getBairro());
        prepare.setString(6, novoEndereco.getCidade());
        prepare.setString(7, novoEndereco.getUf());
        prepare.setString(8,SituacaoObjetoInterno.ATUALIZAR_ENDERECO.toString());
        prepare.setBoolean(9,false);
        prepare.setInt(10, objetoInterno.getId());
        
        prepare.execute();

    }

    private String sqlAtualizaObjetoInterno() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("UPDATE caixa_postal_objetos set ");
        buffer.append("destinatarioCep=?,");
        buffer.append("destinatarioLogradouro=?,");
        buffer.append("destinatarioNumero=?,");
        buffer.append("destinatarioComplemento=?,");
        buffer.append("DestinatarioBairro=?,");
        buffer.append("destinatarioCidade=?,");
        buffer.append("destinatarioUf=?,");
        buffer.append("situacao=?,");
        buffer.append("sincronizado=?");
        buffer.append(" WHERE id=?");
        return buffer.toString();
    }

    public void atualizaIdPrePostagem(ObjetoInterno objetoInterno) throws SQLException {
        PreparedStatement prepare = conn.prepareStatement(sqlAtualizaPrePostagem());
        prepare.setInt(1,objetoInterno.getIdPrePostagem());
        prepare.setString(2,SituacaoObjetoInterno.REENVIAR.toString());
        prepare.setBoolean(3,false);
        prepare.setInt(4, objetoInterno.getId());
        prepare.execute();
    }


    private String sqlAtualizaPrePostagem(){
        return "UPDATE caixa_postal_objetos set idPrePostagem=?,situacao=?,sincronizado=? WHERE id=?";
    }

}
