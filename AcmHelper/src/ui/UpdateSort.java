package ui;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.io.*;
 
 
public class UpdateSort extends JFrame {
    private JLabel id, sort;
    private JTextField inputID;
    private JComboBox sortBox;
    private String[] item = {"模拟", "博弈", "字符串", "图论", "贪心", "简单题",
    		"数学", "搜索", "动态规划", "数据结构"};
    private JButton save;
 
    public UpdateSort() {
        super("修改分类");
        setSize(240,300);
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 30));
 
        id = new JLabel("题号:");
        sort = new JLabel("分类:");
 
        inputID = new JTextField(14);
 
        sortBox = new JComboBox(item);
 
        save = new JButton("修改");
        save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                //docMenuItem0ActionPerformed(evt);
            	String filePath = "E:\\doc\\sort.txt";
            	BufferedReader br = null;  
                String line = null;  
                StringBuffer buf = new StringBuffer();
                try {  
                    // 根据文件路径创建缓冲输入流  
                    br = new BufferedReader(new FileReader(filePath));  
                    System.out.println(inputID.getText().toString());
                      
                    // 循环读取文件的每一行, 对需要修改的行进行修改, 放入缓冲对象中  
                    while ((line = br.readLine()) != null) {  
                    	String s[] = line.split(" ");
                    	//System.out.println(s[0]+" "+s[1]+" "+sortBox.getSelectedItem().toString()+"\n");
                        // 此处根据实际需要修改某些行的内容  
                        if (inputID.getText().toString().equals(s[0])) {  
                            buf.append(s[0]+" "+sortBox.getSelectedItem().toString());
                            System.out.println(s[0]+" "+s[1]+" "+sortBox.getSelectedItem().toString()+"\n");
                        } 
                        // 如果不用修改, 则按原来的内容回写  
                        else {  
                            buf.append(line);
                            System.out.println(line);
                        }  
                        buf.append(System.getProperty("line.separator"));  
                    }  
                } catch (Exception e) {  
                    e.printStackTrace();  
                } finally {  
                    // 关闭流  
                    if (br != null) {  
                        try {  
                            br.close();  
                        } catch (IOException e) {  
                            br = null;  
                        }  
                    }  
                }
                String str = buf.toString();
                BufferedWriter bw = null;  
                
                try {  
                    // 根据文件路径创建缓冲输出流  
                    bw = new BufferedWriter(new FileWriter(filePath));  
                    // 将内容写入文件中  
                    bw.write(buf.toString());  
                } catch (Exception e) {  
                    e.printStackTrace();  
                } finally {  
                    // 关闭流  
                    if (bw != null) {  
                        try {  
                            bw.close();  
                        } catch (IOException e) {  
                            bw = null;  
                        }  
                    }  
                } 
            }
        });
 
        getContentPane().setBackground(Color.WHITE);
        getContentPane().add(Box.createHorizontalStrut(5));
        getContentPane().add(id);
        getContentPane().add(inputID);
        getContentPane().add(Box.createHorizontalStrut(35));
        getContentPane().add(sort);
        getContentPane().add(sortBox);
        getContentPane().add(Box.createHorizontalStrut(35));
        getContentPane().add(save);
 
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(3);
    }
}