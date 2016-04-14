<%@ include file="../includes/telaMsg.jsp" %>
<nav style="z-index: 2000;" class="navbar navbar-default navbar-fixed-top">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header fixed-brand">
        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"  id="menu-toggle" onclick="mtoggle()">
            <span class="glyphicon glyphicon-th-large" aria-hidden="true"></span>
        </button>
        <a class="" href="#"><img src="${pageContext.request.contextPath}/imagensNew/logoNovaH.png" height="40" alt=""/></a>

    </div><!-- navbar-header-->

    <div class="collapse navbar-collapse pull-right" id="bs-example-navbar-collapse-1" style="margin-top: 5px;">
        <ul id="navbar-group" class="nav navbar-nav">
            <li class="active">
                <button class="navbar-toggle collapse in" data-toggle="collapse" id="menu-toggle-2" onclick="mtoggle2()"><i class="fa fa-lg fa-bars" aria-hidden="true"></i></button>
            </li>
        </ul>
    </div><!-- bs-example-navbar-collapse-1 -->
</nav>