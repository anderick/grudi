package net.sf.grudi.ui.actions;

import net.sf.grudi.model.vo.VO;
import net.sf.grudi.ui.editors.configuration.CRUDConfiguration;
import net.sf.grudi.ui.editors.crud.CRUDEditPage;
import net.sf.grudi.ui.editors.crud.CRUDEditor;

public class NewAction extends AbstractEditorAction<CRUDEditor<?,?>> {

	public NewAction(CRUDEditor<?,?> editor) {
		this("Novo", editor);
	}
	
	public NewAction(String text, CRUDEditor<?,?> editor) {
		super(text, editor);
	}
	
	@Override
	public void run() {
		configureNewEntity();
		getEditor().setActivePage(CRUDEditPage.PAGE_ID);
	}

	@SuppressWarnings("unchecked")
	protected void configureNewEntity() {
		CRUDConfiguration editorConfiguration = getEditor().getEditorConfiguration();
		VO entity = editorConfiguration.newVO();
		editorConfiguration.setCurrent(entity);
	}
}
