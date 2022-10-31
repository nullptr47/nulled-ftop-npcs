package com.github.nullptr47.ftopnpcs.util;

import br.com.kickpost.ftop.FTop;
import br.com.kickpost.ftop.configuration.ConfigurationLoader;
import br.com.kickpost.ftop.factions.FactionsManager;
import com.google.common.collect.Maps;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import org.bukkit.entity.EntityType;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Map;

public class FactionUtils {

    @Nullable public static Faction getFactionByPosition(int position) {

        Faction toReturn = null;
        Collection<Faction> factions = FTop.get().getTopFactions();
        int index = 1;

        if (position > factions.size())
            return FactionColl.get().getNone();;

        for (Faction faction : factions)
            if (index++ == position)
                toReturn = faction;

        return toReturn == null ? FactionColl.get().getNone() : toReturn;

    }

    public static double getValue(Faction faction) {

        double sum = 0.0;

        sum += FactionsManager.COINS_BY_FACTION.getOrDefault(faction, 0.0);

        for (Map.Entry<EntityType, Integer> entry :
                FactionsManager.SPAWNERS_BY_FACTION.getOrDefault(faction, Maps.newHashMap()).entrySet()) {

            sum += ConfigurationLoader.ENTITY_BY_PRICE.get(entry.getKey().name().toUpperCase()) * entry.getValue();

        }

        return sum;

    }

}
