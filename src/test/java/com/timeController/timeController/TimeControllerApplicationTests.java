package com.timeController.timeController;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.timeController.timeController.controller.TodoController;
import com.timeController.timeController.model.TodoModel;
import com.timeController.timeController.service.ProfileImageServiceImpl;

import org.junit.jupiter.api.Test;
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
	void TestProfileImageServiceImpl () {
		ProfileImageServiceImpl profileImage = new ProfileImageServiceImpl();
		File fp = new File("/home/unroot/Desktop/pika.gif");
		try {
			FileInputStream in = new FileInputStream(fp);

			BufferedInputStream imageReader = new BufferedInputStream(in);
			try {
				byte[] arr = imageReader.readAllBytes();
				ByteArrayResource byteArrayResource = new ByteArrayResource(arr);
				
				
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
