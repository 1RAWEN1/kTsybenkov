package ru.ktsybenkov.tgBot;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ktsybenkov.tgBot.dao.CategoryRepository;
import ru.ktsybenkov.tgBot.dao.ProductRepository;
import ru.ktsybenkov.tgBot.entity.Category;
import ru.ktsybenkov.tgBot.entity.Product;

@SpringBootTest
public class FillingTests {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @BeforeAll
    void createRecords(){
        //Создание категорий
        Category pizzaCategory = new Category("Пицца", null);
        Category rollCategory = new Category("Роллы", null);
        Category burgerCategory = new Category("Бургеры", null);
        Category drinkCategory = new Category("Напитки", null);

        categoryRepository.save(pizzaCategory);
        categoryRepository.save(rollCategory);
        categoryRepository.save(burgerCategory);
        categoryRepository.save(drinkCategory);

        Category classicRollCategory = new Category("Классические роллы"
                , rollCategory);
        Category bakeRollCategory = new Category("Запеченные роллы"
                , rollCategory);
        Category sweetRollCategory = new Category("Сладкие роллы"
                , rollCategory);
        Category setsCategory = new Category("Наборы", rollCategory);

        categoryRepository.save(classicRollCategory);
        categoryRepository.save(bakeRollCategory);
        categoryRepository.save(sweetRollCategory);
        categoryRepository.save(setsCategory);

        Category classicBurgerCategory = new Category("Классические бургеры"
                , burgerCategory);
        Category spicyBurgerCategory = new Category("Острые бургеры"
                , burgerCategory);

        categoryRepository.save(classicBurgerCategory);
        categoryRepository.save(spicyBurgerCategory);

        Category carbonatedDrinkCategory = new Category("Газированные напитки"
        , drinkCategory);
        Category energeticDrinkCategory = new Category("Энергетические напитки"
                , drinkCategory);
        Category juiceCategory = new Category("Соки", drinkCategory);
        Category otherCategory = new Category("Другое", drinkCategory);

        categoryRepository.save(carbonatedDrinkCategory);
        categoryRepository.save(energeticDrinkCategory);
        categoryRepository.save(juiceCategory);
        categoryRepository.save(otherCategory);

        //Создание товаров
        //Пицца
        Product pizzaPomodoro = new Product(pizzaCategory, "Помодоро"
                , "моцарелла, фирменный соус, бекон ветчина, колбаса, курица"
                , 399);
        Product hawaiianPizza = new Product(pizzaCategory, "Гавайская"
                , "моцарелла, фирменный соус, ветчина и ананасы", 349);
        Product pizzaPepperoni = new Product(pizzaCategory, "Пепперони"
                , "моцарелла, фирменный соус, колбаски пепперони", 399);

        productRepository.save(pizzaPomodoro);
        productRepository.save(hawaiianPizza);
        productRepository.save(pizzaPepperoni);

        //Бургеры
        Product gamBurger = new Product(burgerCategory, "Гамбургер"
                , "булка, рубленая говяжья котлета, кружочки помидора," +
                " маринованного огурца, кетчуп", 149);
        Product cheeseBurger = new Product(burgerCategory, "Чизбургер"
                , "булка, ломтик твердого сыра, говяжья котлета," +
                " колечко репчатого лука, соус", 199);
        Product fishBurger = new Product(burgerCategory, "Фишбургер"
                , "булка, рыбная котлета или рыбное филе", 149);

        productRepository.save(gamBurger);
        productRepository.save(cheeseBurger);
        productRepository.save(fishBurger);

        //Роллы
        Product california = new Product(rollCategory, "Калифорния"
                , "крабовое мясо или крабовые палочки, свежий огурец," +
                " спелый авокадо и икра масаго", 649);
        Product canada = new Product(rollCategory, "Канада"
                , "лосось или семга, сыр, авокадо, огурцы", 649);
        Product philadelphia = new Product(rollCategory, "Филадельфия"
                , "лосось или семга, сливочный сыр «Филадельфия», рис для" +
                " суши, нори", 649);

        productRepository.save(california);
        productRepository.save(canada);
        productRepository.save(philadelphia);

        //Обычные напитки
        Product water = new Product(drinkCategory, "Вода"
                , "вода питьевая, негазированная 1л" +
                " спелый авокадо и икра масаго", 49);
        Product tea = new Product(drinkCategory, "Чай"
                , "чай зеленый, черный 200мл", 49);
        Product coffee = new Product(drinkCategory, "Кофе"
                , "эспрессо, американо 200мл", 99);

        productRepository.save(water);
        productRepository.save(tea);
        productRepository.save(coffee);

        //Классические роллы
        Product avocado = new Product(classicRollCategory, "Авокадо маки"
                , "ролл со свежим авокадо", 89);
        Product vakabi = new Product(classicRollCategory, "Вакаба маки"
                , "ролл с болгарским перцем", 89);
        Product cappa = new Product(classicRollCategory, "Каппа маки"
                , "ролл с огурцом и кунжутом", 79);

        productRepository.save(avocado);
        productRepository.save(vakabi);
        productRepository.save(cappa);

        //Запеченые роллы
        Product rollWithShrimp = new Product(bakeRollCategory, "Запеченный с креветкой"
                , "рис, нори, краб, креветка масаго, соус для запекания", 749);
        Product masselHot = new Product(bakeRollCategory, "Массел хот"
                , "рис, нори, мидии, огурец, краб, соус для запекания", 749);
        Product bakedPhiladelphia = new Product(bakeRollCategory, "Запеченная филадельфия"
                , "рис, нори, сыр, огурец, лосось, соус для запекания", 749);

        productRepository.save(rollWithShrimp);
        productRepository.save(masselHot);
        productRepository.save(bakedPhiladelphia);

        //Сладкие роллы
        Product cherryRoll = new Product(sweetRollCategory, "Вишневый"
                , "вишня, яичный блинчик, шантипак, сыр филадельфия" +
                ", вишневый топпинг", 169);
        Product strawberryRoll = new Product(sweetRollCategory, "Клубничный"
                , "клубника, яичный блинчик, шантипак, сыр филадельфия" +
                ", клубничный топпинг", 169);
        Product kiwiRoll = new Product(sweetRollCategory, "Киви"
                , "киви, яичный блинчик, шантипак, сыр филадельфия" +
                ", топпинг киви", 169);

        productRepository.save(cherryRoll);
        productRepository.save(strawberryRoll);
        productRepository.save(kiwiRoll);

        //Наборы
        Product coldSet = new Product(setsCategory, "Холодный набор"
                , "роллы с обощями, рыбой, сладкие", 1499);
        Product bakedSet = new Product(setsCategory, "Запеченный набор"
                , "роллы без обжаривания в масле", 1499);
        Product hotSet = new Product(setsCategory, "Горячий набор"
                , "роллы с обжаркой в растительном масле", 1499);

        productRepository.save(coldSet);
        productRepository.save(bakedSet);
        productRepository.save(hotSet);

        //Классические бургеры
        Product steakBurger = new Product(classicBurgerCategory, "Стейк-бургер"
                , "булка, цельный кусок говядины, кружочки помидора," +
                " маринованного огурца, кетчуп", 149);
        Product hawaiianBurger = new Product(classicBurgerCategory, "Гавайский бургер"
                , "булка, грильяж из свинины или говядины, ананас, соус", 249);
        Product veganBurger = new Product(classicBurgerCategory, "Веганский бургер"
                , "бургер без продуктов животного происхождения", 149);

        productRepository.save(steakBurger);
        productRepository.save(hawaiianBurger);
        productRepository.save(veganBurger);

        //Острые бургеры
        Product spicyGamBurger = new Product(spicyBurgerCategory, "Гамбургер"
                , "булка, рубленая говяжья котлета, кружочки помидора," +
                " маринованного огурца, отсрый соус", 179);
        Product spicyCheeseBurger = new Product(spicyBurgerCategory, "Чизбургер"
                , "булка, ломтик твердого сыра, говяжья котлета," +
                " колечко репчатого лука, острый соус", 229);
        Product spicyFishBurger = new Product(spicyBurgerCategory, "Фишбургер"
                , "булка, рыбная котлета или рыбное филе, острый соус", 179);

        productRepository.save(spicyGamBurger);
        productRepository.save(spicyCheeseBurger);
        productRepository.save(spicyFishBurger);

        //Газированные напитки
        Product kvass = new Product(carbonatedDrinkCategory, "Квас"
                , "славянский напиток с объёмной долей этилового спирта не" +
                " более 1,2%", 99);
        Product fruitWater = new Product(carbonatedDrinkCategory, "Фруктовая вода" +
                " с сиропом", "вода с фруктовыми или ягодными сиропами", 99);
        Product mineralWater = new Product(carbonatedDrinkCategory, "Вода газированная"
                , "минеральная газированная вода", 99);

        productRepository.save(kvass);
        productRepository.save(fruitWater);
        productRepository.save(mineralWater);

        //Энергетические напитки
        Product fitnessFoodCherry = new Product(energeticDrinkCategory, "Fitness Food" +
                " Вишня-кола", "Энергетик Fitness Food Factory WKUP 450мл со вкусом"
                + " Вишня-кола", 119);
        Product fitnessFoodMint = new Product(energeticDrinkCategory, "Fitness Food" +
                " Малина-мята", "Энергетик Fitness Food Factory WKUP 450мл со вкусом"
                + " Малина-мята", 119);
        Product fitnessFoodBerry = new Product(energeticDrinkCategory, "Fitness Food" +
                " Ягодный микс", "Энергетик Fitness Food Factory WKUP 450мл со вкусом"
                + " Ягодного микса", 119);

        productRepository.save(fitnessFoodCherry);
        productRepository.save(fitnessFoodMint);
        productRepository.save(fitnessFoodBerry);

        //Соки
        Product orangeJuice = new Product(juiceCategory, "Апельсиновый сок"
                , "сок со вкусом апельсина", 99);
        Product appleJuice = new Product(juiceCategory, "Яблочный сок"
                , "сок со вкусом яблока", 99);
        Product bananaJuice = new Product(juiceCategory, "Банановый сок"
                , "сок со вкусом банана", 99);

        productRepository.save(orangeJuice);
        productRepository.save(appleJuice);
        productRepository.save(bananaJuice);

        //Другое
        Product buckwheatTea = new Product(otherCategory, "Гречневый чай"
                , "чай из обжаренной гречихи", 119);
        Product barleyTea = new Product(otherCategory, "Ячменный чай"
                , "настой из обжаренный зерен ячменя", 119);
        Product riceTea = new Product(otherCategory, "Чай из коричневого риса"
                , "настой из обжаренного коричневого риса", 119);

        productRepository.save(buckwheatTea);
        productRepository.save(barleyTea);
        productRepository.save(riceTea);
    }
}
