package ar.com.AmberSoft.util;


public class AsignadoTareaComparatorDESC extends AsignadoTareaComparatorASC {
	@Override
	public int compare(Object arg0, Object arg1) {
		return super.compare(arg0, arg1) * -1;
	}
}
