$.fn.extend({  
		resetForm:function(start,end){
			var me = $(this);
			var sel = $("select");
			var tablePar = me.parents(".example-wrap");
			var table = tablePar.find("table");
			var tabId = table.eq(1).attr("id");
			sel.val("");
			sel.trigger("chosen:updated");
			me[0].reset();
			var dateSel = me.find("#data_5");
			if(dateSel.length != 0){
				
				/*对日期的还原处理*/
				var d = new Date();
				d.setDate(d.getDate()-14);
		    	var startTime = d.getFullYear()+"-"+(d.getMonth()>=9?d.getMonth()+1:"0"+(d.getMonth()+1))+"-"+(d.getDate()>9?d.getDate():"0"+d.getDate());
		    	$('#startTime').datepicker('setDate',startTime);
		    	if(me.find("#data_51")){
		    		$('#startTime1').datepicker('setDate',startTime);
		    	}
		    	var d1 = new Date();
		    	d1.setDate(d1.getDate()+1);
		    	var endDate = d1.getFullYear()+"-"+(d1.getMonth()>=9?d1.getMonth()+1:"0"+(d1.getMonth()+1))+"-"+(d1.getDate()>9?d1.getDate():"0"+d1.getDate());
		    	$("#endTime").datepicker('setDate',endDate);
		    	if(me.find("#data_51")){
		    		$("#endTime1").datepicker('setDate',endDate);
		    	}
		    	
			}
			var dateSel6 = me.find("#data_6");
			if(dateSel6.length != 0){
				/*对日期的还原处理*/
				var d = new Date();
				d.setDate(d.getDate()-14);
		    	var startTime = d.getFullYear()+"-"+(d.getMonth()>=9?d.getMonth()+1:"0"+(d.getMonth()+1))+"-"+(d.getDate()>9?d.getDate():"0"+d.getDate());
		    	$("#"+start).val(startTime);
		    	var d1 = new Date();
		    	d1.setDate(d1.getDate()+1);
		    	var endDate = d1.getFullYear()+"-"+(d1.getMonth()>=9?d1.getMonth()+1:"0"+(d1.getMonth()+1))+"-"+(d1.getDate()>9?d1.getDate():"0"+d1.getDate());
		    	$("#"+end).val(endDate);
			}
	    	refreshGrid(tabId);
		},
        initForm:function(options){  
            //默认参数  
            var defaults = {  
                jsonValue:"",  
                isDebug:false   //是否需要调试，这个用于开发阶段，发布阶段请将设置为false，默认为false,true将会把name value打印出来  
            }  
            //设置参数  
            var setting = $.extend({}, defaults, options);  
            var form = this;  
            jsonValue = setting.jsonValue;  
            //如果传入的json字符串，将转为json对象  
            if($.type(setting.jsonValue) === "string"){  
                jsonValue = $.parseJSON(jsonValue);  
            }  
            //如果传入的json对象为空，则不做任何操作  
            if(!$.isEmptyObject(jsonValue)){  
                var debugInfo = "";  
                var jsonObj = null;
                //如果jsonValue.data 不等于null 
	                if(null != jsonValue.data){
	                	jsonObj = jsonValue.data;
	                }else{
	                	jsonObj = jsonValue;
	                }
	                $.each(jsonObj,function(key,value){  
                    //是否开启调试，开启将会把name value打印出来  
                    if(setting.isDebug){  
//                        alert("name:"+key+"; value:"+value);  
                        debugInfo += "name:"+key+"; value:"+value+" || ";  
                    }  
                    var formField = form.find("[name='"+key+"']");  
                    if($.type(formField[0]) === "undefined"){  
                        if(setting.isDebug){  
//                            alert("can not find name:["+key+"] in form!!!");    //没找到指定name的表单  
                        }  
                    } else {  
                        var fieldTagName = formField[0].tagName.toLowerCase();  
                        if(fieldTagName == "input"){  
                            if(formField.attr("type") == "radio" || formField.attr("type") == "checkbox"){
                            	formField.filter("[name='"+key+"'][value = '"+value+"']").attr("checked",true);
                            } else{  
                                formField.val(value);  
                            }  
                        } else if(fieldTagName == "select"){  
                            formField.val(value);  
                        } else if(fieldTagName == "textarea"){  
                            formField.val(value);  
                        }else if(fieldTagName == "span"){  
                            formField.text(value);  
                        }  else {  
                            formField.val(value);  
                        }  
                    }  
                })  
                if(setting.isDebug){  
                    console.log(debugInfo);  
                }  
            }  
            if(typeof options.afterInitForm=="function"){
            	options.afterInitForm(options);
            }
            return form;    //返回对象，提供链式操作  
        }
    });  

//回车搜索事件
document.onkeydown = function(e){ 
	    var ev = document.all ? window.event : e;
	    if(ev.keyCode==13) 
	    {
	     	$('#searchBtn').click();
	     	if($("#searchBtn1")){
	     		$("#searchBtn1").click();
	     	}
	 	}
}