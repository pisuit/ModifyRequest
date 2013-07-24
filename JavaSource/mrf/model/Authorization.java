package mrf.model;

import mrf.customtype.Role;
import mrf.customtype.RoleKey;

public class Authorization {
	private Long id = null;
	private RoleKey roleKey = RoleKey.MAIN_ROLE;
	private Role role = null;
	private User user = null;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public RoleKey getRoleKey() {
		return roleKey;
	}
	public void setRoleKey(RoleKey roleKey) {
		this.roleKey = roleKey;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
}
