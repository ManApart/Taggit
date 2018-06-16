package org.iceburg.taggit.ui;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InputDialogue {
	private JPanel pane;
	private final JTextField input;
	
	public InputDialogue(String title){
		pane = new JPanel();
		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
		
		JLabel lblTitle = new JLabel(title);
		pane.add(lblTitle);
		
		input = new JTextField();		
		pane.add(input);
		
		input.addComponentListener(new ComponentAdapter(){
			  public void componentShown(ComponentEvent ce){
			    input.requestFocusInWindow();
			  }
		});
		
		
	}
	
	public String show(){
		Object[] options = { "Ok", "Cancel" };
		int result = JOptionPane.showOptionDialog(null, pane, "Please Input", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, "");
		if (result == JOptionPane.OK_OPTION) {
		 	return input.getText();
		} else {
		     System.out.println("Cancelled");
		     return null;
		}
	}
}
