package com.vip.openhft;

import java.util.Date;
import java.util.List;


public interface StudentMapper {
	public StudentEntity getStudent(String studentID);  
    
    public StudentEntity getStudentAndClass(String studentID);  
      
    public List<StudentEntity> getStudentAll();  
      
    public void insertStudent(StudentEntity entity);  
      
    public void deleteStudent(StudentEntity entity);  
      
    public void updateStudent(StudentEntity entity); 
    
    public List<StudentEntity> selectIncrementData(Date updateTime);
    
    public long selectMaxLastUpdateTime();
}
