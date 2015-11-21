package cn.itcast.dao;

import cn.itcast.base.IBaseDao;
import cn.itcast.domain.User;

public interface IUserDao extends IBaseDao<User> {

	int findByLoginName(String loginName);

	User login(User model);

}
