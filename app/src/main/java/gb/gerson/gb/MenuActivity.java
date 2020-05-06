package gb.gerson.gb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.muddzdev.styleabletoast.StyleableToast;
import com.ramotion.circlemenu.CircleMenuView;

import ofertas.OfertasActivity;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.txt_titulo);

        final CircleMenuView circleMenuView = (CircleMenuView)findViewById(R.id.circle_menu);
        circleMenuView.setEventListener(new CircleMenuView.EventListener()
        {
            @Override
            public void onButtonClickAnimationStart(@NonNull CircleMenuView view, int buttonIndex) {
                if (buttonIndex==0){
                    Toast.makeText(MenuActivity.this, "yi", Toast.LENGTH_SHORT).show();

                }
                if (buttonIndex==1){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            StyleableToast.makeText(MenuActivity.this, "Ingresando a Iniciar Sesion", Toast.LENGTH_SHORT, R.style.mytoast).show();
                            Intent intent = new Intent(MenuActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    }, 600);
                }
                if (buttonIndex==2){
                   // Toast.makeText(MenuActivity.this, "Hola1", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            StyleableToast.makeText(MenuActivity.this, "Ingresando a ofertas", Toast.LENGTH_SHORT, R.style.mytoast).show();
                            Intent intent = new Intent(MenuActivity.this, OfertasActivity.class);
                            startActivity(intent);
                        }
                    },600);
                }
                if (buttonIndex==3){
                    Toast.makeText(MenuActivity.this, "Hola2", Toast.LENGTH_SHORT).show();
                }
                //super.onButtonClickAnimationStart(view, buttonIndex);
            }
        });
    }
}
