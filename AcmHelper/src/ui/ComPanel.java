package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ComPanel extends JPanel implements ActionListener {
	private BorderLayout layout;
	private JButton addButton;
	private JDesktopPane desktopPane;
	
	public ComPanel() {
		layout = new BorderLayout();
		this.setLayout(layout);
		addButton = new JButton("添加对比");
		this.add(addButton, BorderLayout.NORTH);
		addButton.addActionListener(this);
		desktopPane = new JDesktopPane();
		desktopPane.setBackground(Color.WHITE);
		desktopPane.setPreferredSize(new Dimension(800, 600));
		this.add(desktopPane);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==addButton) {
			ComInternalFrame internalFrame = new ComInternalFrame();
			internalFrame.setLocation(20, 20);
			internalFrame.setVisible(true);
			desktopPane.add(internalFrame);
		}
	}
}
