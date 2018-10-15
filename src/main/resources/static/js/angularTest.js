/**
 * 
 */

var myAugulars = angular.module('myAugular', ['ngAnimate','ngRoute']);

myAugulars.controller('loginController', ['$scope', '$rootScope','$timeout', 'Configure','$cookieStore', 'locals', 
    function($scope, $rootScope, $timeout, Configure, $cookieStore, locals) {
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