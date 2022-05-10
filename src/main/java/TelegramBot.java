import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class TelegramBot extends TelegramLongPollingBot {

    @SneakyThrows
    public static void main(String[] args) {
        TelegramBot telegramBot = new TelegramBot(); // Создал объект telegramBot
        TelegramBotsApi telegramBotsApi =new TelegramBotsApi(DefaultBotSession.class); // создал объект telegramBotsApi
                                                                                       // и инициализирую конфигурацию с помощью DefaultBotSession.class
        telegramBotsApi.registerBot(telegramBot); // Регистрирую бота

    }

    @Override
    public String getBotUsername() {
        return "@CryptoKvpBot";
    } // этот метод возвращат имя указанное при регистрации нашего бота

    @Override
    public String getBotToken() {
        return "5206434059:AAF8NPe7OWZxlkdpefQHgZJuZz3w0zZ7IRI";
    } // Метот возвращает токет получиный от BotFather

    @Override
    public void onUpdateReceived(Update update) { // метод для приёма сообщений, используется для получения обновлений через LongPool
        // Long pool - это очередь ожидающих запросов, сначала отправляется запрос на сервер а потомсоеденение не закрывается этим сервером,
        // пока не появляется новое событие, событие отправляется в ответ на запрос, и клиент тут же отправляет новые ожидающие запросы
        //ToDo почитать про long pool и web hooks


    if (update.hasCallbackQuery()) {
        handleCallback(update.getCallbackQuery());
    }else if (update.hasMessage()){         // update имеет како-то сообщение, то
        handleMassage(update.getMessage()); // используем отдельно handleMassage
    }

    }
    @SneakyThrows
    private void handleMassage(Message message) {
        //пробуем захендлить команду
        if (message.hasText() && message.hasEntities()) { // команда существует когда у нас есть Text и Entities
                                                          // ( отправляя команду у нас есть минимум 1 сущность command)
            Optional<MessageEntity> commandEntity =       // Класс Optional позволяет исправить ошибку NullPointerException
            message.getEntities().stream().filter(e -> "bot_command".equals(e.getType())).findFirst(); // Ищем нашу команду
            if (commandEntity.isPresent()) { // если мы нашли команду (commandEntity)
                String command =  message.getText().substring(commandEntity.get().getOffset(),commandEntity.get().getLength());
                // Получили команду. Обрезали команду до commandEntity
                switch (command) {
                    case "/set_currency" :
                        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
                        execute(
                                SendMessage.builder().text("Вы отдаёте: ").chatId(message.getChatId().toString())
                                    .replyMarkup(InlineKeyboardMarkup.builder().keyboard(). build())
                                    .build());
                        return;
                }
            }

        }
    }
}
