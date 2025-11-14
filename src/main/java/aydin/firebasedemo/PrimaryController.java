package aydin.firebasedemo;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.cloud.FirestoreClient;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class PrimaryController {

    @FXML private VBox registerPane;
    @FXML private VBox signInPane;

    @FXML private TextField regEmail;
    @FXML private PasswordField regPassword;

    @FXML private TextField loginEmail;
    @FXML private PasswordField loginPassword;

    @FXML
    private void showRegister() {
        registerPane.setVisible(true);
        signInPane.setVisible(false);
    }

    @FXML
    private void showSignIn() {
        signInPane.setVisible(true);
        registerPane.setVisible(false);
    }

    @FXML
    private void createAccount() {
        try {
            UserRecord.CreateRequest req = new UserRecord.CreateRequest()
                    .setEmail(regEmail.getText())
                    .setPassword(regPassword.getText());

            FirebaseAuth.getInstance().createUser(req);

            Firestore db = FirestoreClient.getFirestore();
            db.collection("users").document(regEmail.getText())
                    .set(java.util.Map.of("password", regPassword.getText()));

            registerPane.setVisible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void loginUser() {
        try {
            Firestore db = FirestoreClient.getFirestore();
            DocumentSnapshot snap = db.collection("users")
                    .document(loginEmail.getText()).get().get();

            if (snap.exists()) {
                String pw = snap.getString("password");
                if (pw != null && pw.equals(loginPassword.getText())) {
                    DemoApp.setRoot("secondary");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
