package com.clt.apps.opus.esm.clv.practice1.moneymgmt.integration;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.clt.apps.opus.esm.clv.practice1.moneymgmt.vo.SearchDetailsVO;
import com.clt.apps.opus.esm.clv.practice1.moneymgmt.vo.SummaryVO;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.component.rowset.DBRowSet;
import com.clt.framework.core.layer.integration.DAOException;
import com.clt.framework.support.db.ISQLTemplate;
import com.clt.framework.support.db.RowSetUtil;
import com.clt.framework.support.db.SQLExecuter;
import com.clt.framework.support.layer.integration.DBDAOSupport;

/**
 * ALPS MoneyMgmtDBDAO <br>
 * -JDBC operation to process ALPS-MoneyMgmt system Business Logic.<br>
 * 
 * @author Hai Tran
 * @see MoneyMgmtBCImpl 참조
 * @since J2EE 1.6
 */

public class MoneyMgmtDBDAO  extends DBDAOSupport  {

	/**
	 * Method search summary data
	 * @param summaryVO
	 * @return list<summaryVO>
	 * @throws DAOException
	 */
	public List<SummaryVO> searchSummaryMgmt(SummaryVO summaryVO) throws DAOException {
		DBRowSet dbRowset = null; // save result from db
		List<SummaryVO> list = null; // list VO
		// query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		// velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();
		try {
			if (summaryVO != null) {
				Map<String, String> mapVO = summaryVO.getColumnValues();
				List<String> obj_list_no = new ArrayList<>();
				if(!summaryVO.getJoCrrCd().isEmpty()){
								
					String[] crr_cd = summaryVO.getJoCrrCd().split(",");
					if(crr_cd.length > 0){
						for(int i=0; i<crr_cd.length; i++){
							obj_list_no.add(crr_cd[i]);
						}
					}
				}
				param.putAll(mapVO);
				param.put("obj_list_no", obj_list_no);
				velParam.putAll(mapVO);
				velParam.put("obj_list_no", obj_list_no);
			}
			// result querry
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate) new MoneyMgmtDBDAOSummaryVORSQL(), param, velParam);
			// convert dbRowset to List
			list = (List) RowSetUtil.rowSetToVOs(dbRowset,SummaryVO.class);
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
	 * Method search detail
	 * @param searchDetailsVO
	 * @return list<searchDetailsVO>
	 * @throws DAOException
	 */
	public List<SearchDetailsVO> searchDetails(SearchDetailsVO searchDetailsVO) throws DAOException {
		DBRowSet dbRowset = null; // save result from db
		List<SearchDetailsVO> list = null; // list VO
		// query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		// velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();
		try {
			if (searchDetailsVO != null) {
				Map<String, String> mapVO = searchDetailsVO.getColumnValues();
				List<String> obj_list_no = new ArrayList<>();
				if(!searchDetailsVO.getJoCrrCd().isEmpty()){
					String[] crr_cd = searchDetailsVO.getJoCrrCd().split(",");
					if(crr_cd.length > 0){
						for(int i=0; i<crr_cd.length; i++){
							obj_list_no.add(crr_cd[i]);
						}
					}
				}
				param.putAll(mapVO);
				param.put("obj_list_no", obj_list_no);
				velParam.putAll(mapVO);
				velParam.put("obj_list_no", obj_list_no);
			}
			// result querry
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate) new MoneyMgmtDBDAOSearchDetailsVORSQL(), param, velParam);
			// convert dbRowset to List
			list = (List) RowSetUtil.rowSetToVOs(dbRowset,SearchDetailsVO.class);
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
	 * method search partner for combobox
	 * @param summaryVO
	 * @return list<summaryVO>
	 * @throws DAOException
	 */
	public List<SummaryVO> searchPartnerCombo(SummaryVO summaryVO) throws DAOException {
		DBRowSet dbRowset = null; // save result from db
		List<SummaryVO> list = null; // list VO
		// query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		// velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();

		try {
			if(summaryVO != null){
				Map<String, String> mapVO = summaryVO.getColumnValues();
			
				param.putAll(mapVO);
				velParam.putAll(mapVO);
			}
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate) new MoneyMgmtDBDAOSearchPartnerRSQL(), param, velParam);
			list = (List) RowSetUtil.rowSetToVOs(dbRowset,SummaryVO.class);
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
	 * 
	 * method search Lane for combobox
	 * @param laneVO
	 * @return list<laneVO>
	 * @throws DAOException
	 */
	public List<SummaryVO> searchLaneCombo(SummaryVO laneVO) throws DAOException {
		DBRowSet dbRowset = null; // save result from db
		List<SummaryVO> list = null; // list VO
		// query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		// velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();
		try {
			if (laneVO != null) {
				Map<String, String> mapVO = laneVO.getColumnValues();
				List<String> obj_list_no = new ArrayList<>();
				if(laneVO.getJoCrrCd() != null){
					String[] crr_cd = laneVO.getJoCrrCd().split(",");
					if(crr_cd.length > 0){
						for(int i = 0; i < crr_cd.length; i++){
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
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate) new MoneyMgmtDBDAOSearchLaneRSQL(), param, velParam);
			// convert dbRowset to List
			list = (List) RowSetUtil.rowSetToVOs(dbRowset,SummaryVO.class);
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
	 * method search trade for combobox
	 * @param tradeVO
	 * @return list <tradeVO>
	 * @throws DAOException
	 */
	public List<SummaryVO> searchTradeCombo(SummaryVO tradeVO) throws DAOException {
		DBRowSet dbRowset = null; // save result from db
		List<SummaryVO> list = null; // list VO
		// query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		// velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();
		try {
			if (tradeVO != null) {
				Map<String, String> mapVO = tradeVO.getColumnValues();
				List<String> obj_list_no = new ArrayList<>();
				if(tradeVO.getJoCrrCd() != null){
								
					String[] crr_cd = tradeVO.getJoCrrCd().split(",");
					if(crr_cd.length > 0){
						for(int i=0; i<crr_cd.length; i++){
							obj_list_no.add(crr_cd[i]);
						}
					}
				}
				param.putAll(mapVO);
				param.put("obj_list_no", obj_list_no);
				velParam.putAll(mapVO);
				velParam.put("obj_list_no", obj_list_no);
			}
			// result querry
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate) new MoneyMgmtDBDAOSearchTradeRSQL(), param, velParam);
			// convert dbRowset to List
			list = (List) RowSetUtil.rowSetToVOs(dbRowset,SummaryVO.class);
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
