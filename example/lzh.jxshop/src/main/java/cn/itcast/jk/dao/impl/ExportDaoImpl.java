package cn.itcast.jk.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.itcast.jk.dao.ExportDao;
import cn.itcast.jk.domain.Export;
@Repository
public class ExportDaoImpl extends BaseDaoImpl<Export> implements ExportDao {
	public ExportDaoImpl(){
		this.setNs("cn.itcast.jk.mapper.ExportMapper.");
	}
	
}
