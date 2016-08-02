<%@page import="Entidade.ArDigital"%><%@page import="Emporium.Controle.ContrArDigital"%><%@page import="Controle.contrCliente"%><%@page import="Entidade.Clientes"%><%@page import="Emporium.Controle.ContrPreVenda"%><%@page import="Entidade.PreVenda"%><%@page import="java.util.Date"%><%@page import="java.text.SimpleDateFormat"%><%@page import="java.util.ArrayList"%><%

    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    int idCliente = Integer.parseInt(request.getParameter("idCliente"));
    String nomeBD = request.getParameter("nomeBD");
    String dataInicio = "";
    String dataFinal = "";
    String arquivo = request.getParameter("arquivo");
    
    response.setContentType("text/plain");
    response.setHeader("Content-disposition", "attachment; filename="+arquivo+".SD1");
    response.setHeader("Cache-Control", "no-cache");

    ArDigital ar = ContrArDigital.consultaPorNomeArquivo(arquivo+".SD1", nomeBD); 
    String seq = ArDigital.getSequenciaByNrSequencia(ar.getSequenciaArquivo());
    Clientes cli = contrCliente.consultaClienteById(idCliente, nomeBD);
    ArrayList<PreVenda> movimentacao = ContrPreVenda.consultaVendasArDigital(nomeBD, idCliente, dataInicio, dataFinal, arquivo+".SD1");
    
    //HEADER DO ARQUIVO
    out.print("8");                                                                           //00-01 = Conteudo 8
    out.print(Util.FormataString.preencheStringCom(cli.getAr_digital()+"", "0", 19, 1));      //01-20 = Código do cliente Definido pela ECT
    out.print(Util.FormataString.preencheStringCom(cli.getNome(), " ", 40, 1));               //20-60 = Nome do Cliente
    out.print(sdf.format(ar.getDataArquivo()));                                               //60-68 = Data do arquivo no formato yyyyMMdd
    out.print(Util.FormataString.preencheStringCom((movimentacao.size()+1)+"", "0", 6, -1));  //68-74 = Quantidade de linhas do arquivo
    out.print(Util.FormataString.preencheStringCom("", "0", 94, 1));                          //74-168 = Filler, preenchido com zeros
    out.print(Util.FormataString.preencheStringCom(seq, "0", 5, -1));                         //168-173 = Sequencial do Arquivo
    out.println(Util.FormataString.preencheStringCom("1", "0", 7, -1));                       //173-180 = Nr da Linha do arquivo
    
    for (int i = 0; i < movimentacao.size(); i++) {
        PreVenda pv = movimentacao.get(i);
        out.print("9");                                                                            //00-01 = Conteudo 8
        out.print(Util.FormataString.preencheStringCom(cli.getAr_digital()+"", "0", 4, -1));       //01-05 = Código ECT do cliente
        out.print(Util.FormataString.preencheStringCom(ar.getSiglaAR(), "0", 2, -1));              //05-07 = Sigla do Ar Digital
        out.print(Util.FormataString.preencheStringCom(pv.getNumObjeto()+"", "0", 13, -1));        //07-20 = SRO
        out.print(Util.FormataString.preencheStringCom("1101", "0", 4, -1));                       //20-24 = Código da operacao 1101 para inserção e 1102 para exclusao
        out.print(Util.FormataString.preencheStringCom("", " ", 16, -1));                          //24-40 = Conteudo, campo livre
        out.print(Util.FormataString.preencheStringCom(pv.getNomeDes(), " ", 40, 1));              //40-80 = Nome do destinatario
        out.print(Util.FormataString.preencheStringCom(pv.getEnderecoDes(), " ", 40, 1));          //80-120 = Endereço do destinatario
        out.print(Util.FormataString.preencheStringCom(pv.getCidadeDes(), " ", 30, 1));            //120-150 = Cidade do Destinatario
        out.print(Util.FormataString.preencheStringCom(pv.getUfDes(), " ", 2, 1));                 //150-152 = UF do Destinatario
        out.print(Util.FormataString.preencheStringCom(pv.getCepDes().replace("-", "").replace(".", ""), " ", 8, 1));//152-160 = CEP do Destinatario
        out.print(Util.FormataString.preencheStringCom("", "0", 8, -1));                           //160-168 = Filler
        out.print(Util.FormataString.preencheStringCom(seq, "0", 5, -1));                          //168-173 = Sequencial do Arquivo
        out.println(Util.FormataString.preencheStringCom((i+2)+"", "0", 7, -1));                   //173-180 = Nr da Linha do Arquivo
    }
%>