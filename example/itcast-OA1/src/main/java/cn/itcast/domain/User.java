package cn.itcast.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * 用户实体
 * 
 * @author Administrator
 *
 */

public class User {

	private String loginName;
	private Long id;
	private String name;
	private int gender;
	private String phone;
	private String email;
	private String description;
	private String password;
	private Department department;
	private Set<Role> roles = new HashSet<Role>(); // 初始化为了防止空指针异常
	//public Department m_Department;

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	/*public Department getM_Department() {
		return m_Department;
	}

	public void setM_Department(Department m_Department) {
		this.m_Department = m_Department;
	}*/

	/**
	 * @Description:判断当前用户是否具有给定的权限
	 * @param name
	 * @return
	 */
	public boolean hasPrivilegeByName(String name) {
		// 如果是超级管理员，直接返回true
		if ("admin".equals(this.loginName)) {
			return true;
		}
		for (Role role : roles) {
			Set<Privilege> privileges = role.getPrivileges();
			for (Privilege privilege : privileges) {
				String pName = privilege.getName();
				if (name.equals(pName)) {
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * @Description:通过路径地址来判断用户是否具有访问该路径的权限
	 * @param url
	 * @return
	 */
	public boolean hasPrivilegeByUrl(String url) {
		// 如果是超级管理员，直接返回true
		if ("admin".equals(this.loginName)) {
			return true;
		}
		for (Role role : roles) {
			Set<Privilege> privileges = role.getPrivileges();
			for (Privilege privilege : privileges) {
				String pName = privilege.getUrl();
				if (url.equals(pName)) {
					return true;
				}
			}
		}
		return false;
	}
}