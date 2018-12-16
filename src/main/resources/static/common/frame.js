var pageTitle = '显示第{0}到第{1}条记录，共{2}条记录';

/**
 * 
 * @param dataList
 * @returns
 */
function selectAppend(
		items
	){
	var id = items['id'],dataList = items['data'],key = items['key'],value = items['value'],item = items['item'],cascade = items['cascade'];
	if(id){
		item = $('#'+id);
	}
	//是否有级联
	if(cascade){
		$("#"+id +" option").remove();
		$("#"+id).trigger("chosen:updated");
	}
	$.each(dataList,function(index,data){
		item.append('<option value="'+data[key]+'">'+
				data[value]+
		'</option>  ');
	});
	item.trigger("chosen:updated");
}
/**
 * 传输刷新分页的数据。只支持继承的分页
 * @param page
 * @returns
 */
function refreshPage(page){
	$('.pageHelper').html('');
	$('.pageHelper').html('<div><span class="pageInfo"></span></div><div><ul class="pageLimit" ></ul></div>');
	page['currentPage'] = 1;
	initReqPage(page);
}
/**
 * 请求page分页
 * 传入
 * {
 * 		url:'请求地址',
 * 		useToken:'是否需要权限',
 * 		successFn:function(resp){}请求的方法。返回数据对象
 * 		errorMsgFn:function(resp){}数据异常
 * 		currentPage:当前第几页 非必填
 * 		numberOfPages:每页显示多少条 非必填
 * }
 */
function initReqPage(page){
	var defaultCurrentPage = 1,//默认页数
		defaultNumberOfPages = 5,//默认显示页数
		req = {},currentPage = page['currentPage'],numberOfPages = page['numberOfPages'],
		data = page.queryDataFn();
	if(!currentPage)
		currentPage = defaultCurrentPage;
	if(!numberOfPages)
		numberOfPages = defaultNumberOfPages;
	data['start'] = currentPage;
	data['rows'] = numberOfPages;
	req['reqJSON'] = {
		type : 'get',
		url: page['url'],
		data : data
	};
	req['useToken'] = page['useToken'];
	req['successFn'] = function(respData){
		if(respData.code == SUCCESS_CODE){
			var pageData = {};
			pageData['total'] = respData.data.totalSize;
			pageData['result'] = respData.data.result;
			pageData['currentPage'] = currentPage;
			pageData['numberOfPages'] = numberOfPages;
			pageData['successFn'] = page['successFn'];
			pageData['reqJSON'] = req['reqJSON'];
			pageData['queryDataFn'] = page['queryDataFn'];
			pageData['useToken'] = req['useToken'];
			initPage(pageData); 
		}else{
			page.errorMsgFn(respData);
		}
	};
	ajaxRequest(req);
}
function initPage(page){
	var total = page['total'],
		length = page['result'].length,//数据
		totalPage = total /length + (total % length != 0 ? 1: 0),//总页数
		currentPage = page['currentPage'],//第几页
		numberOfPages = page['numberOfPages'];//显示多少页
	if(length == 0){
		$('.pageInfo').html('没有搜索到相关记录');
		return;
	}
	var _startPage = (currentPage-1)*numberOfPages,
	_endPage = _startPage+length;
	if(totalPage  == 1){
		$('.pageInfo').html(
				pageTitle.
				replace('{0}',_startPage+1).
				replace('{1}',_endPage).
				replace('{2}',total));
//		 $('.pageLimit').bootstrapPaginator(options);
		 page.successFn(page.result);
		return;
	}
	var options = {
		bootstrapMajorVersion:3,
		alignment:"right",
		//	shouldShowPage:true,//是否显示该按钮
	    currentPage: currentPage, //	设置当前页.
	    totalPages: totalPage, //设置总页数.
		numberOfPages:numberOfPages, //	设置控件显示的页码数.即：类型为"page"的操作按钮的数量。
		itemTexts: function (type, page, current) {//设置显示的样式，默认是箭头
	        switch (type) {
	            case "first":
	                return "<<";
	            case "prev":
	                return "<";
	            case "next":
	                return ">";
	            case "last":
	                return ">>";
	            case "page":
	                return page;
	        }
	    }, 
	    onPageClicked: function (event, originalEvent, type, start) 
	    {
		    	var _startPage = (start-1)*numberOfPages,
				_endPage = _startPage+length;
		    	$('.pageInfo').html(
		    			pageTitle.
		    			replace('{0}',_startPage+1).
		    			replace('{1}',_endPage).
		    			replace('{2}',total));
		    	var req = {},queryData;
		    	req['reqJSON'] = page['reqJSON'];
		    	queryData = page.queryDataFn();
		    	queryData['start'] = start;
		    	queryData['rows'] = numberOfPages;

		    	req['reqJSON']['data'] = queryData;
		    	req['useToken'] = page['useToken'];
		    	req['successFn'] = function(respData){
		    		if(respData.code == SUCCESS_CODE){
		    			page.successFn(respData.data.result);
		    		}
	    		};
	    		
	    		ajaxRequest(req);
	    }
	}
	$('.pageInfo').html(
			pageTitle.
			replace('{0}',_startPage+1).
			replace('{1}',_endPage).
			replace('{2}',total));
	 $('.pageLimit').bootstrapPaginator(options);
	 page.successFn(page.result);
}

/**
 * 修改页面--》反选
 * @param id 页面下拉ID
 * @param data 显示数据
 * @param body 窗口对象
 * @param vlaue 页面的key值
 * @returns
 */
function updSelVal(items){
	var id = items['id'],body = items['body'],dataList = items['data'],value = items['value'],key = items['key'],name = items['name'];
	var idVal = body.find('#'+id);
	idVal.chosen();
	var item = {
			'item':idVal,
			'data':dataList,
			key:key ?key:'parmname',
			value:name ?name:'parmvalue'
		};
	selectAppend(item);
	idVal.val(value);
	idVal.trigger("chosen:updated");
}
function initSelectData(items){
	var meItem,id = items['id'],body = items['body'],typeKey = items['typeKey'],cascade = items['cascade'],key = items['key'],value = items['value'];
	meItem = body ? body : $('#'+id);
	meItem.chosen();
	items['item'] = meItem;
	items['key'] = key ? key:'parmname';
	items['value'] = value ? value:'parmvalue';
	selectAppend(items);
	if(items.successFn)
		items.successFn(meItem);
}
/**
 * 适用于List和add页面 初始化下拉  $("#tbParBudCode option[value='" + $("#hidBudCodeID").val() + "']").attr("disabled", "disabled"); 
 */
function initSelect(items){
	var meItem,id = items['id'],body = items['body'],typeKey = items['typeKey'],cascade = items['cascade'], removeParmName = items['removeParmName'],
	reqJSON = items['reqJSON'],
	key = items['key'],value = items['value'];
	meItem = body ? body : $('#'+id);
	meItem.chosen();
	var req = {};
	if(reqJSON){
		req['reqJSON'] = reqJSON;
	}else{
		req['reqJSON'] = {
			type : 'get',
			url : DICT_URL+'gb/gbdict/queryGbDictByType',
			data:{parmtype:typeKey,removeParmName:removeParmName}
		};
	}
	req['useToken'] = true;
	req['successFn'] = function(respData){
		if(respData.code == SUCCESS_CODE){
			var dicData = respData.data;
			var item = {
				'id':id,
				'item':body,
				'data':dicData,
				key: key ? key : 'parmname',
				value: value ? value : 'parmvalue',
				cascade:cascade
			};
			selectAppend(item);
			if(items.successFn)
				items.successFn(dicData);
		}
	};
	ajaxRequest(req);
}



