package com.github.nullptr47.ftopnpcs.model;

import com.github.nullptr47.holograms.Hologram;
import lombok.Builder;
import lombok.Data;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;

@Builder
@Data
public class RankingNPC {

    private ArmorStand entity;
    private Hologram hologram;
    private int position;

}
