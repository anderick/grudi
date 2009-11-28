package net.sf.grudi.ui.actions;

import net.sf.grudi.model.search.SearchVO;
import net.sf.grudi.model.vo.VO;
import net.sf.grudi.ui.editors.configuration.CRUDConfiguration;
import net.sf.grudi.ui.editors.crud.CRUDEditor;

import org.eclipse.jface.action.Action;


public class SearchAction<T extends VO, S extends SearchVO> extends Action {

	private CRUDConfiguration<T,S> configuration;
	
	private CRUDEditor<T,S> editor;
	
	public SearchAction(CRUDEditor<T, S> editor, 
			CRUDConfiguration<T, S> configuration) {
		super("Buscar");
		this.configuration = configuration;
		this.editor = editor;
	}
	
	@Override
	public void run() {
		editor.refreshTable(configuration.getSearchResult());
	}
}
