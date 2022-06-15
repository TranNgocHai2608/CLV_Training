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
package com.clt.apps.opus.esm.clv.practice1.errmsgmgmt.basic;

import java.util.ArrayList;
import java.util.List;

import com.clt.apps.opus.esm.clv.practice1.errmsgmgmt.integration.ErrMsgMgmtDBDAO;
import com.clt.apps.opus.esm.clv.practice1.errmsgmgmt.vo.ErrMsgVO;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.core.layer.integration.DAOException;
import com.clt.framework.support.layer.basic.BasicCommandSupport;
import com.clt.framework.support.view.signon.SignOnUserAccount;

/**
 * ALPS-PRACTICE1 Business Logic Command Interface<br>
 * - ALPS-PRACTICE1에 대한 비지니스 로직에 대한 인터페이스<br>
 *
 * @author Hai Tran
 * @since J2EE 1.6
 */
public class ErrMsgMgmtBCImpl extends BasicCommandSupport implements
		ErrMsgMgmtBC {

	// Database Access Object
	private transient ErrMsgMgmtDBDAO dbDao = null;

	/**
	 * ErrMsgMgmtBCImpl 객체 생성<br>
	 * ErrMsgMgmtDBDAO를 생성한다.<br>
	 */
	
	public ErrMsgMgmtBCImpl() {
		dbDao = new ErrMsgMgmtDBDAO();
	}

	/**
	 * [비즈니스대상]을 [행위] 합니다.<br>
	 * 
	 * @param ErrMsgVO
	 *            errMsgVO
	 * @return List<ErrMsgVO>
	 * @exception EventException
	 */
	
	
	public List<ErrMsgVO> searchErrMsg(ErrMsgVO errMsgVO) throws EventException {
		try {
			return dbDao.searchErrMsg(errMsgVO);
		} catch (DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		}
	}

	/**
	 * [비즈니스대상]을 [행위] 합니다.<br>
	 * 
	 * @param ErrMsgVO
	 *            [] errMsgVO
	 * @param account
	 *            SignOnUserAccount
	 * @exception EventException
	 */
	
	public void manageErrMsg(ErrMsgVO[] errMsgVO, SignOnUserAccount account) throws EventException {
		try {
			List<ErrMsgVO> insertVoList = new ArrayList<ErrMsgVO>();
			List<ErrMsgVO> updateVoList = new ArrayList<ErrMsgVO>();
			List<ErrMsgVO> deleteVoList = new ArrayList<ErrMsgVO>();
			for (int i = 0; i < errMsgVO.length; i++) {
				// get ibflag = I => insert
				if (errMsgVO[i].getIbflag().equals("I")) {
					// if ErrMsgCd exist 
					if (!((checkDuplicate(errMsgVO[i]).size()) == 0)) {
						throw new EventException((new ErrorHandler("ERR00011", new String[] {
										errMsgVO[i].getErrMsgCd(),
										errMsgVO[i].getErrMsgCd()}))
										.getMessage());
					} else {
						// set CreUsrId is  getSignOnUserAccount();
						errMsgVO[i].setCreUsrId(account.getUsr_id());
						// set UpdUsrId is  getSignOnUserAccount();
						errMsgVO[i].setUpdUsrId(account.getUsr_id());	
						// add to insertVoList
						insertVoList.add(errMsgVO[i]);
					}
					// get ibflag = U => Update
				} else if (errMsgVO[i].getIbflag().equals("U")) {
					// set UpdUsrId is  getSignOnUserAccount();
					errMsgVO[i].setUpdUsrId(account.getUsr_id());
					// add to updateVoList
					updateVoList.add(errMsgVO[i]);
					// get ibflag = D => delete
				} else if (errMsgVO[i].getIbflag().equals("D")) {
					// add to deleteVoList
					deleteVoList.add(errMsgVO[i]);
				}
			}
			if (insertVoList.size() > 0) {
				dbDao.addmanageErrMsgS(insertVoList);
			}

			if (updateVoList.size() > 0) {
				dbDao.modifymanageErrMsgS(updateVoList);
			}

			if (deleteVoList.size() > 0) {
				dbDao.removemanageErrMsgS(deleteVoList);
			}
		} catch (DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		}
	}

	
	
	public List<ErrMsgVO> checkDuplicate(ErrMsgVO errMsgVO)	throws EventException {
		try {
			return dbDao.checkDuplicateErrMsg(errMsgVO);
		} catch (DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		}
	}
}