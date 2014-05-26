package model;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class User {
	private String username;
	private int totle;
	private int firstNum;
	private int monthNum;
	private int NUM = 1000;
	
	private String timefilename;
	private BufferedReader timeBr, typeBr, sortBr, solveBr;
	
	private String typefilename;
	private String sortfilename;
	private String solvefilename;
	
	private Map<String, Integer> timemap = new HashMap<String, Integer>();
	private String timeKey[] = new String[NUM];
	private Map<String,Integer> typemap = new HashMap<String, Integer>();
	private Map<String, String> sortmap = new HashMap<String, String>();
	
	public User(String user_name) {
		username = user_name;
		if(init()) {
			timeCounter();
			typeCounter();
			firstAcceptAndTotleCounter();
		}
	}
	
	public boolean init(){
		
		timefilename="E:\\doc\\"+username+"_profile.txt";
		
		typefilename ="E:\\doc\\"+ username + "_profile.txt";
		sortfilename = "E:\\doc\\sort.txt";
		
		solvefilename = "E:\\doc\\"+ username + "_solvedproblem.txt";
		
		try {
			timeBr = new BufferedReader(new FileReader(new File(timefilename)));
			typeBr = new BufferedReader(new FileReader(new File(typefilename)));
			sortBr = new BufferedReader(new FileReader(new File(sortfilename)));
			solveBr = new BufferedReader(new FileReader(new File(solvefilename)));	
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public void timeCounter(){
		try{
			String strline;
			while((strline=timeBr.readLine())!=null){
				String[] s = strline.split("[-： ]+");
				// s[0]:年, s[1]：月, s[6]：题号;
				String time = s[0] + s[1];
				if(timemap.containsKey(time))
					timemap.put(time, timemap.get(time)+1);
				else
					timemap.put(time, 1);
					
			}
			monthNum = 0;
			Iterator<String> iter = timemap.keySet().iterator();
			while(iter.hasNext()) {
				//System.out.println("44444444");
            	String ss = iter.next();
	            //System.out.println(ss);
	            timeKey[monthNum] = ss;
	            monthNum++;
			}
			//冒泡
			for(int i=0; i<monthNum-1; i++)
			{
				for(int j=0; j<monthNum-i-1; j++)
				{
					int j0 = Integer.parseInt(timeKey[j]);
					int j1 = Integer.parseInt(timeKey[j+1]);
					if(j0 > j1)
					{
						String tmp;
						tmp = timeKey[j]; timeKey[j] = timeKey[j+1]; timeKey[j+1] = tmp;
					}
				}
			}
			for(int i=1; i<monthNum; i++) {
				int pre = timemap.get(timeKey[i-1]), cur = timemap.get(timeKey[i]);
				cur = cur + pre;
				timemap.put(timeKey[i], cur);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
    public void typeCounter() {
        try {
            String line, s[], ss[];
            while((line=sortBr.readLine())!=null) {
                s = line.split(" ");
                sortmap.put(s[0], s[1]);
            }
            
            while((line=typeBr.readLine())!=null) {
                ss = line.split("[-:\\t]+");
                if(sortmap.containsKey(ss[5])) {
                    String type = sortmap.get(ss[5]);
                    if(typemap.containsKey(type)) {
                    	typemap.put(type, typemap.get(type)+1);
                    }
                    else {
                    	typemap.put(type, 1);
                    }
                }
            }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void firstAcceptAndTotleCounter() {
    	String s[];
    	firstNum = 0;
    	try {
    		BufferedReader reader = new BufferedReader(new FileReader(new File("E:\\doc\\"+ username + "_solvedproblem.txt")));
    		String line = reader.readLine();
    		line = reader.readLine();
    		line = reader.readLine();
    		s = line.split("\t");
    		totle = Integer.parseInt(s[1]);
    		line = reader.readLine();
    		line = reader.readLine();
    		while((line=reader.readLine())!=null) {
    			s = line.split("\t");
    			if(s[2].equals("1")) {
    				firstNum++;
    			}
    		}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	public String getUsername() {
		return username;
	}

	public int getTotle() {
		return totle;
	}

	public int getFirstNum() {
		return firstNum;
	}

	public int getMonthNum() {
		return monthNum;
	}

	public Map<String, Integer> getTimemap() {
		return timemap;
	}

	public String[] getTimeKey() {
		return timeKey;
	}

	public Map<String, Integer> getTypemap() {
		return typemap;
	}
	
}
