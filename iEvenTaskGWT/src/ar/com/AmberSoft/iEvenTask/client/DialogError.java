package ar.com.AmberSoft.iEvenTask.client;

import ar.com.AmberSoft.iEvenTask.client.resources.Resources;

import com.extjs.gxt.ui.client.widget.Dialog;

public class DialogError extends Dialog {
	public DialogError(String text) {
		super();
		 setHeading("iEvenTask - Error");
		 addText(text);
		 setBodyStyle("fontWeight:bold;padding:13px;");
		 setSize(350, 100);
		 setHideOnButtonClick(true);
		 setButtons(Dialog.OK);
		 setIcon(Resources.ICONS.error());
		 show();
	}
}
