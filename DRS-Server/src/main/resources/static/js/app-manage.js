var app = new Vue({
    el: ".manageContainer",
    data: {
        // 模态框的基本属性
        modelData: {
            info: "info",
            title: "title",
            modelShow: false,
            backgroundShow: false,
            modelStyle: "",
        },
        // 添加问卷问题
        addQuestionType: "",
        addQuestionHasExtraAnswer: "",
        addQuestionTitle: "",
        addQuestionTips: "",
        addQuestionMultiChosenNumber: "",
        addQuestionChosenInput:"",
        addQuestionChosenGroup: ["123aa"],
        addQuestionUpSection: ["upup"],
        addQuestionUpSectionInput:"",
        addQuestionDownSectionInput: "",
        addQuestionDownSection: ["down"],
        addQuestionChosenStress: "",
        addQuestionAutoWeightRatio: "",
        addQuestionDefaultWeight: "0.5",
        addQuestionUpSelectIntro: "u",
        addQuestionDownSelectIntro: "e",
        addQuestionExtraIntro: "ei",
        addQuestionExtraPlaceholder: "eph",
        addQuestionExtraDefaultValue: "eph",
        addQuestionExtraAddButtonValue: "eav",
        // 学生添加管理
        addStudentName: "ads",
        addStudentUid: "dsds",
        addStudentSex: "",
        studentInfoList: [
            {"name": "小明1", "uid":"1233", "userType":0, "sex":0, "planName": "计划1"},
            {"name": "小明2", "uid":"1231", "userType":1, "sex":1, "planName": ""},
        ],
        // 计划添加管理
        addPlanShow: true,
        addPlanName: "addPlanName",
        addPlanStudentStatusList: [

        ],
        // 添加问题到计划
        planQuestionPlanCurrentIndex: 0,
        planQuestionAddQuestionQid: "",
        planQuestionQuestionInfoList: [
            {"qid": 1, "introduction":"我的学习1"},
            {"qid": 2, "introduction":"我的学习2"}
        ],
        planQuestionPlanInfoList: [
            {"planName": "计划1", "pid": 1, "qid": [1,2,3]},
            {"planName": "计划2", "pid": 2, "qid": [1]},
        ],


    },
    methods: {
        // 添加问卷问题选项
        addQuestionChosenToGroup: function() {
            if (app.addQuestionChosenInput == "") {
                return;
            }
            for (chosen in app.addQuestionChosenGroup) {
                if (app.addQuestionChosenGroup[chosen] == app.addQuestionChosenInput){
                    alert("has add!");
                    return;
                }
            }
            app.addQuestionChosenGroup.push(app.addQuestionChosenInput);
            app.addQuestionChosenInput = "";
        },
        // 移除问卷问题选项
        removeQuestionChosenFromGroup: function(groupName, elementId){
            app[groupName].splice(elementId, 1);
        },
        // 添加问卷问题上区间选项范围 (可以与选择类型优化为一个函数)
        addQuestionUpSectionFunction: function() {
            if (app.addQuestionUpSectionInput == "") {
                return;
            }
            for (chosen in app.addQuestionUpSection) {
                if (app.addQuestionUpSection[chosen] == app.addQuestionUpSectionInput){
                    alert("has add!");
                    return;
                }
            }
            app.addQuestionUpSection.push(app.addQuestionUpSectionInput);
            app.addQuestionUpSectionInput = "";
        },
        // 添加问卷问题下区间选项范围 (可以与选择类型优化为一个函数)
        addQuestionDownSectionFunction: function() {
            if (app.addQuestionDownSectionInput == "") {
                return;
            }
            for (chosen in app.addQuestionDownSection) {
                if (app.addQuestionDownSection[chosen] == app.addQuestionDownSectionInput){
                    alert("has add!");
                    return;
                }
            }
            app.addQuestionDownSection.push(app.addQuestionDownSectionInput);
            app.addQuestionDownSectionInput = "";
        },
        // 添加问题到数据库
        addQuestionToDatabase: function() {
            var addQuestionAO = {
                addQuestionType: app.addQuestionType,
                addQuestionHasExtraAnswer: app.addQuestionHasExtraAnswer,
                addQuestionTitle: app.addQuestionTitle,
                addQuestionTips: app.addQuestionTips,
                addQuestionMultiChosenNumber: app.addQuestionMultiChosenNumber,
                addQuestionChosenGroup: app.addQuestionChosenGroup,
                addQuestionUpSection: app.addQuestionUpSection,
                addQuestionDownSection: app.addQuestionDownSection,
                addQuestionChosenStress: app.addQuestionChosenStress,
                addQuestionAutoWeightRatio: app.addQuestionAutoWeightRatio,
                addQuestionDefaultWeight: app.addQuestionDefaultWeight,
                addQuestionUpSelectIntro: app.addQuestionUpSelectIntro,
                addQuestionDownSelectIntro: app.addQuestionDownSelectIntro,
                addQuestionExtraIntro: app.addQuestionExtraIntro,
                addQuestionExtraPlaceholder: app.addQuestionExtraPlaceholder,
                addQuestionExtraDefaultValue: app.addQuestionExtraDefaultValue,
                addQuestionExtraAddButtonValue: app.addQuestionExtraAddButtonValue,
            }
            then = function(response) {
                var data = JSON.parse(JSON.stringify(response.data))
                location.reload();
            }
            var params = {"paramJson1": addQuestionAO}
            alert(JSON.stringify(params))
            toPost("add/question", params, then)
        },
        // 新增学生
        addStudentToDatabase: function() {
            then = function(response) {
                var data = JSON.parse(JSON.stringify(response.data))
                alert(data);
                location.reload();
            }
            var studentRegisterInfo = {
                "name": app.addStudentName,
                "uid": app.addStudentUid,
                "sex": app.addStudentSex,
            }
            alert(JSON.stringify(studentRegisterInfo))
            toPost("register/info", {"regInfoJson":studentRegisterInfo}, then)
        },
        // 删除学生
        deleteStudentFromDatabase: function(uid) {
            then = function(response) {
                var data = JSON.parse(JSON.stringify(response.data))
                alert(data);
                location.reload();
            }
            var studentRegisterInfo = {
                "uid": uid,
            }
            toPost("delete/account", {"delInfo":studentRegisterInfo}, then);
        },
        // 参加寝室分配计划
        addStudentToPlan: function(uid, sIndex) {
            app.addPlanShow = false;
            app.studentInfoList[sIndex]["planName"] = app.addPlanName;
            app.addPlanShow = true;
            app.addPlanStudentStatusList.push({
                "uid": app.studentInfoList[sIndex]["uid"],
                "planName":app.studentInfoList[sIndex]["planName"],
            })
        },
        // 从计划中删除学生
        deleteStudentFromPlan: function(uid, sIndex) {
            app.studentInfoList[sIndex]["planName"] = "";
            for (element in app.addPlanStudentStatusList) {
                if (app.addPlanStudentStatusList[element]["uid"] == app.studentInfoList[sIndex]["uid"]) {
                    app.addPlanStudentStatusList.splice(element, 1);
                }
            }
            app.addPlanStudentStatusList.push({
                "uid": app.studentInfoList[sIndex]["uid"],
                "planName": "",
            })
            alert(JSON.stringify(app.addPlanStudentStatusList))
        },
        // 添加分配计划
        addPlanToDatabase: function() {
            alert(1);
            alert(JSON.stringify(app.addPlanStudentStatusList))
            toPost("add/plan/student", {"studentPlanJson": app.addPlanStudentStatusList});
        },
        // 添加问题到计划
        questionPlanAddQuestionToPlan: function() {
            app.planQuestionPlanInfoList[app.planQuestionPlanCurrentIndex]["qid"].push(app.planQuestionAddQuestionQid);
        },
        // 删除计划中的问题
        questionPlanDeleleQuestionFromPlan: function(qIndex) {
            app.planQuestionPlanInfoList[app.planQuestionPlanCurrentIndex]["qid"].splice(qIndex, 1);
        },
        // 确定计划问题
        questionPlanConfirmPlan: function() {
            alert(JSON.stringify(app.planQuestionPlanInfoList));
            toPost("add/plan/confirm", {"questionPlanJson": app.planQuestionPlanInfoList});
        }
    }
});