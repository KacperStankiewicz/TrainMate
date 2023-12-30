package pl.edu.pja.trainmate.core.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

//TODO: brak formatowania + nieuzywane importy zaleca sie uzywanie ctrl + alt + L, i ctrl+ alt + O bardzo często tak jak kiedys f5 w starych grach
@Getter
@Setter //todo: w encji nie uzzywamy setterów, getteery mogą być, ale tez nie na wszystkie pola, ale do tego dojdziemy pozniej jak sie domena wyklaruje
@Table(name = "user", schema = "public") //TODO: schema public niepotrzebne, brakuje sequenceGenneratora
@Entity //todo: brakuje addnotacji builder i zesttaw konstruktorów no args + all args
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //todo: tutaj zmiana na sequence i static import dodać
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "Name") //todo: nazwy z malych lepiej, tak samo id
    private String name;

}
