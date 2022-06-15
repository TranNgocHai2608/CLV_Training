/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : PRACTICE_1HTMLAction.java
*@FileTitle : Error Messsage Management
*Open Issues :
*Change history :
*@LastModifyDate : 2022.06.07
*@LastModifier : 
*@LastVersion : 1.0
* 2022.06.07 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.practice1.errmsgmgmt.event;

import javax.servlet.http.HttpServletRequest;

import com.clt.framework.component.util.JSPUtil;
import com.clt.framework.core.controller.html.HTMLActionException;
import com.clt.framework.core.layer.event.Event;
import com.clt.framework.core.layer.event.EventResponse;
import com.clt.framework.support.controller.HTMLActionSupport;
import com.clt.framework.support.controller.html.FormCommand;
import com.clt.apps.opus.esm.clv.practice1.errmsgmgmt.vo.ErrMsgVO;

/**
 * HTTP Parser<br>
 * - com.clt.apps.opus.esm.clv.practice1 화면을 통해 서버로 전송되는 HTML DOM 객체의 Value를 자바 변수로 Parsing<br>
 * - Parsing 한 정보를 Event로 변환, request에 담아 PRACTICE1SC로 실행요청<br>
 * - PRACTICE1SC에서 View(JSP)로 실행결과를 전송하는 EventResponse를 request에 셋팅<br>
 * @author Hai Tran
 * @see PRACTICE1Event 참조
 * @since J2EE 1.6
 */

public class PRACTICE_1HTMLAction extends HTMLActionSupport {

	private static final long serialVersionUID = 1L;
	/**
	 * PRACTICE_1HTMLAction 객체를 생성
	 */
	public PRACTICE_1HTMLAction() {}

	/**
	 * HTML DOM 객체의 Value를 자바 변수로 Parsing<br>
	 * HttpRequst의 정보를 PRACTICE1Event로 파싱하여 request에 셋팅<br>
	 * @param request HttpServletRequest HttpRequest
	 * @return Event Event interface를 구현한 객체
	 * @exception HTMLActionException
	 */
	public Event perform(HttpServletRequest request) throws HTMLActionException {
		// get f_command
    	FormCommand command = FormCommand.fromRequest(request);
    	// create event
		Practice1Event event = new Practice1Event();
		// f_command = MULTI
		if(command.isCommand(FormCommand.MULTI)) {
			// create collectionVO
			// set VOS for event
			event.setErrMsgVOS((ErrMsgVO[])getVOs(request, ErrMsgVO.class));
		}
		else if(command.isCommand(FormCommand.SEARCH)) {
//			event.setErrMsgVO((ErrMsgVO)getVO(request, ErrMsgVO.class));
			// create a ErrMsgVO object
			ErrMsgVO err = new ErrMsgVO();
			// set data for ErrMsgCd is s_err_msg_cd
			err.setErrMsgCd(JSPUtil.getParameter(request, "s_err_msg_cd"));
			// set data for ErrMsg is s_err_msg
			err.setErrMsg(JSPUtil.getParameter(request, "s_err_msg"));
			// set VO for event
			event.setErrMsgVO(err);
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
		// set Attribute has key EventResponse for request
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
		// set Attribute has key Event for request
		request.setAttribute("Event", event);
	}
}