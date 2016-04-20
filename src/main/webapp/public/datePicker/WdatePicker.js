/*
 * My97 DatePicker 4.6
 * SITE: http://dp.my97.net
 * BLOG: http://my97.cnblogs.com
 * MAIL: smallcarrot@163.com
 */

function judgeDate(date_begin, date_end) {
	var date_begin_b;
	var date_end_e;
	if (date_begin != undefined && date_end != undefined) {
		date_begin_b = date_begin.replace(/-/g, '');
		date_begin_e = date_end.replace(/-/g, '');
		if (date_begin_b > date_begin_e && date_begin_e != '') {
			return false;
		}
	}
	return true;
}
function getTmpDate(tmpDate){
    var day = tmpDate.getDate();
    var month= tmpDate.getMonth() + 1 ;
    var year= tmpDate.getFullYear();
    month = ((month < 10) ? "0" : "") + month;
    day = ((day < 10) ? "0" : "") + day;
    return year + month + day;
}
//选择历史还是当天
/*
function historyAndToday(begin_id,end_id,span_month_sub,span_date){
	var toDays=0;
	ryt_date(begin_id,end_id,span_month_sub,span_date,toDays,function(){
			var begin_date= document.getElementById(begin_id).value;
	        var maxDate;
	        var now = new Date();
	         now.setDate(now.getDate()-span_date);        
	        var end_date= getTmpDate(now);
	       	 if(begin_date==getTmpDate(new Date())){
	    		 document.getElementById(end_id).value="20110915";
	    	 }else{
	    		 toDays=-1; 
	    	 }
	        if(!judgeDate(begin_date,end_date)){
	            maxDate = parseInt(toDays)==0 ? '%y-%M-%d' : '%y-%M-{%d'+toDays+'}';
	        }else{
	            maxDate = '#F{$dp.$D(\''+begin_id+'\',{d:'+span_date+'})}';
	        }
	       // alert(begin_date+"xxx"+end_date+"=xx="+getTmpDate(new Date())+"xxx"+maxDate)
	        return maxDate;
	});
}*/
//基本时间处理
function base_date(begin_id,end_id,span_month_sub,span_date,toDays,pickedFun){
	 WdatePicker({
		 onpicked:pickedFun(),
         skin:'ext',
         minDate: parseInt(span_month_sub)>0 ? '%y-{%M-'+span_month_sub+'}-{%d-1}' : '2010-01-01',
         maxDate: parseInt(toDays)==0 ? '%y-%M-%d' : '%y-%M-{%d'+toDays+'}',
         dateFmt:'yyyyMMdd',
         readOnly:'true'});
}

//onfocus的时候处理函数
function ryt_date(begin_id,end_id,span_month_sub,span_date,toDays,maxDateFun){
	base_date(begin_id,end_id,span_month_sub,span_date,toDays,function(){
        end_obj = document.getElementById(end_id);
        end_obj.disabled = '';
        end_obj.value = '';
        end_obj.onfocus = function(){
    		var begin_obj = document.getElementById(begin_id);
    	    var begin_date = begin_obj.value;
    	    if(begin_date =='') {
    	    	end_obj.disabled = 'disabled';
    	    }
    	    var min = begin_date.substring(0,4)+'-'+begin_date.substring(4,6)+'-'+begin_date.substring(6,8);
    	    var arg = {};
    	    arg.skin = 'ext';
    	    arg.minDate = min;
    	    arg.maxDate=maxDateFun();//maxDate的处理
    	    arg.dateFmt = 'yyyyMMdd';
    	    arg.readOnly='true';
    	    WdatePicker(arg);
    	};
    });
}

//常用的时间选择
function ryt_area_date(begin_id,end_id,span_month_sub,span_date,toDays){
	ryt_date(begin_id,end_id,span_month_sub,span_date,toDays,function(){
		var begin_date= document.getElementById(begin_id).value;
        var maxDate;
        var now = new Date();
         now.setDate(now.getDate()-span_date);        
        var end_date= getTmpDate(now);
        if(!judgeDate(begin_date,end_date)){
            maxDate = parseInt(toDays)==0 ? '%y-%M-%d' : '%y-%M-{%d'+toDays+'}';
        }else{
            maxDate = '#F{$dp.$D(\''+begin_id+'\',{d:'+span_date+'})}';
        }
        return maxDate;
	});
}

//onfocus的时候处理函数
function ryt_date_sub(begin_id,end_id,span_month_sub,span_date,toDays,maxDateFun){
	base_date_sub(begin_id,end_id,span_month_sub,span_date,toDays,function(){
        end_obj = document.getElementById(end_id);
        end_obj.disabled = '';
        end_obj.value = '';
        end_obj.onfocus = function(){
    		var begin_obj = document.getElementById(begin_id);
    	    var begin_date = begin_obj.value;
    	    if(begin_date =='') {
    	    	end_obj.disabled = 'disabled';
    	    }
    	    var min = begin_date.substring(0,4)+'-'+begin_date.substring(4,6)+'-'+begin_date.substring(6,8);
    	    var arg = {};
    	    arg.skin = 'ext';
    	    arg.minDate = min;
    	    arg.maxDate=maxDateFun();//maxDate的处理
    	    arg.dateFmt = 'yyyyMMdd';
    	    arg.readOnly='true';
    	    WdatePicker(arg);
    	};
    });
}

//t-1日期
function base_date_sub(begin_id,end_id,span_month_sub,span_date,toDays,pickedFun){
	 WdatePicker({
		 onpicked:pickedFun(),
         skin:'ext',
         minDate: parseInt(span_month_sub)>0 ? '%y-{%M-'+span_month_sub+'}-{%d-1}' : '2010-01-01',
         maxDate: parseInt(toDays)==0 ? '%y-%M-{%d-1}' : '%y-%M-{%d-1'+toDays+'}',
         dateFmt:'yyyyMMdd',
         readOnly:'true'});
}

//常用的时间选择
function ryt_before_date(begin_id,end_id,span_month_sub,span_date,toDays){
	ryt_date_sub(begin_id,end_id,span_month_sub,span_date,toDays,function(){
		var begin_date= document.getElementById(begin_id).value;
        var maxDate;
        var now = new Date();
         now.setDate(now.getDate()-span_date);        
        var end_date= getTmpDate(now);
        if(!judgeDate(begin_date,end_date)){
            maxDate = parseInt(toDays)==0 ? '%y-%M-{%d-1}' : '%y-%M-{%d-1'+toDays+'}';
        }else{
            maxDate = '#F{$dp.$D(\''+begin_id+'\',{d:'+span_date+'})}';
        }
        return maxDate;
	});
}


//选择历史还是当天
function historyAndToday(begin_id,end_id,span_month_sub,span_date){
	var toDays=0;
	var begin_obj=document.getElementById(begin_id);
	 begin_obj.onchange=function(){
		 var end_obj=document.getElementById(end_id);
    	if(begin_obj.value==getTmpDate(new Date())){
    		end_obj.value = begin_obj.value;
    		end_obj.disabled = 'disabled';
    	}
	}
	ryt_date(begin_id,end_id,span_month_sub,span_date,toDays,function(){
			var begin_date= document.getElementById(begin_id).value;
	        var maxDate;
	        var now = new Date();
	         now.setDate(now.getDate()-span_date);        
	        var end_date= getTmpDate(now);
	       	 if(begin_date!=getTmpDate(new Date())){
	       		 toDays=-1; 
	    	 }
	        if(!judgeDate(begin_date,end_date)){
	            maxDate = parseInt(toDays)==0 ? '%y-%M-%d' : '%y-%M-{%d'+toDays+'}';
	        }else{
	            maxDate = '#F{$dp.$D(\''+begin_id+'\',{d:'+span_date+'})}';
	        }
	        return maxDate;
	});
}
/*
function ryt_area_date(begin_id,end_id,span_month_sub,span_date,toDays){
		 WdatePicker({
                onpicked:function(){
			        end_obj = document.getElementById(end_id);
			        end_obj.value = '';
			        end_obj.disabled = '';
			        end_obj.onfocus = function(){
                        var begin_obj = document.getElementById(begin_id);
                        var v = begin_obj.value;
                        if(v =='') {
                        	end_obj.disabled = 'disabled';
                        }
                        var min = v.substring(0,4)+'-'+v.substring(4,6)+'-'+v.substring(6,8);
                        var arg = {};
                        arg.skin = 'ext';
                        arg.minDate = min;
                        now = new Date;
                        // 相差**天前日期 
                        now.setDate(now.getDate()-span_date); 
                        var today = parseInt(now.getDate())+"";
                        var month = (parseInt(now.getMonth())+1)+"";
                        var year = parseInt(now.getFullYear())+"";
                        if(today.length == 1){
                        	today = "0"+today
                        }
                        if(month.length == 1){
                        	month = "0"+month
                        }
                        var end_date = year+month+today;
                        if(!judgeDate(v,end_date)){
                            arg.maxDate = parseInt(toDays)==0 ? '%y-%M-%d' : '%y-%M-{%d'+toDays+'}';
                        }else{
                            arg.maxDate = '#F{$dp.$D(\''+begin_id+'\',{d:'+span_date+'})}';
                        }
                        arg.dateFmt = 'yyyyMMdd';
                        arg.readOnly='true';
                        WdatePicker(arg);
                   };
                   },
                skin:'ext',
                minDate: parseInt(span_month_sub)>0 ? '%y-{%M-'+span_month_sub+'}-{%d-1}' : '2010-01-01',
                maxDate: parseInt(toDays)==0 ? '%y-%M-%d' : '%y-%M-{%d'+toDays+'}',
                dateFmt:'yyyyMMdd',
                readOnly:'true'});
}
*/



var $dp,WdatePicker;(function(){var _={
$wdate:true,
$dpPath:"",
$crossFrame:true,
doubleCalendar:false,
position:{},
lang:"auto",
skin:"default",
dateFmt:"yyyyMMdd",
realDateFmt:"yyyyMMdd",
realTimeFmt:"HH:mm:ss",
realFullFmt:"%Date %Time",
minDate:"1900-01-01 00:00:00",
maxDate:"2099-12-31 23:59:59",
startDate:"",
alwaysUseStartDate:false,
yearOffset:1911,
firstDayOfWeek:0,
isShowWeek:false,
highLineWeekDay:true,
isShowClear:true,
isShowToday:true,
isShowOthers:true,
readOnly:false,
errDealMode:0,
autoPickDate:null,
qsEnabled:true,

specialDates:null,specialDays:null,disabledDates:null,disabledDays:null,opposite:false,onpicking:null,onpicked:null,onclearing:null,oncleared:null,ychanging:null,ychanged:null,Mchanging:null,Mchanged:null,dchanging:null,dchanged:null,Hchanging:null,Hchanged:null,mchanging:null,mchanged:null,schanging:null,schanged:null,eCont:null,vel:null,errMsg:"",quickSel:[],has:{}};WdatePicker=U;var X=window,O="document",J="documentElement",C="getElementsByTagName",V,A,T,I,b;switch(navigator.appName){case"Microsoft Internet Explorer":T=true;break;case"Opera":b=true;break;default:I=true;break}A=L();if(_.$wdate)M(A+"skin/WdatePicker.css");V=X;if(_.$crossFrame){try{while(V.parent[O]!=V[O]&&V.parent[O][C]("frameset").length==0)V=V.parent}catch(P){}}if(!V.$dp)V.$dp={ff:I,ie:T,opera:b,el:null,win:X,status:0,defMinDate:_.minDate,defMaxDate:_.maxDate,flatCfgs:[]};B();if($dp.status==0)Z(X,function(){U(null,true)});if(!X[O].docMD){E(X[O],"onmousedown",D);X[O].docMD=true}if(!V[O].docMD){E(V[O],"onmousedown",D);V[O].docMD=true}E(X,"onunload",function(){if($dp.dd)Q($dp.dd,"none")});function B(){V.$dp=V.$dp||{};obj={$:function($){return(typeof $=="string")?this.win[O].getElementById($):$},$D:function($,_){return this.$DV(this.$($).value,_)},$DV:function(_,$){if(_!=""){this.dt=$dp.cal.splitDate(_,$dp.cal.dateFmt);if($)for(var A in $){if(this.dt[A]===undefined)this.errMsg="invalid property:"+A;this.dt[A]+=$[A]}if(this.dt.refresh())return this.dt}return""},show:function(){Q(this.dd,"block")},hide:function(){Q(this.dd,"none")},attachEvent:E};for(var $ in obj)V.$dp[$]=obj[$];$dp=V.$dp}function E(A,$,_){if(T)A.attachEvent($,_);else{var B=$.replace(/on/,"");_._ieEmuEventHandler=function($){return _($)};A.addEventListener(B,_._ieEmuEventHandler,false)}}function L(){var _,A,$=X[O][C]("script");for(var B=0;B<$.length;B++){_=$[B].src.substring(0,$[B].src.toLowerCase().indexOf("wdatepicker.js"));A=_.lastIndexOf("/");if(A>0)_=_.substring(0,A+1);if(_)break}return _}function F(F){var E,C;if(F.substring(0,1)!="/"&&F.indexOf("://")==-1){E=V.location.href;C=location.href;if(E.indexOf("?")>-1)E=E.substring(0,E.indexOf("?"));if(C.indexOf("?")>-1)C=C.substring(0,C.indexOf("?"));var G,I,$="",D="",A="",J,H,B="";for(J=0;J<Math.max(E.length,C.length);J++){G=E.charAt(J).toLowerCase();I=C.charAt(J).toLowerCase();if(G==I){if(G=="/")H=J}else{$=E.substring(H+1,E.length);$=$.substring(0,$.lastIndexOf("/"));D=C.substring(H+1,C.length);D=D.substring(0,D.lastIndexOf("/"));break}}if($!="")for(J=0;J<$.split("/").length;J++)B+="../";if(D!="")B+=D+"/";F=E.substring(0,E.lastIndexOf("/")+1)+B+F}_.$dpPath=F}function M(A,$,B){var D=X[O][C]("HEAD").item(0),_=X[O].createElement("link");if(D){_.href=A;_.rel="stylesheet";_.type="text/css";if($)_.title=$;if(B)_.charset=B;D.appendChild(_)}}function Z($,_){E($,"onload",_)}function G($){$=$||V;var A=0,_=0;while($!=V){var D=$.parent[O][C]("iframe");for(var F=0;F<D.length;F++){try{if(D[F].contentWindow==$){var E=W(D[F]);A+=E.left;_+=E.top;break}}catch(B){}}$=$.parent}return{"leftM":A,"topM":_}}function W(E){if(T)return E.getBoundingClientRect();else{var A={ROOT_TAG:/^body|html$/i,OP_SCROLL:/^(?:inline|table-row)$/i},G=null,_=E.offsetTop,F=E.offsetLeft,D=E.offsetWidth,B=E.offsetHeight,C=E.offsetParent;if(C!=E)while(C){F+=C.offsetLeft;_+=C.offsetTop;if(C.tagName.toLowerCase()=="body")G=C.ownerDocument.defaultView;C=C.offsetParent}C=E.parentNode;while(C.tagName&&!A.ROOT_TAG.test(C.tagName)){if(C.scrollTop||C.scrollLeft)if(!A.OP_SCROLL.test(Q(C)))if(!b||C.style.overflow!=="visible"){F-=C.scrollLeft;_-=C.scrollTop}C=C.parentNode}var $=a(G);F-=$.left;_-=$.top;D+=F;B+=_;return{"left":F,"top":_,"right":D,"bottom":B}}}function N($){$=$||V;var _=$[O];_=_[J]&&_[J].clientHeight&&_[J].clientHeight<=_.body.clientHeight?_[J]:_.body;return{"width":_.clientWidth,"height":_.clientHeight}}function a($){$=$||V;var B=$[O],A=B[J],_=B.body;B=(A&&A.scrollTop!=null&&(A.scrollTop>_.scrollLeft||A.scrollLeft>_.scrollLeft))?A:_;return{"top":B.scrollTop,"left":B.scrollLeft}}function D($){src=$?($.srcElement||$.target):null;if($dp&&$dp.cal&&!$dp.eCont&&$dp.dd&&Q($dp.dd)=="block"&&src!=$dp.el)$dp.cal.close()}function Y(){$dp.status=2;H()}function H(){if($dp.flatCfgs.length>0){var $=$dp.flatCfgs.shift();$.el={innerHTML:""};$.autoPickDate=true;$.qsEnabled=false;K($)}}var R,$;function U(E,_){$dp.win=X;B();E=E||{};if(_){if(!D()){$=$||setInterval(function(){if(V[O].readyState=="complete")clearInterval($);U(null,true)},50);return}if($dp.status==0){$dp.status=1;K({el:{innerHTML:""}},true)}else return}else if(E.eCont){E.eCont=$dp.$(E.eCont);$dp.flatCfgs.push(E);if($dp.status==2)H()}else{if($dp.status==0){U(null,true);return}if($dp.status!=2)return;var C=A();if(C){$dp.srcEl=C.srcElement||C.target;C.cancelBubble=true}E.el=$dp.$(E.el||$dp.srcEl);if(!E.el||E.el.disabled||(E.el==$dp.el&&Q($dp.dd)!="none"&&$dp.dd.style.left!="-1970px"))return;K(E)}function D(){if(T&&V!=X&&V[O].readyState!="complete")return false;return true}function A(){if(I){func=A.caller;while(func!=null){var $=func.arguments[0];if($&&($+"").indexOf("Event")>=0)return $;func=func.caller}return null}return event}}function S(_,$){return _.currentStyle?_.currentStyle[$]:document.defaultView.getComputedStyle(_,false)[$]}function Q(_,$){if(_)if($!=null)_.style.display=$;else return S(_,"display")}function K(H,$){for(var D in _)if(D.substring(0,1)!="$")$dp[D]=_[D];for(D in H)if($dp[D]!==undefined)$dp[D]=H[D];var E=$dp.el?$dp.el.nodeName:"INPUT";if($||$dp.eCont||new RegExp(/input|textarea|div|span|p|a/ig).test(E))$dp.elProp=E=="INPUT"?"value":"innerHTML";else return;if($dp.lang=="auto")$dp.lang=T?navigator.browserLanguage.toLowerCase():navigator.language.toLowerCase();if(!$dp.dd||$dp.eCont||($dp.lang&&$dp.realLang&&$dp.realLang.name!=$dp.lang&&$dp.getLangIndex&&$dp.getLangIndex($dp.lang)>=0)){if($dp.dd&&!$dp.eCont)V[O].body.removeChild($dp.dd);if(_.$dpPath=="")F(A);var B="<iframe src=\""+_.$dpPath+"My97DatePicker.htm\" frameborder=\"0\" border=\"0\" scrolling=\"no\"></iframe>";if($dp.eCont){$dp.eCont.innerHTML=B;Z($dp.eCont.childNodes[0],Y)}else{$dp.dd=V[O].createElement("DIV");$dp.dd.style.cssText="position:absolute;z-index:19700";$dp.dd.innerHTML=B;V[O].body.insertBefore($dp.dd,V[O].body.firstChild);Z($dp.dd.childNodes[0],Y);if($)$dp.dd.style.left=$dp.dd.style.top="-1970px";else{$dp.show();C()}}}else if($dp.cal){$dp.show();$dp.cal.init();if(!$dp.eCont)C()}function C(){var F=$dp.position.left,B=$dp.position.top,C=$dp.el;if(C!=$dp.srcEl&&(Q(C)=="none"||C.type=="hidden"))C=$dp.srcEl;var H=W(C),$=G(X),D=N(V),A=a(V),E=$dp.dd.offsetHeight,_=$dp.dd.offsetWidth;if(isNaN(B)){if(B=="above"||(B!="under"&&(($.topM+H.bottom+E>D.height)&&($.topM+H.top-E>0))))B=A.top+$.topM+H.top-E-3;else B=A.top+$.topM+H.bottom;B+=T?-1:1}else B+=A.top+$.topM;if(isNaN(F))F=A.left+Math.min($.leftM+H.left,D.width-_-5)-(T?2:0);else F+=A.left+$.leftM;$dp.dd.style.top=B+"px";$dp.dd.style.left=F+"px"}}})()