<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../head.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>babasport-list</title>
<script type="text/javascript">
    function checkBox(ids,obj){
    	$("input[name="+ids+"]").attr("checked",obj);
    }
    function optDelete(){
    	var rows = $("input[name=ids]:checked");
    	var y = rows.length;
    	if(y==0){
    		alert("请至少选择一行");
    	}else{
    		var flag = confirm("确定删除吗？");
    		if(flag){
    			var arr = new Array();
    			for(var x=0;x<y;x++){
    				var id = rows[x].value;
    				arr.push(id);
    			}
    			var ids = arr.join(",");
    			$("#id").val(ids);
    			$("#editAndDelete").attr("action","deleteByIds.do");
    			$("#editAndDelete").submit();
    		}
    	}
    }
    function toEdit(id){
    	$("#id").val(id);
    	$("#editAndDelete").attr("action","toEdit.do");
    	$("#editAndDelete").submit();
    }
    function toDelete(id){
    	if(confirm("确定删除吗？")){
    		$("#id").val(id);
        	$("#editAndDelete").attr("action","deleteOne.do");
        	$("#editAndDelete").submit();
    	}
    	
    }
</script>
</head>
<body>
<div class="box-positon">
	<div class="rpos">当前位置: 品牌管理 - 列表</div>
	<form class="ropt">
	    <input class="del-button" type="button" value="删除" onclick="optDelete()"/>
		<input class="add" type="button" value="添加" onclick="javascript:window.location.href='toadd.do'"/>
	</form>
	<div class="clear"></div>
</div>
<div class="body-box">
<form action="list.do" method="post" style="padding-top:5px;" id="editAndDelete">
品牌名称: <input type="text" name="Qname" value="${Qname}"/>
	<select name="QisDisplay">
		<option value="1" <c:if test="${isDisplay==1}">selected="selected"</c:if>>是</option>
		<option value="0" <c:if test="${isDisplay==0}">selected="selected"</c:if>>否</option>
	</select>
	<input type="hidden" name="pageNo" value="${pageBean.pageNo }" />
	<input type="hidden" name="id" id="id" />
	<input type="submit" class="query" value="查询"/>
</form>
<table cellspacing="1" cellpadding="0" border="0" width="100%" class="pn-ltable">
	<thead class="pn-lthead">
		<tr>
			<th width="20"><input type="checkbox" onclick="checkBox('ids',this.checked)"/></th>
			<th>品牌ID</th>
			<th>品牌名称</th>
			<th>品牌图片</th>
			<th>品牌描述</th>
			<th>排序</th>
			<th>是否可用</th>
			<th>操作选项</th>
		</tr>
	</thead>
	<tbody class="pn-ltbody">
	    <c:forEach items="${pageBean.list }" var="brand">
	       <tr bgcolor="#ffffff" onmouseout="this.bgColor='#ffffff'" onmouseover="this.bgColor='#eeeeee'">
			 <td><input type="checkbox" value="${brand.id }" name="ids"/></td>
			 <td align="center">${brand.id }</td>
			 <td align="center">${brand.name }</td>
			 <td align="center"><img width="40" height="40" src="${pageContext.request.contextPath}${brand.imgUrl}"/></td>
			 <td align="center">${brand.description }</td>
			 <td align="center">${brand.sort }</td>
			 <td align="center">
			     <c:if test="${brand.isDisplay==1}">可用</c:if>
			     <c:if test="${brand.isDisplay==0}">不可用</c:if>
			 </td>
			 <td align="center">
			 <a class="pn-opt" onclick="toEdit(${brand.id})" href="#">修改</a> | <a class="pn-opt" onclick="toDelete(${brand.id})" href="#">删除</a>
			 </td>
		 </tr>
	   </c:forEach>
	</tbody>
</table>
<div class="page pb15">
	<span class="r inb_a page_b">
	    <c:forEach items="${pageBean.pageView }" var="page">
	        ${page }
	    </c:forEach>
	</span>
</div>

</div>
</body>
</html>