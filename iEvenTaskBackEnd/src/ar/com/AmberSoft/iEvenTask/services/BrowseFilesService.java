package ar.com.AmberSoft.iEvenTask.services;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import ar.com.AmberSoft.util.ParamsConst;

public class BrowseFilesService extends Service {

	@Override
	public Map onExecute(Map params) {
		
		Collection<File> roots = Arrays.asList(File.listRoots());
		
		Map map = new HashMap();
		map.put(ParamsConst.DATA, null);
	
		/*map.put(ParamsConst.TOTAL_COUNT, queryCount.uniqueResult());
		map.put(ParamsConst.OFFSET, (Integer) params.get(OFFSET));*/
		map.put(ParamsConst.PAGING_LOAD_RESULT, Boolean.TRUE);

		return map;
	}

	@Override
	public Map onEmulate(Map params) {
		Collection<File> roots = new ArrayList<File>();
		
		roots.add(new File("C:\\"));
		roots.add(new File("D:\\"));
		roots.add(new File("E:\\"));
	
		Collection files = transformFiles(roots);
		
		Map map = new HashMap();
		map.put(ParamsConst.DATA, files);
	
		/*map.put(ParamsConst.TOTAL_COUNT, queryCount.uniqueResult());
		map.put(ParamsConst.OFFSET, (Integer) params.get(OFFSET));*/
		map.put(ParamsConst.PAGING_LOAD_RESULT, Boolean.TRUE);

		return map;
	}

	public Collection transformFiles(Collection<File> roots) {
		Collection files = new ArrayList();
		Iterator it = roots.iterator();
		while (it.hasNext()) {
			File file = (File) it.next();
			Map actual = new HashMap();
			actual.put(ParamsConst.PATH, file.getPath());
			//actual.put(ParamsConst.IS_DIRECTORY, file.isDirectory());
			files.add(actual);
		}
		return files;
	}
	
	private Boolean isTransactionControl(Map params) {
		return Boolean.FALSE;
	}
	

}
