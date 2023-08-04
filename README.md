# MarketsSpy

### Project description:
This is a simple application that helps me determine in which of the three supermarkets near my home is cheaper to buy certain products or stuff.
I wrote it after studying `Java Core`, `JDBC` and `SQL` in online courses at Mate Academy.

This is far from the final version, because I already have knowledge in `Spring` and `Hibernate`, which I plan to implement as well.

### Features:

- creating a shopping list in `.csv` format in the next form:

  | `product name` | `weight/volume/quantity` | `actual price` | `old price (if exist)`| `Supermarket` |

- the promotional price and availability of the product in the store are taken into account.
- there is an opportunity to round the price so as not to fall on the marketer's hook

### Project Overview:
I have created my own database with typical products. This database is constantly updated.
To search for products, enter their name in "productsToBuy" in the Main class:
```java
List<String> productsToBuy = List.of(
        "enter your shopping List",
        "each product separately"
        );
```
According to this request, the article number of the product for each store will be obtained from the database. 
With the help of the article number, the program searches the product on the website of a particular supermarket.
The operation of the program is based on web-scraping, with this way I always get actual information on the products I need.

### Technologies used:
- Java 17
- Maven 4.0.0
- Selenium Webdriver 4.8.1
- Spring Framework 5.3.20
- SQL

### Steps to run the program on your computer:
1. Make sure that following programs are installed:
- Java 17 or higher is required;
- IDE to run the App. I used IntelliJ IDEA Ultimate;
- Maven 4.0.0;
- SQL database. For example mySQL;
- download webdriver for your browser. I use chromedriver;
2. Make fork and open the App in your IDE;
3. Create new schema in your DB (see resources/init_db.sql);
4. Import available products to db from resources/products_db;
5. Enter all necessary information in util/ConnectionUtil;
6. Enter path to your webdriver in util/WebDriverImpl;
7. Write your products in "productsToBuy" and run the App;
