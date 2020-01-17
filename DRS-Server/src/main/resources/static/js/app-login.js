var app = new Vue({
    el:".app",
    data:{
        now:"login",
        panelHeight:"height:400px;",
        transShow:true,
        transOpacity:"opacity:1",
        modelShow:false,
        info:"",
        title:"",
        modelStyle:"",
        findButtonStyle:"background-color:#999",
        backgroundShow:false,
        loadingShow:false,
        canAlterPass:"",
        laterLogin:[],
        laterTime:[],
        backgroundPic1:true,
        backgroundPic2:false,
        backgroundPic3:false,
    },
    methods:{
        login(){
            if(domClass("login-item",0).value==""||domClass("login-item",1).value==""){
                app.tips("请输入正确的账号密码哦!");
                return;
            }
            let testTime = app.laterLogin.indexOf(domClass("login-item",0).value);
            if(testTime!=-1&&app.laterTime[testTime]>5){
                app.tips("尝试次数过多,请于20分钟后登录该账号。");
                return;
            }
            app.loading();
            let fun =function(){
                let params = new URLSearchParams();
                params.append("account",domClass("login-item",0).value);
                params.append("password",domClass("login-item",1).value);
                axios.post('login',params).then(function (response){
                    let data = JSON.parse(JSON.stringify(response.data));
                    switch(data.serviceCode) {
                        case 2000:
                            setCookie("token", data.result, 15)
                            window.location.href = "manage";
                            break;
                        case 2001:
                            app.tips("未注册该账号!");
                            break;
                        case 2002:
                            app.tips("密码不正确!");
                            break;
                        default:
                            app.tips("发生错误!请检是否输入正确!");
                    }
                });
            };
            setTimeout(fun,300);
        },
        transReg(){
            app.transShow=false;
            if(app.now=="login"){
                setTimeout('app.transShow=!app.transShow;app.panelHeight="height:540px";app.transOpacity="opacity:0.6";');
                setTimeout('app.now="reg";app.transOpacity="opacity:1"',210);
            }else{
                setTimeout('app.transShow=!app.transShow;app.panelHeight="height:400px";app.transOpacity="opacity:0.6";');
                setTimeout('app.now="login";app.transOpacity="opacity:1"',80);
            }
        },
        registerStudentAccount(){
            let regItem = document.getElementsByClassName("register-item");
            for(let time in regItem){
                if(regItem[time].value===""){
                    app.tips("请输入完整的注册信息哦!");
                    return;
                }
            };
            let fun=function(){
                app.loading();
                let params = new URLSearchParams();
                params.append("account",domClass("account").value);
                params.append("password",domClass("password").value);
                params.append("uid",domClass("numid").value);
                params.append("mail",domClass("email").value);
                params.append("phone",domClass("phone").value);
                axios.post('register/account',params).then(function (response){
                    let data = JSON.parse(JSON.stringify(response.data));
                    switch(data.serviceCode) {
                        case 2000:
                            app.tips("注册成功,现在登录吧!","棒极了!");
                            app.transReg();
                            break;
                        case 2001:
                            app.tips("该学号并没有并录入系统!");
                            break;
                        case 2002:
                            app.tips("不可以重复注册账号!");
                            break;
                        default:
                            app.tips("发生错误!请检是否输入正确!");
                    }
                });
            }
            setTimeout(fun,300);
        },
        tips(info,title){
            title=typeof(title)!=='undefined'?title:"哎呀!";
            info=typeof(info)!=='undefined'?info:"出错啦!";
            app.info=info;
            app.title=title;
            app.modelShow=true;
            app.backgroundShow=true;
            app.modelStyle="margin-top:-300px;";
            setTimeout(app.showModel,10);
//                setTimeout(app.hiddenModel,2100);
        },
        showModel(){
            app.loadingShow=false;
            app.modelShow=false;
            app.modelStyle="margin-top:300px;opacity:0.9;"
            setTimeout("app.modelShow=true;",10);
        },
        hiddenModel(){
            app.modelShow=false;
            app.modelStyle="margin-top:-300px;"
            app.backgroundShow=false;
        },
        findChange(){
            app.findButtonStyle="background-color:#3cf";
            domClass("findButton").disabled=false;
        },
        find(){
            if(domClass("login-item",2).value!=""&&domClass("login-item",3).value!=""){
                app.loading();
                let fun =function(){
                    axios.get('login/find.do',{params:{phone:domClass("login-item",2).value,email:domClass("login-item",3).value}}).then(function (response){
                        if(response.data!="no"){
                            app.now="alterPassword";
                            app.canAlterPass=response.data;
                            app.loadingShow=false;
                            app.tips("已发送验证码到您邮箱!","好的!")
                            return;
                        }else{
                            app.tips("请检查验证信息是否正确!");
                            return;
                        }
                    });
                };
                setTimeout(fun,300);
            }
            else app.tips("请输入完整的验证信息哦!");
        },
        alterPass(){
            app.loading();
            let fun =function(){
                    let params = new URLSearchParams();
                    params.append("password",domClass("newPassword").value);
                    params.append("account",app.canAlterPass);
                    axios.post('login/alterPassword.do',params).then(function (response){
                        if(response.data==="ok"){
                            app.tips("密码已经修改成功了!","好的!");
                            app.now="login";
                            return;
                        }else{
                            app.tips("发生错误!");
                            return;
                        }
                    });
                };
            setTimeout(fun,300);
        },
        appeal(){
            app.tips("xxx,我们会进行审查哦!");
        },
        loading(){
            app.loadingShow=!app.loadingShow;
        },
        switchBackground(){
            app.backgroundPic1 = !app.backgroundPic1;
            app.backgroundPic2 = !app.backgroundPic2;
            app.backgroundPic3 = !app.backgroundPic3;
            setTimeout("app.switchBackground();",3800);
        },
    }
});