<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Entidade.SenhaCliente"%>
<%@page import="Controle.contrSenhaCliente"%>
<%@page import="Entidade.ClientesDeptos"%>
<%@page import="Controle.ContrClienteDeptos"%>
<%@ page import = "java.util.ArrayList,java.sql.Timestamp, java.util.Date, java.text.SimpleDateFormat"%>
<%
    response.setContentType("text/html");
    response.setHeader("Cache-Control", "no-cache");

    String nomeBD = (String) session.getAttribute("empresa");
    if (nomeBD != null) {

        if (session.getAttribute("nivel") != null) {
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

        if (sc.getAcessos() != null) {
            String acc[] = sc.getAcessos().split(";");
            for (int i = 0; i < acc.length; i++) {
                listAcc.add(acc[i]);
            }
        }
        if (sc.getDepartamentos() != null) {
            String dep[] = sc.getDepartamentos().split(";");
            for (int i = 0; i < dep.length; i++) {
                listDep.add(dep[i]);
            }
        }
        if (sc.getServicos() != null) {
            String ser[] = sc.getServicos().split(";");
            for (int i = 0; i < ser.length; i++) {
                listSer.add(ser[i]);
            }
        }


%>

 <form name='form5' action='../../ServEditarLoginPortal' method='post'>
    <div class="row">
        <div class="col-xs-12">
            <ul class="list-group">
                <li class="list-group-item">
                    <div class="row form-horizontal">
                        <div class="col-sm-6 col-md-3 col-lg-2">
                            <label class="small">Login</label>
                            <div class="input-group">
                                <span class="input-group-addon" ><i class="fa fa-user"></i></span>
                                <input class="form-control" type="text" autocomplete="off" placeholder="Login" name="login"  value='<%= login%>' onkeyup="ajaxConfereLogin(this.value, 'fooEditar');"  />
                                <input type='hidden' name='loginaux' value='<%= login%>' />                
                            </div>   
                            <div id='fooEditar'></div>
                        </div>
                        <div class="col-sm-6 col-md-3 col-lg-2">
                            <label class="small">Senha</label>                                            
                            <div class="input-group">
                                <span class="input-group-addon" ><i class="fa fa-lock"></i></span>
                                <input class="form-control" placeholder="Senha" type="password" name="senha" />
                            </div>
                        </div>
                        <div class="col-sm-6 col-md-3 col-lg-2">
                            <label class="small">Repita a Senha</label>                                                                                   
                            <div class="input-group">
                                <span class="input-group-addon" ><i class="fa fa-lock"></i></span>
                                <input class="form-control" placeholder="Repita a senha" type="password" name="senha2" />
                            </div>
                        </div>
                        <div class="col-sm-6 col-md-3 col-lg-2">
                            <label class="small">Nível</label>                                                   
                            <div class="input-group">
                                <span class="input-group-addon" ><i class="fa fa-signal"></i></span>
                                <select class="form-control" name="nivel" >
                                    <option value='1' <%if (sc.getNivel() == 1) {%> selected <%}%>>Administrador</option>
                                    <option value='2' <%if (sc.getNivel() == 2) {%> selected <%}%>>Gerencia</option>
                                    <option value='3' <%if (sc.getNivel() == 3) {%> selected <%}%>>Usuário</option>
                                    <option value='99' <%if (sc.getNivel() == 99) {%> selected <%}%>>Web Service</option>
                                </select>
                            </div>
                        </div>
                    </div>
                </li>
                <li class="list-group-item">
                    <div class="row form-horizontal">
                        <div class="col-sm-12 col-md-4 col-lg-4">
                            <label class="small">
                                PERMISSÕES:<br/>
                                <a style="color:blue;font-size: 9px;" onclick="selectAllCombo(document.getElementById('acessos2'), true);">MARCAR TUDO</a><br/>
                                <a style="color:red;font-size: 9px;" onclick="selectAllCombo(document.getElementById('acessos2'), false);">DESMARCAR TUDO</a>
                            </label>
                            <select class="form-control" name='acessos' id='acessos2' multiple='true' onclick="controleCombobox0(this)" size=10 >
                                <option value="1" <%if (listAcc.contains("1")) {%> selected <%}%> >PESQUISAS / RELATÓRIOS</option>
                                <option value="2" <%if (listAcc.contains("2")) {%> selected <%}%> >CONTROLE DE A.R.</option>
                                <option value="3" <%if (listAcc.contains("3")) {%> selected <%}%> >VISUALIZA VALORES / DESPESAS</option>
                                <option value="4" <%if (listAcc.contains("4")) {%> selected <%}%> >SOLICITAÇÃO DE COLETA</option>
                                <option value="5" <%if (listAcc.contains("5")) {%> selected <%}%> >GERAR ETIQUETAS</option>
                                <option value="6" <%if (listAcc.contains("6")) {%> selected <%}%> >VER DADOS DA EMPRESA</option>
                                <option value="7" <%if (listAcc.contains("7")) {%> selected <%}%> >CADASTRO DE DESTINATÁRIOS</option>
                                <option value="8" <%if (listAcc.contains("8")) {%> selected <%}%> >VISUALIZAR PRAZOS ESTIMADOS DOS CORREIOS</option>
                                <option value="9" <%if (listAcc.contains("9")) {%> selected <%}%> >REIMPRIMIR ETIQUETAS ANTIGAS</option>
                            </select>
                            <script language="">
                                function selectAllCombo(combo, flag) {
                                    for (i = 0; i < combo.length; i++) {
                                        combo.options[i].selected = flag;
                                    }
                                }
                                function controleCombobox0(combo) {
                                    combo_aux0[combo.selectedIndex] = !combo_aux0[combo.selectedIndex];
                                    for (i = 0; i < combo.length; i++) {
                                        combo.options[i].selected = combo_aux0[i];
                                    }
                                }
                                var combo_aux0 = new Array(document.getElementById("acessos2").options.length);
                                for (i = 0; i < document.getElementById("acessos2").options.length; i++) {
                                    combo_aux0[i] = document.getElementById("acessos2").options[i].selected;
                                }

                            </script>
                        </div>
                        <div class="col-sm-12 col-md-4 col-lg-4">
                            <label class="small">
                                DEPARTAMENTOS:<br/>
                                <a style="color:blue;font-size: 9px;" onclick="selectAllCombo(document.getElementById('departamentos2'), true);">MARCAR TUDO</a><br/>
                                <a style="color:red;font-size: 9px;" onclick="selectAllCombo(document.getElementById('departamentos2'), false);">DESMARCAR TUDO</a>
                            </label>
                            <select class="form-control" name='departamentos' id='departamentos2' multiple='true' onclick="controleCombobox1(this)" size=10 >
                                <%
                                    ArrayList<ClientesDeptos> listaDep = ContrClienteDeptos.consultaDeptos(sc.getCodigo(), nomeBD);
                                    for (int i = 0; i < listaDep.size(); i++) {
                                        ClientesDeptos cd = listaDep.get(i);
                                %>
                                <option value="<%=cd.getIdDepartamento()%>" <%if (listDep.contains("" + cd.getIdDepartamento())) {%> selected <%}%>><%= cd.getNomeDepartamento()%></option>
                                <%}%>
                            </select>
                            <script language="">
                                function controleCombobox1(combo) {
                                    combo_aux1[combo.selectedIndex] = !combo_aux1[combo.selectedIndex];
                                    for (i = 0; i < combo.length; i++) {
                                        combo.options[i].selected = combo_aux1[i];
                                    }
                                }
                                var combo_aux1 = new Array(document.getElementById("departamentos2").options.length);
                                for (i = 0; i < document.getElementById("departamentos2").options.length; i++) {
                                    combo_aux1[i] = document.getElementById("departamentos2").options[i].selected;
                                }

                            </script>
                        </div>
                        <div class="col-sm-12 col-md-4 col-lg-4">
                            <label class="small">
                                SERVIÇOS:<br/>
                                <a style="color:blue;font-size: 9px;" onclick="selectAllCombo(document.getElementById('servicos2'), true);">MARCAR TUDO</a><br/>
                                <a style="color:red;font-size: 9px;" onclick="selectAllCombo(document.getElementById('servicos2'), false);">DESMARCAR TUDO</a>
                            </label>
                            <select class="form-control" name="servicos" id="servicos2" multiple='true' onclick="controleCombobox2(this)" size="10" >
                                <option value="1" <%if (listSer.contains("1")) {%> selected <%}%> >PAC</option>
                                <option value="9" <%if (listSer.contains("9")) {%> selected <%}%> >PAC GRD. FORMATOS</option>
                                <option value="2" <%if (listSer.contains("2")) {%> selected <%}%> >SEDEX</option>
                                <option value="4" <%if (listSer.contains("4")) {%> selected <%}%> >SEDEX 10</option>
                                <option value="7" <%if (listSer.contains("7")) {%> selected <%}%> >SEDEX 12</option>
                                <option value="8" <%if (listSer.contains("8")) {%> selected <%}%> >SEDEX HOJE</option>
                                <option value="5" <%if (listSer.contains("5")) {%> selected <%}%> >E-SEDEX</option>
                                <option value="6" <%if (listSer.contains("6")) {%> selected <%}%> >CARTA REGISTRADA</option>
                                <option value="11" <%if (listSer.contains("11")) {%> selected <%}%> >IMPRESSO REGISTRADO</option>
                                <option value="3" <%if (listSer.contains("3")) {%> selected <%}%> >SEDEX A COBRAR</option>
                                <option value="10" <%if (listSer.contains("10")) {%> selected <%}%> >PAC A COBRAR</option>
                                <option value="12" <%if (listSer.contains("12")) {%> selected <%}%> >MDPB REGISTRADO</option>
                            </select>
                            <script language="">
                                function controleCombobox2(combo) {
                                    combo_aux2[combo.selectedIndex] = !combo_aux2[combo.selectedIndex];
                                    for (i = 0; i < combo.length; i++) {
                                        combo.options[i].selected = combo_aux2[i];
                                    }
                                }
                                var combo_aux2 = new Array(document.getElementById("servicos2").options.length);
                                for (i = 0; i < document.getElementById("servicos2").options.length; i++) {
                                    combo_aux2[i] = document.getElementById("servicos2").options[i].selected;
                                }

                            </script>
                        </div>
                    </div>
                </li>
            </ul>
                            
                    <div class="alert alert-danger">
                    <i style='width: 100%; padding-top: 30px;color:red;'>*Obs.: Caso não queira mudar a senha deixe os campos da senha em branco!</i>
                    <input type="hidden" name="id" value="<%= id%>" />
                    <input type="hidden" name="local" value="<%= local%>" />
                    <input type='hidden' name='idCliente' value='<%= sc.getCodigo()%>' />
                    <input type='hidden' name='loginOld' value='<%= login%>' />
                    <input type='hidden' name='senhaOld' value='<%= senha%>' />
                    </div>
        </div>
    </div>
</form>
<%}else{%>
sessaoexpirada
<%}%>