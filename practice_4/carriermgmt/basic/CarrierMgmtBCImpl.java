package com.clt.apps.opus.esm.clv.practice1.CarrierMgmt.basic;

import java.util.ArrayList;
import java.util.List;

import com.clt.apps.opus.esm.clv.practice1.CarrierMgmt.integration.CarrierMgmtDBDAO;
import com.clt.apps.opus.esm.clv.practice1.CarrierMgmt.vo.CarrierMgmtVO;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.core.layer.integration.DAOException;
import com.clt.framework.support.layer.basic.BasicCommandSupport;
import com.clt.framework.support.view.signon.SignOnUserAccount;

public class CarrierMgmtBCImpl extends BasicCommandSupport implements CarrierMgmtBC {
	// Database Access Object
	private transient CarrierMgmtDBDAO dbDao = null;
	
	public CarrierMgmtBCImpl() {
		dbDao = new CarrierMgmtDBDAO();
	}
	
	@Override
	public List<CarrierMgmtVO> searchCarrierMgmt(CarrierMgmtVO carrierMgmtVO) throws EventException, DAOException {
		try {
			return dbDao.searchCarrierMgmt(carrierMgmtVO);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		}
	}
	
	@Override
	public List<CarrierMgmtVO> searchCustomer(CarrierMgmtVO carrierMgmtVO) throws DAOException, EventException {
		try {
			return dbDao.searchCustomer(carrierMgmtVO);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		}
	}
	@Override
	public List<CarrierMgmtVO> searchCarrierCombo(CarrierMgmtVO carrierMgmtVO) throws EventException {
		try {
			return dbDao.searchCarrierCombo(carrierMgmtVO);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		}
	}

	@Override
	public List<CarrierMgmtVO> searchLaneCombo(CarrierMgmtVO carrierMgmtVO) throws EventException {
	try {
		return dbDao.searchLaneCombo(carrierMgmtVO);
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
	
	public void manageCarrierMgmt(CarrierMgmtVO[] carrierMgmtVO, SignOnUserAccount account) throws EventException {
		try {
			List<CarrierMgmtVO> insertVoList = new ArrayList<CarrierMgmtVO>();
			List<CarrierMgmtVO> updateVoList = new ArrayList<CarrierMgmtVO>();
			List<CarrierMgmtVO> deleteVoList = new ArrayList<CarrierMgmtVO>();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < carrierMgmtVO.length; i++) {
				// get ibflag = I => insert
				if (carrierMgmtVO[i].getIbflag().equals("I")) {
					
					if (checkDuplicate(carrierMgmtVO[i]).size() == 0) {
						carrierMgmtVO[i].setCreUsrId(account.getUsr_id());
						carrierMgmtVO[i].setUpdUsrId(account.getUsr_id());	
						insertVoList.add(carrierMgmtVO[i]);
					} else {
						
						sb.append(carrierMgmtVO[i].getJoCrrCd() + " - " + carrierMgmtVO[i].getRlaneCd() + "|" );
					}
					// get ibflag = U => Update
				} else if (carrierMgmtVO[i].getIbflag().equals("U")) {
					// set UpdUsrId is  getSignOnUserAccount();
					carrierMgmtVO[i].setUpdUsrId(account.getUsr_id());
					// add to updateVoList
					updateVoList.add(carrierMgmtVO[i]);
					// get ibflag = D => delete
				} else if (carrierMgmtVO[i].getIbflag().equals("D")) {
					// add to deleteVoList
					deleteVoList.add(carrierMgmtVO[i]);
				}
			}
			
			if(sb.length()!=0){
				sb.deleteCharAt(sb.length()-1);
				throw new EventException(new ErrorHandler("ERR12356", new String[]{sb.toString()}).getMessage());
			}
			
			if (insertVoList.size() > 0) {
				dbDao.addmanageCarrierS(insertVoList);
			}

			if (updateVoList.size() > 0) {
				dbDao.modifymanageCarrierS(updateVoList);
			}

			if (deleteVoList.size() > 0) {
				dbDao.removemanageCarrierS(deleteVoList);
			}
		} catch (DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		}
	}
	
	public List<CarrierMgmtVO> checkDuplicate(CarrierMgmtVO carrierMgmtVO)	throws EventException, DAOException {
		try {
			return dbDao.checkDuplicate(carrierMgmtVO);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		}
	}
}
