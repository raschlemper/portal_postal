

window.alert=function(menssagem){
    var msg = menssagem.replace(/\n/g, "<br />");
    bootbox.dialog({    
        title: "<b class='text-danger'>ATEN\u00c7\u00c3O!</b>",
        message: "<label>"+msg+"</label>",
        onEscape: true,
        buttons: {
            close: {
              label: "<i class='fa fa-lg fa-times fa-spc'></i> FECHAR",
              className: "btn btn-default",
              callback: function() {
                bootbox.hideAll();
              }
            }
        }
    });
    //document.getElementById('tdMsg').innerHTML = messagem;
    //document.getElementById('divEsperaMsg').className = 'mostrar';
}


/*************************************************************************/
/************************* PARTE DAS MASCARAS ****************************/
/*************************************************************************/

//CHAMADA DA FUNCAO - mascara(this,*TIPO);
//*TIPO = tipo da mascara, ex.: mascara(this,telefone); , mascara(this,numero);

function mascara(o,f){
    v_obj=o
    v_fun=f
    setTimeout("execmascara()",1)
}
 
function execmascara(){
    v_obj.value = v_fun(v_obj.value);
}

/******** Tipos de Mascaras *********/
//OBS.: O nome do tipo da mascara não pode ter o mesmo nome e id do campo(input), padrao: "maskNomeDoTipo"

function maskNumero(v){
    v=v.replace(/\D/g,"")                 //Remove tudo o que nao e digito
    return v
}

function maskNumeroRua(v){
    v=v.replace(/[^\.\-\/0-9A-Za-z]/g,"")                 //Remove tudo o que nao e digito
    return v.toUpperCase();
}

function maskLetras(e){
    if (document.all){
        var evt = event.keyCode;
    }else{
        var evt = e.charCode;
    }
    var chr = String.fromCharCode(evt);
    var re = /[A-Za-z]/; // permite apenas de A-Z e de a-z
    return (re.test(chr) || evt<20); // com evt<20 permitimos <ENTER>,<TAB>,<BACKSPACE>
}

function maskReal(v){
    v=v.replace(/\D/g,"")                 //Remove tudo o que nao e digito
    v=v.replace(/(\d+)(\d{2})/,"$1.$2")    //Coloca hifen entre o quarto e o quinto digitos
    return v
}

function maskKilo(v){
    v=v.replace(/\D/g,"")                 //Remove tudo o que nao e digito
    v=v.replace(/(\d+)(\d{3})/,"$1.$2")    //Coloca hifen entre o quarto e o quinto digitos
    return v
}


function maskTelefone(v){
    if(v.length < 15){
        v=v.replace(/\D/g,"")                 //Remove tudo o que nao e digito
        v=v.replace(/^(\d\d)(\d)/g,"($1) $2") //Coloca parenteses em volta dos dois primeiros digitos
        v=v.replace(/(\d{4})(\d)/,"$1-$2")    //Coloca hifen entre o quarto e o quinto digitos        
    }else{
        v=v.replace(/\D/g,"")                 //Remove tudo o que nao e digito
        v=v.replace(/^(\d\d)(\d)/g,"($1) $2") //Coloca parenteses em volta dos dois primeiros digitos
        v=v.replace(/(\d{5})(\d)/,"$1-$2")    //Coloca hifen entre o quarto e o quinto digitos
    }
    v=v.substring(0, 15);
    return v
}

function maskObjetoSRO(v){
    v=v.toUpperCase()             //Maiúsculas
    v=v.replace(/\W/g,"") //Remove tudo o que não alphanumerico
    if(v.length<=2){
        v=v.replace(/\d/g,"")
    }
    if(v.length>2 && v.length<=11){
        var aux = v.substring(2, 11);
        aux = aux.replace(/\D/g,"")
        v=v.substring(0, 2)+aux;
    }
    if(v.length>11){
        var aux2 = v.substring(11, 13);
        aux2 = aux2.replace(/\d/g,"")
        v=v.substring(0, 11)+aux2;
    }
    return v
}

function maskTelefoneRamal(v){
    v=v.replace(/\D/g,"")                 //Remove tudo o que nao e digito
    v=v.replace(/^(\d\d)(\d)/g,"($1) $2") //Coloca parenteses em volta dos dois primeiros digitos
    v=v.replace(/(\d{4})(\d)/,"$1-$2")    //Coloca hifen entre o quarto e o quinto digitos
    v=v.replace(/(\d{4})(\d)/,"$1 / $2")  //Coloca hifen entre o quarto e o quinto digitos
    return v
}
 
function maskData(v){
    v=v.replace(/\D/g,"")                 //Remove tudo o que nao e digito
    v=v.replace(/(\d{2})(\d)/,"$1/$2")    //Coloca um ponto entre o terceiro e o quarto digitos
    v=v.replace(/(\d{2})(\d)/,"$1/$2")    //Coloca um ponto entre o terceiro e o quarto digitos
    return v
}

function maskCnpj(v){
    v=v.replace(/\D/g,"")                 //Remove tudo o que nao e digito
    v=v.replace(/(\d{2})(\d)/,"$1.$2")    //Coloca hifen entre o quarto e o quinto digitos
    v=v.replace(/(\d{3})(\d)/,"$1.$2")    //Coloca hifen entre o quarto e o quinto digitos
    v=v.replace(/(\d{3})(\d)/,"$1/$2")    //Coloca hifen entre o quarto e o quinto digitos
    v=v.replace(/(\d{4})(\d)/,"$1-$2")    //Coloca hifen entre o quarto e o quinto digitos
    return v
}

function maskCpfCnpj(v){
    v=v.replace(/\D/g,"")                 //Remove tudo o que nao e digito
    if(v.length <= 11){
        v=v.replace(/(\d{3})(\d)/,"$1.$2")    //Coloca hifen entre o quarto e o quinto digitos
        v=v.replace(/(\d{3})(\d)/,"$1.$2")    //Coloca hifen entre o quarto e o quinto digitos
        v=v.replace(/(\d{3})(\d)/,"$1-$2")    //Coloca hifen entre o quarto e o quinto digitos
    }else{
        v=v.replace(/(\d{2})(\d)/,"$1.$2")    //Coloca hifen entre o quarto e o quinto digitos
        v=v.replace(/(\d{3})(\d)/,"$1.$2")    //Coloca hifen entre o quarto e o quinto digitos
        v=v.replace(/(\d{3})(\d)/,"$1/$2")    //Coloca hifen entre o quarto e o quinto digitos
        v=v.replace(/(\d{4})(\d)/,"$1-$2")    //Coloca hifen entre o quarto e o quinto digitos
    }
    return v
}

function maskCep(v){
    v=v.replace(/\D/g,"")                 //Remove tudo o que nao e digito
    v=v.replace(/(\d{5})(\d)/,"$1-$2")    //Coloca hifen entre o quinto e o sexto digitos
    return v
}

function maskHora(v){ //ARUMAR MASCARA
    v=v.replace(/\D/g,"")                 //Remove tudo o que nao e digito
    v=v.replace(/(\d{2})(\d)/,"$1:$2")    //Coloca dois pontos entre o segundo e o terceiro digitos
    return v
}

function maskDatahora(v){ //ARUMAR MASCARA
    v=v.replace(/\D/g,"")                 //Remove tudo o que nao e digito
    v=v.replace(/(\d{2})(\d)/,"$1/$2")    //Coloca barra ponto entre o terceiro e o quarto digitos
    v=v.replace(/(\d{2})(\d)/,"$1/$2")    //Coloca barra entre o terceiro e o quarto digitos
    v=v.replace(/(\d{4})(\d)/,"$1 $2")    //Coloca espaço entre o quarto e o quinto digitos
    v=v.replace(/( \d{2})(\d)/,"$1:$2")    //Coloca dois pontos entre o segundo e o terceiro digitos
    return v
}


/*************************************************************************/
/************************ PARTE DAS VALIDACOES ***************************/
/*************************************************************************/

function CheckAll(nomeCheck, checado) {  //JS_PORTAL_POSTAL
    $("[name='"+nomeCheck+"']").each(function() {
        if(checado == true){
            this.checked = checado;
        }else{
            this.checked = checado;            
        }
    });
}

function valida_SRO(numero){

    var soma = 0;
    soma = parseInt(soma) + (parseInt(numero.substring(0, 1)) * 8);
    soma = parseInt(soma) + (parseInt(numero.substring(1, 2)) * 6);
    soma = parseInt(soma) + (parseInt(numero.substring(2, 3)) * 4);
    soma = parseInt(soma) + (parseInt(numero.substring(3, 4)) * 2);
    soma = parseInt(soma) + (parseInt(numero.substring(4, 5)) * 3);
    soma = parseInt(soma) + (parseInt(numero.substring(5, 6)) * 5);
    soma = parseInt(soma) + (parseInt(numero.substring(6, 7)) * 9);
    soma = parseInt(soma) + (parseInt(numero.substring(7, 8)) * 7);

    var dig = 11 - (parseInt(soma) % 11);
    if (dig == 11) {
        dig = 5;
    }
    if (dig == 10) {
        dig = 0;
    }

    if(dig == numero.substring(8, 9)){
        return 'ok';
    }else{
        //alert("Faixa de etiqueta invalida!");
        return dig;
    }

}


//valida data do tipo 'dd/mm/yyyy'
function valida_data(vData) {

    var data = vData.value
    var expReg = /^(([0-2]\d|[3][0-1])\/([0]\d|[1][0-2])\/[1-2][0-9]\d{2})$/;
    var msgErro = 'Formato invalido de data.';

    if ((data.match(expReg)) && (data!='')){
        var dia = data.substring(0,2);
        var mes = data.substring(3,5);
        var ano = data.substring(6,10);
        if(dia == "00" || mes == "00" || ano == "0000"){
            alert("Data invalida dia, mes ou ano não podem ser menor ou igual a 0!");
            return false;
        }
        if((mes==4 || mes==6 || mes==9 || mes==11) && dia > 30){
            alert("Dia incorreto !!! O mes especificado contem no maximo 30 dias.");
            return false;
        } else{
            if(ano%4!=0 && mes==2 && dia>28){
                alert("Data incorreta!! O mes especificado contem no maximo 28 dias.");
                return false;
            } else{
                if(ano%4==0 && mes==2 && dia>29){
                    alert("Data incorreta!! O mes especificado contem no maximo 29 dias.");
                    return false;
                }
            }
        }
    } else {
        if ((data!='')){
            alert(msgErro);
            return false;
        }
    }
    return true;
} // chamada da função --> return mascara_telefone(this);

//valida horario do tipo 'hh:mm'
function valida_hora(horario) {
    var hora = horario.value;
    if(hora.toString().length<5){
        alert("Hora invalida!");
        hora.focus();
        return false;
    }
    var hrs = (hora.substring(0,2));
    var min = (hora.substring(3,5));
    var ponto = hora.split(':');
    var estado = "";
    if (ponto.lenght < 2){
        estado = "errada";
    }
    if ((hrs < 00 ) || (hrs > 23) || ( min < 00) ||( min > 59)){
        estado = "errada";
    }
    if (estado === "errada") {
        alert("Hora invalida!");
        hora.focus();
        return false;
    }
    return true;
} // chamada da função --> return valida_hora(this);

//function que valida um datetime do tipo 'dd/mm/yyyy hh:mm'
function validaDateTime(datetime){  // chamada da função --> return validaDateTime(this.value);
    if(datetime.length<16){
        alert("Preencha corretamente o campo da Data da Coleta");
        return false;
    }else if(datetime.length>16){
        datetime = datetime.substring(0, 16);
    }
    
    var aux = datetime.split(" ");
    var data = aux[0];
    var hora = aux[1];

    var expReg = /^(([0-2]\d|[3][0-1])\/([0]\d|[1][0-2])\/[1-2][0-9]\d{2})$/;
    var msgErro = 'Formato invalido de data.';

    if ((data.match(expReg)) && (data!='')){
        var dia = data.substring(0,2);
        var mes = data.substring(3,5);
        var ano = data.substring(6,10);
        if((mes==4 || mes==6 || mes==9 || mes==11) && dia > 30){
            alert("Dia incorreto !!! O mes especificado contem no maximo 30 dias.");
            return false;
        } else{
            if(ano%4!=0 && mes==2 && dia>28){
                alert("Data incorreta!! O mes especificado contem no maximo 28 dias.");
                return false;
            } else{
                if(ano%4==0 && mes==2 && dia>29){
                    alert("Data incorreta!! O mes especificado contem no maximo 29 dias.");
                    return false;
                }
            }
        }
    } else {
        if ((data!='')){
            alert(msgErro);
            return false;
        }
    }

    hrs = (hora.substring(0,2));
    min = (hora.substring(3,5));
    ponto = hora.split(':');
    estado = "";
    if (ponto.lenght < 2){
        estado = "errada";
    }
    if ((hrs < 00 ) || (hrs > 23) || ( min < 00) ||( min > 59)){
        estado = "errada";
    }
    if (hora == "") {
        estado = "errada";
    }
    if (estado == "errada") {
        alert("Hora invalida!");
        return false;
    }

    return true;
}

//compara se os horarios do tipo 'hh:mm' se a hora1 é menor que a hora2
function valida_hora_inicio_fim(horario1, horario2) {
    var hora1 = horario1.value;
    var hrs1 = parseInt(hora1.substring(0,2));
    var min1 = parseInt(hora1.substring(3,5));
    
    var hora2 = horario2.value;
    var hrs2 = parseInt(hora2.substring(0,2));
    var min2 = parseInt(hora2.substring(3,5));

    if(hrs1 > hrs2){
        alert("O Horario de termino da tarefa deve ser maior que o horario de inicio!");
        return false;
    }
    if((hrs1 == hrs2) && (min1 >= min2)){
        alert("O Horario de termino da tarefa deve ser maior que o horario de inicio!");
        return false;
    }

    return true;
} // chamada da função --> return valida_hora(this);


function isCPFCNPJ(campo){
    if( campo == "" ){
        return false;
    }

    var campo_filtrado = "", valor_1 = " ", valor_2 = " ", ch = "";
    var valido = false;
        
    for (i = 0; i < campo.length; i++){
        ch = campo.substring(i, i + 1);
        if (ch >= "0" && ch <= "9"){
            campo_filtrado = campo_filtrado.toString() + ch.toString()
            valor_1 = valor_2;
            valor_2 = ch;
        }
        if ((valor_1 != " ") && (!valido)) valido = !(valor_1 == valor_2);
    }
    if (!valido) campo_filtrado = "12345678912";

    if (campo_filtrado.length < 11){
        for (i = 1; i <= (11 - campo_filtrado.length); i++){
            campo_filtrado = "0" + campo_filtrado;
        }
    }
    
    if(campo_filtrado.length == 11){
        if ( ( campo_filtrado.substring(9,11) == checkCPF( campo_filtrado.substring(0,9) ) ) && ( campo_filtrado.substring(11,12)=="") ){
            return true;
        }else{
            alert("Preencha um CPF valido!");
        }
    } else if (campo_filtrado.length == 14){
        if ( campo_filtrado.substring(12,14) == checkCNPJ( campo_filtrado.substring(0,12) ) ){
            return true;
        }else{
            alert("Preencha um CNPJ valido!")
        }
    } else {
        alert("Preencha um CNPJ ou CPF valido!")        
    }
	
    return false;
}

function checkCNPJ(vCNPJ){
    var mControle = "";
    var aTabCNPJ = new Array(5,4,3,2,9,8,7,6,5,4,3,2);
    for (i = 1 ; i <= 2 ; i++){
        mSoma = 0;
        for (j = 0 ; j < vCNPJ.length ; j++)
            mSoma = mSoma + (vCNPJ.substring(j,j+1) * aTabCNPJ[j]);
        if (i == 2 ) mSoma = mSoma + ( 2 * mDigito );
        mDigito = ( mSoma * 10 ) % 11;
        if (mDigito == 10 ) mDigito = 0;
        mControle1 = mControle ;
        mControle = mDigito;
        aTabCNPJ = new Array(6,5,4,3,2,9,8,7,6,5,4,3);
    }
    return( (mControle1 * 10) + mControle );
}

function checkCPF(vCPF){
    var mControle = ""
    var mContIni = 2, mContFim = 10, mDigito = 0;
    for (j = 1 ; j <= 2 ; j++){
        mSoma = 0;
        for (i = mContIni ; i <= mContFim ; i++)
            mSoma = mSoma + (vCPF.substring((i-j-1),(i-j)) * (mContFim + 1 + j - i));
        if (j == 2 ) mSoma = mSoma + ( 2 * mDigito );
        mDigito = ( mSoma * 10 ) % 11;
        if (mDigito == 10) mDigito = 0;
        mControle1 = mControle;
        mControle = mDigito;
        mContIni = 3;
        mContFim = 11;
    }
    return( (mControle1 * 10) + mControle );
}