package ar.com.AmberSoft.iEvenTask.client.menu;

import ar.com.AmberSoft.iEvenTask.client.Context;
import ar.com.AmberSoft.iEvenTask.client.IEvenTask;
import ar.com.AmberSoft.iEvenTask.client.PermissionsConst;

import com.extjs.gxt.ui.client.widget.TabPanel;


public class MainTabPanel extends TabPanel {

	public MainTabPanel(){
		super();
		setSize(IEvenTask.APP_WINDOW_WIDTH.toString(), IEvenTask.MAIN_TAB_PANEL_HEIGTH.toString());
		
		//agrego el tab de tareas
		if (Context.getInstance().isAvaiable(PermissionsConst.TAREAS)){
			MainTabTareas mainTabTareas = new MainTabTareas();
			this.add(mainTabTareas);
		}
		//agrego el tab de objetivos
		//if (Context.getInstance().isAvaiable(PermissionsConst.OBJETIVOS)){
			MainTabObjetivos mainTabObjetivos = new MainTabObjetivos();
			this.add(mainTabObjetivos);
		//}
	}
}
