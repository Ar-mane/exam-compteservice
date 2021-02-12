package ma.exam.Service;

import ma.exam.Repositorys.OperationRepository;
import ma.exam.Entity.Compte;
import ma.exam.Entity.Operation;
import ma.exam.Repositorys.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ICompteServiceImp implements ICompetService{

    @Autowired
    CompteRepository compteRepository;

    @Autowired
    OperationRepository operationRepository;

    @Autowired
    ClientServiceClient clientServiceClient;


    @Override
    public Compte ajouterCompte(Compte c) {
        return compteRepository.save(c);
    }

    @Override
    public Operation effectuerVeremant(Long id, double montant) {
        Compte c=compteRepository.getOne(id);
        Operation o=new Operation();
        o.setCompte(c);
        o.setMontant(montant);
        o.setType("virement");
        c.setSold(c.getSold()+montant);
        compteRepository.save(c);
        return operationRepository.save(o);
    }

    @Override
    public Operation effectuerretirer(Long id, double montant) {
        Compte c=compteRepository.getOne(id);
        Operation o=new Operation();
        o.setCompte(c);
        o.setMontant(montant);
        o.setType("retirer");
        c.setSold(c.getSold()-montant);
        compteRepository.save(c);
        return operationRepository.save(o);
    }

    @Override
    @Transactional
    public void verment(Long id1, Long id2 ,double montant) {
        effectuerretirer(id1,montant);
        effectuerVeremant(id2,montant);
    }

    @Override
    public Compte getCompta(Long id) {
        Compte c=compteRepository.getOne(id);
        c.setClient(clientServiceClient.findClientById(c.getClientId()));
        return c;
    }

    @Override
    public Compte activerCompte(Long id) {
        Compte c=compteRepository.getOne(id);
        c.setEtat("Activer");
        compteRepository.save(c);
        return c;
    }

    @Override
    public Compte suspendreCompte(Long id) {
        Compte c=compteRepository.getOne(id);
        c.setEtat("suspendre");
        compteRepository.save(c);
        return c;
    }

    @Override
    public List<Operation> getOperation(Long id) {
        Compte c=compteRepository.getOne(id);
        return c.getOperations();
    }
}
