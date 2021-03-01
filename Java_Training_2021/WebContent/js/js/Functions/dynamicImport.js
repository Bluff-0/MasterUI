/**
 * 
 */

const useLocal= window.confirm("Disabling Dynamic Struts Behavior, in 3...2...1...");


document.createElement('additive-elements');

var jsFilePathObject= {
	common: {
		files: {
			common: './js/js/Functions/common.js',
		}
	},
	'Invoice Data Management': {
		itemname: 'sampleGrid',
		files: {
			store: './js/js/Stores/dataStore.js',
			components: './js/js/PageComponent/invoiceDataManagement.js',
		},
	},
	'Master Data': {
		itemname: 'masterDataTab',
		files: {
			store: './js/js/Stores/dataStore.js',
			components: './js/js/PageComponent/masterData.js',
		},
	},
	'Sakila Trade': {
		itemname: 'sakilaTradePanel',
		files: {
			store: './js/js/Stores/sakilaStore.js',
			components: './js/js/PageComponent/sakilaTrade.js',
		},
	},
};



scriptCreator= (filepath) => {
	let script = document.createElement('script');
	script.src = filepath;
	script.type= "text/javascript";
	return script;
}

scriptLoader= (tabname) => {
	let filepaths= Object.values(jsFilePathObject[tabname].files);
	filepaths.forEach( path => {
		let newScript= scriptCreator(path);
		document.querySelector('additive-elements').appendChild(newScript);
	});
};

renderModules = (me) => {
	document.querySelector('additive-elements').innerHTML='';
	scriptLoader(me.activeTab.title);
	let activeRender= jsFilePathObject[me.activeTab.title];
	me.activeTab.remove(eval(activeRender.itemname));
	me.activeTab.add(eval(activeRender.itemname));
	me.activeTab.update();
}