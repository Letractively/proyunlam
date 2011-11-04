package ar.com.AmberSoft.iEvenTask.client;

import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.button.Button;
@SuppressWarnings({"rawtypes","unchecked"})
public class SaveButton extends Button {
	
	public static String SAVE = "Guardar";

	public SaveButton(final Window window){
		super(SAVE, new SelectionListener() {

			@Override
			public void componentSelected(ComponentEvent ce) {
				window.onSave();
			}

		});
	}	
	
}
