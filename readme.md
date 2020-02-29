# Bar Project

## Overview

Project emulating the work of a bar.

## Сущности
Ниже перечисленный сущности в предметной области проекта и их поля.

### Table (Столик)
Столик в баре. Может быть занят на дату и время. Может быть приватным - обычные столики или не быть - барная стойка.

Поля:
- name, название
- description, описание
- capacity, вместимость
- images, список фото
- isPrivate, приватный

### Order (Бронирование)

Поля:
- table, столик
- user, пользователь
- start, время от
- end, время до
- payed, оплачен ли 

Связи:
- столики
- пользователи

### User (Польователь)
Посетитель бара. Привязывается к столику. Может заказывать предложения и получить счет.

Поля:
- login
- fio
- phone
- type (Guest, Admin)

### Offer (Заказ)
Предложение бара (пункт меню)

Поля:
- type, тип предложения (блюдо, пиво)
- name, 
- description
- price, стоимость
- params, произвольные параметры предложения 
- images
- recipe, рецепт

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
- name
- count, кол-во на складе
- cost price, себестоимость

## User Stories


Действия, которые можно выполнять.

### Bar-1 Запросить меню
Анонимно запрашивается меню. Выводится то, рецепты чего доступны и на что хватает ингредиентов

Request:
`GET /bar/offers

Response:
`200 OK`
```json
{
  "menu": {
    "food": [
      {
        "id": 3,
        "type": "food",
        "name": "Ноги Буша",
        "description": "Так себе закусь",
        "price": 10.00,
        "params": {
          "calories": "много"
        },
        "images": []
      },
      {
        "id": 4,
        "type": "food",
        "name": "Сырная нарезка",
        "description": "Хороша",
        "price": 12.00,
        "params": {
          "calories": "очень много"
        },
        "images": []
      }
    ],
    "beer": [
      {
        "id": 1,
        "type": "beer",
        "name": "Жигули",
        "description": "Четкое пиво",
        "price": 20.00,
        "params": {
          "param1": "1",
          "param2": "2"
        },
        "images": []
      },
      {
        "id": 2,
        "type": "beer",
        "name": "Old bobby",
        "description": "не такое четкое пиво",
        "price": 12.00,
        "params": {
          "param1": "3"
        },
        "images": []
      }
    ]
  }
}
```

### Bar-2 Запросить доступные столики
Анонимно. Показывает доступные столии на запрошенные время, кол-во мест и желание бронирования публичных столов

Request:

`GET /bar/tables?capacity={capacity}&start={start}&end={end}&isPrivate{isPrivate}`


Response:

`200 OK`

```json
{
  "tables": [
    {
      "id": 1,
      "name": "table 1",
      "description": "at window",
      "images": [
        "img1",
        "img2",
        "img3"
      ],
      "capacity": 4,
      "private": true
    },
    {
      "id": 2,
      "name": "table 2",
      "description": "true table",
      "images": [
        "img4",
        "img5",
        "img6",
        "img7"
      ],
      "capacity": 5,
      "private": true
    },
    {
      "id": 3,
      "name": "table 3",
      "description": "near kitchen",
      "images": [
        "img8",
        "img9",
        "img10"
      ],
      "capacity": 3,
      "private": true
    },
    {
      "id": 4,
      "name": "table 4",
      "description": "at window",
      "images": [
        "img11"
      ],
      "capacity": 3,
      "private": true
    },
    {
      "id": 5,
      "name": "bar rack",
      "description": "atata",
      "images": [],
      "capacity": 5,
      "private": false
    }
  ]
}
```

### Bar-3 Бронирование столика
Запрашивается кол-во мест, время бронирования (от, до) и желание сидеть за барной стойкой.
В запросе можно указать список логинов зарегестрированных пользователей.
Бронирование успешно если:
- есть столик с достаточным кол-вом мест
- есть желание сидеть за барной стойкой и там достаточно свободных мест(по умолчанию его нет)

При успешном бронировании создаются orders для запрашивающего, для всех пользователей из списка в запросе и для столика, если стол приватный. 
Возвращаются номера заказов, доступных пользователю - личного и общего.

Request:

`POST /bar/tables/{tableId}/book`

`Headers: Authorization: {auth}`

```json
{
  "guestsCount": 3,
  "registeredGuests": ["Dars"],
  "start": "2020-02-25T15:00",
  "end": "2020-02-25T17:00"
}
```

Response:

`201 Created`

```json
{
  "tableOrder": 3,
  "userOrder": 4
}
```

### Bar-4 получить номера доступных заказов
Возвращаются номера заказов, доступных пользователю - личного и общего.

Request:

`Headers: Authorization: {auth}`

`GET /bar/orders`


Response:

`201 Created`

```json
{
  "tableOrder": 3,
  "userOrder": 4
}
```


### Bar-5 Как посетитель добавляю предложения к заказу.

Request:
`PATCH /bar/orders/{orderId}`

`Headers: Authorization: {auth}`

```json
{
    "offers": {
        "1": 2,
        "2": 3
    }
}
```

Response:
`202 Accepted`

```json
{
    "details": {
        "Жигули": {
            "price": 20.00,
            "count": 2,
            "sum": 40.00
        },
        "Old bobby": {
            "price": 12.00,
            "count": 3,
            "sum": 36.00
        }
    },
    "price": 76.00
}
```

### Bar-6 Как посетитель запрашиваю счет
В хидере передается ID посетителя. Из счета столика отнимается верувшееся

Request:
`GET /bar/orders/{orderId}`

`Headers: Authorization: {auth}`

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

### Bar-7 Как администратор запрашиваю список неоплаченных заказов

Request:
`GET /bar/orders`

`Headers: Authorization: {adminAuth}`

Response:
`200 OK`

```json
{
    "orders": [
    {
        "table": 1,
        "user": "Dars",
        "price": 200.00
        },
    {
        "table": 1,
        "price": 400.00
        },
    {
        "table": 1,
        "user": "LadyEnvy",
        "price": 150.00
        }
    ],
    "price": 750.00
}
```

### Bar-8 Как администратор запрашиваю рекомендованый заказ поставщикам
В хидере передается ID администратора. Возвращается отчет по ингредиентам -
- кол-во сейчас на складе,
- сколько было в начале дня,
- сколько было запрошено после того, как закончилось
- предложенный заказ на завтра

Request:
`GET /bar/ingredients/order`

`Headers: Authorization: {adminAuth}`

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

### Bar-9 Как администратор заполняю заказ поставщикам
Если предложение системы удовлетворяет, оно по умолчанию принимается, если передается измененным - меняется.

Request:
`PATCH /bar/ingredients/order`

`Headers: Authorization: {adminAuth}`

```json
{
  "1": 1100
}
```

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
        "offering": 1100
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

### Bar-10 Как администратор закрываю счет

Request:
`DELETE /bar/orders/{orderId}`

`Headers: Authorization: {adminAuth}`

Response:
`200 OK`

### Bar-11 Как администратор добавляю новый ингредиент.
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

### Bar-12 Как администратор добавляю новый столик.
В хидере передается token администратора. 

Request:
`POST /bar/tables`

`Headers: token = {administrator autorize}`
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

Response:
`201 Created`
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
### Bar-13 Как администратор добавляю новое предложение.
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

