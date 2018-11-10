//验证球员号码
angular.module("voteApp").directive("valPlayerNum", function() { // need write like: <select ... val-player-num>
	return {
	    require: "ngModel",
	    link: function(scope, element, attr, mCtrl) {
	        function valPlayerNum(value) {
	        	if (/^([1-9]\d|\d)$/.test(value)) {
	            	mCtrl.$setValidity('playerNum', true);
	        	} else {
	            	mCtrl.$setValidity('playerNum', false);
	        	}
	        	return value;
	        }
	        mCtrl.$parsers.push(valPlayerNum);
	    }
    };
}).directive("valPlayerVotes", function() { // need write like: <input ... val-player-votes/>
	return {
	    require: "ngModel",
	    link: function(scope, element, attr, mCtrl) {
	        function valPlayerVotes(value) {
	        	if (/^[1-4]\d{3}$/.test(value)) {
	            	mCtrl.$setValidity('playerVotes', true);
	        	} else {
	            	mCtrl.$setValidity('playerVotes', false);
	        	}
	        	return value;
	        }
	        mCtrl.$parsers.push(valPlayerVotes);
	    }
    };
});