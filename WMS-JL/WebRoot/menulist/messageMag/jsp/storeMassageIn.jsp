<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="../../../component/common/css/common.css"
	type="text/css"></link>
<link rel="stylesheet" href="../css/storeMassage.css" type="text/css"></link>
<link rel="stylesheet"
	href="../../../component/select2-4.0.1-rc.1/dist/css/select2.min.css">
<script type="text/javascript"
	src="../../../component/jquery/jquery-3.2.1.min.js"></script>
<script type="text/javascript"
	src="../../../component/jquery.print/jQuery.print.min.js"></script>
<script type="text/javascript"
	src="../../../component/select2-4.0.1-rc.1/dist/js/select2.full.min.js"></script>
<script type="text/javascript"
	src="../../../component/common/js/common.js"></script>
<script type="text/javascript" src="../js/storeMassage.js"></script>
<title>入库通知</title>
</head>
<body>
	<div class="common-black" id="black_block"></div>
	<!-- 主体页面开始 -->
	<div class="common-title">
		<div class="condition table" id="title-blackground">
			<span id="title" class="fontB">入库通知</span>
		</div>
		<div class="condition table" id="applySearch_block">
			<span>SKU号码：</span> <input id="search_a" type="text"
				placeholder="请输入SKU号码">
		</div>
		<div class="condition table" id="applySearch_option_block">
			<button onclick="searchInfo()">查询</button>
			&nbsp;&nbsp;
			<button onclick="clearSearchInfo()">重置</button>
			<span class="space"></span>
		</div>
		<div class="condition table">
			<button onclick="show_storein_pop()">入库扫描</button>
		</div>
	</div>
	<div class="common-body">
		<div class="importblock">
			<span class="import" onclick="exportXls()">导出</span> <span
				style="float: right; width: 2%; height: 100%;"></span> <span
				class="import" onclick="">批量导入</span> <span
				style="float: right; width: 10%; height: 100%;"></span> <span
				id="all" class="select-opntion" onclick="select_status($(this))">全部</span>
			<span id="noResole" class="select-opntion"
				onclick="select_status($(this))">待入库</span> <span id="resoled"
				class="select-opntion" onclick="select_status($(this))">已入库</span>
		</div>
		<div class="goodslist">
			<table>
				<thead>
					<tr style="background-color: #72c2fa;">
						<th style="width: 8%;">序号</th>
						<th style="width: 12%;">单品名称</th>
						<th style="width: 12%;">型号</th>
						<th style="width: 12%;">SKU</th>
						<th style="width: 12%;">存入仓库</th>
						<th style="width: 12%;">批次</th>
						<th style="width: 12%;">数量</th>
						<th style="width: 10%;">收货人</th>
						<th style="width: 10%;">操作</th>
					</tr>
				</thead>
				<tbody id="goodslistInfo">
					<tr class="select-tr">
						<td>1</td>
						<td>010025</td>
						<td>运动鞋</td>
						<td>A-01</td>
						<td>1号库</td>
						<td>2015646</td>
						<td>80</td>
						<td>李四</td>
						<td><span class="option" onclick="show_su_pop()">编辑</span><span
							class="option" onclick="generateCode('5612315646','')">打印标签</span></td>
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
	<!-- 打印标签 -->
	<div class="pop-window-1" id="print_block">
		<div class="pop-title-block">
			<div class="pop-title-info table">
				<span class="titletext">标签打印</span>
			</div>
			<div class="pop-title-quit" id="quit_2">
				<div class="quit-x" onclick="close_print_pop()"></div>
			</div>
		</div>
		<div class="pop-body-block">
			<div class="no-print">打印预览</div>
			<div>
				<img id="print_preview" src="" class="preview-print">
			</div>
			<div class="pop-option-block no-print">
				<input type="button" value="打印" class="print-link"
					onclick="jQuery.print('#print_preview')"> <span
					class="space"></span> <input type="button" value="返回"
					onclick="close_print_pop()">
			</div>
		</div>
	</div>
	<!-- 打印标签结束 -->

	<!-- 快速入库弹窗 -->
	<div class="pop-window-1" id="storein_block">
		<div class="pop-title-block">
			<div class="pop-title-info table">
				<span class="titletext">审批</span>
			</div>
			<div class="pop-title-quit" id="quit_2">
				<div class="quit-x" onclick="close_storein_pop()"></div>
			</div>
		</div>
		<div class="pop-body-block">
			<table class="addgoodsInfo-table font">
				<tr>
					<td class="input-name">SKU:</td>
					<td class="input-value"><input type="text"></td>
				</tr>
				<tr>
					<td class="input-name">库存数量:</td>
					<td class="input-value"><input type="text" readonly="readonly"></td>
				</tr>
				<tr>
					<td class="input-name">操作数量:</td>
					<td class="input-value"><input type="text"></td>
				</tr>
				<tr>
					<td class="input-name">总数量:</td>
					<td class="input-value"><input type="text"></td>
				</tr>
			</table>
		</div>
		<div class="pop-option-block">
			<input type="button" value="确认入库" onclick="deal_approve()"> <span
				class="space"></span> <input type="button" value="撤销入库"
				onclick="close_storein_pop()">
		</div>
	</div>
	<!-- 快速入库结束 -->

</body>
</html>