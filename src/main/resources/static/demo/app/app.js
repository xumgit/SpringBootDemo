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
}]).constant('Constant',{
	Language:{
        "lengthMenu": "row",
        "paginate": {
            "previous": "pre",
            "next": "nex",
        },
        "processing": "loading..."
    },
    Numbers: 101,
	Teams: ["骑士","勇士","尼克斯","快船","火箭","篮网","公牛","雷霆","湖人","开拓者","老鹰","马刺","灰熊","国王","掘金"],
	Positions: [
		{val:"PG",txt:"控球后卫"},
		{val:"SG",txt:"得分后卫"},
		{val:"SF",txt:"小前锋"},
		{val:"PF",txt:"大前锋"},
		{val:"C",txt:"中锋"}
	]
}).factory('Locals', ['$window', function($window){
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