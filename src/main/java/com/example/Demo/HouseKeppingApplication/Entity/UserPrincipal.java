package com.example.Demo.HouseKeppingApplication.Entity;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipal implements UserDetails{
	
	private User user;
	
	public UserPrincipal(User user) {
        this.user = user;
    }


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return Collections.singleton(new SimpleGrantedAuthority("USER"));
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getUserPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}
	
	@Override
    public boolean isAccountNonExpired() {
        return true; // Adjust as per your needs
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Adjust as per your needs
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Adjust as per your needs
    }

    @Override
    public boolean isEnabled() {
        return true; // Adjust as per your needs
    }

}
