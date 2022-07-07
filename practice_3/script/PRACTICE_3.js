/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : PRACTICE_1.js
*@FileTitle : Error Messsage Management
*Open Issues :
*Change history :
*@LastModifyDate : 2022.06.07
*@LastModifier : 
*@LastVersion : 1.0
* 2022.06.07 
* 1.0 Creation
=========================================================*/
/****************************************************************************************
  이벤트 구분 코드: [초기화]INIT=0; [입력]ADD=1; [조회]SEARCH=2; [리스트조회]SEARCHLIST=3;
					[수정]MODIFY=4; [삭제]REMOVE=5; [리스트삭제]REMOVELIST=6 [다중처리]MULTI=7
					기타 여분의 문자상수  COMMAND01=11; ~ COMMAND20=30;
 ***************************************************************************************/

/*------------------다음 코드는 JSDoc을 잘 만들기 위해서 추가된 코드임 ------------------*/
   /**
     * @fileoverview 업무에서 공통으로 사용하는 자바스크립트파일로 달력 관련 함수가 정의되어 있다.
     * @author 한진해운
     */

    /**
	 * @extends
	 * @class PRACTICE_1 : PRACTICE_1 생성을 위한 화면에서 사용하는 업무 스크립트를 정의한다.
	 */
    
	// array sheetObjects
	var sheetObjects = new Array();
	var comboObjects = new Array();
	var tabObjects = new Array();
	// count sheet 
	var sheetCnt = 0;
	var comboCnt = 0;
	var tabCnt = 0;
	var beforetab = 1;
	var searchDetail="";
	var searchSummary="";
	var firstLoad=true;
	var check3Month = false;
	var searchOptionForDbl = "";
	// set function processButtonClick for event onclick on browser
	document.onclick = processButtonClick;
	
	/**
	 *  This function calls a common function that sets the default settings of the sheet,
	 *  It is the first called area when file *jsp onload event.
	 */
	function loadPage(){
		initCalender();
		for(var i = 0; i < sheetObjects.length; i++) {
			ComConfigSheet(sheetObjects[i]);
			initSheet(sheetObjects[i], i + 1);
			ComEndConfigSheet(sheetObjects[i]);
		}
		
		for ( var k = 0; k < comboObjects.length; k++) {
			initCombo(comboObjects[k], k + 1);
		}
		
		for(k = 0;k < tabObjects.length; k++){
			initTab(tabObjects[k], k + 1);
			tabObjects[k].SetSelectedIndex(0);
		}
		s_jo_crr_cd.SetSelectIndex(0);
		doActionIBSheet(sheetObjects[0],document.form,IBSEARCH);
    }
	
	/**
	 * This function that define the basic properties for combo box.
	 * @param comboObj: combo object
	 * @param comboNo: index of combo object
	 */
	function initCombo(comboObj, comboNo) {
		var formObj = document.form;
		switch (comboNo) {
		case 1:
			with (comboObj) {
				SetMultiSelect(1);
		        SetDropHeight(230);
		        ValidChar(2,1);
			}
			
			var comboItems = partnerCombo.split("|");
			addComboItem(comboObj, comboItems);
			break;
		}
	}
	
	/**
	 * Function that add item to combobox 
	 * @param comboObj
	 * @param comboItems
	 */
	 function addComboItem(comboObj, comboItems) {
	        for (var i = 0; i < comboItems.length; i++) {
	        	var comboItem = comboItems[i].split(",");
	        	if(comboItem.length == 1){
	                comboObj.InsertItem(i, comboItem[0], comboItem[0]);
	        } else {
	        	comboObj.InsertItem(i, combxoItem[0] + "|" + comboItem[1], comboItem[1]);
	        }
	    }
	 }
	 
	 /**
	  *  Event start when user checks Partner combobox 
	  * @param Index
	  * @param Code
	  * @param Checked
	  */
	 function s_jo_crr_cd_OnCheckClick(Index, Code, Checked) {
			var count = s_jo_crr_cd.GetItemCount();
			var checkSelectCount = 0;
			if (Code == 0){
				var bChk = s_jo_crr_cd.GetItemCheck(Code);
		        if(bChk){
		            for(var i = 1 ; i < count ; i++) {
		            	s_jo_crr_cd.SetItemCheck(i,false,0);
		            }
		            disableLaneAndTrade();
		        }
			} else {
		        var bChk = s_jo_crr_cd.GetItemCheck(Code);
		        if (bChk) {
		        	s_jo_crr_cd.SetItemCheck(0,false,0);
		        } 
		    }
			for (var i = 0; i < count; i++){
				if (s_jo_crr_cd.GetItemCheck(i)){
					checkSelectCount += 1;
				}	
			}
			 if(checkSelectCount == 0) {
				s_jo_crr_cd.SetItemCheck(0,true,0);
				disableLaneAndTrade();
			 } 		
		}

	 function disableLaneAndTrade() {
		s_rlane_cd.RemoveAll();
      	s_trade_cd.RemoveAll();
     	s_rlane_cd.SetEnable(false);
     	s_trade_cd.SetEnable(false);
	 }
	 
	 
	 /**
	  * Event start when user out focus Partner combobox 
 
	  */
	 function s_jo_crr_cd_OnBlur() {
		 if(document.form.s_jo_crr_cd.value != "All"){
			 getLaneComboData();
			 s_rlane_cd.SetEnable(true);
		 }
	 }
	 
	 /**
	  * function generate data combo
	  * @param comboObj
	  * @param dataStr
	  */
	 function generateDataCombo(comboObj, dataStr) {
		 if (typeof dataStr !== 'undefined'){
				if (dataStr.indexOf("|") != -1) {
					var data = dataStr.split("|");
					for (var i = 0; i < data.length; i++) {
						comboObj.InsertItem(-1, data[i], data[i]);
					}
				}
				if (dataStr.length > 0 && dataStr.indexOf("|") == -1) {
					comboObj.InsertItem(-1, dataStr, dataStr);
				}
			}
		}
	 
	 /**
	  *  Get LaneCombo data from server 
	  */
	 function getLaneComboData() {
	 	s_rlane_cd.RemoveAll();
	 	s_trade_cd.RemoveAll();
	 	document.form.f_cmd.value = SEARCH01;
	 	var xml = sheetObjects[0].GetSearchData("PRACTICE_3GS.do",FormQueryString(document.form));
	 	lanes = ComGetEtcData(xml, "lanes");
	 	generateDataCombo(comboObjects[1], lanes);
	 	if (s_rlane_cd.GetItemCount() > 0) {
	 		s_rlane_cd.SetSelectIndex(0, 1);
	 		s_rlane_cd.SetEnable(true);
	 	} else
	 		s_rlane_cd.SetEnable(false);
	 }

	 /**
	  * Get TradeCombo data from server 
	  */
	 function getTradeComboData() {
	 	s_trade_cd.RemoveAll();
	 	document.form.f_cmd.value = SEARCH02;
	 	var xml = sheetObjects[0].GetSearchData("PRACTICE_3GS.do", FormQueryString(document.form));
	 	trades = ComGetEtcData(xml, "trades");
	 	generateDataCombo(comboObjects[2], trades);
	 	if (s_trade_cd.GetItemCount() > 0) {
	 		s_trade_cd.SetSelectIndex(0, 1);
	 		s_trade_cd.SetEnable(true);
	 	} else{
	 		s_trade_cd.SetEnable(false);
	 	}
	 }
	 
	 /**
	  * event start when user change lane combo
	  */
	function s_rlane_cd_OnChange() {
		s_trade_cd.SetEnable(true);
		getTradeComboData();
	}

	/**
	 * Initializing tab object
	 * @param tabObj
	 * @param tabNo
	 */
	function initTab(tabObj , tabNo) {
		switch(tabNo) {
		case 1:
			with (tabObj) {
				var cnt=0 ;
					InsertItem( "Summary" , "");
					InsertItem( "Detail" , "");
			}
			break;
		}
	}
	
	/**
	 * Initializing calender
	 */
	function initCalender(){
		var formObj = document.form;
		var ymTo = ComGetNowInfo("ym","-");
		formObj.acct_yrmon_to.value = ymTo;
		
		var ymFrom = ComGetDateAdd(formObj.acct_yrmon_to.value + "-01","M",-1);
		formObj.acct_yrmon_from.value = GetDateFormat(ymFrom,"ym");	
	}
	
	/**
	 * Get format date
	 * @param obj
	 * @param sFormat
	 * @returns {String}
	 */
	function GetDateFormat(obj, sFormat) {
		var objDate = String(getArgValue(obj));
		objDate = objDate.replace(/\/|\-|\.|\:|\ /g, "");
		if (ComIsEmpty(objDate))
			return "";

		var dateFormat = "";
		switch (sFormat) {
		case "ym":
			dateFormat = objDate.substring(0, 6);
			break;
		}
		dateFormat = ComGetMaskedValue(dateFormat, sFormat);
		return dateFormat;
	}
	
	/**
	 * Funtion that adds month to date object
	 * @param obj
	 * @param month
	 */
	function addMonth(obj, month) {
		if (obj.value != "") {
			obj.value = ComGetDateAdd(obj.value + "-01", "M", month).substr(0, 7);
		}
	}
	
	/**
	 * event start when user change date
	 */
	function yearMonth_Onchange() {
		sheetObjects[0].RemoveAll();
		sheetObjects[1].RemoveAll();
	}
	
	/**
	 * handle the click event from JSP button 
	 */
    function processButtonClick(){
    	var sheetObject1 = sheetObjects[0];
    	var sheetObject2 = sheetObjects[1];
        var formObject = document.form;
    	try {
    		// get name object
    		var srcName = ComGetEvent("name");
            switch(srcName) {
            	case "btn_Retrieve":
            		if(!check3Month && checkOverThreeMonth(formObject)){
            			if (confirm("Year Month over 3 months, do you really want to get data ?")){
            				check3Month = true;
	    	    		} else {
	    	    			return;
	    	    		}
            		}
            		doActionIBSheet(getCurrentSheet(), formObject, IBSEARCH);
//            		doActionIBSheet(sheetObject2, formObject, IBSEARCH);
        			break;
            	case "btn_New":
            		resetForm(formObject);
            		break;
            	case "btn_DownExcel":
            		doActionIBSheet(sheetObject1, formObject, IBDOWNEXCEL);
            		break;
            	case "btn_datefrom_down":
            		addMonth(formObject.acct_yrmon_from, -1);
            		yearMonth_Onchange();
            		break;
    			case "btn_datefrom_up":
    				if(!checkDate(formObject)) {
            			ComShowMessage("Start date must be earlier than end date");
            		} else {
            			addMonth(formObject.acct_yrmon_from, 1);
                		yearMonth_Onchange();
            		}
    				break;
    			case "btn_dateto_down":
    				addMonth(formObject.acct_yrmon_to, -1);
    				yearMonth_Onchange();
    				break;
    				
    			case "btn_dateto_up":
    				addMonth(formObject.acct_yrmon_to, 1);
    				yearMonth_Onchange();
    				break;
    			default:
    				break;
    		}
    	}catch(e) {
    		if( e == "[object Error]") {
    			ComShowMessage(OBJECT_ERROR);
    		} else {
    			console.log(e);
    		}
    	}
    }
    
    /**
     * set sheet object
     * @param sheet_obj
     */
    function setSheetObject(sheet_obj){
    	sheetObjects[sheetCnt++] = sheet_obj;
    }
    
    /**
     * set combo object
     * @param combo_obj
     */
	function setComboObject(combo_obj) {
		comboObjects[comboCnt++] = combo_obj;
	}
	
	/**
	 * set tab object
	 * @param tab_obj
	 */
	function setTabObject(tab_obj){
		tabObjects[tabCnt++]=tab_obj;
	}
    
	/**
	 *  This function initSheet define the basic properties of the sheet on the screen.
	 * @param sheetObj
	 * @param sheetNo
	 */
    function initSheet(sheetObj,sheetNo) {
    	var cnt = 0;
    	switch (sheetNo) {
    		case 1:
    			with(sheetObj){    
    				var HeadTitle1 = "STS|Partner|Lane|Invoice No|Slip No|Approved|Curr.|Revenue|Expense|Customer/S.Provider|Customer/S.Provider";
    				var HeadTitle2 = "STS|Partner|Lane|Invoice No|Slip No|Approved|Curr.|Revenue|Expense|Code|Name"
    	
    	            SetConfig( { SearchMode:2, MergeSheet:5, Page:20, FrozenCol:0, DataRowMerge:1 } );
    	
    	            var info    = { Sort:0, ColMove:0, HeaderCheck:0, ColResize:1 };
    	            var headers = [ { Text: HeadTitle1, Align: "Center"},
    	                            { Text: HeadTitle2, Align: "Center"}];
    	            InitHeaders(headers, info);
    	            
    	            var cols = [ 
    	       	             { Type: "Status", Hidden: 1, Width: 50,  Align: "Center", ColMerge: 0, SaveName: "ibflag" },
    	       	             { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "jo_crr_cd",       KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0}, 
    	       	             { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "rlane_cd",        KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0}, 
    	       	             { Type: "Text",   Hidden: 0, Width: 150, Align: "Center", ColMerge: 0, SaveName: "inv_no",          KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0}, 
    	       	             { Type: "Text",   Hidden: 0, Width: 200, Align: "Center", ColMerge: 0, SaveName: "csr_no",          KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0}, 
    	       	             { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "apro_flg",        KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0},
    	       	             { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "locl_curr_cd",    KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0},
    	       	             { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "inv_rev_act_amt", KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0},
    	       	          	 { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "inv_exp_act_amt", KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0},
    	       	          	 { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "prnr_ref_no",     KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0},
    	       	          	 { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "cust_vndr_eng_nm",KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0}
    	       	             ];
    	            InitColumns(cols);
    				SetEditable(1);
    				SetAutoSumPosition(1);
    				SetWaitImageVisible(0);
    				resizeSheet(); 
    			}
    			break;
    		case 2:
    			with(sheetObj){
    				var HeadTitle1 = "STS|Partner|Lane|Invoice No|Slip No|Approved|Rev/Exp|Item|Curr.|Revenue|Expense|Customer/S.Provider|Customer/S.Provider";
    				var HeadTitle2 = "STS|Partner|Lane|Invoice No|Slip No|Approved|Rev/Exp|Item|Curr.|Revenue|Expense|Code|Name";
    				
    				SetConfig( { SearchMode:2, MergeSheet:5, Page:20, FrozenCol:0, DataRowMerge:1 } );
    				
    	            var info    = { Sort:0, ColMove:0, HeaderCheck:0, ColResize:1 };
    	            var headers = [ { Text: HeadTitle1, Align: "Center"},
    	                            { Text: HeadTitle2, Align: "Center"}];
    	            InitHeaders(headers, info);
    	            
    	            var cols = [ 
    		       	             { Type: "Status", Hidden: 1, Width: 50,  Align: "Center", ColMerge: 0, SaveName: "ibflag" },
    		       	             { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "jo_crr_cd",       KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0}, 
    		       	             { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "rlane_cd",        KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0}, 
    		       	             { Type: "Text",   Hidden: 0, Width: 150, Align: "Center", ColMerge: 0, SaveName: "inv_no",          KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0}, 
    		       	             { Type: "Text",   Hidden: 0, Width: 200, Align: "Center", ColMerge: 0, SaveName: "csr_no",          KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0}, 
    		       	             { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "apro_flg",        KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0},
    		       	             { Type: "Combo",  Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "rev_exp",         KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0, ComboText: "Rev|Exp", ComboCode: "R|E"},
    		       	          	 { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "item",        	 KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0},
    		       	             { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "locl_curr_cd",    KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0},
    		       	             { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "inv_rev_act_amt", KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0},
    		       	          	 { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "inv_exp_act_amt", KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0},
    		       	          	 { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "prnr_ref_no",     KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0},
    		       	          	 { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "cust_vndr_eng_nm",KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0}
    		       	             ];
    		            InitColumns(cols);
    					SetEditable(1);
    					SetAutoSumPosition(1);
    					SetWaitImageVisible(0);
    					resizeSheet();
    			}
    			break;
    	}
    }

    /**
     * This function defines the transaction logic between the user interface and the server of IBSheet.
     * @param sheetObj
     * @param formObj
     * @param sAction
     */
    function doActionIBSheet(sheetObj, formObj, sAction){
    	switch(sAction) {
		case IBSEARCH:      // 조회
				if(sheetObj.id == "sheet1"){
					ComOpenWait(true);
					searchSummary = getSearchOption();
					formObj.f_cmd.value = SEARCH;
					var xml = sheetObjects[0].GetSearchData("PRACTICE_3GS.do", FormQueryString(formObj));
		    		sheetObjects[0].LoadSearchData(xml,{Sync:1});
		    		
//					sheetObj.DoSearch("PRACTICE_3GS.do", FormQueryString(formObj));
					
				} else if (sheetObj.id == "sheet2"){
					ComOpenWait(true);
					searchDetail = getSearchOption();
					formObj.f_cmd.value = SEARCH03;
					
					var xml = sheetObjects[1].GetSearchData("PRACTICE_3GS.do", FormQueryString(formObj));
		    		sheetObjects[1].LoadSearchData(xml,{Sync:1});
		    		
//					sheetObj.DoSearch("PRACTICE_3GS.do", FormQueryString(formObj));
				}
				break;
	        	
		case IBDOWNEXCEL:	// 엑셀다운로드
			if (sheetObj.RowCount() < 1) {
				ComShowCodeMessage("COM132501");
			} else {
				sheetObj.Down2ExcelBuffer(true);
				sheetObj.Down2Excel({ FileName: 'Report', SheetName: ' sheet1', DownCols: makeHiddenSkipCol(sheetObj), SheetDesign: 1, Merge: 1 });
				sheetObjects[1].Down2Excel({ SheetName: ' sheet2', DownCols: makeHiddenSkipCol(sheetObjects[1]), Merge: 1 });
				sheetObj.Down2ExcelBuffer(false);
				
			}
			break;
    	}
    }
    
    /**
     * This function resize sheet,
     * If don't call this functions, it will may make UI break.
     */
    function resizeSheet() {
    	ComResizeSheet(sheetObjects[0]);
    	ComResizeSheet(sheetObjects[1]);
    }
    
    /**
     *  This function will delete the values ​​in the input and the Grid that are displayed in the UI when click new button.
     * @param formObj
     */
    function resetForm(formObj) {
        formObj.reset();
        initCalender();
        comboObjects[0].SetSelectIndex(0);	
        sheetObjects[0].RemoveAll();
        sheetObjects[1].RemoveAll();

    }
    
    /**
     * check date valid
     * @param formObj
     * @returns {Boolean}
     */
    function checkDate(formObj){
    	var dateFrom = new Date(formObj.acct_yrmon_from.value);
    	var dateTo = new Date(formObj.acct_yrmon_to.value);

    	return dateFrom < dateTo;
    }
    
    /**
     * check between datefrom and dateto over 3 month
     * @param formObj
     * @returns {Boolean}
     */
    function checkOverThreeMonth(formObj){
    	var months;
    	var dateFrom = new Date(formObj.acct_yrmon_from.value);
    	var dateTo = new Date(formObj.acct_yrmon_to.value);
    	
        months = (dateTo.getFullYear() - dateFrom.getFullYear()) * 12;
        months -= dateFrom.getMonth();
        months += dateTo.getMonth();
        
        if(months <= 3) return false;
        else return true;
    }
    
    /**
     * event start when click change tab
     * nItem --> the number of tab that user click in
     * @param tabObj
     * @param nItem
     */
    function tab1_OnChange(tabObj, nItem) {
    	var objs=document.all.item("tabLayer");
    	objs[nItem].style.display="Inline";		
    	for(var i = 0; i<objs.length; i++){
    		  if(i != nItem){
    		   objs[i].style.display="none";
    		   objs[beforetab].style.zIndex=objs[nItem].style.zIndex - 1 ;
    		  }
    		}
    	beforetab=nItem;
    	handleOnchangeTab();
        resizeSheet();
    } 
    
    /**
     * get current search option
     * @returns
     */
    function getSearchOption(){
    	var formObject = document.form;
    	var searchOptionString = formObject.acct_yrmon_from.value + formObject.acct_yrmon_to.value
    				+ formObject.s_jo_crr_cd.value + formObject.s_rlane_cd.value + formObject.s_trade_cd.value;
    	return searchOptionString;
    }
    
    /**
     * get current sheet
     * @returns
     */
    function getCurrentSheet() {
    	return sheetObjects[beforetab];
    }
    
    /**
     * handle on change tab
     */
    function handleOnchangeTab(){
    	if(firstLoad) {
    		firstLoad=false;
    		return;
    	}
    	var currentSheet = getCurrentSheet();
    	var formQuery = getSearchOption();

    	if(searchSummary != formQuery && searchDetail != formQuery){
    		if (confirm("Search data was changed. Do you want to retrieve?")) {
    			doActionIBSheet(currentSheet,document.form,IBSEARCH);
    		} else {
    			return;
    		}
    	}
    	
    	if(currentSheet.id=="sheet1"){//Summary Sheet
    		if(searchSummary != formQuery){
//    			searchSummary=formQuery;
    			doActionIBSheet(currentSheet, document.form, IBSEARCH);
    		}
    	}else{
    		if(searchDetail != formQuery){
//    			searchDetail=formQuery;
    			doActionIBSheet(currentSheet, document.form, IBSEARCH);
    		}
    	}
    }
    
    /**
     * event start when user double click on 1 invoice
     * @param sheetObj
     * @param Row
     * @param Col
     */
    function sheet1_OnDblClick(sheetObj, Row, Col) {
    	formObj = document.form;
    	var rowCount = sheetObjects[1].RowCount();
    	if (sheetObj.GetCellValue(Row,"jo_crr_cd") == ""){
    		return;
    	}
    	if(rowCount == 0) {
    		formObj.f_cmd.value = SEARCH03;
    		var xml = sheetObjects[1].GetSearchData("PRACTICE_3GS.do", searchOptionForDbl);
    		sheetObjects[1].LoadSearchData(xml,{Sync:1});
    		tab1.SetSelectedIndex(1);
    	}
    	sheetObjects[1].SetSelectRow(getSelectRow(sheetObj, Row, Col));
    }
    
    /**
     * get data row in orther tab
     * @param sheetObj
     * @param Row
     * @param Col
     * @returns {Number}
     */
    function getSelectRow(sheetObj, Row, Col){
    	var num = 0;
    	if(sheetObj.GetCellValue(Row,"jo_crr_cd") != "") {
    		var indexInv = sheetObj.GetCellValue(Row,"csr_no");
        	
        	for (var i = 2; i <= sheetObjects[1].RowCount() + 1; i++) {
        		if (sheetObjects[1].GetCellValue(i, "jo_crr_cd") != "") {
        			var indexInvCompare = sheetObjects[1].GetCellValue(i, "csr_no");
        			if (indexInvCompare === indexInv){
        				tab1.SetSelectedIndex(1);
        				num = i;
        				break;
        			}
        		}
        	}
    	} return num;
    }
    
    /**
     * 
     * @param sheetObj
     * @param Code
     * @param Msg
     */
    function sheet1_OnSaveEnd(sheetObj, Code, Msg) {
    	if (Code >= 0) {
    		doActionIBSheet(sheetObj, document.form, IBSEARCH);
    	}
    	var invalidData = sheetObj.FindStatusRow('I');
    	var rows = invalidData.split(';');
    	for(var i = 0; i < rows.length; i++){
    		
    		var code = sheetObj.GetCellValue(rows[i], "jo_crr_cd") + " - " + sheetObj.GetCellValue(rows[i], "rlane_cd");
    		if(Msg.includes(code)){
    			sheetObj.SetRowBackColor(rows[i],"#f58167");
    		}else{
    			sheetObj.SetRowBackColor(rows[i],"#ffffff");
    		}
    	}
    }
    
    /**
     * event start after searching sheet 1
     * @param sheetObj
     * @param Code
     * @param Msg
     * @param StCode
     * @param StMsg
     */
    function sheet1_OnSearchEnd(sheetObj, Code, Msg, StCode, StMsg) { 
     	ComOpenWait(false);
    	var totalRow = sheetObj.RowCount();
    	for (var i = 1; i <= totalRow+1; i++){
    		if (sheetObj.GetCellValue(i, "jo_crr_cd") == ''){
    			if (sheetObj.GetCellValue(i, "inv_no") !== ''){
    				sheetObj.SetRowFontColor(i,"#c55a11");
    				sheetObj.SetRangeFontBold(i,1,i,10,1);
    				sheetObj.SetCellValue(i,"inv_no","");
    			}
    			else if (sheetObj.GetCellValue(i,"inv_no") == ''){
    				sheetObj.SetRowBackColor(i,"#f7caac");
    				sheetObj.SetRangeFontBold(i,1,i,10,1);
    			}

    		}
    		
    	}
    	document.form.f_cmd=SEARCH03;
    	searchOptionForDbl= FormQueryString(document.form)
    	ComOpenWait(false);
     }

    /**
      	* event start after searching sheet 1
     * @param sheetObj
     * @param Code
     * @param Msg
     * @param StCode
     * @param StMsg
     */
    function sheet2_OnSearchEnd(sheetObj, Code, Msg, StCode, StMsg) { 
     	ComOpenWait(false);
    	var totalRow = sheetObj.RowCount();
    	for (var i = 1; i <= totalRow+1; i++){
    		if (sheetObj.GetCellValue(i, "jo_crr_cd") == ''){
    			if (sheetObj.GetCellValue(i, "inv_no") !== ''){
    				sheetObj.SetRowFontColor(i,"#c55a11");
    				sheetObj.SetRangeFontBold(i,1,i,10,1);
    				sheetObj.SetCellValue(i,"inv_no","");
    			}
    			else if (sheetObj.GetCellValue(i,"inv_no") == ''){
    				sheetObj.SetRowBackColor(i,"#f7caac");
    				sheetObj.SetRangeFontBold(i,1,i,10,1);
    			}

    		}
    	}
     }
