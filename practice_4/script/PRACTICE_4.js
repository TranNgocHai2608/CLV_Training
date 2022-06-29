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
	// count sheet 
	var sheetCnt = 0;
	var comboCnt = 0;
	// set function processButtonClick for event onclick on browser
	document.onclick = processButtonClick;
	
	
	function loadPage(){
		for(var i = 0; i < sheetObjects.length; i++) {
			ComConfigSheet(sheetObjects[i]);
			initSheet(sheetObjects[i], i + 1);
			ComEndConfigSheet(sheetObjects[i]);
		}
		for ( var k = 0; k < comboObjects.length; k++) {
			initCombo(comboObjects[k], k + 1);
		}
	
		doActionIBSheet(sheetObjects[0],document.form,IBSEARCH);
    	// IBSEARCH
    }
	
	function initCombo(comboObj, comboNo) {
		comboObj.SetTitle("ALL");
		comboObj.SetTitleVisible(true);
		comboObj.SetEnableAllCheckBtn(true);
//		combiObj.SetMultiSelect(0);
		var formObj = document.form;
		switch (comboNo) {
		case 1:
			with (comboObj) {
				SetMultiSelect(1);
		        SetDropHeight(200);
		        ValidChar(2,1);
			}
			var comboItems = carriersCombo.split("|");
			addComboItem(comboObj, comboItems);
			break;
		}
	}
	
	 function addComboItem(comboObj, comboItems) {
	        for (var i = 0; i < comboItems.length; i++) {
	                comboObj.InsertItem(i, comboItems[i], comboItems[i]);
	        }
	    }
	 
	function setComboObject(combo_obj) {
		comboObjects[comboCnt++] = combo_obj;
	}
    
    function processButtonClick(){
    	var sheetObject1 = sheetObjects[0];
        var formObject = document.form;
    	try {
    		// get name object
    		var srcName = ComGetEvent("name");
            switch(srcName) {
            	case "btn_Retrieve":
            		doActionIBSheet(sheetObject1, formObject, IBSEARCH);
        			break;
            
            		   	
            	case "btn_Add":
            		sheetObject1.DataInsert(-1);
            		break;
            	case "btn_Save":
            		var code = sheetObject1.GetCellValue(sheetObject1.GetSelectRow(), "jo_crr_cd");
            			doActionIBSheet(sheetObject1, formObject, IBSAVE);
            		break;
            	case "btn_New":
            		resetForm(formObject);
            		break;
            	case "btn_Delete":
            		doActionIBSheet(sheetObject1, formObject, IBDELETE);
            		break;
            	case "btn_DownExcel":
            		doActionIBSheet(sheetObject1, formObject, IBDOWNEXCEL);
            		break;
            		
            	case "btn_calendar_dt_fr":
    			case "btn_calendar_dt_to":
                    var cal = new ComCalendar();
                    if(srcName == "btn_calendar_dt_fr"){
                    	cal.select(formObject.s_cre_dt_fm, 'yyyy-MM-dd');
                    }else{
                        cal.select(formObject.s_cre_dt_to, 'yyyy-MM-dd');
                    }
                    break;
    			default:
    				break;
    		}
//            } // end switch
            
    	}catch(e) {
    		if( e == "[object Error]") {
    			ComShowMessage(OBJECT_ERROR);
    		} else {
    			console.log(e);
    		}
    	}
    }
    
    function setSheetObject(sheet_obj){
    	sheetObjects[sheetCnt++] = sheet_obj;
    }
    
    function initSheet(sheetObj,sheetNo) {
    	var cnt = 0;
    	switch (sheetNo) {
    		case 1:
    			with(sheetObj){  
    			var HeadTitle="STS|Chk|Carrier|Rev. Lane|Vendor Code|Customer Code|Customer Code|Trade|Delete Flag|Create Date|Create User ID|Update Date|Update User ID";    				
    			SetConfig( { SearchMode:2, MergeSheet:5, Page:20, FrozenCol:0, DataRowMerge:1 } );
    				var info    = { Sort:1, ColMove:1, HeaderCheck:0, ColResize:1 };
    				var headers = [ { Text:HeadTitle, Align:"Center"} ];
    				InitHeaders(headers, info);
    				var cols = [ 
    				            {Type:"Status",    Hidden:0, Width:50,  Align:"Center",  SaveName:"ibflag"}, 
    				            {Type:"DelCheck",  Hidden:0, Width:50,  Align:"Center",  SaveName:"del_chk"},
    					        {Type:"Popup",     Hidden:0, Width:100, Align:"Center",  SaveName:"jo_crr_cd",     KeyField:1, UpdateEdit:0, InsertEdit:1, AcceptKeys:"E", InputCaseSensitive:1, EditLen:3},
    					        {Type:"ComboEdit", Hidden:0, Width:100, Align:"Center",  SaveName:"rlane_cd",      KeyField:1, UpdateEdit:0, InsertEdit:1, ComboCode: lanesCombo, ComboText: lanesCombo},
    					        {Type:"PopupEdit", Hidden:0, Width:100, Align:"Center",  SaveName:"vndr_seq",      KeyField:1, UpdateEdit:1, InsertEdit:1, AcceptKeys:"N", EditLen:6},
    					        {Type:"PopupEdit", Hidden:0, Width:50,  Align:"Center",  SaveName:"cust_cnt_cd",   KeyField:1, UpdateEdit:1, InsertEdit:1, AcceptKeys:"E", InputCaseSensitive:1, EditLen:2}, 
    						    {Type:"PopupEdit", Hidden:0, Width:100, Align:"Center",  SaveName:"cust_seq",      KeyField:1, UpdateEdit:1, InsertEdit:1, AcceptKeys:"N", EditLen: 6}, 
    						    {Type:"PopupEdit", Hidden:0, Width:100, Align:"Center",  SaveName:"trd_cd",        KeyField:0, UpdateEdit:1, InsertEdit:1, AcceptKeys:"E", InputCaseSensitive:1, EditLen:3},
    						    {Type:"Combo",     Hidden:0, Width:70,  Align:"Center",  SaveName:"delt_flg",      KeyField:0, UpdateEdit:1, InsertEdit:1, ComboCode:"N|Y",  ComboText:"N|Y"}, 
    						    {Type:"Text",      Hidden:0, Width:200, Align:"Center",  SaveName:"cre_dt",        KeyField:0, UpdateEdit:0, InsertEdit:0}, 
    						    {Type:"Text",      Hidden:0, Width:200, Align:"Left",    SaveName:"cre_usr_id",    KeyField:0, UpdateEdit:0, InsertEdit:0}, 
    						    {Type:"Text",      Hidden:0, Width:200, Align:"Center",  SaveName:"upd_dt",        KeyField:0, UpdateEdit:0, InsertEdit:0}, 
    						    {Type:"Text",      Hidden:0, Width:200, Align:"Left",    SaveName:"upd_usr_id",    KeyField:0, UpdateEdit:0, InsertEdit:0}
    					    ];
    		        InitColumns(cols);
    		        SetWaitImageVisible(0);
    		        resizeSheet();
    			}
    			break;
    	}
    }

    
    function resizeSheet(){
   	     ComResizeSheet(sheetObjects[0]);
   }
    
    function doActionIBSheet(sheetObj, formObj, sAction){
    	switch(sAction) {
		case IBSEARCH:      // 조회
				ComOpenWait(true);
				formObj.f_cmd.value = SEARCH;
				var dateFrom = formObj.s_cre_dt_fm.value;
				var dateTo = formObj.s_cre_dt_to.value;
				if(!compareDate(dateFrom, dateTo)){
					ComShowMessage("The start date cannot be earlier than the end date");
					ComOpenWait(false);
	            	break;
				}
				sheetObj.DoSearch("PRACTICE_4GS.do", FormQueryString(formObj));
			break;
		case IBSAVE:
				formObj.f_cmd.value = MULTI;
				sheetObj.DoSave("PRACTICE_4GS.do", FormQueryString(formObj));
			break;
			
		case IBDELETE:        
				sheetObj.SetCellValue(sheetObj.GetSelectRow(),"ibflag", "D");
				formObj.f_cmd.value = MULTI;
	            sheetObj.DoSave("PRACTICE_4GS.do", FormQueryString(formObj));
	        	break;
	        	
		case IBDOWNEXCEL:	// 엑셀다운로드
			if(sheetObj.RowCount() < 1){
				ComShowCodeMessage("COM132501");
			}else{
				sheetObj.Down2Excel({DownCols: makeHiddenSkipCol(sheetObj)});
			}
			break;
    	}
    }
    
    function sheet1_OnPopupClick(sheetObj, Row, Col){
    	var name = sheetObj.ColSaveName(Col);
    	switch(name){
    	case "cust_cnt_cd":
    	case "cust_seq":
    		ComOpenPopup('/opuscntr/customer_popup.do', 1050, 550, 'setCustCd', '1,0,1,1,1,1', true);
    		break;
    	case "jo_crr_cd":
    		ComOpenPopup('/opuscntr/COM_ENS_0N1.do', 800, 500, 'setCrrCd', '1,0,0,1,1,1,1,1', true);
			break;
    	case "vndr_seq":
    		ComOpenPopup('/opuscntr/COM_COM_0007.do', 900, 520, 'setVndrCd', '1,0,1', true);
			break;
    	case "trd_cd":
    		ComOpenPopup('/opuscntr/COM_COM_0012.do', 800, 500, 'setTrdCd', '1,0,0,1,1,1,1,1', true);
			break;
    	}
    }
    
    function setCustCd (arrPopupData) {
    	console.log(arrPopupData)
    	sheetObjects[0].SetCellValue(sheetObjects[0].GetSelectRow(), "cust_cnt_cd", arrPopupData[0][2]);
    	sheetObjects[0].SetCellValue(sheetObjects[0].GetSelectRow(), "cust_seq", arrPopupData[0][3]);
    }
    function setVndrCd (arrPopupData){
    	sheetObjects[0].SetCellValue(sheetObjects[0].GetSelectRow(), "vndr_seq", arrPopupData[0][2]);
    }
    
    function setTrdCd (arrPopupData){
    	sheetObjects[0].SetCellValue(sheetObjects[0].GetSelectRow(), "trd_cd", arrPopupData[0][3]);
    }
    
    function setCrrCd(arrPopupData) {
    	sheetObjects[0].SetCellValue(sheetObjects[0].GetSelectRow(), "jo_crr_cd", arrPopupData[0][3]);
    }
    
    function compareDate(creFrom, creTo){
    	var dateFrom = new Date(creFrom);
    	var dateTo = new Date(creTo);
    	return creFrom <= creTo;
    }
    
    function resetForm(formObj) {
        formObj.reset();
        sheetObjects[0].RemoveAll();
        doActionIBSheet(sheetObjects[0], formObj, IBSEARCH)
    }    
   
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
    function sheet1_OnSearchEnd(sheetObj, Code, Msg, StCode, StMsg) { 
     	ComOpenWait(false);
     }
  