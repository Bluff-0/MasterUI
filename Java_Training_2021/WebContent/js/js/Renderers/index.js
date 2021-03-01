/**
 * 
 */
 
 var mainNavigationBar= new Ext.tab.Panel({
    region: 'center',
    activeTab: 2,
    deferredRender: true,
  	// layoutOnTabChange: true,
  	id: 'topNavigation',
    items: [{
        title: 'Invoice Data Management',
        items: [sampleGrid],
    },{
        title: 'Master Data',
        items: [masterDataTab],
    },{
        title: 'Sakila Trade',
        items: [sakilaTradePanel],
    }],
    listeners: {
    	beforetabchange: function(tabpanel, newtab, oldtab) {
    		// sciptLoader(newtab.config.title)
    	}
    },
});
var mainframeViewportObject = {
    layout: 'border',
    items: [mainNavigationBar]
};


Ext.onReady(function () {
    const mainmenu= Ext.create('Ext.container.Viewport', mainframeViewportObject);
    Ext.QuickTips.init();
    // console.clear();
});