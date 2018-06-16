package org.iceburg.taggit.ui;

import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.iceburg.taggit.Main;
import org.iceburg.taggit.model.Tag;
import org.iceburg.taggit.model.TagCategory;
import org.iceburg.taggit.model.TagLibrary;

public class TagEditorPane extends JPanel{
	private TagPaneEditable tagPane;
	
	public TagEditorPane(TagLibrary library){
		setLayout(new GridBagLayout());
		JPanel centerPane = new JPanel();
		centerPane.setLayout(new FlowLayout(FlowLayout.LEFT));

		JPanel buttonAddPane = new JPanel();
		buttonAddPane.setLayout(new BoxLayout(buttonAddPane, BoxLayout.Y_AXIS));
		
		JButton btnAddCategory = new JButton("Add Category");
		btnAddCategory.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addCategory();
			}
		});
		buttonAddPane.add(btnAddCategory);
		
		JButton btnAddTag = new JButton("Add Tag");
		btnAddTag.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addTag();
			}
		});
		buttonAddPane.add(btnAddTag);
		centerPane.add(buttonAddPane);
		
		Tag tag = library.getFirstTag();
		this.tagPane = new TagPaneEditable(tag);
		centerPane.add(tagPane);
		

		JPanel buttonDeletePane = new JPanel();
		buttonDeletePane.setLayout(new BoxLayout(buttonDeletePane, BoxLayout.Y_AXIS));
		
		JButton btnDeleteCategory = new JButton("Delete Category");
		btnDeleteCategory.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deleteCategory();
			}
		});
		buttonDeletePane.add(btnDeleteCategory);
		
		JButton btnDeleteTag = new JButton("Delete Tag");
		btnDeleteTag.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deleteTag();
			}
		});
		buttonDeletePane.add(btnDeleteTag);
		
		centerPane.add(buttonDeletePane);
		add(centerPane);
		
	}
	
	
	private void addCategory(){
		InputDialogue input = new InputDialogue("Name of New Category:");
		String newCategoryName = input.show();
		if (newCategoryName == null){
			return;
		}
		if (TagCategory.containsInvalidCharacter(newCategoryName)){
			return;
		}
		
		//now get an initial tag
		input = new InputDialogue("Name of New Tag:");
		String newTagName = input.show();
		if (newTagName == null){
			return;
		}
		if (TagCategory.containsInvalidCharacter(newTagName)){
			return;
		}
		
		
		TagCategory category = new TagCategory(newCategoryName);
		Tag tag = new Tag(newTagName, category);
		category.addTag(tag);
		TagLibrary library = Main.getTaggit().getLibrary();
		library.addCategory(category);
		Main.getUI().sendUpdate(library);
		System.out.println("Added category " + category +" and tag " + tag);
	}
	private void addTag(){
		InputDialogue input = new InputDialogue("Name of New Tag:");
		String newName = input.show();
		if (newName == null){
			return;
		}
		if (TagCategory.containsInvalidCharacter(newName)){
			return;
		}
		
		Tag tag = new Tag(newName, tagPane.getCurrentCategory());
		tagPane.getCurrentCategory().addTag(tag);
		TagLibrary library = Main.getTaggit().getLibrary();
		Main.getUI().sendUpdate(library);
		System.out.println("Added tag " + tag);
	}
	private void deleteCategory(){
		TagCategory categoryToDelete = tagPane.getCurrentTag().getCategory();
		TagLibrary library = Main.getTaggit().getLibrary();
		library.removeCategory(categoryToDelete);
		Main.getUI().sendUpdate(library);
		System.out.println("Deleteing category " + categoryToDelete);
	}
	private void deleteTag(){
		Tag tagToDelete = tagPane.getCurrentTag();
		TagLibrary library = Main.getTaggit().getLibrary();
		library.removeTag(tagToDelete);
		Main.getUI().sendUpdate(library);
		System.out.println("Deleteing Tag " + tagPane.getCurrentTag());
	}


}
