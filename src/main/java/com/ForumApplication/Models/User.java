package com.ForumApplication.Models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ForumApplication.JWTModels.Authority;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
//@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User implements UserDetails {

//	@Id
//	@GeneratedValue
//	private int userid;
//	private String usertype;
//	
//	@ManyToMany
//	@JoinTable(name ="User_Role_Mapping",joinColumns=@JoinColumn(name="userid"),
//    inverseJoinColumns=@JoinColumn(name="roleid")
//    )
//	private List<Role> listofroles = new ArrayList<Role>();
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private boolean enabled = true;
   
//    private String profile;

    //user many roles

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
    @JsonIgnore
    private Set<UserRole> userRoles = new HashSet<>();
		
		//////////////////////JWT////////////////////////////
		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<Authority> set=new HashSet<>();
		
		this.userRoles.forEach(userRole->{
		set.add(new Authority(userRole.getRole().getRoleName()));
		});
		
		
		
		return set;
		}
		
		@Override
		public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
		}
		
		@Override
		public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
		}
		
		@Override
		public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
		}

		public User(Long id, String username, String password, String firstName, String lastName, String email,
				String phone, boolean enabled, Set<UserRole> userRoles) {
			super();
			this.id = id;
			this.username = username;
			this.password = password;
			this.firstName = firstName;
			this.lastName = lastName;
			this.email = email;
			this.phone = phone;
			this.enabled = enabled;
			this.userRoles = userRoles;
		}
		//////////////////////////////////////////////////////////////////
	
	

	
}
