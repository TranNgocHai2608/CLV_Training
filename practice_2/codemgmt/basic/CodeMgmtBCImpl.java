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
package com.clt.apps.opus.esm.clv.practice1.codemgmt.basic;

import java.util.ArrayList;
import java.util.List;

import com.clt.apps.opus.esm.clv.practice1.codemgmt.integration.CodeMgmtDBDAO;
import com.clt.apps.opus.esm.clv.practice1.codemgmt.vo.CodeMgmtDetailVO;
import com.clt.apps.opus.esm.clv.practice1.codemgmt.vo.CodeMgmtVO;
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
public class CodeMgmtBCImpl extends BasicCommandSupport implements
		CodeMgmtBC {

	// Database Access Object
	private transient CodeMgmtDBDAO dbDao = null;
	
	public CodeMgmtBCImpl() {
		dbDao = new CodeMgmtDBDAO();
	}
	
	@Override
	public List<CodeMgmtVO> searchCodeMgmt(CodeMgmtVO CodeMgmtVO) throws EventException{
		try {
			return dbDao.searchCodeMgmt(CodeMgmtVO);
		} catch (DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		}
	}
	
	public List<CodeMgmtDetailVO> searchCodeMgmtDetail(CodeMgmtDetailVO CodeMgmtDetailVO) throws EventException{
		try {
			return dbDao.searchCodeDtlMgmt(CodeMgmtDetailVO);
		} catch (DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		}
	}
	@Override
	public void manageCodeMgmt(CodeMgmtVO[] codeMgmtVO, SignOnUserAccount account) throws EventException {
		try {
			List<CodeMgmtVO> insertVoList = new ArrayList<CodeMgmtVO>();
			List<CodeMgmtVO> updateVoList = new ArrayList<CodeMgmtVO>();
			List<CodeMgmtVO> deleteVoList = new ArrayList<CodeMgmtVO>();
			List<CodeMgmtDetailVO> deleteDetailVO = new ArrayList<CodeMgmtDetailVO>();
			for (int i = 0; i < codeMgmtVO.length; i++) {
				if (codeMgmtVO[i].getIbflag().equals("I")) {
					if (!((checkDuplicate(codeMgmtVO[i]).size()) == 0)) {
					throw new EventException((new ErrorHandler("ERR00011", new String[] {
							codeMgmtVO[i].getIntgCdId()})).getMessage());
					} else {
						codeMgmtVO[i].setCreUsrId(account.getUsr_id());
						codeMgmtVO[i].setUpdUsrId(account.getUsr_id());	
						insertVoList.add(codeMgmtVO[i]);
					}
				} else if (codeMgmtVO[i].getIbflag().equals("U")) {
					codeMgmtVO[i].setUpdUsrId(account.getUsr_id());
					updateVoList.add(codeMgmtVO[i]);
					
				} else if (codeMgmtVO[i].getIbflag().equals("D")) {
					CodeMgmtDetailVO codeMgmtDetailVO = new CodeMgmtDetailVO();
					codeMgmtDetailVO.setIntgCdId(codeMgmtVO[i].getIntgCdId());
//					deleteDetailVO.add(dbDao.searchCodeDtlMgmt(codeMgmtDetailVO));
					deleteDetailVO.add(codeMgmtDetailVO);
					deleteVoList.add(codeMgmtVO[i]);
				}
			}
			if (insertVoList.size() > 0) {
				dbDao.addmanageCodeMgmtS(insertVoList);
			}

			if (updateVoList.size() > 0) {
				dbDao.modifymanageCodeMgmtS(updateVoList);
			}

			if (deleteVoList.size() > 0) {
				if(deleteDetailVO.size() > 0){
					dbDao.removemanageCodeMgmtDetailSByCD_Val(deleteDetailVO);
				}
				dbDao.removemanageCodeMgmtS(deleteVoList);
			}
		} catch (DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		}
		
	}

	@Override
	public void manageCodeMgmtDetail(CodeMgmtDetailVO[] codeMgmtDetailVO, SignOnUserAccount account) throws EventException {
		try {
			List<CodeMgmtDetailVO> insertVoList = new ArrayList<CodeMgmtDetailVO>();
			List<CodeMgmtDetailVO> updateVoList = new ArrayList<CodeMgmtDetailVO>();
			List<CodeMgmtDetailVO> deleteVoList = new ArrayList<CodeMgmtDetailVO>();
			for (int i = 0; i < codeMgmtDetailVO.length; i++) {
				if (codeMgmtDetailVO[i].getIbflag().equals("I")) {
						codeMgmtDetailVO[i].setCreUsrId(account.getUsr_id());
						codeMgmtDetailVO[i].setUpdUsrId(account.getUsr_id());	
						insertVoList.add(codeMgmtDetailVO[i]);
				} else if (codeMgmtDetailVO[i].getIbflag().equals("U")) {
					codeMgmtDetailVO[i].setUpdUsrId(account.getUsr_id());
					updateVoList.add(codeMgmtDetailVO[i]);
					
				} else if (codeMgmtDetailVO[i].getIbflag().equals("D")) {
					deleteVoList.add(codeMgmtDetailVO[i]);
				}
			}
			if (insertVoList.size() > 0) {
				dbDao.addmanageCodeMgmtDetailS(insertVoList);
			}

			if (updateVoList.size() > 0) {
				dbDao.modifymanageCodeMgmtDetailS(updateVoList);
			}

			if (deleteVoList.size() > 0) {
				dbDao.removemanageCodeMgmtDetailS(deleteVoList);
			}
		} catch (DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		}
		
	}
	public List<CodeMgmtVO> checkDuplicate(CodeMgmtVO codeMgmtVO)	throws EventException {
		try {
			return dbDao.checkDuplicateCodeMgmt(codeMgmtVO);
		} catch (DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		}
	}


}