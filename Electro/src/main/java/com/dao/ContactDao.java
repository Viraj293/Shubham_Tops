package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.bean.Contact;
import com.util.ProjectUtil;

public class ContactDao {
	
	public static void insertContact(Contact c)
	{
		Connection conn = ProjectUtil.createConnection();
		try {
			String sql = "insert into contact (fname,lname,mobile,address) values (?,?,?,?)";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, c.getFname());
			pst.setString(2, c.getLname());
			pst.setLong(3, c.getMobile());
			pst.setString(4, c.getAddress());
			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
