package com.vip.openhft;

import java.io.Reader;
import java.util.Date;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class DBUpdateThread {
	private static SqlSessionFactory sqlSessionFactory;
	private static Reader reader;
	public static final String DATE_TIME_FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

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

	public static void main(String[] args) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			StudentEntity se = new StudentEntity();
			se.setStudentName("lele");
			Date insertDate = new Date();
			se.setUpdateTime(insertDate);
			se.setCreateTime(insertDate);
			 int insertResult = session.insert("insertStudent", se);
			System.out.println(insertResult);
		} finally {
			session.close();
		}
	}

}
