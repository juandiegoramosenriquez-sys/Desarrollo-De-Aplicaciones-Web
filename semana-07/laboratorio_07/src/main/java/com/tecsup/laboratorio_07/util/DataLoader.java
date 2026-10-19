package com.tecsup.laboratorio_07.util;

import com.tecsup.laboratorio_07.model.Role;
import com.tecsup.laboratorio_07.model.User;
import com.tecsup.laboratorio_07.repository.RoleRepository;
import com.tecsup.laboratorio_07.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initData(UserRepository userRepo,
                               RoleRepository roleRepo,
                               PasswordEncoder encoder) {
        return args -> {

            // 🔹 Crear roles si no existen
            Role roleUser = roleRepo.findByName("ROLE_USER")
                    .orElseGet(() -> {
                        Role r = new Role();
                        r.setName("ROLE_USER");
                        return roleRepo.save(r);
                    });

            Role roleAdmin = roleRepo.findByName("ROLE_ADMIN")
                    .orElseGet(() -> {
                        Role r = new Role();
                        r.setName("ROLE_ADMIN");
                        return roleRepo.save(r);
                    });

            Role roleManager = roleRepo.findByName("ROLE_MANAGER")
                    .orElseGet(() -> {
                        Role r = new Role();
                        r.setName("ROLE_MANAGER");
                        return roleRepo.save(r);
                    });

            // 🔹 Crear o actualizar usuario USER (si ya existe, se le cambia la contraseña)
            User user = userRepo.findByUsername("user").orElseGet(User::new);
            user.setUsername("user");
            user.setPassword(encoder.encode("user2026")); // 🔐 encriptado
            user.setRoles(Set.of(roleUser));
            userRepo.save(user);

            // 🔹 Crear o actualizar usuario ADMIN
            User admin = userRepo.findByUsername("admin").orElseGet(User::new);
            admin.setUsername("admin");
            admin.setPassword(encoder.encode("admin2026")); // 🔐 encriptado
            admin.setRoles(Set.of(roleAdmin));
            userRepo.save(admin);

            // 🔹 Crear o actualizar usuario MANAGER
            User manager = userRepo.findByUsername("manager").orElseGet(User::new);
            manager.setUsername("manager");
            manager.setPassword(encoder.encode("manager2026")); // 🔐 encriptado
            manager.setRoles(Set.of(roleManager));
            userRepo.save(manager);

            System.out.println("✔ Datos iniciales cargados correctamente");
        };
    }
}
