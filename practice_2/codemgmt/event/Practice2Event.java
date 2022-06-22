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
package com.clt.apps.opus.esm.clv.practice1.codemgmt.event;

import com.clt.framework.support.layer.event.EventSupport;
import com.clt.apps.opus.esm.clv.practice1.codemgmt.vo.CodeMgmtDetailVO;
import com.clt.apps.opus.esm.clv.practice1.codemgmt.vo.CodeMgmtVO;

public class Practice2Event extends EventSupport {

	private static final long serialVersionUID = 1L;
	CodeMgmtVO codeMgmtVO = null;
	CodeMgmtVO[] codeMgmtVOs = null;
	
	CodeMgmtDetailVO codeMgmtDetailVO = null;
	CodeMgmtDetailVO[] codeMgmtDetailVOs = null;

	public CodeMgmtDetailVO getCodeMgmtDetailVO() {
		return codeMgmtDetailVO;
	}

	public void setCodeMgmtDetailVO(CodeMgmtDetailVO codeMgmtDetailVO) {
		this.codeMgmtDetailVO = codeMgmtDetailVO;
	}

	public CodeMgmtDetailVO[] getCodeMgmtDetailVOs() {
		return codeMgmtDetailVOs;
	}

	public void setCodeMgmtDetailVOs(CodeMgmtDetailVO[] codeMgmtDetailVOs) {
		this.codeMgmtDetailVOs = codeMgmtDetailVOs;
	}

	public CodeMgmtVO getCodeMgmtVO() {
		return codeMgmtVO;
	}

	public void setCodeMgmtVO(CodeMgmtVO codeMgmtVO) {
		this.codeMgmtVO = codeMgmtVO;
	}

	public CodeMgmtVO[] getCodeMgmtVOs() {
		return codeMgmtVOs;
	}

	public void setCodeMgmtVOs(CodeMgmtVO[] codeMgmtVOs) {
		this.codeMgmtVOs = codeMgmtVOs;
	}

	public Practice2Event() {
	}

}