package community.innovation.ecosystem.controllers;


import community.innovation.ecosystem.entities.Comment;
import community.innovation.ecosystem.services.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@Api(value = "Comment", description = "Available endpoints of this API", tags = {"Comment"})
@RequestMapping("/api")
public class CommentRestController {

    private CommentService commentService;

    public CommentRestController(CommentService commentService) {
        this.commentService = commentService;
    }

    @ApiOperation(value="create or edit comment", tags = { "Comment" })
    @PostMapping("/comments")
    public String createComment(@Valid @RequestBody Comment comment){
        return commentService.createOrEditComment(comment);
    }

    @ApiOperation(value="Get all comments", tags = { "Comment" })
    @GetMapping("/comments")
    public List<Comment> getComments(){
        return commentService.getComments();
    }

    @ApiOperation(value="Get comment", tags = { "Comment" })
    @GetMapping("/comments/{id}")
    public Optional<Comment> getComment(@PathVariable("id") String commentId){
        return commentService.getComment(commentId);
    }

    @ApiOperation(value="Delete comment", tags = { "Comment" })
    @DeleteMapping("/comments/{id}")
    public String deleteInstitute(@PathVariable("id") String commentId){
        return commentService.deleteComment(commentId);
    }

}
