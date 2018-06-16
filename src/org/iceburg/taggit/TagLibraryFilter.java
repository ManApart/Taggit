package org.iceburg.taggit;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import org.iceburg.taggit.parser.Parser;


public class TagLibraryFilter extends FileFilter{


	@Override
	public boolean accept(File f) {
	    if (f.isDirectory()) {
	        return true;
	    }

	    String extension = Parser.getExtension(f);
	    if (extension != null) {
	        if (extension.equals("csv")) {
	                return true;
	        } else {
	            return false;
	        }
	    }

	    return false;
	}

	@Override
	public String getDescription() {
		return "Taggit Library (.csv)";
	}
	


}
