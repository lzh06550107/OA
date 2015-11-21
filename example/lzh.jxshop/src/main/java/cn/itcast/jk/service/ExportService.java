package cn.itcast.jk.service;

import java.io.Serializable;
import java.util.List;

import cn.itcast.jk.domain.Export;

public interface ExportService {
	public List<Export> find(Export entity);
	public Export get(Serializable id);
	public void insert(Export entity);
	public void update(Export entity);
	public void delete(Serializable id);
	public void delete(Serializable[] ids);
	public String getHTMLString(String id);
}
