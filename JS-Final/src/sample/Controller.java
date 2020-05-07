package sample;

import DungeonFiles.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

import javafx.event.ActionEvent;
import javafx.stage.Stage;

import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static javafx.scene.paint.Color.*;

public class Controller implements Initializable {
    @FXML private Canvas mainCanvas;
    @FXML private AnchorPane mainFrame;
    @FXML private Button atkBtn, useItemBtn, moveBtn,nextBtn,usePropBtn;
    @FXML private TextArea resultsTxb;
    @FXML private ListView<String> inventoryLs;
    @FXML private Label maxHpLbl, curHpLbl;

    private Room curRoom;
    private PlayerObject player;
    private int roomIndex,killCount,lootCount;
    private SmallMap map;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //ask the player their name
        String name = JOptionPane.showInputDialog(null,"Enter Name:");
        if(name.equals("")){
            name = "player";
        }
        resultsTxb.setText("Results:");
        killCount = 0;
        lootCount = 0;

        //makes and set map and room
        roomIndex = 0;
        map = new SmallMap(false);
        curRoom = map.getMap().get(roomIndex);

        //set player
        player = new PlayerObject(name, 50);
        player.setCurHP(50);
        player.setxPos(1);
        player.setyPos(1);
        player.setCurState(player.getAliveState());
        //free potion
        player.getItems().add(new Consumable("Healing potion", 3, false, 6));

        //set up the game
        updateUI();


    }

    public void updateUI(){
        //set up inventory
        inventoryLs.getItems().clear();
        for(InventoryItem item: player.getItems()){
            inventoryLs.getItems().add(item.getName());
        }
        //check if all monsters are dead
        nextBtn.setVisible(false);
        int count = 0;
        for(int i = 0;i<curRoom.getMonsters().size();i++){
            if(map.getGoblin(roomIndex, i).getCurState() == map.getGoblin(roomIndex, i).getDeadState()){
                count++;
            }
        }
        //if all monsters are dead allow then to change rooms
        if(count == curRoom.getMonsters().size()){
            nextBtn.setVisible(true);
        }
        curHpLbl.setText(""+player.getCurHP());
        maxHpLbl.setText(""+player.getMaxHP());
        if(player.getCurState() == player.getDeadState()){
            JOptionPane.showMessageDialog( null, player.getName() + " killed: "+ killCount + " Goblins\n"+player.getName()+" Looted "+ lootCount + " items","Game Over",JOptionPane.PLAIN_MESSAGE);
            Stage stage = (Stage)mainFrame.getScene().getWindow();
            stage.close();
        }

        //set up board
        DrawRoom();
        DrawObjects();
    }


    public void DrawObjects(){

        GraphicsContext gc = mainCanvas.getGraphicsContext2D();
        //sizing based on current room
        int objHeight = curRoom.getColSize() / 2;
        int objWidth = curRoom.getRowSize() / 2;
        int gridPlaceX = curRoom.getRowSize() / 4;
        int gridPlaceY = curRoom.getColSize() / 4;

        //draw props
        gc.setFill(BROWN);
        for (PropObject prop: curRoom.getProps()){
            if (prop.getCurState() != prop.getUsedState() ) {
                gc.fillRect(((prop.getxPos()-1)*curRoom.getRowSize())+ gridPlaceX, ((prop.getyPos()-1)*curRoom.getRowSize())+ gridPlaceY, objHeight, objWidth);
            }
        }

        //draw enemies
        gc.setFill(GREEN);
        for (int i = 0; i< curRoom.getMonsters().size();i++) {
            if (map.getGoblin(roomIndex, i).getCurState() != map.getGoblin(roomIndex, i).getDeadState() ) {
                gc.fillOval(((map.getGoblin(roomIndex, i).getxPos() - 1) * curRoom.getRowSize()) + gridPlaceX, ((map.getGoblin(roomIndex, i).getyPos() - 1) * curRoom.getRowSize()) + gridPlaceY, objHeight, objWidth);
            }
        }

        //draw player
        gc.setFill(BLUE);
        gc.fillOval(((player.getxPos()-1)*curRoom.getRowSize())+ gridPlaceX, ((player.getyPos()-1)*curRoom.getRowSize())+ gridPlaceY, objHeight, objWidth);
    }


    public void DrawRoom(){
        GraphicsContext gc = mainCanvas.getGraphicsContext2D();
        //trace frame
        gc.clearRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());
        gc.strokeRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());

        gc.setStroke(GRAY);
        // vertical lines
        for(int i = 0 ; i < mainCanvas.getWidth() ; i+=curRoom.getColSize()){
            gc.strokeLine(i, 0, i, mainCanvas.getWidth());
        }
        // horizontal lines
        for(int i = 0 ; i < mainCanvas.getHeight() ; i+=curRoom.getRowSize()){
            gc.strokeLine(0, i, mainCanvas.getHeight(), i);
        }
    }

    public void GoblinTurn(){
        ArrayList<String> options = new ArrayList<String>();
        int count = curRoom.getMonsters().size();
        int i = 0;

        LivingObject livingObject = map.getGoblin(roomIndex, i);
        if(livingObject.getCurState() == livingObject.getDeadState()) {
            while (livingObject.getCurState() == livingObject.getDeadState()) {
                i++;
                if (i == count) {
                    break;
                }
                livingObject = map.getGoblin(roomIndex, i);

            }
        }
        if(livingObject.getCurState() != livingObject.getDeadState()) {
            if (0 < player.getyPos() - 1) {
                options.add("Move up");
            }
            if (0 < player.getxPos() - 1) {
                options.add("Move left");
            }
            if ((mainCanvas.getWidth() / curRoom.getColSize() + .1) > player.getxPos() + 1) {
                options.add("Move right");
            }
            if ((mainCanvas.getHeight() / curRoom.getRowSize() + .1) > player.getyPos() + 1) {
                options.add("Move down");
            }

            int rand = (int) (options.size() * Math.random());
            switch (options.get(rand)) {
                case "Move up":
                    livingObject.setyPos(player.getyPos() - 1);
                    livingObject.setxPos(player.getxPos());
                    break;
                case "Move left":
                    livingObject.setxPos(player.getxPos() - 1);
                    livingObject.setyPos(player.getyPos());
                    break;
                case "Move right":
                    livingObject.setxPos(player.getxPos() + 1);
                    livingObject.setyPos(player.getyPos());
                    break;
                case "Move down":
                    livingObject.setyPos(player.getyPos() + 1);
                    livingObject.setyPos(player.getxPos());
                    break;
            }
            livingObject.attack(player);
            map.setGoblin(roomIndex, i, livingObject);
            resultsTxb.setText(resultsTxb.getText() + "\n" + livingObject.getName() + " did " + livingObject.getDmgRoll() + " damage to you.");
            updateUI();
        }


    }



    @FXML void handleUsePropBtn(ActionEvent event){
        ArrayList<String> options = new ArrayList<String>();
        ArrayList<PropObject> use = new ArrayList<PropObject>();
        for (PropObject prop: curRoom.getProps()) {
            if(prop.getxPos() == player.getxPos() && prop.getyPos() == player.getyPos() - 1){
                if (prop.getCurState() != prop.getUsedState() ) {
                    options.add("Use above");
                    use.add(prop);
                }
            }
            if(prop.getxPos() == player.getxPos() - 1 && prop.getyPos() ==  player.getyPos()){
                if (prop.getCurState() != prop.getUsedState() ) {
                    options.add("Use left");
                    use.add(prop);
                }

            }
            if(prop.getxPos() == player.getxPos() + 1 && prop.getyPos() == player.getyPos()){
                if (prop.getCurState() != prop.getUsedState() ) {
                    options.add("Use right");
                    use.add(prop);
                }

            }
            if(prop.getxPos() == player.getxPos() && prop.getyPos() == player.getyPos() + 1){
                if (prop.getCurState() != prop.getUsedState() ) {
                    options.add("Use below");
                    use.add(prop);
                }
            }
        }
        int choice = JOptionPane.showOptionDialog(null,"Select which prop to use.", "Use Prop",JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options.toArray(), null);
        if (choice != -1){
            if(use.get(choice).getItem() == null){
                InventoryItem envokeState = use.get(choice).useProp();
                resultsTxb.setText(resultsTxb.getText() + "\nNo loot found");
            }else {
                player.getItems().add(player.use(use.get(choice)));
                lootCount++;
            }
            updateUI();
        }
    }


    @FXML void handleAttackBtn(ActionEvent event){
        ArrayList<String> options = new ArrayList<String>();
        ArrayList<Integer> indexSel = new ArrayList<Integer>();
        for (int i = 0; i< curRoom.getMonsters().size();i++)  {
            LivingObject gob = map.getGoblin(roomIndex, i);
            if(gob.getxPos() == player.getxPos() && gob.getyPos() == player.getyPos() - 1){
                if (gob.getCurState() != gob.getDeadState() ) {
                    options.add("Attack above");
                    indexSel.add(i);
                }
            }
            if(gob.getxPos() == player.getxPos() - 1 && gob.getyPos() ==  player.getyPos()){
                if (gob.getCurState() != gob.getDeadState() ) {
                    options.add("Attack left");
                    indexSel.add(i);
                }

            }
            if(gob.getxPos() == player.getxPos() + 1 && gob.getyPos() == player.getyPos()){
                if (gob.getCurState() != gob.getDeadState() ) {
                    options.add("Attack right");
                    indexSel.add(i);
                }
            }
            if(gob.getxPos() == player.getxPos() && gob.getyPos() == player.getyPos() + 1){
                if (gob.getCurState() != gob.getDeadState() ) {
                    options.add("Attack below");
                    indexSel.add(i);
                }
            }
        }
        int choice = JOptionPane.showOptionDialog(null,"Select a monster to attack.", "Attack",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options.toArray(), null);

        if(choice != -1) {
            LivingObject goblin = map.getGoblin(roomIndex, indexSel.get(choice));
            player.attack(goblin);
            map.setGoblin(roomIndex, indexSel.get(choice), goblin);
            resultsTxb.setText(resultsTxb.getText() + "\n"+player.getName() +" did " + player.getDmgRoll()+" Damage to "+ goblin.getName());
            if(goblin.getCurState() == goblin.getDeadState()){
                resultsTxb.setText(resultsTxb.getText() + "\nYou killed a "+ goblin.getName()+"!");
                killCount++;
            }
            updateUI();
            GoblinTurn();
        }
    }
    @FXML void handleMoveBtn(ActionEvent event){
        ArrayList<String> options = new ArrayList<String>();
        if(0 < player.getyPos() - 1){
            options.add("Move up");
        }
        if(0 < player.getxPos() - 1){
            options.add("Move left");
        }
        if((mainCanvas.getWidth()/curRoom.getColSize()+.1)>player.getxPos() + 1){
            options.add("Move right");
        }
        if((mainCanvas.getHeight()/curRoom.getRowSize()+.1)>player.getyPos() + 1){
            options.add("Move down");
        }
        int choice = JOptionPane.showOptionDialog(null,"Which direction would you like to move.", "Movement",JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options.toArray(), null);
        if(choice != -1) {
            switch (options.get(choice)) {
                case "Move up":
                    player.setyPos(player.getyPos() - 1);
                    break;
                case "Move left":
                    player.setxPos(player.getxPos() - 1);
                    break;
                case "Move right":
                    player.setxPos(player.getxPos() + 1);
                    break;
                case "Move down":
                    player.setyPos(player.getyPos() + 1);
                    break;
            }
        }
        updateUI();
    }
    @FXML void handleUseItemBtn(ActionEvent event){
        if(inventoryLs.getSelectionModel().getSelectedIndex() != -1) {
            ArrayList<String> option = new ArrayList<String>();
            option.add("Self");
            option.add("Enemy");
            int choice = JOptionPane.showOptionDialog(null,"Use on: ", "Use item",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, option.toArray(), null);

            int i = inventoryLs.getSelectionModel().getSelectedIndex();
            switch (option.get(choice)){
                case "Self":
                    player.useItem(i);
                    Consumable u = (Consumable) player.getItems().get(i);
                    if(u.getUses() == 0){
                        player.getItems().remove(i);
                    }
                    break;
                case "Enemy":
                    ArrayList<String> options = new ArrayList<String>();
                    ArrayList<Integer> indexSel = new ArrayList<Integer>();
                    for (int j = 0; j< curRoom.getMonsters().size();j++)  {
                        LivingObject gob = map.getGoblin(roomIndex, j);
                        if(gob.getxPos() == player.getxPos() && gob.getyPos() == player.getyPos() - 1){
                            if (gob.getCurState() != gob.getDeadState() ) {
                                options.add("Use above");
                                indexSel.add(j);
                            }
                        }
                        if(gob.getxPos() == player.getxPos() - 1 && gob.getyPos() ==  player.getyPos()){
                            if (gob.getCurState() != gob.getDeadState() ) {
                                options.add("Use left");
                                indexSel.add(j);
                            }

                        }
                        if(gob.getxPos() == player.getxPos() + 1 && gob.getyPos() == player.getyPos()){
                            if (gob.getCurState() != gob.getDeadState() ) {
                                options.add("Use right");
                                indexSel.add(j);
                            }
                        }
                        if(gob.getxPos() == player.getxPos() && gob.getyPos() == player.getyPos() + 1){
                            if (gob.getCurState() != gob.getDeadState() ) {
                                options.add("Use below");
                                indexSel.add(j);
                            }
                        }
                    }
                    choice = JOptionPane.showOptionDialog(null,"Select a monster to use the item on.", "Use item",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options.toArray(), null);

                    if(choice != -1) {
                        LivingObject goblin = map.getGoblin(roomIndex, indexSel.get(choice));
                        player.useItem(i,goblin);
                        map.setGoblin(roomIndex, indexSel.get(choice), goblin);
                        if(goblin.getCurState() == goblin.getDeadState()) {
                            resultsTxb.setText(resultsTxb.getText() + "\nYou killed a " + goblin.getName() + "!");
                            killCount++;
                        }
                        Consumable ut = (Consumable) player.getItems().get(i);
                        if(ut.getUses() == 0){
                            player.getItems().remove(i);
                        }
                    }
                    break;
            }
            updateUI();

        }
    }
    @FXML void handleNextBtn(ActionEvent event){
        if(roomIndex+1 < map.getMap().size()) {
            roomIndex++;
            curRoom = map.getMap().get(roomIndex);
            player.setxPos(1);
            player.setyPos(1);
            updateUI();
        }
    }
}
