package ar.com.AmberSoft.iEvenTask.client;

import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.button.Button;

public class CancelButton extends Button {
	
	public static String CANCEL = "Cancelar";
	
	public CancelButton(final Window window){
		super(CANCEL, new SelectionListener() {

			@Override
			public void componentSelected(ComponentEvent ce) {
				window.onCancel();
			}

		});
	}
	
	
}
