
<%@ page import = "java.text.DateFormat,java.util.ArrayList,java.sql.Timestamp,java.util.Calendar, java.util.GregorianCalendar, java.util.Date, java.text.SimpleDateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%
    if (session.getAttribute("usuario") == null) {
        response.sendRedirect("../index.jsp?msgLog=3");
    } else {

        String nomeBD = (String) session.getAttribute("empresa");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        Date dataAtual = new Date();
        String vDataAtual = sdf.format(dataAtual);
        String dataAnterior = Util.SomaData.SomarDiasDatas(dataAtual, -30);
        if (request.getParameter("dataFim") != null) {
            vDataAtual = request.getParameter("dataFim");
        }
        if (request.getParameter("dataIni") != null) {
            dataAnterior = request.getParameter("dataIni");
        }

        String vDataInicio = Util.FormatarData.DateToBD(dataAnterior);
        String vDataFinal = Util.FormatarData.DateToBD(vDataAtual);

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

    <head>
        <title> Dashbord Portal Postal</title>
        <%@ include file="../includes/Css_js.jsp" %>
    </head>
    <body onload="fechaMsg();">   
        <script>
            waitMsg();
        </script>
        <jsp:include page="../includes/navBarTop.jsp"></jsp:include>
            <div id="wrapper">
            <%@ include file="../includes/menu_agencia_bootstrap.jsp" %>

            <div id="page-content-wrapper">
                <div class="container-fluid">
                    <div id="page-wrapper">
                    <div class="row">
                        <div class="well well-md">  
                            <div>
                                <h4 class="page">Escolha um periodo</h4>
                            </div> 
                            <form class="form-inline" action="" method="post"> 
                                <div class="form-group" >                                                
                                    <label for="from">De</label>
                                    <div class="input-group">
                                        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                        <input class="form-control" size="8" type="text" id="dateIni" name="dateIni" value="<%= dataAnterior%>" onkeypress="mascara(this, maskData);"/>
                                    </div>
                                    <label for="to">à</label>
                                    <div class="input-group">
                                        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                        <input class="form-control" size="8" type="text" id="dateFin" name="dateFin" value="<%=vDataAtual%>" onkeypress="mascara(this, maskData);" />
                                    </div>
                                </div>
                                <button type="submit" class="btn btn-primary btn-circle"><i class="fa fa-search"></i></button>
                            </form>
                        </div>  
                    </div>
                    <div class="well-lg"></div>
                    <!-- /.row -->
                    <div class="row">
                        <div class="col-lg-3 col-md-6">
                            <div class="panel panel-success">
                                <div class="panel-heading">
                                    <div class="row">
                                        <div class="col-xs-3">
                                            <i class="fa fa-dollar fa-5x"></i>
                                        </div>
                                        <div class="col-xs-9 text-right">
                                            <div class="huge">R$ 26,35</div>
                                            <div>Ticket Médio</div>
                                        </div>
                                    </div>
                                </div>
                                <a href="#">
                                    <div class="panel-footer">
                                        <span class="pull-left">Mais Detalhes</span>
                                        <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                        <div class="clearfix"></div>
                                    </div>
                                </a>
                            </div>
                        </div>
                        <div class="col-lg-3 col-md-6">
                            <div class="panel panel-info">
                                <div class="panel-heading">
                                    <div class="row">
                                        <div class="col-xs-3">
                                            <i class="fa fa-users fa-5x"></i>
                                        </div>
                                        <div class="col-xs-9 text-right">
                                            <div class="huge">196</div>
                                            <div>Clientes Ativos</div>
                                        </div>
                                    </div>
                                </div>
                                <a href="#">
                                    <div class="panel-footer">
                                        <span class="pull-left">Mais Detalhes</span>
                                        <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                        <div class="clearfix"></div>
                                    </div>
                                </a>
                            </div>
                        </div>
                        <div class="col-lg-3 col-md-6">
                            <div class="panel panel-red">
                                <div class="panel-heading">
                                    <div class="row">
                                        <div class="col-xs-3">
                                            <i class="fa fa-warning fa-5x"></i>
                                        </div>
                                        <div class="col-xs-9 text-right">
                                            <div class="huge">31</div>
                                            <div>Clientes sem Movimento</div>
                                        </div>
                                    </div>
                                </div>
                                <a href="#">
                                    <div class="panel-footer">
                                        <span class="pull-left">Mais Detalhes</span>
                                        <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                        <div class="clearfix"></div>
                                    </div>
                                </a>
                            </div>
                        </div>
                        <div class="col-lg-3 col-md-6">
                            <div class="panel panel-yellow">
                                <div class="panel-heading">
                                    <div class="row">
                                        <div class="col-xs-3">
                                            <i class="fa fa-barcode fa-5x"></i>
                                        </div>
                                        <div class="col-xs-9 text-right">
                                            <div class="huge">1254</div>
                                            <div>Etq. Pre-postagem</div>
                                        </div>
                                    </div>
                                </div>
                                <a href="#">
                                    <div class="panel-footer">
                                        <span class="pull-left">Mais Detalhes</span>
                                        <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                        <div class="clearfix"></div>
                                    </div>
                                </a>
                            </div>
                        </div>
                    </div>
                    <!-- /.row -->
                    <div class="row">
                        <div class="col-lg-12">

                            <div class="box">
                                <div class="box-content">
                                    <div class="panel-heading">
                                        <i class="fa fa-th  fa-fw"></i> Forms Example

                                    </div>
                                    <!-- /.panel-heading -->
                                    <form class="form-horizontal" role="form">
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">First name</label>
                                            <div class="col-sm-4">
                                                <input type="text" class="form-control" placeholder="First name" data-toggle="tooltip" data-placement="bottom" title="Tooltip for name"/>
                                            </div>
                                            <label class="col-sm-2 control-label">Last name</label>
                                            <div class="col-sm-4">
                                                <input type="text" class="form-control" placeholder="Last name" data-toggle="tooltip" data-placement="bottom" title="Tooltip for last name"/>
                                            </div>
                                        </div>
                                        <div class="form-group has-success has-feedback">
                                            <label class="col-sm-2 control-label">Company</label>
                                            <div class="col-sm-4">
                                                <input type="text" class="form-control" placeholder="Company">
                                            </div>
                                            <label class="col-sm-2 control-label">Address</label>
                                            <div class="col-sm-4">
                                                <input type="text" class="form-control" placeholder="Address">
                                                    <span class="fa fa-check-square-o txt-success form-control-feedback"></span>
                                            </div>
                                        </div>
                                        <div class="form-group has-warning has-feedback">
                                            <label class="col-sm-2 control-label">Residence</label>
                                            <div class="col-sm-2">
                                                <input type="text" class="form-control" placeholder="City">
                                                    <span class="fa fa-key txt-warning form-control-feedback"></span>
                                            </div>
                                            <div class="col-sm-2">
                                                <input type="text" class="form-control" placeholder="Country">
                                                    <span class="fa fa-frown-o txt-danger form-control-feedback"></span>
                                            </div>
                                            <label class="col-sm-1 control-label">CODE</label>
                                            <div class="col-sm-2">
                                                <input type="text" class="form-control" placeholder="Another info" data-toggle="tooltip" data-placement="top" title="Hello world!">
                                            </div>
                                            <div class="col-sm-2">
                                                <div class="checkbox">
                                                    <label>
                                                        <input type="checkbox" checked> No exist
                                                            <i class="fa fa-square-o small"></i>
                                                    </label>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group has-warning has-feedback">
                                            <label class="col-sm-2 control-label">Select you OS</label>
                                            <div class="col-sm-4">
                                                <select id="s2_with_tag" multiple="multiple" class="populate placeholder">
                                                    <option>Linux</option>
                                                    <option>Windows</option>
                                                    <option>OpenSolaris</option>
                                                    <option>FirefoxOS</option>
                                                    <option>MeeGo</option>
                                                    <option>Android</option>
                                                    <option>Sailfish OS</option>
                                                    <option>Plan9</option>
                                                    <option>DOS</option>
                                                    <option>AIX</option>
                                                    <option>HP/UP</option>
                                                </select>
                                            </div>
                                            <label class="col-sm-2 control-label">Tooltip for inputs</label>
                                            <div class="col-sm-2">
                                                <input type="text" class="form-control" placeholder="Another info" data-toggle="tooltip" data-placement="top" title="Hello world!">
                                            </div>
                                            <span class="help-inline col-xs-12 col-sm-2">
                                                <span class="middle txt-default">only example</span>
                                            </span>
                                        </div>
                                        <div class="form-group has-error has-feedback">
                                            <label class="col-sm-2 control-label">Date</label>
                                            <div class="col-sm-2">
                                                <input type="text" id="input_date" class="form-control" placeholder="Date">
                                                    <span class="fa fa-calendar txt-danger form-control-feedback"></span>
                                            </div>
                                            <div class="col-sm-2">
                                                <input type="text" id="input_time" class="form-control" placeholder="Time">
                                                    <span class="fa fa-clock-o txt-danger form-control-feedback"></span>
                                            </div>
                                            <label class="col-sm-2 control-label">Disabled input</label>
                                            <div class="col-sm-2">
                                                <input type="text" class="form-control" placeholder="No info" data-toggle="tooltip" data-placement="top" title="Hello world!" disabled>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label" for="form-styles">Relative Sizing</label>
                                            <div class="col-sm-10">
                                                <div class="row">
                                                    <div class="col-sm-2">
                                                        <input class="form-control" id="form-styles" placeholder=".col-sm-2" type="text">
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-sm-12">
                                                        <p><small>Dynamic resizing col</small></p>
                                                        <div class="progress progress-ui">
                                                            <div class="progress-bar progress-bar-success slider-style slider-range-min" style="width: 100%;"></div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label" for="form-styles">Textarea</label>
                                            <div class="col-sm-10">
                                                <textarea class="form-control" rows="5" id="wysiwig_simple"></textarea>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label" for="form-styles">Extreme Textarea</label>
                                            <div class="col-sm-10">
                                                <textarea class="form-control" rows="5" id="wysiwig_full"></textarea>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Input groups</label>
                                            <div class="col-sm-2">
                                                <div class="input-group">
                                                    <span class="input-group-addon"><i class="fa fa-github-square"></i></span>
                                                    <input type="text" class="form-control" placeholder="GitHub">
                                                </div>
                                            </div>
                                            <div class="col-sm-2">
                                                <div class="input-group">
                                                    <input type="text" class="form-control" placeholder="Group">
                                                        <span class="input-group-addon"><i class="fa fa-group"></i></span>
                                                </div>
                                            </div>
                                            <div class="col-sm-2">
                                                <div class="input-group">
                                                    <span class="input-group-addon"><i class="fa fa-money"></i></span>
                                                    <input type="text" class="form-control" placeholder="Money">
                                                        <span class="input-group-addon"><i class="fa fa-usd"></i></span>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="clearfix"></div>
                                        <div class="form-group">
                                            <div class="col-sm-offset-2 col-sm-2">
                                                <button type="cancel" class="btn btn-default btn-label-left">
                                                    <span><i class="fa fa-clock-o txt-danger"></i></span>
                                                    Cancel
                                                </button>
                                            </div>
                                            <div class="col-sm-2">
                                                <button type="submit" class="btn btn-warning btn-label-left">
                                                    <span><i class="fa fa-clock-o"></i></span>
                                                    Send later
                                                </button>
                                            </div>
                                            <div class="col-sm-2">
                                                <button type="submit" class="btn btn-primary btn-label-left">
                                                    <span><i class="fa fa-clock-o"></i></span>
                                                    Submit
                                                </button>
                                            </div>
                                        </div>
                                    </form>
                                    <!-- /.panel-body -->
                                </div>
                                <div class="row">
                                    <div class="col-xs-12 col-sm-8">
                                        <div class="box">
                                            <div class="box-header">
                                                <div class="box-name">
                                                    <i class="fa fa-search"></i>
                                                    <span>Validator forms</span>
                                                </div>
                                                <div class="box-icons">
                                                    <a class="collapse-link">
                                                        <i class="fa fa-chevron-up"></i>
                                                    </a>
                                                    <a class="expand-link">
                                                        <i class="fa fa-expand"></i>
                                                    </a>
                                                    <a class="close-link">
                                                        <i class="fa fa-times"></i>
                                                    </a>
                                                </div>
                                                <div class="no-move"></div>
                                            </div>
                                            <div class="box-content">
                                                <form id="defaultForm" method="post" action="validators.html" class="form-horizontal">
                                                    <fieldset>
                                                        <legend>Not Empty validator</legend>
                                                        <div class="form-group">
                                                            <label class="col-sm-3 control-label">Username</label>
                                                            <div class="col-sm-5">
                                                                <input type="text" class="form-control" name="username" />
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <label class="col-sm-3 control-label">Country</label>
                                                            <div class="col-sm-5">
                                                                <select class="populate placeholder" name="country" id="s2_country">
                                                                    <option value="">-- Select a country --</option>
                                                                    <option value="fr">France</option>
                                                                    <option value="de">Germany</option>
                                                                    <option value="it">Italy</option>
                                                                    <option value="jp">Japan</option>
                                                                    <option value="ru">Russia</option>
                                                                    <option value="gb">United Kingdom</option>
                                                                    <option value="us">United State</option>
                                                                </select>
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <div class="col-sm-9 col-sm-offset-3">
                                                                <div class="checkbox">
                                                                    <label>
                                                                        <input type="checkbox"  name="acceptTerms" /> Accept the terms and policies
                                                                        <i class="fa fa-square-o small"></i>
                                                                    </label>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </fieldset>
                                                    <fieldset>
                                                        <legend>Regular expression based validators</legend>
                                                        <div class="form-group">
                                                            <label class="col-sm-3 control-label">Email address</label>
                                                            <div class="col-sm-5">
                                                                <input type="text" class="form-control" name="email" />
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <label class="col-sm-3 control-label">Website</label>
                                                            <div class="col-sm-5">
                                                                <input type="text" class="form-control" name="website" placeholder="http://" />
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <label class="col-sm-3 control-label">Phone number</label>
                                                            <div class="col-sm-5">
                                                                <input type="text" class="form-control" name="phoneNumber" />
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <label class="col-sm-3 control-label">Hex color</label>
                                                            <div class="col-sm-3">
                                                                <input type="text" class="form-control" name="color" />
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <label class="col-sm-3 control-label">US zip code</label>
                                                            <div class="col-sm-3">
                                                                <input type="text" class="form-control" name="zipCode" />
                                                            </div>
                                                        </div>
                                                    </fieldset>
                                                    <fieldset>
                                                        <legend>Identical validator</legend>
                                                        <div class="form-group">
                                                            <label class="col-sm-3 control-label">Password</label>
                                                            <div class="col-sm-5">
                                                                <input type="password" class="form-control" name="password" />
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <label class="col-sm-3 control-label">Retype password</label>
                                                            <div class="col-sm-5">
                                                                <input type="password" class="form-control" name="confirmPassword" />
                                                            </div>
                                                        </div>
                                                    </fieldset>
                                                    <fieldset>
                                                        <legend>Other validators</legend>
                                                        <div class="form-group">
                                                            <label class="col-sm-3 control-label">Ages</label>
                                                            <div class="col-sm-3">
                                                                <input type="text" class="form-control" name="ages" />
                                                            </div>
                                                        </div>
                                                    </fieldset>
                                                    <div class="form-group">
                                                        <div class="col-sm-9 col-sm-offset-3">
                                                            <button type="submit" class="btn btn-primary">Submit</button>
                                                        </div>
                                                    </div>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xs-12 col-sm-4">
                                        <div class="box">
                                            <div class="box-header">
                                                <div class="box-name">
                                                    <i class="fa fa-search"></i>
                                                    <span>Contextual backgrounds</span>
                                                </div>
                                                <div class="box-icons">
                                                    <a class="collapse-link">
                                                        <i class="fa fa-chevron-up"></i>
                                                    </a>
                                                    <a class="expand-link">
                                                        <i class="fa fa-expand"></i>
                                                    </a>
                                                    <a class="close-link">
                                                        <i class="fa fa-times"></i>
                                                    </a>
                                                </div>
                                                <div class="no-move"></div>
                                            </div>
                                            <div class="box-content">
                                                <p class="bg-primary">Simple info</p>
                                                <p class="bg-success">Message success</p>
                                                <p class="bg-info">Message info</p>
                                                <p class="bg-warning">Message warning</p>
                                                <p class="bg-danger">Message danger</p>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                            </div>
                            <!-- /.col-lg-8 -->
                            <div class="row">
                                <div class="col-xs-12 col-sm-6">
                                    <div class="box">
                                        <div class="box-header">
                                            <div class="box-name">
                                                <i class="fa fa-bar-chart-o"></i>
                                                <span>Column Chart</span>
                                            </div>

                                            <div class="no-move"></div>
                                        </div>
                                        <div class="box-content">
                                            <div id="google-chart-1" style="min-height: 200px;"></div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xs-12 col-sm-6">
                                    <div class="box">
                                        <div class="box-header">
                                            <div class="box-name">
                                                <i class="fa fa-circle"></i>
                                                <span>Pie Chart</span>
                                            </div>

                                            <div class="no-move"></div>
                                        </div>
                                        <div class="box-content">
                                            <div id="google-chart-2" style="min-height: 200px;"></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-12 col-sm-6">
                                    <div class="box">
                                        <div class="box-header">
                                            <div class="box-name">
                                                <i class="fa fa-ellipsis-v"></i>
                                                <span>Scatter Chart</span>
                                            </div>

                                            <div class="no-move"></div>
                                        </div>
                                        <div class="box-content">
                                            <div id="google-chart-3" style="min-height: 200px;"></div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xs-12 col-sm-6">
                                    <div class="box">
                                        <div class="box-header">
                                            <div class="box-name">
                                                <i class="fa fa-dot-circle-o"></i>
                                                <span>Bubble Chart</span>
                                            </div>

                                            <div class="no-move"></div>
                                        </div>
                                        <div class="box-content">
                                            <div id="google-chart-4" style="min-height: 200px;"></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-12">
                                    <div class="box">
                                        <div class="box-header">
                                            <div class="box-name">
                                                <i class="fa fa-map-marker"></i>
                                                <span>Geo Chart</span>
                                            </div>

                                            <div class="no-move"></div>
                                        </div>
                                        <div class="box-content">
                                            <div id="google-chart-5" style="min-height: 400px;"></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-12 col-sm-4">
                                    <div class="box">
                                        <div class="box-header">
                                            <div class="box-name">
                                                <i class="fa fa-asterisk"></i>
                                                <span>Line Chart</span>
                                            </div>

                                            <div class="no-move"></div>
                                        </div>
                                        <div class="box-content">
                                            <div id="google-chart-6" style="min-height: 200px;"></div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xs-12 col-sm-4">
                                    <div class="box">
                                        <div class="box-header">
                                            <div class="box-name">
                                                <i class="fa fa-bullseye"></i>
                                                <span>Donut Chart</span>
                                            </div>

                                            <div class="no-move"></div>
                                        </div>
                                        <div class="box-content">
                                            <div id="google-chart-7" style="min-height: 200px;"></div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xs-12 col-sm-4">
                                    <div class="box">
                                        <div class="box-header">
                                            <div class="box-name">
                                                <i class="fa fa-signal"></i>
                                                <span>Trendline Chart</span>
                                            </div>

                                            <div class="no-move"></div>
                                        </div>
                                        <div class="box-content">
                                            <div id="google-chart-8" style="min-height: 200px;"></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- /.row -->

                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box">
                                <div class="box-header">
                                    <div class="box-name">
                                        <i class="fa fa-usd"></i>
                                        <span>The World's billionaries</span>
                                    </div>

                                    <div class="no-move"></div>
                                </div>
                                <div class="box-content no-padding">
                                    <table class="table table-bordered table-striped table-hover table-heading table-datatable" id="datatable-1">
                                        <thead style="background-color: #CCCCCC">
                                            <tr>
                                                <th>Rank</th>
                                                <th>Name</th>
                                                <th>Net Worth</th>
                                                <th>Age</th>
                                                <th>Source</th>
                                                <th>Country of Citizenship</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <!-- Start: list_row -->

                                            <tr>
                                                <td>16</td>
                                                <td><img class="img-rounded" src="http://i.forbesimg.com/media/lists/people/alice-walton_50x50.jpg" alt="">Alice Walton</td>
                                                <td>$26.3 B</td>
                                                <td>64</td>
                                                <td>Wal-Mart</td>
                                                <td>United States</td>
                                            </tr>

                                            <tr>
                                                <td>94</td>
                                                <td><img class="img-rounded" src="http://i.forbesimg.com/media/lists/people/hans-rausing_50x50.jpg" alt="">Hans Rausing</td>
                                                <td>$11 B</td>
                                                <td>87</td>
                                                <td>packaging</td>
                                                <td>Sweden</td>
                                            </tr>
                                            <tr>
                                                <td>94</td>
                                                <td><img class="img-rounded" src="http://i.forbesimg.com/media/lists/people/jack-taylor_50x50.jpg" alt="">Jack Taylor & family</td>
                                                <td>$11 B</td>
                                                <td>91</td>
                                                <td>Enterprise Rent-A-Car</td>
                                                <td>United States</td>
                                            </tr>
                                            <tr>
                                                <td>98</td>
                                                <td><img class="img-rounded" src="http://i.forbesimg.com/media/lists/people/lui-che-woo_50x50.jpg" alt="">Lui Che Woo</td>
                                                <td>$10.7 B</td>
                                                <td>84</td>
                                                <td>gaming</td>
                                                <td>Hong Kong</td>
                                            </tr>
                                            <tr>
                                                <td>98</td>
                                                <td><img class="img-rounded" src="http://i.forbesimg.com/media/lists/people/laurene-powell-jobs_50x50.jpg" alt="">Laurene Powell Jobs & family</td>
                                                <td>$10.7 B</td>
                                                <td>50</td>
                                                <td>Apple, Disney</td>
                                                <td>United States</td>
                                            </tr>
                                            <tr>
                                                <td>100</td>
                                                <td><img class="img-rounded" src="http://i.forbesimg.com/media/lists/people/eike-batista_50x50.jpg" alt="">Eike Batista</td>
                                                <td>$10.6 B</td>
                                                <td>57</td>
                                                <td>mining, oil</td>
                                                <td>Brazil</td>
                                            </tr>
                                            <!-- End: list_row -->
                                        </tbody>
                                        <tfoot>
                                            <tr>
                                                <th>Rank</th>
                                                <th>Name</th>
                                                <th>Net Worth</th>
                                                <th>Age</th>
                                                <th>Source</th>
                                                <th>Country of Citizenship</th>
                                            </tr>
                                        </tfoot>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box">
                                <div class="box-header">
                                    <div class="box-name">
                                        <i class="fa fa-linux"></i>
                                        <span>LinuxJournal Readers' Choice Awards 2013 Linux distro</span>
                                    </div>

                                    <div class="no-move"></div>
                                </div>
                                <div class="box-content no-padding table-responsive">
                                    <table class="table table-bordered table-striped table-hover table-heading table-datatable" id="datatable-2">
                                        <thead>
                                            <tr>
                                                <th><label><input type="text" name="search_rate" value="Search rate" class="search_init" /></label></th>
                                                <th><label><input type="text" name="search_name" value="Search distro" class="search_init" /></label></th>
                                                <th><label><input type="text" name="search_votes" value="Search votes" class="search_init" /></label></th>
                                                <th><label><input type="text" name="search_homepage" value="Search homepage" class="search_init" /></label></th>
                                                <th><label><input type="text" name="search_version" value="Search versions" class="search_init" /></label></th>
                                            </tr>
                                        </thead>
                                        <tbody>

                                            <tr>
                                                <td>17</td>
                                                <td>Red Hat Enterprise Linux</td>
                                                <td>1.6%</td>
                                                <td><i class="fa fa-home"></i><a href="http://redhat.com" target="_blank">http://redhat.com</a></td>
                                                <td>7</td>
                                            </tr>
                                            <tr>
                                                <td>18</td>
                                                <td>Ubuntu Server</td>
                                                <td>1.6%</td>
                                                <td><i class="fa fa-home"></i><a href="http://ubuntu.com" target="_blank">http://ubuntu.com</a></td>
                                                <td>13.10</td>
                                            </tr>
                                            <tr>
                                                <td>19</td>
                                                <td>CrunchBang</td>
                                                <td>1.1%</td>
                                                <td><i class="fa fa-home"></i><a href="http://crunchbang.org" target="_blank">http://crunchbang.org</a></td>
                                                <td>11</td>
                                            </tr>



                                        </tbody>
                                        <tfoot>
                                            <tr>
                                                <th>Rate</th>
                                                <th>Distro</th>
                                                <th>Votes</th>
                                                <th>Homepage</th>
                                                <th>Version</th>
                                            </tr>
                                        </tfoot>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box">
                                <div class="box-header">
                                    <div class="box-name">
                                        <i class="fa fa-linux"></i>
                                        <span>Most popular Linux distro</span>
                                    </div>

                                    <div class="no-move"></div>
                                </div>
                                <div class="box-content no-padding">
                                    <table class="table table-bordered table-striped table-hover table-heading table-datatable" id="datatable-3">
                                        <thead>
                                            <tr>
                                                <th>Rate</th>
                                                <th>Distro</th>
                                                <th>Votes</th>
                                                <th>Homepage</th>
                                                <th>Version</th>
                                                <th>Icon</th>
                                            </tr>
                                        </thead>
                                        <tbody>

                                            <tr>
                                                <td>26</td>
                                                <td>Mandriva</td>
                                                <td>0.1%</td>
                                                <td><i class="fa fa-home"></i><a href="http://openmandriva.org" target="_blank">http://openmandriva.org</a></td>
                                                <td>2013</td>
                                                <td><i class="fa fa-linux"></i></td>
                                            </tr>
                                            <tr>
                                                <td>27</td>
                                                <td>Oracle Linux</td>
                                                <td>0.1%</td>
                                                <td><i class="fa fa-home"></i><a href="http://oracle.com" target="_blank">http://oracle.com</a></td>
                                                <td>3</td>
                                                <td><i class="fa fa-linux"></i></td>
                                            </tr>
                                            <tr>
                                                <td>28</td>
                                                <td>SolusOS</td>
                                                <td>0.1%</td>
                                                <td>abandoned</td>
                                                <td>1.3</td>
                                                <td><i class="fa fa-linux"></i></td>
                                            </tr>
                                            <tr>
                                                <td>29</td>
                                                <td>Zorin OS</td>
                                                <td>0.1%</td>
                                                <td><i class="fa fa-home"></i><a href="http://zorin-os.com" target="_blank">http://zorin-os.com</a></td>
                                                <td>8</td>
                                                <td><i class="fa fa-linux"></i></td>
                                            </tr>
                                        </tbody>
                                        <tfoot>
                                            <tr>
                                                <th>Rate</th>
                                                <th>Distro</th>
                                                <th>Votes</th>
                                                <th>Homepage</th>
                                                <th>Version</th>
                                                <th>Icon</th>
                                            </tr>
                                        </tfoot>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box">
                                <div class="box-header">
                                    <div class="box-name">
                                        <i class="fa fa-table"></i>
                                        <span>Beauty Tables (Inputs in cell+Keyboard navigation)</span>
                                    </div>

                                    <div class="no-move"></div>
                                </div>
                                <div class="box-content">
                                    <h4>This is table with hidden inputs in cell's and keyboard navigation in table, see below</h4>
                                    <ol>
                                        <li><kbd>SHIFT</kbd>+<kbd>arrow key</kbd>-Prev(Next) cell at row/col</li>
                                        <li><kbd>CTRL</kbd>+<kbd>arrow key</kbd>-First(End) cell at row/col</li>
                                        <li><kbd>PgUp/PgDown</kbd>-First(End) cell in table</li>
                                        <li><kbd>Enter/Tab</kbd>-Next cell in table</li>
                                        <li>Press link Table to JSON in box header and see result</li>
                                    </ol>
                                    <p>For basic styling add the base class <code>.beauty-table</code> to <code>&lt;table&gt;</code>.
                                    </p>
                                    <table id="tex" class="table beauty-table table-hover">
                                        <thead>
                                            <tr>
                                                <th>&nbsp;</th>
                                                <th>Rank</th>
                                                <th>Mobile OS</th>
                                                <th>Home page</th>
                                                <th>First Release</th>
                                                <th>Last versions</th>
                                                <th>Base kernel</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td>Android</td>
                                                <td><input type="text" value="1"></td>
                                                <td><input type="text" value="46.6%"></td>
                                                <td><input type="text" value="http://android.com"></td>
                                                <td><input type="text" value="23/09/2008"></td>
                                                <td><input type="text" value="4.2.2"></td>
                                                <td><input type="text" value="linux"></td>
                                            </tr>
                                            <tr>
                                                <td>Sailfish OS</td>
                                                <td><input type="text" value="2"></td>
                                                <td><input type="text" value="17.5%"></td>
                                                <td><input type="text" value="https://sailfishos.org/"></td>
                                                <td><input type="text" value="development"></td>
                                                <td><input type="text" value="alpha"></td>
                                                <td><input type="text" value="linux"></td>
                                            </tr>
                                            <tr>
                                                <td>CyanogenMod</td>
                                                <td><input type="text" value="3"></td>
                                                <td><input type="text" value="14.2%"></td>
                                                <td><input type="text" value="www.cyanogenmod.org"></td>
                                                <td><input type="text" value="11/07/2010"></td>
                                                <td><input type="text" value="10.2.1"></td>
                                                <td><input type="text" value="linux"></td>
                                            </tr>
                                            <tr>
                                                <td>FirefoxOS</td>
                                                <td><input type="text" value="4"></td>
                                                <td><input type="text" value="6.3% "></td>
                                                <td><input type="text" value="http://firefoxos.com"></td>
                                                <td><input type="text" value="02/07/2013"></td>
                                                <td><input type="text" value="1.4beta"></td>
                                                <td><input type="text" value="linux"></td>
                                            </tr>
                                            <tr>
                                                <td>MeeGo</td>
                                                <td><input type="text" value="5"></td>
                                                <td><input type="text" value="2.6%"></td>
                                                <td><input type="text" value="https://meego.com"></td>
                                                <td><input type="text" value="26/05/2010"></td>
                                                <td><input type="text" value="1.2.0.9"></td>
                                                <td><input type="text" value="linux"></td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box">
                                <div class="box-header">
                                    <div class="box-name">
                                        <i class="fa fa-table"></i>
                                        <span>Beauty Tables (Inputs in cell+Keyboard navigation)+String Fill</span>
                                    </div>

                                    <div class="no-move"></div>
                                </div>
                                <div class="box-content">
                                    <h4>Another table with hidden inputs in cell's and keyboard navigation in table, see below</h4>
                                    <ol>
                                        <li><kbd>SHIFT</kbd>+<kbd>arrow key</kbd>-Prev(Next) cell at row/col</li>
                                        <li><kbd>CTRL</kbd>+<kbd>arrow key</kbd>-First(End) cell at row/col</li>
                                        <li><kbd>PgUp/PgDown</kbd>-First(End) cell in table</li>
                                        <li><kbd>Enter/Tab</kbd>-Next cell in table</li>
                                        <li>check <code>String Fill</code> checkbox and after entering in cell- next cell filled same value</li>
                                        <li>Press link Table to JSON in box header and see result</li>
                                    </ol>
                                    <p>For basic styling add the base class <code>.beauty-table</code> to <code>&lt;table&gt;</code>.
                                    </p>
                                    <table class="table beauty-table table-hover">
                                        <thead>
                                            <tr>
                                                <th><input type="checkbox" name="string-fill">-string fill</th>
                                                <th>Rank</th>
                                                <th>Mobile OS</th>
                                                <th>Home page</th>
                                                <th>First Release</th>
                                                <th>Last version</th>
                                                <th>Base kernel</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td>Android</td>
                                                <td><input type="text" value="1"></td>
                                                <td><input type="text" value="46.6%"></td>
                                                <td><input type="text" value="http://android.com"></td>
                                                <td><input type="text" value="23/09/2008"></td>
                                                <td><input type="text" value="4.2.2"></td>
                                                <td><input type="text" value="linux"></td>
                                            </tr>
                                            <tr>
                                                <td>Sailfish OS</td>
                                                <td><input type="text" value="2"></td>
                                                <td><input type="text" value="17.5%"></td>
                                                <td><input type="text" value="https://sailfishos.org/"></td>
                                                <td><input type="text" value="development"></td>
                                                <td><input type="text" value="alpha"></td>
                                                <td><input type="text" value="linux"></td>
                                            </tr>
                                            <tr>
                                                <td>CyanogenMod</td>
                                                <td><input type="text" value="3"></td>
                                                <td><input type="text" value="14.2%"></td>
                                                <td><input type="text" value="www.cyanogenmod.org"></td>
                                                <td><input type="text" value="11/07/2010"></td>
                                                <td><input type="text" value="10.2.1"></td>
                                                <td><input type="text" value="linux"></td>
                                            </tr>
                                            <tr>
                                                <td>FirefoxOS</td>
                                                <td><input type="text" value="4"></td>
                                                <td><input type="text" value="6.3% "></td>
                                                <td><input type="text" value="http://firefoxos.com"></td>
                                                <td><input type="text" value="02/07/2013"></td>
                                                <td><input type="text" value="1.4beta"></td>
                                                <td><input type="text" value="linux"></td>
                                            </tr>
                                            <tr>
                                                <td>MeeGo</td>
                                                <td><input type="text" value="5"></td>
                                                <td><input type="text" value="2.6%"></td>
                                                <td><input type="text" value="https://meego.com"></td>
                                                <td><input type="text" value="26/05/2010"></td>
                                                <td><input type="text" value="1.2.0.9"></td>
                                                <td><input type="text" value="linux"></td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            </div>
        </div>
        <!-- /#page-wrapper -->

        <script type="text/javascript">
            // Run Datables plugin and create 3 variants of settings
            function AllTables() {
                StartDataTable('datatable-1');
                TestTable2();
                TestTable3();
                LoadSelect2Script(MakeSelectDataTable);
            }

            function DemoSelect2() {
                $('#s2_with_tag').select2({placeholder: "Select OS"});
                $('#s2_country').select2();
            }
// Run timepicker
            function DemoTimePicker() {
                $('#input_time').timepicker({setDate: new Date()});
            }

            $(document).ready(function () {
                // Load Google Chart API and callback to draw test graphs
                $.getScript('http://www.google.com/jsapi?autoload={%22modules%22%3A[{%22name%22%3A%22visualization%22%2C%22version%22%3A%221%22%2C%22packages%22%3A[%22corechart%22%2C%22geochart%22]%2C%22callback%22%3A%22DrawAllCharts%22}]}');
                // This need for correct resize charts, when box open or drag
                var graphxChartsResize;
                $(".box").resize(function (event) {
                    event.preventDefault();
                    clearTimeout(graphxChartsResize);
                    graphxChartsResize = setTimeout(DrawAllCharts, 500);
                });
                $('.beauty-table').each(function () {
                    // Run keyboard navigation in table
                    $(this).beautyTables();
                    // Nice CSS-hover row and col for current cell
                    $(this).beautyHover();
                });

                LoadDataTablesScripts(AllTables);
                TinyMCEStart('#wysiwig_simple', null);
                TinyMCEStart('#wysiwig_full', 'extreme');
                // Add slider for change test input length
                FormLayoutExampleInputLength($(".slider-style"));
                // Initialize datepicker
                $('#input_date').datepicker({setDate: new Date()});
                $('#dateIni').datepicker({
                    setDate: new Date(),
                    dateFormat: "dd/mm/yy",
                    dayNames: ['Domingo', 'Segunda', 'Terça', 'Quarta', 'Quinta', 'Sexta', 'Sábado'],
                    dayNamesMin: ['D', 'S', 'T', 'Q', 'Q', 'S', 'S', 'D'],
                    dayNamesShort: ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sáb', 'Dom'],
                    monthNames: ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'],
                    monthNamesShort: ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'],
                    nextText: 'Próximo',
                    prevText: 'Anterior'

                });

                // Load Timepicker plugin
                LoadTimePickerScript(DemoTimePicker);
                // Add tooltip to form-controls
                $('.form-control').tooltip();
                LoadSelect2Script(DemoSelect2);
                // Load example of form validation
                LoadBootstrapValidatorScript(DemoFormValidator);
                // Add Drag-n-Drop action for .box
                WinMove();
            });
        </script>
    </body>

</html>
<%}%>                                

