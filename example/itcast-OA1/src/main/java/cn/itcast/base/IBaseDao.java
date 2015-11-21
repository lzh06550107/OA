package cn.itcast.base;

import java.util.List;

import cn.itcast.domain.PageBean;
import cn.itcast.utils.HQLHelper;

/**
 * 通用Dao接口
 * @author Administrator
 *
 */
public interface IBaseDao<T> {
	/**
	 * 添加
	 * @param entity
	 */
	public void save(T entity);
	/**
	 * 根据id删除
	 * @param id
	 */
	public void delete(Long id);
	/**
	 * 根据id修改
	 * @param entity
	 */
	public void update(T entity);
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public T getById(Long id);
	/**
	 * 一次查询多个对象
	 * @param ids
	 * @return
	 */
	public List<T> getByIds(Long[] ids);
	/**
	 * 查询所有
	 * @return
	 */
	public List<T> findAll();
	/**
	 * 公共分页
	 * @param hh
	 * @param currentPage
	 * @return
	 */
	public PageBean getPageBean(HQLHelper hh, int currentPage);
}
