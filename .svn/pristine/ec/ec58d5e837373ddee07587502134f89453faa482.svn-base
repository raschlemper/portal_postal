
package br.com.portalpostal.servelet;

import Emporium.Controle.ContrlayoutImportacao;
import Emporium.Entity.LayoutImportacao;
import Emporium.Entity.MapeamentoLayout;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ServletLayoutImportacao extends HttpServlet {

   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String nomeBD = (String) request.getSession().getAttribute("nomeBD");
        String acao = request.getParameter("idAcao");
        String formato = request.getParameter("formato");
        
        MapeamentoLayout mapeamento = new MapeamentoLayout(criaAtributosDoForm(request));
        List<LayoutImportacao> dadosConvertidor = mapeamento.converter(formato);

        try {
            ContrlayoutImportacao importacao = new ContrlayoutImportacao(nomeBD);
            if(acao.equals("SALVAR")){
                salvarDados(importacao, mapeamento, dadosConvertidor);
                response.sendRedirect("Cliente/Cadastros/cadastroLayoutPrePostagem.jsp?layout="+mapeamento.getNomeLayout());
            }else{
                importacao.delete(mapeamento.getNomeLayout());
                response.sendRedirect("Cliente/Cadastros/cadastroLayoutPrePostagem.jsp");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void salvarDados(ContrlayoutImportacao importacao, MapeamentoLayout mapeamento, List<LayoutImportacao> dadosConvertidor) throws SQLException {
        if (importacao.isLayoutCadastrado(mapeamento.getNomeLayout())) {
            importacao.update(dadosConvertidor);
        } else {
            importacao.cadastrar(dadosConvertidor);
        }
    }

    private List<String[]> criaAtributosDoForm(HttpServletRequest request) {
        List<String[]> atributos = new ArrayList<>();

        atributos.add(new String[] {MapeamentoLayout.NOMELAYOUT,request.getParameter("nomeLayout"),null});

        atributos.add(new String[] {MapeamentoLayout.PARAMETROPULALINHA,request.getParameter("parametroPulaLinha"),null});

        atributos.add(new String[] {MapeamentoLayout.OBJETO,request.getParameter("objetoIni"),request.getParameter("objetoFim")});

        atributos.add(new String[] {MapeamentoLayout.IDENTIFICADOR,request.getParameter("identificadorIni"),request.getParameter("identificadorFim")});

        atributos.add(new String[] {MapeamentoLayout.NOME,request.getParameter("nomeIni"),request.getParameter("nomeFim")});

        atributos.add(new String[] {MapeamentoLayout.CEP,request.getParameter("cepIni"),request.getParameter("cepFim")});

        atributos.add(new String[] {MapeamentoLayout.LOGRADOURO,request.getParameter("logradouroIni"),request.getParameter("logradouroFim")});

        atributos.add(new String[] {MapeamentoLayout.NUMERO,request.getParameter("numeroIni"),request.getParameter("numeroFim")});

        atributos.add(new String[] {MapeamentoLayout.COMPLEMENTO,request.getParameter("complementoIni"),request.getParameter("complementoFim")});

        atributos.add(new String[] {MapeamentoLayout.BAIRRO,request.getParameter("bairroIni"),request.getParameter("bairroFim")});

        atributos.add(new String[] {MapeamentoLayout.CIDADE,request.getParameter("cidadeIni"),request.getParameter("cidadeFim")});

        atributos.add(new String[] {MapeamentoLayout.UF,request.getParameter("ufIni"),request.getParameter("ufFim")});

        atributos.add(new String[] {MapeamentoLayout.CODIGOECT,request.getParameter("servicoIni"),request.getParameter("servicoFim")});

        atributos.add(new String[] {MapeamentoLayout.SERVICOADICIONAL,request.getParameter("adicionalIni"),request.getParameter("adicionalFim")});

        atributos.add(new String[] {MapeamentoLayout.VALORDECLARADO,request.getParameter("valorDeclaradoIni"),request.getParameter("valorDeclaradoFim")});
        
        atributos.add(new String[] {MapeamentoLayout.NOTAFISCAL,request.getParameter("notaIni"),request.getParameter("notaFim")});

        atributos.add(new String[] {MapeamentoLayout.SERIENOTAFISCAL,request.getParameter("serieNotaIni"),request.getParameter("serieNotaFim")});

        atributos.add(new String[] {MapeamentoLayout.QUANTIDADEVOLUME,request.getParameter("quantidadeVolumeIni"),request.getParameter("quantidadeVolumeFim")});

        atributos.add(new String[] {MapeamentoLayout.PESO,request.getParameter("pesoIni"),request.getParameter("pesoFim")});

        atributos.add(new String[] {MapeamentoLayout.ALTURA,request.getParameter("alturaIni"),request.getParameter("alturaFim")});

        atributos.add(new String[] {MapeamentoLayout.LARGURA,request.getParameter("larguraIni"),request.getParameter("larguraFim")});

        atributos.add(new String[] {MapeamentoLayout.COMPRIMENTO,request.getParameter("comprimentoIni"),request.getParameter("comprimentoFim")});

        atributos.add(new String[] {MapeamentoLayout.CONTEUDO,request.getParameter("conteudoIni"),request.getParameter("conteudoFim")});

        atributos.add(new String[] {MapeamentoLayout.EMAIL,request.getParameter("emailIni"),request.getParameter("emailFim")});
        
        atributos.add(new String[] {MapeamentoLayout.CELULAR,request.getParameter("celularIni"),request.getParameter("celularFim")});

        atributos.add(new String[] {MapeamentoLayout.OUTRASOBSERVACAO,request.getParameter("outrasObservacaoIni"),request.getParameter("outrasObservacaoFim")});

        atributos.add(new String[] {MapeamentoLayout.OBSERVACAO,request.getParameter("observacaoIni"),request.getParameter("observacaoFim")});

        return atributos;
    }
  
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
   
 
}
