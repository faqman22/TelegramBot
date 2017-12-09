
import com.google.common.collect.ListMultimap;
import com.google.common.collect.MultimapBuilder;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SendBot extends TelegramLongPollingBot {
    Map<Long, String> response = new HashMap<Long, String>();

    ListMultimap<Long, String> users =
            MultimapBuilder.treeKeys().arrayListValues().build();


    @Override
    public void onUpdateReceived(Update update) {



        if (update.getMessage().hasText() && update.hasMessage()) {
            String ds = update.getMessage().getChatId().toString();
            if (update.getMessage().getText().equals("/start") || update.getMessage().getText().equals("/Назад")) {

                 SendMessage sendMessage = new SendMessage();
                 sendMessage.setChatId(update.getMessage().getChatId());
                 sendMessage.setText("Привет! Я бот сервиса btelegram.ru \n" +
                         "Я помогу вам оставить заявку на  личного бота ");
                 sendMessage.setText("Пожалуйста, введите ваше имя");


                try {
                    execute(sendMessage);
                    response.put(update.getMessage().getChatId(), "name");
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }





            } else {
                String s = response.get(update.getMessage().getChatId()).toString();
                Long chat_id = update.getMessage().getChatId();

                switch (response.get(update.getMessage().getChatId())) {

                    case "name":
                        users.put(update.getMessage().getChatId(), update.getMessage().getText());
                        SendMessage sendMessage = new SendMessage();
                        sendMessage.setChatId(chat_id);
                        sendMessage.setText("Здравствуйте, " + update.getMessage().getText() + "! Теперь введите ваш номер телефона");
                        response.clear();
                        response.put(chat_id,"phone");

                        try {
                            execute(sendMessage);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                        break;

                    case "phone":
                        String answ = update.getMessage().getText();
                        answ = answ.replace(" ","");
                        if(answ.startsWith("+")){
                            answ = answ.substring(1);
                        }
                        try {
                            Long i = Long.parseLong(answ);
                            if (answ.length() == 11 ){
                                users.put(update.getMessage().getChatId(),update.getMessage().getText());
                                SendMessage sendMessage1 = new SendMessage();
                                sendMessage1.setChatId(chat_id);
                                sendMessage1.setText("Отлично! Теперь напишите, что должен уметь ваш бот. Можно кратко :)");
                                response.clear();
                                response.put(chat_id,"info");
                                try {
                                    execute(sendMessage1);
                                } catch (TelegramApiException e) {
                                    e.printStackTrace();


                                }
                            } else {
                                System.out.println("-1");
                                SendMessage sendMessage2 = new SendMessage();
                                sendMessage2.setChatId(chat_id);
                                sendMessage2.setText("Неверный формат, попробуйте еще");
                                try {
                                    execute(sendMessage2);
                                } catch (TelegramApiException e1) {
                                    e1.printStackTrace();
                                }

                            }
                        } catch (Exception e) {
                            System.out.println("-2");
                            e.printStackTrace();
                            SendMessage sendMessage2 = new SendMessage();
                            sendMessage2.setChatId(chat_id);
                            sendMessage2.setText("Неверный формат, попробуйте еще");
                            try {
                                execute(sendMessage2);
                            } catch (TelegramApiException e1) {
                                e1.printStackTrace();
                            }
                        }

                        break;
                    case "info" :
                        users.put(chat_id,update.getMessage().getText());

                            ReplyKeyboardMarkup keyboardMarkup1 = new ReplyKeyboardMarkup();
                            List<KeyboardRow> rows1 = new ArrayList<>();
                            KeyboardRow row1 = new KeyboardRow();
                            row1.add("/Назад");
                            rows1.add(row1);
                            keyboardMarkup1.setKeyboard(rows1);
                            keyboardMarkup1.setOneTimeKeyboard(true);
                            keyboardMarkup1.setResizeKeyboard(true);

                            SendMessage sendMessage3 = new SendMessage();
                            sendMessage3.setChatId(chat_id);
                            sendMessage3.setReplyMarkup(keyboardMarkup1);
                            sendMessage3.setText("Поздравляем! Ваша заявка на бота принята, ожидайте, пока наш оператор свяжется с вами");


                            response.clear();
                            ClientBot clientBot = new ClientBot();
                            clientBot.SendClietn(users.get(chat_id));
                            try {
                                execute(sendMessage3);
                            } catch (TelegramApiException e1) {
                                e1.printStackTrace();
                            }



                        break;
                        default:
                            SendMessage sendMessage4 = new SendMessage();
                            sendMessage4.setChatId(chat_id);
                            sendMessage4.setText("Неверный формат");
                            try {
                                execute(sendMessage4);
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                            break;

                }
            }

        }


    }




    @Override
    public String getBotUsername() {
        return "forBuisnessBot";
    }

    @Override
    public String getBotToken() {
        return "503245767:AAF-Zdr6DKI-_RMAweKO4E8pWDBHGbKs9fg";
    }
}
