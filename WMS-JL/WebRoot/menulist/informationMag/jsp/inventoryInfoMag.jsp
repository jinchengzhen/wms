<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="../../../component/common/css/common.css"
	type="text/css"></link>
<link rel="stylesheet" href="../css/goodsInfoMag.css" type="text/css"></link>
<link rel="stylesheet"
	href="../../../component/select2-4.0.1-rc.1/dist/css/select2.min.css">
<script type="text/javascript"
	src="../../../component/jquery/jquery-3.2.1.min.js"></script>
<script type="text/javascript"
	src="../../../component/select2-4.0.1-rc.1/dist/js/select2.full.min.js"></script>
<script type="text/javascript" src="../js/inventoryInfoMag.js"></script>
<script type="text/javascript"
	src="../../../component/common/js/common.js"></script>
<title>库存信息管理</title>
</head>
<body>
	<div class="common-black" id="black_block"></div>
	<!-- 主体页面开始 -->
	<div class="common-title">
		<div class="condition table">
			<span class="space"></span> <span class="fontB">库存信息</span> <span
				class="space"></span>
		</div>
		<div class="condition table">
			<span>所在仓库：</span> <input id="current_store" type="text"
				readonly="readonly">
		</div>
		<div class="condition table">
			<span class="space"></span> <span class="space"></span> <span
				class="space"></span>
		</div>
		<!-- <div class="condition table">
			<span>货名：</span>
			<input id="search_goodsName" type="text" placeholder="请选择货品">
		</div>
		<div class="condition table">
			<span>批次：</span>
			<input id="search_batchID" type="text" placeholder="请选择货品批次">
		</div> -->
		<div class="condition table">
			<button id="select_func" onclick="searchInfo()" hidden="hidden">查询</button>
			&nbsp;&nbsp;
			<!-- <button onclick="clearSearchInfo()">重置</button> -->
			<span class="space"></span>
			<button onclick="show_add_pop()">库存新增</button>
		</div>
		<div class="condition table">
			<button onclick="inventMag()">返回仓库信息</button>
		</div>
	</div>
	<div class="common-body">
		<div class="importblock">
			<span class="import" onclick="exportXls()">导出</span> <span
				style="float: right; width: 2%; height: 100%;"></span> <span
				class="import" onclick="">导入原始资料</span>
		</div>
		<div class="goodslist">
			<table>
				<thead>
					<tr style="background-color: #72c2fa;">
						<th style="width: 4%;">序号</th>
						<th style="width: 10%;">货品名称</th>
						<th style="width: 8%;">货品型号</th>
						<th style="width: 8%;">批次</th>
						<th style="width: 8%;">所在库房</th>
						<th style="width: 5%;">所在仓位</th>
						<th style="width: 9%;">现有数量</th>
						<th style="width: 10%;">存入时间</th>
						<th style="width: 10%;">过期时间</th>
						<th style="width: 10%;">校验人</th>
						<th style="width: 12%;">备注</th>
					</tr>
				</thead>
				<tbody id="inventorylistInfo">
					<tr class="select-tr">
						<td>1</td>
						<td>运动鞋</td>
						<td>A-1</td>
						<td>13420826</td>
						<td>1号库</td>
						<td>1</td>
						<td>200双</td>
						<td>2018-12-31</td>
						<td>2018-12-31</td>
						<td>王五</td>
						<td>0</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	<div class="common-foot">
		<div class="page-info table">
			当前是第 <span id="currpage">1</span> 页， 共 <span id="totalpage">10</span>
			页， 用时 <span id="select_time">0.01</span> 秒
		</div>
		<div class="pageturn table">
			<div class="pageopt" onclick="pageturn('first')">首页</div>
			<div class="pageopt" onclick="pageturn('prev')">上一页</div>
			<div class="pageopt" onclick="pageturn('next')">下一页</div>
			<div class="pageopt" onclick="pageturn('last')">尾页</div>
		</div>
	</div>
	<!-- 主体页面结束 -->
	<!-- 新增弹窗 -->
	<div class="pop-window-1" id="add_inventoryinfo_block">
		<div class="pop-title-block">
			<div class="pop-title-info table">
				<span class="titletext">新增库存</span>
			</div>
			<div class="pop-title-quit" id="quit_2">
				<div class="quit-x" onclick="close_add_pop()"></div>
			</div>
		</div>
		<div class="pop-body-block">
			<table class="addstoreInfo-table font">
				<tr>
					<td class="input-name"><span>所在仓位：</span></td>
					<td class="input-value"><input id="add_storelocation"
						type="text" class="font" placeholder="请输入仓库具体位置"></td>
				</tr>
				<tr>
					<td class="input-name"><span>货品名称：</span></td>
					<td class="input-value"><input id="add_goodsName" type="text"
						class="font" placeholder="请输入长x宽x高"></td>
				</tr>
				<tr>
					<td class="input-name"><span>货品型号：</span></td>
					<td class="input-value"><input id="add_goodsVersion"
						type="text" class="font" placeholder="请设置仓位数量，默认为 1"></td>
				</tr>
				<tr>
					<td class="input-name"><span>批&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;次：</span></td>
					<td class="input-value"><input id="add_batchID" type="text"
						class="font" placeholder="请选择管理人员"></td>
				</tr>
				<tr>
					<td class="input-name"><span>数&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;量：</span></td>
					<td class="input-value"><input id="add_total" type="text"
						class="font"></td>
				</tr>
				<tr>
					<td class="input-name"><span>备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</span></td>
					<td class="input-value"><input id="add_remark" type="text"
						class="font"></td>
				</tr>
			</table>
		</div>
		<div class="pop-option-block">
			<input type="button" value="确认" onclick="deal_add()"> <span
				class="space"></span> <input type="button" value="清除"
				onclick="clearADDInfo()">
		</div>
	</div>
	<!-- 新增弹窗结束 -->

</body>
</html>