<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../head.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>babasport-list</title>
<script type="text/javascript">
//下架
function isHide(){
	//请至少选择一个
	var rows = $("input[name='ids']:checked");
	var y = rows.length;
	if(y == 0){
		alert("请至少选择一个");
		return;
	}
	//你确定删除吗
	if(!confirm("你确定下架吗？")){
		return;
	}
	var arr = new Array();
	for(var i=0;i<y;i++){
		var id = rows[i].value;
		arr.push(id);
	}
	var ids = arr.join(",")
	$("#id").val(ids);
	//提交 Form表单
	$("#query").attr("action","isNotShow.do");
	$("#query").submit();
}
//上架
function isShow(){
	//请至少选择一个
	var rows = $("input[name='ids']:checked");
	var y = rows.length;
	if(y == 0){
		alert("请至少选择一个");
		return;
	}
	//你确定删除吗
	if(!confirm("你确定上架吗")){
		return;
	}
	var arr = new Array();
	for(var i=0;i<y;i++){
		var id = rows[i].value;
		arr.push(id);
	}
	var ids = arr.join(",")
	$("#id").val(ids);
	//提交 Form表单
	$("#query").attr("action","isShow.do");
	$("#query").submit();
	
}
function look(id){
	$("#query").attr("action","look.do");
	$("#id").val(id);
	$("#query").submit();
	
}
function edit(id){
	$("#query").attr("action","toEdit.do");
	$("#id").val(id);
	$("#query").submit();
	
}
</script>
</head>
<body>
<div class="box-positon">
	<div class="rpos">当前位置: 商品管理 - 列表</div>
	<form class="ropt">
		<input class="add" type="button" value="添加" onclick="window.location.href='toAdd.do'"/>
	</form>
	<div class="clear"></div>
</div>
<div class="body-box">
<form action="list.do" method="post" style="padding-top:5px;" id="query">
名称:<input type="text" name="Qname" value="${Qname}"/>
    <input type="hidden" name="id" id="id"/>
    <input type="hidden" name="pageNo"  value="${pageBean.pageNo }"/>
	<select name="QbrandId">
		<option value="">请选择品牌</option>
		<c:forEach items="${brands }" var="brand"> 
		    <option value="${brand.id }" <c:if test="${brand.id == QbrandId }">selected</c:if> >${brand.name}</option>
		</c:forEach>
	</select>
	<select name="QisShow">
		<option value="1" <c:if test="${QisShow }">selected="selected"</c:if>>上架</option>
		<option value="0" <c:if test="${!QisShow }">selected="selected"</c:if>>下架</option>
	</select>
	<input type="submit" class="query" value="查询"/>
</form>
<form id="jvForm">
<table cellspacing="1" cellpadding="0" width="100%" border="0" class="pn-ltable">
	<thead class="pn-lthead">
		<tr>
			<th width="20"><input type="checkbox" onclick="Pn.checkbox('ids',this.checked)"/></th>
			<th>商品编号</th>
			<th>商品名称</th>
			<th>图片</th>
			<th width="4%">新品</th>
			<th width="4%">热卖</th>
			<th width="4%">推荐</th>
			<th width="4%">上下架</th>
			<th width="12%">操作选项</th>
		</tr>
	</thead>
	<tbody class="pn-ltbody">
	   <c:forEach items="${pageBean.list}" var="product">
		  <tr bgcolor="#ffffff" onmouseover="this.bgColor='#eeeeee'" onmouseout="this.bgColor='#ffffff'">
		  	<td><input type="checkbox" name="ids" value="${product.id }"/></td>
		  	<td>${product.id }</td>
		  	<td align="center">${product.name }</td>
		  	
		  	<td align="center"><img width="50" height="50" <c:if test="${product.imgUrls!=null}">src="${product.imgUrls[0] }"</c:if> /></td>
		  	<c:if test="${product.isNew }"></c:if>
		  	<td align="center">
		  	    <c:if test="${product.isNew }">是</c:if>
		  	    <c:if test="${!product.isNew }">否</c:if>
		  	</td>
		  	<td align="center">
		  	    <c:if test="${product.isHot }">是</c:if>
		  	    <c:if test="${!product.isHot }">否</c:if>
		  	</td>
		  	<td align="center">
		  	    <c:if test="${product.isCommend }">是</c:if>
		  	    <c:if test="${!product.isCommend }">否</c:if>
		  	</td>
		  	<td align="center">
		  	    <c:if test="${product.isShow }">上架</c:if>
		  	    <c:if test="${!product.isShow }">下架</c:if>
		  	</td>
		  	<td align="center">
		  	<a href="#" onclick="look(${product.id})" class="pn-opt">查看</a> | <a href="#" onclick="edit(${product.id})" class="pn-opt">修改</a> | <a href="#" onclick="if(!confirm('您确定删除吗？')) {return false;}" class="pn-opt">删除</a> | <a href="/sku/list.do?productId=${product.id }" class="pn-opt">库存</a>
		  	</td>
		  </tr>
	   </c:forEach>
	</tbody>
</table>
<div class="page pb15">
	<span class="r inb_a page_b">
	    <c:forEach items="${pageBean.pageView}" var="page">
	        ${page}
	    </c:forEach>
	</span>
</div>
<div style="margin-top:15px;"><input class="del-button" type="button" value="删除" onclick="optDelete();"/><input class="add" type="button" value="上架" onclick="isShow();"/><input class="del-button" type="button" value="下架" onclick="isHide();"/></div>
</form>
</div>
</body>
</html>