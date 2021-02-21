package WebView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class Controller implements Initializable {

    String htlink = "";
    @FXML
    Button btnGo;
    @FXML
    TextField addressBar;
    @FXML
    WebView web;
    WebView current;
    Button refresh;

    WebEngine engine, currentEngine;

    Tab currentTab;
    String addressLink = "";

    @FXML
    TabPane tabs;

    @FXML
    Button addNewTab;

    String refreshLink = "";
    Button back;

    @FXML
    Button forward, viewSource;

    @FXML
    public void go(ActionEvent event) throws IOException {
        current = new WebView();
        currentEngine = new WebEngine();
        current.setPrefSize(web.getPrefWidth(), web.getPrefHeight()); //set preferences from the first tab

        //get the selected tab
        currentTab = tabs.getSelectionModel().getSelectedItem();
        current = (WebView) currentTab.getContent(); //get the webview associated with the tab

        currentEngine = current.getEngine();

        addressLink = addressBar.getText().toString();

        if (!addressLink.contains("http")) {

            if (checkUrlExists("http://" + addressLink)) {
                addressLink = "http://" + addressLink;
            } else {
                addressLink = "https://duckduckgo.com/?t=ffab&q=" + addressLink;
            }
        }

        //load site
        currentEngine.load(addressLink);
        currentEngine.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:84.0) Gecko/20100101 Firefox/84.0  ");

        //if site has finished loading!
        addressBar.setText(currentEngine.getLocation().toString());
        setName();
        listenToChange();
    }

    /*Start of browser*/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentEngine = web.getEngine();
        currentEngine.load("https://broow.neocities.org");
        currentEngine.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.86 Safari/537.36");
        currentTab = tabs.getSelectionModel().getSelectedItem();

        setName();

        System.out.println(currentTab.onSelectionChangedProperty().getName());
        updateAdBar(currentEngine.getLocation().toString());

    }

    /*Listen to changes in activity*/
    public void listenToChange() {
        tabs.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {

            currentTab = tabs.getSelectionModel().getSelectedItem();
            current = (WebView) currentTab.getContent(); //get the webview associated with current tab
            currentEngine = current.getEngine();
            System.out.println(currentEngine.getTitle().toString());
            setTabName(currentEngine.getTitle().toString());
            updateAdBar(currentEngine.getLocation().toString());

        });
        currentEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            {
                if (oldState != newState) {
                    updateAdBar(currentEngine.getLocation().toString());

                }

            }
        });

        setName();
    }

    /*Add Tab Button*/
    @FXML
    public void addTab(ActionEvent event) {

        Tab tab = new Tab();
        tab.setText("New Tab");

        WebView newTab = new WebView();
        WebEngine newTabEngine = newTab.getEngine();
        newTabEngine.load("https://broow.neocities.org");
        tab.setContent(newTab);
        tabs.getTabs().add(tab);
        updateAdBar(currentEngine.getLocation().toString());
    }

    /*Add new Tab for source viewing : Default is Google for now*/
    public void addTab(String code) {

        Tab tab = new Tab();
        tab.setText("Source Code");

        WebView newTab = new WebView();
        WebEngine newTabEngine = newTab.getEngine();
        newTabEngine.loadContent(code, "text/plain");
        tab.setContent(newTab);
        tabs.getTabs().add(tab);
        updateAdBar(currentEngine.getLocation().toString());
    }

    /*Set Tab Name*/
    public void setTabName(String name) {
        currentTab.setText(name);
        updateAdBar(currentEngine.getLocation().toString());

    }

    /*Refresh Page*/
    public void refresh() {
        currentEngine.reload();
        setName();
        updateAdBar(currentEngine.getLocation().toString());

    }

    /*Set Name of Tab*/
    public void setName() {
        currentEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {

                String name = currentEngine.titleProperty().toString();
                setTabName(name.substring(name.indexOf("value: ") + "value: ".length(), name.lastIndexOf("-")));
            }
        });
    }

    /*Update the address bar*/
    public void updateAdBar(String address) {
        addressBar.setText(address);
    }

    @FXML
    public void viewSource() throws IOException {
        Tab tab = new Tab();
        tab.setText("Github");

        WebView newTab = new WebView();
        WebEngine newTabEngine = newTab.getEngine();
        newTabEngine.load("https://github.com/slaxeea");
        tab.setContent(newTab);
        tabs.getTabs().add(tab);
        updateAdBar(currentEngine.getLocation().toString());
    }

    /*Check if URL exists */
    public boolean checkUrlExists(String url) throws IOException {
        URL urlToCheck = new URL(url);
        HttpURLConnection huc = (HttpURLConnection) urlToCheck.openConnection();
        huc.setRequestMethod("GET");
        int code;
        try {
            huc.connect();
            code = huc.getResponseCode();
        } catch (Exception e) //url does not exist
        {
            code = -1232; //please don't judge
        }

        if (code == -1232) {
            return false;
        }
        return true;
    }
}
