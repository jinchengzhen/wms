<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="../../../component/common/css/common.css"
	type="text/css"></link>
<link rel="stylesheet" href="../css/userInfoMag.css" type="text/css"></link>
<script type="text/javascript"
	src="../../../component/jquery/jquery-3.2.1.min.js"></script>
<script type="text/javascript"
	src="../../../component/common/js/common.js"></script>
<script type="text/javascript"
	src="../../../component/jquery-md5/jquery-md5-new.js"></script>
<script type="text/javascript" src="../js/userInfoMag.js"></script>
<title>人员信息管理</title>
</head>
<body>
	<div class="tip" id="basePath" basepath=<%= "\""+basePath+"\"" %>
		style="display: none;"></div>
	<div class="common-black" id="black_block"></div>
	<!-- 主体页面开始 -->
	<div class="common-title">
		<div class="condition table">
			<span>姓名：</span> <input id="search_userName" type="text"
				placeholder="请输入姓名">
		</div>
		<div class="condition table">
			<span>身份证号：</span> <input id="search_IDcard" type="text"
				placeholder="请输入身份证号">
		</div>
		<div class="condition table">
			<button onclick="searchInfo()">查询</button>
			&nbsp;&nbsp;
			<button onclick="clearSearchInfo()">重置</button>
			<span class="space"></span>
			<button onclick="show_add_pop()">新增人员</button>
		</div>
	</div>
	<div class="common-body">
		<!--      -->
		<div class="importblock">
			<span class="import" onclick="exportXls()">导出</span> <span
				style="float: right; width: 2%; height: 100%;"></span> <span
				class="import" onclick="">批量导入</span>
		</div>
		<div class="userlist">
			<table>
				<thead>
					<tr style="background-color: #72c2fa;">
						<th style="width: 10%;">序号</th>
						<th style="width: 12%;">姓名</th>
						<th style="width: 16%;">人员编号</th>
						<th style="width: 22%;">身份证号</th>
						<th style="width: 16%;">联系电话</th>
						<th style="width: 24%;">操作</th>
					</tr>
				</thead>
				<tbody id="userlistInfo">
					<tr class="select-tr">
						<td>1</td>
						<td>金成振</td>
						<td>134564</td>
						<td>21105161651562</td>
						<td>系统管理员</td>
						<td><span class="option" onclick="delete_userInfo()">删除人员</span><span
							class="option" onclick="su_userInfo()">查看|编辑</span><span
							class="option" onclick="">密码重置</span><span class="option"
							onclick="">加入黑名单</span></td>
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
	<div class="pop-window-1" id="add_userinfo_block">
		<div class="pop-title-block">
			<div class="pop-title-info table">
				<span class="titletext">新增</span>
			</div>
			<div class="pop-title-quit">
				<div class="quit-x" onclick="close_add_pop()"></div>
			</div>
		</div>
		<div class="pop-body-block">
			<table class="adduserInfo-table">
				<tr>
					<td><span>姓名：</span></td>
					<td><input id="add_userName" type="text" placeholder="请输入姓名"></td>
				</tr>
				<tr>
					<td><span>身份证号：</span></td>
					<td><input id="add_IDcard" type="text" placeholder="请输入身份证信息"></td>
				</tr>
				<tr>
					<td><span>联系电话：</span></td>
					<td><input id="add_phone" type="text" placeholder="请输入手机号码"></td>
				</tr>
				<tr></tr>
			</table>
		</div>
		<div class="pop-option-block">
			<input type="button" value="确认" onclick="deal_add()"> <span
				class="space"></span> <input type="button" value="取消"
				onclick="close_add_pop()">
		</div>
	</div>
	<!-- 新增弹窗结束 -->
	<!-- 查看/修改弹窗开始 -->
	<div class="pop-window-2" id="su_block">
		<div class="pop-title-block">
			<div class="pop-title-info table">
				<span id="pop_title_info" class="titletext">个人详细信息</span>
			</div>
			<div class="pop-title-quit" id="quit_2">
				<div class="quit-x" onclick="close_su_pop()"></div>
			</div>
		</div>
		<div class="pop-body-block">
			<table>
				<tr>
					<td>姓名：</td>
					<td><input id="su_userName" type="text"></td>
					<td>性别：</td>
					<td><input id="su_sex" type="text"></td>
					<td>出生日期：</td>
					<td><input id="su_birthday" type="text"></td>
				</tr>
				<tr>
					<td>年龄：</td>
					<td><input id="su_userAge" type="text"></td>
					<td>电话：</td>
					<td><input id="su_phone" type="text"></td>
					<td>身份证号：</td>
					<td><input id="su_IDcard" type="text"></td>
				</tr>
				<tr>
					<td>用户ID：</td>
					<td><input id="su_userID" type="text" readonly="readonly"></td>
					<td>入职时间：</td>
					<td><input id="su_hireDate" type="text"></td>
				</tr>
				<tr></tr>
			</table>
		</div>
		<div class="pop-option-block">
			<input type="button" value="确认" onclick="update_userInfo()">
			<span class="space" id="space_su"></span> <input type="button"
				value="返回" onclick="close_su_pop()">
		</div>
	</div>
	<!-- 查看/修改弹窗结束 -->
	<!-- 菜单权限设置弹窗开始 -->
	<div class="pop-window-2" id="as_block">
		<div class="pop-title-block">
			<div class="pop-title-info table">
				<span id="pop_title_info" class="titletext">权限设置</span>
			</div>
			<div class="pop-title-quit" id="quit_2">
				<div class="quit-x" onclick="close_menuUpdate()"></div>
			</div>
		</div>
		<div class="pop-body-block">
			<div id="menulist" class="menulist-block font-menu"></div>
		</div>
		<div class="pop-option-block">
			<input id="menu_set" type="button" value="确认"
				onclick="updateWorkcontent($(this).attr('user'))" user=""> <span
				class="space" id="space_su"></span> <input type="button" value="返回"
				onclick="close_menuUpdate()"> <input id="loginAut"
				type="checkbox"><label id="loginAut_tip" for="loginAut">禁止登录</label>
		</div>
	</div>
	<!-- 权限设置弹窗结束 -->
</body>
</html>