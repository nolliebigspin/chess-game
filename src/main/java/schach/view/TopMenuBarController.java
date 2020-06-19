package schach.view;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class TopMenuBarController {

    private MenuBar topMenuBar;
    private Menu menuGame;
    private MenuItem buttonRestart;
    private MenuItem buttonGiveUp;
    private MenuItem buttonSave;
    private MenuItem buttonOpenStartMenu;
    private MenuItem buttonManual;
    private MenuItem buttonAbout;

    public TopMenuBarController(MenuBar topMenuBar) {
        this.topMenuBar = topMenuBar;
    }

}
