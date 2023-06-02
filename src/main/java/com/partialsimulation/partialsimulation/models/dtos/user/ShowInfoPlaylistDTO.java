package com.partialsimulation.partialsimulation.models.dtos.user;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowInfoPlaylistDTO {
    
    private UUID id_playlist;

    private String title;

    private String description;

    private UUID id_user;

}
