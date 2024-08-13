package com.comments.api.service.implementation;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comments.api.model.Comments;
import com.comments.api.repository.CommentsRepository;
import com.comments.api.service.interfaces.CommentsService;

@Service
public class CommentsServiceImpl implements CommentsService {
	
	@Autowired
	private CommentsRepository commentsRepository;

	@Override
	public List<Comments> getAllComments() {
		return commentsRepository.findAll();
	}

	@Override
	public List<Comments> getCommentsByUsername(String username) {
		if (username == null || username.trim().isEmpty()) {
	        throw new IllegalArgumentException("Username cannot be null or empty");
	    }
		return commentsRepository.findByUsername(username);
	}

	@Override
	public List<Comments> getCommentsByDate(LocalDate date) {
		if(date==null) {
			throw new IllegalArgumentException("Date cannot be null");
		}
		return commentsRepository.findByDateOfComment(date);
	}

	@Override
	public Comments saveComment(Comments comments) throws Exception {
		if(comments==null) {
			throw new IllegalArgumentException("Comment cannot be null");
		}
		if (comments.getUsername() == null || comments.getUsername().trim().isEmpty()) {
	        throw new IllegalArgumentException("Username cannot be null or empty");
	    }
	    if (comments.getText() == null || comments.getText().trim().isEmpty()) {
	        throw new IllegalArgumentException("Text cannot be null or empty");
	    }
	    
	    comments.setUsername(comments.getUsername().trim());
	    comments.setText(comments.getText().trim());

	    if(commentsRepository.existsByUsernameAndText(comments.getUsername(), comments.getText())) {
	    	throw new Exception("A similar comment already exists");
	    }
		return commentsRepository.save(comments);
	}

	@Override
	public Comments updateComment(Long id, Comments newComments) throws Exception {
		if(newComments==null) {
			throw new IllegalArgumentException("Updated comment data cannot be null");
		}
		return commentsRepository.findById(id)
				.map(comment -> {
					comment.setUsername(newComments.getUsername());
					comment.setText(newComments.getText());
					comment.setDateOfComment(newComments.getDateOfComment());
					
					 if(commentsRepository.existsByUsernameAndText(comment.getUsername(), comment.getText())) {
					    	throw new IllegalArgumentException("A similar comment already exists");   
					 }
					
					return commentsRepository.save(comment);
				}).orElseThrow(() -> new Exception("Comment not found with id " + id));
	}

	@Override
	public void deleteComment(Long id) {
		commentsRepository.deleteById(id);		
	}

	@Override
	public boolean existsById(Long id) {
		return commentsRepository.existsById(id);
	}
	
	

}
