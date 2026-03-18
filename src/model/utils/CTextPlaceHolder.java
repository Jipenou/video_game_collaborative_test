package model.utils;

/**
 * This class is a database of string constant to use in views
 */
public class CTextPlaceHolder {
    public static final String ADMINISTRATION = "administration";
    public static final String AJOUTER = "ajouter";
    public static final String APPLICATION_NAME = "video Game Platform";
    public static final String AUCUN = "aucun";

    public static final String BIENVENUE = "bienvenue";
    public static final String BLOQUER = "bloquer";
    public static final String BUILD = "build";

    public static final String CATEGORIE = "catégorie";
    public static final String CHOISIR = "choisir";
    public static final String CONDITION_S = "conditions";
    public static final String CONNEXION = "connexion";
    public static final String CONNECTER = "connecter";
    public static final String COMPTE = "compte";
    public static final String COMMENTAIRE = "commentaire";
    public static final String COLLECTION = "collection";
    public static final String CREER = "créer";

    public static final String DATE = "date";
    public static final String DECONNECTER = "déconnecter";
    public static final String DEBLOQUER = "débloquer";
    public static final String DESINSCRIRE = "désinscrire";
    public static final String DISPONIBLE = "disponible";

    public static final String EDITEUR = "éditeur";
    public static final String ENREGISTRER = "enregistrer";
    public static final String EVALUATION = "évaluation";
    public static final String EVALUATION_S = "évaluations";

    public static final String GENRE = "genre";

    public static final String HEURE_S = "heures";

    public static final String INFORMATION_S = "informations";
    public static final String INUTILE = "inutile";
    public static final String INVITE = "invité";

    public static final String JOUEUR = "joueur";
    public static final String JETON_S = "jetons";
    public static final String JEU = "jeu";
    public static final String JEU_S = "jeux";

    public static final String LISTE = "liste";

    public static final String MENU = "menu";
    public static final String MOYENNE = "moyenne";

    public static final String NOM = "nom";
    public static final String NOMBRE = "nombre";
    public static final String NOTE = "note";
    public static final String NOTE_S = "notes";
    public static final String NEUTRE = "neutre";

    public static final String PROMOUVOIR = "promouvoir";
    public static final String PRINCIPAL = "principal";
    public static final String PLATEFORME = "plateforme";
    public static final String PLATEFORME_S = "plateformes";
    public static final String PROFIL = "profil";
    public static final String PSEUDO = "pseudo";
    public static final String PAGE = "page";

    public static final String RECHARGER = "recharger";
    public static final String RETIRER = "retirer";

    public static final String SUPPRIMER = "supprimer";
    public static final String SIGNALER = "signaler";
    public static final String SIGNALEMENT = "signalement";
    public static final String SIGNALEMENT_S = "signalements";
    public static final String SCORE = "score";

    public static final String TEST = "test";
    public static final String TEST_S = "test";
    public static final String TEXTE = "texte";
    public static final String TESTER = "tester";
    public static final String TEMPS = "temps";
    public static final String TESTEUR = "testeur";

    public static final String UTILISATEUR = "utilisateur";
    public static final String UTILISATEUR_S = "utilisateurs";
    public static final String UTILE = "utile";

    public static final String VALIDER = "valider";
    public static final String VERSION = "version";
    public static final String VOIR = "voir";
    public static final String VIDEO = "vidéo";
    public static final String VIDEO_GAME = "jeu vidéo";

    /**
     *
     * @param text the string to capitalize
     * @return the text with the first letter in capital letter
     */
    public static String capitalize(String text) {
        if (text == null || text.isEmpty()) return text;
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }
}
