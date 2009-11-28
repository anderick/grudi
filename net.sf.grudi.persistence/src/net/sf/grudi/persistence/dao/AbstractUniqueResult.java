package net.sf.grudi.persistence.dao;

import org.hibernate.classic.Session;

public abstract class AbstractUniqueResult<T> {
	
	public abstract T executeUniqueResult(Session session);

}
