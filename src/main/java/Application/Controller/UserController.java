package Application.Controller;

import Application.Entity.User;
import Application.Interface.UserRepository;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;
//    private final String SECRET_KEY = "u3SND7mP0WfNDqhIy1QdUs8lkU3gSBtrA+5LJpPf3l4bQzw/XwECAw==";
//    private final long EXPIRATION_TIME = 30 * 60 * 1000; // 30 minut w milisekundach
    private JWTTokenManager jwtTokenUtil = new JWTTokenManager();

    @PostMapping("/login")
    public String login(@RequestBody User loginRequest) {
        String password = loginRequest.getPassword();
        password = hashPassword(password);
        System.out.println(password);
        String email = loginRequest.getEmail();
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password) || !EmailValidator.getInstance().isValid(email)) {
            String token = jwtTokenUtil.generateToken(email);
            return token;
        } else {
            return "Błędny e-mail lub hasło.";
        }
    }

    @PostMapping("/register")
    public String register(@RequestBody User registrationRequest) {
        System.out.println("Register");
        String email = registrationRequest.getEmail();
        String password = registrationRequest.getPassword();
        password = hashPassword(password);
        String name = registrationRequest.getName();

        if (userRepository.findByEmail(email) != null || !EmailValidator.getInstance().isValid(email)) {
            System.out.println(email);
            System.out.println(EmailValidator.getInstance().isValid(email));
            return "Użytkownik o podanym e-mailu już istnieje lub e-mail jest błędny.";
        } else {
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            user.setName(name);
            userRepository.save(user);
            return "Zarejestrowano pomyślnie!";
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyToken(@RequestBody Map<String, String> request) {
        String token = request.get("token");

        if (token != null && !token.isEmpty()) {
            if (jwtTokenUtil.validateToken(token)) {
                jwtTokenUtil.getUsernameFromToken(token);
//                System.out.println(jwtTokenUtil.getUsernameFromToken(token));
                return ResponseEntity.ok("Token jest ważny i poprawny.");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token jest nieprawidłowy lub wygasł.");
            }
        }

        return ResponseEntity.badRequest().body("Nieprawidłowy token.");
    }




    private String hashPassword(String password) {
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        password = passwordEncoder.encode(password);
        return password;
    }
}
