package net.sf.grudi.ui.application;

import net.sf.grudi.ui.UIPlugin;
import net.sf.grudi.ui.actions.OpenEditorAction;
import net.sf.grudi.ui.editors.configuration.EditorConfiguration;
import net.sf.grudi.ui.editors.crud.CRUDEditor;
import net.sf.grudi.ui.editors.plan.PlanEditor;
import net.sf.grudi.ui.editors.report.ReportEditor;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.ToolBarContributionItem;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;


/**
 * An action bar advisor is responsible for creating, adding, and disposing of the
 * actions added to a workbench window. Each window will be populated with
 * new actions.
 */
public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

	public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
		super(configurer);
	}

	@Override
	protected void fillMenuBar(IMenuManager menuBar) {
		UIPlugin.getDefault().getLog().log(new Status(IStatus.INFO, UIPlugin.PLUGIN_ID, "Loading menus and menu items..."));
		try {
			for (IConfigurationElement menu : 
				Platform.getExtensionRegistry().getConfigurationElementsFor("net.sf.grudi.ui.menu")) {
				MenuManager menuManager = new MenuManager(menu.getAttribute("text"));

				// FIXME - Where is the image? Where is the report file? Where is the security?
				for (IConfigurationElement crud : menu.getChildren("crud")) {
					String name = crud.getAttribute("name");
					String tooltip = crud.getAttribute("tooltip");
					EditorConfiguration editorConfig = (EditorConfiguration) crud.createExecutableExtension("configuration");
					editorConfig.setName(name);
					editorConfig.setTooltip(tooltip);
					OpenEditorAction action = new OpenEditorAction(name, CRUDEditor.EDITOR_ID, editorConfig);
					action.setToolTipText(tooltip);
					menuManager.add(action);
				}

				for (IConfigurationElement report : menu.getChildren("report")) {
					String name = report.getAttribute("name");
					String tooltip = report.getAttribute("tooltip");
					EditorConfiguration editorConfig = (EditorConfiguration) report.createExecutableExtension("configuration");
					editorConfig.setName(name);
					editorConfig.setTooltip(tooltip);
					OpenEditorAction action = new OpenEditorAction(name, ReportEditor.EDITOR_ID, editorConfig);
					action.setToolTipText(tooltip);
					menuManager.add(action);
				}

				for (IConfigurationElement plan : menu.getChildren("plan")) {
					String name = plan.getAttribute("name");
					String tooltip = plan.getAttribute("tooltip");
					EditorConfiguration editorConfig = (EditorConfiguration) plan.createExecutableExtension("configuration");
					editorConfig.setName(name);
					editorConfig.setTooltip(tooltip);
					OpenEditorAction action = new OpenEditorAction(name, PlanEditor.EDITOR_ID, editorConfig);
					action.setToolTipText(tooltip);
					menuManager.add(action);
				}

				menuBar.add(menuManager);
			}
		} catch (CoreException e) {
			UIPlugin.getDefault().getLog().log(
					new Status(IStatus.ERROR, UIPlugin.PLUGIN_ID, "Error during load editor configuration or menu items.", e));
		}
	}

	@Override
	protected void fillCoolBar(ICoolBarManager coolBar) {
		IConfigurationElement[] configs = Platform.getExtensionRegistry().getConfigurationElementsFor("net.sf.grudi.ui.button");
		if (configs.length > 0) {
			IToolBarManager toolbar = new ToolBarManager(SWT.FLAT | SWT.RIGHT);
			coolBar.add(new ToolBarContributionItem(toolbar, "shortcuts"));

			UIPlugin.getDefault().getLog().log(new Status(IStatus.INFO, UIPlugin.PLUGIN_ID, "Loading button items to the toolbar..."));
			try {
				for (IConfigurationElement button : 
					configs) {

					String name = button.getAttribute("name");
					String tooltip = button.getAttribute("tooltip");
					EditorConfiguration editorConfig = (EditorConfiguration) button.createExecutableExtension("configuration");
					editorConfig.setName(name);
					editorConfig.setTooltip(tooltip);

					OpenEditorAction action = new OpenEditorAction(name, PlanEditor.EDITOR_ID, editorConfig);
					action.setToolTipText(tooltip);

					toolbar.add(action);
				}
			} catch (CoreException e) {
				UIPlugin.getDefault().getLog().log(
						new Status(IStatus.ERROR, UIPlugin.PLUGIN_ID, "Error during load editor configuration or menu items.", e));
			}
		}
	}
}
