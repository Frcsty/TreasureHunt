package com.github.frcsty.treasurehunt.util;

import com.github.frcsty.treasurehunt.user.UserController;
import com.github.frcsty.treasurehunt.user.impl.User;

import java.util.*;
import java.util.stream.Collectors;

public final class Sort {

    public static LinkedList<User> getSortedPlayers(final UserController controller) {
        final Map<UUID, User> players = controller.getUsers();
        final Map<UUID, Integer> profiles = new HashMap<>();

        for (final UUID uuid : players.keySet()) {
            profiles.put(uuid, players.get(uuid).getPoints());
        }

        final Map<UUID, Integer> sortedMap = profiles.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

        final LinkedList<User> resultMap = new LinkedList<>();
        for (final UUID uuid : sortedMap.keySet()) {
            resultMap.add(players.get(uuid));
        }

        return resultMap;
    }

}
