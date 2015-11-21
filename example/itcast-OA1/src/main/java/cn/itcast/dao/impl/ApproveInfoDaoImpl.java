package cn.itcast.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import cn.itcast.base.BaseDaoImpl;
import cn.itcast.dao.IApproveInfoDao;
import cn.itcast.domain.ApproveInfo;
@Repository
public class ApproveInfoDaoImpl extends BaseDaoImpl<ApproveInfo> implements
		IApproveInfoDao {

	@Override
	public List<ApproveInfo> findApproveInfoListByApplicationId(
			Long applicationId) {
		String hql = "FROM ApproveInfo ai WHERE ai.application.id = ? ORDER BY ai.approveTime ASC";
		Query query = this.getSession().createQuery(hql);
		query.setParameter(0, applicationId);
		
		return query.list();
	}
}
