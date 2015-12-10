var weChat = angular.module('weChat', ['ui.router', 'ngAnimate','registerCtrls','mainCtrls','weChatDirectives']);
weChat.config(['$stateProvider','$urlRouterProvider',function($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise("/login");
    $stateProvider
        .state('login', {
            url: "/login",
            templateUrl: 'tpls/login.html'
        })
        .state('main',{
            url:"/main",
            templateUrl: 'tpls/main.html'
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
            url:"/main",
            views: {
                'rightview@main': {
                    templateUrl: 'tpls/setting.html'
                }
            }
        })
        .state('main.chatpage',{
            url:"/main",
            views: {
                'rightview@main': {
                    templateUrl: 'tpls/chatpage.html',
                    controller: function($scope, $state) {
                        $scope.chat = function(e) {
                            var name = $(e).children(".chatmessage-1").children(".chatmessage-1-info").children("h4").text();
                            alert(name);
                            $(".main-right .main-right-nav h2").text(name);
                        }
                    }
                }
            }
        });
}]);