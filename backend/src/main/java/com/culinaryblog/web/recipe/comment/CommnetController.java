package com.culinaryblog.web.recipe.comment;


import com.culinaryblog.domain.recipe.comment.Comment;
import com.culinaryblog.services.recipe.comment.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/comments")
@RestController
public class CommnetController {

    private CommentService commentService;

    @Autowired
    public void setCommentService(CommentService commentService){
        this.commentService = commentService;
    }

    @PostMapping
    public void createComment(@Valid @RequestBody Comment comment) {
        commentService.createComment(comment);
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable int id) {
        commentService.deleteComment(id);
    }


}
