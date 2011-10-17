package ar.com.AmberSoft.iEvenTask.services;

import java.util.Date;
import java.util.Map;

import ar.com.AmberSoft.iEvenTask.backend.entities.Entity;
import ar.com.AmberSoft.iEvenTask.backend.entities.Objetivo;
import ar.com.AmberSoft.util.ParamsConst;

public class CreateObjectiveService extends CreateService {

	@SuppressWarnings("rawtypes")
	@Override
	public Entity getEntity(Map params) {
		Objetivo objetivo = new Objetivo();
		objetivo.setNombreObjetivo((String)params.get(ParamsConst.NOMBRE_OBJETIVO));
		objetivo.setTipoObjetivo((String)params.get(ParamsConst.TIPO_OBJETIVO));
		objetivo.setEscalaMedicion((String)params.get(ParamsConst.ESCALA_MEDICION));
		objetivo.setFechaFinalizacion((Date)params.get(ParamsConst.FECHA_FINALIZACION));
		objetivo.setPonderacion((Integer)params.get(ParamsConst.PONDERACION));
		objetivo.setIdUsuarioAsignado((String) params.get(ParamsConst.ID_USUARIO_ASIGNADO));
		objetivo.setDescripcion((String)params.get(ParamsConst.DESCRIPCION));
		
		return objetivo;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map onEmulate(Map params) {
		// TODO Auto-generated method stub
		return null;
	}

}
