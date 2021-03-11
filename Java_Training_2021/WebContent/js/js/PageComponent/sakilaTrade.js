/**
 * 
 */

addElementsToSakilaStore= function() {
	
	var dataInputForm= new Ext.form.Panel({
	    defaultType: 'textfield',
	    buttonAlign: 'center',
	    items: [
	        {
	            fieldLabel: 'Title',
	            name: 'title',
	            allowBlank: false,
	        },
	        {
	        	xtype: 'numberfield',
	            fieldLabel: 'Release Year',
	            name: 'release_year',
	            allowBlank: false,
	        },
	        {
	        	xtype: 'combobox',
	            fieldLabel: 'Special Features',
	            name: 'special_features',
	            allowBlank: false,
	            store: new Ext.data.Store({
	            	proxy: {
				        type: 'ajax', 
				        url: '/Java_Training_2021/api/getComboboxData?db=sakila&table=film&column=special_features&type=set',
				        reader: {
				            type: 'json',
				            transform: raw => raw.map(v => ({ special_features: v }))
				        },
				    },
			        fields: ['special_features'],
			    }),
			    valueField: 'special_features',
    			displayField: 'special_features',
    			triggerAction: 'all',
    			multiSelect: true,
    			forceSlection: true,
    			listeners: {
    				change: function (me, newVal, oldVal) {
    					me.setValue(newVal.toString());
    				}
    			}
	        },
	        {
	        	xtype: 'combobox',
	            fieldLabel: 'Rating',
	            name: 'rating',
	            allowBlank: false,
	            store: new Ext.data.Store({
	            	proxy: {
				        type: 'ajax', 
				        url: '/Java_Training_2021/api/getComboboxData?db=sakila&table=film&column=rating&type=enum',
				        reader: {
				            type: 'json',
				            transform: raw => raw.map(v => ({ rating: v }))
				        },
				    },
			        fields: ['rating'],
			    }),
			    valueField: 'rating',
    			displayField: 'rating',
    			triggerAction: 'all',
	        },
	        {
	        	xtype: 'combobox',
	            fieldLabel: 'Language',
	            name: 'language',
	            allowBlank: false,
	            store: new Ext.data.Store({
	            	proxy: {
				        type: 'ajax', 
				        url: '/Java_Training_2021/api/getComboboxData?db=sakila&table=language&column=name&type=table',
				        reader: {
				            type: 'json',
				            transform: raw => raw.map(v => ({ language: v }))
				        },
				    },
			        fields: ['language'],
			    }),
			    valueField: 'language',
    			displayField: 'language',
    			triggerAction: 'all',
	        },
	        {
	            fieldLabel: 'Director Name',
	            name: 'director',
	            allowBlank: true,
	        },
	        {
	        	xtype: 'textareafield',
	            fieldLabel: 'Description',
	            name: 'description',
	            allowBlank: false,
	            anchor: '100%'
	        },
	    ],
	    buttons: [{
	    	text: 'Save',
	    	formBind: true,
	    	handler: function () {
	    		var inputForm= this.up('form').getForm();
	    		if(inputForm.isValid()) {
	    			var formValues= inputForm.getFieldValues();
	    			formValues['qType']= 'insert';
	    			Ext.Ajax.request({
			            //url: '/Java_Training_2021/api/sakilaModification',
			            //url: '/Struts_WebApp/sakila/modifyData.action',
			            url: '/Spring_WebApp/sakila_spring/modifyData.action',
			            method: 'GET',
			            timeout: 60000,
			            params: formValues,
			            headers:
			            {
			                'Content-Type': 'application/json'
			            },
			            success: function (response) {
			            	Ext.toast("Yassssss");
			            	sakilaStore.load();
			            },
			            failure: function (response) {
			                Ext.toast("Oh No! Oh No!");
			
			            }
			        });
	    		}
	    		this.up('window').close();
	    	}
        },{
        	text: 'Cancel',
        	handler: function () {
        		this.up('window').close();
        	}
        }]
	});
	
	
	return new Ext.window.Window({
	    title: 'Add Film',
	    width: screen.width * 0.35,
	    modal: true,
	    layout: 'fit',
	    items: [dataInputForm]
	});
}



editElementsToSakilaStore= function(storeConstructorObject) {
	var dataInputForm= new Ext.form.Panel({
	    defaultType: 'textfield',
	    buttonAlign: 'center',
	    items: [
	        {
	            fieldLabel: 'Title',
	            name: 'title',
	            value: storeConstructorObject[0].data.title,
	            allowBlank: false,
	        },
	        {
	        	xtype: 'numberfield',
	            fieldLabel: 'Release Year',
	            name: 'release_year',
	            value: storeConstructorObject[0].data.release_year,
	            allowBlank: false,
	        },
	        {
	        	xtype: 'combobox',
	            fieldLabel: 'Special Features',
	            name: 'special_features',
	            value: storeConstructorObject[0].data.special_features.split(","),
	            allowBlank: false,
	            store: new Ext.data.Store({
	            	proxy: {
				        type: 'ajax', 
				        url: '/Java_Training_2021/api/getComboboxData?db=sakila&table=film&column=special_features&type=set',
				        reader: {
				            type: 'json',
				            transform: raw => raw.map(v => ({ special_features: v }))
				        },
				    },
			        fields: ['special_features'],
			    }),
			    valueField: 'special_features',
    			displayField: 'special_features',
    			triggerAction: 'all',
    			multiSelect: true,
    			forceSlection: true,
    			listeners: {
    				change: function (me, newVal, oldVal) {
    					me.setValue(newVal.toString());
    				}
    			}
	        },
	        {
	        	xtype: 'combobox',
	            fieldLabel: 'Rating',
	            name: 'rating',
	            value: storeConstructorObject[0].data.rating,
	            allowBlank: false,
	            store: new Ext.data.Store({
	            	proxy: {
				        type: 'ajax', 
				        url: '/Java_Training_2021/api/getComboboxData?db=sakila&table=film&column=rating&type=enum',
				        reader: {
				            type: 'json',
				            transform: raw => raw.map(v => ({ rating: v }))
				        },
				    },
			        fields: ['rating'],
			    }),
			    valueField: 'rating',
    			displayField: 'rating',
    			triggerAction: 'all',
	        },
	        {
	        	xtype: 'combobox',
	            fieldLabel: 'Language',
	            name: 'language',
	            value: storeConstructorObject[0].data.language,
	            allowBlank: false,
	            store: new Ext.data.Store({
	            	proxy: {
				        type: 'ajax', 
				        url: '/Java_Training_2021/api/getComboboxData?db=sakila&table=language&column=name&type=table',
				        reader: {
				            type: 'json',
				            transform: raw => raw.map(v => ({ language: v }))
				        },
				    },
			        fields: ['language'],
			    }),
			    valueField: 'language',
    			displayField: 'language',
    			triggerAction: 'all',
	        },
	        {
	            fieldLabel: 'Director Name',
	            name: 'director',
	            value: storeConstructorObject[0].data.director,
	            allowBlank: true,
	        },
	        {
	        	xtype: 'textareafield',
	            fieldLabel: 'Description',
	            name: 'description',
	            value: storeConstructorObject[0].data.description,
	            allowBlank: false,
	            anchor: '100%'
	        },
	        {
		        xtype: 'hiddenfield',
		        name: 'film_id',
		        value: storeConstructorObject[0].data.film_id,
		    }
	    ],
	    buttons: [{
	    	text: 'Save',
	    	formBind: true,
	    	handler: function () {
	    		var inputForm= this.up('form').getForm();
	    		if(inputForm.isValid()) {
	    			var formValues= inputForm.getFieldValues();
	    			formValues['qType']= 'update';
	    			
	    			Ext.Ajax.request({
			            // url: '/Java_Training_2021/api/sakilaModification',
			            // url: '/Struts_WebApp/sakila/modifyData.action',
			            url: '/Spring_WebApp/sakila_spring/modifyData.action',
			            method: 'GET',
			            timeout: 60000,
			            params: formValues,
			            headers:
			            {
			                'Content-Type': 'application/json'
			            },
			            success: function (response) {
			            	Ext.toast("Yassssss");
			            	sakilaStore.load();
			            },
			            failure: function (response) {
			                Ext.toast("Oh No! Oh No!");
			
			            }
			        });
	    		}
	    		this.up('window').close();
	    	}
        },{
        	text: 'Cancel',
        	handler: function () {
        		this.up('window').close();
        	}
        }]
	});
	
	
	return new Ext.window.Window({
	    title: 'Edit Film',
	    width: screen.width * 0.35,
	    modal: true,
	    layout: 'fit',
	    items: [dataInputForm]
	});
}


deleteElementsFromSakilaStore = function (storeConstructorObject) {
	var rowSelection= storeConstructorObject;
	var idArray=[];
	rowSelection.forEach(d => idArray.push(d.data.film_id));
	idArray= idArray.toString();
	
	var transmitObj= {
		film_id: idArray,
		qType: 'delete',
		soft: 0,
	}
	Ext.Ajax.request({
        // url: '/Java_Training_2021/api/sakilaModification',
        // url: '/Struts_WebApp/sakila/modifyData.action',
        url: '/Spring_WebApp/sakila_spring/modifyData.action',
        method: 'GET',
        timeout: 60000,
        params: transmitObj,
        headers:
        {
            'Content-Type': 'application/json'
        },
        success: function (response) {
        	Ext.toast(`${idArray} Data deleted Successfully`);
        	sakilaStore.load();
        },
        failure: function (response) {
            Ext.toast("Could not perform the operation. Try again later.");

        }
    });
}


softDeleteElementsFromSakilaStore = function (storeConstructorObject) {
	var rowSelection= storeConstructorObject;
	var idArray=[];
	rowSelection.forEach(d => idArray.push(d.data.film_id));
	idArray= idArray.toString();
	
	var transmitObj= {
		film_id: idArray,
		qType: 'delete',
		soft: 1,
	}
	Ext.Ajax.request({
        // url: '/Struts_WebApp/sakila/modifyData.action',
        url: '/Spring_WebApp/sakila_spring/modifyData.action',
        method: 'GET',
        timeout: 60000,
        params: transmitObj,
        headers:
        {
            'Content-Type': 'application/json'
        },
        success: function (response) {
        	Ext.toast(`${idArray} Data deleted Successfully`);
        	sakilaStore.load();
        },
        failure: function (response) {
            Ext.toast("Could not perform the operation. Try again later.");

        }
    });
}



var sakilaMovieAdvanceSearch= new Ext.form.Panel({
	title: 'Movie Advance Search',
	cls: 'sakila-movieAdvanceSearch',
	layout: 'hbox',
	buttonAlign: 'center',
	items: [{
		xtype: 'panel',
		bodyPadding: 20,
		width: '50%',
		items: [
			{
				xtype: 'textfield',
	            fieldLabel: 'Movie Name',
	            name: 'title',
	        }, {
				xtype: 'datefield',
	            fieldLabel: 'Release Year',
	            name: 'release_year',
	        }
		],
	},{
		xtype: 'panel',
		bodyPadding: 20,
		width: '50%',
		items: [
			{
				xtype: 'textfield',
	            fieldLabel: 'Director Name',
	            name: 'director',
	        }, {
				xtype: 'combobox',
	            fieldLabel: 'Language',
	            name: 'language',
				store: useLocal? sakilaComboStore : (
					new Ext.data.Store({
		            	proxy: {
					        type: 'ajax', 
					        url: '/Java_Training_2021/api/getComboboxData?db=sakila&table=language&column=name&type=table',
					        reader: {
					            type: 'json',
					            transform: raw => raw.map(v => ({ key: v }))
					        },
					    },
				        fields: ['key'],
				    })
				),
			    displayField: 'key',
			    valueField: 'key',
	        }
		],
	}],
	buttons: [{
		text: 'Search',
		iconCls: 'btn-icon-errecast icon-alternate',
		handler: function() {
			var formvalue= this.up('form').getForm().getFieldValues();
			formvalue.release_year= extractYearFromDatefield(formvalue.release_year);
			var processedValue= prepareDataForAdvSearch(formvalue);
			processedValue.forEach(d => {
				sakilaStore.filter(d);
			});
		}
	},{
		text: 'Reset',
		iconCls: 'btn-icon-recycle icon-alternate',
		handler: function() {
			this.up('form').getForm().reset();
			sakilaStore.clearFilter();
		}
	}]
});

var resetSortSakilaData= {
		text: '',
        iconCls: 'x-btn-text-icon btn-icon-resetSort',
        tooltip: 'Reset Sort',
        itemId: 'resetSortButton',
        handler: function() {
        	sakilaStore.sorters.clear();
    		sakilaStore.load();
        }
}

var addSakilaData= {
        text: 'Add',
        iconCls: 'x-btn-text-icon btn-icon-add',
        tooltip: 'Add',
        itemId: 'addButton',
        handler: function() {
        	addElementsToSakilaStore().show();
        }
};

var editSakilaData= {
		text: 'Edit',
        iconCls: 'x-btn-text-icon btn-icon-edit',
        tooltip: 'Edit',
        disabled: true,
        itemId: 'editButton',
        handler: function () {
        	editElementsToSakilaStore(sakilaMovieGrid.getSelectionModel().getSelection()).show();
        }
};

var deleteSakilaData= {
		text: 'Delete',
        iconCls: 'x-btn-text-icon btn-icon-bomb',
        tooltip: 'Delete',
        disabled: true,
        itemId: 'deleteButton',
        handler : function () {
        	deleteElementsFromSakilaStore(sakilaMovieGrid.getSelectionModel().getSelection());
        }
};

var softDeleteSakilaData= {
		text: 'Soft Delete',
        iconCls: 'x-btn-text-icon btn-icon-delete',
        tooltip: 'Soft Delete',
        disabled: true,
        itemId: 'softDeleteButton',
        handler : function () {
        	softDeleteElementsFromSakilaStore(sakilaMovieGrid.getSelectionModel().getSelection());
        }
};

var sakilaTradePaginationToolbar= new Ext.PagingToolbar({
        pageSize: 20,
        displayInfo: true,
        displayMsg: 'Movies {0} - {1} of {2}',
        emptyMsg: "No movie to display",
        store: sakilaStore,
    	dock: 'top',
    	items: ['-',resetSortSakilaData,'-',addSakilaData,'-',editSakilaData,'-',softDeleteSakilaData,'-',deleteSakilaData],
});


var sakilaMovieGrid= new Ext.grid.Panel({
	title: 'Movie Grid',
	store: sakilaStore,
	height: screen.height * .45,
	columns: [
        {
            text: 'Title',
            flex: 1.5,
			dataIndex: 'title',
        },
        {
            text: 'Description',
            flex: 3,
			dataIndex: 'description',
        },
        {
            text: 'Release Year',
            flex: 1,
			dataIndex: 'release_year',
        },
        {
            text: 'Language',
            flex: 1,
			dataIndex: 'language',
        },
        {
            text: 'Director',
            flex: 1,
			dataIndex: 'director',
        },
        {
            text: 'Rating',
            flex: 1,
			dataIndex: 'rating',
        },
        {
            text: 'Special Feature',
            flex: 1,
			dataIndex: 'special_features',
        }
    ],
	selModel: {
    	selType: 'checkboxmodel',
    },
    dockedItems: [sakilaTradePaginationToolbar],
    listeners: {
		scope: this,
		selectionchange: function(){
			var selectedLength= sakilaMovieGrid.getSelectionModel().getSelection().length;
			if(selectedLength === 1){
				sakilaTradePaginationToolbar.getComponent('editButton').setDisabled(false);
				sakilaTradePaginationToolbar.getComponent('deleteButton').setDisabled(false);
				sakilaTradePaginationToolbar.getComponent('softDeleteButton').setDisabled(false);
			}
			else if(selectedLength >= 1){
				sakilaTradePaginationToolbar.getComponent('editButton').setDisabled(true);
				sakilaTradePaginationToolbar.getComponent('deleteButton').setDisabled(false);
				sakilaTradePaginationToolbar.getComponent('softDeleteButton').setDisabled(false);
			}
			else {
				sakilaTradePaginationToolbar.getComponent('editButton').setDisabled(true);
				sakilaTradePaginationToolbar.getComponent('deleteButton').setDisabled(true);
				sakilaTradePaginationToolbar.getComponent('softDeleteButton').setDisabled(true);
			}
		}
	}
});

var sakilaTradePanel= Ext.create('Ext.panel.Panel', {
	bodyStyle: 'padding: 15px',
    items: [sakilaMovieAdvanceSearch, sakilaMovieGrid],
});
