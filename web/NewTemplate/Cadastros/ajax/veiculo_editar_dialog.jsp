<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import = "java.util.ArrayList,java.sql.Timestamp, java.util.Date, java.text.SimpleDateFormat"%>
<%
    /*AJAX*/
    response.setContentType("text/xml");
    response.setHeader("Cache-Control", "no-cache");
    String resultado = "sessaoexpirada";

    String nomeBD = (String) session.getAttribute("empresa");
    if (nomeBD == null) return;

//    int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
//    Entidade.Usuario col = Controle.contrUsuario.consultaUsuarioById(idUsuario, nomeBD);
//    String nome = col.getNome();
//    String login = col.getLogin();
//    String senha = col.getSenha();
//    String email = col.getEmail();
//    int nivelUs = col.getIdNivel();
//    int usaPortal = col.getUsaPortalPostal();
//    int usaConsolidador = col.getUsaConsolidador();
//    ArrayList<Integer> listaAcPortal = col.getListaAcessosPortalPostal();
//    ArrayList<Integer> listaAcConsol = col.getListaAcessosConsolidador();

%>
<form name="veiculoEditForm" action="${pageContext.request.contextPath}/veiculo/update" method="post">
    <ul class="list-unstyled">
        <li class="list-group-item">
            <div class="row form-horizontal">
                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                    <label class="small">Tipo</label>
                    <div class="input-group">
                        <span class="input-group-addon" ><i class="fa fa-car"></i></span>
                        <select class="form-control tipo" name="tipo"></select>
                    </div>
                </div>
                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                    <label class="small">Marca</label>
                    <div class="input-group">
                        <span class="input-group-addon" ><i class="fa fa-car"></i></span>
                        <select class="form-control marca" name="marca"></select>
                    </div>
                </div>
                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                    <label class="small">Modelo</label>
                    <div class="input-group">
                        <span class="input-group-addon" ><i class="fa fa-car"></i></span>
                        <select class="form-control modelo" name="modelo"></select>
                    </div>
                </div>
                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                    <label class="small">Placa</label>
                    <div class="input-group">
                        <span class="input-group-addon" ><i class="fa fa-asterisk"></i></span>
                        <input type="text" autocomplete="off" name="placa" class="form-control placa" placeholder="Placa"/>                                    
                    </div>
                </div>
                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                    <label class="small">Ano Fabricação</label>
                    <div class="input-group">
                        <span class="input-group-addon" ><i class="fa fa-asterisk"></i></span>
                        <input type="text" autocomplete="off" name="anoFabricacao" class="form-control ano" placeholder="Ano de Fabricação"/>                                    
                    </div>
                </div>
                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                    <label class="small">Ano Modelo</label>
                    <div class="input-group">
                        <span class="input-group-addon" ><i class="fa fa-asterisk"></i></span>
                        <input type="text" autocomplete="off" name="anoModelo" class="form-control ano" placeholder="Ano do Modelo"/>                                    
                    </div>
                </div>
                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                    <label class="small">Chassis</label>
                    <div class="input-group">
                        <span class="input-group-addon" ><i class="fa fa-asterisk"></i></span>
                        <input type="text" autocomplete="off" name="chassis" class="form-control chassis" placeholder="Chassis"/>                                    
                    </div>
                </div>
                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                    <label class="small">Renavam</label>
                    <div class="input-group">
                        <span class="input-group-addon" ><i class="fa fa-asterisk"></i></span>
                        <input type="text" autocomplete="off" name="renavam" class="form-control renavam" placeholder="Renavam"/>                                    
                    </div>
                </div>
                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                    <label class="small">Quilometragem</label>
                    <div class="input-group">
                        <span class="input-group-addon" ><i class="fa fa-asterisk"></i></span>
                        <input type="text" autocomplete="off" name="quilometragem" class="form-control number" placeholder="Quilometragem"/>                                    
                    </div>
                </div>
                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                    <label class="small">Tipo Combustível</label>
                    <div class="input-group">
                        <span class="input-group-addon" ><i class="fa fa-info"></i></span>
                        <select class="form-control combustivel" name="combustivel"></select>
                    </div>
                </div>
                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                    <label class="small">Estado Veículo</label>
                    <div class="input-group">
                        <span class="input-group-addon" ><i class="fa fa-info"></i></span>
                        <select class="form-control status" name="status"></select>
                    </div>
                </div>
                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                    <label class="small">Situação</label>
                    <div class="input-group">
                        <span class="input-group-addon" ><i class="fa fa-info"></i></span>
                        <select class="form-control situacao" name="situacao"></select>
                    </div>
                </div>
            </div>
        </li>
    </ul>
</form>
    
    
        <script type="text/javascript">  
            
            var veiculo = new Veiculo(veiculoEditForm); 
            
            var init = function(form) {
//                veiculo.funcoes.loading();
                addPageListas();
                addMascaras();
                addPageEventos();
            };
            
            var addPageListas = function() { 
                veiculo.funcoes.setTipoVeiculo(1);                
                veiculo.funcoes.setCombustivel();
                veiculo.funcoes.setEstado();
                veiculo.funcoes.setSituacao();                
                veiculo.acoes.pesquisarTodos();
            }
            
            var addMascaras = function() {
                veiculo.mascara.addMascaraPlaca();
                veiculo.mascara.addMascaraAno();
                veiculo.mascara.addMascaraChassis();
                veiculo.mascara.addMascaraRenavam();
                veiculo.mascara.addMascaraNumber();
            }
            
            var addPageEventos = function() { 
                veiculo.eventos.addTipoEventListener();
                veiculo.eventos.addMarcaEventListener();
                veiculo.eventos.addSalvarEventListener();
            };
            
            init();
            
        </script>