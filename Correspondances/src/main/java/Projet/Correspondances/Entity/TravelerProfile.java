package Projet.Correspondances.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "traveler_profile")
public class TravelerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private Long profileId;

    @Column(name = "profile_name", nullable = false, length = 100)
    private String profileName;

    public TravelerProfile() {}

    public Long getProfileId() { return profileId; }
    public void setProfileId(Long profileId) { this.profileId = profileId; }

    public String getProfileName() { return profileName; }
    public void setProfileName(String profileName) { this.profileName = profileName; }
}
