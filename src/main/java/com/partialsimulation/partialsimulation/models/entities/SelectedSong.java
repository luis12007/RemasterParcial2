package com.partialsimulation.partialsimulation.models.entities;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "songxplaylist", schema = "public")
public class SelectedSong {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "code")
    private UUID code;

    @Column(name = "date_added")
    private Date date_added;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_song_code", nullable = true)
    private Song song;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_playlist_code", nullable = true)
    private PlayList playlist;

    public SelectedSong(Date date_added, Song song, PlayList playlist) {
        this.date_added = date_added;
        this.song = song;
        this.playlist = playlist;
    }

    public SelectedSong(Song song, PlayList playlist) {
        this.song = song;
        this.playlist = playlist;
    }

}
