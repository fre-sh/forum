const app = angular.module("admin", ["pagination"]);
const appService = app.service("appService", function ($http) {

    /**
     * 消息管理
     * @param id
     * @returns {*}
     */
    this.getMessage = function (id) {
        return $http.get('/admin/message/' + id);
    };
    this.delMessage = function (ids) {
        return $http.post('/admin/message/delAll', ids);
    };
    this.saveMessage = function (entity) {
        return $http.post('/admin/message/save', entity);
    };
    this.listMessage = function (query) {
        return $http.post('/admin/message/list', query);
    };

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
    this.getCurUser = function () {
        return $http.get('/admin/user/cur');
    };
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
    this.allUser = function (query) {
        return $http.post('/admin/user/all', query);
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


//inputTime 参数是毫秒级时间戳
function formatDate(inputTime) {
    var date = new Date(inputTime);
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    m = m < 10 ? ('0' + m) : m;
    var d = date.getDate();
    d = d < 10 ? ('0' + d) : d;
    var h = date.getHours();
    h = h < 10 ? ('0' + h) : h;
    var minute = date.getMinutes();
    var second = date.getSeconds();
    minute = minute < 10 ? ('0' + minute) : minute;
    second = second < 10 ? ('0' + second) : second;
    return y + '-' + m + '-' + d + ' ' + h + ':' + minute + ':' + second;
}