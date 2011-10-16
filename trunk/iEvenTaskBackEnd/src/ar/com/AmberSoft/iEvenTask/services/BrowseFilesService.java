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

	public static final String COMPUTER = "COMPUTER";
	
	@SuppressWarnings({"unchecked", "rawtypes"})
	@Override
	public Map onExecute(Map params) {

		Collection<File> files = new ArrayList<File>();
		
		String folder = (String) params.get(ParamsConst.FOLDER);
		
		if (folder==null){
			files.add(new File(COMPUTER));
		} else {
			if (COMPUTER.equals(folder)){
				files = Arrays.asList(File.listRoots());
			} else {
				File directory = new File(folder);
				files = Arrays.asList(directory.listFiles());
			}
		}
	
		Collection cFiles = transformFiles(files);

		Map map = new HashMap();
		map.put(ParamsConst.DATA, cFiles);
	
		map.put(ParamsConst.PAGING_LOAD_RESULT, Boolean.TRUE); 

		return map;
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	@Override
	public Map onEmulate(Map params) {
		Collection<File> files = new ArrayList<File>();
		
		String folder = (String) params.get(ParamsConst.FOLDER);
		
		if (folder==null){
			files.add(new File(COMPUTER));
		} else {
			files.add(new File("C:\\" ));
			files.add(new File("D:\\"));
			files.add(new File("E:\\"));	
		}
	
		Collection cFiles = transformFiles(files);
		Iterator it = cFiles.iterator();
		int index = 1;
		while (it.hasNext()) {
			Map dir = (Map) it.next();
			if (index < 3){
				dir.put(ParamsConst.IS_DIRECTORY, Boolean.TRUE);
			} else {
				dir.put(ParamsConst.IS_DIRECTORY, Boolean.FALSE);
			}
			index++;
		}
		
		Map map = new HashMap();
		map.put(ParamsConst.DATA, cFiles);
	
		map.put(ParamsConst.PAGING_LOAD_RESULT, Boolean.TRUE); 

		return map;
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	public Collection transformFiles(Collection<File> roots) {
		Collection files = new ArrayList();
		Iterator it = roots.iterator();
		while (it.hasNext()) {
			File file = (File) it.next();
			Map actual = new HashMap();
			actual.put(ParamsConst.PATH, file.getPath());
			if (!COMPUTER.equals(file.getPath())){
				actual.put(ParamsConst.IS_DIRECTORY, file.isDirectory());
			} else {
				actual.put(ParamsConst.IS_DIRECTORY, Boolean.TRUE);
			}
			files.add(actual);
		}
		return files;
	}
	
	@SuppressWarnings({"unused", "rawtypes"})
	private Boolean isTransactionControl(Map params) {
		return Boolean.FALSE;
	}
	

}
