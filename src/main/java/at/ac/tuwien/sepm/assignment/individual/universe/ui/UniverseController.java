package at.ac.tuwien.sepm.assignment.individual.universe.ui;

import at.ac.tuwien.sepm.assignment.individual.universe.service.UniverseService;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

public class UniverseController {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final double VISIBLE = 1.0;
    private static final double INVISIBLE = 0.0;

    private final FadeTransition fadeOutTransition = new FadeTransition(Duration.seconds(1));
    private final FadeTransition fadeInTransition = new FadeTransition(Duration.seconds(3));

    private final UniverseService universeService;

    @FXML
    private AnchorPane productsPane;

    @FXML
    private AnchorPane tablesPane;

    @FXML
    private AnchorPane ordersPane;

    public UniverseController(UniverseService universeService) {
        this.universeService = universeService;

        fadeOutTransition.setFromValue(VISIBLE);
        fadeOutTransition.setToValue(INVISIBLE);
        fadeInTransition.setFromValue(INVISIBLE);
        fadeInTransition.setToValue(VISIBLE);
    }

    @FXML
    void initialize() {
        assert productsPane != null : "fx:id=\"ProductsPane\" was not injected: check your FXML file 'universe.fxml'.";
        assert tablesPane != null : "fx:id=\"TablesPane\" was not injected: check your FXML file 'universe.fxml'.";
        assert ordersPane != null : "fx:id=\"OrdersPane\" was not injected: check your FXML file 'universe.fxml'.";

        addAnchorPane("products.fxml", productsPane);
        addAnchorPane("ordered.fxml", tablesPane);
        addAnchorPane("invoice.fxml", ordersPane);
    }

    private void addAnchorPane(String fxml, AnchorPane parent){
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/" + fxml));

        AnchorPane pane = null;
        try {
            pane = (AnchorPane) loader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }
        AnchorPane.setTopAnchor(pane, 0.0);
        AnchorPane.setLeftAnchor(pane, 0.0);
        AnchorPane.setBottomAnchor(pane, 0.0);
        AnchorPane.setRightAnchor(pane, 0.0);

        parent.getChildren().add(pane);
    }
}
