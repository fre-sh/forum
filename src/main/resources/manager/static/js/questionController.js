// 问题（包含导航）
app.controller("questionController", function ($scope, $http, appService) {
    $scope.query = {
        kw:null
    };

    $scope.pageConf = {
        currentPage: 1,
        totalItems: 10,
        itemsPerPage: 10,
        onChange: function(){
            $scope.loadPage();//重新加载
        }
    };

    $scope.loadPage = function () {
        $scope.query.curPage = $scope.pageConf.currentPage;
        $scope.query.pageSize = $scope.pageConf.itemsPerPage;
        appService.listQuestion($scope.query).success(function (res) {
            // let i = 1;
            $scope.questionList = res.data.content;
            $scope.pageConf.totalItems = res.data.totalElements;
        });
    };


});