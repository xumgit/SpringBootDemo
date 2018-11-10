//自定义Service
angular.module("voteApp").service("voteSer", ["$q", "$http", function($q, $http) {
	this.getPlayerNames = function() {
		return $http.get("/demo/getData").then(function(resp) {
			if(typeof resp.data === "object") {
				var playerNames = [];
				angular.forEach(resp.data, function(v, k) {
					//playerNames.push(angular.$$lowercase(v.name));
					playerNames.push(v.name);
				});
				return playerNames;
			}else {
				//无效数据
				return $q.reject(resp.data);
			}
		}, function(resp) {
			return $q.reject(resp.status);
		});
	};
}]);