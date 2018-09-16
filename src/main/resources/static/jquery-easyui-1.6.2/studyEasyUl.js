// http://www.jeasyui.com/demo/main/index.php?plugin=DataGrid&theme=metro-red&dir=ltr&pitem=Basic%20DataGrid&sort=asc	
var names = [
	    {productid:'Koi',name:'Koi'},
	    {productid:'Dalmation',name:'Dalmation'},
	    {productid:'Rattlesnake',name:'Rattlesnake'},
	    {productid:'Iguana',name:'Iguana'}
	];
var countrys = [
	    {countryid:'1',country:'China'},
	    {countryid:'2',country:'German'},
	    {countryid:'3',country:'USA'},
	    {countryid:'4',country:'France'},
	    {countryid:'5',country:'Japan'},
	    {countryid:'6',country:'Polan'},
	    {countryid:'7',country:'England'}
	];

var dataDrag = {"total":0,"rows":[]};
var totalCostDrag = 0;
	
$(document).ready(function(){
			
	var windowWidth = $(window).width();
	var contentWidth = (windowWidth-58)*0.20;
	// datagrid test
    $('#myEasyui').datagrid({
        //width: 800,                 //设置宽度
        url: '/easyui/getAuthor',       //远程加载数据地址
        title: '用户列表',           //面板属性，添加标题
        iconCls: 'icon-search',     //加图标
        columns: [[ 
        	{
                field: 'id',      //field,对应远程JSON 数据里的对象属性，也就是数据库字段
                title: '键值',      //title,定义数据的标题
 				halign: 'center',
 				align: 'center',
 				hidden: false,
 				resizable: false,
 				fixed: true,
 				sortable: true,
 				checkbox: true,
 				width: 28,
 				styler: function(value,row,index){
					//return 'background-color:#666666;color:red;width:50%;';
					// the function can return predefined css class and inline style
					return {class:'tdContent',style:'color:red'};
				}
            },
            {
                field: 'name',      //field,对应远程JSON 数据里的对象属性，也就是数据库字段
                title: '姓名',      //title,定义数据的标题
				halign: 'center',
				align: 'center',
				resizable: false,
				fixed: true,
				sortable: true,
 				width: contentWidth,
				formatter: function(value,row,index){
					if (row.id){
						var html = "<div id=\"" + row.id + "\" index=\"" + index + "\" name=\"" + value + "\" age=\"" + row.age + "\">" 
									+ value + "</div>";
						return html;
					} else {
						return value;
					}
				},
				styler: function(value,row,index){
					//return 'background-color:#666666;color:red;width:50%;';
					// the function can return predefined css class and inline style
					return {class:'tdContent', style:'color:orange'};
				},
				editor:{
					type: 'combobox',
					options:{
						valueField: 'productid',
						textField: 'name',
						data: names,
						required: true
					}
				}
            },
            {
                field: 'age',      //field,对应远程JSON 数据里的对象属性，也就是数据库字段
                title: '年龄',      //title,定义数据的标题
				halign: 'center',
				align: 'center',
				resizable: false,
				fixed: true,
				sortable: true,
 				width: contentWidth,
				styler: function(value,row,index){
					//return 'background-color:#555555;color:red;width:50%;';
					// the function can return predefined css class and inline style
					return {class:'tdContent',style:'color:red'};
				},
				editor: 'numberbox'
            },
            {
                field: 'email',     //field,对应远程JSON 数据里的对象属性，也就是数据库字段
                title: '邮件',      //title,定义数据的标题
				halign: 'center',
				align: 'center',
				resizable: false,
				fixed: true,
				sortable: true,
 				width: contentWidth,
				styler: function(value,row,index){
					//return 'background-color:#444444;color:red;width:50%;';
					// the function can return predefined css class and inline style
					return {class:'tdContent',style:'color:red'};
				},
				editor: {
                    type: 'validatebox',
                    options: {
                        required: true,
                        validType: 'email'
                    }
                }
            },
            {
                field: 'country',      //field,对应远程JSON 数据里的对象属性，也就是数据库字段
                title: '国家',   //title,定义数据的标题
				halign: 'center',
				align: 'center',
				resizable: false,
				fixed: true,
				sortable: true,
 				width: contentWidth,
				styler: function(value,row,index){
					//return 'background-color:#333333;color:red;width:50%;';
					// the function can return predefined css class and inline style
					return {class:'tdContent',style:'color:red'};
				},
				editor:{
					type: 'combobox',
					options:{
						valueField: 'countryid',
						textField: 'country',
						data: countrys,
						required: true
					}
				}
            },
            {
            	field:'action', 
            	title:'Action', 
            	align:'center',
            	fixed: true,
            	width: contentWidth,
				formatter:function(value,row,index){
					if (row.editing){
						var s = '<button id="save" onclick="saverow('+row.id+','+index+')">Save</button> ';
						var c = '<button id="cancel" onclick="cancelrow('+row.id+','+index+')">Cancel</button>';
						return s+c;
					} else {
						var e = '<button id="edit" onclick="editrow('+row.id+','+index+')">Edit</button> ';
						var d = '<button id="delete" onclick="deleterow('+row.id+','+index+')">Delete</button>';
						return e+d;
					}
				}
			}
        ]],
        loadMsg: 'loading...',
        singleSelect: false,
        showHeader: true,
        showFooter: false,
        rownumbers: false,
        scrollbarSize: 30,
        fitColumns: true,
        toolbar: '#tb',
        view: detailview,
        detailFormatter: function(index,row){
    		return '<div id="ddv_' + index + '" class="easyui-panel" style="padding:5px 0"></div>';
    	},
    	onExpandRow: function(index,row){
    		var ddv = $(this).datagrid('getRowDetail',index).find('#ddv_'+index);
    		ddv.panel({
    			title: 'Detail info',
    			fit: false,
    			border: true,
    			cache: false,
    			collapsed: false,
    			collapsible: false,
    			iconCls:'icon-ok',tools:[
    				{
    					iconCls:'icon-add',
    					handler:function(){console.log('add');}
    				},{
    					iconCls:'icon-edit',
    					handler:function(){console.log('edit');}
    				}],
    			href: '/easyui/dsiplayAuthorAgain?authorId='+row.id+"&name="+row.name+"&age="+row.age+"&email="+row.email+"&country="+row.country,
    			onBeforeOpen: function() {
    				console.log("onBeforeOpen");
    			},
    			onOpen: function() {
    				console.log("onOpen");
    			},
    			onBeforeClose: function() {
    				console.log("onBeforeClose");
    			},
    			onClose: function() {
    				console.log("onClose");
    			},
    			onBeforeDestroy: function() {
    				console.log("onBeforeDestroy");
    			},
    			onDestroy: function() {
    				console.log("onDestroy");
    			},
    			onBeforeCollapse: function() {
    				console.log("onBeforeCollapse");
    			},
    			onCollapse: function() {
    				console.log("onCollapse");
    			},
    			onBeforeExpand: function() {
    				console.log("onBeforeExpand");
    			},
    			onExpand: function() {
    				console.log("onExpand");
    			},
    			onLoad: function(){
    				console.log("onLoad");
    				$('#myEasyui').datagrid('fixDetailRowHeight',index);
    			}
    		});
    		$('#myEasyui').datagrid('fixDetailRowHeight',index);
    	},
		pagination: true,
		pageNumber: 1,             //设置分页时初始化页码
        pageSize: 3,               //设置分页时设置每页多少条
        pageList: [3,6,9],          //设置可选每页显示条数
		pagePosition: 'bottom',
		sortName: 'id',        //设置哪些列可以进行排序。默认为 null。值为field的值也就是可以排序的字段，这个值会发送到数据库
        sortOrder: 'ASC',        //设置列排序的顺序,ASC 和 DESC，默认是 ASC。这个值会发送到数据库
		/*queryParams:{           //设置请求远程数据发送的额外数据
            id: 1
        },*/
        /*loadFilter: function(data) {
        	console.log("response data="+data);
        },*/
        onBeforeLoad: function(param) {
        	console.log("onBeforeLoad=>param="+param);
        	for(k in param) {
        		console.log("onBeforeLoad=>k="+k+",v="+param[k]);
        	}
        },
        onLoadSuccess: function(data) {
        	console.log("onLoadSuccess=>data="+data.total);
        	//$("div[id^='ddv_']").panel('open', true);
        	//var expland_length = $("td[field='_expander']").length;
        	//console.log("expland_length="+expland_length);
        	$("td[field='_expander']").each(function(index, element){
        		// auto open panel
        		//$(this).find("span.datagrid-row-expander").trigger("click");
        	});
        },
        onLoadError: function() {
        	console.log("onLoadError=>");
        },
        rowStyler: function (index,row) {    //row接收整个数据对象
        	// 被styler方法覆盖
            if (row.name == "zhangshan"){
                return {style:'color:red'};
            }
        },
        onBeginEdit: function(index,row) {
        	console.log("onBeginEdit=>index="+index);
        },
        onEndEdit: function(index,row,changes) {
        	var field = "";
        	for(x in changes) {
        		field = x;
        	}
        	console.log("field="+field);
        	if (field != "") {
        		var ed = $(this).datagrid('getEditor', {
    				index: index,
    				field: field
    			});
    			var textValue = $(ed.target).combobox('getText');
    			console.log("onEndEdit=>textValue="+textValue+",index="+index);
    			if ('name' == field) {
    				row.name = textValue;
    			} else if ('country' == field) {
    				row.country = textValue;
    			}
        	}			
		},
		onBeforeEdit: function(index,row){
			console.log("onBeforeEdit=>index="+index);
			row.editing = true;
			$(this).datagrid('refreshRow', index);
		},
		onAfterEdit: function(index,row,changes){
			console.log("onAfterEdit=>index="+index+",id="+row.id);
			console.log("name="+row.name+",age="+row.age+",email="+row.email+",country="+row.country);
			$.ajax({
				type: "POST",
				url: "/easyui/saveAuthor",
				data: {
					"authorId": row.id,
					"name": row.name,
					"age": row.age,
					"email": row.email,
					"country": row.country
				},
				success: function(msg){
					msg = $.parseJSON(msg);
					console.log("status="+msg.status);
					if (msg.status == "success") {
						showMessage('Tip', '<span style="color: green;">Save author info success</span>');
					}
				},
				error: function(data){
					console.log("error="+data);
				}
			});
			row.editing = false;
			$(this).datagrid('refreshRow', index);
		},
		onCancelEdit: function(index,row){
			console.log("onCancelEdit=>index="+index);
			row.editing = false;
			$(this).datagrid('refreshRow', index);
		},
		onClickRow: function(index,row) {
			console.log("onClickRow=>index="+index);
		},
		onDblClickRow: function(index,row) {
			console.log("onDblClickRow=>index="+index);
		},
		onClickCell: function(index,field,value){
			console.log("onClickCell=>index="+index+",field="+field+",value="+value);		
		},
		onDblClickCell: function(index,field,value){
			console.log("onDblClickCell=>index="+index+",field="+field+",value="+value);
		},
		onSortColumn: function(sort,order) {
			console.log("onSortColumn=>sort="+sort+",order="+order);
		},
		onResizeColumn: function(field,width) {
			console.log("onResizeColumn=>field="+field+",width="+width);
		},
		onSelect: function(index,row) {
			console.log("onSelect=>index="+index);
		},
		onUnselect: function(index,row) {
			console.log("onUnselect=>index="+index);
		},
		onSelectAll: function(rows) {
			console.log("onSelectAll=>"+rows.length);
		},
		onUnselectAll: function(rows) {
			console.log("onUnselectAll=>"+rows.length);
		},
		onCheck: function(index,row) {
			console.log("onCheck=>index="+index);
		}, 
		onUncheck: function(index,row) {
			console.log("onUncheck=>index="+index);
		},
		onCheckAll: function(rows) {
			console.log("onCheckAll=>"+rows.length);
		},
		onUncheckAll: function(rows) {
			console.log("onUncheckAll=>"+rows.length);
		},
		onHeaderContextMenu: function(e,field) {
			console.log("onHeaderContextMenu=>field="+field+",e="+e);
		},
		onRowContextMenu: function(e,index,row) {
			console.log("onRowContextMenu=>index="+index+",e="+e);
		}
    });
    
    //数据表格工具栏1
    $('#addRow').linkbutton({  //将工具栏里的添加执行按钮方法
        iconCls: 'icon-add',     //设置图标
        plain: true              //按钮扁平化
    });
    $('#modifyRow').linkbutton({  //将工具栏里的修改执行按钮方法
        iconCls: 'icon-edit',    //设置图标
        plain: true              //按钮扁平化
    });
    $('#deleteRow').linkbutton({  //将工具栏里的删除执行按钮方法
        iconCls: 'icon-remove',  //设置图标
        plain: true              //按钮扁平化
    });
    //数据表格工具栏2
    //$('.shj1').datebox();   //查询日期输入框
    //$('.shj2').datebox();   //查询日期输入框
    $('.chx').linkbutton({  //查询按钮
        iconCls:"icon-search"
    });
    $('#addRow').click(function() {
    	obj.addRow();
    });
    $('#modifyRow').click(function() {
    	obj.modifyRow();
    });
    $('#deleteRow').click(function() {
    	obj.deleteRow();
    });
    //查询功能
    $('.chx').click(function () {   //点击查询后执行obj对象里的search方法
        obj.search();
    });
    var obj = {    //obj对象  
    	panduan: false,
        search: function() {
            $('#myEasyui').datagrid('load',{                         //执行数据表格的load方法提交数据
            	searchPhrase: $.trim($('#searchPharse').val())
            });
        },
        addRow: function() {     	
        	$('#myEasyui').datagrid('appendRow',{
            	id: 51,
            	name: 'newName',
            	age: 30,
            	email: 'newName@267.com',
            	country: 'China'
            });
        },
        modifyRow: function() {
        	var row = $('#myEasyui').datagrid('getChecked');
        	console.log("length="+row.length);
        	if(row.length == 0){
        		showMessage('Tip', '<span style="color: red;">please select a row</span>');
        	}else if(row.length > 1) {
        		showMessage('Tip', '<span style="color: red;">only select a row</span>');
        	}else if(row.length == 1) {
        		var row = $('#myEasyui').datagrid('getSelected');
        		var index = $('#myEasyui').datagrid('getRowIndex', row);
        		$('#dd').dialog({
        		    title: 'My Dialog',
        		    width: 400,
        		    height: 250,
        		    closed: false,
        		    cache: false,
        		    href: '/easyui/dsiplayAuthor?authorId='+row.id+"&name="+row.name+"&age="+row.age+"&email="+row.email+"&country="+row.country,
        		    modal: true,
        		    onClose: function () {
                        console.log("onClose");
                    },
                    /*toolbar: [{
                        text: 'Add',
                        iconCls: 'icon-add',
                        handler: function () {
                            console.log('add');
                        }
                    }, '-', {
                        text: 'Save',
                        iconCls: 'icon-save',
                        handler: function () {
                        	console.log('save');
                        }
                    }],*/
                    buttons: [{
                        text: 'Save',
                        iconCls: 'icon-save',
                        handler: function () {
                        	console.log('ok');
                        	$.ajax({
                				type: "POST",
                				url: "/easyui/saveAuthor",
                				data: {
                					"authorId": $("#display_authorId").val(),
                					"name": $("#display_name").val(),
                					"age": $("#display_age").val(),
                					"email": $("#display_email").val(),
                					"country": $("#display_country").val()
                				},
                				success: function(msg){
                					msg = $.parseJSON(msg);
                					console.log("status="+msg.status);
                					if (msg.status == "success") {
                						var trs = $("div#userlist .panel-body .datagrid-view2 div.datagrid-body table tr");
                						trs.each(function(index, element){
                							if($(this).hasClass("datagrid-row-checked")) {
                								var indexName = $(this).find("td:eq(1) div div").attr("index");
                								$(this).find("td:eq(1) div").html("<div id=\"" + $("#display_authorId").val() + "\" index=\"" + indexName 
                										+ "\" name=\"" + $("#display_name").val() + "\" age=\"" + $("#display_age").val() + "\">" 
                										+ $("#display_name").val() + "</div>");
                								$(this).find("td:eq(2) div").text($("#display_age").val());
                								$(this).find("td:eq(3) div").text($("#display_email").val());
                								$(this).find("td:eq(4) div").text($("#display_country").val());
                								return false;
                							}
                						});
                						$('#dd').dialog('close');
                						showMessage('Tip', '<span style="color: green;">Save author info success</span>');
                					}
                				},
                				error: function(data){
                					console.log("error="+data);
                				}
                			});
                        }
                    }, {
                        text: 'Cancel',
                        iconCls: 'icon-cancel',
                        handler: function () {
                            $('#dd').dialog('close');
                        }
                    }]
        		});
        		//$('#dd').dialog('refresh', 'xx');
        	}
        },
        deleteRow: function() {
        	var row = $('#myEasyui').datagrid('getChecked');
        	console.log("length="+row.length);
        	if (row.length == 0) {
        		showMessage('Tip', '<span style="color: red;">please select row</span>');
        		return;
        	}

        	$.messager.confirm('Tip', 'Delete select row?', function(b){
        		if (b){
        			for(r in row){
        				$('#myEasyui').datagrid('deleteRow', $('#myEasyui').datagrid('getRowIndex', row[r]));
        	        }     	          
        		} else {
        			console.log('cancel');       	            
        		}
        	});
        	
        }
    };
    test();
    
    // drag example
    $('#drag #cartcontent').datagrid({
		singleSelect:true,
		columns: [[
			{
	            field: 'name',      //field,对应远程JSON 数据里的对象属性，也就是数据库字段
	            title: 'Name',      //title,定义数据的标题
				halign: 'center',
				align: 'center',
				resizable: false,
				fixed: true,
				width: 80,
				formatter: function(value,row,index){
					return value;
				}
	        },
	        {
	            field: 'quantity',      //field,对应远程JSON 数据里的对象属性，也就是数据库字段
	            title: 'Quantity',      //title,定义数据的标题
				halign: 'center',
				align: 'center',
				resizable: false,
				fixed: true,
				width: 120,
				formatter:function(value,row,index) {
					var html = "<table class=\"table_quantity\"><tr>"+
								"<td>" + "<input class=\"minusBtn\" index=\""+index+"\" price=\""+row.price+"\" id=\"minus_"+index+"\" style=\"width:20px;\" type=\"button\" value=\"-\" />" + "</td>" +
								"<td>" + "<input style=\"width:50px;text-align:center;\" id=\"quantity_"+index+"\" value=\""+value+"\" readonly=\"readonly\"/>" + "</td>" +
								"<td>" + "<input class=\"addBtn\" index=\""+index+"\" price=\""+row.price+"\" id=\"add_"+index+"\" style=\"width:20px;\" type=\"button\" value=\"+\" />" + "</td>" +
								"</tr></table>";
					return html;
				},
				editor:{
					type:'numberbox',
					options:{
						precision:0
					}
				}
	        },
	        {
	            field: 'price',     //field,对应远程JSON 数据里的对象属性，也就是数据库字段
	            title: 'Price',      //title,定义数据的标题
				halign: 'center',
				align: 'center',
				resizable: false,
				fixed: true,
				width: 40				
	        },
	        {
            	field:'action', 
            	title:'Action', 
            	align:'center',
            	fixed: true,
            	width: 115,
				formatter:function(value,row,index){
					/*if (row.editing){
						var s = '<button id="saveRowPrice" style="width:50px;text-align:center;" onclick="saveRowPrice(\''
							+row.name+'\','+row.quantity+','+row.price+','+index+')">Save</button> ';
						var c = '<button id="cancelRowPrice" style="width:50px;text-align:center;" onclick="cancelRowPrice(\''
							+row.name+'\','+row.quantity+','+row.price+','+index+')">Cancel</button>';
						return s+c;
					} else {
						var e = '<button id="editRowPrice" style="width:50px;text-align:center;" onclick="editRowPrice(\''
							+row.name+'\','+row.quantity+','+row.price+','+index+')">Edit</button> ';
						var d = '<button id="deleteRowPrice" style="width:50px;text-align:center;" onclick="deleteRowPrice(\''
							+row.name+'\','+row.quantity+','+row.price+','+index+')">Delete</button>';
						return e+d;
					}*/	
					var d = '<button id="deleteRowPrice" style="width:50px;text-align:center;" onclick="deleteRowPrice(\''
						+row.name+'\','+row.quantity+','+row.price+','+index+')">Delete</button>';
					return d;
				}
			}
		]],
        onBeginEdit: function(index,row) {
        	console.log("onBeginEdit=>index="+index);
        },
        onEndEdit: function(index,row,changes) {
        	console.log("onEndEdit=>index="+index);
		},
		onBeforeEdit: function(index,row){
			console.log("onBeforeEdit=>index="+index);
			row.editing = true;
			$(this).datagrid('refreshRow', index);
		},
		onAfterEdit: function(index,row,changes){
			console.log("onAfterEdit=>index="+index);
			row.editing = false;
			$(this).datagrid('refreshRow', index);
		},
		onCancelEdit: function(index,row){
			console.log("onCancelEdit=>index="+index);
			row.editing = false;
			$(this).datagrid('refreshRow', index);
		},
		onLoadSuccess: function(data){
		    $("input.minusBtn").click(function(){
		    	var index = parseInt($(this).attr("index"));
		    	var price = parseInt($(this).attr("price"));
		    	var quantity = parseInt($("#quantity_"+index).val());
		    	console.log("index="+index+",price="+price+",quantity="+quantity);
		    	if(quantity > 0){
		    		$("#quantity_"+index).val(quantity-1);
		    		var remainPrice = parseInt($('#drag div.cart .total').html().split('$')[1]) - price;
		    		$('#drag div.cart .total').html('Total: $'+remainPrice);
		    	}else if(quantity == 0) {
		    		console.log("nothing");
		    	}
		    });
		    $("input.addBtn").click(function(){
		    	var index = parseInt($(this).attr("index"));
		    	var price = parseInt($(this).attr("price"));
		    	var quantity = parseInt($("#quantity_"+index).val());
		    	console.log("index="+index+",price="+price+",quantity="+quantity);
		    	if(quantity >= 0){
		    		$("#quantity_"+index).val(quantity+1);
		    		var remainPrice = parseInt($('#drag div.cart .total').html().split('$')[1]) + price;
		    		$('#drag div.cart .total').html('Total: $'+remainPrice);
		    	}
		    });
		}
	});
	$('#drag .item').draggable({
		revert:true,
		proxy:'clone',
		onStartDrag:function(){
			$(this).draggable('options').cursor = 'not-allowed';
			$(this).draggable('proxy').css('z-index',10);
		},
		onStopDrag:function(){
			$(this).draggable('options').cursor='move';
		}
	});
	$('#drag .cart').droppable({
		onDragEnter:function(e,source){
			$(source).draggable('options').cursor='auto';
		},
		onDragLeave:function(e,source){
			$(source).draggable('options').cursor='not-allowed';
		},
		onDrop:function(e,source){
			var name = $(source).find('p:eq(0)').html();
			var price = $(source).find('p:eq(1)').html();
			addProduct(name, parseFloat(price.split('$')[1]));
		}
	});
    

	
	// tabs test
	$('#nav-tabs').tabs({
		selected: 1,
		onSelect: function (title,index) {
            console.log('onSelect=>title='+title);
            console.log('onSelect=>index='+index);
        },
		onUnselect: function (title,index) {
            console.log('onUnselect=>title='+title);
            console.log('onUnselect=>index='+index);
        },
		onBeforeClose: function (title,index) {
            console.log('onBeforeClose=>title='+title);
            console.log('onBeforeClose=>index='+index);
        },
		onClose: function (title,index) {
            console.log('onClose=>title='+title);
            console.log('onClose=>index='+index);
        },
		onAdd: function (title,index) {   //在添加一个新选项卡面板的时候触发。
            console.log('onAdd=>title='+ title + '|' + 'index=' + index);
        },
		onContextMenu: function (e,title,index) {
			console.log('onContextMenu=>e='+e);
            console.log('onContextMenu=>title='+title);
            console.log('onContextMenu=>index='+index);
        }
	});
});

function saveRowPrice(name, quantity, price, index) {
	$('#cartcontent').datagrid('endEdit', index);
}

function cancelRowPrice(name, quantity, price, index) {
	$('#cartcontent').datagrid('cancelEdit', index);
}

function editRowPrice(name, quantity, price, index) {
	$('#cartcontent').datagrid('beginEdit', index);
}

function deleteRowPrice(name, quantity, price, index) {	
	var _quantity = parseInt($("#quantity_"+index).val());
	console.log("price="+$('#drag div.cart .total').html().split('$')[1]+","+_quantity*price);
	var remainPrice = $('#drag div.cart .total').html().split('$')[1] - _quantity*price;
	$('#drag div.cart .total').html('Total: $'+remainPrice);
	$('#cartcontent').datagrid('deleteRow', index);
}

function addProduct(name,price){
	function add(){
		for(var i=0; i<dataDrag.total; i++){
			var row = dataDrag.rows[i];
			if (row.name == name){
				row.quantity += 1;
				return;
			}
		}
		dataDrag.total += 1;
		dataDrag.rows.push({
			name:name,
			quantity:1,
			price:price
		});
	}
	add();
	totalCostDrag += price;
	$('#drag #cartcontent').datagrid('loadData', dataDrag);
	$('#drag div.cart .total').html('Total: $'+totalCostDrag);
}

function showcontent(language) {
	$('#content').html('Introduction to ' + language + ' language');
}

function showMessage(title, msg) {
	$.messager.show({
		title: title,
		msg: msg,
		timeout: 4000,
		width: 200,
		height: 10,
		showType: 'show',
		style:{
			right: 0,
			left: '',
			top: document.body.scrollTop+document.documentElement.scrollTop,
			bottom: ''
		}
	});
}

function test() {
	
	$('#myEasyui').datagrid('checkAll');
	var row = $('#myEasyui').datagrid('getSelected');
	
	var nameOption = $('#myEasyui').datagrid('getColumnOption','name');
	for(k in nameOption){
		//console.log("test=>k="+k+",v="+nameOption[k]);
	}
	
	var loadData = $('#myEasyui').datagrid('getData');
	for(k in loadData){
		//console.log("test=>k="+k+",v="+loadData[k]);
	}
	
	var getRowsIndex = $('#myEasyui').datagrid('getRows', row);
	console.log("test=>getRowsIndex="+getRowsIndex);
	
	var getCheckedRows = $('#myEasyui').datagrid('getChecked');
	console.log("test=>getCheckedRows="+getCheckedRows);
	
	var getSelectedRows = $('#myEasyui').datagrid('getSelected');
	console.log("test=>getSelectedRows="+getSelectedRows);
	
	$('#myEasyui').datagrid('highlightRow', 1);	
}

function getRowIndex(target){
	var tr = $(target).closest('tr.datagrid-row');
	return parseInt(tr.attr('datagrid-row-index'));
}

function editrow(id,index){
	//$('#myEasyui').datagrid('beginEdit', getRowIndex(target));
	console.log("id="+id+",index="+index);
	$('#myEasyui').datagrid('beginEdit', index);
}

function deleterow(id,index){
	$.messager.confirm('Confirm','Are you sure delete?',function(r){
		if (r){
			$('#myEasyui').datagrid('deleteRow', index);
		}
	});
}

function saverow(id,index){
	//var currentRow = $('#myEasyui').datagrid('selectRow',index);
	//console.log("name="+currentRow.name);
	//var inserted = $('#myEasyui').datagrid('getChanges','inserted');
    //var updated  = $('#myEasyui').datagrid('getChanges','updated');
	$('#myEasyui').datagrid('endEdit', index);
}

function cancelrow(id,index){
	$('#myEasyui').datagrid('cancelEdit', index);
}

function insert(){
	var index = 0;
	var row = $('#myEasyui').datagrid('getSelected');
	if (row){
		index = $('#myEasyui').datagrid('getRowIndex', row);
	} else {
		index = 0;
	}
	$('#myEasyui').datagrid('insertRow', {
		index: index,
		row:{
			id: 50+parseInt(index),
	    	name: 'newName',
	    	age: 30,
	    	email: 'newName@267.com',
	    	country: 'China'
		}
	});
	$('#myEasyui').datagrid('selectRow',index);
	$('#myEasyui').datagrid('beginEdit',index);
}

var index = 0;

function addRow() {
	index++;
    $('#myEasyui').datagrid('appendRow',{
    	id: 50+parseInt(index),
    	name: 'newName',
    	age: 30,
    	email: 'newName@267.com',
    	country: 'China'
    });
}

function addRowByIndex(indexRow) {
	index++;
	$('#myEasyui').datagrid('insertRow',{
		index: indexRow,
		row: {
			id: 50+parseInt(index),
	    	name: 'newName',
	    	age: 30,
	    	email: 'newName@267.com',
	    	country: 'China'
		}
	});
}

function addPanel() {
	index++;
	$('#nav-tabs').tabs('add',{
	    id: 'id' + index,
		title: 'New Tab ' + index,
		content: 'Tab Body ' + index,
		closable: true,
		tools:[{
			iconCls: 'icon-mini-refresh',
			handler: function(){
				//alert('refresh');
			}
		}]
	});
}

function removePanel(){
	var tab = $('#nav-tabs').tabs('getSelected');
	if (tab){
		var index = $('#nav-tabs').tabs('getTabIndex', tab);
		console.log("tab index=" + index);
		$('#nav-tabs').tabs('close', index);
	}
}
