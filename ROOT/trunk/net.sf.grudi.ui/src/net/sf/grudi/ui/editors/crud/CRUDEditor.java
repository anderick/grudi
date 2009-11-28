package net.sf.grudi.ui.editors.crud;

import java.util.List;

import net.sf.grudi.model.search.SearchVO;
import net.sf.grudi.model.vo.VO;
import net.sf.grudi.ui.UIPlugin;
import net.sf.grudi.ui.editors.AbstractEditor;
import net.sf.grudi.ui.editors.configuration.CRUDConfiguration;
import net.sf.grudi.ui.editors.configuration.ConfigurationInput;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;


public final class CRUDEditor<T extends VO, S extends SearchVO> extends AbstractEditor {

	public static final String EDITOR_ID = "net.sf.grudi.ui.crudEditor";
	private CRUDEditPage editPage;
	private CRUDSearchPage<T, S> searchPage;

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		super.init(site, input);
		setPartName(getEditorConfiguration().getName());
	}
	
	@Override
	protected void addPages() {
		try {
			searchPage = new CRUDSearchPage<T,S>(this);
			addPage(searchPage);
			editPage = new CRUDEditPage(this);
			addPage(editPage);
		} catch (PartInitException e) {
			UIPlugin.getDefault().getLog().log(
					new Status(IStatus.ERROR, UIPlugin.PLUGIN_ID, "Error to add pages to CRUD Editor", e));
		}
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		T current = getEditorConfiguration().getCurrent();
		getEditorConfiguration().getDAO().saveOrUpdate(current);
		setDirty(false);
		showMessage(editPage, "Salvo com sucesso!", IMessageProvider.INFORMATION);
	}

	@SuppressWarnings("unchecked")
	public CRUDConfiguration<T, S> getEditorConfiguration() {
		return (CRUDConfiguration<T, S>) ((ConfigurationInput)getEditorInput()).getEditorConfiguration();
	}
	
	public void refreshTable(List<T> searchResult) {
		searchPage.refreshTableInput(searchResult);
	}
	
	public TableItem[] getTableSelection() {
		return searchPage.getSelection();
	}
	public void clearTable() {
		searchPage.clearTable();
	}

	public boolean canSave() {
		return getEditorConfiguration().canSave();
	}	
}
