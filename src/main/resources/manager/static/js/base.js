const app = angular.module("admin", []);
const appService = app.service("appService", function ($http) {
    this.listQuestion = function (query) {
        return $http.post('/admin/question/list', query);
    }
});