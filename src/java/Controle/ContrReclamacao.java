
package Controle;

/*import Entidade.Configuracao;
import Entidade.ItemReclamacao;
import Entidade.Login;
import Entidade.Protocolo;
import Entidade.Reclamacao;*/
import Util.Conexao;
import Util.Holiday;
import static java.lang.Math.round;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;



public class ContrReclamacao {

    private Map<Date, String> feriados;
   
    //public List<Reclamacao> pesquisaEntregasAtrasadas(){
    public void pesquisaEntregasAtrasadas(int idCliente, String nomeBD) {
        Connection con = Conexao.conectar(nomeBD);
       String sql = "SELECT  \n" +
                            "  t.numObjeto codigoRegistro,\n" +
                            "  CAST(t.data_postagem AS DATE) dataPostagem,\n" +
                            "  t.prazo_estimado prazoEstimado,\n" +
                            "  CAST(t.prazo_cumprido AS DATE) prazoCumprido,\n" +
                            "  t.last_status_code,\n" +
                            "  t.last_status_name,\n" +
                            "  t.last_status_date,\n" +
                            "  t.pi_status_code AS statusCode,\n" +
                            "  t.pi_status_name AS statusDescricao,\n" +
                            "  t.pi_number protocolo,\n" +
                            "  p.nomeServico,\n"+
                            "  c.cnpj documento,\n" +
                            "  c.nome remetenteNome,\n" +
                            "  c.cep remetenteCep,\n" +
                            "  c.endereco remetenteEndereco,\n" +
                            "  c.numero remetenteNumero,\n" +
                            "  c.complemento remetenteComplemento,\n" +
                            "  c.bairro remetenteBairro,\n" +
                            "  c.email remetenteEmail,\n" +
                            "  d.nome destinatarioNome,\n" +
                            "  d.cep destinatarioCep,\n" +
                            "  d.endereco destinatarioEndereco,\n" +
                            "  d.numero destinatarioNumero,\n" +
                            "  d.complemento destinatarioComplemento,\n" +
                            "  d.bairro destinatarioBairro\n" +
                            "FROM movimentacao_tracking AS t\n" +
                            "LEFT JOIN cliente AS c ON c.codigo = t.idCliente\n" +
                            "LEFT JOIN pre_venda AS p ON p.numObjeto = t.numObjeto\n" +
                            "LEFT JOIN pre_venda_destinatario AS d ON d.idDestinatario = p.idDestinatario\n" +
                            "WHERE p.numObjeto IS NOT NULL \n" +
                            "  AND data_postagem >= DATE_SUB(DATE(NOW()), INTERVAL 90 DAY)\n" +
                            "  AND (prazo_estimado < DATE(prazo_cumprido)\n" +
                            "  OR (last_status_code, last_status_type) IN (\n" +
                            "   (9, 'BDE'),(9, 'BDR'),(9, 'BDI'),\n" +
                            "   (28, 'BDE'),(28, 'BDR'),(28, 'BDI'),\n" +
                            "   (37, 'BDE'),(37, 'BDR'),(37, 'BDI'),\n" +
                            "   (43, 'BDE'),(43, 'BDR'),(43, 'BDI'),\n" +
                            "   (50, 'BDE'),(50, 'BDR'),(50, 'BDI'),\n" +
                            "   (51, 'BDE'),(51, 'BDR'),(51, 'BDI'),\n" +
 "   (52, 'BDE'),(52, 'BDR'),(52, 'BDI')))"
               + " AND  t.pi_status_code <> 2 "
               + " AND t.idCliente = " + idCliente
               + " ORDER BY c.nome ASC ;";
        try {
            
            PreparedStatement prepare = con.prepareStatement(sql);
            ResultSet result = (ResultSet) prepare.executeQuery();
            //List<Reclamacao> reclamacoes = carregaReclamacao(result);
            //verificaObjetosAtrasados(reclamacoes);
            //return reclamacoes;
        } catch (SQLException e) {
            Logger.getLogger(ContrGrupoFaturamento.class.getName()).log(Level.WARNING, e.getMessage(), e);
            //return null;
        } finally {
            Conexao.desconectar(con);
        }
    }

    /*
   public void enviarProtocolo(Protocolo protocolo) throws SQLException{
       String sql = "UPDATE movimentacao_tracking SET pi_number=?, pi_date=NOW(), pi_status_date=NOW(), pi_status_code=?, pi_status_name=? WHERE numObjeto=?";
       Connection con = Conexao.conectar(login.getDocumento());
       PreparedStatement prepare = con.prepareStatement(sql);
       prepare.setString(1,protocolo.getNumero() );
       prepare.setInt(2,protocolo.getCodigoStatus() );
       prepare.setString(3,protocolo.getDescricaoStatus() );
       prepare.setString(4,protocolo.getCodigoPostagem() );
       prepare.executeUpdate();
       con.close();
   }

    private ArrayList<Reclamacao> carregaReclamacao(ResultSet result) throws SQLException {
        ArrayList<Reclamacao> reclamacoes = new ArrayList<Reclamacao>();
        while (result.next()) {
            ItemReclamacao remetente = criaRemetente(result);
            ItemReclamacao destinatario = criaDestinatario(result);
            Reclamacao reclamacao = new Reclamacao();
            reclamacao.setRemetente(remetente);
            reclamacao.setDestinatario(destinatario);
            reclamacoes.add(reclamacao);
        }
        return reclamacoes;
    }

    private ItemReclamacao criaRemetente(ResultSet result) throws SQLException {
        ItemReclamacao remetente = new ItemReclamacao();
        remetente.setConfiguracao(configuraReclamacao(result.getInt("last_status_code")));
        remetente.setDocumento(result.getString("documento"));
        remetente.setNomeCompleto(result.getString("remetenteNome").toUpperCase());
        remetente.setDataPostagem(result.getDate("dataPostagem"));
        remetente.setCep(result.getString("remetenteCep"));
        remetente.setEndereco(result.getString("remetenteEndereco"));
        remetente.setNumero(filtraNumero(result.getString("remetenteNumero")));
        remetente.setComplemento(result.getString("remetenteComplemento"));
        remetente.setBairro(result.getString("remetenteBairro"));
        remetente.setEmail(result.getString("remetenteEmail"));
        remetente.setNomeServico(result.getString("nomeServico"));
        remetente.setProtocolo(result.getString("protocolo"));
        return remetente;
    }
   
    private ItemReclamacao criaDestinatario(ResultSet result) throws SQLException {
        ItemReclamacao destinatario = new ItemReclamacao();
        destinatario.setCodigoRegistro(result.getString("codigoRegistro"));
        destinatario.setNomeCompleto(result.getString("destinatarioNome").toUpperCase());
        destinatario.setCep(result.getString("destinatarioCep"));
        destinatario.setDataPostagem(result.getDate("dataPostagem"));
        destinatario.setEndereco(result.getString("destinatarioEndereco"));
        destinatario.setBairro(result.getString("destinatarioBairro"));
        destinatario.setComplemento(result.getString("destinatarioComplemento"));
        destinatario.setNumero(filtraNumero(result.getString("destinatarioNumero")));
        destinatario.setPrevisaoEntrega(result.getDate("prazoEstimado"));
        destinatario.setDataEntrega(result.getDate("prazoCumprido"));        
        destinatario.setStatusCode(result.getInt("statusCode"));
        destinatario.setStatusDescricao(result.getString("statusDescricao"));
        destinatario.setStatusCode(result.getInt("statusCode"));
        destinatario.setStatusDescricao(result.getString("statusDescricao"));
        destinatario.setLastStatusCode(result.getInt("last_status_code"));
        return destinatario;
   }
  
   private String filtraNumero(String valor){
       if (valor != null && !valor.trim().isEmpty() && !valor.trim().equals("null")) {
           return valor;
       }
       return "0";
   }
   
   private Configuracao configuraReclamacao(Integer statusCode) {
        Configuracao configuracao = new Configuracao();
        configuracao.setTipoReclamacao('I');
        configuracao.setTipoAtendimento('N');
        configuracao.setMotivoReclamacao(getCodigoMotivoReclamacao(statusCode));
        configuracao.setTipoServico("114");
        return configuracao;
    }
   
   private String getCodigoMotivoReclamacao(Integer statusCode){
       if(isObjetoComSinistro(statusCode)){
           return Configuracao.MOTIVO_NAO_RECEBEU;
       }
       return Configuracao.MOTIVO_ATRASADO;
   }*/
   
   
   private Map<Date,String> getFeriados() throws SQLException{
        Map<Date,String> mapFeriados = new HashMap<Date, String>();
        List<Holiday> feriaddos = consultaFeriados();
        for (Holiday feriado : feriaddos) {
            mapFeriados.put(feriado.getDate(), feriado.getName());
        }
        return mapFeriados;
    }
   
   private List<Holiday> consultaFeriados() throws SQLException{
       Connection con = Conexao.conectarGeral();
       String sql = "SELECT * FROM portalpostal.feriados_nacionais";
       PreparedStatement prepare = con.prepareStatement(sql);
       ResultSet result = prepare.executeQuery();
       List<Holiday> feriados = new ArrayList<Holiday>();
       while(result.next()){
           Holiday feriado = new Holiday();
           feriado.setDate(result.getDate("data"));
           feriado.setName(result.getString("descricao"));
           feriados.add(feriado);
       }
       con.close();
       return feriados;
   }
   
   /*private void verificaObjetosAtrasados(List<Reclamacao> reclamacoes) throws SQLException{
       feriados = getFeriados();
       for(Iterator iterableReclamacoes = reclamacoes.iterator();iterableReclamacoes.hasNext();){
            removeEntregaSemAtraso(iterableReclamacoes);
       }
   }

    private void removeEntregaSemAtraso(Iterator iterableReclamacoes) {
        Reclamacao reclamacao = (Reclamacao) iterableReclamacoes.next();
        Date dataPrevisaoEntrega = reclamacao.getDestinatario().getPrevisaoEntrega();
        Date dataEntrega = reclamacao.getDestinatario().getDataEntrega();
        Date dataPostagem = reclamacao.getRemetente().getDataPostagem();
        String tipoServico = reclamacao.getRemetente().getNomeServico();

        boolean objetoComSinistro = isObjetoComSinistro(reclamacao.getDestinatario().getLastStatusCode());
        boolean dataValida = isDataValida(dataEntrega, dataPrevisaoEntrega);

        if (objetoComSinistro) {
            return;
        }
        if (!dataValida) {
            iterableReclamacoes.remove();
            return;
        }

        Calendar novadataPrevisaoEntrega = recalculaDataEstimada(dateToCalendar(dataPostagem), dateToCalendar(dataPrevisaoEntrega), tipoServico);
        if (!dataEntrega.after(novadataPrevisaoEntrega.getTime())) {
            iterableReclamacoes.remove();
        }
    }*/
    

    private static boolean isDataValida(Date dataEntrega, Date dataPrevisaoEntrega) {
        return dataEntrega != null && dataPrevisaoEntrega != null;
    }

    private boolean isObjetoComSinistro(Integer statusCode) {
        Integer[] codigoSinistro = new Integer[]{9, 28, 37, 43, 50, 51, 52};
        return Arrays.asList(codigoSinistro).contains(statusCode);
    }

    private boolean isFinalDeSemanaOuFeriado(Calendar calendario, String tipoServico) {
        if (feriados == null || feriados.isEmpty()) {
            try {
                feriados = getFeriados();
            } catch (SQLException ex) {
                feriados = new HashMap<Date, String>();
            }
        }
        return isFeriado(calendario, feriados) || isFinalDeSemana(tipoServico, calendario);
    }

    private boolean isFeriado(Calendar date, Map<Date, String> feriados) {
        return feriados.get(date) != null;
    }

    private boolean isFinalDeSemana(String tipoServico, Calendar data) {
        int diaDaSemana = data.get(Calendar.DAY_OF_WEEK);
        Integer[] consideraFinalDeSemana = considerarSabadoDomingo(tipoServico);
        return Arrays.asList(consideraFinalDeSemana).contains(diaDaSemana);
    }

    private Integer[] considerarSabadoDomingo(String tipoServico) {
       // return  tipoServico.contains("SEDEX") ? new Integer[]{1} : new Integer[]{1, 7};
         return  new Integer[]{1, 7};
    }

    public Calendar recalculaDataEstimada(Calendar dataPostagem, Calendar dataEstimada, String tipoServico) {
        Calendar dataVerificadora = new GregorianCalendar();
        dataVerificadora.setTime(dataPostagem.getTime());
        int quantidadeDiasDePrazo = diferencaDias(dataPostagem, dataEstimada);
        int dia = 0;
        int diaUtil = 0;
        while (diaUtil < quantidadeDiasDePrazo) {
            dia++;
            dataVerificadora.add(Calendar.DATE, 1);
            if (!isFinalDeSemanaOuFeriado(dataVerificadora, tipoServico)) {
                diaUtil++;
            }
        }
        return adicionaDiasExtrasDataEstimada(dataPostagem, dia);
    }

    private Calendar adicionaDiasExtrasDataEstimada(Calendar dataEstimada, int diasExtras) {
        Calendar novaDataEstivada = Calendar.getInstance();
        novaDataEstivada.setTime(dataEstimada.getTime());
        novaDataEstivada.add(Calendar.DATE, diasExtras);
        return novaDataEstivada;
    }

    private int diferencaDias(Calendar dataPostagem, Calendar dataEstimada) {
        long diferenca = dataEstimada.getTime().getTime() - dataPostagem.getTime().getTime();
        int dias = round(diferenca / 1000 / 60 / 60 / 24);
        return dias;
    }

    public Calendar dateToCalendar(Date data) {
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(data);
        return calendario;
    }

}
