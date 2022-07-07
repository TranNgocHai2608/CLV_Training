/*=========================================================
 *Copyright(c) 2022 CyberLogitec
 *@FileName : Practice1Event.java
 *@FileTitle : Error Messsage Management
 *Open Issues :
 *Change history :
 *@LastModifyDate : 2022.06.07
 *@LastModifier : 
 *@LastVersion : 1.0
 * 2022.06.07 
 * 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.practice1.moneymgmt.event;

import com.clt.apps.opus.esm.clv.practice1.moneymgmt.vo.SearchDetailsVO;
import com.clt.apps.opus.esm.clv.practice1.moneymgmt.vo.SummaryVO;
import com.clt.framework.support.layer.event.EventSupport;

public class Esm_Dou_0108Event extends EventSupport {

	private static final long serialVersionUID = 1L;
	SummaryVO summaryVO = null;
	SummaryVO[] summaryVOs= null;
	
	SearchDetailsVO searchDetailsVO = null;
	SearchDetailsVO[] searchDetailsVOs = null;

	public SearchDetailsVO getSearchDetailsVO() {
		return searchDetailsVO;
	}

	public void setSearchDetailsVO(SearchDetailsVO searchDetailsVO) {
		this.searchDetailsVO = searchDetailsVO;
	}

	public SearchDetailsVO[] getSearchDetailsVOs() {
		return searchDetailsVOs;
	}

	public void setSearchDetailsVOs(SearchDetailsVO[] searchDetailsVOs) {
		this.searchDetailsVOs = searchDetailsVOs;
	}

	public SummaryVO getSummaryVO() {
		return summaryVO;
	}

	public void setSummaryVO(SummaryVO summaryVO) {
		this.summaryVO = summaryVO;
	}

	public SummaryVO[] getSummaryVOs() {
		return summaryVOs;
	}

	public void setSummaryVOs(SummaryVO[] summaryVOs) {
		this.summaryVOs = summaryVOs;
	}

	public Esm_Dou_0108Event() {
	}

}