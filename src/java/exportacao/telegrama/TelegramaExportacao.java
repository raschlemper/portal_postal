package exportacao.telegrama;

import Util.Conexao;
import com.google.gson.Gson;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TelegramaExportacao {

    private String nomeBancoDados;
    private String idsTelegrama;

    public TelegramaExportacao(String nomeBancoDados){
        this.nomeBancoDados = nomeBancoDados;
    }

    public String exportar() {
        TelegramaList telegramaList = new TelegramaList();
        String json = "";
        try{
            List<Telegrama> telegramas = consultaTelegramas();
            telegramaList.setTelegramas(telegramas);
            json = new Gson().toJson(telegramaList);
            atualizaStatusTelegramasExportador(telegramas);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return json;
    }

    public void setIdsTelegrama(String idsTelegrama) {
        this.idsTelegrama = idsTelegrama;
    }

    private List<Telegrama> consultaTelegramas() {
        Connection conn = (Connection) Conexao.conectar(nomeBancoDados);
        try {
            return getResultQuery(conn);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }


    private List<Telegrama> getResultQuery(Connection conn) throws SQLException {
        String sql = null;
        ResultSet result = null;
        if (isIdsEmpty()) {
            sql = "SELECT * FROM telegrama_postal WHERE status = ?;";
            PreparedStatement prepareStatement = conn.prepareStatement(sql);
            prepareStatement.setString(1, "0");
            result = (ResultSet) prepareStatement.executeQuery();
        } else {
            sql = "SELECT * FROM telegrama_postal WHERE id IN ("+idsTelegrama+");";
            PreparedStatement prepareStatement = conn.prepareStatement(sql);
            result = prepareStatement.executeQuery();
        }
        return carregaOsObjetos(result);
    }

    private boolean isIdsEmpty() {
        return idsTelegrama==null || idsTelegrama.isEmpty();
    }

    private List<Telegrama> carregaOsObjetos(ResultSet result) throws SQLException {
        List<Telegrama> telegramas = new ArrayList<Telegrama>();
        while (result.next()) {
            ItemTelegrama remetente = criaRemetente(result);
            ItemTelegrama destinatario = criaDestinatario(result);
            Telegrama telegrama = criaTelegrama(result);
            telegrama.setRemetente(remetente);
            telegrama.setDestinatario(destinatario);
            telegramas.add(telegrama);
        }
        return telegramas;
    }

    private Telegrama criaTelegrama(ResultSet result) throws SQLException {
        Telegrama telegrama = new Telegrama();
        telegrama.setId(result.getInt("id"));
        telegrama.setCopiaTelegrama(result.getString("envioCopia"));
        telegrama.setPedidoConfirmacao(result.getString("adicionais"));
        telegrama.setStatus(TelegramaStatus.PENDENTE);
        telegrama.setValor(result.getDouble("valor"));
        telegrama.setEtiqueta(result.getString("sro"));
        telegrama.setTexto(result.getString("mensagem"));
        return telegrama;
    }

    private ItemTelegrama criaRemetente(ResultSet result) throws SQLException {
        ItemTelegrama remetente = new ItemTelegrama();
        remetente.setBairro(result.getString("bairroRem"));
        remetente.setCep(result.getString("cepRem"));
        remetente.setNome(result.getString("nomeRem"));
        remetente.setNumero(result.getString("numeroRem"));
        remetente.setComplemento(result.getString("complementoRem"));
        remetente.setEndereco(result.getString("enderecoRem"));
        return remetente;
    }

    private ItemTelegrama criaDestinatario(ResultSet result) throws SQLException {
        ItemTelegrama destinatario = new ItemTelegrama();
        destinatario.setBairro(result.getString("bairroDes"));
        destinatario.setCep(result.getString("cepDes"));
        destinatario.setNome(result.getString("nomeDes"));
        destinatario.setNumero(result.getString("numeroDes"));
        destinatario.setComplemento(result.getString("complementoDes"));
        destinatario.setEndereco(result.getString("enderecoDes"));
        return destinatario;
    }


    private void atualizaStatusTelegramasExportador(List<Telegrama> telegramas) throws SQLException{
         String sql = "UPDATE telegrama_postal SET status =-1 WHERE id=?";
         Connection conn = (Connection) Conexao.conectar(nomeBancoDados);
         for (Telegrama telegrama : telegramas) {
            PreparedStatement prepareStatement = conn.prepareStatement(sql);
            prepareStatement.setLong(1, telegrama.getId());
            prepareStatement.execute();
        }
    }
}
