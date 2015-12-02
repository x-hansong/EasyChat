/**
 * Created by Administrator on 2015/10/30.
 */
var weChatDirectives = angular.module('weChatDirectives', []);

weChatDirectives.directive('setting', function() {
    return {
        restrict: 'AECM',
        templateUrl: 'tpls/setting.html',
        replace: true
    }
});

weChatDirectives.directive('friend', function() {
    return {
        restrict: 'AECM',
        templateUrl: 'tpls/friend.html',
        replace: true
    }
});