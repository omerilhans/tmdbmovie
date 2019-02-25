
        (TDMB Api Url:  https://www.themoviedb.org/documentation/api)

        TMDB Api kullanarak yaptığım android uygulama çalışma süreçleri;

 * Explain the project in general and how to run it.

- Splash ekranıyla başlayan uygulama, 1.250 sn sonra Main ekranına geçer, Main açılır açılmaz;
  sırasıyla, Tüm Filmler, Vizyondaki Filmler, Popüler Filmler, En Beğenilen Filmler, Yakında Gelecek Filmler
  Tab seçenekleri için servisten datalar alınır ve tablar ViewPagerAdapter yardımıyla oluşturulur ve listeler gösterilir.

  Her Tab ekranı tek bir Fragment kopyasıdır. Hepsinde Search alanı ve liste içerikleri mevcuttur.
  Listede seçilen filmler için Detail ekranı açılır ve seçilen film id'si ile film detayları servisten çekilip
  ekranda gösterilir.

  Her Tab içinde listeler sonsuza kadar aşşağı doğru swipe edilebilir.

  * Why you have selected that software architecture?

    - Projede herhangi bir Enterprise pattern kullanılmadı.

  * Which software design principles you have followed in your project?

    - Projede kullanılan design pattern'ler;
           - Singleton Design Pattern
           - Static Factory Method

  * What would you change if you had more time?

    - Proje yeterli zaman olduğunda, MVP / MVVM ile tasarlanabilir.

  * What to do to make the app production ready?

    - Projenin release sürümü şifrelenerek çıkarılıp, yayınlanmaya hazır hale alınabilir.

  * Does your project require any particular tool to be able to run?

    - Projenin çalıştırılması için açılması planlanan yerde, Android Studio üzerinde,
      gerekli gradle ayarları yapılmış olması yeterlidir.





  -Projede third party library'lerden kullanılanlar,

   - ButterKnife
   - Retrofit2
   - Dagger2
   - JacksonConverter
   - Gson
   - Glide
   - Apache-Common Lang3
   - RxAndroid
   - Material Library
   - GlasFish Javax Annotation
   - OkHttp
   - PagerIndicator.


