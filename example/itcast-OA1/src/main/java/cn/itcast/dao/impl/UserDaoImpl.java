package cn.itcast.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.itcast.base.BaseDaoImpl;
import cn.itcast.dao.IUserDao;
import cn.itcast.domain.User;
import cn.itcast.utils.MD5Utils;
@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements IUserDao {


	public int findByLoginName(String loginName) {
		String hql = "SELECT COUNT(id) FROM User u WHERE u.loginName = ?";
		Long count = (Long) this.getSession().createQuery(hql).setParameter(0, loginName).uniqueResult();
		return count.intValue();
	}

	public User login(User model) {
		String hql = "FROM User u WHERE u.loginName = ? AND u.password = ?";
		List<User> list = this.getSession().createQuery(hql).setParameter(0, model.getLoginName())
				.setParameter(1, MD5Utils.md5(model.getPassword())).list();
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
}
