package com.qfedu.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.qfedu.dao.C3P0Utils;
import com.qfedu.dao.User;

public class StudentsDao {
/**
 * 添加 用 连接池 实现
 * @throws SQLException 
 */
	public int insert(User stu) throws SQLException{
		QueryRunner run=new QueryRunner(C3P0Utils.getDataSource());
		String sql="insert into user(file_size,file_odd_name,creat_time,file_path,UUID,type) values(?,?,?,?,?,?)";
		Object[] param=new Object[]{stu.getFile_size(),stu.getFile_odd_name(),stu.getCreat_time(),stu.getFile_path(),stu.getUUID(),stu.getType()};
		int count=run.update(sql, param);
		return count;
	}
	/**
	 * 修改
	 * @throws Exception 
	 */
//	public int update(User stu) throws Exception{
//		QueryRunner run=new QueryRunner(C3P0Utils.getDataSource());
//		String sql="update student set age=?,sname=? ,course=? where sid=?";
//		Object[] param=new Object[]{stu.getAge(),stu.getSname(),stu.getCourse(),stu.getSid()};
//		int count=run.update(sql, param);
//		return count;
//	}
	/**
	 * 删除
	 * @throws Exception 
	 */
//	public int delete(Student stu) throws Exception{
//		QueryRunner run=new QueryRunner(C3P0Utils.getDataSource());
//		String sql="delete from student where sname=?";
//		Object[] param=new Object[]{stu.getSname()};
//		int count=run.update(sql,param);
//		return count;
//	}
	/**
	 * 根据id查询单个数居
	 * @throws Exception 
	 */
	public User selectOne(String UUID) throws Exception{
		QueryRunner run=new QueryRunner(C3P0Utils.getDataSource());
		String sql="select * from user where UUID=?";
		Object[] param=new Object[]{UUID};
		User newStu=run.query(sql, new BeanHandler<>(User.class),param);
		System.out.println(newStu.getCreat_time());
		return newStu;
	}
	/**
	 * 查询许所有数据
	 * @throws Exception 
	 */
//	public List<Student> selectAll() throws Exception{
//		QueryRunner run=new QueryRunner(C3P0Utils.getDataSource());
//		String sql="select * from student";
//		List<Student> list=run.query(sql, new BeanListHandler<>(Student.class));
//		return list;
//	}
}
