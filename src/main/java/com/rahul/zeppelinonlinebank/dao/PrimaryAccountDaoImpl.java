package com.rahul.zeppelinonlinebank.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rahul.zeppelinonlinebank.pojo.PrimaryAccount;

@Repository
public class PrimaryAccountDaoImpl implements PrimaryAccountDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public PrimaryAccount findByAccountNumber(int accountNumber) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<PrimaryAccount> theQuery = currentSession.createQuery("from PrimaryAccount where accountNumber=:aNumber", PrimaryAccount.class);
		theQuery.setParameter("aNumber", accountNumber);
		PrimaryAccount primaryAccount = null;
		
		try {
			primaryAccount = theQuery.getSingleResult();
		}
		catch (Exception e) {
			primaryAccount = null;
		}
		return primaryAccount;
	}

	@Override
	public void save(PrimaryAccount primaryAccount) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		
		currentSession.saveOrUpdate(primaryAccount);

	}

}
