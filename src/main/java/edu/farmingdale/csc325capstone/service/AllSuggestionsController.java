package edu.farmingdale.csc325capstone;

import edu.farmingdale.csc325capstone.model.PreBuilt;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.List;

public class AllSuggestionsController {

    @FXML private ImageView img1, img2, img3;
    @FXML private Label name1, name2, name3;
    @FXML private Label specs1, specs2, specs3;
    @FXML private Label price1, price2, price3;

    private static List<PreBuilt> sharedTopThree;

    public static void setTopThree(List<PreBuilt> pcs) {
        sharedTopThree = pcs;
    }

    @FXML
    public void initialize() {
        if (sharedTopThree == null || sharedTopThree.isEmpty()) return;

        ImageView[] imgs = {img1, img2, img3};
        Label[] names = {name1, name2, name3};
        Label[] specs = {specs1, specs2, specs3};
        Label[] prices = {price1, price2, price3};

        for (int i = 0; i < sharedTopThree.size(); i++) {
            PreBuilt pc = sharedTopThree.get(i);
            names[i].setText(pc.getName());
            specs[i].setText("GPU: " + pc.getGPU() + "\nCPU: " + pc.getCPU()
                    + "\nRAM: " + pc.getRAM() + "  |  Storage: " + pc.getStorage());
            prices[i].setText("$" + pc.getPrice());
            if (pc.getImageURL() != null && !pc.getImageURL().isEmpty()) {
                imgs[i].setImage(new Image(pc.getImageURL(), true));
            }
        }
    }

    @FXML private void onDeal1Clicked() { openLink(sharedTopThree.get(0).getLink()); }
    @FXML private void onDeal2Clicked() { openLink(sharedTopThree.get(1).getLink()); }
    @FXML private void onDeal3Clicked() { openLink(sharedTopThree.get(2).getLink()); }

    private void openLink(String url) {
        if (url != null && !url.isEmpty()) {
            try { java.awt.Desktop.getDesktop().browse(new java.net.URI(url)); }
            catch (Exception e) { e.printStackTrace(); }
        }
    }

    @FXML
    private void onBackClicked() throws Exception {
        HelloApplication.setRoot("buildQuestionnaireView.fxml");
    }

    @FXML
    private void onHomeClicked() throws Exception {
        HelloApplication.setRoot("homeView.fxml");
    }
}