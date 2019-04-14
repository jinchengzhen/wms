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
<title>货品信息管理</title>
</head>
<body>
	<div class="common-black" id="black_block"></div>
	<!-- 主体页面开始 -->
	<div class="common-title">
		<div class="condition table">
			<span>货品名称：</span> <input id="search_goodsName" type="text"
				placeholder="请输入货品名称">
		</div>
		<div class="condition table">
			<span>货品种类：</span> <input id="search_typeName" type="text"
				placeholder="请输入货品种类">
		</div>
		<div class="condition table">
			<span>货源：</span> <input id="search_goodsSource" type="text"
				placeholder="请选择货源">
		</div>
		<div class="condition table">
			<button onclick="searchInfo()">查询</button>
			&nbsp;&nbsp;
			<button onclick="clearSearchInfo()">重置</button>
			<span class="space"></span>
			<button onclick="show_add_pop()">新增货品</button>
		</div>
	</div>
	<div class="common-body">
		<div class="importblock">
			<span class="import" onclick="exportXls()">导出</span> <span
				style="float: right; width: 2%; height: 100%;"></span> <span
				class="import" onclick="">批量导入</span>
		</div>
		<div class="goodslist">
			<table>
				<thead>
					<tr style="background-color: #72c2fa;">
						<th style="width: 8%;">序号</th>
						<th style="width: 12%;">货品编号</th>
						<th style="width: 12%;">货品名称</th>
						<th style="width: 12%;">货品型号</th>
						<th style="width: 12%;">货品种类</th>
						<th style="width: 12%;">货源</th>
						<th style="width: 12%;">记录人</th>
						<th style="width: 20%;">操作</th>
					</tr>
				</thead>
				<tbody id="goodslistInfo">
					<tr class="select-tr">
						<td>1</td>
						<td>010025</td>
						<td>运动鞋</td>
						<td>鞋/帽</td>
						<td>A-01</td>
						<td>生产部</td>
						<td>451512242</td>
						<td><span class="option" onclick="delete_goodsInfo()">删除货品</span><span
							class="option" onclick="su_goodsInfo()">查看|编辑</span><span
							class="option" onclick="">设为禁用</span></td>
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
	<div class="pop-window-2" id="add_goodsinfo_block">
		<div class="pop-title-block">
			<div class="pop-title-info table">
				<span class="titletext">新增货品</span>
			</div>
			<div class="pop-title-quit" id="quit_2">
				<div class="quit-x" onclick="close_add_pop()"></div>
			</div>
		</div>
		<div class="pop-body-block">
			<table class="addgoodsInfo-table font">
				<tr>
					<td class="input-name"><span>货品名称：</span></td>
					<td class="input-value"><input id="add_goodsName" type="text"
						placeholder="请输入货品名称"></td>
					<td class="input-name"><span>货品简称：</span></td>
					<td class="input-value"><input id="add_simpleName" type="text"
						placeholder="请输入货品简称"></td>
					<td class="input-name"><span>货品类别：</span></td>
					<td class="input-value"><input id="add_typeName" type="text"
						placeholder="请输入货品类别"></td>
				</tr>
				<tr>
					<td><span>基本单位：</span></td>
					<td><input id="add_baseUnit" type="text" placeholder="请输入基本单位"
						title="请输入基本单位" onblur="setBaseUnit($(this).val())"></td>
					<td><span>库存上限：</span></td>
					<td><input id="add_sizeMax" class="leftInput" type="text"
						placeholder="请输入库存上限" title="请输入库存上限"><input
						name="baseunit" class="rightInput" type="text" placeholder="单位"
						readonly="readonly"></td>
					<td><span>库存下限：</span></td>
					<td><input id="add_sizeMin" class="leftInput" type="text"
						placeholder="请输入库存下限" title="请输入库存下限"><input
						name="baseunit" class="rightInput" type="text" placeholder="单位"
						readonly="readonly"></td>
				</tr>
				<tr>
					<td><span>规&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;格：</span></td>
					<td><input id="add_TP" type="text" placeholder="请设置规格"
						onclick="show_setTP()" readonly="readonly"
						style="cursor: pointer;"></td>
					<td><span>质&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;量：</span></td>
					<td><input id="add_quality" type="text" placeholder="请输入质量"></td>
					<td><span>货&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;源：</span></td>
					<td><input id="add_goodsSource" type="text"
						placeholder="请输入货源"></td>
				</tr>
				<tr>
					<td><span>货品型号：</span></td>
					<td><input id="add_goodsVersion" type="text"
						placeholder="请输入货品型号"></td>
					<td><span>存储条件：</span></td>
					<td><input id="add_storage" type="text" placeholder="请输入存储条件"></td>
					<td><span>有效期限：</span></td>
					<td><input id="add_limitTerm" type="text"
						placeholder="请输入有效期限" title="请输入有效期限"></td>
				</tr>
				<tr>
					<td colspan="1"><span>备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</span></td>
					<td colspan="5"><textarea id="add_remark" cols="2"
							class="font"></textarea></td>
				</tr>
			</table>
		</div>
		<div class="pop-option-block">
			<input type="button" value="确认" onclick="deal_add()"> <span
				class="space"></span> <input type="button" value="取消"
				onclick="close_add_pop()">
		</div>
	</div>
	<!-- 新增弹窗结束 -->
	<!-- 规格设置弹窗开始 -->
	<div class="pop-window-1" id="TP_block">
		<div class="pop-title-block">
			<div class="pop-title-info table">
				<span id="pop_title_info" class="titletext">货品规格设置</span>
			</div>
			<div class="pop-title-quit" id="quit_2">
				<div class="quit-x" onclick="close_setTP()"></div>
			</div>
		</div>
		<div class="pop-body-block">
			<div id="tp_table" class="tp-table table">
				<div class="tp-tr">
					<div class="tp-th1 fontB">属性</div>
					<div class="tp-th1 fontB">数值</div>
					<div class="tp-th2 fontB">单位</div>
					<div class="tp-th3 fontB">操作</div>
				</div>
				<div class="tp-tr">
					<div class="tp-th1">
						<input type="text">
					</div>
					<div class="tp-th1">
						<input type="text">
					</div>
					<div class="tp-th2">
						<input type="text">
					</div>
					<div class="tp-th3">
						<img src="/JIN-WMS/static/images/delete.png"
							onclick="delete_TPproperty()">
					</div>
				</div>
			</div>
			<div class="tp-trn" id="tp_insert">
				<div class="tp-thn" title="添加属性">
					<img src="../../../static/images/addInfoFJ.png"
						onclick="insert_TPproperty()">
				</div>
			</div>
		</div>
		<div class="pop-option-block">
			<input type="button" value="确认" onclick="setTP()"> <span
				class="space" id="space_su"></span> <input type="button" value="返回"
				onclick="close_setTP()">
		</div>
	</div>
	<!-- 规格设置弹窗结束 -->
	<!-- 查看/修改弹窗开始 -->
	<div class="pop-window-2" id="su_block">
		<div class="pop-title-block">
			<div class="pop-title-info table">
				<span id="pop_title_info" class="titletext">货品详细信息</span>
			</div>
			<div class="pop-title-quit" id="quit_2">
				<div class="quit-x" onclick="close_su_pop()"></div>
			</div>
		</div>
		<div class="pop-body-block">
			<table class="addgoodsInfo-table">
				<tr>
					<td class="input-name"><span>货品名称：</span></td>
					<td class="input-value"><input id="su_goodsName" type="text"></td>
					<td class="input-name"><span>货品编号：</span></td>
					<td class="input-value"><input id="su_goodsID" type="text"></td>
					<td class="input-name"><span>货品简称：</span></td>
					<td class="input-value"><input id="su_simpleName" type="text"></td>
				</tr>
				<tr>
					<td class="input-name"><span>货品类别：</span></td>
					<td class="input-value"><input id="su_typeName" type="text"></td>
					<td class="input-name"><span>货品型号：</span></td>
					<td class="input-value"><input id="su_goodsVersion"
						type="text"></td>
					<td class="input-name"><span>规&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;格：</span></td>
					<td class="input-value"><input id="su_TP" type="text"></td>
				</tr>
				<tr>
					<td class="input-name"><span>存储条件：</span></td>
					<td class="input-value"><input id="su_storage" type="text"></td>
					<td class="input-name"><span>库存上限：</span></td>
					<td class="input-value"><input id="su_sizeMax" type="text"></td>
					<td class="input-name"><span>库存下限：</span></td>
					<td class="input-value"><input id="su_sizeMin" type="text"></td>
				</tr>
				<tr>
					<td class="input-name"><span>货&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;源：</span></td>
					<td class="input-value"><input id="su_goodsSource" type="text"></td>
					<td class="input-name"><span>质量等级：</span></td>
					<td class="input-value"><input id="su_quality" type="text"></td>
					<td class="input-name"><span>有效期限：</span></td>
					<td class="input-value"><input id="su_limitTerm" type="text"></td>
				</tr>
				<tr>
					<td class="input-name"><span>记录人ID：</span></td>
					<td class="input-value"><input id="su_userID" type="text"></td>
					<td class="input-name"><span>修改时间：</span></td>
					<td class="input-value"><input id="su_updateDate" type="text"></td>
				</tr>
				<tr>
					<td colspan="1"><span>备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</span></td>
					<td colspan="5"><textarea id="su_remark" cols="2" class="font"></textarea></td>
				</tr>
			</table>
		</div>
		<div class="pop-option-block">
			<input type="button" value="确认" onclick="update_userInfo()">
			<span class="space" id="space_su"></span> <input type="button"
				value="返回" onclick="close_su_pop()">
		</div>
	</div>
	<!-- 查看/修改弹窗结束 -->
</body>
<script type="text/javascript"
	src="../../../component/common/js/common.js"></script>
<script type="text/javascript" src="../js/goodsInfoMag.js"></script>
</html>