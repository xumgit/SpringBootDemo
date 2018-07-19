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
});