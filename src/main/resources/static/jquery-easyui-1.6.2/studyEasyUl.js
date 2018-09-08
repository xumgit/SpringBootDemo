
$(document).ready(function(){
	// datagrid test
    $('#myEasyui').datagrid({
        //width: 400,                 //设置宽度
        url: '/easyui/getAuthor',       //远程加载数据地址
        title: '用户列表',           //面板属性，添加标题
        iconCls: 'icon-search',     //添加图标
        columns: [[                 //设置要显示表格数据
            {
                field: 'name',      //field，对应远程JSON 数据里的对象属性，也就是数据库字段
                title: '姓名',      //title，定义数据的标题
				halign:'center'
            },
            {
                field: 'age',      //field，对应远程JSON 数据里的对象属性，也就是数据库字段
                title: '年龄',      //title，定义数据的标题
				halign:'center'
            },
            {
                field: 'email',     //field，对应远程JSON 数据里的对象属性，也就是数据库字段
                title: '邮件',      //title，定义数据的标题
				halign:'center'
            },
            {
                field: 'country',      //field，对应远程JSON 数据里的对象属性，也就是数据库字段
                title: '国家',   //title，定义数据的标题
				halign:'center'
            }
        ]],
		pagination: true,
		pageNumber: 1,             //设置分页时初始化页码
        pageSize: 5,               //设置分页时设置每页多少条
        pageList: [5,10,15],          //设置可选每页显示条数
		pagePosition: 'bottom',
		sortName: 'country',        //设置哪些列可以进行排序。默认为 null。值为field的值也就是可以排序的字段，这个值会发送到数据库
        sortOrder: 'DESC',        //设置列排序的顺序,ASC 和 DESC，默认是 ASC。这个值会发送到数据库
		queryParams:{           //设置请求远程数据发送的额外数据
            id: 1
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

var index = 0;

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
