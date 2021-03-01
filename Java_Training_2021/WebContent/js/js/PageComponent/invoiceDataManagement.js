/**
 * 
 */

var resetSort= {
	text: '',
	iconCls: 'x-btn-text-icon btn-icon-resetSort',
	tooltip: 'Reset Sort',
	handler: function() {
		userStore.sorters.clear();
    	userStore.load();
	}
};

var exportSingle= {
	text: '',
	iconCls: 'x-btn-text-icon btn-icon-export',
	tooltip: 'Export Selected Rows',
	handler: function(){ 
        var s= sampleGrid.getSelectionModel().getSelection();
		Ext.Msg.show({
		    title:'Export Selected Data',
		    message: `${s.length} items will be exported. Press OK to continue`,
		    buttons: Ext.Msg.OKCANCEL,
		    icon: Ext.Msg.QUESTION,
		    fn: function(btn) {
		        if (btn === 'ok') {
		            all = [];
					Ext.each(s, function (item) {
					  all.push(item.data);
					});
					var csvString= JSONtoCSV(all);
					downloadCSV(csvString);
		        }
		    }
		});
    }
};

var exportAll= {
	text: '',
	iconCls: 'x-btn-text-icon btn-icon-exportAll',
	tooltip: 'Export All',
	handler: function(){ 
        var s= userStore.data.items;
        Ext.Msg.show({
		    title:'Export All Data',
		    message: `${s.length} items will be exported. Press OK to continue`,
		    buttons: Ext.Msg.OKCANCEL,
		    icon: Ext.Msg.QUESTION,
		    fn: function(btn) {
		        if (btn === 'ok') {
		            all = [];
					Ext.each(s, function (item) {
					  all.push(item.data);
					});
					var csvString= JSONtoCSV(all);
					downloadCSV(csvString);
		        }
		    }
		});
       
    }
};

addElementsToStore= function() {
	
	const emailRegex = /^(([^<>()[\]\.,;:\s@\"]+(\.[^<>()[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i;
	
	var dataInputForm= new Ext.form.Panel({
	    defaultType: 'textfield',
	    items: [
	        {
	            fieldLabel: 'Name',
	            name: 'name',
	            allowBlank: false,
	        },
	        {
	            fieldLabel: 'Email',
	            name: 'email',
	            regex: emailRegex,
	            invalidText: 'Doesn\'t support proper Email Formatting',
	            allowBlank: false,
	        },
	        {
	            xtype: 'numberfield',
	            fieldLabel: 'Phone',
	            name: 'phone',
	            allowBlank: false,
	        }
	    ],
	    buttons: [{
	    	text: 'Add Data',
	    	formBind: true,
	    	handler: function () {
	    		var inputForm= this.up('form').getForm();
	    		if(inputForm.isValid()) {
	    			userStore.add(inputForm.getFieldValues());
	    			this.up('window').close();
	    		}
	    	}
        },{
        	text: 'Cancel',
        	handler: function () {
        		this.up('window').close();
        	}
        }]
	});
	
	
	return new Ext.window.Window({
	    title: 'Add Invoice Data',
	    height: screen.height * 0.65,
	    width: screen.width * 0.35,
	    modal: true,
	    layout: 'fit',
	    items: [dataInputForm]
	});
}

editStoreElement= function(storeConstructorObject) {
	
	const emailRegex = /^(([^<>()[\]\.,;:\s@\"]+(\.[^<>()[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i;
	
	var dataInputForm= new Ext.form.Panel({
	    defaultType: 'textfield',
	    items: [
	        {
	            fieldLabel: 'Name',
	            name: 'name',
	            value: storeConstructorObject[0].data.name,
	            allowBlank: false,
	        },
	        {
	            fieldLabel: 'Email',
	            name: 'email',
	            value: storeConstructorObject[0].data.email,
	            regex: emailRegex,
	            invalidText: 'Doesn\'t support proper Email Formatting',
	            allowBlank: false,
	        },
	        {
	            xtype: 'numberfield',
	            fieldLabel: 'Phone',
	            name: 'phone',
	            value: storeConstructorObject[0].data.phone,
	            allowBlank: false,
	        }
	    ],
	    buttons: [{
	    	text: 'Edit Data',
	    	formbind: true,
	    	handler: function () {
	    		var inputForm= this.up('form').getForm();
	    		if(inputForm.isValid()) {
	    			Ext.Msg.show({
						    title:'Confirm Edit',
						    message: 'Data Store will be modified. Comfirm your action.',
						    buttons: Ext.Msg.YESNO,
						    icon: Ext.Msg.WARNING,
						    fn: function(btn) {
						        if (btn === 'yes') {
						            userStore.getByInternalId(storeConstructorObject[0].internalId).set(inputForm.getFieldValues());
						            this.up('window').close();
						            Ext.toast('Data Deleted');
						        }
						    }
					});
	    		}
	    	}
        },{
        	text: 'Cancel',
        	handler: function () {
        		this.up('window').close();
        	}
        }]
	});
	
	
	return new Ext.window.Window({
	    title: 'Add Invoice Data',
	    height: screen.height * 0.65,
	    width: screen.width * 0.35,
	    modal: true,
	    layout: 'fit',
	    items: [dataInputForm]
	});
};

deleteStoreElement= function(storeConstructorObject) {
	
	deletedEl= [];
	Ext.Msg.show({
		    title:'Warning',
		    message: `${storeConstructorObject.length} Data will be deleted. Press OK to continue`,
		    buttons: Ext.Msg.OKCANCEL,
		    icon: Ext.Msg.WARNING,
		    fn: function(btn) {
		        if (btn === 'ok') {
		            storeConstructorObject.forEach( d => {
		            	deletedEl.push(d.data.name); 
		            	userStore.remove(d);
		            });
		            
		            Ext.toast('Samosa le '+deletedEl.join(", "));
		        }
		    }
	});
	
};


var addButton= {
        text: 'Add',
        iconCls: 'x-btn-text-icon btn-icon-add',
        tooltip: 'Add',
        itemId: 'addButton',
        handler: function() {
        	addElementsToStore().show();
        }
};

var editButton= {
		text: 'Edit',
        iconCls: 'x-btn-text-icon btn-icon-edit',
        tooltip: 'Edit',
        disabled: true,
        itemId: 'editButton',
        handler: function () {
        	editStoreElement(sampleGrid.getSelectionModel().getSelection()).show()
        }
};

var deleteButton= {
		text: 'Delete',
        iconCls: 'x-btn-text-icon btn-icon-delete',
        tooltip: 'Delete',
        disabled: true,
        itemId: 'deleteButton',
        handler : function () {
        	deleteStoreElement(sampleGrid.getSelectionModel().getSelection())
        }
};

// Grid Toolbar Define and Component Addition

var paginationToolbar= new Ext.PagingToolbar({
        pageSize: 2,
        displayInfo: true,
        displayMsg: 'Invoices {0} - {1} of {2}',
        emptyMsg: "No invoices to display",
        store: userStore,
    	dock: 'top',
});

paginationToolbar.add(11, '-');
paginationToolbar.add(12, resetSort);
paginationToolbar.add(13, '-');
paginationToolbar.add(14, exportSingle);
paginationToolbar.add(15, '-');
paginationToolbar.add(16, exportAll);
paginationToolbar.add(17, '-');
paginationToolbar.add(18, addButton);
paginationToolbar.add(19, '-');
paginationToolbar.add(20, editButton);
paginationToolbar.add(21, '-');
paginationToolbar.add(22, deleteButton);



var sampleGrid = Ext.create('Ext.grid.Panel', {
    store: userStore,
    columns: [
        {
            text: 'Company ID',
            flex: 1,
            dataIndex: 'name'
        },
        {
            text: 'Account Header ID',
            flex: 1,
            dataIndex: 'email',
        },
        {
            text: 'Document Number',
            flex: 1,
            dataIndex: 'phone'
        },
        {
            text: 'Business Code',
            flex: 1,
            dataIndex: 'name'
        },
        {
            text: 'Create User',
            flex: 1,
            dataIndex: 'email',
        },
        {
            text: 'Document Line Number',
            flex: 1,
            dataIndex: 'phone'
        },
        {
            text: 'Document Type',
            flex: 1,
            dataIndex: 'name'
        },
        {
            text: 'Customer Number',
            flex: 1,
            dataIndex: 'email',
        },
        {
            text: 'Customer Map ID',
            flex: 1,
            dataIndex: 'phone'
        },
        {
            text: 'Name of Customer',
            flex: 1,
            dataIndex: 'name'
        },
        {
            text: 'Division',
            flex: 1,
            dataIndex: 'email',
        },
        {
            text: 'Document Create Date',
            flex: 1,
            dataIndex: 'phone'
        },
        {
            text: 'Posting Date',
            flex: 1,
            dataIndex: 'phone'
        }
    ],
    dockedItems: [paginationToolbar],
    selModel: {
    	selType: 'checkboxmodel',
    },
    listeners: {
		scope: this,
		selectionchange: function(){
			var selectedLength= sampleGrid.getSelectionModel().getSelection().length;
			if(selectedLength === 1){
				paginationToolbar.getComponent('editButton').setDisabled(false);
				paginationToolbar.getComponent('deleteButton').setDisabled(false);
			}
			else if(selectedLength >= 1){
				paginationToolbar.getComponent('editButton').setDisabled(true);
				paginationToolbar.getComponent('deleteButton').setDisabled(false);
			}
			else {
				paginationToolbar.getComponent('editButton').setDisabled(true);
				paginationToolbar.getComponent('deleteButton').setDisabled(true);
			}
		}
	}
});