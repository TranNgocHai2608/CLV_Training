<%
	/*=========================================================
	 *Copyright(c) 2022 CyberLogitec
	 *@FileName : ESM_DOU_0108.jsp
	 *@FileTitle : Money Management
	 *Open Issues :
	 *Change history :
	 *@LastModifyDate : 2022.05.13
	 *@LastModifier : 
	 *@LastVersion : 1.0
	 * 2022.04.22 
	 * 1.0 Creation
	 =========================================================*/
%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="com.clt.framework.component.util.JSPUtil"%>
<%@ page import="com.clt.framework.component.util.DateTime"%>
<%@ page import="com.clt.framework.component.message.ErrorHandler"%>
<%@ page import="com.clt.framework.core.layer.event.GeneralEventResponse"%>
<%@ page import="com.clt.framework.support.controller.html.CommonWebKeys"%>
<%@ page import="com.clt.framework.support.view.signon.SignOnUserAccount"%>
<%@ page import="com.clt.apps.opus.esm.clv.practice1.moneymgmt.event.Esm_Dou_0108Event"%>
<%@ page import="org.apache.log4j.Logger"%>

<%
	Esm_Dou_0108Event event = null; //PDTO(Data Transfer Object including Parameters)
	Exception serverException = null; //서버에서 발생한 에러
	String strErrMsg = ""; //에러메세지
	int rowCount = 0; //DB ResultSet 리스트의 건수
	String successFlag = "";
	String codeList = "";
	String pageRows = "100";
	String partner = "";
	try {
		GeneralEventResponse eventResponse = (GeneralEventResponse) request.getAttribute("EventResponse");
		/* partner = eventResponse.getETCData("partners"); */
		partner = eventResponse.getETCData("partner");
	} catch (Exception e) {
		out.println(e.toString());
	}
%>

<script language="javascript">
	var partnerCombo = "All|<%=partner%>";
	function setupPage(){
		loadPage();
	}
</script>

<form name="form">
	<input type="hidden" name="f_cmd"> 
	<input type="hidden" name="pagerows"> 
	<input type="hidden" name="value_partner">
	<input type="hidden" name="dblclick"> 
	<!-- page_title_area(S) -->
	<div class="page_title_area clear">
		<!-- page_title(S) -->
		<h2 class="page_title">
			<button type="button">
			
				<span id="title"><b></b></span>
			</button>
		</h2>
		
		<!-- page_title(E) -->
		<!-- opus_design_btn(S) -->
		<div class="opus_design_btn">
			<button type="button" class="btn_accent" name="btn_Retrieve" id="btn_Retrieve">Retrieve</button><!--
			--><button type="button" class="btn_normal" name="btn_New" id="btn_New">New</button><!--
			--><button type="button" class="btn_normal" name="btn_DownExcel"id="btn_DownExcel">Down Excel</button><!-- 
			--><button type="button" class="btn_normal" name="btn_DownExcel2" id="btn_DownExcel2">Down Excel2</button>
		</div>
		<!-- opus_design_btn(E) -->
		<!-- page_location(S) -->
		<div class="location">
			<span id="navigation"></span>
		</div>
		<!-- page_location(E) -->
	</div>
	<!-- page_title_area(E) -->
	
	<!-- wrap_search(S) -->
	<div class="wrap_search_tab">
		<!-- opus_design_inquiry(S) -->
		<div class="opus_design_inquiry wFit">
			<table>
				<tbody>
				<colgroup>
						<col width="80px">
						<col width="100px">
						<col width="105px">
						<col width="75px">
						<col width="55px">
						<col width="75px">
						<col width="55px">
						<col width="*" />
					</colgroup>
						<th>Year Month</th>
						<td>
							<input type="text" style="width: 100px;" class="input1" value="" name="acct_yrmon_from" id="acct_yrmon_from" readonly><!--
							--><button type="button" class="btn_left" name="btn_datefrom_down" id="btn_datefrom_down"></button><!--
							--><button type="button" class="btn_right" name="btn_datefrom_up" id="b	tn_datefrom_up"></button><!--
							--><input type="text" style="width: 100px;" class="input1" value="" name="acct_yrmon_to" id="acct_yrmon_to" readonly><!--
							--><button type="button" class="btn_left" name="btn_dateto_down" id="btn_dateto_down"></button><!--
							--><button type="button" class="btn_right" name="btn_dateto_up" id="btn_dateto_up"></button>
						</td>
						<th>Partner</th>
						<td><script type="text/javascript">ComComboObject('s_jo_crr_cd', 1, 100, 1, 0, 0);</script></td>
						<th>Lane</th>
						<td><script type="text/javascript">ComComboObject('s_rlane_cd', 1, 100, 1, 0, 0);</script></td>
						<th>Trade</th>
						<td><script type="text/javascript">ComComboObject('s_trade_cd', 1, 100, 1, 0, 0);</script></td>
				</tbody>
			</table>
		</div>
		<!-- opus_design_inquiry(E) -->
	</div>
	<!-- wrap_search(E) -->

<!-- wrap_result(S) -->
	<div class="wrap_result">
	    <div class="opus_design_tab sm">
	        <script type="text/javascript">ComTabObject('tab1')</script>
	    </div>
	    <!-- opus_design_grid(S) -->
	    <div class="opus_design_grid clear" name="tabLayer" id="tabLayer">
	        <script language="javascript">ComSheetObject('sheet1');</script>
	    </div>
	    <!-- opus_design_grid(E) -->
	
	    <!-- opus_design_grid(S) -->
	    <div class="opus_design_grid clear" name="tabLayer" id="tabLayer">
	        <script language="javascript">ComSheetObject('sheet2');</script>
	    </div>
	    <!-- opus_design_grid(E) -->
	</div>
      
      <!-- wrap_result(E) -->
</form>