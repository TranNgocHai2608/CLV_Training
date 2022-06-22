/*=========================================================
 *Copyright(c) 2022 CyberLogitec
 *@FileName : PRACTICE1SC.java
 *@FileTitle : Error Messsage Management
 *Open Issues :
 *Change history :
 *@LastModifyDate : 2022.06.07
 *@LastModifier : 
 *@LastVersion : 1.0
 * 2022.06.07 
 * 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.practice1;

import ice.pilots.html4.DAppletElement;

import java.util.List;

import com.clt.apps.opus.esm.clv.practice1.CarrierMgmt.basic.CarrierMgmtBC;
import com.clt.apps.opus.esm.clv.practice1.CarrierMgmt.basic.CarrierMgmtBCImpl;
import com.clt.apps.opus.esm.clv.practice1.CarrierMgmt.event.practice4Event;
import com.clt.apps.opus.esm.clv.practice1.CarrierMgmt.vo.CarrierMgmtVO;
import com.clt.apps.opus.esm.clv.practice1.codemgmt.basic.CodeMgmtBC;
import com.clt.apps.opus.esm.clv.practice1.codemgmt.basic.CodeMgmtBCImpl;
import com.clt.apps.opus.esm.clv.practice1.codemgmt.event.Practice2Event;
import com.clt.apps.opus.esm.clv.practice1.codemgmt.vo.CodeMgmtDetailVO;
import com.clt.apps.opus.esm.clv.practice1.codemgmt.vo.CodeMgmtVO;
import com.clt.apps.opus.esm.clv.practice1.errmsgmgmt.basic.ErrMsgMgmtBC;
import com.clt.apps.opus.esm.clv.practice1.errmsgmgmt.basic.ErrMsgMgmtBCImpl;
import com.clt.apps.opus.esm.clv.practice1.errmsgmgmt.event.Practice1Event;
import com.clt.framework.core.layer.event.Event;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.core.layer.event.EventResponse;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.core.layer.event.GeneralEventResponse;
import com.clt.framework.core.layer.integration.DAOException;
import com.clt.framework.support.controller.html.FormCommand;
import com.clt.framework.support.layer.service.ServiceCommandSupport;
import com.clt.framework.support.view.signon.SignOnUserAccount;
import com.clt.apps.opus.esm.clv.practice1.errmsgmgmt.vo.ErrMsgVO;

/**
 * ALPS-PRACTICE1 Business Logic ServiceCommand - ALPS-PRACTICE1 대한 비지니스 트랜잭션을
 * 처리한다.
 * 
 * @author Hai Tran
 * @see ErrMsgMgmtDBDAO
 * @since J2EE 1.6
 */

public class PRACTICE1SC extends ServiceCommandSupport {
	// Login User Information
	private SignOnUserAccount account = null;

	/**
	 * PRACTICE1 system 업무 시나리오 선행작업<br>
	 * 업무 시나리오 호출시 관련 내부객체 생성<br>
	 */

	public void doStart() {
		log.debug("PRACTICE1SC 시작");
		try {
			// 일단 comment --> 로그인 체크 부분
			account = getSignOnUserAccount();
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}
	}

	/**
	 * PRACTICE1 system 업무 시나리오 마감작업<br>
	 * 업무 시나리오 종료시 관련 내부객체 해제<br>
	 */

	public void doEnd() {
		log.debug("PRACTICE1SC 종료");
	}

	/**
	 * 각 이벤트에 해당하는 업무 시나리오 수행<br>
	 * ALPS-PRACTICE1 system 업무에서 발생하는 모든 이벤트의 분기처리<br>
	 * 
	 * @param e
	 *            Event
	 * @return EventResponse
	 * @exception EventException
	 */

	public EventResponse perform(Event e) throws EventException {
		// RDTO(Data Transfer Object including Parameters)
		EventResponse eventResponse = null;

		// SC가 여러 이벤트를 처리하는 경우 사용해야 할 부분
		// compare name Event to Practice1Event
		if (e.getEventName().equalsIgnoreCase("Practice1Event")) {
			// event get f_command = Search
			if (e.getFormCommand().isCommand(FormCommand.SEARCH)) {
				// define variable eventResponse = searchErrMsg(e) 
				eventResponse = searchErrMsg(e);
				// event get f_command = MULTI
			} else if (e.getFormCommand().isCommand(FormCommand.MULTI)) {
				// define variable eventResponse = searchErrMsg(e) 
				eventResponse = manageErrMsg(e);
			}
			
			// practice 2
		} else if(e.getEventName().equalsIgnoreCase("Practice2Event")){
			if (e.getFormCommand().isCommand(FormCommand.SEARCH)) {
				eventResponse = searchCodeMgmt(e);
				
			} else if (e.getFormCommand().isCommand(FormCommand.SEARCH01)){
				eventResponse = searchCodeMgmtDetail(e);
				
			} else if (e.getFormCommand().isCommand(FormCommand.MULTI)) {
				eventResponse = manageCodeMgmt(e);
				
			} else if (e.getFormCommand().isCommand(FormCommand.MULTI01)) {
				eventResponse = manageCodeMgmtDetail(e);
			}
			
			//practice 4
		} else if(e.getEventName().equalsIgnoreCase("practice4Event")){
			 if (e.getFormCommand().isCommand(FormCommand.DEFAULT)) {
					eventResponse = initCombo(e);
			} else if (e.getFormCommand().isCommand(FormCommand.SEARCH)) {
				eventResponse = searchCarrierMgmt(e);
				
			} else if (e.getFormCommand().isCommand(FormCommand.MULTI)) {
				eventResponse = manageCarrierMgmt(e);
		}
	}
		return eventResponse;
	}
	

	
	private EventResponse initCombo(Event e) throws  EventException{
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		practice4Event event = (practice4Event) e;
		CarrierMgmtBC command = new CarrierMgmtBCImpl();
		try {
			List<CarrierMgmtVO> list = command.searchCarrierCombo(event.getCarrierMgmtVO());
			eventResponse.setRsVoList(list);
			
			StringBuilder sb = new StringBuilder();
			if(list != null){
				for (int i = 0; i < list.size(); i++) {
					String carriers = list.get(i).getJoCrrCd();
					sb.append(carriers);
					if (i < list.size() - 1) {
						sb.append("|");
					}
				}	
			}
			eventResponse.setETCData("carriers", sb.toString());
			
			List<CarrierMgmtVO> listLane = command.searchLaneCombo(event.getCarrierMgmtVO());
			eventResponse.setRsVoList(listLane);
			
			StringBuilder SBLane = new StringBuilder();
			
			if(listLane != null){
				for (int i = 0; i < listLane.size(); i++) {
					String lanes = listLane.get(i).getRlaneCd();
					SBLane.append(lanes);
					if (i < listLane.size() - 1) {
						SBLane.append("|");
					}
				}	
			}
			eventResponse.setETCData("lanes", SBLane.toString());
			
		} catch (EventException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		}
		return eventResponse;
	}

	private EventResponse searchErrMsg(Event e) throws EventException {
		// PDTO(Data Transfer Object including Parameters)
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		// define Event e = Practice1Event 
		Practice1Event event = (Practice1Event) e;
		ErrMsgMgmtBC command = new ErrMsgMgmtBCImpl();
		try {
			// return searching result
			List<ErrMsgVO> list = command.searchErrMsg(event.getErrMsgVO());
			eventResponse.setRsVoList(list);
		} catch (EventException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		}
		return eventResponse;
	}

	private EventResponse searchCodeMgmt(Event e) throws EventException {
		// PDTO(Data Transfer Object including Parameters)
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		Practice2Event event = (Practice2Event) e;
		CodeMgmtBC command = new CodeMgmtBCImpl();
		try {
			// return searching result
			List<CodeMgmtVO> list = command.searchCodeMgmt(event.getCodeMgmtVO());
			eventResponse.setRsVoList(list);
		} catch (EventException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		}
		return eventResponse;
	}
	
	private EventResponse searchCodeMgmtDetail(Event e) throws EventException {
		// PDTO(Data Transfer Object including Parameters)
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		Practice2Event event = (Practice2Event) e;
		CodeMgmtBC command = new CodeMgmtBCImpl();
		try {
			// return searching result
			List<CodeMgmtDetailVO> list = command.searchCodeMgmtDetail(event.getCodeMgmtDetailVO());
			eventResponse.setRsVoList(list);
		} catch (EventException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		}
		return eventResponse;
	}
	

	private EventResponse manageErrMsg(Event e) throws EventException {
		// PDTO(Data Transfer Object including Parameters)
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		Practice1Event event = (Practice1Event) e;
		ErrMsgMgmtBC command = new ErrMsgMgmtBCImpl();
		try {
			// start transaction
			begin();
			command.manageErrMsg(event.getErrMsgVOS(), account);
			// ETC
			eventResponse.setUserMessage(new ErrorHandler("DOU00001").getUserMessage());
			commit();
		} catch (EventException ex) {
			// if fail while CRUD rollback
			rollback();
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		} catch (Exception ex) {
			rollback();
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		}
		return eventResponse;
	}
	
	private EventResponse manageCodeMgmt(Event e) throws EventException {
		// PDTO(Data Transfer Object including Parameters)
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		Practice2Event event = (Practice2Event) e;
		CodeMgmtBC command = new CodeMgmtBCImpl();
		try {
			begin();
			command.manageCodeMgmt(event.getCodeMgmtVOs(), account);
			eventResponse.setUserMessage(new ErrorHandler("DOU00001").getUserMessage());
			commit();
		} catch (EventException ex) {
			rollback();
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		} catch (Exception ex) {
			rollback();
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		}
		return eventResponse;
	}
	
	private EventResponse manageCodeMgmtDetail(Event e) throws EventException {
		// PDTO(Data Transfer Object including Parameters)
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		Practice2Event event = (Practice2Event) e;
		CodeMgmtBC command = new CodeMgmtBCImpl();
		try {
			begin();
			command.manageCodeMgmtDetail(event.getCodeMgmtDetailVOs(), account);
			eventResponse.setUserMessage(new ErrorHandler("DOU00001").getUserMessage());
			commit();
		} catch (EventException ex) {
			rollback();
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		} catch (Exception ex) {
			rollback();
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		}
		return eventResponse;
	}
	
	
	private EventResponse searchCarrierMgmt(Event e) throws EventException {
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		practice4Event event = (practice4Event) e;
		CarrierMgmtBC command = new CarrierMgmtBCImpl();
		try {
			List<CarrierMgmtVO> list = command.searchCarrierMgmt(event.getCarrierMgmtVO());
			eventResponse.setRsVoList(list);
		} catch (EventException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		}
		return eventResponse;
	}
	
	private EventResponse manageCarrierMgmt(Event e) throws EventException{
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		practice4Event event = (practice4Event) e;
		CarrierMgmtBC command = new CarrierMgmtBCImpl();
		try {
			begin();
			command.manageCarrierMgmt(event.getCarrierMgmtVOs(), account);
			eventResponse.setUserMessage(new ErrorHandler("DOU00001").getUserMessage());
			commit();
		} catch (EventException ex) {
			rollback();
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		} catch (Exception ex) {
			rollback();
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		}
		return eventResponse;
	}
}