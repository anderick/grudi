package net.sf.grudi.ui.actions;

import net.sf.grudi.model.vo.VO;
import net.sf.grudi.ui.editors.crud.CRUDEditPage;
import net.sf.grudi.ui.editors.crud.CRUDEditor;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.TableItem;


public class EditAction extends Action {

	private CRUDEditor<?,?> editor;
	
	public EditAction(CRUDEditor<?,?> editor) {
		super("Editar");
		this.editor = editor;
	}

	@Override
	public void run() {
		int reg = editor.getTableSelection().length;
		if (reg == 0) {
			return;
		}
		for (TableItem item : editor.getTableSelection()) {
			editor.getEditorConfiguration().setCurrent((VO)item.getData());
			editor.setActivePage(CRUDEditPage.PAGE_ID);
			break;
		}
		editor.clearTable();
	}
}
