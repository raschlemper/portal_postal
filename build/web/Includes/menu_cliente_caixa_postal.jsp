
<%@page import="Entidade.Usuario"%>
<%@page import="Entidade.Clientes"%>
<%@page import="java.util.ArrayList"%>
<%
    String usMenu = (String) session.getAttribute("nomeUser");
    String senhaMenu = (String) session.getAttribute("senhaUser");
    String empMenu = (String) session.getAttribute("nomeEmpresa");
    int idAgM = (Integer) session.getAttribute("idEmpresa");
    int nvMenu = (Integer) session.getAttribute("nivelUsuarioEmp");
    ArrayList<Integer> acessosMn = (ArrayList<Integer>) session.getAttribute("acessos");
    Clientes cliMenu = (Clientes) session.getAttribute("cliente");

    ArrayList<Usuario> lsNomesBd = (ArrayList<Usuario>) session.getAttribute("userMaster");
%>
<script>
    function getval(sel) {
        var aux = sel.value.split(';');
        $('#agenciaHoito').val(aux[0]);
        $('#senhaHoito').val(aux[1]);
        $('#logar').submit();
    }
</script>

