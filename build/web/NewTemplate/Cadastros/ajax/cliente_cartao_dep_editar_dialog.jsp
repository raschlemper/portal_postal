<%@page import="br.com.portalpostal.entity.ClienteDepartamento"%>
<%@page import="br.com.portalpostal.componentes.ConexaoEntityManager"%>
<%@page import="br.com.portalpostal.providers.ProviderClienteDepartamento"%>
<%@page import="Controle.ContrClienteDeptos"%>
<%@page import="Entidade.ClientesDeptos"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    response.setContentType("text/html");
    response.setHeader("Cache-Control", "no-cache");
    String nomeBD = (String) session.getAttribute("empresa");
    if (nomeBD != null) {
        int idCliente = Integer.parseInt(request.getParameter("idCliente"));
        int idDep = Integer.parseInt(request.getParameter("idDepartamento"));
        ProviderClienteDepartamento providerClienteDepartamento = new ProviderClienteDepartamento(ConexaoEntityManager.getConnection(nomeBD));
        ClienteDepartamento clienteDepartamento = providerClienteDepartamento.findByPkAtivos(idCliente, idDep);
        String cartao = request.getParameter("cartao");
        String depto = request.getParameter("depto");
%>
<form name='form5' action='../../ServEditarCartaoDep' method='post'>
    <ul class="list-unstyled">
        <li class="list-group-item">
            <div class="row form-horizontal">
                <div class="col-xs-12 col-sm-4 col-md-4 col-lg-4">
                    <label class="small">Nome do Departamento: </label>
                    <div class="input-group">
                        <span class="input-group-addon" ><i class="fa fa-sitemap"></i></span>
                        <input class="form-control" autocomplete="off" type='text' name='nome' placeholder="Nome do Depto." value='<%= depto%>' maxlength="40" />
                    </div>
                </div>
                <div class="col-xs-12 col-sm-4 col-md-4 col-lg-4">
                    <label class="small">Cartão de Postagem: </label>
                    <div class="input-group">
                        <span class="input-group-addon" ><i class="fa fa-credit-card"></i></span>
                        <input class="form-control" autocomplete="off" type='text' name='cartao' placeholder="Depto. sem cartão" value='<%= cartao%>' maxlength="10" />
                    </div>
                </div>
                     <div class="col-xs-12 col-sm-4 col-md-4 col-lg-4">
                    <label class="small">Codigo Referência: </label>
                    <div class="input-group">
                        <span class="input-group-addon" ><i class="fa fa-credit-card"></i></span>
                        <input class="form-control" autocomplete="off" type='text' name='codReferencia' placeholder="Sem codigo ref." value='<%= clienteDepartamento.getCodReferencia() %>' maxlength="10" />
                    </div>
                     </div>
                   
            </div>
        </li>                
        <li class="list-group-item">
            <div class="row form-horizontal">
                    <div class="col-xs-12">
                        <div class="alert alert-danger no-margin no-padding">
                            <b>ATENÇÃO!</b> Caso não tenha cartão de postagem deixe em branco!
                     </div>
                    </div>
            </div>
        </li>                
        <li class="list-group-item list-group-heading">
            <b>Possui endereço diferente? </b>
            <input type="checkbox" name="temEndereco" id="temEndereco" value="1" <%if (clienteDepartamento.getTemEndereco() == 1) {%>checked="true"<%}%> data-size="mini" data-toggle="toggle" data-on="SIM" data-off="NÃO" data-onstyle="success" data-offstyle="danger" />
        </li>
        <li class="list-group-item" id="liTemEndereco" <%if (clienteDepartamento.getTemEndereco() == 0) {%> style="display:none;" <%}%>>
            <div class="row form-horizontal">
                <div class="col-xs-12 col-sm-10 col-md-10 col-lg-10">
                    <label class="small">Nome: </label>
                    <input class="form-control" autocomplete="off" type='text' name='nomeEndereco' placeholder="Nome do Endereço" value='<%= clienteDepartamento.getNomeEndereco()%>' maxlength="40" />
                </div>
                <div class="col-xs-12 col-sm-2 col-md-2 col-lg-2">
                    <label class="small">CEP: </label>
                    <input class="form-control" autocomplete="off" type='text' id="idCep" name='cep' placeholder="CEP" value='<%= clienteDepartamento.getCep()%>' onchange="carregaCep(this.value)" maxlength="40" />
                </div>
                <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                    <label class="small">Logradouro: </label>
                    <input class="form-control" autocomplete="off" type='text' id="idEndereco" name='logradouro' placeholder="Logradouro" value='<%= clienteDepartamento.getLogradouro()%>' maxlength="40" />
                </div>
                <div class="col-xs-12 col-sm-4 col-md-4 col-lg-4">
                    <label class="small">Complemento: </label>
                    <input class="form-control" autocomplete="off" type='text' name='complemento' placeholder="Complemento" value='<%= clienteDepartamento.getComplemento()%>' maxlength="40" />
                </div>
                <div class="col-xs-12 col-sm-2 col-md-2 col-lg-2">
                    <label class="small">Número: </label>
                    <input class="form-control" autocomplete="off" type='text' id="idNumero" name='numero' onchange="carregaMapByEndereco()" placeholder="Número" value='<%= clienteDepartamento.getNumero()%>' maxlength="40" />
                </div>
                
                <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                    <label class="small">Bairro: </label>
                    <input class="form-control" autocomplete="off" type='text' id="idBairro" name='bairro' placeholder="Bairro" value='<%= clienteDepartamento.getBairro()%>' maxlength="40" />
                </div>
                <div class="col-xs-12 col-sm-4 col-md-4 col-lg-4">
                    <label class="small">Cidade: </label>
                    <input class="form-control" autocomplete="off" type='text' id="idCidade" name='cidade' placeholder="Cidade" value='<%= clienteDepartamento.getCidade()%>' maxlength="40" />
                </div>
                <div class="col-xs-12 col-sm-2 col-md-2 col-lg-2">
                    <label class="small">UF: </label>
                    <select id="idUf" name="uf" class="form-control">
                        <option value="" selected='true'>--</option>
                        <option value="AC" <%if(clienteDepartamento.getUf().equals("AC")){%>selected='true'<%}%> >AC</option>
                        <option value="AL" <%if(clienteDepartamento.getUf().equals("AL")){%>selected='true'<%}%> >AL</option>
                        <option value="AP" <%if(clienteDepartamento.getUf().equals("AP")){%>selected='true'<%}%> >AP</option>
                        <option value="AM" <%if(clienteDepartamento.getUf().equals("AM")){%>selected='true'<%}%> >AM</option>
                        <option value="BA" <%if(clienteDepartamento.getUf().equals("BA")){%>selected='true'<%}%> >BA</option>
                        <option value="CE" <%if(clienteDepartamento.getUf().equals("CE")){%>selected='true'<%}%> >CE</option>
                        <option value="DF" <%if(clienteDepartamento.getUf().equals("DF")){%>selected='true'<%}%> >DF</option>
                        <option value="ES" <%if(clienteDepartamento.getUf().equals("ES")){%>selected='true'<%}%> >ES</option>
                        <option value="GO" <%if(clienteDepartamento.getUf().equals("GO")){%>selected='true'<%}%> >GO</option>
                        <option value="MA" <%if(clienteDepartamento.getUf().equals("MA")){%>selected='true'<%}%> >MA</option>
                        <option value="MT" <%if(clienteDepartamento.getUf().equals("MT")){%>selected='true'<%}%> >MT</option>
                        <option value="MS" <%if(clienteDepartamento.getUf().equals("MS")){%>selected='true'<%}%> >MS</option>
                        <option value="MG" <%if(clienteDepartamento.getUf().equals("MG")){%>selected='true'<%}%> >MG</option>
                        <option value="PA" <%if(clienteDepartamento.getUf().equals("PA")){%>selected='true'<%}%> >PA</option>
                        <option value="PB" <%if(clienteDepartamento.getUf().equals("PB")){%>selected='true'<%}%> >PB</option>
                        <option value="PR" <%if(clienteDepartamento.getUf().equals("PR")){%>selected='true'<%}%> >PR</option>
                        <option value="PE" <%if(clienteDepartamento.getUf().equals("PE")){%>selected='true'<%}%> >PE</option>
                        <option value="PI" <%if(clienteDepartamento.getUf().equals("PI")){%>selected='true'<%}%> >PI</option>
                        <option value="RJ" <%if(clienteDepartamento.getUf().equals("RJ")){%>selected='true'<%}%> >RJ</option>
                        <option value="RN" <%if(clienteDepartamento.getUf().equals("RN")){%>selected='true'<%}%> >RN</option>
                        <option value="RS" <%if(clienteDepartamento.getUf().equals("RS")){%>selected='true'<%}%> >RS</option>
                        <option value="RO" <%if(clienteDepartamento.getUf().equals("RO")){%>selected='true'<%}%> >RO</option>
                        <option value="RR" <%if(clienteDepartamento.getUf().equals("RR")){%>selected='true'<%}%> >RR</option>
                        <option value="SC" <%if(clienteDepartamento.getUf().equals("SC")){%>selected='true'<%}%> >SC</option>
                        <option value="SP" <%if(clienteDepartamento.getUf().equals("SP")){%>selected='true'<%}%> >SP</option>
                        <option value="SE" <%if(clienteDepartamento.getUf().equals("SE")){%>selected='true'<%}%> >SE</option>
                        <option value="TO" <%if(clienteDepartamento.getUf().equals("TO")){%>selected='true'<%}%> >TO</option>
                    </select>     
                </div>
            </div><br>
                    <div align="center" id="map"  style="width: 100%; height: 250px">
        </li>
    </ul>
    
    <input type='hidden' name='idCliente' value='<%= idCliente%>' />
    <input type='hidden' name='idDepartamento' value='<%= idDep%>' />
    <input type='hidden' name='latitude' id="latitude" value='<%= clienteDepartamento.getLatitude()%>' />
    <input type='hidden' name='longitude' id="longitude" value='<%= clienteDepartamento.getLongitude()%>' />
    <input class="form-control" type="hidden"  name="lat" id="lat" value="0" />
    <input class="form-control" type="hidden"  name="log" id="log" value="0" />
    
        
        
  
</form>
<script type="text/javascript" src="../../javascript/jx.js"></script>
<script type="text/javascript">
    $(function () {
        $('#temEndereco').bootstrapToggle({
            width: 50
        });
    });
    $('#temEndereco').change(function () {
        $('#liTemEndereco').slideToggle();
        carregaMapa();
        
    });
    
    function carregaCep(cep){
        JxGet('', '../../AjaxPages/ajax_cep_json.jsp?cep='+cep, ajaxCepResult);
    }
    
    function ajaxCepResult(ajax,div){
        if(ajax.readyState == 4 && ajax.status == 200 ){
            var cepJson = JSON.parse(ajax.responseText);
            console.log(cepJson);
            $("#idEndereco").val(cepJson.logradouro);
            $("#idBairro").val(cepJson.bairro);
            $("#idCidade").val(cepJson.cidade);
            $("#idUf").val(cepJson.uf);
            if(cepJson.cidade!=""){
                carregaMapByEndereco();
            }
        }
    }
            var latitude=<%=clienteDepartamento.getLatitude()%>;
            var longitude=<%=clienteDepartamento.getLongitude()%>;
            var geocoder =  new google.maps.Geocoder();
            var map;
            var marker;
          
            function carregaMapa() {
                    var center = new google.maps.LatLng(latitude,longitude);
                    var myOptions = {
                        zoom: 16,
                        center: center,
                        scrollwheel: false,
                        mapTypeId: google.maps.MapTypeId.ROADMAP
                    };
                    map = new google.maps.Map(document.getElementById("map"), myOptions);
                    marker = new google.maps.Marker({
                        map: map,
                        position: map.getCenter(),
                        animation: google.maps.Animation.DROP,
                        draggable: true
                    });

                    google.maps.event.addListener(marker, "dragend", function () {
                        var point = marker.getPosition();
                        map.panTo(point);
                        document.getElementById("latitude").value = point.lat().toFixed(6);
                        document.getElementById("longitude").value = point.lng().toFixed(6);
                    });
               
            }
            
             function carregaMapByEndereco() {
                 var address = getEndereco();
                geocoder.geocode({'address':address }, function (results, status) {
                    if (status == google.maps.GeocoderStatus.OK) {
                        map.setCenter(results[0].geometry.location);
                        marker.setPosition(results[0].geometry.location);
                        marker.setAnimation(google.maps.Animation.DROP);
                        var center = map.getCenter();
                        document.getElementById("latitude").value = center.lat().toFixed(6);
                        document.getElementById("longitude").value = center.lng().toFixed(6);
                    } else {
                        alert("Não foi possivel carregar o endereço");
                    }
                });
            }
            
            function getEndereco(){
                var logradouro = $("#idEndereco").val();
                var numero = $("#idNumero").val();
                var bairro = $("#idBairro").val();
                var cidade = $("#idCidade").val();
                var uf = $("#idUf").val();
                return logradouro+" , "+numero+" - "+bairro+","+ cidade + " - " + uf;  
            }
    
    
    $("#idCep").mask("99999-999");
    
    setTimeout("carregaMapa()",1000);

</script>

<%} else {%>
sessaoexpirada
<%}%>