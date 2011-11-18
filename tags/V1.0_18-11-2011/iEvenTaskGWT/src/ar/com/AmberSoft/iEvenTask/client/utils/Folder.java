package ar.com.AmberSoft.iEvenTask.client.utils;

import java.io.Serializable;

import com.extjs.gxt.ui.client.data.BaseTreeModel;

@SuppressWarnings("serial")
public class Folder extends BaseTreeModel implements Serializable {
	@Override
	public boolean isLeaf() {
		return Boolean.FALSE;
	}

	private static int ID = 0;

	public Folder() {
		set("id", ID++);
	}

}
