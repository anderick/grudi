package net.sf.grudi.ui.actions;

import net.sf.grudi.model.search.SearchVO;
import net.sf.grudi.model.vo.VO;
import net.sf.grudi.ui.editors.crud.CRUDEditor;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;


public class SaveAction extends AbstractEditorAction<CRUDEditor<? extends VO, ? extends SearchVO>> {

	private CRUDEditor<?,?> editor;
	
	public SaveAction(CRUDEditor<?,?> editor) {
		super("Salvar", editor);
		this.editor = editor;
	}
	
	@Override
	public void run() {
		if (editor.canSave()) {
			getEditor().doSave(new NullProgressMonitor());
		} else {
			MessageDialog.openError(null, "Salvar", "Não foi possível salvar, preencha corretamente todos os campos.");
		}
	}
	
}
