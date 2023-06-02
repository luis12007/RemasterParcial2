package com.partialsimulation.partialsimulation.models.entities;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@Entity
@ToString(exclude = {"playlists"})
@Table(name = "song", schema = "public")
public class Song {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "code")
    private UUID code;
    
    @Column(name = "title")
    private String title;
    
    @Column(name = "duration")
    private int duration;
    
  
    @OneToMany(mappedBy = "song", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<SelectedSong> playlists;
    
    public Song(String title, int duration) {
        this.title = title;
        this.duration = duration;
    }

}
