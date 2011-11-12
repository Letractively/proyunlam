package ar.com.AmberSoft.iEvenTask.client.validaciones;

import java.util.Date;

import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.Validator;

public class ValidateFecha implements Validator {
	
	DateField inicio;
	
	public ValidateFecha(DateField inicio){
		this.inicio = inicio;
	}

	@Override
	public String validate(Field<?> field, String value) {
		if ((inicio.getValue()!=null) && (field.getValue()!=null) && (((DateField)field).getValue().before(inicio.getValue()))){
			return "La fecha de finalizacion debe ser posterior a la fecha de inicio.";
		}
		if ((field.getValue()!=null) && (((DateField)field).getValue().before(new Date()))){
			return "La fecha de finalizacion debe ser posterior a la fecha actual.";
		}
		return null;
	}

}
