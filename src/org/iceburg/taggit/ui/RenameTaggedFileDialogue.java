package org.iceburg.taggit.ui;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.iceburg.taggit.Main;
import org.iceburg.taggit.model.TaggedFile;
import org.iceburg.taggit.parser.Parser;

public class RenameTaggedFileDialogue extends JPanel{
	private TaggedFile taggedFile;
	private JTextField input;
	private File file;
	Parser parser;
	
	public RenameTaggedFileDialogue(TaggedFile taggedFile){		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.taggedFile = taggedFile;
		this.file = Main.getTaggit().getFileOf(taggedFile);
		this. parser = Main.getTaggit().getParser();

		JLabel lblName = new JLabel("Rename to:");
		add(lblName);
		
		input = new JTextField();
		String name = parser.getFileNameWithoutExtension(taggedFile.getName());
		input.setText(name);
		
		Action action = new AbstractAction(){
		    @Override
		    public void actionPerformed(ActionEvent e){
		    	attemptToRename();
		    	Window win = SwingUtilities.getWindowAncestor(input);
	            win.setVisible(false);
		    }
		};		
		input.addActionListener(action);		
		add(input);
		
		showDialogue();
	}

	private void showDialogue() {
		Object[] options = { "Ok", "Cancel" };
		int result = JOptionPane.showOptionDialog(null, this, "Rename " + parser.getFileNameWithoutExtension(file) + "?", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, "");
        if (result == JOptionPane.OK_OPTION) {
        	attemptToRename();
        } else {
            System.out.println("Cancelled");
        }
	}
	
	private void attemptToRename(){
		file = parser.attemptToRenameFile(file, input.getText());
    	taggedFile.setName(file.getName());
    	Main.getUI().sendUpdate(taggedFile);
	}
	

}
