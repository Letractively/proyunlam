package ar.com.AmberSoft.iEvenTask.client;

import java.io.Serializable;

@SuppressWarnings("serial")
public class State implements Serializable {

	/**
	 * Constante que representa el estado nuevo
	 */
	public final static  State NEW_STATE = new NewState();
	
	/**
	 * Constante que representa el estado actualizacion
	 */
	public final static  State UPDATE_STATE = new UpdateState();
	
	/**
	 * Constante que representa el estado desconocido
	 */
	public final static  State UNKNOW_STATE = new UnknowState();

	/**
	 * Evalua si los tipos de estados son iguales
	 */
	@Override
	public boolean equals(Object state) {
		return this.getClass().equals(state.getClass());
	}
	
	
	
	
}
