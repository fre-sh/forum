// 问题（包含导航）
app.controller("questionController", function ($scope, $http, appService) {

    $scope.test = function () {
        alert("测试");
    };

    $scope.showEditData = function () {
        let i = localStorage.getItem('qId');
        if (i !== null) {
            appService.getQuestion(i).success(function (res) {
                $scope.entity = res.data;
            });
        }
    };

    $scope.delAll = function () {
        let ids = [];
        $("input + div.layui-form-checked").each(function () {
            ids.push(parseInt($(this).prev("input").attr('data-id')));
        });
        console.log(ids);
        appService.delQuestion(ids).success(function (res) {
            if (res.code === 0) {
                layer.msg("删除成功", {
                    icon: 6
                });
                $scope.loadPage();
            } else {
                layer.alert(res.msg);
            }
        });
    };

    $scope.delOne = function (id) {
        appService.delQuestion([id]).success(function (res) {
            if (res.code === 0) {
                layer.msg("删除成功", {
                    icon: 6
                });
                $scope.loadPage();
            } else {
                layer.alert(res.msg);
            }
        });
    };

    $scope.showAddPage = function () {
        localStorage.removeItem('qId');
        let w = '700px', h = '500px';
        var index = layer.open({
            type: 2,
            title: '问题添加',
            area: [w, h],
            fixed: false, //不固定
            content: 'question-add.html',
            end: function () {
                $scope.loadPage();
            }
        });
    };

    $scope.showEditPage = function (id) {
        localStorage.setItem("qId", id);
        let w = '700px', h = '500px';
        var index = layer.open({
            type: 2,
            title: '问题编辑',
            area: [w, h],
            fixed: false, //不固定
            content: 'question-add.html',
            end: function () {
                $scope.loadPage();
            }
        });
    };

    $scope.add = function () {
        if ($scope.entity == null){
            return
        }
        appService.addQuestion($scope.entity).success(function (res) {
            if (res.code === 0) {
                var index = parent.layer.getFrameIndex(window.name); /* 先得到当前iframe层的索引 */
                // parent.layui.table.reload('educationReload',{page:{curr:1}}); //主页代码，第一个参数为： 父页面的表格属性 id名
                parent.layer.close(index); //成功再执行关闭
                parent.layer.msg("添加成功", {
                    icon: 6
                });
                // window.frames[iframeObjName].location.reload();
            } else {
                layer.alert(res.msg);
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
            // layui.form().render('checkbox');
        });
    };


});