/**
 * 
 */

angularDataApp.config(['$locationProvider', function($locationProvider) {   
    $locationProvider.hashPrefix(''); 
}])
.config(["$routeProvider", function($routeProvider) {
    $routeProvider.when("/loginSuccess",{
        redirectTo: "/angularjs/index",
        controller: "authorlistController"
    }).when("/loginError",{
        redirectTo: "/angularjs/error",
        controller: "authorlistController"
    });
}]);