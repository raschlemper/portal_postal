<%@page import="Entidade.SenhaCliente"%>
<%@page import="Controle.contrSenhaCliente"%>
<%@page import="Entidade.ClientesDeptos"%>
<%@page import="Controle.ContrClienteDeptos"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import = "java.util.ArrayList,java.sql.Timestamp, java.util.Date, java.text.SimpleDateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    /*
     * AJAX
     */
    response.setContentType("text/xml");
    response.setHeader("Cache-Control", "no-cache");
    String resultado = "sessaoexpirada";

    String nomeBD = (String) session.getAttribute("empresa");
    if (nomeBD != null) {
        
        if(session.getAttribute("nivel") != null){
            int idNivelDoUsuario = (Integer) session.getAttribute("nivel");
            if (idNivelDoUsuario == 3) {
                response.sendRedirect("../Importacao/imp_movimento.jsp?msg=Acesso Negado!");
            }
        }
        
        int id = Integer.parseInt(request.getParameter("id"));
        String local = request.getParameter("local");
        
        SenhaCliente sc = contrSenhaCliente.consultaSenhaClienteById(id, nomeBD);
        String login = sc.getLogin();
        String senha = sc.getSenha();
        
        ArrayList<String> listAcc = new ArrayList<String>();
        ArrayList<String> listDep = new ArrayList<String>();
        ArrayList<String> listSer = new ArrayList<String>();
        
        if(sc.getAcessos() != null){
            String acc[] = sc.getAcessos().split(";");
            for(int i=0; i<acc.length; i++){
                listAcc.add(acc[i]);
            }
        }
        if(sc.getDepartamentos() != null){
            String dep[] = sc.getDepartamentos().split(";");
            for(int i=0; i<dep.length; i++){
                listDep.add(dep[i]);
            }
        }
        if(sc.getServicos() != null){
            String ser[] = sc.getServicos().split(";");
            for(int i=0; i<ser.length; i++){ 
                listSer.add(ser[i]);
            }
        }
        
        
%>

<div style="width: 100%; margin: 15px;">
    <div style="width: 95%; text-align: left;">
        <div style='float:right;'><a onclick='chamaDivProtecao();' href='#' class='botaoClose'>Fechar</a></div>
        <div id='titulo1'>Editar Login do Cliente</div>
    </div>
    <img style="margin-bottom: 25px;" width="100%" src="../../imagensNew/linha.jpg"/>
    <form name='form5' action='../../ServEditarLoginPortal' method='post'>
        <ul style="width: 95%; text-align: left;" class="ul_formulario">
            <li>
                <dd>
                    <label>Login do Cliente</label>
                    <input style='width:200px;' autocomplete="off" type='text' name='login' value='<%= login%>'onkeyup='confereLoginPortalEditar(this.value);' />
                    <input type='hidden' name='loginaux' value='<%= login%>' />
                    <div id='fooEditar'></div>
                </dd>
                <dd>
                    <label>Senha</label>
                    <input style='width:200px;' type='password' name='senha' />
                </dd>
                <dd>
                    <label>Repita a Senha</label>
                    <input style='width:200px;' type='password' name='senha2' />
                </dd>
                <dd>
                    <label>Nivel:</label>
                    <select style='width:200px;' name='nivel'>
                        <option value='1' <%if(sc.getNivel() == 1){%> selected <%}%>>Administrador</option>
                        <option value='2' <%if(sc.getNivel() == 2){%> selected <%}%>>Gerencia</option>
                        <option value='3' <%if(sc.getNivel() == 3){%> selected <%}%>>Usuário</option>
                    </select>
                </dd>
            </li>
            <li>
                <dd>
                    <label>ACESSOS22:</label>
                    <select style="width: 206px;" name=acessos id=acessos multiple onclick="controleCombobox0(this)" size=10 >
                        <option value="1" <%if(listAcc.contains("1")){%> selected <%}%> >PESQUISAS / RELATÓRIOS</option>
                        <option value="2" <%if(listAcc.contains("2")){%> selected <%}%> >CONTROLE DE A.R.</option>
                        <option value="3" <%if(listAcc.contains("3")){%> selected <%}%> >VISUALIZA VALORES / DESPESAS</option>
                        <option value="4" <%if(listAcc.contains("4")){%> selected <%}%> >SOLICITAÇÃO DE COLETA</option>
                        <option value="5" <%if(listAcc.contains("5")){%> selected <%}%> >GERAR ETQIUETAS</option>
                        <option value="6" <%if(listAcc.contains("6")){%> selected <%}%> >VER DADOS DA EMPRESA</option>
                        <option value="7" <%if(listAcc.contains("7")){%> selected <%}%> >CADASTRO DE DESTINATÁRIOS</option>
                    </select>
                    <script language="">
                        function controleCombobox0(combo) {
                            combo_aux0[combo.selectedIndex] = !combo_aux0[combo.selectedIndex];
                            for (i=0; i < combo.length; i++ ){
                                combo.options[i].selected = combo_aux0[i];
                            }
                        }
                        var combo_aux0 = new Array(document.getElementById("acessos").options.length);
                        for (i=0; i < document.getElementById("acessos").options.length; i++ ) {
                            combo_aux0[i] = document.getElementById("acessos").options[i].selected;
                        }

                    </script>
                </dd>
                <dd>
                    <label>DEPARTAMENTOS:</label>
                    <select style="width: 206px;" name=departamentos id=departamentos multiple onclick="controleCombobox1(this)" size=10 >
                        <%
                            ArrayList<ClientesDeptos> listaDep = ContrClienteDeptos.consultaDeptos(sc.getCodigo(), nomeBD);
                            for (int i = 0; i < listaDep.size(); i++) {
                                ClientesDeptos cd = listaDep.get(i);
                        %>
                        <option value="<%=cd.getIdDepartamento()%>" <%if(listDep.contains(""+cd.getIdDepartamento())){%> selected <%}%>><%= cd.getNomeDepartamento()%></option>
                        <%}%>
                    </select>
                    <script language="">
                        function controleCombobox1(combo) {
                            combo_aux1[combo.selectedIndex] = !combo_aux1[combo.selectedIndex];
                            for (i=0; i < combo.length; i++ ){
                                combo.options[i].selected = combo_aux1[i];
                            }
                        }
                        var combo_aux1 = new Array(document.getElementById("departamentos").options.length);
                        for (i=0; i < document.getElementById("departamentos").options.length; i++ ) {
                            combo_aux1[i] = document.getElementById("departamentos").options[i].selected;
                        }

                    </script>
                </dd>
                <dd>
                    <label>SERVIÇOS:</label>
                    <select style="width: 206px;" name=servicos id=servicos multiple onclick="controleCombobox2(this)" size=10 >
                        <option value="1" <%if(listSer.contains("1")){%> selected <%}%> >PAC</option>
                        <option value="2" <%if(listSer.contains("2")){%> selected <%}%> >SEDEX</option>
                        <option value="3" <%if(listSer.contains("3")){%> selected <%}%> >SEDEX A COBRAR</option>
                        <option value="4" <%if(listSer.contains("4")){%> selected <%}%> >SEDEX 10</option>
                        <option value="5" <%if(listSer.contains("5")){%> selected <%}%> >E-SEDEX</option>
                        <option value="6" <%if(listSer.contains("6")){%> selected <%}%> >CARTA REGISTRADA</option>
                        <option value="7" <%if(listSer.contains("7")){%> selected <%}%> >SEDEX 12</option>
                    </select>
                    <script language="">
                        function controleCombobox2(combo) {
                            combo_aux2[combo.selectedIndex] = !combo_aux2[combo.selectedIndex];
                            for (i=0; i < combo.length; i++ ){
                                combo.options[i].selected = combo_aux2[i];
                            }
                        }
                        var combo_aux2 = new Array(document.getElementById("servicos").options.length);
                        for (i=0; i < document.getElementById("servicos").options.length; i++ ) {
                            combo_aux2[i] = document.getElementById("servicos").options[i].selected;
                        }

                    </script>
                </dd>
            </li>
            <li>
                <dd style='width: 100%; padding-top: 30px;color:red;'>*Obs.: Caso não queira mudar a senha deixe os campos da senha em branco!</dd>
            </li>
        </ul>

        <div class="buttons">
            <input type="hidden" name="id" value="<%= id%>" />
            <input type="hidden" name="local" value="<%= local%>" />
            <input type='hidden' name='idCliente' value='<%= sc.getCodigo() %>' />
            <input type='hidden' name='loginOld' value='<%= login%>' />
            <input type='hidden' name='senhaOld' value='<%= senha%>' />
            <input type='hidden' name='visualizarPrazos' value='<%if(listAcc.contains("8")){%>8<%}%>' />

            <button type='button' class='positive' onclick='return preencherCamposEdit();'><img src="../../imagensNew/tick_circle.png" /> SALVAR DADOS</button>
            <button type='button' class='negative' onClick='chamaDivProtecao();'><img src="../../imagensNew/cross_circle.png" /> CANCELAR</button>
        </div>
    </form>
</div>
<%}%>