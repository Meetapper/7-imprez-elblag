package imprezy.elblag.hit.controllers;

import imprezy.elblag.hit.postgres.models.FriendStatus;
import imprezy.elblag.hit.postgres.models.InteractionType;
import imprezy.elblag.hit.security.jwt.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasRole('USER')")
@RestController
@RequestMapping("/api/friends")
public class FriendsController {

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/request")
    public void friendRequest(@RequestBody String receiverId,
                              HttpServletRequest request) {
        String username = jwtUtils.getUserNameFromJwtToken(jwtUtils.parseJwt(request));
        //todo
    }

    @PostMapping("/accept")
    public void friendAccept(@RequestBody String senderId,
                             HttpServletRequest request) {
        String username = jwtUtils.getUserNameFromJwtToken(jwtUtils.parseJwt(request));
        //todo
    }

    @GetMapping("/list/{friendStatus}")
    public void friendAccept(@PathVariable FriendStatus friendStatus,
                             @RequestBody String senderId,
                             HttpServletRequest request) {
        String username = jwtUtils.getUserNameFromJwtToken(jwtUtils.parseJwt(request));
        //todo
    }
}
