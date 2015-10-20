

/****************************************************************/


/* Troca o conteudo da div do contato pelo form para adicionar novo contato. */
/* E Salva o conteudo antigo da div no input 'conteudoDivContato' */
function adicionarContato(){
    var conteudoDiv = document.getElementById("liContato").innerHTML;
    //salva o contato
    document.getElementById("conteudoLiContato").value = conteudoDiv;
    var novoConteudoDiv =
    "<dd><label>Nome <a onClick='voltarContato();'><img src='../../imagensNew/back.png' />Retornar</a></label><input style='width:200px;' type='text' id='contato' name='contato' text-align:left;'/></dd>"+
    "<dd><label>Telefone</label><input style='width:180px;' type='text' id='telefone' name='telefone' maxlength='14' onkeypress='mascara(this,telefoneRamal)'/></dd>"+
    "<dd><label>E-mail</label><input style='width:200px;' type='text' id='email' name='email' /></dd>"+
    "<dd><label>Setor</label><input style='width:180px;' type='text' id='setor' name='setor' /></dd>";
    document.getElementById("liContato").innerHTML = novoConteudoDiv;
}

/* Volta o conteudo antigo da div do contato , ou seja, o form para selecionar um contato */
function voltarContato(){
    document.getElementById("liContato").innerHTML = document.getElementById("conteudoLiContato").value
}


/****************************************************************/

