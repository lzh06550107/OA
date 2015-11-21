package cn.itcast.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.dao.IApproveInfoDao;
import cn.itcast.domain.ApproveInfo;
import cn.itcast.service.IApproveInfoService;
@Service
@Transactional
public class ApproveInfoServiceImpl implements IApproveInfoService {
	@Resource
	private IApproveInfoDao approveInfoDao;

	@Override
	public List<ApproveInfo> findApproveInfoListByApplicationId(
			Long applicationId) {
		return approveInfoDao.findApproveInfoListByApplicationId(applicationId);
	}
}
