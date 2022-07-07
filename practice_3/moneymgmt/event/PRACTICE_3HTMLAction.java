package com.clt.apps.opus.esm.clv.practice1.moneymgmt.event;

import javax.servlet.http.HttpServletRequest;

import com.clt.apps.opus.esm.clv.practice1.moneymgmt.vo.SearchDetailsVO;
import com.clt.apps.opus.esm.clv.practice1.moneymgmt.vo.SummaryVO;
import com.clt.framework.component.util.JSPUtil;
import com.clt.framework.core.controller.html.HTMLActionException;
import com.clt.framework.core.layer.event.Event;
import com.clt.framework.core.layer.event.EventResponse;
import com.clt.framework.support.controller.HTMLActionSupport;
import com.clt.framework.support.controller.html.FormCommand;
import com.clt.syscommon.management.opus.codemanagement.event.CodeManagementEvent;

/*=========================================================
*Copyright(c) 2006 CyberLogitec
*@FileName : UI_COM_EDM_001HTMLAction.java
*@FileTitle : 공통코드관리
*Open Issues :
*Change history :
*@LastModifyDate : 2006-09-07
*@LastModifier : SeongWook Kim
*@LastVersion : 1.0
* 2006-09-07 SeongWook Kim
* 1.0 최초 생성
=========================================================*/

/**
 * HTTP Parser<br>
 * - Parsing the value of the HTML DOM object sent to the server through the com.clt.apps.opus.esm.clv.doutraining.moneymgmt screen as a Java variable<br>
 * - Parsing information is converted into an event, put in a request, and requested to be executed by MoneyMgmtSC<br>
 * - EventResponse that transmits execution result from MoneyMgmtSC to View (JSP) is set in the request<br>
 * @author haitran
 * @see moneymgmtEvent 참조
 * @since J2EE 1.6
 */
public class PRACTICE_3HTMLAction extends HTMLActionSupport {

	/**
	 * CodeManagementHTMLAction 객체를 생성
	 */
	public PRACTICE_3HTMLAction() {
	}

	/**
	 * HTML DOM 객체의 Value를 자바 변수로 Parsing<br>
	 * HttpRequst의 정보를 UI_COM_EDM_001Event로 파싱하여 request에 셋팅<br>
	 * 
	 * @param request HttpServletRequest HttpRequest
	 * @return Event Event interface를 구현한 객체
	 * @exception HTMLActionException
	 */
	public Event perform(HttpServletRequest request) throws HTMLActionException {
		
    	FormCommand command = FormCommand.fromRequest(request);
    	Practice3Event event = new Practice3Event();
    	
    	if(command.isCommand(FormCommand.SEARCH)){
    		SummaryVO summaryVO = new SummaryVO();
    		summaryVO.setAcctYrmonFr(JSPUtil.getParameter(request, "acct_yrmon_from", ""));
			summaryVO.setAcctYrmonTo(JSPUtil.getParameter(request, "acct_yrmon_to", ""));
			summaryVO.setTrdCd(JSPUtil.getParameter(request, "s_trade_cd", ""));
			summaryVO.setRlaneCd(JSPUtil.getParameter(request, "s_rlane_cd", ""));
			summaryVO.setJoCrrCd(JSPUtil.getParameter(request, "s_jo_crr_cd", ""));
			event.setSummaryVO(summaryVO);
			
//    	} else if (command.isCommand(FormCommand.SEARCH01)){
//    		CodeMgmtDetailVO err = new CodeMgmtDetailVO(); 
//    		err.setIntgCdId(JSPUtil.getParameter(request, "dblclick"));
//    		event.setCodeMgmtDetailVO(err);
    		
    	}else if(command.isCommand(FormCommand.SEARCH01)) {
    		SummaryVO summaryVO = new SummaryVO();
    		summaryVO.setJoCrrCd(JSPUtil.getParameter(request, "s_jo_crr_cd", ""));
			event.setSummaryVO(summaryVO);
			
		} else if(command.isCommand(FormCommand.SEARCH02)) {
			SummaryVO summaryVO = new SummaryVO();
    		summaryVO.setJoCrrCd(JSPUtil.getParameter(request, "s_jo_crr_cd", ""));
			summaryVO.setRlaneCd(JSPUtil.getParameter(request, "s_rlane_cd", ""));
			event.setSummaryVO(summaryVO);
			
		} else if(command.isCommand(FormCommand.SEARCH03)){
    		SearchDetailsVO searchDetailsVO = new SearchDetailsVO();
    		searchDetailsVO.setAcctYrmonFr(JSPUtil.getParameter(request, "acct_yrmon_from", ""));
    		searchDetailsVO.setAcctYrmonTo(JSPUtil.getParameter(request, "acct_yrmon_to", ""));
    		searchDetailsVO.setTrdCd(JSPUtil.getParameter(request, "s_trade_cd", ""));
    		searchDetailsVO.setRlaneCd(JSPUtil.getParameter(request, "s_rlane_cd", ""));
    		searchDetailsVO.setJoCrrCd(JSPUtil.getParameter(request, "s_jo_crr_cd", ""));
			event.setSearchDetailsVO(searchDetailsVO);
    	}
		return  event;
	}

	
	/**
	 * Storing the business scenario execution result value in the attribute of HttpRequest<br>
	 * Setting the ResultSet that transmits the execution result from ServiceCommand to View (JSP) in the request<br>
	 * 
	 * @param request HttpServletRequest HttpRequest
	 * @param eventResponse An object that implements the EventResponse interface.
	 */
	public void doEnd(HttpServletRequest request, EventResponse eventResponse) {
		request.setAttribute("EventResponse", eventResponse);
	}

	/**
	 * Saving the HttpRequest parsing result value in the HttpRequest attribute<br>
	 * HttpRequest parsing result value and set in request<br>
	 * 
	 * @param request HttpServletRequest HttpRequest
	 * @param Event An object that implements the Event interface.
	 */
	public void doEnd(HttpServletRequest request, Event event) {
		request.setAttribute("Event", event);
	}

}