package com.example.sciencelab;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sciencelab.Experiment;
import com.example.sciencelab.ExperimentAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Experiment> experimentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        experimentList.add(new Experiment(1, "грипп \"Синева\"", "Опасный штамм гриппа, беспрервывно мутирующий в теле заболевшего.", "2024-11-19", "2025-01-19", "Планируется", "Гутман Вячеслав Павлович"));
        experimentList.add(new Experiment(4, "Матрица", "Некая теоретическая математическая сущность, являющееся разумным существом.", "2023-01-08", "2024-07-17", "Завершен", "Гутман Вячеслав Павлович"));
        experimentList.add(new Experiment(5, "Мозговой червь", "Паразит, развивающийся в мозге человека и заставляющий его подчинятся своей воле.", "2018-05-14", "2020-02-07", "Приостановлен", "Мальков Михаил Владимирович"));
        experimentList.add(new Experiment(6, "Машина времени", "Переделанная микроволнока, способная отправлять сообщения в прошлое.", "2021-10-08", "2027-07-23", "В процессе", "Гутман Вячеслав Павлович"));
        experimentList.add(new Experiment(14, "Кот Шредингера", "Теоретический эксперимент", "2024-01-01", "2026-01-01", "Планируется", "Гутман Вячеслав Павлович"));
        experimentList.add(new Experiment(15, "Портал в аниме-мир", "Аниме-мир - теоретическая параллельная реальность с персонажами из японской анимации", "2010-06-15", "2030-01-01", "В процессе", "Мальков Михаил Владимирович"));
        experimentList.add(new Experiment(16, "Гниль", "Болезнь, которая заставляет плоть разлагаться", "2004-01-01", "2012-01-01", "Завершен", "Мальков Михаил Владимирович"));
        experimentList.add(new Experiment(18, "Лекарство от смерти", "Флакон с субстанцией, которой, предположительно, 20 тысяч лет", "2015-10-26", "2025-01-02", "В процессе", "Гутман Вячеслав Павлович"));
        experimentList.add(new Experiment(19, "Изучение квантовых явлений", "Квантовая физика теоретически способно во много раз увеличить скорость вычислений", "2016-11-27", "2024-12-01", "В процессе", "Мальков Михаил Владимирович"));
        experimentList.add(new Experiment(31, "Изучение поведения бактерий в условиях низких температур", "Исследование способности микробов выживать при замораживании", "2019-03-15", "2024-11-30", "Завершен", "Смирнова Оксана Ивановна"));
        experimentList.add(new Experiment(32, "Влияние радиации на рост растений", "Оценка воздействия радиации на рост и развитие растений", "2020-06-20", "2025-02-18", "В процессе", "Петров Дмитрий Анатольевич"));
        experimentList.add(new Experiment(33, "Разработка биоремедиации почвы", "Использование микробных культур для очистки почвы от загрязнителей", "2021-01-10", "2026-03-15", "В процессе", "Кузнецова Лариса Борисовна"));
        experimentList.add(new Experiment(34, "Наночастицы в медицине", "Изучение применения наночастиц для доставки лекарств в целевые органы", "2022-05-05", "2027-11-10", "В процессе", "Захарова Наталья Сергеевна"));
        experimentList.add(new Experiment(35, "Использование солнечных панелей на основе графена", "Разработка эффективных солнечных панелей с использованием графеновых технологий", "2020-11-15", "2025-06-30", "Завершен", "Романова Ирина Викторовна"));
        experimentList.add(new Experiment(36, "Микробиом человека и его влияние на здоровье", "Изучение влияния микробов в организме на общее состояние здоровья человека", "2021-09-12", "2026-01-01", "В процессе", "Иванов Александр Викторович"));
        experimentList.add(new Experiment(37, "Синтетические органические материалы для медицины", "Разработка новых синтетических материалов для биосовместимости", "2018-04-10", "2027-10-20", "В процессе", "Федоров Андрей Павлович"));
        experimentList.add(new Experiment(38, "Экологическое воздействие пластиковых отходов", "Оценка воздействия пластиковых отходов на экосистемы морей и океанов", "2022-08-30", "2025-09-25", "В процессе", "Тимофеева Марина Николаевна"));
        experimentList.add(new Experiment(39, "Исследование биологических эффектов изменения климата", "Оценка воздействия изменений климата на биоразнообразие", "2019-10-15", "2024-12-01", "Завершен", "Чернова Елена Анатольевна"));
        experimentList.add(new Experiment(40, "Анализ воздействия микропластика на человека", "Исследование воздействия микропластиковых частиц на организм человека", "2021-02-20", "2026-07-15", "В процессе", "Дорофеева Светлана Павловна"));
        experimentList.add(new Experiment(41, "Исследование устойчивости микроорганизмов к антибиотикам", "Изучение способности микроорганизмов адаптироваться к различным антибиотикам и вырабатывать резистентность", "2022-03-10", "2027-09-05", "В процессе", "Михайлова Татьяна Юрьевна"));

        // Настроим адаптер
        ExperimentAdapter adapter = new ExperimentAdapter(this, experimentList, experiment -> {
            // Передаем данные в DetailActivity
            Intent intent = new Intent(MainActivity.this, ExperimentDetailActivity.class);
            intent.putExtra("TITLE", experiment.getTitle());
            intent.putExtra("DESCRIPTION", experiment.getDescription());
            intent.putExtra("START_DATE", experiment.getStartDate());
            intent.putExtra("END_DATE", experiment.getEndDate());
            intent.putExtra("STATUS", experiment.getStatus());
            intent.putExtra("RESEARCHER", experiment.getResearcher());
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);
    }
}
