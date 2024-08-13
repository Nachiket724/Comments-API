package com.comments.api.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.comments.api.model.Comments;

@Repository("commentsRepository")
public interface CommentsRepository extends JpaRepository<Comments, Long> {
	List<Comments> findByUsername(String username);

	@Query("SELECT c FROM Comments c WHERE DATE(c.dateOfComment) = :date")
	List<Comments> findByDateOfComment(LocalDate date);
	
	boolean existsByUsernameAndText(String username, String text);
}
