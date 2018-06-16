package org.iceburg.taggit.model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class TaggedFileTest {
	
	
	
	@Test
	public void copyFileTagsTo(){
		TaggedFile source = new TaggedFile("Source", -1);
		
		TagCategory cat = TagCategoryTest.createTags("Places", "Place", 5);		
		for (Tag tag : cat.getTags()){
			source.addTag(tag);
		}
		
		cat = TagCategoryTest.createTags("Things", "Stuff", 5);		
		for (Tag tag : cat.getTags()){
			source.addTag(tag);
		}
		
		TaggedFile destination = new TaggedFile("Destination", -1);
		source.copyTagsTo(destination);
		for (Tag tag : source.getTags()){
			assertTrue(destination.hasTag(tag.getName()));
		}
	}

	
	public static boolean testFilesMatch(TaggedFile fileA, TaggedFile fileB){
		if (fileA == null || fileB == null){
			System.err.println("Tagged File is null");
			return false;
		}
		if (!fileA.getName().equals(fileB.getName())){
			System.err.println(fileA.getName() + " has a different name than Tagged File " + fileB.getName());
			return false;
		}
		if (fileA.getTags().size() != fileB.getTags().size()){
			System.err.println("Tagged File's tag count does not match");
			return false;
		}		
			
		//if there is a tag in one category but not the other
		for(Tag tagA : fileA.getTags()){
			if (!fileB.hasTag(tagA.getName())){
				System.err.println("Tagged File " + fileB.getName() + " does not contain " + tagA.getName());
				return false;
			}
		}
		return true;
	}
	
	
	public static void createFileAndAddToLibrary(String fileName, ArrayList<Tag> tags, TagLibrary library){
		TaggedFile file = new TaggedFile(fileName, 0);
		for (Tag tag : tags){
			file.addTag(tag);
		}
		library.getAllFiles().add(file);
	}
}
