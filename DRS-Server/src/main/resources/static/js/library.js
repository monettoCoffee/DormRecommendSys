function domClass(className,index){
    index=typeof(index)!=='undefined'?index:0;
    return document.getElementsByClassName(className)[index];
}

function setCookie(cookieName,value,expiredays){
    value=typeof(value)!=='undefined'?value:"null";
    expiredays=typeof(expiredays)!=='undefined'?expiredays:3;
    var exdate=new Date();
    exdate.setDate(exdate.getDate()+expiredays);
    document.cookie=cookieName+ "=" +escape(value)+((expiredays==null) ? "" : ";expires="+exdate.toGMTString());
}

function delCookie(name){
    var exp = new Date(); 
    exp.setTime(exp.getTime() - 1); 
    var cval=getCookie(name); 
    if(cval!=null) 
        document.cookie= name + "="+cval+";expires="+exp.toGMTString(); 
} 

function getCookie(cookieName){
    if (document.cookie.length>0){
        let start=document.cookie.indexOf(cookieName + "=");
        if (start!=-1){ 
            start=start + cookieName.length+1;
            let end=document.cookie.indexOf(";",start);
            if (end==-1) 
                end=document.cookie.length;
            return unescape(document.cookie.substring(start,end));
        } 
    }
    return "";
}   

function checkCookie(cookieName){
    let mCookie=getCookie(cookieName);
    if (mCookie!=null && mCookie!="")
        return true;
    return false;
}

function convertImgToBase64(url,callback,size,sqare,outputFormat){ 
    var canvas = document.createElement('CANVAS'); 
    var ctx = canvas.getContext('2d'); 
    var img = new Image; 
    img.crossOrigin = 'Anonymous'; 
    img.onload = function(){
        var width = img.width;
        var height = img.height;
        var rate = (width<height ? width/height : height/width)/parseInt(width/size);
        if(sqare){
        	canvas.width = width*rate; 
        	canvas.height = width*rate;
        	ctx.drawImage(img,0,0,width,height,0,0,width*rate,width*rate);
        }else{
        	canvas.width = width*rate; 
        	canvas.height = height*rate;
        	ctx.drawImage(img,0,0,width,height,0,0,width*rate,height*rate);
        }
        var dataURL = canvas.toDataURL(outputFormat || 'image/png'); 
        callback.call(this, dataURL); 
        canvas = null; 
    };
    img.src = url; 
}
      
function getObjectURL(file) {
    var url = null ; 
    if (window.createObjectURL!=undefined) {
        url = window.createObjectURL(file) ;
    } else if (window.URL!=undefined) {
        url = window.URL.createObjectURL(file) ;
    } else if (window.webkitURL!=undefined) {
        url = window.webkitURL.createObjectURL(file) ;
    }
    return url ;
}
     
function fileCompressToBase64(inputClass,size,outputClass,sqare){
	//参数分别为输入的那个input file,转换成的图片的宽大小,输出src对应的img,是否转化为正方形
	if(!strInArray(domClass(inputClass).value.split(".")[domClass(inputClass).value.split(".").length-1],["jpg,jpeg,png,bmp"]))
		app.VLtips("上传的不是图片!");
    size=typeof(size)!=='undefined'?size:200;
    sqare=typeof(sqare)!=='undefined'?sqare:false;
    var imageUrl = getObjectURL(document.getElementsByClassName(inputClass)[0].files[0]);
    convertImgToBase64(imageUrl, function(base64Img){
    document.getElementsByClassName(outputClass)[0].src=base64Img;
    //不需要base64的前缀data:image/jpg;base64的情况下
    //alert(base64Img.split(",")[1]);
    },size,sqare);
    event.preventDefault(); 
}

function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) 
    	return unescape(r[2]);
    return null;
}

function strInArray(val, arr) {
    for (var ele in arr) {
        if (arr[ele] == val) {
            return true;
        }
    }
    return false;
}

function strInArrayInline(val, arr){
    for (var ele in arr) {
        if (arr[elel].indexOf(val) != -1) {
            return true;
        }
    }
    return false;
} 

function isIEBrowser() {
   return window.navigator.userAgent.indexOf("MSIE")>=1 || (!!window.ActiveXObject || "ActiveXObject" in window)
}

function toPost(url, params, thenFunction, errorFunction) {
    params = typeof(params) == 'undefined' ? {} : params;
    var urlSearchParams = new URLSearchParams();
    for (key in params) {
        urlSearchParams.append(key, JSON.stringify(params[key]));
    }
    if (typeof(thenFunction) == 'undefined') {
        thenFunction = function(response) {
            alert(JSON.stringify(response));
        }
    }
    if (typeof(errorFunction) == 'undefined') {
        errorFunction = function(error) {
            alert("Error: " + JSON.stringify(error));
        }
    }
    axios.post(url, urlSearchParams).then(
        function(response) {thenFunction(response);}
    )
//    .catch(
//        function(error) {errorFunction(error);}
//    );
}