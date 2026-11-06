package Projet.Correspondances.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "travel_class")
public class TravelClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_id")
    private Long classId;

    @Column(name = "class_name", nullable = false, length = 50)
    private String className;

    @Column(name = "description", length = 255)
    private String description;

    public TravelClass() {}

    public Long getClassId() { return classId; }
    public void setClassId(Long classId) { this.classId = classId; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
