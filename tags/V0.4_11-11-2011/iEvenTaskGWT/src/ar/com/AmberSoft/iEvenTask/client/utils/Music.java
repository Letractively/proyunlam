package ar.com.AmberSoft.iEvenTask.client.utils;

import java.io.Serializable;

import com.extjs.gxt.ui.client.data.BaseTreeModel;

@SuppressWarnings("serial")
public class Music extends BaseTreeModel implements Serializable {
	  public Music() {

	  }

	  public Music(String name) {
	    set("name", name);
	  }

	  public Music(String name, String author, String genre) {
	    set("name", name);
	    set("author", author);
	    set("genre", genre);
	  }

	  public String getName() {
	    return (String) get("name");
	  }

	  public String getAuthor() {
	    return (String) get("author");
	  }

	  public String getGenre() {
	    return (String) get("genre");
	  }

	  public String toString() {
	    return getName();
	  }
}
