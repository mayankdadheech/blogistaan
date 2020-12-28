package com.blogistaan.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Posts {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int postId;
//	private String topic;
	private String title;
	@Column(length = 3000)
	private String content;	
	private Date date;
	@ManyToOne
	private Blog blog;
}
