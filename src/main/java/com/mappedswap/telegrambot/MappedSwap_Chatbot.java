package com.mappedswap.telegrambot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class MappedSwap_Chatbot extends TelegramLongPollingBot {

    @Override
    public String getBotUsername() {
        return "MappedSwap Butler";
    }

    @Override
    public String getBotToken() {
        return "5009170654:AAHWAJUYOB4NkF4e38NzDRruOdRAR-mTFIM";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage()) {
            // get user's chatID
            String chatID = update.getMessage().getChatId().toString();
            // get user's text input
            String text_input = update.getMessage().getText().toUpperCase();

            try {
                // reply to user's command
                // start
                if(text_input.equals("/START")) {
                    // welcome message
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setText("\uD83D\uDE80Hello! I am MappedSwap Butler. Are you stuck or do you need help?\n" +
                            "\uD83D\uDE80Please see the below command lists so we can help you.\n" +
                            "\uD83D\uDE80If you have any other inquiries, please ask in the General channel.\n" +
                            "\n" +
                            "\uD83D\uDE80About us: \n" +
                            "https://www.mappedswap.io/about/\n" +
                            "\uD83D\uDE80Twitter:\n" +
                            "https://twitter.com/mappedswap/\n" +
                            "\uD83D\uDE80Documentation: \n" +
                            "https://mappedswap.gitbook.io/mappedswap-en/\n");
                    sendMessage.setChatId(chatID);

                    // Inline keyboard buttons
                    InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
                    List<List<InlineKeyboardButton>> inlineButtons = new ArrayList<>();
                    // First Row
                    List<InlineKeyboardButton> inlineKeyboardButtonList1 = new ArrayList<>();
                    // Second Row
                    List<InlineKeyboardButton> inlineKeyboardButtonList2= new ArrayList<>();

                    InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
                    InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
                    InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();
                    InlineKeyboardButton inlineKeyboardButton4 = new InlineKeyboardButton();

                    inlineKeyboardButton1.setText("Example 1");
                    inlineKeyboardButton1.setCallbackData("Test 1");
                    inlineKeyboardButtonList1.add(inlineKeyboardButton1);

                    inlineKeyboardButton2.setText("Example 2");
                    inlineKeyboardButton2.setCallbackData("Test 2");
                    inlineKeyboardButtonList1.add(inlineKeyboardButton2);

                    inlineKeyboardButton3.setText("Command List");
                    inlineKeyboardButton3.setCallbackData("cmd list");
                    inlineKeyboardButtonList2.add(inlineKeyboardButton3);

                    inlineKeyboardButton4.setText("Poll Example 1");
                    inlineKeyboardButton4.setCallbackData("Poll Test");
                    inlineKeyboardButtonList2.add(inlineKeyboardButton4);

                    inlineButtons.add(inlineKeyboardButtonList1);
                    inlineButtons.add(inlineKeyboardButtonList2);
                    inlineKeyboardMarkup.setKeyboard(inlineButtons);
                    sendMessage.setReplyMarkup(inlineKeyboardMarkup);
                    try {
                        execute(sendMessage);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
                // reach_team
                else if(text_input.equals("/REACH_TEAM")) {
                    execute(new SendMessage(chatID, "❓How to reach the team?\n" +
                            " Nolan Teng\n" +
                            " Please send us the email about your question!\n" +
                            " \uD83D\uDCE7: contact.us@mappedswap.io"));
                }
                // faq
                else if(text_input.equals("/FAQ")) {
                    execute(new SendMessage(chatID, "General Questions and Answers!"));
                }
                // msg
                else if(text_input.split(" ")[0].equals("/MSG")) {
                    execute(new SendMessage(chatID, "Your Message is \n" + text_input.split(" ", 2)[1]));
                }

                // reply to specific words (.equals() or .contains)
                else if(text_input.equals("HI") || text_input.equals("HELLO")) {
                    execute(new SendMessage(chatID, "Hello how can I help you?"));
                }
                else if (text_input.contains("JIRA")) {
                    execute(new SendMessage(chatID, "Do you want to submit Jira ticket?"));
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        // Reply for inline buttons
        else if(update.hasCallbackQuery()) {
            Message message = update.getCallbackQuery().getMessage();
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String data = callbackQuery.getData();
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(message.getChatId().toString());
            SendPoll sendPoll = new SendPoll();
            sendPoll.setChatId(message.getChatId().toString());

            System.out.println(data);

            // use switch
            if(data.equals("Test 1")) {
                sendMessage.setText("Test 1");
            }
            else if(data.equals("Test 2")) {
                sendMessage.setText("Test 2");
            }
            else if(data.equals("cmd list")) {
                sendMessage.setText("List of commands\n" +
                        "reach_team - How to reach our team\n" +
                        "faq - General FAQ\n" +
                        "msg - Type your message to our team after this command\n");
            }
            else if(data.equals("Poll Test")) {
                // SendMessage(chatId=null, text=null, parseMode=null, disableWebPagePreview=null, disableNotification=null, replyToMessageId=null, replyMarkup=null, entities=null, allowSendingWithoutReply=null, protectContent=null)
                sendPoll.setType("quiz"); // quiz, regular, ...
                sendPoll.setQuestion("Sample Question for Poll Testing??????");
                sendPoll.setCorrectOptionId(1);
                sendPoll.setIsAnonymous(true);

                // set the options
                List<String> option = new ArrayList<>();
                option.add("wrong");
                option.add("right");
                option.add("wrong");
                option.add("wrong");

                sendPoll.setOptions(option);
            }

            try {
                if(data.equals("Poll Test")) {
                    execute(sendPoll);
                }
                else {
                    execute(sendMessage);
                }
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }

    }
}
