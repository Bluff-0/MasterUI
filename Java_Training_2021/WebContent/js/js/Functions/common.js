/**
 * 
 */
function JSONtoCSV(items) {
	var replacer = (key, value) => value === null ? '' : value;
	var header = Object.keys(items[0]);
	var csv = [
	  header.join(','),
	  ...items.map(row => header.map(fieldName => JSON.stringify(row[fieldName], replacer)).join(','))
	].join('\r\n')
	
	return csv;
}

function downloadCSV(csvStr, filename="data.csv") {
	var blob = new Blob([csvStr], { type: 'text/csv;charset=utf-8;' });
    if (navigator.msSaveBlob) 
    {
        navigator.msSaveBlob(blob, filename);
    }
    else
    {
        var link = document.createElement("a");
        if (link.download !== undefined) 
        {
            var url = URL.createObjectURL(blob);
            link.setAttribute("href", url);
            link.setAttribute("download", filename);
            link.style.visibility = 'hidden';
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
        }
    }
}

function prepareDataForAdvSearch(searchObj) {
	var retObj= [];
	var allKeys= Object.keys(searchObj)
	allKeys.forEach(d => {
		if(searchObj[d] != null && searchObj[d].length != 0)
		{
			var temp={};
			temp['property']= d;
			temp['value']= searchObj[d];
			temp['exactMatch']= false;
	  		temp['caseSensitive']= false;
	  		retObj.push(temp);
		}
	});
	return retObj;
}



extractDistinctStoreElement= (store, key) => {
	var keyCollection= [];
	var tempValidator= [];
	
	store.each((rec,index) => {
		if(tempValidator.indexOf(rec.data[key]) === -1)
		{
			keyCollection.push({key: rec.data[key]});
			tempValidator.push(rec.data[key]);
		}
	});
	
	return keyCollection;
};

extractYearFromDatefield= (date) => date != null ? new Date(date).getFullYear(): null;