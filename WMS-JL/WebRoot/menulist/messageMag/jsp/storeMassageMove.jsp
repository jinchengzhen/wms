<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="../../../component/common/css/common.css" type="text/css"></link>
<link rel="stylesheet" href="../css/storeMassage.css" type="text/css"></link>
<link rel="stylesheet" href="../../../component/select2-4.0.1-rc.1/dist/css/select2.min.css">
<script type="text/javascript" src="../../../component/jquery/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="../../../component/select2-4.0.1-rc.1/dist/js/select2.full.min.js"></script>
<title>物料采购</title>
</head>
<body>
<div class="common-black" id="black_block"></div>
    <!-- 主体页面开始 -->
	<div class="common-title">
		<div class="condition table" id="title-blackground">
			<span id="title"class="fontB">移库通知</span>
		</div>
		<div class="condition table" id="applySearch_block">
			<span>SKU号码：</span>
			<input id="search_a" type="text" placeholder="请输入SKU号码"  >
		</div>
		<div class="condition table" id="applySearch_option_block">
			<button onclick="searchInfo()">查询</button>&nbsp;&nbsp;
			<button onclick="clearSearchInfo()">重置</button>
			<span class="space"></span>
		</div>
		<div class="condition table">
			<button onclick="show_storemove_pop()">新增处理</button>
		</div>
		<div class="condition table">
			<button onclick="show_storemove_pop()">移库扫描</button>
		</div>
	</div>
	<div class="common-body">
		<div class="importblock">
			<span class="import" onclick="exportXls()">导出</span>
			<span style="float: right;    width: 2%;    height: 100%;"></span>
			<span class="import" onclick="">批量导入</span>
			<span style="float: right;    width: 10%;    height: 100%;"></span>
			<span id="all"class="select-opntion" onclick="select_status($(this))">全部</span>
			<span id="noResole"class="select-opntion" onclick="select_status($(this))">待处理</span>
			<span id="resoled"class="select-opntion" onclick="select_status($(this))">已处理</span>
		</div>
		<div class="goodslist">
			<table>
				<thead>
					<tr style="background-color: #72c2fa;">
						<th style="width: 8%;">序号</th><th style="width: 10%;">单品名称</th><th style="width: 10%;">型号</th><th style="width: 10%;">SKU</th><th style="width: 12%;">批次</th><th style="width: 10%;">原始位置</th><th style="width: 10%;">目标位置</th><th style="width: 10%;">数量</th><th style="width: 10%;">操作人</th><th style="width: 10%;">操作</th>
					</tr>
				</thead>
				<tbody id="goodslistInfo">
					<tr class="select-tr">
						<td>1</td><td>运动鞋</td><td>A-01</td><td>010025</td><td>45612</td><td>1号库</td><td>2号库</td><td>201</td><td>李四</td><td><span class="option" onclick="show_su_pop()">查看</span></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	<div class="common-foot">
		<div class="page-info table">
			当前是第 <span id="currpage">1</span> 页，
			共 <span id="totalpage">10</span> 页，
			用时 <span id="select_time">0.01</span> 秒
		</div>
		<div class="pageturn table">
			<div class="pageopt" onclick="pageturn('first')">首页</div>
			<div class="pageopt" onclick="pageturn('prev')">上一页</div>
			<div class="pageopt" onclick="pageturn('next')">下一页</div>
			<div class="pageopt" onclick="pageturn('last')">尾页</div>
		</div>
	</div>
	<!-- 主体页面结束 -->
	
	
	<!-- 快速移库弹窗 -->
	<div class="pop-window-1" id="storemove_block">
		<div class="pop-title-block">
			<div class="pop-title-info table"><span class="titletext">快速出库</span></div>
			<div class="pop-title-quit" id="quit_2"><div class="quit-x" onclick="close_storeout_pop()"></div></div>
		</div>
		<div class="pop-body-block">
			<table class="addgoodsInfo-table font">
				<tr>
					<td class="input-name">SKU:</td><td class="input-value"><input type="text" ></td>
				</tr>
				<tr>
					<td class="input-name">货品名称:</td><td class="input-value"><input type="text" ></td>
				</tr>
				<tr>
					<td class="input-name">原始位置:</td><td class="input-value"><input type="text" readonly="readonly"></td>
				</tr>
				<tr>
					<td class="input-name">目标位置:</td><td class="input-value"><input type="text" readonly="readonly"></td>
				</tr>
				<tr>
					<td class="input-name">操作数量:</td><td class="input-value"><input type="text" ></td>
				</tr>
			</table>
		</div>
		<div class="pop-option-block">
			<input type="button" value="确认" onclick="deal_store_out()">
			<span class="space"></span>
			<input type="button" value="撤销" onclick="close_storeout_pop()">
		</div>
	</div>
	<!-- 快速出库结束 -->
	
</body>
<script type="text/javascript" src="../../../component/common/js/common.js"></script>
<script type="text/javascript" src="../js/storeMassage.js"></script>
</html>