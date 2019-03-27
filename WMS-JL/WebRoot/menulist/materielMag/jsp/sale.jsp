<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="../../../component/common/css/common.css" type="text/css"></link>
<link rel="stylesheet" href="../css/materiel.css" type="text/css"></link>
<link rel="stylesheet" href="../../../component/select2-4.0.1-rc.1/dist/css/select2.min.css">
<script type="text/javascript" src="../../../component/jquery/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="../../../component/select2-4.0.1-rc.1/dist/js/select2.full.min.js"></script>
<title>物料采购</title>
</head>
<body>
<div class="common-black" id="black_block"></div>
    <!-- 主体页面开始 -->
	<div class="common-title">
		<div class="condition table" id="applySearch_block">
			<span id="title"class="fontB">产品销售</span>
		</div>
		<div class="condition table" id="applySearch_block">
			<span>申请人：</span>
			<%
				String userName = session.getAttribute("userName").toString();
				String auth = session.getAttribute("duty").toString();
				if("1".equals(auth)){
					userName = "";
					auth = "";
				}else{
					auth = "readonly=\"readonly\"";
				}
			%>
			<input id="search_apply" type="text" placeholder="请输入申请人" value=<%="\""+userName+"\"" + auth%> >
		</div>
		<div class="condition table" id="applySearch_option_block">
			<button onclick="searchInfo()">查询</button>&nbsp;&nbsp;
			<button onclick="clearSearchInfo()">重置</button>
			<span class="space"></span>
		</div>
		<div class="condition table">
			<button onclick="show_add_pop()">新增</button>
		</div>
	</div>
	<div class="common-body">
		<div class="importblock">
			<span class="import" onclick="exportXls()">导出</span>
			<span style="float: right;    width: 2%;    height: 100%;"></span>
			<span class="import" onclick="">批量导入</span>
			<span style="float: right;    width: 10%;    height: 100%;"></span>
			<span id="all"class="select-opntion" onclick="select_status($(this))">全部</span>
			<span id="noResole"class="select-opntion" onclick="select_status($(this))">待审批</span>
			<span id="resoled"class="select-opntion" onclick="select_status($(this))">已审批</span>
		</div>
		<div class="goodslist">
			<table>
				<thead>
					<tr style="background-color: #72c2fa;">
						<th style="width: 8%;">序号</th><th style="width: 12%;">申请人</th><th style="width: 12%;">申请时间</th><th style="width: 12%;">审批状态</th><th style="width: 12%;">审批人</th><th style="width: 12%;">审批时间</th><th style="width: 12%;">附件</th><th style="width: 20%;">操作</th>
					</tr>
				</thead>
				<tbody id="goodslistInfo">
					<tr class="select-tr">
						<td>1</td><td>010025</td><td>运动鞋</td><td>鞋/帽</td><td>A-01</td><td>生产部</td><td>451512242</td><td><span class="option" onclick="show_su_pop()">编辑</span><span class="option" onclick="show_approve_pop()">审批</span></td>
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
	<!-- 新增弹窗 -->
	<div class="pop-window-1" id="add_apply_block">
		<div class="pop-title-block">
			<div class="pop-title-info table"><span class="titletext">新增申请</span></div>
			<div class="pop-title-quit" id="quit_2"><div class="quit-x" onclick="close_add_pop()"></div></div>
		</div>
		<div class="pop-body-block">
			<form method="post" onsubmit="return false"enctype="multipart/form-data">
			<table class="addgoodsInfo-table font">
				<tr>
					<td>若无申请模版，请先下载模版！</td><td></td>
				</tr>
				<tr>
					<td><a href="">申请模版下载</a></td>
				</tr>
				<tr>
					<td><input type="text" name="fileShowName" id="fileShowName"style="width:79%;height:4.5%;background-image:url(../../../static/images/fujian1.png);background-repeat:no-repeat;background-size:2.3%;background-position-x:0.3%;background-position-y:30.3%;cursor: pointer;"placeholder="&nbsp;&nbsp;&nbsp;&nbsp;上传文件" msg="上传文件"onclick="$('#submitFile').click();" readonly="readonly" /></td>
					<td><input id="submitFile" type="file" style="display: none;"onchange="upfile()"></td>
				</tr>
			</table>
			</form>
		</div>
		<div class="pop-option-block">
			<input type="button" value="确认" onclick="deal_add()">
			<span class="space"></span>
			<input type="button" value="取消" onclick="close_add_pop()">
		</div>
	</div>
	<!-- 新增弹窗结束 -->
	
	<!-- 审批弹窗 -->
	<div class="pop-window-1" id="approve_block">
		<div class="pop-title-block">
			<div class="pop-title-info table"><span class="titletext">审批</span></div>
			<div class="pop-title-quit" id="quit_2"><div class="quit-x" onclick="close_approve_pop()"></div></div>
		</div>
		<div class="pop-body-block">
			<form method="post" onsubmit="return false"enctype="multipart/form-data">
			<table class="addgoodsInfo-table font">
				<tr>
					<td>通过审批：</td><td><input id="result_yes" style="width: 26%;height: 18px;"type="radio" name="approve_result" value="yes" checked="checked"><label for="result_yes">是</label></td>
					<td><input id="result_no" style="width: 26%;height: 18px;"type="radio" name="approve_result" value="no"><label for="result_no">否</label></td>
				</tr>
				<tr><td colspan="1"><span>备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</span></td><td colspan="5"><textarea id="add_remark"cols="2" class="font"></textarea></td>
				</tr>
			</table>
			</form>
		</div>
		<div class="pop-option-block">
			<input type="button" value="确认" onclick="deal_approve()">
			<span class="space"></span>
			<input type="button" value="取消" onclick="close_approve_pop()">
		</div>
	</div>
	<!-- 审批弹窗结束 -->
	
	<!-- 查看/修改弹窗开始 -->
	<div class="pop-window-1" id="su_block">
		<div class="pop-title-block">
			<div class="pop-title-info table"><span id="pop_title_info" class="titletext">货品详细信息</span></div>
			<div class="pop-title-quit" id="quit_2"><div class="quit-x" onclick="close_su_pop()"></div></div>
		</div>
		<div class="pop-body-block">
			<table class="addgoodsInfo-table">
				<tr><td class="input-name"><span>申请人：</span></td><td class="input-value"><input id="su_goodsName" type="text" ></td>
					<td class="input-name"><span>申请时间：</span></td><td class="input-value"><input id="su_goodsID" type="text" ></td>
				</tr>
				<tr><td class="input-name"><span>审批人：</span></td><td class="input-value"><input id="su_typeName" type="text" ></td>
					<td class="input-name"><span>审批时间：</span></td><td class="input-value"><input id="su_storage" type="text" ></td>
					<td class="input-name"><span>审批状态：</span></td><td class="input-value"><input id="su_simpleName" type="text" ></td>
				</tr>
				<tr><td class="input-name"><span>附&nbsp;件：</span></td><td class="input-value"><input id="su_goodsSource" type="text" ></td>
				</tr>
				<tr><td colspan="1"><span>备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</span></td><td colspan="5"><textarea id="add_remark"cols="2" class="font"></textarea></td>
				</tr>
			</table>
		</div>
		<div class="pop-option-block">
			<input type="button" value="确认" onclick="update_userInfo()">
			<span class="space" id="space_su"></span>
			<input type="button" value="返回" onclick="close_su_pop()">
		</div>
	</div>
	<!-- 查看/修改弹窗结束 -->
</body>
<script type="text/javascript" src="../../../component/common/js/common.js"></script>
<script type="text/javascript" src="../js/supply.js"></script>
</html>