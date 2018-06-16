package org.iceburg.taggit.parser;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

import org.iceburg.taggit.Main;
import org.iceburg.taggit.TagLibraryFilter;
import org.iceburg.taggit.model.TagLibrary;
import org.iceburg.taggit.model.TaggedFile;

public class Parser {
	
	public String promptCreateNewLibrary(){
		File f = null;
		final JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Select a Folder for the Library");
		fc.setMultiSelectionEnabled(false);
		Action details = fc.getActionMap().get("viewTypeDetails");
		details.actionPerformed(null);
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    fc.setAcceptAllFileFilterUsed(false);
	    
	    //for testing
	    fc.setCurrentDirectory(new File("B://Coding//Workspace//Taggit//Sample Data"));
	    
		if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			f = fc.getSelectedFile();
		}
		if (f != null && f.exists()) {
			System.out.println(f.getAbsolutePath());
			return f.getAbsolutePath();
		}
		return null;
	}
	public String promptOpenLibrary(){
		File f = null;
		final JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Find Library.csv file");
		fc.setMultiSelectionEnabled(false);
		Action details = fc.getActionMap().get("viewTypeDetails");
		details.actionPerformed(null);
		fc.setFileFilter(new TagLibraryFilter());
		
		//for testing
	    fc.setCurrentDirectory(new File("B://Coding//Workspace//Taggit//Sample Data"));
		
		if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			f = fc.getSelectedFile();
		}
		if (f != null && f.exists()) {
			return f.getAbsolutePath();
		}
		return null;
	}
	public String promptSaveLibrary(String defaultDirectory){
		File f = null;
		final JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Find Library.tag file");
		fc.setMultiSelectionEnabled(false);
		Action details = fc.getActionMap().get("viewTypeDetails");
		details.actionPerformed(null);
		fc.setFileFilter(new TagLibraryFilter());
		
		if (defaultDirectory != null){
			File defaultDir = new File(defaultDirectory);
			if (defaultDir.exists()){
				fc.setCurrentDirectory(defaultDir);
			}
		}
		
		//for testing
//		fc.setCurrentDirectory(new File("B://Coding//Workspace//Taggit//Sample Data"));
		
		if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			f = fc.getSelectedFile();
		
			String fileName = f.getAbsolutePath();
			if (!fileName.endsWith(".csv")){
				fileName += ".csv";
			}
			return fileName;
		}
		return null;
	}
	
	public static ArrayList<TaggedFile> getFiles(String directory, int directoryIndex){
		ArrayList<TaggedFile> files = new ArrayList<TaggedFile>();
		if (directory == null){
			return files;
		}
		File folder = new File(directory);
		if (!folder.exists()){
			return files;
		}
		File[] listOfFiles = folder.listFiles();
	    for (int i = 0; i < listOfFiles.length; i++) {
	    	File file = listOfFiles[i];
	    	if (file.getName().endsWith(".csv") || file.getName().endsWith(".lnk") || file.getName().endsWith(".sfk")){
	    		System.out.println("Skipping csv " + file.getName());
	    		continue;
	    	}
			if (file.isFile()) {
				System.out.println("File: " + file.getName());
				TaggedFile tagFile = new TaggedFile(file.getName(),directoryIndex);
				files.add(tagFile);
			} else if (file.isDirectory()) {
				System.out.println("Directory: " + file.getName());
			}
		}
	    return files;
	}
	
	public static String getExtension(File f) {
	     String ext = null;
	     String s = f.getName();
	     int i = s.lastIndexOf('.');

	     if (i > 0 &&  i < s.length() - 1) {
	         ext = s.substring(i+1).toLowerCase();
	     }
	     return ext;
	 }
	
	public TagLibrary readLibrary(String fileName){
		BufferedReader fileReader = null;
	    ArrayList<String> lines = new ArrayList<String>();
        try {
            String line = "";
            fileReader = new BufferedReader(new FileReader(fileName));
//            fileReader.readLine();
            
            //Read the file line by line starting from the second line
            while ((line = fileReader.readLine()) != null) {
               lines.add(line);
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        TagLibrary library = CSVaFier.stringsToLibrary(lines);

		return library;
	}

	public void writeLibrary(String fileName, TagLibrary library){
		ArrayList<String> libraryStrings = CSVaFier.libraryToList(library);
		
		FileWriter writer =null;
		try {
			writer = new FileWriter(fileName);
			for (String line : libraryStrings){
				writer.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
			try {
				if (writer != null){
					writer.flush();
					writer.close();
				}
			} catch (IOException e) {
                e.printStackTrace();
			}
		}
		System.out.println("Saved!");
	}
	
	public File attemptToRenameFile(File file, String newName){
		if (file == null || !file.exists()){
			System.out.println("File not valid!");
			return file;
		}
		if (newName == null || newName.isEmpty()){
			System.out.println("New Name not valid!");
			return file;
		}
		
		String newNameFormatted = getFileNameWithoutExtension(new File(newName));
		String extension = getFileExtension(file);
		newNameFormatted += extension;
		
		File dest = new File(file.getParent() + "\\" + newNameFormatted);
		if (dest.exists()){
			System.out.println(dest.getName() + " already exists!");
			return file;
		}
		Path source = file.toPath();
		try {
			Files.move(source, source.resolveSibling(newNameFormatted));
		} catch (IOException e) {
			e.printStackTrace();
		}	
		if (!dest.exists()){
			System.out.println(dest.getName() + " not created!");
			return file;
		}
		System.out.println("Renamed " + file.getName() + "to " + newName);
		return dest;
	}
	public String getFileNameWithoutExtension(File file) {
		if (file == null || file.getName() == null || file.getName().isEmpty()){
			return "";
		}
		String name = file.getName();
		return getFileNameWithoutExtension(name);
	}
	public String getFileNameWithoutExtension(String name) {
		int index = name.indexOf('.');
		if (index == -1){
			return name;
		}
		name = name.substring(0, index);
		return name;
	}
	public String getFileExtension(File file){
		if (file == null || file.getName() == null || file.getName().isEmpty()){
			return "";
		}
		String name = file.getName();
		int index = name.indexOf('.');
		if (index == -1){
			return "";
		}
		name = name.substring(index, name.length());
		return name;
	}

}
