package ssm;

/**
 * This class provides setup constants for initializing the application
 * that are NOT language dependent.
 * 
 * @author McKilla Gorilla & _____________
 */
public class StartupConstants {
    // WE'LL LOAD ALL THE UI AND LANGUAGE PROPERTIES FROM FILES,
    // BUT WE'LL NEED THESE VALUES TO START THE PROCESS
    public static String PROPERTY_TYPES_LIST = "property_types.txt";
    public static String UI_PROPERTIES_FILE_NAME = "properties_EN.xml";
    public static String UI_PROPERTIES_FILE_NAME_CH = "properties_CH.xml";
    public static String PROPERTIES_SCHEMA_FILE_NAME = "properties_schema.xsd";
    public static String PATH_DATA = "./data/";
    public static String PATH_SLIDE_SHOWS = PATH_DATA + "ePortfolios/";
    public static String PATH_IMAGES = "./images/";
    public static String PATH_ICONS = PATH_IMAGES + "icons/";
   public static String PATH_SLIDE_SHOW_IMAGES = PATH_IMAGES + "ePortfolio_images/";
   public static String PATH_EPORTFOLIO_IMAGES = PATH_IMAGES + "ePortfolio_images/";
    
    public static String PATH_CSS = "ssm/style/";
     public static String STYLE_SHEET_EPORTFOLIO_UI = PATH_CSS + "ePortfolioMakerStyle.css";
    public static String STYLE_SHEET_SLIDESHOW_UI = PATH_CSS + "SlideShowMakerStyle.css";
    public static String STYLE_SHEET_DIALOGBOX_UI=PATH_CSS+"dialogboxStyle.css";
    // HERE ARE THE LANGUAGE INDEPENDENT GUI ICONS
    public static String ICON_NEW_EPORTFOLIO_SHOW = "New.png";
    public static String ICON_LOAD_EPORTFOLIO_SHOW = "Load.png";
    public static String ICON_SAVE_EPORTFOLIO_SHOW = "Save.png";
    public static String ICON_SAVE_AS_EPORTFOLIO_SHOW= "save_as.png";
    public static String ICON_VIEW_EPORTFOLIO_SHOW = "View.png";
    public static String ICON_EXIT = "Exit.png";
    
    public static String ICON_ADD_PAGE = "Add_Page.png";
    public static String ICON_REMOVE_PAGE = "Remove_page.png";
    public static String ICON_SITE_VIWER = "Site_viewer.png";
    public static String ICON_WORKSPACE_MODE = "work_space_mode.png";
    
    public static String ICON_SELECT_BANNER_IMAGE = "Select_Banner.png";
    public static String ICON_EDIT_HEADER="Edit_header.png";
    public static String ICON_ADD_IMAGE = "Add_Image.png";
    public static String ICON_ADD_VIDEO = "Add_Video.png";
    public static String ICON_ADD_TEXT = "Add_Text.png";
    public static String ICON_ADD_HYPERLINKER = "Add_Hyperlinker.png";
    public static String ICON_ADD_SLIDESHOW = "Add_Slideshow.png";
     public static String ICON_ADD_LIST = "Add_List.png";
    
    public static String ICON_DELETE_COMPONENT = "Delete_Component.png";   
    public static String ICON_SELECT_COMPONENT="Select_Component.png";
    public static String ICON_VIEW_PAGE="Globe.png";
        
    public static String ICON_NEW_SLIDE_SHOW = "New.png";
    public static String ICON_LOAD_SLIDE_SHOW = "Load.png";
    public static String ICON_SAVE_SLIDE_SHOW = "Save.png";    
    public static String ICON_VIEW_SLIDE_SHOW = "View.png";    
    public static String ICON_ADD_SLIDE = "Add.png";
    public static String ICON_REMOVE_SLIDE = "Remove.png";
    public static String ICON_MOVE_UP = "MoveUp.png";
    public static String ICON_MOVE_DOWN = "MoveDown.png";
    public static String ICON_PREVIOUS = "Previous.png";
    public static String ICON_NEXT = "Next.png";
    public static String ICON_WINDOW="Hello_Kitty_logo.png";   
    
    
    
    
    
    
    
    // @todo
    public static String    DEFAULT_SLIDE_IMAGE = "DefaultStartSlide.png";
    public static int	    DEFAULT_THUMBNAIL_WIDTH = 200;
    public static int	    DEFAULT_SLIDE_SHOW_HEIGHT = 500;    
    //CSS STYLE FOR THE MESSAGE BOX
    public static String    CSS_CLASS_LANG_DIALOG_PANE = "lang_dialog_pane";
    public static String    CSS_CLASS_LANG_PROMPT = "lang_prompt";
    public static String    CSS_CLASS_LANG_COMBO_BOX = "lang_combo_box";
    public static String    CSS_CLASS_LANG_OK_BUTTON = "lang_ok_button";
    public static String    CSS_CLASS_LANG_TEXT_FIELD="text_field";
    // CSS STYLE SHEET CLASSES
    public static String    CSS_CLASS_VERTICAL_TOOLBAR_BUTTON = "vertical_toolbar_button";
    public static String    CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON = "horizontal_toolbar_button";
    public static String    CSS_CLASS_SLIDE_SHOW_EDIT_VBOX = "slide_show_edit_vbox";
    
     //public static String    CSS_CLASS_SELECTED_SLIDE_EDIT_VIEW = "selected_slide_edit_view";
    public static final String    CSS_CLASS_SLIDE_EDIT_VIEW = "slide_edit_view"; 
    public static final String    CSS_CLASS_TWO_SLIDE_EDIT_VIEW= "slide_two_edit_view";
    public static final String    CSS_CLASS_THREE_SLIDE_EDIT_VIEW= "slide_three_edit_view";
    public static final String    CSS_CLASS_FOUR_SLIDE_EDIT_VIEW= "slide_four_edit_view";
     public static final String    CSS_CLASS_FIVE_SLIDE_EDIT_VIEW= "slide_five_edit_view";
    public static final String    CSS_CLASS_SLIDESHOW_MAKER_VIEW="slideshow_maker_view";
    // UI LABELS
    public static String    LABEL_SLIDE_SHOW_TITLE = "slide_show_title";
    
}
