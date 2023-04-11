package com.lcwd.electroinic.store;

import com.lcwd.electroinic.store.dtos.UserDTO;
import com.lcwd.electroinic.store.entities.Role;
import com.lcwd.electroinic.store.repositories.RoleRepository;
import com.lcwd.electroinic.store.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;
import java.util.UUID;

@SpringBootApplication
@EnableWebMvc

public class ElectronicStoreApplication implements CommandLineRunner {
	@Autowired
	private RoleRepository roleRepository;
	Logger logger= LoggerFactory.getLogger(ElectronicStoreApplication.class);
	@Value("${admin.role.id}")
	private String role_admin_id;
	@Value("${normal.role.id}")
	private String role_normal_id;
	public static void main(String[] args) {
		SpringApplication.run(ElectronicStoreApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		try
		{

			Role role_admin = Role.builder().roleId(this.role_admin_id).roleName("ROLE_ADMIN").build();
			Role role_normal = Role.builder().roleId(this.role_normal_id).roleName("ROLE_NORMAL").build();
this.roleRepository.save(role_admin);
this.roleRepository.save(role_normal);
		}catch(Exception e)
		{
			e.printStackTrace();
		}

	}
}
