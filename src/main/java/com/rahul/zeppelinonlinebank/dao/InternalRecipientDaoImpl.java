package com.rahul.zeppelinonlinebank.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rahul.zeppelinonlinebank.pojo.ExternalRecipient;
import com.rahul.zeppelinonlinebank.pojo.InternalRecipient;

@Repository
public class InternalRecipientDaoImpl implements InternalRecipientDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<InternalRecipient> findall() {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<InternalRecipient> theQuery = currentSession.createQuery("from InternalRecipient", InternalRecipient.class);
		
		List<InternalRecipient> results = theQuery.list();
		return results;
	}

	@Override
	public InternalRecipient findByName(String theName) {
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<InternalRecipient> theQuery = currentSession.createQuery("from InternalRecipient where name=:theName", InternalRecipient.class);
		theQuery.setParameter("theName", theName);
		
		InternalRecipient recipient = null;
		
		try {
			recipient = theQuery.getSingleResult();
		} catch (Exception e) {
			recipient = null;
		}
		
		return recipient;
	}

	

	@Override
	public void deleteByName(String theName) {
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query theQuery = currentSession.createQuery("delete from InternalRecipient where name=:theName");
		theQuery.setParameter("theName", theName);
		int rowsDeleted = theQuery.executeUpdate();
		System.out.println("Number of rows Deleted: "+rowsDeleted);

	}

	@Override
	public void save(InternalRecipient recipient) {
		Session currentSession = sessionFactory.getCurrentSession();
		
		currentSession.saveOrUpdate(recipient);

	}

}
