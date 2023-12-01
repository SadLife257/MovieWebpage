package tdtu.edu.vn.Backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import tdtu.edu.vn.Backend.Models.UsersModel;

import tdtu.edu.vn.Backend.Repositories.UsersRepo;

@SpringBootApplication
public class BackendApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}
	
	@Autowired
    UsersRepo userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    //Initialize admin account value
    @Value("${user.default.avatar}")
    private String defaultAvatar;
    @Value("${admin.username}")
    private String adminUsername;
    @Value("${admin.password}")
    private String adminPassword;
    @Value("${admin.name}")
    private String adminName;
    @Value("${admin.email}")
    private String adminEmail;

    //Create admin account
    @Override
    public void run(String... args) throws Exception {
        if(userRepository.count() == 0){
            UsersModel user = new UsersModel();
            user.setUsername(adminUsername);
            user.setName(adminName);
            user.setEmail(adminEmail);
            user.setAvatar(defaultAvatar);
            user.setRoles("ROLE_USER,ROLE_ADMIN");
            user.setPassword(passwordEncoder.encode(adminPassword));
            userRepository.save(user);
            System.out.println(user);
        }
    }
}
