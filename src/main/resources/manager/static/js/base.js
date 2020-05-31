const app = angular.module("admin", ["pagination"]);
const appService = app.service("appService", function ($http) {

    /**
     * 用户管理
     * @param id
     * @returns {*}
     */
    this.getUser = function (id) {
        return $http.get('/admin/user/' + id);
    };
    this.delUser = function (ids) {
        return $http.post('/admin/user/delAll', ids);
    };
    this.addUser = function (entity) {
        return $http.post('/admin/user/save', entity);
    };
    this.listUser = function (query) {
        return $http.post('/admin/user/list', query);
    };

    /**
     * 问题管理
     * @param id
     * @returns {*}
     */
    this.getQuestion = function (id) {
        return $http.get('/admin/question/' + id);
    };
    this.delQuestion = function (ids) {
        return $http.post('/admin/question/delAll', ids);
    };
    this.addQuestion = function (entity) {
        return $http.post('/admin/question/add', entity);
    };
    this.listQuestion = function (query) {
        return $http.post('/admin/question/list', query);
    }
});