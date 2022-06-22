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
			
		}
    	doActionIBSheet(sheetObjects[0], document.form, IBSEARCH); // IBSEARCH
    }
    
    function processButtonClick(){
    	var sheetObject1 = sheetObjects[0];
    	var sheetObject2 = sheetObjects[1];
        var formObject = document.form;
    	try {
    		// get name object
    		var srcName = ComGetEvent("name");
            switch(srcName) {
            	case "btn_Retrieve":
            		doActionIBSheet(sheetObject1, formObject, IBSEARCH);
        			break;
            	case "btn_Add_sheet1":
            		sheetObject1.DataInsert();
            		break;
            	case "btn_Del_sheet1":
//            		sheetObject1.RowDelete(sheetObject1.GetSelectRow(), 1);
//            		if(){
//            			
//            		}
            		doActionIBSheet(sheetObject1, formObject, IBDELETE);
            		break;
            		
            		
            	case "btn_Add_sheet2":
            		var code = sheetObject1.GetCellValue(sheetObject1.GetSelectRow(), "intg_cd_id");
//            		console.log(code);
            		sheetObject2.DataInsert();
            		sheetObject2.SetCellValue(sheetObject2.GetSelectRow(), "intg_cd_id", code);
            		
            		break;
            	case "btn_Del_sheet2":
            		doActionIBSheet(sheetObject2, formObject, IBDELETE);
            		break;            	
            		
            	case "btn_Save":
        		if(sheetObject1.IsDataModified()){
        			doActionIBSheet(sheetObject1, formObject, IBSAVE);
            	} 
        		if(sheetObject2.IsDataModified()) {
        			doActionIBSheet(sheetObject2, formObject, IBSAVE);
        		}
        		break;
            	
            } // end switch
            
    	}catch(e) {
//    		console.log(12345);
    		if( e == "[object Error]") {
    			ComShowMessage(OBJECT_ERROR);
    		} else {
    			console.log(e);
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
            	var HeadTitle="|SubSystem|Cd ID|Cd Name|Length|Cd Type|Table Name|Description Remark|Flag|Create User|Create Date|Update User|Update Date";
                
                SetConfig( { SearchMode: 2, Page:20} );
                var info    = { Sort:1, ColMove:1, HeaderCheck:0, ColResize:1 };
                var headers = [ { Text:HeadTitle, Align:"Center"} ];
                
                InitHeaders(headers, info);
                var cols = [
   	                     {Type:"Status", Hidden:0,  Width:30,   Align:"Center",  ColMerge:0, SaveName:"ibflag",          KeyField:0, CalcLogic:"", Format:"",    PointCount:0, UpdateEdit:1, InsertEdit:1 },
   	                     {Type:"Text",   Hidden:0,  Width:90,   Align:"Center",  ColMerge:0, SaveName:"ownr_sub_sys_cd", KeyField:1, CalcLogic:"", Format:"",    PointCount:0, UpdateEdit:1, InsertEdit:1, AcceptKeys : "E", InputCaseSensitive : 1 },
   	    	             {Type:"Text",   Hidden:0,  Width:60,   Align:"Center",  ColMerge:0, SaveName:"intg_cd_id",      KeyField:1, CalcLogic:"", Format:"",    PointCount:0, UpdateEdit:0, InsertEdit:1, AcceptKeys : "E", InputCaseSensitive : 1 },
   	    	             {Type:"Text",   Hidden:0,  Width:200,  Align:"Left",    ColMerge:0, SaveName:"intg_cd_nm",      KeyField:0, CalcLogic:"", Format:"",    PointCount:0, UpdateEdit:1, InsertEdit:1 },
   	    	             {Type:"Text",   Hidden:0,  Width:50,   Align:"Center",  ColMerge:0, SaveName:"intg_cd_len",     KeyField:0, CalcLogic:"", Format:"",    PointCount:0, UpdateEdit:1, InsertEdit:1, AcceptKeys:"N" },
   	    	             {Type:"Combo",   Hidden:0,  Width:100,  Align:"Center",  ColMerge:0, SaveName:"intg_cd_tp_cd",   KeyField:0, CalcLogic:"", Format:"",    PointCount:0, UpdateEdit:1, InsertEdit:1, ComboCode:"G", ComboText:"General" },
   	    	             {Type:"Text",   Hidden:0,  Width:150,  Align:"Left",    ColMerge:0, SaveName:"mng_tbl_nm",      KeyField:1, CalcLogic:"", Format:"",    PointCount:0, UpdateEdit:0, InsertEdit:1 },
   	    	             {Type:"Text",   Hidden:0,  Width:350,  Align:"Left",    ColMerge:0, SaveName:"intg_cd_desc",    KeyField:0, CalcLogic:"", Format:"",    PointCount:0, UpdateEdit:1, InsertEdit:1 },
   	    	             {Type:"Combo",   Hidden:0,  Width:40,   Align:"Center",  ColMerge:0, SaveName:"intg_cd_use_flg", KeyField:0, CalcLogic:"", Format:"",    PointCount:0, UpdateEdit:1, InsertEdit:1, ComboCode:"N|Y", ComboText:"N|Y" },
   	    	             {Type:"Text",   Hidden:0,  Width:80,   Align:"Center",  ColMerge:0, SaveName:"cre_usr_id",      KeyField:0, CalcLogic:"", Format:"",    PointCount:0, UpdateEdit:0, InsertEdit:0 },
   	    	             {Type:"Date",   Hidden:0,  Width:80,   Align:"Center",  ColMerge:0, SaveName:"cre_dt",          KeyField:0, CalcLogic:"", Format:"Ymd", PointCount:0, UpdateEdit:0, InsertEdit:0 },
   	    	             {Type:"Text",   Hidden:0,  Width:80,   Align:"Center",  ColMerge:0, SaveName:"upd_usr_id",      KeyField:0, CalcLogic:"", Format:"",    PointCount:0, UpdateEdit:0, InsertEdit:0 },
   	    	             {Type:"Date",   Hidden:0,  Width:80,   Align:"Center",  ColMerge:0, SaveName:"upd_dt",          KeyField:0, CalcLogic:"", Format:"Ymd", PointCount:0, UpdateEdit:0, InsertEdit:0 } ];
   	                             
                InitColumns(cols);
                SetWaitImageVisible(0);
                SetSheetHeight(400);
//                resizeSheet(); 
                }
            break;
            case "sheet2":
            	with(sheet_obj){
            	var HeadTitle="|Cd ID|Cd Val|Display Name|Description Remark|Order" ;
            	 
                 SetConfig( { SearchMode: 2, Page:20} );
                 var info    = { Sort:1, ColMove:1, HeaderCheck:0, ColResize:1 };
                 var headers = [ { Text:HeadTitle, Align:"Center"} ];
                 InitHeaders(headers, info);

                 var cols = [
                             {Type:"Status", Hidden:0, Width:30,  Align:"Center", ColMerge:0, SaveName:"ibflag",              KeyField:0, CalcLogic:"", Format:"", PointCount:0, UpdateEdit:1, InsertEdit:1 },
            			     {Type:"Text",   Hidden:0, Width:60,  Align:"Center", ColMerge:0, SaveName:"intg_cd_id",          KeyField:1, CalcLogic:"", Format:"", PointCount:0, UpdateEdit:0, InsertEdit:0, AcceptKeys : "E", InputCaseSensitive : 1 },
            			     {Type:"Text",   Hidden:0, Width:60,  Align:"Center", ColMerge:0, SaveName:"intg_cd_val_ctnt",    KeyField:1, CalcLogic:"", Format:"", PointCount:0, UpdateEdit:0, InsertEdit:1, AcceptKeys : "E", InputCaseSensitive : 1 },
            			     {Type:"Text",   Hidden:0, Width:200, Align:"Center", ColMerge:0, SaveName:"intg_cd_val_dp_desc", KeyField:0, CalcLogic:"", Format:"", PointCount:0, UpdateEdit:1, InsertEdit:1 },
            			     {Type:"Text",   Hidden:0, Width:600, Align:"Left",   ColMerge:0, SaveName:"intg_cd_val_desc",    KeyField:0, CalcLogic:"", Format:"", PointCount:0, UpdateEdit:1, InsertEdit:1 },
            			     {Type:"Text",   Hidden:0, Width:50,  Align:"Center", ColMerge:0, SaveName:"intg_cd_val_dp_seq",  KeyField:0, CalcLogic:"", Format:"", PointCount:0, UpdateEdit:1, InsertEdit:1 }];
                             
                 InitColumns(cols);
                 SetWaitImageVisible(0);
                 SetSheetHeight(300);
                 // change size to fit window
//                 resizeSheet(); 
     		}
             break;
        }
    }
    
    function resizeSheet(){
//   	     sheetObjects[sheetCnt++]=sheetObj;
   }
    
    function doActionIBSheet(sheetObj, formObj, sAction){
    	switch(sAction) {
    	
		case IBSEARCH:      // 조회
			if(sheetObj.id == "sheet1"){
				ComOpenWait(true);
				formObj.f_cmd.value = SEARCH;
				sheetObj.DoSearch("PRACTICE_2GS.do", FormQueryString(formObj));
				
			} else if(sheetObj.id == "sheet2") {
				formObj.f_cmd.value = SEARCH01;
				sheetObj.DoSearch("PRACTICE_2GS.do", FormQueryString(formObj));
			}
			break;
			
		case IBSAVE:        // 저장
			if(sheetObj.id == "sheet1") {
				formObj.f_cmd.value = MULTI;
	        	//Save data based on data transaction status or column.
	            sheetObj.DoSave("PRACTICE_2GS.do", FormQueryString(formObj));
			
			} else if (sheetObj.id == "sheet2") {
				formObj.f_cmd.value = MULTI01;
	        	//Save data based on data transaction status or column.
	            sheetObj.DoSave("PRACTICE_2GS.do", FormQueryString(formObj));
			}
        	break;
        	
		case IBDELETE:        
			if(sheetObj.id == "sheet1") {
				sheetObj.SetCellValue(sheetObj.GetSelectRow(),"ibflag", "D");
				formObj.f_cmd.value = MULTI;
	        	//Save data based on data transaction status or column.
	            sheetObj.DoSave("PRACTICE_2GS.do", FormQueryString(formObj));
	        	sheetObjects[1].RemoveAll();
			
			} else if (sheetObj.id == "sheet2") {
				sheetObj.SetCellValue(sheetObj.GetSelectRow(),"ibflag", "D");
				formObj.f_cmd.value = MULTI01;
	        	//Save data based on data transaction status or column.
	            sheetObj.DoSave("PRACTICE_2GS.do", FormQueryString(formObj));
			}
        	break; 
    	}
    }
    
    function sheet1_OnChange(sheetObj) {
    	// Get value of error message code at current cell
    	var code = sheetObj.GetCellValue(sheetObj.GetSelectRow(), "intg_cd_id");
			 for(var i = 0; i < sheetObj.RowCount(); i++) {
	    		var orlcode = sheetObj.GetCellValue(i, "intg_cd_id");
	    		if(code != '' && i != sheetObj.GetSelectRow() && code == orlcode){
	   				 ComShowCodeMessage('COM131302',code);
	   				 sheetObj.SetCellValue(sheetObj.GetSelectRow(), "intg_cd_id", "");
	   				 return;
	    	}
		}
    }
    
    function sheet2_OnChange(sheetObj) {
    	// Get value of error message code at current cell
    	var code = sheetObj.GetCellValue(sheetObj.GetSelectRow(), "intg_cd_val_ctnt");
			 for(var i = 0; i < sheetObj.RowCount(); i++) {
	    		var orlcode = sheetObj.GetCellValue(i, "intg_cd_val_ctnt");
	    		if(code != '' && i != sheetObj.GetSelectRow() && code == orlcode){
	   				 ComShowCodeMessage('COM131302',code);
	   				 sheetObj.SetCellValue(sheetObj.GetSelectRow(), "intg_cd_val_ctnt", "");
	   				 return;
	    	}
		}
    }
    
    function sheet1_OnDblClick(sheetObj, Row) {
    	//Set to move to another page when a row is double-clicked
    	document.form.dblclick.value = sheetObj.GetCellValue(Row, "intg_cd_id");
    	doActionIBSheet(sheetObjects[1],document.form,IBSEARCH);
    
//    	console.log(document.form.dblclick.value);
    	}
    

   
    function sheet1_OnSearchEnd(sheetObj, Code, Msg, StCode, StMsg) { 
     	ComOpenWait(false);
     }
  