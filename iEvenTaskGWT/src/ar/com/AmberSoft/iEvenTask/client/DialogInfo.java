package ar.com.AmberSoft.iEvenTask.client;

import ar.com.AmberSoft.iEvenTask.client.resources.Resources;

import com.extjs.gxt.ui.client.widget.Dialog;

public class DialogInfo extends Dialog {

	public DialogInfo(String text) {
		super();
		 setHeading("iEvenTask - Info");
		 addText(text);
		 setBodyStyle("fontWeight:bold;padding:13px;");
		 setSize(350, 150);
		 setHideOnButtonClick(true);
		 setButtons(Dialog.OK);
		 setIcon(Resources.ICONS.info());
		 show();
	}

}
