package com.vip.openhft;

import java.io.Reader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class DBPollingThread implements Runnable{
	private boolean stopMe = false;
	private static SqlSessionFactory sqlSessionFactory;
	private static Reader reader;

	static {
		try {
			reader = Resources.getResourceAsReader("mybatis-config.xml");
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static SqlSessionFactory getSession() {
		return sqlSessionFactory;
	}
	
	@Override
	public void run() {
		SqlSession session = sqlSessionFactory.openSession();
		DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
		Date initialDate = null;
		try {
			initialDate = dateFormat1.parse("2000-01-01");
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		while(!stopMe) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			System.out.println("next polling date" + initialDate);
			
			List<StudentEntity> lastUpdateStudentList = session.selectList("selectIncrementData",
					initialDate);
			if (lastUpdateStudentList != null) {
				for (StudentEntity entityTemp : lastUpdateStudentList) {
					System.out.println(entityTemp);
				}
			}
			
			initialDate = new Date();

		}

		session.close();
	}

	public boolean isStopMe() {
		return stopMe;
	}


	public void setStopMe(boolean stopMe) {
		this.stopMe = stopMe;
	}

	public static void main(String[] args) throws InterruptedException {
		DBPollingThread dbpt = new DBPollingThread();
		Thread t1 = new Thread(dbpt);
		t1.start();
		
		Thread.sleep(60000);
		dbpt.setStopMe(true);	
	}
}
