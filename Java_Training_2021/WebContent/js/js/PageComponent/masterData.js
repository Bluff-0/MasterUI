/**
 * 
 */


var customerMasterPaginationToolbar= new Ext.PagingToolbar({
        pageSize: 20,
        displayInfo: true,
        displayMsg: 'Customers {0} - {1} of {2}',
        emptyMsg: "No customer to display",
        store: pagingStoreCustomerMaster,
    	dock: 'bottom',
});

var customerGrid= Ext.create('Ext.grid.Panel', {
    store: pagingStoreCustomerMaster,
	height: screen.height * .35,
	pageSize: 20,
    columns: [
        {
            text: 'ID',
            dataIndex: 'id',
			flex: 1,
        },
		{
            text: 'First Name',
            dataIndex: 'firstname',
			flex: 1,
        },
		{
            text: 'Last Name',
            dataIndex: 'lastname',
			flex: 1,
        },
		{
            text: 'Profession',
            dataIndex: 'profession',
			flex: 1,
        },
        {
            text: 'Email Address',
            dataIndex: 'email',
			flex: 1,
			editor: 'textfield',
        },
        {
            text: 'Phone Number',
            dataIndex: 'phone',
			flex: 1,
			editor: 'textfield',
        },
        {
            text: 'Country',
            dataIndex: 'Country',
			flex: 1,
        }
    ],
	dockedItems: [customerMasterPaginationToolbar, {
		xtype: 'toolbar',
        dock: 'top',
		items: [{
			xtype: 'button',
			text: 'Deffer',
		},{
			xtype: 'combobox',
			emptyText: 'Country',
		    store: pagingStoreCustomerMaster,
		    queryMode: 'local',
		    displayField: 'Country',
		    valueField: 'CC',
			width: 200,
			listeners: {
				select: function (combo, rec) {
					pagingStoreCustomerMaster.clearFilter();
					pagingStoreCustomerMaster.filter(prepareDataForAdvSearch({CC: combo.value}));
				}
			}
		},{
			xtype: 'button',
			text: 'Reset',
			handler: function() {
				this.up('toolbar').down('combobox').resetToInitialValue();
				pagingStoreCustomerMaster.clearFilter();
			}
		}]
	}],
	listeners: {
		selectionchange: function(grid, selection) {
			if(selection.length)
			{
				var row= selection[0];
				var data= `<b>ID${row.data.id}</b><br>`+
				`<br><b>${row.data.firstname} ${row.data.lastname}</b> who is <b>${row.data.profession}</b> lives in <b>${row.data.Country} [${row.data.CC}]</b><br>`+
				`<br>Contact:<br>${row.data.phone}<br>${row.data.email}`
				Ext.getCmp('customerMasterDetails').setHtml(data);
			}
			else
			{
				Ext.getCmp('customerMasterDetails').setHtml("...");
			}
		}
	},
	selType: 'rowmodel',
    plugins: [{
        ptype: 'rowediting',
        clicksToEdit: 2,
    }]
});

var customerMasterAdvSearch= new Ext.form.Panel({
	title: 'Advanced Search',
	height: '100%',
	width: '33.3%',
	padding: 10,
	bodyPadding: 5,
	id: 'customerMasterAdvSearch',
	items:[{
		xtype: 'textfield',
		emptyText: 'ID *',
		name: 'id',
		allowBlank: false,
	},{
		xtype: 'textfield',
		emptyText: 'Name',
		name: 'firstname',
	}],
	buttons: [{
		text: 'Search',
		formBind: true,
		handler: function() {
			var formvalue= this.up('form').getForm().getFieldValues();
			var processedValue= prepareDataForAdvSearch(formvalue);
			processedValue.forEach(d => {
				pagingStoreCustomerMaster.filter(d);
			});
		}
	},{
		text: 'Reset',
		handler: function() {
			this.up('form').getForm().reset();
			pagingStoreCustomerMaster.clearFilter();
		}
	}]
});

var customerMasterPanel= Ext.create('Ext.panel.Panel', {
	bodyStyle: 'padding: 15px',
    items: [customerGrid, {
		xtype: 'panel',
		layout: 'hbox',
		height: screen.height * .35,
		layoutConfig : {
        	align : 'stretch',
    	},
		items: [{
			xtype: 'panel',
			title: 'Model Details',
			height: '100%',
			width: '33.3%',
			style: 'padding: 10px',
			bodyPadding: 5,
			html: 'Currently not using any model',
			id: 'customerMasterModel',
			collapsible: true,
		},{
			xtype: 'panel',
			title: 'Data Details',
			height: '100%',
			width: '33.3%',
			padding: 10,
			bodyPadding: 5,
			html: '...',
			id: 'customerMasterDetails',
		},customerMasterAdvSearch],
		buttons: [{
			text: 'Sample Button',
		}],
	}],
});


var masterDataTab= Ext.create('Ext.TabPanel', {
    activeTab: 0,
    deferredRender: true, 
    layoutOnTabChange: true,
    items: [
        {
            title: 'Customer Master',
            items: [customerMasterPanel],
        },
        {
            title: 'Invoice Master',
            html: 'Invoive Home Screen'
        }
    ]
});