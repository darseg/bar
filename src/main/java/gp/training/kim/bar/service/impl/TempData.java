package gp.training.kim.bar.service.impl;

import gp.training.kim.bar.dbo.IngredientDBO;
import gp.training.kim.bar.dbo.OfferDBO;
import gp.training.kim.bar.dbo.TableDBO;
import gp.training.kim.bar.dbo.VisitorDBO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class TempData {
    final private static Map<Integer, IngredientDBO> ingredients = new HashMap<>();

    final private static Map<Integer, OfferDBO> beer = new HashMap<>();

    final private static Map<Integer, OfferDBO> food = new HashMap<>();

    final private static Map<Integer, TableDBO> tables = new HashMap<>();

    final private static Map<Integer, VisitorDBO> visitors = new HashMap<>();

    final private static Map<Integer, TableDBO> visitorToTableMap = new HashMap<>();

    static Map<Integer, IngredientDBO> getIngredients() {
        if(ingredients.isEmpty()) {
            final List<IngredientDBO> ingredientsList = new ArrayList<>();
            ingredientsList.add(IngredientDBO.builder().id(1).costPrice(new BigDecimal("5.6")).balance(560).name("Жигули").startBalance(600).build());
            ingredientsList.add(IngredientDBO.builder().id(2).costPrice(new BigDecimal("1")).balance(26).name("Ноги").build());
            ingredientsList.add(IngredientDBO.builder().id(3).costPrice(new BigDecimal("0.5")).balance(1000).name("Панировка для ног").build());
            ingredientsList.add(IngredientDBO.builder().id(4).costPrice(new BigDecimal("20")).balance(1).name("Сыр Чеддер").build());
            ingredientsList.add(IngredientDBO.builder().id(5).costPrice(new BigDecimal("4")).balance(1).name("Old Bobby").build());

            ingredients.putAll(ingredientsList.stream().collect(Collectors.toMap(IngredientDBO::getId, o -> o)));
        }

        return ingredients;
    }

    static Map<Integer, OfferDBO> getBeer() {
        if (beer.isEmpty()) {
            final List<OfferDBO> beerList = new ArrayList<>();

            beerList.add(new OfferDBO(1,
                    "Жигули",
                    "Четкое пиво",
                    Map.of("param1", "1", "param2", "2"),
                    new BigDecimal("20"),
                    Collections.singletonList(getIngredients().get(1))));

            beerList.add(new OfferDBO(2,
                    "Old bobby",
                    "не такое четкое пиво",
                    Map.of("param1", "3"),
                    new BigDecimal("12"),
                    Collections.singletonList(getIngredients().get(5))));

            beer.putAll(beerList.stream().collect(Collectors.toMap(OfferDBO::getId, o -> o)));
        }

        return beer;
    }

    static Map<Integer, OfferDBO> getFood() {
        if (food.isEmpty()) {
            final List<OfferDBO> foodList = new ArrayList<>();

            foodList.add(new OfferDBO(3,
                    "Ноги Буша",
                    "Так себе закусь",
                    Map.of("calories", "много"),
                    new BigDecimal("10"),
                    Arrays.asList(getIngredients().get(2), getIngredients().get(3))));

            foodList.add(new OfferDBO(4,
                    "Сырная нарезка",
                    "Хороша",
                    Map.of("calories", "очень много"),
                    new BigDecimal("12"),
                    Collections.singletonList(getIngredients().get(4))));

            food.putAll(foodList.stream().collect(Collectors.toMap(OfferDBO::getId, o -> o)));
        }

        return food;
    }

    static Map<Integer, VisitorDBO> getVisitors() {
        if (visitors.isEmpty()) {
            final List<VisitorDBO> visitorsList = new ArrayList<>();

            final List<OfferDBO> list1 = new ArrayList<>();
            for (int i = 0; i < 14; i++) {
                list1.add(getBeer().get(1));
            }
            list1.add(getFood().get(3));
            visitorsList.add(new VisitorDBO(3, list1));

            final List<OfferDBO> list2 = new ArrayList<>();
            for (int i = 0; i < 15; i++) {
                list2.add(getBeer().get(1));
            }
            list2.add(getFood().get(3));
            visitorsList.add(new VisitorDBO(4, list2));

            final List<OfferDBO> list3 = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                list3.add(getBeer().get(1));
                list3.add(getBeer().get(2));
            }
            list3.add(getFood().get(3));
            visitorsList.add(new VisitorDBO(5, list3));

            final List<OfferDBO> list4 = new ArrayList<>();
            for (int i = 0; i < 25; i++) {
                list4.add(getBeer().get(2));
            }
            list4.add(getFood().get(3));
            visitorsList.add(new VisitorDBO(6, list4));

            visitors.putAll(visitorsList.stream().collect(Collectors.toMap(VisitorDBO::getId, o -> o)));
        }

        return visitors;
    }

    static Map<Integer, TableDBO> getTables() {
        if (tables.isEmpty()) {
            final List<VisitorDBO> visitorsList = new ArrayList<>();
            visitorsList.add(getVisitors().get(3));
            visitorsList.add(getVisitors().get(4));
            visitorsList.add(getVisitors().get(5));
            visitorsList.add(getVisitors().get(6));
            tables.put(2, new TableDBO(2, "", visitorsList));
        }
        return tables;
    }

    static Map<Integer, TableDBO> getVisitorToTableMap() {
        getTables();
        if (visitorToTableMap.isEmpty()) {
            getTables().values().forEach(tableDBO -> tableDBO.getVisitors().forEach(visitorDBO -> visitorToTableMap.put(visitorDBO.getId(), tableDBO)));
        }
        return visitorToTableMap;
    }
}
