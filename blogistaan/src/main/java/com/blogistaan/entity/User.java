package com.blogistaan.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@NotBlank(message="Field should not be left blank.")
	@Size(min=2, max=32, message="Field should be between 2 to 32 characters.")
	private String firstName;
	@NotBlank(message="Field should not be left blank.")
	@Size(min=2, max=32, message="Field should be between 2 to 32 characters.")
	private String lastName;
	private String phoneNumber;
	@Column(unique = true)
	@Email(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$")
	@NotBlank(message="Field should not be left blank.")
	@Size(min=2, max=32, message="Field should be between 2 to 32 characters.")
	private String email;
	@Column(length = 1000)
	@NotBlank(message="Field should not be left blank.")
	@Size(min=2, message="Field should be minimum 2 characters.")
	private String password;
	private Date dateOfBirth;
	private String gender;
	private String role;
	private String country;
	private String image;
	private boolean enabled;
	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	private List<Blog> blogs = new ArrayList<>();
}
