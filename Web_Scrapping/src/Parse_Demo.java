import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import com.mongodb.Mongo;
import com.mongodb.DB;
import com.mongodb.DBCollection;
//import com.mongodb.MongoClient;
import com.mongodb.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
public class Parse_Demo {
	public static Set<String> uniqueURL = new LinkedHashSet<String>();
	public static Set<String> url = new LinkedHashSet<String>();

	
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Document doc = Jsoup.connect("https://www.firstpost.com/").get();  

		String keywords = doc.select("meta[name=keywords]").attr("content");  
       // System.out.println("Meta keyword : " + keywords);  
        String description = doc.select("meta[name=description]").attr("content");  
        System.out.println("Meta description : " + description); 
		
		Elements links = doc.select("a[href]");  
		System.out.println("Title : "+doc.title());
		for (Element link : links) {  
			if(link.text().equals("sports" ) ) {
		    //System.out.println("\nlink : " + link.attr("href"));  
		   // System.out.println("text : " + link.text());  
		    uniqueURL.add(link.attr("href"));
		    
			}
			else if(link.text().equals("Business")) {
//				 System.out.println("\nlink : " + link.attr("href"));  
//				    System.out.println("text : " + link.text()); 
				uniqueURL.add(link.attr("href"));
			}
			else if(link.text().equals("india")) {
//				System.out.println("\nlink : " + link.attr("href"));  
//			    System.out.println("text : " + link.text());
				uniqueURL.add(link.attr("href"));
			}
			else
				continue;
			//System.out.println("over");
		}  
		 Set<String> linkSet = new LinkedHashSet<String>();
		 Set<String> contentSet = new LinkedHashSet<String>();
		 Set<String> imgSet = new LinkedHashSet<String>();
		for (String set : uniqueURL) {
			System.err.println("Main Url : "+set);
			
			Document newDoc= Jsoup.connect(set).get();
			Elements el_1= newDoc.select("div[class=big-thumb]");
			for(Element e:el_1) {
				contentSet.add(e.text());
			System.out.println("content : "+e.text());
			}
			Elements el_2= newDoc.select("a[href]");
			for(Element link : el_2) {
				linkSet.add(link.attr("href"));
				if(link.text().contains("sports")|link.text().contains("Business")|link.text().contains("India")) {
				System.out.println("Links : "+link.attr("href"));
				}
			}
			
			Elements el_3= newDoc.select("img[src]");
			for(Element img : el_3) {
				imgSet.add(img.attr("src"));
				System.out.println("img-src : " + img.attr("src"));
			}
			
			
		}
//		for(String s0:uniqueURL)
//			{
//			System.out.println("\n main url : "+s0+"\n");
//			//for(int i=0;i<contentSet.size();i++) System.out.println("content : "+contentSet[i]);
//			Iterator<String> iter = contentSet.iterator();
//			while (iter.hasNext()) {
//			    System.out.println("content : "+iter.next());
//			}
//			//for(String s:contentSet) System.out.println("content : "+s);
//			//for(String s1:linkSet) System.out.println("Sub url link : "+s1);
//			}

		Map<String, Set<String>> mainLinkMap = new HashMap<>();
		uniqueURL.forEach(a -> mainLinkMap.computeIfAbsent(a, k -> new HashSet<>()).add(a));
//		for (Entry<String, Set<String>> entry : mainLinkMap.entrySet())  
//            System.out.println("Main links = " + entry.getKey() + 
//                             ", Value = " + entry.getValue());
		
		System.out.println("\n\n");
		
		Map<String, Set<String>> contentMap = new HashMap<>();
		contentSet.forEach(b -> contentMap.computeIfAbsent(b, k -> new HashSet<>()).add(b));
//		for (Entry<String, Set<String>> entry : contentMap.entrySet())  
//            System.out.println("Content = " + entry.getKey() + 
//                             ", Value = " + entry.getValue());
//		
		Map<String, Set<String>> linkMap = new HashMap<>();
		linkSet.forEach(c -> linkMap.computeIfAbsent(c, k -> new HashSet<>()).add(c));
//		for (Entry<String, Set<String>> entry : linkMap.entrySet())  
//            System.out.println("links = " + entry.getKey() + 
//                             ", Value = " + entry.getValue());
		
		Map<String, Set<String>> imgMap = new HashMap<>();
		imgSet.forEach(d ->imgMap.computeIfAbsent(d, k -> new HashSet<>()).add(d));
//		for (Entry<String, Set<String>> entry : imgMap.entrySet())  
//            System.out.println("img-src = " + entry.getKey() + 
//                             ", Value = " + entry.getValue());
		
		
		
		// Mongo insertion :: commenting due to packge mismatch you can run without it and see the results 
		
		/*MongoClient mongo = new MongoClient("localhost", 27017);
        DB db = mongo.getDB("myDb");
        DBCollection collection = db.getCollection("users");
        collection.insert(new BasicDBObject(mainLinkMap));
        collection.insert(new BasicDBObject(contentMap));
        collection.insert(new BasicDBObject(linkMap));
        collection.insert(new BasicDBObject(imgMap));
		
		*/
	}
}
		// just run and see the results:  
	
