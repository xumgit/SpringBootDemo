/**
 * 
 */

angularDataApp.controller('testAngularJsAPIController', ['$scope','$log', '$rootScope', '$http', 'Configure', 'bootStrapAndAngularService', '$location', 'locals', 
    function($scope, $log, $rootScope, $http, Configure, bootStrapAndAngularService, $location, locals) {

    $scope.testAngularApiFun = function() {
        var log = [];
        var values = { name: 'misko', gender: 'male' };
        angular.forEach(values, function (value, key) {
            this.push(key + ':' + value);
        }, log);
        $scope.result = log.join(",");
        $log.info("dd="+$scope.dd);
    }

    $scope.master1 = {};
    $scope.user1 = {};
    $scope.update = function() {
        $scope.user1 = {"testKey1":"testValue1", "testKey2":"testVlaue2"};
        $scope.master1 = angular.copy($scope.user1);
    }
    $scope.reset = function() {
        $scope.user1 = {};
        $scope.master1 = {};
    }

    
}]);