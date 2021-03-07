package com.zizi.rendezvous.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.zizi.rendezvous.Activity.ActivityLogin;
import com.zizi.rendezvous.Activity.ActivityMeetings;
import com.zizi.rendezvous.Activity.ActivityPay;
import com.zizi.rendezvous.GlobalApp;
import com.zizi.rendezvous.Data.Data;
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
  //  private Dialog dialog; //класс для показа всплывающих окон
    //private FragmentManager fragmentManager; // для управления показом компонентов

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
    private TextInputEditText til_contact_et;
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
        //dialog = new Dialog(); // класс для показа всплывающих окон
        //fragmentManager = getActivity().getSupportFragmentManager();

        // находим все вьюхи на активити
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
        //=========================================================================================



    }



    @Override
    public void onStart() {
        super.onStart();

        if (!globalApp.IsAuthorized()) { // если пользователь не авторизован
            startActivity(new Intent(getActivity().getApplicationContext(), ActivityLogin.class)); // отправляем к началу на авторизацию
            getActivity().finish(); // убиваем активити
        } else {
            UpdateUI();
        }

    }


    /**
     * Обновляет интерфейс пользователя
     */
    private void UpdateUI () {

        svRequestMeeting.setVisibility(View.GONE); // скрываем виджеты
        pbRequestMeeting.setVisibility(View.VISIBLE); // показываем прогрессбар



        // materialToolbar ////////////////////////////////////////////////////////////////////////////////
        materialToolbar.setTitle("Заявка"); // заголовок в панельке верхней
        materialToolbar.getMenu().findItem(R.id.request).setVisible(false); // скрываем пункт заявки на встречу

        if (globalApp.GetParam("statusRequestMeeting").equals(Data.ACTIVE)) { //если статус заявки активный
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
                    //ничего не делаем
                    //getActivity().onBackPressed();
                }
            });
        }
        //==========================================================================================



        // til_name_et //////////////////////////////////////////////////////////////////////////////
        til_name_et.setText(globalApp.GetRequestMeeting().getName()); // восстанавливаем текст из памяти
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
        til_gender_act.setText(globalApp.GetRequestMeeting().getGender());

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
        til_age_act.setText(globalApp.GetRequestMeeting().getAge()); // восстанавливаем выбранное значение из памяти

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
        til_phone_et.setText(globalApp.GetRequestMeeting().getPhone()); // восстанавливаем выбранное значение из памяти

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
        if (globalApp.GetRequestMeeting().getOnlyWrite().equals("trueTrue")){ // если галка отмечена и сохранена
            cb_only_write.setChecked(true);
        } else {
            cb_only_write.setChecked(false);// то не ставим галку
        }
        // ==============================================================================================



        // til_soc_net_et ////////////////////////////////////////////////////////////////////////////////
        til_soc_net_et.setText(globalApp.GetRequestMeeting().getSocNet()); // восстанавливаем выбранное значение из памяти);
        // =============================================================================================


        // til_contact ////////////////////////////////////////////////////////////////////////////
        til_contact_et.setText(globalApp.GetRequestMeeting().getContact()); // восстанавливаем выбранное значение из памяти

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
        til_gender_partner_act.setText(globalApp.GetRequestMeeting().getGender_partner()); // восстанавливаем выбранное значение из памяти

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

        if (globalApp.GetRequestMeeting().getAge_min().equals("")){
            til_age_min_act.setText("18");
        } else {
            til_age_min_act.setText(globalApp.GetRequestMeeting().getAge_min()); // восстанавливаем выбранное значение из памяти
        }

        if (globalApp.GetRequestMeeting().getAge_max().equals("")) {
            til_age_max_act.setText("70");
        } else {
            til_age_max_act.setText(globalApp.GetRequestMeeting().getAge_max()); // восстанавливаем выбранное значение из памяти
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
        til_region_act.setText(globalApp.GetRequestMeeting().getRegion());  // восстанавливаем выбранное значение из памяти

        //формируем список для выбора
        ArrayAdapter<String> adapter_regions = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.regionsTmp);
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
            til_town_act.setText(globalApp.GetRequestMeeting().getTown()); // подгружаем имя города из памяти
            til_town_act.setAdapter(CreateAdapterTowns(globalApp.GetRequestMeeting().getRegion()));//тут нужно дернуть слушатель, чтобы подгрузил города
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
        til_place_et.setText(globalApp.GetRequestMeeting().CreateStringFromArrayListPlaces());

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
        til_time_act.setText(globalApp.GetRequestMeeting().getTime()); // восстанавливаем выбранное значение из памяти

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
        til_comment_et.setText(globalApp.GetRequestMeeting().getComment()); // восстанавливаем выбранное значение из памяти

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

                    //нужно проверить есть ли бесплатные заявки
                    int countRequestMeetings = Integer.valueOf(globalApp.currentUser.getCountRequestMeetings());
                    globalApp.Log(getClass().getSimpleName(), "btn_apply_request.setOnClickListener", "Количество бесплатных заявок = " + countRequestMeetings, false);
                    if (countRequestMeetings > 0) {

                        CheckModeration(); //проверка на модерацию

                        SaveParamsToRAM(); // сохранение в оперативную память

                        // сохраняем заявку в БД
                        documentReference = globalApp.GenerateDocumentReference("meetings", globalApp.GetCurrentUserUid());
                        documentReference.set(globalApp.GetRequestMeeting()).addOnCompleteListener(new OnCompleteListener<Void>() { //прям объект класса кидаем в БД
                            @Override
                            public void onComplete(@NonNull Task<Void> task) { //если задачка по работе с БД выполнилась
                                if (task.isSuccessful()) { //если задача по работе с БД выполнилась успешно

                                    globalApp.SaveRequestMeetingToMemory(); // сохраняем заявку в память

                                    globalApp.PreparingToSave("statusRequestMeeting", Data.ACTIVE); // отмечаем статус заявки активным
                                    globalApp.SaveParams();

                                    DecrementCountRequestMeetings();// вычеркиваем одну бесплатную заявку и записываем в БД

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
                                                    intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TASK //очищаем стек с задачей
                                                            |Intent.FLAG_ACTIVITY_NEW_TASK   //хотим создать активити в основной очищенной задаче
                                                    );

                                                    startActivity(intent); //переходим на другую активити, то есть фактически входим в приложение
                                                }
                                            })
                                            //.setNegativeButton("No", null)
                                            .show();
                                    //===============================================================================


                                    //показываем всплывающее окно
                                    //dialog.setTitle("Ошибка записи в БД");
                                    //dialog.setMessage("Ошибка при подаче заявки. Проверьте включен ли Интернет. Проверьете доступен ли Интернет. Ошибка записи заявки в БД: " + task.getException());
                                    //dialog.setPositiveButtonRedirect(Data.ACTIVITY_LOGIN);
                                    //dialog.show(fragmentManager, "classDialog");

                                }

                            }
                        });
                    } else {

                        //Перейти на страничку с оплатой
                        //создаем намерение, что хотим перейти на другую активити
                        Intent intent = new Intent(getActivity().getApplicationContext(), ActivityPay.class);
                        //intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TASK //очищаем стек с задачей
                        //                  |Intent.FLAG_ACTIVITY_NEW_TASK   //хотим создать активити в основной очищенной задаче
                        //                );

                        startActivity(intent); //переходим на другую активити, то есть фактически входим в приложение

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

                    //Toast.makeText(getActivity().getApplicationContext(), "Заполните обязательные поля выделенные красным цветом", Toast.LENGTH_LONG).show();
                    //dialog.setTitle("Заполните все поля");
                    //dialog.setMessage("Заполните обязательные поля выделенные красным цветом");
                    //dialog.show(fragmentManager, "classDialog");

                }


            }
        });
        //==================================================================================================



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
            globalApp.GetRequestMeeting().setModeration(false);
        } else {
            globalApp.GetRequestMeeting().setModeration(true);
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
            case "Республика Мордовия":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.theRepublicOfMordovia);
                break;
            case "Нижегородская область":
                adapter_towns = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_drop_down_list, Data.nizhnyNovgorodRegion);
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

        globalApp.GetRequestMeeting().setName(til_name_et.getText().toString());
        globalApp.GetRequestMeeting().setGender(til_gender_act.getEditableText().toString());
        globalApp.GetRequestMeeting().setAge(til_age_act.getText().toString());
        globalApp.GetRequestMeeting().setPhone(til_phone_et.getText().toString().trim());

        if (cb_only_write.isChecked()){
            globalApp.GetRequestMeeting().setOnlyWrite("trueTrue");
        } else {
            globalApp.GetRequestMeeting().setOnlyWrite("");
        }

        globalApp.GetRequestMeeting().setSocNet(til_soc_net_et.getText().toString().trim());
        globalApp.GetRequestMeeting().setContact( til_contact_et.getText().toString().trim());
        globalApp.GetRequestMeeting().setGender_partner( til_gender_partner_act.getEditableText().toString());
        globalApp.GetRequestMeeting().setAge_min( til_age_min_act.getText().toString());
        globalApp.GetRequestMeeting().setAge_max( til_age_max_act.getText().toString());
        globalApp.GetRequestMeeting().setRegion( til_region_act.getEditableText().toString());
        globalApp.GetRequestMeeting().setTown( til_town_act.getEditableText().toString());
        //classGlobalApp.GetRequestMeeting().setPlace( til_place_et.getEditableText().toString());
        globalApp.GetRequestMeeting().setTime( til_time_act.getEditableText().toString());
        globalApp.GetRequestMeeting().setComment( til_comment_et.getText().toString().trim());




/*        classGlobalApp.PreparingToSave("name", til_name_et.getText().toString());
        classGlobalApp.PreparingToSave("gender", til_gender_act.getEditableText().toString());
        classGlobalApp.PreparingToSave("age", til_age_act.getText().toString());
        classGlobalApp.PreparingToSave("phone", til_phone_et.getText().toString().trim());

        if (cb_only_write.isChecked()){
            classGlobalApp.PreparingToSave("onlyWrite", "trueTrue");
        } else {
            classGlobalApp.PreparingToSave("onlyWrite", "");
        }

        classGlobalApp.PreparingToSave("socNet", til_soc_net_et.getText().toString().trim());
        classGlobalApp.PreparingToSave("contact", til_contact_et.getText().toString().trim());
        classGlobalApp.PreparingToSave("gender_partner", til_gender_partner_act.getEditableText().toString());
        classGlobalApp.PreparingToSave("age_min", til_age_min_act.getText().toString());
        classGlobalApp.PreparingToSave("age_max", til_age_max_act.getText().toString());
        classGlobalApp.PreparingToSave("region", til_region_act.getEditableText().toString());
        classGlobalApp.PreparingToSave("town", til_town_act.getEditableText().toString());
        classGlobalApp.PreparingToSave("place", til_place_et.getEditableText().toString());
        classGlobalApp.PreparingToSave("time", til_time_act.getEditableText().toString());
        classGlobalApp.PreparingToSave("comment", til_comment_et.getText().toString().trim());

        classGlobalApp.SaveParams();*/
    }


}