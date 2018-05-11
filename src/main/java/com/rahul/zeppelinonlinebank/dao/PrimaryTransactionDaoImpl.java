package com.rahul.zeppelinonlinebank.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rahul.zeppelinonlinebank.pojo.PrimaryTransaction;

@Repository
public class PrimaryTransactionDaoImpl implements PrimaryTransactionDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<PrimaryTransaction> findAll() {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<PrimaryTransaction> theQuery = currentSession.createQuery("from PrimaryTransaction", PrimaryTransaction.class);
		List<PrimaryTransaction> results = theQuery.list();
		return results;
	}

	@Override
	public void save(PrimaryTransaction primaryTransaction) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		currentSession.saveOrUpdate(primaryTransaction);
		
	}

	@Override
	public PrimaryTransaction findTransaction(long ID) {
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<PrimaryTransaction> theQuery = currentSession.createQuery("from PrimaryTransaction p where p.id=:ID", PrimaryTransaction.class);
		theQuery.setParameter("ID", ID);
		PrimaryTransaction primaryTransaction = theQuery.getSingleResult();
		return primaryTransaction;
	}

}
