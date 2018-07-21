package com.sts.demo;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.sts.demo.pojo.Author;
import com.sts.demo.service.AuthorService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthorTest {

	private static final Logger LOG = LogManager.getLogger(AuthorTest.class);
	
	@Autowired
	private AuthorService authorService;
	
	@Test
	public void test() {
		List<Author> authorList = authorService.selectAllByList();
		assertArrayEquals(
				new Object[]{
						authorList.size() > 0
                    }, 
                new Object[]{
                        true
                    }
        );
		LOG.info("authorList.size()="+authorList.size());
		assertTrue("author not null", authorList.size() > 0);
	}

}
