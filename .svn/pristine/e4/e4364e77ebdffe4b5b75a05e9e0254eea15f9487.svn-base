var loadings = false;
var resposta;
var historico;
var Aja;
function AjaxRequest() {
	var B = false;
	if (window.XMLHttpRequest) {
		B = new XMLHttpRequest()
	} else {
		if (window.ActiveXObject) {
			try {
				B = new ActiveXObject("Msxml2.XMLHTTP")
			} catch(A) {
				try {
					B = new ActiveXObject("Microsoft.XMLHTTP")
				} catch(A) {
				}
			}
		}
	}
	return B
}

function JxResult(Ajax, div) {
	if (Ajax.readyState <= 3) {
		try {
                    if(loadings!=true){
			document.getElementById("carregando").innerHTML = '<img height="30" src="img/load.gif">';
                    }
		} catch(e) {
		}
	}
	if (Ajax.readyState == 4) {
		if (Ajax.status == 200) {
			var html = delimitador(Ajax.responseText);
			historico = html;
			try {
				document.getElementById("carregando").innerHTML = ""
			} catch(e) {
			}
			try {
				if (html.length > 5) {
					document.getElementById(div).innerHTML = html
				}
			} catch(e) {
			}
			eval(Alljs)
		} else {
			document.getElementById(div).innerHTML = ["ERRO"]
		}
	}
}

var Alljs = "";
function delimitador(F) {
	Alljs = "try{";
	while (F.indexOf("%js") != -1) {
		var E = F.length;
		var D = F.indexOf("%js");
		var A = F.indexOf("js%");
		var C = "";
		if (D >= 0) {
			for (var B = (D + 3); B < A; B++) {
				C = C + F.charAt(B)
			}
			Alljs = Alljs + C
		}
		F = F.replace(C, "");
		F = F.replace("%js", "");
		F = F.replace("js%", "")
	}
	Alljs = Alljs + "}catch(e){alert(e);}";
	return F;
}

function JxPost(E, A, B, D, C) {
	if (E == "divaux") {
		document.getElementById(E).style.display = "block"
	}
	var F = AjaxRequest(E);
	if (!F) {
		document.write("ERRO002");
		return;
	} else {
		F.onreadystatechange = function() {
			A(F, E);
		};

		F.open("POST", B, true);
		F.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		F.send(D);
	}
}

function JxGet(E, F, B, C, D) {

	var check = F.substr((F.length - 3), 3);
	if (check == "php" || check == "tml") {
		F = F + "?uids=" + Math.ceil(Math.random() * 999999999999);
	} else {
		F = F + "&uids=" + Math.ceil(Math.random() * 999999999999);
	}
	if (D != false) {
		D = true;
	}
	if (E == "divaux") {
		document.getElementById(E).style.display = "block"
	}
	if (B == "") {
		B = JxResult;
	}
	var A = AjaxRequest(E);
	Aja = A;
	if (!A) {
		document.write("ERRO002");
		return;
	}
	loadings = C;
	A.onreadystatechange = function() {
		B(A, E);
	};

	A.open("GET", F, D);
	A.send(null);
}

function termino(a) {
	var status = 0;
	var indice = -1;
	var intervalo_a;
	var intervalo_b;
	var arraylista = a.split("-");
	termino.verifica = function() {
		if (Aja.readyState == 4) {
			indice++
		}
	};

	termino.listrequest = function() {
		if (indice == status) {
			if (indice < arraylista.length) {
				eval(arraylista[indice]);
				status++
			} else {
				clearInterval(intervalo_b);
				clearInterval(intervalo_a);
			}
		}
	};

	this.request = function() {
		intervalo_a = setInterval("termino.verifica()", 500);
		intervalo_b = setInterval("termino.listrequest()", 300);
	}
}

function requeste(B) {
	var A = new termino(B);
	A.request();
}

function include(A) {
	var B = document.createElement("script");
	B.setAttribute("type", "text/javascript");
	B.setAttribute("src", A);
	document.getElementsByTagName("head")[0].appendChild(B);
}

