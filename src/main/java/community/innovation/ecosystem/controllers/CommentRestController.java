package community.innovation.ecosystem.controllers;


import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@Api(value = "Comment", description = "Available endpoints of this API", tags = {"Comment"})
@RequestMapping("/api")
public class CommentRestController {

}
