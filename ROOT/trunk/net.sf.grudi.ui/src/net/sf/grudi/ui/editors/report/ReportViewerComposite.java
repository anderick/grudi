package net.sf.grudi.ui.editors.report;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ToolBar;

import com.jasperassistant.designer.viewer.DefaultHyperlinkHandler;
import com.jasperassistant.designer.viewer.IReportViewer;
import com.jasperassistant.designer.viewer.ReportViewer;
import com.jasperassistant.designer.viewer.StatusBar;
import com.jasperassistant.designer.viewer.actions.ExportAsCsvAction;
import com.jasperassistant.designer.viewer.actions.ExportAsPdfAction;
import com.jasperassistant.designer.viewer.actions.ExportAsSingleXlsAction;
import com.jasperassistant.designer.viewer.actions.ExportMenuAction;
import com.jasperassistant.designer.viewer.actions.FirstPageAction;
import com.jasperassistant.designer.viewer.actions.LastPageAction;
import com.jasperassistant.designer.viewer.actions.NextPageAction;
import com.jasperassistant.designer.viewer.actions.PageNumberContributionItem;
import com.jasperassistant.designer.viewer.actions.PreviousPageAction;
import com.jasperassistant.designer.viewer.actions.PrintAction;
import com.jasperassistant.designer.viewer.actions.ReloadAction;
import com.jasperassistant.designer.viewer.actions.ZoomActualSizeAction;
import com.jasperassistant.designer.viewer.actions.ZoomComboContributionItem;
import com.jasperassistant.designer.viewer.actions.ZoomFitPageAction;
import com.jasperassistant.designer.viewer.actions.ZoomFitPageWidthAction;
import com.jasperassistant.designer.viewer.actions.ZoomInAction;
import com.jasperassistant.designer.viewer.actions.ZoomOutAction;

public class ReportViewerComposite extends Composite {

	private ReportViewer reportViewer;

	/**
	 * Constructs the viewer
	 * 
	 * @param parent
	 *            the parent
	 * @param style
	 *            the style. For possible styles see {@link Composite}class.
	 */
	public ReportViewerComposite(Composite parent, int style) {
		super(parent, style);
		createContents();
	}

	private void createContents() {
		GridLayout layout = new GridLayout(1, false);
		layout.marginWidth = layout.marginWidth = 2;
		layout.verticalSpacing = 2;
		setLayout(layout);

		reportViewer = new ReportViewer(SWT.BORDER);

		ToolBarManager tbManager = new ToolBarManager(SWT.FLAT);
		ExportMenuAction exportMenu = new ExportMenuAction(reportViewer);
		IAction pdfAction = null;
		exportMenu.getMenuManager().add(
				pdfAction = new ExportAsPdfAction(reportViewer));
//        exportMenu.getMenuManager().add(
//                new ExportAsRtfAction(reportViewer));
//		exportMenu.getMenuManager().add(
//				new ExportAsJasperReportsAction(reportViewer));
//		exportMenu.getMenuManager().add(new ExportAsHtmlAction(reportViewer));
		exportMenu.getMenuManager().add(
				new ExportAsSingleXlsAction(reportViewer));
//		exportMenu.getMenuManager().add(
//				new ExportAsMultiXlsAction(reportViewer));
		exportMenu.getMenuManager().add(new ExportAsCsvAction(reportViewer));
//		exportMenu.getMenuManager().add(new ExportAsXmlAction(reportViewer));
//		exportMenu.getMenuManager().add(
//				new ExportAsXmlWithImagesAction(reportViewer));
		exportMenu.setDefaultAction(pdfAction);

		tbManager.add(exportMenu);
		tbManager.add(new PrintAction(reportViewer));
		tbManager.add(new ReloadAction(reportViewer));
		tbManager.add(new Separator());
		tbManager.add(new FirstPageAction(reportViewer));
		tbManager.add(new PreviousPageAction(reportViewer));
		if (SWT.getPlatform().equals("win32")) //$NON-NLS-1$
			tbManager.add(new PageNumberContributionItem(reportViewer));
		tbManager.add(new NextPageAction(reportViewer));
		tbManager.add(new LastPageAction(reportViewer));
		tbManager.add(new Separator());
		tbManager.add(new ZoomActualSizeAction(reportViewer));
		tbManager.add(new ZoomFitPageAction(reportViewer));
		tbManager.add(new ZoomFitPageWidthAction(reportViewer));
		tbManager.add(new Separator());
		tbManager.add(new ZoomOutAction(reportViewer));
		tbManager.add(new ZoomComboContributionItem(reportViewer));
		tbManager.add(new ZoomInAction(reportViewer));

		ToolBar toolbar = tbManager.createControl(this);
		toolbar.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Control reportViewerControl = reportViewer.createControl(this);
		reportViewerControl.setLayoutData(new GridData(GridData.FILL_BOTH));

		StatusBar statusBar = new StatusBar();
		statusBar.setReportViewer(reportViewer);
		Control statusBarControl = statusBar.createControl(this);
		statusBarControl.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		reportViewer.addHyperlinkListener(new DefaultHyperlinkHandler());
	}

	/**
	 * Returns the report viewer used for viewing reports.
	 * 
	 * @return the report viewer
	 */
	public IReportViewer getReportViewer() {
		return reportViewer;
	}

}