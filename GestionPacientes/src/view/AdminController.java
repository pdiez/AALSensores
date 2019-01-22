package view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.StringConverter;
import model.Role;
import model.User;
import util.Persistent;

public class AdminController implements Initializable{
	
	ObservableList<Role> roles = FXCollections.observableArrayList(Persistent.getRoles().getRoleList());
	
	@FXML
	Label lblUsuarios;
	
	@FXML
	ImageView imgUsuarios;
	
	@FXML
	JFXComboBox cboRole;
	
	@FXML
	TableView<User> tblUsuarios;
	
	@FXML
	JFXButton btnOk;
	
	@FXML
	JFXTextField txtUsername;
	
	@FXML
	JFXTextField txtName;
	
	@FXML
	JFXPasswordField txtPassword;
	
	@FXML
	JFXTextField txtPhone;
	
	@FXML
	JFXTextArea txtNotas;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		imgUsuarios.setImage(new Image(Main.class.getResourceAsStream("/assets/users.png")));
		
//		cboRoles = new JFXComboBox<Role>();
		cboRole.setConverter(new StringConverter<Role>() {
		    @Override
		    public String toString(Role object) {
		        return object.getRoleName();
		    }

		    @Override
		    public Role fromString(String string) {
		        return null;
		    }
		});
		
		
		cboRole.setItems(roles);

		cargaTablaUsuarios();
		
		
	}

	
	private void cargaTablaUsuarios() {

		ObservableList<User> data = FXCollections.observableArrayList();
		data.addAll(Persistent.getUsers().getUserList());
				
		TableColumn c1 = new TableColumn("Usuario");
        c1.setCellValueFactory(new PropertyValueFactory<User,String>("username"));
		
    	TableColumn c2 = new TableColumn("Nombre");
        c2.setCellValueFactory(new PropertyValueFactory<User,String>("name"));
        
    	TableColumn c3 = new TableColumn("Rol");
        c3.setCellValueFactory(new PropertyValueFactory<User,String>("role"));
        
        
        TableColumn c4 = new TableColumn("Telefono");
        c4.setCellValueFactory(new PropertyValueFactory<User,String>("phone"));
        
        TableColumn c5 = new TableColumn("Notas");
        c5.setCellValueFactory(new PropertyValueFactory<User,String>("notes"));
		
		tblUsuarios.setItems(data);
		tblUsuarios.getColumns().addAll(c1, c2, c3, c4, c5);
		
	}
	
	private void updateTablaUsuarios() {

		ObservableList<User> data = FXCollections.observableArrayList();
		data.addAll(Persistent.getUsers().getUserList());
		tblUsuarios.setItems(data);
		
	}
	
	private void resetFormUsuarios() {
		txtUsername.setText("");
		txtName.setText("");
		txtPassword.setText("");
		txtPhone.setText("");
		txtNotas.setText("");
		cboRole.getSelectionModel().clearSelection();
	}
	
	


	@FXML
    private void handlebtnOkAction(ActionEvent event) throws IOException {
		
		User u = new User();
		u.setUsername(txtUsername.getText());
		u.setName(txtName.getText());
		u.setPassword(txtPassword.getText());
		u.setPhone(txtPhone.getText());
		u.setNotes(txtNotas.getText());
		Role rol = (Role) cboRole.getSelectionModel().getSelectedItem();
		u.setRole(rol.getId());
		
		u.Guardar();
		
		updateTablaUsuarios();
		resetFormUsuarios();
		
	}
}
