package net.sf.grudi.persistence.dao;

import org.hibernate.classic.Session;

public abstract class AbstractTransaction {

	public abstract void execute(Session session);
}
