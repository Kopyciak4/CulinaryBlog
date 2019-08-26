package com.culinaryblog.services.recipe.comment;

import com.culinaryblog.domain.recipe.comment.Comment;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {

    void createComment(Comment comment);

    void deleteComment(int id);
}
