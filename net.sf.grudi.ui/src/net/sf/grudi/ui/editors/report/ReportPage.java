package net.sf.grudi.ui.editors.report;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import net.sf.grudi.model.search.SearchVO;
import net.sf.grudi.model.vo.VO;
import net.sf.grudi.ui.editors.configuration.ConfigurationInput;
import net.sf.grudi.ui.editors.configuration.ReportConfiguration;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.dialogs.MessageDialog;
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


public class ReportPage<S extends SearchVO> extends FormPage {

	private ReportViewerComposite reportComposite;

	public ReportPage(FormEditor editor) {
		super(editor, "REPORT_PAGE", "Relatório");
		setContentDescription("Relatório");
	}

	@Override
	protected void createFormContent(IManagedForm managedForm) {
		FormToolkit toolkit = managedForm.getToolkit();
		ScrolledForm form = managedForm.getForm();

		form.setText(getEditorConfiguration().getName());
		getEditorConfiguration().createFilterAreaButtons(getEditor(), form.getToolBarManager());

		Composite parent = form.getBody();
		parent.setLayout(GridLayoutFactory.swtDefaults().create());

		createFilterSection(toolkit, parent);
		createReportSection(toolkit, parent);

		getEditorConfiguration().bind();

		form.getToolBarManager().update(true);
	}

	/**
	 * Create the section that contains the report viewer.
	 * 
	 * @param toolkit
	 * @param parent
	 */
	private void createReportSection(FormToolkit toolkit, Composite parent) {
		GridData gdReportSection = new GridData(SWT.FILL, SWT.FILL, true, true);

		Section reportSection = toolkit.createSection(parent, Section.TITLE_BAR);
		reportSection.setLayoutData(gdReportSection);
		reportSection.setText("Visualização de relatório");

		reportComposite = new ReportViewerComposite(reportSection, SWT.NONE);
		reportSection.setClient(reportComposite);
	}

	/**
	 * Create the section that contains the search fields.
	 * 
	 * @param toolkit
	 * @param parent
	 */
	private void createFilterSection(FormToolkit toolkit, Composite parent) {
		GridData gdSection = new GridData(SWT.FILL, SWT.FILL, true, false);

		Section filterSection = toolkit.createSection(parent, Section.TITLE_BAR);
		filterSection.setLayoutData(gdSection);
		filterSection.setText("Filtros de Busca");

		Composite filterComposite = toolkit.createComposite(filterSection);
		filterComposite.setLayout(new FillLayout());
		filterSection.setClient(filterComposite);

		getEditorConfiguration().createFilterArea(filterComposite, toolkit);

		Composite filterButtonBar = toolkit.createComposite(parent);
		filterButtonBar.setLayout(new RowLayout());

	}

	@SuppressWarnings("unchecked")
	private ReportConfiguration<S> getEditorConfiguration() {
		return (ReportConfiguration<S>) ((ConfigurationInput)getEditorInput()).getEditorConfiguration();
	}

	@Override
	@SuppressWarnings("unchecked")
	public ReportEditor<S> getEditor() {
		return (ReportEditor<S>) super.getEditor();
	}

	public void refreshReport(List<VO> list, Map<String, Object> parametros) {
		Assert.isNotNull(parametros);

		if (list.isEmpty()) {
			MessageDialog.openInformation(null, "Não encontrado", "Não foram encontrados dados para exibir no relatório");
			reportComposite.getReportViewer().unsetDocument(null);
			return;
		}
		
		JRDataSource jrds = new JRBeanCollectionDataSource(list);

		try	{  
			JasperPrint print = JasperFillManager.fillReport(getEditorConfiguration().getReportInputStream(), parametros, jrds);
			reportComposite.getReportViewer().setDocument(print);
		} catch (JRException e) {         
			e.printStackTrace();  
		} catch (IOException e) {         
			e.printStackTrace();  
		}  

	}

}
