package cn.itcast.dao.impl;

import org.springframework.stereotype.Repository;

import cn.itcast.base.BaseDaoImpl;
import cn.itcast.dao.IBookDao;
import cn.itcast.domain.Book;
@Repository
public class BookDaoImpl extends BaseDaoImpl<Book> implements IBookDao {
	//继承是为了传递Book给父类并使用父类的方法
	//实现IBookDao接口是为了制定一个规范，表明本来实现了该接口方法，其实可以省略
}
