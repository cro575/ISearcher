package net.ion.nsearcher.index;

import java.io.File;
import java.io.IOException;
import java.util.List;

import net.ion.framework.util.Debug;
import net.ion.nsearcher.ISTestCase;
import net.ion.nsearcher.common.MyField;
import net.ion.nsearcher.common.ReadDocument;
import net.ion.nsearcher.common.WriteDocument;
import net.ion.nsearcher.config.Central;
import net.ion.nsearcher.config.CentralConfig;
import net.ion.nsearcher.index.collect.FileCollector;
import net.ion.nsearcher.search.SearchResponse;
import net.ion.nsearcher.search.Searcher;
import net.ion.nsearcher.search.processor.StdOutProcessor;

public class TestIndexer extends ISTestCase {

	File file = getTestDir("/ion_page");

	public void testCreate() throws Exception {
		Central central = writeDocument() ;
		
		FileCollector col = new FileCollector(file, true);

		NonBlockingListener adapterListener = getNonBlockingListener(central.newIndexer());
		col.addListener(adapterListener) ;
		// col.addListener(new DefaultReportor()) ;
		
		col.collect() ;
		adapterListener.waitForCompleted() ;
		central.destroySelf() ;
	}

	public void testAfterClose() throws Exception {
		Central c = writeDocument() ;
		Searcher s1 = c.newSearcher() ;
		s1.addPostListener(new StdOutProcessor()) ;
		
		c.newIndexer().index(new IndexJob<Void>() {
			public Void handle(IndexSession session) throws IOException {
				return null;
			}
		}) ;
		
		c.newSearcher() ; // new Searcher..
		SearchResponse sr = s1.search("bleujin") ;
		
		List<ReadDocument> docs = sr.getDocument() ;
		Debug.debug(docs) ;
		
	}
	
	public void testOppsCommit() throws Exception {
		Central c = CentralConfig.newRam().build() ;
		
		Indexer indexer = c.newIndexer() ;
		indexer.index(new IndexJob<Void>() {
			public Void handle(IndexSession isession) throws IOException {
				WriteDocument doc = isession.newDocument();
				isession.insertDocument(doc.add(MyField.keyword("name", "bleujin"))) ;
				return null;
			}
		}) ;
	}



}
