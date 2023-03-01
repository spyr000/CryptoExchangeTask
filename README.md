# CryptoExchangeTask

Созданное приложение является криптовалютной биржей, которая может хранить такие данные, как:
> Пользователи</br>
> Электронные кошельки с валютой</br>
> Валюта</br>
> Курсы валют</br>
> Информация о проведенных транзакциях</br>

**Выполненные доп. задания:**

Подключена PostgreSQL </br>
Реализована безопасность с помощью Spring Security, добавлена авторизация и аутентификация</br>

После регистрации с указанием имени пользователя и почты (имя пользователя и почта должны быть уникальными) создается секретный ключ, который является идентификатором кошелька и кодом для аутентификации в сети.
Также система создает уникальный jwt-токен при каждой аутентификации, для разграничения доступа на Администратора (ADMIN) и Пользователя (USER). Кроме того, токен имеет срок годности, по истечении которого, для использования большинства функций приложения требуется пройти повторную аутентификацию. Без jwt-токена возможна только регистрация пользователя и его аутентификация.

## Реализованные запросы

### 1.Регистрация

http://localhost:8080/api/v1/auth/register

body:
```
{
    "username" : "userrrrnew",
    "email" : "userrrr@user.u",
    "password" : "password"
}
```
response:
```
{
    "secretKey": "1148ebf5a67804c039a4169d2e3f1e3ef408e1e9410652560444cce2ffa63666"
}
```
секретный ключ генерируется с помощью SHA3-256 алгоритма.
если имя пользователя уже имеется в бд, пользователь не создастся.

### Вход

http://localhost:8080/api/v1/auth/authenticate

body:
```
{
    "username" : "dogg",
    "password" : "dg"
}
```
response:
```
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkb2dnIiwiaWF0IjoxNjc3NzEwMjE5LCJleHAiOjE2NzgzMTUwMTl9.aN-7m87-sB3fwkymvju8QItABM2torqBI8_FW7P8Ztk"
}
```
при входе генерируется jwt-токен из имени пользователя. Без токена функции приложения будут не доступны

### Создание счета

http://localhost:8080/api/v1/wallets

для использования необходимо указать header, содержащий в себе jwt-токен
пример: **Authorization:Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkb2dnZyIsImlhdCI6MTY3NzY5NDY0MCwiZXhwIjoxNjc3Njk2MDgwfQ.hoZG-QD_JBgZvsC4kGc_CJd1wmJCI3grov2bLgfYceI**

body:

```
{
    "username" : "admin",
    "currency_name" : "BTTC"
}
```

response:
```
{
    "secret_key": "fb001dfcffd1c899f3297871406242f097aecf1a5342ccf3ebcd116146188e4b"
}
```

### Пополнение счета

http://localhost:8080/api/v1/wallets/deposit

для использования необходимо указать header, содержащий в себе jwt-токен
пример: **Authorization:Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkb2dnZyIsImlhdCI6MTY3NzY5NDY0MCwiZXhwIjoxNjc3Njk2MDgwfQ.hoZG-QD_JBgZvsC4kGc_CJd1wmJCI3grov2bLgfYceI**

body:

```
{
	"secret_key": "fb001dfcffd1c899f3297871406242f097aecf1a5342ccf3ebcd116146188e4b",
	"RUB_wallet": "10000"
}
```

response:

```
{
    "RUB_wallet": 49992.0
}
```

### Снятие денег

http://localhost:8080/api/v1/wallets/withdraw

для использования необходимо указать header, содержащий в себе jwt-токен
пример: **Authorization:Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkb2dnZyIsImlhdCI6MTY3NzY5NDY0MCwiZXhwIjoxNjc3Njk2MDgwfQ.hoZG-QD_JBgZvsC4kGc_CJd1wmJCI3grov2bLgfYceI**

body:
```
{
	"secret_key": "fb001dfcffd1c899f3297871406242f097aecf1a5342ccf3ebcd116146188e4b",
	"currency": "RUB",
	"count": "2",
	"credit_card": "1234 5678 9012 3456"
}
```

response:

```
{
    "RUB_wallet": 49990.0
}
```

### Количество операций за определенное время

http://localhost:8080/api/v1/admin/transactions/count

для использования необходимо указать header, содержащий в себе jwt-токен
пример: **Authorization:Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkb2dnZyIsImlhdCI6MTY3NzY5NDY0MCwiZXhwIjoxNjc3Njk2MDgwfQ.hoZG-QD_JBgZvsC4kGc_CJd1wmJCI3grov2bLgfYceI**

body:
```
{
	"date_from": "01-03-2023 23:00:00",
	"date_to": "03-03-2023 00:00:00"
}
```

response:

```
{
    "transaction_count": 6
}
```

### Перевод в другую валюту

http://localhost:8080/wallet/exchange

для использования необходимо указать header, содержащий в себе jwt-токен
пример:
**Authorization:Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkb2dnZyIsImlhdCI6MTY3NzY5NDY0MCwiZXhwIjoxNjc3Njk2MDgwfQ.hoZG-QD_JBgZvsC4kGc_CJd1wmJCI3grov2bLgfYceI**

body:

```
{
    "secret_key" : "fb001dfcffd1c899f3297871406242f097aecf1a5342ccf3ebcd116146188e4b",
    "currency_from" : "RUB",
    "currency_to" : "ETH",
    "amount" :  1000
}
```

response:

```
{
    "currency_from": "RUB",
    "currency_to": "ETH",
    "amount_from": 49992.0,
    "amount_to": 20000.0
}
```

### Получение балансов всех кошельков пользователя

http://localhost:8080/api/v1/wallets/admin/balances

для использования необходимо указать header, содержащий в себе jwt-токен
пример:
**Authorization:Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkb2dnZyIsImlhdCI6MTY3NzY5NDY0MCwiZXhwIjoxNjc3Njk2MDgwfQ.hoZG-QD_JBgZvsC4kGc_CJd1wmJCI3grov2bLgfYceI**

body:

```
{
    "secret_key" : "fb001dfcffd1c899f3297871406242f097aecf1a5342ccf3ebcd116146188e4b"
}
```

response:

```
{
    "ETH_wallet": 0.0,
    "BTTC_wallet": 0.0,
    "RUB_wallet": 39992.0
}
```


### Получение баланса одного кошелька пользователя

http://localhost:8080/api/v1/wallets/balance

для использования необходимо указать header, содержащий в себе jwt-токен
пример:
**Authorization:Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkb2dnZyIsImlhdCI6MTY3NzY5NDY0MCwiZXhwIjoxNjc3Njk2MDgwfQ.hoZG-QD_JBgZvsC4kGc_CJd1wmJCI3grov2bLgfYceI**

body:

```
{
    "secret_key" : "fb001dfcffd1c899f3297871406242f097aecf1a5342ccf3ebcd116146188e4b",
    "currency_name" : "ETH"
}
```

response:

```
{
    "ETH_wallet": 0.0
}
```

### Смена курса валют

http://localhost:8080/api/v1/exchange-rates/admin/change

для использования необходимо указать header, содержащий в себе jwt-токен
пример:
**Authorization:Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkb2dnZyIsImlhdCI6MTY3NzY5NDY0MCwiZXhwIjoxNjc3Njk2MDgwfQ.hoZG-QD_JBgZvsC4kGc_CJd1wmJCI3grov2bLgfYceI**

body:

```
{
	"base_currency": "USDT",
	"ETH": "0.000096",
	"RUB": "184"
}
```

response:

```
{
    "ETH": 9.6E-5,
    "RUB": 184.0
}
```

### Получение курсов валют

http://localhost:8080/api/v1/exchange-rates/rates

для использования необходимо указать header, содержащий в себе jwt-токен
пример:
**Authorization:Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkb2dnZyIsImlhdCI6MTY3NzY5NDY0MCwiZXhwIjoxNjc3Njk2MDgwfQ.hoZG-QD_JBgZvsC4kGc_CJd1wmJCI3grov2bLgfYceI**

body:

```
{
     "input_currency_name" : "RUB"
}
```

response:

```
{
    "ETH": 0.05,
    "USDT": 0.005434782608695652,
    "BTTC": 0.005405405405405406
}
```

### Получение курса двух валют

http://localhost:8080/api/v1/exchange-rates/rate

для использования необходимо указать header, содержащий в себе jwt-токен
пример:
**Authorization:Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkb2dnZyIsImlhdCI6MTY3NzY5NDY0MCwiZXhwIjoxNjc3Njk2MDgwfQ.hoZG-QD_JBgZvsC4kGc_CJd1wmJCI3grov2bLgfYceI**

body:

```
{
    "input_currency_name" : "RUB",
    "output_currency_name" : "BTTC"
}
```

response:

```
{
    "currency_coefficient": 0.005405405405405406
}
```

### Добавление нового курса валют

http://localhost:8080/api/v1/exchange-rates/admin

для использования необходимо указать header, содержащий в себе jwt-токен
пример:
**Authorization:Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkb2dnZyIsImlhdCI6MTY3NzY5NDY0MCwiZXhwIjoxNjc3Njk2MDgwfQ.hoZG-QD_JBgZvsC4kGc_CJd1wmJCI3grov2bLgfYceI**

body:

```
{
    "input_currency_name" : "BTTC",
    "output_currency_name" : "RUB",
    "output_cost" : 185 
}
```

response:

```
{
    "exchange_rate_id": 654
}
```

