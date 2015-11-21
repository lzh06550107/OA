package cn.itcast.service.impl;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.itcast.domain.Book;
import cn.itcast.service.IBookService;

public class TestBaseDao {
	/**
	 * 测试save()
	 */
	@Test
	public void test1(){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		IBookService bookService = (IBookService) ctx.getBean("bookServiceImpl");
		
		Book book = new Book();
		book.setName("cock");
		
		bookService.save(book);
	}
	/**
	 * 测试update()
	 */
	@Test
	public void test2(){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		IBookService bookService = (IBookService) ctx.getBean("bookServiceImpl");
		
		Book book = new Book();
		book.setId(3L);
		book.setName("sex");
		
		bookService.updateBook(book);
	}
	/**
	 * 测试delete()
	 */
	@Test
	public void test3(){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		IBookService bookService = (IBookService) ctx.getBean("bookServiceImpl");
		
		bookService.deleteBook(5L);
	}
	/**
	 * 测试getById()
	 */
	@Test
	public void test4(){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		IBookService bookService = (IBookService) ctx.getBean("bookServiceImpl");
		
		Book book = bookService.getBookById(1L);
		System.out.println(book);
	}
	/**
	 * 测试getById()
	 */
	@Test
	public void test5(){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		IBookService bookService = (IBookService) ctx.getBean("bookServiceImpl");
		Long[] ids = new Long[]{1L,3L};
		List<Book> books = bookService.getBookByIds(ids);
		for(Book book : books)
			System.out.println(book);
	}
	/**
	 * 测试findAll()
	 */
	@Test
	public void test6(){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		IBookService bookService = (IBookService) ctx.getBean("bookServiceImpl");
		List<Book> books = bookService.findAll();
		for(Book book : books)
			System.out.println(book);
	}
}
