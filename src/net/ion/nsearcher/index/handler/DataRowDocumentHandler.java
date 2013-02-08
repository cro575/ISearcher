package net.ion.nsearcher.index.handler;

import java.io.IOException;

import net.ion.nsearcher.common.MyDocument;
import net.ion.nsearcher.common.MyField;
import net.ion.nsearcher.index.event.CollectorEvent;
import net.ion.nsearcher.index.event.DataRowEvent;
import net.ion.nsearcher.index.event.KeyValues;

public class DataRowDocumentHandler implements DocumentHandler {

	public DataRowDocumentHandler() {
	}

	public MyDocument[] makeDocument(CollectorEvent _event) throws IOException {
		if (! (_event instanceof DataRowEvent)) return new MyDocument[0] ;
		
		DataRowEvent event = (DataRowEvent)_event ;
		KeyValues keyValues = event.getKeyValues();
		String[] keyColumns = event.getKeyColumns() ;
		
		String docName = "" ;
		for (String key : keyColumns) {
			docName += keyValues.get(key) + "_" ;
		}
		
		MyDocument doc = MyDocument.newDocument(String.valueOf(event.getEventId())).event(event).name(docName);
		for (String colName : keyValues.getKeySet()) {
			Object value = keyValues.get(colName);
			if (value != null) {
//				MyField myfield = MyField.text(colName, value.toString()) ;
//				if (ArrayUtils.contains(keyColumns, colName)) myfield.setBoost(HEAD_BOOST) ;
				
				doc.add(MyField.unknown(colName, value));
			}
		}
		return new MyDocument[]{doc};
	}


}
