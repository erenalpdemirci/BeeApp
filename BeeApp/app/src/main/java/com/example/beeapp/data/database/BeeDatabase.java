package com.example.beeapp.data.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.beeapp.data.dao.BeeDao;
import com.example.beeapp.data.model.Bee;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Bee.class}, version = 1)
public abstract class BeeDatabase extends RoomDatabase {
    private static volatile BeeDatabase INSTANCE;
    private static final ExecutorService executor = Executors.newSingleThreadExecutor();

    public abstract BeeDao beeDao();

    public static BeeDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (BeeDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            BeeDatabase.class,
                            "bee_database"
                    ).addCallback(new RoomDatabase.Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            executor.execute(() -> {
                                BeeDao dao = INSTANCE.beeDao();
                                dao.insertAll(getInitialData());
                            });
                        }
                    }).build();
                }
            }
        }
        return INSTANCE;
    }

    private static List<Bee> getInitialData() {
        List<Bee> bees = new ArrayList<>();
        
        Bee bee1 = new Bee();
        bee1.setName("Bal Arısı");
        bee1.setType("Apis mellifera");
        bee1.setJob("Bal üretimi, polen taşıma ve bitki tozlaşması");
        bee1.setFeatures("• 12-15 mm uzunluğunda\n• Sarı-siyah bantlı vücut\n• 6 bacak ve 2 çift kanat\n• Özel polen sepetleri\n• Dans dili ile iletişim yeteneği\n• 20-25 km/s uçuş hızı");
        bee1.setDescription("Bal arıları, doğanın en önemli tozlayıcılarından biridir. 50.000'e kadar üyesi olan koloniler halinde yaşarlar. Bir kolonide:\n\n" +
                "• 1 kraliçe arı (günde 2000'e kadar yumurta)\n" +
                "• Birkaç yüz erkek arı\n" +
                "• Binlerce işçi arı bulunur\n\n" +
                "İşçi arılar:\n" +
                "• Nektar ve polen toplar\n" +
                "• Petek örer\n" +
                "• Koloninin bakımını yapar\n" +
                "• Larvaları besler\n" +
                "• Kovanı savunur\n\n" +
                "Yaşam süreleri:\n" +
                "• Kraliçe: 3-5 yıl\n" +
                "• İşçi arı (yaz): 4-6 hafta\n" +
                "• İşçi arı (kış): 4-6 ay\n\n" +
                "Bir işçi arı ömrü boyunca yaklaşık 1/12 çay kaşığı bal üretir. 1 kg bal için arılar 4 milyon çiçeği ziyaret eder.");
        bee1.setYoutubeVideoId("ZtmPt0_v4IE");
        bees.add(bee1);

        Bee bee2 = new Bee();
        bee2.setName("İtalyan Arısı");
        bee2.setType("Apis mellifera ligustica");
        bee2.setJob("Yüksek verimli bal üretimi ve etkili tozlaşma");
        bee2.setFeatures("• 13-15 mm uzunluğunda\n• Açık sarı-altın rengi vücut\n• Sakin ve uysal karakter\n• Güçlü koloni yapısı\n• Hijyenik davranış\n• Düşük oğul verme eğilimi");
        bee2.setDescription("İtalyan arıları, dünyada en çok tercih edilen bal arısı türüdür. Ana vatanı İtalya'dır.\n\n" +
                "Önemli özellikleri:\n" +
                "• Sakin ve uysal yapı\n" +
                "• Yüksek bal üretim kapasitesi\n" +
                "• Hastalıklara karşı direnç\n" +
                "• Hızlı koloni gelişimi\n" +
                "• Mükemmel kovan temizliği\n\n" +
                "Üretim kapasitesi:\n" +
                "• Yıllık bal üretimi: 35-40 kg\n" +
                "• Polen toplama: Yüksek verim\n" +
                "• Propolis üretimi: Orta seviye\n\n" +
                "Davranış özellikleri:\n" +
                "• Kovan savunması: Orta seviye\n" +
                "• Yağmacılık eğilimi: Düşük\n" +
                "• Kışlama yeteneği: İyi\n" +
                "• Hastalık direnci: Yüksek");
        bee2.setYoutubeVideoId("whyBvLIq4zo");
        bees.add(bee2);

        Bee bee3 = new Bee();
        bee3.setName("Kafkas Arısı");
        bee3.setType("Apis mellifera caucasia");
        bee3.setJob("Derin çiçek nektarı toplama ve bal üretimi");
        bee3.setFeatures("• Gri-siyah renkte vücut\n• Uzun dil yapısı (7.2 mm)\n• Sakin mizaç\n• Yüksek propolis kullanımı\n• Soğuğa dayanıklı\n• Ekonomik kışlama");
        bee3.setDescription("Kafkas arıları, Kafkas dağlarının yerli ırkıdır ve özel özellikleriyle tanınır.\n\n" +
                "Üstün özellikleri:\n" +
                "• En uzun dilli bal arısı ırkı\n" +
                "• Derin çiçeklerden nektar toplama\n" +
                "• Yüksek propolis kullanımı\n" +
                "• Soğuk iklime adaptasyon\n\n" +
                "Koloni özellikleri:\n" +
                "• Kışlama kabiliyeti: Mükemmel\n" +
                "• Bal tüketimi: Ekonomik\n" +
                "• Oğul verme: Düşük eğilim\n" +
                "• Hastalık direnci: Yüksek\n\n" +
                "Üretim değerleri:\n" +
                "• Yıllık bal üretimi: 30-35 kg\n" +
                "• Propolis üretimi: Çok yüksek\n" +
                "• Nektar toplama: Özellikle yonca ve üçgülde başarılı");
        bee3.setYoutubeVideoId("q7NFmLPvQnE");
        bees.add(bee3);

        Bee bee4 = new Bee();
        bee4.setName("Anadolu Arısı");
        bee4.setType("Apis mellifera anatoliaca");
        bee4.setJob("Yerel flora tozlaşması ve bal üretimi");
        bee4.setFeatures("• Koyu renkli vücut\n• Orta büyüklük\n• Güçlü uçuş yeteneği\n• Yüksek savunma içgüdüsü\n• Hızlı bahar gelişimi\n• Yerel koşullara adaptasyon");
        bee4.setDescription("Anadolu arıları, Türkiye'nin yerli arı ırkıdır ve binlerce yıllık doğal seleksiyonun ürünüdür.\n\n" +
                "Temel özellikler:\n" +
                "• Yerel floraya mükemmel uyum\n" +
                "• Yüksek kışlama kabiliyeti\n" +
                "• Hastalıklara dayanıklılık\n" +
                "• Çalışkanlık ve dayanıklılık\n\n" +
                "Davranış özellikleri:\n" +
                "• Kovan savunması: Çok güçlü\n" +
                "• İlkbahar gelişimi: Hızlı\n" +
                "• Oğul verme eğilimi: Yüksek\n" +
                "• Propolis kullanımı: Orta-yüksek\n\n" +
                "Üretim değerleri:\n" +
                "• Yıllık bal üretimi: 25-30 kg\n" +
                "• Kış bal tüketimi: Ekonomik\n" +
                "• Yerel çiçeklerden yararlanma: Çok başarılı");
        bee4.setYoutubeVideoId("38jiPlUL6vM");
        bees.add(bee4);

        Bee bee5 = new Bee();
        bee5.setName("Bombus Arısı");
        bee5.setType("Bombus terrestris");
        bee5.setJob("Sera tozlaşması ve doğal ekosistemin sürdürülebilirliği");
        bee5.setFeatures("• 20-25 mm uzunluğunda\n• Tüylü ve iri yapı\n• Siyah-sarı bantlı vücut\n• Güçlü titreşim yeteneği\n• Soğukta çalışabilme\n• Uzun dil yapısı");
        bee5.setDescription("Bombus arıları, özellikle sera tarımında vazgeçilmez tozlayıcılardır.\n\n" +
                "Özel yetenekleri:\n" +
                "• -10°C'ye kadar çalışabilme\n" +
                "• Titreşimli tozlaşma yapabilme\n" +
                "• Kapalı ortamlarda navigasyon\n" +
                "• Hızlı öğrenme yeteneği\n\n" +
                "Koloni yapısı:\n" +
                "• 50-150 birey\n" +
                "• Yılda 6-8 döngü\n" +
                "• Kraliçe + işçi arılar\n\n" +
                "Tarımsal önemi:\n" +
                "• Domates tozlaşması\n" +
                "• Biber tozlaşması\n" +
                "• Patlıcan tozlaşması\n" +
                "• Çilek tozlaşması\n" +
                "• Yaban çiçekleri tozlaşması");
        bee5.setYoutubeVideoId("EJSyYqfd3y8");
        bees.add(bee5);

        Bee bee6 = new Bee();
        bee6.setName("Yaban Arısı");
        bee6.setType("Xylocopa violacea");
        bee6.setJob("Doğal tozlaşma ve ekosistem dengesi koruma");
        bee6.setFeatures("• 20-30 mm uzunluğunda\n• Metalik mor-siyah renk\n• Güçlü çene yapısı\n• Uzun uçuş menzili\n• Soliter yaşam\n• Ahşap oyma yeteneği");
        bee6.setDescription("Yaban arıları, doğal ekosistemin önemli üyeleridir ve tek başına yaşarlar.\n\n" +
                "Yaşam döngüsü:\n" +
                "• İlkbaharda yuva yapımı\n" +
                "• Ahşap içinde tünel açma\n" +
                "• Yumurta bırakma\n" +
                "• Besin paketi hazırlama\n\n" +
                "Ekolojik önemi:\n" +
                "• Büyük çiçeklerin tozlaşması\n" +
                "• Biyoçeşitliliğin korunması\n" +
                "• Doğal denge sürdürme\n\n" +
                "Tehditler:\n" +
                "• Habitat kaybı\n" +
                "• Kentleşme\n" +
                "• Tarım ilaçları\n" +
                "• İklim değişikliği\n\n" +
                "Koruma statüsü: Nesli tehlike altında");
        bee6.setYoutubeVideoId("FvBRbfdy7T8");
        bees.add(bee6);

        return bees;
    }
}