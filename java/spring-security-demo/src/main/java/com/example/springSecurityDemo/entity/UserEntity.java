package com.example.springSecurityDemo.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

// 用户信息表
@Data
public class UserEntity implements UserDetails {

	private Integer id;
	private String username;
	private String realname;
	private String password;
	private Date createDate;
	private Date lastLoginTime;
	private boolean enabled;
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	// 用户所有权限
	private List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

	public Collection<? extends GrantedAuthority> getAuthorities() {

		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	//账户是否过期,过期无法验证
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	//指定用户是否被锁定或者解锁,锁定的用户无法进行身份验证
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	//指示是否已过期的用户的凭据(密码),过期的凭据防止认证
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	//是否被禁用,禁用的用户不能身份验证
	@Override
	public boolean isEnabled() {
		return true;
	}
}
