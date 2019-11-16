package community.innovation.ecosystem.services;

import community.innovation.ecosystem.repositories.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CommentService {

    private CommentRepository commentRepository;
}
