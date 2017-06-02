package com.jx372.mysite.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jx372.mysite.repository.AdminDao;
import com.jx372.mysite.vo.AdminVo;

@Service
public class AdminService {

	@Autowired
	private AdminDao admindao;
	private static final String SAVE_PATH="/uploads";
	private static final String PREFIX_URL="/uploads/images/";

	public AdminVo get() {

		AdminVo adminvo = admindao.get();
		return adminvo;

	}

	public void insert(AdminVo adminvo) {
		
		admindao.insert(adminvo);		
	}
	

	public void update(AdminVo adminvo) {
		
		admindao.update(adminvo);

	}

	public String restore(MultipartFile file) {
		String url = "";
		try {
			
			if (file.isEmpty() == true) {
				return url;
			}

			String originalFileName = file.getOriginalFilename();
			String extName = originalFileName.substring(originalFileName.lastIndexOf("."), originalFileName.length());
			Long fileSize = file.getSize();
			String saveFileName = genSaveFileName(extName);

			System.out.println("##########" + originalFileName);
			System.out.println("##########" + extName);
			System.out.println("##########" + fileSize);
			System.out.println("##########" + saveFileName);

			writeFile(file, saveFileName);
			
			url = PREFIX_URL+saveFileName;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return url;
	}
	
	private String genSaveFileName(String extName) {
		String fileName = "";

		Calendar calendar = Calendar.getInstance();
		fileName += calendar.get(Calendar.YEAR);
		fileName += calendar.get(Calendar.MONTH);
		fileName += calendar.get(Calendar.DATE);
		fileName += calendar.get(Calendar.HOUR);
		fileName += calendar.get(Calendar.MINUTE);
		fileName += calendar.get(Calendar.SECOND);
		fileName += calendar.get(Calendar.MILLISECOND);
		fileName += extName;

		return fileName;
	}

	private void writeFile(MultipartFile multipartFile, String saveFileName) throws IOException {
		
		byte[] fileData = multipartFile.getBytes();
		FileOutputStream fos =  new FileOutputStream(SAVE_PATH+"/"+saveFileName);
		fos.write(fileData);
		fos.close();

	}


}
