package org.iceburg.taggit.model;

import java.util.ArrayList;

import org.iceburg.taggit.Main;

public class TaggedFile implements TagOwner{
	private int directoryIndex;
	private String name;
	private ArrayList<Tag> tags;
	
	
	public TaggedFile(String name, int directoryIndex){
		this.name = name;
		this.directoryIndex = directoryIndex;
		this.tags = new ArrayList<Tag>();
	}
	
	@Override
	public String toString(){
		return getName();
	}
	
	@Override
	public boolean equals(Object other){
		if (other instanceof TaggedFile){
			TaggedFile otherTag = (TaggedFile) other;
			if (getName().equals(otherTag.getName())){
				if (getDirectoryIndex() == otherTag.getDirectoryIndex()){
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void addTag(Tag tag) {
//		System.out.println("File: Adding " + tag);
		getTags().add(tag);
	}

	@Override
	public void removeTag(Tag tag){
		System.out.println("File: Removing " + tag);
		getTags().remove(tag);
		Main.getUI().sendUpdate(this);
	}	

	@Override
	public void swapTags(Tag oldTag, Tag newTag) {
		System.out.println("File: Swapping " + oldTag + " for " + newTag);
		getTags().remove(oldTag);
		getTags().add(newTag);
		
	}


	public String getName() {
		return name;
	}
	
	public int getDirectoryIndex(){
		return directoryIndex;
	}

	public void setName(String name){
		this.name = name;
	}


	public ArrayList<Tag> getTags() {
		return tags;
	}

	public void addBlankTag(){
//		if (!tags.contains(Tag.blankTag())){
//			tags.add(Tag.blankTag());
//		}
		tags.add(Main.getTaggit().getLibrary().getFirstTag());
	}
	
	public boolean hasTag(String tagName){
		for (Tag tag : getTags()){
			if (tag.getName().equals(tagName)){
				return true;
			}
		}
		return false;
	}

	public boolean hasTags() {
		if (getTags().size() > 0){
			return true;
		}
		return false;
	}

	public void copyTagsTo(TaggedFile file) {
		for (Tag tag : getTags()){
			file.addTag(tag);
		}
	}



}
