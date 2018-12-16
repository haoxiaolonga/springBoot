


/**
 * 
 * @param tableId
 * 			初始化得tableId
 * @param url
 * 			请求得URL对象，必填
 * @param columns
 * 			需要初始化得咧，必填
 * @param pagination
 * 			是否分页，可不填。默认不分页
 * @param queryParams
 * 			查询得方法，可不填
 * @param responseHandler
 * 			请求通用方法。可不填
 * 		table = {
 * 				'id':'你所需要得id',
 * 				'url':'你所需要调用得url',
 * 				'pagination':'是否使用分页，true代表使用，false不使用，默认不使用分页',
 * 				queryParams:'查询得方法，可不填',
 * 				responseHandler:'请求通用方法。可不填'
 * 		};
 * 
 * @returns
 */
function initTable(table){
	var bootstrapTableConfig = {};
	bootstrapTableConfig['method'] = 'get';
	bootstrapTableConfig['dataType'] = 'json';
	bootstrapTableConfig['contentType'] = 'application/x-www-form-urlencoded';
	bootstrapTableConfig['url'] = table['url'];
	bootstrapTableConfig['dataField'] = 'rows';
	bootstrapTableConfig['sidePagination'] = 'server';
	if(table['pagination']){
		bootstrapTableConfig['pagination'] = true;
		bootstrapTableConfig['pageSize'] = 10;
		bootstrapTableConfig['pageNumber'] = 1;
		bootstrapTableConfig['pageList'] = [10,20,30,40];
		//自带分页得时候默认分页条件
		if(!table['queryParams']){
			bootstrapTableConfig['queryParams'] = queryBaseParams;
		}
	}
	if((typeof table.useToken) ==  "undefined" || table.useToken){
		bootstrapTableConfig['ajaxOptions']={};
		bootstrapTableConfig['ajaxOptions']['headers'] = {
			 'Authorization': 'Bearer '+getSessionData('userToken')
	    }
	}
	
	if(table['queryParams']){
		//存在分页
		bootstrapTableConfig['queryParams'] = table['queryParams'];
	}
	if(table['responseHandler']){
		bootstrapTableConfig['responseHandler'] = table['responseHandler'];
	}else{
		bootstrapTableConfig['responseHandler'] = responseBaseHandler;
	}
	if(table['columns']){
		bootstrapTableConfig['columns'] = table['columns'];
	}
	if(table['rowStyle']){
		bootstrapTableConfig['rowStyle'] = table['rowStyle'];
	}
	if(table['events']){
		$.each(table['events'],function(key,value){
			bootstrapTableConfig[key] = value;
		})
	}
	$('#'+table['id']).bootstrapTable(bootstrapTableConfig)
}

/**
 * 初始化树
 * @param table
 * @returns
 */
function initTableTree(table){
    var ext={};
    if((typeof table.useToken) ==  "undefined" || table.useToken){
    	ext.ajaxOptions={};
    	ext.ajaxOptions.headers = {
			 'Authorization': 'Bearer '+getSessionData('userToken')
//			 'Action':'getToken'
	    }
	}
    var expandTreeDefault={};
    var defaults={
    		dataField:'rows',
            pagination : false,
            treetable : true,
            dataType:'json',
            type:'get',
            idField:'id',
            parentid:'parentid',
            responseHandler:responseBaseHandler,
            onTreeTableExpand:function(field, row){
            	if(typeof opDefault.expandHandler=='undefined'
               	 || typeof opDefault.expandHandler!='function'){
            		if(typeof opDefault.queryParams!="undefined"){
           				expandTreeDefault.data=$.extend({parentid:row[opDefault.idField]},opDefault.queryParams);
           			}else{
           				expandTreeDefault.data={parentid:row[opDefault.idField]};
           			}
            		var req = {};
            		req.reqJSON=expandTreeDefault;
            		req.successFn=expandTreeDefault.successFn;
            		ajaxRequest(req);
            	}else{
           			opDefault.expandHandler(field, row);
           		}
            },
            sidePagination : "server"
    };
    var opDefault = $.extend({},defaults,table);
    opDefault= $.extend({},opDefault,ext);
	$('#'+table.id).bootstrapTable(opDefault);
	var $table = $('#'+table.id);
	expandTreeDefault=$.extend({},{
		successFn:function(data){
			var obj = responseBaseHandler(data);
			if(obj.success){
				$table.bootstrapTable('addTreeNode', {row: obj.rows}); 
			}
		}
	},opDefault);
	
}

function initJQTreeTable(config){
	 if(config.columns!=null){
		$('#'+config.id).html('');
		var html="<thead>";
		for(var i=0;i<config.columns.length;i++){
			var column = config.columns[i];
			var field=!column.field?'':column.field;
			if(typeof column.checkbox!='undefined' && column.checkbox){
				html+='<th data-field="'+field+'" class="grid-header"><input type="checkbox" class="allCheck"/></input></th>'
			}else{
				html+='<th data-field="'+field+'"  class="grid-header">'+column.title+'</th>'
			}
		}
		html+="</thead>"
		$('#'+config.id).append(html);
	}
$('#'+config.id).append("<tbody id='"+config.id+"_body'></tbody>");
var defaults={
	    		   dataType:'json',
	               type:'get',
	               idField:'id',
	               onCheckCascade:false,
	               onUnCheckCascade:false,
	               onCheckParentCascade:false,
	               onUnCheckParentCascade:false,
	               successFn:function(data){
	            	var realData=[];
	            	var realHtml=[];
	       			var obj = responseCommonBaseHandler(data);
	       			if(obj.success){
	       				if(data!=null){
	       					var index=0;
		       				for(var i=0;i<obj.data.length;i++){
		       					var row = obj.data[i];
		       					getTreeGridHtml(config,realData,realHtml,row,'');
		       				}
	       				}
	       			}
	       		$('#'+config.id).data("rows",realData);
	       		$('#'+config.id).find("tbody").append(realHtml.join(' '));
       		    $('#'+config.id).treegrid($.extend({},{
       		    	initialState:'collapse',
                    expanderExpandedClass:(typeof config.expanderExpandedClass!="undefined")?config.expanderExpandedClass:'glyphicon glyphicon-minus',
                    expanderCollapsedClass:(typeof config.expanderCollapsedClass!="undefined")?config.expanderCollapsedClass:'glyphicon glyphicon-plus'
                },config));
	       		 $('#'+config.id).find('.allCheck').click(function(){
	       			if($(this).prop("checked")){
	       				$('#'+config.id).find('input[type="checkbox"][data-index]').prop('checked',true);
	       				if(typeof config.onCheckAll!='undefined'){
	       					var rows = $('#'+config.id).data("rows");
		       				config.onCheckAll(rows);
						}
	       			}else{
	       				$('#'+config.id).find('input[type="checkbox"][data-index]').prop('checked',false);
	       				if(typeof config.onUnCheckAll!='undefined'){
	       					var rows = $('#'+config.id).data("rows");
		       				config.onUnCheckAll(rows);
						}
	       			}
	       		 })
	       		 $('#'+config.id).find('tbody').find('input[type="checkbox"][data-index]').click(function(){
	       			if($(this).prop("checked")){
                        tiggerCheck(config,this);
                        tiggerChildCheck(config,this);
                        tiggerParentCheck(config,this);
	       			}else{
	       				tiggerUnCheck(config,this);//触发非选中事件
	       				tiggerChildUnCheck(config,this);
	       				triggerUnCheckParentCascade(config,this)
	       			}
	       		 })
	       	}
	    };
	    if((typeof config.useToken) ==  "undefined" || config.useToken){
	    	defaults.headers = {
				 'Authorization': 'Bearer '+getSessionData('userToken')
//				 'Action':'getToken'
		    }
		}
	    var req = {};
		req.reqJSON=$.extend({},defaults,config);
		req.successFn=defaults.successFn;
		ajaxRequest(req);
}
/**
 * 触发check事件
 * @returns
 */
function tiggerCheck(config,obj){
	 if(typeof config.onCheck!='undefined'){
			var rows = $('#'+config.id).data("rows");
			var index = $(obj).attr("data-index");
			var tr = $('#'+config.id).find('tbody').find('tr[data-index="'+index+'"]');
			config.onCheck(index,rows[index],tr);
	 }
}

/**
 * 触发子节点check事件
 * @returns
 */
function tiggerChildCheck(config,obj){
	   if(config.onCheckCascade){//子级联非选中
			var rows = $('#'+config.id).data("rows");
			var index = $(obj).attr("data-index");
			var children =
				$('#'+config.id).find('tbody').find('tr[data-index="'+index+'"]').
				treegrid('getChildNodes');
			$.each(children,function(i,trObj){
				var tCheck = $(trObj).find('input[type="checkbox"][data-index="'+$(trObj).attr("data-index")+'"]');
				$(tCheck).prop("checked",true);
				tiggerCheck(config,tCheck);
				tiggerChildCheck(config,trObj);
			})
		}
}

/**
 * 触发unCheck事件
 * @returns
 */
function tiggerUnCheck(config,obj){
	if(typeof config.onUnCheck!='undefined'){
		var rows = $('#'+config.id).data("rows");
		var index = $(obj).attr("data-index");
		config.onUnCheck(index,rows[index]);
}
}
/**
 * 触发子unCheck事件
 * @returns
 */
function tiggerChildUnCheck(config,obj){
	if(config.onUnCheckCascade){//子级联非选中
		var rows = $('#'+config.id).data("rows");
		var index = $(obj).attr("data-index");
		var children =
			$('#'+config.id).find('tbody').find('tr[data-index="'+index+'"]').
			treegrid('getChildNodes');
		$.each(children,function(i,trObj){
			var tCheck = $(trObj).find('input[type="checkbox"][data-index="'+$(trObj).attr("data-index")+'"]');
			$(tCheck).prop("checked",false);
			tiggerUnCheck(config,tCheck);
			tiggerChildUnCheck(config,trObj);
		})
	}
}
/**
 * 触发父unCheck事件
 * @returns
 */
function triggerUnCheckParentCascade(config,obj){
	 if(config.onUnCheckParentCascade){//父级联非选中
			var rows = $('#'+config.id).data("rows");
			var index = $(obj).attr("data-index");
			var parentNode =
				$('#'+config.id).find('tbody').find('tr[data-index="'+index+'"]').
				treegrid('getParentNode');
			if(parentNode != null){
				var children = $(parentNode).treegrid('getChildNodes');
				var flag = true;
				$.each(children,function(i,trObj){
					var tCheck = $(trObj).find('input[type="checkbox"][data-index="'+$(trObj).attr("data-index")+'"]');
					if($(tCheck).prop("checked")){
						flag = false;
					};
				})
				if(flag){
				   index = $(parentNode).attr("data-index");
					var check = $(parentNode).find('input[type="checkbox"][data-index="'+index+'"]');
					check.prop("checked",false);
					tiggerUnCheck(config,check);
					triggerUnCheckParentCascade(config,check);
				}
		}
	}
}

/**
 * 级联选中父亲
 * @returns
 */
function tiggerParentCheck(config,obj){
	if(config.onCheckParentCascade){
		var rows = $('#'+config.id).data("rows");
		var index = $(obj).attr("data-index");
		var parent =
			$('#'+config.id).find('tbody').find('tr[data-index="'+index+'"]').
			treegrid('getParentNode');
		if(parent!=null){
			var tCheck = $(parent).find('input[type="checkbox"][data-index="'+$(parent).attr("data-index")+'"]');
			$(tCheck).prop("checked",true);
			tiggerCheck(config,tCheck);
			tiggerParentCheck(config,parent);
		}
	}
	
}	

function getChildHtml(config,realData,realHtml,obj,pclass){
		var children = obj.children;
		if(children!=null && children.length>0){
			for(var j=0;j<children.length;j++){
				var child = children[j];
				getTreeGridHtml(config,realData,realHtml,child,pclass);
			}
		}
}
/**
 * 获取一行html代码
 * @param columns
 * @param row
 * @returns
 */
function getTreeGridHtml(config,realData,realHtml,row,pclass){
	var html="<tr data-index='"+realHtml.length+"' data-pk='"+row[config.idField]+"' class='treegrid-"+realHtml.length+" "+pclass+"'>";
	pclass = "treegrid-parent-"+realHtml.length;
	var columns = config.columns;
	var index=realHtml.length;
	for(var i=0;i<columns.length;i++){
		var column = columns[i];
		var field=!column.field?'':column.field;
		if(typeof column.checkbox!='undefined' && column.checkbox){
			if(typeof row.checked!='undefined' && row.checked){
				html+='<td data-field="'+field+'" class="grid-td"><input data-index="'+index+'" checked="checked" type="checkbox" class="rowcheck"/></input></td>'
			}else{
				html+='<td data-field="'+field+'" class="grid-td"><input data-index="'+index+'" type="checkbox" class="rowcheck"/></input></td>'
			}
		}else{
			var result  = '';
			if(typeof column.formatter=='function'){
				var tempResult=column.formatter(result,row,index);
				if(typeof tempResult!='undefined'){
					result = tempResult;
				}
			}else{
				result = row[field];
			}
			html+='<td data-field="'+field+'"  class="grid-td">'+result+'</td>'
		}
	}
	html+="</tr>";
	realData.push(row);
	realHtml.push(html);
	getChildHtml(config,realData,realHtml,row,pclass);
}

function getJQTreeGridCheck(id){
	var checks = $('#'+id).find('tbody').find('input[type="checkbox"][data-index]:checked');
	var allRows = $("#"+id).data("rows");
	var rows=[];
	$.each(checks,function(i,trObj){
		var index = $(trObj).attr("data-index");
		rows.push(allRows[index]);
	})
	return rows;
}

//请求服务数据时所传参数
function queryBaseParams(params){
    return {
    	rows : params.limit, //每一页的数据行数，默认是上面设置的10(pageSize)
        start : params.offset/params.limit+1 //当前页面,默认是上面设置的1(pageNumber)
//             param : "Your Param" //这里是其他的参数，根据自己的需求定义，可以是多个
    }
}

//请求成功方法
function responseBaseHandler(result){
    var errcode = result.code;//在此做了错误代码的判断
	if(errcode != SUCCESS_CODE){
	    alert("错误代码" + errcode);
	    return {success:false};
	}
	//如果没有错误则返回数据，渲染表格
	return {
		success:true,
	    total : result.data.totalSize, //总页数,前面的key必须为"total"
	    rows : result.data.result //行数据，前面的key要与之前设置的dataField的值一致.
    };
}


//请求成功通用的返回
function responseCommonBaseHandler(result){
   var errcode = result.code;//在此做了错误代码的判断
	if(errcode != SUCCESS_CODE){
	    alert("错误代码" + errcode);
	    return {success:false};
	}
	//如果没有错误则返回数据，渲染表格
	return {
		success:true,
	    data : result.data
  };
}

//刷新表格数据,点击你的按钮调用这个方法就可以刷新
function refreshGrid(tableId,tableUrl) {
    $('#'+tableId).bootstrapTable('refresh', {url: tableUrl});
}