package com.zizi.rendezvous;

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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

public class ActivityMeetings extends AppCompatActivity {

    private ClassGlobalApp classGlobalApp; //класс для работы с функциями общими для всех активити, фрагментов, сервисов
    //private MaterialToolbar materialToolbar; // верхняя панелька
    private FragmentManager fragmentManager; // для управления показом компонентов
    //private FragmentTransaction fragmentTransaction; // для выполнения операций над фрагментами
    private Fragment fragmentListMeetings; // фрагмент со списком заявок
    private Fragment fragmentRequestMeeting; // фрагмент с заявкой
    //private Fragment fragmentChat; // фрагмент с чатом
    //private Fragment currentFragment; // текущий фрагмент
    //private Fragment fragmentListChats; // текущий фрагмент
    private DocumentReference documentReference; // ссылка на документ
    //private Map<String, Object> mapDocument; //Документ с информацией о встрече
    private ClassDialog classDialog; //класс для показа всплывающих окон
    private ActionBarDrawerToggle  actionBarDrawerToggle;

    private DrawerLayout drawerLayout; //шторка меню слева
    private TextView tvUserID; //поле с идентификатором пользователя в заголовке шторки


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetings);

        //инициализация /////////////////////////////////////////////////////////////////////////////
        classGlobalApp = (ClassGlobalApp) getApplicationContext();
        classGlobalApp.Log(getClass().getSimpleName(), "onCreate", "Начинаю создавать активити", false);

        classGlobalApp.LoadRequestMeetingFromMemory(); // подгружаем заявку из памяти, даже если там нет ничего, заполнятся пустые поля

        fragmentManager = getSupportFragmentManager();
        fragmentListMeetings = new FragmentListMeetings();
        fragmentRequestMeeting = new FragmentRequestMeeting();
        Fragment fragmentChat = new FragmentChat();
        //fragmentListChats = new FragmentListChats();
        //mapDocument = new HashMap<String, Object>();
        classDialog = new ClassDialog(); // класс для показа всплывающих окон

        //ищем нужные элементы
        MaterialToolbar materialToolbar = (MaterialToolbar) findViewById(R.id.materialToolbar); // верхняя панель с кнопками
        drawerLayout = findViewById(R.id.drawerLayout); //слой левой шторки
        NavigationView navigationView = findViewById(R.id.navigationView);
        //tvUserID = findViewById(R.id.tvUserID);
        //============================================================================================



        //drawerLayout шторка///////////////////////////////////////////////////////////////////////

        //связыватель drawerLayout и materialToolbar типа если открыта шторка или закрыта, то иконка гамбургера соответствующая
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                materialToolbar, R.string.drawerLayoutOpen, R.string.drawerLayoutClose) {

            /** Этот код вызывается, когда боковое меню переходит в полностью закрытое состояние. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //!getActionBar().setTitle(mTitle); //сменить заголовок в верхней панельке, типа Гуглы так рекомендуют
                //!invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu() Что то связано с пересозданием меню действий, типа пересоздавать или затирать его
            }

            /** Этот код вызывается, когда боковое меню полностью открывается. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //!getActionBar().setTitle(mDrawerTitle);
                //!invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                tvUserID = findViewById(R.id.tvUserID);
                tvUserID.setText(classGlobalApp.currentUser.getEmail());
            }



        };

        //добавляем к лушателю связыватель/переключатель )))
        drawerLayout.addDrawerListener(actionBarDrawerToggle);



        //нажатие на пункты
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case (R.id.itemMeetings):


                        ChangeFragment(fragmentListMeetings,false);

                        break;

                    case (R.id.itemAbout):

                        break;

                    case (R.id.itemAdmin):

                        break;

                    default:
                        classGlobalApp.Log(getClass().getSimpleName(), "onCreate/onNavigationItemSelected",
                                "Левое меню, нет обработчика события для нажатого пункта", false);
                        break;
                }

                drawerLayout.closeDrawer(GravityCompat.START, true); //закрываем шторку
                return true;
            }
        });
        //==========================================================================================



        // materialToolbar ///////////////////////////////////////////////////////////////////////////

        // Set whether to include the application home affordance in the action bar.
        // (and put a back mark at icon in ActionBar for "up" navigation)
        //getActionBar().setDisplayHomeAsUpEnabled(true);

        // Enable or disable the "home" button in the corner of the action bar.
        // (clickable or not)
        //getActionBar().setHomeButtonEnabled(true); // сделать гамбургер кликабельным

        materialToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {// слушатель нажатия на кнопки верхней панели
                if (item.getItemId() == R.id.request) // если нажата кнопка показать заявку
                {
                    classGlobalApp.LoadRequestMeetingFromMemory(); // подгружаем заявку из памяти телефона
                    ChangeFragment(fragmentRequestMeeting, true); // грузим фрагмент с заявкой на встречу
                }
                return false;
            }
        });
        //==========================================================================================



        //ГРУЗИМ НУЖНЫЙ ФРАГМЕНТ/////////////////////////////////////////////////////////////////////

        // Рассмотрим статус заявки
        switch (classGlobalApp.GetParam("statusRequestMeeting")) {
            // если статус заявки пустой, то есть неизвестен, то его нужно выяснить.
            // Нужно залезть в БД и посмотреть, есть ли там заявка и попробовать получить ее.
            case "":
                classGlobalApp.Log(getClass().getSimpleName(), "onCreate", "Статус заявки неизвестен, лезем в БД, пытаемся получить заявку по ID пользователя", false);

                GetRequestMeetingFromDB(); // проверяем есть ли завяка в БД

                break;

            case Data.NOT_ACTIVE: //если Если статус заявки NOT_ACTIVE

                classGlobalApp.Log(getClass().getSimpleName(), "onCreate", "Статус заявки не активный, будем грузить фрагмент с формой заявки", false);

                ChangeFragment(fragmentRequestMeeting, false); // показываем заявку

                break;

            case Data.ACTIVE: //если Если статус заявки ACTIVE

                classGlobalApp.Log(getClass().getSimpleName(), "onCreate", "Статус заявки активный, будем дальше смотреть, что грузить", false);

                Bundle bundle = getIntent().getExtras(); //получаем параметры переданные в активити

                //classGlobalApp.Log(getClass().getSimpleName(), "onCreate", "Получен параметр: partnerID="  + bundle.getString("partnerID"),false);
                //classGlobalApp.Log(getClass().getSimpleName(), "onCreate", "Получен параметр: partnerTokenDevice="  + bundle.getString("partnerTokenDevice"),false);
                //classGlobalApp.Log(getClass().getSimpleName(), "onCreate", "Получен параметр: partnerName="  + bundle.getString("partnerName"),false);
                //classGlobalApp.Log(getClass().getSimpleName(), "onCreate", "Получен параметр: partnerAge="  + bundle.getString("partnerAge"),false);

                if (bundle != null && bundle.getString("fragmentForLoad").equals(Data.FRAGMENT_CHAT)) { // если нужно грузить фрагмент с чатом
                    classGlobalApp.Log(getClass().getSimpleName(), "onCreate", "Параметр: fragmentForLoad=" + bundle.getString("fragmentForLoad") + ", нужно грузить фрагмент с чатом", false);

                    //извлекаем параметры и передаем их дальше фрагменту
                    classGlobalApp.ClearBundle();
                    classGlobalApp.AddBundle("partnerID", bundle.getString("partnerID"));
                    classGlobalApp.AddBundle("partnerTokenDevice", bundle.getString("partnerTokenDevice"));
                    classGlobalApp.AddBundle("partnerName", bundle.getString("partnerName"));
                    classGlobalApp.AddBundle("partnerAge", bundle.getString("partnerAge"));

                    ChangeFragment(fragmentListMeetings, false); // показываем встречи
                    ChangeFragment(fragmentChat, true); // переходим к чату


                } else { // если в fragmentForLoad не указан явно фрагмент, то грузим список встреч

                    ChangeFragment(fragmentListMeetings, false); // показываем встречи
                }

                break;

            default:  //если неопознанный статус, то грузим фрагмент с заявкой

                classGlobalApp.Log(getClass().getSimpleName(), "onCreate", "Статус заявки неопознан, будем грузить фрагмент с заявкой", false);
                ChangeFragment(fragmentRequestMeeting, false); // показываем заявку

                break;
        }
        //===========================================================================================

    }



    @Override
    public void onStart() {
        super.onStart();

        if (!classGlobalApp.IsAuthorized()) { // если пользователь не авторизован
            startActivity(new Intent(getApplicationContext(), ActivityLogin.class)); // отправляем к началу на авторизацию
            finish(); // убиваем активити
        }

    }



    /** Этот метод вызывается, когда мы вызываем invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Если панель навигации открыта, скрыть элементы действий, связанные с контентом
        //boolean drawerLayoutOpen = drawerLayout.isDrawerOpen(mDrawerList); //mDrawerList - лист с пунктами меню
        //menu.findItem(R.id.action_websearch).setVisible(!drawerLayoutOpen);
        return super.onPrepareOptionsMenu(menu);
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
     * Проеряет наличие заявки текущего пользователя в БД. Если есть возвращает результат в глобальную переменную requestMeetingCurrentUser, если нет, в нее же null
     */
    private void GetRequestMeetingFromDB() {

        documentReference = classGlobalApp.GenerateDocumentReference("meetings", classGlobalApp.GetCurrentUserUid()); // формируем путь к документу
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() { // вешаем слушателя на задачу чтения документа из БД
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) { // как задача чтения выполнилась
                if (task.isSuccessful()) { // если выполнилась успешно

                    classGlobalApp.Log(getClass().getSimpleName(), "GetRequestMeetingFromDB", "Задача чтения заявки из БД выполнена успешно, будем получать результат чтения", false);

                    DocumentSnapshot document = task.getResult(); // получаем документ

                    if (document.exists()) { // если документ такой есть, не null

                        classGlobalApp.Log(getClass().getSimpleName(), "GetRequestMeetingFromDB", "Документ с заявкой есть в БД", false);

                        ModelSingleMeeting requestMeetingCurrentUser = document.toObject(ModelSingleMeeting.class); // получаем заявку текущего пользователя из БД
                        classGlobalApp.SetRequestMeeting(requestMeetingCurrentUser); // записываем заявку пользователя в текущий класс

                        classGlobalApp.SaveRequestMeetingToMemory(); //сохраняем заявку в память телефона
                        RefreshDeviceTokenInMeeting();              //нужно обновить токен в заявке в БД

                    } else { // если запрошенного документа не существует в БД

                        classGlobalApp.Log(getClass().getSimpleName(), "GetRequestMeetingFromDB", "Запрошенного документа нет в БД", false);

                        //classGlobalApp.SetRequestMeeting(null); // делаем заявку в глобальном классе пустой
                        classGlobalApp.PreparingToSave("statusRequestMeeting", "NotActive"); // отмечаем статус заявки неактивным

                        ChangeFragment(fragmentRequestMeeting, false); //переходим на фрагмент с заявкой

                    }

                } else { // если ошибка чтения БД

                    classGlobalApp.Log(getClass().getSimpleName(), "GetRequestMeetingFromDB", "Ошибка чтения БД: " + task.getException(), true);

                    //показываем всплывающее окно
                    classDialog.setTitle("Ошибка чтения БД");
                    classDialog.setMessage("Ошибка проверки статуса заявки на встречу пользователя, попробуйте войти позже. Подробности ошибки: " + task.getException());
                    classDialog.setPositiveButtonRedirect(Data.ACTIVITY_LOGIN);
                    classDialog.show(fragmentManager, "classDialog");

                }
            }
        });

    }



    /**
     * Меняет фрагмент на экране (в активити)
     * @param fragment новый фрагмент, который надо показать
     * @param toStack добавлять его в стек или нет, чтобы можно было переходить по кнопке назад
     */
    void ChangeFragment (Fragment fragment, boolean toStack){ // меняет отображение фрагмента

        classGlobalApp.Log(getClass().getSimpleName(), "ChangeFragment", "Имя класса нового фрагмента = " + fragment.getClass().getSimpleName(), false);

        FragmentTransaction fragmentTransaction; // для выполнения операций над фрагментами
        fragmentTransaction = fragmentManager.beginTransaction();                // начинаем транзакцию
        fragmentTransaction.replace(R.id.fragment_replace, fragment, fragment.getClass().getSimpleName());  // обновляем фрагмент
        if (toStack) { // если нужно добавить для навигации в стек фрагментов
            fragmentTransaction.addToBackStack(null);                           // добавляем в конец стека фрагментов для навигации
        }
        fragmentTransaction.commit();                                       // применяем
        //currentFragment = fragment;                                  // запоминаем текущий фрагмент

    }



    /**
     * Записывает новый tokenDevice в завку встречи в БД
     */
    private void RefreshDeviceTokenInMeeting() {

        classGlobalApp.Log(getClass().getSimpleName(), "RefreshDeviceTokenInMeeting", "Обновляем tokenDevice в заявке в БД", false);

        documentReference = classGlobalApp.GenerateDocumentReference("meetings", classGlobalApp.GetCurrentUserUid()); // документ со встречей текущего пользователя
        documentReference.update("tokenDevice", classGlobalApp.GetTokenDevice()).addOnCompleteListener(new OnCompleteListener<Void>() { // записываем новый tokenDevice в БД в заявку встречи
            @Override
            public void onComplete(@NonNull Task<Void> task) { // если задача работы с БД выполнена
                if(task.isSuccessful()){ //если tokenDevice записан в заявку успешно

                    //classGlobalApp.PreparingToSave("loginNotFirstTime", "trueTrue"); // отмечаем, что уже разок логинились
                    //classGlobalApp.PreparingToSave("requestIsActive", "trueTrue"); //отмечаем, что заявочка активна
                    classGlobalApp.PreparingToSave("statusRequestMeeting", Data.ACTIVE); // отмечаем статус заявки активным
                    classGlobalApp.SaveParams(); // сохраняем в девайс

                    ChangeFragment(fragmentListMeetings, false); // показываем встречи
                }
                // если tokenDevice не записан в заявку встречи, то пользователь по старому токену не будет получать уведомления, то есть заявку можно считать неактивной
                //удалять ее из БД бессмысленно, что-то не так с БД, раз мы записать не смогли, думаю нужно направить пользователя к заполнению заявки, пусть по кнопке еще пытается подать заявку
                else {

                    classGlobalApp.Log(getClass().getSimpleName(), "RefreshDeviceTokenInMeeting",
                            "Ошибка при записи tokenDevice в активную заявку на встречу. Пользователю не будут приходить уведомлени. Нужно заполнить заявку по новой.", true);

                    //считаем заявку не активной
                    classGlobalApp.PreparingToSave("statusRequestMeeting", Data.NOT_ACTIVE); // отмечаем статус заявки неактивным
                    classGlobalApp.SaveParams(); // сохраняем в девайс

                    ChangeFragment(fragmentRequestMeeting, false); // показываем заявку

                }
            }
        });

    }

    @Override
    public void onBackPressed() {

        //если шторка открыта, то закрываем ее
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START, true);
        } else { // если шторка закрыта, то отрабатываем обычное нажатие на кнопку назад
            super.onBackPressed();
        }
        //ChangeFragment(fragmentListMeetings, true);

/*        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setNegativeButton("No", null).show();*/

    }
}