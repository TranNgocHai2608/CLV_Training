package com.clt.apps.opus.esm.clv.practice1.CarrierMgmt.integration;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.clt.apps.opus.esm.clv.practice1.CarrierMgmt.vo.CarrierMgmtVO;
import com.clt.apps.opus.esm.clv.practice1.codemgmt.integration.CodeMgmtDBDAOCodeMgmtDuplicateRSQL;
import com.clt.apps.opus.esm.clv.practice1.codemgmt.vo.CodeMgmtVO;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.component.rowset.DBRowSet;
import com.clt.framework.core.layer.integration.DAOException;
import com.clt.framework.support.db.ISQLTemplate;
import com.clt.framework.support.db.RowSetUtil;
import com.clt.framework.support.db.SQLExecuter;
import com.clt.framework.support.layer.integration.DBDAOSupport;

public class CarrierMgmtDBDAO  extends DBDAOSupport  {

	public List<CarrierMgmtVO> searchCarrierMgmt(CarrierMgmtVO carrierMgmtVO) throws DAOException {
		DBRowSet dbRowset = null; // save result from db
		List<CarrierMgmtVO> list = null; // list VO
		// query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		// velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();

		try {
			if (carrierMgmtVO != null) {
				
				Map<String, String> mapVO = carrierMgmtVO.getColumnValues();
				List<String> obj_list_no = new ArrayList<>();
				
				if(carrierMgmtVO.getJoCrrCd() != null){
								
					String[] crr_cd = carrierMgmtVO.getJoCrrCd().split(",");
					if(crr_cd.length > 0){
						for(int i=0;i<crr_cd.length;i++){
							obj_list_no.add(crr_cd[i]);
						}
					}
				}
				
				param.putAll(mapVO);
				param.put("obj_list_no",obj_list_no);
				velParam.putAll(mapVO);
				velParam.put("obj_list_no",obj_list_no);
			}
			// result querry
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate) new CarrierMgmtDBDAOCarrierMgmtRSQL(), param, velParam);
			// convert dbRowset to List
			list = (List) RowSetUtil.rowSetToVOs(dbRowset,CarrierMgmtVO.class);
		} catch (SQLException se) {
			log.error(se.getMessage(), se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return list;
	}

	public List<CarrierMgmtVO> searchCarrierCombo(CarrierMgmtVO carrierMgmtVO) throws DAOException {
		DBRowSet dbRowset = null; // save result from db
		List<CarrierMgmtVO> list = null; // list VO
		// query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		// velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();

		try {
			if(carrierMgmtVO != null){
				Map<String, String> mapVO = carrierMgmtVO.getColumnValues();
			
				param.putAll(mapVO);
				velParam.putAll(mapVO);
			}
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate) new CarrierDBDAOCarrierCodeRSQL(), param, velParam);
			list = (List) RowSetUtil.rowSetToVOs(dbRowset,CarrierMgmtVO.class);
		} catch (SQLException se) {
			log.error(se.getMessage(), se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return list;
	}
	
	public List<CarrierMgmtVO> searchCustomer(CarrierMgmtVO carrierMgmtVO) throws DAOException{
		DBRowSet dbRowset = null; // save result from db
		List<CarrierMgmtVO> list = null; // list VO
		// query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		// velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();

		try {
			if(carrierMgmtVO != null){
				Map<String, String> mapVO = carrierMgmtVO.getColumnValues();
			
				param.putAll(mapVO);
				velParam.putAll(mapVO);
			}
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate) new CarrierMgmtDBDAOSearchCustomerRSQL(), param, velParam);
			list = (List) RowSetUtil.rowSetToVOs(dbRowset,CarrierMgmtVO.class);
		} catch (SQLException se) {
			log.error(se.getMessage(), se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return list;
	}
	
	public List<CarrierMgmtVO> searchLaneCombo(CarrierMgmtVO carrierMgmtVO) throws DAOException {
		DBRowSet dbRowset = null; // save result from db
		List<CarrierMgmtVO> list = null; // list VO
		// query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		// velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();

		try {
			if(carrierMgmtVO != null){
				Map<String, String> mapVO = carrierMgmtVO .getColumnValues();
			
				param.putAll(mapVO);
				velParam.putAll(mapVO);
			}
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate) new CarrierMgmtDBDAOCarrierLaneRSQL(), param, velParam);
			list = (List) RowSetUtil.rowSetToVOs(dbRowset,CarrierMgmtVO.class);
		} catch (SQLException se) {
			log.error(se.getMessage(), se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return list;
	}
	
	
	public int[] addmanageCarrierS(List<CarrierMgmtVO> carrierMgmtVO) throws DAOException {
		int addCnt[] = null;
		try {
			SQLExecuter sqlExe = new SQLExecuter("");
			if (carrierMgmtVO.size() > 0) {
				addCnt = sqlExe.executeBatch((ISQLTemplate) new CarrierMgmtDBDAOCarrierMgmtCSQL(), carrierMgmtVO, null);
				for (int i = 0; i < addCnt.length; i++) {
					if (addCnt[i] == Statement.EXECUTE_FAILED)
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
		return addCnt;		
	}
		

	public int[] modifymanageCarrierS(List<CarrierMgmtVO> carrierMgmtVO) throws DAOException {
		int updCnt[] = null;
		try {
			SQLExecuter sqlExe = new SQLExecuter("");
			if (carrierMgmtVO.size() > 0) {
				updCnt = sqlExe.executeBatch((ISQLTemplate) new CarrierMgmtDBDAOCarrierMgmtUSQL(), carrierMgmtVO, null);
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

		
	public int[] removemanageCarrierS(List<CarrierMgmtVO> carrierMgmtVO) throws DAOException {
		int delCnt[] = null;
		try {
			SQLExecuter sqlExe = new SQLExecuter("");
			if (carrierMgmtVO.size() > 0) {
				delCnt = sqlExe.executeBatch((ISQLTemplate) new CarrierMgmtDBDAOCarrierMgmtDSQL(), carrierMgmtVO, null);
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

	public  List<CarrierMgmtVO> checkDuplicate(CarrierMgmtVO carrierMgmtVO) throws DAOException {
		DBRowSet dbRowset = null;
		List<CarrierMgmtVO> list = null;
		// query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		// velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();

		try {
			if (carrierMgmtVO != null) {
				Map<String, String> mapVO = carrierMgmtVO.getColumnValues();
				param.putAll(mapVO);
				velParam.putAll(mapVO);
			}
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate) new CarrierMgmtDBDAOCheckDuplicateRSQL(), param, velParam);
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
