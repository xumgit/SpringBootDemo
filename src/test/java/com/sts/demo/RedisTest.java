package com.sts.demo;

import static org.junit.Assert.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.sts.demo.service.RedisService;

/*
 * 	@Before：初始化方法   对于每一个测试方法都要执行一次（注意与BeforeClass区别，后者是对于所有方法执行一次）
 *  @After：释放资源  对于每一个测试方法都要执行一次（注意与AfterClass区别，后者是对于所有方法执行一次）
 *	@Test：测试方法，在这里可以测试期望异常和超时时间 
 *	@Test(expected=ArithmeticException.class)检查被测方法是否抛出ArithmeticException异常 
 *	@Ignore：忽略的测试方法 
 *	@BeforeClass：针对所有测试，只执行一次，且必须为static void 
 *	@AfterClass：针对所有测试，只执行一次，且必须为static void 
 *	一个JUnit4的单元测试用例执行顺序为： 
 *	@BeforeClass -> @Before -> @Test -> @After -> @AfterClass; 
 *	每一个测试方法的调用顺序为： 
 *	@Before -> @Test -> @After; 
 * 
 * */

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

	private static final Logger LOG = LogManager.getLogger(RedisTest.class);

	@Autowired
	RedisService redisService;

	@Before
	public void before() {
		redisService.setStr("a", "12345");
		//Author author = new Author();
		//author.setName("bTestName");
		//author.setAge(11);
		redisService.setObj("b", "name=bTestName&age=11"); // serialize
		LOG.info("@Before");
	}

	@Test
	public void test() {
		String aValue = redisService.getStr("a");
		LOG.info("aValue="+aValue);
		assertEquals("12345", aValue);
		
		String bAuthor = (String)redisService.getObj("b");
		LOG.info("bName="+bAuthor.split("&")[0].split("=")[1]);
		assertEquals("bTestName", bAuthor.split("&")[0].split("=")[1]);
	}

	@Ignore
	@Test
	public void testIgnore() {
		LOG.info("@Ignore");
	}

	@Test(timeout = 50000)     
	public void testTimeout() {     
		LOG.info("@Test(timeout = 50000)");     
		assertEquals(5 + 5, 10);     
	}

	@Test(expected = ArithmeticException.class)     
	public void testExpected() {     
		LOG.info("@Test(expected = Exception.class)");     
		throw new ArithmeticException();     
	}

	@After
	public void after() {
		redisService.del("a");
		redisService.delObj("b");
		LOG.info("@After");
	}

	@BeforeClass
	public static void beforeClass() {
		LOG.info("@BeforeClass");
	};

	@AfterClass
	public static void afterClass() {
		LOG.info("@AfterClass");
	};
}
