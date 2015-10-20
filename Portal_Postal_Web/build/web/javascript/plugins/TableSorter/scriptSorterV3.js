var TINY={};
//expressao regular para identificar uma data
var expReg = /^((0?[1-9]|[12]\d)\/(0?[1-9]|1[0-2])|30\/(0?[13-9]|1[0-2])|31\/(0?[13578]|1[02]))\/(19|20)?\d{2}$/;
//expressao regular para identificar um date time com hr:min
var expReg2 = /^((0?[1-9]|[12]\d)\/(0?[1-9]|1[0-2])|30\/(0?[13-9]|1[0-2])|31\/(0?[13578]|1[02]))\/(19|20)?\d{2} ([0-1]\d|2[0-3]):[0-5]\d$/;
//expressao regular para identificar um date time com hr:min:seg
var expReg3 = /^((0?[1-9]|[12]\d)\/(0?[1-9]|1[0-2])|30\/(0?[13-9]|1[0-2])|31\/(0?[13578]|1[02]))\/(19|20)?\d{2} ([0-1]\d|2[0-3]):[0-5]\d:[0-5]\d$/;
            

function T$(i){
    return document.getElementById(i)
}
function T$$(e,p){
    return p.getElementsByTagName(e)
}

TINY.table=function(){
    function sorter(n,t,p){
        this.n=n; this.id=t; this.p=p; if(this.p.init){
            this.init()
        }
    }
    sorter.prototype.init=function(){
        this.set(); var t=this.t, i=d=0; t.h=T$$('tr',t)[0];
        t.l=t.r.length;
        if(t.l <= 0){ //codigo novo
            var tbody = T$$('tbody',t)[0];
            var qtdCols = t.h.cells.length;
            //cria a TR
            var row = document.createElement("TR")
            //cria as TDs
            for(var i = 0; i<qtdCols;i++){
                row.appendChild(document.createElement("TD"));
            }
            tbody.appendChild(row);
        } // fim codigo novo
        t.w=t.r[0].cells.length; t.a=[]; t.c=[]; this.p.is=this.p.size;
        if(this.p.colddid){
            d=T$(this.p.colddid);
            var o=document.createElement('option'); o.value=-1; o.innerHTML='Todas as Colunas'; d.appendChild(o)
        }
        for(i;i<t.w;i++){
            var c=t.h.cells[i]; t.c[i]={};
            if(c.className!='nosort'){
                c.className=this.p.headclass; c.onclick=new Function(this.n+'.sort('+i+')');
                c.onmousedown=function(){
                    return false
                };
            }
            if(this.p.columns){
                var l=this.p.columns.length, x=0;
                for(x;x<l;x++){
                    if(this.p.columns[x].index==i){
                        var g=this.p.columns[x];
                        t.c[i].format=g.format==null?1:g.format; t.c[i].decimals=g.decimals==null?2:g.decimals
                    }
                }
            }
            if(d){
                var o=document.createElement('option'); o.value=i; o.innerHTML=T$$('h3',c)[0].innerHTML; d.appendChild(o)
            }
        }
        this.reset()
    };
    sorter.prototype.reset=function(){
        var t=this.t; t.t=t.l;
        for(var i=0;i<t.l;i++){
            t.a[i]={}; t.a[i].s=1
        }
        if(this.p.sortcolumn!=undefined){
            this.sort(this.p.sortcolumn,1,this.p.is)
        }else{
            if(this.p.paginate){
                this.size()
            } this.alt(); this.sethover()
        }
        this.calc()
    };
    sorter.prototype.sort=function(x,f,z){
        var t=this.t; t.y=x; var x=t.h.cells[t.y], i=0, n=document.createElement('tbody');
        for(i;i<t.l;i++){
            t.a[i].o=i; var v=t.r[i].cells[t.y]; t.r[i].style.display='';
            while(v.hasChildNodes()){
                v=v.firstChild
            }
            t.a[i].v=v.nodeValue?v.nodeValue:''
        }
        for(i=0;i<t.w;i++){
            var c=t.h.cells[i]; if(c.className!='nosort'){
                c.className=this.p.headclass
            }
        }
        if(t.p==t.y&&!f){
            t.a.reverse(); x.className=t.d?this.p.ascclass:this.p.descclass; t.d=t.d?0:1
        }
        else{
            t.p=t.y; f&&this.p.sortdir==-1?t.a.sort(cp).reverse():t.a.sort(cp); t.d=0; x.className=this.p.ascclass
        }
        for(i=0;i<t.l;i++){
            var r=t.r[t.a[i].o].cloneNode(true); n.appendChild(r)
        }
        t.replaceChild(n,t.b); this.set(); this.alt(); if(this.p.paginate){
            this.size(z)
        } this.sethover()
    };
    sorter.prototype.sethover=function(){
        if(this.p.hoverid){
            for(var i=0;i<this.t.l;i++){
                var r=this.t.r[i];
                r.setAttribute('onmouseover',this.n+'.hover('+i+',1)');
                r.setAttribute('onmouseout',this.n+'.hover('+i+',0)')
            }
        }
    };
    sorter.prototype.calc=function(){
        if(this.p.sum||this.p.avg){
            var t=this.t, i=x=0, f,r;
            if(!T$$('tfoot',t)[0]){
                f=document.createElement('tfoot'); t.appendChild(f)
            }else{
                f=T$$('tfoot',t)[0]; while(f.hasChildNodes()){
                    f.removeChild(f.firstChild)
                }
            }
            if(this.p.sum){
                r=this.newrow(f);
                for(i;i<t.w;i++){
                    var j=r.cells[i];
                    if(this.p.sum.exists(i)){
                        var s=0, m=t.c[i].format||'';
                        for(x=0;x<this.t.l;x++){
                            if(t.a[x].s){
                                s+=parseFloat(t.r[x].cells[i].innerHTML.replace(/(\$|\,)/g,''))
                            }
                        }
                        s=decimals(s,t.c[i].decimals?t.c[i].decimals:2);
                        s=isNaN(s)?'n/a':m=='$'?s=s.currency(t.c[i].decimals):s+m;
                        r.cells[i].innerHTML=s
                    }else{
                        r.cells[i].innerHTML='&nbsp;'
                    }
                }
            }
            if(this.p.avg){
                r=this.newrow(f);
                for(i=0;i<t.w;i++){
                    var j=r.cells[i];
                    if(this.p.avg.exists(i)){
                        var s=c=0, m=t.c[i].format||'';
                        for(x=0;x<this.t.l;x++){
                            if(t.a[x].s){
                                s+=parseFloat(t.r[x].cells[i].innerHTML.replace(/(\$|\,)/g,'')); c++
                            }
                        }
                        s=decimals(s/c,t.c[i].decimals?t.c[i].decimals:2);
                        s=isNaN(s)?'n/a':m=='$'?s=s.currency(t.c[i].decimals):s+m;
                        j.innerHTML=s
                    }else{
                        j.innerHTML='&nbsp;'
                    }
                }
            }
        }
    };
    sorter.prototype.newrow=function(p){
        var r=document.createElement('tr'), i=0; p.appendChild(r);
        for(i;i<this.t.w;i++){
            r.appendChild(document.createElement('td'))
        }
        return r
    };
    sorter.prototype.alt=function(){
        var t=this.t, i=x=0;
        for(i;i<t.l;i++){
            var r=t.r[i];
            if(t.a[i].s){
                r.className=x%2==0?this.p.evenclass:this.p.oddclass; var cells=T$$('td',r);
                for(var z=0;z<t.w;z++){
                    cells[z].className=t.y==z?x%2==0?this.p.evenselclass:this.p.oddselclass:''
                }
                x++
            }
            if(!t.a[i].s){
                r.style.display='none'
            }
        }
    };
    sorter.prototype.page=function(s){
        var t=this.t, i=x=0, l=s+parseInt(this.p.size);
        if(this.p.totalrecid){
            T$(this.p.totalrecid).innerHTML=t.t
        }
        if(this.p.currentid){
            T$(this.p.currentid).innerHTML=this.g
        }
        if(this.p.startingrecid){
            var b=((this.g-1)*this.p.size)+1, m=b+(this.p.size-1); m=m<t.l?m:t.t; m=m<t.t?m:t.t;
            T$(this.p.startingrecid).innerHTML=t.t==0?0:b;; T$(this.p.endingrecid).innerHTML=m
        }
        for(i;i<t.l;i++){
            var r=t.r[i]; if(t.a[i].s){
                r.style.display=x>=s&&x<l?'':'none'; x++
            }else{
                r.style.display='none'
            }
        }
    };
    sorter.prototype.move=function(d,m){
        this.goto(d==1?(m?this.d:this.g+1):(m?1:this.g-1))
    };
    sorter.prototype.goto=function(s){
        s=parseInt(s); // fixes 1st issue
        if(s<=this.d&&s>0){
            this.g=s; this.page((s-1)*this.p.size)
        }
        T$(this.p.pageddid).selectedIndex = this.g-1; // fixes 2nd issue
    //if(s<=this.d&&s>0){this.g=s; this.page((s-1)*this.p.size)}     //codigo antigo
    };
    sorter.prototype.size=function(s){
        var t=this.t;
        if(s){
            this.p.size=s
        }
        this.g=1; this.d=Math.ceil(this.t.t/this.p.size);
        if(this.p.navid){
            T$(this.p.navid).style.display=this.d<2?'none':'block'
        }
        this.page(0);
        if(this.p.totalid){
            T$(this.p.totalid).innerHTML=t.t==0?1:this.d
        }
        if(this.p.pageddid){
            var d=T$(this.p.pageddid), l=this.d+1;
            d.setAttribute('onchange',this.n+'.goto(this.value)');
            while(d.hasChildNodes()){
                d.removeChild(d.firstChild)
            }
            for(var i=1;i<=this.d;i++){
                var o=document.createElement('option');
                o.value=i; o.innerHTML=i; d.appendChild(o)
            }
        }
    };
    sorter.prototype.showall=function(){
        this.size(this.t.t)
    };
    sorter.prototype.search=function(f){
        var i=x=n=0, k=-1, q=T$(f).value.toLowerCase();
        if(this.p.colddid){
            k=T$(this.p.colddid).value
        }
        var s=(k==-1)?0:k, e=(k==-1)?this.t.w:parseInt(s)+1;
        for(i;i<this.t.l;i++){
            var r=this.t.r[i], v;
            if(q==''){
                v=1
            }else{
                for(x=s;x<e;x++){
                    var b=r.cells[x].innerHTML.toLowerCase();
                    if(b.indexOf(q)==-1){
                        v=0
                    }else{
                        v=1; break
                    }
                }
            }
            if(v){
                n++
            }
            this.t.a[i].s=v
        }
        this.t.t=n;
        if(this.p.paginate){
            this.size()
        }
        this.calc(); this.alt()
    };
    sorter.prototype.hover=function(i,d){
        this.t.r[i].id=d?this.p.hoverid:''
    };
    sorter.prototype.set=function(){
        var t=T$(this.id); t.b=T$$('tbody',t)[0]; t.r=t.b.rows; this.t=t
    };
    Array.prototype.exists=function(v){
        for(var i=0;i<this.length;i++){
            if(this[i]==v){
                return 1
            }
        } return 0
    };
    Number.prototype.currency=function(c){
        var n=this, d=n.toFixed(c).split('.');
        d[0]=d[0].split('').reverse().join('').replace(/(\d{3})(?=\d)/g,'$1,').split('').reverse().join('');
        return '$'+d.join('.')
    };
    function decimals(n,d){
        return Math.round(n*Math.pow(10,d))/Math.pow(10,d)
    };
    function cp(f,c){
        var g,h; f=g=f.v.toLowerCase(); c=h=c.v.toLowerCase();
        var i=parseFloat(f.replace(/(\$|\,)/g,'')), n=parseFloat(c.replace(/(\$|\,)/g,''));
        if(!isNaN(i)&&!isNaN(n)){
            g=i,h=n
        }
        //i=Date.parse(f); n=Date.parse(c); //codigo antigo
        var datas = transferDateString(f,c); //codigo novo
        var aux2 = datas.split("@"); //codigo novo
        i=Date.parse(aux2[0]); //codigo novo
        n=Date.parse(aux2[1]); //codigo novo

        if(!isNaN(i)&&!isNaN(n)){
            g=i; h=n
        }
        return g>h?1:(g<h?-1:0)
    };
    
    function transferDateString(originalDate, originalDate2){ //codigo novo
        var retorno = "";
        try{
            if(originalDate.match(expReg)){
                var date = originalDate.substring(0,originalDate.indexOf("/"));
                var month = originalDate.substring(originalDate.indexOf("/")+1,originalDate.lastIndexOf("/"));
                var year = originalDate.substring(originalDate.lastIndexOf("/")+1);
                retorno = month+"/"+date+"/"+year;
            }else if(originalDate.match(expReg2)){
                var aux = originalDate.split(" ");
                var date = aux[0].substring(0,aux[0].indexOf("/"));
                var month = aux[0].substring(aux[0].indexOf("/")+1,aux[0].lastIndexOf("/"));
                var year = aux[0].substring(aux[0].lastIndexOf("/")+1);
                retorno = month+"/"+date+"/"+year+" "+aux[1];
            }else if(originalDate.match(expReg3)){
                var aux = originalDate.split(" ");
                var date = aux[0].substring(0,aux[0].indexOf("/"));
                var month = aux[0].substring(aux[0].indexOf("/")+1,aux[0].lastIndexOf("/"));
                var year = aux[0].substring(aux[0].lastIndexOf("/")+1);
                retorno = month+"/"+date+"/"+year+" "+aux[1];
            }else{
                retorno = "";
            }
            retorno += "@";
            if(originalDate2.match(expReg)){
                var date2 = originalDate2.substring(0,originalDate2.indexOf("/"));
                var month2 = originalDate2.substring(originalDate2.indexOf("/")+1,originalDate2.lastIndexOf("/"));
                var year2 = originalDate2.substring(originalDate2.lastIndexOf("/")+1);
                retorno += month2+"/"+date2+"/"+year2;
            }else if(originalDate2.match(expReg2)){
                var aux2 = originalDate2.split(" ");
                var date2 = aux2[0].substring(0,aux2[0].indexOf("/"));
                var month2 = aux2[0].substring(aux2[0].indexOf("/")+1,aux2[0].lastIndexOf("/"));
                var year2 = aux2[0].substring(aux2[0].lastIndexOf("/")+1);
                retorno += month2+"/"+date2+"/"+year2+" "+aux2[1];
            }else if(originalDate2.match(expReg3)){
                var aux2 = originalDate2.split(" ");
                var date2 = aux2[0].substring(0,aux2[0].indexOf("/"));
                var month2 = aux2[0].substring(aux2[0].indexOf("/")+1,aux2[0].lastIndexOf("/"));
                var year2 = aux2[0].substring(aux2[0].lastIndexOf("/")+1);
                retorno += month2+"/"+date2+"/"+year2+" "+aux2[1];
            }else{
                retorno += "";
            }
            return retorno;
        }catch(e){
            return "@";
        }
    }
    return{
        sorter:sorter
    }
}();