package com.rahul.zeppelinonlinebank.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rahul.zeppelinonlinebank.pojo.ExternalRecipient;

@Repository
public class ExternalRecipientDaoImpl implements ExternalRecipientDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<ExternalRecipient> findall() {
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<ExternalRecipient> theQuery = currentSession.createQuery("from ExternalRecipient", ExternalRecipient.class);
		
		List<ExternalRecipient> results = theQuery.list();
		return results;
	}

	@Override
	public ExternalRecipient findByName(String theName) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<ExternalRecipient> theQuery = currentSession.createQuery("from ExternalRecipient where name=:theName", ExternalRecipient.class);
		theQuery.setParameter("theName", theName);
		
		ExternalRecipient recipient = null;
		
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
		
		Query theQuery = currentSession.createQuery("delete from ExternalRecipient where name=:theName");
		theQuery.setParameter("theName", theName);
		int rowsDeleted = theQuery.executeUpdate();
		System.out.println("Number of rows Deleted: "+rowsDeleted);
		
		

	}

	@Override
	public void save(ExternalRecipient recipient) {
		Session currentSession = sessionFactory.getCurrentSession();
		
		currentSession.saveOrUpdate(recipient);

	}

}
