    
	var sheetObjects = new Array();
	var sheetCnt = 0;
	document.onclick = processButtonClick;
	
	
	function loadPage(){
		for(var i = 0; i < sheetObjects.length; i++) {
			ComConfigSheet(sheetObjects[i]);
			initSheet(sheetObjects[i], i + 1);
			ComEndConfigSheet(sheetObjects[i]);
		}
		doActionIBSheet(sheetObjects[0],document.form,IBSEARCH);
    }
    
    function processButtonClick(){
    	var sheetObject1 = sheetObjects[0];
        var formObject = document.form;
    		// get name object
    		var srcName = ComGetEvent("name");
            switch(srcName) {
            	case "btn_Retrieve":
            		doActionIBSheet(sheetObject1, formObject, IBSEARCH);
        			break;
            
            	case "btn_OK":
            		comPopupOK();
        			break;
            
    			default:
    				break;
    	}
    }
    
    function setSheetObject(sheet_obj){
    	sheetObjects[sheetCnt++] = sheet_obj;
    }
    
    function initSheet(sheetObj,sheetNo) {
    	var cnt = 0;
    	switch (sheetNo) {
    		case 1:
    			with (sheetObj) {
            	var HeadTitle = "STS|Select|Country|Sequence|Legacy Customer Name|Short Name";
    			
    			SetConfig( { SearchMode:2, MergeSheet:5, Page:20, FrozenCol:0, DataRowMerge:1 } );
    			
    			var info    = { Sort:1, ColMove:1, HeaderCheck:0, ColResize:1 };
    			var headers = [ { Text:HeadTitle, Align:"Center"} ];
    			InitHeaders(headers, info);
    			
    			var cols = [
    				{Type : "Radio",  Hidden:0, Width:50,  Align:"Center", SaveName:"radio",           KeyField:0, UpdateEdit:1, InsertEdit:1}, 
    				{Type:"CheckBox", Hidden:0, Width:20,  Align:"Center", SaveName:"checkbox",        KeyField:0, UpdateEdit:1, InsertEdit:1},
    				{Type : "Text",   Hidden:0, Width:100, Align:"Center", SaveName:"cust_cnt_cd",     KeyField:1, UpdateEdit:0, InsertEdit:1}, 
    				{Type : "Text",   Hidden:0, Width:100, Align:"Center", SaveName:"cust_seq",        KeyField:1, UpdateEdit:0, InsertEdit:1}, 
    				{Type : "Text",   Hidden:0, Width:500, Align:"Left",   SaveName:"cust_lgl_eng_nm", KeyField:1, UpdateEdit:0, InsertEdit:1}, 
    				{Type : "Text",   Hidden:0, Width:200, Align:"Left",   SaveName:"cust_abbr_nm",    KeyField:0, UpdateEdit:0, InsertEdit:1}
    				];
    				InitColumns(cols)
    		        SetWaitImageVisible(0);
    				SetSheetHeight(400);
//                    ComResizeSheet(sheetObjects[0]);
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
//				ComOpenWait(true);
				formObj.f_cmd.value = SEARCH01;
				sheetObj.DoSearch("customer_popupGS.do", FormQueryString(formObj));
			break;
    	}
    }
    
    function sheet1_OnSearchEnd(sheetObj, Code, Msg, StCode, StMsg) { 
     	ComOpenWait(false);
     }
  