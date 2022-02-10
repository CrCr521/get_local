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
		
		//ͨ��UUID��ѯ�����ļ�
		String UUID=request.getParameter("UUID");
		System.out.println(UUID);
		StudentsDao studentsDao = new StudentsDao();
		try {
			user = studentsDao.selectOne(UUID);
			System.out.println("ȡ��������į"+user.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("ȡ��������į������");
			e.printStackTrace();
		}
		System.out.println(user.toString());
		String file_path = user.getFile_path();
		String path = file_path.replace("\\","\\\\");
		String path_name = path+"\\\\"+user.getUUID()+"."+user.getType();
		System.out.println(path_name);
		//��ȡĿ���ļ��ľ���·��
//		String realPath=this.getServletContext().getRealPath(path_name);
		File file=new File(path_name);
		//��ȡĿ���ļ���������
		FileInputStream inputStream=new FileInputStream(file);
		//������Ӧͷ
		response.setContentType("application/x-msdownload");
		response.setHeader("Content-Disposition", 
				"attachment;filename="+URLEncoder.encode(file.getName(),"UTF-8"));
	 /**
	  * setHeader:����ͷ�ļ����ڿα���174ҳ
	  * ��Ҫ��˼Ϊ������ֵ������Ϊ���͵���Ӧͷ��Ϣ����������������
	  * String name����Ӧͷ������
	  * String value����Ӧͷ��ֵ
	  * 
	  * setContentType��������Ӧ��Ĵ�С
	  * ������
	  * String type
	  * 
	  * URLEncoder:HTML ��ʽ�����ʵ�ù����ࡣ
	  * ��������˽� String ת��Ϊ application/x-www-form-urlencoded MIME ��ʽ�ľ�̬������
	  * 
	  * encode��ʹ��ָ���ı�����ƽ��ַ���ת��Ϊ application/x-www-form-urlencoded ��ʽ��
	  * �÷���ʹ���ṩ�ı�����ƻ�ȡ����ȫ�ַ����ֽڡ� 
	  */
		//ͨ��response�����ȡ�����
		ServletOutputStream outputStream=response.getOutputStream();
		byte[] buffer=new byte[1024]; 
		int len=0;
		while((len=inputStream.read(buffer))!=-1){
			outputStream.write(buffer,0,len);
		}
		outputStream.close();
		inputStream.close();
		/**
		 * �ȿ��ĺ�ر�
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
