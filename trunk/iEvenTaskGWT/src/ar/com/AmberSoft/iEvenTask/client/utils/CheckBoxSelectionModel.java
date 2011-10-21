package ar.com.AmberSoft.iEvenTask.client.utils;

import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.store.StoreEvent;

public class CheckBoxSelectionModel extends
		com.extjs.gxt.ui.client.widget.grid.CheckBoxSelectionModel {

	@Override
	protected void handleMouseClick(GridEvent e) {
		super.handleMouseClick(e);
	}

	@Override
	protected void handleMouseDown(GridEvent e) {
		super.handleMouseDown(e);
	}

	@Override
	protected void onClear(StoreEvent se) {
		super.onClear(se);
	}

	@Override
	protected void onHeaderClick(GridEvent e) {
		super.onHeaderClick(e);
	}

	@Override
	protected void onRemove(ModelData model) {
		super.onRemove(model);
	}

	@Override
	protected void onSelectChange(ModelData model, boolean select) {
		super.onSelectChange(model, select);
	}

}
