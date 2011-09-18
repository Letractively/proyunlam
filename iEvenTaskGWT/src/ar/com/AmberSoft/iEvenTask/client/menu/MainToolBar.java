package ar.com.AmberSoft.iEvenTask.client.menu;

import ar.com.AmberSoft.iEvenTask.client.IEvenTask;
import ar.com.AmberSoft.iEvenTask.client.ObjectiveWindow;
import ar.com.AmberSoft.iEvenTask.client.TaskWindowOld;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;

public class MainToolBar extends ToolBar {

	public MainToolBar(){
		super();
		setSize(IEvenTask.APP_WINDOW_WIDTH.toString(), IEvenTask.DEFAULT_MENU_HEIGTH.toString());
		
		Button btnNuevaTarea = new Button("Nueva Tarea");
		btnNuevaTarea.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent be) {
				TaskWindowOld taskWindow = new TaskWindowOld();
				taskWindow.show();
			}
		});
		this.add(btnNuevaTarea);

		Button btnNuevoObjetivo = new Button("Nuevo Objetivo");
		btnNuevoObjetivo.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent be) {
				ObjectiveWindow objectiveWindow = new ObjectiveWindow();
				objectiveWindow.show();
			}
		});
		this.add(btnNuevoObjetivo);

	}
}
