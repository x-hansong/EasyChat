var weChat = angular.module('weChat', ['ui.router', 'ngAnimate','registerCtrls','mainCtrls','weChatDirectives']);
weChat.config(['$stateProvider','$urlRouterProvider',function($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise("/login");
    $stateProvider
        .state('login', {
            url: "/login",
            templateUrl: 'tpls/login.html'
        })
        .state('main', {
            url: "/main",
            views: {
                '':{
                    templateUrl: 'tpls/main.html'
                },
                'leftview@main': {
                    templateUrl: 'tpls/chatlist.html'
                }
            }
        })
        .state('main.friend',{
            url:"/friend",
            views: {
                'rightview@main': {
                    
                    templateUrl: 'tpls/friend.html'
                }
            }
        })
        .state('main.setting',{
            url:"/setting",
            views: {
                'rightview@main': {
                    templateUrl: 'tpls/setting.html'
                }
            }
        })
        .state('main.chatpage',{
            url:"/chatpage",
            views: {
                'rightview@main': {
                    templateUrl: 'tpls/chatpage.html'
                }
            }
        })
        .state('main.friendlist',{
            url:"/friendlist",
            views: {
                'leftview@main': {
                    templateUrl: 'tpls/friendlist.html'
                }
            }
        })
        .state('main.chatlist',{
            url:"/chatlist",
            views: {
                'leftview@main': {
                    templateUrl: 'tpls/chatlist.html'
                }
            }
        });
}]);