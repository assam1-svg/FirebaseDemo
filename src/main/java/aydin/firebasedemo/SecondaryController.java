package aydin.firebasedemo;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SecondaryController {

    @FXML private TextField nameField;
    @FXML private TextField ageField;
    @FXML private TextField phoneField;

    Firestore db = FirestoreClient.getFirestore();

    @FXML
    private void writePerson() {
        try {
            Person p = new Person(
                    nameField.getText(),
                    Integer.parseInt(ageField.getText()),
                    phoneField.getText()
            );

            db.collection("persons").add(
                    java.util.Map.of(
                            "name", p.getName(),
                            "age", p.getAge(),
                            "phoneNumber", p.getPhoneNumber()
                    )
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void readPersons() {
        try {
            ApiFuture<QuerySnapshot> q = db.collection("persons").get();
            for (QueryDocumentSnapshot doc : q.get().getDocuments()) {
                System.out.println(doc.getData());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void switchToPrimary() {
        try {
            DemoApp.setRoot("primary");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
