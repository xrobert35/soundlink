package fr.soundlink.ihm;

import android.app.Activity;
import android.app.AlertDialog;
import android.widget.EditText;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import fr.soundlink.R;
import fr.soundlink.bean.User;
import fr.soundlink.bean.Connexion;
import fr.soundlink.service.SoundlinkService;
import fr.soundlink.service.async.AsyncCallBack;
 
@EActivity(R.layout.login_activity)
public class LoginActivity extends Activity {

    @ViewById(R.id.passwordField)
    protected EditText passswordField;

    @ViewById(R.id.emailField)
    protected EditText emailField;

    @Bean
    protected SoundlinkService soundlinkService;

    @Click
    void loginButton() {
        String email = emailField.getText().toString();
        String mdp = passswordField.getText().toString();
        Connexion connexion = new Connexion();
        connexion.setEmail(email);
        connexion.setMdp(mdp);
        soundlinkService.login(connexion, new AsyncCallBack<User>() {
            @Override
            public void onSuccess(User result) {
                emailField.setText(result.getEmail() + result.getLogin());
            }

            @Override
            public void onFailure(Exception cause) {
                new AlertDialog.Builder(LoginActivity.this)
                        .setTitle("Une erreur est survenue")
                        .setMessage(cause.getMessage())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }
}