package ar.com.AmberSoft.iEvenTask.client.menu;

import ar.com.AmberSoft.iEvenTask.client.IEvenTask;

import com.extjs.gxt.ui.client.widget.TabPanel;


public class MainTabPanel extends TabPanel {

	public MainTabPanel(){
		super();
		setSize(IEvenTask.APP_WINDOW_WIDTH.toString(), IEvenTask.MAIN_TAB_PANEL_HEIGTH.toString());
		
		//agrego el tab de tareas
		MainTabTareas mainTabTareas = new MainTabTareas();
		this.add(mainTabTareas);
		
		//agrego el tab de objetivos
		MainTabObjetivos mainTabObjetivos = new MainTabObjetivos();
		this.add(mainTabObjetivos);
	}
}
