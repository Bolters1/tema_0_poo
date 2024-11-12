package main;

import checker.Checker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import checker.CheckerConstants;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import fileio.CardInput;
import fileio.Input;
import stuff.*;

import java.util.Random;
import java.util.ArrayList;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import java.util.Collections;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * DO NOT MODIFY MAIN METHOD
     * Call the checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(CheckerConstants.TESTS_PATH);
        Path path = Paths.get(CheckerConstants.RESULT_PATH);

        if (Files.exists(path)) {
            File resultFile = new File(String.valueOf(path));
            for (File file : Objects.requireNonNull(resultFile.listFiles())) {
                file.delete();
            }
            resultFile.delete();
        }
        Files.createDirectories(path);

        for (File file : Objects.requireNonNull(directory.listFiles())) {
            String filepath = CheckerConstants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getName(), filepath);
            }
        }

        Checker.calculateScore();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Input inputData = objectMapper.readValue(new File(CheckerConstants.TESTS_PATH + filePath1),
                Input.class);

        ArrayNode output = objectMapper.createArrayNode();

        /*
         * TODO Implement your function here
         *
         * How to add output to the output array?
         * There are multiple ways to do this, here is one example:
         *
         * ObjectMapper mapper = new ObjectMapper();
         *
         * ObjectNode objectNode = mapper.createObjectNode();
         * objectNode.put("field_name", "field_value");
         *
         * ArrayNode arrayNode = mapper.createArrayNode();
         * arrayNode.add(objectNode);
         *
         * output.add(arrayNode);
         * output.add(objectNode);
         *
         */
        ObjectMapper mapper = new ObjectMapper();
        //CardInput card = inputData.getPlayerOneDecks().getDecks().get(0);
        ArrayList<CardInput> deck_input1 = inputData.getPlayerOneDecks().getDecks().get(inputData.getGames().get(0).getStartGame().getPlayerOneDeckIdx());
        Random seed = new Random(inputData.getGames().get(0).getStartGame().getShuffleSeed());
        Collections.shuffle(deck_input1, seed);
        Deck deck_1 = new Deck(deck_input1);
        ArrayList<CardInput> deck_input2 = inputData.getPlayerTwoDecks().getDecks().get(inputData.getGames().get(0).getStartGame().getPlayerTwoDeckIdx());
        Random seed2 = new Random(inputData.getGames().get(0).getStartGame().getShuffleSeed());
        Collections.shuffle(deck_input2, seed2);
        Deck deck_2 = new Deck(deck_input2);
        ActionsInput action;
        //ObjectNode object = mapper.createObjectNode();
        CardInput erou_1 = inputData.getGames().get(0).getStartGame().getPlayerOneHero();
        Erou hero_1 = new Erou(erou_1);
        CardInput erou_2 = inputData.getGames().get(0).getStartGame().getPlayerTwoHero();
        Erou hero_2 = new Erou(erou_2);
        Player player_1 = new Player(deck_1.getDeck().get(0), deck_1);
        Player player_2 = new Player(deck_2.getDeck().get(0), deck_2);
        GameState game = new GameState(inputData.getGames().get(0).getStartGame().getStartingPlayer());
        //hand_1.addCardToHand();
        //object.put("command", "reaad");
        //object.set("deck", mapper.valueToTree(deck_1));
        //Minion test  =  new Minion();
        for(int i = 0; i < inputData.getGames().get(0).getActions().size(); i++) {
            //object.put("command", "reaad");
            action = inputData.getGames().get(0).getActions().get(i);
            ObjectNode object = mapper.createObjectNode();
            //object.put("command", action.getCommand());
            if (action.getCommand().equals("getPlayerDeck")) {
                object.put("command", action.getCommand());
                object.put("playerIdx", action.getPlayerIdx());
                if (action.getPlayerIdx() == 1)
                    object.put("output", mapper.valueToTree(deck_1.getDeck()));
                    //object.set("output", mapper.valueToTree(deck_1));
                else if (action.getPlayerIdx() == 2)
                    object.put("output", mapper.valueToTree(deck_2.getDeck()));
//                    object.set("output", mapper.valueToTree(deck_2));
                output.add(object);
            } else if (action.getCommand().equals("getPlayerHero")) {
                object.put("command", action.getCommand());
                object.put("playerIdx", action.getPlayerIdx());
                if (action.getPlayerIdx() == 1)
                    object.set("output", mapper.valueToTree(hero_1));
                else if (action.getPlayerIdx() == 2)
                    object.set("output", mapper.valueToTree(hero_2));
                output.add(object);
            } else if (action.getCommand().equals("getPlayerTurn")) {
                object.put("command", action.getCommand());
                object.set("output", mapper.valueToTree(game.getPlayerTurn()));
                output.add(object);
            } else if (action.getCommand().equals("placeCard")) {
                if (game.getPlayerTurn() == 1 ) {
                    Minion card = player_1.getCardFromHand(action.getHandIdx());
                    card.assign_position(card);
                    if(card.getMana() > player_1.getMana()){
                        object.put("command", action.getCommand());
                        object.put("handIdx", action.getHandIdx());
                        object.set("error", objectMapper.valueToTree("Not enough mana to place card on table."));
                        //object.set("player1 mana", objectMapper.valueToTree(player_1.getMana()));
                        //object.set("card cost", objectMapper.valueToTree(card.getMana()));
                        output.add(object);
                    }
                    else if(player_1.getNrOfCardsFront() >= 5 && card.getPosition() == 1){
                        object.put("command", action.getCommand());
                        object.put("handIdx", action.getHandIdx());
                        object.set("error", objectMapper.valueToTree("Cannot place card on table since row is full."));
                        output.add(object);
                    }
                    else if(player_1.getNrOfCardsBack() >= 5 && card.getPosition() == 0){
                        object.put("command", action.getCommand());
                        object.put("handIdx", action.getHandIdx());
                        object.set("error", objectMapper.valueToTree("Cannot place card on table since row is full."));
                        output.add(object);
                    }
                    else if (card.getPosition() == 1) {
                        //object.set("player1 mana", objectMapper.valueToTree(player_1.getMana()));
                        //object.set("card cost", objectMapper.valueToTree(card.getMana()));
                        player_1.addToFrontRow(player_1.getFrontRow(), card);
                        player_1.removeCard(player_1.getCardsInHand(), action.getHandIdx());
                        //player_1.setMana(player_1.getMana() - card.getMana());
                       // object.set("player1 mana after", objectMapper.valueToTree(player_1.getMana()));
                       // output.add(object);
                    } else if (card.getPosition() == 0) {
                        //object.set("player1 mana", objectMapper.valueToTree(player_1.getMana()));
                        //object.set("card cost", objectMapper.valueToTree(card.getMana()));
                        player_1.addToBackRow(player_1.getBackRow(), card);
                        player_1.removeCard(player_1.getCardsInHand(), action.getHandIdx());
                        //player_1.setMana(player_1.getMana() - card.getMana());
                        //object.set("player1 mana after", objectMapper.valueToTree(player_1.getMana()));
                        //output.add(object);
                    }
                } else if (game.getPlayerTurn() == 2) {
                    Minion card = player_2.getCardFromHand(action.getHandIdx());
                    card.assign_position(card);
                    if(card.getMana() > player_2.getMana()){
                        object.put("command", action.getCommand());
                        object.put("handIdx", action.getHandIdx());
                        object.set("error", objectMapper.valueToTree("Not enough mana to place card on table."));
                        //object.set("player2 mana", objectMapper.valueToTree(player_2.getMana()));
                        //object.set("card cost", objectMapper.valueToTree(card.getMana()));
                        output.add(object);
                    }
                    else if(player_2.getNrOfCardsFront() >= 5 && card.getPosition() == 1){
                        object.put("command", action.getCommand());
                        object.put("handIdx", action.getHandIdx());
                        object.set("error", objectMapper.valueToTree("Cannot place card on table since row is full."));
                        output.add(object);
                    }
                    else if(player_2.getNrOfCardsBack() >= 5 && card.getPosition() == 0){
                        object.put("command", action.getCommand());
                        object.put("handIdx", action.getHandIdx());
                        object.set("error", objectMapper.valueToTree("Cannot place card on table since row is full."));
                        output.add(object);
                    }
                    else if (card.getPosition() == 1) {
                        //object.set("player2 mana", objectMapper.valueToTree(player_2.getMana()));
                        //object.set("card cost", objectMapper.valueToTree(card.getMana()));
                        player_2.addToFrontRow(player_2.getFrontRow(), card);
                        player_2.removeCard(player_2.getCardsInHand(), action.getHandIdx());
                        //player_2.setMana(player_2.getMana() - card.getMana());
                        //object.set("player2 mana after", objectMapper.valueToTree(player_2.getMana()));
                        //output.add(object);
                    } else if (card.getPosition() == 0) {
                        //object.set("player2 mana", objectMapper.valueToTree(player_2.getMana()));
                        //object.set("card cost", objectMapper.valueToTree(card.getMana()));

                        player_2.addToBackRow(player_2.getBackRow(), card);
                        player_2.removeCard(player_2.getCardsInHand(), action.getHandIdx());
                        //player_2.setMana(player_2.getMana() - card.getMana());
                        //object.set("player2 mana after", objectMapper.valueToTree(player_2.getMana()));
                        //output.add(object);
                    }
                }
            } else if (action.getCommand().equals("endPlayerTurn")) {
                if (game.getPlayerTurn() == 1) {
                    if (player_2.getPlayer_turn_check() == 0) {
                        player_1.setPlayer_turn_check(1);
                        game.setPlayerTurn(2);
                    } else if (player_2.getPlayer_turn_check() == 1) {
                        player_2.setPlayer_turn_check(0);
                        game.setOverallTurn(game.getOverallTurn() + 1);
                        game.setPlayerTurn(2);
                        if(deck_1.getNrOfCardsInDeck() > 0)
                            player_1.addCardToHand(deck_1.getDeck().get(0), deck_1);
                        if(deck_2.getNrOfCardsInDeck() > 0)
                            player_2.addCardToHand(deck_2.getDeck().get(0), deck_2);
                        game.setMana_to_receive(game.getMana_to_receive() + 1);
                        player_1.setMana(player_1.getMana() + game.getMana_to_receive());
                        player_2.setMana(player_2.getMana() + game.getMana_to_receive());
                        game.resetAttack(player_1, player_2);
//                        object.put("command", action.getCommand());
//                        object.set("check mana", objectMapper.valueToTree(game.getMana_to_receive()));
//                        object.set("player1 mana", objectMapper.valueToTree(player_1.getMana()));
//                        object.set("player2 mana", objectMapper.valueToTree(player_2.getMana()));
//                        object.set("turn start", objectMapper.valueToTree(game.getOverallTurn()));
//                        output.add(object);
                    }
                } else if (game.getPlayerTurn() == 2) {
                    if (player_1.getPlayer_turn_check() == 0) {
                        player_2.setPlayer_turn_check(1);
                        game.setPlayerTurn(1);
                    } else if (player_1.getPlayer_turn_check() == 1) {
                        player_1.setPlayer_turn_check(0);
                        game.setOverallTurn(game.getOverallTurn() + 1);
                        game.setPlayerTurn(1);
                        if(deck_1.getNrOfCardsInDeck() > 0)
                            player_1.addCardToHand(deck_1.getDeck().get(0), deck_1);
                        if(deck_2.getNrOfCardsInDeck() > 0)
                            player_2.addCardToHand(deck_2.getDeck().get(0), deck_2);
                        game.setMana_to_receive(game.getMana_to_receive() + 1);
                        player_1.setMana(player_1.getMana() + game.getMana_to_receive());
                        player_2.setMana(player_2.getMana() + game.getMana_to_receive());
                        game.resetAttack(player_1, player_2);
//                        object.put("command", action.getCommand());
//                        object.set("check mana", objectMapper.valueToTree(game.getMana_to_receive()));
//                        object.set("player1 mana", objectMapper.valueToTree(player_1.getMana()));
//                        object.set("player2 mana", objectMapper.valueToTree(player_2.getMana()));
//                        object.set("turn start", objectMapper.valueToTree(game.getOverallTurn()));
//                        output.add(object);
                    }
                }
            }
            else if(action.getCommand().equals("getCardsInHand")){
                object.put("command", action.getCommand());
                object.put("playerIdx", action.getPlayerIdx());
                if (action.getPlayerIdx() == 1)
                    object.put("output", mapper.valueToTree(player_1.getCardsInHand()));
                else if (action.getPlayerIdx() == 2)
                    object.put("output", mapper.valueToTree(player_2.getCardsInHand()));
                output.add(object);
            }
            else if(action.getCommand().equals("getPlayerMana")){
                object.put("command", action.getCommand());
                object.put("playerIdx", action.getPlayerIdx());
                if(action.getPlayerIdx() == 1) {
                    object.put("output", player_1.getMana());
                }
                else if(action.getPlayerIdx() == 2)
                    object.put("output", player_2.getMana());
                output.add(object);
            }
            else if(action.getCommand().equals("getCardsOnTable")){
                object.put("command", action.getCommand());
                ArrayNode output_cards = mapper.createArrayNode();
                if(player_2.getNrOfCardsBack() > 0){
                    output_cards.add(mapper.valueToTree(player_2.getBackRow()));
                }else {
                    output_cards.add(mapper.createArrayNode());
                }
                if(player_2.getNrOfCardsFront() > 0)
                    output_cards.add(mapper.valueToTree(player_2.getFrontRow()));
                else {
                    output_cards.add(mapper.createArrayNode());
                }
                if(player_1.getNrOfCardsFront() > 0)
                    output_cards.add(mapper.valueToTree(player_1.getFrontRow()));
                else {
                    output_cards.add(mapper.createArrayNode());
                }
                if(player_1.getNrOfCardsBack() > 0)
                    output_cards.add(mapper.valueToTree(player_1.getBackRow()));
                else {
                    output_cards.add(mapper.createArrayNode());
                }
                object.put("output", output_cards);
                output.add(object);
            }
            else if(action.getCommand().equals("cardUsesAttack")){
                Minion attacker = player_1.getCardFromCoordonates(player_1, player_2, action.getCardAttacker().getX(),action.getCardAttacker().getY() );
                Minion attacked = player_1.getCardFromCoordonates(player_1, player_2, action.getCardAttacked().getX(),action.getCardAttacked().getY() );
                int result; //0 = a functionat 1-4 eroare
                if(game.getPlayerTurn() == 1)
                    result = game.Attack(attacker, attacked, player_2,action.getCardAttacked().getX(), action.getCardAttacked().getY(), game);
                else
                    result = game.Attack(attacker, attacked, player_1,action.getCardAttacked().getX(), action.getCardAttacked().getY(), game);
                if(result == 1){
                    object.put("command", action.getCommand());
                    object.put("cardAttacker",mapper.valueToTree(action.getCardAttacker()));
                    object.put("cardAttacked",mapper.valueToTree(action.getCardAttacked()));
                    object.set("error", objectMapper.valueToTree("Attacked card does not belong to the enemy."));
                    output.add(object);
                }
                else if(result == 2){
                    object.put("command", action.getCommand());
                    object.put("cardAttacker",mapper.valueToTree(action.getCardAttacker()));
                    object.put("cardAttacked",mapper.valueToTree(action.getCardAttacked()));
                    object.set("error", objectMapper.valueToTree("Attacker card has already attacked this turn."));
                    output.add(object);
                }
                else if(result == 3){
                    object.put("command", action.getCommand());
                    object.put("cardAttacker",mapper.valueToTree(action.getCardAttacker()));
                    object.put("cardAttacked",mapper.valueToTree(action.getCardAttacked()));
                    object.set("error", objectMapper.valueToTree("Attacker card is frozen."));
                    output.add(object);
                }
                else if(result == 4){
                    object.put("command", action.getCommand());
                    object.put("cardAttacker",mapper.valueToTree(action.getCardAttacker()));
                    object.put("cardAttacked",mapper.valueToTree(action.getCardAttacked()));
                    object.set("error", objectMapper.valueToTree("Attacked card is not of type 'Tank'."));
                    output.add(object);
                }
            }
            else if(action.getCommand().equals("getCardAtPosition")){
                Minion card = game.getCardAtThisPosition(player_1, player_2, action.getX(), action.getY());
                if(card == null){
                    object.put("command", action.getCommand());
                    object.put("output",mapper.valueToTree("No card available at that position."));
                    object.put("x",mapper.valueToTree(action.getX()));
                    object.put("y",mapper.valueToTree(action.getY()));
                    output.add(object);
                }
                else{
                    object.put("command", action.getCommand());
                    object.put("output",mapper.valueToTree(mapper.valueToTree(card)));
                    object.put("x",mapper.valueToTree(action.getX()));
                    object.put("y",mapper.valueToTree(action.getY()));
                    output.add(object);
                }
            }

        }
        //deck = deck.setDeck(deck_input);
        //output.add(mapper.valueToTree(inputData.getPlayerOneDecks().getDecks().get(0).get(0)));
        //ArrayList<ActionsInput> a = inputData.getGames().get(0).getActions();
        //a.size()
        //a.listIterator();


        //ObjectNode object = mapper.createObjectNode();
//        object.put("command", "reaad");
//        object.set("card", mapper.valueToTree(card));
//        output.add(object);
        //object.put("command", "reaad");
        //Deck deck1 = null;
        //deck1.setDeck(deck.get(0));
        //object.set("deck", mapper.valueToTree(deck_1));
        //output.add(object);


        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(filePath2), output);
    }
}