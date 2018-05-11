package com.rahul.zeppelinonlinebank.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rahul.zeppelinonlinebank.pojo.SavingsAccount;

@Repository
public class SavingsAccountDaoImpl implements SavingsAccountDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public SavingsAccount findByAccountNumber(int accountNumber) {
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<SavingsAccount> theQuery = currentSession.createQuery("from SavingsAccount where accountNumber=:aNumber", SavingsAccount.class);
		theQuery.setParameter("aNumber", accountNumber);
		SavingsAccount savingsAccount = null;
		
		try {
			savingsAccount = theQuery.getSingleResult();
		}
		catch(Exception e) {
			savingsAccount = null;
		}
		return savingsAccount;
	}

	@Override
	public void save(SavingsAccount savingsAccount) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		currentSession.saveOrUpdate(savingsAccount);

	}

	

}
