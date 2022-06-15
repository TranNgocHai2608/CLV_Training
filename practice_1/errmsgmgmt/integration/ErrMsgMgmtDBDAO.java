/*=========================================================
 *Copyright(c) 2022 CyberLogitec
 *@FileName : ErrMsgMgmtDBDAO.java
 *@FileTitle : Error Messsage Management
 *Open Issues :
 *Change history :
 *@LastModifyDate : 2022.06.07
 *@LastModifier : 
 *@LastVersion : 1.0
 * 2022.06.07 
 * 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.practice1.errmsgmgmt.integration;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.clt.apps.opus.esm.clv.practice1.errmsgmgmt.basic.ErrMsgMgmtBCImpl;
import com.clt.apps.opus.esm.clv.practice1.errmsgmgmt.vo.ErrMsgVO;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.component.rowset.DBRowSet;
import com.clt.framework.core.layer.integration.DAOException;
import com.clt.framework.support.db.ISQLTemplate;
import com.clt.framework.support.db.RowSetUtil;
import com.clt.framework.support.db.SQLExecuter;
import com.clt.framework.support.layer.integration.DBDAOSupport;

/**
 * ALPS ErrMsgMgmtDBDAO <br>
 * - ALPS-PRACTICE1 system Business Logic을 처리하기 위한 JDBC 작업수행.<br>
 * 
 * @author Hai Tran
 * @see ErrMsgMgmtBCImpl 참조
 * @since J2EE 1.6
 */
public class ErrMsgMgmtDBDAO extends DBDAOSupport {

	/**
	 * [처리대상] 정보를 [행위] 합니다.<br>
	 * 
	 * @param ErrMsgVO
	 *            errMsgVO
	 * @return List<ErrMsgVO>
	 * @exception DAOException
	 */
	@SuppressWarnings("unchecked")
	
	public List<ErrMsgVO> searchErrMsg(ErrMsgVO errMsgVO) throws DAOException {
		DBRowSet dbRowset = null; // save result from db
							
		List<ErrMsgVO> list = null; // list VO
		// query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		// velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();

		try {
			if (errMsgVO != null) {
				
				Map<String, String> mapVO = errMsgVO.getColumnValues();
				// add all value to param
				param.putAll(mapVO);
				// add all value to velparam
				velParam.putAll(mapVO);
			}
			// result querry
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate) new ErrMsgMgmtDBDAOErrMsgVORSQL(), param, velParam);
			// convert dbRowset to List
			list = (List) RowSetUtil.rowSetToVOs(dbRowset, ErrMsgVO.class);
		} catch (SQLException se) {
			log.error(se.getMessage(), se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return list;
	}
	/**
	 * [처리대상] 정보를 [행위] 합니다.<br>
	 * 
	 * @param List
	 *            <ErrMsgVO> errMsgVO
	 * @return int[]
	 * @exception DAOException
	 * @exception Exception
	 */
	public int[] addmanageErrMsgS(List<ErrMsgVO> errMsgVO) throws DAOException, Exception {
		
		int insCnt[] = null;
		try {
			//create new SQLExecuter variable to execute query
			SQLExecuter sqlExe = new SQLExecuter("");
			// if list not null, execute query to save add new changes in database
			if (errMsgVO.size() > 0) {
				insCnt = sqlExe.executeBatch((ISQLTemplate) new ErrMsgMgmtDBDAOErrMsgVOCSQL(), errMsgVO, null);
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

	/**
	 * [처리대상] 정보를 [행위] 합니다.<br>
	 * 
	 * @param List
	 *            <ErrMsgVO> errMsgVO
	 * @return int[]
	 * @exception DAOException
	 * @exception Exception
	 */
	public int[] modifymanageErrMsgS(List<ErrMsgVO> errMsgVO) throws DAOException, Exception {
		int updCnt[] = null;
		try {
			SQLExecuter sqlExe = new SQLExecuter("");
			if (errMsgVO.size() > 0) {
				updCnt = sqlExe.executeBatch((ISQLTemplate) new ErrMsgMgmtDBDAOErrMsgVOUSQL(), errMsgVO, null);
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

	/**
	 * [처리대상] 정보를 [행위] 합니다.<br>
	 * 
	 * @param List
	 *            <ErrMsgVO> errMsgVO
	 * @return int[]
	 * @exception DAOException
	 * @exception Exception
	 */
	public int[] removemanageErrMsgS(List<ErrMsgVO> errMsgVO) throws DAOException, Exception {
		int delCnt[] = null;
		try {
			SQLExecuter sqlExe = new SQLExecuter("");
			if (errMsgVO.size() > 0) {
				delCnt = sqlExe.executeBatch((ISQLTemplate) new ErrMsgMgmtDBDAOErrMsgVODSQL(), errMsgVO, null);
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

	public List<ErrMsgVO> checkDuplicateErrMsg(ErrMsgVO errMsgVO) throws DAOException {
		DBRowSet dbRowset = null;
		List<ErrMsgVO> list = null;
		// query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		// velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();

		try {
			if (errMsgVO != null) {
				Map<String, String> mapVO = errMsgVO.getColumnValues();
				param.putAll(mapVO);
				velParam.putAll(mapVO);
			}
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate) new ErrMsgMgmtDBDAOcheckDuplicateRSQL(), param, velParam);
			list = (List) RowSetUtil.rowSetToVOs(dbRowset, ErrMsgVO.class);
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