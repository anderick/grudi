package net.sf.grudi.persistence.dao;

import java.util.List;

import org.hibernate.classic.Session;

public abstract class AbstractQuery<T> {

	public abstract List<T> execute(Session session);
}
