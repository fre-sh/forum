// 首页（包含导航）
app.controller("indexController", function ($scope, appService) {

    // 显示用户
    appService.getCurUser().success(function (res) {
        $scope.user = res.data;
    });

    layui.use(['form', 'dialog'], function () {
        let form = layui.form();
        form.render();
        // 监听添加按钮
    });

    $scope.save = function () {
        appService.saveUser($scope.user).success(function (res) {
            if (res.code === 0) {
                layer.msg("修改成功", {
                    icon: 6
                });
                $scope.loadPage();
            } else {
                layer.alert(res.msg);
            }
        });
    };

    $scope.updatePass = function () {
        if ($scope.newPass !== $scope.newPass2) {
            layer.alert('两次新密码输入不一致');
            return;
        }
        let to = {
            id: $scope.user.id,
            oldPass: $scope.oldPass,
            newPass: $scope.newPass
        };
        appService.updatePass(to).success(function (res) {
            if (res.code === 0) {
                layer.msg("修改成功", {
                    icon: 6
                });
                $scope.loadPage();
            } else {
                layer.alert(res.msg);
            }
        });
    }

});