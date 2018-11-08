//定义主模块并注入依赖
angular.module("voteApp", ["ngRoute"]);

//路由配置
angular.module("voteApp").config(["$routeProvider", function($routeProvider) {
	$routeProvider.when("/player/list", {
		templateUrl: "/demo/list",
		controller: playerListCtrl
	}).when("/player/view/:playerId", {
		templateUrl: "/demo/view",
		controller: playerViewCtrl
	}).when("/player/view/:playerId/:playerName", {
		templateUrl: "/demo/view",
		controller: playerViewCtrl
	}).when("/player/add", {
		templateUrl: "/demo/add",
		controller: playerAddCtrl
	}).when("/player/edit/:playerId", {
		templateUrl: "/demo/edit",
		controller: playerEditCtrl
	}).otherwise({
		redirectTo: "/player/list"
	});
}]).config(['$locationProvider', function($locationProvider) {
  $locationProvider.hashPrefix('');
}]);