/**
 * 
 */

var angularDataApp = angular.module('angularDataApp', ['ngAnimate','ngRoute','ngCookies']);

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

angularDataApp.controller('authorlistController', ['$scope', '$rootScope', '$http', '$location', 'locals', 
    function($scope, $rootScope, $http, $location, locals) {
    $scope.initData = function() {
        $("#author-grid-data").bootgrid({
            ajax: true,
            rowCount: [5, 10, 15, 20],
            post: function ()
            {
                return {
                    paraid: "b0df282a",
                    type: "selectAll"
                };
            },
            url: "/index/authorBootGrid",
            selection: true,
            multiSelect: true,
            keepSelection: true,
            formatters: {
                "add": function(column, row)
                {
                    return "<button id=\"expland_" + row.id + "\" class=\"expland_" + row.id + "\" data=" + row.add + " row-id=\"" + row.id +"\"></button>";
                },
                "email": function(column, row)
                {
                    return "<a href=\"#\">" + column.id + ": " + row.id + ": " + row.email + "</a>";
                }
            }
        }).on("loaded.rs.jquery.bootgrid",function(){
            console.log("loaded");                    
		    $("span.fa-refresh").parent().css({"height": "34px", "width": "50px"});
		    $("span.dropdown-text").parent().css({"height": "34px", "width": "50px"});
		    $("button[title='Refresh']").html("<span class=\"icon fa fa-refresh\"></span><span class=\"glyphicon glyphicon-refresh\"></span>");
		    $("<span class=\"glyphicon glyphicon-list\"></span>").appendTo(".fa-th-list");
        }).on("selected.rs.jquery.bootgrid", function(e, rows){
            console.log("selected="+$(this).data("row-id"));
        }).on("deselected.rs.jquery.bootgrid", function(e, rows){
            console.log("deselected");
        });
    }  
}]);