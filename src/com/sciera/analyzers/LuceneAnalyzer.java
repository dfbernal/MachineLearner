package com.sciera.analyzers;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Explanation;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;


public class LuceneAnalyzer 
{
	private Analyzer analyzer;
	private Directory index;
	private String file;
	
	public LuceneAnalyzer(String file) throws IOException
	{
		this.file = file;
		analyzer = new StandardAnalyzer(Version.LUCENE_36);
		
		// Create Index
		index = new RAMDirectory();
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_36, analyzer);
		
		IndexWriter w = new IndexWriter(index, config);
	    
	    FileInputStream fstream = new FileInputStream(file);
	    
		// Get the object of DataInputStream
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
	    
	    String strLine;
	    //Read File Line By Line
	    while ((strLine = br.readLine()) != null)   {
	    	addDoc(w, strLine);
	    }
	    w.close();
	    in.close();
	}
	
	public String getFile()
	{
		return file;
	}
	
	private void addDoc(IndexWriter w, String value) throws IOException
	{
		Document doc = new Document();
		doc.add(new Field("title", value, Field.Store.YES, Field.Index.ANALYZED));
		w.addDocument(doc);
	}
	
	public int analyze(String input) throws IOException, ParseException
	{
		String querystr = input;
		BooleanQuery bq = new BooleanQuery();
		Query q = new QueryParser(Version.LUCENE_36, "title", analyzer).parse(querystr);
		bq.add(q, BooleanClause.Occur.MUST);
		
		int hitsPerPage = 10;
		IndexReader reader = IndexReader.open(index);
		IndexSearcher searcher = new IndexSearcher(reader);
		TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);
		searcher.search(bq, collector);
		ScoreDoc[] hits = collector.topDocs().scoreDocs;
		
//		System.out.println("Found " + hits.length + " hits.");
//		for(int i=0;i<hits.length;++i) {
//		    int docId = hits[i].doc;
//		    Document d = searcher.doc(docId);
//		    System.out.println((i + 1) + ". " + d.get("title"));
//		}
		
		searcher.close();
		
		return hits.length;
	}
	
	public String[] explain(String input) throws ParseException, IOException
	{
		String querystr = input;
		BooleanQuery bq = new BooleanQuery();
		Query q = new QueryParser(Version.LUCENE_36, "title", analyzer).parse(querystr);
		bq.add(q, BooleanClause.Occur.MUST);
		
		IndexReader reader = IndexReader.open(index);
		IndexSearcher searcher = new IndexSearcher(reader);
		TopDocs topDocs = searcher.search(bq, 10);
		
		for (int i = 0; i < topDocs.totalHits; i++) 
		{
	        ScoreDoc match = topDocs.scoreDocs[i];
	        Explanation explanation = searcher.explain(bq, match.doc);   
	        System.out.println("----------");
	        Document doc = searcher.doc(match.doc);
	        System.out.println(doc.get("title"));
	        System.out.println(explanation.toString());
	    }
		
		searcher.close();
		
		return null;
	}
}
