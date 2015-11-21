package cn.itcast.dao;

import java.util.List;

import cn.itcast.base.IBaseDao;
import cn.itcast.domain.ApproveInfo;

public interface IApproveInfoDao extends IBaseDao<ApproveInfo> {

	public List<ApproveInfo> findApproveInfoListByApplicationId(Long applicationId);

}
