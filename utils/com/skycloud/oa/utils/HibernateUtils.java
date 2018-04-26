package com.skycloud.oa.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class HibernateUtils {

	//private static SessionFactory sessionFactory;
	private static ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();

	public static Session getCurrentSession() {
		return threadLocal.get();
	}
	/*public static Session openSession() {
		Session session = sessionFactory.openSession();
		threadLocal.set(session);
		return session;
	}*/
	
	public static Session openSession(SessionFactory sessionFactory){
		Session session = sessionFactory.openSession();
		threadLocal.set(session);
		return session;
	}

	public static void closeCurrentSession() {

		Session session = threadLocal.get();

		session.close();

		threadLocal.remove();
	}
}
