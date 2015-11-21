package cn.itcast.service;

import java.util.List;

import cn.itcast.domain.Book;

public interface IBookService {
	public void save(Book entity);
	
	public void deleteBook(Long id);
	
	public void updateBook(Book entity);
	
	public Book getBookById(Long id);
	
	public List<Book> getBookByIds(Long[] ids);
	
	public List<Book> findAll();
}
