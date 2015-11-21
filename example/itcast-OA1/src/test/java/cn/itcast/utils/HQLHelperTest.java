package cn.itcast.utils;

import org.junit.Test;

import cn.itcast.domain.User;

public class HQLHelperTest {
	@Test
	public void test(){
		HQLHelper hh = new HQLHelper(User.class);
		//hh.addWhere("o.name = ?", "zhangsan");
		//hh.addWhere("o.age > ?", 20);
		//hh.addWhere("o.address = ?", "bj");
		//hh.addWhere("o.tel is not null");
		
		hh.addOrderBy("o.name", false);
		hh.addOrderBy("o.age", true);
		
		System.out.println(hh.getListHQL());
		System.out.println(hh.getCountHQL());
		System.out.println(hh.getArgs());
	}
}
