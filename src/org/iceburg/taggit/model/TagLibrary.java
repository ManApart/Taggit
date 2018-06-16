package org.iceburg.taggit.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.iceburg.taggit.Main;
import org.iceburg.taggit.parser.Parser;

public class TagLibrary {
	private ArrayList<String> directories;
	private ArrayList<TaggedFile> files, filteredFiles;
	private HashMap<String, TagCategory> categories;
	private String filePath;
	private TaggedFile clipboardFile;
	
	public TagLibrary(String directory){
		this.directories = new ArrayList<String>();
		this.directories.add(directory);
		this.categories = new HashMap<String, TagCategory>();		
//		this.addCategory(Tag.blankTag().getCategory());		
		this.files = new ArrayList<TaggedFile>();	   		
//		this.filteredFiles = new ArrayList<TaggedFile>();	   		
	}
	

	@Override
	public String toString(){
		return "Library for " + getDirectory(0);
	}

	public void addTestCategories() {
		TagCategory cat = new TagCategory("Test A"); 
		cat.addTag(new Tag("Tag A", cat));
		cat.addTag(new Tag("Tag A2", cat));
		cat.addTag(new Tag("Tag A3", cat));
		addCategory(cat);

		cat = new TagCategory("Test B"); 
		cat.addTag(new Tag("Tag B1", cat));
		cat.addTag(new Tag("Tag B2", cat));
		cat.addTag(new Tag("Tag B3", cat));
		cat.addTag(new Tag("Tag B4", cat));
		addCategory(cat);

		cat = new TagCategory("Test C"); 
		cat.addTag(new Tag("Tag C1", cat));
		addCategory(cat);
	}
	
	public ArrayList<TaggedFile> getAllFiles(){
		return files;
	}
	public ArrayList<TaggedFile> getFilteredFiles(){
		if (filteredFiles == null){
			return new ArrayList<TaggedFile>(files);
		}
		return filteredFiles;
	}
	
	
	public void parseFilesFromDirectories(){
		for (int i=0; i < directories.size(); i++){
			String directory = directories.get(i);
			ArrayList<TaggedFile> newFiles = Parser.getFiles(directory, i);
			for (TaggedFile file : newFiles){
				if (!getAllFiles().contains(file)){
					getAllFiles().add(file);
				}
			}
		}
	}
	
	public void addDirectory(String directory){
		if (!directories.contains(directory)){
			this.directories.add(directory);
		}
	}
	public void addFile(TaggedFile file){
		this.files.add(file);
	}
	
	public String getDirectory(int index){
		if (index < 0 || index >= directories.size()){
			return directories.get(0);
		}
		return directories.get(index);
	}
	public ArrayList<String> getDirectories(){
		return directories;
	}

	public ArrayList<TagCategory> getCategories() {
		ArrayList<TagCategory> tagCategories = new ArrayList<TagCategory>(categories.values());
		tagCategories.sort(new TagCategory.TagCategoryComparator());
		return tagCategories;
	}
	
	public TagCategory getCategory(String name){
		if (categoryExists(name)){
			return categories.get(name);
		}
		return null;
	}
	public Tag getTag(String name){
		for (TagCategory cat : categories.values()){
			if (cat.hasTag(name)){
				return cat.getTag(name);
			}
		}
		return null;
	}
	public Tag getFirstTag(){
		for (TagCategory cat : getCategories()){
			if (cat.getTags().size() > 0){
				return cat.getTags().get(0);
			}
		}
		return null;
	}
	public TaggedFile getFile(String name){
		for (TaggedFile file : getAllFiles()){
			if (file.getName().equals(name)){
				return file;
			}
		}
		return null;
	}
	
	public void addCategory(TagCategory category){
		categories.put(category.getName(), category);
	}
	public boolean categoryExists(String name){
		return (categories.containsKey(name));
	}
	public boolean tagExists(String name){
		for (TagCategory cat : categories.values()){
			if (cat.hasTag(name)){
				return true;
			}
		}
		return false;
	}
	public void removeTag(Tag tag){
		for (TaggedFile file : getAllFiles()){
			file.removeTag(tag);
		}
		tag.getCategory().removeTag(tag);
	}
	public void removeCategory(TagCategory category){
		for (Tag tag : category.getTags()){
			for (TaggedFile file : getAllFiles()){
				file.removeTag(tag);
			}
		}
		categories.remove(category.getName());
	}
	public void removeTaggedFile(TaggedFile fileToDelete){
		getAllFiles().remove(fileToDelete);
	}
	
	public String getPathOfFile(TaggedFile taggedFile) {
		String folder = getDirectory(taggedFile.getDirectoryIndex());
		String absolutePath = folder + "\\"+ taggedFile.getName();
		File file = new File(absolutePath);
		if (file.exists()){
			return absolutePath;
		}
		
		//if the set directory didn't work, try other directories
		for (String directory : directories){
			absolutePath = directory + "\\"+ taggedFile.getName();
			file = new File(absolutePath);
			if (file.exists()){
				return absolutePath;
			}
		}
		
		return null;
	}


	public String getSaveFilePath() {
		return filePath;
	}


	public void setSaveFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public void doSearch(TaggedFile include, TaggedFile exclude){
		filteredFiles = new ArrayList<TaggedFile>(getAllFiles());
		filterOutExcludedFiles(exclude);
		filterOutAllButIncludedFiles(include);
		
	}




	private void filterOutAllButIncludedFiles(TaggedFile include) {
		if (include == null){
			return;
		}
		if (include.getTags().size() == 0){
			return;
		}
		for (int i= filteredFiles.size()-1; i >=0; i--){
			TaggedFile libFile = filteredFiles.get(i);
			boolean hasAllTags = true;
			for (Tag matchTag : include.getTags()){
				if (!libFile.hasTag(matchTag.getName())){
					hasAllTags = false;
					break;
				}
			}
			if (!hasAllTags){
				filteredFiles.remove(libFile);
			}
		}
		
	}
	private void filterOutExcludedFiles(TaggedFile exclude) {
		if (exclude == null){
			return;
		}
		for (Tag matchTag : exclude.getTags()){
			for (int i= filteredFiles.size()-1; i >=0; i--){
				TaggedFile libFile = filteredFiles.get(i);
				if (libFile.hasTag(matchTag.getName())){
					filteredFiles.remove(libFile);
				}
			}
		}
	}


	public void saveToClipboard(TaggedFile file) {
		this.clipboardFile = file;		
	}


	public void pasteFromClipboard(TaggedFile file) {
		if (clipboardFile != null){
			clipboardFile.copyTagsTo(file);
		}		
		Main.getUI().sendUpdate(file);
	}
	

}
