package cn.itcast.base;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import cn.itcast.domain.PageBean;
import cn.itcast.utils.HQLHelper;


public class BaseDaoImpl<T> implements IBaseDao<T> {
	@Resource
	private SessionFactory sessionFactory;
	private Class<T> clazz;
	//继承该类的具体类为
	//public class UserDao extends BaseDaoImpl<User>(){}
	//public class DepartmentDao extends BaseDaoImpl<Department>(){}
	//那么如何从父类获取子类传入的具体的类型
	public BaseDaoImpl(){
		//获取实体类型
		ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass(); //获得真正的父类
		Type[] types = genericSuperclass.getActualTypeArguments();
		clazz = (Class<T>) types[0];
	}
	
	public void save(T entity) {
		sessionFactory.getCurrentSession().save(entity);
	}

	public void delete(Long id) {
		sessionFactory.getCurrentSession().delete(sessionFactory.getCurrentSession().get(clazz, id));
	}

	public void update(T entity) {
		sessionFactory.getCurrentSession().update(entity);
	}

	public T getById(Long id) {
		return (T) sessionFactory.getCurrentSession().get(clazz, id);
	}
	//可以用循环，但还有更好的方法
	public List<T> getByIds(Long[] ids) {
		String hql = "FROM " + clazz.getSimpleName() + " WHERE id in (:ids)";
		Query query = (Query) sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameterList("ids", ids);
		return query.list();
	}

	public List<T> findAll() {
		String hql = "FROM " + clazz.getSimpleName();
		return sessionFactory.getCurrentSession().createQuery(hql).list();
	}
	public Session getSession(){
		return sessionFactory.getCurrentSession();
	}

	@Override
	public PageBean getPageBean(HQLHelper hh, int currentPage) {
		int pageSize = this.getPageSize(); //每页显示的记录条数
		int firstResult = (currentPage -1) * pageSize; //当前页第一条记录在总记录中的第几个
		String listHQL = hh.getListHQL();
		String countHQL = hh.getCountHQL();
		List<Object> args = hh.getArgs();
		//查询记录
		Query query = this.getSession().createQuery(listHQL);
		if(args != null && args.size() > 0){
			int index = 0;
			for(Object o : args){
				query.setParameter(index++, o);
			}
		}
		query.setFirstResult(firstResult);
		query.setMaxResults(pageSize);
		List recordList = query.list();
		
		//查询记录总条数
		query = this.getSession().createQuery(countHQL);
		//查询总记录数和查询list记录的条件是一样的
		if(args != null && args.size() > 0){
			int index = 0;
			for(Object o : args){
				query.setParameter(index++, o);
			}
		}
		Long recordCount = (Long) query.uniqueResult();
		
		return new PageBean(currentPage, pageSize, recordCount.intValue(), recordList);
	}
	
	private Integer getPageSize(){
		int pageSize = 10; //默认每页显示记录数
		
		Properties pro = new Properties();
		InputStream inStream = this.getClass().getClassLoader().getResourceAsStream("page.properties");
		try{
			pro.load(inStream);
			String str = pro.getProperty("pageSize");
			pageSize = Integer.parseInt(str);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(inStream != null){
				try {
					inStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return pageSize;
	}
	
}
