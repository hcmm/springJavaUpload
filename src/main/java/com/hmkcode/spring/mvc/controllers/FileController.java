package com.hmkcode.spring.mvc.controllers;

import java.util.LinkedList;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.hmkcode.spring.mvc.model.FileMeta;
import com.hmkcode.spring.mvc.service.FileUploadService;

@Controller
@RequestMapping("/controller")
public class FileController implements BeanFactoryAware {

	private BeanFactory beanFactory;


	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public @ResponseBody LinkedList<FileMeta> upload(
			MultipartHttpServletRequest request, HttpServletResponse response) {
		LinkedList<FileMeta> retornaUpload = getFileUpload().retornaUpload(request);
		return retornaUpload;

	}

	@RequestMapping(value = "/get/{value}", method = RequestMethod.GET)
	public void get(HttpServletResponse response, @PathVariable String value) {
		getFileUpload().download(response, value);
	}
	
	@Override
	public void setBeanFactory(BeanFactory bf) throws BeansException {
		this.beanFactory = bf;
	}

	public FileUploadService getFileUpload() {
		return beanFactory.getBean(FileUploadService.class);
	}
	
	 @PostConstruct
	 public void inicia(){
		 System.out.println("Inicializanbdo Controller");
	 }
	 
	 @PreDestroy
	 public void finaliza(){
		 System.out.println("Finalizando Controller");
	 }

}
