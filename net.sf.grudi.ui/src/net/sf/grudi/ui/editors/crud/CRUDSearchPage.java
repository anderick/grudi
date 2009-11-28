package net.sf.grudi.ui.editors.crud;

import java.util.List;

import net.sf.grudi.model.search.SearchVO;
import net.sf.grudi.model.vo.VO;
import net.sf.grudi.ui.components.table.GenericTableColumn;
import net.sf.grudi.ui.components.table.GenericTableLabelProvider;
import net.sf.grudi.ui.editors.configuration.CRUDConfiguration;
import net.sf.grudi.ui.editors.configuration.ConfigurationInput;

import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;


public class CRUDSearchPage<T extends VO, S extends SearchVO> extends FormPage {

	private TableViewer tblViewer;
	private Table tblResults;

	public CRUDSearchPage(FormEditor editor) {
		super(editor, "SEARCH_PAGE", "Buscar");
		setContentDescription("Busca");
	}

	@Override
	protected void createFormContent(IManagedForm managedForm) {
		FormToolkit toolkit = managedForm.getToolkit();
		ScrolledForm form = managedForm.getForm();

		form.setText("Buscar");
		getEditorConfiguration().createSearchAreaButtons(getEditor(), form.getToolBarManager());

		Composite parent = form.getBody();
		parent.setLayout(GridLayoutFactory.swtDefaults().create());

		createSearchSection(toolkit, parent);
		createTableSection(toolkit, parent);

		getEditorConfiguration().bind();

		form.getToolBarManager().update(true);
	}

	/**
	 * Create the section that contains the table.
	 * 
	 * @param toolkit
	 * @param parent
	 */
	private void createTableSection(FormToolkit toolkit, Composite parent) {
		GridData gdTableSection = new GridData(SWT.FILL, SWT.FILL, true, true);

		Section tableSection = toolkit.createSection(parent, Section.TITLE_BAR);
		tableSection.setLayoutData(gdTableSection);
		tableSection.setText("Resultados");

		Composite tableComposite = toolkit.createComposite(tableSection);
		tableComposite.setLayout(new FillLayout(SWT.VERTICAL | SWT.HORIZONTAL));
		tableSection.setClient(tableComposite);

		createTable(tableComposite, toolkit);
	}

	/**
	 * Create the section that contains the search fields.
	 * 
	 * @param toolkit
	 * @param parent
	 */
	private void createSearchSection(FormToolkit toolkit, Composite parent) {
		GridData gdSection = new GridData(SWT.FILL, SWT.FILL, true, false);

		Section searchSection = toolkit.createSection(parent, Section.TITLE_BAR);
		searchSection.setLayoutData(gdSection);
		searchSection.setText("Filtros de Busca");

		Composite searchComposite = toolkit.createComposite(searchSection);
		searchComposite.setLayout(new FillLayout());
		searchSection.setClient(searchComposite);

		getEditorConfiguration().createSearchArea(searchComposite, toolkit);

		Composite searchButtonBar = toolkit.createComposite(parent);
		searchButtonBar.setLayout(new RowLayout());
		
	}

	/**
	 * Create a table to show results of the search.
	 * 
	 * @param parent
	 * @param toolkit
	 */
	private void createTable(Composite parent, FormToolkit toolkit) {
		tblResults = toolkit.createTable(parent, SWT.SINGLE | SWT.VIRTUAL | SWT.WRAP);
		tblResults.setLinesVisible(true);
		tblResults.setHeaderVisible(true);

		final List<GenericTableColumn> tblColList = getEditorConfiguration().createColumns();

		for (GenericTableColumn gtc : tblColList) {
			TableColumn tc = new TableColumn(tblResults, SWT.LEFT);
			String name = gtc.getName();
			tc.setText(name);
			final int width = gtc.getWidth();
			tc.setWidth(width == 0 ? 100 : width);
		}

		tblViewer = new TableViewer(tblResults);
		tblViewer.setContentProvider(new ArrayContentProvider());
		tblViewer.setLabelProvider(new GenericTableLabelProvider(tblColList));

		Composite tableButtonBar = toolkit.createComposite(parent);
		tableButtonBar.setLayout(new RowLayout());
	}

	public boolean canLeaveThePage() {
		return getEditorConfiguration().getCurrent() != null;
	}

	@SuppressWarnings("unchecked")
	private CRUDConfiguration<T, S> getEditorConfiguration() {
		return (CRUDConfiguration<T, S>) ((ConfigurationInput)getEditorInput()).getEditorConfiguration();
	}

	@Override
	@SuppressWarnings("unchecked")
	public CRUDEditor<T, S> getEditor() {
		return (CRUDEditor<T, S>) super.getEditor();
	}

	public void refreshTableInput(List<?> searchResult) {
		tblViewer.setInput(searchResult);
	}

	public TableItem[] getSelection() {
		return tblResults.getSelection();
	}

	public void clearTable() {
		tblResults.removeAll();
	}

}
