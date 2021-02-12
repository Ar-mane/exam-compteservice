package ma.exam;

import ma.exam.Entity.Client;
import ma.exam.Repositorys.OperationRepository;
import ma.exam.Entity.Compte;
import ma.exam.Entity.Operation;
import ma.exam.Repositorys.CompteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootApplication
@EnableFeignClients
public class ComptesServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComptesServiceApplication.class, args);
    }


    @Bean
    CommandLineRunner commandLineRunner(RepositoryRestConfiguration repositoryRestConfiguration, CompteRepository compteRepository, OperationRepository operationRepository) {

        repositoryRestConfiguration.exposeIdsFor(Client.class);
        return args -> {
            Compte compte = compteRepository.save(new Compte(null, "AKACODE1", 19.5, new Date(), "COURANT", "SUSPENDED", null, (long) 1, null));
            List<Operation> operations = new ArrayList<>();
            operations.add(operationRepository.save(new Operation(null, 20.00, "vers", compte)));
            compte.setOperations(operations);
            operations.forEach(o -> {
                operationRepository.save(o);
            });
        };
    }
}
