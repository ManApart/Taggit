package org.iceburg.taggit.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

import org.iceburg.taggit.Main;
import org.iceburg.taggit.model.Tag;
import org.iceburg.taggit.model.TagCategory;
import org.iceburg.taggit.model.TagOwner;

public class TagPane extends JPanel{
	private TagCategory lastCategory;
	private Tag lastTag;
	private JComboBox<Tag> cmbTags;
	private JComboBox<TagCategory> cmbCategories;
	private TagOwner owner;
	private JButton btnRemove;
	
	public TagPane(Tag tag, TagOwner owner){
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.owner = owner;
		sendUpdate(tag);
	}

	

	public void sendUpdate(Tag tag) {
		createCategoryDropDown(tag);		
		revalidate();
		repaint();
	}
	private void createCategoryDropDown(Tag tag){
		if (cmbCategories != null){
			remove(cmbCategories);
		}
		ArrayList<TagCategory> categories = Main.getTaggit().getLibrary().getCategories();
		cmbCategories = new JComboBox<TagCategory>(categories.toArray(new TagCategory[categories.size()]));
		
		//set proper index
		cmbCategories.setSelectedItem(tag.getCategory());
		cmbCategories.setEditable(false);
		cmbCategories.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				udpateCategoryName(cmbCategories);
			}
		});
		
		lastCategory = tag.getCategory();
		add(cmbCategories);
		createTagDropDown(tag);
	}
		
	private void createTagDropDown(Tag tag){
		if (cmbTags != null){
			remove(cmbTags);
		}
		ArrayList<Tag> tags = tag.getCategory().getTags();
		cmbTags = new JComboBox<Tag>(tags.toArray(new Tag[tags.size()]));
		cmbTags.setSelectedItem(tag);
		cmbTags.setEditable(false);
		cmbTags.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				udpateTagName(cmbTags);
			}
		});
			
		swapTags(lastTag, tag);
		lastTag = tag;
		add(cmbTags);
		addRemoveButton(owner);
	}
	

	
	private void udpateCategoryName(JComboBox<TagCategory> cmbCategories){
		TagCategory category = cmbCategories.getItemAt(cmbCategories.getSelectedIndex());
		System.out.println("Selected Category " + category.getName());
		if (category.getTags().size() > 0){
			sendUpdate(category.getTags().get(0));
		} else{
			System.err.println("No tags in selected category");
			cmbCategories.setSelectedItem(lastCategory);
		}
		
	}
	
	
	private void udpateTagName(JComboBox<Tag> cmbTags){
		Tag tag = cmbTags.getItemAt(cmbTags.getSelectedIndex());
		System.out.println("Selected Tag " + tag.getName());
		swapTags(lastTag, tag);
	}
	
	
	private void addRemoveButton(TagOwner owner) {
		if (btnRemove != null){
			remove(btnRemove);
		}
		btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {				
				owner.removeTag(getCurrentTag());
				
			}
		});
		add(btnRemove);
		
	}
	
	public TagCategory getCurrentCategory(){
		TagCategory tag = cmbCategories.getItemAt(cmbCategories.getSelectedIndex());
		return tag;
	}
	public Tag getCurrentTag(){
		Tag tag = cmbTags.getItemAt(cmbTags.getSelectedIndex());
		return tag;
	}
	
	private void swapTags(Tag oldTag, Tag newTag){
		if (oldTag == null || newTag == null || newTag == oldTag){
			return;
		}
		if (owner != null){
			owner.swapTags(oldTag, newTag);
		}
	}

}
