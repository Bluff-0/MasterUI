/**
 * 
 */

var sakilaStoreServeletObject= {
    proxy: {
        type: 'ajax', 
        url: '/Java_Training_2021/api/getSakilaData',
        reader: {
            type: 'json',
            rootProperty: 'Data',
            totalProperty: 'Total'
        },
        enablePaging: true,
    },
	listeners: {
        load: function() {
            sakilaComboStore.getProxy().setData(extractDistinctStoreElement(sakilaStore, 'language'));
            sakilaComboStore.load();
        }
    },
    autoLoad: true,
    pageSize: 20,
}

var sakilaStoreStrutsObject= {
    proxy: {
        type: 'ajax', 
        url: '/Struts_WebApp/sakila/getData',
        reader: {
            type: 'json',
            transform: {
            	fn: function(data){
					var finalData = JSON.parse(data);
					return finalData;
				},
				scope: this,
            },
            rootProperty: 'Data',
            totalProperty: 'Total'
        },
        enablePaging: true,
    },
	listeners: {
        load: function() {
            // sakilaComboStore.getProxy().setData(extractDistinctStoreElement(sakilaStore, 'language'));
            // sakilaComboStore.load();
        }
    },
    autoLoad: true,
    pageSize: 20,
    remoteFilter: true,
    remoteSort: true,
}


var sakilaStore = Ext.create('Ext.data.Store', useLocal ? sakilaStoreServeletObject : sakilaStoreStrutsObject);

var sakilaMemoryStore= Ext.create('Ext.data.Store', {
    proxy: {
        type: 'memory',
        enablePaging: true,
    },
    pageSize: 20
});

var sakilaComboStore= Ext.create('Ext.data.Store', {
	extend:'Ext.data.ArrayStore',
    proxy: {
        type: 'memory',
    },
});