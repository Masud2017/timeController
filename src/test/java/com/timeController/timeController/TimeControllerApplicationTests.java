package com.timeController.timeController;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import com.timeController.timeController.controller.TodoController;
import com.timeController.timeController.model.TodoModel;
import com.timeController.timeController.service.MultiPartFileImpl;
import com.timeController.timeController.service.ProfileImageServiceImpl;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javassist.bytecode.ByteArray;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;


@SpringBootTest
class TimeControllerApplicationTests {
	private static final Logger LOG = LoggerFactory.getLogger(TimeControllerApplicationTests.class);
	

	@Test
	void contextLoads() {
	}

	@Test
	void TestTodo2() {
		TodoController todo = new TodoController();
		String result = todo.hello();
		assertEquals(result, "Hello world");
	}

	@Test
	void TestMulipartFileImpl() throws URISyntaxException, IOException {
		
		File file = new File("image.jpg");
		FileInputStream fin = new FileInputStream(file);
		BufferedInputStream bin = new BufferedInputStream(fin);
		byte[] bt = bin.readAllBytes();

		
		ByteArrayResource btRe = new ByteArrayResource(bt);

		MultipartFile mul = new MultiPartFileImpl(btRe);

		
		LOG.debug(btRe.getFilename());
		assertEquals(btRe.getFilename(), bt.getClass().getName()+"p","Two return values are not equal");
	}
	
	@Test
	void TestProfileImageServiceImpl () {
		ProfileImageServiceImpl profileImage = new ProfileImageServiceImpl();
		File fp = new File("/home/unroot/Desktop/profile-avator.png");
		try {
			FileInputStream in = new FileInputStream(fp);

			BufferedInputStream imageReader = new BufferedInputStream(in);
			try {
				byte[] arr = imageReader.readAllBytes();
				ByteArrayResource byteArrayResource = new ByteArrayResource(arr);
				
				MultipartFile mult = new MultiPartFileImpl(byteArrayResource);


				profileImage.setImageContent(mult);
				profileImage.setImageName(mult.getName());
				profileImage.writeImageToDisk();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
