package cn.itcast.jk.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import cn.itcast.jk.dao.ExportDao;
import cn.itcast.jk.dao.ExportProductDao;
import cn.itcast.jk.domain.Export;
import cn.itcast.jk.domain.ExportProduct;
import cn.itcast.jk.service.ExportService;
import cn.itcast.utils.UtilFuns;

@Service
@Transactional
public class ExportServiceImpl implements ExportService {

	@Resource
	private ExportDao exportDao;
	@Resource
	private ExportProductDao exportProductDao;

	public List<Export> find(Export entity) {
		return exportDao.find(entity);
	}

	public Export get(Serializable id) {
		return exportDao.get(id);
	}

	public void insert(Export entity) {
		exportDao.insert(entity);
	}

	public void update(Export entity) {
		exportDao.update(entity);
	}

	public void delete(Serializable id) {
		exportDao.delete(id);
	}

	public void delete(Serializable[] ids) {
		exportDao.delete(ids);
	}

	// 拼接HTML判断，addTRRecord("mRecordTable", id, productNo, cnumber, grossWeight,
	// netWeight, sizeLength, sizeWidth, sizeHeight, exPrice, tax);
	public String getHTMLString(String exportId) {

		List<ExportProduct> epList = exportProductDao.findByExportId(exportId);
		StringBuffer sBuf = new StringBuffer();
		for (ExportProduct ep : epList) {
			sBuf.append("addTRRecord(\"mRecordTable\", \"").append(ep.getId())
					.append("\", \"").append(ep.getProductNo())
					.append("\", \"")
					.append(UtilFuns.convertNull(ep.getCnumber()))
					.append("\", \"")
					.append(UtilFuns.convertNull(ep.getGrossWeight()))
					.append("\", \"")
					.append(UtilFuns.convertNull(ep.getNetWeight()))
					.append("\", \"")
					.append(UtilFuns.convertNull(ep.getSizeLength()))
					.append("\", \"")
					.append(UtilFuns.convertNull(ep.getSizeWidth()))
					.append("\", \"")
					.append(UtilFuns.convertNull(ep.getSizeHeight()))
					.append("\", \"")
					.append(UtilFuns.convertNull(ep.getExPrice()))
					.append("\", \"").append(UtilFuns.convertNull(ep.getTax()))
					.append("\");");
		}

		return sBuf.toString();
	}

}
