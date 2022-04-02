package a8.ybond.javafxtodo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class TodoController {
    private String selectedTab;
    private final ObservableList<Item> items = FXCollections.observableArrayList();

    @FXML
    TextField txtItem;

    // we need to list the added item inside ListView

    @FXML
    ListView<Item> listItems;

    // to bind txtItem to listItems
    @FXML
    public void initialize()
    {
        listItems.setItems(items);
    }

    // implementing a method onTabChanged

    @FXML
    private void onTabChanged(Event e)
    {
        // storing our tab that is changed
        // we have to cast Tab because it is coming from an event
        Tab tab = (Tab) e.getTarget();
        // we want to print out only the tab which is selected
        if(tab.isSelected())
        {
            selectedTab = tab.getText();
            // System.out.println(tab.getText());
            // This is where to get list of items using the selected tab

            // before getting the items, we would clear the ListView
            items.clear();
            APIBridge.getList(selectedTab, items);
        }
    }

    // if double-clicked, the item is going to be deleted

    @FXML
    private void onItemClicked(MouseEvent click)
    {
        if(click.getClickCount() == 2)
        {
            Item selectedItem = listItems.getSelectionModel().getSelectedItem();
            items.remove(selectedItem);
            APIBridge.deleteItem(selectedTab, selectedItem);

        }
    }

    @FXML
    private void onAddItem(Event e)
    {
        Item item = new Item(txtItem.getText());
        items.add(item);
        txtItem.setText(null);
        APIBridge.addItem(selectedTab, item);

    }

}