/*
 * 1、setBaseDate
 * 2、fetchData
 * 3、deal（jsonarray）
 * 4、setData
 * 5、setStatistic
 */
statistic = {
	jsonarray : null,
	basedate : null,
	seriesArr : new Array(),
	data : new Array(),//每一个元素均为数组
	nameArr : new Array(),
	angledData : new Array(),
	setBaseDate : function(year,month,day){
		this.basedate = new Date(year,month,day); 
	},
	setData : function(dataArr){
		this.setxAxis();
		this.data = this.data.concat(dataArr);
		this.setNameArr(dataArr);
		this.setAngleData(dataArr);
		this.generateseriesArr(this.nameArr.length);
	},
	fetchData : function(url,args){
		$.post(url,args,function(data){
			 var jsonobj = JSON.parse(data);
			 if(jsonobj.state == '1'){
				 this.jsonarray = jsonobj.data;
				 return true;
			 }
		});
		return false;
	},
	setAngleData : function(data){
		for(var i = 0; i < data.length; i++){
			this.angledData.push({value:this.getSum(data[i]), name:data[i][0]});
		}
	},
	setNameArr : function(names){
		for(var i = 0; i < names.length; i++){
			this.nameArr.push(names[i][0]);
		}
	},
	setxAxis : function(){
		var currdate = new Date();
		var startdate = this.basedate;
		var dateArr = new Array();
		dateArr.push('product');
		var now = null;
		while(startdate.getTime() < currdate.getTime() ){
			 now = new Date(startdate.setDate(startdate.getDate()+1));
			 now = [now.getFullYear(), now.getMonth(), now.getDate()].join('-');
			 dateArr.push(now);
		}
		this.data.push(dateArr);
	},
	getSum : function(dataArr){
		var result = 0;
		for(var i = 1; i < dataArr.length; i++){
			result += dataArr[i];
		}
		return result;
	},
	generateseriesArr : function(num){
		for(var i = 0; i < num; i++){
			this.seriesArr.push({type: 'line', seriesLayoutBy: 'row'});
		}
	},
	setStatistic : function(left,right){
		//echart
		var myleftChart = echarts.init(document.getElementById(left));
		var myrightChart = echarts.init(document.getElementById(right));
		 // 指定图表的配置项和数据
	    //折线图
	  	leftoption = {
		    legend: {},
		    tooltip: {},
		    //可交互，x轴滑动
		    dataZoom: [
		        {
		            type: 'slider',
		            xAxisIndex: 0,
		            start: 10,
		            end: 60
		        },
		        {
		            type: 'inside',
		            xAxisIndex: 0,
		            start: 10,
		            end: 60
		        },
		    ],
		    dataset: {
		        source: this.data
		    },
		    xAxis: 
		        {type: 'category', gridIndex: 0},
		    
		    yAxis: 
		        {gridIndex: 0},
		    
		    grid: [
		        {top: '10%'} ,
		        {bottom: '90%'},
		    ],
		    series: this.seriesArr,
		};
	  //南丁格尔图
	    rightoption = {
		    series : [
		        {
		            name: '产品',
		            type: 'pie',
		            radius: '60%',
		            roseType: 'angle',
		            data:this.angledData
		        }
		    ]
		};
	    // 使用刚指定的配置项和数据显示图表。
	    myleftChart.setOption(leftoption);
	    myrightChart.setOption(rightoption);
	},
	
}