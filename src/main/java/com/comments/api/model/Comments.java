package com.comments.api.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "comments")
public class Comments {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "username", nullable = false)
	private String username;
	
	@Column(name = "Text", nullable = false)
	private String text;
	
	@Column(name = "dateofcomment", nullable = false)
	private LocalDateTime dateOfComment;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public LocalDateTime getDateOfComment() {
		return dateOfComment;
	}

	public void setDateOfComment(LocalDateTime dateOfComment) {
		this.dateOfComment = dateOfComment;
	}

	@Override
	public String toString() {
		return "Comments [id=" + id + ", username=" + username + ", text=" + text + ", dateOfComment=" + dateOfComment
				+ "]";
	}
	
	
}
