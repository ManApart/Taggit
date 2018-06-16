package org.iceburg.taggit.model;

import static org.junit.Assert.*;

import java.io.File;

import org.iceburg.taggit.Main;
import org.iceburg.taggit.Taggit;
import org.junit.Test;

public class TaggitTest {

	
	@Test
	public void testGetFileOf(){
		Main.testSetup();
		
		TaggedFile taggedFile = new TaggedFile("test.mp3", 0);
		File file = Main.getTaggit().getFileOf(taggedFile);
		String expected = "B:\\Coding\\Workspace\\Taggit\\Sample Data\\test.mp3";
		assertEquals(expected, file.getAbsolutePath());
	}
}
