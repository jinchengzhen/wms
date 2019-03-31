/*
 * 1、setBaseDate
 * 2、fetchData
 * 3、setData
 * 4、setStatistic
 */
var data = new Array();
var basedate = new Date(2019,1,1);
var currdate = new Date();
var dataNameA = 'Matcha Latte';
var dataNameB = 'Milk Tea';
var dataNameC = 'Cheese Cocoa';

$(function(){
	statistic.setBaseDate(2019,1,1);
	getData();
	statistic.setData(data);
	statistic.setStatistic('left','right');
})



////获取数据
function getData(){
//	var dateArr = ['product'];
	dataA = [dataNameA];
	dataB = [dataNameB];
	dataC = [dataNameC];
	var now;
	while(basedate.getTime() < currdate.getTime() ){
		 basedate.setDate(basedate.getDate()+1);
		 dataA.push((Math.random() + 2.5) * 10);
		 dataB.push((Math.random() + 2) * 10);
		 dataC.push((Math.random() + 1.5) * 10);
	}
	data.push(dataA);
	data.push(dataB);
	data.push(dataC);
}
