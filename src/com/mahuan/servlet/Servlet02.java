package com.mahuan.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qfedu.dao.StudentsDao;
import com.qfedu.dao.User;

/**
 * Servlet implementation class Servlet03
 */
@WebServlet("/Servlet02")
public class Servlet02 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Servlet02() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		
		User user = null;
		
		//通过UUID查询下载文件
		String UUID=request.getParameter("UUID");
		System.out.println(UUID);
		StudentsDao studentsDao = new StudentsDao();
		try {
			user = studentsDao.selectOne(UUID);
			System.out.println("取出来个寂寞"+user.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("取出来个寂寞！！！");
			e.printStackTrace();
		}
		System.out.println(user.toString());
		String file_path = user.getFile_path();
		String path = file_path.replace("\\","\\\\");
		String path_name = path+"\\\\"+user.getUUID()+"."+user.getType();
		System.out.println(path_name);
		//获取目标文件的绝对路径
//		String realPath=this.getServletContext().getRealPath(path_name);
		File file=new File(path_name);
		//获取目标文件的输入流
		FileInputStream inputStream=new FileInputStream(file);
		//设置响应头
		response.setContentType("application/x-msdownload");
		response.setHeader("Content-Disposition", 
				"attachment;filename="+URLEncoder.encode(file.getName(),"UTF-8"));
	 /**
	  * setHeader:设置头文件的在课本的174页
	  * 主要意思为：设置值的类型为整型的响应头信息，其中有两个参数
	  * String name：响应头的名称
	  * String value：响应头的值
	  * 
	  * setContentType：设置响应体的大小
	  * 参数：
	  * String type
	  * 
	  * URLEncoder:HTML 格式编码的实用工具类。
	  * 该类包含了将 String 转换为 application/x-www-form-urlencoded MIME 格式的静态方法。
	  * 
	  * encode：使用指定的编码机制将字符串转换为 application/x-www-form-urlencoded 格式。
	  * 该方法使用提供的编码机制获取不安全字符的字节。 
	  */
		//通过response对象获取输出流
		ServletOutputStream outputStream=response.getOutputStream();
		byte[] buffer=new byte[1024]; 
		int len=0;
		while((len=inputStream.read(buffer))!=-1){
			outputStream.write(buffer,0,len);
		}
		outputStream.close();
		inputStream.close();
		/**
		 * 先开的后关闭
		 */
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
