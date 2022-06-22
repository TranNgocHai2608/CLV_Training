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
package com.clt.apps.opus.esm.clv.practice1.codemgmt.basic;

import java.util.List;

import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.support.view.signon.SignOnUserAccount;
import com.clt.apps.opus.esm.clv.practice1.codemgmt.vo.CodeMgmtDetailVO;
import com.clt.apps.opus.esm.clv.practice1.codemgmt.vo.CodeMgmtVO;

/**
 * ALPS-Practice1 Business Logic Command Interface<br>
 * - ALPS-Practice1에 대한 비지니스 로직에 대한 인터페이스<br>
 *
 * @author Hai Tran
 * @since J2EE 1.6
 */

public interface CodeMgmtBC {

	/**
	 * [비즈니스대상]을 [행위] 합니다.<br>
	 * 
	 * @param CodeMgmtVO	CodeMgmtVO
	 * @return List<ErrMsgVO>
	 * @exception EventException
	 */
	public List<CodeMgmtVO> searchCodeMgmt(CodeMgmtVO CodeMgmtVO) throws EventException;
	public List<CodeMgmtDetailVO> searchCodeMgmtDetail(CodeMgmtDetailVO CodeMgmtDetailVO) throws EventException;
	/**
	 * [비즈니스대상]을 [행위] 합니다.<br>
	 * 
	 * @param CodeMgmtVO[] CodeMgmtVO
	 * @param account SignOnUserAccount
	 * @exception EventException
	 */
	public void manageCodeMgmt(CodeMgmtVO[] CodeMgmtVO,SignOnUserAccount account) throws EventException;
	public void manageCodeMgmtDetail(CodeMgmtDetailVO[] CodeMgmtDetailVO,SignOnUserAccount account) throws EventException;
	
}