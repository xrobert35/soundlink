package fr.soundlink.ihm;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.EditText;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import fr.soundlink.R;
import fr.soundlink.bean.User;
import fr.soundlink.bean.Connexion;
import fr.soundlink.service.SoundlinkService;
import fr.soundlink.service.async.AsyncCallBack;

@EActivity
public class LoginActivity extends AccountAuthenticatorActivity {

	@ViewById(R.id.passwordField)
	protected EditText passswordField;

	@ViewById(R.id.loginField)
	protected EditText emailField;

	@Bean
	protected SoundlinkService soundlinkService;

	private AccountManager accountManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		accountManager = AccountManager.get(this);
	}

	@Click
	void loginButton() {
		String email = emailField.getText().toString();
		String mdp = passswordField.getText().toString();
		
		Connexion connexion = new Connexion();
		connexion.setLogin(email);
		connexion.setPassword(mdp);
	}
	
	@Background
	protected void attempLogin(final Connexion connexion){
		soundlinkService.login(connexion, new AsyncCallBack<String>() {
			@Override
			public void onSuccess(String token) {
				 Account account = new Account(connexion.getLogin(), AccountManager.KEY_ACCOUNT_TYPE);
				accountManager.addAccountExplicitly(account, connexion.getPassword(), null);
				accountManager.setAuthToken(account, AccountManager.KEY_ACCOUNT_TYPE, token);
				emailField.setText("Success");
			}

			@Override
			public void onFailure(Exception cause) {
				new AlertDialog.Builder(LoginActivity.this).setTitle("Une erreur est survenue")
						.setMessage(cause.getMessage()).setIcon(android.R.drawable.ic_dialog_alert).show();
			}
		});
	}
}
