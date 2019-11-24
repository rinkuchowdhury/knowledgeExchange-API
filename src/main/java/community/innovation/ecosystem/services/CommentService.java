package community.innovation.ecosystem.services;

import community.innovation.ecosystem.entities.Comment;
import community.innovation.ecosystem.entities.Knowledge;
import community.innovation.ecosystem.repositories.CommentRepository;
import community.innovation.ecosystem.repositories.KnowledgeRepository;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CommentService {

    // date and formatter to String
    private SimpleDateFormat dateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    private Boolean commentExist;
    private CommentRepository commentRepository;
    private KnowledgeRepository knowledgeRepository;

    public CommentService(CommentRepository commentRepository, KnowledgeRepository knowledgeRepository ) {
        this.commentRepository = commentRepository;
        this.knowledgeRepository = knowledgeRepository;
    }

    public String createOrEditComment(Comment comment) {

        String status;
        String commentId= comment.getCommentId();
        String knowledgeId= comment.getKnowledgeId();
        Boolean knowledgeIdExist=knowledgeRepository.existsById(knowledgeId);
        Knowledge knowledge;
        if(!knowledgeIdExist) status = "Error: The given knowledgeId does not exist";
        else{
            comment.setPostedOn(dateTime.format(new Date()));
            if(commentId!=null){
                commentExist=commentRepository.existsByCommentId(commentId);
                if(commentExist){
                    commentRepository.save(comment);
                    status= "Successfully edited";
                }
                else status= "The id is not available in the portal";
            }
            else{
                commentRepository.save(comment);
                commentId=commentRepository.findByComment(comment.getComment()).getCommentId();
                status= "Successfully added";
            }
            knowledge= knowledgeRepository.findByKnowledgeId(knowledgeId);
            Map<String,String> addComment=knowledge.getComment();
            addComment.put(commentId,comment.getComment());
            knowledge.setComment(addComment);
            knowledgeRepository.save(knowledge);
            status= status+ " and also inserted into Knowledge";
        }
        return status;
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
        commentExist=commentRepository.existsByCommentId(commentId);
        if(!commentExist){
            return "The id: "+commentId+" does not exist";
        }
        else{
            String knowledgeId= commentRepository.findByCommentId(commentId).getKnowledgeId();

            // delete comment from knowledge document
            Knowledge knowledge=knowledgeRepository.findByKnowledgeId(knowledgeId);
            knowledge.getComment().remove(commentId);
            knowledgeRepository.save(knowledge);

            // delete from comment document
            commentRepository.deleteById(commentId);
            return "The id: "+commentId+" related information has been deleted";
        }
    }
}
