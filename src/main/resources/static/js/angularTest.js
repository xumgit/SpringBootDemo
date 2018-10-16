/**
 * 
 */

var myAugulars = angular.module('myAugular', ['ngAnimate','ngRoute','ngCookies']);

myAugulars.factory('locals', ['$window', function($window){
                   return{
                      set: function(key, value){
                        $window.localStorage[key] = value;
                      },
                      get: function(key, defaultValue){
                        return  $window.localStorage[key] || defaultValue;
                      },
                      setObject: function(key, value){
                        $window.localStorage[key] = JSON.stringify(value);
                      },
                      getObject: function(key) {
                        return JSON.parse($window.localStorage[key] || '{}');
                      }
                    };
                 }]);

myAugulars.controller('loginController', ['$scope', '$rootScope','$timeout', 'Configure','$cookiesProvider', 'locals', 
    function($scope, $rootScope, $timeout, Configure, $cookiesProvider, locals) {
    $scope.user = {userName:'', userPassword:'', remeberMe:false, kaptcha:'', captchaguid:''}; 
    $scope.login = function() {
        console.log("$scope.user.userName=" + $scope.user.userName);
        console.log("$scope.user.userPassword=" + $scope.user.userPassword);
    } 
    $scope.remeberChange = function($event) {
        console.log("checked=" + $event.target.checked);
        locals.setObject("remeberMe", $event.target.checked);
    }   
}]);