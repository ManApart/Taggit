package org.iceburg.taggit;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import org.iceburg.taggit.model.TagLibrary;
import org.iceburg.taggit.model.TaggedFile;
import org.iceburg.taggit.parser.Parser;


public class Taggit {
	private Parser parser;
	private TagLibrary library;
	
	public Taggit(){
		this.parser = new Parser();
	}
	
	public Parser getParser(){
		return parser;
	}
	
	
	public void createNewLibrary(){
		String directory = parser.promptCreateNewLibrary();
		if (directory == null){
			return;
		}
		library = new TagLibrary(directory);
		library.parseFilesFromDirectories();
		Main.getUI().sendUpdate(library);
		
	}
	public void createTestLibrary(){
//		String directory = "B:\\Videos\\My Videos\\Video Game stuff\\Halo 5";
		String directory = "B:\\Coding\\Workspace\\Taggit\\Sample Data";
		if (directory != null){
			library = new TagLibrary(directory);
			library.addTestCategories();
			library.parseFilesFromDirectories();
			Main.getUI().sendUpdate(library);
		}
	}
	public void openLibrary(){
		String libraryPath = parser.promptOpenLibrary();
		if (libraryPath == null){
			return;
		}
		this.library = parser.readLibrary(libraryPath);
		library.setSaveFilePath(libraryPath);
		library.parseFilesFromDirectories();
		Main.getUI().sendUpdate(library);
		
	}
	public void addLibraryDirectory(){
		String directory = parser.promptCreateNewLibrary();
		if (directory == null){
			return;
		}
		library.addDirectory(directory);
		library.parseFilesFromDirectories();
		Main.getUI().sendUpdate(library);
		
	}
	
	public void saveLibrary(){
		String directory = library.getSaveFilePath();
		if (directory == null){
			directory = parser.promptSaveLibrary(library.getDirectory(0));
		}
		if (directory == null){
			return;
		}
		parser.writeLibrary(directory, library);
	}
	public void saveLibraryAs(){
		String directory = parser.promptSaveLibrary(library.getDirectory(0));
		if (directory == null){
			return;
		}
		parser.writeLibrary(directory, library);
	}
	
	public TagLibrary getLibrary(){
		return library;
	}
	
	public void openFile(TaggedFile taggedFile){
		String fileName = library.getPathOfFile(taggedFile);
		File file = new File (fileName);
		
		Desktop desktop = Desktop.getDesktop();
		try {
			desktop.open(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public File getFileOf(TaggedFile taggedFile){
		String fileName = library.getDirectory(taggedFile.getDirectoryIndex())+ "\\" + taggedFile.getName();
		File file = new File (fileName);
		return file;
	}
	
	
	public void openFolder(TaggedFile taggedFile){
//		String fileName = library.getDirectory(taggedFile.getDirectoryIndex());
		String fileName = library.getPathOfFile(taggedFile);
		File file = new File (fileName);
		Desktop desktop = Desktop.getDesktop();
		try {
//			desktop.open(file);
			Runtime.getRuntime().exec("explorer.exe /select," + file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void doSearch(TaggedFile include, TaggedFile exclude){
		getLibrary().doSearch(include, exclude);
		Main.getUI().sendUpdate(getLibrary());
	}
	
}
