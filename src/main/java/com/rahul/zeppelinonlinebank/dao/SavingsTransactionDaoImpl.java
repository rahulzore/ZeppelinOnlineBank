package com.rahul.zeppelinonlinebank.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rahul.zeppelinonlinebank.pojo.SavingsAccount;
import com.rahul.zeppelinonlinebank.pojo.SavingsTransaction;

@Repository
public class SavingsTransactionDaoImpl implements SavingsTransactionDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<SavingsTransaction> findAll() {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<SavingsTransaction> theQuery = currentSession.createQuery("from SavingsTransaction", SavingsTransaction.class);
		
		List<SavingsTransaction> results = theQuery.list();
		
		return results;
	}

	@Override
	public void save(SavingsTransaction savingsTransaction) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		currentSession.saveOrUpdate(savingsTransaction);
	}
	
	@Override
	public SavingsTransaction findTransaction(long ID) {
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<SavingsTransaction> theQuery = currentSession.createQuery("from SavingsTransaction s where s.id=:id", SavingsTransaction.class);
		theQuery.setParameter("ID", ID);
		SavingsTransaction savingsTransaction = theQuery.getSingleResult();
		return savingsTransaction;
	}

}
