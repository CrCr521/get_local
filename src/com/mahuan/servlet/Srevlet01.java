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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
//        System.out.println(df.format(new Date()));
        user.setCreat_time(sdf.format(new Date()));// new Date()为获取当前系统时间
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		//创建DiskFileItemFactory对象
		DiskFileItemFactory df=new DiskFileItemFactory();
		//设置文件上传后保存的目录
		SimpleDateFormat sdf_path = new SimpleDateFormat("yyyy-MM-dd");
		String path_min = sdf_path.format(new Date());
		
		
//		File fileDir=new File(this.getServletContext().getRealPath("\\fileDir"));
		File fileDir=new File(this.getServletContext().getRealPath("\\\\"+path_min));
		user.setFile_path(this.getServletContext().getRealPath("\\\\"+path_min));
		//这个fileDir路径会在后面的代码中使用到
		System.out.println(fileDir);
		if(!fileDir.exists()){
			fileDir.mkdirs();
		}
		//设置临时文件
		File tempDir=new File(this.getServletContext().getRealPath("\\tempDor"));
		if(!tempDir.exists()){
			tempDir.mkdirs();
		}
		df.setRepository(tempDir);//临时文件还需要在此设置位置这个是自动的
		//创建ServletFileUpload对象
		ServletFileUpload upload=new ServletFileUpload(df);
		//编写编码
		upload.setHeaderEncoding("utf-8");
		//处理request对象，获取表单提交的全部内容，将封装的FileItem对象存储在List
		List<FileItem> fileItemList;
		try{
			fileItemList=upload.parseRequest(request);
			for(FileItem fileItem:fileItemList){
				if(fileItem.isFormField()){//查看是不是正常的字符串 如果是
					String name=fileItem.getFieldName();
					String value=fileItem.getString("utf-8");
					out.print("用户"+value+"<br>");
				}else{//如果不是
					String fileName=fileItem.getName();
					String file_name = fileName.substring(fileName.lastIndexOf("\\")+1);
					user.setFile_odd_name(file_name);
					user.setType(file_name.substring(file_name.lastIndexOf(".")+1));
					user.setFile_size(fileItem.getSize()+"");
					out.print("文件："+fileName+"<br>");
					//获取文件名
					fileName=fileName.substring(fileName.lastIndexOf("\\")+1);
					/**
					 * substring：返回一个新的字符串，它是此字符串的一个子字符串。
					 * 该子字符串从指定索引处的字符开始，直到此字符串末尾。
					 * 
					 * lastIndexOf：返回指定子字符串在此字符串中最右边出现处的索引。
					 * 将最右边的空字符串 "" 视为出现在索引值 
					 */
					//保证文件名唯一
					String UUID_temp = UUID.randomUUID().toString();
					fileName=UUID_temp+"."+file_name.substring(file_name.lastIndexOf(".")+1);
					//表示通用唯一标识符 (UUID) 的类
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
					
					//关闭流
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
