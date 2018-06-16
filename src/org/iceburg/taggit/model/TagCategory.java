package org.iceburg.taggit.model;

import java.util.ArrayList;
import java.util.Comparator;

public class TagCategory {
	private String name;
	private ArrayList<Tag> tags;

	public TagCategory(String name){
		this.name = name;
		this.tags = new ArrayList<Tag>();
//		tags.add(new Tag(Tag.BLANK_TAG_NAME, this));
	}
	
	@Override
	public String toString(){
		return getName();
	}
	
	
	public static class TagCategoryComparator implements Comparator<TagCategory>{
		public int compare(TagCategory catA, TagCategory catB){
			return catA.getName().compareToIgnoreCase(catB.getName());
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Tag> getTags() {
		tags.sort(new Tag.TagComparator());
		return tags;
	}
	public void addTag(Tag tag){
		this.tags.add(tag);
	}
	public void removeTag(Tag tag){
		this.tags.remove(tag);
	}
	
	public static boolean containsInvalidCharacter(String s){
		if (s.contains("<") || s.contains(">")
				|| s.contains("'")
				|| s.contains("/")
				|| s.contains("\\")
				|| s.contains(",")){
			return true;
		}
		return false;
	}
	
	public boolean hasTag(String tagName){
		for (Tag tag : getTags()){
			if (tag.getName().equals(tagName)){
				return true;
			}
		}
		return false;
	}
	public Tag getTag(String tagName){
		for (Tag tag : getTags()){
			if (tag.getName().equals(tagName)){
				return tag;
			}
		}
		return null;
	}

	
}
