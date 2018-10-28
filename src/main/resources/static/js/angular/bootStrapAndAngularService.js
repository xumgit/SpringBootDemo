/**
 * 
 */

angularDataApp.service('bootStrapAndAngularService', function($http, $q, Configure){

    this.update = function(obj) {
        var deferred = $q.defer();
        $http({
            method: "POST",
            url: "/angularjs/updatedata",
            params: {
                "id": $(obj).attr("id"),
                "age": $(obj).val()
            }
        }).then(function(success){
            console.log("success,status:"+success.data.status)
            deferred.resolve("success");         
        }, function(error){
            console.log("error,status:"+error.data.status)
            deferred.reject("error");
        });
        return deferred.promise;
    };

    this.delete = function(obj) {
        var deferred = $q.defer();
        $http({
            method: "POST",
            url: "/angularjs/deletedata",
            params: {
                "id": $(obj).attr("id")
            }
        }).then(function(success){
            console.log("success,status:"+success.data.status)
            deferred.resolve("success");         
        }, function(error){
            console.log("error,status:"+error.data.status)
            deferred.reject("error");
        });
        return deferred.promise;
    };

});