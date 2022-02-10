package com.mahuan.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.qfedu.dao.StudentsDao;
import com.qfedu.dao.User;
/**
 * Servlet implementation class Srevlet01
 */
@WebServlet("/Srevlet01")
public class Srevlet01 extends HttpServlet {
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		User user = new User();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
//        System.out.println(df.format(new Date()));
        user.setCreat_time(sdf.format(new Date()));// new Date()Ϊ��ȡ��ǰϵͳʱ��
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		//����DiskFileItemFactory����
		DiskFileItemFactory df=new DiskFileItemFactory();
		//�����ļ��ϴ��󱣴��Ŀ¼
		SimpleDateFormat sdf_path = new SimpleDateFormat("yyyy-MM-dd");
		String path_min = sdf_path.format(new Date());
		
		
//		File fileDir=new File(this.getServletContext().getRealPath("\\fileDir"));
		File fileDir=new File(this.getServletContext().getRealPath("\\\\"+path_min));
		user.setFile_path(this.getServletContext().getRealPath("\\\\"+path_min));
		//���fileDir·�����ں���Ĵ�����ʹ�õ�
		System.out.println(fileDir);
		if(!fileDir.exists()){
			fileDir.mkdirs();
		}
		//������ʱ�ļ�
		File tempDir=new File(this.getServletContext().getRealPath("\\tempDor"));
		if(!tempDir.exists()){
			tempDir.mkdirs();
		}
		df.setRepository(tempDir);//��ʱ�ļ�����Ҫ�ڴ�����λ��������Զ���
		//����ServletFileUpload����
		ServletFileUpload upload=new ServletFileUpload(df);
		//��д����
		upload.setHeaderEncoding("utf-8");
		//����request���󣬻�ȡ���ύ��ȫ�����ݣ�����װ��FileItem����洢��List
		List<FileItem> fileItemList;
		try{
			fileItemList=upload.parseRequest(request);
			for(FileItem fileItem:fileItemList){
				if(fileItem.isFormField()){//�鿴�ǲ����������ַ��� �����
					String name=fileItem.getFieldName();
					String value=fileItem.getString("utf-8");
					out.print("�û�"+value+"<br>");
				}else{//�������
					String fileName=fileItem.getName();
					String file_name = fileName.substring(fileName.lastIndexOf("\\")+1);
					user.setFile_odd_name(file_name);
					user.setType(file_name.substring(file_name.lastIndexOf(".")+1));
					user.setFile_size(fileItem.getSize()+"");
					out.print("�ļ���"+fileName+"<br>");
					//��ȡ�ļ���
					fileName=fileName.substring(fileName.lastIndexOf("\\")+1);
					/**
					 * substring������һ���µ��ַ��������Ǵ��ַ�����һ�����ַ�����
					 * �����ַ�����ָ�����������ַ���ʼ��ֱ�����ַ���ĩβ��
					 * 
					 * lastIndexOf������ָ�����ַ����ڴ��ַ��������ұ߳��ִ���������
					 * �����ұߵĿ��ַ��� "" ��Ϊ����������ֵ 
					 */
					//��֤�ļ���Ψһ
					String UUID_temp = UUID.randomUUID().toString();
					fileName=UUID_temp+"."+file_name.substring(file_name.lastIndexOf(".")+1);
					//��ʾͨ��Ψһ��ʶ�� (UUID) ����
					user.setUUID(UUID_temp);
					
					String filePath=fileDir+"\\"+fileName;
					File file=new File(filePath);
					InputStream inputStream=fileItem.getInputStream();
					FileOutputStream outputStream=new FileOutputStream(file);
					byte[] buffer=new byte[1024];
					int len;
					while((len=inputStream.read(buffer))>0){
						outputStream.write(buffer, 0, len);
					}
					
					StudentsDao studentsDao = new StudentsDao();
					try {
						studentsDao.insert(user);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					//�ر���
					inputStream.close();
					outputStream.close();
				}
			}
		}catch(FileUploadException e){
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
