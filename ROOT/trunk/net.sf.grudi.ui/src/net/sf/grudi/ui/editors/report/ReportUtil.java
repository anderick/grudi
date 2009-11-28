package net.sf.grudi.ui.editors.report;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.eclipse.swt.SWT;

import com.jasperassistant.designer.viewer.ReportViewer;
import com.jasperassistant.designer.viewer.actions.PrintAction;

public class ReportUtil {

	public static <D> void print(InputStream report, List<D> list, Map<String, Object> parameters) {
		ReportViewer reportViewer = new ReportViewer(SWT.BORDER);
		JRDataSource jrds = new JRBeanCollectionDataSource(list);

		try	{  
			JasperPrint print = JasperFillManager.fillReport(report, parameters, jrds);
			reportViewer.setDocument(print);
		} catch (JRException e) {         
			e.printStackTrace();  
		} 
		new PrintAction(reportViewer).run();
	}
	
}
