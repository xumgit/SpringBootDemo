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
	
$(document).ready(function(){
			
	var windowWidth = $(window).width();
	var contentWidth = (windowWidth-32)*0.20;
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
		pagination: true,
		pageNumber: 1,             //设置分页时初始化页码
        pageSize: 5,               //设置分页时设置每页多少条
        pageList: [5,10,15],          //设置可选每页显示条数
		pagePosition: 'bottom',
		sortName: 'id',        //设置哪些列可以进行排序。默认为 null。值为field的值也就是可以排序的字段，这个值会发送到数据库
        sortOrder: 'ASC',        //设置列排序的顺序,ASC 和 DESC，默认是 ASC。这个值会发送到数据库
		queryParams:{           //设置请求远程数据发送的额外数据
            id: 1
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
			console.log("name="+row.name+",email="+row.email+",country="+row.country);
			// update database
			row.editing = false;
			$(this).datagrid('refreshRow', index);
		},
		onCancelEdit: function(index,row){
			console.log("onCancelEdit=>index="+index);
			row.editing = false;
			$(this).datagrid('refreshRow', index);
		}
    });
    
    //数据表格工具栏1
    $('#tianjia').linkbutton({  //将工具栏里的添加执行按钮方法
        iconCls: 'icon-add',     //设置图标
        plain: true              //按钮扁平化
    });
    $('#xiougai').linkbutton({  //将工具栏里的修改执行按钮方法
        iconCls: 'icon-edit',    //设置图标
        plain: true              //按钮扁平化
    });
    $('#shanchu').linkbutton({  //将工具栏里的删除执行按钮方法
        iconCls: 'icon-remove',  //设置图标
        plain: true              //按钮扁平化
    });
    //数据表格工具栏2
    //$('.shj1').datebox();   //查询日期输入框
    //$('.shj2').datebox();   //查询日期输入框
    $('.chx').linkbutton({  //查询按钮
        iconCls:"icon-search"
    });
      
    //查询功能
    $('.chx').click(function () {   //点击查询后执行obj对象里的search方法
        obj.search();
    });
    var obj = {    //obj对象  
    	panduan: false,
        search: function () {
            $('#myEasyui').datagrid('load',{                         //执行数据表格的load方法提交数据
            	searchPhrase: $.trim($('#searchPharse').val())
            });
        }
    };
    
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
