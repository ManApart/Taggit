package org.iceburg.taggit.model;

public interface TagOwner {

	public void addTag(Tag tag);
	public void removeTag(Tag tag);
	public void swapTags(Tag oldTag, Tag newTag);
}
