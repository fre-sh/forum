// 用户管理
app.controller("userController", function ($scope, $http, appService) {
    // 初始化
    layui.use('form', function () {
        let form = layui.form();
        form.render();
        form.on('submit(addUser)', function (data) {
            $scope.add();
            return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
        });
    });

    $scope.test = function () {
        alert("测试");
    };

    $scope.showEditData = function () {
        let i = localStorage.getItem('qId');
        if (i !== null) {
            appService.getUser(i).success(function (res) {
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
        appService.delUser(ids).success(function (res) {
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
        appService.delUser([id]).success(function (res) {
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
            title: '用户添加',
            area: [w, h],
            fixed: false, //不固定
            content: 'user-add.html',
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
            title: '用户编辑',
            area: [w, h],
            fixed: false, //不固定
            content: 'user-add.html',
            end: function () {
                $scope.loadPage();
            }
        });
    };

    $scope.add = function () {
        if ($scope.entity == null){
            return
        }
        appService.addUser($scope.entity).success(function (res) {
            if (res.code === 0) {
                var index = parent.layer.getFrameIndex(window.name); /* 先得到当前iframe层的索引 */
                parent.layer.close(index); //成功再执行关闭
                parent.layer.msg("添加成功", {
                    icon: 6
                });
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
        appService.listUser($scope.query).success(function (res) {
            $scope.entityList = res.data.content;
            $scope.pageConf.totalItems = res.data.totalElements;
        });
    };


});