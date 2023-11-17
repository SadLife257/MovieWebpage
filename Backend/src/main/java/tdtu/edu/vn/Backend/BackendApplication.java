package tdtu.edu.vn.Backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import tdtu.edu.vn.Backend.Models.UsersModel;

import tdtu.edu.vn.Backend.Repositories.UsersRepo;

@SpringBootApplication
public class BackendApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}
	
	
    @Override
    public void run(String... args) throws Exception {
    }
}
