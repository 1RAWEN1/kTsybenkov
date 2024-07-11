package ru.ktsybenkov.tgBot.TgBot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.ktsybenkov.tgBot.dao.ClientOrderRepository;
import ru.ktsybenkov.tgBot.dao.OrderProductRepository;
import ru.ktsybenkov.tgBot.entity.Client;
import ru.ktsybenkov.tgBot.entity.ClientOrder;
import ru.ktsybenkov.tgBot.entity.OrderProduct;
import ru.ktsybenkov.tgBot.entity.Product;
import ru.ktsybenkov.tgBot.service.CategoryService;
import ru.ktsybenkov.tgBot.service.ClientService;
import ru.ktsybenkov.tgBot.service.ProductService;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TgBotService {
    private TelegramBot bot;
    private final CategoryService categoryService;
    private final ProductService productService;
    private final ClientService clientService;
    private final ClientOrderRepository clientOrderRepository;
    private final OrderProductRepository orderProductRepository;

    public TgBotService(CategoryService categoryService, ProductService productService, ClientService clientService,
                        ClientOrderRepository clientOrderRepository, OrderProductRepository orderProductRepository) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.clientService = clientService;
        this.clientOrderRepository = clientOrderRepository;
        this.orderProductRepository = orderProductRepository;
    }

    @Value("${telegram.bot.token}")
    private String botToken;

    @PostConstruct
    public void createConnection()
    {
        bot = new TelegramBot(botToken);
        bot.setUpdatesListener(updates -> {
            updates.forEach(this::update);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    private void update(Update update) {
        if(update.callbackQuery() != null){
            Client client = clientService.getClientByExternalId(update.callbackQuery().from().id());

            if(client != null) {
                String[] callback = update.callbackQuery().data().split(",");
                editOrder(client.getId(), callback, update);
            }
            else {
                registrationMessage(update.callbackQuery().from().id());
            }

            return;
        }

        if(update.message().text().equals("/start")){
            Client client = clientService.getClientByExternalId(update.message().chat().id());

            if(client != null) {
                bot.execute(new SendMessage(update.message().chat().id(),
                        "Бот готов к работе"));

                List<KeyboardButton> categories = categoryService.getCategoryByParent(null)
                        .stream()
                        .map(category -> new KeyboardButton(category.getName())).toList();
                defaultMessage(update, categories);
            }
            else {
                registrationMessage(update.message().chat().id());
            }

            return;
        }

        Client client = clientService.getClientByExternalId(update.message().chat().id());

        if(client != null) {
            switch (update.message().text()) {
                case "Редактировать информацию клиента" -> {
                    bot.execute(new SendMessage(update.message().chat().id(),
                            "Информация клиента:\n" + client.getFullName() + "," + client.getPhoneNumber() +
                                    "," + client.getAddress()));

                    clientInformationEditMessage(update);
                }
                case "Информация клиента" -> bot.execute(new SendMessage(update.message().chat().id(),
                        "Имя клиента: " + client.getFullName() + "\nНомер телефона: " + client.getPhoneNumber() +
                                "\nАдрес: " + client.getAddress() + "\nВнутренний ID: " + client.getId()));
                case "Оформить заказ" -> {
                    ClientOrder clientOrder = clientOrderRepository
                            .findNewOrderByClientId(client.getId());

                    if(clientOrder.getTotal().doubleValue() > 0) {
                        clientOrder.setStatus(2);

                        clientOrderRepository.save(clientOrder);

                        bot.execute(new SendMessage(update.message().chat().id(),
                                "Заказ успешно оформлен. ID: " + clientOrder.getId()));

                        newClientOrder(client);
                    }
                    else{
                        bot.execute(new SendMessage(update.message().chat().id(),
                                "В заказе пока нет товаров"));
                    }
                }
                case "Просмотреть заказ" -> {
                    ClientOrder clientOrder = clientOrderRepository
                            .findNewOrderByClientId(client.getId());

                    List<OrderProduct> orderProducts = orderProductRepository
                            .findByClientOrder(clientOrder);
                    for(OrderProduct orderProduct : orderProducts){
                        int productCount = orderProduct.getCountProduct();
                        bot.execute(new SendMessage(update.message().chat().id(),
                                orderProduct.getProduct().getName() + " х" + productCount +
                                " Общая цена: " + (orderProduct.getProduct().getPrice().doubleValue() * productCount)
                                        + "р"));
                    }

                    bot.execute(new SendMessage(update.message().chat().id(),
                            "Итого: " + clientOrder.getTotal()
                                    + "р"));
                }
                case "В основное меню" -> {
                    List<KeyboardButton> categories = categoryService.getCategoryByParent(null)
                            .stream()
                            .map(category -> new KeyboardButton(category.getName())).toList();

                    defaultMessage(update, categories);
                }
                default -> {
                    Long categoryId = categoryService.getCategoryByName(update.message().text());
                    if (categoryId != null) {
                        List<KeyboardButton> categories = categoryService.getCategoryByParent(categoryId)
                                .stream()
                                .map(category -> new KeyboardButton(category.getName())).toList();

                        defaultMessage(update, categories);

                        for (Product product : productService.search(null, categoryId)) {
                            InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
                            InlineKeyboardButton button = new
                                    InlineKeyboardButton("Добавить к заказу")
                                    .callbackData("a," + product.getId());

                            markupInline.addRow(button);

                            bot.execute(new SendMessage(update.message().chat().id(),
                                    "Товар: " + product.getName() + "\nОписание: " +
                                            product.getDescription() + "\nЦена: " +
                                            product.getPrice()).replyMarkup(markupInline));
                        }
                    }
                    else
                        registration(update, client);
                }
            }
        }
        else
            registration(update, null);
    }

    public void registration(Update update, Client client){
        String fullName;
        String phoneNumber;
        String address;
        try{
            String[] parts = update.message().text().split(",");

            fullName = parts[0];
            phoneNumber = parts[1];
            address = parts[2];
            Client newClient = client;
            if(newClient == null) {
                newClient = new Client(update.message().chat().id(), fullName, phoneNumber, address);

                clientService.saveClient(newClient);
            }
            else {
                newClient.setFullName(fullName);
                newClient.setPhoneNumber(phoneNumber);
                newClient.setAddress(address);

                clientService.saveClient(newClient);
            }

            newClientOrder(newClient);

            bot.execute(new SendMessage(update.message().chat().id(),
                    "Информация о клиенте изменена"));

            List<KeyboardButton> categories = categoryService.getCategoryByParent(null)
                    .stream()
                    .map(category -> new KeyboardButton(category.getName())).toList();
            defaultMessage(update, categories);
        }
        catch (Exception e){
            System.out.println("Неизвестная команда. Ошибка парсинга клиента: " + e.getMessage());

            if(client == null) {
                registrationMessage(update.message().chat().id());
            }
            else {
                bot.execute(new SendMessage(update.message().chat().id(),
                        "Я не знаю такой команды."));

                List<KeyboardButton> categories = categoryService.getCategoryByParent(null)
                        .stream()
                        .map(category -> new KeyboardButton(category.getName())).toList();
                defaultMessage(update, categories);
            }
        }
    }

    public void registrationMessage(Long chatId){
        bot.execute(new SendMessage(chatId,
                "Привет! Я Бот для автоматизации доставки заказов. Вначале нужно зарегистрироваться.\n" +
                        "Введи через запятую:\n|Полное Имя,Номер телефона(10 символов),Адрес|\n" +
                        "Без пробелов после запятых"));
    }

    public void clientInformationEditMessage(Update update){
        bot.execute(new SendMessage(update.message().chat().id(),
                "Все как и в прошлый раз. Только теперь для редактирования.\n" +
                        "Введи через запятую:\n|Полное Имя,Номер телефона(10 символов),Адрес|\n" +
                        "Без пробелов после запятых"));
    }

    private void newClientOrder(Client client){
        ClientOrder clientOrder = new ClientOrder(client, 1, new BigDecimal(0));
        clientOrderRepository.save(clientOrder);
    }

    private void defaultMessage(Update update, List<KeyboardButton> categories) {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup(categories.toArray(KeyboardButton[]::new));
        markup.resizeKeyboard(true);
        markup.addRow(new KeyboardButton("Информация клиента"),new KeyboardButton("Редактировать информацию клиента"));
        markup.addRow(new KeyboardButton("Просмотреть заказ"), new KeyboardButton("Оформить заказ"));
        markup.addRow(new KeyboardButton("В основное меню"));
        bot.execute(new SendMessage(update.message().chat().id(),
                "Товары").replyMarkup(markup));
    }

    public void editOrder(Long clientId, String[] callback, Update update){
        Long productId = null;
        try {
            productId = Long.parseLong(callback[1]);
        } catch (Exception e) {
            System.out.println("Ошибка парсинга: " + e.getMessage());
        }

        if (productId != null) {
            ClientOrder clientOrder = clientOrderRepository
                    .findNewOrderByClientId(clientId);

            OrderProduct orderProduct = orderProductRepository
                    .findProductByClientOrderId(clientOrder.getId(), productId);

            InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
            String text = "";
            switch (callback[0]) {
                case "a" -> {
                    if (orderProduct == null) {
                        orderProduct = new OrderProduct(clientOrder,
                                productService.getProductById(productId), 1);
                    } else {
                        orderProduct.setCountProduct(orderProduct.getCountProduct() + 1);
                    }
                    orderProductRepository.save(orderProduct);

                    clientOrder.setTotal(clientOrder.getTotal().add(orderProduct.getProduct().getPrice()));
                    clientOrderRepository.save(clientOrder);

                    InlineKeyboardButton button = new
                            InlineKeyboardButton("Добавить еще 1 к заказу")
                            .callbackData("a," + orderProduct.getProduct().getId());

                    markupInline.addRow(button);

                    text = orderProduct.getProduct().getName() + " успешно добавлен к заказу";
                }
            }

            bot.execute(new SendMessage(update.callbackQuery().from().id(),
                    text).replyMarkup(markupInline));
        }
    }
}
