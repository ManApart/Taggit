package org.iceburg.taggit.parser;

import static org.junit.Assert.*;

import java.io.File;

import org.iceburg.taggit.model.Tag;
import org.iceburg.taggit.model.TagCategory;
import org.iceburg.taggit.model.TagLibrary;
import org.iceburg.taggit.model.TaggedFile;
import org.junit.Test;

public class ParserTest {
	
	
	private TagLibrary addTestCategories(TagLibrary library) {
		TagCategory cat = new TagCategory("Test A"); 
		cat.addTag(new Tag("Tag A", cat));
		library.addCategory(cat);

		cat = new TagCategory("Test B"); 
		cat.addTag(new Tag("Tag B1", cat));
		cat.addTag(new Tag("Tag B2", cat));
		library.addCategory(cat);
		library.addCategory(new TagCategory("Test C"));
		return library;
	}
	
	
	@Test
	public void writeReadLibrary(){
		String directory = "B:\\Coding\\Workspace\\Taggit\\Sample Data";
		TagLibrary library = new TagLibrary(directory);
		addTestCategories(library);
		
		Parser parser = new Parser();
		String fileName = directory + "\\testLibrary.csv";
		
		parser.writeLibrary(fileName, library);
		
		TagLibrary newLibrary = parser.readLibrary(fileName);
		
		
		assertTrue(newLibrary.categoryExists("Test A"));
		assertTrue(newLibrary.categoryExists("Test B"));
		assertTrue(newLibrary.categoryExists("Test C"));
		
		assertTrue(newLibrary.tagExists("Tag A"));
		assertTrue(newLibrary.tagExists("Tag B1"));
		assertTrue(newLibrary.tagExists("Tag B2"));
		
	}

	@Test
	public void getNameWithoutFileExtension(){		
		File file = new File("file.test");
		Parser parser = new Parser();
		String name = parser.getFileNameWithoutExtension(file);
		assertEquals("file", name);

		file = new File("video_example.mp4");
		name = parser.getFileNameWithoutExtension(file);
		assertEquals("video_example", name);
		
		file = new File("complicated.tar.gz");
		name = parser.getFileNameWithoutExtension(file);
		assertEquals("complicated", name);
		
		file = new File("file");
		name = parser.getFileNameWithoutExtension(file);
		assertEquals("file", name);
		
		file = new File("");
		name = parser.getFileNameWithoutExtension(file);
		assertEquals("", name);
		
		file = null;
		name = parser.getFileNameWithoutExtension(file);
		assertEquals("", name);
	}
	
	@Test
	public void getFileExtension(){
		File file = new File("file.test");
		Parser parser = new Parser();
		String name = parser.getFileExtension(file);
		assertEquals(".test", name);

		file = new File("video_example.mp4");
		name = parser.getFileExtension(file);
		assertEquals(".mp4", name);
		
		file = new File("complicated.tar.gz");
		name = parser.getFileExtension(file);
		assertEquals(".tar.gz", name);
		
		file = new File("file");
		name = parser.getFileExtension(file);
		assertEquals("", name);
		
		file = new File("");
		name = parser.getFileExtension(file);
		assertEquals("", name);
		
		file = null;
		name = parser.getFileExtension(file);
		assertEquals("", name);
	}
	
	@Test
	public void renameFile(){
		File file = new File("B:\\Coding\\Workspace\\Taggit\\Sample Data\\Rename Test.csv");
		Parser parser = new Parser();
		
		String expected = "Rename Test 2";
		file = parser.attemptToRenameFile(file, expected);
		assertTrue(file != null);
		assertEquals(expected, parser.getFileNameWithoutExtension(file));
		assertEquals(".csv", parser.getFileExtension(file));
		
		expected = "Rename Test";
		file = parser.attemptToRenameFile(file, expected);
		assertTrue(file != null);
		assertEquals(expected, parser.getFileNameWithoutExtension(file));
		assertEquals(".csv", parser.getFileExtension(file));
		
		file = parser.attemptToRenameFile(file, "");
		assertTrue(file != null);
		assertEquals(expected, parser.getFileNameWithoutExtension(file));
		assertEquals(".csv", parser.getFileExtension(file));
		
		file = parser.attemptToRenameFile(file, null);
		assertTrue(file != null);
		assertEquals(expected, parser.getFileNameWithoutExtension(file));
		assertEquals(".csv", parser.getFileExtension(file));
		
		file = parser.attemptToRenameFile(null, "new name");
		assertTrue(file == null);
		assertEquals("", parser.getFileNameWithoutExtension(file));
		assertEquals("", parser.getFileExtension(file));
	}
}
