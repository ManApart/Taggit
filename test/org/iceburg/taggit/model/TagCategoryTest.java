package org.iceburg.taggit.model;

import java.util.ArrayList;

public class TagCategoryTest {
	
	public static boolean testCategoriesMatch(TagCategory catA, TagCategory catB){
		if (catA == null || catB == null){
			System.err.println("Category is null");
			return false;
		}
		if (!catA.getName().equals(catB.getName())){
			System.err.println(catA.getName() + " has a different name than category " + catB.getName());
			return false;
		}
		if (catA.getTags().size() != catB.getTags().size()){
			System.err.println("Category has different tag count");
			return false;
		}
				
		//if there is a tag in one category but not the other
		for(Tag tagA : catA.getTags()){
			if (!catB.hasTag(tagA.getName())){
				System.err.println(catB.getName() + " does not have " + tagA.getName());
				return false;
			}
		}
		return true;
	}
	
	
	public static TagCategory createTags(String categoryName, ArrayList<String> tagNames){
		TagCategory category = new TagCategory(categoryName);
		for (String tagName : tagNames){
			Tag tag = new Tag(tagName, category);
			category.addTag(tag);
		}
		return category;
	}
	public static TagCategory createTags(String categoryName, String tagName, int count){
		TagCategory category = new TagCategory(categoryName);
		for (int i=0; i< count; i++){
			Tag tag = new Tag(tagName + " " + i, category);
			category.addTag(tag);
		}
		return category;
	}
	
	public static TagCategory createTagsAndAddThemToFile(TaggedFile file, String categoryName, ArrayList<String> tagNames){
		TagCategory category = new TagCategory(categoryName);
		for (String tagName : tagNames){
			Tag tag = new Tag(tagName, category);
			category.addTag(tag);
			file.addTag(tag);
		}
		return category;
	}

}
