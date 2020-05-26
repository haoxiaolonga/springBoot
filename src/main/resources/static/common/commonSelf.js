var SUCCESS_CODE = 200,
	SSO_URL = "@SSO_URL@";
/**
 * 获取session用户
 * @returns object
 */
function getSessionUser(){
	var user=null;
	var userStr = getSessionData('user');
	if($.trim(userStr)!=''){
		user = JSON.parse(userStr);
	}
	return user;
}
/**
 * 获取用户权限
 * @returns arrays
 */
function getUserPower(){
	var userPower=[];
	var userStr = getSessionData('userPower');
	if($.trim(userStr)!=''){
		userPower = JSON.parse(userStr);
	}
	return userPower;
}

function checkPower(funid){
	var powers = getUserPower();
	if(powers!=null){
		for(var i=0;i<powers.length;i++){
			if(powers[i].funid==funid){
				return true;
			}
		}
	}
	return false;
}

function checkPowerAndJump(funid){
	var fun = funid.split(",");
	var flag = false;
	for(var i=0;i<fun.length;i++){
		if(checkPower(fun[i])){
			flag = true;
			break;
		}
	}
	if(!flag){
		window.location.href="/403.html";
	}
}
/**
 * 清除session
 * @returns void
 */
function clearSession(){
	if(window.sessionStorage){
		window.sessionStorage.clear(); 
	}else{
		var cookies = document.cookie ? document.cookie.split('; ') : [];
		for (var i = 0, l = cookies.length; i < l; i++) {
			var parts = cookies[i].split('=');
			var name = decodeURIComponent(parts.shift());
			if (name) {
				$.removeCookie(name);
			}
		}
	}
}

/**
 * 存储session数据
 * @param key
 * @param value
 * @returns
 */
function storeSessionData(key,value){
	if(window.sessionStorage ){
		window.sessionStorage.setItem(key, value); 
	}else{
		$.cookie(key,value,{ expires: 7, path: '/' });
	}
}
/**
 * 获取session数据
 * @param key
 * @param value
 * @returns
 */
function getSessionData(key,value){
	var value = '';
    if(window.sessionStorage ){
    	value =  window.sessionStorage.getItem(key);
	}else{
		value =  $.cookie(key);
	}
    if(key=='userToken' && $.trim(value)==''){
    	window.location.href='/login.html';
    }
    return value;
}
	
function checkFileType(fileFiledId){
	 var flag = true;
	 var fileUrl = $("#"+fileFiledId).val();
	 if($.trim(fileUrl)!=''){
		 var fileExtension = fileUrl.split('.').pop().toLowerCase();
		 if((UPLOAD_FILE_ALLOWED_TYPES != '*') &&
			        !UPLOAD_FILE_ALLOWED_TYPES.match(fileExtension)){
			 flag=false;
	     }
	 }
	 return flag;
}
/**
 * 发送单个文件
 * @param fileFiledId 文件域id
 * @param options 其他操作参数
 * @returns
 */
function sendSingleFile(fileFiledId,options){
   var result = {"code":"500","message":"上传文件失败！"};
   var fd=new FormData();  
   fd.append("file",$("#"+fileFiledId).get(0).files[0]);
   if(typeof options=="undefined" || typeof options!='object'){
	   options={};
   }
   if((typeof options.useToken) ==  "undefined" || options.useToken){
	    options['headers'] = {
			 'Authorization': 'Bearer '+getSessionData('userToken')
//			 'Action':'getToken'
	    }
	}
   if((typeof options.dataParams) ==  "object"){
	   $.each(options.dataParams, function(key, val) {
		   fd.append(key,options.dataParams[key]);
		});
   }
   var defaultConfig={  
		      url:UPLOAD_FILE_URL,  
		      type:"POST",  
		      async: false,
		      processData: false,  
		      contentType:false,  
		      dataType:'json',
		      data: fd,  
		      xhr: function() {
		         myXhr = $.ajaxSettings.xhr();
		         if(myXhr.upload){
		            myXhr.upload.addEventListener('progress',function(evt){  
		               var percentComplete = Math.round(evt.loaded*100 / evt.total);  
		            }, false);
		         }  
		         return myXhr;  
		      },  
		      success:function(data){
		    	   result=data;
			    }  
			 }
   $.ajax($.extend({},defaultConfig,options)); 
	return result;
}
	
	   
/**
 * 发送req请求
 * @param req
 * @returns
 */
function ajaxRequest(req){
	var	ajaxConfig = $.extend(true,req.reqJSON,{});
	if(ajaxConfig['type'] == 'post' || ajaxConfig['type'] == 'put'){
		ajaxConfig['contentType'] = 'application/json;charset=utf-8';
	}
	if(req['contentType']){
        ajaxConfig['contentType'] = req['contentType'];
	}
	ajaxConfig['error'] = function(data){

		debugger;

		var error = $.parseJSON(data.responseText);
		if(typeof error  == undefined){
			layer.msg('系统异常！',{icon:5});
		}else if(error.code == 401){
			refreshToken('Bearer '+getSessionData('userToken'),req);
		}else{
			layer.msg(error.message,{icon:5});
		}
	};
	ajaxConfig['success'] = function(data){
//		if(data.code == SUCCESS_CODE){
			return req.successFn(data);
//		}
	};
	if(req['beforeSendFn']){
	    ajaxConfig['beforeSend'] =  req['beforeSendFn'];
	}else{
	    ajaxConfig['beforeSend'] = function(xhr) {
    		if((typeof req.useToken) ==  "undefined" || req.useToken){
    			xhr.setRequestHeader('Authorization','Bearer '+getSessionData('userToken'));
    //			xhr.setRequestHeader('Action','getToken');
    		}
    	}
	}

	$.ajax(ajaxConfig);
}

function ajaxRequestByAttach(req){
	var	ajaxConfig = $.extend(true,req.reqJSON,{});
	ajaxConfig['error'] = function(data){
		var error = $.parseJSON(data.responseText);
		if(typeof error  == undefined){
			layer.msg('系统异常！',{icon:5});
		}else if(error.code == 401){
			refreshToken('Bearer '+getSessionData('userToken'),req);
		}else{
			layer.msg(error.message,{icon:5});
		}
	};
	ajaxConfig['success'] = function(data){
//		if(data.code == SUCCESS_CODE){
		return req.successFn(data);
//		}
	};
	
	$.ajax(ajaxConfig);
}
function refreshToken(token,req){
	var	ajaxConfig ={};
	ajaxConfig['url'] = API_URL+'accessToken/refresh';
	ajaxConfig['type'] = 'put';
	ajaxConfig['error'] = function(data){
		layer.msg(data.responseText,{icon:5});
	};
	ajaxConfig['success'] = function(data){
		if(data.code == SUCCESS_CODE){
			storeSessionData('userToken',data.data);
			return ajaxRequest(req);
		}
	}
	ajaxConfig['beforeSend'] = function(xhr) {
		xhr.setRequestHeader('Authorization','Bearer '+getSessionData('userToken'));
	}
	$.ajax(ajaxConfig);
}
/**
 * 根据form表单序列化json对象
 * @param form
 * @returns
 */
function serializeForm(form){
	var data = {};
	$(form).serializeArray().map(function(x){
      if (data[x.name] !== undefined) {
                if (!data[x.name].push) {
                    data[x.name] = [data[x.name]];
                }
                data[x.name].push(x.value || '');
            } else {
                data[x.name] = x.value || '';
            }
     });
	return data;
}

/**
 * 获取页面传入的参数
 * @param name
 * @returns
 */
function GetQueryString(name)
{
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)return  unescape(r[2]); return null;
}

function getUrlParam() {
   var url = decodeURI(location.search); //获取url中"?"符后的字串
   var theRequest = new Object();
   if (url.indexOf("?") != -1) {
      var str = url.substr(1);
      strs = str.split("&");
      for(var i = 0; i < strs.length; i ++) {
         theRequest[strs[i].split("=")[0]]=(unescape(strs[i].split("=")[1]));
      }
   }
   return theRequest;
}

try{
$(function(){
	//主动检查页面元素
	$.each($(".checkPower"),function(i,obj){
		var key = $(obj).attr("power");
		if(!checkPower(key)){
			$(obj).remove();
		}
	})
})
}catch(e){}

//时间戳转日期
function timeTransfoDate(time){
	var date =  new Date(time);
    var y = 1900+date.getYear();
    var m = "0"+(date.getMonth()+1);
    var d = "0"+date.getDate();
    return y+"-"+m.substring(m.length-2,m.length)+"-"+d.substring(d.length-2,d.length);
}
 function  formatDateTime (inputTime) {    
    var date = new Date(inputTime);  
    var y = date.getFullYear();    
    var m = date.getMonth() + 1;    
    m = m < 10 ? ('0' + m) : m;    
    var d = date.getDate();    
    d = d < 10 ? ('0' + d) : d;    
    var h = date.getHours();  
    h = h < 10 ? ('0' + h) : h;  
    var minute = date.getMinutes();  
    var second = date.getSeconds();  
    minute = minute < 10 ? ('0' + minute) : minute;    
    second = second < 10 ? ('0' + second) : second;   
    return y + '-' + m + '-' + d+' '+h+':'+minute+':'+second;    
};

/**
 * 格式化日期
 * @param inputTime
 * @returns
 */
function  formatDate (inputTime) {    
    var date = new Date(inputTime);  
    var y = date.getFullYear();    
    var m = date.getMonth() + 1;    
    m = m < 10 ? ('0' + m) : m;    
    var d = date.getDate();    
    d = d < 10 ? ('0' + d) : d;    
    return y + '-' + m + '-' + d;    
};

/**
 * 格式化金额
 */
function formatMoney(s, n)   
{   
   n = n > 0 && n <= 20 ? n : 2;   
   s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";   
   var l = s.split(".")[0].split("").reverse(),   
   r = s.split(".")[1];   
   t = "";   
   for(i = 0; i < l.length; i ++ )   
   {   
      t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");   
   }   
   return t.split("").reverse().join("") + "." + r;   
} 
/**
 * 通过凭证ID获取图片路径 并改变图片路径（仅Table内使用）
 */
function getURLById(id,waterVoucherId){
	var req = {};
	req['reqJSON'] = {
		type:"get",
		url:FILE_URL+"/getById?id="+id
	};
	req['useToken'] = true;
	req['async'] = false;
	req['successFn'] = function(respData){
		$('#'+waterVoucherId).attr('src',FILE_VIEW_URL + respData.data);
	};
	ajaxRequest(req);
}
/**
 * 初始化jqgrid
 */
function initJqGrid(options){
	var default_config = {
		 styleUI : 'Bootstrap',
		"datatype":"json",
	    mtype: "GET",
	    ajaxGridOptions:{
	    	beforeSend:function(xhr){
	    		  if((typeof options.useToken) ==  "undefined" || options.useToken){
	    			  xhr.setRequestHeader('Authorization','Bearer '+getSessionData('userToken'));
	    			}
	    	}
	    },
	    ajaxSubgridOptions:{
	    	beforeSend:function(xhr){
	    		  if((typeof options.useToken) ==  "undefined" || options.useToken){
	    			  xhr.setRequestHeader('Authorization','Bearer '+getSessionData('userToken'));
	    			}
	    	}
	    },
	    "width":document.body.clientWidth-100,
		"beforeRequest" : function() {
			if(this.p.postData.nodeid != null) {
				var nid = new Number(this.p.postData.nodeid);
				if( nid > -1 ) {
					$(this).jqGrid('setGridParam', { postData: { id:this.p.postData.nodeid+""} });
				}
			}
		},
	   jsonReader : { 
		   root: "data",  //数据模型
		   repeatitems: false//如果设为false，则jqGrid在解析json时，会根据name(colmodel 指定的name)来搜索对应的数据元素（即可以json中元素可以不按顺序）
		 },
		"height":"100%",
		"pager":false,  
		"hoverrows":false,
		"viewrecords":false,
		"gridview":true,
		"height":"auto",
		"loadonce":false,
		"scrollrows":false,
		"treeGrid":true,
		// which column is expandable
		"ExpandColumn":"name",
		// datatype
		"treedatatype":"json",
		// the model used
		"treeGridModel": 'adjacency', 
		// configuration of the data comming from server
	    "treeReader" : {    
                parent_id_field: "parentId",    
                leaf_field: "opened"
         },  
		"sortorder":"asc",
		"datatype":"json"
    };
	var newConfig = $.extend({},default_config,options);
	$("#"+options.datagridId).jqGrid(newConfig);
	
}

function loadGbDictToSelect(config){
	 if(config.addDefalut){
	    $('#'+config.id).append("<option value=''>请选择</option>")
	    try{
	    $('#'+config.id).selectpicker('refresh');
	    }catch(e){}
	 }
	var req = {};
	req['reqJSON'] = {
		type : 'get',
		url :  DICT_URL+'gb/gbdict/findGbDictByType',
		data : {parmtype:config.parmtype}
	};
	req['useToken'] = true;
	req['successFn'] = function(respData){
	    if(respData.code == SUCCESS_CODE){
	    	if($.isFunction(config.successFn)){
	    		config.successFn(respData);
	    		if(config.loadTriggerChange){
		    		   $('#'+config.id).trigger('change');
		    	}
			}else{
			   var datas = respData.data;
			   if(config.filterData){
				   datas =  config.filterData(datas);
			   }
			   for(var i=0;i<datas.length;i++){
				   var d = datas[i];
				   var selected='';
				   if(config.defaultValue){
				   	   if(d.parmname== config.defaultValue){
				   	   	   selected="selected";
					   }
				   }
				   $('#'+config.id).append("<option "+selected+" value='"+d.parmname+"'>"+d.parmvalue+"</option>")
			   }
			   try{
			   $('#'+config.id).selectpicker('refresh');
			   }catch(e){}
			   if($.isFunction(config.loadSuccess)){
				   config.loadSuccess(config);
			   }
			   if(config.loadTriggerChange){
	    		   $('#'+config.id).trigger('change');
	    	   }
			}
		 }
	};
	ajaxRequest(req);
}

/**
 * 普通select初始化
 * @param config
 * @returns
 */
function loadGbDictToGeneralSelect(config){
	$('#'+config.id+" option").remove();
	if(config.addDefalut){
		$('#'+config.id).append("<option value=''>请选择</option>")
	}
	var req = {};
	req['reqJSON'] = {
		type : 'get',
		url :  DICT_URL+'gb/gbdict/findGbDictByType',
		data : {parmtype:config.parmtype}
	};
	req['useToken'] = true;
	req['successFn'] = function(respData){
		if(respData.code == SUCCESS_CODE){
			var datas = respData.data;
			if(config.filterData){
				datas =  config.filterData(datas);
			}
			for(var i=0;i<datas.length;i++){
				var d = datas[i];
				$('#'+config.id).append("<option value='"+d.parmname+"'>"+d.parmvalue+"</option>")
			}
			if($.isFunction(config.loadSuccess)){
				config.loadSuccess(config);
			}
			if(config.loadTriggerChange){
				$('#'+config.id).trigger('change');
			}
		}
	};
	ajaxRequest(req);
}

var loadInputJsTree_flag = false;
function loadInputJsTree(config){
	$('#'+config.jsTreeId).css('width',$("#"+config.inputId).outerWidth());
	$("#"+config.inputId).click(function(){
		if(!loadInputJsTree_flag){
			loadInputJsTree_flag = true;
			$('#'+config.jsTreeId).jstree({ 'core' : {
			     'data' : {
					'url' : config.url,
					 beforeSend:function(xhr){
						  if((typeof config.useToken) ==  "undefined" || config.useToken){
							  xhr.setRequestHeader('Authorization','Bearer '+getSessionData('userToken'));
							}
					 },
					 'data' : function (node) {
						 return  node.id === '#' ? { 'id' : null }: { 'id' : node.id };
					  },
					  filterData:function(data){
						  if(data.code==SUCCESS_CODE){
							  return data.data;
						  }else{
							  return [];
						  }
						  return data;
					  },
					  error:function(){
					    $('#'+config.jsTreeId).find(".jstree-loading").css("display","none");
					  }
				}
			 } });
			
			$('#'+config.jsTreeId).on('select_node.jstree',function(node,selected,event){
				if($.isFunction(config.selectFun)){
					config.selectFun(selected.node.id,selected.node.text);
				}
				 $('#'+config.jsTreeId).css("display","none");
	          });
		}
		if($('#'+config.jsTreeId).is(':hidden')){
			 $('#'+config.jsTreeId).css("display","");
		}else{
			$('#'+config.jsTreeId).css("display","none");
		}
	})
}

/**
 * 刷新
 * @returns
 */
function refreshJqGrid(datagridId){
	$("#"+datagridId).trigger("reloadGrid");
}

function uuid() {
    var s = [];
    var hexDigits = "0123456789abcdef";
    for (var i = 0; i < 36; i++) {
        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
    }
    s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
    s[8] = s[13] = s[18] = s[23] = "-";
 
    var uuid = s.join("");
    return uuid;
}

function getQcode(func,text,callBack){
    var params ={
        "token":FILE_MIDDLE_TOKEN,
        "func":func,
        "reqType":1,
        "reqParams":text
    }
    var req={};
    req['useToken']=false;
    var formData = new FormData();
    formData.append("token", FILE_MIDDLE_TOKEN);
    formData.append("func", func);
    formData.append("reqType", "1");
    formData.append("reqParams", text);
    req['reqJSON'] = {
        dataType:'json',
        contentType: false,
        async: true, //异步请求
        cache: false, //是否缓存
        type : 'post',
        processData: false,
        url : QCODE_FILE_URL,
        data:formData
    };
    req['successFn'] = function(respData){
        callBack(respData);
    };
    ajaxRequestByAttach(req);
}