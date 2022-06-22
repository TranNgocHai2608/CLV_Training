
<%
	/*=========================================================
	 *Copyright(c) 2022 CyberLogitec
	 *@FileName : PRACTICE_1.jsp
	 *@FileTitle : Error Messsage Management
	 *Open Issues :
	 *Change history :
	 *@LastModifyDate : 2022.06.07
	 *@LastModifier : 
	 *@LastVersion : 1.0
	 * 2022.06.07 
	 * 1.0 Creation
	 =========================================================*/
%>

<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="com.clt.framework.component.util.JSPUtil"%>
<%@ page import="com.clt.framework.component.util.DateTime"%>
<%@ page import="com.clt.framework.component.message.ErrorHandler"%>
<%@ page
	import="com.clt.framework.core.layer.event.GeneralEventResponse"%>
<%@ page
	import="com.clt.framework.support.controller.html.CommonWebKeys"%>
<%@ page
	import="com.clt.framework.support.view.signon.SignOnUserAccount"%>
<%@ page
	import="com.clt.apps.opus.esm.clv.practice1.errmsgmgmt.event.Practice1Event"%>
<%@ page import="org.apache.log4j.Logger"%>

<script language="javascript">
	function setupPage(){
		loadPage();
	}
</script>

<form name="form">
	<input type="hidden" name="f_cmd"> <input type="hidden"
		name="pagerows">
		<input type="hidden" name="dblclick"> <input type="hidden"
		name="pagerows">
	<!-- 개발자 작업	-->
	<div class="page_title_area clear">
		<h2 class="page_title">
			<button type="button">
				<span id="title"><B>PRACTICE 2</B></span>
			</button>
		</h2>
		<div class="opus_design_btn">
			<button type="button" class="btn_accent" name="btn_Retrieve"
				id="btn_Retrieve">Retrieve</button>
			<!--
   -->
			<button type="button" class="btn_normal" name="btn_Save"
				id="btn_Save">Save</button>
			<!--
   -->
		</div>
		<div class="location">
			<span id="navigation"></span>
		</div>
	</div>

	<div class="wrap_search">
		<div class="opus_design_inquiry">
			<table>
				<tbody>
					<tr>
						<th width="40">Subsystem</th>
						<td width="120"><input type="text" style="width: 100px;"
							class="input" value="" name="s_sub_system" id="s_sub_system"></td>
						<th width="40">Cd ID</th>
						<td><input type="text" style="width: 100px;" class="input"
							value="" name="s_cd_id" id="s_cd_id"></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	<div class="wrap_result">
	<div class="opus_design_grid">
		<h3 class="title_design">Master</h3>
		<div class="opus_design_grid">
			<div class="opus_design_btn">
				<button type="button" class="btn_accent" name="btn_Add_sheet1" id="btn_Add_sheet1">Row
					Add</button> <!--
					--><button type="button" class="btn_accent" name="btn_Del_sheet1" id="btn_Del_sheet1">Row
					Delete</button>
			</div>
			<script language="javascript">
				ComSheetObject('sheet1');
			</script>
		</div>
	</div>
	

	<div class="opus_design_grid">
		<h3 class="title_design">Detail</h3>
		<div class="opus_design_grid">
			<div class="opus_design_btn">
				<button type="button" class="btn_accent" name="btn_Add_sheet2" id="btn_Add_sheet2">Row
					Add</button> <!--
					--><button type="button" class="btn_accent" name="btn_Del_sheet2" id="btn_Del_sheet2">Row
					Delete</button>
			</div>
			<script language="javascript">
				ComSheetObject('sheet2');
			</script>
		</div>
	</div>
</div>
	<!-- 개발자 작업  끝 -->
</form>
