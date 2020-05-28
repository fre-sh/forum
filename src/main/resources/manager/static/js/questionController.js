// 问题（包含导航）
app.controller("questionController", function ($scope, $http, appService) {

    $scope.test = function () {
        alert("测试");
    };

    $scope.add = function () {
        if ($scope.entity == null){
            return
        }
        appService.addQuestion($scope.entity).success(function (res) {
            if (res.code !== 0) {
                layer.alert(res.msg);
                // parent.layer.warn(res.msg);
                return;
            }
            var index = parent.layer.getFrameIndex(window.name); /* 先得到当前iframe层的索引 */
            if (res.code === 0) {
                // parent.layui.reload(); //主页代码，第一个参数为： 父页面的表格属性 id名
                // parent.layui.table.reload('educationReload',{page:{curr:1}}); //主页代码，第一个参数为： 父页面的表格属性 id名
                parent.layer.close(index); //成功再执行关闭
                parent.layer.msg("添加成功", {
                    icon: 6
                });
                // parent.layer.location.reload();
                window.frames[iframeObjName].location.reload();
            }
        });
    };

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