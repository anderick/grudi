package net.sf.grudi.ui.editors.configuration;


public abstract class EditorConfiguration {

	public abstract void init();
	
	private String name;
	
	private String tooltip;
	
	/**
	 * Name as declared in the extension point.
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Set the name declared in the extension point.
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Tooltip as declared in the extension point.
	 * 
	 * @return tooltip
	 */
	public String getTooltip() {
		return tooltip;
	}
	
	/**
	 * Set the tooltip declared in the extension point.
	 * 
	 * @param tooltip
	 */
	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EditorConfiguration other = (EditorConfiguration) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
