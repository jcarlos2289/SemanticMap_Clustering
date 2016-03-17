package buildMap;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JScrollPane;

import com.apporiented.algorithm.clustering.Cluster;
import com.apporiented.algorithm.clustering.ClusteringAlgorithm;
import com.apporiented.algorithm.clustering.CompleteLinkageStrategy;
import com.apporiented.algorithm.clustering.DefaultClusteringAlgorithm;
import com.apporiented.algorithm.clustering.visualization.DendrogramPanel;

public class HierClustering {
	
	
	double[][] distanceMatrix;
	ArrayList<ImageTags> ImageFullList;
	int dim;
	
	
	public HierClustering( int dimi, ArrayList<ImageTags> ImageList) {
			dim = dimi;
			ImageFullList = ImageList;
			}
	
	
	public JScrollPane cluster(){
		generateDistanceMatrix();
		//Genero y visualizo
		
		
		return getHierCluster();
						
		//solo genero mas no imprimo salida 
		//Cluster cl = generateHierCluster();
		
	}
	
    private void generateDistanceMatrix(){
		
		distanceMatrix = new double[ImageFullList.size()][ImageFullList.size()];
		for (int i = 0; i < ImageFullList.size(); i++) {
			for (int j = 0; j < ImageFullList.size(); j++) {
				distanceMatrix[i][j] = -1.0f;
			}
		}
		
		for (int i = 0; i < ImageFullList.size(); i++) {
			distanceMatrix[i][i]=0;
		}
		
				
		int n1=0;
		
		for (ImageTags fs : ImageFullList) {
		
	
				for(int n2 = n1+1; n2 <ImageFullList.size();n2++){
			
					distanceMatrix[n1][n2] = distanceOfNodes_KS(fs, ImageFullList.get(n2));
					distanceMatrix[n2][n1] = distanceMatrix[n1][n2];
				}
	
			n1++;
		}
		
	}
		
	private Cluster generateHierCluster(){
	//matrix de distancias
	//arreglo con los nombres
		
		String[] names = new String[ImageFullList.size()];
		for (int i = 0; i < ImageFullList.size(); i++) {
			String[] ram ;
			ram = ImageFullList.get(i).imageName.split("/");
			names[i] = ram[ram.length-1];//"Node_" +String.valueOf(i+1);
		}
			
		if(distanceMatrix==null)
			generateDistanceMatrix();
		
		ClusteringAlgorithm alg = new DefaultClusteringAlgorithm();
		Cluster cluster = alg.performClustering(distanceMatrix, names, new CompleteLinkageStrategy());  //SingleLinkageStrategy() CompleteLinkageStrategy()
				
		//cluster.toConsole(1);
		//System.out.println(printCluster(cluster));
		return cluster;
	}
		
	private JScrollPane getHierCluster(){
		Cluster cluster = generateHierCluster();
		
		
		DendrogramPanel dp = new DendrogramPanel();
		dp.setBackground(new Color(255,255,255));
		dp.setSize(500, 3000);
		dp.setModel(cluster);
		JScrollPane pane = new JScrollPane(dp);
		
		
		return pane;
		
		
	}
		
	private float distanceOfNodes_KS(ImageTags node1, ImageTags node2){

		Set<String> hs1;
		Iterator<String> iterator;
			
		hs1 = node1.tags.keySet();
		iterator = hs1.iterator();

		
		ArrayList<String> tagList = new ArrayList<String>();
		while(iterator.hasNext()){
			tagList.add(iterator.next());
		}
		
		Collections.sort(tagList);
		
		float[]cumulatedN1 = new float[tagList.size()];
		float[]cumulatedN2 = new float[tagList.size()];
		float[]distanceKS = new float[tagList.size()];
		
		cumulatedN1[0] = node1.tags.get(tagList.get(0));
		cumulatedN2[0] = node2.tags.get(tagList.get(0));
		distanceKS[0] = Math.abs(cumulatedN2[0]-cumulatedN1[0]);
		
		
		for (int i = 1; i < tagList.size(); i++) {
			cumulatedN1[i] = cumulatedN1[i-1]+ node1.tags.get(tagList.get(i));
			cumulatedN2[i] =  cumulatedN2[i-1]+ node2.tags.get(tagList.get(i));
			distanceKS[i] = Math.abs(cumulatedN2[i]-cumulatedN1[i]);
		}
		
    	ArrayList<Float> tagDmax = new ArrayList<Float>();
    	for(float s: distanceKS)
    		tagDmax.add(s);
		
		Collections.sort(tagDmax);
		
		
		
		return (float) tagDmax.get(tagDmax.size()-1);
		
				
	}

}
