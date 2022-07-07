/*=========================================================
 *Copyright(c) 2022 CyberLogitec
 *@FileName : ErrMsgMgmtBCImpl.java
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

import com.clt.apps.opus.esm.clv.practice1.moneymgmt.integration.MoneyMgmtDBDAO;
import com.clt.apps.opus.esm.clv.practice1.moneymgmt.vo.SearchDetailsVO;
import com.clt.apps.opus.esm.clv.practice1.moneymgmt.vo.SummaryVO;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.core.layer.integration.DAOException;
import com.clt.framework.support.layer.basic.BasicCommandSupport;

/**
 * ALPS-PRACTICE1 Business Logic Command Interface<br>
 * - ALPS-PRACTICE1에 대한 비지니스 로직에 대한 인터페이스<br>
 *
 * @author Hai Tran
 * @since J2EE 1.6
 */
public class MoneyMgmtBCImpl extends BasicCommandSupport implements
		MoneyMgmtBC {

	// Database Access Object
	private transient MoneyMgmtDBDAO dbDao = null;
	
	public MoneyMgmtBCImpl() {
		dbDao = new MoneyMgmtDBDAO();
	}
	
	@Override
	public List<SummaryVO> searchSummaryMgmt(SummaryVO summaryVO) throws EventException{
		try {
			return dbDao.searchSummaryMgmt(summaryVO);
		} catch (DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		}
	}
	public List<SearchDetailsVO> searchDetails(SearchDetailsVO searchDetailsVO) throws EventException{
		try {
			return dbDao.searchDetails(searchDetailsVO);
		} catch (DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		}
	}
	public List<SummaryVO> searchPartnerCombo(SummaryVO summaryVO) throws EventException{
		try {
			return dbDao.searchPartnerCombo(summaryVO);
		} catch (DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		}
	}
	
	public List<SummaryVO> searchLaneCombo(SummaryVO summaryVO) throws EventException{
		try {
			return dbDao.searchLaneCombo(summaryVO);
		} catch (DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		}
	}
	
	public List<SummaryVO> searchTradeCombo(SummaryVO summaryVO) throws EventException{
		try {
			return dbDao.searchTradeCombo(summaryVO);
		} catch (DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		}
	}

}