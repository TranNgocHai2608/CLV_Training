/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : ErrMsgMgmtBC.java
*@FileTitle : Error Messsage Management
*Open Issues :
*Change history :
*@LastModifyDate : 2022.06.07
*@LastModifier : 
*@LastVersion : 1.0
* 2022.06.07 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.practice1.moneymgmt.basic;

import java.util.List;

import com.clt.apps.opus.esm.clv.practice1.moneymgmt.vo.SearchDetailsVO;
import com.clt.apps.opus.esm.clv.practice1.moneymgmt.vo.SummaryVO;
import com.clt.framework.core.layer.event.EventException;

/**
 * ALPS-Moneymgmt Business Logic Command Interface<br>
 * - Interface to business logic for ALPS-Moneymgmt<br>
 *
 * @author Hai Tran
 * @since J2EE 1.6
 */

public interface MoneyMgmtBC {
	/**
	 * Searching Summary data method
	 * @param summaryVO
	 * @return
	 * @throws EventException
	 */
	List<SummaryVO> searchSummaryMgmt(SummaryVO summaryVO) throws EventException;
	/**
	 * Searching Details data
	 * @param searchDetailVO
	 * @return
	 * @throws EventException
	 */
	List<SearchDetailsVO> searchDetails(SearchDetailsVO searchDetailVO) throws EventException;
	/**
	 * Getting Partner combo
	 * @param summaryVO
	 * @return
	 * @throws EventException
	 */
	public List<SummaryVO> searchPartnerCombo(SummaryVO summaryVO) throws EventException;
	/**
	 * Getting Lane combo
	 * @param summaryVO
	 * @return
	 * @throws EventException
	 */
	public List<SummaryVO> searchLaneCombo(SummaryVO summaryVO) throws EventException;
	/**
	 * getting Trade combo
	 * @param summaryVO
	 * @return
	 * @throws EventException
	 */
	public List<SummaryVO> searchTradeCombo(SummaryVO summaryVO) throws EventException;	
}