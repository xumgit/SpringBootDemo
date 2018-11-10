//List Controller
function playerListCtrl($scope, $http) {
	$http.get("/demo/getData").then(function(data) {
		$scope.players = data.data.rows;
	});
	$scope.orderProp = "-votes"; //默认按票数降序排列
		 
	//删除
	$scope.removePlayer = function(ev,id) {
		ev.preventDefault();
		angular.forEach($scope.players, function(val, key) {
			console.log("id:" + id + ",val.id:" + val.id);
			if(id === val.id) {
				$scope.players.splice(key, 1);
			}			
		});
		$http({
			method: "post",
			url: "/demo/deleteOneData",
			params: {
				"playerId": id
			}
		}).then(function(resp) {
			console.log("Delete Successfully! Status: " + resp.status + ",status:" + resp.data.status);
			//$location.path("#/player/list");
		}, function(resp) {
			console.log("Delete Failly! Status: " + resp.status + ",status:" + resp.data.status);
			//$location.path("#/player/list");
		});
	};
}

//Add Controller
function playerAddCtrl($scope, $http, $location, voteSer, Constant) {
	//初始化位置信息
	$scope.positions = Constant.Positions;
	$scope.numbers = [];
	for(var i=1;i<Constant.Numbers;i++) {
		$scope.numbers.push(i);
	}
	//初始化球队信息
	$scope.teams = Constant.Teams;
	$scope.fileChanged = function(ele) {
		$scope.player.thumb = ele.files[0].name;
		$scope.$apply(); 
	}
	//提交表单
	$scope.submitForm = function() {
		console.log("Submit Player Form: ", angular.toJson($scope.player));
		voteSer.getPlayerNames().then(function(data) {
			//判断该球员姓名是否已存在
			if(data.indexOf($scope.player.name) >= 0) {
				$scope.isExisted = true;
			}else {
				//提交表单
				$http({
					method: "post",
					url: "/demo/addOneData", 
            		contentType: "application/json",
					data: JSON.stringify($scope.player)
				}).then(function(resp) {
					console.log("Saved Successfully! Status: " + resp.status + ",status: " + resp.data.status);
					$location.path("#/player/list");
				}, function(resp) {
					console.log("Saved Failly! Status: " + resp.status + ",status: " + resp.data.status);
					$location.path("#/player/list");
				});
			}
		});
	};
}

//Edit Controller
function playerEditCtrl($scope, $http, $routeParams, $location, Constant) {
	//初始化位置信息
	$scope.positions = Constant.Positions;
	//初始化球队信息
	$scope.teams = Constant.Teams;
	console.log("[playerEditCtrl]playerId:" + $routeParams.playerId);
	//获取被编辑的球员信息
	$http.get("/demo/getOneData",{
		params:{  
        	"playerId": $routeParams.playerId  
	    }
	}).then(function(response) {
		//var i = parseInt($routeParams.playerId)-1;
		//$scope.player = data.data.rows[i];
		console.log("data:" + response.data[0]);
		$scope.player = response.data[0];
	});
	
	//提交表单
	$scope.submitForm = function() {
		console.log("Submit Player Form: ", angular.toJson($scope.player));
		$http({
			method: "post",
			url: "/demo/update",
			params: {
				"id": $scope.player.id,
				"number": $scope.player.number,
				"thumb": $scope.player.thumb,
				"name": $scope.player.name,
				"votes": $scope.player.votes,
				"position": $scope.player.position,
				"team": $scope.player.team
			}
		}).then(function(resp) {
			console.log("Saved Successfully! Status: " + resp.status + ",status:" + resp.data.status);
			$location.path("#/player/list");
		}, function(resp) {
			console.log("Saved Failly! Status: " + resp.status + ",status:" + resp.data.status);
			$location.path("#/player/list");
		});
	};
}

//View Controller
function playerViewCtrl($scope, $http, $location, $routeParams) {
	console.log("[playerViewCtrl]playerId:" + $routeParams.playerId);
	$http.get("/demo/getOneData",{
		params:{  
        	"playerId": $routeParams.playerId  
	    }
	}).then(function(response) {
		//var i = parseInt($routeParams.playerId)-1;
		console.log("Success,status:" + response.status);
		$scope.player = response.data[0];
	}, function(response) {
		console.log("Failed,status:" + response.status);
	});
	
	//获取头像图片名称
	$scope.getThumb = function(playerThumb) {
		console.log("Player Thumb: " + playerThumb);
	};
	
	//投票
	$scope.voteBtnText = "投票";
	$scope.vote = function(playerId) {
		console.log("playerId:" + playerId);
		$scope.player.votes = parseInt($scope.player.votes) + 1;
		$scope.voteBtnText = "已投票";
		$scope.isVoted = true;
		$http({
			method: "post",
			url: "/demo/update",
			params: {
				"id": $scope.player.id,
				"votes": $scope.player.votes
			}
		}).then(function(resp) {
			console.log("Update Successfully! Status: " + resp.status + ",status:" + resp.data.status);
			//$location.path("#/player/list");
		}, function(resp) {
			console.log("Update Failly! Status: " + resp.status + ",status:" + resp.data.status);
			//$location.path("#/player/list");
		});
	};
}