package imprezy.elblag.hit.controllers;

import imprezy.elblag.hit.postgres.models.Interaction;
import imprezy.elblag.hit.postgres.models.InteractionType;
import imprezy.elblag.hit.postgres.models.User;
import imprezy.elblag.hit.postgres.repositories.InteractionRepository;
import imprezy.elblag.hit.postgres.repositories.UserRepository;
import imprezy.elblag.hit.security.jwt.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasRole('USER')")
@RestController
@RequestMapping("/api/interact")
public class InteractionController {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InteractionRepository interactionRepository;

    @PostMapping("/{receiverName}/{interactionType}")
    public String interact(@PathVariable InteractionType interactionType,
                         @PathVariable String receiverName,
                         HttpServletRequest request) {
        String username = jwtUtils.getUserNameFromJwtToken(jwtUtils.parseJwt(request));
        User sender = userRepository.findByUsername(username).get();
        User receiver = userRepository.findByUsername(receiverName).get();

        interactionRepository.save(
                new Interaction(sender, receiver, interactionType)
        );

        return receiverName;
    }

    @GetMapping("/{userId}")
    public InteractionType getInteraction(@PathVariable String userId) {
        //todo
        return InteractionType.UNSEEN;
    }

    @GetMapping("/stats")
    public void getStatistics(HttpServletRequest request) {
        String username = jwtUtils.getUserNameFromJwtToken(jwtUtils.parseJwt(request));
        //todo
    }

    @GetMapping("stats/{userId}")
    public void getStatistics(@PathVariable String userId,
                              HttpServletRequest request) {
        //todo
    }

}
