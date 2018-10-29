/**
 * 
 */
//http://www.angularjsapi.cn/#/show/copy

angularDataApp.controller('authorlistController', ['$scope','$log', '$rootScope', '$http', 'Configure', 'bootStrapAndAngularService', '$location', 'locals', 
    function($scope, $log, $rootScope, $http, Configure, bootStrapAndAngularService, $location, locals) {
    $scope.title = '点击展开';
    $scope.text = '这里是内部的内容。';
    $scope.expanders = [{
        title : 'Click me to expand',
        text : 'Hi there folks, I am the content that was hidden but is now shown.'
    }, {
        title : 'Click this',
        text : 'I am even better text than you have seen previously'
    }, {
        title : 'Test',
        text : 'test'
    }];
    $scope.result = "";
    $scope.myStyle = {
             "color" : "black",
             "font-size" : "18px",
             "margin" : "15px"
    }
    $scope.saveAgeData = function(obj) {
        console.log("age="+$(obj).val()+",id="+$(obj).attr("id"));
        bootStrapAndAngularService.update(obj).then(function(result){
            console.log("result:"+result);
        });
    } 
    
    $scope.downloadData = function(obj) {
        console.log("download something,id=" + $(obj).attr("rowid"));
        $("#showModal").modal("show");
    }

    $scope.okButtonFun = function() {
        $("#showModal").modal("hide");
    }

    $scope.testAngularApiFun = function() {
        var log = [];
        var values = { name: 'misko', gender: 'male' };
        angular.forEach(values, function (value, key) {
            this.push(key + ':' + value);
        }, log);
        $scope.result = log.join(",");
        $log.info("dd="+$scope.dd);
    }
    //$scope.testAngularApiFun();

    $scope.initData = function() {
        $("#author-grid-data").on("initialize.rs.jquery.bootgrid", function (e) {
            $log.info('info log');
			//$log.warn('warn log');
			//$log.error('error log');
			//$log.log('log log');
			//$log.debug('debug log');
        }).on("initialized.rs.jquery.bootgrid", function (e) {
        	
        });
        
        var authorGridData = $("#author-grid-data").bootgrid({
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
                "age": function(column, row)
                {
                    return "<input id=" + row.id + "  value=\"" + row.age + "\" onchange=\"angular.element(this).scope().saveAgeData(this)\">";
                },
                "headerImg": function(column, row)
                {
                    return "<img id=" + row.id + " src=\"" + Configure.ImgUrl + "\" width=\"30px\" height=\"30px\"></img>";
                },
                "email": function(column, row)
                {
                    return "<a href=\"#\">" + column.id + ": " + row.id + ": " + row.email + "</a>";
                },
                "command": function(column, row) 
                {
                    var div = '<a id="download_'+row.id+'" rowid="'+row.id+'"><span class="glyphicon glyphicon-download" style="font-size:18px;"></span></a>&nbsp;' +
					          '<a><span class="glyphicon glyphicon-trash" style="color:rgb(212, 106, 64);font-size:18px;"></span></a>&nbsp;' +
					          '<a><span class="glyphicon glyphicon-refresh" style="font-size:18px;"></span></a>&nbsp;' +
					          '<a><span class="glyphicon glyphicon-repeat" style="font-size:18px;"></span></a>&nbsp' +
					          '<a><span class="glyphicon glyphicon-stop"style="color:rgb(212, 106, 64);font-size:18px;"></a>';
                    return div;
                }
            }
        }).on("loaded.rs.jquery.bootgrid",function(){
            console.log("loaded");                              
		    $("span.fa-refresh").parent().css({"height": "34px", "width": "50px"});
		    $("span.dropdown-text").parent().css({"height": "34px", "width": "50px"});
		    $("button[title='Refresh']").html("<span class=\"icon fa fa-refresh\"></span><span class=\"glyphicon glyphicon-refresh\"></span>");
            $("<span class=\"glyphicon glyphicon-list\"></span>").appendTo(".fa-th-list");
            authorGridData.find("[id^='download_']").on("click",function(e){
					$scope.downloadData(this);
			}).end();
        }).on("selected.rs.jquery.bootgrid", function(e, rows){
            console.log("selected="+$(this).data("row-id"));
        }).on("deselected.rs.jquery.bootgrid", function(e, rows){
            console.log("deselected");
        });  
    }    
}]);