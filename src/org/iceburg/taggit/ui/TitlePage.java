package org.iceburg.taggit.ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.iceburg.taggit.Main;

public class TitlePage extends JPanel{

	public TitlePage(){
		setBackground(Color.pink);
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		JButton btnNew = new JButton("New");
		btnNew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.getTaggit().createNewLibrary();
			}
		});
		add(btnNew);
		
		JButton btnOpen = new JButton("Open");
		btnOpen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.getTaggit().openLibrary();
			}
		});
		add(btnOpen);
		
		JButton btnAddFolder = new JButton("Add a Folder");
		btnAddFolder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.getTaggit().addLibraryDirectory();
			}
		});
		add(btnAddFolder);

		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.getTaggit().saveLibrary();
			}
		});
		add(btnSave);
		JButton btnSaveAs = new JButton("Save As");
		btnSaveAs.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.getTaggit().saveLibraryAs();
			}
		});
		add(btnSaveAs);
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.getUI().sendUpdate(Main.getTaggit().getLibrary());
			}
		});
		add (btnRefresh);
	}
}
