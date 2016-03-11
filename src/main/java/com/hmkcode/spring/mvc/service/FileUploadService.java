package com.hmkcode.spring.mvc.service;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.github.javaplugs.jsf.SpringScopeView;
import com.hmkcode.spring.mvc.model.FileMeta;
@Service
//@Scope("session")
@SpringScopeView
public class FileUploadService {
	
	LinkedList<FileMeta> files = new LinkedList<FileMeta>();
	FileMeta fileMeta = null;
	Integer teste = 2;
	
	/***************************************************
	 * URL: /rest/controller/upload  
	 * upload(): receives files
	 * @param request : MultipartHttpServletRequest auto passed
	 * @param response : HttpServletResponse auto passed
	 * @return LinkedList<FileMeta> as json format
	 ****************************************************/
	public LinkedList<FileMeta> retornaUpload(MultipartHttpServletRequest request){
		teste = 3;
		 Iterator<String> itr =  request.getFileNames();
		 MultipartFile mpf = null;

		 while(itr.hasNext()){
			 mpf = request.getFile(itr.next()); 
			 System.out.println(mpf.getOriginalFilename() +" uploaded! "+files.size());

			 if(files.size() >= 10)
				 files.pop();
			 
			 fileMeta = new FileMeta();
			 fileMeta.setFileName(mpf.getOriginalFilename());
			 fileMeta.setFileSize(mpf.getSize()/1024+" Kb");
			 fileMeta.setFileType(mpf.getContentType());
			 
			 try {
				fileMeta.setBytes(mpf.getBytes());
				
//				FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream("C:/temp/files/"+mpf.getOriginalFilename()));
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			 files.add(fileMeta);
			 
		 }
		return files;
	}
	
	/***************************************************
	 * URL: /rest/controller/get/{value}
	 * get(): get file as an attachment
	 * @param response : passed by the server
	 * @param value : value from the URL
	 * @return void
	 ****************************************************/
	 public void download(HttpServletResponse response,@PathVariable String value){
		 FileMeta getFile = files.get(Integer.parseInt(value));
		 try {		
			 	response.setContentType(getFile.getFileType());
			 	response.setHeader("Content-disposition", "attachment; filename=\""+getFile.getFileName()+"\"");
		        FileCopyUtils.copy(getFile.getBytes(), response.getOutputStream());
		 }catch (IOException e) {
				e.printStackTrace();
		 }
	 }
	 
	 @PostConstruct
	 public void inicia(){
		 System.out.println("Incializando bean fileUpload");
	 }
	 
	 @PreDestroy
	 public void finaliza(){
		 System.out.println("Finalizando Bean FileUplaod");
	 }

	public FileMeta getFileMeta() {
		return fileMeta;
	}

	public void setFileMeta(FileMeta fileMeta) {
		this.fileMeta = fileMeta;
	}
	 
	 

}
