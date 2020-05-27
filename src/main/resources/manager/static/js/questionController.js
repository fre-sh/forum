// 问题（包含导航）
app.controller("questionController", function ($scope, $http, appService) {
    $scope.questionListInit = function () {
        $scope.query = {
            kw:null
        };
        appService.listQuestion($scope.query).success(function (res) {
            $scope.questionList = res.data;
        });
    };
});