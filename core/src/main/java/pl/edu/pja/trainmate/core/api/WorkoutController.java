package pl.edu.pja.trainmate.core.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pja.trainmate.core.annotation.HasRole;

import static pl.edu.pja.trainmate.core.config.security.RoleType.PERSONAL_TRAINER;

@RequiredArgsConstructor
@RestController
@HasRole(roleType = PERSONAL_TRAINER)
@RequestMapping("/workout")
public class WorkoutController {

}
