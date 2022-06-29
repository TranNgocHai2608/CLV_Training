package com.clt.apps.opus.esm.clv.practice1.CarrierMgmt.basic;

import java.util.List;

import com.clt.apps.opus.esm.clv.practice1.CarrierMgmt.vo.CarrierMgmtVO;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.core.layer.integration.DAOException;
import com.clt.framework.support.view.signon.SignOnUserAccount;

public interface CarrierMgmtBC  {
	public List<CarrierMgmtVO> searchCarrierMgmt(CarrierMgmtVO carrierMgmtVO) throws EventException, DAOException;
	public List<CarrierMgmtVO> searchCarrierCombo(CarrierMgmtVO carrierMgmtVO) throws EventException, DAOException;
	public List<CarrierMgmtVO> searchLaneCombo(CarrierMgmtVO carrierMgmtVO) throws EventException, DAOException;
	public void manageCarrierMgmt(CarrierMgmtVO[] carrierMgmtVO, SignOnUserAccount account) throws EventException;
	public List<CarrierMgmtVO> searchCustomer(CarrierMgmtVO carrierMgmtVO )  throws EventException, DAOException;
}
