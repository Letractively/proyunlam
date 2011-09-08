package ar.com.AmberSoft.iEvenTask.client.menu;

import ar.com.AmberSoft.iEvenTask.client.EventWindow;
import ar.com.AmberSoft.iEvenTask.client.ProfilesWindow;
import ar.com.AmberSoft.iEvenTask.client.RelationWindow;

import com.extjs.gxt.ui.client.widget.menu.Menu;

public class FileMenu extends Menu {

	MenuItem profileItem;
	MenuItem eventItem;
	MenuItem actionsItem;
	MenuItem preferencesItem;
	
	public FileMenu(){
		profileItem = new MenuItem("Perfiles", new ProfilesWindow());
		add(profileItem);
		eventItem = new MenuItem("Eventos", new EventWindow());
		add(eventItem);
		eventItem = new MenuItem("Relaciones", new RelationWindow());
		add(eventItem);
		actionsItem = new MenuItem("Acciones", null);
		add(actionsItem);
		preferencesItem = new MenuItem("Preferencias", null);
		add(preferencesItem);
	}
	
	
}
