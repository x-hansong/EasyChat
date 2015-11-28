var weChat = angular.module('weChat', ['ui.router', 'ngAnimate','registerCtrls']);
weChat.config(['$stateProvider','$urlRouterProvider',function($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise("/login");
    $stateProvider
        .state('login', {
            url: "/login",
            templateUrl: 'tpls/login.html'
        })
        .state('main',{
            url:"/main",
            templateUrl:'tpls/main.html'
        });
}]);
