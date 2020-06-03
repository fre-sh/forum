// 内容管理
app.controller("contentController", function ($scope, $http, appService) {

    /**
     * 添加页面初始化
     */
    $scope.initArticle = function () {
        $scope.entity = {id:null, contentType:'article'};
        // $scope.refreshSelect();
        $scope.showEditData();
    };

    $scope.initAnswer = function () {
        $scope.entity = {id:null, contentType:'answer'};
        // $scope.refreshSelect();
        $scope.showEditData();
    };

    /**
     * 列表页面初始化
     */
    $scope.initArticleList = function () {
        $scope.query = {contentType:'article'};
        del_msg += '文章';
    };
    $scope.initAnswerList = function () {
        $scope.query = {contentType:'answer'};
        del_msg += '回答';
    };

// 初始化
    let editor;
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
        var E = window.wangEditor;
        editor = new E('#editor');
        editor.customConfig.uploadImgShowBase64 = true;
        // 或者 var editor = new E( document.getElementById('editor') )
        editor.create()
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
            appService.getContent(i).success(function (res) {
                $scope.entity = res.data;
                $('#editor').html($scope.entity.content);
            });
        }
    };

    let del_msg = '确定要删除';
    $scope.delAll = function () {
        layui.dialog.confirm({
            message: del_msg,
            success: function () {
                let ids = [];
                $("input + div.layui-form-checked").each(function () {
                    ids.push(parseInt($(this).prev("input").attr('data-id')));
                });
                console.log(ids);
                appService.delContent(ids).success(function (res) {
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
                appService.delContent([id]).success(function (res) {
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
        window.location.href="/admin/index/article-detail.html";
    };

    $scope.showAddAnswerPage = function () {
        localStorage.removeItem('qId');
        window.location.href="/admin/index/answer-detail.html";
    };

    $scope.showEditPage = function (id) {
        localStorage.setItem("qId", id);
        window.location.href="/admin/index/article-detail.html?id="+id;
    };

    $scope.showEditAnswerPage = function (id) {
        localStorage.setItem("qId", id);
        window.location.href="/admin/index/answer-detail.html?id="+id;
    };

    $scope.save = function () {
        if ($scope.entity == null){
            return
        }
        $scope.entity.content = editor.txt.html();
        appService.saveContent($scope.entity).success(function (res) {
            if (res.code === 0) {
                var index = parent.layer.getFrameIndex(window.name); /* 先得到当前iframe层的索引 */
                parent.layer.close(index); //成功再执行关闭
                parent.layer.msg($scope.entity.id === null ? "添加成功" : "修改成功", {
                    icon: 6
                });
                location.href = '/admin/index/article-list.html';
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
        appService.listContent($scope.query).success(function (res) {
            $scope.entityList = res.data.content;
            $scope.pageConf.totalItems = res.data.totalElements;
            // layui.form.render();
        });
    };


});