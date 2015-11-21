package cn.itcast.dao.impl;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.itcast.dao.IUserDao;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration({"classpath:applicationContext.xml"})
public class UserDaoImplTest extends AbstractTransactionalJUnit4SpringContextTests {
	@Resource
	IUserDao userDao;
	
	@Test
	public void testFindByLoginName(){
		int i = userDao.findByLoginName("zhangsan");
		assertThat(i, is(1));
	}
}
