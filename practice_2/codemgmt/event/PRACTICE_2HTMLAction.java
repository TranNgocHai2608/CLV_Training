package com.clt.apps.opus.esm.clv.practice1.codemgmt.event;

import javax.servlet.http.HttpServletRequest;

import com.clt.framework.component.util.JSPUtil;
import com.clt.framework.core.controller.html.HTMLActionException;
import com.clt.framework.core.layer.event.Event;
import com.clt.framework.core.layer.event.EventResponse;
import com.clt.framework.support.controller.HTMLActionSupport;
import com.clt.framework.support.controller.html.FormCommand;
import com.clt.syscommon.management.opus.codemanagement.event.CodeManagementEvent;
import com.clt.syscommon.management.opus.codemanagement.vo.CodeMgmtCondVO;
import com.clt.syscommon.management.opus.codemanagement.vo.CodeMgmtMstVO;
import com.clt.apps.opus.esm.clv.practice1.codemgmt.vo.CodeMgmtDetailVO;
import com.clt.apps.opus.esm.clv.practice1.codemgmt.vo.CodeMgmtVO;
import com.clt.apps.opus.esm.clv.practice1.errmsgmgmt.vo.ErrMsgVO;

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
 * - com.hanjin.apps.edm.codepublish 화면을 통해 서버로 전송되는 HTML DOM 객체의 Value를 자바 변수로 Parsing<br>
 * - Parsing 한 정보를 Event로 변환, request에 담아 CodePublishSC로 실행요청<br>
 * - CodePublishSC에서 View(JSP)로 실행결과를 전송하는 EventResponse를 request에 셋팅<br>
 *
 * @author SeongWook Kim
 * @see CodeManagementEvent , UI_COM_EDM_001EventResponse 참조
 * @since J2EE 1.4
 */
public class PRACTICE_2HTMLAction extends HTMLActionSupport {

	/**
	 * CodeManagementHTMLAction 객체를 생성
	 */
	public PRACTICE_2HTMLAction() {
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
    	Practice2Event event = new Practice2Event();
    	
    	if(command.isCommand(FormCommand.SEARCH)){
    		CodeMgmtVO err = new CodeMgmtVO();
    		err.setIntgCdId(JSPUtil.getParameter(request, "s_cd_id"));
    		err.setOwnrSubSysCd(JSPUtil.getParameter(request, "s_sub_system"));
			event.setCodeMgmtVO(err);
			
    	} else if (command.isCommand(FormCommand.SEARCH01)){
    		CodeMgmtDetailVO err = new CodeMgmtDetailVO(); 
    		err.setIntgCdId(JSPUtil.getParameter(request, "dblclick"));
    		event.setCodeMgmtDetailVO(err);
    		
    	}else if(command.isCommand(FormCommand.MULTI)) {
			event.setCodeMgmtVOs((CodeMgmtVO[])getVOs(request, CodeMgmtVO.class));
			
		} else if(command.isCommand(FormCommand.MULTI01)) {
			event.setCodeMgmtDetailVOs((CodeMgmtDetailVO[])getVOs(request, CodeMgmtDetailVO.class));
		}
		return  event;
	}

	
	/**
	 * HttpRequest의 attribute에 업무시나리오 수행결과 값 저장<br>
	 * ServiceCommand에서 View(JSP)로 실행결과를 전송하는 ResultSet을 request에 셋팅<br>
	 * 
	 * @param request HttpServletRequest HttpRequest
	 * @param eventResponse EventResponse interface를 구현한 객체
	 */
	public void doEnd(HttpServletRequest request, EventResponse eventResponse) {
		request.setAttribute("EventResponse", eventResponse);
	}

	/**
	 * HttpRequest의 attribute에 HttpRequest 파싱 수행결과 값 저장<br>
	 * HttpRequest 파싱 수행결과 값 request에 셋팅<br>
	 * 
	 * @param request HttpServletRequest HttpRequest
	 * @param event Event interface를 구현한 객체
	 */
	public void doEnd(HttpServletRequest request, Event event) {
		request.setAttribute("Event", event);
	}

}