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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @GetMapping("/{receiverName}")
    public InteractionType getInteraction(@PathVariable String receiverName,
                                          HttpServletRequest request) {
        String username = jwtUtils.getUserNameFromJwtToken(jwtUtils.parseJwt(request));
        User sender = userRepository.findByUsername(username).get();
        User receiver = userRepository.findByUsername(receiverName).get();
        return interactionRepository.findBy(sender, receiver, LocalDate.now()).get().getInteractionType();
    }

    @GetMapping("/stats")
    public Map<InteractionType, Long> getStatistics(HttpServletRequest request) {
        String username = jwtUtils.getUserNameFromJwtToken(jwtUtils.parseJwt(request));
        User user = userRepository.findByUsername(username).get();
        return toResult(interactionRepository.getStats(user).get());
    }

    @GetMapping("stats/{username}")
    public Map<InteractionType, Long> getStatistics(@PathVariable String username) {
        User user = userRepository.findByUsername(username).get();
        return toResult(interactionRepository.getStats(user).get());
    }

    @PostMapping("/list")
    public List<Map<String, ?>> getMappedUsers(@RequestBody String userIds) {
        String[] abomination = StringUtils.commaDelimitedListToStringArray(StringUtils.split(StringUtils.split(userIds, "[")[1], "]")[0]);
        return userRepository.mapIdToUser(Arrays.stream(abomination).map(s -> (Long.valueOf(s))).collect(Collectors.toList())).stream()
                .map(o -> Map.of("username", (String) o[0], "likeRatio", o[1] != null ? (Double) o[1] : 0.0))
                .collect(Collectors.toList());
    }

    private Map<InteractionType, Long> toResult(List<Object[]> list) {
        Map<InteractionType, Long> tmp = list.stream().collect(Collectors.toMap(el -> (InteractionType) el[0], el -> (Long) el[1]));
        if (!tmp.containsKey(InteractionType.SEEN)) {
            tmp.put(InteractionType.SEEN, 0L);
        }
        if (!tmp.containsKey(InteractionType.LIKED)) {
            tmp.put(InteractionType.LIKED, 0L);
        }
        return tmp;
    }

    public static class UserIds {
        private List<Long> ids = new ArrayList<>();

        public UserIds() {
        }

        public UserIds(List<Long> ids) {
            this.ids = ids;
        }

        public List<Long> getIds() {
            return ids;
        }

        public void setIds(List<Long> ids) {
            this.ids = ids;
        }
    }
}
