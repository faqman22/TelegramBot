import com.google.common.collect.ListMultimap;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.List;

public class ClientBot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {


    }

    @Override
    public String getBotUsername() {
        return "clietnBtelegramBot";
    }

    @Override
    public String getBotToken() {
        return "463763045:AAEeTLxy10kYqgEbMj-ay7VBwFaEVnDibxw";
    }
    public void SendClietn(List<String> client){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(client.toString());
        sendMessage.setChatId("293658066");
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }
}
