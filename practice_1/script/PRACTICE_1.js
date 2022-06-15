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
	
	// count sheet 
	var sheetCnt = 0;
	// set function processButtonClick for event onclick on browser
	document.onclick = processButtonClick;
	
	
    function loadPage(){
    	for(i = 0; i < sheetObjects.length; i++){
    		// Set the basic design of the sheet. This function must be called before sheet initialization. 
			ComConfigSheet(sheetObjects[i]);
			initSheet(sheetObjects[i], i+1);
			// Finish the basic design of the sheet. This function must be called after sheet initialization.
			ComEndConfigSheet(sheetObjects[i]);
			// auto search data after loading finish.
			doActionIBSheet(sheetObjects[i], document.form, IBSEARCH); // IBSEARCH
		}
    }
    
    function processButtonClick(){
    	// get sheet 1
    	var sheetObject1 = sheetObjects[0];
        var formObject = document.form;
    	try {
    		// get name object
    		var srcName = ComGetEvent("name");
            switch(srcName) {
            	case "btn_Add":
            		// new row will be displayed below the selected row
            		sheetObject1.DataInsert();
            		break;
            	case "btn_Retrieve":
            		// call doActionIBSheet with action is IBSEARCH
            		doActionIBSheet(sheetObject1, formObject, IBSEARCH);
            		break;
            	case "btn_Save":
            		// Get value of error message code at current cell
            		var code = sheetObject1.GetCellValue(sheetObject1.GetSelectRow(), "err_msg_cd");
            		// check validate
            		if(validateForm(sheetObject1, code) == true){
            			//call doActionIBSheet with action is IBSAVE
            			doActionIBSheet(sheetObject1, formObject, IBSAVE);
            		} else {
            			// alert
            			ComShowCodeMessage("COM12151", code);
            		}
            		break;
            	case "btn_DownExcel":
            		//call doActionIBSheet with action is IBDOWNEXCEL
            		doActionIBSheet(sheetObject1, formObject, IBDOWNEXCEL);
            		break;
            } // end switch
    	}catch(e) {
    		if( e == "[object Error]") {
    			ComShowMessage(OBJECT_ERROR);
    		} else {
    			ComShowMessage(e);
    		}
    	}
    }
    
    /*
     * Function that add sheet object to array
     */
    function setSheetObject(sheet_obj){
    	sheetObjects[sheetCnt++]=sheet_obj;
    }
    
    function initSheet(sheet_obj, sheetNo){
    	var cnt = 0;
    	// get sheet id
    	var sheetID = sheet_obj.id;
        switch(sheetID) {
            case "sheet1":
            	with(sheet_obj){
            	// Define header title
                var HeadTitle="|Del|Msg Cd|Msg Type|Msg level|Message|Description" ;
                
                // fetch initialized sheet, location of frozen rows or columns and other basic configurations.
                // SearchMode: Configure search mode (Default: 2)
                // Page: Number of rows to display in one page (Default=20)
                
                SetConfig( { SearchMode: 2, Page:20} );
                
                // Sort: Whether to allow sorting by clicking on the header (Default=1)
                // ColMove: Whether to allow column movement in header (Default=1)
                // ColResize: Whether to allow resizing of column  width (Default=1)
                // Whether the CheckAll in the header is checked (Default=1)
                var info    = { Sort:1, ColMove:1, HeaderCheck:0, ColResize:1 };
                
                
                // Text: tring of texts to display in header, adjoined by "|"
                // Align: How to align header text (Default = "Center")
                var headers = [ { Text:HeadTitle, Align:"Center"} ];
                
                InitHeaders(headers, info);

                // Type: Type of column
                // Hidden: Whether a column is hidden
                // Width: column width
                // Align: Data alignment
                // SaveName: A parameter name used to save or search data
                // KeyField: Required fields
                // UpdateEdit: Whether to allow data editing when transaction is in "Search" state
                // InsertEdit: Whether to allow data editing when transaction is in "Insert" state
                // ComboCode: Combo list code group
                // ComboText: Combo list text string group
                // MultiText: Whether to use multilines
                var cols = [{Type:"Status", Hidden:0, Width:30,   Align:"Center", SaveName:"ibflag" },
	                     {Type:"DelCheck",  Hidden:0, Width:45,   Align:"Center", SaveName:"DEL",         KeyField:0, UpdateEdit:1,   InsertEdit:1 },
	                     {Type:"Text",      Hidden:0, Width:80,   Align:"Center", SaveName:"err_msg_cd",  KeyField:1, UpdateEdit:0,   InsertEdit:1 },
	                     {Type:"Combo",     Hidden:0, Width:80,   Align:"Center", SaveName:"err_tp_cd",   KeyField:1, UpdateEdit:1,   InsertEdit:1, ComboText:"Server|UI|Both", ComboCode:"S|U|B" },
	                     {Type:"Combo",     Hidden:0, Width:80,   Align:"Center", SaveName:"err_lvl_cd",  KeyField:1, UpdateEdit:1,   InsertEdit:1, ComboText:"ERR|WARNING|INFO", ComboCode:"E|W|I" },
	                     {Type:"Text",      Hidden:0, Width:400,  Align:"Left",   SaveName:"err_msg",     KeyField:1, UpdateEdit:1,   InsertEdit:1, MultiLineText:1 },
	                     {Type:"Text",      Hidden:0, Width:250,  Align:"Left",   SaveName:"err_desc",    KeyField:0, UpdateEdit:1,   InsertEdit:1 } ];
                InitColumns(cols);
                SetWaitImageVisible(0);
                // change size to fit window
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
			// ComOpenWait:
				// true: lock the screen and appear loading image
				// false: return normal
			ComOpenWait(true);
			// set value for f_cmd is hidden on UI
			formObj.f_cmd.value = SEARCH;
			// Connect to search page to read search XML, and then load XML data internally in IBSheet
			sheetObj.DoSearch("PRACTICE_1GS.do", FormQueryString(formObj));
			break;
		case IBSAVE:        // 저장
        	formObj.f_cmd.value = MULTI;
        	//Save data based on data transaction status or column.
            sheetObj.DoSave("PRACTICE_1GS.do", FormQueryString(formObj));
			break;
		case IBDOWNEXCEL:	// 엑셀다운로드
			// if sheet null display error
			if(sheetObj.RowCount() < 1){
				ComShowCodeMessage("COM132501");
			}else{
				// Download Excel:
				// DownCols: parameter is a string connecting all downloading rows using "|".
				// makeHiddenSkipCol: ignore hidden column
				sheetObj.Down2Excel({DownCols: makeHiddenSkipCol(sheetObj)});
			}
			break;
    	}
    }
    
    function sheet1_OnChange(sheetObj) {
    	// Get value of error message code at current cell
    	var code = sheetObj.GetCellValue(sheetObj.GetSelectRow(), "err_msg_cd");
    		if (validateForm(sheetObj, code) == false){
    			ComShowCodeMessage("COM132201", "8 characters are required, the first 3 characters are uppercase letters, the last 5 characters are numbers. " + code);
		} else {
			 for(var i = 0; i < sheetObj.RowCount(); i++) {
	    		var orlcode = sheetObj.GetCellValue(i, "err_msg_cd");
	    		// Check exist code
	    		if(code != '' && i != sheetObj.GetSelectRow() && code == orlcode){
	   				 ComShowCodeMessage('COM131302',code);
	   				 sheetObj.SetCellValue(sheetObj.GetSelectRow(), "err_msg_cd", code);
	   				 return;
	   			 }
	    	}
		}
    }
    
    function validateForm(sheetObj, code){
    	var regex = /^[A-Z]{3}[0-9]{5}$/;
    	if (code.match(regex)) {
    		return true;
    	}
    	return false;
    } 
   
    function sheet1_OnSearchEnd(sheetObj, Code, Msg, StCode, StMsg) { 
     	ComOpenWait(false);
     }
  