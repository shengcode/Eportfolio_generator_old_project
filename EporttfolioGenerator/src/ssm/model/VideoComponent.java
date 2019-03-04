package ssm.model;

import java.io.File;
import java.net.URL;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javax.json.Json;
import javax.json.JsonObject;
import properties_manager.PropertiesManager;
import static ssm.LanguagePropertyType.NOT_A_IMAGE;
import static ssm.LanguagePropertyType.NOT_A_IMAGE_NOTICE;
import static ssm.file.SlideShowFileManager.SLASH;
import ssm.view.AddVideoDialogBox;

/**
 * *
 * @author shengchun
 */
public class VideoComponent extends Components {

    static final String TYPE = "video";
    String videoFileName;
    String videoPath;
    double videoHeight;
    double videoWidth;
    String videoAlignment;
    String videoCaption;
    MediaPlayer videoMediaPlayer;
    MediaView videoViewToAdd;
    Button play;
    Button pause;
    HBox buttonBox;
    AddVideoDialogBox addVideoBox =  new AddVideoDialogBox(new Stage());

    public VideoComponent() {
        

    }

    public VideoComponent(String videoFileName, String videoPath, String videoCaption, String videoAlignment, double videoHeight, double videoWidth) {

        this.videoFileName = videoFileName;
        this.videoPath = videoPath;
        this.videoCaption = videoCaption;
        this.videoAlignment = videoAlignment;
        this.videoHeight = videoHeight;
        this.videoWidth = videoWidth;
    }

    public JsonObject toJSON() {
        return Json.createObjectBuilder()
                .add("type", TYPE)
                .add("videoFileName", videoFileName)
                .add("videoPath", videoPath)
                .add("videoAlignment", videoAlignment)
                .add("videoHeight", videoHeight)
                .add("videoWidth", videoWidth)
                .add("videoCaption", videoCaption)
                .build();
    }

    public static Components fromJSON(JsonObject obj) {
        return new ImageComponent(
                obj.getString("videoFileName"),
                obj.getString("videoPath"),
                obj.getString("videoCaption"),
                obj.getString("videoAlignment"),
                obj.getJsonNumber("videoHeight").doubleValue(),
                obj.getJsonNumber("videoWidth").doubleValue());
    }

    public String getVideoFileName() {
        return videoFileName;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public Double getVideoHeight() {
        return videoHeight;
    }

    public Double getVideoWidth() {
        return videoWidth;
    }

    public void setVideoFileName(String videoFileName) {
        this.videoFileName = videoFileName;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public void setVideoHeight(double videoHeight) {
        this.videoHeight = videoHeight;
    }

    public void setVideoWidth(double videoWidth) {
        this.videoWidth = videoWidth;
    }

    public String getVideoAlignment() {
        return videoAlignment;
    }

    public void setVideoAlignment(String videoAlignment) {
        this.videoAlignment = videoAlignment;
    }

    public String getVideoCaption() {
        return videoCaption;
    }

    public void setVideoCaption(String videoCaption) {
        this.videoCaption = videoCaption;
    }

    public VBox createUI() {
        VBox videoBox = new VBox();
        String videoFilePath = videoPath + SLASH + videoFileName;
        File file = new File(videoFilePath);
        try {
            // GET AND SET THE IMAGE
            URL fileURL = file.toURI().toURL();
            Media videoToAdd = new Media(fileURL.toExternalForm());
            videoMediaPlayer = new MediaPlayer(videoToAdd);
            videoViewToAdd = new MediaView(videoMediaPlayer);
            videoViewToAdd.setFitHeight(videoHeight);
            videoViewToAdd.setFitWidth(videoWidth);

        } catch (Exception e) {

            PropertiesManager props = PropertiesManager.getPropertiesManager();
            String notAVideo = props.getProperty(NOT_A_IMAGE.toString());
            String notice = props.getProperty(NOT_A_IMAGE_NOTICE.toString());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(notAVideo);
            alert.setHeaderText(notice);
            alert.setContentText(notice);
            alert.showAndWait();
        }
        Label captionLabel = new Label("Caption: ");
        Label caption = new Label();
        caption.setText(videoCaption);
        HBox captionBox = new HBox();
        play = new Button("play");
        play.setOnAction(e -> {
            videoMediaPlayer.play();
        });
        pause = new Button("pause");
        pause.setOnAction(e -> {
            videoMediaPlayer.pause();
        });
        buttonBox = new HBox();
        buttonBox.getChildren().addAll(play, pause);
        captionBox.getChildren().addAll(captionLabel, caption);
        videoBox.getChildren().addAll(videoViewToAdd, captionBox);
        videoBox.getChildren().add(buttonBox);
        return videoBox;
    }

    public void showDialogBox() {

        addVideoBox.setVideoName(videoFileName);
        addVideoBox.setVideoAlignment(videoAlignment);
        addVideoBox.setVideoCaption(videoCaption);
        addVideoBox.setVideoHeight(videoHeight + "");
        addVideoBox.setVideoWidth(videoWidth + "");

        addVideoBox.showAndWait();

        updated = addVideoBox.isUpdated();
        
        setVideoFileName(addVideoBox.getVideoFileName());
        setVideoPath(addVideoBox.getVideoPath());
        setVideoCaption(addVideoBox.getVideoCaption());
        setVideoAlignment(addVideoBox.getVideoAlignment());
        setVideoHeight(addVideoBox.getVideoHeight());
        setVideoWidth(addVideoBox.getVideoWidth());

    }
}
