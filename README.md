## Проект "Обмен валют"

REST API для описания валют и обменных курсов. Позволяет просматривать и редактировать списки валют и обменных курсов.

Веб-интерфейс для проекта не подразумевается.

## Технологии

- Java - коллекции, ООП
- Gradle
- Spring Boot, Spring Data JDBC
- PostgreSQL, Liquibase
- Docker
- HTTP - GET и POST запросы, коды ответа
- REST API, JSON

## Мотивация проекта

- REST API - правильное именование ресурсов, использование HTTP кодов ответа
- SQL - базовый синтаксис, создание таблиц
- Работа с Spring Data JDBC
- Изучение управления жизненным циклом контейнера БД и приложения со стороны Spring Boot

## Структура базы данных

### `Currencies`

| Колонка  | Тип     | Комментарий |
|----------|---------| --- |
| ID       | Serial  | Айди валюты, автоинкремент, первичный ключ |
| Code     | Varchar | Код валюты |
| FullName | Varchar | Полное имя валюты |
| Sign     | Varchar | Символ валюты |

Индексы:
- Первичный ключ по полю ID
- Unique индекс по полю Code для гарантий уникальности валюты в таблице, и для ускорения поиска валюты по её аббривеатуре

### `ExchangeRates`

| Колонка | Тип     | Комментарий |
| --- |---------| --- |
| ID | Serial  | Айди курса обмена, автоинкремент, первичный ключ |
| BaseCurrencyId | int     | ID базовой валюты|
| TargetCurrencyId | int     | ID целевой валюты |
| Rate | Decimal | Курс обмена единицы базовой валюты к единице целевой валюты |

Индексы:
- Первичный ключ по полю ID
- Unique индекс по паре полей BaseCurrencyId, TargetCurrencyId для гарантий уникальности валютной пары, и для ускорения поиска курса по паре валют

## REST API

Методы REST API реализуют [CRUD](https://en.wikipedia.org/wiki/Create,_read,_update_and_delete) интерфейс над базой данных - позволяют создавать (C - craete), читать (R - read), редактировать (U - update). В целях упрощения, опустим удаление (D - delete).

### Валюты

#### GET `/currencies`

Получение списка валют. Пример ответа:
```
[
    "currency": {
        "id": 0,
        "name": "US Dollar",
        "code": "USD",
        "sign": "$"
    },   
    "currency": {
        "id": 0,
        "name": "Euro",
        "code": "EUR",
        "sign": "€"
    }
]
```

HTTP коды ответов:
- Успех - 200
- Ошибка (например, база данных недоступна) - 500

#### GET `/currency/USD`

Получение конкретной валюты. Пример ответа:
```
{
    "id": 0,
    "name": "Euro",
    "code": "EUR",
    "sign": "€"
}
```

HTTP коды ответов:
- Успех - 200
- Код валюты отстствует в адресе - 400
- Валюта не найдена - 404
- Ошибка (например, база данных недоступна) - 500

#### POST `/currencies`

Добавление новой валюты в базу. Данные передаются в теле запроса в виде полей формы (`application/json`). Поля формы - `name`, `code`, `sign`. Пример ответа - JSON представление вставленной в базу записи, включая её ID:
```
{
    "id": 0,
    "name": "Euro",
    "code": "EUR",
    "sign": "€"
}
```

HTTP коды ответов:
- Успех - 200
- Отсутствует нужное поле формы - 400
- Ошибка (например, база данных недоступна) - 500

### Обменные курсы

#### GET `/exchangeRates`

Получение списка всех обменных курсов. Пример ответа:
```
[
    "id": 0,
    "exchangeRate": {
        "baseCurrency": {
            "id": 0,
            "name": "US Dollar",
            "code": "USD",
            "sign": "$"
        },
        "targetCurrency": {
            "id": 1,
            "name": "Euro",
            "code": "EUR",
            "sign": "€"
        },
        "rate": 0.99
    }
]
```

HTTP коды ответов:
- Успех - 200
- Ошибка (например, база данных недоступна) - 500

#### GET `/exchangeRate/USDRUB`

Получение конкретного обменного курса. Валютная пара задаётся идущими подряд кодами валют в адресе запроса в форме (`application/json`).
Пример ответа:
```
{
    "id": 0,
    "baseCurrency": {
        "id": 0,
        "name": "US Dollar",
        "code": "USD",
        "sign": "$"
    },
    "targetCurrency": {
        "id": 1,
        "name": "Euro",
        "code": "EUR",
        "sign": "€"
    },
    "rate": 0.99
}

```

HTTP коды ответов:
- Успех - 200
- Коды валют пары отсутствуют в адресе - 400
- Обменный курс для пары не найден - 404
- Ошибка (например, база данных недоступна) - 500

#### POST `/exchangeRates`

Добавление нового обменного курса в базу. Данные передаются в теле запроса в виде полей формы (`application/json`). Поля формы - `baseCurrencyCode`, `targetCurrencyCode`, `rate`. Пример полей формы:
- `baseCurrencyCode` - USD
- `targetCurrencyCode` - EUR
- `rate` - 0.99

Пример ответа - JSON представление вставленной в базу записи, включая её ID:
```
{
    "id": 0,
    "baseCurrency": {
        "id": 0,
        "name": "US Dollar",
        "code": "USD",
        "sign": "$"
    },
    "targetCurrency": {
        "id": 1,
        "name": "Euro",
        "code": "EUR",
        "sign": "€"
    },
    "rate": 0.99
}
```

HTTP коды ответов:
- Успех - 200
- Отсутствует нужное поле формы - 400
- Ошибка (например, база данных недоступна) - 500
