package org.ps.reconciliation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReconciliationApplication {

//	@Autowired
//	private UserRepository userRepository;
//	@PostConstruct
//	public void initUsers() {
//		List<User> users = Stream.of(
//				new User(101, "javatechie", "password"),
//				new User(102, "user1", "pwd1"),
//				new User(103, "user2", "pwd2"),
//				new User(104, "user3", "pwd3")
//		).collect(Collectors.toList());
//		userRepository.saveAll(users);
//	}

    public static void main(String[] args) {
        SpringApplication.run(ReconciliationApplication.class, args);
    }

}
