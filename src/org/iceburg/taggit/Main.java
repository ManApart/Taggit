package org.iceburg.taggit;

import javax.swing.SwingUtilities;

import org.iceburg.taggit.ui.TagUIManager;

public class Main {
	private static TagUIManager uiManager;
	private static Taggit taggit;

	
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				uiManager = new TagUIManager();
				taggit = new Taggit();
				taggit.createTestLibrary();
			}
		});
	}
	public static TagUIManager getUI(){
		return uiManager;
	}
	public static Taggit getTaggit(){
		return taggit;
	}
	
	public static void testSetup(){
		uiManager = new TagUIManager();
		taggit = new Taggit();
		taggit.createTestLibrary();
	}
}
