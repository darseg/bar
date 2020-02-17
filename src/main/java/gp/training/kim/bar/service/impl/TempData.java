package gp.training.kim.bar.service.impl;

import gp.training.kim.bar.dbo.IngredientDBO;
import gp.training.kim.bar.dbo.OfferDBO;
import gp.training.kim.bar.dbo.TableDBO;

import java.util.HashMap;
import java.util.Map;

class TempData {
    final private static Map<Long, IngredientDBO> ingredients = new HashMap<>();

    final private static Map<Long, OfferDBO> beer = new HashMap<>();

    final private static Map<Long, OfferDBO> food = new HashMap<>();

    final private static Map<Long, TableDBO> tables = new HashMap<>();

    final private static Map<Long, TableDBO> visitorToTableMap = new HashMap<>();

    static Map<Long, IngredientDBO> getIngredients() {
        /*if(ingredients.isEmpty()) {
            final List<IngredientDBO> ingredientsList = new ArrayList<>();
            ingredientsList.add(IngredientDBO.builder().id(1L).costPrice(new BigDecimal("5.6")).balance(560).name("Жигули").startBalance(600).build());
            ingredientsList.add(IngredientDBO.builder().id(2L).costPrice(new BigDecimal("1")).balance(26).name("Ноги").build());
            ingredientsList.add(IngredientDBO.builder().id(3L).costPrice(new BigDecimal("0.5")).balance(1000).name("Панировка для ног").build());
            ingredientsList.add(IngredientDBO.builder().id(4L).costPrice(new BigDecimal("20")).balance(1).name("Сыр Чеддер").build());
            ingredientsList.add(IngredientDBO.builder().id(5L).costPrice(new BigDecimal("4")).balance(1).name("Old Bobby").build());

            ingredients.putAll(ingredientsList.stream().collect(Collectors.toMap(IngredientDBO::getId, o -> o)));
        }*/

        return ingredients;
    }

    static Map<Long, OfferDBO> getBeer() {
        /*if (beer.isEmpty()) {
            final List<OfferDBO> beerList = new ArrayList<>();

            beerList.add(new OfferDBO(1L,
                    "Жигули",
                    "Четкое пиво",
                    Map.of("param1", "1", "param2", "2"),
                    new BigDecimal("20"),
                    Collections.singletonList(getIngredients().get(1L))));

            beerList.add(new OfferDBO(2L,
                    "Old bobby",
                    "не такое четкое пиво",
                    Map.of("param1", "3"),
                    new BigDecimal("12"),
                    Collections.singletonList(getIngredients().get(5L))));

            beer.putAll(beerList.stream().collect(Collectors.toMap(OfferDBO::getId, o -> o)));
        }
*/
        return beer;
    }

    static Map<Long, OfferDBO> getFood() {
        /*if (food.isEmpty()) {
            final List<OfferDBO> foodList = new ArrayList<>();

            foodList.add(new OfferDBO(3L,
                    "Ноги Буша",
                    "Так себе закусь",
                    Map.of("calories", "много"),
                    new BigDecimal("10"),
                    Arrays.asList(getIngredients().get(2L), getIngredients().get(3L))));

            foodList.add(new OfferDBO(4L,
                    "Сырная нарезка",
                    "Хороша",
                    Map.of("calories", "очень много"),
                    new BigDecimal("12"),
                    Collections.singletonList(getIngredients().get(4L))));

            food.putAll(foodList.stream().collect(Collectors.toMap(OfferDBO::getId, o -> o)));
        }
*/
        return food;
    }

    /*static Map<Long, GuestDBO> getVisitors() {
        if (visitors.isEmpty()) {
            final List<GuestDBO> visitorsList = new ArrayList<>();

            final List<OfferDBO> list1 = new ArrayList<>();
            for (int i = 0; i < 14; i++) {
                list1.add(getBeer().get(1L));
            }
            list1.add(getFood().get(3L));
            visitorsList.add(new GuestDBO(3L, list1));

            final List<OfferDBO> list2 = new ArrayList<>();
            for (int i = 0; i < 15; i++) {
                list2.add(getBeer().get(1L));
            }
            list2.add(getFood().get(3L));
            visitorsList.add(new GuestDBO(4L, list2));

            final List<OfferDBO> list3 = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                list3.add(getBeer().get(1L));
                list3.add(getBeer().get(2L));
            }
            list3.add(getFood().get(3L));
            visitorsList.add(new GuestDBO(5L, list3));

            final List<OfferDBO> list4 = new ArrayList<>();
            for (int i = 0; i < 25; i++) {
                list4.add(getBeer().get(2L));
            }
            list4.add(getFood().get(3L));
            visitorsList.add(new GuestDBO(6L, list4));

            visitors.putAll(visitorsList.stream().collect(Collectors.toMap(GuestDBO::getId, o -> o)));
        }

        return visitors;
    }*/

    static Map<Long, TableDBO> getTables() {
        /*if (tables.isEmpty()) {
            final List<GuestDBO> visitorsList = new ArrayList<>();
            visitorsList.add(getVisitors().get(3L));
            visitorsList.add(getVisitors().get(4L));
            visitorsList.add(getVisitors().get(5L));
            visitorsList.add(getVisitors().get(6L));
            tables.put(2L, new TableDBO(2L, "", visitorsList));
        }*/
        return tables;
    }

    static Map<Long, TableDBO> getVisitorToTableMap() {
        getTables();
       /* if (visitorToTableMap.isEmpty()) {
            getTables().values().forEach(tableDBO -> tableDBO.getGuests().forEach(visitorDBO -> visitorToTableMap.put(visitorDBO.getGid(), tableDBO)));
        }*/
        return visitorToTableMap;
    }
}
