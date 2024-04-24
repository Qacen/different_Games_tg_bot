package org.example;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BunkerRoom {
    boolean isFirstRound;
    boolean startGame;
    boolean endOpen;
    int countUser;
    int openInFirst;
    int inBunker;
    int cataclusm;
    long chatId;
    int curId;
    int curOpen;
    int adminId;
    Bunker bunker;
    List<BunkerUser> users = new ArrayList<>();
    String[]prof = bunkerData.getProffesions();
    String[] des = bunkerData.getDescription();
    String[]traits = bunkerData.getTraits();
    String[]phobia = bunkerData.getPhobia();
    String[]hobby = bunkerData.getHobby();
    String[] health = bunkerData.getHealth();
    String[]inventor = bunkerData.getInventor();
    String[]cards = bunkerData.getCards();
    String[]additional = bunkerData.getAdditional();
    String[]bodyType = bunkerData.getBodyTypes();
    String[]bunkerPlace = bunkerData.bunkerPlace;
    String[]bunkerSize = bunkerData.bunkerSize;
    String[] cataclusms = bunkerData.cataclusm;
    BunkerRoom(long groupId){
        this.chatId = groupId;
        this.cataclusm = 0;
    }
    public void generateBunker(){
        bunker = new Bunker();
    }
    public boolean userInRoom(long userID){
        for (int i = 0; i < users.size();i++){
            if (users.get(i).tgId == userID){
                return false;
            }
        }
        return true;
    }
    public int nextId(int start){
        for (int i = start; i < countUser;i++){
            if (users.get(i).isAlive){
                return i+1;
            }
        }
        return 0;
    }
    public void open(Bot bot, int buttonId,int id , String area){
        System.out.println(curId+ " " + id);
        if (curId == id) {
            endOpen = true;
            if (isFirstRound){
                if (curOpen == 1){
                    curOpen = openInFirst;
                    curId = nextId(id);
                }else {
                    curOpen--;
                }
            }else {
                curId = nextId(id);
            }
            System.out.println("inOpen");
            System.out.println(curOpen + "     " + curId);
            users.get(id-1).isOpen[buttonId-1] = true;
            InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
            List<InlineKeyboardButton> row = new ArrayList<>();
            area = area.replaceAll(""+buttonId,"");
            for (int i = 0;i<area.length();i++){
                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setCallbackData("bunkerGame~"+area.charAt(i)+"~"+id+"~"+area);
                button.setText(area.charAt(i)+"");
                row.add(button);
            }
            keyboard.add(row);
            markup.setKeyboard(keyboard);
            EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup();
            editMessageReplyMarkup.setChatId(users.get(id - 1).tgId);
            editMessageReplyMarkup.setMessageId(users.get(id - 1).messageLsId);
            editMessageReplyMarkup.setReplyMarkup(markup);
            EditMessageText editMessageText = new EditMessageText();
            editMessageText.setMessageId(users.get(id - 1).mеssageId);
            editMessageText.setChatId(chatId);
            editMessageText.setText(printUser(id - 1, false));
            try {
                bot.execute(editMessageText);
                bot.execute(editMessageReplyMarkup);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }if (curId == 0 & endOpen) {
            endOpen = false;
            isFirstRound = false;
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText("Все раскрыли нужное количество информации о себе\nПроголосуйте за того, кого хотите выгнать");
            try {
                bot.execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void addUser(Bot bot, long tgID,String name){
        if (!startGame) {
            users.add(new BunkerUser(users.size() + 1, tgID,name));
        }
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("игрок " + name + "("+users.size()+") зашел в комнату" );
        try {
            bot.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    public void setCurUser(int id){
        curId = id;
        if (isFirstRound) {
            curOpen = openInFirst;
        }else {
            curOpen = 1;
        }
    }
    public void nextRound(){
        curId = 1;
        curOpen = 1;
    }
    public void kickUser(Bot bot,int id){
        System.out.println("kick");
        users.get(id-1).isAlive = false;
        update(bot,id);
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("пользователь "+id + " был кикнут");
        try {
            bot.execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    public void startGame(Bot bot){
        generateBunker();
        isFirstRound = true;
        startGame = true;
        countUser = users.size();
        curId = 1;
        if (countUser == 8 & countUser == 9){
            openInFirst = 3;
            inBunker = 4;
        }else if (countUser == 10 & countUser == 11){
            openInFirst = 3;
            inBunker = 5;
        }else if (countUser == 12& countUser == 13){
            openInFirst = 2;
            inBunker = 6;
        } else if (countUser < 6) {
            openInFirst = 5;
            inBunker = 2;
        } else {
            openInFirst = 3;
            inBunker = 1;
        }
        curOpen = openInFirst;
        SendMessage sendMessageBunker = new SendMessage();
        SendMessage sendMessageCataclusm = new SendMessage();
        sendMessageBunker.setText(printBunker());
        sendMessageCataclusm.setText(cataclusms[cataclusm]);
        sendMessageBunker.setChatId(chatId);
        sendMessageCataclusm.setChatId(chatId);
        for (int i = 0; i < users.size();i++){
            BunkerUser user = users.get(i);
            SendMessage sendMessage = new SendMessage();
            SendMessage sendMessage1 = new SendMessage();
            SendMessage sendMessage2 = new SendMessage();
            SendMessage sendMessage3 = new SendMessage();
            SendMessage sendMessage4 = new SendMessage();
            sendMessage.setText(printUser(i,true));
            sendMessage1.setText(prof[user.proffesion] + " ("+des[user.proffesion]+")");
            sendMessage2.setText("ваша карта: "+ cards[user.card]);
            sendMessage3.setText("выбери какую характеристику раскрыть");
            sendMessage4.setText(printUser(i,false));
            sendMessage.setChatId(user.tgId);
            sendMessage1.setChatId(user.tgId);
            sendMessage2.setChatId(user.tgId);
            sendMessage3.setChatId(user.tgId);
            sendMessage4.setChatId(chatId);
            InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
            List<InlineKeyboardButton> row = new ArrayList<>();
            for (int j = 0; j < 8; j++){
                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setText(j+1+"");
                button.setCallbackData("bunkerGame~"+(j+1)+"~"+(i+1)+"~123456789");   // 0 - тип, 1 - id кнопки, 2 - id пользователя, 3 - area
                row.add(button);
            }
            keyboard.add(row);
            List<InlineKeyboardButton> row2 = new ArrayList<>();
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText("9");
            button.setCallbackData("bunkerGame~"+9+"~"+(i+1)+"~123456789");
            row2.add(button);
            keyboard.add(row2);
            markup.setKeyboard(keyboard);
            sendMessage3.setReplyMarkup(markup);
            try {
                if (i == 0){
                    bot.execute(sendMessageBunker);
                    bot.execute(sendMessageCataclusm);
                }
                bot.execute(sendMessage);
                bot.execute(sendMessage1);
                bot.execute(sendMessage2);
                users.get(i).messageLsId  = bot.execute(sendMessage3).getMessageId();
                users.get(i).mеssageId = bot.execute(sendMessage4).getMessageId();
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }

    }
    public String printUser(int id,boolean isOp){
        String[] par = new String[10];
        BunkerUser us = users.get(id);
        String res = "";
        String heal = "";
        String invent = "";
        String addit = "";
        for (int i = 0; i < us.health.size();i++){
            heal += health[us.health.get(i)]+", ";
        }
        heal = heal.substring(0,heal.length()-2);
        for (int i = 0; i < us.inventory.size();i++){
            invent += inventor[us.inventory.get(i)]+", ";
        }
        invent = invent.substring(0,invent.length()-2);
        for (int i = 0; i < us.additional.size();i++){
            addit += additional[us.additional.get(i)]+", ";
        }
        addit = addit.substring(0, addit.length()-2);
        par[0] = us.id +"   " + us.userName;
        par[1] = us.printBio();
        par[2] = prof[us.proffesion];
        par[3] = heal;
        par[4] = hobby[us.hobby];
        par[5] = phobia[us.phobia];
        par[6] = bodyType[us.bodyType];
        par[7] = traits[us.trait];
        par[8] = addit;
        par[9] = invent;

        if (!isOp & us.isAlive) {
            for (int i = 1; i < par.length; i++) {
                if (!us.isOpen[i-1]){
                    par[i] = "?";
                }
            }
        }
        System.out.println(Arrays.toString(par));
        res+="id: "+ par[0]+ ((us.isAlive)?(""):(" ❌"))+"\n";
        res+="1)лет/рост/пол: "+par[1]+"\n";
        res+="2)проф: "+ par[2]+"\n";
        res+="3)здор: "+ par[3]+"\n";
        res+="4)хобби: " + par[4]+"\n";
        res+="5)фоббия: "+ par[5]+"\n";
        res+="6)телслож: " + par[6]+"\n";
        res+="7)черта: " + par[7]+"\n";
        res+="8)доп: " + par[8]+"\n";
        res+="9)багаж: " + par[9]+"\n";
        return res;
    }
    public String printBunker(){
        String res = "";

        String inventory = "";
        for (int i = 0; i < bunker.itemInBunker.size();i++){
            inventory += inventor[bunker.itemInBunker.get(i)]+", ";
        }
        inventory = inventory.substring(0,inventory.length()-2);
        res+="Продержаться месяцев в букере: " + bunker.timeSpent+"\n";
        res+="Еды в бункере на недель: "+bunker.countFood+"\n";
        res+="Воды в бункере на недель: "+bunker.countWater+"\n";
        res+="Размер бункера: " + bunkerSize[bunker.size]+"\n";
        res+="Местонахождение: " + bunkerPlace[bunker.place]+"\n";
        res+="В бункере есть: " + inventory;
        return res;
    }
    public void generateUs(Bot bot,int id, int num){
        users.get(id-1).generateCur(num);
        update(bot,id);
    }
    public void changeUs(Bot bot, int id, int cur,int num){
        users.get(id-1).changeCur(cur,num);
        update(bot,id);
    }
    public void add(Bot bot, int id, int cur,int num){
        users.get(id-1).addCur(cur,num);
        update(bot,id);
    }
    public void openCur(Bot bot, int id,int cur){
        users.get(id-1).isOpen[cur-1] = true;
        update(bot,id);
    }
    public void update(Bot bot,int id){
        System.out.println("update");
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setMessageId(users.get(id-1).mеssageId);
        editMessageText.setChatId(chatId);
        editMessageText.setText(printUser(id-1,false));
        try {
            bot.execute(editMessageText);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}

