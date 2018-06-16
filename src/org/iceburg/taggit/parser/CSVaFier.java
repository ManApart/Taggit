package org.iceburg.taggit.parser;

import java.util.ArrayList;

import org.iceburg.taggit.model.Tag;
import org.iceburg.taggit.model.TagCategory;
import org.iceburg.taggit.model.TagLibrary;
import org.iceburg.taggit.model.TaggedFile;

public class CSVaFier {
	public static final String TAG_NAME = "Tag:", CATEGORY_NAME = "Category:", FILE_NAME = "File:", LIBRARY_NAME = "Library:";
//	private static final String DELIMETER = ",";
	
	public static ArrayList<String> libraryToList(TagLibrary library){
		ArrayList<String> strings = new ArrayList<String>();
		strings.add(createLibraryHeaderString(library));
		for (TagCategory category: library.getCategories()){
			strings.add(categoryToString(category));
		}
		for (TaggedFile file: library.getAllFiles()){
			if (file.hasTags()){
				strings.add(fileToString(library, file));
			}
		}
		return strings;
	}
	public static String createLibraryHeaderString(TagLibrary library){
		String line = LIBRARY_NAME +",";
		
		for (String folder : library.getDirectories()){			
			line += folder + ",";
		}
		//remove last comma
		line = line.substring(0, line.length()-1);
		
		return line;
	}
	
	public static TagLibrary stringsToLibrary(ArrayList<String> lines) {
		TagLibrary library = findAndCreateLibrary(lines);
		findAndCreateTagCategories(lines, library);
		findAndCreateFiles(lines, library);
		return library;
	}
	
	private static TagLibrary findAndCreateLibrary(ArrayList<String> lines) {
		for (String line : lines){
			if (line.startsWith(LIBRARY_NAME)){
				return stringToLibrary(line);
			}
		}
		return null;
	}
	private static TagLibrary stringToLibrary(String libraryHeaderLine) {
		String[] fields = libraryHeaderLine.split(",");		
		TagLibrary library = new TagLibrary(fields[1]);
		for (int i=2; i< fields.length; i++){
			library.addDirectory(fields[i]);
		}
		return library;
	}
	private static void findAndCreateTagCategories(ArrayList<String> lines, TagLibrary library) {
		for (String line : lines){
			if (line.startsWith(CATEGORY_NAME)){
				TagCategory category = stringToCategory(line);
				library.addCategory(category);
			}
		}
		
	}
	private static void findAndCreateFiles(ArrayList<String> lines, TagLibrary library) {
		for (String line : lines){
			if (line.startsWith(FILE_NAME)){
				TaggedFile file = stringToTaggedFile(line, library);
				library.addFile(file);
			}
		}
		
	}
	public static String categoryToString(TagCategory category){
		String line = CATEGORY_NAME + ",";
		line += category.getName() + ",";
		
		for (Tag tag : category.getTags()){
			line += tag.getName() + ",";
		}
		//remove last comma
		line = line.substring(0, line.length()-1);
		
		return line;
	}
	public static TagCategory stringToCategory(String line) {
		String[] fields = line.split(",");
		//0 is the header, not needed
		String name = fields[1];
		TagCategory category = new TagCategory(name);
		
		for (int i=2; i< fields.length; i++){
			Tag tag = new Tag(fields[i], category);
			category.addTag(tag);
		}
		return category;
	}
	
	public static String fileToString(TagLibrary library, TaggedFile file){
		String line = FILE_NAME + ",";
		line += file.getName() + ",";
		line += file.getDirectoryIndex() + ",";
		
		for (Tag tag : file.getTags()){
			line += tag.getName() + ",";
		}
		//remove last comma
		line = line.substring(0, line.length()-1);
		
		return line;
	}
	public static TaggedFile stringToTaggedFile(String line, TagLibrary library) {
		String[] fields = line.split(",");
		//0 is the header, not needed
		String name = fields[1];
		int directoryIndex = Integer.parseInt(fields[2]);
		TaggedFile file = new TaggedFile(name, directoryIndex);
		TagCategory noCat = new TagCategory("No Category");
				
		for (int i=3; i< fields.length; i++){
			String tagName = fields[i];
			if (library.tagExists(tagName)){
				Tag tag = library.getTag(tagName);
				file.addTag(tag);
				
			} else{
				System.out.println(tagName + " does not exist in library");
				Tag tag = new Tag(tagName, noCat);
				noCat.addTag(tag);
				file.addTag(tag);
			}
			
		}
		return file;
	}

	
	

}
