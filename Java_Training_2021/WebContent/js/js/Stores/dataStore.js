/** Data Stores are defined here
 * Invoice Data Store
 */

Ext.define('User', {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'name',  type: 'string'},
        {name: 'email',   type: 'string'},
        {name: 'phone', type: 'string'},
    ],

    changeName: function() {
        var oldName = this.get('name'),
            newName = oldName + " The Barbarian";

        this.set('name', newName);
    }
});

// Data Store creation to populate data dynamically

var userStore = Ext.create('Ext.data.Store', {
    data: [
    	{ name: 'Lisa', email: 'lisa@simpsons.com', phone: '555-111-1224' },
        { name: 'Bart', email: 'bart@simpsons.com', phone: '555-222-1234' },
        { name: 'Homer', email: 'homer@simpsons.com', phone: '555-222-1244' },
        { name: 'Marge', email: 'marge@simpsons.com', phone: '555-222-1254' }
    ]
});

var customerMasterStore = Ext.create('Ext.data.Store', {
    proxy: {
        type: 'ajax', 
        url: './js/Data/placeholder.json',
        reader: 'json',
    },
    listeners: {
        load: function() {
            pagingStoreCustomerMaster.getProxy().setData(customerMasterStore.getRange());
            pagingStoreCustomerMaster.load();
        }
    },
    autoLoad: true,
});

var pagingStoreCustomerMaster= Ext.create('Ext.data.Store', {
    proxy: {
        type: 'memory',
        enablePaging: true,
    },
    pageSize: 20
});