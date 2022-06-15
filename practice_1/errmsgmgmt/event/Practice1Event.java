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
package com.clt.apps.opus.esm.clv.practice1.errmsgmgmt.event;

import com.clt.framework.support.layer.event.EventSupport;
import com.clt.apps.opus.esm.clv.practice1.errmsgmgmt.vo.ErrMsgVO;

/**
 * PRACTICE_1 에 대한 PDTO(Data Transfer Object including Parameters)<br>
 * - PRACTICE_1HTMLAction에서 작성<br>
 * - ServiceCommand Layer로 전달하는 PDTO로 사용<br>
 *
 * @author Hai Tran
 * @see PRACTICE_1HTMLAction 참조
 * @since J2EE 1.6
 */

public class Practice1Event extends EventSupport {

	private static final long serialVersionUID = 1L;

	/** Table Value Object 조회 조건 및 단건 처리 */
	ErrMsgVO errMsgVO = null;

	/** Table Value Object Multi Data 처리 */
	ErrMsgVO[] errMsgVOs = null;

	public Practice1Event() {
	}

	public void setErrMsgVO(ErrMsgVO errMsgVO) {
		this.errMsgVO = errMsgVO;
	}

	public void setErrMsgVOS(ErrMsgVO[] errMsgVOs) {
		this.errMsgVOs = errMsgVOs;
	}

	public ErrMsgVO getErrMsgVO() {
		return errMsgVO;
	}

	public ErrMsgVO[] getErrMsgVOS() {
		return errMsgVOs;
	}

}