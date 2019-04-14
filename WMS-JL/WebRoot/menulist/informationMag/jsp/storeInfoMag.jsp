<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="../../../component/common/css/common.css"
	type="text/css"></link>
<link rel="stylesheet" href="../css/storeInfoMag.css" type="text/css"></link>
<link rel="stylesheet"
	href="../../../component/select2-4.0.1-rc.1/dist/css/select2.min.css">
<script type="text/javascript"
	src="../../../component/jquery/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="../js/storeInfoMag.js"></script>
<script type="text/javascript"
	src="../../../component/common/js/common.js"></script>
<script type="text/javascript"
	src="../../../component/select2-4.0.1-rc.1/dist/js/select2.full.min.js"></script>
<title>仓库信息管理</title>
</head>
<body>
	<div class="common-black" id="black_block"></div>
	<!-- 主体页面开始 -->
	<div class="common-title">
		<div class="condition table">
			<span>仓库编号：</span> <input id="search_storeID" type="text"
				placeholder="请输入仓库编号">
		</div>
		<div class="condition table">
			<span>仓库负责人：</span> <input id="search_managerID" type="text"
				placeholder="请选择库管人员">
		</div>
		<div class="condition table">
			<button onclick="searchInfo()">查询</button>
			&nbsp;&nbsp;
			<button onclick="clearSearchInfo()">重置</button>
			<span class="space"></span>
			<button onclick="show_add_pop()">新增仓库</button>
		</div>
	</div>
	<div class="common-body">
		<div class="importblock">
			<span class="import" onclick="exportXls()">导出</span> <span
				style="float: right; width: 2%; height: 100%;"></span> <span
				class="import" onclick="">批量导入</span>
		</div>
		<div class="storelist">
			<table>
				<thead>
					<tr style="background-color: #72c2fa;">
						<th style="width: 8%;">序号</th>
						<th style="width: 10%;">库房编号</th>
						<th style="width: 18%;">库房位置</th>
						<th style="width: 10%;">库房面积</th>
						<th style="width: 10%;">仓位数量</th>
						<th style="width: 12%;">负责人</th>
						<th style="width: 20%;">操作</th>
						<th style="width: 18%;">备注</th>
					</tr>
				</thead>
				<tbody id="storelistInfo">
					<tr class="select-tr">
						<td>1</td>
						<td>1号库</td>
						<td>开福区福元路1号</td>
						<td>200㎡</td>
						<td>1</td>
						<td>451512242</td>
						<td><span class="option" onclick="delete_goodsInfo()">删除货品</span><span
							class="space"></span><span class="option" onclick="">设为禁用</span></td>
						<td></td>
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
	<div class="pop-window-1" id="add_storeinfo_block">
		<div class="pop-title-block">
			<div class="pop-title-info table">
				<span class="titletext">新增库房</span>
			</div>
			<div class="pop-title-quit" id="quit_2">
				<div class="quit-x" onclick="close_add_pop()"></div>
			</div>
		</div>
		<div class="pop-body-block">
			<table class="addstoreInfo-table font">
				<tr>
					<td class="input-name"><span>库房编号：</span></td>
					<td class="input-value"><input id="add_storeID" type="text"
						placeholder="请输入仓库编号"></td>
				</tr>
				<tr>
					<td class="input-name"><span>库房位置：</span></td>
					<td class="input-value"><input id="add_storelocation"
						type="text" placeholder="请输入仓库具体位置"></td>
				</tr>
				<tr>
					<td class="input-name"><span>库房大小：</span></td>
					<td class="input-value"><input id="add_storeSize" type="text"
						placeholder="请输入长x宽x高"></td>
				</tr>
				<tr>
					<td class="input-name"><span>仓位数量：</span></td>
					<td class="input-value"><input id="add_positionNum"
						type="text" placeholder="请设置仓位数量，默认为 1"></td>
				</tr>
				<tr>
					<td class="input-name"><span>仓库管理：</span></td>
					<td class="input-value"><input id="add_managerID" type="text"
						placeholder="请选择管理人员"></td>
				</tr>
				<tr>
					<td class="input-name"><span>备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</span></td>
					<td class="input-value"><textarea id="add_remark" cols="2"
							class="font"></textarea></td>
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
	<!-- 新增弹窗 -->
	<div class="pop-window-1" id="update_storeinfo_block">
		<div class="pop-title-block">
			<div class="pop-title-info table">
				<span class="titletext">新增库房</span>
			</div>
			<div class="pop-title-quit" id="quit_2">
				<div class="quit-x" onclick="close_update_pop()"></div>
			</div>
		</div>
		<div class="pop-body-block">
			<table class="addstoreInfo-table font">
				<tr>
					<td class="input-name"><span>库房编号：</span></td>
					<td class="input-value"><input id="update_storeID" type="text"
						readonly="readonly"></td>
				</tr>
				<tr>
					<td class="input-name"><span>库房位置：</span></td>
					<td class="input-value"><input id="update_storelocation"
						type="text" placeholder="请输入仓库具体位置"></td>
				</tr>
				<tr>
					<td class="input-name"><span>库房大小：</span></td>
					<td class="input-value"><input id="update_storeSize"
						type="text" placeholder="请输入长x宽x高"></td>
				</tr>
				<tr>
					<td class="input-name"><span>仓位数量：</span></td>
					<td class="input-value"><input id="update_positionNum"
						type="text" placeholder="请设置仓位数量，默认为 1"></td>
				</tr>
				<tr>
					<td class="input-name"><span>仓库管理：</span></td>
					<td class="input-value"><input id="update_managerID"
						type="text"></td>
				</tr>
				<tr>
					<td class="input-name"><span>备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</span></td>
					<td class="input-value"><textarea id="update_remark" cols="2"
							class="font"></textarea></td>
				</tr>
			</table>
		</div>
		<div class="pop-option-block">
			<input type="button" value="确认" onclick="deal_update()"> <span
				class="space"></span> <input type="button" value="取消"
				onclick="close_update_pop()">
		</div>
	</div>
	<!-- 新增弹窗结束 -->
	<iframe id="inventoryInfo" src="inventoryInfoMag.jsp"></iframe>
</body>
<script type="text/javascript">
	$.fn.select2.defaults.set('width', '100%');
</script>
</html>