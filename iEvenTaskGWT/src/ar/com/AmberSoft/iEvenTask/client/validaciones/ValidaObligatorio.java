package ar.com.AmberSoft.iEvenTask.client.validaciones;

import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.Validator;

public class ValidaObligatorio implements Validator {

	@Override
	public String validate(Field<?> field, String value) {
		if (!((value==null)||("".equalsIgnoreCase(value.trim())))){
			return "El campo es obligatio, introduzca un valor.";
		}
		return null;
	}

}
