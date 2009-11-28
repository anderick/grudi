package net.sf.grudi.ui.editors.crud;

import net.sf.grudi.ui.editors.configuration.CRUDConfiguration;
import net.sf.grudi.ui.editors.configuration.ConfigurationInput;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;


public class CRUDEditPage extends FormPage {

	public static final String PAGE_ID = "EDIT_PAGE";
	
	public CRUDEditPage(FormEditor editor) {
		super(editor, PAGE_ID, "Editar");
	}

	@Override
	protected void createFormContent(IManagedForm managedForm) {
		FormToolkit toolkit = managedForm.getToolkit();
		ScrolledForm form = managedForm.getForm();

		form.setText("Editar");
		getEditorConfiguration().createEditAreaButtons(getEditor(), form.getToolBarManager());
		
		Composite parent = form.getBody();
		parent.setLayout(GridLayoutFactory.swtDefaults().create());
		createEditSection(toolkit, parent);

		form.getToolBarManager().update(false);
	}

	/**
	 * Create section to edit area.
	 * 
	 * @param toolkit
	 * @param parent
	 */
	private void createEditSection(FormToolkit toolkit, Composite parent) {
		GridData gdSection = new GridData(SWT.FILL, SWT.FILL, true, true);

		Composite editButtonBar = toolkit.createComposite(parent);
		editButtonBar.setLayout(new RowLayout());
		
		Section editSection = toolkit.createSection(parent, Section.TITLE_BAR);
		editSection.setLayoutData(gdSection);
		editSection.setText("Dados");
		
		Composite editComposite = toolkit.createComposite(editSection);
		editComposite.setLayout(new FillLayout());
		editSection.setClient(editComposite);
		
		getEditorConfiguration().createEditArea(editComposite, toolkit);
		
	}
	
	/**
	 * Don't leave this if the editor is dirty. 
	 * Ask to user about save to leave the page.
	 */
	@Override
	public boolean canLeaveThePage() {
		if (getEditor().isDirty()) {
			MessageDialogWithToggle msg = MessageDialogWithToggle.openYesNoQuestion(
					getEditor().getSite().getShell(), "Alterações", "Existem alterações não salvas, deseja mesmo continuar?",
					"Salvar ao continuar", true, null, null);
			if (msg.getReturnCode() == 3) { // answered NO
				return false;
			}
			if (msg.getToggleState()) {
				getEditor().doSave(new NullProgressMonitor());
			}
		}
		return true;
	}

	private CRUDConfiguration<?, ?> getEditorConfiguration() {
		return (CRUDConfiguration<?, ?>) ((ConfigurationInput)getEditorInput()).getEditorConfiguration();
	}
	
	@Override
	public CRUDEditor<?, ?> getEditor() {
		return (CRUDEditor<?, ?>) super.getEditor();
	}
	
	@Override
	public void setActive(boolean active) {
		if (active) {
			Assert.isNotNull(getEditorConfiguration().getCurrent());
			getEditorConfiguration().refreshPage();
			getEditorConfiguration().getCurrent().addPropertyChangeListener(getEditor());
			getEditorConfiguration().bind();
		} else {
			getEditorConfiguration().getCurrent().removePropertyChangeListener(getEditor());
			getEditorConfiguration().setCurrent(null);
			getEditor().setDirty(false);
		}
		getEditor().showMessage(this, "", IMessageProvider.NONE);
		super.setActive(active);
	}

}
