package pl.edu.pja.trainmate.core.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/dupa")
    public String dupa() {
        return "DUPSKO";
    }
}
