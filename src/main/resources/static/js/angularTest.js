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

myAugulars.controller('loginController', ['$scope', '$rootScope', '$http', 'locals', 
    function($scope, $rootScope, $http, locals) {
    $scope.user = {userName:'', userPassword:'', remeberMe:false, kaptcha:'', captchaguid:''}; 
    $scope.captchaUrl = "/angularjs/defaultKaptcha";
    $scope.login = function() {
        console.log("$scope.user.userName=" + $scope.user.userName);
        console.log("$scope.user.userPassword=" + $scope.user.userPassword);
    } 
    $scope.init = function() {
        var u = locals.getObject("loginUser");
        if(!$.isEmptyObject(u)){
    		$scope.user.userName = u.userName;
            $scope.user.userPassword = u.userPassword;
            $scope.user.remeberMe = u.remeberMe;
    	}
    }
    $scope.changeCaptcha = function(){
    	$scope.captchaUrl = "/angularjs/defaultKaptcha?d=" + Math.floor(Math.random()*100);
    }
    $scope.remeberChange = function($event) {
        console.log("checked=" + $scope.user.remeberMe);
        //locals.setObject("remeberMe", $event.target.checked);
        //$scope.user.remeberMe = !($scope.user.remeberMe);
        //$scope.user.userPassword = "";
        console.log("$scope.user.captchaguid=" + $scope.user.captchaguid);
        $http({
            method: "GET",
            url: "/angularjs/imgverifyControllerDefaultKaptcha",
            params: {
                "vrifyCode": $scope.user.captchaguid
            }
        }).then(function(response){
            console.log("success response=" + response);
        }).then(function(response){
            console.log("error response=" + response);
        });
        if ($scope.user.remeberMe) {
            locals.setObject("loginUser", $scope.user);
        } else {
            $scope.user.userName = "";
            $scope.user.userPassword = "";
            $scope.user.remeberMe = false;
            locals.setObject("loginUser", $scope.user);
        }      
    }   
}]);