package org.example;
import org.example.token;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;


public class Bot extends TelegramLongPollingBot {
    long myTgID = 1404899566l;
    BunkerRoom bunkerRoom;
    @Override
    public void onUpdateReceived(Update update) {

        System.out.println("callback: "+update.hasCallbackQuery());
        if (update.hasCallbackQuery()){
            System.out.println("callback");
            if (update.getCallbackQuery().getData().startsWith("bunkerGame")){
                String[] callback = update.getCallbackQuery().getData().split("~");
                System.out.println("open" + callback[1]);
                bunkerRoom.open(this,Integer.parseInt(callback[1]),Integer.parseInt(callback[2]),callback[3]);
            }
        }
        if (update.hasMessage()) {
            String text = update.getMessage().getText();
            if (text.startsWith("/")) {

                long userID = (update.getMessage().getFrom().getId());
                System.out.println(userID);
                if (userID == myTgID) {
                    if (text.startsWith("/bunkerRoomCreate")) {
                        System.out.println("комната");
                        bunkerRoom = new BunkerRoom(update.getMessage().getChatId());
                    } else if (text.startsWith("/bunkerStart")) {
                        System.out.println("start");
                        bunkerRoom.startGame(this);
                    } else if (text.startsWith("/generate")) {
                        String[] txt = text.split(" ")[1].split("/");
                        if (txt.length == 2) {
                            bunkerRoom.generateUs(this, Integer.parseInt(txt[0]), Integer.parseInt(txt[1]));
                        }
                    } else if (text.startsWith("/change")) {
                        String[] txt = text.split(" ")[1].split("/");
                        if (txt.length == 3) {
                            bunkerRoom.changeUs(this, Integer.parseInt(txt[0]), Integer.parseInt(txt[1]), Integer.parseInt(txt[2]));
                        }
                    } else if (text.startsWith("/add")) {
                        String[] txt = text.split(" ")[1].split("/");
                        if (txt.length == 3) {
                            bunkerRoom.add(this, Integer.parseInt(txt[0]), Integer.parseInt(txt[1]), Integer.parseInt(txt[2]));
                        }
                    } else if (text.startsWith("/openProperty")) {
                        String[] txt = text.split(" ")[1].split("/");
                        if (txt.length == 2) {
                            bunkerRoom.openCur(this, Integer.parseInt(txt[0]), Integer.parseInt(txt[1]));
                        }
                    } else if (text.startsWith("/kickUser")) {
                        bunkerRoom.kickUser(this, Integer.parseInt(text.split(" ")[1]));
                    } else if (text.startsWith("/setStep")) {
                        bunkerRoom.setCurUser(Integer.parseInt(text.split(" ")[1]));
                    } else if (text.startsWith("/nextRound")){
                        bunkerRoom.nextRound();
                    }
                    else if (text.startsWith("/adminHelp")) {
                        String helpText = "/bunkerRoomCreate - создание комнаты\n" +
                                "/bunkerStart - начало игры\n" +
                                "/generate - случайная характеристика (игрок/номер характеристики)\n" +
                                "/change - изменение характеристики (игрок/номер характеристики/значение)\n" +
                                "/add - добавить характеристику (игрок/0-здоровье,1-ивентарь/значение\n" +
                                "/openProperty - открыть всем характеристику игрока (игрок/номер характеристики\n" +
                                "/kickUser - выгнать игрока(номер игрока)\n" +
                                "/setStep - изменить игрока, который раскрывает характеристику (номер игрока)\n"+
                                "/nextRound - следующий раунд\n ";
                        SendMessage sendMessage = new SendMessage();
                        sendMessage.setChatId(update.getMessage().getChatId());
                        sendMessage.setText(helpText);
                        try {
                            this.execute(sendMessage);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                if (text.startsWith("/bunkerJoin")) {
                    if (bunkerRoom != null) {
                        if (bunkerRoom.userInRoom(userID)) ;
                        {
                            System.out.println("join");
                            bunkerRoom.addUser(this,userID,update.getMessage().getFrom().getFirstName());
                        }
                    }
                } else if (text.startsWith("/button")) {
                    InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
                    List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
                    List<InlineKeyboardButton> row = new ArrayList<>();
                    InlineKeyboardButton button = new InlineKeyboardButton();
                    button.setText("1");
                    button.setCallbackData("asd");

                    row.add(button);
                    keyboard.add(row);
                    markup.setKeyboard(keyboard);
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(update.getMessage().getChatId());
                    sendMessage.setReplyMarkup(markup);
                    try {
                        this.execute(sendMessage);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
    @Override
    public String getBotUsername() {
        return "DifferentGamesBot";
    }
    @Override
    public String getBotToken(){return token.getToken();}
}
