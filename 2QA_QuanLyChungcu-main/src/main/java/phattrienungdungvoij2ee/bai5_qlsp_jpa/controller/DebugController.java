package phattrienungdungvoij2ee.bai5_qlsp_jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import phattrienungdungvoij2ee.bai5_qlsp_jpa.model.Account;
import phattrienungdungvoij2ee.bai5_qlsp_jpa.repository.AccountRepository;
import phattrienungdungvoij2ee.bai5_qlsp_jpa.repository.RoleRepository;

import java.util.Optional;

@Controller
public class DebugController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Truy cap: http://localhost:8080/debug?username=xxx&password=yyy
    @GetMapping("/debug")
    public String debug(
            @RequestParam(value = "username", defaultValue = "") String username,
            @RequestParam(value = "password", defaultValue = "") String password,
            Model model
    ) {
        StringBuilder result = new StringBuilder();
        result.append("<h2>DEBUG REPORT</h2>");

        // 1. Kiem tra tat ca roles trong DB
        result.append("<h3>1. Roles in DB:</h3><ul>");
        try {
            roleRepository.findAll().forEach(r ->
                    result.append("<li>id=").append(r.getId())
                            .append(" | name=[").append(r.getName()).append("]</li>")
            );
        } catch (Exception e) {
            result.append("<li>ERROR: ").append(e.getMessage()).append("</li>");
        }
        result.append("</ul>");

        // 2. Kiem tra account theo username
        if (!username.isEmpty()) {
            result.append("<h3>2. Account lookup for username=[").append(username).append("]:</h3>");
            try {
                Optional<Account> acc = accountRepository.findByLoginName(username);
                if (acc.isPresent()) {
                    Account a = acc.get();
                    result.append("<p>Found! login_name=[").append(a.getLogin_name()).append("]</p>");
                    result.append("<p>Password in DB=[").append(a.getPassword()).append("]</p>");
                    result.append("<p>Roles: <ul>");
                    a.getRoles().forEach(r -> result.append("<li>").append(r.getName()).append("</li>"));
                    result.append("</ul></p>");

                    // 3. Kiem tra password match
                    if (!password.isEmpty()) {
                        boolean match = passwordEncoder.matches(password, a.getPassword());
                        result.append("<h3>3. Password check for input=[").append(password).append("]:</h3>");
                        result.append("<p style='color:").append(match ? "green" : "red").append("'>")
                                .append(match ? "PASSWORD MATCH OK" : "PASSWORD DOES NOT MATCH - This is why login fails!")
                                .append("</p>");

                        // Neu khong match, thu encode de xem
                        if (!match) {
                            String encoded = passwordEncoder.encode(password);
                            result.append("<p>Encoded version of your input: ").append(encoded).append("</p>");
                            result.append("<p>Stored in DB: ").append(a.getPassword()).append("</p>");
                            boolean isHashed = a.getPassword().startsWith("$2a$") || a.getPassword().startsWith("$2b$");
                            result.append("<p>Password in DB is BCrypt hashed: <b>").append(isHashed).append("</b></p>");
                            if (!isHashed) {
                                result.append("<p style='color:red'><b>PROBLEM: Password in DB is plain text, not BCrypt! That is why login fails.</b></p>");
                            }
                        }
                    }
                } else {
                    result.append("<p style='color:red'>Account NOT FOUND for username=[").append(username).append("]</p>");
                }
            } catch (Exception e) {
                result.append("<p style='color:red'>ERROR: ").append(e.getMessage()).append("</p>");
            }
        } else {
            result.append("<h3>2. Pass ?username=xxx&password=yyy in URL to check a specific account</h3>");
        }

        model.addAttribute("debugResult", result.toString());
        return "debug";
    }
}