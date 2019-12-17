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
        String commentId=comment.getCommentId();
        Boolean knowledgeIdExist=knowledgeRepository.existsById(comment.getKnowledgeId());
        if(!knowledgeIdExist) status = "Error: The given knowledgeId does not exist";
        else {
            comment.setPostedOn(dateTime.format(new Date()));

            // create comment
            if (commentId == null || commentId.isEmpty()) {
                Comment comment1 = commentRepository.save(comment);

                // add comment into knowledge
                addCommentInKnowledge(comment.getKnowledgeId(), comment1.getCommentId(), comment1.getComment());

                status = "Successfully added id: " + comment1.getCommentId();
            }

            // edit comment
            else {
                commentRepository.save(comment);

                // add comment into knowledge
                addCommentInKnowledge(comment.getKnowledgeId(), comment.getCommentId(), comment.getComment());

                status = "Successfully edited id: " + commentId;
            }
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

    public void addCommentInKnowledge(String knowledgeId,String commentId,String comment){
        Map<String,String> addComment;
        Knowledge knowledge=knowledgeRepository.findByKnowledgeId(knowledgeId);
        if(knowledge.getComment()==null){
            addComment = new HashMap<>();
        }
        else{
            addComment=knowledge.getComment();
        }
        addComment.put(commentId,comment);
        knowledge.setComment(addComment);
        knowledgeRepository.save(knowledge);
    }
}
