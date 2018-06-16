package org.iceburg.taggit.parser;

import static org.junit.Assert.*;

import java.lang.reflect.Array;
import java.util.ArrayList;

import org.iceburg.taggit.model.Tag;
import org.iceburg.taggit.model.TagCategory;
import org.iceburg.taggit.model.TagCategoryTest;
import org.iceburg.taggit.model.TagLibrary;
import org.iceburg.taggit.model.TaggedFile;
import org.iceburg.taggit.model.TaggedFileTest;
import org.iceburg.taggit.model.TaggedLibraryTest;
import org.junit.Test;

public class CSVaFierTest {
	
	
	@Test
	public void categoryToStringTest(){
		TagCategory category = new TagCategory("Test Cat");
		category.addTag(new Tag("Tag A", category));

		String expected = CSVaFier.CATEGORY_NAME + ",Test Cat,Tag A";
		String actual = CSVaFier.categoryToString(category);
		assertEquals(expected,actual);

		category = new TagCategory("Test Cat 2");
		category.addTag(new Tag("C Tag", category));
		category.addTag(new Tag("Tag B", category));
		
		expected = CSVaFier.CATEGORY_NAME + ",Test Cat 2,C Tag,Tag B";
		actual = CSVaFier.categoryToString(category);
		assertEquals(expected,actual);
		
		
		TagCategory readCat = CSVaFier.stringToCategory(expected);
		assertTrue(TagCategoryTest.testCategoriesMatch(category, readCat));
		
	}
	@Test
	public void fileToStringTest(){
		String path = "B:\\Coding\\Workspace\\Taggit\\Sample Data";
		TaggedFile file = new TaggedFile("File.mp3", 0);
		TagLibrary library = new TagLibrary(path);

		ArrayList<String> tagNames = new ArrayList<String>();
		tagNames.add("Tag A");
		tagNames.add("Tag B");
		TagCategory category = TagCategoryTest.createTagsAndAddThemToFile(file, "Test Cat", tagNames);
		library.addCategory(category);

		tagNames.clear();
		tagNames.add("Weapons");
		tagNames.add("Cool Stuff");
		category = TagCategoryTest.createTagsAndAddThemToFile(file, "Contents", tagNames);
		library.addCategory(category);			
	
		String expected = CSVaFier.FILE_NAME + ",File.mp3,0,Tag A,Tag B,Weapons,Cool Stuff";
		String actual = CSVaFier.fileToString(library, file);
		assertEquals(expected,actual);

		
		TaggedFile readFile = CSVaFier.stringToTaggedFile(expected, library);
		assertTrue(TaggedFileTest.testFilesMatch(file, readFile));
		
	}

	
	
	@Test
	public void libraryToStringTest(){
		TagLibrary library = TaggedLibraryTest.createTestLibrary();
		ArrayList<String> actualLines = CSVaFier.libraryToList(library);
		
		ArrayList<String> expectedLines = createTestLibraryStrings();
		
		for (int i=0; i <expectedLines.size(); i++){
			String expected = expectedLines.get(i);
			String actual = actualLines.get(i);
			assertEquals(expected, actual);
		}
		
		TagLibrary readLibrary = CSVaFier.stringsToLibrary(expectedLines);
		//Remove our empty file from our initial library, so the read in file matches
		TaggedFile emptyFile = library.getFile("Empty");
		library.removeTaggedFile(emptyFile);
		assertTrue(TaggedLibraryTest.testLibrariesMatch(library, readLibrary));
	}
	
	
	
	private ArrayList<String> createTestLibraryStrings(){
		ArrayList<String> strings = new ArrayList<String>();
		strings.add(CSVaFier.LIBRARY_NAME + ",B:\\Coding\\Workspace\\Taggit\\Sample Data");
		strings.add(CSVaFier.CATEGORY_NAME + ",People,People Tag 0,People Tag 1,People Tag 2");
		strings.add(CSVaFier.CATEGORY_NAME + ",Places,Places Tag 0,Places Tag 1,Places Tag 2");
		strings.add(CSVaFier.CATEGORY_NAME + ",Things,Things Tag 0,Things Tag 1,Things Tag 2");
		strings.add(CSVaFier.FILE_NAME + ",All Tags,0,People Tag 0,People Tag 1,People Tag 2,Places Tag 0,Places Tag 1,Places Tag 2,Things Tag 0,Things Tag 1,Things Tag 2");
		strings.add(CSVaFier.FILE_NAME + ",Each First Tag,0,People Tag 0,People Tag 1,People Tag 2,Places Tag 0,Places Tag 1,Places Tag 2,Things Tag 0,Things Tag 1,Things Tag 2,People Tag 0,Places Tag 0,Things Tag 0");
		strings.add(CSVaFier.FILE_NAME + ",First Category Tags,0,People Tag 0,People Tag 1,People Tag 2");
//		strings.add(CSVaFier.CATEGORY_NAME + "");
		return strings;
	}
	

	
	
	

	


	


}
