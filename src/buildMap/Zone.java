package buildMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

public class Zone {
	String name ="";
	ArrayList<Node> areas;
	Node representative;
	HashMap<String, Float> ZoneHistoMean;
	
	public Zone(String na){
		representative = new Node();
		areas = new ArrayList<Node>();
		ZoneHistoMean = new HashMap<String,Float>();
		name = na;
	}
	
	public Zone (Node no){
		representative = new Node();
		areas = new ArrayList<Node>();
		ZoneHistoMean = new HashMap<String,Float>();
		addNode(no);
		name = calcZoneName();
	}
	
	private String calcZoneName() {
		// TODO Auto-generated method stub
	    ArrayList<String> auxFortags = new ArrayList<String>();
		ArrayList<Float> auxFortagsValue = new ArrayList<Float>();
		
		for (Node no : areas) {
			auxFortags.addAll(no.getTopXNodes(3));
			auxFortagsValue.addAll(no.getTopXNodesValues(3));
		}
		
				
		ConcurrentHashMap<String, Float> probTopTags = new ConcurrentHashMap<String, Float>();
		for (int i = 0; i < auxFortags.size(); i++) {
						
				if(probTopTags.containsKey(auxFortags.get(i)))
					probTopTags.replace(auxFortags.get(i), probTopTags.get(auxFortags.get(i))+ auxFortagsValue.get(i)/Collections.frequency(auxFortags,auxFortags.get(i)));
					else
				     probTopTags.put(auxFortags.get(i), auxFortagsValue.get(i)/Collections.frequency(auxFortags,auxFortags.get(i)));
				
				
		}
		ArrayList<String> maxTag = new ArrayList<>();
        maxTag = ListMethods.getTopXNodes(1, probTopTags);
		
		return maxTag.get(0);
	}

	public int getSize(){
		return areas.size();
	}
	
	public String getName(){
		return name;
	}
	
	public String toString(){
		String result="";
		
		result = "Zone Category Tag:\t " +this.getName();
		result +="\nNodes in this zone:\t " + String.valueOf(this.areas.size());
			
		
		return result;
	}
	
	public String toWebString(){
		String text = "<html>\n";
		text +="<h1> Zone Asigned Class: " +this.getName() +"</h1>";
			text+="<h2>Nodes in this Zone: "+ String.valueOf(this.areas.size())+"</h2>";
			text += "\n</html>";
		return text;
	}
	
	
	public void addNode(Node n){
		areas.add(n);
		//calcRepresentative();
		 //  name = calcZoneName();
	}
	
	public void udpateStatus(){
		calcRepresentative();
		   name = calcZoneName();
	}

	private void calcRepresentative() {
		// TODO Auto-generated method stub
		ZoneHistoMean = new HashMap<String, Float>();
		//Node auxNode = new Node();
		representative = new Node();
		
		for (Node no : areas) {
			representative.add(no.representative);
			
			
			
			
		/*	for (Entry<String, Float> g: no.histoMean.entrySet()){
				
				//System.out.println(g.getKey());
				
				if(ZoneHistoMean.containsKey(g.getKey()))
					ZoneHistoMean.put(g.getKey(),   ( ZoneHistoMean.get(g.getKey()) + g.getValue()/areas.size()));
				else
				 ZoneHistoMean.put(g.getKey(),g.getValue()/areas.size()); //g.getValue()/areas.size() g.getKey()
				
			}*/
				
		}
		//auxNode.histoMean= ZoneHistoMean;
		//representative = auxNode;
		
	}
	
	public PieDataset getChartDataset(){
		
		ArrayList<String> originalClass = new ArrayList<String>();
		
		for(ImageTags imt: representative.images)
			originalClass.add(imt.category);
		
		 Set<String> data = new HashSet<>(originalClass);

	
	     DefaultPieDataset dataset = new DefaultPieDataset( );
		  for (String key : data) 
	    	 dataset.setValue( key , new Double( Collections.frequency(originalClass, key) ) );
		
		  
		  return dataset;  
	    
	     
			
	}

}
