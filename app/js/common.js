var localhost = "http://192.168.2.111:8080/";
var mapkey = "hVI52P08obxgT6Gl1uxKnxId";
function getTypeNames(typeNames, typeValues)
{
	console.log(typeValues)
	var rtnValue = "";
	var typeValueArray = typeValues.split(","); 
	for (var j = 0; j < typeValueArray.length; j++)
	{
		var typeValue = typeValueArray[j];
		
		for(var i = 0; i < typeNames.length; i++)
		{
			if (typeNames[i].type == typeValue)
			{
				rtnValue += typeNames[i].name + "、";
			}
		}		
	}
	return rtnValue;
}
var evalTypeNames = [{
	type: 1,
	name: "人行道宽度窄"
}, {
	type: 2,
	name: "人行道无盲道"
}, {
	type: 3,
	name: "盲道有障碍物"
}, {
	type: 4,
	name: "人行道不平整"
}, {
	type: 5,
	name: "绿化环境差"
}, {
	type: 6,
	name: "人行道与非机动车道未隔离"
}, {
	type: 7,
	name: "人行道有障碍物"
}, {
	type: 8,
	name: "缺少过街设施"
}, {
	type: 9,
	name: "过街距离过长"
}, {
	type: 10,
	name: "过街信号灯过短"
}, {
	type: 11,
	name: "信号灯等待时间过长"
}, {
	type: 12,
	name: "缺少人行道"
}];










var infoTypeNames = [{
	type: 1,
	name: "人行跨路桥"
}, {
	type: 2,
	name: "栅栏"
}, {
	type: 3,
	name: "照明设施"
}, {
	type: 4,
	name: "视线诱导标志"
}, {
	type: 5,
	name: "公路反射镜"
}, {
	type: 6,
	name: "公路情报板"
}, {
	type: 7,
	name: "公路监视系统"
}, {
	type: 8,
	name: "停车场"
}, {
	type: 9,
	name: "公共汽车停靠站"
}];
// 对Date的扩展，将 Date 转化为指定格式的String   
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，   
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)   
// 例子：   
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423   
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18   
Date.prototype.Format = function(fmt) { //author: meizz   
	var o = {
		"M+": this.getMonth() + 1, //月份   
		"d+": this.getDate(), //日   
		"h+": this.getHours(), //小时   
		"m+": this.getMinutes(), //分   
		"s+": this.getSeconds(), //秒   
		"q+": Math.floor((this.getMonth() + 3) / 3), //季度   
		"S": this.getMilliseconds() //毫秒   
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	for (var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
}