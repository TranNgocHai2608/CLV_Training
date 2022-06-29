package com.clt.apps.opus.esm.clv.practice1.CarrierMgmt.event;

import javax.servlet.http.HttpServletRequest;

import com.clt.apps.opus.esm.clv.practice1.CarrierMgmt.vo.CarrierMgmtVO;
import com.clt.apps.opus.esm.clv.practice1.CarrierMgmt.vo.SearchCustomerVO;
import com.clt.framework.component.util.JSPUtil;
import com.clt.framework.core.controller.html.HTMLActionException;
import com.clt.framework.core.layer.event.Event;
import com.clt.framework.core.layer.event.EventResponse;
import com.clt.framework.support.controller.HTMLActionSupport;
import com.clt.framework.support.controller.html.FormCommand;

public class PRACTICE_4HTMLAction extends HTMLActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * CodeManagementHTMLAction 객체를 생성
	 */
	public PRACTICE_4HTMLAction() {
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
    	practice4Event event = new practice4Event();
    	if(command.isCommand(FormCommand.SEARCH)){
    		CarrierMgmtVO carrierMgmtVO = new CarrierMgmtVO();
    		carrierMgmtVO.setJoCrrCd(JSPUtil.getParameter(request, "s_carrier"));
    		carrierMgmtVO.setVndrSeq(JSPUtil.getParameter(request, "s_vndr_seq"));
    		carrierMgmtVO.setSCreDtFm(JSPUtil.getParameter(request, "s_cre_dt_fm"));
    		carrierMgmtVO.setSCreDtTo(JSPUtil.getParameter(request, "s_cre_dt_to"));
//    		event.setCarrierMgmtVO((CarrierMgmtVO)getVO(request, CarrierMgmtVO.class));
    		event.setCarrierMgmtVO(carrierMgmtVO);
    		
    	} else if(command.isCommand(FormCommand.MULTI)) {
			event.setCarrierMgmtVOs((CarrierMgmtVO[])getVOs(request, CarrierMgmtVO.class));
			
		} else if(command.isCommand(FormCommand.SEARCH01)){
			CarrierMgmtVO carrierMgmtVO = new CarrierMgmtVO();
			carrierMgmtVO.setCustCntCd(JSPUtil.getParameter(request, "s_cust_cnt_cd"));
			carrierMgmtVO.setCustSeq(JSPUtil.getParameter(request, "s_cust_seq"));
    		
    		event.setCarrierMgmtVO(carrierMgmtVO);    		
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
