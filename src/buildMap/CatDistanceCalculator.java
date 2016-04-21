package buildMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class CatDistanceCalculator {
	ArrayList<ImageTags> sequence;
	ArrayList<Node> categoryMap;
	double [][] distanceMatrix;
	
	
	public CatDistanceCalculator(ArrayList<ImageTags> seq){
		sequence = seq;
	}
	
	public void createMap(){
		
		categoryMap = new ArrayList<Node>();
		Node auxNode = new Node();
		ArrayList<String> categoryList = new ArrayList<String>();
		
		//auxNode.add(sequence.get(0));
		//auxNode.nodeName = sequence.get(0).category;
		
		//categoryMap.add(auxNode);
		
		for (ImageTags imgTag : sequence) {
			categoryList.add(imgTag.category);
		}
		
		HashSet<String> hashSet = new HashSet<String>(categoryList);
		categoryList.clear();
		categoryList.addAll(hashSet);
		
		Collections.sort(categoryList);
		
		for (String cat :categoryList) {
			auxNode = new Node();
			auxNode.nodeName =cat;
			categoryMap.add(auxNode);
		}
		
		for (ImageTags imgTag : sequence) {
			auxNode = getNodeByName(imgTag.category);
			auxNode.add(imgTag);
		}
		
		generateDistanceMatrix();
						
	}
	
	private Node getNodeByName(String name){
		//Node y = new Node(true, weights);
		Node y = new Node();
		for(Node x: categoryMap){
			if(x.nodeName.equals(name)){
				y = x;
				break;
			}
		}
		return y;
	}
	
	public void print (){

		for(Node n: categoryMap){
			System.out.printf("Node Name: %S\t\tSize: %d\n", n.nodeName, n.images.size());
		}
	}
	

public void generateDistanceMatrix(){
		
		distanceMatrix = new double[categoryMap.size()][categoryMap.size()];
		for (int i = 0; i < categoryMap.size(); i++) {
			for (int j = 0; j < categoryMap.size(); j++) {
				distanceMatrix[i][j] = -1.0f;
			}
		}
		
		for (int i = 0; i < categoryMap.size(); i++) {
			distanceMatrix[i][i]=0;
		}
		
				
		int n1=0;
		//Para Usar los Nodos del Mapa que se esta vizualizando
		for (Node fs : categoryMap) {
		
	
				for(int n2 = n1+1; n2 <categoryMap.size();n2++){
			
				//	distanceMatrix[n1][n2] = distanceOfNodes_KS(fs, categoryMap.get(n2));
					//distanceMatrix[n2][n1] = distanceMatrix[n1][n2];
					//distanceMatrix[n1][n2] = 
					distanceMatrix[n2][n1] = distanceOfNodes_KS(fs, categoryMap.get(n2));
				}
	
			n1++;
		}
		
	}


private float distanceOfNodes_KS(Node node1, Node node2){

	Set<String> hs1;
	Iterator<String> iterator;
		
	hs1 = node1.histoMean.keySet();
	iterator = hs1.iterator();

	
	ArrayList<String> tagList = new ArrayList<String>();
	while(iterator.hasNext()){
		tagList.add(iterator.next());
	}
	
	Collections.sort(tagList);
	
	float[]cumulatedN1 = new float[tagList.size()];
	float[]cumulatedN2 = new float[tagList.size()];
	float[]distanceKS = new float[tagList.size()];
	
	cumulatedN1[0] = node1.histoMean.get(tagList.get(0));
	cumulatedN2[0] = node2.histoMean.get(tagList.get(0));
	distanceKS[0] = Math.abs(cumulatedN2[0]-cumulatedN1[0]);
	
	
	for (int i = 1; i < tagList.size(); i++) {
		cumulatedN1[i] = cumulatedN1[i-1]+ node1.histoMean.get(tagList.get(i));
		cumulatedN2[i] =  cumulatedN2[i-1]+ node2.histoMean.get(tagList.get(i));
		distanceKS[i] = Math.abs(cumulatedN2[i]-cumulatedN1[i]);
	}
	
	ArrayList<Float> tagDmax = new ArrayList<Float>();
	for(float s: distanceKS)
		tagDmax.add(s);
	
	Collections.sort(tagDmax);
	
	
	
	return (float) tagDmax.get(tagDmax.size()-1);
	
			
}


public void printMatrix(String name){
    for (int x=0; x < distanceMatrix.length; x++) {
        System.out.print("|");
        for (int y=0; y < distanceMatrix[x].length; y++) {
          System.out.printf ("%.4f",distanceMatrix[x][y]);
          if (y!=distanceMatrix[x].length-1) 
        	  System.out.print("\t");
        }
        System.out.println("|");
      }
    
    String fileName = "DistMatrix_"+name;
    String ram="";
    for(Node n: categoryMap){
		ram += n.nodeName;
		if(n != categoryMap.get(categoryMap.size()-1))
			ram+=";";
	}
    ram+="\n";
    
    FileMethods.saveFile(ram,fileName, false);
    
    for (int x=0; x < distanceMatrix.length; x++) {
        //System.out.print("|"); 
        for (int y=0; y < distanceMatrix[x].length; y++) {
          //System.out.printf ("%.4f",distanceMatrix[x][y]);
        	if(distanceMatrix[x][y] != -1.0f && distanceMatrix[x][y] != 0){
        	  FileMethods.saveFile( String.valueOf(distanceMatrix[x][y]),fileName, true);
        	}
        		
          if (y!=distanceMatrix[x].length-1) 
        	  FileMethods.saveFile( ";",fileName, true);
        	  //System.out.print("\t");
        }
        //System.out.println("|");
        FileMethods.saveFile( "\n",fileName, true);
      }
    
    
	
}
	

	

}
