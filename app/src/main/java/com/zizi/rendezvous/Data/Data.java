package com.zizi.rendezvous.Data;

public class Data {

    //константы
    public static final String ANY_PLACE = "Любое место";
    public static final String ANY_TIME = "Любое время";
    public static final String CHANNEL_ID = "Сообщения";
    public static final String TAG_LOG = "!@#";
    public static final String FRAGMENT_CHAT = "FragmentChat";
    public static final String OTHER = "Прочее";
    public static final String DEFAULT_COUNT_REQUEST_MEETINGS = "31"; // количество бесплатных встреч по умолчанию
    public static final String VERSION_CODE_KEY = "VERSION_CODE_KEY"; // для запроса последней версии с удаленного конфига
    public static final boolean USE_DEBAG_DB = true; //использовать отладочную ветку БД

    //статусы заявки на встречу
    public static final String STATUS_ACTIVE = "STATUS_ACTIVE";
    public static final String STATUS_NOT_ACTIVE = "STATUS_NOT_ACTIVE";
    public static final String STATUS_MODERATION_FAIL = "STATUS_MODERATION_FAIL";



    //массив с временами
    public static final String[] times = new String[]{
            //"Любое время",
            ANY_TIME,
            "Сейчас",
            "00:00",
            "01:00",
            "02:00",
            "03:00",
            "04:00",
            "05:00",
            "06:00",
            "07:00",
            "08:00",
            "09:00",
            "10:00",
            "11:00",
            "12:00",
            "13:00",
            "14:00",
            "15:00",
            "16:00",
            "17:00",
            "18:00",
            "19:00",
            "20:00",
            "21:00",
            "22:00",
            "23:00"
    };



    //Список всех регионов
    public static final String[] regions = new String[]{
        "Алтайский край",
        "Амурская область",
        "Архангельская область",
        "Астраханская область",
        "Белгородская область",
        "Брянская область",
        "Владимирская область",
        "Волгоградская область",
        "Вологодская область",
        "Воронежская область",
        "Еврейская автономная область",
        "Забайкальский край",
        "Ивановская область",
        "Иркутская область",
        "Кабардино-Балкарская Республика",
        "Калининградская область",
        "Калужская область",
        "Камчатский край",
        "Карачаево-Черкесская Республика",
        "Кемеровская область",
        "Кировская область",
        "Костромская область",
        "Краснодарский край",
        "Красноярский край",
        "Курганская область",
        "Курская область",
        "Липецкая область",
        "Магаданская область",
        "Московская область",
        "Мурманская область",
        "Ненецкий автономный округ",
        "Нижегородская область",
        "Новгородская область",
        "Новосибирская область",
        "Омская область",
        "Оренбургская область",
        "Орловская область",
        "Пензенская область",
        "Пермский край",
        "Приморский край",
        "Псковская область",
        "Республика Адыгея",
        "Республика Алтай",
        "Республика Башкортостан",
        "Республика Бурятия",
        "Республика Дагестан",
        "Республика Ингушетия",
        "Республика Калмыкия",
        "Республика Карелия",
        "Республика Коми",
        "Республика Крым",
        "Республика Марий Эл",
        "Республика Мордовия",
        "Республика Саха (Якутия)",
        "Республика Северная Осетия - Алания",
        "Республика Татарстан",
        "Республика Тыва",
        "Республика Хакасия",
        "Ростовская область",
        "Рязанская область",
        "Самарская область",
        "Саратовская область",
        "Сахалинская область",
        "Свердловская область",
        "Смоленская область",
        "Ставропольский край",
        "Тамбовская область",
        "Тверская область",
        "Томская область",
        "Тульская область",
        "Тюменская область",
        "Удмуртская Республика",
        "Ульяновская область",
        "Хабаровский край",
        "Ханты-Мансийский автономный округ - Югра",
        "Челябинская область",
        "Чеченская Республика",
        "Чувашская Республика - Чувашия",
        "Чукотский автономный округ",
        "Ямало-Ненецкий автономный округ",
        "Ярославская область"
    };

    //Алтайский край - список городов
    public static final String[] altaiRegion = new String[]{
        "Алейск",
        "Барнаул",
        "Белокуриха",
        "Бийск",
        "Горняк",
        "Заринск",
        "Змеиногорск",
        "Камень-на-Оби",
        "Новоалтайск",
        "Рубцовск",
        "Славгород",
        "Яровое"
    };



    //Амурская область - список городов
    public static final String[] amurskayaOblast = new String[]{
        "Белогорск",
        "Благовещенск",
        "Завитинск",
        "Зея",
        "Райчихинск",
        "Свободный",
        "Сковородино",
        "Тында",
        "Циолковский",
        "Шимановск"
    };



    //Архангельская область - список городов
    public static final String[] arkhangelskRegion = new String[]{
        "Архангельск",
        "Коряжма",
        "Котлас",
        "Мирный",
        "Новодвинск",
        "Онега",
        "Северодвинск"
    };



    //Астраханская область - список городов
    public static final String[] astrakhanRegion = new String[]{
        "Астрахань",
        "Ахтубинск",
        "Знаменск",
        "Камызяк",
        "Нариманов",
        "Харабали"
    };



    //Белгородская область - список городов
    public static final String[] belgorodRegion = new String[]{
        "Алексеевка",
        "Белгород",
        "Бирюч",
        "Валуйки",
        "Грайворон",
        "Губкин",
        "Короча",
        "Новый Оскол",
        "Старый Оскол",
        "Строитель",
        "Шебекино"

    };



    //Брянская область - список городов
    public static final String[] bryanskRegion = new String[]{
        "Брянск",
        "Дятьково",
        "Жуковка",
        "Злынка",
        "Карачев",
        "Клинцы",
        "Мглин",
        "Новозыбков",
        "Почеп",
        "Севск",
        "Сельцо",
        "Стародуб",
        "Сураж",
        "Трубчевск",
        "Унеча",
        "Фокино"

    };



    //Владимирская область - список городов
    public static final String[] vladimirRegion = new String[]{
        "Александров",
        "Владимир",
        "Вязники",
        "Гороховец",
        "Гусь-Хрустальный",
        "Камешково",
        "Карабаново",
        "Киржач",
        "Ковров",
        "Кольчугино",
        "Костерёво",
        "Курлово",
        "Лакинск",
        "Меленки",
        "Муром",
        "Петушки",
        "Покров",
        "Радужный",
        "Собинка",
        "Струнино",
        "Судогда",
        "Суздаль",
        "Юрьев-Польский",

    };



    //Волгоградская область - список городов
    public static final String[] volgogradRegion = new String[]{
        "Волгоград",
        "Волжский",
        "Дубовка",
        "Жирновск",
        "Калач-на-Дону",
        "Камышин",
        "Котельниково",
        "Котово",
        "Краснослободск",
        "Ленинск",
        "Михайловка",
        "Николаевск",
        "Новоаннинский",
        "Палласовка",
        "Петров Вал",
        "Серафимович",
        "Суровикино",
        "Урюпинск",
        "Фролово"

    };



    //Вологодская область - список городов
    public static final String[] vologodskayaOblast = new String[]{
        "Бабаево",
        "Белозерск",
        "Великий Устюг",
        "Вологда",
        "Вытегра",
        "Грязовец",
        "Кадников",
        "Кириллов",
        "Красавино",
        "Никольск",
        "Сокол",
        "Тотьма",
        "Усолье",
        "Устюжна",
        "Харовск",
        "Череповец"

    };



    //Воронежская область - список городов
    public static final String[] voronezhRegion = new String[]{
        "Бобров",
        "Богучар",
        "Борисоглебск",
        "Бутурлиновка",
        "Воронеж",
        "Калач",
        "Лиски",
        "Нововоронеж",
        "Новохопёрск",
        "Острогожск",
        "Павловск",
        "Поворино",
        "Россошь",
        "Семилуки",
        "Эртиль"

    };



    //Еврейская автономная область - список городов
    public static final String[] jewishAutonomousRegion = new String[]{
            "Биробиджан",
            "Облучье"

    };



    //Забайкальский край - список городов
    public static final String[] transbaikalRegion = new String[]{
            "Балей",
            "Борзя",
            "Краснокаменск",
            "Могоча",
            "Нерчинск",
            "Петровск-Забайкальский",
            "Сретенск",
            "Хилок",
            "Чита",
            "Шилка"

    };



    //Ивановская область - список городов
    public static final String[] ivanovoRegion = new String[]{
        "Вичуга",
        "Гаврилов Посад",
        "Заволжск",
        "Иваново",
        "Кинешма",
        "Комсомольск",
        "Кохма",
        "Наволоки",
        "Плёс",
        "Приволжск",
        "Пучеж",
        "Родники",
        "Тейково",
        "Шуя",
        "Южа",
        "Юрьевец"

    };



    //Иркутская область - список городов
    public static final String[] irkutskRegion = new String[]{
        "Алзамай",
        "Ангарск",
        "Байкальск",
        "Бирюсинск",
        "Бодайбо",
        "Братск",
        "Вихоревка",
        "Железногорск-Илимский",
        "Зима",
        "Иркутск",
        "Киренск",
        "Нижнеудинск",
        "Саянск",
        "Свирск",
        "Слюдянка",
        "Тайшет",
        "Тулун",
        "Усолье-Сибирское",
        "Усть-Илимск",
        "Усть-Кут",
        "Черемхово",
        "Шелехов"

    };




    //Кабардино-Балкарская Республика - список городов
    public static final String[] kabardinoBalkarRepublic = new String[]{
        "Баксан",
        "Майский",
        "Нальчик",
        "Нарткала",
        "Прохладный",
        "Терек",
        "Тырныауз",
        "Чегем"
    };



    //Калининградская область - список городов
    public static final String[] kaliningradRegion = new String[]{
        "Багратионовск",
        "Балтийск",
        "Гвардейск",
        "Гурьевск",
        "Гусев",
        "Зеленоградск",
        "Калининград",
        "Краснознаменск",
        "Ладушкин",
        "Мамоново",
        "Неман",
        "Нестеров",
        "Озёрск",
        "Пионерский",
        "Полесск",
        "Правдинск",
        "Приморск",
        "Светлогорск",
        "Светлый",
        "Славск",
        "Советск",
        "Черняховск"
    };



    //Калужская область - список городов
    public static final String[] kalugaRegion = new String[]{
        "Балабаново",
        "Белоусово",
        "Боровск",
        "Ермолино",
        "Жиздра",
        "Жуков",
        "Калуга	Калуга",
        "Киров",
        "Козельск",
        "Кондрово",
        "Кремёнки",
        "Людиново",
        "Малоярославец",
        "Медынь",
        "Мещовск",
        "Мосальск",
        "Обнинск",
        "Сосенский",
        "Спас-Деменск",
        "Сухиничи",
        "Таруса",
        "Юхнов"
    };



    //Камчатский край - список городов
    public static final String[] kamchatkaKrai = new String[]{
            "Вилючинск",
            "Елизово",
            "Петропавловск-Камчатский"
    };



    //Карачаево-Черкесская Республика - список городов
    public static final String[] karachayCherkessRepublic = new String[]{
            "Карачаевск",
            "Теберда",
            "Усть-Джегута",
            "Черкесск"
    };



    //Кемеровская область - список городов
    public static final String[] kemerovoRegion = new String[]{
            "Анжеро-Судженск",
            "Белово",
            "Берёзовский",
            "Гурьевск",
            "Калтан",
            "Кемерово",
            "Киселёвск",
            "Ленинск-Кузнецкий",
            "Мариинск",
            "Междуреченск",
            "Мыски",
            "Новокузнецк",
            "Осинники",
            "Полысаево",
            "Прокопьевск",
            "Салаир",
            "Тайга",
            "Таштагол",
            "Топки",
            "Юрга"
    };




    //Кировская область - список городов
    public static final String[] kirovRegion = new String[]{
        "Белая",
        "Вятские Поляны",
        "Зуевка",
        "Киров",
        "Кирово-Чепецк",
        "Кирс",
        "Котельнич",
        "Луза",
        "Малмыж",
        "Мураши",
        "Нолинск",
        "Омутнинск",
        "Орлов",
        "Слободской",
        "Советск",
        "Сосновка",
        "Уржум",
        "Яранск"
    };




    //Костромская область - список городов
    public static final String[] kostromaRegion = new String[]{
            "Буй",
            "Волгореченск",
            "Галич",
            "Кологрив",
            "Кострома",
            "Макарьев",
            "Мантурово",
            "Нерехта",
            "Нея",
            "Солигалич",
            "Чухлома",
            "Шарья"
    };



    //Краснодарский край - список городов
    public static final String[] krasnodarRegion = new String[]{
            "Абинск",
            "Анапа",
            "Апшеронск",
            "Армавир",
            "Белореченск",
            "Геленджик",
            "Горячий Ключ",
            "Гулькевичи",
            "Ейск",
            "Кореновск",
            "Краснодар",
            "Кропоткин",
            "Крымск",
            "Курганинск",
            "Лабинск",
            "Новокубанск",
            "Новороссийск",
            "Приморско-Ахтарск",
            "Славянск-на-Кубани",
            "Сочи",
            "Темрюк",
            "Тимашёвск",
            "Тихорецк",
            "Туапсе",
            "Усть-Лабинск",
            "Хадыженск"
    };



    //Красноярский край - список городов
    public static final String[] krasnoyarskRegion = new String[]{
            "Артёмовск",
            "Ачинск",
            "Боготол",
            "Бородино",
            "Дивногорск",
            "Дудинка",
            "Енисейск",
            "Железногорск",
            "Заозёрный",
            "Зеленогорск",
            "Игарка",
            "Иланский",
            "Канск",
            "Кодинск",
            "Красноярск",
            "Лесосибирск",
            "Минусинск",
            "Назарово",
            "Норильск",
            "Сосновоборск",
            "Ужур",
            "Уяр",
            "Шарыпово"
    };



    //Курганская область - список городов
    public static final String[] kurganRegion = new String[]{
            "Далматово",
            "Катайск",
            "Курган",
            "Куртамыш",
            "Макушино",
            "Петухово",
            "Шадринск",
            "Шумиха",
            "Щучье"
    };



    //Курская область - список городов
    public static final String[] kurskRegion = new String[]{
            "Дмитриев",
            "Железногорск",
            "Курск",
            "Курчатов",
            "Льгов",
            "Обоянь",
            "Рыльск",
            "Суджа",
            "Фатеж",
            "Щигры"
    };



    //Ленинградская область - список городов
    public static final String[] leningradRegion = new String[]{
            "Бокситогорск",
            "Волосово",
            "Волхов",
            "Всеволожск",
            "Выборг",
            "Высоцк",
            "Гатчина",
            "Ивангород",
            "Каменногорск",
            "Кингисепп",
            "Кириши",
            "Кировск",
            "Коммунар",
            "Кудрово",
            "Лодейное Поле",
            "Луга",
            "Любань",
            "Мурино",
            "Никольское",
            "Новая Ладога",
            "Отрадное",
            "Пикалёво",
            "Подпорожье",
            "Приморск",
            "Приозерск",
            "Санкт-Петербург",
            "Светогорск",
            "Сертолово",
            "Сланцы",
            "Сосновый Бор",
            "Сясьстрой",
            "Тихвин",
            "Тосно",
            "Шлиссельбург"
    };



    //Липецкая область - список городов
    public static final String[] lipetskRegion = new String[]{
            "Грязи",
            "Данков",
            "Елец",
            "Задонск",
            "Лебедянь",
            "Липецк",
            "Усмань",
            "Чаплыгин"
    };



    //Магаданская область - список городов
    public static final String[] magadanRegion = new String[]{
            "Магадан",
            "Сусуман"
    };


    //Московская область - список городов
    public static final String[] moscowRegion = new String[]{
        "Апрелевка",
        "Балашиха",
        "Белоозёрский",
        "Бронницы",
        "Верея",
        "Видное",
        "Волоколамск",
        "Воскресенск",
        "Высоковск",
        "Голицыно",
        "Дедовск",
        "Дзержинский",
        "Дмитров",
        "Долгопрудный",
        "Домодедово",
        "Дрезна",
        "Дубна",
        "Егорьевск",
        "Жуковский",
        "Зарайск",
        "Звенигород",
        "Ивантеевка",
        "Истра",
        "Кашира",
        "Клин",
        "Коломна",
        "Королёв",
        "Котельники",
        "Красноармейск",
        "Красногорск",
        "Краснозаводск",
        "Краснознаменск",
        "Кубинка",
        "Куровское",
        "Ликино - Дулёво",
        "Лобня",
        "Лосино-Петровский",
        "Луховицы",
        "Лыткарино",
        "Люберцы",
        "Можайск",
        "Москва",
        "Мытищи",
        "Наро-Фоминск",
        "Ногинск",
        "Одинцово",
        "Озёры",
        "Орехово-Зуево",
        "Павловский Посад",
        "Пересвет",
        "Подольск",
        "Протвино",
        "Пушкино",
        "Пущино",
        "Раменское",
        "Реутов",
        "Рошаль",
        "Руза",
        "Сергиев Посад",
        "Серпухов",
        "Солнечногорск",
        "Старая Купавна",
        "Ступино",
        "Талдом",
        "Фрязино",
        "Химки",
        "Хотьково",
        "Черноголовка",
        "Чехов",
        "Шатура",
        "Щёлково",
        "Электрогорск",
        "Электросталь",
        "Электроугли",
        "Яхрома"
    };



    //Мурманская область - список городов
    public static final String[] murmanskRegion = new String[]{
        "Апатиты",
        "Гаджиево",
        "Заозёрск",
        "Заполярный",
        "Кандалакша",
        "Кировск",
        "Ковдор",
        "Кола",
        "Мончегорск",
        "Мурманск",
        "Оленегорск",
        "Островной",
        "Полярные Зори",
        "Полярный",
        "Североморск",
        "Снежногорск"
    };



    //Ненецкий автономный округ - список городов
    public static final String[] nenetsAutonomousOkrug = new String[]{
            "Нарьян-Мар"
    };



    //Нижегородская область - список городов
    public static final String[] nizhnyNovgorodRegion = new String[]{
        "Арзамас",
        "Балахна",
        "Богородск",
        "Бор",
        "Ветлуга",
        "Володарск",
        "Ворсма",
        "Выкса",
        "Горбатов",
        "Городец",
        "Дзержинск",
        "Заволжье",
        "Княгинино",
        "Кстово",
        "Кулебаки",
        "Лукоянов",
        "Лысково",
        "Навашино",
        "Нижний Новгород",
        "Павлово",
        "Первомайск",
        "Перевоз",
        "Саров",
        "Семенов",
        "Сергач",
        "Урень",
        "Чкаловск",
        "Шахунья"
    };



    //Новгородская область - список городов
    public static final String[] novgorodRegion = new String[]{
            "Боровичи",
            "Валдай",
            "Великий Новгород",
            "Малая Вишера",
            "Окуловка",
            "Пестово",
            "Сольцы",
            "Старая Русса",
            "Холм",
            "Чудово"
    };



    //Новосибирская область - список городов
    public static final String[] novosibirskRegion = new String[]{
            "Барабинск",
            "Бердск",
            "Болотное",
            "Искитим",
            "Карасук",
            "Каргат",
            "Куйбышев",
            "Купино",
            "Новосибирск",
            "Обь",
            "Татарск",
            "Тогучин",
            "Черепаново",
            "Чулым"
    };



    //Омская область - список городов
    public static final String[] omskRegion = new String[]{
            "Исилькуль",
            "Калачинск",
            "Называевск",
            "Омск",
            "Тара",
            "Тюкалинск"
    };



    //Оренбургская область - список городов
    public static final String[] orenburgRegion = new String[]{
            "Абдулино",
            "Бугуруслан",
            "Бузулук",
            "Гай",
            "Кувандык",
            "Медногорск",
            "Новотроицк",
            "Оренбург",
            "Орск",
            "Соль-Илецк",
            "Сорочинск",
            "Ясный"
    };



    //Орловская область - список городов
    public static final String[] oryolRegion = new String[]{
        "Болхов",
        "Дмитровск",
        "Ливны",
        "Малоархангельск",
        "Мценск",
        "Новосиль",
        "Орёл"
    };



    //Пензенская область - список городов
    public static final String[] penzaRegion = new String[]{
            "Белинский",
            "Городище",
            "Заречный",
            "Каменка",
            "Кузнецк",
            "Нижний Ломов",
            "Никольск",
            "Пенза",
            "Сердобск",
            "Спасск",
            "Сурск"
    };



    //Пермский край - список городов
    public static final String[] permTerritory = new String[]{
        "Александровск",
        "Березники",
        "Верещагино",
        "Горнозаводск",
        "Гремячинск",
        "Губаха",
        "Добрянка",
        "Кизел",
        "Красновишерск",
        "Краснокамск",
        "Кудымкар",
        "Кунгур",
        "Лысьва",
        "Нытва",
        "Оса",
        "Оханск",
        "Очёр",
        "Пермь",
        "Соликамск",
        "Усолье",
        "Чайковский",
        "Чердынь",
        "Чёрмоз",
        "Чернушка",
        "Чусовой"
    };



    //Приморский край - список городов
    public static final String[] primorskyKrai = new String[]{
        "Арсеньев",
        "Артём",
        "Большой Камень",
        "Владивосток",
        "Дальнегорск",
        "Дальнереченск",
        "Лесозаводск",
        "Находка",
        "Партизанск",
        "Спасск-Дальний",
        "Уссурийск",
        "Фокино"
    };



    //Псковская область - список городов
    public static final String[] pskovRegion = new String[]{
            "Великие Луки",
            "Гдов",
            "Дно",
            "Невель",
            "Новоржев",
            "Новосокольники",
            "Опочка",
            "Остров",
            "Печоры",
            "Порхов",
            "Псков",
            "Пустошка",
            "Пыталово",
            "Себеж"
    };



    //Республика Адыгея - список городов
    public static final String[] republicOfAdygea = new String[]{
        "Адыгейск",
        "Майкоп"
    };



    //Республика Алтай - список городов
    public static final String[] altaiRepublic = new String[]{
            "Горно-Алтайск"
    };



    //Республика Башкортостан - список городов
    public static final String[] republicOfBashkortostan = new String[]{
            "Агидель",
            "Баймак",
            "Белебей",
            "Белорецк",
            "Бирск",
            "Благовещенск",
            "Давлеканово",
            "Дюртюли",
            "Ишимбай",
            "Кумертау",
            "Межгорье",
            "Мелеуза",
            "Нефтекамск",
            "Октябрьский",
            "Приютово",
            "Салават",
            "Сибай",
            "Стерлитамак",
            "Туймазы",
            "Уфа",
            "Учалы",
            "Чишмы",
            "Янаул"

    };



    //Республика Бурятия - список городов
    public static final String[] theRepublicOfBuryatia = new String[]{
            "Бабушкин",
            "Гусиноозёрск",
            "Закаменск",
            "Кяхта",
            "Северобайкальск",
            "Улан-Удэ"

    };



    //Республика Дагестан - список городов
    public static final String[] theRepublicOfDagestan = new String[]{
            "Буйнакск",
            "Дагестанские Огни",
            "Дербент",
            "Избербаш",
            "Каспийск",
            "Кизилюрт",
            "Кизляр",
            "Махачкала",
            "Хасавюрт",
            "Южно-Сухокумск"

    };



    //Республика Ингушетия - список городов
    public static final String[] theRepublicOfIngushetia = new String[]{
            "Карабулак",
            "Магас",
            "Малгобек",
            "Назрань",
            "Сунжа"

    };




    //Республика Калмыкия - список городов
    public static final String[] republicOfKalmykia = new String[]{
            "Городовиковск",
            "Лагань",
            "Элиста"

    };



    //Республика Карелия - список городов
    public static final String[] republicOfKarelia = new String[]{
        "Беломорск",
        "Кемь",
        "Кондопога",
        "Костомукша",
        "Лахденпохья",
        "Медвежьегорск",
        "Олонец",
        "Петрозаводск",
        "Питкяранта",
        "Пудож",
        "Сегежа",
        "Сортавала",
        "Суоярви"
    };



    //Республика Коми - список городов
    public static final String[] komiRepublic = new String[]{
        "Воркута",
        "Вуктыл",
        "Емва",
        "Инта",
        "Микунь",
        "Печора",
        "Сосногорск",
        "Сыктывкар",
        "Усинск",
        "Ухта"
    };



    //Республика Крым - список городов
    public static final String[] republicOfCrimea = new String[]{
            "Алупка",
            "Алушта",
            "Армянск",
            "Бахчисарай",
            "Белогорск",
            "Джанкой",
            "Евпатория",
            "Керчь",
            "Красноперекопск",
            "Саки",
            "Севастополь",
            "Симферополь",
            "Старый Крым",
            "Судак",
            "Феодосия",
            "Щёлкино",
            "Ялта"
    };



    //Республика Марий Эл - список городов
    public static final String[] mariElRepublic = new String[]{
        "Йошкар-Ола",
        "Волжск",
        "Звенигово",
        "Козьмодемьянск",
    };



    //Республика Мордовия - список городов
    public static final String[] theRepublicOfMordovia = new String[]{
        "Ардатов",
        "Инсар",
        "Ковылкино",
        "Краснослободск",
        "Рузаевка",
        "Саранск",
        "Темников"

    };



    //Республика Саха (Якутия) - список городов
    public static final String[] theRepublicOfSakhaYakutia = new String[]{
            "Алдан",
            "Верхоянск",
            "Вилюйск",
            "Ленск",
            "Мирный",
            "Нерюнгри",
            "Нюрба",
            "Олёкминск",
            "Покровск",
            "Среднеколымск",
            "Томмот",
            "Удачный",
            "Якутск"
    };



    //Республика Северная Осетия - Алания - список городов
    public static final String[] republicOfNorthOssetiaAlania = new String[]{
            "Алагир",
            "Ардон",
            "Беслан",
            "Владикавказ",
            "Дигора",
            "Моздок"
    };



    //Республика Татарстан - список городов
    public static final String[] republicOfTatarstan = new String[]{
            "Агрыз",
            "Азнакаево",
            "Альметьевск",
            "Арск",
            "Бавлы",
            "Болгар",
            "Бугульма",
            "Буинск",
            "Елабуга",
            "Заинск",
            "Зеленодольск",
            "Иннополис",
            "Казань",
            "Кукмор",
            "Лаишево",
            "Лениногорск",
            "Мамадыш",
            "Менделеевск",
            "Мензелинск",
            "Набережные Челны",
            "Нижнекамск",
            "Нурлат",
            "Тетюши",
            "Чистополь"
    };



    //Республика Тыва - список городов
    public static final String[] tyvaRepublic = new String[]{
            "Кызыл",
            "Ак-Довурак",
            "Шагонар",
            "Чадан",
            "Туран"
    };



    //Республика Хакасия - список городов
    public static final String[] theRepublicOfKhakassia = new String[]{
            "Абаза",
            "Абакан",
            "Саяногорск",
            "Сорск",
            "Черногорск"
    };



    //Ростовская область - список городов
    public static final String[] rostovRegion = new String[]{
            "Азов",
            "Аксай",
            "Батайск",
            "Белая Калитва",
            "Волгодонск",
            "Гуково",
            "Донецк",
            "Зверево",
            "Зерноград",
            "Каменск-Шахтинский",
            "Константиновск",
            "Миллерово",
            "Морозовск",
            "Новочеркасск",
            "Ростов-на-Дону",
            "Семикаракорск",
            "Красный Сулин",
            "Таганрог",
            "Цимлянск",
            "Шахты"
    };



    //Рязанская область - список городов
    public static final String[] ryazanOblast = new String[]{
        "Касимов",
        "Кораблино",
        "Михайлов",
        "Новомичуринск",
        "Рыбное",
        "Ряжск",
        "Рязань",
        "Сасово",
        "Скопин",
        "Спас-Клепики",
        "Спасск-Рязанский",
        "Шацк"
    };



    //Самарская область - список городов
    public static final String[] samaraRegion = new String[]{
            "Жигулёвск",
            "Кинель",
            "Нефтегорск",
            "Новокуйбышевск",
            "Октябрьск",
            "Отрадный",
            "Похвистнево",
            "Самара",
            "Сызрань",
            "Тольятти",
            "Чапаевск"
    };


    //Саратовская область - список городов
    public static final String[] saratovRegion = new String[]{
            "Аркадак",
            "Аткарск",
            "Балаково",
            "Балашов",
            "Вольск",
            "Ершов",
            "Калининск",
            "Красноармейск",
            "Красный Кут",
            "Маркс",
            "Новоузенск",
            "Петровск",
            "Пугачёв",
            "Ртищево",
            "Саратов",
            "Хвалынск",
            "Шиханы",
            "Энгельс"
    };



    //Сахалинская область - список городов
    public static final String[] sakhalinRegion = new String[]{
        "Александровск-Сахалинский",
        "Анива",
        "Долинск",
        "Корсаков",
        "Курильск",
        "Макаров",
        "Невельск",
        "Оха",
        "Поронайск",
        "Северо-Курильск",
        "Томари",
        "Углегорск",
        "Холмск",
        "Южно-Сахалинск"
    };



    //Свердловская область - список городов
    public static final String[] sverdlovskRegion = new String[]{
        "Екатеринбург",
        "Алапаевск",
        "Арамиль",
        "Артёмовский",
        "Асбест",
        "Берёзовский",
        "Богданович",
        "Верхний Тагил",
        "Верхняя Пышма",
        "Верхняя Салда",
        "Верхняя Тура",
        "Верхотурье",
        "Волчанск",
        "Дегтярск",
        "Заречный",
        "Ивдель",
        "Ирбит",
        "Каменск-Уральский",
        "Камышлов",
        "Карпинск",
        "Качканар",
        "Кировград",
        "Краснотурьинск",
        "Красноуральск",
        "Красноуфимск",
        "Кушва",
        "Лесной",
        "Михайловск",
        "Невьянск",
        "Нижние Серги",
        "Нижний Тагил",
        "Нижняя Салда",
        "Нижняя Тура",
        "Новая Ляля",
        "Новоуральск",
        "Первоуральск",
        "Полевской",
        "Ревда",
        "Реж",
        "Североуральск",
        "Серов",
        "Среднеуральск",
        "Сухой Лог",
        "Сысерть",
        "Тавда",
        "Талица",
        "Туринск"
    };



    //Смоленская область - список городов
    public static final String[] smolenskRegion = new String[]{
            "Велиж",
            "Вязьма",
            "Гагарин",
            "Демидов",
            "Десногорск",
            "Дорогобуж",
            "Духовщина",
            "Ельня",
            "Починок",
            "Рославль",
            "Рудня",
            "Сафоново",
            "Смоленск",
            "Сычёвка",
            "Ярцево"
    };



    //Ставропольский край - список городов
    public static final String[] stavropolRegion = new String[]{
            "Благодарный",
            "Будённовск",
            "Георгиевск",
            "Ессентуки",
            "Железноводск",
            "Зеленокумск",
            "Изобильный",
            "Ипатово",
            "Кисловодск",
            "Лермонтов",
            "Минеральные Воды",
            "Михайловск",
            "Невинномысск",
            "Нефтекумск",
            "Новоалександровск",
            "Новопавловск",
            "Пятигорск",
            "Светлоград",
            "Ставрополь"
    };



    //Тамбовская область - список городов
    public static final String[] tambovRegion = new String[]{
            "Кирсанов",
            "Котовск",
            "Мичуринск",
            "Моршанск",
            "Рассказово",
            "Жердевка",
            "Тамбов",
            "Уварово"
    };



    //Тверская область - список городов
    public static final String[] tverRegion = new String[]{
            "Андреаполь",
            "Бежецк",
            "Белый",
            "Бологое",
            "Весьегонск",
            "Вышний Волочёк",
            "Западная Двина",
            "Зубцов",
            "Калязин",
            "Кашин",
            "Кимры",
            "Конаково",
            "Красный Холм",
            "Кувшиново",
            "Лихославль",
            "Нелидово",
            "Осташков",
            "Ржев",
            "Старица",
            "Тверь",
            "Торжок",
            "Торопец",
            "Удомля"
    };



    //Томская область - список городов
    public static final String[] tomskRegion = new String[]{
            "Томск",
            "Асино",
            "Кедровый",
            "Колпашево",
            "Северск",
            "Стрежевой"
    };




    //Тульская область - список городов
    public static final String[] tulaRegion = new String[]{
        "Алексин",
        "Белёв",
        "Богородицк",
        "Болохово",
        "Венёв",
        "Донской",
        "Ефремов",
        "Кимовск",
        "Киреевск",
        "Липки",
        "Новомосковск",
        "Плавск",
        "Советск",
        "Суворов",
        "Тула",
        "Узловая",
        "Чекалин",
        "Щёкино",
        "Ясногорск"
    };



    //Тюменская область - список городов
    public static final String[] tyumenRegion = new String[]{
            "Заводоуковск",
            "Ишим",
            "Тобольск",
            "Тюмень",
            "Ялуторовск"
    };



    //Удмуртская Республика - список городов
    public static final String[] udmurtia = new String[]{
            "Воткинск",
            "Глазов",
            "Ижевск",
            "Камбарка",
            "Можга",
            "Сарапул"
    };




    //Ульяновская область - список городов
    public static final String[] ulyanovskRegion = new String[]{
            "Барыш",
            "Димитровград",
            "Инза",
            "Новоульяновск",
            "Сенгилей",
            "Ульяновск"
    };



    //Хабаровский край - список городов
    public static final String[] khabarovskRegion = new String[]{
            "Хабаровск",
            "Амурск",
            "Бикин",
            "Вяземский",
            "Комсомольск-на-Амуре",
            "Николаевск-на-Амуре",
            "Советская Гавань"
    };



    //Ханты-Мансийский автономный округ - Югра - список городов
    public static final String[] khantyMansiAutonomousOkrugYugra = new String[]{
            "Белоярский",
            "Когалым",
            "Лангепас",
            "Лянтор",
            "Мегион",
            "Нефтеюганск",
            "Нижневартовск",
            "Нягань",
            "Покачи",
            "Пыть-Ях",
            "Радужный",
            "Советский",
            "Сургут",
            "Урай",
            "Ханты-Мансийск",
            "Югорск"
    };



    //Челябинская область - список городов
    public static final String[] chelyabinskRegion = new String[]{
            "Аша",
            "Бакал",
            "Верхнеуральск",
            "Верхний Уфалей",
            "Еманжелинск",
            "Златоуст",
            "Карабаш",
            "Карталы",
            "Касли",
            "Катав-Ивановск",
            "Копейск",
            "Коркино",
            "Куса",
            "Кыштым",
            "Магнитогорск",
            "Миасс",
            "Миньяр",
            "Нязепетровск",
            "Озёрск",
            "Пласт",
            "Сатка",
            "Сим",
            "Снежинск",
            "Трёхгорный",
            "Троицк",
            "Усть-Катав",
            "Чебаркуль",
            "Челябинск",
            "Южноуральск",
            "Юрюзань"
    };



    //Чеченская Республика - список городов
    public static final String[] chechenRepublic = new String[]{
            "Аргун",
            "Грозный",
            "Гудермес",
            "Курчалой",
            "Урус-Мартан",
            "Шали"
    };



    //Чувашская Республика - Чувашия - список городов
    public static final String[] chuvashRepublicChuvashia = new String[]{
            "Чебоксары",
            "Новочебоксарск",
            "Алатырь",
            "Канаш",
            "Козловка",
            "Мариинский Посад",
            "Цивильск",
            "Шумерля",
            "Ядрин"

    };



    //Чукотский автономный округ - список городов
    public static final String[] chukotkaAutonomousDistrict = new String[]{
            "Анадырь",
            "Билибино",
            "Певек"

    };



    //Ямало-Ненецкий автономный округ - список городов
    public static final String[] yamaloNenetsAutonomousDistrict = new String[]{
        "Губкинский",
        "Лабытнанги",
        "Муравленко",
        "Надым",
        "Новый Уренгой",
        "Ноябрьск",
        "Салехард",
        "Тарко-Сале"
    };




    //Ярославская область - список городов
    public static final String[] yaroslavlRegion = new String[]{
            "Губкинский",
            "Лабытнанги",
            "Муравленко",
            "Надым",
            "Новый Уренгой",
            "Ноябрьск",
            "Салехард",
            "Тарко-Сале"
    };

}
