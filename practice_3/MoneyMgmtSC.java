/*=========================================================
 *Copyright(c) 2022 CyberLogitec
 *@FileName : MoneyMgmtSC.java
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
import com.clt.framework.support.controller.html.FormCommand;
import com.clt.framework.support.layer.service.ServiceCommandSupport;
import com.clt.framework.support.view.signon.SignOnUserAccount;
import com.clt.apps.opus.esm.clv.practice1.errmsgmgmt.vo.ErrMsgVO;
import com.clt.apps.opus.esm.clv.practice1.moneymgmt.basic.MoneyMgmtBC;
import com.clt.apps.opus.esm.clv.practice1.moneymgmt.basic.MoneyMgmtBCImpl;
import com.clt.apps.opus.esm.clv.practice1.moneymgmt.event.Esm_Dou_0108Event;
import com.clt.apps.opus.esm.clv.practice1.moneymgmt.vo.SearchDetailsVO;
import com.clt.apps.opus.esm.clv.practice1.moneymgmt.vo.SummaryVO;

/**
 * ALPS-MoneyMgmt Business Logic ServiceCommand - Process business transaction for ALPS-MoneyMgmt.
 * 
 * @author HaiTran
 * @see MoneyMgmtDBDAO
 * @since J2EE 1.6
 */
public class MoneyMgmtSC extends ServiceCommandSupport {
	// Login User Information
	private SignOnUserAccount account = null;

	/**
	* MoneyMgmt system task scenario precedent work<br>
	* Creating related internal objects when calling a business scenario<br>
	*/

	public void doStart() {
		log.debug("MoneyMgmtSC 시작");
		try {
			// 일단 comment --> 로그인 체크 부분
			account = getSignOnUserAccount();
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}
	}

	/**
	* MoneyMgmt system work scenario finishing work<br>
	* Release related internal objects when the work scenario is finished<br>
	*/

	public void doEnd() {
		log.debug("MoneyMgmtSC 종료");
	}

	/**
	 * Carry out business scenarios for each event<br>
	 * Branch processing of all events occurring in ALPS-MoneyMgmt system work<br>
	 * 
	 * @param e Event
	 * @return EventResponse
	 * @exception EventException
	 */

	public EventResponse perform(Event e) throws EventException {
		// RDTO(Data Transfer Object including Parameters)
		EventResponse eventResponse = null;

		// SC가 여러 이벤트를 처리하는 경우 사용해야 할 부분
		if (e.getEventName().equalsIgnoreCase("Esm_Dou_0108Event")) {
		 if(e.getFormCommand().isCommand(FormCommand.DEFAULT)){
			 eventResponse = searchPartner(e);
		 	}else if (e.getFormCommand().isCommand(FormCommand.SEARCH)) {
				eventResponse = searchMoneyMgmt(e);
	
			} else if (e.getFormCommand().isCommand(FormCommand.SEARCH01)) {
				eventResponse = searchLane(e);
				
			} else if (e.getFormCommand().isCommand(FormCommand.SEARCH02)) {
				eventResponse = searchTrade(e);
				
			} else if (e.getFormCommand().isCommand(FormCommand.SEARCH03)) {
				eventResponse = searchDetails(e);
			}
		}
		return eventResponse;
	}
	
	/**
	 *  Initializing data for partner combobox
	 * @param e
	 * @return
	 * @throws EventException
	 */
	private EventResponse searchPartner(Event e) throws  EventException{
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		Esm_Dou_0108Event event = (Esm_Dou_0108Event) e;
		MoneyMgmtBC command = new MoneyMgmtBCImpl();
		try {
			List<SummaryVO> list = command.searchPartnerCombo(event.getSummaryVO());
			eventResponse.setRsVoList(list);
			
			StringBuilder sb = new StringBuilder();
			if(list != null){
				for (int i = 0; i < list.size(); i++) {
					String partner = list.get(i).getJoCrrCd();
					sb.append(partner);
					if (i < list.size() - 1) {
						sb.append("|");
					}
				}	
			}
			eventResponse.setETCData("partner", sb.toString());
		
		} catch (EventException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		}
		return eventResponse;
	}
	
	/**
	 *  Initializing data for Lane combo box
	 * @param e
	 * @return
	 * @throws EventException
	 */
	private EventResponse searchLane(Event e) throws  EventException{
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		Esm_Dou_0108Event event = (Esm_Dou_0108Event) e;
		MoneyMgmtBC command = new MoneyMgmtBCImpl();
		try {
			List<SummaryVO> list = command.searchLaneCombo(event.getSummaryVO());
			eventResponse.setRsVoList(list);
			
			StringBuilder laneSb = new StringBuilder();
			if(list != null){
				for (int i = 0; i < list.size(); i++) {
					String lane = list.get(i).getRlaneCd();
					laneSb.append(lane);
					if (i < list.size() - 1) {
						laneSb.append("|");
					}
				}	
			}
			eventResponse.setETCData("lanes", laneSb.toString());
		
		} catch (EventException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		}
		return eventResponse;
	}
	
	/**
	 *  Initializing data for trade combo box
	 * @param e
	 * @return
	 * @throws EventException
	 */
	private EventResponse searchTrade(Event e) throws  EventException{
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		Esm_Dou_0108Event event = (Esm_Dou_0108Event) e;
		MoneyMgmtBC command = new MoneyMgmtBCImpl();
		try {
			List<SummaryVO> list = command.searchTradeCombo(event.getSummaryVO());
			eventResponse.setRsVoList(list);
			
			StringBuilder tradeSb = new StringBuilder();
			if(list != null){
				for (int i = 0; i < list.size(); i++) {
					String trade = list.get(i).getTrdCd();
					tradeSb.append(trade);
					if (i < list.size() - 1) {
						tradeSb.append("|");
					}
				}	
			}
			eventResponse.setETCData("trades", tradeSb.toString());
		
		} catch (EventException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		}
		return eventResponse;
	}

	/**
	 * ESM_DOU_0108 : [Event]<br>
	 * [Act] for [Business Target].<br>
	 * This method is used for searching Summary data  
	 * @param Event e
	 * @return EventResponse
	 * @exception EventException
	 */
	private EventResponse searchMoneyMgmt(Event e) throws EventException {
		// PDTO(Data Transfer Object including Parameters)
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		// define Event e = Practice1Event 
		Esm_Dou_0108Event event = (Esm_Dou_0108Event) e;
		MoneyMgmtBC command = new MoneyMgmtBCImpl();
		try {
			// return searching result
			List<SummaryVO> list = command.searchSummaryMgmt(event.getSummaryVO());
			eventResponse.setRsVoList(list);
		} catch (EventException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		}
		return eventResponse;
	}
	
	/**
	 * ESM_DOU_0108 : [Event]<br>
	 * [Act] for [Business Target].<br>
	 * This method is used for searching Details data  
	 * @param Event e
	 * @return EventResponse
	 * @exception EventException
	 */
	private EventResponse searchDetails(Event e) throws  EventException{
		// PDTO(Data Transfer Object including Parameters)
				GeneralEventResponse eventResponse = new GeneralEventResponse();
				Esm_Dou_0108Event event = (Esm_Dou_0108Event) e;
				MoneyMgmtBC command = new MoneyMgmtBCImpl();
				try {
					// return searching result
					List<SearchDetailsVO> list = command.searchDetails(event.getSearchDetailsVO());
					eventResponse.setRsVoList(list);
				} catch (EventException ex) {
					throw new EventException(new ErrorHandler(ex).getMessage(), ex);
				} catch (Exception ex) {
					throw new EventException(new ErrorHandler(ex).getMessage(), ex);
				}
				return eventResponse;
	}
}