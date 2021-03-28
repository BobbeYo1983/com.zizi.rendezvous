package com.zizi.rendezvous.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.zizi.rendezvous.Fragments.FragmentAbout;
import com.zizi.rendezvous.Fragments.FragmentAdmin;
import com.zizi.rendezvous.Fragments.FragmentRegulations;
import com.zizi.rendezvous.GlobalApp;
import com.zizi.rendezvous.Data.Data;
import com.zizi.rendezvous.Fragments.FragmentChat;
import com.zizi.rendezvous.Fragments.FragmentListMeetings;
import com.zizi.rendezvous.Fragments.FragmentRequestMeeting;
import com.zizi.rendezvous.Models.ModelMeeting;
import com.zizi.rendezvous.R;

public class ActivityMeetings extends AppCompatActivity {

    private GlobalApp globalApp; //класс для работы с функциями общими для всех активити, фрагментов, сервисов
    private FragmentManager fragmentManager; // для управления показом компонентов
    private Fragment fragmentListMeetings; // фрагмент со списком заявок
    private Fragment fragmentRequestMeeting; // фрагмент с заявкой
    //private DocumentReference documentReference; // ссылка на документ
    private ActionBarDrawerToggle  actionBarDrawerToggle; //связыватель drawerLayout и materialToolbar типа если открыта шторка или закрыта, то иконка гамбургера соответствующая

    //виджеты
    private DrawerLayout drawerLayout; //шторка меню слева
    private MaterialToolbar materialToolbar; //верхняя панелька
    private TextView tvUserID; //поле с идентификатором пользователя в заголовке шторки

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetings);

        //инициализация /////////////////////////////////////////////////////////////////////////////
        globalApp = (GlobalApp) getApplicationContext();
        globalApp.Log(getClass().getSimpleName(), "onCreate", "Начинаю создавать активити", false);

        fragmentManager = getSupportFragmentManager();
        fragmentListMeetings = new FragmentListMeetings();
        fragmentRequestMeeting = new FragmentRequestMeeting();

        //ищем нужные виджеты
        materialToolbar = findViewById(R.id.material_toolbar); // верхняя панель с кнопками
        drawerLayout = findViewById(R.id.drawerLayout); //слой левой шторки
        NavigationView navigationView = findViewById(R.id.navigationView);




        //drawerLayout шторка///////////////////////////////////////////////////////////////////////
        //связыватель drawerLayout и materialToolbar типа если открыта шторка или закрыта, то иконка гамбургера соответствующая
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                materialToolbar, R.string.drawerLayoutOpen, R.string.drawerLayoutClose) {

            /** Этот код вызывается, когда боковое меню полностью открывается. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                tvUserID = findViewById(R.id.tv_user_id);
                tvUserID.setText(globalApp.currentUser.getEmail());
            }

            /** Этот код вызывается, когда боковое меню переходит в полностью закрытое состояние. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);

            }

        };

        //добавляем к лушателю связыватель/переключатель )))
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        //если пользователь админ, то показать пункт Админки
        if (globalApp.currentUser.getEmail().equals("denis@1.com")
                | globalApp.currentUser.getEmail().equals("880846@gmail.com")
                | globalApp.currentUser.getEmail().equals("nadinzinina1988@gmail.com")
        ) {
            navigationView.getMenu().findItem(R.id.itemAdmin).setVisible(true);
        }

        //нажатие на пункты
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case (R.id.itemMeetings):

                        //если статус заявки активный
                        if (globalApp.requestMeeting.getStatus().equals(Data.STATUS_ACTIVE)){
                            ChangeFragment(new FragmentListMeetings(),false);
                        } else { //если не активный, то отправляем заполнять заявку
                            ChangeFragment(new FragmentRequestMeeting(),false);
                        }
                        break;

                    case (R.id.itemAbout):
                        globalApp.ClearBundle();
                        globalApp.AddBundle("title", item.getTitle().toString());
                        ChangeFragment(new FragmentAbout(),false);
                        break;

                    case (R.id.itemAdmin):
                        globalApp.ClearBundle();
                        globalApp.AddBundle("title", item.getTitle().toString());
                        ChangeFragment(new FragmentAdmin(),false);
                        break;

                    default:
                        globalApp.Log(getClass().getSimpleName(), "onCreate/onNavigationItemSelected",
                                "Левое меню, нет обработчика события для нажатого пункта", false);
                        break;
                }

                drawerLayout.closeDrawer(GravityCompat.START, true); //закрываем шторку
                return true;
            }
        });
        //==========================================================================================



        // materialToolbar ///////////////////////////////////////////////////////////////////////////
        materialToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {// слушатель нажатия на кнопки верхней панели
                if (item.getItemId() == R.id.request) // если нажата кнопка показать заявку
                {
                    globalApp.LoadRequestMeetingFromMemory(); // подгружаем заявку из памяти телефона
                    ChangeFragment(fragmentRequestMeeting, true); // грузим фрагмент с заявкой на встречу
                }
                return false;
            }
        });
        //==========================================================================================



        GetRequestMeetingFromDB(); // проверяем есть ли завяка в БД и формируем статус заявки

    }



    @Override
    public void onStart() {
        super.onStart();

        if (!globalApp.IsAuthorized()) { // если пользователь не авторизован
            startActivity(new Intent(getApplicationContext(), ActivityLogin.class)); // отправляем к началу на авторизацию
            finish(); // убиваем активити
        }

    }



    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //Синхронизация состояния переключеля шторки после того, как произошло onRestoreInstanceState.
        actionBarDrawerToggle.syncState();
    }



    //вызывается при изменении конфигурации устройства.
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //перерисовываем переключатель шторки
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }



    /**
     * Проеряет наличие заявки текущего пользователя в БД. Если есть возвращает результат в переменную глобального класса globalApp.requestMeeting, если нет, в нее же null
     */
    private void GetRequestMeetingFromDB() {

        globalApp.Log(getClass().getSimpleName(), "GetRequestMeetingFromDB", "Проверяем, есть ли заявка текущего пользователя в БД", false);

        DocumentReference documentReference = globalApp.GenerateDocumentReference("meetings", globalApp.GetCurrentUserUid()); // формируем путь к документу
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() { // вешаем слушателя на задачу чтения документа из БД
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) { // как задача чтения выполнилась
                if (task.isSuccessful()) { // если выполнилась успешно

                    globalApp.Log(getClass().getSimpleName(), "GetRequestMeetingFromDB", "Задача чтения заявки из БД выполнена успешно, будем получать результат чтения", false);

                    DocumentSnapshot document = task.getResult(); // получаем документ

                    if (document.exists()) { // если документ такой есть, не null

                        globalApp.Log(getClass().getSimpleName(), "GetRequestMeetingFromDB", "Документ с заявкой есть в БД", false);

                        ModelMeeting requestMeetingCurrentUser = document.toObject(ModelMeeting.class); // получаем заявку текущего пользователя из БД
                        globalApp.SetRequestMeeting(requestMeetingCurrentUser); // записываем заявку пользователя в текущий класс

                        globalApp.SaveRequestMeetingToMemory(); //сохраняем заявку в память телефона
                        RefreshDeviceTokenInMeeting(); //нужно обновить токен в заявке в БД на всякий

                    } else { // если запрошенного документа не существует в БД

                        globalApp.Log(getClass().getSimpleName(), "GetRequestMeetingFromDB", "Запрошенного документа нет в БД", false);

                        globalApp.LoadRequestMeetingFromMemory(); // подгружаем заявку из памяти
                        globalApp.requestMeeting.setStatus(Data.STATUS_NOT_ACTIVE);//отмечаем статус заявки не активным

                        ChangeFragment(fragmentRequestMeeting, false); //переходим на фрагмент с заявкой

                    }

                } else { // если ошибка чтения БД

                    globalApp.Log(getClass().getSimpleName(), "GetRequestMeetingFromDB", "Ошибка чтения БД: " + task.getException(), true);

                    //Показать сообщение пользователю ///////////////////////////////////////////////
                    new AlertDialog.Builder(ActivityMeetings.this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Ошибка чтения БД")
                            .setMessage("Ошибка проверки статуса заявки на встречу пользователя, попробуйте войти позже. Подробности ошибки: " + task.getException())
                            .setPositiveButton("Понятно", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    //создаем намерение, что хотим перейти на другую активити
                                    Intent intent = new Intent(getApplicationContext(), ActivityLogin.class);
                                    intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TASK //очищаем стек с задачей
                                            |Intent.FLAG_ACTIVITY_NEW_TASK   //хотим создать активити в основной очищенной задаче
                                            );

                                    startActivity(intent); //переходим на другую активити, то есть фактически входим в приложение
                                }
                            })
                            //.setNegativeButton("No", null)
                            .show();
                    //===============================================================================
                }
            }
        });
    }



    /**
     * Меняет фрагмент на экране (в активити)
     * @param fragment новый фрагмент, который надо показать
     * @param saveToStackCurrentFragment сохранить ли в стеке текущий фрагмент, чтобы по кнопке назад к нему можно было перейти
     */
    public void ChangeFragment (Fragment fragment, boolean saveToStackCurrentFragment){ // меняет отображение фрагмента

        globalApp.Log(getClass().getSimpleName(), "ChangeFragment", "Имя класса нового фрагмента = " + fragment.getClass().getSimpleName(), false);

        FragmentTransaction fragmentTransaction; // для выполнения операций над фрагментами
        fragmentTransaction = fragmentManager.beginTransaction();                // начинаем транзакцию
        fragmentTransaction.replace(R.id.fragment_replace, fragment, fragment.getClass().getSimpleName());  // обновляем фрагмент
        if (saveToStackCurrentFragment) { // если нужно добавить для навигации в стек фрагментов
            fragmentTransaction.addToBackStack(null);                           // добавляем в конец стека фрагментов для навигации
        }
        fragmentTransaction.commit();                                       // применяем
        //currentFragment = fragment;                                  // запоминаем текущий фрагмент

    }



    /**
     * Записывает новый tokenDevice в завку встречи в БД
     */
    private void RefreshDeviceTokenInMeeting() {

        globalApp.Log(getClass().getSimpleName(), "RefreshDeviceTokenInMeeting", "Обновляем tokenDevice в заявке в БД", false);

        DocumentReference documentReference = globalApp.GenerateDocumentReference("meetings", globalApp.GetCurrentUserUid()); // документ со встречей текущего пользователя
        documentReference.update("tokenDevice", globalApp.GetTokenDevice()).addOnCompleteListener(new OnCompleteListener<Void>() { // записываем новый tokenDevice в БД в заявку встречи
            @Override
            public void onComplete(@NonNull Task<Void> task) { // если задача работы с БД выполнена
                if(task.isSuccessful()){ //если tokenDevice записан в заявку в БД успешно

                    //classGlobalApp.PreparingToSave("loginNotFirstTime", "trueTrue"); // отмечаем, что уже разок логинились
                    //classGlobalApp.PreparingToSave("requestIsActive", "trueTrue"); //отмечаем, что заявочка активна
                    //globalApp.PreparingToSave("statusRequestMeeting", Data.STATUS_ACTIVE); // отмечаем статус заявки активным
                    //globalApp.SaveParams(); // сохраняем в девайс

                    //ChangeFragment(fragmentListMeetings, false); // показываем встречи

                    LoadFragment(); //грузим нужный фрагмент в зависимости от статуса заявки
                }
                //! если tokenDevice не записан в заявку встречи, то пользователь по старому токену не будет получать уведомления, то есть заявку можно считать неактивной
                //!удалять ее из БД бессмысленно, что-то не так с БД, раз мы записать не смогли, думаю нужно направить пользователя к заполнению заявки, пусть по кнопке еще пытается подать заявку
                else {

                    globalApp.Log(getClass().getSimpleName(), "RefreshDeviceTokenInMeeting",
                            "Ошибка при записи tokenDevice в активную заявку на встречу. Пользователю не будут приходить уведомлени. Нужно заполнить заявку по новой.", true);

                    //считаем заявку не активной
                    //globalApp.PreparingToSave("statusRequestMeeting", Data.STATUS_NOT_ACTIVE); // отмечаем статус заявки неактивным
                    //globalApp.SaveParams(); // сохраняем в девайс

                    //TODO сюда попали по ошибке, когда пользователя направляем на фрагмент с заявкой, нужно что-то сообщить ему, показать сообщение
                    ChangeFragment(fragmentRequestMeeting, false); // показываем заявку

                }
            }
        });

    }



    /** Загружает нужный фрагмент в зависимости от стутуса */
    private void LoadFragment() {

        globalApp.Log(getClass().getSimpleName(), "onCreate", "Статус заявки = " + globalApp.requestMeeting.getStatus(), false);

        switch (globalApp.requestMeeting.getStatus()){

            case Data.STATUS_NOT_ACTIVE: //если заявка не активна, ее нет в БД

                globalApp.Log(getClass().getSimpleName(), "onCreate", "Статус заявки не активный, будем грузить фрагмент с формой заявки", false);

                ChangeFragment(fragmentRequestMeeting, false); // показываем заявку на встречу

                break;

            case Data.STATUS_MODERATION_FAIL:

                //направить на фрагмент с правилами и разъяснить пользователю
                globalApp.Log(getClass().getSimpleName(), "onCreate", "Статус заявки STATUS_MODERATION_FAIL, нужно предупредить пользователя", false);

                globalApp.ClearBundle();
                globalApp.AddBundle("moderation", "false"); //отмечаем, что модерация не пройдена

                ChangeFragment(new FragmentRegulations(), false); // показываем заявку на встречу

                break;

            case Data.STATUS_ACTIVE: //если заявка активная

                globalApp.Log(getClass().getSimpleName(), "onCreate", "Статус заявки активный, будем дальше смотреть, что грузить", false);

                Bundle bundle = getIntent().getExtras(); //получаем параметры переданные в активити

                //classGlobalApp.Log(getClass().getSimpleName(), "onCreate", "Получен параметр: partnerID="  + bundle.getString("partnerID"),false);
                //classGlobalApp.Log(getClass().getSimpleName(), "onCreate", "Получен параметр: partnerTokenDevice="  + bundle.getString("partnerTokenDevice"),false);
                //classGlobalApp.Log(getClass().getSimpleName(), "onCreate", "Получен параметр: partnerName="  + bundle.getString("partnerName"),false);
                //classGlobalApp.Log(getClass().getSimpleName(), "onCreate", "Получен параметр: partnerAge="  + bundle.getString("partnerAge"),false);

                if (bundle != null && bundle.getString("fragmentForLoad").equals(Data.FRAGMENT_CHAT)) { // если нужно грузить фрагмент с чатом
                    globalApp.Log(getClass().getSimpleName(), "onCreate", "Параметр: fragmentForLoad=" + bundle.getString("fragmentForLoad") + ", нужно грузить фрагмент с чатом", false);

                    //извлекаем параметры и передаем их дальше фрагменту
                    globalApp.ClearBundle();
                    globalApp.AddBundle("partnerID", bundle.getString("partnerID"));
                    globalApp.AddBundle("partnerTokenDevice", bundle.getString("partnerTokenDevice"));
                    globalApp.AddBundle("partnerName", bundle.getString("partnerName"));
                    globalApp.AddBundle("partnerAge", bundle.getString("partnerAge"));

                    ChangeFragment(fragmentListMeetings, false); // показываем сначала встречи, чтобы правильно отработала кнопка назад
                    ChangeFragment(new FragmentChat(), true); // переходим к чату


                } else { // если в fragmentForLoad не указан явно фрагмент, то грузим список встреч

                    ChangeFragment(fragmentListMeetings, false); // показываем встречи
                }

                break;

            default:  //если неопознанный статус, то грузим фрагмент с заявкой

                globalApp.Log(getClass().getSimpleName(), "onCreate", "Статус заявки неопознан, будем грузить фрагмент с заявкой", false);
                ChangeFragment(fragmentRequestMeeting, false); // показываем заявку

                break;
        }

    }

    @Override
    public void onBackPressed() {

        //если шторка открыта, то закрываем ее
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START, true);
        } else { // если шторка закрыта, то отрабатываем обычное нажатие на кнопку назад
            super.onBackPressed();
        }

        /*
        //предупреждение о выходе
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setNegativeButton("No", null).show();
         */

    }
}