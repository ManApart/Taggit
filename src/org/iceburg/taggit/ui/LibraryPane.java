package org.iceburg.taggit.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import org.iceburg.taggit.Main;
import org.iceburg.taggit.model.TagLibrary;
import org.iceburg.taggit.model.TaggedFile;

public class LibraryPane extends JPanel{
	private HashMap<TaggedFile, JPanel> panes;
	private JPanel mainPane;
	private JTabbedPane optionTabs;
	private SearchPane searchPane;

	
	public LibraryPane(TagLibrary library){
		super();
		this.panes = new HashMap<TaggedFile, JPanel>();
//		setLayout(new BorderLayout());
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		mainPane = new JPanel();
		mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.Y_AXIS));
		sendUpdate(library);
	}
	
	public void sendUpdate(TagLibrary library){		

		//First, record the tab we were on (search or edit)
		int selectedTab = 0;
		if (optionTabs != null){
			selectedTab = optionTabs.getSelectedIndex();
		}
		
		removeAll();
		mainPane.removeAll();
		
		if (optionTabs == null){
			addEditorAndSearchPanes(library);
		} else{
			add(optionTabs);
			optionTabs.setSelectedIndex(selectedTab);
		}
		
		for (TaggedFile file : library.getFilteredFiles()){
			TaggedFilePane tPane = new TaggedFilePane(file, true);
			JPanel pane = new JPanel(new BorderLayout());
			pane.add(tPane);
			panes.put(file, pane);
			mainPane.add(pane);
		}
		
		JScrollPane jScroll = new JScrollPane(mainPane);
		jScroll.getVerticalScrollBar().setUnitIncrement(30);
		add(jScroll);
	}

	private void addEditorAndSearchPanes(TagLibrary library) {
		optionTabs = new JTabbedPane();
		optionTabs.addTab("Edit", new TagEditorPane(library));
		searchPane = new SearchPane();
		optionTabs.addTab("Search", searchPane);
		optionTabs.setMaximumSize(new Dimension(50000, 20));
		add(optionTabs);
//		mainPane.add(tabs);
	}

	public void sendUpdate(TaggedFile update) {
		JPanel pane = panes.get(update);
		if (pane != null){
			pane.removeAll();
			pane.add(new TaggedFilePane(update, true));
			pane.revalidate();
			pane.repaint();
		}
		searchPane.sendUpdate(update);
		revalidate();
		repaint();
	}
}
