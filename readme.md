# Bar Project

## Overview

Project emulating the work of a bar.

## Сущности
Ниже перечисленный сущности в предметной области проекта и их поля.

### Table (Столик)
Столик в баре. Может быть занят на дату и время из списка bookings. Может быть приватным - обычные столики или не быть - барная стойка.

Поля:
- name, название
- capacity, вместимость
- isPrivate, приватный
- bookings, бронирования

Связи:
- bookings

### Booking (Бронирование)

Поля:
- table, столик
- from, время от
- to, время до
- visitors, список посетителей

### Admin (Администратор)
Администратор бара. Может запрашивать отчеты, подтверждать и изменять заказы поставщикам

Поля:
- ФИО

Связи:
- Нет

### Visitor (Посетитель)
Посетитель бара. Привязывается к столику. Может заказывать предложения и получить счет.

Поля:
- Список заказов

Связи:
- Привязывается к бронированию столика

### Offer (Заказ)
Заказ посетителя. Может быть 2х видов - пиво и блюдо

Поля:
- price, стоимость
- recipe, рецепт
- type, тип

Связи:
- рецепт

#### Beer (Пиво)
Пиво.

Поля:
- description, описание
- price, стоимость

Связи:
- рецепт (из одного ингредиента - собственно сорта пива)

#### Dish (Блюдо)
Блюдо.

Поля:
- description, описание
- price, стоимость

Связи:
- рецепт (из нескольких ингредиентов)

### Recipe (Рецепт)
Рецепт заказа

Поля:
- ingredients, список ингредиентов и их кол-ва
- available, доступность

Связи:
ингредиенты

### Ingredient (Ингредиент)
Ингредиент

Поля:
- count, кол-во на складе
- cost price, себестоимость
- parameters, список параметров - обороты, калорийность т.д.

## User Stories

Действия, которые можно выполнять.

### Bar-1 Бронирование столика
Посетитель еще не определен, анонимный запрос. Запрашивается кол-во мест, время бронирования (от, до) и желание сидеть за барной стойкой.
Бронирование успешно если 
- есть столик с достаточным кол-вом мест
- есть желание сидеть за барной стойкой и там достаточно свободных мест(по умолчанию его нет)

Иначе отказ.

При успешном бронировании возвращается запрошенное кол-во идентификаторов посетителей и название столика

Request:

`POST /bar/visitor/book`

```json
{
  "count": 4,
  "date": "20-02-2020",
  "from": "18:30",
  "to": "23:00",
  "barRack": true
}

```
Response:
`201 Created`
```json
{
  "table": "Столик №2",
  "visitiors": [3, 4, 5, 6]
}
```

### Bar-2 Запросить меню
Анонимно запрашивается меню. Выводится то, рецепты чего доступны и на что хватает ингредиентов

Request:
`GET /bar/visitor/menu

Response:
`200 OK`
```json
{
  "beer": [
    {
      "id": 1,
      "name": "Жигули",
      "description": "Четкое пиво",
      "params": {
        "param1": "1",
        "param2": "2"},
      "price": 20
    },
    {
      "id": 2,
      "name": "Old bobby",
      "description": "не такое четкое пиво",
      "params": {
        "param1": "3"
      },
      "price": 12
    }
  ],
  "food": [
    {
      "id": 3,
      "name": "Ноги Буша",
      "description": "Так себе закусь",
      "params": {
        "calories": "много"
      },
      "price": 2
    },
    {
      "id": 4,
      "name": "Сырная нарезка",
      "description": "Хороша",
      "params": {
        "calories": "очень много"
      },
      "price": 10
    }
  ]
}
```

### Bar-3 Как посетитель делаю заказ   
В хидере передается ID посетителя. В теле - список заказанного из меню.
Если что-либо из запрошенного недоступно - возвращается ошибка, а недоступное сохраняется для формирования заказов поставщикам.

Request:
`POST /bar/visitor/order`

`Headers: visitor=3` 
```json
[1, 3]
```

Response:
`202 Accepted`

### Bar-4 Как посетитель запрашиваю счет
В хидере передается ID посетителя. Из счета столика отнимается верувшееся

Request:
`GET /bar/visitor/check`

`Headers: visitor=3`

Response:
`200 OK`

```json
{
  "details": {
    "Жигули": {
      "price": 20,
      "count": 14,
      "sum": 280
    },
    "Ноги Буша": {
      "price": 10,
      "count": 1,
      "sum": 10
    }
  },
  "price": 290
}
```

### Bar-5 Как запрашиваю счет за столик и отдельно для одного из посетителей
В хидере передается ID посетителя, привязанного к столику.
Из общего счета отнимается заказанное отдельными посетителями

Request:
`GET /bar/visitor/tableCheck?visitors=3`

`Headers: visitor=3`

Response:
`200 OK`

```json
{
  "table": {
    "details": {
      "Old bobby": {
        "price": 12,
        "count": 30,
        "sum": 360
      },
      "Жигули": {
        "price": 20,
        "count": 20,
        "sum": 400
      },
      "Ноги Буша": {
        "price": 10,
        "count": 3,
        "sum": 30
      }
    },
    "price": 790
  },
  "visitors": {
    "3": {
      "details": {
        "Жигули": {
          "price": 20,
          "count": 14,
          "sum": 280
        },
        "Ноги Буша": {
          "price": 10,
          "count": 1,
          "sum": 10
        }
      },
      "price": 290
    }
  }
}
```

### Bar-6 Как администратор запрашиваю отчет за день
В хидере передается ID администратора. В отчете возвращается остаток ингредиентов на складе,
себестоимость потраченных и выручка за текущий день.

Request:
`GET /bar/admin/report

`Headers: admin=1`

Response:
`200 OK`

```json
{
  "storeHouse": [
    {
      "name": "Жигули",
      "balance": 560,
      "costPrice": 5.6
    },
    {
      "name": "Ноги",
      "balance": 26,
      "costPrice": 1
    },
    {
      "name": "Панировка для ног",
      "balance": 1000,
      "costPrice": 0.5
    },
    {
      "name": "Сыр Чеддер",
      "balance": 1,
      "costPrice": 20
    },
    {
      "name": "Old Bobby",
      "balance": 1,
      "costPrice": 4
    }
  ],
  "costPrice": 316.4,
  "profit": 763.6
}
```

### Bar-7 Как администратор запрашиваю рекомендованый заказ поставщикам
В хидере передается ID администратора. Возвращается отчет по ингредиентам -
- кол-во сейчас на складе,
- сколько было в начале дня,
- сколько было запрошено после того, как закончилось
- предложенный заказ на завтра

Request:
`GET /admin/order`

`Headers: admin=1`

Response:
`200 OK`

```json
[
    {
        "id": 1,
        "name": "Жигули",
        "itWas": 800,
        "left": 0,
        "unavailable": 300,
        "offering": 1050
    },
    {
        "id": 2,
        "name": "Old Bobby",
        "itWas": 800,
        "left": 200,
        "unavailable": 0,
        "offering": 550
    },
    {
        "id": 3,
        "name": "Ноги",
        "itWas": 30,
        "left": 26,
        "unavailable": 0,
        "offering": 0
    },
    {
        "id": 4,
        "name": "Панировка для ног",
        "itWas": 1100,
        "left": 1000,
        "unavailable": 0,
        "offering": 0
    },
    {
        "id": 5,
        "name": "Сыр Чеддер",
        "itWas": 1100,
        "left": 0,
        "unavailable": 10,
        "offering": 1105
    }
]
```

### Bar-8 Как администратор заполняю заказ поставщикам
В хидере передается ID администратора. Если предложение системы удовлетворяет, оно по умолчанию принимается,
если передается измененным - меняется.

Request:
`PUT /bar/admin/order`

`Headers: admin=1`

Response:
`200 OK`

```json
{
  "1": 1100
}
```

### Bar-9 Как администратор закрываю счет
В хидере передается ID администратора. Выполняется после получения оплаты. Передается id посетителя

Request:
`DELETE /bar/admin/tableCheck/3`
`DELETE /bar/admin/visitorCheck/2`

`Headers: admin=1`

Response:
`200 OK`

### Bar-10 Как администратор добавляю новоый ингредиент.
В хидере передается token администратора. 

Request:
`POST /bar/ingredients`

`Headers: token = {administrator autorize}`
```json
{
    "name": "Пиво Козёл",
    "balance": 100,
    "costPrice": 5
}
```
Response:
`201 Created`
```json
{
    "id": 1,
    "name": "Пиво Козёл",
    "balance": 100,
    "costPrice": 5
}
```

### Bar-11 Как администратор добавляю новоый столик.
В хидере передается token администратора. 

Request:
`POST /bar/tables`

`Headers: token = {administrator autorize}`
```json
{
    "id": 1,
    "name": "Столик №2",
    "description": "На втором этаже у окна",
    "images": [
        "images/maps/table2",
        "images/table2_01"
        ],
    "capacity": 5,
    "isPrivate": true
}
```

Response:
`201 Created`
```json
{
    "name": "Столик №2",
    "description": "На втором этаже у окна",
    "images": [
        "images/maps/table2",
        "images/table2_01"
        ],
    "capacity": 5,
    "isPrivate": true
}

```
### Bar-12 Как администратор добавляю новоый ингредиент.
В хидере передается token администратора. 

Request:
`POST /bar/offers`

`Headers: token = {administrator autorize}`
```json
{
    "type": "beer",
    "name": "Пиво Козёл (0.5л)",
    "description": "Пить можно, если с сухариками",
    "price": 15,
    "params": {
        "Производитель.": "SABMiller",
        "Крепость": "4%"      
        },
    "images": ["imageKozel1"],
    "ingredients": {
        "1": 0.5
        }
}
```

Response:
`201 Created`
```json
{
    "id": 1,
    "type": "beer",
    "name": "Пиво Козёл (0.5л)",
    "description": "Пить можно, если с сухариками",
    "price": 15,
    "params": {
        "Производитель.": "SABMiller",
        "Крепость": "4%"      
        },
    "images": ["imageKozel1"],
    "ingredients": {
        "1": 0.5
        }
}
```

