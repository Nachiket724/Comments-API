package com.comments.api.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import javax.validation.constraints.Positive;

import com.comments.api.model.Comments;
import com.comments.api.service.interfaces.CommentsService;

@RestController
@RequestMapping("/api/v2/comments")
public class CommentsController {

	@Autowired
	private CommentsService commentsService;
	
	@GetMapping
	public ResponseEntity<List<Comments>> getAllComments(){
		List<Comments> comments = commentsService.getAllComments();
		if(comments.isEmpty()) {
			return ResponseEntity.noContent().build();
		}else {
			return ResponseEntity.ok(comments);
		}
	}
	
	@GetMapping("/search")
	public ResponseEntity<?> searchComments(
			@RequestParam(required = false) String username,
			@RequestParam(required = false) LocalDate date){
		
		if(username!=null) {
			List<Comments> comments = commentsService.getCommentsByUsername(username);
			if(comments.isEmpty()) {
				return ResponseEntity.noContent().build();
			}else {
				return ResponseEntity.ok(comments);
			}
			
		}else if (date!=null) {
			List<Comments> comments = commentsService.getCommentsByDate(date);
			if(comments.isEmpty()) {
				return ResponseEntity.noContent().build();
			}else {
				return ResponseEntity.ok(comments);
			}
		}else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Bad Request: Please provide either a username or a date to search for comments.");
		}
	}
	
	@PostMapping
	public ResponseEntity<?> createComment(@Valid @RequestBody Comments comments){ 
		try {
			Comments savedComments = commentsService.saveComment(comments);
			Map<String, Object> responseBodyMap = new HashMap<>();
			responseBodyMap.put("message", "Comment created successfully");
			responseBodyMap.put("comment", savedComments);
			return new ResponseEntity<>(responseBodyMap, HttpStatus.CREATED);
		} catch (Exception e) {
			Map<String, Object> errorMap = new HashMap<>();
			errorMap.put("message", "Failed to create comment");
			errorMap.put("error", e.getMessage());
			return new ResponseEntity<>(errorMap, HttpStatus.ALREADY_REPORTED);
		}
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateComment(@PathVariable("id") @Positive(message = "ID must be a positive number") Long id,@Valid @RequestBody Comments comments){
		try {
			return ResponseEntity.ok(commentsService.updateComment(id, comments));
		} catch (Exception e) {
			Map<String, Object> errorMap = new HashMap<>();
			errorMap.put("message", "Failed to update comment");
			errorMap.put("error", e.getMessage());
			return new ResponseEntity<>(errorMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, String>> deleteComment(@PathVariable("id") @Positive(message = "ID must be a positive number") Long id){
		
		Map<String, String> responseMap = new HashMap<>();
		
		if(!commentsService.existsById(id)) {
			responseMap.put("message", "Cannot delete comment with id "+id+" because it does not exist");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMap);
		}
		
		try {
			commentsService.deleteComment(id);
			responseMap.put("message", "Comment deleted successfully");
			return ResponseEntity.ok(responseMap);
		} catch (Exception e) {
			responseMap.put("message", "An error occurred while deleting the comment");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMap);
		}
		
	}
}
