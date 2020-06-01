// 首页（包含导航）
app.controller("indexController", function ($scope, appService) {

    // 显示用户
    appService.getCurUser().success(function (res) {
        $scope.user = res.data;
    });

});