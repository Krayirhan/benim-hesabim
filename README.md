# Benim Hesabim

Benim Hesabim, gelir ve gider takibi icin gelistirilmis basit ama duzenli mimarili bir Android uygulamasidir. Uygulama once lokal veritabani ile calisir, sonra da Supabase ile senkronizasyona uygun bir yapida ilerler.

## Proje Ozeti

Bu proje, kullanicinin para hareketlerini hizli sekilde kaydedebilecegi ve aylik durumunu gorebilecegi bir MVP olarak tasarlandi.

Temel hedefler:
- gelir ve gider eklemek
- islem gecmisini listelemek
- aylik ozet gormek
- email/sifre ile giris ve kayit yapmak
- offline-first calismak

## Kullanilan Teknolojiler

- Kotlin
- Jetpack Compose
- Material 3
- MVVM
- Hilt
- Room
- Coroutines + Flow
- Navigation Compose
- Supabase Auth
- Supabase PostgreSQL
- Gradle Version Catalog

## Guncel Sayfalar

Projede su anda aktif olan sayfalar:

- `SplashScreen`: oturum kontrolu yapar ve ilk yonlendirmeyi belirler
- `LoginScreen`: kullanicinin email ve sifre ile giris yapmasini saglar
- `RegisterScreen`: yeni kullanici kaydi olusturur
- `HomeScreen`: toplam gelir, gider ve kalan bakiyeyi gosterir
- `AddTransactionScreen`: yeni gelir veya gider ekler
- `TransactionListScreen`: tum islemleri listeler
- `SettingsScreen`: hesap bilgisi ve cikis islemleri icin kullanilir

## Gelecekte Eklenecek Sayfalar

Henuz projede olmayan ama mantikli gelisim sirasi icinde planlanabilecek sayfalar:

- `TransactionDetailScreen`
- `EditTransactionScreen`
- `ProfileScreen`
- `SyncStatusScreen`
- `AboutScreen`

Bu sayfalarin gorevleri:

- `TransactionDetailScreen`: tek bir islemin detayini gostermek
- `EditTransactionScreen`: mevcut bir islemi guncellemek
- `ProfileScreen`: kullanici bilgilerini tek yerde toplamak
- `SyncStatusScreen`: lokal veri ile Supabase arasindaki senkron durumunu gostermek
- `AboutScreen`: uygulama ve proje hakkinda bilgi vermek

## Supabase Kurulumu

1. Supabase Dashboard uzerinden yeni bir proje olustur.
2. `supabase/schema.sql` dosyasindaki SQL komutlarini Supabase SQL Editor icinde calistir.
3. Project Settings > API bolumunden `anon` key al.
4. `service_role` key'i mobil uygulamaya koyma.

## local.properties

`local.properties` dosyasina su degerleri ekle:

```properties
SUPABASE_URL=BURAYA_SUPABASE_PROJECT_URL_GIR
SUPABASE_ANON_KEY=BURAYA_SUPABASE_ANON_KEY_GIR
```

Gercek degerleri Supabase Dashboard > Project Settings > API bolumunden al.

## Ornek Dosyalar

Repoda ornek olarak sunlar bulunur:

- `.env.example`
- `local.properties.example`

Gercek `local.properties` ve `.env` dosyalari `.gitignore` ile disarida tutulur.

## Supabase SQL Dosyasini Calistirma

1. Supabase Dashboard ac.
2. SQL Editor bolumune git.
3. `supabase/schema.sql` icerigini yapistir.
4. Calistir.

## Calistirma Adimlari

1. Android Studio ile projeyi ac.
2. `local.properties` dosyasini olustur ve Supabase degerlerini gir.
3. Gradle sync yap.
4. Uygulamayi calistir.

## Proje Klasor Yapisi

```text
com.benimhesabim.app
|-- core
|   |-- common
|   |-- designsystem
|   |-- util
|   `-- navigation
|-- data
|   |-- local
|   |   |-- database
|   |   |-- dao
|   |   `-- entity
|   |-- remote
|   |   |-- supabase
|   |   `-- dto
|   |-- repository
|   `-- mapper
|-- domain
|   |-- model
|   |-- repository
|   `-- usecase
|-- feature
|   |-- auth
|   |-- home
|   |-- transaction
|   `-- settings
`-- app
    |-- MainActivity
    `-- BenimHesabimApplication
```

## MVP Ozellikleri

- Splash ekran
- Email/sifre giris
- Email/sifre kayit
- Ana ekranda aylik ozet
- Gelir/gider ekleme
- Islem listesi
- Ayarlar ekranı
- Room tabanli offline-first kayit
- Basit Supabase senkronizasyonu

## GitHub'a Yukleme

Proje zaten `main` branch uzerinde tutuluyor. Degisiklikleri GitHub'a gondermek icin:

```bash
git status
git add README.md app build.gradle.kts gradle/libs.versions.toml
git commit -m "Update project overview and app changes"
git push origin main
```

Onemli notlar:

- `local.properties` commit'e girmemeli
- `.env` commit'e girmemeli
- gercek Supabase key'leri repoya eklenmemeli
- build ciktilari commit'e girmemeli

