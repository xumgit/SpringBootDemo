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
	        "email": function(column, row)
	        {
	            return "<a href=\"#\">" + column.id + ": " + row.id + ": " + row.email + "</a>";
	        }
	    }
	}).on("loaded.rs.jquery.bootgrid",function(){
	    console.log("loaded");
	}).on("selected.rs.jquery.bootgrid", function(e, rows){
		console.log("selected");
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