<%
            //MENSAGEM
            String msg = (String) session.getAttribute("msg");
            String msg2 = "";
            if (request.getParameter("msg") != null) {
                msg2 = request.getParameter("msg").replaceAll(";", "<br>");
            } else if (msg != null) {
                msg2 = msg.replaceAll(";", "<br>");
                session.setAttribute("msg", null);
            }
            
%>
<script type="text/javascript">
    function telaMsg(){
       <%if (!msg2.equals("")) {%>
           bootbox.dialog({
                title: "Mesagem do Sistema!",
                message: "<%= msg2.replace("\"", "'") %>",
                animate: true,
                onEscape: true,
                buttons: {
                    "Cancelar": {
                        label:"<i class='fa fa-lg fa-spc fa-times'></i>FECHAR",
                        className: "btn btn-default",
                        callback: function() {
                            bootbox.hideAll();
                        }
                    }
                }
            });
        <%}%>
        return false;
    }
</script>