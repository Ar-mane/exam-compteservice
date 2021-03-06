package ma.exam.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Compte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private double solde;
    private Date date;
    private String etat;
    private String type;

    @Transient
    private Client client;
    private Long clientId;

    @OneToMany(mappedBy = "compte")
    private List<Operation> operations;
}
