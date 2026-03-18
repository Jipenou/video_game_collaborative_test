package view.videoGame;

import controller.CVideoGameController;
import model.utils.CDocumentAdapter;
import model.utils.CTextPlaceHolder;
import model.videoGame.CPlatform;
import model.videoGame.CVideoGame;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * This class represent the frame that displayed all the video game in the application
 */
public class CVideoGamesView extends JFrame {

    /** the list of all the video game */
    private final List<CVideoGame> allGames;

    /** the default list to display (for the filters) */
    private final DefaultListModel<CVideoGame> listModel;

    /** the list of games */
    private final JList<CVideoGame> list;

    public CVideoGamesView(CVideoGameController videoGameController) {

        this.allGames = videoGameController.getController().getDatabase().getAllVideoGames();
        this.listModel = new DefaultListModel<>();
        this.list = new JList<>(listModel);

        setTitle(CTextPlaceHolder.capitalize(CTextPlaceHolder.LISTE) + " des " + CTextPlaceHolder.JEU_S);
        setSize(1000, 800);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // search bar
        filterPanel.add(new JLabel("Recherche :"));
        JTextField searchField = new JTextField(15);
        filterPanel.add(searchField);

        // platform filter
        filterPanel.add(new JLabel("Plateforme :"));
        JComboBox<String> platformBox = new JComboBox<>();
        platformBox.addItem("Toutes");
        getAllPlatformNames().forEach(platformBox::addItem);
        filterPanel.add(platformBox);

        // category filter
        filterPanel.add(new JLabel("Catégorie :"));
        JComboBox<String> categoryBox = new JComboBox<>();
        categoryBox.addItem("Toutes");
        getAllCategories().forEach(categoryBox::addItem);
        filterPanel.add(categoryBox);

        // reset filters
        JButton resetButton = new JButton("Réinitialiser");
        filterPanel.add(resetButton);

        panel.add(filterPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(list), BorderLayout.CENTER);

        JButton viewButton = new JButton(CTextPlaceHolder.capitalize(CTextPlaceHolder.VOIR) + " " + CTextPlaceHolder.INFORMATION_S);
        panel.add(viewButton, BorderLayout.SOUTH);

        searchField.getDocument().addDocumentListener(new CDocumentAdapter() {
            @Override
            public void update() {
                applyFilters(searchField.getText(), (String) platformBox.getSelectedItem(), (String) categoryBox.getSelectedItem());
            }
        });

        platformBox.addActionListener(_ ->
                applyFilters(searchField.getText(), (String) platformBox.getSelectedItem(), (String) categoryBox.getSelectedItem()));

        categoryBox.addActionListener(_ ->
                applyFilters(searchField.getText(), (String) platformBox.getSelectedItem(), (String) categoryBox.getSelectedItem()));

        resetButton.addActionListener(_ -> {
            searchField.setText("");
            platformBox.setSelectedIndex(0);
            categoryBox.setSelectedIndex(0);
        });

        viewButton.addActionListener(_ -> {
            CVideoGame videoGame = list.getSelectedValue();
            if (videoGame != null) {
                videoGameController.viewInfoGameFrame(videoGame);
            }
        });

        applyFilters("", "Toutes", "Toutes");
        add(panel);
    }

    /** apply filter selected */
    private void applyFilters(String search, String platform, String category) {
        listModel.clear();
        allGames.stream()
                .filter(g -> search.isBlank() || g.getName().toLowerCase().contains(search.toLowerCase()))
                .filter(g -> "Toutes".equals(platform) || g.getPlatforms().stream().anyMatch(p -> p.getName().equals(platform)))
                .filter(g -> "Toutes".equals(category) || g.getCategory().equals(category))
                .forEach(listModel::addElement);
    }

    /**
     *
     * @return all the platforms for the video game
     */
    private List<String> getAllPlatformNames() {
        return allGames.stream()
                .flatMap(g -> g.getPlatforms().stream())
                .map(CPlatform::getName)
                .distinct()
                .sorted()
                .toList();
    }

    /**
     *
     * @return all the category in all videos games
     */
    private List<String> getAllCategories() {
        return allGames.stream()
                .map(CVideoGame::getCategory)
                .distinct()
                .sorted()
                .toList();
    }
}