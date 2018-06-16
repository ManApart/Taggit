package org.iceburg.taggit.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.iceburg.taggit.Main;
import org.iceburg.taggit.model.TagLibrary;
import org.iceburg.taggit.model.TaggedFile;

public class SearchPane extends JPanel{
	private TaggedFile includeFile, excludeFile;
	
	public SearchPane(){
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		includeFile = new TaggedFile("Include Files:", -1);
		excludeFile = new TaggedFile("Exclude Files:", -1);
		
		initPane();
	}

	private void initPane() {
		add(new TaggedFilePane(includeFile, false));
		add(new TaggedFilePane(excludeFile, false));
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.getTaggit().doSearch(includeFile, excludeFile);
			}
		});
		add(btnSearch);
	}

	public void sendUpdate(TaggedFile update) {
		if (update != includeFile && update != excludeFile){
			return;
		}
		removeAll();
		initPane();
		
	}


}
