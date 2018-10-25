/**
 * 
 */

 angularDataApp.filter("menufilter",function(){
                return function(input,url){
                	if(input==undefined || input.length==0 || input==''){
                		return " ";
                	}
                    var out = "/manager" +input;
                    var fdStart = url.indexOf(out);
                    if(fdStart == 0){
                    	return " active ";
                    }else{
                	    return " ";
                    }
                };
            }).filter("submenufilter",function(){
                return function(input,url){
                	if(input==undefined || input.length==0 || input==''){
                		return " ";
                	}
                    var out =  "/manager" +input;
                    if(out == url){
                    	return " active ";
                    }else{
                	    return " ";
                    }
                };
            }).filter("trusted", ["$sce", function ($sce) {
                return function (html) {
                    if (typeof html== 'string') 
                        return $sce.trustAsHtml(html);  
                    return html;
                };
            }]);