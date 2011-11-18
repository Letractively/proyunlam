package ar.com.AmberSoft.iEvenTask.client.utils;

import com.extjs.gxt.ui.client.GXT;
import com.extjs.gxt.ui.client.Style.SortDir;
import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.menu.CheckMenuItem;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;

public class GridView extends com.extjs.gxt.ui.client.widget.grid.GridView {

	@Override
	protected Menu createContextMenu(final int colIndex) {
		final Menu menu = new Menu();

		if (cm.isSortable(colIndex)) {
			MenuItem item = new MenuItem();
			item.setText(GXT.MESSAGES.gridView_sortAscText());
			item.setIcon(getImages().getSortAsc());
			item.addSelectionListener(new SelectionListener<MenuEvent>() {
				public void componentSelected(MenuEvent ce) {
					doSort(colIndex, SortDir.ASC);
				}

			});
			menu.add(item);

			item = new MenuItem();
			item.setText(GXT.MESSAGES.gridView_sortDescText());
			item.setIcon(getImages().getSortDesc());
			item.addSelectionListener(new SelectionListener<MenuEvent>() {
				public void componentSelected(MenuEvent ce) {
					doSort(colIndex, SortDir.DESC);
				}
			});
			menu.add(item);
		}

		/*MenuItem columns = new MenuItem();
		columns.setText(GXT.MESSAGES.gridView_columnsext());
		columns.setIcon(getImages().getColumns());
		columns.setData("gxt-columns", "true");

		final Menu columnMenu = new Menu();

		int cols = cm.getColumnCount();
		for (int i = 0; i < cols; i++) {
			if (shouldNotCount(i, false)) {
				continue;
			}
			final int fcol = i;
			final CheckMenuItem check = new CheckMenuItem();
			check.setHideOnClick(false);
			check.setText(cm.getColumnHeader(i));
			check.setChecked(!cm.isHidden(i));
			check.addSelectionListener(new SelectionListener<MenuEvent>() {
				public void componentSelected(MenuEvent ce) {
					cm.setHidden(fcol, !cm.isHidden(fcol));
					restrictMenu(columnMenu);
				}
			});
			columnMenu.add(check);
		}

		restrictMenu(columnMenu);

		columns.setSubMenu(columnMenu);
		menu.add(columns);*/
		return menu;
	}
	
	  private boolean shouldNotCount(int columnIndex, boolean includeHidden) {
		    return cm.getColumnHeader(columnIndex) == null || cm.getColumnHeader(columnIndex).equals("")
		        || (includeHidden && cm.isHidden(columnIndex)) || cm.isFixed(columnIndex);
		  }
	  
	  private void restrictMenu(Menu columns) {
		    int count = 0;
		    for (int i = 0, len = cm.getColumnCount(); i < len; i++) {
		      if (!shouldNotCount(i, true)) {
		        count++;
		      }
		    }

		    if (count == 1) {
		      for (Component item : columns.getItems()) {
		        CheckMenuItem ci = (CheckMenuItem) item;
		        if (ci.isChecked()) {
		          ci.disable();
		        }
		      }
		    } else {
		      for (Component item : columns.getItems()) {
		        item.enable();
		      }
		    }
		  }
}
