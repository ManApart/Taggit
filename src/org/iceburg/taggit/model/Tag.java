package org.iceburg.taggit.model;

import java.util.Comparator;
import java.util.Locale.Category;

public class Tag {
	private String name;
	private TagCategory category;
	
	public Tag(String name, TagCategory category){
		this.name = name;
		this.category = category;
	}
	
	@Override
	public String toString(){
		return getName();
	}
	
	
	public TagCategory getCategory(){
		return category;
	}
	
	public static class TagComparator implements Comparator<Tag>{
		public int compare(Tag tagA, Tag tagB){
			return tagA.getName().compareToIgnoreCase(tagB.getName());
		}
	}
	
	public String getName(){
		return name;
	}

	public void setName(String newName) {
		this.name = newName;
		
	}
}
