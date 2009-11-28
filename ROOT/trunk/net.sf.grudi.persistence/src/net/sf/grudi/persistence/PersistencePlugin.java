package net.sf.grudi.persistence;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.hibernate.cfg.AnnotationConfiguration;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class PersistencePlugin extends AbstractUIPlugin implements IStartup {

	// The plug-in ID
	public static final String PLUGIN_ID = "net.sf.grudi.persistence";

	// The shared instance
	private static PersistencePlugin plugin;
	
	/**
	 * The constructor
	 */
	public PersistencePlugin() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static PersistencePlugin getDefault() {
		return plugin;
	}

	@Override
	public void earlyStartup() {
		final Display display = new Display();
		final Shell shell = new Shell(display);
		final IRunnableWithProgress runnableCreateFile = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InterruptedException, InvocationTargetException {
				try {					
					initialiazeAndConfigDB(monitor);
				} catch (Exception e) {
					Status status = new Status(IStatus.ERROR, PersistencePlugin.PLUGIN_ID, 
							"Erro ao iniciar conexão e configuração com a Base de Dados", e);
					PersistencePlugin.getDefault().getLog().log(status);
					throw new  InterruptedException("Erro ao iniciar conexão e configuração com a Base de Dados");	
				}finally{
					monitor.done();
				}
			}
		};
		
		final ProgressMonitorDialog doCreate = new ProgressMonitorDialog(shell);

		try {
			/* starts thread create file */
			doCreate.run(true, false, runnableCreateFile);		
		}catch (InvocationTargetException e) {
			Status status = new Status(IStatus.ERROR, PersistencePlugin.PLUGIN_ID, 
					"Erro ao iniciar conexão e configuração com a Base de Dados", e);
			PersistencePlugin.getDefault().getLog().log(status);
			MessageDialog.openError(shell, "Erro ao iniciar conexão e configuração com a Base de Dados", e.getLocalizedMessage());
		} catch (InterruptedException e) {
			Status status = new Status(IStatus.ERROR, PersistencePlugin.PLUGIN_ID, 
					"Erro ao iniciar conexão e configuração com a Base de Dados", e);
			PersistencePlugin.getDefault().getLog().log(status);
			MessageDialog.openError(shell, "Erro ao iniciar conexão e configuração com a Base de Dados", e.getLocalizedMessage());
		} 
	}
	
	
	private void initialiazeAndConfigDB(IProgressMonitor monitor){
		getLog().log(new Status(IStatus.INFO, PLUGIN_ID, "Starting Hibernate configuration..."));
		IExtension[] extensions = Platform.getExtensionRegistry().getExtensionPoint("net.sf.grudi.persistence.configuration").getExtensions();		
		for (IExtension extension : extensions) {
			IConfigurationElement[] configurationElements = extension.getConfigurationElements();
			int length = configurationElements.length;
			monitor.beginTask("Iniciando Configuração com a Base de Dados...",(length/2) + 1);		
			// hibernate configuration
			AnnotationConfiguration ac = new AnnotationConfiguration();		
			for (IConfigurationElement config : configurationElements) {
				// configuration to properties to Hibernate
				if ("property".equals(config.getName())) {
					String propertyName = config.getAttribute("name");
					String value = config.getAttribute("value");
					ac.setProperty(propertyName, value);
				} else
				// configuration to packages that contains annotated classes
				if ("classes".equals(config.getName())) {
					for (IConfigurationElement clazz : config.getChildren("class")) {
						String className = clazz.getAttribute("class");
						try {
							ac.addAnnotatedClass(Platform.getBundle(extension.getContributor().getName()).loadClass(className));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				if(length % 2 == 0){
					monitor.worked(1);		
				}				
				length--;		
			}	
			// store SessionFactory Hibernate to this configuration to a worspace session to be used by DAOs
			String Id = extension.getUniqueIdentifier();
			getLog().log(new Status(IStatus.INFO, PLUGIN_ID, "Configuration to: " + Id));			
			QualifiedName qualifiedName = new QualifiedName(PersistencePlugin.PLUGIN_ID, Id);
			monitor.worked(1);
			try {
				ResourcesPlugin.getWorkspace().getRoot().setSessionProperty(qualifiedName, ac.buildSessionFactory());
			} catch (CoreException e) {
				getLog().log(new Status(IStatus.ERROR, PLUGIN_ID, "Error during load configurations for Hibernate, please check ID", e));
			}
			monitor.worked(1);			
		}
	}
}
