$.validator.setDefaults({
	ignore: ":hidden:not(.chosen-select)" ,
    highlight: function(e) {
        $(e).closest(".form-group").removeClass("has-success").addClass("has-error")
    },
    success: function(e) {
        e.closest(".form-group").removeClass("has-error").addClass("has-success")
    },
    errorElement: "span",
    errorPlacement: function(e, r) {
        e.appendTo(r.is(":radio") || r.is(":checkbox") ? r.parent().parent().parent() : r.parent())
    },
    errorClass: "help-block m-b-none error-valid",
    validClass: "help-block m-b-none"
});

jQuery.validator.addMethod("isPhone", function(value, element) {
	var length = value.length;
	var mobile = /^(((13[0-9]{1})|(15[0-9]{1})|(14[0-9]{1})|(18[0-9]{1})|(17[0-9]{1}))+\d{8})$/;
	    return this.optional(element) || (length == 11 && mobile.test(value));
},'请填写正确得手机号');

jQuery.validator.addMethod("isTel", function(value, element) {
	var length = value.length;
	var phone = /(^(\d{3,4}-)?\d{6,8}$)|(^(\d{3,4}-)?\d{6,8}(-\d{1,5})?$)|(\d{11})/;
    return this.optional(element) || (phone.test(value));
   }, "请填写正确的固定电话");


jQuery.validator.addMethod("email", function(value, element) {
	var length = value.length;
	var email = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    return this.optional(element) || (email.test(value));
   }, "请填写正确的邮箱");


jQuery.validator.addMethod("isWord", function(value, element) {
	var length = value.length;
	var phone = /^\w*$/;
    return this.optional(element) || (phone.test(value));
   }, "只能输入字母或者数字");

$.fn.formEdit = function(data){
    return this.each(function(){
        var input, name;
        if(data == null){this.reset(); return; }
        for(var i = 0; i < this.length; i++){  
            input = this.elements[i];
            //checkbox的name可能是name[]数组形式
            name = (input.type == "checkbox")? input.name.replace(/(.+)\[\]$/, "$1") : input.name;
            if(data[name] == undefined) continue;
            switch(input.type){
                case "checkbox":
                    if(data[name] == ""){
                        input.checked = false;
                    }else{
                        //数组查找元素
                        if(data[name].indexOf(input.value) > -1){
                            input.checked = true;
                        }else{
                            input.checked = false;
                        }
                    }
                break;
                case "radio":
                    if(data[name] == ""){
                        input.checked = false;
                    }else if(input.value == data[name]){
                        input.checked = true;
                    }
                break;
                case "button": break;
                default: input.value = data[name];
            }
        }
    });
};
/**
 * 判断最小的不能大于最大的
 * 判断最大的不能小于最小的
 * @param value:文本框输入的值
 * @param element:文本节点
 * @param params:调用传来的参数
 * @author WangLaiLi
 */
$.validator.addMethod("checkMax",function(value,element,params){
				var id = params[0];
				var check = params[1];
				if(check == 'min'){
					if(parseFloat(value) > parseFloat($('#'+id).val())){
						return false;
					}
				}else if(check == 'max'){
					if(parseFloat(value) < parseFloat($('#'+id).val())){
						return false;
					}	
				}
			 return true;
});

//选择不能小于当前时间
$.validator.addMethod("checkDate",function(value,element,params){
	var selDate = Date.parse(new Date(value));
	selDate = selDate / 1000;
	var nowDate = new Date();
	nowDate = nowDate.getTime();
	nowDate = nowDate / 1000;
	if(selDate <= nowDate){
		return false;
	}
	return true;
});
//多选按钮必选一个
$.validator.addMethod("selectOne",function(value,element){
	if(value == "Select Some Options"){
		//return true;
	}
	return false;
});

/**
 * 验证数字只有2位小数
 */
$.validator.addMethod("decimal2", function(value, element) {
	var decimal = /^-?\d+(\.\d{1,2})?$/;
	return this.optional(element) || (decimal.test(value));
}, "小数位数不能超过两位!");

//数字控件输入
$(function() {
	$(".checkNum").keypress(function(event) { 
	    var keyCode = event.which; 
	    if ((keyCode >= 48 && keyCode <=57)|| keyCode == 127) 
	        return true; 
	    else if(keyCode == 46)
	    	if($(this).val().indexOf(".")>=0) 
	    	   return false;
	    	else
	    	   return true;
	    else
	    	return false; 
	}).keyup(function(){  
		 $(this).val($(this).val().replace(/[^0-9.]/g,'')); 
	    var str = $(this).val();
	    var reg = /(\.)(?=.*\1)/g;
	    var result = str.replace(reg, "");
	    $(this).val(result);
	}).bind("paste",function(){
	    $(this).val($(this).val().replace(/[^0-9.]/g,'')); 
	    var str = $(this).val();
	    var reg = /(\.)(?=.*\1)/g;
	    var result = str.replace(reg, "");
	    $(this).val(result);
	}).css("ime-mode", "disabled");


	/*  检查非负整数  */
    $(".checkNumNotPoint").keypress(function(event) {
        var keyCode = event.which;
        if ((keyCode >= 48 && keyCode <=57)|| keyCode == 127)
            return true;
        /*else if(keyCode == 46)
            if($(this).val().indexOf(".")>=0)
                return false;
            else
                return true;*/
        else
            return false;
    }).keyup(function(){
        $(this).val($(this).val().replace(/[^0-9]/g,''));
        var str = $(this).val();
        var reg = /(\.)(?=.*\1)/g;
        var result = str.replace(reg, "");
        $(this).val(result);
    }).bind("paste",function(){
        $(this).val($(this).val().replace(/[^0-9]/g,''));
        var str = $(this).val();
        var reg = /(\.)(?=.*\1)/g;
        var result = str.replace(reg, "");
        $(this).val(result);
    }).css("ime-mode", "disabled");

});