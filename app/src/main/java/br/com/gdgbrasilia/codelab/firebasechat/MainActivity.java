package br.com.gdgbrasilia.codelab.firebasechat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    static final int RC_PHOTO_PICKER = 1;

    private Button sendBtn;
    private EditText messageTxt;
    private RecyclerView messagesList;
    private ChatMessageAdapter adapter;
    private ImageButton imageBtn;
    private TextView usernameTxt;
    private View loginBtn;
    private View logoutBtn;
    private String username;

    //TODO Importar o os packages do Firebase - fireImports
    private FirebaseApp app;



    private void setUsername(String username) {
        Log.d(TAG, "setUsername("+String.valueOf(username)+")");
        if (username == null) {
            username = "Android";
        }
        boolean isLoggedIn = !username.equals("Android");
        this.username = username;
        this.usernameTxt.setText(username);
        this.logoutBtn.setVisibility(isLoggedIn ? View.VISIBLE : View.GONE);
        this.loginBtn .setVisibility(isLoggedIn ? View.GONE    : View.VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendBtn = (Button) findViewById(R.id.sendBtn);
        messageTxt = (EditText) findViewById(R.id.messageTxt);
        messagesList = (RecyclerView) findViewById(R.id.messagesList);
        imageBtn = (ImageButton) findViewById(R.id.imageBtn);
        loginBtn = findViewById(R.id.loginBtn);
        logoutBtn = findViewById(R.id.logoutBtn);
        usernameTxt = (TextView) findViewById(R.id.usernameTxt);
        setUsername("Android");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        messagesList.setHasFixedSize(false);
        messagesList.setLayoutManager(layoutManager);

        // Mostra um image picker quando o usuário quer uplodear uma imagem.
        imageBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete a ação usando"), RC_PHOTO_PICKER);
            }
        });
        // Mostra um popup quando o usuário quer logar
        loginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LoginDialog.showLoginPrompt(MainActivity.this, app);
            }
        });


        // TODO Permitir que os usuários se desloguem - fireSignOut



        //Configura um adapter, acopla à lista de mensagens e registra um observador
        // de dados do tipo RecyclerView para iterar sobre as mensagens de forma suave
        adapter = new ChatMessageAdapter(this);
        messagesList.setAdapter(adapter);
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            public void onItemRangeInserted(int positionStart, int itemCount) {
                messagesList.smoothScrollToPosition(adapter.getItemCount());
            }
        });


        //TODO Carregando os dados de configuração do Firebase no app - fireLoadConfig



        //TODO Recuperando uma referencia ao nó chat - fireDbRef



        sendBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ChatMessage chat = new ChatMessage(username, messageTxt.getText().toString());

                //TODO Salvar a mensagem no DB,
                // (não jogar na tela por aqui mais.) - firePushMessage
                adapter.addMessage(chat);
                messageTxt.setText("");

            }
        });

        //TODO Criar um listener para atualizar a tela quando nós filhos são adicionados ao db - fireMessagelistener




        //TODO Após preencher a Dialog com as credentials, tentar se logar com o Email e Senha - fireEmailSignIn



        //TODO Quando o usuário logar ou deslogar, atualizar seu username - fireOnAuthStateChanged


    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();

            //TODO Recuperar uma ref ao local de armazenam. de arquivos compart. - fireStorageRef


            //TODO Recuperar uma ref para o arquivo em chat_photos/<FILENAME>.jpg - fireFileRef



            //TODO Upload do arquivo para o Firebase Storage - firePutFile

        }
    }
}
