package ar.com.AmberSoft.util;

import java.util.Comparator;

import ar.com.AmberSoft.iEvenTask.backend.entities.Tarea;

public class AsignadoTareaComparatorASC implements Comparator{

	@Override
	public int compare(Object arg0, Object arg1) {
		return ((Tarea)arg0).getAsignado().compareTo(((Tarea)arg1).getAsignado());
	}

}
