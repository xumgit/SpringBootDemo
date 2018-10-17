/**
 * 
 */

var myAugulars = angular.module('myAugular', ['ngAnimate','ngRoute','ngCookies']);

myAugulars.config(['$locationProvider', function($locationProvider) {   
    $locationProvider.hashPrefix(''); 
}])
.config(["$routeProvider", function($routeProvider) {
    $routeProvider.when("/loginSuccess",{
        redirectTo: "/angularjs/index",
        controller: "loginController"
    }).when("/loginError",{
        redirectTo: "/angularjs/error",
        controller: "loginController"
    });
}]).factory('locals', ['$window', function($window){
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

myAugulars.controller('loginController', ['$scope', '$rootScope', '$http', '$location', 'locals', 
    function($scope, $rootScope, $http, $location, locals) {
    $scope.user = {userName:'', userPassword:'', remeberMe:false, kaptcha:'', captchaguid:''}; 
    $scope.captchaUrl = "/angularjs/defaultKaptcha";
    $scope.login = function() {
        console.log("$scope.user.userName=" + $scope.user.userName);
        console.log("$scope.user.userPassword=" + $scope.user.userPassword);
        console.log("$scope.user.captchaguid=" + $scope.user.captchaguid);
        $http({
            method: "GET",
            url: "/angularjs/imgverifyControllerDefaultKaptcha",
            params: {
                "userName": $scope.user.userName,
                "password": $scope.user.userPassword,
                "vrifyCode": $scope.user.captchaguid
            }
        }).then(function(response){
            var isLoginSuccess = response.data.status;
            console.log("isLoginSuccess=" + isLoginSuccess);
            if (isLoginSuccess == "success") {
                $location.path("/loginSuccess");
            } else {
                $location.path("/loginError");
            }
        });
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