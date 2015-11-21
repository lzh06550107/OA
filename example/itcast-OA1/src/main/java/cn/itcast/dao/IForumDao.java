package cn.itcast.dao;

import java.util.List;

import cn.itcast.base.IBaseDao;
import cn.itcast.domain.Forum;
import cn.itcast.domain.PageBean;
import cn.itcast.utils.HQLHelper;
//在面向接口编程时，如果IForumDao不继承IBaseDao<Forum>
//声明为IForumDao 的变量不能获取IBaseDao<Forum>中声明的方法
//即使IForumDao的实现类继承了IBaseDao<Forum>的实现类
public interface IForumDao extends IBaseDao<Forum> {

	List<Forum> findAll();

	Forum getById(Long id);


}
