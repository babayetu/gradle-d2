package com.vip.openhft;

import java.io.Serializable;
import java.util.Date;

public class StudentEntity implements Serializable {
	private static final long serialVersionUID = 3096154202413606831L;  
    private ClassEntity classEntity;  
    private Date studentBirthday;  
    private int id;  
    private String studentName;  
    private String studentSex;
    private Date updateTime;
    private Date createTime;
    

	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public ClassEntity getClassEntity() {
		return classEntity;
	}
	public void setClassEntity(ClassEntity classEntity) {
		this.classEntity = classEntity;
	}
	public Date getStudentBirthday() {
		return studentBirthday;
	}
	public void setStudentBirthday(Date studentBirthday) {
		this.studentBirthday = studentBirthday;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getStudentSex() {
		return studentSex;
	}
	public void setStudentSex(String studentSex) {
		this.studentSex = studentSex;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "StudentEntity [studentName=" + studentName + ", updateTime=" + updateTime + ", createTime=" + createTime
				+ "]";
	}


}
