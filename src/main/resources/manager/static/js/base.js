const app = angular.module("admin", ["pagination"]);
const appService = app.service("appService", function ($http) {

    /**
     * 内容管理
     */
    this.getContent = function (id) {
        return $http.get('/admin/content/' + id);
    };
    this.delContent = function (ids) {
        return $http.post('/admin/content/delAll', ids);
    };
    this.saveContent = function (entity) {
        return $http.post('/admin/content/save', entity);
    };
    this.listContent = function (query) {
        return $http.post('/admin/content/list', query);
    };
    this.allContent = function (query) {
        return $http.post('/admin/content/all', query);
    };

    /**
     * 评论管理
     * @param id
     * @returns {*}
     */
    this.getComment = function (id) {
        return $http.get('/admin/comment/' + id);
    };
    this.delComment = function (ids) {
        return $http.post('/admin/comment/delAll', ids);
    };
    this.saveComment = function (entity) {
        return $http.post('/admin/comment/save', entity);
    };
    this.listComment = function (query) {
        return $http.post('/admin/comment/list', query);
    };

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
    this.saveUser = function (entity) {
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