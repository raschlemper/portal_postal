package br.com.portalpostal.importacao.layout;

import Controle.ContrClienteContrato;
import Controle.ContrClienteEtiquetas;
import Controle.ContrServicoAbrangencia;
import Controle.ContrServicoECT;
import Entidade.ArquivoImportacao;
import Entidade.ServicoECT;
import Util.CalculoEtiqueta;
import Util.FormatarDecimal;
import caixapostal.componentes.Conexao;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MontaGridImportacaoOld {

    private ArrayList<ArquivoImportacao> arquivosImportacao;
    private Connection conexao;
    private String nomeDataBase;
    private String aviso;
    private String falha;
    private List<String> falhas;
    private Map<String, ServicoECT> listaServAvista;
    private Map<String, Integer> listaContratoCli;
    private Integer idCliente;

    public MontaGridImportacaoOld(Integer idCliente,ArrayList<ArquivoImportacao> arquivoImportacao, String nomeDB) {
        this.idCliente = idCliente;
        this.arquivosImportacao = arquivoImportacao;
        this.nomeDataBase = nomeDB;
        this.conexao = Conexao.getConnection(nomeDataBase);
        this.listaServAvista = getServicosVista();
        this.listaContratoCli = getServicoContrato(idCliente, nomeDataBase);
        this.falha="";
        this.aviso="";
    }

    public String getAviso() {
        return aviso;
    }

    public String getFalha() {
        return falha;
    }

    public ArrayList<ArquivoImportacao> validaDadosArquivo(String servicoEscolhido) {

       

        for (ArquivoImportacao arquivoImportacao : arquivosImportacao) {
            try {

                arquivoImportacao.setCepSemMascara(String.valueOf(validaCep(arquivoImportacao)));
                int linha = FormatarDecimal.formataInteiro(arquivoImportacao.getNrLinha()) - 1;                
                String servico = getServico(servicoEscolhido, linha, arquivoImportacao, listaContratoCli);
                ajustaValorDeclarado(arquivoImportacao, servico);

                if (isCEPSuspenso(arquivoImportacao, servico)) {
                    falha += "<br/>Linha n." + linha + " <br/>Os envios para o CEP " + arquivoImportacao.getCep() + " estao suspensao ate 20/08/2016!<br/>Motivo: Jogos Olimpicos Rio 2016!<br/>Remova este objeto do arquivo para importar.";
                } else if (isServicoVazio(servico)) {
                    falha += "<br/>Linha n." + arquivoImportacao.getNrLinha() + " - Servico " + arquivoImportacao.getServico() + " invalido!";
                } else if (isEtiquetaInvalida(arquivoImportacao)) {
                    falha += "<br/>Linha n." + arquivoImportacao.getNrLinha() + " - Etiqueta " + arquivoImportacao.getNrObjeto() + " invalida!";
                } else {

                    float peso = 0;
                    float altura = 0;
                    float largura = 0;
                    float comprimento = 0;

                    peso = FormatarDecimal.floatConverter(arquivoImportacao.getPeso());
                    altura = FormatarDecimal.floatConverter(arquivoImportacao.getAltura());
                    largura = FormatarDecimal.floatConverter(arquivoImportacao.getLargura());
                    comprimento = FormatarDecimal.floatConverter(arquivoImportacao.getComprimento());
                    
                    arquivoImportacao.setCodECT(consutaCodigoECTparaServico(arquivoImportacao, listaContratoCli, servico, listaServAvista));
                    arquivoImportacao.setServico(servico);
                    arquivoImportacao.setPeso(peso + "");
                    arquivoImportacao.setAltura(altura + "");
                    arquivoImportacao.setLargura(largura + "");
                    arquivoImportacao.setComprimento(comprimento + "");

                }
            } catch (Exception e) {
                e.printStackTrace();
                falha += "<br/>FALHA AO MONTAR DADOS DO SQL = " + e;
            }

        }

        return arquivosImportacao;
    }

    private static boolean isServicoVazio(String servico) {
        return servico.equals("");
    }

    private String getServico(String servicoEscolhido,  int linha, ArquivoImportacao arquivoImportacao, Map<String, Integer> listaContratoCli) {
        String servico="";
        if (!isServicoDefinidoNoArquivo(servicoEscolhido)) {
            servico = servicoEscolhido;
            if (isMDPB(servico)) {
                servico = ajustaServicoMDPB(linha, arquivoImportacao);
            }
        } else if (isPac(arquivoImportacao)) {
            servico = "PAC";
        } else if (isCarta(arquivoImportacao)) {
            servico = "CARTA";
        } else if (isSimples(arquivoImportacao)) {
            servico = "SIMPLES";
        } else if (isPax(arquivoImportacao)) {
            servico = ajusteServicoPax(arquivoImportacao, listaContratoCli, linha);
        } else if (isESedex(arquivoImportacao)) {
            servico = ajusteServicoESedex(arquivoImportacao, listaContratoCli, linha);
        } else if (isSedex10(arquivoImportacao)) {
            servico = ajusteSedex10(linha, arquivoImportacao);
        } else if (isSedex12(arquivoImportacao)) {
            servico = ajuteSedex12(linha, arquivoImportacao);
        } else if (isSedexHoje(arquivoImportacao)) {
            servico = ajustaSedexHJ(linha, arquivoImportacao);
        } else if (isSEDEX(arquivoImportacao)) {
            servico = "SEDEX";
        } else if (isImpresso(arquivoImportacao)) {
            servico = "IMPRESSO";
        } else if (isMDPB(servico)) {
            servico = "CARTA";
            servico = ajustaServicoMDPB( linha, arquivoImportacao);
        }
        return servico;
    }

    private int consutaCodigoECTparaServico(ArquivoImportacao arquivoImportacao, Map<String, Integer> listaContratoCli, String servico, Map<String, ServicoECT> listaServAvista) {
        int codECT=0;
        if (arquivoImportacao.getContrato().equals("") || !listaContratoCli.containsKey(servico)) {
            codECT = listaServAvista.get(servico).getCodECT();
            if (servico.equals("SIMPLES") || servico.equals("CARTA_MOD")) {
                codECT = listaServAvista.get("CARTA").getCodECT();
            }
        } else {
            codECT = listaContratoCli.get(servico);
        }
        return codECT;
    }



    private static boolean isEtiquetaInvalida(ArquivoImportacao arquivoImportacao) {
        return !arquivoImportacao.getNrObjeto().equals("avista") && !CalculoEtiqueta.validaNumObjeto(arquivoImportacao.getNrObjeto());
    }

    private boolean isCEPSuspenso(ArquivoImportacao arquivoImportacao, String servico) {
        //verificar no BD se arquivoImportacao.getCep() está suspenso para este servico
        boolean isSuspenso = false;
        return isSuspenso;
    }

    private void ajustaValorDeclarado(ArquivoImportacao arquivoImportacao, String servico) {
        Float vd = formataVD(arquivoImportacao);
        if (vd == null) {
            falha += "<br/>Linha n." + arquivoImportacao.getNrLinha() + " - Valor Declr. " + arquivoImportacao.getVd() + " invalido!";
            vd = 0f;
        }
        if (vd > 0 && vd < 17) {
            vd = 17f;
        } else if ((servico.startsWith("IMPRESSO") || servico.startsWith("MDPB") || servico.equals("CARTA")) && vd > 500) {
            vd=500f;
        } else if ((servico.startsWith("PAC") || servico.equals("PAX")) && vd > 3000) {
            vd = 3000f;
        } else if (vd > 10000) {
            vd=10000f;
        }
        arquivoImportacao.setVd(String.valueOf(vd));
    }

    private Float formataVD(ArquivoImportacao arquivoImportacao) throws NumberFormatException {
        Float vd = null;
        try {
            vd = Float.parseFloat(arquivoImportacao.getVd().replace(",", ".").trim());
        } catch (NumberFormatException e) {

        }
        return vd;
    }

    private static boolean isImpresso(ArquivoImportacao arquivoImportacao) {
        return arquivoImportacao.getServico().startsWith("IMPRESSO");
    }

    private static boolean isSEDEX(ArquivoImportacao arquivoImportacao) {
        List<String> possibilidadesSedex = Arrays.asList("40096", "40436", "40444", "40010");
        return arquivoImportacao.getServico().startsWith("SEDEX") || possibilidadesSedex.contains(arquivoImportacao.getServico());
    }

    private String ajustaSedexHJ(int linha, ArquivoImportacao arquivoImportacao) {
        String servico="";
        int cepInt = Integer.valueOf(arquivoImportacao.getCep());
        if (ContrServicoAbrangencia.verificaByCepServico(cepInt, "SEDEXHJ", nomeDataBase)) {
            servico = "SEDEXHJ";
        } else {
            servico = "SEDEX";
            avisoTrocaServico(servico,linha, arquivoImportacao, " nao aceita Sedex Hoje! O servico foi alterado para Sedex!");
            
        }
        return servico;
    }

    private static boolean isSedexHoje(ArquivoImportacao arquivoImportacao) {
        List<String> possibilidadesSedex12 = Arrays.asList("40290");
        String servico = arquivoImportacao.getServico().replace(" ", "");
        return servico.startsWith("SEDEXHJ") || possibilidadesSedex12.contains(servico);
    }

    private String ajuteSedex12(int linha, ArquivoImportacao arquivoImportacao) {
        String servico="";
        int cepInteiro = Integer.valueOf(arquivoImportacao.getCep());
        if (ContrServicoAbrangencia.verificaByCepServico(cepInteiro, "SEDEX12", nomeDataBase)) {
            servico = "SEDEX12";
        } else {
            servico = "SEDEX";
            avisoTrocaServico(servico,linha, arquivoImportacao, " nao aceita Sedex 12! O servico foi alterado para Sedex!");
            
        }
        return servico;
    }

    private boolean isSedex12(ArquivoImportacao arquivoImportacao) {
        List<String> possibilidadesSedex12 = Arrays.asList("40169");
        String servico = arquivoImportacao.getServico().replace(" ", "");
        return servico.startsWith("SEDEX12") || possibilidadesSedex12.contains(servico);
    }

    private String ajusteSedex10(int linha, ArquivoImportacao arquivoImportacao) {
        String servico="";
        int cepInteiro = Integer.valueOf(arquivoImportacao.getCep());
        if (ContrServicoAbrangencia.verificaByCepServico(cepInteiro, "SEDEX10", nomeDataBase)) {
            servico = "SEDEX10";
        } else {
            servico = "SEDEX";
            avisoTrocaServico(servico,linha, arquivoImportacao, " nao aceita Sedex 10! O servico foi alterado para Sedex!");
            
        }
        return servico;
    }

    private static boolean isSedex10(ArquivoImportacao arquivoImportacao) {
        List<String> possibilidadesSedex10 = Arrays.asList("40215");
        String servico = arquivoImportacao.getServico().replace(" ", "");
        return servico.startsWith("SEDEX10") || possibilidadesSedex10.contains(servico);
    }

    private String ajusteServicoESedex(ArquivoImportacao arquivoImportacao, Map<String, Integer> listaContratoCli, int linha) {
        String servico="";
        int cepInterno = Integer.valueOf(arquivoImportacao.getCep());
        if (!isServicoVazio(arquivoImportacao.getContrato()) && listaContratoCli.containsKey("ESEDEX") && ContrServicoAbrangencia.verificaByCepServico(cepInterno, "ESEDEX", nomeDataBase)) {
            servico = "ESEDEX";
        } else { 
            servico = "SEDEX";
            avisoTrocaServico(servico,linha, arquivoImportacao, " nao aceita E-Sedex! O servico foi alterado para Sedex!");
        }

        return servico;
    }

    private String buscaNovaEtiqueta(ArquivoImportacao arquivoImportacao, String servico) {

        String etiqueta = ContrClienteEtiquetas.pegaEtiquetaNaoUtilizadaPorGrupoServComTipoEtiqueta(idCliente, servico, 0, nomeDataBase);
        int codECT = consutaCodigoECTparaServico(arquivoImportacao, listaContratoCli, servico, listaServAvista);
        if (etiqueta != null) {
            return  etiqueta.split(";")[0];
        }
        if (isEtiquetaSolicitada(codECT)) {
            etiqueta = ContrClienteEtiquetas.pegaEtiquetaNaoUtilizadaPorGrupoServComTipoEtiqueta(idCliente, servico, 0, nomeDataBase);
            return etiqueta!=null?etiqueta.split(";")[0]:"";
        }
        return "";
    }

    private boolean isEtiquetaSolicitada(int codECT) {
        return ContrClienteEtiquetas.solicitarEtiquetasSigepWEB(codECT, Controle.contrCliente.consultaClienteById(idCliente, nomeDataBase), nomeDataBase);
    }

    private static boolean isESedex(ArquivoImportacao arquivoImportacao) {
        List<String> posibilidadesESedex = Arrays.asList("81019");
        String servico = arquivoImportacao.getServico().trim().replaceAll("-", "");
        return servico.startsWith("ESEDEX") || posibilidadesESedex.contains(servico);
    }

    private String ajusteServicoPax(ArquivoImportacao arquivoImportacao, Map<String, Integer> listaContratoCli, int linha) {
        String servico="";
        int cepInteiro = Integer.parseInt(arquivoImportacao.getCep());
        if (!isServicoVazio(arquivoImportacao.getContrato()) && listaContratoCli.containsKey("PAX") && ContrServicoAbrangencia.verificaByCepServico(cepInteiro, "PAX", nomeDataBase)) {
            servico = "PAX";
        } else {
            servico = "PAC";
            avisoTrocaServico(servico,linha, arquivoImportacao, " nao aceita PAC GRANDES FORMATOS! O servico foi alterado para PAC!");
        }
        return servico;
    }

    private static boolean isPax(ArquivoImportacao arquivoImportacao) {
        List<String> posibilidadesSimples = Arrays.asList("41300");
        return arquivoImportacao.getServico().startsWith("PAX") || posibilidadesSimples.contains(arquivoImportacao);
    }

    private static boolean isSimples(ArquivoImportacao arquivoImportacao) {
        List<String> posibilidadesSimples = Arrays.asList("10715");
        return arquivoImportacao.getServico().startsWith("SIMPLES") || posibilidadesSimples.contains(arquivoImportacao.getServico());
    }

    private static boolean isCarta(ArquivoImportacao arquivoImportacao) {
        List<String> posibilidadesCarta = Arrays.asList("10014", "10707");
        return arquivoImportacao.getServico().startsWith("CARTA") || posibilidadesCarta.contains(arquivoImportacao.getServico());
    }

    private static boolean isPac(ArquivoImportacao arquivoImportacao1) {
        List<String> posibilidadesPAC = Arrays.asList("41068", "41106", "41211", "41491");
        return arquivoImportacao1.getServico().startsWith("PAC") || posibilidadesPAC.contains(arquivoImportacao1.getServico());
    }

    private String ajustaServicoMDPB( int linha, ArquivoImportacao arquivoImportacao1) {
        String servico="";
        int cepInteiro = Integer.valueOf(arquivoImportacao1.getCep());
        String resultado = ContrServicoAbrangencia.verificaMDPBxCep(cepInteiro, nomeDataBase);
        if (resultado != null && resultado.equals("erro")) {
            servico = "CARTA";
            avisoTrocaServico(servico,linha, arquivoImportacao1, " nao aceita MDPB! O servico foi alterado para CARTA!");
        } else {
            servico = resultado;
        }
        return servico;
    }

    private static boolean isMDPB(String servico) {
        return servico.startsWith("MDPB");
    }

    private static boolean isServicoDefinidoNoArquivo(String servicoEscolhido) {
        return servicoEscolhido.equals("ARQUIVO");
    }

    private int validaCep(ArquivoImportacao arquivoImportacao) {
        String cep = arquivoImportacao.getCep().replace(".", "").replace("-", "");
        int cepInteiro = 0;
        if (cep.length() < 7 || cep.length() > 8) {
            falha += "<br/>Linha n." + arquivoImportacao.getNrLinha() + " - CEP " + arquivoImportacao.getCep() + " invalido!";
        }
        try {
            cepInteiro = Integer.parseInt(cep);
        } catch (NumberFormatException e) {
            falha += "<br/>Linha n." + arquivoImportacao.getNrLinha() + " - CEP " + arquivoImportacao.getCep() + " invalido!";
        }
        return cepInteiro;
    }

    private static Map<String, Integer> getServicoContrato(int idCliente, String nomeBD) {
        //MAP DE SERVICOS DO CONTRATO
        Map<String, Integer> listaContratoCli = ContrClienteContrato.consultaMapContratoClienteByIdCliente(idCliente, nomeBD);
        return listaContratoCli;
    }

    private static Map<String, ServicoECT> getServicosVista() {
        //MAP DE SERVICOS A VISTA
        Map<String, ServicoECT> listaServAvista = ContrServicoECT.consultaMapServicosAvista();
        return listaServAvista;
    }

    private void avisoTrocaServico(String servico, int linha, ArquivoImportacao arquivoImportacao, String msg) {
        aviso += "<br/>Destinatario :"+arquivoImportacao.getNome()+" - Linha n." + linha + " - CEP " + arquivoImportacao.getCep() + msg;
        aviso += "<br/><br/>ATENCÃO ******Foi trocado o numero de etiqueta!******<br/>";
        aviso += "<br/>REIMPRIMA ESTA ETIQUETA E TROQUE NO OBJETO";
        
        String etiqueta = buscaNovaEtiqueta(arquivoImportacao, servico);
        if(etiqueta.isEmpty()){
        falha += "<br/>Destinatario :"+arquivoImportacao.getNome()+" - Linha n." + linha + " - CEP " + arquivoImportacao.getCep() + msg.replace("O servico foi alterado", " Tentamos alterar o servico<br/>");
        falha += "<br/>Porem ocorreu falha ao solicitar range de etiquetas para " + servico + "!";

        }

      arquivoImportacao.setNrObjeto(etiqueta);
    }

}
