

/**
 * 初始化items对象
 * @param items
 * 			type:图形类型，
 * 			useToken:是否使用token
 * 			url:请求类型,
 * 			queryDataFn:查询数据的方法
 * 			item:请求类型传入的数据对象。例如initPieCharts(pieCharts)方法,
 * 			succesFn: 成功后返回的方法
 * @returns
 */
function initCharts(items){
	var req = {},data = items.queryDataFn();
	req['reqJSON'] = {
		type : 'get',
		url: items['url'],
		data : data
	};
	req['useToken'] = items['useToken'];
	req['successFn'] = function(respData){
		if(respData.code == SUCCESS_CODE){
			var myCharts = items['item'];
			myCharts['url'] = items['url'];
			myCharts['result'] = respData.data;
			myCharts['successFn'] = items['successFn'];
			myCharts['reqJSON'] = req['reqJSON'];
			myCharts['queryDataFn'] = items['queryDataFn'];
			myCharts['useToken'] = req['useToken'];
			if(items['type'] == 'pie')
				initPieCharts(myCharts);
		}else{
			items.errorMsgFn(respData);
		}
	};
	ajaxRequest(req);
}
function refreshPieData(items){
	var myCharts = items['charts'],
		option = myCharts.getOption();
	var req = {},data = items.queryDataFn();
	req['reqJSON'] = {
		type : 'get',
		url: items['url'],
		data : data
	};
	req['useToken'] = items['useToken'];
	req['successFn'] = function(respData){
		if(respData.code == SUCCESS_CODE){
			var nameValuePair = [];
			$.each(items['showData'],function(index,data){
				var name = data['name'],key = data['key'];
				nameValuePair.push({
					name:name,
					value: respData['data'][key]
				});
			});
			items['result'] = nameValuePair;
			option.series[0].data = items['result'];   
		    myCharts.setOption(option);  
		}
	};
	ajaxRequest(req);
     
}
/**
 * 初始化pieCharts
 * @param pieCharts
 * 		pieCharts = {
 * 			item : 
 * 				操作的对象（js）
 * 			name: 
 * 				显示的名称。formatter中指定{a}  选项
 * 			formatter:
 * 				top中需要显示的数据。内容.表达式.可加入换行表示 。 {a} 代表name,{b} 代表点击的data,{c} 代表value,{d} 代表数据百分比
 * 				支持回掉
 * 			showData:
 * 				显示的data数据。包含key，name。数组格式[{key:'数据展示的的列名',name:'值代表是展示的名称'}]
 * 			result:
 * 				result 返回的数据对象，
 *			succesFn:
 *				返回的成功参数
 * 		}
 * @returns
 */
function initPieCharts(pieCharts){
	var myChart = echarts.init(pieCharts['item'][0]),nameValuePair = [],
		legendData = [];//显示的列名
	
	$.each(pieCharts['showData'],function(index,data){
		var name = data['name'],key = data['key'];
		legendData.push(name);
		nameValuePair.push({
			name:name,
			value:pieCharts['result'][key]
		});
	});
	var option = {
		tooltip : {
			trigger : 'item',
			formatter : pieCharts['formatter']
		},
		legend : {
			orient : 'vertical',
//			x : 'center',	
			y : 'bottom',
			data : legendData
		},
		series : [ {
			name : pieCharts['name'],
			type : 'pie',
			radius : [ '40%','60%' ],
//			center: ['50%', '60%'],
			data : nameValuePair,
			itemStyle : {
                normal : {
                    label : {
                        show : true
                    },
                    labelLine : {
                        show : true
                    }
                },
                emphasis : {
                    label : {
                        show : true,
                        position : 'center',
                        textStyle : {
                            fontSize : '30',
                            fontWeight : 'bold'
                        }
                    }
                }
            },
		} ]
	};
	if (option && typeof option === "object") {
		myChart.setOption(option, true);
	}
	
	pieCharts['charts'] = myChart
	pieCharts.successFn(pieCharts);
}