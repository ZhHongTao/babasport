<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../head.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>babasport-add</title>
<style type="">
.h2_ch a:hover, .h2_ch a.here {
    color: #FFF;
    font-weight: bold;
    background-position: 0px -32px;
}
.h2_ch a {
    float: left;
    height: 32px;
    margin-right: -1px;
    padding: 0px 16px;
    line-height: 32px;
    font-size: 14px;
    font-weight: normal;
    border: 1px solid #C5C5C5;
    background: url('/images/admin/bg_ch.gif') repeat-x scroll 0% 0% transparent;
}
a {
    color: #06C;
    text-decoration: none;
}
</style>
<script type="text/javascript">
$(function(){
	var tObj;
	$("#tabs a").each(function(){
		if($(this).attr("class").indexOf("here") == 0){tObj = $(this)}
		$(this).click(function(){
			var c = $(this).attr("class");
			if(c.indexOf("here") == 0){return;}
			var ref = $(this).attr("ref");
			var ref_t = tObj.attr("ref");
			tObj.attr("class","nor");
			$(this).attr("class","here");
			$(ref_t).hide();
			$(ref).show();
			tObj = $(this);
			if(ref == '#tab_3'){
				// 编辑器参数
		 		var kingEditorParams = {
					//指定上传文件参数名称
					filePostName  : "uploadFile",
					//指定上传文件请求的url。
					uploadJson : '/upload/uploadFck.do',
					//上传类型，分别为image、flash、media、file
					dir : "image",
// 					width : '1000px',
 					height : '400px'
				}; 
				KindEditor.create('#productdesc',kingEditorParams);
				KindEditor.sync();
			}
		});
	});
});
</script>
</head>
<body>
<div class="box-positon">
	<div class="rpos">当前位置: 商品管理 -查看</div>
	<form class="ropt" action="list.do" method="post">
	    <input type="hidden" name="Qname" value="${Qname}"/>
        <input type="hidden" name="id" id="id"/>
        <input type="hidden" name="pageNo"  value="${pageNo }"/>
        <input type="hidden" name="QisShow"  value="${QisShow }"/>
        <input type="hidden" name="QbrandId"  value="${QbrandId }"/>
		<input type="submit"  value="返回列表" class="return-button"/>
	</form>
	<div class="clear"></div>
</div>
<h2 class="h2_ch"><span id="tabs">
<a href="javascript:void(0);" ref="#tab_1" title="基本信息" class="here">基本信息</a>
<a href="javascript:void(0);" ref="#tab_2" title="商品图片" class="nor">商品图片</a>
<a href="javascript:void(0);" ref="#tab_3" title="商品描述" class="nor">商品描述</a>
<a href="javascript:void(0);" ref="#tab_4" title="包装清单" class="nor">包装清单</a>
</span></h2>
<div class="body-box" style="float:right">
	<form id="jvForm" action="add.do" method="post" enctype="multipart/form-data">
		<table cellspacing="1" cellpadding="2" width="100%" border="0" class="pn-ftable">
			<tbody id="tab_1">
				<tr>
					<td width="20%" class="pn-flabel pn-flabel-h">
						<span class="pn-frequired">*</span>
						商品类型:</td><td width="80%" class="pn-fcontent">
								<select name="typeId">
									<option value="2">瑜珈服</option>
								</select>
					</td>
				</tr>
				<tr>
					<td width="20%" class="pn-flabel pn-flabel-h">
						<span class="pn-frequired">*</span>
						商品名称:</td><td width="80%" class="pn-fcontent">
						${product.name}
					</td>
				</tr>
				<tr>
					<td width="20%" class="pn-flabel pn-flabel-h">
						商品品牌:</td><td width="80%" class="pn-fcontent">
							    ${product.brandName }
					</td>
				</tr>
				<tr>
					<td width="20%" class="pn-flabel pn-flabel-h">
						商品毛重:</td><td width="80%" class="pn-fcontent">
						${product.weight }KG
					</td>
				</tr>
				<tr>
					<td width="20%" class="pn-flabel pn-flabel-h">
						<span class="pn-frequired">*</span>
						颜色:</td>
					<td width="80%" class="pn-fcontent">
						<c:forEach items="${product.colors}" var="color" varStatus="vs">
						   ${color}
						   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<c:if test="${vs.count%8==0 }"><br/></c:if>
						</c:forEach>
					</td>
				</tr>
				<tr>
					<td width="20%" class="pn-flabel pn-flabel-h">
						<span class="pn-frequired">*</span>
						尺码:</td><td width="80%" class="pn-fcontent">
						<c:forEach items="${product.sizess }" var="s">
						<input type="checkbox" checked="checked" disabled="disabled"/>${s}
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</c:forEach>
					</td>
				</tr>
				<tr>
					<td width="20%" class="pn-flabel pn-flabel-h">
						状态:</td><td width="80%" class="pn-fcontent">
						<c:if test="${product.isNew}"><input type="checkbox" checked="checked"  disabled="disabled"/>新品</c:if>
						<c:if test="${product.isCommend}"><input type="checkbox" checked="checked"  disabled="disabled"/>推荐</c:if>
						<c:if test="${product.isHot}"><input type="checkbox" checked="checked"  disabled="disabled"/>热卖</c:if>
					</td>
				</tr>
				<tr>
					<td width="20%" class="pn-flabel pn-flabel-h">商品图片(90x150尺寸):</td>
						<td width="80%" class="pn-fcontent">
						<c:forEach items="${product.imgUrls }" var="imgUrl">
						    <img width="100" height="100" src="${imgUrl }" />
						</c:forEach>
					</td>
				</tr>
			</tbody>
			<tbody id="tab_2" style="display: none">
				<tr>
					<td width="20%" class="pn-flabel pn-flabel-h">
						<span class="pn-frequired">*</span>
						上传商品图片(90x150尺寸):</td>
						<td width="80%" class="pn-fcontent">
						注:该尺寸图片必须为90x150。
					</td>
				</tr>
				<tr>
					<td width="20%" class="pn-flabel pn-flabel-h"></td>
						<td width="80%" class="pn-fcontent">
						<c:forEach items="${product.imgUrls }" var="imgUrl">
						    <img width="100" height="100" src="${imgUrl }" />
						</c:forEach>
					</td>
				</tr>
			</tbody>
			<tbody id="tab_3" style="display: none">
				<tr>
					<td >
						<textarea rows="20" cols="180" id="productdesc" name="description">${product.description }</textarea>
					</td>
				</tr>
			</tbody>
			<tbody id="tab_4" style="display: none">
				<tr>
					<td >
						<textarea rows="20" cols="180" id="productList" disabled="disabled"  name="packageList">${product.packageList }</textarea>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</div>
</body>
</html>