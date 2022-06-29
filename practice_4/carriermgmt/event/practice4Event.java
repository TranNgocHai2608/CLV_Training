package com.clt.apps.opus.esm.clv.practice1.CarrierMgmt.event;

import com.clt.apps.opus.esm.clv.practice1.CarrierMgmt.vo.CarrierMgmtVO;
import com.clt.framework.support.layer.event.EventSupport;

public class practice4Event extends EventSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	CarrierMgmtVO carrierMgmtVO = null;
	CarrierMgmtVO[] carrierMgmtVOs = null;
	
	public practice4Event() {
		
	}


	public CarrierMgmtVO getCarrierMgmtVO() {
		return carrierMgmtVO;
	}

	public void setCarrierMgmtVO(CarrierMgmtVO carrierMgmtVO) {
		this.carrierMgmtVO = carrierMgmtVO;
	}

	public CarrierMgmtVO[] getCarrierMgmtVOs() {
		return carrierMgmtVOs;
	}

	public void setCarrierMgmtVOs(CarrierMgmtVO[] carrierMgmtVOs) {
		this.carrierMgmtVOs = carrierMgmtVOs;
	}
}
