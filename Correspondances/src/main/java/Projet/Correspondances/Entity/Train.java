package Projet.Correspondances.Entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "train")
public class Train {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "train_id")
    private Long trainId;

    @OneToMany(mappedBy = "train")
    private List<Circulation> circulations = new ArrayList<>();

    @OneToMany(mappedBy = "train")
    private List<Coach> coaches = new ArrayList<>();

    public Train() {}

    public Long getTrainId() { return trainId; }
    public void setTrainId(Long trainId) { this.trainId = trainId; }

    public List<Circulation> getCirculations() { return circulations; }
    public void setCirculations(List<Circulation> circulations) { this.circulations = circulations; }

    public List<Coach> getCoaches() { return coaches; }
    public void setCoaches(List<Coach> coaches) { this.coaches = coaches; }
}
