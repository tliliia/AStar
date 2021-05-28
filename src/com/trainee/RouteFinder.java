package com.trainee;

import java.math.BigDecimal;
import java.util.*;

public interface RouteFinder {
    /**
     * Поиск кратчайшего маршрута между двумя точками
     * @param map карта
     * @return карта с построенным маршрутом
     */
    char[][] findRoute(char[][] map);
}
