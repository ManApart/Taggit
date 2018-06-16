package org.iceburg.taggit.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import org.iceburg.taggit.Main;
import org.iceburg.taggit.model.Tag;
import org.iceburg.taggit.model.TagCategory;
import org.iceburg.taggit.model.TagOwner;

public class TagPaneEditable extends JPanel{
	private TagCategory lastCategory;
	private Tag lastTag;
	private JComboBox<Tag> cmbTags;
	private JComboBox<TagCategory> cmbCategories;

	public TagPaneEditable(Tag tag) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
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
//		cmbCategories.add(comp);
		
		//set proper index
		cmbCategories.setSelectedItem(tag.getCategory());
		cmbCategories.setEditable(true);
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
		cmbTags.setEditable(true);
		cmbTags.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				udpateTagName(cmbTags);
			}
		});
			
		lastTag = tag;
		add(cmbTags);
	}
	

	
	private void udpateCategoryName(JComboBox<TagCategory> cmbCategories){
		TagCategory category = cmbCategories.getItemAt(cmbCategories.getSelectedIndex());

		String newName = null;
		if (category == null){
			category = lastCategory;
			newName = (String) cmbCategories.getSelectedItem();
		} else{
			lastCategory = category;
			newName = category.getName();
		}
		System.out.println("Selected Category " + newName);
		
		if (newName == null){
			return;
		}
		//If category exists, set our combo to him
		if (Main.getTaggit().getLibrary().categoryExists(newName)){
			TagCategory newCategory = Main.getTaggit().getLibrary().getCategory(newName);
			cmbCategories.setSelectedItem(newCategory);
			sendUpdate(newCategory.getTags().get(0));
			return;
		}
		//if this is an invalid name, revert our category to previous
		if (TagCategory.containsInvalidCharacter(newName)){
			cmbCategories.setSelectedItem(category);
			return;
		}
		
		//otherwise just rename this category
		category.setName(newName);
		Main.getUI().sendUpdate(Main.getTaggit().getLibrary());
	}
	
	
	private void udpateTagName(JComboBox<Tag> cmbTags){
		Tag tag = cmbTags.getItemAt(cmbTags.getSelectedIndex());
		
		String newName = null;
		if (tag == null){
			tag = lastTag;
			newName = (String) cmbTags.getSelectedItem();
		} else{
			lastTag = tag;
			newName = tag.getName();
		}
		System.out.println("Selected Tag " + newName);
		
		if (newName == null || newName.equals(tag.getName())){
			return;
		}
		//if invalid name, revert to previous tag
		if (TagCategory.containsInvalidCharacter(newName)){
			cmbTags.setSelectedItem(tag);
			return;
		}
		if (Main.getTaggit().getLibrary().tagExists(newName)){
			cmbTags.setSelectedItem(newName);
			return;
		}
		//otherwise just rename this category
		tag.setName(newName);
		Main.getUI().sendUpdate(Main.getTaggit().getLibrary());
	}
	
	
	public TagCategory getCurrentCategory(){
		TagCategory tag = cmbCategories.getItemAt(cmbCategories.getSelectedIndex());
		if (tag == null){
			tag = lastCategory;
		}
		return tag;
	}
	public Tag getCurrentTag(){
		Tag tag = cmbTags.getItemAt(cmbTags.getSelectedIndex());
		
		if (tag == null){
			tag = lastTag;
		}
		return tag;
	}
	
}
