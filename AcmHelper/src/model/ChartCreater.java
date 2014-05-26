package model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class ChartCreater {
	private ArrayList<User> userArray;
	private ArrayList<String> timeKeyArray;
	
	public ChartCreater() {
		userArray = new ArrayList<User>();
		timeKeyArray = new ArrayList<String>();
	}
	public ChartCreater(User user) {
		userArray = new ArrayList<User>();
		timeKeyArray = new ArrayList<String>();
		userArray.add(user);
		sort();
	}
	public boolean reset() {
		userArray.clear();
		return true;
	}
	public boolean add(User user) {
		userArray.add(user);
		sort();
		return true;
	}
	
	public JFreeChart getTimeChart() {
		return  CreateTimeChart(CreateTimeDataset());
	}
	
	public JFreeChart getTypeChart() {
		return CreateTypeChart(CreateTypeDataset());
	}
	
	private void sort() {
		timeKeyArray.clear();
		for(int i=0; i<userArray.size(); i++) {
			User user = userArray.get(i);
			String[] timeKey = user.getTimeKey();
			int monthNum = user.getMonthNum();
			for(int j=0; j<monthNum; j++) {
				if(!timeKeyArray.contains(timeKey[j])) {
					timeKeyArray.add(timeKey[j]);
				}
			}
		}
		Collections.sort(timeKeyArray);
		/*
		for(int i=0; i<timeKeyArray.size(); i++) {
			System.out.println(timeKeyArray.get(i));
		}
		*/
	}
	
	private CategoryDataset CreateTimeDataset() 
	{	
		DefaultCategoryDataset dataset=new DefaultCategoryDataset();
		for(int i=0; i<timeKeyArray.size(); i++) {
			for(int j=0; j<userArray.size(); j++) {
				User user = userArray.get(j);
				if(user.getTimemap().containsKey(timeKeyArray.get(i))) {
					dataset.addValue(user.getTimemap().get(timeKeyArray.get(i)),user.getUsername(),timeKeyArray.get(i));
				}
			}
		}
		/*
		for(int i=0; i<userArray.size(); i++) {
			User user = userArray.get(i);
			String[] timeKey = user.getTimeKey();
			Map<String,Integer> timemap = user.getTimemap();
			int monthNum = user.getMonthNum();
			for(int j=0; j<monthNum; j++) {
				dataset.addValue(timemap.get(timeKey[j]),user.getUsername(),timeKey[j]);
			}
		}
		*/
		return dataset;
	}
	   
	private JFreeChart CreateTimeChart(CategoryDataset dataset) //用数据集创建一个图表
	{
		JFreeChart chart = ChartFactory.createLineChart(
               "按时间题数",   // chart title
               null,                       // domain axis label
               "题数",                   // range axis label
               dataset,                         // data
               PlotOrientation.VERTICAL,        // orientation
               true,                           // include legend
               true,                            // tooltips
               false                            // urls
		);
		//chart.setBackgroundPaint(Color.white);

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        
        //plot.setBackgroundPaint(Color.lightGray);
        
        plot.setRangeGridlinesVisible(false);
        // customise the range axis...
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        // customise the renderer...
        LineAndShapeRenderer renderer 
                = (LineAndShapeRenderer) plot.getRenderer();
        renderer.setBaseShapesVisible(true);
        renderer.setDrawOutlines(true);
        renderer.setUseFillPaint(true);
        renderer.setBaseFillPaint(Color.white);
        renderer.setSeriesStroke(0, new BasicStroke(3.0f));
        renderer.setSeriesOutlineStroke(0, new BasicStroke(2.0f));
        renderer.setSeriesShape(0, new Ellipse2D.Double(-5.0, -5.0, 10.0, 10.0));
        CategoryAxis categoryAxis=plot.getDomainAxis();//获得横坐标
      //设置横坐标Label倾斜角度
        categoryAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());//显示每个柱的数值 
        renderer.setBaseItemLabelsVisible(true); 
		return chart;
	}
	
	private CategoryDataset CreateTypeDataset() 
	{	
		DefaultCategoryDataset dataset=new DefaultCategoryDataset();
		for(int i=0; i<userArray.size(); i++) {
			User user = userArray.get(i);
			Map<String,Integer> typemap = user.getTypemap();
			Iterator<String> itertype = typemap.keySet().iterator();
		       while(itertype.hasNext()) {
		            String ss = itertype.next();
		            dataset.addValue(typemap.get(ss),user.getUsername(),ss);
		       }
		}
		return dataset;
	}
	   
	private JFreeChart CreateTypeChart(CategoryDataset dataset) //用数据集创建一个图表
	{
		JFreeChart chart=ChartFactory.createBarChart("hi", "类别", 
               "题数", 
               dataset, 
               PlotOrientation.VERTICAL, 
               true, 
               true, 
               false
           ); //创建一个JFreeChart
       chart.setTitle(new TextTitle("按分类题数",new Font("宋体",Font.BOLD+Font.ITALIC,20)));//可以重新设置标题，替换“hi”标题
       //chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
       CategoryPlot plot=(CategoryPlot)chart.getPlot();//获得图标中间部分，即plot
       CategoryAxis categoryAxis=plot.getDomainAxis();//获得横坐标
       categoryAxis.setLabelFont(new Font("黑体",Font.BOLD,16));//设置横坐标字体
       //设置横坐标Label倾斜角度
       categoryAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
       BarRenderer render = (BarRenderer) plot.getRenderer();
       render.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());//显示每个柱的数值 
       render.setBaseItemLabelsVisible(true);    
       return chart;
	}
	
}
