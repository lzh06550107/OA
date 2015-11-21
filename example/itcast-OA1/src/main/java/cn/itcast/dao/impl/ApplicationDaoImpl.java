package cn.itcast.dao.impl;

import org.springframework.stereotype.Repository;

import cn.itcast.base.BaseDaoImpl;
import cn.itcast.dao.IApplicationDao;
import cn.itcast.domain.Application;
@Repository
public class ApplicationDaoImpl extends BaseDaoImpl<Application> implements IApplicationDao{

}
