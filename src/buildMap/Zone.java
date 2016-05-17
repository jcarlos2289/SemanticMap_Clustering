package buildMap;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
//import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import hierClustering.*;

public class Zone {
	String name ="";
	ArrayList<Node> areas;
	Node representative;
	HashMap<String, Float> ZoneHistoMean;
	Color zoneColor;
	Cluster clus;
	String nickName;
	
	
	public Zone(String na){
		representative = new Node();
		areas = new ArrayList<Node>();
		ZoneHistoMean = new HashMap<String,Float>();
		name = na;
		zoneColor = Color.white;
	}
	
	/*public Zone (Node no){
		representative = new Node();
		areas = new ArrayList<Node>();
		ZoneHistoMean = new HashMap<String,Float>();
		addNode(no);
		nickName = calcZoneName();
		name ="Cluster";
		
	}*/
	
	private String calcZoneName() {
		// TODO Auto-generated method stub
	  //  ArrayList<String> auxFortags = new ArrayList<String>();
	//	ArrayList<Float> auxFortagsValue = new ArrayList<Float>();
		
		/*for (Node no : areas) {
			auxFortags.addAll(no.getTopXNodes(no.representative.tags.size()));  //cambiar por la dimensionalidad del modelo
			auxFortagsValue.addAll(no.getTopXNodesValues(no.representative.tags.size()));
			}
		
		ConcurrentHashMap<String, Float> probTopTags = new ConcurrentHashMap<String, Float>();
		for (int i = 0; i < auxFortags.size(); i++) {
						
				if(probTopTags.containsKey(auxFortags.get(i)))
					probTopTags.replace(auxFortags.get(i), probTopTags.get(auxFortags.get(i))+ auxFortagsValue.get(i)/Collections.frequency(auxFortags,auxFortags.get(i)));
					else
				     probTopTags.put(auxFortags.get(i), auxFortagsValue.get(i)/Collections.frequency(auxFortags,auxFortags.get(i)));
				
				
		}*/
		
		//-----------------------
		
	/*	ConcurrentHashMap<String, Float> probTopTags = new ConcurrentHashMap<String, Float>();
		
		for (Node no : areas) {
			int div = no.representative.tags.size();
			for(Entry<String, Float> e : no.histoMean.entrySet()){
				
				if(probTopTags.containsKey(e.getKey()))
					probTopTags.replace(e.getKey(), probTopTags.get(e.getKey())+ e.getValue()/div);
					else
				     probTopTags.put(e.getKey(), e.getValue()/div);
			}
			
			}
		
		//------------------------------------
		
		*/
		
		ArrayList<String> maxTag = new ArrayList<>();
       // maxTag = ListMethods.getTopXNodes(5, probTopTags);
        
        
        
        String nick ="";
        
      /*  for(String j : maxTag){
        	nick += j +" ";
        }*/
        
        ConcurrentHashMap<String, Float> gh = new ConcurrentHashMap<String, Float>();
        gh.putAll(representative.histoMean);
        maxTag = ListMethods.getTopXNodes(3, gh);
        
     //    nick +="%%%%%";
        
        for(String j : maxTag){
        	nick += j +" ";
        }
        
        
		return nick.trim();
	}

	public int getSize(){
		return areas.size();
	}
	
	public String getName(){
		return name;
	}
	
	public String toString(){
		String result="";
		
		result = "Zone Category Tags:\t " +this.getNick();
		result +="\nNodes in this zone:\t " + String.valueOf(this.areas.size());
			
		
		return result;
	}
	
	public String toWebString(){
		String text = "<html>\n";
		text +="<h1> Zone Asigned Class: " +this.getNick() +"</h1>";
			text+="<h2>Nodes in this Zone: "+ String.valueOf(this.areas.size())+"</h2>";
			text += "\n</html>";
		return text;
	}
	
	public String getZoneContent(String[] tags){
		String text ="";
		
		text += ";" +this.getName()+";"+this.getNick();
		String[]ram = this.getName().split(" ");
		text += ";" +ram[1];
		text += ";" +String.valueOf(this.areas.size());
		
		ArrayList<String> originalClass = new ArrayList<String>();
		for(ImageTags imt: representative.images){
			String[] x = imt.category.split("-");
			originalClass.add(x[0]);
		}
	    
		  for (String key :tags) 
	    	text += ";" +Collections.frequency(originalClass, key);
		
		
		return text;
	}
	
	
	public void addNode(Node n){
		areas.add(n);
		//calcRepresentative();
		 //  name = calcZoneName();
	}
	
	public void udpateStatus(){
		calcRepresentative();
		nickName = calcZoneName();
		
	}

	private void calcRepresentative() {
		// TODO Auto-generated method stub
		ZoneHistoMean = new HashMap<String, Float>();
		//Node auxNode = new Node();
		representative = new Node();
		
		for (Node no : areas) {
			representative.add(no.representative);
		}
	
		
	}
	
	public String getNick(){
		return this.nickName;
	}
	
	public PieDataset getChartDataset(){
		
		ArrayList<String> originalClass = new ArrayList<String>();
		for(ImageTags imt: representative.images)
			originalClass.add(imt.category);
		
		 Set<String> data = new HashSet<>(originalClass);

		 
		  DefaultPieDataset dataset = new DefaultPieDataset( );
		  for (String key : data) 
	    	 dataset.setValue( key , new Double( Collections.frequency(originalClass, key) ) );
		
		//System.out.println("Tamaño de Dataset "+  dataset.getItemCount());
		
		  
		  
		  return dataset;  
	    
	     
			
	}
	
	public void setCluster(Cluster cs){
		clus = new Cluster("aux");
		this.clus = cs;
	}
	
	public Cluster getCluster(){
		return this.clus;
	}

}
