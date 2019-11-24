package community.innovation.ecosystem.repositories;

import community.innovation.ecosystem.entities.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {
    Boolean existsByCommentId(String commentId);
    Comment findByComment(String comment);
    Comment findByCommentId(String commentId);
}
