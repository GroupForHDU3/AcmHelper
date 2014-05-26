package model;
import java.net.URL;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.BufferedWriter;
import java.util.regex.*;

public class InfoDownload {
	String username;
	String filename;
	String urlstr;
	String strline; //html文件每行的内容
	String runid_str;
	int runid_int;
	URL url;
	
	String valid_regex;
	String runid_regex;
	String time_regex;
	String pro_regex;
	String last_regex;
	String solved_regex;
	String rank_regex;
	String submit_regex;
	String prosolved_regex;
	String submission_regex;
	String accept_regex;
	Pattern valid_pat;
	Pattern runid_pat;
	Pattern time_pat;
	Pattern pro_pat;
	Pattern last_pat;
	Pattern solved_pat;
	Pattern rank_pat;
	Pattern submit_pat;
	Pattern prosolved_pat;
	Pattern submission_pat;
	Pattern accept_pat;
	Matcher valid_mat;
	Matcher runid_mat;
	Matcher time_mat;
	Matcher pro_mat;
	Matcher last_mat;
    Matcher solved_mat;	
    Matcher rank_mat;
    Matcher submit_mat;
    Matcher prosolved_mat;
    Matcher submission_mat;
    Matcher accept_mat;
	File file;
	OutputStreamWriter write;
	BufferedWriter bw;
	InputStream in;
	InputStreamReader read; 
	BufferedReader br;
	
	boolean isLastPage; //判断页面是否为最后一页
	
	
	public boolean init(String user_name){
		username=user_name;
		valid_regex=">No such user.<";
		valid_pat=Pattern.compile(valid_regex);
		urlstr="http://acm.hdu.edu.cn/userstatus.php?user="+username;
		try{
		url=new URL(urlstr);
		in=url.openStream();
		read = new InputStreamReader(in,"UTF-8"); 
		br=new BufferedReader(read);
		while((strline=br.readLine())!=null){
			valid_mat=valid_pat.matcher(strline);
			if(valid_mat.find())
				return false;
		}
		
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		urlstr="http://acm.hdu.edu.cn/status.php?user="+username+"&status=5";
		filename="E:\\doc\\"+username+"_profile.txt";
		try{
		url=new URL(urlstr);
		in=url.openStream();
		read = new InputStreamReader(in,"UTF-8"); 
		br=new BufferedReader(read);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			file=new File(filename);
			write = new OutputStreamWriter(new FileOutputStream(file),"UTF-8");
			bw=new BufferedWriter(write);
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		
		runid_regex="px>\\d+";
		time_regex="\\d+-\\d+-\\d+ \\d+:\\d+:\\d+";
		pro_regex=">\\d+</a>";
		last_regex="Next Page";
		solved_regex="(\\d+,[1-9]\\d*,\\d+)";
		rank_regex="td>Rank\\D+\\d+";
		submit_regex="Problems Submitted\\D+\\d+";
		prosolved_regex="Problems Solved\\D+\\d+";
		submission_regex="td>Submissions\\D+\\d+";
		accept_regex="Accepted\\D+\\d+";
		
		
		runid_pat=Pattern.compile(runid_regex);
		time_pat=Pattern.compile(time_regex);
		pro_pat=Pattern.compile(pro_regex);
		last_pat=Pattern.compile(last_regex);
		solved_pat=Pattern.compile(solved_regex);
		rank_pat=Pattern.compile(rank_regex);
		submit_pat=Pattern.compile(submit_regex);
		prosolved_pat=Pattern.compile(prosolved_regex);
		submission_pat=Pattern.compile(submission_regex);
		accept_pat=Pattern.compile(accept_regex);
		
		return true;
	}
	public void getinfo(){
		while(!isLastPage){
			isLastPage=true;
		try{
			while((strline=br.readLine())!=null){
				runid_mat=runid_pat.matcher(strline);
				time_mat=time_pat.matcher(strline);
				pro_mat=pro_pat.matcher(strline);
				last_mat=last_pat.matcher(strline);
				if(last_mat.find())
					isLastPage=false;
				while(runid_mat.find() && time_mat.find() && pro_mat.find()){
					runid_str=runid_mat.group().substring(3);
					bw.write(time_mat.group()+"\t"+pro_mat.group().substring(1,5));
					bw.newLine();
				}
				bw.flush();//刷新缓冲区
			}
			if(!isLastPage){
			runid_int=Integer.parseInt(runid_str)-1;
			runid_str=String.valueOf(runid_int);
			urlstr="http://acm.hdu.edu.cn/status.php?first="+runid_str+"&user="+username+"&pid=&lang=&status=5#status";
			}
			//bin.close();
			//bw.close();
			url=new URL(urlstr);
			in=url.openStream();
			read = new InputStreamReader(in,"UTF-8"); 
			br=new BufferedReader(read);
		}
		
		catch(Exception e){
			e.printStackTrace();
		}
		
		}
		
		urlstr="http://acm.hdu.edu.cn/userstatus.php?user="+username;
		filename="E:\\doc\\"+username+"_solvedproblem.txt";
		try{
		url=new URL(urlstr);
		in=url.openStream();
		read = new InputStreamReader(in,"UTF-8"); 
		br=new BufferedReader(read);
		file=new File(filename);
		write = new OutputStreamWriter(new FileOutputStream(file),"UTF-8");
		bw=new BufferedWriter(write);
		
		while((strline=br.readLine())!=null){
			rank_mat=rank_pat.matcher(strline);
			submit_mat=submit_pat.matcher(strline);
			prosolved_mat=prosolved_pat.matcher(strline);
			submission_mat=submission_pat.matcher(strline);
			accept_mat=accept_pat.matcher(strline);
			
			
			if(rank_mat.find()){
				bw.write("Rank"+"\t"+rank_mat.group().substring(29));
				bw.newLine();
				}
			if(submit_mat.find()){
				bw.write("Problems_Submitted"+"\t"+submit_mat.group().substring(40));
				bw.newLine();
			}
			if(prosolved_mat.find()){
				bw.write("Problems_Solved"+"\t"+prosolved_mat.group().substring(37));
				bw.newLine();
			}
			if(submission_mat.find()){
				bw.write("Submissions"+"\t"+submission_mat.group().substring(36));
				bw.newLine();
			}
			if(accept_mat.find()){
				bw.write("Accepted"+"\t"+accept_mat.group().substring(30));
				bw.newLine();
			}
			solved_mat=solved_pat.matcher(strline);
			while(solved_mat.find()){
				bw.write(solved_mat.group().replace(',', '\t'));
				bw.newLine();
			}
		}
		bw.flush();
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		
	}

}
