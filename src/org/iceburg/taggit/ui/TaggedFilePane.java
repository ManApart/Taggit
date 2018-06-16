package org.iceburg.taggit.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.iceburg.taggit.Main;
import org.iceburg.taggit.model.Tag;
import org.iceburg.taggit.model.TaggedFile;
import org.iceburg.taggit.resources.Resources;

public class TaggedFilePane extends JPanel{
	private TaggedFile file;
	private JPanel tagsPane;
	private JButton btnName;
	public TaggedFilePane(TaggedFile file, boolean enableFileOptions){
		super();
		this.file = file;
		setLayout(new FlowLayout(FlowLayout.LEFT));
		setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));
//		setBorder(BorderFactory.createLineBorder(Color.black));
		
		if (enableFileOptions){
			buildFileOptionsPane(file);
		} else{
			add(new JLabel(file.getName()));
		}
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.Y_AXIS));
		
		JButton btn = new JButton("Add");
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addTag();
			}
		});
		btnPanel.add(btn);
		
		btn = new JButton("Copy");
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveToClipboard();
			}
		});
		btnPanel.add(btn);
		
		btn = new JButton("Paste");
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pasteFromClipboard();
			}
		});
		btnPanel.add(btn);
		
		add(btnPanel);
		
		tagsPane = new JPanel();
		tagsPane.setLayout(new BoxLayout(tagsPane, BoxLayout.X_AXIS));
		add(tagsPane);
		refresh();
		
	}



	private void buildFileOptionsPane(TaggedFile file) {
		JPanel namePane = new JPanel();
		BoxLayout layout = new BoxLayout(namePane, BoxLayout.Y_AXIS);
		namePane.setLayout(layout);
		btnName = new JButton(file.getName());
		btnName.setFocusPainted(false);
		btnName.setMargin(new Insets(0, 0, 0, 0));
		btnName.setContentAreaFilled(false);
		btnName.setBorderPainted(false);
		btnName.setOpaque(false);
		btnName.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showRenameDialogue();
				
			}
		});
		namePane.add(btnName);
		
		JPanel btnPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		JButton btnOpenFile = new JButton(new ImageIcon(Resources.class.getResource("file.png")));
		btnOpenFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.getTaggit().openFile(file);
			}
		});
		btnPane.add(btnOpenFile);
		
		JButton btnOpenFolder = new JButton(new ImageIcon(Resources.class.getResource("folder.png")));
		btnOpenFolder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.getTaggit().openFolder(file);
			}
		});
		btnPane.add(btnOpenFolder);
		
		namePane.add(btnPane);
		add(namePane);
	}
	
	
	
	private void refresh(){
		tagsPane.removeAll();
//		System.out.println("Refreshing: " + file.getName());
		ArrayList<Tag> tags = file.getTags();
		for (int i=0; i < tags.size(); i++){
			Tag tag = tags.get(i);
			tagsPane.add(new TagPane(tag, file));
			System.out.println("Adding " + tag);
		}
		tagsPane.revalidate();
		tagsPane.repaint();
		
		revalidate();
		repaint();
	}
	
	private void addTag() {
		if (Main.getTaggit().getLibrary().getFirstTag() == null){
			System.err.println("No Tag to add!");
			return;
		}
		file.addBlankTag();
		refresh();
	}
	public void addTag(Tag tag) {
//		System.out.println("Add Tag " + tag);
		file.getTags().add(tag);
		refresh();
	}
	
	private void saveToClipboard(){
		Main.getTaggit().getLibrary().saveToClipboard(file);
	}
	private void pasteFromClipboard(){
		Main.getTaggit().getLibrary().pasteFromClipboard(file);
	}

	public void removeTagPane(Tag tagToRemove) {
		if (tagToRemove != null){
			file.getTags().remove(tagToRemove);
		}
		refresh();
	}
	
	private void showRenameDialogue() {
		RenameTaggedFileDialogue rd = new RenameTaggedFileDialogue(file);
	}
}
