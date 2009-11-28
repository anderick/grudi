package net.sf.grudi.ui.editors.plan;

import net.sf.grudi.ui.editors.configuration.ConfigurationInput;
import net.sf.grudi.ui.editors.configuration.PlanConfiguration;

import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;


public class PlanPage extends FormPage {

	public PlanPage(FormEditor editor) {
		super(editor, "PLAN_PAGE", "Detalhe");
	}

	@Override
	protected void createFormContent(IManagedForm managedForm) {
		FormToolkit toolkit = managedForm.getToolkit();
		ScrolledForm form = managedForm.getForm();

		form.setText(getEditorConfiguration().getName());
		createButtons(form);
		
		Composite parent = form.getBody();
		parent.setLayout(GridLayoutFactory.swtDefaults().create());
		createEditSection(toolkit, parent);

		form.getToolBarManager().update(false);
	}

	private void createButtons(ScrolledForm form) {
		getEditorConfiguration().createButtons(form);
	}

	/**
	 * Create section to edit area.
	 * 
	 * @param toolkit
	 * @param parent
	 */
	private void createEditSection(FormToolkit toolkit, Composite parent) {
		Composite editButtonBar = toolkit.createComposite(parent);
		editButtonBar.setLayout(new RowLayout());
		
		Composite planComposite = toolkit.createComposite(parent);
		planComposite.setLayout(new RowLayout());
		
		getEditorConfiguration().createPlanArea(planComposite, toolkit);
	}
	
	private PlanConfiguration<?,?> getEditorConfiguration() {
		return (PlanConfiguration<?,?>) ((ConfigurationInput)getEditorInput()).getEditorConfiguration();
	}
	
	@Override
	public PlanEditor getEditor() {
		return (PlanEditor) super.getEditor();
	}
	
}
