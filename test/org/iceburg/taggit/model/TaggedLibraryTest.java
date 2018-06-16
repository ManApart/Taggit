package org.iceburg.taggit.model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class TaggedLibraryTest {

	public static boolean testLibrariesMatch(TagLibrary libraryA, TagLibrary libraryB) {
		if (libraryA == null || libraryB == null){
			System.err.println("Null Library");
			return false;
		}
		if (libraryA.getDirectories().size() != libraryB.getDirectories().size()){
			System.err.println("Library Directory lists don't match");
			return false;
		}
		if (libraryA.getAllFiles().size() != libraryB.getAllFiles().size()){
			System.err.println("Library File count does not match");
			return false;
		}
		if (libraryA.getCategories().size() != libraryB.getCategories().size()){
			System.err.println("Library Category count does not match");
			return false;
		}
			
		
		for(TagCategory tagCatA : libraryA.getCategories()){
			TagCategory tagCatB = libraryB.getCategory(tagCatA.getName());
			if (!TagCategoryTest.testCategoriesMatch(tagCatA, tagCatB)){
				return false;
			}
		}
		
		for(String directoryA : libraryA.getDirectories()){
			if (!libraryB.getDirectories().contains(directoryA)){
				System.err.println("Library does not contain " + directoryA);
				return false;
			}
		}
		
		for(TaggedFile fileA : libraryA.getAllFiles()){
			TaggedFile fileB = libraryB.getFile(fileA.getName());
			if (!TaggedFileTest.testFilesMatch(fileA, fileB)){
				return false;
			}
		}
		return true;
	}
	
	
	@Test
	public void doSearchIncludeFiles(){
		TagLibrary library = createTestLibrary();
		TaggedFile includeFile = new TaggedFile("Include", -1);
		TaggedFile excludeFile = new TaggedFile("Exclude", -1);
		
		//initial size
		assertEquals(4, library.getAllFiles().size());
		assertEquals(4, library.getFilteredFiles().size());
		
		//null critera and blank critera return all results
		library.doSearch(null, null);
		assertEquals(4, library.getFilteredFiles().size());

		library.doSearch(includeFile, excludeFile);
		assertEquals(4, library.getFilteredFiles().size());
		
		//only include results that have all of the include criteria
		Tag tag = library.getTag("People Tag 0");
		includeFile.addTag(tag);
		
		library.doSearch(includeFile, excludeFile);
		assertEquals(3, library.getFilteredFiles().size());
		
		//add another tag
		tag = library.getTag("People Tag 1");
		includeFile.addTag(tag);
		
		library.doSearch(includeFile, excludeFile);
		assertEquals(2, library.getFilteredFiles().size());
		
		//add a third tag, should now only get one file that has all the tags
		tag = library.getTag("Places Tag 1");
		includeFile.addTag(tag);
		
		library.doSearch(includeFile, excludeFile);
		assertEquals(1, library.getFilteredFiles().size());
		
		//null critera and blank critera return all results
		library.doSearch(null, null);
		assertEquals(4, library.getFilteredFiles().size());
	}
	@Test
	public void doSearchExcludeFiles(){
		TagLibrary library = createTestLibrary();
		TaggedFile includeFile = new TaggedFile("Include", -1);
		TaggedFile excludeFile = new TaggedFile("Exclude", -1);
		
		//initial size
		assertEquals(4, library.getAllFiles().size());
		assertEquals(4, library.getFilteredFiles().size());
		
		//null critera and blank critera return all results
		library.doSearch(null, null);
		assertEquals(4, library.getFilteredFiles().size());
		
		library.doSearch(includeFile, excludeFile);
		assertEquals(4, library.getFilteredFiles().size());
		
		//only include results that have all of the include criteria
		Tag tag = library.getTag("People Tag 0");
		excludeFile.addTag(tag);
		
		library.doSearch(includeFile, excludeFile);
		assertEquals(1, library.getFilteredFiles().size());
		
		//use a tag that only shows up in one place
		tag = library.getTag("Places Tag 1");
		excludeFile.getTags().clear();
		excludeFile.addTag(tag);
		
		library.doSearch(includeFile, excludeFile);
		assertEquals(3, library.getFilteredFiles().size());
		
		//add another tag, should now get only the empty file
		tag = library.getTag("People Tag 1");
		excludeFile.addTag(tag);
		
		library.doSearch(includeFile, excludeFile);
		assertEquals(2, library.getFilteredFiles().size());
		
		//null critera and blank critera return all results
		library.doSearch(null, null);
		assertEquals(4, library.getFilteredFiles().size());
	}

	public static TagLibrary createTestLibrary(){
		String directory = "B:\\Coding\\Workspace\\Taggit\\Sample Data";
		ArrayList<String> categoryNames = new ArrayList<String>();
		categoryNames.add("Places");
		categoryNames.add("People");
		categoryNames.add("Things");

		TagLibrary library = TaggedLibraryTest.createLibraryContents(directory,categoryNames, "Tag", 3);
		
		ArrayList<Tag> tags = new ArrayList<Tag>();
		
		for (TagCategory category : library.getCategories()){
			for (Tag tag : category.getTags()){
				tags.add(tag);
			}
		}		
		TaggedFileTest.createFileAndAddToLibrary("All Tags", tags, library);
		
		//Add empty file without tags
		TaggedFile emptyFile = new TaggedFile("Empty", 0);
		library.addFile(emptyFile);
		
		tags.clear();
		for (TagCategory category : library.getCategories()){
			tags.add(category.getTags().get(0));
		}		
		TaggedFileTest.createFileAndAddToLibrary("Each First Tag", tags, library);
		
		tags.clear();
		for (Tag tag : library.getCategories().get(0).getTags()){
			tags.add(tag);
		}		
		TaggedFileTest.createFileAndAddToLibrary("First Category Tags", tags, library);
		
		return library;
	}
	
	public static TagLibrary createLibraryContents(String directory, ArrayList<String> categoryNames, String tagSuffix, int tagCount){
		TagLibrary library = new TagLibrary(directory);
		for (String categoryName : categoryNames){
			ArrayList<String> tagNames = new ArrayList<String>();
			for (int i=0; i < tagCount; i++){
				tagNames.add(categoryName + " " + tagSuffix + " " + i);
			}
			TagCategory category = TagCategoryTest.createTags(categoryName, tagNames);
			library.addCategory(category);
		}
		return library;
	}
}
