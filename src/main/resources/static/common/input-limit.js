$(function(){
	//只能输入字母和数字
	$("input.letterNum").keyup(function(){
	    		var value = $(this).val();
	    		$(this).val(value.replace(/[\W]/g,''));
	});
});