package gb.gerson.gb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.muddzdev.styleabletoast.StyleableToast;
import com.ramotion.circlemenu.CircleMenuView;

import ofertas.OfertasActivity;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.txt_titulo);

        final CircleMenuView circleMenuView = (CircleMenuView)findViewById(R.id.circle_menu);
        circleMenuView.setEventListener(new CircleMenuView.EventListener()
        {
            @Override
            public void onButtonClickAnimationStart(@NonNull CircleMenuView view, int buttonIndex) {
                if (buttonIndex==0){
                    Toast.makeText(MainMenuActivity.this, "", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainMenuActivity.this, RemoverOfertasActivity.class);
                    startActivity(intent);
                }
                if (buttonIndex==1){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            StyleableToast.makeText(MainMenuActivity.this, "Ingresando a Iniciar Sesion", Toast.LENGTH_SHORT, R.style.mytoast).show();
                            Intent intent = new Intent(MainMenuActivity.this, MenuDosActivity.class);
                            startActivity(intent);
                        }
                    }, 600);
                }
                //super.onButtonClickAnimationStart(view, buttonIndex);
            }
        });
    }
}
