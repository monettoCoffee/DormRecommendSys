function initial(){
    setTimeout("app.switchBackground();",2200);
    if(window.location.href.indexOf("question") == -1 && checkCookie("token")){
//        window.location.href = "manage";
    // todo 验证其可用性，否则会无限跳转
    // TODO Token可用才能跳转
    }
}

window.onload = function() {
    initial();
}