package net.ion.nsearcher.problem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import net.ion.framework.util.Debug;
import net.ion.nsearcher.common.IndexField;
import net.ion.nsearcher.common.SearchConstant;
import net.ion.nsearcher.common.FieldIndexingStrategy.FieldType;
import net.ion.nsearcher.search.analyzer.MyKoreanAnalyzer;
import net.ion.radon.impl.util.CsvReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.debug.standard.DStandardAnalyzer;
import org.apache.lucene.analysis.kr.AnalyzerUtil;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;

import junit.framework.TestCase;

public class TestJava extends TestCase {

	public void testStringBuilder() throws Exception {
		File file = new File("C:/temp/freebase-datadump-tsv/data/medicine/drug_label_section.tsv");

		CsvReader reader = new CsvReader(new BufferedReader(new FileReader(file)));
		reader.setFieldDelimiter('\t');
		String[] headers = reader.readLine();
		String[] line = reader.readLine();
		int max = 100000;
		Analyzer analyzer = new MyKoreanAnalyzer(SearchConstant.LuceneVersion);
		int sum = 0 ;
		while (line != null && line.length > 0 && max-- > 0) {
			StringBuilder sb = new StringBuilder();
			for (int ii = 0, last = headers.length; ii < last; ii++) {
				sb.append(line[ii] + " ");
			}
			String[] tokens = AnalyzerUtil.toToken(analyzer, sb.toString()) ;
			sum += tokens.length ;
			line = reader.readLine() ;
		}

	}
}
