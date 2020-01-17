function initial(){
    var thenFunction = function(response) {
        var data = JSON.parse(JSON.stringify(response.data))
        app.studentInfoList = data.result;
        app.addPlanStudentStatus = [];
        for (var element in data.result) {
            app.addPlanStudentStatus.push(data.result[element]["planName"]);
        }
        // alert(JSON.stringify())
    }
    toPost("select/account", {}, thenFunction);
    var thenFunction2 = function(response) {
        var data = JSON.parse(JSON.stringify(response.data))
        app.planQuestionPlanInfoList = data.result
    }
    toPost("plan/info", {}, thenFunction2);
    var thenFunction3 = function(response) {
        var data = JSON.parse(JSON.stringify(response.data))
        app.planQuestionQuestionInfoList = data.result
    }
    toPost("question/introduction", {}, thenFunction3);
}

window.onload = function() {
    initial();
}