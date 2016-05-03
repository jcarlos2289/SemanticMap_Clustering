package buildMap;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Set;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
//import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;

public class Gui extends JFrame implements ActionListener {
	private static final long serialVersionUID = -7457350242559078527L;
	private CanvasMap cm;
	int width = 1700, height = 1000;
	JButton process, clusterbt, clusterCoefBt;
	JCheckBox originalButton, graphButton, backButton, showNodes,clusters,highTags;
	JTextField th1, th2, th3,thTagTF;
	JComboBox<String> nodes;
	JComboBox<String> tagList;
	double threshold1, threshold2, thTag=0;
	int cutNode;
	int seqNumber=0;
	boolean original = true, showMap = true, background = true,
			mapGenerated = false, nodesMode = false, selectNodeChanged = false, 
			showCluster = false, tagMode = false, selectTagChanged = false, thTagMode=false,
			zoneGenerated=false, zoneSelectedMode=false, selectZoneChanged = false;
	
	int selectedNode = -1 ;
	int selectZone = -1;
	String selectedTag = null;
	
	
	String dataSetPath="/home/jcarlos2289/Descargas/";
	
	BuildMap bm;
	Kmeans km;
	String name ;
	
	 JMenu jmOperations, jmShows, jmCharts;
	 JMenuItem jmiGetCluster, jmiGenCluster, jmiCapture, jmiGenMap, jmiGenAllMaps, jmiVidSq1, jmiGenZone, jmiGenHierCluster, 
	 			jmiNodeComposition,  jmiGenFullHierCluster, jMIGetNodesContent, jMIGetZonesContent, jmiShowZoneChart, 
	 			jmiShowZoneChart_2, jmiShowZoneChart_3, jmiShowZoneChart_4, jmiCategoryMap, jmiZoneResume;
     JCheckBoxMenuItem originalCB, graphCB, backCB, showNodesCB, clustersCB, highTagsCB, thTagsCB,zoneCB;
     JMenu jMDataSet,jMSunny,jMCloudy, jMNight;
     JMenuItem jmiCl_1, jmiCl_2, jmiCl_3, jmiCl_4, jmiNi_1, jmiNi_2, jmiNi_3, jmiNi_4, jmiSu_1, jmiSu_2, jmiSu_3, jmiSu_4;
     JLabel statusLabel;
          
     JMenuItem jMiHybrid_Sq1;
     JMenuItem jMiHybrid_Sq2;
     JMenuItem jMiHybrid_Sq3;
     JMenuItem jMiHybrid_Sq4;
     JMenuItem jMiHybrid_Sq5;
     JMenuItem jMIImgAlex_Sq1;
     JMenuItem jMIImgAlex_Sq2;
     JMenuItem jMIImgAlex_Sq3;
     JMenuItem jMIImgAlex_Sq4;
     JMenuItem jMIImgAlex_Sq5;
     JMenuItem jMIImgCaff_Sq1;
     JMenuItem jMIImgCaff_Sq2;
     JMenuItem jMIImgCaff_Sq3;
     JMenuItem jMIImgCaff_Sq4;
     JMenuItem jMIImgCaff_Sq5;
     JMenuItem jMIImgGNet_Sq1;
     JMenuItem jMIImgGNet_Sq2;
     JMenuItem jMIImgGNet_Sq3;
     JMenuItem jMIImgGNet_Sq4;
     JMenuItem jMIImgGNet_Sq5;
     JMenuItem jMIImgVGG_Sq1;
     JMenuItem jMIImgVGG_Sq2;
     JMenuItem jMIImgVGG_Sq3;
     JMenuItem jMIImgVGG_Sq4;
     JMenuItem jMIImgVGG_Sq5;
     JMenuItem jMIPlaAlex_Sq1;
     JMenuItem jMIPlaAlex_Sq2;
     JMenuItem jMIPlaAlex_Sq3;
     JMenuItem jMIPlaAlex_Sq4;
     JMenuItem jMIPlaAlex_Sq5;
     JMenuItem jMIPlaGNet_Sq1;
     JMenuItem jMIPlaGNet_Sq2;
     JMenuItem jMIPlaGNet_Sq3;
     JMenuItem jMIPlaGNet_Sq4;
     JMenuItem jMIPlaGNet_Sq5;
     
     JMenuItem jMIImgMX_Sq1;
     JMenuItem jMIImgMX_Sq2;
     JMenuItem jMIImgMX_Sq3;
     JMenuItem jMIImgMX_Sq4;
     JMenuItem jMIImgMX_Sq5;
          
     JMenuItem jMIImgMX21K_Sq1;
     JMenuItem jMIImgMX21K_Sq2;
     JMenuItem jMIImgMX21K_Sq3;
     JMenuItem jMIImgMX21K_Sq4;
     JMenuItem jMIImgMX21K_Sq5;
     
     JMenuItem jMIImgAlex_Sq6;
     JMenuItem jMIImgCaff_Sq6;
     JMenuItem jMIImgGNet_Sq6;
     JMenuItem jMIImgMX_Sq6;
     JMenuItem jMIImgVGG_Sq6;
     JMenuItem jMIImgMX21K_Sq6;
     JMenu jMRelation;
     JPopupMenu.Separator jSeparator1;
     
          
     JMenu jMVidrilo;
     JMenu jMSeq1,jMSeq2, jMSeq3, jMSeq4, jMSeq5;
     
    
	
	public Gui() {
		
		threshold1 = 0.5;
		threshold2 = 0.0;
		cutNode = 1;
		bm = new BuildMap(threshold1, threshold2, cutNode);
			
		getContentPane().setLayout(new BorderLayout());
		setSize(width, height);
		setTitle(name+" Clustering");
		cm = new CanvasMap(this);
		//setTitle("Topological Mapping");
		getContentPane().add(cm, BorderLayout.CENTER);
		getContentPane().add(getToolBar(), BorderLayout.NORTH);
		getContentPane().add(getStatusBar(), BorderLayout.SOUTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
				
	}
	
	
	public JPanel getStatusBar(){
		JPanel statusPanel = new JPanel();
		statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		//frame.add(statusPanel, BorderLayout.SOUTH);
		
		//statusPanel.setPreferredSize(new Dimension(frame.getWidth(), 16));
		statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
		statusLabel = new JLabel("SemanticMapping 1");
		statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
		statusPanel.add(statusLabel);
		
		
		return statusPanel;
	}

	public JPanel getToolBar() {
		
		    jmOperations = new JMenu();
	        //jmOperations.addActionListener(this);
	        jmOperations.setText("Operations");

	        jmiGenMap = new JMenuItem("Gen Map");
	        jmiGenMap.addActionListener(this);
	        jmiGenMap.setEnabled(false);
	        jmOperations.add(jmiGenMap);
	        
	        jmiGenZone = new JMenuItem("Gen Zones");
	        jmiGenZone.addActionListener(this);
	        jmiGenZone.setEnabled(false);
	        jmOperations.add(jmiGenZone);
	        	        
	        jmiGenFullHierCluster = new JMenuItem("Gen Full Hierarchical Clustering (HierClass)");
	        jmiGenFullHierCluster.addActionListener(this);
	        //jmiGenFullHierCluster.setEnabled(false);
	        jmOperations.add(jmiGenFullHierCluster);
	           
	        jmiGenHierCluster = new JMenuItem("Gen Hierarchical Clustering (From Map)");
	        jmiGenHierCluster.addActionListener(this);
	        jmiGenHierCluster.setEnabled(false);
	        jmOperations.add(jmiGenHierCluster);

	        jmiGetCluster = new JMenuItem();
	        jmiGetCluster.setText("Get K Cluster");
	        jmiGetCluster.addActionListener(this);
	        jmOperations.add(jmiGetCluster);

	        jmiGenCluster = new JMenuItem();
	        jmiGenCluster.setText("Gen Cluster");
	        jmiGenCluster.addActionListener(this);
	        jmOperations.add(jmiGenCluster);

	        jmiCapture = new JMenuItem("Capture Screen");
	        jmiCapture.addActionListener(this);
	        jmiCapture.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
	        jmOperations.add(jmiCapture);
	        
	        jmiGenAllMaps = new JMenuItem("Gen 12 Idol Maps");
	        jmiGenAllMaps.addActionListener(this);
	       //jmOperations.add(jmiGenAllMaps);
	        
	        jSeparator1 = new JPopupMenu.Separator();
	        jMRelation = new JMenu();
	        jMIImgMX_Sq6 = new JMenuItem();
	        jMIImgAlex_Sq6 = new JMenuItem();
	        jMIImgCaff_Sq6 = new JMenuItem();
	        jMIImgGNet_Sq6 = new JMenuItem();
	        jMIImgVGG_Sq6 = new JMenuItem();
	        jMIImgMX21K_Sq6= new JMenuItem();
	        
	        jMIGetNodesContent = new JMenuItem();
	        jMIGetZonesContent = new JMenuItem();
	        
	        jmCharts = new JMenu("View Charts");
	        jmiShowZoneChart = new JMenuItem();
	        jmiShowZoneChart_2 = new JMenuItem();
	        jmiShowZoneChart_3 = new JMenuItem();
	        jmiShowZoneChart_4 = new JMenuItem();
	        
	        jmiCategoryMap= new JMenuItem();
	        	        
	        jMIImgMX_Sq6.addActionListener(this);
	        jMIImgAlex_Sq6.addActionListener(this);
	        jMIImgCaff_Sq6.addActionListener(this);
	        jMIImgGNet_Sq6.addActionListener(this);
	        jMIImgVGG_Sq6.addActionListener(this);
	        jMIImgMX21K_Sq6.addActionListener(this);
	        
	        
	        	        
	        jmOperations.add(jSeparator1);

	        jMRelation.setText("Get Relationship with");
	        jMRelation.setEnabled(false);

	        jMIImgMX_Sq6.setText("ImageNet_MXNet");
	        jMRelation.add(jMIImgMX_Sq6);
	        
	        jMIImgMX21K_Sq6.setText("ImageNet_MXNet21K");
	        jMRelation.add(jMIImgMX21K_Sq6);

	        jMIImgAlex_Sq6.setText("ImageNet_AlexNet");
	        jMRelation.add(jMIImgAlex_Sq6);

	        jMIImgCaff_Sq6.setText("ImageNetCaffeNet");
	        jMRelation.add(jMIImgCaff_Sq6);

	        jMIImgGNet_Sq6.setText("ImageNet_GoogLeNet");
	        jMRelation.add(jMIImgGNet_Sq6);

	        jMIImgVGG_Sq6.setText("ImageNet_VGG");
	        jMRelation.add(jMIImgVGG_Sq6);
	        	        
	        jmOperations.add(jMRelation);
	        
	        jmiNodeComposition = new JMenuItem("Node Composition");
	        jmiNodeComposition.addActionListener(this);
	        jmiNodeComposition.setEnabled(false);
	        jmOperations.add(jmiNodeComposition);
	        
	        jMIGetNodesContent.setText("Export Nodes Information");
	        jMIGetNodesContent.addActionListener(this);
	        jMIGetNodesContent.setEnabled(false);
	        jmOperations.add(jMIGetNodesContent);
	        
	        jMIGetZonesContent.setText("Export Zones Information");
	        jMIGetZonesContent.addActionListener(this);
	        jMIGetZonesContent.setEnabled(false);
	        jmOperations.add(jMIGetZonesContent);
	        
	        jmiShowZoneChart.setText("Zones Pie Chart Cat1-vs-Tag");
	        jmiShowZoneChart.addActionListener(this);
	        jmiShowZoneChart.setToolTipText("Cat1-vs-Tag");
	        jmiShowZoneChart.setEnabled(false);
	        jmCharts.add(jmiShowZoneChart);
	        
	        jmiShowZoneChart_2.setText("Zones Pie Chart Cat1-vs-ZoneTag");
	        jmiShowZoneChart_2.addActionListener(this);
	        jmiShowZoneChart_2.setToolTipText("Cat1-vs-ZoneTag");
	        jmiShowZoneChart_2.setEnabled(false);
	        jmCharts.add(jmiShowZoneChart_2);
	        
	        jmiShowZoneChart_3.setText("Zones Pie Chart Cat2-vs-Tag");
	        jmiShowZoneChart_3.addActionListener(this);
	        jmiShowZoneChart_3.setToolTipText("Cat2-vs-Tag");
	        jmiShowZoneChart_3.setEnabled(false);
	        jmCharts.add(jmiShowZoneChart_3);
	        	        
	        jmiShowZoneChart_4.setText("Zones Pie Chart Cat2-vs-ZoneTag");
	        jmiShowZoneChart_4.addActionListener(this);
	        jmiShowZoneChart_4.setToolTipText("Cat2-vs-ZoneTag    ");
	        jmiShowZoneChart_4.setEnabled(false);
	        jmCharts.add(jmiShowZoneChart_4);
	        
	        jmOperations.add(jmCharts) ;    
	        
	        jmiZoneResume= new JMenuItem("Zone Information Table");
	        jmiZoneResume.addActionListener(this);
	        jmiZoneResume.setEnabled(false);
	        jmOperations.add(jmiZoneResume);
	        
	        
	        jmiCategoryMap.addActionListener(this);
	        jmiCategoryMap.setText("Calculate Distance between Categories");
	        jmOperations.add(jmiCategoryMap);
	        
	       //Cat1-vs-Tag     // Cat1-vs-ZoneTag     //Cat2-vs-Tag      //Cat2-vs-ZoneTag      
	       
	        jmShows = new JMenu("View");
	        
	        originalCB= new javax.swing.JCheckBoxMenuItem("OriginalMap");
	        originalCB.setSelected(true);
	        originalCB.addActionListener(this);
	                
	        graphCB = new javax.swing.JCheckBoxMenuItem("Graph");
	        graphCB.addActionListener(this);
	        graphCB.setSelected(true);
	        graphCB.setEnabled(mapGenerated);
	        	        
	        backCB = new javax.swing.JCheckBoxMenuItem("Background Image");
	        backCB.setSelected(true);
	        backCB.addActionListener(this);
	        	           
	        showNodesCB = new javax.swing.JCheckBoxMenuItem("Show Nodes");
	        showNodesCB.setSelected(false);
	        showNodesCB.setEnabled(false);
	        showNodesCB.addActionListener(this);
	        	        
	        clustersCB =new JCheckBoxMenuItem("Clusters");
	        clustersCB.addActionListener(this);
	        clustersCB.setSelected(false);
			clustersCB.setEnabled(false);
			
			highTagsCB = new JCheckBoxMenuItem("Show High Tag");
			highTagsCB.addActionListener(this);
			highTagsCB.setSelected(false);
			highTagsCB.setToolTipText("A termic color scale for the whole map");
			highTagsCB.setEnabled(false);
						
			thTagsCB = new JCheckBoxMenuItem("Show High Threshold Tags");
			thTagsCB.addActionListener(this);
			thTagsCB.setSelected(false);
			thTagsCB.setToolTipText("A termic color scale for every node in the map.");
			thTagsCB.setEnabled(false);
			
			zoneCB = new JCheckBoxMenuItem("Show Zones");
			zoneCB.addActionListener(this);
			zoneCB.setSelected(false);
			zoneCB.setEnabled(false);
				                
	        jmShows.add(originalCB);
	        jmShows.add(graphCB);
	        jmShows.add(backCB);
	        jmShows.add(showNodesCB);
	        jmShows.add(clustersCB);
	        jmShows.add(highTagsCB);
	        jmShows.add(thTagsCB);
	        jmShows.add(zoneCB);
	        
	        //JPanel jp2 = new JPanel();
	        JMenuBar jMenuBar1 = new JMenuBar();
	        jMenuBar1.add(jmOperations);
	        jMenuBar1.add(jmShows);
	        	          
	        jMDataSet = new javax.swing.JMenu();
	        
	        
	        jMDataSet.setText("Select Data");
     	        
	        jMSeq1 = new JMenu();
	        jMiHybrid_Sq1 = new JMenuItem();
	        jMIImgAlex_Sq1 = new JMenuItem();
	        jMIImgCaff_Sq1 = new JMenuItem();
	        jMIImgGNet_Sq1 = new JMenuItem();
	        jMIImgVGG_Sq1 = new JMenuItem();
	        jMIPlaAlex_Sq1 = new JMenuItem();
	        jMIPlaGNet_Sq1 = new JMenuItem();
	        jMIImgMX_Sq1 = new JMenuItem();
	        jMSeq2 = new JMenu();
	        jMiHybrid_Sq2 = new JMenuItem();
	        jMIImgAlex_Sq2 = new JMenuItem();
	        jMIImgCaff_Sq2 = new JMenuItem();
	        jMIImgGNet_Sq2 = new JMenuItem();
	        jMIImgVGG_Sq2 = new JMenuItem();
	        jMIPlaAlex_Sq2 = new JMenuItem();
	        jMIPlaGNet_Sq2 = new JMenuItem();
	        jMSeq3 = new JMenu();
	        jMiHybrid_Sq3 = new JMenuItem();
	        jMIImgAlex_Sq3 = new JMenuItem();
	        jMIImgCaff_Sq3 = new JMenuItem();
	        jMIImgGNet_Sq3 = new JMenuItem();
	        jMIImgVGG_Sq3 = new JMenuItem();
	        jMIPlaAlex_Sq3 = new JMenuItem();
	        jMIPlaGNet_Sq3 = new JMenuItem();
	        jMSeq4 = new JMenu();
	        jMiHybrid_Sq4 = new JMenuItem();
	        jMIImgAlex_Sq4 = new JMenuItem();
	        jMIImgCaff_Sq4 = new JMenuItem();
	        jMIImgGNet_Sq4 = new JMenuItem();
	        jMIImgVGG_Sq4 = new JMenuItem();
	        jMIPlaAlex_Sq4 = new JMenuItem();
	        jMIPlaGNet_Sq4 = new JMenuItem();
	        jMSeq5 = new JMenu();
	        jMiHybrid_Sq5 = new JMenuItem();
	        jMIImgAlex_Sq5 = new JMenuItem();
	        jMIImgCaff_Sq5 = new JMenuItem();
	        jMIImgGNet_Sq5 = new JMenuItem();
	        jMIImgVGG_Sq5 = new JMenuItem();
	        jMIPlaAlex_Sq5 = new JMenuItem();
	        jMIPlaGNet_Sq5 = new JMenuItem();
	        jMVidrilo = new JMenu();
	        
	        jMIImgMX_Sq1= new JMenuItem();
	        jMIImgMX_Sq2= new JMenuItem();
	        jMIImgMX_Sq3= new JMenuItem();
	        jMIImgMX_Sq4= new JMenuItem();
	        jMIImgMX_Sq5= new JMenuItem();
	             
	        jMIImgMX21K_Sq1= new JMenuItem();
	        jMIImgMX21K_Sq2= new JMenuItem();
	        jMIImgMX21K_Sq3= new JMenuItem();
	        jMIImgMX21K_Sq4= new JMenuItem();
	        jMIImgMX21K_Sq5= new JMenuItem();
	       	        
	        jMVidrilo.setText("Vidrilo");

	        jMSeq1.setText("Sequence1");
	        
	        jMiHybrid_Sq1.addActionListener(this);
	        jMIImgAlex_Sq1.addActionListener(this);
	        jMIImgCaff_Sq1.addActionListener(this);
	        jMIImgGNet_Sq1.addActionListener(this);
	        jMIImgVGG_Sq1.addActionListener(this);
	        jMIPlaAlex_Sq1.addActionListener(this);
	        jMIPlaGNet_Sq1.addActionListener(this);
	        jMIImgMX_Sq1.addActionListener(this);
	        jMIImgMX21K_Sq1.addActionListener(this);
	        
	        jMiHybrid_Sq2.addActionListener(this);
	        jMIImgAlex_Sq2.addActionListener(this);
	        jMIImgCaff_Sq2.addActionListener(this);
	        jMIImgGNet_Sq2.addActionListener(this);
	        jMIImgVGG_Sq2.addActionListener(this);
	        jMIPlaAlex_Sq2.addActionListener(this);
	        jMIPlaGNet_Sq2.addActionListener(this);
	        jMIImgMX_Sq2.addActionListener(this);
	        jMIImgMX21K_Sq2.addActionListener(this);
	        
	        jMiHybrid_Sq3.addActionListener(this);
	        jMIImgAlex_Sq3.addActionListener(this);
	        jMIImgCaff_Sq3.addActionListener(this);
	        jMIImgGNet_Sq3.addActionListener(this);
	        jMIImgVGG_Sq3.addActionListener(this);
	        jMIPlaAlex_Sq3.addActionListener(this);
	        jMIPlaGNet_Sq3.addActionListener(this);
	        jMIImgMX_Sq3.addActionListener(this);
	        jMIImgMX21K_Sq3.addActionListener(this);
	        
	        jMiHybrid_Sq4.addActionListener(this);
	        jMIImgAlex_Sq4.addActionListener(this);
	        jMIImgCaff_Sq4.addActionListener(this);
	        jMIImgGNet_Sq4.addActionListener(this);
	        jMIImgVGG_Sq4.addActionListener(this);
	        jMIPlaAlex_Sq4.addActionListener(this);
	        jMIPlaGNet_Sq4.addActionListener(this);
	        jMIImgMX_Sq4.addActionListener(this);
	        jMIImgMX21K_Sq4.addActionListener(this);
	        
	        jMiHybrid_Sq5.addActionListener(this);
	        jMIImgAlex_Sq5.addActionListener(this);
	        jMIImgCaff_Sq5.addActionListener(this);
	        jMIImgGNet_Sq5.addActionListener(this);
	        jMIImgVGG_Sq5.addActionListener(this);
	        jMIPlaAlex_Sq5.addActionListener(this);
	        jMIPlaGNet_Sq5.addActionListener(this);
	        jMIImgMX_Sq5.addActionListener(this);
	        jMIImgMX21K_Sq5.addActionListener(this);
	        
	        jMiHybrid_Sq1.setText("Hybrid");
	        jMSeq1.add(jMiHybrid_Sq1);
	        
	        jMIImgAlex_Sq1.setText("ImageNet_AlexNet");
	        jMSeq1.add(jMIImgAlex_Sq1);

	        jMIImgCaff_Sq1.setText("ImageNetCaffeNet");
	        jMSeq1.add(jMIImgCaff_Sq1);

	        jMIImgGNet_Sq1.setText("ImageNet_GoogLeNet");
	        jMSeq1.add(jMIImgGNet_Sq1);

	        jMIImgVGG_Sq1.setText("ImageNet_VGG");
	        jMSeq1.add(jMIImgVGG_Sq1);

	        jMIPlaAlex_Sq1.setText("Places_AlexNet");
	        jMSeq1.add(jMIPlaAlex_Sq1);

	        jMIPlaGNet_Sq1.setText("Places_GoogLeNet");
	        jMSeq1.add(jMIPlaGNet_Sq1);
	        
	        jMIImgMX_Sq1.setText("ImageNet_MXNet");
	        jMSeq1.add(jMIImgMX_Sq1);
	        
	        jMIImgMX21K_Sq1.setText("ImageNet_MXNet21K");
	        jMSeq1.add(jMIImgMX21K_Sq1);

	        jMVidrilo.add(jMSeq1);

	        jMSeq2.setText("Sequence2");

	        jMiHybrid_Sq2.setText("Hybrid");
	        jMSeq2.add(jMiHybrid_Sq2);

	        jMIImgAlex_Sq2.setText("ImageNet_AlexNet");
	        jMSeq2.add(jMIImgAlex_Sq2);

	        jMIImgCaff_Sq2.setText("ImageNetCaffeNet");
	        jMSeq2.add(jMIImgCaff_Sq2);

	        jMIImgGNet_Sq2.setText("ImageNet_GoogLeNet");
	        jMSeq2.add(jMIImgGNet_Sq2);

	        jMIImgVGG_Sq2.setText("ImageNet_VGG");
	        jMSeq2.add(jMIImgVGG_Sq2);

	        jMIPlaAlex_Sq2.setText("Places_AlexNet");
	        jMSeq2.add(jMIPlaAlex_Sq2);

	        jMIPlaGNet_Sq2.setText("Places_GoogLeNet");
	        jMSeq2.add(jMIPlaGNet_Sq2);
	        
	        jMIImgMX_Sq2.setText("ImageNet_MXNet");
	        jMSeq2.add(jMIImgMX_Sq2);
	        
	        jMIImgMX21K_Sq2.setText("ImageNet_MXNet21K");
	        jMSeq2.add(jMIImgMX21K_Sq2);

	        jMVidrilo.add(jMSeq2);

	        jMSeq3.setText("Sequence3");

	        jMiHybrid_Sq3.setText("Hybrid");
	        jMSeq3.add(jMiHybrid_Sq3);

	        jMIImgAlex_Sq3.setText("ImageNet_AlexNet");
	        jMSeq3.add(jMIImgAlex_Sq3);

	        jMIImgCaff_Sq3.setText("ImageNetCaffeNet");
	        jMSeq3.add(jMIImgCaff_Sq3);

	        jMIImgGNet_Sq3.setText("ImageNet_GoogLeNet");
	        jMSeq3.add(jMIImgGNet_Sq3);

	        jMIImgVGG_Sq3.setText("ImageNet_VGG");
	        jMSeq3.add(jMIImgVGG_Sq3);

	        jMIPlaAlex_Sq3.setText("Places_AlexNet");
	        jMSeq3.add(jMIPlaAlex_Sq3);

	        jMIPlaGNet_Sq3.setText("Places_GoogLeNet");
	        jMSeq3.add(jMIPlaGNet_Sq3);
	        
	        jMIImgMX_Sq3.setText("ImageNet_MXNet");
	        jMSeq3.add(jMIImgMX_Sq3);
	        
	        jMIImgMX21K_Sq3.setText("ImageNet_MXNet21K");
	        jMSeq3.add(jMIImgMX21K_Sq3);

	        jMVidrilo.add(jMSeq3);

	        jMSeq4.setText("Sequence4");

	        jMiHybrid_Sq4.setText("Hybrid");
	        jMSeq4.add(jMiHybrid_Sq4);

	        jMIImgAlex_Sq4.setText("ImageNet_AlexNet");
	        jMSeq4.add(jMIImgAlex_Sq4);

	        jMIImgCaff_Sq4.setText("ImageNetCaffeNet");
	        jMSeq4.add(jMIImgCaff_Sq4);

	        jMIImgGNet_Sq4.setText("ImageNet_GoogLeNet");
	        jMSeq4.add(jMIImgGNet_Sq4);

	        jMIImgVGG_Sq4.setText("ImageNet_VGG");
	        jMSeq4.add(jMIImgVGG_Sq4);

	        jMIPlaAlex_Sq4.setText("Places_AlexNet");
	        jMSeq4.add(jMIPlaAlex_Sq4);

	        jMIPlaGNet_Sq4.setText("Places_GoogLeNet");
	        jMSeq4.add(jMIPlaGNet_Sq4);
	        
	        jMIImgMX_Sq4.setText("ImageNet_MXNet");
	        jMSeq4.add(jMIImgMX_Sq4);
	        
	        jMIImgMX21K_Sq4.setText("ImageNet_MXNet21K");
	        jMSeq4.add(jMIImgMX21K_Sq4);
	        
	        jMVidrilo.add(jMSeq4);

	        jMSeq5.setText("Sequence5");

	        jMiHybrid_Sq5.setText("Hybrid");
	        jMSeq5.add(jMiHybrid_Sq5);

	        jMIImgAlex_Sq5.setText("ImageNet_AlexNet");
	        jMSeq5.add(jMIImgAlex_Sq5);

	        jMIImgCaff_Sq5.setText("ImageNetCaffeNet");
	        jMSeq5.add(jMIImgCaff_Sq5);

	        jMIImgGNet_Sq5.setText("ImageNet_GoogLeNet");
	        jMSeq5.add(jMIImgGNet_Sq5);

	        jMIImgVGG_Sq5.setText("ImageNet_VGG");
	        jMSeq5.add(jMIImgVGG_Sq5);

	        jMIPlaAlex_Sq5.setText("Places_AlexNet");
	        jMSeq5.add(jMIPlaAlex_Sq5);

	        jMIPlaGNet_Sq5.setText("Places_GoogLeNet");
	        jMSeq5.add(jMIPlaGNet_Sq5);
	        
	        jMIImgMX_Sq5.setText("ImageNet_MXNet");
	        jMSeq5.add(jMIImgMX_Sq5);
	        
	        jMIImgMX21K_Sq5.setText("ImageNet_MXNet21K");
	        jMSeq5.add(jMIImgMX21K_Sq5);

	        jMVidrilo.add(jMSeq5);

	        jMDataSet.add(jMVidrilo);

	        jMenuBar1.add(jMDataSet);

	        this.setJMenuBar(jMenuBar1);
		
		
		
		JPanel jp = new JPanel();
		jp.setSize(width, 100);
		jp.setLayout(new BoxLayout(jp, BoxLayout.LINE_AXIS));
		jp.setAlignmentX(LEFT_ALIGNMENT);
				
		
		JLabel lab1 = new JLabel("Clustering Group Cut ");
		jp.add(lab1);
		th1 = new JTextField(String.valueOf(threshold1));
		th1.addActionListener(this);
		jp.add(th1);
		//JLabel lab2 = new JLabel("Threshold2");
		//jp.add(lab2);
		//th2 = new JTextField(String.valueOf(threshold2));
		//th2.addActionListener(this);
		//jp.add(th2);
		//JLabel lab3 = new JLabel("CutNode");
		//jp.add(lab3);
		///th3 = new JTextField(String.valueOf(cutNode));
		//th3.addActionListener(this);
		//jp.add(th3);
		
		//th1.setEnabled(false);
		//th2.setEnabled(false);
		//th3.setEnabled(false);
				
		
		JLabel lab4 = new JLabel("TagThreshold");
		jp.add(lab4);
		thTagTF=new JTextField(String.valueOf(thTag));
		thTagTF.addActionListener(this);
		thTagTF.setEnabled(false);
			
		jp.add(thTagTF);
				
		String[] aux = {"Select Node"};
		nodes = new JComboBox<String>(aux);
		nodes.addActionListener(this);
		nodes.setEnabled(nodesMode);
		jp.add(nodes);
		
		
		String[] aux2 = {"Select Tag"};
		tagList = new JComboBox<String>(aux2);
		tagList.addActionListener(this);
		tagList.setEnabled(tagMode); 
		jp.add(tagList);
		
		
		
		return jp;
	}

	public void genComboNodes() {
		int size = bm.map.nodes.size();
		String[] aux = new String[size + 1];
		aux[0] = "Select Node";
		for (int i = 1; i < size + 1; i++) {
			aux[i] = String.valueOf(i - 1) + " "
					+ bm.map.getNode(i - 1).getSize();
		}
		nodes.setModel(new DefaultComboBoxModel<String>(aux));
		nodes.setSelectedIndex(0);
		//Imprimir lista de nodos
		/*String lst = "";
		for (int i = 0; i < aux.length; i++) {
			lst += aux[i] + "\n";
		}*/

		// FileMethods.saveFile(lst, "NodeList", false);
	}
	
	public void genComboTag(){
		int size = bm.dimension;
		String[] aux = new String[size + 1];
		aux[0] = "Select Tag";
		
		ImageTags auxImage;
		
		auxImage = bm.imgTags.get(0);
		Set<String> keyset = auxImage.tags.keySet();
		
		ArrayList<String> keys =new ArrayList<String>();
		
		for (String object : keyset) {
			keys.add(object);
		}
		
		Collections.sort(keys);
		
		for (int i = 1; i < size + 1; i++) {
			aux[i] =keys.get(i-1);
		}
			
		tagList.setModel(new DefaultComboBoxModel<String>(aux));
		tagList.setSelectedIndex(0);
		
	}
	

	public static void main(String[] args) {
		
		Gui g = new Gui();
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		g.setLocationRelativeTo(null);
		 g.setVisible(true);
	     g.toFront();
	     
	     //Ejecucion por Consola
	     
	     if(!args[0].equals("gui")){
	   	     //Seleccionar la Secuencia-----------------------------------------------------------------------
	    	 int key = Integer.parseInt(args[0]);
	    	 
	   switch (key) {
		case 1 :
			//Seq1 Places AlexNet
			g.bm.readTags("/home/jcarlos2289/Documentos/VidriloTags/Sequence1/Vidrilo_Sequence1_PlacesAlexNet/sequence1visual", -0.00000001, 2389, "/home/jcarlos2289/Documentos/VidriloTags/Sequence1/Sequence1_CAT2.txt",205, 10, 2000000000,"TrianglesPoints.txt");
		  	g.name= "VidriloSeq1_PlacesAlexNet";
		  	g.setTitle(g.name);      
	        //g.cm.repaint();
	        g.statusLabel.setText("Sequence : "+g.name+" selected.");
	        g.seqNumber=1;
	        g.jmiGenMap.setEnabled(true);
			
			break;
		case 2 :
			//Seq1 Places GoogLeNet
			g.bm.readTags("/home/jcarlos2289/Documentos/VidriloTags/Sequence1/Vidrilo_Sequence1_PlacesGoogLeNet/sequence1visual", -0.00000001, 2389, "/home/jcarlos2289/Documentos/VidriloTags/Sequence1/Sequence1_CAT2.txt",205, 10, 2000000000,"TrianglesPoints.txt");
	        g.name= "VidriloSeq1_PlacesGoogLeNet";
	        g.setTitle(g.name);      
	        //g.cm.repaint();
	        g.statusLabel.setText("Sequence : "+ g.name+" selected.");
	        g.seqNumber=1;
	        g.jmiGenMap.setEnabled(true);
					
			break;
		case 3 :
			 //Seq2 Places AlexNet
	        g.bm.readTags("/home/jcarlos2289/Documentos/VidriloTags/Sequence2/Vidrilo_Sequence2_PlacesAlexNet/sequence2visual", -0.00000001, 4579, "/home/jcarlos2289/Documentos/VidriloTags/Sequence2/Sequence2_CAT2.txt",205, 10, 2000000000,"TrianglesPoints.txt");
	        g.name= "VidriloSeq2_PlacesAlexNet";
	        g.setTitle(g.name);      
			//g.cm.repaint();
			g.statusLabel.setText("Sequence : "+g.name+" selected.");
			g.seqNumber=2;
			g.jmiGenMap.setEnabled(true);
			
			break;
		case 4 :
			 //Seq2 Places GoogleNet
			g.bm.readTags("/home/jcarlos2289/Documentos/VidriloTags/Sequence2/Vidrilo_Sequence2_PlacesGoogLeNet/sequence2visual", -0.00000001, 4579, "/home/jcarlos2289/Documentos/VidriloTags/Sequence2/Sequence2_CAT2.txt",205, 10, 2000000000,"TrianglesPoints.txt");
			g.name= "VidriloSeq2_PlacesGoogLeNet";
			g.setTitle(g.name);      
			//g.cm.repaint();
			g.statusLabel.setText("Sequence : "+g.name+" selected.");
			g.seqNumber=2;
			g.jmiGenMap.setEnabled(true);
			
			break;
		case 5 :
			 //VidriloSeq1_ImageNetAlexNet
		     g.bm.readTags("/home/jcarlos2289/Documentos/VidriloTags/Sequence1/Vidrilo_Sequence1_ImageNetAlexNet/sequence1visual",-0.00000001,2389,"/home/jcarlos2289/Documentos/VidriloTags/Sequence1/Sequence1_CAT2.txt",1000,10,2000000000,"TrianglesPoints.txt");
		     g.name="VidriloSeq1_ImageNetAlexNet";
		     g.setTitle(g.name);
		     //g.cm.repaint();
		     g.statusLabel.setText("Sequence:"+g.name+"selected.");
		     g.seqNumber=1;
		     g.jmiGenMap.setEnabled(true);
			
			break;
		case 6 :
			  //VidriloSeq1_ImageNetGoogLeNet
		     g.bm.readTags("/home/jcarlos2289/Documentos/VidriloTags/Sequence1/Vidrilo_Sequence1_ImageNetGoogLeNet/sequence1visual",-0.00000001,2389,"/home/jcarlos2289/Documentos/VidriloTags/Sequence1/Sequence1_CAT2.txt",1000,10,2000000000,"TrianglesPoints.txt");
		     g.name="VidriloSeq1_ImageNetGoogLeNet";
		     g.setTitle(g.name);
		     //g.cm.repaint();
		     g.statusLabel.setText("Sequence:"+g.name+"selected.");
		     g.jmiGenMap.setEnabled(true);
			
			break;
		case 7 :
			 //VidriloSeq2_ImageNetAlexNet
			 g.bm.readTags("/home/jcarlos2289/Documentos/VidriloTags/Sequence2/Vidrilo_Sequence2_ImageNetAlexNet/sequence2visual",-0.00000001,4579,"/home/jcarlos2289/Documentos/VidriloTags/Sequence2/Sequence2_CAT2.txt",1000,10,2000000000,"TrianglesPoints.txt");
		     g.name="VidriloSeq2_ImageNetAlexNet";
		     g.setTitle(g.name);
		     //g.cm.repaint();
		     g.statusLabel.setText("Sequence:"+g.name+"selected.");
		     g.seqNumber=2;
		     g.jmiGenMap.setEnabled(true);
			
			break;
		case 8 :
			 //VidriloSeq2_ImageNetGoogLeNet
		     g.bm.readTags("/home/jcarlos2289/Documentos/VidriloTags/Sequence2/Vidrilo_Sequence2_ImageNetGoogLeNet/sequence2visual",-0.00000001,4579,"/home/jcarlos2289/Documentos/VidriloTags/Sequence2/Sequence2_CAT2.txt",1000,10,2000000000,"TrianglesPoints.txt");
		     g.name="VidriloSeq2_ImageNetGoogLeNet";
		     g.setTitle(g.name);
		     //g.cm.repaint();
		     g.statusLabel.setText("Sequence:"+g.name+"selected.");
		     g.jmiGenMap.setEnabled(true);
		  
		     break;
			     
		case 9 :
			 //Seq1_ImageNetMXNet21K 1500
			 g.bm.readTags("/home/jcarlos2289/Documentos/VidriloTags/Sequence1/Vidrilo_Sequence1_ImageNetMXNet21K_1500/sequence1visual", -1.0000000, 2389, "/home/jcarlos2289/Documentos/VidriloTags/Sequence1/Sequence1_CAT2.txt",1500, 10, 2000000000,"TrianglesPoints.txt");
		  	 g.name= "VidriloSeq1_ImageNetMXNet21K";
		  	 g.setTitle(g.name);      
	         // g.cm.repaint();
	         g.statusLabel.setText("Sequence : "+g.name+" selected.");
	         g.seqNumber=1;
	         g.jmiGenMap.setEnabled(true);
			break;
			
		case 10 :
			//Seq2_ImageNetMXNet21K 1500
			g.bm.readTags("/home/jcarlos2289/Documentos/VidriloTags/Sequence2/Vidrilo_Sequence2_ImageNetMXNet21K_1500/sequence2visual", -1.0000000, 4579, "/home/jcarlos2289/Documentos/VidriloTags/Sequence2/Sequence2_CAT2.txt",1500, 10, 2000000000,"TrianglesPoints.txt");
  			g.name= "VidriloSeq2_ImageNetMXNet21K";
  			g.setTitle(g.name);      
     	    //g.cm.repaint();
	     	g.statusLabel.setText("Sequence : "+g.name+" selected.");
	     	g.seqNumber=2;
	     	g.jmiGenMap.setEnabled(true);
			
			break;

		default :
			break;
	} 	 
	   	 
	                         
         //Generar el Mapa (nodos)----------------------------------------------------------
        g.showMap = true;
	        		//background = true;
	    g.mapGenerated = false; 
	    g.nodesMode = false; 
	    g.selectNodeChanged = false; 
	    g.showCluster = false; 
	    g.tagMode = false; 
	    g.selectTagChanged = false; 
	    g.thTagMode=false;
	    g.selectedNode = -1 ;
	    g.selectedTag = null;      
	        	  
	        	       	  
	    g.graphCB.setEnabled(false);
	  	g.showNodesCB.setEnabled(false);
	  	g.bm.buildMap();
	  	//g.graphCB.setEnabled(true);
	  	//g.showNodesCB.setEnabled(true);
	  
	  	g.genComboNodes();
	  	g.genComboTag();
	  	//g.tagList.setEnabled(true);
	  	//g.highTagsCB.setEnabled(true);
	  	//g.highTagsCB.setSelected(true);
	  	//g.thTagsCB.setEnabled(true);
	  	//g.thTagsCB.setSelected(false);
	  	//g.tagMode =true;
	  	g.mapGenerated = true;
	  	g.zoneGenerated = false;
	  	//g.cm.repaint();
	  	Date date = new Date();
	    DateFormat hourdateFormat = new SimpleDateFormat("HH:mm:ss");
	   
	  	g.statusLabel.setText("Map for : "+g.name+" generated at: "+hourdateFormat.format(date));
	  	
	  			
	  	//g.jMIGetNodesContent.setEnabled(true);
	  	//g.jMRelation.setEnabled(true);
	  	//g.jmiGenZone.setEnabled(true);
	  	//g.jmiGenHierCluster.setEnabled(true);
	  	//g.jmiNodeComposition.setEnabled(true);

	  			//aki el for para los TH 
	  			//Generar las Zonas----------------------------------------------
        double[] thTest = {0.05, 0.1, 0.2 ,0.3, 0.4, 0.5, 0.6, 0.7 ,0.8 ,0.9, 1.0, 1.1, 1.2, 1.3, 1.4, 1.5, 1.6, 1.7, 1.8, 1.9, 2.0 ,2.1 ,2.2 ,2.3, 2.4 
        		          , 2.5, 2.6, 2.7, 2.8, 2.9, 3.0, 3.1, 3.2, 3.3, 3.4, 3.5 ,3.6, 3.7,3.8, 3.9, 4.0, 4.1, 4.2, 4.3, 4.4, 4.5}; //{0.05, 0.1, 0.2 ,0.3, 0.4, 0.5, 0.6, 0.7 ,0.8 ,0.9, 1.0, 1.1, 1.2, 1.3, 1.4, 1.5, 1.6, 1.7, 1.8, 1.9, 2.0 ,2.1 ,2.2 ,2.3 , 2.4 , 2.5, 2.6, 2.7, 2.8, 2.9, 3.0, 3.1, 3.2, 3.3, 3.4, 3.5 ,3.6, 3.7,3.8, 3.9, 4.0, 4.1, 4.2, 4.3, 4.4, 4.5};
for(int i = 0; i< thTest.length; i++){	 //agreagar el guardar el cluster(jpg) 
			
	        g.bm.map.setCutZoneThreshold(thTest[i]);
	  		g.bm.map.generateZones(thTest[i]); 
            g.zoneGenerated = true;
            g.zoneCB.setSelected(true);
            g.zoneCB.setEnabled(true);
            //g.jMIGetZonesContent.setEnabled(true);
           // g.jmiShowZoneChart.setEnabled(true);
           // g.jmiShowZoneChart_2.setEnabled(true);
           // g.jmiShowZoneChart_3.setEnabled(true);
           // g.jmiShowZoneChart_4.setEnabled(true);
           // g.statusLabel.setText("Zones have been generated.");
           // g.cm.repaint();

            	//Imprimir los valores en los txt------------------------------------------
            double thh = g.bm.map.getCutZoneThreshold();
	        String colofon ="_"+g.name +"_TH_"+ thh+"_Zones_"+ g.bm.map.zones.size();
            	            	
            	//Formato 1 ZoneTag # CantNodos VidriloCat1 VidriloCat2 .....VidriloCat10
            String[] tags = {"HallEntrance","ProfessorRoom","StudentsRoom","TechnicalRoom","Toilet","Secretary","Videoconference","Warehouse","ElevatorArea","Corridor"};
            String h="N;Zone_Tag;Num;Nodes";
            for(String gif: tags){
            		h+=";"+gif;
            }
            h+="\n";
            FileMethods.saveFile(h, "ZonesContent"+ colofon, false);
            int q =1;
            for(Zone z: g.bm.map.zones){
            	FileMethods.saveFile(String.valueOf(q) +z.getZoneContent(tags)+"\n",  "ZonesContent"+ colofon , true);
            	q++;
            }
            	//Otros Formatos
            g.bm.map.printMapCategoriesInformation(new String[]{"Cat1-vs-Tag"+colofon,"Cat1-vs-ZoneTag"+colofon,"Cat2-vs-Tag"+colofon,"Cat2-vs-ZoneTag"+colofon});
            
            g.bm.map.printZonesDataset(new String[]{"Cat1-vs-TagSum"+colofon,"Cat1-vs-ZoneTagSum"+colofon,"Cat2-vs-TagSum"+colofon,"Cat2-vs-ZoneTagSum"+colofon});
            
            g.statusLabel.setText("Test # " + (i+1) + " of " + (thTest.length));
            
            
            if (g.bm.map.zones.size()==1){
            	
            	for (int k = i+1;k < thTest.length;k++){
            	   	 colofon ="_"+g.name +"_TH_"+ thTest[k]+"_Zones_"+ g.bm.map.zones.size();
	            	 g.bm.map.printMapCategoriesInformation(new String[]{"Cat1-vs-Tag"+colofon,"Cat1-vs-ZoneTag"+colofon,"Cat2-vs-Tag"+colofon,"Cat2-vs-ZoneTag"+colofon});
	                 g.bm.map.printZonesDataset(new String[]{"Cat1-vs-TagSum"+colofon,"Cat1-vs-ZoneTagSum"+colofon,"Cat2-vs-TagSum"+colofon,"Cat2-vs-ZoneTagSum"+colofon});
	                 g.statusLabel.setText("Test # " + (k) + " of " + (thTest.length));
            	  	}
            	
            	
            	
            	break;
            }
            
            
         }//fin del format

		g.setVisible(false);
		g.dispose();
	     }   
	     //*******************************************************************************************************************
	     
	    
	}

	public void actionPerformed(ActionEvent e) {


		if (e.getSource() == th1) {
			threshold1 = Double.parseDouble(th1.getText());
			bm.setThreshold1(threshold1);
			cm.repaint();
			return;
		}
		if (e.getSource() == th2) {
			threshold2 = Double.parseDouble(th2.getText());
			bm.setThreshold2(threshold2);
			cm.repaint();
			return;
		}
		if (e.getSource() == th3) {
			System.out.println(th3.getText());
			bm.setCutNode(Integer.parseInt(th3.getText()));
			if (bm.map != null)
				bm.map.setWeights(Integer.parseInt(th3.getText()));
			cm.repaint();
			return;
		}

		if (e.getSource()== thTagTF){
			thTag=Double.parseDouble(thTagTF.getText());
			return;
		}

		if (e.getSource() == tagList) {


			if(tagMode){
				if (((String) tagList.getSelectedItem()).equals("Select Tag")) {
					selectedTag = null;
				} else {
					selectedTag = (String) tagList.getSelectedItem();
					selectTagChanged = true;
					//tagMode =true;
					nodesMode=false;
					cm.repaint();
				}}


			if(thTagMode){
				if(!thTagTF.getText().isEmpty()){
					thTag = Double.parseDouble(thTagTF.getText());
					selectedTag = (String) tagList.getSelectedItem();
					selectTagChanged = true;
					nodesMode=false;
					cm.repaint();
				}
			}

			return;
		}
		//-*----------------------------------------------


		if (e.getSource() == originalCB) {
			original = originalCB.isSelected();
			cm.repaint();
			return;
		}
		if (e.getSource() == graphCB) {
			showMap = graphCB.isSelected();
			cm.repaint();
			return;
		}
		if (e.getSource() == backCB) {
			background = backCB.isSelected();
			cm.repaint();
			return;
		}

		if (e.getSource() == showNodesCB) {
			nodesMode = showNodesCB.isSelected();
			selectedNode = -1;
			nodes.setEnabled(true);
			cm.repaint();
			return;
		}

		if(e.getSource()== highTagsCB){
			tagMode=highTagsCB.isSelected();
			//System.out.println("Evento tag");
			if(tagMode){
				//System.out.println("Tag mode en True");
				thTagMode= false;
				thTagsCB.setSelected(false);
				thTagTF.setEnabled(false);
				if (!((String) tagList.getSelectedItem()).equals("Select Tag")){
					selectTagChanged = true;
					//tagMode=true;
					selectedTag=(String) tagList.getSelectedItem();}
			}
			cm.repaint();
			return;
		}

		if(e.getSource()== thTagsCB){
			thTagMode=thTagsCB.isSelected();
			//System.out.println("Evento tag");
			if(thTagMode){
				thTagTF.setEnabled(true);
				tagMode=false;
				highTagsCB.setSelected(false);
				thTag = Double.parseDouble(thTagTF.getText());
				//System.out.println("Tag mode en True");
				if (!((String) tagList.getSelectedItem()).equals("Select Tag")){
					selectTagChanged = true;
					//tagMode=true;
					selectedTag=(String) tagList.getSelectedItem();}
			}
			else
				thTagTF.setEnabled(false);
			cm.repaint();
			return;
		}


		if (e.getSource() == clustersCB) {
			zoneGenerated = true;
			cm.repaint();
			return;
		}

		if (e.getSource() == zoneCB) {
			zoneGenerated = zoneCB.isSelected();
			cm.repaint();
			return;
		}


		if (e.getSource() == jmiGenMap) {
			//original = true;
			showMap = true;
			//background = true;
			mapGenerated = false; 
			nodesMode = false; 
			selectNodeChanged = false; 
			showCluster = false; 
			tagMode = false; 
			selectTagChanged = false; 
			thTagMode=false;
			selectedNode = -1 ;
			selectedTag = null;      


			graphCB.setEnabled(false);
			showNodesCB.setEnabled(false);
			bm.buildMap();
			graphCB.setEnabled(true);
			showNodesCB.setEnabled(true);
			//highTagsCB.setEnabled(true);
			genComboNodes();
			genComboTag();
			tagList.setEnabled(true);
			highTagsCB.setEnabled(true);
			highTagsCB.setSelected(true);
			thTagsCB.setEnabled(true);
			thTagsCB.setSelected(false);
			tagMode =true;
			mapGenerated = true;
			zoneGenerated = false;
			cm.repaint();
			Date date = new Date();
			DateFormat hourdateFormat = new SimpleDateFormat("HH:mm:ss");
			//  String imgName = name+"_"+bm.threshold1+"_"+bm.threshold2+"_"+hourdateFormat.format(date);
			statusLabel.setText("Map for : "+name+" generated at: "+hourdateFormat.format(date));
			//cm.showNodeDetails();

			//cm.showMapInfo();

			jMIGetNodesContent.setEnabled(true);
			jMRelation.setEnabled(true);
			jmiGenZone.setEnabled(true);
			jmiGenHierCluster.setEnabled(true);
			jmiNodeComposition.setEnabled(true);

			return;
		}
	          
		if (e.getSource()==jmiGenAllMaps){
			statusLabel.setText("Generating all sequences maps in process...");
			generate12ImagesMaps();
			statusLabel.setText("Generation of all Sequence Maps Ended.");
			return;
		}

		if (e.getSource() == jmiCapture) {
			Date date = new Date();
			DateFormat hourdateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
			String imgName = name+"_"+bm.threshold1+"_"+bm.threshold2+"_"+bm.cutNode+"_"+hourdateFormat.format(date);
			cm.createImage(imgName);
			statusLabel.setText(imgName +" saved.");
			return;
		}

		if (e.getSource() == jmiGenCluster) {
			// Kmeans
			int k =Integer.parseInt( JOptionPane.showInputDialog("How many clusters?","4"));
			km= new Kmeans(k, bm.dimension, bm.imgTags);
			km.findMeans();
			//mapGenerated = false;
			showCluster=true;
			clustersCB.setEnabled(true);
			clustersCB.setSelected(true);
			cm.repaint();
			return;
		}

		if (e.getSource() == jmiGetCluster) {
			Kmeans km2;
			km2 = new Kmeans(1,bm.dimension, bm.imgTags);
			ArrayList<Float> coef = new ArrayList<Float>();
			int k=1; 

			if(k==1)
				FileMethods.saveFile("K;s2\n", "K_Variances_"+name, false);


			do {
				km2.setK(k);
				Float coefValue =km2.findMeansCoef();
				coef.add(coefValue); 
				FileMethods.saveFile(String.valueOf(k)+";"+String.valueOf(coefValue)+"\n", "K_Variances_"+name, true);
				++k;
				if ((k%10)==0) System.out.println("K="+k);
			}
			while(k<=100);

			statusLabel.setText("K Clusters for : "+name+" calculated.");

			return;
		}


		if(e.getSource()==  jmiGenFullHierCluster){
			HierClustering hc = new HierClustering(bm.dimension, bm.imgTags);
			cm.showHierCluster(hc.cluster());
		}


		//Sequence1 Vidrilo
		if(e.getSource() == jMiHybrid_Sq1){
			bm.readTags("/home/jcarlos2289/Documentos/VidriloTags/Sequence1/Vidrilo_Sequence1_Hybrid/sequence1visual", -0.00000001, 2389, "/home/jcarlos2289/Documentos/VidriloTags/Sequence1/Sequence1_CAT2.txt",1183, 10, 2000000000, "TrianglesPoints.txt");
			name= "VidriloSeq1_HybridAlexNet";
			setTitle(name);      
			cm.repaint();
			statusLabel.setText("Sequence : "+name+" selected.");
			seqNumber=1;
			jmiGenMap.setEnabled(true);
			resetDraw();
			return;
		}

		if(e.getSource() == jMIImgAlex_Sq1){
			bm.readTags("/home/jcarlos2289/Documentos/VidriloTags/Sequence1/Vidrilo_Sequence1_ImageNetAlexNet/sequence1visual", -0.00000001, 2389, "/home/jcarlos2289/Documentos/VidriloTags/Sequence1/Sequence1_CAT2.txt",1000, 10, 2000000000,"TrianglesPoints.txt");
			name= "VidriloSeq1_ImageNetAlexNet";
			setTitle(name);      
			cm.repaint();
			statusLabel.setText("Sequence : "+name+" selected.");
			seqNumber=1;
			jmiGenMap.setEnabled(true);
			resetDraw();
			return;
		}

		if(e.getSource() == jMIImgCaff_Sq1){
			bm.readTags("/home/jcarlos2289/Documentos/VidriloTags/Sequence1/Vidrilo_Sequence1_ImageNetCaffeNet/sequence1visual", -0.00000001, 2389, "/home/jcarlos2289/Documentos/VidriloTags/Sequence1/Sequence1_CAT2.txt",1000, 10, 2000000000,"TrianglesPoints.txt");
			name= "VidriloSeq1_ImageNetCaffeNet";
			setTitle(name);      
			cm.repaint();
			statusLabel.setText("Sequence : "+name+" selected.");
			seqNumber=1;
			jmiGenMap.setEnabled(true);
			resetDraw();
			return;
		}

		if(e.getSource() == jMIImgGNet_Sq1){
			bm.readTags("/home/jcarlos2289/Documentos/VidriloTags/Sequence1/Vidrilo_Sequence1_ImageNetGoogLeNet/sequence1visual", -0.00000001, 2389, "/home/jcarlos2289/Documentos/VidriloTags/Sequence1/Sequence1_CAT2.txt",1000, 10, 2000000000,"TrianglesPoints.txt");
			name= "VidriloSeq1_ImageNetGoogLeNet";
			setTitle(name);      
			cm.repaint();
			statusLabel.setText("Sequence : "+name+" selected.");
			jmiGenMap.setEnabled(true);
			resetDraw();
			return;
		}

		if(e.getSource() == jMIImgVGG_Sq1){
			bm.readTags("/home/jcarlos2289/Documentos/VidriloTags/Sequence1/Vidrilo_Sequence1_ImageNetVGG/sequence1visual", -0.00000001, 2389, "/home/jcarlos2289/Documentos/VidriloTags/Sequence1/Sequence1_CAT2.txt",1000, 10, 2000000000,"TrianglesPoints.txt");
			name= "VidriloSeq1_ImageNetVGG";
			setTitle(name);      
			cm.repaint();
			statusLabel.setText("Sequence : "+name+" selected.");
			seqNumber=1;
			jmiGenMap.setEnabled(true);
			resetDraw();
			return;
		}


		if(e.getSource() == jMIPlaAlex_Sq1){
			bm.readTags("/home/jcarlos2289/Documentos/VidriloTags/Sequence1/Vidrilo_Sequence1_PlacesAlexNet/sequence1visual", -0.00000001, 2389, "/home/jcarlos2289/Documentos/VidriloTags/Sequence1/Sequence1_CAT2.txt",205, 10, 2000000000,"TrianglesPoints.txt");
			name= "VidriloSeq1_PlacesAlexNet";
			setTitle(name);      
			cm.repaint();
			statusLabel.setText("Sequence : "+name+" selected.");
			seqNumber=1;
			jmiGenMap.setEnabled(true);
			resetDraw();
			return;
		}

		if(e.getSource() == jMIPlaGNet_Sq1){
			bm.readTags("/home/jcarlos2289/Documentos/VidriloTags/Sequence1/Vidrilo_Sequence1_PlacesGoogLeNet/sequence1visual", -0.00000001, 2389, "/home/jcarlos2289/Documentos/VidriloTags/Sequence1/Sequence1_CAT2.txt",205, 10, 2000000000,"TrianglesPoints.txt");
			name= "VidriloSeq1_PlacesGoogLeNet";
			setTitle(name);      
			cm.repaint();
			statusLabel.setText("Sequence : "+name+" selected.");
			seqNumber=1;
			jmiGenMap.setEnabled(true);
			resetDraw();
			return;
		}

		if(e.getSource() == jMIImgMX_Sq1){
			bm.readTags("/home/jcarlos2289/Documentos/VidriloTags/Sequence1/Vidrilo_Sequence1_ImageNetMXNet/sequence1visual", -0.00000001, 2389, "/home/jcarlos2289/Documentos/VidriloTags/Sequence1/Sequence1_CAT2.txt",1000, 10, 2000000000,"TrianglesPoints.txt");
			name= "VidriloSeq1_ImageNetMXNet";
			setTitle(name);      
			cm.repaint();
			statusLabel.setText("Sequence : "+name+" selected.");
			seqNumber=1;
			jmiGenMap.setEnabled(true);
			resetDraw();
			return;
		}

		if(e.getSource() == jMIImgMX21K_Sq1){
			bm.readTags("/home/jcarlos2289/Documentos/VidriloTags/Sequence1/Vidrilo_Sequence1_ImageNetMXNet21K_750/sequence1visual", -1.0000000, 2389, "/home/jcarlos2289/Documentos/VidriloTags/Sequence1/Sequence1_CAT2.txt",750, 10, 2000000000,"TrianglesPoints.txt");
			name= "VidriloSeq1_ImageNetMXNet21K";
			setTitle(name);      
			cm.repaint();
			statusLabel.setText("Sequence : "+name+" selected.");
			seqNumber=1;
			jmiGenMap.setEnabled(true);
			resetDraw();
			return;
		}

	
		if(e.getSource() == jMiHybrid_Sq2){
			bm.readTags("/home/jcarlos2289/Documentos/VidriloTags/Sequence2/Vidrilo_Sequence2_Hybrid/sequence2visual", -0.00000001, 4579, "/home/jcarlos2289/Documentos/VidriloTags/Sequence2/Sequence2_CAT2.txt",1183, 10, 2000000000, "TrianglesPoints.txt");
			name= "VidriloSeq2_HybridAlexNet";
			setTitle(name);      
			cm.repaint();
			statusLabel.setText("Sequence : "+name+" selected.");
			seqNumber=2;
			jmiGenMap.setEnabled(true);
			resetDraw();
			return;
		}

		if(e.getSource() == jMIImgAlex_Sq2){
			bm.readTags("/home/jcarlos2289/Documentos/VidriloTags/Sequence2/Vidrilo_Sequence2_ImageNetAlexNet/sequence2visual", -0.00000001, 4579, "/home/jcarlos2289/Documentos/VidriloTags/Sequence2/Sequence2_CAT2.txt",1000, 10, 2000000000,"TrianglesPoints.txt");
			name= "VidriloSeq2_ImageNetAlexNet";
			setTitle(name);      
			cm.repaint();
			statusLabel.setText("Sequence : "+name+" selected.");
			seqNumber=2;
			jmiGenMap.setEnabled(true);
			resetDraw();
			return;
		}

		if(e.getSource() == jMIImgCaff_Sq2){
			bm.readTags("/home/jcarlos2289/Documentos/VidriloTags/Sequence2/Vidrilo_Sequence2_ImageNetCaffeNet/sequence2visual", -0.00000001, 4579, "/home/jcarlos2289/Documentos/VidriloTags/Sequence2/Sequence2_CAT2.txt",1000, 10, 2000000000,"TrianglesPoints.txt");
			name= "VidriloSeq2_ImageNetCaffeNet";
			setTitle(name);      
			cm.repaint();
			statusLabel.setText("Sequence : "+name+" selected.");
			seqNumber=2;
			jmiGenMap.setEnabled(true);
			resetDraw();
			return;
		}

		if(e.getSource() == jMIImgGNet_Sq2){
			bm.readTags("/home/jcarlos2289/Documentos/VidriloTags/Sequence2/Vidrilo_Sequence2_ImageNetGoogLeNet/sequence2visual", -0.00000001, 4579, "/home/jcarlos2289/Documentos/VidriloTags/Sequence2/Sequence2_CAT2.txt",1000, 10, 2000000000,"TrianglesPoints.txt");
			name= "VidriloSeq2_ImageNetGoogLeNet";
			setTitle(name);      
			cm.repaint();
			statusLabel.setText("Sequence : "+name+" selected.");
			jmiGenMap.setEnabled(true);
			resetDraw();
			return;
		}

		if(e.getSource() == jMIImgVGG_Sq2){
			bm.readTags("/home/jcarlos2289/Documentos/VidriloTags/Sequence2/Vidrilo_Sequence2_ImageNetVGG/sequence2visual", -0.00000001, 4579, "/home/jcarlos2289/Documentos/VidriloTags/Sequence2/Sequence2_CAT2.txt",1000, 10, 2000000000,"TrianglesPoints.txt");
			name= "VidriloSeq2_ImageNetVGG";
			setTitle(name);      
			cm.repaint();
			statusLabel.setText("Sequence : "+name+" selected.");
			seqNumber=2;
			jmiGenMap.setEnabled(true);
			resetDraw();
			return;
		}

		if(e.getSource() == jMIPlaAlex_Sq2){
			bm.readTags("/home/jcarlos2289/Documentos/VidriloTags/Sequence2/Vidrilo_Sequence2_PlacesAlexNet/sequence2visual", -0.00000001, 4579, "/home/jcarlos2289/Documentos/VidriloTags/Sequence2/Sequence2_CAT2.txt",205, 10, 2000000000,"TrianglesPoints.txt");
			name= "VidriloSeq2_PlacesAlexNet";
			setTitle(name);      
			cm.repaint();
			statusLabel.setText("Sequence : "+name+" selected.");
			seqNumber=2;
			jmiGenMap.setEnabled(true);
			resetDraw();
			return;
		}

		if(e.getSource() == jMIPlaGNet_Sq2){
			bm.readTags("/home/jcarlos2289/Documentos/VidriloTags/Sequence2/Vidrilo_Sequence2_PlacesGoogLeNet/sequence2visual", -0.00000001, 4579, "/home/jcarlos2289/Documentos/VidriloTags/Sequence2/Sequence2_CAT2.txt",205, 10, 2000000000,"TrianglesPoints.txt");
			name= "VidriloSeq2_PlacesGoogLeNet";
			setTitle(name);      
			cm.repaint();
			statusLabel.setText("Sequence : "+name+" selected.");
			seqNumber=2;
			jmiGenMap.setEnabled(true);
			resetDraw();
			return;
		}

		if(e.getSource() == jMIImgMX_Sq2){
			bm.readTags("/home/jcarlos2289/Documentos/VidriloTags/Sequence2/Vidrilo_Sequence2_ImageNetMXNet/sequence2visual", -0.00000001, 4579, "/home/jcarlos2289/Documentos/VidriloTags/Sequence2/Sequence2_CAT2.txt",1000, 10, 2000000000,"TrianglesPoints.txt");
			name= "VidriloSeq2_ImageNetMXNet";
			setTitle(name);      
			cm.repaint();
			statusLabel.setText("Sequence : "+name+" selected.");
			seqNumber=2;
			jmiGenMap.setEnabled(true);
			resetDraw();
			return;
		}

		if(e.getSource() == jMIImgMX21K_Sq2){
			bm.readTags("/home/jcarlos2289/Documentos/VidriloTags/Sequence2/Vidrilo_Sequence2_ImageNetMXNet21K_750/sequence2visual", 0.0000000, 4579, "/home/jcarlos2289/Documentos/VidriloTags/Sequence2/Sequence2_CAT2.txt",750, 10, 2000000000,"TrianglesPoints.txt");
			name= "VidriloSeq2_ImageNetMXNet21K";
			setTitle(name);      
			cm.repaint();
			statusLabel.setText("Sequence : "+name+" selected.");
			seqNumber=2;
			jmiGenMap.setEnabled(true);
			resetDraw();
			return;
		}

		//---------------------------------------------------SEQ2---END-------------------------------

		///Para generacion de relacion entre modelos 
		if(e.getSource() == jMIImgAlex_Sq6){
			//bm.readTags("/home/jcarlos2289/Documentos/VidriloTags/Sequence1/Vidrilo_Sequence1_ImageNetAlexNet/sequence1visual", -0.00000001, 2389, "/home/jcarlos2289/Documentos/VidriloTags/Sequence1/Sequence1_CAT2.txt",1000, 10, 2000000000);
			cm.showNodeRelation("/home/jcarlos2289/Documentos/VidriloTags/Sequence"+String.valueOf(seqNumber)+"/Vidrilo_Sequence"+String.valueOf(seqNumber)+"_ImageNetAlexNet/");
			return;
		}

		if(e.getSource() == jMIImgCaff_Sq6){
			//bm.readTags("/home/jcarlos2289/Documentos/VidriloTags/Sequence1/Vidrilo_Sequence1_ImageNetCaffeNet/sequence1visual", -0.00000001, 2389, "/home/jcarlos2289/Documentos/VidriloTags/Sequence1/Sequence1_CAT2.txt",1000, 10, 2000000000);
			cm.showNodeRelation("/home/jcarlos2289/Documentos/VidriloTags/Sequence"+String.valueOf(seqNumber)+"/Vidrilo_Sequence"+String.valueOf(seqNumber)+"_ImageNetCaffeNet/");
			return;
		}

		if(e.getSource() == jMIImgGNet_Sq6){
			//bm.readTags("/home/jcarlos2289/Documentos/VidriloTags/Sequence1/Vidrilo_Sequence1_ImageNetGoogLeNet/sequence1visual", -0.00000001, 2389, "/home/jcarlos2289/Documentos/VidriloTags/Sequence1/Sequence1_CAT2.txt",1000, 10, 2000000000);
			cm.showNodeRelation("/home/jcarlos2289/Documentos/VidriloTags/Sequence"+String.valueOf(seqNumber)+"/Vidrilo_Sequence"+String.valueOf(seqNumber)+"_ImageNetGoogLeNet/");
			return;
		}

		if(e.getSource() == jMIImgVGG_Sq6){
			//bm.readTags("/home/jcarlos2289/Documentos/VidriloTags/Sequence1/Vidrilo_Sequence1_ImageNetVGG/sequence1visual", -0.00000001, 2389, "/home/jcarlos2289/Documentos/VidriloTags/Sequence1/Sequence1_CAT2.txt",1000, 10, 2000000000);
			cm.showNodeRelation("/home/jcarlos2289/Documentos/VidriloTags/Sequence"+String.valueOf(seqNumber)+"/Vidrilo_Sequence"+String.valueOf(seqNumber)+"_ImageNetVGG/");
			return;
		}

		if(e.getSource() == jMIImgMX_Sq6){
			cm.showNodeRelation("/home/jcarlos2289/Documentos/VidriloTags/Sequence"+String.valueOf(seqNumber)+"/Vidrilo_Sequence"+String.valueOf(seqNumber)+"_ImageNetMXNet/");
			return;
		}

		if(e.getSource() == jMIImgMX21K_Sq6){
			cm.showNodeRelation("/home/jcarlos2289/Documentos/VidriloTags/Sequence"+String.valueOf(seqNumber)+"/Vidrilo_Sequence"+String.valueOf(seqNumber)+"_ImageNetMXNet21K/");
			return;
		}

		//--------------------------Nodes Content

		if(e.getSource()== jMIGetNodesContent){
			int o = 1;
			for (Node nd : bm.map.nodes) {
				FileMethods.saveFile(nd.getNodesContentRows(), "Node_" + String.valueOf(o), false);
				//FileMethods.saveFile(nd.getNodeMeanData(), "NodeMean_" + String.valueOf(o), false);
				//FileMethods.saveFile(nd.getNodeMeanData(), "NodeMean", true);
				o++;
			}
		}

		if(e.getSource()== jmiGenZone){
			bm.map.generateZones(Double.parseDouble(th1.getText()));
			cm.showZoneInfomation();
			//bm.map.generateZones();
			zoneGenerated = true;
			zoneCB.setSelected(true);
			zoneCB.setEnabled(true);
			jMIGetZonesContent.setEnabled(true);
			jmiShowZoneChart.setEnabled(true);
			jmiShowZoneChart_2.setEnabled(true);
			jmiShowZoneChart_3.setEnabled(true);
			jmiShowZoneChart_4.setEnabled(true);
			jmiZoneResume.setEnabled(true);
			statusLabel.setText("Zones have been generated.");
			cm.repaint();
		}

		if(e.getSource()==jmiGenHierCluster){
			cm.showHierCluster(bm.map.getHierCluster());
		}

		if(e.getSource()==jmiNodeComposition){
			cm.showNodeComposition();
			//cm.showNodeDetails(); //top10
		}

		if (e.getSource()== jMIGetZonesContent){
			// Date date = new Date();
			// DateFormat hourdateFormat = new SimpleDateFormat("MM-dd_HH:mm");

			double thh = bm.map.cutTh;
			String colofon ="_"+name +"_TH_"+ thh+"_Zones_"+ bm.map.zones.size();

			//Formato 1 ZoneTag # CantNodos VidriloCat1 VidriloCat2 .....VidriloCat10
			String[] tags = {"HallEntrance","ProfessorRoom","StudentsRoom","TechnicalRoom","Toilet","Secretary","Videoconference","Warehouse","ElevatorArea","Corridor"};
			String h="N;Zone_Tag;Num;Nodes";
			for(String g: tags){
				h+=";"+g;
			}
			h+="\n";
			FileMethods.saveFile(h, "ZonesContent"+ colofon, false);
			int g =1;
			for(Zone z: bm.map.zones){
				FileMethods.saveFile(String.valueOf(g) +z.getZoneContent(tags)+"\n",  "ZonesContent"+ colofon , true);
				g++;
			}
			//Otros Formatos
			bm.map.printMapCategoriesInformation(new String[]{"Cat1-vs-Tag"+colofon,"Cat1-vs-ZoneTag"+colofon,"Cat2-vs-Tag"+colofon,"Cat2-vs-ZoneTag"+colofon});
			bm.map.printZonesDataset(new String[]{"Cat1-vs-TagSum"+colofon,"Cat1-vs-ZoneTagSum"+colofon,"Cat2-vs-TagSum"+colofon,"Cat2-vs-ZoneTagSum"+colofon});

			//CatDeepTagSum Cat2ZoneTagSum CatZoneTagSum Cat2DeepTagSum


			/*//Formato 1 idImg VidriloCat DeepTag
            	bm.map.printMapCategoriesInformation("CatDeepTag"+colofon,1);

            	//Formato 2 idImg VidriloCat DeepTag
            	bm.map.printMapCategoriesInformation("CatZoneTag"+colofon,2);


            	//Formato 3 idImg VidriloCat DeepTag
            	bm.map.printMapCategoriesInformation("Cat2ZoneTag"+colofon,3);


            	//Formato 4 idImg VidriloCat DeepTag
            	bm.map.printMapCategoriesInformation("Cat2DeepTag"+colofon,4);*/


		}

		if(e.getSource()== jmiZoneResume){
			cm.showZoneInfomation();
		}

		if (e.getSource() ==  jmiShowZoneChart){      //Cat1 Tag 
			cm.showZonesCharts(1);
		}

		if (e.getSource() ==  jmiShowZoneChart_2){   // Cat1 ZoneTag  
			cm.showZonesCharts(2);
		}

		if (e.getSource() ==  jmiShowZoneChart_3){    //Cat2 Tag 
			cm.showZonesCharts(3);
		}

		if (e.getSource() ==  jmiShowZoneChart_4){  //Cat2 Zonetag    
			cm.showZonesCharts(4);
		}

		if(e.getSource()== jmiCategoryMap){
			CatDistanceCalculator cdc = new CatDistanceCalculator(bm.imgTags);
			cdc.createMap();
			cdc.printMatrix(name);
		}

	}


	public void resetDraw(){
		mapGenerated = false; 
		nodesMode = false; 
		selectNodeChanged = false; 
		showCluster = false; 
		tagMode = false; 
		selectTagChanged = false; 
		thTagMode=false;
		selectedNode = -1 ;
		selectedTag = null;      
		
	}
	
	
	private void generate12ImagesMaps() {
		// TODO Auto-generated method stub
		
		    Date date = new Date();
	        DateFormat hourdateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
	        String imgName;
	        float thr1 = (float) 0.001;
	        float thr2 = (float) 0.015;
	        int cn =1;
	        
	        thr1 = Float.parseFloat(th1.getText());
	        thr2 = Float.parseFloat(th2.getText());
	        cn = Integer.parseInt(th3.getText());
	       
	        bm.setThreshold1(thr1);
	        bm.setThreshold2(thr2);
	        bm.setCutNode(cn);
	        DecimalFormatSymbols simbol = new DecimalFormatSymbols();
	        simbol.setDecimalSeparator('.');
	        DecimalFormat formateador = new DecimalFormat("####.####",simbol);
	       
	        //Solo lo deje como ejemplo

	        //Cloudy
	        //1
	        //bm.readTags(dataSetPath + "KTH_IDOL/KTH_Minnie/min_cloudy1/min_cloudy1_HybridAlexNet/IDOL_MINNIE_Cl1_", -0.00000001, 915, dataSetPath + "KTH_IDOL/KTH_Minnie/min_cloudy1/IDOL_MINNIE_Cl1.txt",1183, 5, 2000000000);
	        name= "MinnieCloudy1_HybridAlexNet";
	        bm.buildMap();
	        mapGenerated = true;
	        original=false;
	        cm.repaint();
	        date = new Date();
	        imgName = name+"_"+formateador.format(bm.threshold1)+"_"+formateador.format(bm.threshold2)+"_"+hourdateFormat.format(date);
	        cm.createImage(imgName);
				
	}

}
