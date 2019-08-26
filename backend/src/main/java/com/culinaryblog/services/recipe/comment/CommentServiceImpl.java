package com.culinaryblog.services.recipe.comment;

import com.culinaryblog.DAO.recipe.comment.CommentRepository;
import com.culinaryblog.domain.recipe.comment.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public void createComment(Comment comment){
        comment.setDateOfComment(new Date());
        commentRepository.save(comment);
    }

    @Override
    public void deleteComment(int id){
        commentRepository.deleteById(id);
    }


}
