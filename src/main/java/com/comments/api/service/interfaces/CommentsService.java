package com.comments.api.service.interfaces;

import java.time.LocalDate;
import java.util.List;
import com.comments.api.model.Comments;

public interface CommentsService {

	boolean existsById(Long id);
	
	List<Comments> getAllComments();

	List<Comments> getCommentsByUsername(String username);

	List<Comments> getCommentsByDate(LocalDate date);

	Comments saveComment(Comments comments) throws Exception;

	Comments updateComment(Long id, Comments newComments)  throws Exception;

	void deleteComment(Long id);

}
