const app = angular.module("admin", ["pagination"]);
const appService = app.service("appService", function ($http) {

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