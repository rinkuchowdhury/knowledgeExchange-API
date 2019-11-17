package community.innovation.ecosystem.services;

import community.innovation.ecosystem.entities.Comment;
import community.innovation.ecosystem.repositories.CommentRepository;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    // date and formatter to String
    SimpleDateFormat dateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    Boolean commentExist;
    private CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public String createOrEditComment(Comment comment) {

        String commentId= comment.getCommentId();
        comment.setPostedOn(dateTime.format(new Date()));
        if(commentId==null){
            commentRepository.save(comment);
        }
        else{
            commentExist=commentRepository.existsByCommentId(commentId);
            if(commentExist){
                commentRepository.save(comment);
                return "Successfully edited";
            }
            else return "The id is not available in the portal";
        }
        return "Successfully added";
    }

    // get: retrieve all comments
    public List<Comment> getComments() {
        return commentRepository.findAll();
    }

    // get: retrieve the comment
    public Optional<Comment> getComment(String commentId) {
        return commentRepository.findById(commentId);
    }

    public String deleteComment(String commentId) {
        commentRepository.deleteById(commentId);
        commentExist=commentRepository.existsByCommentId(commentId);
        if(!commentExist){
            return "The id: "+commentId+" related information has been deleted";
        }
        else return "The id: "+commentId+" is not deleted";
    }
}
