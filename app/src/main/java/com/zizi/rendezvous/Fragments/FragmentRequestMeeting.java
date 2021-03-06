package com.zizi.rendezvous.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.zizi.rendezvous.Activity.ActivityLogin;
import com.zizi.rendezvous.Activity.ActivityMeetings;
import com.zizi.rendezvous.Activity.ActivityPay;
import com.zizi.rendezvous.GlobalApp;
import com.zizi.rendezvous.Data.Data;
import com.zizi.rendezvous.Models.ModelMeeting;
import com.zizi.rendezvous.R;

import java.util.ArrayList;

public class FragmentRequestMeeting extends Fragment {

    //Объявление ///////////////////////////////////////////////////////////////////////////////////
    private GlobalApp globalApp; //глобальный класс приложения, общий для всех компонентов
    private DocumentReference documentReference; // для работы с документами в базе, нужно знать структуру базы FirebaseFirestore
    private ActivityMeetings activityMeetings; // настоящая активити
    private FragmentListMeetings fragmentListMeetings; //фрагмент со встречами
    private FragmentPlace fragmentPlace; // фрагмент с выбором места
    private ArrayAdapter<String> arrayAdapterMaxAge; // адаптер для формирование максимального возраста партнера

    //виджеты
    private MaterialToolbar materialToolbar; // верхняя панелька
    private TextInputLayout til_name;
    private TextInputEditText til_name_et; // имя пользователя
    private TextInputLayout til_gender; // пол пользователя
    private AutoCompleteTextView til_gender_act; // пол пользователя
    private TextInputLayout til_age;
    private AutoCompleteTextView til_age_act; // возраст пользователя
    private TextInputEditText til_phone_et;     // контактный номер
    private CheckBox cb_only_write; //галка можно ли звонить
    private TextInputEditText til_soc_net_et; // страничка в соц сети
    private TextInputLayout til_contact;
    private TextInputEditText til_contact_et; //контактные данные
    private TextInputLayout til_gender_partner;
    private AutoCompleteTextView til_gender_partner_act; // пол партнера
    private TextInputLayout til_age_min;
    private AutoCompleteTextView til_age_min_act; // возраст партнера минимальный
    private TextInputLayout til_age_max;
    private AutoCompleteTextView til_age_max_act; // возраст партнера максимальный
    private TextInputLayout til_region;
    private AutoCompleteTextView til_region_act; // регион
    private TextInputLayout til_town; // город
    private AutoCompleteTextView til_town_act; // город
    private TextInputLayout til_place; // место встречи
    private TextInputEditText til_place_et; // место встречи
    private TextInputLayout til_time;
    private AutoCompleteTextView til_time_act; // время
    private TextInputLayout til_comment;
    private TextInputEditText til_comment_et; // комментарий к встрече
    private Button btn_apply_request; // кнопка подачи заявки
    private ScrollView svRequestMeeting; // прокручиваемый слой
    private ProgressBar pbRequestMeeting; // крутилка
    private DrawerLayout drawerLayout; //шторка меню слева



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_request_meeting, container, false);
    }



    @Override //Вызывается, когда отработает метод активности onCreate(), а значит фрагмент может обратиться к компонентам активности
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //инициализация /////////////////////////////////////////////////////////////////////////////
        globalApp = (GlobalApp) getActivity().getApplicationContext();
        globalApp.Log(getClass().getSimpleName(), "onActivityCreated", "Метод запущен", false);
        activityMeetings = (ActivityMeetings)getActivity();
        fragmentListMeetings = new FragmentListMeetings();
        fragmentPlace = new FragmentPlace();
        //==========================================================================================



        // находим все вьюхи на активити/////////////////////////////////////////////////////////////
        til_name = getActivity().findViewById(R.id.til_name);
        til_name_et = getActivity().findViewById(R.id.til_name_et);
        til_gender = getActivity().findViewById(R.id.til_gender);
        til_gender_act = getActivity().findViewById(R.id.til_gender_act);
        til_age = getActivity().findViewById(R.id.til_age);
        til_age_act = getActivity().findViewById(R.id.til_age_act);
        til_phone_et = getActivity().findViewById(R.id.til_phone_et);
        cb_only_write = getActivity().findViewById(R.id.cb_only_write);
        til_soc_net_et = getActivity().findViewById(R.id.til_soc_net_et);
        til_contact = getActivity().findViewById(R.id.til_contact);
        til_contact_et = getActivity().findViewById(R.id.til_contact_et);
        til_gender_partner = getActivity().findViewById(R.id.til_gender_partner);
        til_gender_partner_act = getActivity().findViewById(R.id.til_gender_partner_act); // низпадающий список выбора пола партнера
        til_age_min = getActivity().findViewById(R.id.til_age_min);
        til_age_min_act = getActivity().findViewById(R.id.til_age_min_act);
        til_age_max = getActivity().findViewById(R.id.til_age_max);
        til_age_max_act = getActivity().findViewById(R.id.til_age_max_act);
        til_region = getActivity().findViewById(R.id.til_region);
        til_region_act = getActivity().findViewById(R.id.til_region_act);
        til_town = getActivity().findViewById(R.id.til_town);
        til_town_act = getActivity().findViewById(R.id.til_town_act);
        til_place = getActivity().findViewById(R.id.til_place);
        til_place_et = getActivity().findViewById(R.id.til_place_et);
        til_time = getActivity().findViewById(R.id.til_time);
        til_time_act = getActivity().findViewById(R.id.til_time_act);
        til_comment = getActivity().findViewById(R.id.til_comment);
        til_comment_et = getActivity().findViewById(R.id.til_comment_et);
        materialToolbar = getActivity().findViewById(R.id.material_toolbar);
        btn_apply_request = getActivity().findViewById(R.id.btn_apply_request);
        svRequestMeeting = getActivity().findViewById(R.id.svRequestMeeting);
        pbRequestMeeting = getActivity().findViewById(R.id.pbRequestMeeting);
        drawerLayout = getActivity().findViewById(R.id.drawerLayout); //слой левой шторки
        //=========================================================================================



        //СЛУШАТЕЛИ//////////////////////////////////////////////////////////////////////////////////



        // btn_apply_request /////////////////////////////////////////////////////////////////////////
        //слушатель нажатия на кнопку подачи заявки
        btn_apply_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Если поля все введены корректно
                if (!til_name_et.getText().toString().equals("") &           // если имя не пустое
                        !til_gender_act.getText().toString().equals("") &        // если пол выбран
                        !til_age_act.getText().toString().equals("") &           //если возраст выбран
                        !til_gender_partner_act.getText().toString().equals("") &//если пол партнера выбран
                        !til_age_min_act.getText().toString().equals("") &       //если возраст минимальный партнера выбран
                        !til_age_max_act.getText().toString().equals("") &       //если возраст максимальный партнера выбран
                        !til_region_act.getText().toString().equals("") &        //если регион выбран
                        !til_town_act.getText().toString().equals("") &          //если город выбран
                        !til_place_et.getText().toString().equals("") &          //если место выбрано
                        !til_time_act.getText().toString().equals("")            //если время выбрано

                ) { // Если поля все введены корректно

                    if(globalApp.currentUser.isAcceptRules() == true) { //если правила приняты, то двигаемся дальше


                        //нужно проверить есть ли бесплатные заявки
                        int countRequestMeetings = Integer.valueOf(globalApp.currentUser.getCountRequestMeetings());
                        globalApp.Log(getClass().getSimpleName(), "btn_apply_request.setOnClickListener", "Количество бесплатных заявок = " + countRequestMeetings, false);
                        if (countRequestMeetings > 0) {

                            CheckModeration(); //проверка на модерацию

                            globalApp.Log(getClass().getSimpleName(), "btn_apply_request.setOnClickListener",
                                    "Метка времени заявки = " + globalApp.requestMeeting.getTimeStamp(), false);

                            if (globalApp.requestMeeting.getStatus().equals(Data.STATUS_ACTIVE)) { //если статус заявки активный, то есть мы заявку редактируем, то время подачи заявки не трогать
                                globalApp.requestMeeting.setTimeStamp(globalApp.requestMeeting.getTimeStamp()); //оставляем такое же время заявки
                            } else {
                                globalApp.requestMeeting.setTimeStamp(null); //если null, то будет записано серверное время
                                globalApp.requestMeeting.setStatus(Data.STATUS_ACTIVE); //устанавливаем статус у заявки Активная
                            }

                            SaveParamsToRAM(); // сохранение в оперативную память

                            // сохраняем заявку в БД
                            documentReference = globalApp.GenerateDocumentReference("meetings", globalApp.GetCurrentUserUid());
                            documentReference.set(globalApp.requestMeeting).addOnCompleteListener(new OnCompleteListener<Void>() { //прям объект класса кидаем в БД
                                @Override
                                public void onComplete(@NonNull Task<Void> task) { //если задачка по работе с БД выполнилась
                                    if (task.isSuccessful()) { //если задача по работе с БД выполнилась успешно

                                        globalApp.SaveRequestMeetingToMemory(); // сохраняем заявку в память

                                        //globalApp.PreparingToSave("statusRequestMeeting", Data.STATUS_ACTIVE); // отмечаем статус заявки активным
                                        //globalApp.SaveParams();

                                        //!!!DecrementCountRequestMeetings();// вычеркиваем одну бесплатную заявку и записываем в профиль в БД

                                        activityMeetings.ChangeFragment(fragmentListMeetings, false); // переходим к списку встреч

                                    } else { //если задача по работе с БД выполнилась не успешно

                                        globalApp.Log(getClass().getSimpleName(), "UpdateUI/onComplete", "Ошибка при подаче заявки. Ошибка записи заявки в БД: " + task.getException(), true);

                                        //Показать сообщение пользователю ///////////////////////////////////////////////
                                        new AlertDialog.Builder(getContext()) //в фрагментах getContext(), в активити ActivityName.this
                                                .setIcon(android.R.drawable.ic_dialog_alert)
                                                .setTitle("Ошибка записи в БД")
                                                .setMessage("Ошибка при подаче заявки. Проверьте включен ли Интернет. Проверьете доступен ли Интернет. Ошибка записи заявки в БД: " + task.getException())
                                                .setPositiveButton("Понятно", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                        //создаем намерение, что хотим перейти на другую активити
                                                        Intent intent = new Intent(getActivity().getApplicationContext(), ActivityLogin.class);
                                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK //очищаем стек с задачей
                                                                | Intent.FLAG_ACTIVITY_NEW_TASK   //хотим создать активити в основной очищенной задаче
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
                        } else { //если бесплатные заявки закончились

                            //Перейти на страничку с оплатой
                            //создаем намерение, что хотим перейти на другую активити
                            Intent intent = new Intent(getActivity().getApplicationContext(), ActivityPay.class);
                            //intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TASK //очищаем стек с задачей
                            //                  |Intent.FLAG_ACTIVITY_NEW_TASK   //хотим создать активити в основной очищенной задаче
                            //                );

                            startActivity(intent); //переходим на другую активити, то есть фактически входим в приложение

                        }
                    } else { //если правила приложения НЕ приняты

                        SaveParamsToRAM(); // сохранение в оперативную память

                        activityMeetings.ChangeFragment(new FragmentRegulations(), true); //переходим на фрагмент с правилами
                    }

                } else {// если одно из обязательных полей не заполнено в заявке

                    if (til_name_et.getText().toString().isEmpty()) {          // если имя не пустое
                        til_name.setError("Введите Ваше имя");
                    }

                    if (til_gender_act.getText().toString().isEmpty()) {       // если пол не выбран
                        til_gender.setError("Выберите Ваш пол");
                    }

                    if (til_age_act.getText().toString().isEmpty()) {       //если возраст не выбран
                        til_age.setError("Выберите Ваш возраст");
                    }

                    if (til_gender_partner_act.getText().toString().isEmpty()) {       //если пол партнера не выбран
                        til_gender_partner.setError("Выберите пол");
                    }

                    if (til_age_min_act.getText().toString().isEmpty()) {       //если возраст минимальный партнера не выбран
                        til_age_min.setError("Выберите минимальный возраст");
                    }

                    if (til_age_max_act.getText().toString().isEmpty()) {       //если возраст максимальный партнера не выбран
                        til_age_max.setError("Выберите максимальный возраст");
                    }

                    if (til_region_act.getText().toString().isEmpty()) {       //если регион не выбран
                        til_region.setError("Выберите регион");
                    }

                    if (til_town_act.getText().toString().isEmpty()) {       //если город не выбран
                        til_town.setError("Выберите город");
                    }

                    if (til_place_et.getText().toString().isEmpty()) {       //если место не выбрано
                        til_place.setError("Выберите место встречи");
                    }

                    if (til_time_act.getText().toString().isEmpty()) {      //если время не выбрано
                        til_time.setError("Выберите время встречи");
                    }

                    //Покажем пользователю сообщение/////////////////////////////////////////////////
                    new AlertDialog.Builder(getContext())
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Заполните все поля")
                            .setMessage("Заполните обязательные поля выделенные красным цветом")
                            .setPositiveButton("Понятно", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            //.setNegativeButton("No", null)
                            .show();
                    //===============================================================================


                }
            }
        });
        //==================================================================================================

    }



    @Override
    public void onStart() {
        super.onStart();

        if (!globalApp.IsAuthorized()) { // если пользователь не авторизован
            startActivity(new Intent(getActivity().getApplicationContext(), ActivityLogin.class)); // отправляем к началу на авторизацию
            getActivity().finish(); // убиваем активити
        } else {

            //показываем прогрессбар
            svRequestMeeting.setVisibility(View.GONE); // скрываем виджеты
            pbRequestMeeting.setVisibility(View.VISIBLE); // показываем прогрессбар



            // materialToolbar /////////////////////////////////////////////////////////////////////////
            materialToolbar.setTitle("Заявка"); // заголовок в панельке верхней
            materialToolbar.getMenu().findItem(R.id.request).setVisible(false); // скрываем пункт заявки на встречу

            if (globalApp.requestMeeting.getStatus().equals(Data.STATUS_ACTIVE)) { //если статус заявки активный
                //if (globalApp.GetParam("statusRequestMeeting").equals(Data.STATUS_ACTIVE)) { //если статус заявки активный
                materialToolbar.setNavigationIcon(R.drawable.ic_outline_arrow_back_24); // делаем кнопку навигации стрелкой в верхней панельке
                // событие при клике на кнопку навигации, на этом фрагменте она в виде стрелочки
                materialToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        getActivity().onBackPressed();
                    }
                });
            } else {
                materialToolbar.setNavigationIcon(R.drawable.ic_outline_menu_24); // делаем кнопку навигации менюшкой в верхней панельке
                materialToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        drawerLayout.openDrawer(GravityCompat.START);
                    }
                });
            }
            //==========================================================================================


            GetRequestMeetingFromDB();



        }

    }



    /**
     * Проеряет наличие заявки текущего пользователя в БД. Если есть возвращает результат в переменную глобального класса globalApp.requestMeeting
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

                        globalApp.Log(getClass().getSimpleName(), "GetRequestMeetingFromDB", "Документ с заявкой есть в БД, метка времени заявки получена.", false);

                        ModelMeeting requestMeetingCurrentUser = document.toObject(ModelMeeting.class); // получаем заявку текущего пользователя из БД
                        globalApp.SetRequestMeeting(requestMeetingCurrentUser); // записываем заявку пользователя в текущий класс

                        globalApp.SaveRequestMeetingToMemory(); //сохраняем заявку в память телефона

                        UpdateUI();

                    } else { // если запрошенного документа не существует в БД

                        globalApp.Log(getClass().getSimpleName(), "GetRequestMeetingFromDB", "Запрошенного документа нет в БД", false);
                        globalApp.Log(getClass().getSimpleName(), "GetRequestMeetingFromDB", "Метка времени неизвестна и при подаче будет задана новая метка времени.", false);

                        //globalApp.LoadRequestMeetingFromMemory(); // подгружаем заявку из памяти
                        globalApp.requestMeeting.setStatus(Data.STATUS_NOT_ACTIVE);//отмечаем статус заявки не активным

                        UpdateUI();

                    }

                } else { // если ошибка чтения БД

                    globalApp.Log(getClass().getSimpleName(), "GetRequestMeetingFromDB", "Ошибка чтения БД: " + task.getException(), true);

                    //Показать сообщение пользователю ///////////////////////////////////////////////
                    new AlertDialog.Builder(getContext()) //во фрагментах getContext()
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Ошибка чтения БД")
                            .setMessage("Ошибка чтения заявки из БД, метка времени неизвестна и при подаче будет задана новая метка времени, ошибка: " + task.getException())
                            .setPositiveButton("Понятно", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.cancel();

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
     * Обновляет интерфейс пользователя
     */
    private void UpdateUI () {

        svRequestMeeting.setVisibility(View.GONE); // скрываем виджеты
        pbRequestMeeting.setVisibility(View.VISIBLE); // показываем прогрессбар



        // til_name_et //////////////////////////////////////////////////////////////////////////////
        til_name_et.setText(globalApp.requestMeeting.getName()); // восстанавливаем текст из памяти
        // слушатель изменения текста
        til_name_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                if (!til_name_et.getText().toString().isEmpty()){
                    til_name.setErrorEnabled(false); // убираем отображение ошибки
                }
            }
        });
        //==========================================================================================



        // til_gender_act /////////////////////////////////////////////////////////////////////////
        til_gender_act.setThreshold(100); // чтобы при установлении текста отображался весь список, иначе будет предлагать только найденные строки по введенному тексту
        til_gender_act.setText(globalApp.requestMeeting.getGender());

        //наполняем низпадающий список выбора пола для выбора пола
        String[] gender = new String[] {"Мужской", "Женский"}; // Ниспадающий список выбора пола
        ArrayAdapter<String> adapter_gender = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, gender); // связываем с адаптером
        til_gender_act.setAdapter(adapter_gender);

        til_gender_act.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                til_gender.setErrorEnabled(false); // убираем описание ошибки
            }
        });

        //==========================================================================================



        // til_age_act /////////////////////////////////////////////////////////////////////////////
        til_age_act.setThreshold(100);
        til_age_act.setText(globalApp.requestMeeting.getAge()); // восстанавливаем выбранное значение из памяти

        // набиваем список для выбора
        ArrayAdapter<String> arrayAdapterAge = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, CreateAges(18,70)); //  связываем адаптер с данными
        til_age_act.setAdapter(arrayAdapterAge); // связываем представление с адаптером

        til_age_act.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                til_age.setErrorEnabled(false); // убираем описание ошибки
            }
        });
        // =========================================================================================



        //til_phone_et ////////////////////////////////////////////////////////////////////////////
        til_phone_et.setText(globalApp.requestMeeting.getPhone()); // восстанавливаем выбранное значение из памяти

        //слушатель введенного текста, нужен для показать или спрятать подсказку
        til_phone_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if (til_phone_et.getText().toString().isEmpty()){ // если телефон не указан, то галку "не звонить" делаем неактивной
                    cb_only_write.setEnabled(false); //то галку "не звонить" делаем неактивной
                    cb_only_write.setChecked(false); // то галку убираем
                } else {
                    cb_only_write.setEnabled(true);
                }

            }
        });
        //================================================================================================



        // cb_only_write ////////////////////////////////////////////////////////////////////////////////
        //восстанавливаем из памяти
        if (globalApp.requestMeeting.getOnlyWrite().equals("trueTrue")){ // если галка отмечена и сохранена
            cb_only_write.setChecked(true);
        } else {
            cb_only_write.setChecked(false);// то не ставим галку
        }
        // ==============================================================================================



        // til_soc_net_et ////////////////////////////////////////////////////////////////////////////////
        til_soc_net_et.setText(globalApp.requestMeeting.getSocNet()); // восстанавливаем выбранное значение из памяти);
        // =============================================================================================



        // til_contact ////////////////////////////////////////////////////////////////////////////
        til_contact_et.setText(globalApp.requestMeeting.getContact()); // восстанавливаем выбранное значение из памяти

        //слушатель введенного текста, нужен для показать или спрятать подсказку
        if (til_contact_et.getText().toString().isEmpty()) {
            til_contact.setHelperText(getString(R.string.til_contact));
        } else {
            til_contact.setHelperText(" ");
        }
        til_contact_et.addTextChangedListener(new TextWatcher() { // при изменении текста в контактных данных
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if (til_contact_et.getText().toString().isEmpty()) {
                    til_contact.setHelperText(getString(R.string.til_contact));
                } else {
                    til_contact.setHelperText(" ");
                }
            }
        });
        //=============================================================================================



        //til_gender_partner_act//////////////////////////////////////////////////////////////////////////
        til_gender_partner_act.setThreshold(100);
        til_gender_partner_act.setText(globalApp.requestMeeting.getGender_partner()); // восстанавливаем выбранное значение из памяти

        til_gender_partner_act.setAdapter(adapter_gender); //список для выбора

        //слушатель при выборе любого элемента
        til_gender_partner_act.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                til_gender_partner.setErrorEnabled(false);
            }
        });
        //=============================================================================================



        //til_age_min_act,  til_age_max_act///////////////////////////////////////////////////////////////////////////
        til_age_min_act.setThreshold(100);
        til_age_max_act.setThreshold(100);

        if (globalApp.requestMeeting.getAge_min().equals("")){
            til_age_min_act.setText("18");
        } else {
            til_age_min_act.setText(globalApp.requestMeeting.getAge_min()); // восстанавливаем выбранное значение из памяти
        }

        if (globalApp.requestMeeting.getAge_max().equals("")) {
            til_age_max_act.setText("70");
        } else {
            til_age_max_act.setText(globalApp.requestMeeting.getAge_max()); // восстанавливаем выбранное значение из памяти
        }
        //  связываем адаптер с данными
        ArrayAdapter<String> arrayAdapterMinAge = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, CreateAges(18,70));
        til_age_min_act.setAdapter(arrayAdapterMinAge);

        if (til_age_min_act.getText().toString().equals("")) {//если поле с начальным возрастом пустое, то делваем весь диапазон возрастов в максимальном возразте
            arrayAdapterMaxAge = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, CreateAges(18,70)); //  связываем адаптер с данными
        } else { // если минимальный возраст выбран, то делаем диапазон макимальных возрастов от минимального
            arrayAdapterMaxAge = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, CreateAges(Integer.parseInt(til_age_min_act.getText().toString()),70)); //  связываем адаптер с данными
        }
        til_age_max_act.setAdapter(arrayAdapterMaxAge); // применяем данные

        til_age_min_act.setOnItemClickListener(new AdapterView.OnItemClickListener() { // как только выбрали минимальный возраст
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //если выбранный минимальный возраст больше максимального, то в максимальный подставить минимальный
                if (Integer.parseInt(til_age_min_act.getText().toString()) > Integer.parseInt(til_age_max_act.getText().toString())) {
                    til_age_max_act.setText(til_age_min_act.getText());
                }
                arrayAdapterMaxAge = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, CreateAges(Integer.parseInt(til_age_min_act.getText().toString()), 70));
                til_age_max_act.setAdapter(arrayAdapterMaxAge); // применяем данные

            }
        });
        //============================================================================================================



        // til_region_act //////////////////////////////////////////////////////////////////////////////////////
        til_region_act.setThreshold(100);
        til_region_act.setText(globalApp.requestMeeting.getRegion());  // восстанавливаем выбранное значение из памяти

        //формируем список для выбора
        ArrayAdapter<String> adapter_regions = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.regions);
        til_region_act.setAdapter(adapter_regions);

        //слушатель - если меняется выбор региона
        til_region_act.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                // в зависимости от выбранного региона формируем список городов
                til_town_act.setAdapter(CreateAdapterTowns(parent.getItemAtPosition(position).toString()));
                til_town.setEnabled(true);
                til_town_act.setEnabled(true);
                til_town_act.setText("");

                til_region.setErrorEnabled(false); //сбрасываем ошибку
            }
        });
        //=====================================================================================================



        //til_town_act заполняем список с городами///////////////////////////////////////////////////
        til_town_act.setThreshold(100);
        if (til_region_act.getText().toString().equals("")) { // если поле с регионом пустое, то блокируем выбор города
            til_town.setEnabled(false); // то делаем не активным
            til_town_act.setEnabled(false); // то делаем не активным
        } else {
            til_town.setEnabled(true);
            til_town_act.setEnabled(true); // то делаем активным
            til_town_act.setText(globalApp.requestMeeting.getTown()); // подгружаем имя города из памяти
            til_town_act.setAdapter(CreateAdapterTowns(globalApp.requestMeeting.getRegion()));//тут нужно дернуть слушатель, чтобы подгрузил города
        }

        til_town_act.setOnItemClickListener(new AdapterView.OnItemClickListener() { //при нажатии на выбранный элемент
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                til_town.setErrorEnabled(false); // сбрасываем описание ошибки
            }
        });
        //===========================================================================================



        //til_place_et //////////////////////////////////////////////////////////////////////////////
        //til_place_et.setText(LoadFromMemory());
        til_place_et.setText(globalApp.requestMeeting.CreateStringFromArrayListPlaces());

        // Слушатель при нажатии на поле
        til_place_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                til_place.setErrorEnabled(false);
                SaveParamsToRAM(); // сохраняем значения полей в память
                activityMeetings.ChangeFragment(fragmentPlace, true);
            }
        });
        //===========================================================================================



        // til_time_act /////////////////////////////////////////////////////////////////////////////
        til_time_act.setThreshold(100);
        til_time_act.setText(globalApp.requestMeeting.getTime()); // восстанавливаем выбранное значение из памяти

        //формируем список для сохранения времени
        ArrayAdapter<String> adapter_time = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.times);
        til_time_act.setAdapter(adapter_time);

        til_time_act.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                til_time.setErrorEnabled(false);
            }
        });
        //==========================================================================================



        // til_comment ///////////////////////////////////////////////////////////////////////////////
        til_comment_et.setText(globalApp.requestMeeting.getComment()); // восстанавливаем выбранное значение из памяти

        // показывать/не показывать подсказку
        if (til_comment_et.getText().toString().isEmpty()) {
            til_comment.setHelperText(getString(R.string.til_comment));
        } else {
            til_comment.setHelperText(" ");
        }

        //слушатель - показывать/не показывать подсказку
        til_comment_et.addTextChangedListener(new TextWatcher() { // при изменении текста в комментарии к встрече
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {             }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {            }
            @Override
            public void afterTextChanged(Editable s) {
                if (til_comment_et.getText().toString().isEmpty()) {
                    til_comment.setHelperText(getString(R.string.til_comment));
                } else {
                    til_comment.setHelperText(" ");
                }
            }
        });
        //===============================================================================================



        svRequestMeeting.setVisibility(View.VISIBLE); // Показываем UI
        pbRequestMeeting.setVisibility(View.GONE); // крутилку скрываем



    }



    /** Автоматическая модерация, если есть сомнения, то в заявке на встречу в поле moderation устанавливается флаг false*/
    private void CheckModeration(){

        if (til_comment_et.getText().toString().toLowerCase().contains("секс")
            |til_contact_et.getText().toString().toLowerCase().contains("секс")
            | til_comment_et.getText().toString().toLowerCase().contains(" бля")
            | til_contact_et.getText().toString().toLowerCase().contains(" бля")

        ) {
            globalApp.requestMeeting.setModeration(false);
        } else {
            globalApp.requestMeeting.setModeration(true);
        }

    }



    /**
     * Уменьшение количества бесплатных заявок на встречи
     */
    private void DecrementCountRequestMeetings() {

        //отнимаем одну встречу
        int countRequestMeetings = Integer.valueOf(globalApp.currentUser.getCountRequestMeetings())-1;
        globalApp.currentUser.setCountRequestMeetings(String.valueOf(countRequestMeetings));

        DocumentReference documentReference = globalApp.GenerateDocumentReference("users", globalApp.GetCurrentUserUid()); // формируем путь к документу
        documentReference.update("countRequestMeetings", globalApp.currentUser.getCountRequestMeetings())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()){
                            globalApp.Log(getClass().getSimpleName(), "DecrementCountRequestMeetings", "Ошибка, бесплатная встреча списана, но не записано новое значение в БД", true);
                        }
                    }
                });
    }


    /**
     * Формирование списка возрастов
     * @param beginAge начальный возраст
     * @param endAge конечный возраст
     * @return список возрастов
     */
    private ArrayList<String> CreateAges(int beginAge, int endAge)
    {
        ArrayList<String> arrayListAges = new ArrayList<String>();

        for (int i=beginAge; i <= endAge; i++ ) {
            arrayListAges.add(String.valueOf(i));
            //arrayListAges.add(i);
        }

        return arrayListAges;
    }

    /**
     * Формирует список городов и возвращает в виде адаптера ArrayAdapter<String>
     * @param region регион
     * @return заполненный адаптер типа ArrayAdapter<String>
     */
    private ArrayAdapter<String> CreateAdapterTowns(String region){

        ArrayAdapter<String> adapter_towns;

        switch(region) {
            case "Алтайский край":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.altaiRegion);
                break;

            case "Амурская область":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.amurskayaOblast);
                break;

            case "Архангельская область":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.arkhangelskRegion);
                break;

            case "Астраханская область":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.astrakhanRegion);
                break;

            case "Белгородская область":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.belgorodRegion);
                break;

            case "Брянская область":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.bryanskRegion);
                break;

            case "Владимирская область":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.vladimirRegion);
                break;

            case "Волгоградская область":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.volgogradRegion);
                break;

            case "Вологодская область":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.vologodskayaOblast);
                break;

            case "Воронежская область":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.voronezhRegion);
                break;

            case "Еврейская автономная область":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.jewishAutonomousRegion);
                break;

            case "Забайкальский край":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.transbaikalRegion);
                break;

            case "Ивановская область":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.ivanovoRegion);
                break;

            case "Иркутская область":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.irkutskRegion);
                break;

            case "Кабардино-Балкарская Республика":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.kabardinoBalkarRepublic);
                break;

            case "Калининградская область":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.kaliningradRegion);
                break;

            case "Калужская область":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.kalugaRegion);
                break;

            case "Камчатский край":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.kamchatkaKrai);
                break;

            case "Карачаево-Черкесская Республика":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.karachayCherkessRepublic);
                break;

            case "Кемеровская область":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.kemerovoRegion);
                break;

            case "Кировская область":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.kirovRegion);
                break;

            case "Костромская область":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.kostromaRegion);
                break;

            case "Краснодарский край":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.krasnodarRegion);
                break;

            case "Красноярский край":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.krasnoyarskRegion);
                break;

            case "Курганская область":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.kurganRegion);
                break;

            case "Курская область":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.kurskRegion);
                break;

            case "Ленинградская область":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.leningradRegion);
                break;

            case "Липецкая область":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.lipetskRegion);
                break;

            case "Магаданская область":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.magadanRegion);
                break;

            case "Московская область":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.moscowRegion);
                break;

            case "Мурманская область":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.murmanskRegion);
                break;

            case "Ненецкий автономный округ":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.nenetsAutonomousOkrug);
                break;

            case "Нижегородская область":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.nizhnyNovgorodRegion);
                break;

            case "Новгородская область":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.novgorodRegion);
                break;

            case "Новосибирская область":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.novosibirskRegion);
                break;

            case "Омская область":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.omskRegion);
                break;

            case "Оренбургская область":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.orenburgRegion);
                break;

            case "Орловская область":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.oryolRegion);
                break;

            case "Пензенская область":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.penzaRegion);
                break;

            case "Пермский край":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.permTerritory);
                break;

            case "Приморский край":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.primorskyKrai);
                break;

            case "Псковская область":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.pskovRegion);
                break;

            case "Республика Адыгея":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.republicOfAdygea);
                break;

            case "Республика Алтай":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.altaiRepublic);
                break;

            case "Республика Башкортостан":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.republicOfBashkortostan);
                break;

            case "Республика Бурятия":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.theRepublicOfBuryatia);
                break;

            case "Республика Дагестан":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.theRepublicOfDagestan);
                break;

            case "Республика Ингушетия":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.theRepublicOfIngushetia);
                break;

            case "Республика Калмыкия":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.republicOfKalmykia);
                break;

            case "Республика Карелия":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.republicOfKarelia);
                break;

            case "Республика Коми":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.komiRepublic);
                break;

            case "Республика Крым":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.republicOfCrimea);
                break;

            case "Республика Марий Эл":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.mariElRepublic);
                break;

            case "Республика Мордовия":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.theRepublicOfMordovia);
                break;

            case "Республика Саха (Якутия)":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.theRepublicOfSakhaYakutia);
                break;

            case "Республика Северная Осетия - Алания":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.republicOfNorthOssetiaAlania);
                break;

            case "Республика Татарстан":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.republicOfTatarstan);
                break;

            case "Республика Тыва":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.tyvaRepublic);
                break;

            case "Республика Хакасия":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.theRepublicOfKhakassia);
                break;

            case "Ростовская область":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.rostovRegion);
                break;

            case "Рязанская область":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.ryazanOblast);
                break;

            case "Самарская область":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.samaraRegion);
                break;

            case "Саратовская область":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.saratovRegion);
                break;

            case "Сахалинская область":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.sakhalinRegion);
                break;

            case "Свердловская область":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.sverdlovskRegion);
                break;

            case "Смоленская область":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.smolenskRegion);
                break;

            case "Ставропольский край":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.stavropolRegion);
                break;

            case "Тамбовская область":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.tambovRegion);
                break;

            case "Тверская область":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.tverRegion);
                break;

            case "Томская область":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.tomskRegion);
                break;

            case "Тульская область":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.tulaRegion);
                break;

            case "Тюменская область":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.tyumenRegion);
                break;

            case "Удмуртская Республика":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.udmurtia);
                break;

            case "Ульяновская область":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.ulyanovskRegion);
                break;

            case "Хабаровский край":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.khabarovskRegion);
                break;

            case "Ханты-Мансийский автономный округ - Югра":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.khantyMansiAutonomousOkrugYugra);
                break;

            case "Челябинская область":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.chelyabinskRegion);
                break;

            case "Чеченская Республика":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.chechenRepublic);
                break;

            case "Чувашская Республика - Чувашия":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.chuvashRepublicChuvashia);
                break;

            case "Чукотский автономный округ":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.chukotkaAutonomousDistrict);
                break;

            case "Ямало-Ненецкий автономный округ":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.yamaloNenetsAutonomousDistrict);
                break;

            case "Ярославская область":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.yaroslavlRegion);
                break;

            default:
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list); // наверное пустой будет, не проверял
                break;
        }
        return adapter_towns;
    }

    /**
     * Сохраняет значения введенных полей на настоящем фрагменте в оперативную память
     */
    private void SaveParamsToRAM () {

        globalApp.requestMeeting.setName(til_name_et.getText().toString());
        globalApp.requestMeeting.setGender(til_gender_act.getEditableText().toString());
        globalApp.requestMeeting.setAge(til_age_act.getText().toString());
        globalApp.requestMeeting.setPhone(til_phone_et.getText().toString().trim());

        if (cb_only_write.isChecked()){
            globalApp.requestMeeting.setOnlyWrite("trueTrue");
        } else {
            globalApp.requestMeeting.setOnlyWrite("");
        }

        globalApp.requestMeeting.setSocNet(til_soc_net_et.getText().toString().trim());
        globalApp.requestMeeting.setContact( til_contact_et.getText().toString().trim());
        globalApp.requestMeeting.setGender_partner( til_gender_partner_act.getEditableText().toString());
        globalApp.requestMeeting.setAge_min( til_age_min_act.getText().toString());
        globalApp.requestMeeting.setAge_max( til_age_max_act.getText().toString());
        globalApp.requestMeeting.setRegion( til_region_act.getEditableText().toString());
        globalApp.requestMeeting.setTown( til_town_act.getEditableText().toString());
        //classGlobalApp.GetRequestMeeting().setPlace( til_place_et.getEditableText().toString());
        globalApp.requestMeeting.setTime( til_time_act.getEditableText().toString());
        globalApp.requestMeeting.setComment( til_comment_et.getText().toString().trim());

    }


}