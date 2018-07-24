/**
 * 
 */

$(function(){
	var grid_devices_data = $("#grid-data").bootgrid({
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
	    			return "<button id=\"expland_" + row.id + "\" row-id=\"" + row.id +"\">" + "Expland" + "</button>";
	    		},
	        "email": function(column, row)
	        {
	            return "<a href=\"#\">" + column.id + ": " + row.id + ": " + row.email + "</a>";
	        }
	    }
	}).on("loaded.rs.jquery.bootgrid",function(){
	    console.log("loaded");
	    //$("#grid-data").bootgrid("search", "46");
	    $("#grid-data").bootgrid("remove", [1,"2",3]);
	    $("#grid-data").find("[id^=expland_]").on('click', function(){
	    		var rowId = $(this).attr("row-id");
	    		console.log("rowId="+rowId);
	    		$(".content_" + rowId).each(function(index, element){
 				$(this).remove();
 			});    		
	     	if ($(this).text() == "Expland") {	     		
	     		$(this).text("Collapse");
	     		var html = "<tr class=\"content_" + rowId + "\">" 
			    		+ "<td class=\"text-left\">" + "" +"</td>"
			    		+ "<td class=\"text-left\">" + "test1" +"</td>"
			    		+ "<td class=\"text-left\">" + "test1" +"</td>"
			    		+ "<td class=\"text-left\">" + "test1" +"</td>"
			    		+ "<td class=\"text-left\">" + "test1" +"</td>"
			    		+ "<td class=\"text-left\">" + "test1" +"</td>"
			    		+ "<td class=\"text-left\">" + "test1" +"</td>"
			    		+ "</tr>"
			    		+ "<tr class=\"content_" + rowId + "\">" 
			    		+ "<td class=\"text-left\">" + "" +"</td>"
			    		+ "<td class=\"text-left\">" + "test1" +"</td>"
			    		+ "<td class=\"text-left\">" + "test1" +"</td>"
			    		+ "<td class=\"text-left\">" + "test1" +"</td>"
			    		+ "<td class=\"text-left\">" + "test1" +"</td>"
			    		+ "<td class=\"text-left\">" + "test1" +"</td>"
			    		+ "<td class=\"text-left\">" + "test1" +"</td>"
			    		+ "</tr>";
	     			$("tbody tr").each(function(index, element){
	     				if ($(this).find("td:eq(1)").text() == rowId) {
	     					grid_devices_data.find("tbody tr:eq("+index+")").after(html);
	     					return false;
	     				}
	     			});
	     			
	     		} else {
	     			$(this).text("Expland");
	     			$(".content_" + $(this).attr("row-id")).each(function(index, element){
	     				$(this).css("display", "none");
	     			});
	     		}
	    });
	   
	    	
	    
	}).on("selected.rs.jquery.bootgrid", function(e, rows){
		console.log("selected="+$(this).data("row-id"));

	}).on("deselected.rs.jquery.bootgrid", function(e, rows){
		console.log("deselected");
	});

	fixBootStrapProblem();
	function fixBootStrapProblem() {
		$("span.fa-refresh").parent().css({"height": "34px", "width": "50px"});
		$("span.dropdown-text").parent().css({"height": "34px", "width": "50px"});
		$("button[title='Refresh']").html("<span class=\"icon fa fa-refresh\"></span><span class=\"glyphicon glyphicon-refresh\"></span>");
		$("<span class=\"glyphicon glyphicon-list\"></span>").appendTo(".fa-th-list");
	}
});