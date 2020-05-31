// 评论管理
app.controller("commentController", function ($scope, $http, appService) {

    $scope.init = function () {
        $scope.query = {};
        $scope.entity = {entity:{id:null}};
        $scope.refreshSelect();
        $scope.showEditData();
    };

// 初始化
    layui.use(['form', 'dialog'], function () {
        let form = layui.form();
        form.render();
        // 监听添加按钮
        form.on('submit(commit)', function (data) {
            $scope.save();
            return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
        });
        // 监听添加的下拉框
        form.on('select(select)', function(data){
            // 搜索刷新select
            $scope.entity.entity.id = parseInt(data.value);
        });
        // 监听搜索下拉框
        form.on('select(status)', function(data){
            let value = data.value;
            $scope.query.contentType = value !== "null" ? value : null;
            $scope.loadPage();
        });
    });

    $scope.refreshSelect = function () {
        appService.allContent($scope.query).success(function (res) {
            if (res.code === 0) {
                $scope.contents = res.data;
            } else {
                layer.alert(res.msg);
            }
        });
    };

    $scope.showEditData = function () {
        let i = localStorage.getItem('qId');
        if (i !== null) {
            appService.getComment(i).success(function (res) {
                $scope.entity = res.data;
            });
        }
    };

    const del_msg = '确定要删除评论';
    $scope.delAll = function () {
        layui.dialog.confirm({
            message: del_msg,
            success: function () {
                let ids = [];
                $("input + div.layui-form-checked").each(function () {
                    ids.push(parseInt($(this).prev("input").attr('data-id')));
                });
                console.log(ids);
                appService.delComment(ids).success(function (res) {
                    if (res.code === 0) {
                        layer.msg("删除成功", {
                            icon: 6
                        });
                        $scope.loadPage();
                    } else {
                        layer.alert(res.msg);
                    }
                });
            }
        });
    };

    $scope.delOne = function (id) {
        layui.dialog.confirm({
            message: del_msg,
            success: function () {
                appService.delComment([id]).success(function (res) {
                    if (res.code === 0) {
                        layer.msg("删除成功", {
                            icon: 6
                        });
                        $scope.loadPage();
                    } else {
                        layer.alert(res.msg);
                    }
                });
            }
        });
    };

    $scope.showAddPage = function () {
        localStorage.removeItem('qId');
        let w = '700px', h = '500px';
        var index = layer.open({
            type: 2,
            title: '评论添加',
            area: [w, h],
            fixed: false, //不固定
            content: 'comment-add.html',
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
            title: '评论编辑',
            area: [w, h],
            fixed: false, //不固定
            content: 'comment-add.html',
            end: function () {
                $scope.loadPage();
            }
        });
    };

    $scope.save = function () {
        if ($scope.entity == null){
            return
        }
        appService.saveComment($scope.entity).success(function (res) {
            if (res.code === 0) {
                var index = parent.layer.getFrameIndex(window.name); /* 先得到当前iframe层的索引 */
                parent.layer.close(index); //成功再执行关闭
                parent.layer.msg($scope.entity.id === null ? "添加成功" : "修改成功", {
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
        appService.listComment($scope.query).success(function (res) {
            $scope.entityList = res.data.content;
            $scope.pageConf.totalItems = res.data.totalElements;
            // layui.form.render();
        });
    };


});