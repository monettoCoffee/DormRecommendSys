var app = new Vue({
    el: ".questionContainer",
    data: {
        // 可选择的与选择数量的对应关系
        questionChoseType: {
            1: "单选",
            2: "最多选择两项",
            3: "最多选择三项",
            0: "多选",
        },
        // 页面上显示的选择结果
        questionResult:[],
        // 结果强调级别
        questionResultStressLevel: {
            0: "选择",
            1: "强调",
            2: "再次强调"
        },
        // 问题的数据
        questionData: [],
        // 模态框的基本属性
        modelData: {
            info: "info",
            title: "title",
            modelShow: false,
            backgroundShow: false,
            modelStyle: "",
        },
    },
    methods: {
        // select标签选择区间添加
        addSectionButton: function(questionIndex, chooseType) {
            if (!app.canAddToResult(questionIndex, chooseType)) {
                app.showTips("填写数量过多", "已经达到答案上限了!");
                return;
            }
            var arr = app.questionData[questionIndex].section;
            var addResult = "";
            for (var index in arr) {
                addResult = addResult + arr[index].bindValue + " - ";
            }
            addResult = addResult.slice(0, addResult.length-3)
            if (strInArray(addResult, app.questionResult[questionIndex])) {
                app.showTips("重复答案", "已经添加这个选项了!");
                return;
            }
            app.questionResult[questionIndex].push({stress: 0, "word": addResult});
        },
        // 自定义答案输入添加
        addCustomAnswerButton: function(questionIndex, chooseType) {
            if (!app.canAddToResult(questionIndex, chooseType)) {
                app.showTips("填写数量过多", "已经达到答案上限了!");
                return;
            }
            var customValue = app.questionData[questionIndex].extra.extBindValue;
            if (typeof(customValue) == 'undefined' || customValue.length == 0) {
                app.showTips("填写错误", "填写不可以为空");
                return;
            }
            var arr = app.questionResult[questionIndex];
            if (strInArray(customValue, arr)) {
                app.showTips("重复答案", "已经添加这个选项了!");
                return;
            }
            arr.push({"stress": 0, "word": customValue});
        },
        // 多选按钮选择添加
        addResult: function(questionIndex, resultIndex, chooseType) {
            if (!app.canAddToResult(questionIndex, chooseType)) {
                app.showTips("填写数量过多", "已经达到答案上限了!");
                return;
            }
            var arr = app.questionResult[questionIndex];
            var option = app.questionData[questionIndex].option[resultIndex];
            if (strInArray(option, arr)) {
                app.showTips("重复答案", "已经添加这个选项了!");
                return;
            }
            arr.push({"stress": 0, "word": option});
        },
        // 判断是否可以添加进结果中
        canAddToResult: function(questionIndex, chooseType) {
            var chosenLength = app.questionResult[questionIndex].length;
            return (chooseType == 0 || chosenLength < chooseType);
        },
        // 强调选择选项
        stressChosen: function(questionIndex, chosenResultIndex) {
            var questionElement = app.questionData[questionIndex]
            if ((questionElement.displayType&4)==0) {
                return;
            }
            var chosenElement = app.questionResult[questionIndex][chosenResultIndex];
            chosenElement.stress++;
            if (chosenElement.stress == 3) {
                chosenElement.stress = 0;
            }
        },
        // 删除选择结果
        removeResult: function(questionIndex, resultIndex) {
            var arr = app.questionResult[questionIndex];
            arr.splice(resultIndex, 1);
        },
        // 显示自定义模态框
        showTips: function(title, info) {
            title = typeof(title) !== 'undefined' ? title : "哎呀!";
            info = typeof(info) !== 'undefined' ? info : "出错啦!";
            app.modelData.info = info;
            app.modelData.title = title;
            app.modelData.modelShow = true;
            app.modelData.backgroundShow = true;
            app.modelData.modelStyle = "margin-top:-10%;";
            setTimeout(app.showModel, 10);
        },
        // 模态框控制函数
        showModel: function() {
            app.modelData.loadingShow = false;
            app.modelData.modelShow = false;
            app.modelData.modelStyle = "margin-top:10%;"
            setTimeout("app.modelData.modelShow=true;", 10);
        },
        // 模态框控制函数
        hiddenModel: function() {
            app.modelData.modelShow = false;
            app.modelData.modelStyle = "margin-top:-10%;"
            app.modelData.backgroundShow = false;
        },
        // 将页面答案转化为数据函数
        postAllAnswer: function() {
            var data = [];
            var questionArr = app.questionData;
            for (var index in questionArr) {
                var question = questionArr[index];
                data.push({"qid": question.qid});
                // 获取单选类型答案
                if (question.questionType == 1) {
                    if (question.bindChooseResult == -1) {
                        var info = '第' + (Number(index)+1) + "题没有填写选项"
                        app.showTips("请填写完整", info);
                        return;
                    } else {
                        data[index].radio = question.bindChooseResult;
                    }
                }
                // 获取附加答案
                if ( (question.displayType&1) != 0) {
                    var extBindValue = question.extra.extBindValue;
                    if ( (typeof(extBindValue)=='undefined' || extBindValue == '') && app.questionResult[index].length == 0) {
                        var info = '第' + (Number(index)+1) + "题没有填写答案";
                        app.showTips("请填写完整", info);
                        return;
                    } else {
                        data[index].extAnswer = extBindValue;
                    }
                }
                // 获取具有选项卡的答案
                if ( (question.displayType&2) != 0) {
                    var result = app.questionResult[index];
                    if (result.length == 0) {
                        var info = '第' + (Number(index)+1) + "题没有选择选项";
                        app.showTips("请填写完整", info);
                        return;
                    } else {
                        data[index].result = result;
                    }
                }
            }
            var params = {"chosen": data};
            then = function(response) {
                var data = JSON.parse(JSON.stringify(response.data))
                if (data.serviceCode == 200) {
                    app.showTips("好的", "您已成功完成填写!");
                } else if (data.serviceCode == 201) {
                    app.showTips("错误", "数据校验有误!");
                } else {
                    app.showTips("错误", "网络错误!");
                }
            }
            alert(JSON.stringify(params))
            toPost("chosen", params, then)
;        }

    }
})

