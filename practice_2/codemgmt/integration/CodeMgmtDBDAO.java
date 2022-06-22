/*=========================================================
 *Copyright(c) 2022 CyberLogitec
 *@FileName : codemgmtDBDAO.java
 *@FileTitle : Error Messsage Management
 *Open Issues :
 *Change history :
 *@LastModifyDate : 2022.06.07
 *@LastModifier : 
 *@LastVersion : 1.0
 * 2022.06.07 
 * 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.practice1.codemgmt.integration;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.clt.apps.opus.esm.clv.practice1.codemgmt.vo.CodeMgmtDetailVO;
import com.clt.apps.opus.esm.clv.practice1.codemgmt.vo.CodeMgmtVO;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.component.rowset.DBRowSet;
import com.clt.framework.core.layer.integration.DAOException;
import com.clt.framework.support.db.ISQLTemplate;
import com.clt.framework.support.db.RowSetUtil;
import com.clt.framework.support.db.SQLExecuter;
import com.clt.framework.support.layer.integration.DBDAOSupport;

/**
 * ALPS codemgmtDBDAO <br>
 * - ALPS-PRACTICE1 system Business Logic을 처리하기 위한 JDBC 작업수행.<br>
 * 
 * @author Hai Tran
 * @see codemgmtBCImpl 참조
 * @since J2EE 1.6
 */
public class CodeMgmtDBDAO extends DBDAOSupport {

	/**
	 * [처리대상] 정보를 [행위] 합니다.<br>
	 * 
	 * @param codeMgmtVO
	 *            codeMgmtVO
	 * @return List<codeMgmtVO>
	 * @exception DAOException
	 */
	@SuppressWarnings("unchecked")
	
	public List<CodeMgmtVO> searchCodeMgmt(CodeMgmtVO codeMgmtVO) throws DAOException {
		DBRowSet dbRowset = null; // save result from db
		List<CodeMgmtVO> list = null; // list VO
		// query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		// velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();

		try {
			if (codeMgmtVO != null) {
				
				Map<String, String> mapVO = codeMgmtVO.getColumnValues();
				// add all value to param
				param.putAll(mapVO);
				// add all value to velparam
				velParam.putAll(mapVO);
			}
			// result querry
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate) new CodeMgmtDBDAOCodeMgmtRSQL(), param, velParam);
			// convert dbRowset to List
			list = (List) RowSetUtil.rowSetToVOs(dbRowset,CodeMgmtVO.class);
		} catch (SQLException se) {
			log.error(se.getMessage(), se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return list;
	}

	public List<CodeMgmtDetailVO> searchCodeDtlMgmt(CodeMgmtDetailVO codeMgmtDetailVO) throws DAOException {
		DBRowSet dbRowset = null; // save result from db
		List<CodeMgmtDetailVO> list = null; // list VO
		// query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		// velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();

		try {
			if (codeMgmtDetailVO != null) {
				
				Map<String, String> mapVO = codeMgmtDetailVO.getColumnValues();
				// add all value to param
				param.putAll(mapVO);
				// add all value to velparam
				velParam.putAll(mapVO);
			}
			// result querry
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate) new CodeMgmtDBDAOCodeMgmtDetailRSQL(), param, velParam);
			// convert dbRowset to List
			list = (List) RowSetUtil.rowSetToVOs(dbRowset, CodeMgmtDetailVO.class);
		} catch (SQLException se) {
			log.error(se.getMessage(), se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return list;
	}
	
public int[] addmanageCodeMgmtS(List<CodeMgmtVO> codeMgmtVO) throws DAOException, Exception {
		
		int insCnt[] = null;
		try {
			//create new SQLExecuter variable to execute query
			SQLExecuter sqlExe = new SQLExecuter("");
			// if list not null, execute query to save add new changes in database
			if (codeMgmtVO.size() > 0) {
				insCnt = sqlExe.executeBatch((ISQLTemplate) new CodeMgmtDBDAOCodeMgmtCSQL(), codeMgmtVO, null);
				for (int i = 0; i < insCnt.length; i++) {
					if (insCnt[i] == Statement.EXECUTE_FAILED)
						throw new DAOException("Fail to insert No" + i + " SQL");
				}
			}
		} catch (SQLException se) {
			log.error(se.getMessage(), se);
			// throw Exception
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return insCnt;
	}

public int[] addmanageCodeMgmtDetailS(List<CodeMgmtDetailVO> codeMgmtDetailVO) throws DAOException, Exception {
	
	int insCnt[] = null;
	try {
		SQLExecuter sqlExe = new SQLExecuter("");
		if (codeMgmtDetailVO.size() > 0) {
			insCnt = sqlExe.executeBatch((ISQLTemplate) new CodeMgmtDBDAOCodeMgmtDetailCSQL(), codeMgmtDetailVO, null);
			for (int i = 0; i < insCnt.length; i++) {
				if (insCnt[i] == Statement.EXECUTE_FAILED)
					throw new DAOException("Fail to insert No" + i + " SQL");
			}
		}
	} catch (SQLException se) {
		log.error(se.getMessage(), se);
		// throw Exception
		throw new DAOException(new ErrorHandler(se).getMessage());
	} catch (Exception ex) {
		log.error(ex.getMessage(), ex);
		throw new DAOException(new ErrorHandler(ex).getMessage());
	}
	return insCnt;
}

	public int[] modifymanageCodeMgmtS(List<CodeMgmtVO> codeMgmtVO) throws DAOException, Exception {
		int updCnt[] = null;
		try {
			SQLExecuter sqlExe = new SQLExecuter("");
			if (codeMgmtVO.size() > 0) {
				updCnt = sqlExe.executeBatch((ISQLTemplate) new CodeMgmtDBDAOCodeMgmtUSQL(), codeMgmtVO, null);
				for (int i = 0; i < updCnt.length; i++) {
					if (updCnt[i] == Statement.EXECUTE_FAILED)
						throw new DAOException("Fail to insert No" + i + " SQL");
				}
			}
		} catch (SQLException se) {
			log.error(se.getMessage(), se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return updCnt;
	}
	public int[] modifymanageCodeMgmtDetailS(List<CodeMgmtDetailVO> codeMgmtDetailVO) throws DAOException, Exception {
		int updCnt[] = null;
		try {
			SQLExecuter sqlExe = new SQLExecuter("");
			if (codeMgmtDetailVO.size() > 0) {
				updCnt = sqlExe.executeBatch((ISQLTemplate) new CodeMgmtDBDAOCodeMgmtDetailUSQL(), codeMgmtDetailVO, null);
				for (int i = 0; i < updCnt.length; i++) {
					if (updCnt[i] == Statement.EXECUTE_FAILED)
						throw new DAOException("Fail to insert No" + i + " SQL");
				}
			}
		} catch (SQLException se) {
			log.error(se.getMessage(), se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return updCnt;
	}
	public int[] removemanageCodeMgmtS(List<CodeMgmtVO> codeMgmtVO) throws DAOException, Exception {
		int delCnt[] = null;
		try {
			SQLExecuter sqlExe = new SQLExecuter("");
			if (codeMgmtVO.size() > 0) {
				delCnt = sqlExe.executeBatch((ISQLTemplate) new CodeMgmtDBDAOCodeMgmtDSQL(), codeMgmtVO, null);
				for (int i = 0; i < delCnt.length; i++) {
					if (delCnt[i] == Statement.EXECUTE_FAILED)
						throw new DAOException("Fail to insert No" + i + " SQL");
				}
			}
		} catch (SQLException se) {
			log.error(se.getMessage(), se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return delCnt;
	}
	public int[] removemanageCodeMgmtDetailS(List<CodeMgmtDetailVO> codeMgmtDetailVO) throws DAOException, Exception {
		int delCnt[] = null;
		try {
			SQLExecuter sqlExe = new SQLExecuter("");
			if (codeMgmtDetailVO.size() > 0) {
				delCnt = sqlExe.executeBatch((ISQLTemplate) new CodeMgmtDBDAOCodeMgmtDetailDSQL(), codeMgmtDetailVO, null);
				for (int i = 0; i < delCnt.length; i++) {
					if (delCnt[i] == Statement.EXECUTE_FAILED)
						throw new DAOException("Fail to insert No" + i + " SQL");
				}
			}
		} catch (SQLException se) {
			log.error(se.getMessage(), se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return delCnt;
	}
	
	public int[] removemanageCodeMgmtDetailSByCD_Val(List<CodeMgmtDetailVO> codeMgmtDetailVO) throws DAOException, Exception {
		int delCnt[] = null;
		try {
			SQLExecuter sqlExe = new SQLExecuter("");
			if (codeMgmtDetailVO.size() > 0) {
				delCnt = sqlExe.executeBatch((ISQLTemplate) new CodeMgmtDBDAODeleteDetailCodeMgmtDSQL(), codeMgmtDetailVO, null);
				for (int i = 0; i < delCnt.length; i++) {
					if (delCnt[i] == Statement.EXECUTE_FAILED)
						throw new DAOException("Fail to insert No" + i + " SQL");
				}
			}
		} catch (SQLException se) {
			log.error(se.getMessage(), se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return delCnt;
	}
	
	public List<CodeMgmtVO> checkDuplicateCodeMgmt(CodeMgmtVO codeMgmtVO) throws DAOException {
		DBRowSet dbRowset = null;
		List<CodeMgmtVO> list = null;
		// query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		// velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();

		try {
			if (codeMgmtVO != null) {
				Map<String, String> mapVO = codeMgmtVO.getColumnValues();
				param.putAll(mapVO);
				velParam.putAll(mapVO);
			}
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate) new CodeMgmtDBDAOCodeMgmtDuplicateRSQL(), param, velParam);
			list = (List) RowSetUtil.rowSetToVOs(dbRowset, CodeMgmtVO.class);
		} catch (SQLException se) {
			log.error(se.getMessage(), se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return list;
	}

}
