package com.timeController.timeController;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


//@SpringBootTest
class TimeControllerApplicationTests {
	
	Hello hl = new Hello();

	@Test
	void contextLoads() {
		int res = hl.retVal();

		assertEquals(2, res);
	}
	class Hello {
		public int retVal() {
			return 2;
		}
	}

}
