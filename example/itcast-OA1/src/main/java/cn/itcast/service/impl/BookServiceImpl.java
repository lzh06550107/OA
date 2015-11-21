package cn.itcast.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.jbpm.api.ProcessEngine;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.dao.IBookDao;
import cn.itcast.domain.Book;
import cn.itcast.service.IBookService;
@Service
@Transactional
public class BookServiceImpl implements IBookService {
	
	@Resource
	private IBookDao bookDao;
	@Resource
	private ProcessEngine processEngine;
	
	public BookServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	public void save(Book entity) {
		bookDao.save(entity);
	}

	public void deleteBook(Long id) {
		bookDao.delete(id);
	}

	public void updateBook(Book entity) {
		bookDao.update(entity);
	}

	public Book getBookById(Long id) {
		return bookDao.getById(id);
	}

	public List<Book> getBookByIds(Long[] ids) {
		return bookDao.getByIds(ids);
	}

	public List<Book> findAll() {
		return bookDao.findAll();
	}

}
