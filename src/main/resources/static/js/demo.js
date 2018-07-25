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
	    		return "<span class=\"glyphicons glyphicons-plus\"></span>"+"<button id=\"expland_" + row.id + "\" data=" + row.add + " row-id=\"" + row.id +"\">" + "Expland" + "</button>";
	    	},
	        "email": function(column, row)
	        {
	            return "<a href=\"#\">" + column.id + ": " + row.id + ": " + row.email + "</a>";
	        }
	    }
	}).on("loaded.rs.jquery.bootgrid",function(){
	    console.log("loaded");
	    
		/*$("[id^=expland_]").each(function(index, element){
			addRow(this);
		});*/
	    
	    $("#grid-data").find("[id^=expland_]").on('click', function(e){
	    	if ($(this).text() == "Expland") {
	    		addRow(this);
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

	function addRow(obj){
		var rowId = $(obj).attr("row-id");
    	var data = $(obj).attr("data");
    	var jsonConfig = $.parseJSON(data);
    	$(obj).text("Collapse");
    	var html = "";
    	html += "<tr class=\"content_" + rowId + "\">" 
	      + "<td class=\"text-left\">" + "" + "</td>"
		  + "<td class=\"text-left\">" + jsonConfig.id + "_test" + "</td>"
		  + "<td class=\"text-left\">" 
		  + "<input type=\"text\" style=\"border:0px solid #dddddd;background: none repeat scroll 0 0 ;\"" +
		  	" onchange=\"nameChange(this)\" data='" + data + "' orgName=\"" + jsonConfig.name + "\" row-id=\"" + rowId + "\" value=\"" + jsonConfig.name + "\">" 
		  + "</input></td>"
		  + "<td class=\"text-left\">" + jsonConfig.age + "</td>"
		  + "<td class=\"text-left\">" + jsonConfig.email + "</td>"
		  + "<td class=\"text-left\">" + jsonConfig.country + "</td>"
		  + "<td class=\"text-left\">" + jsonConfig.clone + "</td>"
		  + "</tr>";
    	$("tbody tr").each(function(index, element){
			if ($(this).find("td:eq(1)").text() == rowId) {
				$(this).find("td:eq(1)").parent().after(html);
				return false;
			}
		});
	}
	
	nameChange = function(obj){	
		
		var newname = $(obj).val();
		var oldname = $(obj).attr("orgName");
		var rowId = $(obj).attr("row-id");
		console.log("newname="+newname+",oldname="+oldname+",rowId="+rowId);					
		newname = newname.replace(/^\s+|\s+$/g,'');
		oldname = oldname.replace(/^\s+|\s+$/g,'');	
		var numericReg =/[\/\\:*?\"<>|]/g; 
		if(numericReg.test(newname)) {
			$().toastmessage('showErrorToast','Failure: Special characters > < \ / | : ? * " are not allowed for group name.');
			$(obj).val($(obj).attr('orgName'));
 			return false;
		}
		
		var data = $(obj).attr("data");
		var jsonConfig = $.parseJSON(data);
    	jsonConfig.name = newname;
    	$("#expland_"+rowId).attr("data", JSON.stringify(jsonConfig));
    	
		$.ajax({
			type: 'POST',
			data: {"id":rowId,"name":newname},
			dataType: "json",
			url: "/index/saveAuthor",				   
			success: function(msg) {
				if(msg.status =="success"){
					$().toastmessage('showSuccessToast', "name is updated successfully!");
				}else {				
					$().toastmessage('showErrorToast', "name is updated failed!");
				}						
			}						
		});
	}
	
	fixBootStrapProblem();
	function fixBootStrapProblem() {
		$("span.fa-refresh").parent().css({"height": "34px", "width": "50px"});
		$("span.dropdown-text").parent().css({"height": "34px", "width": "50px"});
		$("button[title='Refresh']").html("<span class=\"icon fa fa-refresh\"></span><span class=\"glyphicon glyphicon-refresh\"></span>");
		$("<span class=\"glyphicon glyphicon-list\"></span>").appendTo(".fa-th-list");
	}
});