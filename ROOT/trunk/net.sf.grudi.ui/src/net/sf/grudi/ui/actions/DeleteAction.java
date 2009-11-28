package net.sf.grudi.ui.actions;

import net.sf.grudi.model.vo.VO;
import net.sf.grudi.ui.editors.crud.CRUDEditor;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.TableItem;


public class DeleteAction extends Action {

	private CRUDEditor<?, ?> editor;

	public DeleteAction(CRUDEditor<?,?> editor) {
		super("Apagar");
		this.editor = editor;
	}

	@Override
	public void run() {
		int reg = editor.getTableSelection().length;
		if (reg == 0) {
			return;
		}
		boolean canContinue = MessageDialog.openConfirm(null, "Apagar", String.format("Deseja realmente apagar %s registros?", reg));
		if (canContinue) {
			for (TableItem item : editor.getTableSelection()) {
				reg++;
				VO vo = (VO) item.getData();
				editor.getEditorConfiguration().getDAO().delete(vo);
			}
			MessageDialog.openQuestion(null, "Apagar", String.format("%s registros apagados.", reg));
			editor.clearTable();
		}
	}
}
