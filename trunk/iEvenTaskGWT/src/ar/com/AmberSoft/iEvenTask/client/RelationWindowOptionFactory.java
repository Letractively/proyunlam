package ar.com.AmberSoft.iEvenTask.client;

import java.util.HashMap;
import java.util.Map;

public class RelationWindowOptionFactory {
	private static RelationWindowOptionFactory instance;
	
	private Map<String, RelationWindowOption> relationWindowOptions;
	
	private RelationWindowOptionFactory(){
		relationWindowOptions = new HashMap<String, RelationWindowOption>();
		relationWindowOptions.put(RelationWindowOption.CREATE_TASK, new RelationWindowOptionCreateTask());
		relationWindowOptions.put(RelationWindowOption.MODIFY_STATE, new ReelationWindowOptionModifyState());
	}
	
	public static RelationWindowOptionFactory getInstance(){
		if (instance==null){
			instance = new RelationWindowOptionFactory();
		}
		return instance;
	}
	
	public RelationWindowOption getRelationWindowOption(String option, RelationWindow relationWindow){
		RelationWindowOption windowOption = relationWindowOptions.get(option);
		if (windowOption!=null){
			windowOption.setRelationWindow(relationWindow);
		}
		return windowOption;
	}
}
