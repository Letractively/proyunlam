package ar.com.AmberSoft.iEvenTask.client;

import java.util.List;

public interface Seleccionable {
	/**
	 * Se invoca cuando se realiza una accion de borrado
	 */
	void onDelete();
	
	/**
	 * Se invoca cuando se realiza una accion de seleccion
	 */
	void onSelect(List selected);
	
	/**
	 * Se invoca cuando se realiza una accion de modificacion
	 */
	void onModify();
}
