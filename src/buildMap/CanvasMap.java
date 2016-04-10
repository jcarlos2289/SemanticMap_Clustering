package buildMap;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
//import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
//import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.MultiplePiePlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.TableOrder;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
/*
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;*/
import com.itextpdf.text.pdf.PdfWriter;



public class CanvasMap extends JPanel implements MouseListener {
	private static final long serialVersionUID = 1366050590724868148L;
	Gui gui;
	ImageIcon img2;
	double xmean=5.9199204755, ymean=15.7735103837;
	double zoomFactor=28;
	double xdesp, ydesp;
	
	int radius=15;
	
	JDialog tags=null, nodeInfo=null, top=null, nodeDetailsDialog =null, mapInfoDialog=null, tagCloudDialog=null, clusterDialog=null, zoneDialog=null, nodeRelationDialog=null, compositionDialog=null;
	JDialog grafDialog=null, zoneInfo=null, zoneChartDialog = null;
	
	public CanvasMap (Gui ig) {
		img2 = new ImageIcon(Toolkit.getDefaultToolkit().getImage("BuildingA_1.png"));
		gui=ig;
		xdesp=(gui.width/4.3)-65;
		ydesp=(gui.height/2.68)-74;
	    addMouseListener(this);
	    
	}
	
	private double distance (MouseEvent evt, Node n) {
		double dist=0.0;
		
		
		//dist = Math.pow(evt.getX()-(int)(zoomFactor*(n.representative.xcoord-xmean)+xdesp+radius), 2.0);
		//dist += Math.pow(evt.getY()-(int)(-zoomFactor*(n.representative.ycoord-ymean)+ydesp+radius), 2.0);
		
		dist = Math.pow(evt.getX()-(int)((n.representative.xcoord)+radius), 2.0);
		dist += Math.pow(evt.getY()-(int)((n.representative.ycoord)+radius), 2.0);
		
		
		return Math.sqrt(dist);
	}
	
	public  float getMaxWidth(){
        float newX=0;
		newX = (float) (((float)(img2.getIconWidth() -xdesp))/zoomFactor +xmean);
		return newX;
	}
	
	public  float getMaxHeight(){
        float newY=0;
		newY = (float) (((float)(img2.getIconHeight() -ydesp))/zoomFactor +ymean);
		return newY;
	}
	
	public float MaxDistance(){
			
		float dmax = (float) Math.sqrt(Math.pow(getMaxWidth(), 2)+ Math.pow(getMaxHeight(), 2));
		
		return dmax;
	}
	

	public void paint(Graphics g) {
		int x,y, xAnt=0, yAnt=0;
		boolean firstTime=true;
		Graphics2D g2d = (Graphics2D) g;
		
		paintComponent(g);
		g.setColor(new Color(255,255,255));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		if (gui.background)
			img2.paintIcon(this, g, 0, 0);
		g.setColor(new Color(5,0,55));
		g2d.setStroke(new BasicStroke(2.0f));
		if (gui.original) {
			for (int i=0; i<gui.bm.imgTags.size(); i++) {
				if (gui.bm.imgTags.get(i).xcoord!=-1) {
					//x=(int)(zoomFactor*(gui.bm.imgTags.get(i).xcoord-xmean)+xdesp);
					//y=(int)(-zoomFactor*(gui.bm.imgTags.get(i).ycoord-ymean)+ydesp);
					x= (int) gui.bm.imgTags.get(i).xcoord;
					y= (int) gui.bm.imgTags.get(i).ycoord;
					
					if (firstTime) {
						xAnt=x;
						yAnt=y;
						firstTime=false;
					}
					if (Math.sqrt(Math.pow(x-xAnt, 2.0)+Math.pow(y-yAnt,2.0)) < 30.0) 
						g2d.drawLine(xAnt, yAnt, x, y);
					xAnt=x;
					yAnt=y;
				}				
			}
		}
		g.setColor(new Color(0,0,255));
		if (gui.mapGenerated && gui.showMap) {
		
			if (gui.nodesMode && gui.selectedNode!=-1) {
				Node selectn = gui.bm.map.nodes.get(gui.selectedNode);
				for (ImageTags img:selectn.images) {
				//	x=(int)(zoomFactor*(img.xcoord-xmean)+xdesp);
					//y=(int)(-zoomFactor*(img.ycoord-ymean)+ydesp);		
					x= (int) img.xcoord;
					y= (int) img.ycoord;
					
					
			        g.drawOval(x, y, radius-6, radius-6);
			        g.fillOval(x, y, radius-6, radius-6);
				}
				g.setColor(new Color(0,255,255));
				//x=(int)(zoomFactor*(selectn.representative.xcoord-xmean)+xdesp);
				//y=(int)(-zoomFactor*(selectn.representative.ycoord-ymean)+ydesp);
				
				x = (int)selectn.representative.xcoord;
				y = (int)selectn.representative.ycoord;
				
				g.setColor(new Color(0,193,0));
				//g.drawPolygon(selectn.limits.getXArray(), selectn.limits.getYArray(), 3);
				
				g.setColor(new Color(0,255,255));
		        g.drawOval(x, y, radius-4, radius-4);
				g.setColor(new Color(255,0,255));
				for (Map.Edge e:gui.bm.map.edges) {
					if (e.a==selectn || e.b==selectn) {
						
						//xAnt=(int)(zoomFactor*(e.a.representative.xcoord-xmean)+xdesp);
						//yAnt=(int)(-zoomFactor*(e.a.representative.ycoord-ymean)+ydesp);
						//x=(int)(zoomFactor*(e.b.representative.xcoord-xmean)+xdesp);
						//y=(int)(-zoomFactor*(e.b.representative.ycoord-ymean)+ydesp);
						
						xAnt = (int) e.a.representative.xcoord;
						yAnt = (int) e.a.representative.ycoord;
						x = (int) e.b.representative.xcoord;
						y = (int) e.b.representative.ycoord;
						
						
						g2d.drawLine(xAnt+radius/2, yAnt+radius/2, x+radius/2, y+radius/2);
					}
				}
				if (gui.selectNodeChanged) {
					if (tags!=null) {
						 tags.dispose();
	                     nodeInfo.dispose();
	                    // graf.dispose();
	                     top.dispose();
	                     tagCloudDialog.dispose();
	                     tags=null;
	                     nodeInfo=null;
	                    // graf=null;
	                     top=null;
	                     tagCloudDialog=null;
					}
					showInfo(selectn);
					gui.selectNodeChanged=false;
				}
			}
			
			
			else {
				
				
			
				
				for (Map.Edge e:gui.bm.map.edges) {
					//xAnt=(int)(zoomFactor*(e.a.representative.xcoord-xmean)+xdesp);
					//yAnt=(int)(-zoomFactor*(e.a.representative.ycoord-ymean)+ydesp);
					//x=(int)(zoomFactor*(e.b.representative.xcoord-xmean)+xdesp);
					//y=(int)(-zoomFactor*(e.b.representative.ycoord-ymean)+ydesp);
					
					xAnt = (int) e.a.representative.xcoord;
					yAnt = (int) e.a.representative.ycoord;
					x = (int) e.b.representative.xcoord;
					y = (int) e.b.representative.ycoord;
					
					
					g2d.drawLine(xAnt+radius/2, yAnt+radius/2, x+radius/2, y+radius/2);
				}
			//	int number = 1;
				for (Node n:gui.bm.map.nodes) {
					
					if(n.getSize()!=0){
					
					//x=(int)(zoomFactor*(n.representative.xcoord-xmean)+xdesp);
					//y=(int)(-zoomFactor*(n.representative.ycoord-ymean)+ydesp);
					
					x = (int) n.representative.xcoord;
					y = (int) n.representative.ycoord;
			       
					
			        g.setColor(new Color(255,255,255));
			        g.fillOval(x, y, radius-5, radius-5);
			        
			        g.setColor(new Color(0,0,255));
			        g.drawOval(x, y, radius-5, radius-5);
			        
			      //  g.setColor(new Color(0,0,0));
			      //  Font oldFont=new Font("Monospaced", Font.PLAIN, 8);
			       // g.setFont(oldFont);
			       // g.drawString(n.nodeName, x+3, y+10);
			      //  number++;
			        
			      //  g.drawPolygon(n.limits.getXArray(), n.limits.getYArray(), 3);
					//g.fillPolygon(nd.limits.getXArray(), nd.limits.getYArray(), 3);
			        
			        
				}

				}
				
				if(gui.zoneGenerated){
					//---------------------------------------------------
					//System.out.print("Drawing Zones");
					ArrayList<String> cats = new ArrayList<String>();
					for(Zone zn : gui.bm.map.zones){
						if(!cats.contains(zn.name))						
						   cats.add(zn.name);
					}
					
				
					HashMap<String, Color> clave = new HashMap<String,Color>();
									
					
					int colors[][] = new int[cats.size()][3];  //modifique la longuitud del arreglo anteriormente era gui.bm.map.zones.size()
					
					for (int i = 0; i <colors.length ; i++) {
						for (int j = 0; j < colors[i].length; j++) {
							colors [i][j] = (int)(Math.random()*255);
							}
						}
					
					
					for (int i = 0; i < cats.size(); i++) {
						clave.put(cats.get(i), (new Color(colors[i][0],colors[i][1],colors[i][2])));
					} 
						
					int coord_y = 390;
					int coord_x = 255;
					 Font oldFont2=getFont();
				        Font fuente=new Font("Monospaced", Font.PLAIN, 12);
				        g.setFont(fuente);
				       
				       
					
					
					for (java.util.Map.Entry<String, Color> entry : clave.entrySet()) {
					    
						g.setColor(entry.getValue());
						g.drawOval(coord_x, coord_y, radius-1, radius-1);
						g.fillOval(coord_x, coord_y, radius-1, radius-1);
						
						g.setColor(new Color(0,0,0));
						g.drawString(entry.getKey(), (int) (coord_x+1.5*(radius-1)), coord_y+12);
						coord_y+= 20;
					}
					 g.setFont(oldFont2);
					
					//	int o = 0;			
					for(Zone zn : gui.bm.map.zones){
						// g.setColor(new Color(colors[o][0],colors[o][1],colors[o][2]));					
								 
						 for(Node nd : zn.areas){
							g.setColor(clave.get(zn.name));
							
							
							 g.drawOval((int)nd.representative.xcoord, (int) nd.representative.ycoord, radius, radius);
							 g.fillOval((int)nd.representative.xcoord, (int) nd.representative.ycoord, radius, radius);
							 g.drawOval((int)nd.representative.xcoord, (int) nd.representative.ycoord, 1, 1);
							// g.drawString(zn.name, (int)nd.representative.xcoord,  (int)nd.representative.ycoord);
							//System.out.println(String.valueOf(nd.representative.xcoord));
							
							//g.drawPolygon(nd.limits.getXArray(), nd.limits.getYArray(), 3);
							//g.fillPolygon(nd.limits.getXArray(), nd.limits.getYArray(), 3);
							
						}
					//o++;
					
					}
				}
				}
			
			
			
		}
		//else{
		//Para TagMode  //orden y escala termica comparando el valor con los valores de esa etiqueta en los demas nodos, una sola escala termica para todo el mapa
		if(gui.tagMode && gui.mapGenerated){
			if(gui.selectTagChanged && !gui.selectedTag.equals(null)){
				//Proceso para mostrar los highTags
				
			
				
			//	ArrayList<String> aux;
				ArrayList<Float> values = new ArrayList<Float>();
			//	ArrayList<Float> valuesOrder = new ArrayList<Float>();
				for(Node n:gui.bm.map.nodes){
					values.add(n.histoMean.get(gui.selectedTag));
				}
				Collections.sort(values);
				
				float max = values.get(values.size()-1);
				float min = values.get(0);
				
				
				for(Node n:gui.bm.map.nodes){
					values.add(n.histoMean.get(gui.selectedTag));
					g.setColor(produceHeatColor(n.histoMean.get(gui.selectedTag)  , min, max));
					//g.setColor(produceHeatColor(((n.histoMean.get(gui.selectedTag)-min)/(max-min))  , 0, 1));
					//x=(int)(zoomFactor*(n.representative.xcoord-xmean)+xdesp);
				//	y=(int)(-zoomFactor*(n.representative.ycoord-ymean)+ydesp); 
					
					x = (int) n.representative.xcoord;
					y = (int) n.representative.ycoord;
					
					
					g.drawOval(x, y, radius, radius);
				    g.fillOval(x, y, radius, radius);
				}
				
				 g.setColor(Color.BLACK);
			        
			        Font oldFont=getFont();
			        g.setFont(oldFont);
			        g.drawString(gui.selectedTag, 223, 30);
				
						
				
				
				ArrayList<String> aux;
				
				for(Node n:gui.bm.map.nodes){
					aux = n.getTop10Nodes();
					if(aux.contains(gui.selectedTag)){
					//	x=(int)(zoomFactor*(n.representative.xcoord-xmean)+xdesp);
					//	y=(int)(-zoomFactor*(n.representative.ycoord-ymean)+ydesp);
						
						x=(int)(n.representative.xcoord);
						y=(int)(n.representative.ycoord);
						
				        //g.drawOval(x, y, radius, radius);
					//	g.setColor(new Color(0,255,0));
				      //  g.drawRect(x, y, radius, radius);
				    //    g.fillRect(x, y, radius, radius);
				        int h = aux.indexOf(gui.selectedTag)+1;
				        g.setColor(Color.BLACK);
				        
				        Font oldFont2=getFont();
				        Font fuente=new Font("Monospaced", Font.PLAIN, 10);
				        g.setFont(fuente);
				        g.drawString(String.valueOf(h), x+1, y+10);
				        g.setFont(oldFont2);
				        g.drawString(gui.selectedTag, 223, 30);
				        
				        
					}
				}
				
				gui.selectTagChanged=false;
								
			       // gui.selectTagChanged=false;
				
			}
		}	
		
		
		//para thTag Mode  //orden y escala termica en comparacion con los valores del nodo, una escala termca para cada nodo
		if(gui.thTagMode && gui.mapGenerated ){
			if(gui.selectTagChanged && !gui.selectedTag.equals(null) && gui.thTag>=0){
				//Proceso para mostrar los highTags
				
				 ArrayList<ArrayList<String>> aux2 = new ArrayList<ArrayList<String>>();
			     ArrayList<ArrayList<Float>> auxFloat = new ArrayList<ArrayList<Float>>();
				
				for(Node n:gui.bm.map.nodes){
					  aux2.add(n.getTopNodes((float)gui.thTag));
				      auxFloat.add(n.getTopNodesValues((float)gui.thTag));
				}
				
				
				ArrayList<Integer> foundTags = new ArrayList<Integer>();
				ArrayList<Integer> foundTagsNode = new ArrayList<Integer>();
				
				int z = 0;
				for (ArrayList<String> listaTags : aux2) {
					if(!listaTags.isEmpty()){
						if(listaTags.contains(gui.selectedTag)){
							int h = listaTags.indexOf(gui.selectedTag);
							foundTags.add(h);
							foundTagsNode.add(z);
						}
				}
					++z;
				}
				
				if(!foundTags.isEmpty()){
				//ArrayList<Float> foundValues = new ArrayList<Float>();
				//ArrayList<Float> foundValuesOrder = new ArrayList<Float>();

				for (int i = 0; i < foundTagsNode.size(); i++) {
					//foundValues.add(auxFloat.get(foundTagsNode.get(i)).get(foundTags.get(i)));
					//foundValuesOrder.add(auxFloat.get(foundTagsNode.get(i)).get(foundTags.get(i)));
					int max = aux2.get(foundTagsNode.get(i)).size();
					int min = 0;
					Color heatColor = produceHeatColor(max-(foundTags.get(i)-min), min, max);
					g.setColor(heatColor);
					//x=(int)(zoomFactor*(gui.bm.map.nodes.get(foundTagsNode.get(i)).representative.xcoord-xmean)+xdesp);
					//y=(int)(-zoomFactor*(gui.bm.map.nodes.get(foundTagsNode.get(i)).representative.ycoord-ymean)+ydesp);
					
					x = (int) gui.bm.map.nodes.get(foundTagsNode.get(i)).representative.xcoord;
					y = (int) gui.bm.map.nodes.get(foundTagsNode.get(i)).representative.ycoord;
					
			        //g.drawOval(x, y, radius, radius);
			        //g.setColor(new Color(251,255,97));
			        g.drawOval(x, y, radius, radius);
			        g.fillOval(x, y, radius, radius);
					 
					
					
					
				}
				
				 g.setColor(Color.BLACK);
			        
			        Font oldFont=getFont();
			       // Font fuente=new Font("Monospaced", Font.PLAIN, 10);
			      //  g.setFont(fuente);
			       // g.drawString(String.valueOf(h), x+1, y+10);
			        g.setFont(oldFont);
			        g.drawString(gui.selectedTag, 223, 30);

				
					
				}	
					
			
			}
		}
	        //Recorro para encontrar el mayor
	        //Recorro para ver en quje nodo esta el tag
	        //busco su indice y uso el valor para generar la escala termica
	        //guardo solo las coordenadas del nodo y su color para despues de solo pintarlo
		
				
			if (gui.showCluster){
				
				int colors[][] = new int[gui.km.k][3];
				
				for (int i = 0; i <colors.length ; i++) {
					for (int j = 0; j < colors[i].length; j++) {
						colors [i][j] = (int)(Math.random()*255);
						//colors [i][1] = (int)(Math.random()*255);
						//colors [i][2] = (int)(Math.random()*255);
					}
					}
				
										
				for (int j = 0; j <gui.km.obtained.size(); j++) {
					
					Point point = gui.km.obtained.get(j);
					g.setColor(new Color(colors[gui.km.near.get(j)][0],colors[gui.km.near.get(j)][1],colors[gui.km.near.get(j)][2]));
					//x=(int)(zoomFactor*(point.xcoord-xmean)+xdesp);
					//y=(int)(-zoomFactor*(point.ycoord-ymean)+ydesp);
					
					x = (int)point.xcoord;
					y = (int)point.ycoord;
							
			        g.drawOval(x, y, 2, 2);
									
				}
				
				g.setColor(new Color(0,0,255));
				//g.draw3DRect(80, 100, 30 ,15, true);
				g.drawRect(360, 85, 50 ,20);
				g.fillRect(360, 85, 50 ,20);
				g.setColor(new Color(255,255,255));
				g.drawString("k = " + gui.km.k, 361, 100);
				//g.drawString(str, xAnt, yAnt);
								
			}
			
		//}
	}
	
	private Color produceHeatColor(float v, float vmin, float vmax) {
		// TODO Auto-generated method stub
		
		Color c = new Color(1,1,1); // white
		   double dv;
		   double r=1, g=1, b=1;

		   if (v < vmin)
		      v = vmin;
		   if (v > vmax)
		      v = vmax;
		   dv = vmax - vmin;

		   if (v < (vmin + 0.25 * dv)) {
		     r = 0;
		      g = 4 * (v - vmin) / dv;
		   } else if (v < (vmin + 0.5 * dv)) {
		     r = 0;
		      b = 1 + 4 * (vmin + 0.25 * dv - v) / dv;
		   } else if (v < (vmin + 0.75 * dv)) {
		      r = 4 * (v - vmin - 0.5 * dv) / dv;
		     b = 0;
		   } else {
		     g = 1 + 4 * (vmin + 0.75 * dv - v) / dv;
		      b = 0;
		   }
		/*   System.out.println("------------------------");
		   System.out.println(Math.round(r*255));
		   System.out.println(Math.round(g*255));
		   System.out.println(Math.round(b*255));
		   System.out.println("------------------------");*/
		   int ir = (int) Math.round(r*255);
		   int ig = (int) Math.round(g*255);
		   int ib = (int) Math.round(b*255);
		   r=1;
		   g=1;
		   b=1;
		   c = new Color(ir,ig,ib);
		   
		   return(c);
		}

	public void showInfo (Node sel) {
		JLabel textArea, presenceData;
		JScrollPane scroll, scroll2;
	
		gui.selectedNode=gui.bm.map.nodes.indexOf(sel);
		//gui.showNodes.setSelected(true);
		gui.nodesMode=true;
		//gui.nodes.setEnabled(true);
		gui.nodes.setSelectedIndex(gui.bm.map.nodes.indexOf(sel)+1);
		
		tags=new JDialog(gui);
		tags.setTitle("Tags");
		tags.setSize(450, 400);
		textArea= new JLabel(sel.getTextTags());
		tags.add(textArea);
		tags.setLocation(1050,0);
		tags.setVisible(true);
		nodeInfo=new JDialog(gui);
		nodeInfo.setTitle("Images");
		nodeInfo.setSize(450, 300);
		textArea= new JLabel(sel.getNodeInfo(gui.bm.map.edges, gui.bm.map.nodes));
		scroll=new JScrollPane(textArea);
		nodeInfo.add(scroll);
		nodeInfo.setLocation(1050,400);
		nodeInfo.setVisible(true);
		//FileMethods.saveFile(sel.getNodesContent(), "Node_"+String.valueOf(gui.bm.map.nodes.indexOf(sel)), false);
		//createChart(sel);
			
		presenceData = new JLabel(sel.getTop10());
		scroll2 = new JScrollPane(presenceData);
		top = new JDialog(gui);
		top.add(scroll2);
		top.setSize(450, 300);
		top.setLocation(850,400);
		top.setVisible(true);
		
		 tagCloudDialog =new JDialog(gui);
		 tagCloudDialog.setTitle("Tags Cloud");
		 	     
		 JLabel lab =  new JLabel(new ImageIcon(sel.getTagCloudImage()));
		 tagCloudDialog.setSize(new Dimension(lab.getIcon().getIconWidth(),lab.getIcon().getIconHeight()+50));
		 tagCloudDialog.add(lab);
		 tagCloudDialog.setLocation(850,0);
		// tagCloudDialog.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		 
		 JButton pdf = new JButton("Save as pdf");
		 pdf.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					//System.out.println("aloha world");
					try {
						savePDFTagCloud(lab);
					} catch (DocumentException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}

				private void savePDFTagCloud(JLabel lab) throws DocumentException, IOException {
					// TODO Auto-generated method stub
					Date date = new Date();
		        	  DateFormat hourdateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		        	  String imgName = gui.name+"_Node= "+gui.selectedNode+"_"+gui.bm.threshold1+"_"+gui.bm.threshold2+"_"+hourdateFormat.format(date);
		        	  
		        	  
		        	  
		        	 JPanel panel = new JPanel();
		      		panel.add(lab);
		     		// File miDir = new File(".");
		     	    // String c = miDir.getAbsolutePath();

		     	     //elimino el punto (.) nombre del archivo(virtual) que cree para obtener la ruta de la carpeta del proyecto
		     	   //  String ruta = c.substring(0, c.length() - 1);
		           //   ruta += "resultados/" + imgName.trim() + ".png";
		     		
		     		//File fichero = new File(ruta);
		     	    int w = panel.getWidth();
		     	    int h = panel.getHeight();
		     	    
		     	    w= lab.getIcon().getIconWidth();
		     	    h= lab.getIcon().getIconHeight();
		     	    
		     	    BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		     	    Graphics2D g = bi.createGraphics();
		     	    lab.getIcon().paintIcon(lab, g, 0, 0);
		     	    
		     	    Image imagen = Image.getInstance(bi,null);
		     	    
		     	      Document document = new Document();
		     	        //document.setPageSize(PageSize.A5);
		     	        document.setPageSize(new com.itextpdf.text.Rectangle(w,h));
		     	       document.setMargins(0, 0, 0, 0);
		     	      //  document.setMargins(36, 72, 108, 180);
		     	        // step 2
		     	        PdfWriter.getInstance(document, new FileOutputStream("resultados/" + imgName.trim() + "pdf"));
		     	        // step 3
		     	        document.open();
		     	        // step 4
		     	        // document.add(new Paragraph("Hello World"));
		     	        document.add(imagen);
		     	        // step 5
		     	        document.close();
		     	    
				}
			});
		 
		 JButton h = new JButton("Save as Image");
		 h.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//System.out.println("aloha world");
				saveTagCloud(lab);
				
			}

			private void saveTagCloud(JLabel lab) {
				// TODO Auto-generated method stub
				Date date = new Date();
	        	  DateFormat hourdateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
	        	  String imgName = gui.name+"_Node= "+gui.selectedNode+"_"+gui.bm.threshold1+"_"+gui.bm.threshold2+"_"+hourdateFormat.format(date);
	        	  
	        	  
	        	  
	        	 JPanel panel = new JPanel();
	      		panel.add(lab);
	     		 File miDir = new File(".");
	     	     String c = miDir.getAbsolutePath();

	     	     //elimino el punto (.) nombre del archivo(virtual) que cree para obtener la ruta de la carpeta del proyecto
	     	     String ruta = c.substring(0, c.length() - 1);
	              ruta += "resultados/" + imgName.trim() + ".png";
	     		
	     		File fichero = new File(ruta);
	     	    int w = panel.getWidth();
	     	    int h = panel.getHeight();
	     	    
	     	    w= lab.getIcon().getIconWidth();
	     	    h= lab.getIcon().getIconHeight();
	     	    
	     	    BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
	     	    Graphics2D g = bi.createGraphics();
	     	    lab.getIcon().paintIcon(lab, g, 0, 0);
	     	   // panel.paint(g);
	     	    
	     	    try {
	     			ImageIO.write(bi, "png", fichero);
	     			System.out.println("Image " +imgName +" Saved");
	     		} catch (IOException e) {
	     			System.out.println("Writing Error");
	     		}
	        	  
	        	 	
			}
		});
		
		 JPanel f = new JPanel();
		 f.add(h);
		 f.add(pdf);
		 f.add(lab);
		 tagCloudDialog.add(f);
		 tagCloudDialog.setVisible(true);
		 this.repaint();
			
		
	}
	
	public void showZoneAditionalInfo(Zone selZone){
		if(grafDialog!=null){
			grafDialog.dispose();
			grafDialog=null;
		}	
		
		PieDataset dataset = selZone.getChartDataset();
		
		JFreeChart chart = ChartFactory.createPieChart3D(      
		         "Category Distribution in the Zone",  // chart title 
		         dataset,        // data    
		         true,           // include legend   
		         true, 
		         false);
		
		
		PiePlot3D plot = (PiePlot3D) chart.getPlot();
     //   plot.setStartAngle(290);
       // plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
       // plot.setBackgroundAlpha(1.0f);
        
		chart.getPlot().setBackgroundPaint(Color.white);
		chart.getPlot().setBackgroundAlpha(0.5f);
		chart.getPlot().setOutlineVisible(false);
		plot.setLabelFont(new Font("Droid Sans", Font.PLAIN, 15));
		
		final PieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator("{0} = {1}({2})");
		plot.setLabelGenerator(labelGenerator);
		
		
		 GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    ge.getAllFonts();

		    Font font = new Font("Droid Sans", Font.PLAIN, 35);
		    
		
		chart.getTitle().setFont(font);
		//Cuadro Leyenda
		chart.getLegend().setItemFont(new Font("Droid Sans", Font.ITALIC, 15));
		
	chart.setBackgroundPaint(Color.white);
            
    final ChartPanel chartPanel = new ChartPanel(chart);
    chartPanel.setPreferredSize(new Dimension(600, 500));
    JButton j = new JButton("Save Image");
    
    j.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			//System.out.println("aloha world");
			try {
				saveClusterImage();
			} catch (DocumentException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}

		private void saveClusterImage() throws DocumentException, IOException {
		
			 
		      int height = 1080; /* Height of the image */ 
		      int width = (int) (height*1.7786458333); /* Width of the image */
		      
			        
		      Date date = new Date();
	        	 DateFormat hourdateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
	        	 String imgName = gui.name+"_Clusters_Zone_"+selZone.getName()+"_"+hourdateFormat.format(date);
		      
		      
		      File miDir = new File(".");
	        	 String c = miDir.getAbsolutePath();
	        	 String ruta = c.substring(0, c.length() - 1);
	              ruta += "resultados/" + imgName.trim() + ".png";
	              File fichero = new File(ruta);
	              
		     	    try {
		     	    	ChartUtilities.saveChartAsPNG( fichero , chart , width , height );
		     			System.out.println("Image " +imgName +" Saved");
		     		} catch (IOException e) {
		     			System.out.println("Writing Error");
		     		}
	              
	              
	        	    
		}
	});
    
	JPanel panel = new JPanel();
	
       
   // panel2.setBackground(new java.awt.Color(20, 148, 0));

   // javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(chartPanel);
 /*   panel2.setLayout(panel2Layout);
    panel2Layout.setHorizontalGroup(
        panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGap(0, 574, Short.MAX_VALUE)
    );
    panel2Layout.setVerticalGroup(
        panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGap(0, 1026, Short.MAX_VALUE)
    );*/
/*
    javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
    panel.setLayout(panelLayout);
  
    panelLayout.setHorizontalGroup(
        panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(panelLayout.createSequentialGroup()
            .addGap(249, 249, 249)
            .addComponent(j)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        .addGroup(panelLayout.createSequentialGroup()
            .addComponent(chartPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(0, 0, Short.MAX_VALUE))
    );
    panelLayout.setVerticalGroup(
        panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
            .addComponent(j)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(chartPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
*/
	
   
	   panel.setAutoscrolls(true);
       panel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
       panel.setLayout(new java.awt.BorderLayout());

       chartPanel.setBackground(new java.awt.Color(20, 148, 0));

       javax.swing.GroupLayout chartPanelLayout = new javax.swing.GroupLayout(chartPanel);
       chartPanel.setLayout(chartPanelLayout);
       chartPanelLayout.setHorizontalGroup(
           chartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
           .addGap(0, 934, Short.MAX_VALUE)
       );
       chartPanelLayout.setVerticalGroup(
           chartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
           .addGap(0, 532, Short.MAX_VALUE)
       );

       panel.add(chartPanel, java.awt.BorderLayout.CENTER);

       //j.setText("jButton1");

       javax.swing.GroupLayout layout = new javax.swing.GroupLayout(panel);
       panel.setLayout(layout);
       layout.setHorizontalGroup(
           layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
           .addComponent(chartPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
           .addGroup(layout.createSequentialGroup()
               .addComponent(j, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
               .addGap(0, 0, Short.MAX_VALUE))
       );
       layout.setVerticalGroup(
           layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
           .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
               .addComponent(j, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
               .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
               .addComponent(chartPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
               .addContainerGap())
       );
	
	grafDialog = new JDialog(gui);
    chartPanel.add(j);
    //grafDialog.setContentPane(chartPanel);
    grafDialog.add(panel);
    grafDialog.setLocation(1050,650);
    grafDialog.setSize(600, 500);
    grafDialog.setTitle("Distribution Pie Chart");
    grafDialog.setVisible(true);
	
    JLabel dat = new JLabel(selZone.toWebString());
    
    zoneInfo=new JDialog(gui);
    zoneInfo.add(dat);
    zoneInfo.setTitle("Zone Information");
    zoneInfo.setSize(500, 100);
    zoneInfo.setLocation(750,750);
    zoneInfo.setVisible(true);
			
	}
	
	
	
	public void showMapInfo(){
		if(mapInfoDialog!=null){
			mapInfoDialog.dispose();
			mapInfoDialog=null;
		}	
			mapInfoDialog = new JDialog(gui);
			mapInfoDialog.setSize(700, 500);
			
			JLabel dat = new JLabel(gui.bm.map.printMetricTable(MaxDistance()));
			JScrollPane scroll = new JScrollPane(dat);
			mapInfoDialog.setTitle("Map Metrics Information");
			mapInfoDialog.setContentPane(scroll);
			mapInfoDialog.setLocationRelativeTo(null);
			mapInfoDialog.setVisible(true);
		}
	
	
	public void showNodeDetails() {
		if(nodeDetailsDialog!=null){
			nodeDetailsDialog.dispose();
			nodeDetailsDialog=null;
		}
		
		String text = gui.bm.map.getMapInfo(gui.name, String.valueOf(gui.bm.threshold1), String.valueOf(gui.bm.threshold2), String.valueOf(gui.bm.cutNode));
		//String text = gui.bm.map.getNodeTagsRelation(gui.name, String.valueOf(gui.bm.threshold1), String.valueOf(gui.bm.threshold2), String.valueOf(gui.bm.cutNode));
		nodeDetailsDialog = new JDialog(gui);
		nodeDetailsDialog.setSize(450, 300);
		
		JLabel dat = new JLabel(text);
		JScrollPane scroll = new JScrollPane(dat);
		nodeDetailsDialog.setTitle("Top10 Nodes Tags");
		nodeDetailsDialog.setContentPane(scroll);
		nodeDetailsDialog.setLocationRelativeTo(null);
		nodeDetailsDialog.setVisible(true);
		
		//FileMethods.saveFile(text, gui.name+"_Node_Data", false);
			
	}
	
	
	
	public void showNodeRelation(String ModelsPath ) {
		if(nodeRelationDialog!=null){
			nodeRelationDialog.dispose();
			nodeRelationDialog=null;
		}
		
		//String text = gui.bm.map.getMapInfo(gui.name, String.valueOf(gui.bm.threshold1), String.valueOf(gui.bm.threshold2), String.valueOf(gui.bm.cutNode));
		String text = gui.bm.map.getNodeTagsRelation(gui.name, ModelsPath);
		nodeRelationDialog = new JDialog(gui);
		nodeRelationDialog.setSize(600, 500);
		
		JLabel dat = new JLabel(text);
		JScrollPane scroll = new JScrollPane(dat);
		nodeRelationDialog.setTitle("Top10 Nodes Tags");
		nodeRelationDialog.setContentPane(scroll);
		nodeRelationDialog.setLocationRelativeTo(null);
		nodeRelationDialog.setVisible(true);
		
		//FileMethods.saveFile(text, gui.name+"_Node_Data", false);
			
	}
	
	
	public void showHierCluster(JScrollPane scroll){
		if(clusterDialog!=null){
			clusterDialog.dispose();
			clusterDialog=null;
		}
		
		JPanel panel = new JPanel();
		JPanel panel2 = new JPanel();
		JButton k = new JButton("Save as Image...");
		k.setSize(new Dimension(50, 20));	
		clusterDialog = new JDialog(gui);
		//JScrollPane scroll = new JScrollPane();
		//scroll = gui.bm.map.getHierCluster();
		
		
		k.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//System.out.println("aloha world");
				try {
					saveClusterImage(panel2);
				} catch (DocumentException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}

			private void saveClusterImage(JPanel panelImg) throws DocumentException, IOException {
			
				        BufferedImage img = new BufferedImage(panelImg.getWidth(), panelImg.getHeight(), BufferedImage.TRANSLUCENT);
				        Graphics2D g2d = (Graphics2D) img.getGraphics();
				        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				        panelImg.paintAll(g2d);
				    	
				        Date date = new Date();
			        	 DateFormat hourdateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
			        	 String imgName = gui.name+"_Clusters_"+hourdateFormat.format(date);
			        	 
			        	 File miDir = new File(".");
			        	 String c = miDir.getAbsolutePath();
			        	 String ruta = c.substring(0, c.length() - 1);
			              ruta += "resultados/" + imgName.trim() + ".png";
			     		
			     		File fichero = new File(ruta);
			        	  
				        
			     	    try {
			     			ImageIO.write(img, "png", fichero);
			     			System.out.println("Image " +imgName +" Saved");
			     		} catch (IOException e) {
			     			System.out.println("Writing Error");
			     		}
				        
				        
			     	    
			}
		});
		
		panel2.setBackground(new Color (255,255,255));
		panel.setBackground(new Color (255,255,255));
		
		 javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
	        panel2.setLayout(panel2Layout);
	        panel2Layout.setHorizontalGroup(
	            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(panel2Layout.createSequentialGroup()
	                .addContainerGap()
	                .addComponent(scroll)
	                .addContainerGap(0, 0))//.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
	        );
	        panel2Layout.setVerticalGroup(
	            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(panel2Layout.createSequentialGroup()
	                .addContainerGap()
	                .addComponent(scroll)
	                .addContainerGap(0, 0))
	        );

	        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
	        panel.setLayout(panelLayout);
	        panelLayout.setHorizontalGroup(
	            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(panelLayout.createSequentialGroup()
	                .addGap(249, 249, 249)
	                .addComponent(k)
	                .addContainerGap(0, 0))
	            .addGroup(panelLayout.createSequentialGroup()
	                .addComponent(panel2)
	                .addGap(0, 0, 0))//Short.MAX_VALUE
	        );
	        panelLayout.setVerticalGroup(
	            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
	                .addComponent(k)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addComponent(panel2)
	                .addContainerGap(0, 0))
	        );
	        
	    

		
	     //   clusterDialog.add(k);
		clusterDialog.add(panel);
		clusterDialog.setTitle("Hierchical Cluster of the Nodes en the Map");
		
		//clusterDialog.setContentPane(scroll);
		clusterDialog.setSize(570, 1055);
		clusterDialog.setLocationRelativeTo(null);
		clusterDialog.setVisible(true);
		
		
	}
	
	
	public void showZoneInfomation(double th1){
		if(zoneDialog!=null){
			zoneDialog.dispose();
			zoneDialog=null;
		}
		
		zoneDialog = new JDialog(gui);
		JLabel dat = new JLabel(gui.bm.map.generateZones(th1));
		JScrollPane scroll = new JScrollPane(dat);
		zoneDialog.setSize(1040, 800);
		zoneDialog.setContentPane(scroll);
		zoneDialog.setLocationRelativeTo(null);
		zoneDialog.setVisible(true);
		
		
		
	}
	
	public void showZonesCharts(){
		if(zoneChartDialog!=null){
			zoneChartDialog.dispose();
			zoneChartDialog=null;
		}
		
		zoneChartDialog = new JDialog(gui);
		CategoryDataset fDataset = gui.bm.map.getZonesDataset();
		//fDataset = gui.bm.map.getZonesDataset();
		
		
	
		
		final JFreeChart chart = ChartFactory.createMultiplePieChart(
	            "Class composition of the Zones", fDataset, TableOrder.BY_COLUMN, true, true, false
	        ); 
	        chart.setBackgroundPaint(Color.white);
	        final MultiplePiePlot plot = (MultiplePiePlot) chart.getPlot();
	        final JFreeChart subchart = plot.getPieChart();
//	        final StandardLegend legend = new StandardLegend();
	  //      legend.setItemFont(new Font("SansSerif", Font.PLAIN, 8));
	    //    legend.setAnchor(Legend.SOUTH);
	      //  subchart.setLegend(legend);
	        plot.setLimit(0.10);
	        final PiePlot p = (PiePlot) subchart.getPlot();
	        final PieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator("{0} = {1}({2})");
	        
	        p.setLabelGenerator(labelGenerator);
	        p.setLabelFont(new Font("SansSerif", Font.PLAIN, 8));
	        p.setInteriorGap(0.30);
	        
	        chart.getPlot().setBackgroundPaint(Color.white);
			chart.getPlot().setBackgroundAlpha(0.5f);
			chart.getPlot().setOutlineVisible(false);
			p.setLabelFont(new Font("Droid Sans", Font.PLAIN, 8));
	        chart.getTitle().setFont(new Font("Droid Sans", Font.PLAIN, 20));
	        //chart.getCategoryPlot().setBackgroundPaint(Color.white);
	        
	       
	        p.setBackgroundPaint(null);
	       // p.setOutlineStroke(null);
	        
	        
	        
	        
	        final ChartPanel chartPanel = new ChartPanel(chart, true, true, true, false, true);
	        //chartPanel.setPreferredSize(new java.awt.Dimension(1000, 700));
	        zoneChartDialog.setSize(800, 800);
	        zoneChartDialog.setContentPane(chartPanel);
	        zoneChartDialog.setLocationRelativeTo(null);
	        zoneChartDialog.setVisible(true);
	        
		
	}
	
	public void showNodeComposition(){
		if(compositionDialog!=null){
			compositionDialog.dispose();
			compositionDialog=null;
		}
		
		compositionDialog = new JDialog(gui);
		JLabel dat = new JLabel(gui.bm.map.nodeComposition(gui.name));
		JScrollPane scroll = new JScrollPane(dat);
		compositionDialog.setSize(600, 900);
		compositionDialog.setContentPane(scroll);
		compositionDialog.setLocationRelativeTo(null);
		compositionDialog.setVisible(true);
		
	}
	
	public void createImage(String name) {

		JPanel panel = this;
		
		 File miDir = new File(".");
	     String c = miDir.getAbsolutePath();

	     //elimino el punto (.) nombre del archivo(virtual) que cree para obtener la ruta de la carpeta del proyecto
	     String ruta = c.substring(0, c.length() - 1);
         ruta += "resultados/" + name.trim() + ".png";
		
		File fichero = new File(ruta);
	    int w = panel.getWidth();
	    int h = panel.getHeight();
	    
	    w= img2.getIconWidth();
	    h= img2.getIconHeight();
	    
	    BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
	    Graphics2D g = bi.createGraphics();
	    panel.paint(g);
	    
	    try {
			ImageIO.write(bi, "png", fichero);
			System.out.println("Image " +name +" Saved");
		} catch (IOException e) {
			System.out.println("Writing Error");
		}
	      
	}
	
			
	public void mousePressed(MouseEvent evt) {
		Node sel=null;
		Zone selZone =null;
		///System.out.println("click ");
		
		
		//para ver la informacion de un nodo particular
		if (gui.mapGenerated && gui.showMap && !gui.zoneGenerated) {
			
			if (tags!=null) {
				tags.dispose();
				nodeInfo.dispose();
				//grafDialog.dispose();
				top.dispose();
				tagCloudDialog.dispose();
				gui.nodes.setSelectedIndex(0);
				tags=null;
				nodeInfo=null;
				//grafDialog=null;
				top = null;
				tagCloudDialog=null;
				
			}
			for (Node n:gui.bm.map.nodes) {
			//	System.out.println(distance(evt, n));
				if (distance(evt, n) < radius) {
					sel=n;
					//System.out.print(gui.bm.map.nodes.indexOf(n)+" ");
				}
			}
			if (sel!=null)  {
				showInfo(sel);
			}
		}
		
		
		//para informacion sobre las zonas
		
		
       if (gui.mapGenerated && gui.showMap && gui.zoneGenerated) {
			
			if (tags!=null) {
				tags.dispose();
				nodeInfo.dispose();
				grafDialog.dispose();
				top.dispose();
				tagCloudDialog.dispose();
				zoneInfo.dispose();
				//gui.nodes.setSelectedIndex(0);
				tags=null;
				nodeInfo=null;
				grafDialog=null;
				top = null;
				tagCloudDialog=null;
				zoneInfo=null;
				
			}
			
			
			for(Zone z:gui.bm.map.zones){
				for (Node n:z.areas) {
					//	System.out.println(distance(evt, n));
						if (distance(evt, n) < radius) {
							selZone=z;
							continue;
							//System.out.print(gui.bm.map.nodes.indexOf(n)+" ");
						}
					}
				if(selZone != null)
					continue;
				
			}
				
			if (selZone!=null)  {
				System.out.println(selZone.toString());
				showInfo(selZone.representative);
				showZoneAditionalInfo(selZone);//funcion adicional para pie chart
				
			}
		}
		
		
		
		
		
		
	}
	
	public void mouseClicked (MouseEvent evt) {
	}
	public void mouseReleased(MouseEvent e) {
	}
	public void mouseEntered(MouseEvent e) {
	}
	public void mouseExited(MouseEvent e) {
	}

}
