function initial() {
    getDynamicData();
    //getStaticData();
}

// 从后端获取动态数据
function getDynamicData() {
    var thenFunction = function(response) {
        var data = JSON.parse(JSON.stringify(response.data))
        var questionData = data.result;
        formatQuestionData(questionData);
        app.questionData = questionData;
    }
    toPost("question", {}, thenFunction);
}

// 格式化问题数据
function formatQuestionData(questionData) {
    for (index in questionData) {
        element = questionData[index];
        addDefaultValueToSection(element.section);
        regulateInputStyle(element.extra);
        app.questionResult.push([]);
        questionData[index].bindChooseResult = -1;
     }
}

// 自动调整前端额外输入框大小
function regulateInputStyle(extra) {
    if (typeof(extra) !== 'undefined') {
        var plength = extra.extPlaceholder.length;
        var dlength = extra.extDefaultValue.length;
        plength = plength > dlength ? plength : dlength;
        plength = (16*plength);
        extra.extFrontStyle = "width:"+plength+"px";
    }
}

// 给前端选择框添加默认值
function addDefaultValueToSection(section) {
    if (typeof(section) !== 'undefined') {
        for (sectionEle in section) {
            var element = section[sectionEle];
            element.bindValue = element.selectOption[0];
        }
    }
}

// 获取静态演示用数据
function getStaticData() {
    var questionData = [
        {
            "qid": "q001",
            "tips": "情根据您的个人习惯进行选择哦!",
            "introduction": "我的学习方式?",
            "questionType": 1,
            "displayType": 0,
            "bindChooseResult": -1,
            "chooseType": 1,
            "option": ["独自学习", "共同学习", "都可以"],
            "extra":{
                "extIntro": "",
                "extFrontStyle": "",
                "extPlaceholder": "",
                "extDefaultValue": "",
                "extAddButtonValue": ""
            },
        },{
            "qid": "q002",
            "tips": "可以选择优先和老乡一个寝室!",
            "introduction": "我们来自哪里?",
            "questionType": 1,
            "displayType": 1,
            "chooseType": 1,
            "bindChooseResult": -1,
            "extra":{
                "extIntro": "我来自",
                "extFrontStyle": "",
                "extPlaceholder": "格式: xxx省/xxx市",
                "extDefaultValue": "辽宁省/沈阳市",
                "extBindValue": "辽宁省/沈阳市",
                "extAddButtonValue": "",
            },
            "option": ["同样的城市", "不同的城市", "都可以"],
        },{
            "qid": "q003",
            "tips": "我们会为您分配作息时间相似的同学!",
            "introduction": "我们的就寝时间?",
            "questionType": 3,
            "displayType": 2,
            "extra":{
                "extIntro": "",
                "extFrontStyle": "",
                "extPlaceholder": "",
                "extDefaultValue": "",
            },
            "section": [
                {
                    "selectIntro": "熄灯",
                    "selectOption": ["20:00", "21:00"],
                    "bindValue":"",
                },{
                    "selectIntro": "起床",
                    "selectOption": ["05:00", "06:00"],
                    "bindValue":"",
                }
            ],"chooseType": 0,
            "option": []
        },{
            "qid": "q004",
            "tips": "大家一起玩才有趣!",
            "introduction": "我的爱好有哪些?",
            "questionType": 2,
            "displayType": 3,
            "option": ["篮球","羽毛球"],
            "chooseType": 2,
            "extra":{
                "extIntro": "其他爱好",
                "extFrontStyle": "",
                "extPlaceholder": "爱好",
                "extDefaultValue": "",
                "extBindValue": "",
                "extAddButtonValue": "添加爱好",
            }
        },
        {
            "qid": "q005",
            "tips": "这是大家想要的寝室的模样!",
            "introduction": "你希望寝室是什么样子的?",
            "questionType": 2,
            "displayType": 2,
            "option": [
                "热闹","安静","整洁明亮","归属感"
            ],"chooseType": 2,
            "extra":{
                "extIntro": "",
                "extFrontStyle": "",
                "extPlaceholder": "",
                "extDefaultValue": "",
                "extBindValue": "",
                "extAddButtonValue": "添加爱好",

            }
        },
        {
            "qid": "q006",
            "tips": "",
            "introduction": "你不希望寝室是什么样子的?",
            "questionType": 2,
            "displayType": 6,
            "option": [
                "吵闹", "冷清", "杂乱", "异味"
            ],"chooseType": 2,
            "extra":{
                "extIntro": "其他爱好",
                "extFrontStyle": "",
                "extPlaceholder": "爱好",
                "extDefaultValue": "",
                "extAddButtonValue": "添加爱好",
            }
        },
    ]
    formatQuestionData(questionData);
    app.questionData = questionData;
}

window.onload = function() {
    initial();
}
